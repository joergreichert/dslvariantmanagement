package org.openarchitectureware.var.tailor.code;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.emf.mwe.core.WorkflowContext;
import org.eclipse.emf.mwe.core.issues.Issues;
import org.eclipse.emf.mwe.core.monitor.ProgressMonitor;
import org.eclipse.emf.mwe.utils.StandaloneSetup;
import org.openarchitectureware.var.featureaccess.ConfigurationModelWrapper;
import org.openarchitectureware.var.featureaccess.ext.FeatureStringSupport;
import org.openarchitectureware.var.featureaccess.ext.FeatureSupport;

/**
 * Workflow CoderComponent for <em>cutting</em> the Java files. <br>
 * <b>Workflow Input:</b>
 * <ul>
 * <li>Configuration Model
 * <li>Source Path
 * <li>Gen Path
 * </ul>
 * <p>
 * The java source files within the source path are read, the feature comments
 * are evaluated and, depending on the evaluation, the source code within
 * commments is removed or not.
 * </p>
 * 
 * @author rubinv
 * 
 */
public class CoderComponent4Java extends BaseComponent {

	private enum CommentType {
		BEGIN, END
	};

	/**
	 * Private final Class for structuring the feature comments
	 */
	private final class CommentStructure {
		private final int line;
		private final String clause;
		private final boolean evaluation;
		private final CommentType type;

		CommentStructure(int line, String clause, boolean evaluation,
				CommentType type) {
			this.line = line;
			this.clause = clause;
			this.evaluation = evaluation;
			this.type = type;
		}

		public CommentType getType() {
			return type;
		}

		public int getLine() {
			return line;
		}

		public String getClause() {
			return clause;
		}

		public boolean isEvaluation() {
			return evaluation;
		}
	}

	/** Java Extension Constant */
	private static final String JAVA_EXT = ".java";
	/** Start Comment */
	// TODO: comments must be done configurable
	private static final String FEATURE_SECTION_BEGIN = "#BEGIN";
	/** End Comment */
	private static final String FEATURE_SECTION_END = "#END";
	/**
	 * End of the Feature Comment (is needed because comments can be splitted in
	 * several Strings by the formatter)
	 */
	private static final String FEATURE_COMMENT_END = "#";

	/** FeatureStringSupported is used for evaluating features */
	private FeatureStringSupport featureStringSupport;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.emf.mwe.core.lib.AbstractWorkflowComponent#invokeInternal
	 * (org.eclipse.emf.mwe.core.WorkflowContext,
	 * org.eclipse.emf.mwe.core.monitor.ProgressMonitor,
	 * org.eclipse.emf.mwe.core.issues.Issues)
	 */
	@Override
	protected void invokeInternal(WorkflowContext ctx, ProgressMonitor monitor,
			Issues issues) {
		this.issues = issues;
		String root = StandaloneSetup.getPlatformRootPath();

		// find out the selected features
		ConfigurationModelWrapper configModel = getConfigurationModel(ctx,
				issues);
		if (configModel == null)
			return;
		//selectedFeatues = configModel.findSelectedFeatureNames();

		// init featureStringSupport for Feature Parsing and Evaluation
		featureStringSupport = FeatureStringSupport.getInstance();
		FeatureSupport.setConfigurationModelWrapper(configModel);

		// find the list of java files
		fullSourcePath = normalizePath(pathAppend(root, sourcePath));
		fullGenPath = normalizePath(pathAppend(root, genPath));
		File srcDir = new File(fullSourcePath);
		List<File> files = new ArrayList<File>();
		collect(srcDir, files, JAVA_EXT);
		Collections.sort(files, new Comparator<File>() {
			public int compare(File f1, File f2) {
				String n1 = stripExtension(f1.getName());
				String n2 = stripExtension(f2.getName());
				return n1.compareTo(n2);
			}

			private String stripExtension(String name) {
				return name.substring(0, name.indexOf('.'));
			}
		});

		// process feature dependencies for each relevant file
		for (File file : files) {
			try {
				processFeatureDependencies(file);
			} catch (IOException e) {
				issues.addError("cannot process file " + file.getAbsolutePath()
						+ ". Reason: " + e.getMessage());
			}
		}
	}

