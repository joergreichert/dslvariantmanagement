package org.openarchitectureware.var.featureaccess;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.mwe.utils.StandaloneSetup;

public class FileCreator {

	public static String getAccessiblePath( String filenameOrUri ) {
		return "platform:/resource/"+filenameOrUri;
//		String rootPath = StandaloneSetup.getPlatformRootPath();
//		if ( rootPath != null ) {
//			String fn = appendPaths(rootPath, filenameOrUri);
//			return fn;
//		} else {
//			return "platform:/resource/"+filenameOrUri;
////			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(filenameOrUri));
////			return file.getFullPath().toString();
//		}
	}
	
	public static InputStream getFileContents( String filenameOrUri ) {
		String rootPath = StandaloneSetup.getPlatformRootPath();
		if ( rootPath != null ) {
			try {
				String fn = appendPaths(rootPath, filenameOrUri);
				FileInputStream f = new FileInputStream(fn);
				return f;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(filenameOrUri));
			try {
				return file.getContents();
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private static String appendPaths(String rootPath, String filenameOrUri) {
		return rootPath+((filenameOrUri.startsWith("/") || filenameOrUri.startsWith("\\"))?"":"/")+filenameOrUri;
	}
	
	
	
}
