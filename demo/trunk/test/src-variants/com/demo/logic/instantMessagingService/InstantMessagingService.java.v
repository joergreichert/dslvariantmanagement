package com.demo.logic.instantMessagingService;

import  java.lang.Math;

import com.demo.logic.IAudioServiceRequest;
import com.demo.logic.IAudioServiceResponse;
import com.demo.logic.IInstantMessagingServiceRequest;
import com.demo.logic.IMessageDispatcherRequest;
import com.demo.logic.IMessageDispatcherResponse;

public class InstantMessagingService extends InstantMessagingServiceBase {

	@Override
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
		
		//# Security
		msg.setOpenKey( request.getOpenKey() );
		//~# Security
		
		//# Authentication
		msg.setOpenKey( request.getOpenKey() );
		msg.setEncryptedHash( request.getEncryptedHash() );
		//~# Authentication
		
		//delegate message to the sender-method for the instant messenger
		receiveSendMessage(msg);
		
		IMessageDispatcherResponse response = new IMessageDispatcherResponse();
		response.setConversationID(request.getConversationID());
		return response;
	}

	@Override
	public void receiveSendMessage(IInstantMessagingServiceRequest message) {
		//send message
	}

	@Override
	public IAudioServiceResponse requestDial(IAudioServiceRequest request) {
		
		//# TelephoneIntegration
		IPAudioConRequirer audioCon = audioCon();
		//make TelephoneIntegration stuff
		return null;
		//~# TelephoneIntegration
		
		//# VoiceOverIP
		IPAudioConRequirer audioCon = audioCon();
		//make VoiceOverIP stuff
		return null;
		//~# VoiceOverIP
		
	}
	
	@Override
	public void receiveAddToConference(IAudioServiceRequest message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receiveHangUp(IAudioServiceRequest message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receiveMicOff(IAudioServiceRequest message) {
		// TODO Auto-generated method stub
		
	}

}
