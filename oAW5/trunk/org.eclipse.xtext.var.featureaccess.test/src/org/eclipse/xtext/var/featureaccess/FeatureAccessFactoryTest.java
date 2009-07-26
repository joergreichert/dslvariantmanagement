package org.eclipse.xtext.var.featureaccess;

import java.io.IOException;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.var.featureaccess.fmp.FMPConfigurationModelWrapper;
import org.eclipse.xtext.var.featureaccess.fmp.FMPFeatureModelWrapper;
import org.eclipse.xtext.var.featureaccess.pv.PVConfigurationModelWrapper;
import org.eclipse.xtext.var.featureaccess.pv.PVFeatureModelWrapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
		Assert.assertTrue(wrapper instanceof FMPFeatureModelWrapper);
	}

	@Test
	public void testGetConfigurationModelWrapperWhenFmp() {
		resourceSet = TestHelper.loadFmpPackages();
		ConfigurationModelWrapper wrapper = FeatureAccessFactory.getConfigurationModelWrapper(fmpModelURI);
		Assert.assertTrue(wrapper instanceof FMPConfigurationModelWrapper);
	}
}
