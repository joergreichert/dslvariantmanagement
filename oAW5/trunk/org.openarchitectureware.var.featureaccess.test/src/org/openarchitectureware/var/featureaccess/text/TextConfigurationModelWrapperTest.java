package org.openarchitectureware.var.featureaccess.text;

import java.util.List;

import org.eclipse.emf.mwe.core.config.AttributeNotFound;
import org.eclipse.emf.mwe.core.config.FeatureNotFound;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openarchitectureware.var.featureaccess.AbstractFeatureAccessTest;
import org.openarchitectureware.var.featureaccess.TestData;
import org.openarchitectureware.var.featureaccess.TestHelper;

public class TextConfigurationModelWrapperTest extends AbstractFeatureAccessTest
		implements TestData {
	private TextConfigurationModelWrapper wrapper;

	@Before
	public void setUp() throws Exception {
		wrapper = new TextConfigurationModelWrapper();
		List<String> fileEntries = TestHelper.getTextModelData(textFeatureModelURI);
		wrapper.setConfigurationData(fileEntries);
	}

	@Test
	public void testFindSelectedFeatureNames() {
		List<String> selectedFeatureNames = wrapper.findSelectedFeatureNames();
		TestHelper.checkIfFeaturesAreEqual(expectedSelectedFeatureNames,
				selectedFeatureNames);
	}
	
	@Test
	public void testFeatureExists() {
		Assert.assertTrue(wrapper.featureExists("Security"));
	}
	
	@Test
	public void testFeatureSelected() throws FeatureNotFound {
		Assert.assertTrue(wrapper.isFeatureSelected("Security"));
	}
	
	@Test
	public void testGetFeature() throws FeatureNotFound {
		Object securityFeature = wrapper.getFeature("Security");
		Assert.assertNotNull(securityFeature);
		Assert.assertTrue(securityFeature instanceof String);
		Assert.assertEquals("Security", (String) securityFeature);
	}
	
	@Test (expected=UnsupportedOperationException.class)
	public void testGetAttributeValue() throws FeatureNotFound, AttributeNotFound {
		wrapper.getAttributeValue("Security", "name");
	}
}
