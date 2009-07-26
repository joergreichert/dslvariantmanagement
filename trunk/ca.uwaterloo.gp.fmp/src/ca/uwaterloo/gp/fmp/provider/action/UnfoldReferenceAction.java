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
package ca.uwaterloo.gp.fmp.provider.action;

import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.ui.action.CommandActionHandler;
import org.eclipse.ui.IEditorPart;

import ca.uwaterloo.gp.fmp.Reference;
import ca.uwaterloo.gp.fmp.presentation.FmpEditor;
import ca.uwaterloo.gp.fmp.provider.command.UnfoldReferenceCommand;


/**
 * @author Michal Antkiewicz <mantkiew@swen.uwaterloo.ca>
 */
public class UnfoldReferenceAction extends CommandActionHandler {
	AdapterFactory adapterFactory;
	
	public UnfoldReferenceAction(EditingDomain domain) {
		super(domain, "Unfold Reference");
	}
	public UnfoldReferenceAction()  {
	    super(null, "Unfold Reference");
	}
	public void setActiveEditor(IEditorPart editorPart)  {
	    if (editorPart instanceof IEditingDomainProvider)
	    	domain = ((IEditingDomainProvider)editorPart).getEditingDomain();
	    if (editorPart instanceof FmpEditor)
	    	adapterFactory = ((FmpEditor) editorPart).getAdapterFactory();
	}
	public Command createCommand(Collection selection) {
		if (selection.size() == 1) {
			EObject obj = (EObject) selection.iterator().next();
			if (obj instanceof Reference)
				return new UnfoldReferenceCommand(domain, adapterFactory, (Reference) obj);
		}
		return UnexecutableCommand.INSTANCE;
	}
	public boolean isEnabled() {
		return (command != null && command.canExecute());
	}
}
