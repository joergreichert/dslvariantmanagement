
package com.demo.logic.messageDispatcher;

/**
 * generated base class for the component MessageDispatcher
 * you have to manually write a subclass called MessageDispatcher
*/
public abstract class MessageDispatcherBase implements org.openarchitectureware.demo.java.platform.IComponent , IPPUIProvider {

	
	

 		

	
	

 		

	
	// a "hack" to make Eclipse provide a useful quick fix
	// to create the required subclass
	static { 
		if (false) {
			MessageDispatcherBase temp = new MessageDispatcher();
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

	
	
		

			// rService is a toMany port, hence the list
			private java.util.List<IPRServiceRequirer> rServiceProxyList = new java.util.ArrayList<IPRServiceRequirer>();

			// register a new provider for this port (toMany!)
			public void __addRServiceProxy( IPRServiceRequirer proxy ) {
				this.rServiceProxyList.add( proxy );
			}
			
			// returns the index'th of the providers
			protected IPRServiceRequirer rService( int index ) {
				return this.rServiceProxyList.get(index);
			} 

			// returns the number of providers registered with the rService toMany port
			protected int rServiceCount() {
				return this.rServiceProxyList.size();
			} 
		
		
	
	
} 
