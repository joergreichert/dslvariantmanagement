package org.openarchitectureware.var.featureaccess;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;



public class ElementRemovalHelper { 
	
	public static EObject removeElementFromBase (EObject element, EObject baseModel ) {
		Map<EObject, Collection<EStructuralFeature.Setting>> referenceMap = EcoreUtil.CrossReferencer.find(baseModel.eContents());
		Collection<EStructuralFeature.Setting> settings = referenceMap.get(element);
		if ( settings != null ) {
			Iterator<EStructuralFeature.Setting> it = settings.iterator();
			while (it.hasNext()) {
				EStructuralFeature.Setting s = (EStructuralFeature.Setting)it.next();
				EStructuralFeature feature = s.getEStructuralFeature();
				EObject client = s.getEObject();
				if ( feature.isChangeable() && !feature.isDerived() ) {
					if (feature.getUpperBound() == 1) {
						client.eSet(feature, null);
					} else {
						Collection coll = (Collection)client.eGet(feature);
						coll.remove( element );
					}
				}
			}
		}
		EcoreUtil.remove(element);
		return baseModel;
	}
	
}
