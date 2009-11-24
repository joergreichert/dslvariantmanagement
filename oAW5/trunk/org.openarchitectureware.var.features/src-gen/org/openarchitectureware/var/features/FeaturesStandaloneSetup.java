package org.openarchitectureware.var.features;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.xtext.ISetup;
import org.eclipse.emf.ecore.resource.Resource;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Generated from StandaloneSetup.xpt!
 */
public class FeaturesStandaloneSetup implements ISetup {

	public static void doSetup() {
		new FeaturesStandaloneSetup().createInjectorAndDoEMFRegistration();
	}

	public Injector createInjectorAndDoEMFRegistration() {
		org.eclipse.xtext.common.TerminalsStandaloneSetup.doSetup();

		Injector injector = createInjector();
		register(injector);
		return injector;
	}
	
	public Injector createInjector() {
		return Guice.createInjector(new org.openarchitectureware.var.features.FeaturesRuntimeModule());
	}
	
	public void register(Injector injector) {
	if (!EPackage.Registry.INSTANCE.containsKey("http://www.openarchitectureware.org/var/features/Features")) {
		EPackage.Registry.INSTANCE.put("http://www.openarchitectureware.org/var/features/Features", org.openarchitectureware.var.features.features.FeaturesPackage.eINSTANCE);
	}

	}
}
