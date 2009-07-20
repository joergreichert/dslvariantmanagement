package org.openarchitectureware.var.tailor.model.weaver;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

public class WeaverEditorAPI {

	public static Collection<EObject> findPointcutTargets( Collection<EObject> allElements, EObject pointcut, EObject aspectElement ) {
		return new Weaver().resolveTargets(allElements, pointcut, aspectElement, null);
	}
	
}
