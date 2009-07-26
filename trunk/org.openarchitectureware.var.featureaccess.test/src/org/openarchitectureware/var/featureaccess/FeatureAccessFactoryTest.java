package org.openarchitectureware.var.featureaccess;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.eclipse.emf.ecore.EObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openarchitectureware.var.featureaccess.pv.PVConfigurationModelWrapper;
import org.openarchitectureware.var.featureaccess.pv.PVFeatureModelWrapper;

import ca.uwaterloo.gp.fmp.Project;

import com.ps.consul.eclipse.ecore.pvmeta.Model;
import com.ps.consul.eclipse.ecore.pvmodel.ConsulModel;

public class FeatureAccessFactoryTest extends AbstractFeatureAccessTest
		implements TestData {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testPureVariantsFeatureModel() {
		EObject root = TestHelper.getPureVariantsData(pvFeatureModelURI);
		Assert.assertNotNull(root);
		Assert.assertTrue(root instanceof ConsulModel);
	}

	public void testPureVariantsConfigurationModel() {
		EObject root = TestHelper.getPureVariantsData(pvConfigurationModelURI);
		Assert.assertNotNull(root);
		Assert.assertTrue(root instanceof Model);
	}

	@Test
	public void testFmpModel() throws IOException {
		EObject root = TestHelper.getFmpModelData(fmpModelURI);
		Assert.assertNotNull(root);
		Assert.assertTrue(root instanceof Project);
	}

	@Test
	public void testGetFeatureModelWrapperWhenPureVariants() {
		resourceSet = TestHelper.loadPureVariantsPackages();
		FeatureModelWrapper wrapper = FeatureAccessFactory
				.getFeatureModelWrapper(pvFeatureModelURI);
		System.out.println(wrapper);
		Assert.assertTrue(wrapper instanceof PVFeatureModelWrapper);
	}

	@Test
	public void testGetConfigurationModelWrapperWhenPureVariants() {
		resourceSet = TestHelper.loadPureVariantsPackages();
		ConfigurationModelWrapper wrapper = FeatureAccessFactory
				.getConfigurationModelWrapper(pvConfigurationModelURI);
		Assert.assertTrue(wrapper instanceof PVConfigurationModelWrapper);
	}

	@Test
	public void testGetFeatureModelWrapperWhenFmp() {
		resourceSet = TestHelper.loadFmpPackages();
		FeatureModelWrapper wrapper = FeatureAccessFactory
				.getFeatureModelWrapper(fmpModelURI);
		fail("Not yet implemented");
	}

	@Test
	public void testGetConfigurationModelWrapperWhenFmp() {
		resourceSet = TestHelper.loadFmpPackages();
		FeatureAccessFactory.getConfigurationModelWrapper(fmpModelURI);
		fail("Not yet implemented");
	}

}
