package org.openarchitectureware.var.tailor.code;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;


import org.eclipse.emf.mwe.utils.StandaloneSetup;
import org.eclipse.xtext.var.featureaccess.ConfigurationModelWrapper;
import org.eclipse.emf.mwe.core.WorkflowContext;
import org.eclipse.emf.mwe.core.issues.Issues;
import org.eclipse.emf.mwe.core.monitor.ProgressMonitor;

public class CoderComponent extends BaseComponent {
	
	private static final String FEATURE_SECTION_BEGIN = "#";
	private static final String FEATURE_SECTION_END = "~#";

	private List<String> selectedFeatues;
	
	private String featureSelectionBeginToken() {
		return singleLineCommentLiteral()+FEATURE_SECTION_BEGIN;
	}
	
	private String featureSelectionEndToken() {
		return singleLineCommentLiteral()+FEATURE_SECTION_END;
	}


	@Override
	protected void invokeInternal(WorkflowContext ctx, ProgressMonitor monitor, Issues issues) {
		this.issues = issues;
		String root = StandaloneSetup.getPlatformRootPath();
		ConfigurationModelWrapper configModel = getConfigurationModel(ctx, issues);
		if ( configModel == null ) return;
		selectedFeatues = configModel.findSelectedFeatureNames();
		fullSourcePath = normalizePath( pathAppend( root, sourcePath ) );
		fullGenPath = normalizePath( pathAppend( root, genPath ) );
		File srcDir = new File(fullSourcePath);
		List<File> files = new ArrayList<File>();
		collect( srcDir, files);
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

		for (File file : files) {
			try {
				processFeatureDependencies( file );
			} catch (IOException e) {
				issues.addError("cannot process file "+file.getAbsolutePath()+". Reason: "+e.getMessage());
			} 
		}
	}

	private void processFeatureDependencies(File file) throws IOException {
		if ( needsToBeProcessed(file) ) {
			List<String> lines = loadFile(file);
			List<String> newLines = handleFeatureDependencies( lines );
			writeFile( newLines, buildTargetFileName( file.getAbsoluteFile() ));
		}
	}

	private boolean needsToBeProcessed(File file) {
		String name = file.getName();
		name = name.split("\\.")[0];
		int hashPos = name.indexOf(FEATURE_SECTION_BEGIN);
		if ( hashPos >= 0 ) { 
			String baseName = name.substring(0,hashPos);
			String featureName = name.substring(hashPos+1);
			return isFeatureSelected(featureName);
		}
		return true;
	}

	private List<String> handleFeatureDependencies(List<String> lines) {
		boolean repeat = true;
		while ( repeat ) {
			repeat = iterateFeatureDependencies(lines);
		}
		return lines;
	}
	
	private boolean iterateFeatureDependencies(List<String> lines) {
		int beginLine = findLineWithBeginToken(lines);
		if ( beginLine == NONE_FOUND ) return false;
		String beginFeatureName = extractFeatureName( lines.get(beginLine) );
		boolean isNegated = beginFeatureName.startsWith("!");
		if ( isNegated ) beginFeatureName = beginFeatureName.substring(1);

		int nextBeginLineSameFeature = findNextLineWithBeginToken(beginLine+1, lines, beginFeatureName);
		int endLine = findLineWithEndToken( lines, beginLine, beginFeatureName );
		if ( (nextBeginLineSameFeature != NONE_FOUND) && (endLine > nextBeginLineSameFeature) ) endLine = NONE_FOUND;
		if ( needsToBeRemoved( beginFeatureName, isNegated )) {
			if ( endLine == NONE_FOUND ) {
				lines.remove(beginLine);
			} else {
				for ( int i = endLine; i>beginLine; i-- ) {
					lines.remove(i);
				}
			}
		} else {
			if ( endLine == NONE_FOUND ) {
				String line = lines.get(beginLine);
				int beg = line.indexOf(featureSelectionBeginToken());
				int fn = line.indexOf(beginFeatureName, beg);
				line = line.substring(0,beg)+line.substring(fn+beginFeatureName.length()+1);
				lines.set(beginLine, line);
			} else {
				lines.remove(endLine);
				lines.remove(beginLine);
			}
		}
		return true; 
	}	
	
	private int findNextLineWithBeginToken(int beginLine, List<String> lines,
			String beginFeatureName) {
		for (int i = beginLine; i < lines.size(); i++) {
			String line = lines.get(i);
			int p = line.indexOf(featureSelectionBeginToken()); 
			if ( p >= 0 ) {
				StringTokenizer st = new StringTokenizer(line.substring(p+featureSelectionEndToken().length()), " \n\t");
				String endFeatureName = st.nextToken();
				if ( beginFeatureName.equals( endFeatureName ) ) return i;
			}
		}
		return NONE_FOUND;
	}

	private boolean needsToBeRemoved(String featureName, boolean isNegated) {
		if ( !isFeatureSelected(featureName) && !isNegated ) return true;
		if ( isFeatureSelected(featureName) && isNegated ) return true;
		return false;
	}

	private int findLineWithEndToken(List<String> lines, int beginLine, String beginFeatureName) {
		for (int i = beginLine; i < lines.size(); i++) {
			String line = lines.get(i);
			int p = line.indexOf(featureSelectionEndToken()); 
			if ( p >= 0 ) {
				StringTokenizer st = new StringTokenizer(line.substring(p+featureSelectionEndToken().length()), " \n\t");
				String endFeatureName = st.nextToken();
				if ( beginFeatureName.equals( endFeatureName ) ) return i;
			}
		}
		return NONE_FOUND;
	}

	private String extractFeatureName(String line) {
		int begin = line.indexOf(featureSelectionBeginToken());
		StringTokenizer st = new StringTokenizer(line.substring(begin+featureSelectionBeginToken().length()), " \n\t");
		return st.nextToken();
	}

	private int findLineWithBeginToken(List<String> lines) {
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			if ( line.indexOf(featureSelectionBeginToken()) >= 0 ) return i;
		}
		return NONE_FOUND;
	}

	

	private boolean isFeatureSelected(String featureName) {
		return selectedFeatues.contains(featureName);
	}

	protected void writeFile(List<String> processedLines, String targetFileName) throws IOException {
		File dir = new File( targetFileName.substring(0,targetFileName.lastIndexOf('/')) );
		dir.mkdirs();
		List<String> oldLines = null;
		File targetFile = new File(targetFileName);
		if ( targetFile.exists() ) oldLines = loadFile(targetFile);
		FileWriter w = new FileWriter(targetFileName);
		if ( oldLines != null ) {
			for (String line : oldLines) {
				w.write(line+"\n");
			}
		}
		for (String line : processedLines) {
			w.write(line+"\n");
		}
		w.close();
	}


}
