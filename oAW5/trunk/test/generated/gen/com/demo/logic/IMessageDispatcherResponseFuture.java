
package com.demo.logic;

public interface IMessageDispatcherResponseFuture {

	public boolean isResultAvailable();
	
	public IMessageDispatcherResponse getValue();

}
