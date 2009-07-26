package org.eclipse.xtext.var.featureaccess.fmp;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.var.featureaccess.AbstractFeatureAccessTest;
import org.eclipse.xtext.var.featureaccess.TestData;
import org.eclipse.xtext.var.featureaccess.TestHelper;
import org.eclipse.xtext.var.featureaccess.fmp.FMPConfigurationModelWrapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ca.uwaterloo.gp.fmp.Project;

public class FMPConfigurationModelWrapperTest extends AbstractFeatureAccessTest
		implements TestData {
	private FMPConfigurationModelWrapper wrapper;

	@Before
	public void setUp() throws Exception {
		wrapper = new FMPConfigurationModelWrapper();
	}

	@Test
	public void testFindSelectedFeatureNames() {
		EObject root = TestHelper.getFmpModelData(fmpModelURI);
		Assert.assertNotNull(root);
		Assert.assertTrue(root instanceof Project);
		Project data = (Project) root;
		wrapper.setConfigurationData(data);
		List<String> selectedFeatureNames = wrapper.findSelectedFeatureNames();
		TestHelper.checkIfFeaturesAreEqual(expectedSelectedFeatureNames,
				selectedFeatureNames);
	}
}
