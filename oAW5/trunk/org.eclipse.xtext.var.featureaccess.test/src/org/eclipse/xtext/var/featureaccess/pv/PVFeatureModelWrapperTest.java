package org.eclipse.xtext.var.featureaccess.pv;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.var.featureaccess.AbstractFeatureAccessTest;
import org.eclipse.xtext.var.featureaccess.TestData;
import org.eclipse.xtext.var.featureaccess.TestHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PVFeatureModelWrapperTest extends AbstractFeatureAccessTest
		implements TestData {

	@Before
	public void setUp() throws Exception {
		resourceSet = TestHelper.loadPureVariantsPackages();
	}

	@Test
	public void testLoadFeatureData() {
		List<String> featureNames = new ArrayList<String>();
		resourceSet = TestHelper.loadPureVariantsPackages();
		// ResourceFactoryRegistryImpl.INSTANCE.getExtensionToFactoryMap().put(
		// "xmi", new XMIResourceFactoryImpl());
		// EPackageRegistryImpl.INSTANCE.put(PvmodelPackage.eNS_URI,
		// PvmodelPackage.eINSTANCE);
		URI xfmURI = URI.createFileURI(pvFeatureModelURI);
		Assert.assertNotNull(xfmURI);
		
		// code copied from original class
		Resource r = resourceSet.getResource(xfmURI, true);
		EObject root = r.getContents().get(0);
		for (Iterator<EObject> iterator = root.eAllContents(); iterator
				.hasNext();) {
			EObject o = iterator.next();
			if (o.eClass().getName().equals("Element")) {
				String name = (String) o.eGet(o.eClass().getEStructuralFeature(
						"name"));
				String type = (String) o.eGet(o.eClass().getEStructuralFeature(
						"type"));
				if (type.equals("ps:feature"))
					featureNames.add(name);
			}
		}
		// wrapper.loadFeatureData(pvFeatureModelURI);
		
		TestHelper.checkIfFeaturesAreEqual(expectedFeatureNames, featureNames);
	}
}
