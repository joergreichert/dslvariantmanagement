package org.openarchitectureware.demo.archdsl.language.helper;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.openarchitectureware.demo.archdsl.language.adsl.System;

public class AdslHelper {
  
  /**
   * returns all elements in all resources of the resourceSet of anElementInRootResource
   * @param anElementInRootResource
   * @return all elements 
   */
  public static List<EObject> allElements( EObject anElementInRootResource ) {
    ResourceSet rs = anElementInRootResource.eResource().getResourceSet();
    TreeIterator<EObject> all = EcoreUtil.getAllContents(rs, true);
    List<EObject> result = new ArrayList<EObject>();
    while ( all.hasNext() ) {
        Object ne = all.next();
        if ( ne instanceof EObject ) result.add((EObject)ne);
    }
    return result;
  }
  
  /**
   * returns all systems that are not empty, using the predicate defined above
   * @param o
   * @return non empty systems
   */
  public static List<System> allNonEmptySystems(EObject o){
    List<System> systems = new ArrayList<System>();
    for (System s : allSystems(o)) {
      if( ! isEmpty(s) )
        systems.add(s);
    }
    return systems;
  }

  
  /**
   * determines whether a system is empty
   * @param s
   * @return boolean empty status
   */
  public static boolean isEmpty(System s) {
    if ( s.getComponents().size() == 0
         && s.getInterfaces().size() == 0
         && s.getDatatypes().size() == 0 )
      return true;
    else return false;
  }

  /**
   * returns all systems in the model, incl. those imported from other files
   * @param o
   * @return all systems
   */
  public static List<System> allSystems(EObject o){
    List<EObject> elements = allElements(o);
    List<System> systems = new ArrayList<System>(); 
    for(EObject e : elements){
      if(e instanceof System)
        systems.add((System) e);
    }
    return systems;
  }
  
  /**
   * the qualified name for an element is 
   * based on the system's qualified name
   * @param o
   * @return qualified name of o (dot separated)
   */
  public static String qualifiedName( EObject o ){
    return qualifiedName( system(o) ) + "." + localName(o);
  }
  
  /**
   * uses reflection to extract the name property out of any element
   * @param o
   * @return name of o
   */
  public static String localName(EObject o) {
    return (String) o.eGet( o.eClass().getEStructuralFeature("name") );
  }

  public static String qualifiedName( System s ){
    System parentSys = parentSystem(s);
    return ( parentSys != null 
             ? qualifiedName(parentSystem(s)) +"."+s.getName() 
             : s.getName());
  }
  
  public static System parentSystem(System s) {
    return (System) ( s.eContainer() instanceof System 
                      ? s.eContainer() : null );
  }

  /**
   * a system's system is itself, for any other element
   * it's the container's system (i.e. recursion, but
   * terminated by the system itself)
   * @param o
   * @return owner system of o
   */
  public static System system(EObject o){
    if(o instanceof System){
      return (System) o; 
    } else {
      return ( o.eContainer() != null ? system(o.eContainer()) : null );
    }
  }
  
}
