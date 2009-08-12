package org.openarchitectureware.var.featureaccess;


import org.eclipse.emf.mwe.core.WorkflowContext;
import org.eclipse.emf.mwe.core.issues.Issues;
import org.eclipse.emf.mwe.core.lib.AbstractWorkflowComponent2;

public abstract class FeatureDependentComponent extends AbstractWorkflowComponent2 {
	
	protected String configurationModelSlot;
	private String configWrapperClass;

	public void setConfigurationModelSlot( String slotname ) {
		configurationModelSlot = slotname;
	}
	
	public void setConfigurationModelWrapperClass( String cn ) {
		configWrapperClass = cn;
	}
	
	@Override
	protected void checkConfigurationInternal(Issues issues) {
		if ( configurationModelSlot == null ) issues.addWarning("configurationModelSlot not specified");
	}
	
	protected ConfigurationModelWrapper getConfigurationModel(WorkflowContext ctx, Issues issues) {
		try {
			ConfigurationModelWrapper w = (ConfigurationModelWrapper) Class.forName(configWrapperClass).newInstance();
			if ( configurationModelSlot != null ) {
				Object configurationModel = ctx.get(configurationModelSlot);
				w.setConfigurationData( configurationModel );
			}
			return w;
		} catch (Exception e) {
			issues.addError("cannot instantiate class "+configWrapperClass);
			issues.addError(e.getMessage());
		}
		return null;
	}
	

}
