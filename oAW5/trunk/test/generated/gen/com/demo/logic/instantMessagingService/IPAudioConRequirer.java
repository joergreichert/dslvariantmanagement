
package com.demo.logic.instantMessagingService;

public interface IPAudioConRequirer {
	
	com.demo.logic.IAudioConnectionResponseFuture receiveEstablishCon( com.demo.logic.IAudioConnectionRequest request );
	
 	

	
 	

	
 	


	void sendBreakCon( com.demo.logic.IAudioConnectionRequest message );
	
 	


}