	/**
	 * Processing feature dependencies for a File.
	 * 
	 * @param file
	 * @throws IOException
	 */
	private void processFeatureDependencies(File file) throws IOException {
		List<String> lines = loadFile(file);
		List<String> newLines = handleFeatureDependencies(lines);

		writeFile(newLines, buildTargetFileName(file.getAbsoluteFile()));
	}

	/**
	 * Entry Point for handling feature dependencies for strings from file.<br>
	 * Iterate over comments, collect a set of strings to remove, remove them.
	 * 
	 * @param lines
	 * @return a list of rest lines
	 */
	private List<String> handleFeatureDependencies(List<String> lines) {
		List<CommentStructure> comments = buildCommentsStructure(lines);
		TreeSet<Integer> lines2Remove = new TreeSet<Integer>();

		for (int i = 0; i < comments.size(); i++) {
			CommentStructure comment = comments.get(i);

			if (CommentType.BEGIN.equals(comment.getType())) {
				CommentStructure endComment = findScope(comments, comment, i);

				if (endComment != null) {
					if (comment.isEvaluation()) {
						feedLines2Remove(lines, lines2Remove, comment);
						feedLines2Remove(lines, lines2Remove, endComment);
					} else {
						feedLines2Remove(lines, lines2Remove, endComment);
						for (int j = endComment.getLine() - 1; j >= comment
								.getLine(); j--) {
							lines2Remove.add(j);
						}
					}
				}
			}
		}
		// removing strings from end to beginning
		Iterator<Integer> it = lines2Remove.descendingIterator();
		while (it.hasNext()) {
			lines.remove((int) it.next());
		}

		return lines;
	}

	/**
	 * Add the lines that have to be removed to the list
	 * 
	 * @param lines
	 * @param lines2Remove
	 * @param comment
	 */
	private void feedLines2Remove(List<String> lines,
			Set<Integer> lines2Remove, CommentStructure comment) {
		for (int j = comment.getLine(); j < lines.size(); j++) {
			if (lines.get(j).contains(FEATURE_COMMENT_END)) {
				lines2Remove.add(j);
				break;
			}
		}
	}

	/**
	 * Find end comment for the begin one.
	 * 
	 * @param comments
	 * @param comment
	 * @param iStart
	 * @return the end comment
	 */
	private CommentStructure findScope(List<CommentStructure> comments,
			CommentStructure comment, int iStart) {
		int beginsCounter = 1;

		for (int i = iStart + 1; i < comments.size(); i++) {
			CommentStructure currentComment = comments.get(i);

			if (currentComment.getClause().equals(comment.clause)) {
				if (CommentType.BEGIN.equals(currentComment.getType())) {
					++beginsCounter;
				} else // CommentType.END
				{
					if (--beginsCounter == 0) {
						return currentComment;
					}
				}

			}
		}
		// happens only if comments are not properly filled
		return null;
	}

	/**
	 * Build a list of comments. <br>
	 * Iterate through file and sort out the comments.
	 * 
	 * @param lines
	 * @return a list of comments
	 */
	private List<CommentStructure> buildCommentsStructure(List<String> lines) {
		List<CommentStructure> newComments = new ArrayList<CommentStructure>();

		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);

