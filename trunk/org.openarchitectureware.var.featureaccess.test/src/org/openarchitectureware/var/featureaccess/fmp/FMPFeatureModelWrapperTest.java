package org.openarchitectureware.var.featureaccess.fmp;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openarchitectureware.var.featureaccess.AbstractFeatureAccessTest;
import org.openarchitectureware.var.featureaccess.TestData;
import org.openarchitectureware.var.featureaccess.TestHelper;

import ca.uwaterloo.gp.fmp.Feature;
import ca.uwaterloo.gp.fmp.Project;

public class FMPFeatureModelWrapperTest extends AbstractFeatureAccessTest
		implements TestData {

	@Before
	public void setUp() throws Exception {
		resourceSet = TestHelper.loadFmpPackages();
	}

	@Test
	public void testLoadFeatureData() {
		EObject root = TestHelper.getFmpModelData(fmpModelURI);
		Assert.assertNotNull(root);
		Assert.assertTrue(root instanceof Project);
		Project data = (Project) root;
		Feature modelRoot = data.getModel();
		List<String> featureNames = FMPModelIterator.getFeatureNames(modelRoot);
		TestHelper.checkIfFeaturesAreEqual(expectedFeatureNames, featureNames);
	}
}
