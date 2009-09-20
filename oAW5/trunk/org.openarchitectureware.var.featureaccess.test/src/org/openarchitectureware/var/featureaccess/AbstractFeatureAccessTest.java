package org.openarchitectureware.var.featureaccess;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.junit.After;

public abstract class AbstractFeatureAccessTest {
	protected ResourceSet resourceSet = null;

	@After
	public void tearDown() {
		if (resourceSet != null) {
			TestHelper.unloadModels(resourceSet);
		}
	}
}
