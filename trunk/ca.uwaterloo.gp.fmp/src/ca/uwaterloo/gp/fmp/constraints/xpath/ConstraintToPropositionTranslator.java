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
package ca.uwaterloo.gp.fmp.constraints.xpath;

import ca.uwaterloo.gp.fmp.Constraint;
import ca.uwaterloo.gp.fmp.Feature;
import Data.EvaluatorTree;

/**
 * @author Chang Hwan Peter Kim <chpkim@swen.uwaterloo.ca>
 *
 */
public class ConstraintToPropositionTranslator
{
	/**
	 * The RootFeature holding constraints
	 */
	protected Feature rootFeature;
	
	public ConstraintToPropositionTranslator(Feature rootFeature)
	{
		 setRootFeature(rootFeature);
	}
	
	/**
	 * Returns the text that represents the translated form of constraints
	 * @return
	 */
	public String translate()
	{
		String expression = "";
		
		if(rootFeature != null)
		{
			for(int i = 0; i < rootFeature.getConstraints().size(); i++)
			{
				expression += convertToBoolean(((Constraint)rootFeature.getConstraints().get(i)).getText());
			}
		}
		
		return expression;
	}
	
	/**
	 * @return Returns the rootFeature.
	 */
	public Feature getRootFeature() {
		return rootFeature;
	}
	/**
	 * @param rootFeature The rootFeature to set.
	 */
	public void setRootFeature(Feature rootFeature) {
		this.rootFeature = rootFeature;
	}
	
	/**
	 * 
	 * @author vinayks
	 * 
	 * converts constraints expressed in XPath to pm Expressions
	 */
	private String convertToBoolean(String constraint)
	{
		Compile myCompiler = null;
	    //System.out.println("XPath expressions is: " + constraint);	
	    try{	
	    	myCompiler = new Compile();
	    } catch(Exception e){
	    	e.printStackTrace();
	    } 
	    
	   	myCompiler.setInput(constraint);
	   	myCompiler.run();
	    	
	    EvaluatorTree result = (EvaluatorTree)myCompiler.getResult();
		
	    String booleanExpr = (String)result.getValueOf("boolExpr");
		
	    //System.out.println(booleanExpr);
		
	    return booleanExpr;
		
	}
}
