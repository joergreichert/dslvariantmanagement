
package com.demo.logic.collabDB;

/**
 * generated base class for the component CollabDB
 * you have to manually write a subclass called CollabDB
*/
public abstract class CollabDBBase implements org.demo.java.platform.IComponent  {

	
	
	
	// a "hack" to make Eclipse provide a useful quick fix
	// to create the required subclass
	static { 
		if (false) {
			CollabDBBase temp = new CollabDB();
		}
	}

	// each component instance at runtime knows the name of 
	// the instance in the model from which it was created	
	private String instanceID;
	
	public void __setInstanceID( String id ) {
		this.instanceID = id;
	}
	
	public String __getInstanceID() {
		return instanceID;
	}

	
	
} 
