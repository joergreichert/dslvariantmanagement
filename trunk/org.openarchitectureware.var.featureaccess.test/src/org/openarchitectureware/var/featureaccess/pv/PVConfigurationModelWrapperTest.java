package org.openarchitectureware.var.featureaccess.pv;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openarchitectureware.var.featureaccess.AbstractFeatureAccessTest;
import org.openarchitectureware.var.featureaccess.TestData;
import org.openarchitectureware.var.featureaccess.TestHelper;

import com.ps.consul.eclipse.ecore.pvmeta.Model;

public class PVConfigurationModelWrapperTest extends AbstractFeatureAccessTest
		implements TestData {
	private PVConfigurationModelWrapper wrapper;

	@Before
	public void setUp() throws Exception {
		wrapper = new PVConfigurationModelWrapper();
	}

	@Test
	public void testFindSelectedFeatureNames() {
		EObject root = TestHelper.getPureVariantsData(pvConfigurationModelURI);
		Assert.assertNotNull(root);
		Assert.assertTrue(root instanceof Model);
		Model data = (Model) root;
		wrapper.setConfigurationData(data);
		List<String> selectedFeatureNames = wrapper.findSelectedFeatureNames();
		TestHelper.checkIfFeaturesAreEqual(expectedSelectedFeatureNames,
				selectedFeatureNames);
	}
}
