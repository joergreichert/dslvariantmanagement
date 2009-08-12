package org.openarchitectureware.demo.java.platform;

/**
 * An interface implemented by all generated component 
 * classes. By default, the system provides an instance
 * ID slot for each component, so it remembers its own
 * name in the system
 */

public interface IComponent {

	void __setInstanceID( String id );
	String __getInstanceID();
	
}
