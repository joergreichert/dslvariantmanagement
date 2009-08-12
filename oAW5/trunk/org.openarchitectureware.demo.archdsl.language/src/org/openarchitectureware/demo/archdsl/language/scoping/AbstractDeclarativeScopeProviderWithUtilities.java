package org.openarchitectureware.demo.archdsl.language.scoping;

import java.lang.reflect.Method;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.IScopedElement;
import org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider;
import org.eclipse.xtext.scoping.impl.ScopedElement;
import org.eclipse.xtext.scoping.impl.SimpleScope;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;

public class AbstractDeclarativeScopeProviderWithUtilities extends AbstractDeclarativeScopeProvider {

	public static class SimpleNameScope<T extends EObject> extends SimpleScope {
		public SimpleNameScope( Iterable<T> elementsWithName ) {
			super( IScope.NULLSCOPE, Iterables.transform(elementsWithName,
				new Function<EObject, IScopedElement>() {
			private Method nameMethod = null;
			public IScopedElement apply(EObject s) {
				return ScopedElement.create(getName(s), s);
			}
			private String getName(EObject elem) {
				if ( nameMethod == null ) {
					nameMethod = getMethod(elem);
				}
				try {
					return (String)nameMethod.invoke(elem, null);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
			private Method getMethod(EObject elem) {
				try {
					return elem.getClass().getMethod("getName", null);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		}));
		}
		
	}
	
	protected <T extends EObject> IScope createSimpleNameScope( Iterable<T> elementsWithName ) {
		return new SimpleNameScope<T>(elementsWithName);
		
	}
	
	protected <C extends EObject> C goUpTo( EObject from, java.lang.Class<C> type ) {
		EObject parent = from;
		while ( parent != null ) {
			parent = parent.eContainer();
			if ( type.isInstance(parent)) return (C)parent;
		}
		return null;
	}
	
	
}
