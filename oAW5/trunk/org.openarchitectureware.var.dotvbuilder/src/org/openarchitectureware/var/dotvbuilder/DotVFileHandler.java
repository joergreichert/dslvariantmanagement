package org.openarchitectureware.var.dotvbuilder;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.internal.resources.ResourceException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.parsetree.SyntaxError;
import org.openarchitectureware.var.featureaccess.FeatureAccessFactory;
import org.openarchitectureware.var.featureaccess.FeatureModelWrapper;
import org.openarchitectureware.var.features.FeaturesRuntimeModule;
import org.openarchitectureware.var.features.FeaturesStandaloneSetup;
import org.openarchitectureware.var.features.features.Atom;
import org.openarchitectureware.var.features.features.Feature;
import org.openarchitectureware.var.features.parser.antlr.FeaturesParser;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class DotVFileHandler {
	
	static{
		FeaturesStandaloneSetup.doSetup();
	}
	private static Injector injector = Guice.createInjector(new FeaturesRuntimeModule());
	
	private final class CommentStructure {
		private final int line;
		private final String clause;
		private final CommentType type;

		CommentStructure(int line, String clause, CommentType type) {
			this.line = line;
			this.clause = clause;
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

	}
	
	private static final String FEATURE_SECTION_BEGIN = "#BEGIN";
	private static final String FEATURE_SECTION_END = "#END";
	protected static final String SINGLE_LINE_COMMENT_DEFAULT = "//";
	private static final String FEATURE_COMMENT_END = "#";
	
	public static final String PLATFORM_RESOURCE = "platform:/resource";
	
	private static Log log = LogFactory.getLog(DotVFileHandler.class);
	
	private DotVBuilder builder;
	private List<String> featureNames = null;
	private String featureModelUri;

	public DotVFileHandler( DotVBuilder builder ) {
		this.builder = builder;
	}
	
	private enum CommentType {
		BEGIN, END
	};
	
	void checkFile(IResource resource) {
		if ( resource instanceof IFile ) {
			IFile file = (IFile) resource;
			if ( file.getName().endsWith("xfm.xmi") ) {
				String thisUri = resource.getFullPath().toString();
				featureModelUri = getFeatureModelUri(file.getProject());
				if ( thisUri.equals(featureModelUri) ) refreshFeatureModel(file.getProject());
			}
			if ( file.getName().endsWith(".v")) {
				builder.deleteMarkers(file);
				checkFilenameDepenency(file);
				checkFileContents( file );
			}
			if ( file.getName().endsWith(".java")) {
				try {
					List<CommentStructure> comments = buildCommentsStructure( loadFile(file) );
					if(comments.size() > 0){
						for (CommentStructure c : comments) {
							IParseResult result = injector.getInstance(FeaturesParser.class)
							 .parse("FeatureClause", new ByteArrayInputStream(c.clause.getBytes()));
							if(result.getParseErrors().size() > 0){
								String message = "";
								for (SyntaxError err : result.getParseErrors()) 
									message += err.getMessage() + "\n";
								Exception e = new RuntimeException("parse error: " + message);
								log.error("error while parsing feature clause", e);
							}
							EObject clause = result.getRootASTElement();
							for(EObject e : EcoreUtil2.eAllContentsAsList(clause)){
								if(e instanceof Feature || e instanceof Atom){
									String feature = e.eGet( e.eClass().getEStructuralFeature("feature")).toString();
									if( ! isFeatureDefined(file, feature) )
										addMarkerIfNotDefined(feature , file, c.line);
								}
							}
						}
					}
				} catch (ResourceException e) {
					log.error("error loading file", e);
				}catch (Throwable e) {
					log.error("error loading file", e);
				} 
			}
		}
	}
	
	
	public List<CommentStructure> buildCommentsStructure(List<String> lines) {
		List<CommentStructure> newComments = new ArrayList<CommentStructure>();

		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);

			if (isFeatureCommentBegin(line)) {
				String featureClause = extractFeatureClauseFromComment(lines,
						i, FEATURE_SECTION_BEGIN);

				newComments.add(new CommentStructure(i, featureClause, CommentType.BEGIN));
			} else if (isFeatureCommentEnd(line)) {
				String featureClause = extractFeatureClauseFromComment(lines,
						i, FEATURE_SECTION_END);

				newComments.add(new CommentStructure(i, featureClause, CommentType.END));
			}
		}

		return newComments;
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
	
	private List<String> loadFile(IFile file) throws FileNotFoundException,
			IOException, CoreException {
		List<String> lines = new ArrayList<String>();
		DataInputStream in = new DataInputStream(file.getContents());
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		while (br.ready()) {
			String line = br.readLine();
			lines.add(line);
		}
		br.close();
		return lines;
	}

	private String getFeatureModelUri(IProject p) {
		String uri = DotVBuilder.getValue(p, PreferenceConstants.FEATURE_MODEL_URI);
		if ( uri.toLowerCase().startsWith(PLATFORM_RESOURCE) ) {
			uri = uri.substring(PLATFORM_RESOURCE.length());
		}
		return uri;
	}

	private FeatureModelWrapper getFeatureModelWrapper(IProject project) {
		FeatureModelWrapper w = wrappers.get(project);
		if ( w == null ) {
			String uri = getFeatureModelUri(project);
			try {
				w = FeatureAccessFactory.getFeatureModelWrapper(uri);
				wrappers.put(project, w);
				return w;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
		}
		return w;
	}

	private void checkFileContents(IFile file) {
		BufferedReader br = null;
		try {
			int line = 0;
			br = new BufferedReader( new InputStreamReader( file.getContents() ) );
			while( br.ready() ) {
				line++;
				String s = br.readLine();
				addMarkerIfNotDefined(extractFeatureName_FeatureDependency( file, s ), file, line);
				addMarkerIfNotDefined(extractFeatureName_Joinpoint( file, s  ), file, line);
				addMarkerIfNotDefined(extractFeatureName_Advice( file, s, line ), file, line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {}
		}
		
	}

	private void addMarkerIfNotDefined(String featureName, IFile file, int line) {
		if ( featureName != null ) {
			if ( !isFeatureDefined(file, featureName ) ) {
				builder.addMarker( file, "Feature '"+featureName+"' referenced in file does not exist in feature model.", line, IMarker.SEVERITY_ERROR);
			}
		}
	}

	private String extractFeatureName_FeatureDependency(IFile file, String line) {
		String tag = DotVBuilder.getValue(file, PreferenceConstants.FEATURE_BEGIN);
		String s = tryToFindAndExtract( line, tag );
//		if ( s != null ) return s;
//		tag = builder.getValue(file, PreferenceConstants.FEATURE_END);
//		s = tryToFindAndExtract( line, tag );
		return s;
	}
		
	private String extractFeatureName_Joinpoint(IFile file, String line) {
		String tag = DotVBuilder.getValue(file, PreferenceConstants.JOINPOINT_SECTION_BEGIN);
		String s = tryToFindAndExtract( line, tag );
//		if ( s != null ) return s;
//		tag = DotVBuilder.getValue(file, PreferenceConstants.JOINPOINT_SECTION_END);
//		s = tryToFindAndExtract( line, tag );
		return s;
	}
		
	private String extractFeatureName_Advice(IFile file, String line, int lineNo) {
		String tag = DotVBuilder.getValue(file, PreferenceConstants.ADVICE_SECTION_BEGIN);
		String s = tryToFindAndExtract( line, tag );
		if ( s != null ) return extractAdvice(file, lineNo, s);
//		tag = DotVBuilder.getValue(file, PreferenceConstants.ADVICE_SECTION_END);
//		s = tryToFindAndExtract( line, tag );
//		if ( s != null ) {
//			return extractAdvice(file, lineNo, s);
//		} 
		return null;
	}

	private String extractAdvice(IFile file, int lineNo, String s) {
		String[] pair = s.split(":");
		if (pair.length != 2 ) { 
			builder.addMarker( file, "Invalid Advice Syntax", lineNo, IMarker.SEVERITY_ERROR);
			return null;
		}
		if ( pair[0].equals("before") || 
		     pair[0].equals("after") ||
			 pair[0].equals("instead") ) return pair[1];
		return null;
	}
		
	private String tryToFindAndExtract(String line, String tag) {
		int p = line.indexOf(tag);
		if ( p >= 0 ) {
			int start = p+tag.length()+1;
			int end = line.indexOf(' ',start+2);
			if ( end >= 0 ) return line.substring(start, end).trim();
			return line.substring(start).trim();
		}
		return null;
	}

	private void checkFilenameDepenency(IFile file) {
		String fileFeatureToken = builder.getValue(file, PreferenceConstants.FEATURE_IN_FILENAME);
		String fileName = file.getName();
		int p = fileName.indexOf(fileFeatureToken); 
		if ( p >= 0) {
			String featureName = fileName.substring(p+1, fileName.indexOf('.',p));
			if ( !isFeatureDefined(file, featureName) ) {
				builder.addMarker( file, "Feature '"+featureName+"' referenced in file name does not exist in in feature model.", 0, IMarker.SEVERITY_ERROR);
			}
		}
	}
	
	private Map<IProject, FeatureModelWrapper> wrappers = new HashMap<IProject, FeatureModelWrapper>();

	public void refreshFeatureModel(IProject project) {
		getFeatureModelWrapper(project).loadFeatureData(getFeatureModelUri(project));
	}
	
	private boolean isFeatureDefined( IFile file, String feature ) {
		FeatureModelWrapper w = getFeatureModelWrapper(file.getProject());
		if ( !w.isLoaded() ) refreshFeatureModel(file.getProject());
		return w.isFeatureDefined( feature );
	}

}
