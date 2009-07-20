
package com.demo.logic;

public interface IAudioConnectionResponseFuture {

	public boolean isResultAvailable();
	
	public IAudioConnectionResponse getValue();

}
