
package com.demo.logic.vOIPConnection;

public interface IPAudioConProvider {
	
	com.demo.logic.IAudioConnectionResponse requestEstablishCon( com.demo.logic.IAudioConnectionRequest request );
	
 	

	
 	


	void receiveBreakCon( com.demo.logic.IAudioConnectionRequest message );
	
 	


}
