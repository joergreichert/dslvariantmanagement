/**************************************************************************************
 * Copyright (c) 2005, 2006 Generative Software Development Lab, University of Waterloo
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   1. Generative Software Development Lab, University of Waterloo,
 *      http://gp.uwaterloo.ca  - initial API and implementation
 **************************************************************************************/
package ca.uwaterloo.gp.fmp.constraints;

/**
 * @author Chang Hwan Peter Kim <chpkim@swen.uwaterloo.ca>
 */
public class VariableAssignment
{
	protected String variable;
	protected boolean value;
	
	/**
	 * @return Returns the value.
	 */
	public boolean getValue() {
		return value;
	}
	/**
	 * @param value The value to set.
	 */
	public void setValue(boolean value) {
		this.value = value;
	}
	/**
	 * @return Returns the variable.
	 */
	public String getVariable() {
		return variable;
	}
	/**
	 * @param variable The variable to set.
	 */
	public void setVariable(String variable) {
		this.variable = variable;
	}
}
