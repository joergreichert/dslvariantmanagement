package org.openarchitectureware.var.dotvbuilder;

import java.util.Map;


import org.eclipse.core.internal.resources.Workspace;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.QualifiedName;
import org.openarchitectureware.var.dotvbuilder.Activator;
import org.openarchitectureware.var.dotvbuilder.properties.PropertyConstants;

public class DotVBuilder extends IncrementalProjectBuilder {

	private static final String PLATFORM_RESOURCE = "platform:/resource";

	private DotVFileHandler handler = new DotVFileHandler(this);
	
	class DotVResoucreDeltaVisitor implements IResourceDeltaVisitor {
		public boolean visit(IResourceDelta delta) throws CoreException {
			IResource resource = delta.getResource();
			switch (delta.getKind()) {
			case IResourceDelta.ADDED:
				handler.checkFile(resource);
				break;
			case IResourceDelta.REMOVED:
				break;
			case IResourceDelta.CHANGED:
				handler.checkFile(resource);
				break;
			}
			return true;
		}
	}

	class DotVResourceVisitor implements IResourceVisitor {
		public boolean visit(IResource resource) {
			handler.checkFile(resource);
			return true;
		}
	}

	public static final String BUILDER_ID = "org.openarchitectureware.var.dotvbuilder.dotVBuilder";

	private static final String MARKER_TYPE = "org.openarchitectureware.var.dotvbuilder.xmlProblem";

	/**
	 * @param file
	 * @param message
	 * @param lineNumber
	 * @param severity -
	 *            {@link IMarker#SEVERITY_ERROR} or
	 *            {@link IMarker#SEVERITY_INFO} or
	 *            {@link IMarker#SEVERITY_WARNING}
	 */
	void addMarker(IFile file, String message, int lineNumber,
			int severity) {
		try {
			IMarker marker = file.createMarker(MARKER_TYPE);
			marker.setAttribute(IMarker.MESSAGE, message);
			marker.setAttribute(IMarker.SEVERITY, severity);
			if (lineNumber == -1) {
				lineNumber = 1;
			}
			marker.setAttribute(IMarker.LINE_NUMBER, lineNumber);
		} catch (CoreException e) {
		}
	}

	@SuppressWarnings("unchecked")
	protected IProject[] build(int kind, Map args, IProgressMonitor monitor)
			throws CoreException {
		if (kind == FULL_BUILD) {
			fullBuild(monitor);
		} else {
			IResourceDelta delta = getDelta(getProject());
			if (delta == null) {
				fullBuild(monitor);
			} else {
				incrementalBuild(delta, monitor);
			}
		}
		return null;
	}

	public static String getValue(IFile f, String key) {
		return getValue(f.getProject(), key);
	}
		
		
	public static String getValue( IProject project, String key ) {
		try {
			boolean useProjectSettings = Boolean.parseBoolean(getProperty(project,
					PropertyConstants.ENABLE_PROJECT_SETTINGS_PROP));
			if (useProjectSettings) {
				return getProperty(project, key);
			}
		} catch (CoreException e) {
		}
		return Activator.getDefault().getPreferenceStore().getString(key);
	}

	public static String getProperty(IProject p, String key) throws CoreException {
		return p.getPersistentProperty(new QualifiedName("", key));
	}

	void deleteMarkers(IFile file) {
		try {
			file.deleteMarkers(MARKER_TYPE, false, IResource.DEPTH_ZERO);
		} catch (CoreException ce) {
		}
	}

	protected void fullBuild(final IProgressMonitor monitor)
			throws CoreException {
		try {
			String featureDataUri = getValue(getProject(), PreferenceConstants.FEATURE_MODEL_URI);
			if ( featureDataUri.toLowerCase().startsWith(PLATFORM_RESOURCE) ) {
				featureDataUri = featureDataUri.substring(PLATFORM_RESOURCE.length());
			}
			IFile featureDataFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(featureDataUri));
			handler.refreshFeatureModel(getProject());
			getProject().accept(new DotVResourceVisitor());
		} catch (CoreException e) {
		}
	}

	protected void incrementalBuild(IResourceDelta delta,
			IProgressMonitor monitor) throws CoreException {
		delta.accept(new DotVResoucreDeltaVisitor());
	}
}
