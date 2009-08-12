package org.openarchitectureware.var.featureaccess.chk;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

public class FeatureAccessConstraintHelper {

	public static boolean duplicateNamesWithoutFeatureClause( Object candidate, List elements ) {
		EObject cand = (EObject)candidate;
		List allWithSameName = findAllWithSameName( elements, cand );
		if ( allWithSameName.size() <= 1 ) return false;
		return !allHaveFeatureClause(allWithSameName);
	}

	private static boolean allHaveFeatureClause(List allWithSameName) {
		for (Iterator iterator = allWithSameName.iterator(); iterator.hasNext();) {
			EObject it = (EObject) iterator.next();
			try {
				EObject featureClause = (EObject)(it.eGet(it.eClass().getEStructuralFeature("featureClause")));
				if ( featureClause == null ) return false;
			} catch ( Throwable t ) {
				return false;
			}
		}
		return true; 
	}

	private static List findAllWithSameName(List elements, EObject candidate) {
		List duplicates = new ArrayList();
		if ( elements == null ) return duplicates;
		if ( elements.size() == 0 ) return duplicates;
		for (Iterator iterator = elements.iterator(); iterator.hasNext();) {
			EObject it = (EObject) iterator.next();
			String candidateName = getName(candidate);
			String itName = getName(it);
			if ( candidateName == null ) throw new RuntimeException("element "+candidate+" has no name");
			if ( itName == null ) throw new RuntimeException("element "+it+" has no name");
			if ( candidateName.equals(itName)) duplicates.add(candidate);
		}
		return duplicates;
	}

	private static String getName(EObject it) {
		try {
			return (String)(it.eGet(it.eClass().getEStructuralFeature("name")));
		} catch ( Throwable t ) {
			throw new RuntimeException(t);
		}
	}
	
}
