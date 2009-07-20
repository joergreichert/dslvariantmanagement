package org.demo.java.platform;

import java.util.List;

/** 
 * will be replaced by Spring's application context
 */
public interface IComposition {

	String __getCompositionID(); 
	List<IComponent> __getInstances();
	void setup();
	
}
