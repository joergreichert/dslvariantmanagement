
package com.demo.logic.instantMessagingService;

public interface IPAudioServicesProvider {
	
	com.demo.logic.IAudioServiceResponse requestDial( com.demo.logic.IAudioServiceRequest request );
	
 	

	
 	


	void receiveAddToConference( com.demo.logic.IAudioServiceRequest message );
	
 	


	void receiveHangUp( com.demo.logic.IAudioServiceRequest message );
	
 	


	void receiveMicOff( com.demo.logic.IAudioServiceRequest message );
	
 	


}
