package org.openarchitectureware.var.featureaccess.fmp;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import ca.uwaterloo.gp.fmp.FmpPackage;

public class FMPHelper {
	
	static ResourceSet loadFmpPackages() {
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
				.put("fmp", new XMIResourceFactoryImpl());
		resourceSet.getPackageRegistry().put(FmpPackage.eNS_URI,
				FmpPackage.eINSTANCE);
		return resourceSet;
	}
}
