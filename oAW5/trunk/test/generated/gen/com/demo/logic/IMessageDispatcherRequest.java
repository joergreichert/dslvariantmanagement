
package com.demo.logic;

public class IMessageDispatcherRequest {

	

	

		private String message = null;
 
		public void setMessage(String value) {
			this.message = value;
		}
		
		public String getMessage() {
			return this.message;
		}

	



	

		private int sourceID = 0;
 
		public void setSourceID(int value) {
			this.sourceID = value;
		}
		
		public int getSourceID() {
			return this.sourceID;
		}

	



	

		private int destinationID = 0;
 
		public void setDestinationID(int value) {
			this.destinationID = value;
		}
		
		public int getDestinationID() {
			return this.destinationID;
		}

	



	

		private int conversationID = 0;
 
		public void setConversationID(int value) {
			this.conversationID = value;
		}
		
		public int getConversationID() {
			return this.conversationID;
		}

	



	

		private int openKey = 0;
 
		public void setOpenKey(int value) {
			this.openKey = value;
		}
		
		public int getOpenKey() {
			return this.openKey;
		}

	



	

		private int encryptedHash = 0;
 
		public void setEncryptedHash(int value) {
			this.encryptedHash = value;
		}
		
		public int getEncryptedHash() {
			return this.encryptedHash;
		}

	



}
