package org.openarchitectureware.var.featureaccess;

import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.junit.Assert;

import ca.uwaterloo.gp.fmp.FmpPackage;

import com.ps.consul.eclipse.ecore.pvmeta.PvmetaPackage;
import com.ps.consul.eclipse.ecore.pvmodel.PvmodelPackage;

public class TestHelper {

	public static ResourceSet loadPureVariantsPackages() {
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
				.put("xmi", new XMIResourceFactoryImpl());
		resourceSet.getPackageRegistry().put(PvmetaPackage.eNS_URI,
				PvmetaPackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(PvmodelPackage.eNS_URI,
				PvmodelPackage.eINSTANCE);
		return resourceSet;
	}

	public static EObject getPureVariantsData(String pvModelURI) {
		ResourceSet resourceSet = TestHelper.loadPureVariantsPackages();
		return getModelRoot(resourceSet, pvModelURI);
	}

	public static ResourceSet loadFmpPackages() {
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
				.put("fmp", new XMIResourceFactoryImpl());
		resourceSet.getPackageRegistry().put(FmpPackage.eNS_URI,
				FmpPackage.eINSTANCE);
		return resourceSet;
	}

	public static EObject getFmpModelData(String fmpModelURI) {
		ResourceSet resourceSet = TestHelper.loadFmpPackages();
		return getModelRoot(resourceSet, fmpModelURI);
	}

	private static EObject getModelRoot(ResourceSet resourceSet, String modelURI) {
		URI xmiURI = URI.createFileURI(modelURI);
		Assert.assertNotNull(xmiURI);
		Resource xmiResource = resourceSet.getResource(xmiURI, true);
		Assert.assertNotNull(xmiResource);
		Assert.assertNotNull(xmiResource.getContents());
		Assert.assertTrue(xmiResource.getContents().size() > 0);
		return xmiResource.getContents().get(0);
	}

	static void unloadModels(ResourceSet resourceSet) {
		resourceSet.getPackageRegistry().clear();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
				.clear();
	}

	public static void checkIfFeaturesAreEqual(String[] expectedFeatureNames,
			List<String> actualFeatureNames) {
		for (String expectedFeature : expectedFeatureNames) {
			Assert.assertTrue("expected feature " + expectedFeature
					+ " not contained", actualFeatureNames
					.contains(expectedFeature));
		}
		Assert.assertEquals(expectedFeatureNames.length, actualFeatureNames
				.size());
	}

	public static List<String> getTextModelData(String textfeaturemodeluri) {
		String[] expectedFeatureNames = new String[] {
				"+CollaborationSystemFeatures", "+AccessManagement",
				"+Security", "+Authentication", "+Encryption",
				"+AsynchronousConferencing", "+SynchronousConferencing",
				"+DocumentRepository", "+Forum", "-MicroBlogging", "+Text",
				"+Audio", "+Video", "+Data", "-TelephoneIntegration",
				"+VoiceOverIP", "-VNC", "+X11" };
		return Arrays.asList(expectedFeatureNames);
	}
}
