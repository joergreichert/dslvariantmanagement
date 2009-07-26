package org.eclipse.xtext.var.featureaccess.pv;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.var.featureaccess.AbstractFeatureAccessTest;
import org.eclipse.xtext.var.featureaccess.TestData;
import org.eclipse.xtext.var.featureaccess.TestHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
