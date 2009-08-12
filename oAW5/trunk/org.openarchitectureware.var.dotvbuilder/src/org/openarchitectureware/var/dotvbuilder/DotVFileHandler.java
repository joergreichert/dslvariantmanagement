package org.openarchitectureware.var.dotvbuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.openarchitectureware.var.featureaccess.FeatureAccessFactory;
import org.openarchitectureware.var.featureaccess.FeatureModelWrapper;

public class DotVFileHandler {

	public static final String PLATFORM_RESOURCE = "platform:/resource";
	
	private DotVBuilder builder;
	private List<String> featureNames = null;
	private String featureModelUri;

	public DotVFileHandler( DotVBuilder builder ) {
		this.builder = builder;
	}
	
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
		}
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
