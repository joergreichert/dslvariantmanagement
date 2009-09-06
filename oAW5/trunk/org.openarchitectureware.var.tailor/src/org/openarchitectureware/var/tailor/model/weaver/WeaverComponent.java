package org.openarchitectureware.var.tailor.model.weaver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtend.util.stdlib.DynamicEcoreHelper;
import org.eclipse.emf.mwe.core.WorkflowContext;
import org.eclipse.emf.mwe.core.issues.Issues;
import org.eclipse.emf.mwe.core.lib.AbstractWorkflowComponent2;
import org.eclipse.emf.mwe.core.monitor.ProgressMonitor;


public class WeaverComponent extends AbstractWorkflowComponent2 {
	
	private String architectureModelSlot;
	private String aspectModelUri;
	private DynamicEcoreHelper h;

	public void setArchitectureModelSlot( String slotname ) {
		architectureModelSlot = slotname;
	}
	
	public void setAspectModelUri( String modelUri ) {
		aspectModelUri = modelUri;
	}
	
	@Override
	protected void checkConfigurationInternal(Issues issues) {
		if ( architectureModelSlot == null ) issues.addError("architectureModelSlot not specified");
	}
	
	@Override
	public String getLogMessage() {
		return "weaving aspects inside "+architectureModelSlot;
	}
	
	@Override
	protected void invokeInternal(WorkflowContext ctx, ProgressMonitor monitor, Issues issues) {
		EObject architectureModel = (EObject)ctx.get(architectureModelSlot);
		if ( architectureModel == null )  {
			issues.addError("no contents found in slot "+architectureModelSlot);
			return;
		}		
		Weaver w = new Weaver();
		w.weave(architectureModel, aspectModelUri);
	}


}
