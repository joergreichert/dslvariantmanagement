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

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * @author Chang Hwan Peter Kim <chpkim@swen.uwaterloo.ca>
 */
public class PropositionalFormula {
	protected String text;
	protected List variables = new Vector();
	protected String rules = "";
	
	/**
	 * @return Returns the text.
	 */
	public String getText() {
		text = "variable\n     ";
		
		for(Iterator variablesIterator = variables.iterator(); variablesIterator.hasNext(); ) 
		{
			String id = (String)variablesIterator.next();
			text += id + ", ";
		}
		text = ModelToPropositionTranslator.removeTrailer(text, ", ") + ": boolean;\n";
		
		text += "rule\n     " + rules + "\n";
		
		return text;
	}
	/**
	 * @param text The text to set.
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * @return Returns the variables.
	 */
	public List getVariables() {
		return variables;
	}
	/**
	 * @param variables The variables to set.
	 */
	public void setVariables(List variables) {
		this.variables = variables;
	}
	/**
	 * @return Returns the rules.
	 */
	public String getRules() {
		return rules;
	}
	/**
	 * @param rules The rules to set.
	 */
	public void setRules(String rules) {
		this.rules = rules;
	}
}
