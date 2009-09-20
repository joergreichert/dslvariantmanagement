package org.openarchitectureware.var.featureaccess.text;

import java.util.List;

import org.junit.Test;
import org.openarchitectureware.var.featureaccess.AbstractFeatureAccessTest;
import org.openarchitectureware.var.featureaccess.TestData;
import org.openarchitectureware.var.featureaccess.TestHelper;

public class TextFeatureModelWrapperTest extends AbstractFeatureAccessTest
		implements TestData {

	@Test
	public void testLoadFeatureData() {
		TextFeatureModelWrapper wrapper = new TextFeatureModelWrapper();
		wrapper.loadFeatureData(textFeatureModelURI);
		List<String> featureNames = wrapper.findAllFeatureNames();
		TestHelper.checkIfFeaturesAreEqual(expectedFeatureNames, featureNames);
	}
}