			if (isFeatureCommentBegin(line)) {
				String featureClause = extractFeatureClauseFromComment(lines,
						i, FEATURE_SECTION_BEGIN);
				boolean evaluation = isFeatureClauseTrue(featureClause);

				newComments.add(new CommentStructure(i, featureClause,
						evaluation, CommentType.BEGIN));
			} else if (isFeatureCommentEnd(line)) {
				String featureClause = extractFeatureClauseFromComment(lines,
						i, FEATURE_SECTION_END);
				boolean evaluation = isFeatureClauseTrue(featureClause);

				newComments.add(new CommentStructure(i, featureClause,
						evaluation, CommentType.END));
			}
		}

		return newComments;
	}

	/**
	 * The method extracts a feature clause String from the commented String
	 * like<br>
	 * <em>//#BEGIN [FeatureClause] #</em> <br>
	 * or from a set of commented strings (one Feature Clause String can be
	 * wrapped by a formatter) like<br>
	 * <em>//#BEGIN [FeatureClause ...<br>
	 * //...<br>
	 * // .... FeatureClause] #</em>
	 * 
	 * <br>
	 * 
	 * @param lines
	 * @param iStartLine
	 * @param commentStart
	 * @return a Feature Clause String
	 */
	private String extractFeatureClauseFromComment(List<String> lines,
			int iStartLine, String commentStart) {
		String result = "";
		String startLine = lines.get(iStartLine);

		if (startLine.endsWith(FEATURE_COMMENT_END)) { // one line comment
			int iFeatureCommentBegin = startLine.indexOf(commentStart);
			int iFeatureCommentEnd = startLine.lastIndexOf(FEATURE_COMMENT_END);

			result = startLine.substring(
					iFeatureCommentBegin + commentStart.length(),
					iFeatureCommentEnd).trim();
		} else { // multiple lines comments
			int iEndLine = 0;
			String endLine = "";
			// iterate over commented lines
			boolean commentLinesFinished = false;
			int i = iStartLine;
			while (!commentLinesFinished) {
				String nextLine = lines.get(++i);

				if (nextLine.contains(SINGLE_LINE_COMMENT_DEFAULT)) {
					if (nextLine.endsWith(FEATURE_COMMENT_END)) {
						// comment was finished with #
						iEndLine = i;
						endLine = nextLine;
						commentLinesFinished = true;
					}
				} else { // comment was not properly finished with #
					commentLinesFinished = true;
				}
			}

			if (iEndLine != 0) // comment is properly finished
			{
				result = startLine.substring(startLine.indexOf(commentStart)
						+ commentStart.length());
				for (int j = iStartLine + 1; j < iEndLine; j++) {
					result += lines.get(j).substring(
							SINGLE_LINE_COMMENT_DEFAULT.length()).trim();
				}
				result += endLine.substring(
						SINGLE_LINE_COMMENT_DEFAULT.length(),
						endLine.indexOf(FEATURE_COMMENT_END)).trim();
			}
		}

		return result;
	}

	/**
	 * @param st
	 * @return is "begin" feature comment
	 */
	private boolean isFeatureCommentBegin(String st) {
		return st.matches("[\\t ]*" + SINGLE_LINE_COMMENT_DEFAULT + "[\\t ]*"
				+ FEATURE_SECTION_BEGIN + "(.)+");
	}

	/**
	 * @param st
	 * @return is "end" feature comment
	 */
	private boolean isFeatureCommentEnd(String st) {
		return st.matches("[\\t ]*" + SINGLE_LINE_COMMENT_DEFAULT + "[\\t ]*"
				+ FEATURE_SECTION_END + "(.)+");
	}

	/**
	 * File writer
	 * 
	 * @param processedLines
	 * @param targetFileName
	 * @throws IOException
	 */
	protected void writeFile(List<String> processedLines, String targetFileName)
			throws IOException {
		File dir = new File(targetFileName.substring(0, targetFileName
				.lastIndexOf('/')));
		dir.mkdirs();
		FileWriter w = new FileWriter(targetFileName);
		for (String line : processedLines) {
			w.write(line + "\n");
		}
		w.close();
	}

	/**
	 * Feature evaluation method
	 * 
	 * @param featureClause
	 * @return
	 */
	private boolean isFeatureClauseTrue(String featureClause) {
		return featureStringSupport.isFeatureClauseTrue(featureClause);
	}

}
