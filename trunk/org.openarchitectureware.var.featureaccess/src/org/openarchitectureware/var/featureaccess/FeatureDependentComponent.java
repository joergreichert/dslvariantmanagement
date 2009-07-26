package org.openarchitectureware.var.featureaccess;


import org.openarchitectureware.workflow.WorkflowContext;
import org.openarchitectureware.workflow.issues.Issues;
import org.openarchitectureware.workflow.lib.AbstractWorkflowComponent2;

public abstract class FeatureDependentComponent extends AbstractWorkflowComponent2 {
	
	protected String configurationModelSlot;
	private String configWrapperClass;
	private String configModelUri;

	public void setConfigurationModelSlot( String slotname ) {
		configurationModelSlot = slotname;
	}
	
	public void setConfigurationModelWrapperClass( String cn ) {
		configWrapperClass = cn;
	}
	
	public void setConfigurationModelUri( String cURI ) {
		configModelUri = cURI;
	}
	
	@Override
	protected void checkConfigurationInternal(Issues issues) {
		if ( configurationModelSlot == null ) issues.addWarning("configurationModelSlot not specified");
	}
	
	protected ConfigurationModelWrapper getConfigurationModel(WorkflowContext ctx, Issues issues) {
		try {
			ConfigurationModelWrapper w;
			if(configWrapperClass != null){
				w = (ConfigurationModelWrapper) Class.forName(configWrapperClass).newInstance();
			} else {
				if(configModelUri == null) throw new Exception();
				w = FeatureAccessFactory.getConfigurationModelWrapper(configModelUri);
			}
			if ( configurationModelSlot != null ) {
				Object configurationModel = ctx.get(configurationModelSlot);
				w.setConfigurationData( configurationModel );
			}
			return w;
		} catch (Exception e) {
			issues.addError("cannot instantiate class "+configWrapperClass);
		}
		return null;
	}
	

}
