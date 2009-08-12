package com.demo.logic.instantMessagingService;

import  java.lang.Math;

import com.demo.logic.IAudioServiceRequest;
import com.demo.logic.IAudioServiceResponse;
import com.demo.logic.IInstantMessagingServiceRequest;
import com.demo.logic.IMessageDispatcherRequest;
import com.demo.logic.IMessageDispatcherResponse;

public class InstantMessagingService extends InstantMessagingServiceBase {

	
	public IMessageDispatcherResponse requestDispatch(
			IMessageDispatcherRequest request) {
		if( request.getConversationID() == 0 ){
			request.setConversationID( (int) System.currentTimeMillis() );
		}
		
		//convert generic message request to instant message request
		IInstantMessagingServiceRequest msg = new IInstantMessagingServiceRequest();
		msg.setMessage( request.getMessage() );
		msg.setReceiverID( request.getSourceID() );
		msg.setSenderID( request.getDestinationID() );
		  
		msg.setOpenKey( request.getOpenKey() );
		
		msg.setOpenKey( request.getOpenKey() );
		msg.setEncryptedHash( request.getEncryptedHash() );
		
		//delegate message to the sender-method for the instant messenger
		receiveSendMessage(msg);
		
		IMessageDispatcherResponse response = new IMessageDispatcherResponse();
		response.setConversationID(request.getConversationID());
		return response;
	}

	
	public void receiveSendMessage(IInstantMessagingServiceRequest message) {
		//send message
	}

	
	public IAudioServiceResponse requestDial(IAudioServiceRequest request) {
		
		
		IPAudioConRequirer audioCon = audioCon();
		//make VoiceOverIP stuff
		return null;
		
	}
	
	
	public void receiveAddToConference(IAudioServiceRequest message) {
		// TODO Auto-generated method stub
		
	}

	
	public void receiveHangUp(IAudioServiceRequest message) {
		// TODO Auto-generated method stub
		
	}

	
	public void receiveMicOff(IAudioServiceRequest message) {
		// TODO Auto-generated method stub
		
	}

}
