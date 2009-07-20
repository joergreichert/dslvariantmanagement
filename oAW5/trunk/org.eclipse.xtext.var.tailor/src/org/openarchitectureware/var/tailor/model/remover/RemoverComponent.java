package org.openarchitectureware.var.tailor.model.remover;


import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.var.featureaccess.ConfigurationModelWrapper;
import org.eclipse.xtext.var.featureaccess.FeatureDependentComponent;
import org.eclipse.xtext.var.featureaccess.FeatureModelWrapper;
import org.eclipse.xtext.var.featureaccess.ext.FeatureSupport;
import org.eclipse.xtend.util.stdlib.DynamicEcoreHelper;
import org.eclipse.emf.mwe.core.WorkflowContext;
import org.eclipse.emf.mwe.core.WorkflowInterruptedException;
import org.eclipse.emf.mwe.core.issues.Issues;
import org.eclipse.emf.mwe.core.monitor.ProgressMonitor;

public class RemoverComponent extends FeatureDependentComponent {
	
	private String architectureModelSlot;
	

	public void setArchitectureModelSlot( String slotname ) {
		architectureModelSlot = slotname;
	}
	
	@Override
	protected void checkConfigurationInternal(Issues issues) {
		super.checkConfigurationInternal(issues);
		if ( architectureModelSlot == null ) issues.addError("architectureModelSlot not specified");
	}
	
	@Override
	public String getLogMessage() {
		return "removing variable elements from "+architectureModelSlot+" using feature model "+configurationModelSlot;
	}
	
//	@Override
//	protected void invokeInternal(org.eclipse.emf.mwe.core.WorkflowContext ctx,
//			org.eclipse.emf.mwe.core.monitor.ProgressMonitor monitor,
//			org.eclipse.emf.mwe.core.issues.Issues issues) {
//		// TODO Auto-generated method stub
//		super.invokeInternal(ctx, monitor, issues);
//	}
	@Override
	protected void invokeInternal(WorkflowContext ctx, ProgressMonitor monitor, Issues issues) {
		EObject architectureModel = (EObject)ctx.get(architectureModelSlot);
		if ( architectureModel == null )  {
			issues.addError("no contents found in slot "+architectureModelSlot);
			return;
		}
		
		ConfigurationModelWrapper configurationWrapper = getConfigurationModel(ctx, issues);
		if ( configurationWrapper == null ) throw new WorkflowInterruptedException("no configurationWrapper defined!");
		FeatureSupport.setConfigurationModelWrapper(configurationWrapper);

		Remover r = new Remover();
		r.remove( architectureModel, configurationWrapper );
	}
	

}
