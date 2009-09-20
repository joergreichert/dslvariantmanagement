package org.openarchitectureware.var.featureaccess;

import org.eclipse.emf.mwe.core.WorkflowContext;
import org.eclipse.emf.mwe.core.WorkflowContextDefaultImpl;
import org.eclipse.emf.mwe.core.config.FeatureComponent;
import org.eclipse.emf.mwe.core.issues.Issues;
import org.eclipse.emf.mwe.core.issues.IssuesImpl;
import org.eclipse.emf.mwe.core.monitor.NullProgressMonitor;
import org.junit.Assert;
import org.junit.Test;

public class FeatureAccessFromWorkflowTest implements TestData {

	@Test
	public void testFeatureAccessFromWorkflowForPV() {
		executeFeatureAccessFromWorkflowTest(TestData.pvConfigurationModelURI);
	}

	@Test
	public void testFeatureAccessFromWorkflowForFMP() {
		executeFeatureAccessFromWorkflowTest(TestData.fmpModelURI);
	}

	@Test
	public void testFeatureAccessFromWorkflowForText() {
		executeFeatureAccessFromWorkflowTest(TestData.textFeatureModelURI);
	}

	public void executeFeatureAccessFromWorkflowTest(String file) {
		Issues issues = new IssuesImpl();
		ConfigurationModelReader reader = new ConfigurationModelReader();
		reader.setConfigurationModelFile(file);
		WorkflowContext ctx = new WorkflowContextDefaultImpl();
		reader.invoke(ctx, new NullProgressMonitor(), issues);
		FeatureComponent featureComponent = new FeatureComponent();
		featureComponent.setIsSelected("Security");
		boolean succesful = featureComponent.evaluate();
		Assert.assertTrue("feature shoudld be selected in " + file, succesful);
	}
}
