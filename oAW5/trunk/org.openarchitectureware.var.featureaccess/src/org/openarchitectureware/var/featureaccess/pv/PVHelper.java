package org.openarchitectureware.var.featureaccess.pv;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import com.ps.consul.eclipse.ecore.pvmeta.PvmetaPackage;
import com.ps.consul.eclipse.ecore.pvmodel.PvmodelPackage;

public class PVHelper {
	
	static ResourceSet loadPureVariantsPackages() {
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
				.put("xmi", new XMIResourceFactoryImpl());
		resourceSet.getPackageRegistry().put(PvmetaPackage.eNS_URI,
				PvmetaPackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(PvmodelPackage.eNS_URI,
				PvmodelPackage.eINSTANCE);
		return resourceSet;
	}
}
