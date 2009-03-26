package org.openarchitectureware.var.pv.relbuilderdotv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.openarchitectureware.var.dotvbuilder.DotVBuilder;
import org.openarchitectureware.var.dotvbuilder.PreferenceConstants;

import com.ps.consul.eclipse.relationsbuilder.IIndexer;
import com.ps.consul.eclipse.relationsbuilder.ITargetCache;

public class DotVFileIndexer implements IIndexer {
 
	public void scan(IFile file, ITargetCache cache) throws CoreException {
		InputStream i = null;
		try {
			i = file.getContents();
			BufferedReader br = new BufferedReader(new InputStreamReader(i));
			int lineCount = 0;
			while (br.ready()) {
				lineCount++;
				String l = br.readLine();
				List<String> features = parseLine(file, l );
				for (String feature : features) {
					cache.add( feature, file, lineCount);
				}
			}
			br.close();
			String fileNameFeature = getCompleteFileDependency(file);
			if (fileNameFeature != null) {
				cache.add( fileNameFeature, file, 1);
			}
		} catch ( IOException ex ) {
			Status s = new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, ex.getMessage(), ex);
			CoreException coreException = new CoreException(s);
			throw coreException;
		} finally {
			try {
				i.close();
			} catch (IOException ignore) {
			}
		}
	}


	
	public static List<String> parseLine( IFile file, String line ) {
		List<String> features = new ArrayList<String>();
		List<String> l = null;
		l = findInLineBySymbol( file, line, DotVBuilder.getValue(file, PreferenceConstants.FEATURE_BEGIN) );
		if ( l != null ) features.addAll(l);
		l = findInLineBySymbol( file, line, DotVBuilder.getValue(file, PreferenceConstants.JOINPOINT_SECTION_BEGIN) );
		if ( l != null ) features.addAll(l);
		l = findInLineBySymbol( file, line, DotVBuilder.getValue(file, PreferenceConstants.ADVICE_SECTION_BEGIN) );
		if ( l != null ) features.addAll(split(l));
		return features;
	}

	private static List<String> split(List<String> l) {
		List<String> result = new ArrayList<String>();
		for (String s : l) {
			String[] pair = s.split(":");
			if (pair.length == 2 ) { 
				if ( pair[0].equals("before") || 
				     pair[0].equals("after") ||
					 pair[0].equals("instead") ) result.add(pair[1]);
			}
		}
		return result;
	}

	public static List<String> findInLineBySymbol( IFile file, String line, String symbol ) {
		int fp = line.indexOf(symbol);
		if ( fp >= 0 ) {
			List<String> features = new ArrayList<String>();
			StringTokenizer st = new StringTokenizer(line, " \n\t");
			while (st.hasMoreTokens() ) {
				String token = st.nextToken();
				if ( token.trim().equals(symbol) ) {
					if ( st.hasMoreTokens() ) features.add( st.nextToken().trim() );
				}
			}
			return features;
		} else return null;
	}

	public static String getCompleteFileDependency(IFile file) {
		String fn = file.getName();
		int hashPos = fn.indexOf('#'); 
		if ( hashPos >= 0 ) {
			return fn.substring(hashPos+1, fn.indexOf('.', hashPos) );
		}
		return null;
	}
	
	
	
	
}
