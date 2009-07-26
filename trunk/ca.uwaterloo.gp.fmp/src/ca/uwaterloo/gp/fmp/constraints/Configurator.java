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

import java.util.List;

import ca.uwaterloo.gp.fmp.Feature;
import ca.uwaterloo.gp.fmp.Node;


/**
 * @author Chang Hwan Peter Kim <chpkim@swen.uwaterloo.ca>		 
 */
public interface Configurator{
	/**
	 * The edit session modes. Depending on the mode, the user can make certain choices
	 * and their choices will result in different consequences.
	 */
	// Only allows user to select undecided features and deselect user-selected features
	public static final int EDIT_MODE_USER_ONLY = 0;
	public static final String EDIT_MODE_USER_ONLY_TITLE = "User choices only";
	// When a user tries to make a choice that conflicts, asks the user what to do 
	public static final int EDIT_MODE_INTERACTIVE = 1;
	public static final String EDIT_MODE_INTERACTIVE_TITLE = "Interactive";
	// Always takes the user's most recent's decisions, resolving conflicts by 
	// discarding previous choices 
	public static final int EDIT_MODE_AUTORESOLVE_CONFLICTS = 2;
	public static final String EDIT_MODE_AUTORESOLVE_CONFLICTS_TITLE = "Auto-resolve conflicts";
	// Does not provide any intelligence during editing session - so user's can make
	// mistakes and make wrong choices - but it's up to the responsibility of the user
	// to make sure that eventually, the configuration is correct
	public static final int EDIT_MODE_NO_INTEL = 3;
	
	/**
	 * Sets the feature tree as feature. Reconstructs the propositional formulas and compiles
	 * the solution representation.
	 * @param feature
	 * @return
	 */
	public Result setFeature(Feature feature);
	
	/**
	 * Returns the solution set to the representation of obj, additional propositional formula, 
	 * time limit, and the number of solutions. 
	 * @param obj
	 * @param additionalFormula
	 * @param variableList
	 * @param timeLimitInMillis
	 * @param numOfSolutions
	 * @return a Result object with value as a List of variableAssignments, and a message indicating
	 * any errors that happened. 
	 */
	public Result getSolutions(Object obj, PropositionalFormula additionalFormula, List variableList, long timeLimitInMillis, int requestedNumOfSolutions);
	
	public int getMode();
	public void setMode(int mode);
	public Node getNode(int pmNode);
	public Node getNode(String pmNode);
	public int getPMNodeInt(Node node);
	public String getPMNodeStr(Node node);
	public int getVarCount();
	public String getVarName(int variable);
	public int getVarIndex(String varible_name);
	public int getMin(int variable);
	public int getMin(String variable);
	public  int getMax(int variable);
	public int getMax(String variable);
	public String getValueName(int variable, int value);
	public void assignValue(String variable, int value, boolean force);
	public void assignValue(int variable, int value, boolean force);
	public double getSolutionCount();
	public void setRequestedSolCount(int solCount);
	public int[] getSolutions();
	public int getSelectedValue(int variable);
//	public void applySelection(CS_VarSelection selection);
//	public void applySelections(CS_VarSelection[] selection_array);
//	public CS_VarSelection getSelection(int variable);
//	public CS_VarSelection[] getSelections();
	public void removeSelection(String variable,boolean values,boolean min,boolean max);
	public void removeSelection(int variable,boolean values,boolean min,boolean max);
	public Conflicts getConflicts(String name);
	public int getPMNodeCount();
	public int[] getPMNodeChildren(int node);
	public int getPMNodeParent(int node);
	public String getPMNodeName(int node);
	public int getPMNodeIndex(int node);
	public int getPMNodeFromVariable(int variable);
	public int getPMNodeFromPath(String path);

	/**
	 * @param constraint
	 * @return
	 */
	public boolean evaluateBoolean(PropositionalFormula constraint, String exprId);
	public String evaluateString(PropositionalFormula constraint, String exprId);
}
