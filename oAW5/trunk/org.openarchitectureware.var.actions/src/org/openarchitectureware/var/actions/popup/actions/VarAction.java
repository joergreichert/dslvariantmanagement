package org.openarchitectureware.var.actions.popup.actions;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPart;
import org.openarchitectureware.var.actions.Activator;
import org.openarchitectureware.var.actions.PreferenceConstants;
import org.openarchitectureware.var.actions.properties.PropertyConstants;


public class VarAction implements IObjectActionDelegate {
	
	public class VarActionOperation extends AbstractOperation {
		Shell shell;
		
		public VarActionOperation(Shell shell) {
			super("VarTooling Operation");
			this.shell = shell;
		}

		@Override
		public IStatus execute(IProgressMonitor monitor, IAdaptable info)
				throws ExecutionException {
			ISelection sel= part.getSite().getSelectionProvider().getSelection();
			
			if (sel instanceof ITreeSelection){
				ICompilationUnit compUnit = (ICompilationUnit) ((ITreeSelection) sel).getFirstElement();
				if (compUnit.getResource() instanceof IFile){
					IFile file = (IFile) compUnit.getResource(); 

					//move the file to the var-folder
					makeVFile(file, compUnit);
				} 
				else {
					MessageDialog.openInformation(
							shell,
							"PackageExplorerSelectionAction Plug-in",
							"comp unit cannot be mapped: " + sel.toString() );
					
				}
			} else{
				MessageDialog.openInformation(
				shell,
				"PackageExplorerSelectionAction Plug-in",
				"cannot map selected element: " + sel.toString() );
			}
			return null;
		}

		@Override
		public IStatus redo(IProgressMonitor monitor, IAdaptable info)
				throws ExecutionException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IStatus undo(IProgressMonitor monitor, IAdaptable info)
				throws ExecutionException {
			ISelection sel= part.getSite().getSelectionProvider().getSelection();
			
			if (sel instanceof ITreeSelection){
				ICompilationUnit compUnit = (ICompilationUnit) ((ITreeSelection) sel).getFirstElement();
				if (compUnit.getResource() instanceof IFile){
					IFile file = (IFile) compUnit.getResource(); 

					//move the file to the src-code folder
					makeSrcCode(file, compUnit);
				} 
				else {
					MessageDialog.openInformation(
							shell,
							"PackageExplorerSelectionAction Plug-in",
							"comp unit cannot be mapped: " + sel.toString() );
					
				}
			} else{
				MessageDialog.openInformation(
				shell,
				"PackageExplorerSelectionAction Plug-in",
				"cannot map selected element: " + sel.toString() );
			}
			return null;
		}

	}
	
	public static class LastFileInfo{
		public static String folderPath; 
		public static String srcCodePath;
		public static String fileName;		
		
		public static String getCompleteFilePath(){
			return srcCodePath + folderPath + fileName;
		}		
	}

	private Shell shell;
	private IWorkbenchPart part;
	private IWorkbench workbench;
	
	/**
	 * Constructor for Action1.
	 */
	public VarAction() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
		part = targetPart;
		workbench = part.getSite().getWorkbenchWindow().getWorkbench();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {		
		IUndoableOperation operation = new VarActionOperation(shell); 
		
		IWorkbench workbench = part.getSite().getWorkbenchWindow().getWorkbench();
		IOperationHistory operationHistory = workbench.getOperationSupport().getOperationHistory();

		IUndoContext undoContext = workbench.getOperationSupport().getUndoContext();
		operation.addContext(undoContext);
		try {
			operationHistory.execute(operation, null, null);
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void makeVFile(IFile file, ICompilationUnit compUnit) {
		String pkgName =  compUnit.getParent().getElementName();
		IFolder varFolder = file.getProject().getFolder( getValue(file.getProject(), PreferenceConstants.VARIANTS_SRC_PATH) );
		IPackageFragmentRoot fragRoot = compUnit.getJavaProject().getPackageFragmentRoot( varFolder );
		try {
			compUnit.getJavaProject().getResolvedClasspath(true);
		} catch (JavaModelException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		try {
			fragRoot.open(null);
			fragRoot.createPackageFragment(pkgName, false, null);
		} catch (JavaModelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		IPath pkgPath = varFolder.getFullPath().append( pkgName.replaceAll("\\.", "/") + "/" );

		
		IPath targetPath = pkgPath.append( file.getName() + ".v" );
		
		try {
			file.move(targetPath, false, null);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	
	private void makeSrcCode(IFile file, ICompilationUnit compUnit) {
		
		String pkgName =  compUnit.getParent().getElementName();
		IFolder srcFolder = file.getProject().getFolder( LastFileInfo.srcCodePath );
		
	//	if(file.getProject().getFo pkgName)
		//create package, if it doesn't exist
		IPackageFragmentRoot fragRoot = compUnit.getJavaProject().getPackageFragmentRoot( srcFolder );		
		try {
			fragRoot.open(null);
			fragRoot.createPackageFragment(pkgName, false, null);
		} catch (JavaModelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		IPath pkgPath = srcFolder.getFullPath().append( pkgName.replaceAll("\\.", "/") + "/" );		

		
		IPath targetPath = pkgPath.append( file.getName() + ".v" );
		
		try {
			file.copy(targetPath, false, null);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
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

}
