
package com.demo.logic;

public class IInstantMessagingServiceRequest {

	

	

		private int senderID = 0;
 
		public void setSenderID(int value) {
			this.senderID = value;
		}
		
		public int getSenderID() {
			return this.senderID;
		}

	



	

		private int receiverID = 0;
 
		public void setReceiverID(int value) {
			this.receiverID = value;
		}
		
		public int getReceiverID() {
			return this.receiverID;
		}

	



	

		private String message = null;
 
		public void setMessage(String value) {
			this.message = value;
		}
		
		public String getMessage() {
			return this.message;
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
