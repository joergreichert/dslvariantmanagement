package org.eclipse.xtext.var.featureaccess.dummy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


import org.eclipse.emf.mwe.utils.StandaloneSetup;
import org.eclipse.xtext.var.featureaccess.FileCreator;
import org.eclipse.emf.mwe.core.WorkflowContext;
import org.eclipse.emf.mwe.core.issues.Issues;
import org.eclipse.emf.mwe.core.lib.AbstractWorkflowComponent2;
import org.eclipse.emf.mwe.core.monitor.ProgressMonitor;

public class DummyConfigurationModelLoader extends AbstractWorkflowComponent2 {

	private String configDataUri;
	private String outputslot;

	public void setUri( String uri ) {
		this.configDataUri = uri;
	}
	
	public void setOutputSlot( String slot ) {
		this.outputslot = slot;
	}
	
	@Override
	protected void checkConfigurationInternal(Issues issues) {
		if ( configDataUri == null ) {
			issues.addError("uri not set");
		}
		if ( outputslot == null ) {
			issues.addError("outputSlot not set");
		}
	}

	@Override
	protected void invokeInternal(WorkflowContext ctx, ProgressMonitor monitor,
			Issues issues) {
		try {
			List<String> data = new ArrayList<String>();
			BufferedReader br = new BufferedReader( new InputStreamReader(FileCreator.getFileContents(configDataUri)) );
			while ( br.ready() ) {
				data.add( br.readLine() );
			}
			br.close();
			ctx.set(outputslot, data);
		} catch ( Exception ex ) {
			issues.addError("error loading config data: "+ex.getMessage());
		}
	}

}
