/**************************************************************************************
 * Copyright (c) 2005, 2006 Generative Software Development Lab, University of Waterloo
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   1. Generative Software Development Lab, University of Waterloo,
 *      http://gp.uwaterloo.ca  - initial API and implementation
 **************************************************************************************/
package ca.uwaterloo.gp.fmp.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;

import ca.uwaterloo.gp.fmp.provider.FmpItemProviderAdapterFactory;

/**
 * @author Michal Antkiewicz <mantkiew@swen.uwaterloo.ca>
 */
public class FmpExternalLoader {
	AdapterFactory adapterFactory;
	EditingDomain editingDomain;
	
	public AdapterFactory getAdapterFactory() {
		return adapterFactory;
	}

	public EditingDomain getEditingDomain() {
		return editingDomain;
	}
	
	public FmpExternalLoader() {
		// Create an adapter factory that yields item providers.
		//
		List factories = new ArrayList();
		factories.add(new ResourceItemProviderAdapterFactory());
		factories.add(new FmpItemProviderAdapterFactory());
		factories.add(new ReflectiveItemProviderAdapterFactory());
		
		// Create the command stack that will notify this editor as commands are executed.
		//
		BasicCommandStack commandStack = new BasicCommandStack();
		
		// Create the editing domain with a special command stack.
		//
		editingDomain = new AdapterFactoryEditingDomain(adapterFactory, commandStack, new HashMap());
	}
	/**
	 * Load a resource using editing domain.
	 * @param file
	 */
	public void load(IFile file) {
		load(file.getFullPath().toString());
	}
	/**
	 * Load a resource using editing domain.
	 * @param fullPath
	 */
	public void load(String fullPath) {
		// Load the resource through the editing domain.
		//
		editingDomain.loadResource
				(URI.createPlatformResourceURI(fullPath).toString());
	}
	/**
	 * @return list of resources loaded using editing domain.
	 */
	public EList getResources() {
		return editingDomain.getResourceSet().getResources();
	}
	/**
	 * Saves ith resource.
	 * @param i
	 */
	public void save(int i) {
		try {
			// Save the resource to the file system.
			//
			Resource savedResource = (Resource)editingDomain.getResourceSet().getResources().get(i);
			savedResource.save(Collections.EMPTY_MAP);
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
		try {
			// Refresh the necessary state.
			//
			((BasicCommandStack)editingDomain.getCommandStack()).saveIsDone();
		}
		catch (Exception exception) {
			// Something went wrong that shouldn't.
			//
			exception.printStackTrace();
		}
	}
}
