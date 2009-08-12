
package com.demo.logic.vOIPConnection;

/**
 * generated base class for the component VOIPConnection
 * you have to manually write a subclass called VOIPConnection
*/
public abstract class VOIPConnectionBase implements org.openarchitectureware.demo.java.platform.IComponent , IPAudioConProvider {

	
	

 		

	
	
	// a "hack" to make Eclipse provide a useful quick fix
	// to create the required subclass
	static { 
		if (false) {
			VOIPConnectionBase temp = new VOIPConnection();
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
