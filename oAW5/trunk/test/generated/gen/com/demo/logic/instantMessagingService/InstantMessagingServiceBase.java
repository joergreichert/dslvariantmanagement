
package com.demo.logic.instantMessagingService;

/**
 * generated base class for the component InstantMessagingService
 * you have to manually write a subclass called InstantMessagingService
*/
public abstract class InstantMessagingServiceBase implements org.openarchitectureware.demo.java.platform.IComponent , IPPDispatcherProvider, IPPServiceProvider, IPAudioServicesProvider {

	
	

 		

	

 		

	

 		

	
	

 		

	
	// a "hack" to make Eclipse provide a useful quick fix
	// to create the required subclass
	static { 
		if (false) {
			InstantMessagingServiceBase temp = new InstantMessagingService();
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

	
	
		
		
			// audioCon is a to-one port
			private IPAudioConRequirer audioConProxy = null;

			// register the provider for the audioCon port
			public void __setAudioConProxy( IPAudioConRequirer proxy ) {
				this.audioConProxy = proxy;
			}
			
			// return the provider for the audioCon port
			protected IPAudioConRequirer audioCon() {
				return this.audioConProxy;
			} 

		
		
	
	
} 
