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
package ca.uwaterloo.gp.fmp.constraints.JavaBDDConfigurator;


import java.io.StringReader;
import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import net.sf.javabdd.BDD;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import ca.uwaterloo.gp.fmp.Clonable;
import ca.uwaterloo.gp.fmp.ConfigState;
import ca.uwaterloo.gp.fmp.Feature;
import ca.uwaterloo.gp.fmp.Node;
import ca.uwaterloo.gp.fmp.constraints.Configurator;
import ca.uwaterloo.gp.fmp.constraints.Conflicts;
import ca.uwaterloo.gp.fmp.constraints.ModelToPropositionTranslator;
import ca.uwaterloo.gp.fmp.constraints.PropositionalFormula;
import ca.uwaterloo.gp.fmp.constraints.Result;
import ca.uwaterloo.gp.fmp.constraints.VariableAssignment;
import ca.uwaterloo.gp.fmp.constraints.xpath.ConstraintToPropositionTranslator;
import ca.uwaterloo.gp.fmp.system.NodeIdDictionary;

import antlr.CommonAST;
import antlr.RecognitionException;
import antlr.TokenStreamException;


/**
 * @author Krzysztof Pietroszek <kmpietro@swen.uwaterloo.ca>
 */
public class JavaBDDConfigurator implements Configurator
{
	protected int mode;
	protected Feature feature;
	protected BDDManager controlManager;
	protected String fileNamePrefix = null;
	protected PropositionalFormula propositionalFormula = null;

	public Object clone() {
		JavaBDDConfigurator configurator = new JavaBDDConfigurator();
		configurator.controlManager = (BDDManager)controlManager.clone();
		configurator.fileNamePrefix = fileNamePrefix;
		configurator.feature = feature;
		configurator.propositionalFormula = propositionalFormula;
		return configurator;
	}
	
	public JavaBDDConfigurator()
	{
		mode = Configurator.EDIT_MODE_AUTORESOLVE_CONFLICTS; 
	}

	/* (non-Javadoc)
	 * 
	 * 
	 * 
	 * @see fmp.constraints.Configurator#getSolutions(java.lang.Object, fmp.constraints.PropositionalFormula, long, int)
	 */
	public Result getSolutions(Object obj, PropositionalFormula additionalFormula, List variableList, long timeLimitInMillis, int requestedNumOfSolutions) 
	{
//		feature = (Feature) obj;

		// Generate a boolean expression from the given model
/*		ModelToPropositionTranslator propositionTranslator = new ModelToPropositionTranslator();			
		propositionalFormula = propositionTranslator.translate(feature, false);
 		propositionalFormula.setRules(propositionalFormula.getRules().substring(0,propositionalFormula.getRules().length()-1)); 
			
		String constraints = (new ConstraintToPropositionTranslator((Feature)feature.getOrigin())).translate();
		
		if (constraints != "") propositionalFormula.setRules(propositionalFormula.getRules()
				+ " and (" 
				+ constraints.substring(0,additionalFormula.getRules().length()-1)+")");

		if (additionalFormula != null) propositionalFormula.setRules(propositionalFormula.getRules()
				+" and (" 
				+ additionalFormula.getRules().substring(0,additionalFormula.getRules().length()-1)+" )");
*/
//	    System.out.println(propositionalFormula.getRules());
		
		if (controlManager == null) throw new NullPointerException();

		long s = System.currentTimeMillis();
		BDDManager aControlManager = adjustControlManager(additionalFormula.getRules().substring(0,additionalFormula.getRules().length()-1), controlManager);
		long f = System.currentTimeMillis();
		System.out.println("**** BOOLEAN -> BDD + MERGE ****");
		System.out.println("Evaluation time:"+(f-s));
		System.out.println("**** BOOLEAN -> BDD + MERGE END ****");

		s = System.currentTimeMillis();
		System.out.println(" SOLUTIONS: "+getSolutionCount(aControlManager));
		f = System.currentTimeMillis();
		System.out.println("**** SAT ****");
		System.out.println("Satisfiability (number of solutions):"+(f-s));
		System.out.println("**** SAT END ****");
		
		Result result = new Result();
		List variableAssignmentsList = new ArrayList();
				
		if (aControlManager != null) {
			if (getSolutionCount(aControlManager) > 0) {
				variableAssignmentsList.add(new Integer((int) getSolutionCount(aControlManager)));
				BDD oneSolution = aControlManager.solution.satOne();
				byte[] e = (byte[])oneSolution.allsat().get(0);
				for (int k = 0; k < e.length; k++){
					System.out.println(aControlManager.getVariableNameByNumber(k) + " = " + e[k]);
				}
				List variableAssignments = new ArrayList();
				// for now, I need only the first solution
				for (int i = 0; i <= aControlManager.getActVarCount(); i++)
					if (variableList.contains(aControlManager.getVariableNameByNumber(i))) {
						VariableAssignment varAssign= new VariableAssignment();
						varAssign.setValue((e[i] == 0) ? false : true);
						varAssign.setVariable(aControlManager.getVariableNameByNumber(i));
						variableAssignments.add(varAssign);
					}
				variableAssignmentsList.add(variableAssignments);
				result.setMessage("There exists a solution");
			    result.setValue(variableAssignmentsList);
			} else {
				result.setValue("false");
			}
			return result;
	
		} else {
			result.setValue("false");
			return result;
		}
	}
	

	protected BDDManager adjustControlManager(String propositionalFormula, BDDManager manager)
	{

	    BooleanExpressionLexer lexer = new BooleanExpressionLexer(new StringReader(propositionalFormula)); 
	    BooleanExpressionParser parser = new BooleanExpressionParser(lexer);
	    try {
			parser.expression();
		} catch (RecognitionException e) {
			e.printStackTrace();
		} catch (TokenStreamException e) {
			e.printStackTrace();
		}

	    CommonAST parseTree = (CommonAST)parser.getAST();
//	    System.out.println(parseTree.toStringList());
//	    ASTFrame frame = new ASTFrame("The tree", parseTree);
//	    frame.setVisible(true);

        ExpressionTreeWalker walker = new ExpressionTreeWalker(manager);
	    BDD problem;
		try {
			problem = walker.expression(parseTree);
			manager.setProblem(problem);
		} catch (RecognitionException e1) {
			e1.printStackTrace();
		}
	    
		return manager;
	}

	/**
	 * Create a control manager whose pm and vt filename will have "aFileNamePrefix" and
	 * whose values will be those assigned from the feature diagram specified by "rootFeature"
	 * @param rootFeature
	 * @param aFileNamePrefix
	 * @return
	 */
	protected BDDManager createControlManager(Node rootFeature, PropositionalFormula propositionalFormula, boolean treatUndecidedAsEliminated)
	{

		BDDManager aControlManager = new BDDManager(propositionalFormula.getVariables().size(), propositionalFormula.getVariables().size());
	    BooleanExpressionLexer lexer = new BooleanExpressionLexer(new StringReader(propositionalFormula.getRules())); 

	    BooleanExpressionParser parser = new BooleanExpressionParser(lexer);
	    try {
			parser.expression();
		} catch (RecognitionException e) {
			e.printStackTrace();
		} catch (TokenStreamException e) {
			e.printStackTrace();
		}

	    CommonAST parseTree = (CommonAST)parser.getAST();
//	    System.out.println(parseTree.toStringList());
//	    ASTFrame frame = new ASTFrame("The tree", parseTree);
//	    frame.setVisible(true);

        ExpressionTreeWalker walker = new ExpressionTreeWalker(aControlManager);
	    BDD problem;
		try {
			problem = walker.expression(parseTree);
			aControlManager.setProblem(problem);
		} catch (RecognitionException e1) {
			e1.printStackTrace();
		}
	    
	    // assign values to the control manager by traversing the rootFeature
		if(rootFeature != null)
			this.assignValuesToCtrlMngr(rootFeature, aControlManager, treatUndecidedAsEliminated);
		
		return aControlManager;
	}
	
	/**
	 * @param feature2
	 * @param additionalFormula
	 */
	public Result setFeature(Feature f) {
		return setFeature(f,null);
	}
	/**
	 * @param rootFeature The rootFeature to set.
	 */
	public Result setFeature(Feature feature, PropositionalFormula additionalFormula) {
		this.feature = feature;
		fileNamePrefix = null;
		
		// Generate a boolean expression from the given model
		ModelToPropositionTranslator propositionTranslator = new ModelToPropositionTranslator();			
		propositionalFormula = propositionTranslator.translate(feature, false);
		propositionalFormula.setRules(propositionalFormula.getRules().substring(0,propositionalFormula.getRules().length()-1)); 
		
		String constraints = (new ConstraintToPropositionTranslator((Feature)feature.getOrigin())).translate();
		
		if (constraints != "") propositionalFormula.setRules(propositionalFormula.getRules()
				+ " and (" 
				+ constraints.substring(0,constraints.length()-1).replaceAll(";",") and (")+")");

		if (additionalFormula != null) propositionalFormula.setRules(propositionalFormula.getRules()
				+" and (" 
				+ additionalFormula.getRules().substring(0,additionalFormula.getRules().length()-1)+")");
		
		
		// create a Control Manager whose state reflects the VT file
		controlManager = createControlManager(this.feature, propositionalFormula, false);
		// generate solution for the first time
		getSolutionCount();
//		List b = controlManager.problem.allsat();
//		for (Iterator i = b.iterator(); i.hasNext();) {
//			byte[] s = (byte[]) i.next();
//			System.out.println(s);
//		}
		return null;
	}

	
	/**
	 * @param mode The mode to set.
	 */
	public void setMode(int mode) {
		this.mode = mode;
	}
	
	/**
	 * Returns the file name prefix shared by the compiled file (Virtual Table)
	 * and product model (PM) file.
	 * @return
	 */
	protected String getFileNamePrefix()
	{
		if(fileNamePrefix == null)
		{
			// below line doens't work
			//String fileName = feature.getURI().devicePath() + "@" + (new UID()).toString();
			fileNamePrefix = feature.getId() + "@" + generateGuid(feature);
			
			fileNamePrefix = replaceIllegalCharactersInFileName(fileNamePrefix);
		}
			
		return fileNamePrefix;
	}
	
	protected String replaceIllegalCharactersInFileName(String filename)
	{
		// TODO: properly determine the filename. For now, we're taking
		// the GUID and replacing any illegal characters
		filename = filename.replace('/', '_');
		filename = filename.replace(':', '_');
		filename = filename.replace('.', '_');
		
		return filename;
	}
	
	private static String generateGuid( EObject o ) {
		String guid = null;
		
		if(o != null)
		{
			// TODO: make sure always that this resource is not null
			Resource resource = o.eResource();
			if(resource != null)
			{
				URI uri = resource.getURI();
				UID uid = new UID();
				guid = constructGuid(uri.devicePath(), uid.toString());
			}
			else
				guid = (new UID()).toString();
		}
		
		return guid;
	}
	
	private static String constructGuid(String devicePath, String uidString)
	{
		return devicePath + "@" + uidString;
	}
	
	protected String getPMFileName()
	{
		return getFileNamePrefix() + ".pm";
	}
	
	protected String getVTFileName()
	{
		return getFileNamePrefix() + ".vt";
	}
	
	public int getMode() {		
		return mode;
	}

	/* (non-Javadoc)
	 * @see featureMdl.Configurator#getVarCount()
	 */
	public int getVarCount() {
		return controlManager.getActVarCount();
	}

	/* (non-Javadoc)
	 * @see featureMdl.Configurator#getVarName(int)
	 */
	public String getVarName(int variable) {
		return controlManager.getVariableNameByNumber(variable);
	}

	public int getVarIndex(String variable_name) {
		BDD bdd = controlManager.getBDDofVariableName(variable_name);
		return bdd.var();
	}

	public int getMin(int variable) {
		return getMin(variable,controlManager);
	}

	private int getMin(int variable,BDDManager ctrlManager) {
		BDD check = ctrlManager.solution.id();
		if (!check.and(ctrlManager.factory.nithVar((variable))).isZero()) return 0; // added n
		else return 1;
	}

	public int getMin(String variable) {
		return getMin(variable,controlManager);
	}
	private int getMin(String variable, BDDManager ctrlManager) {
		BDD check = ctrlManager.solution.id();
		if (!check.and(ctrlManager.factory.nithVar(ctrlManager.getNumberByVariableName(variable))).isZero()) return 0;
		else return 1;
	}

	public int getMax(int variable) {
		return getMax(variable,controlManager);
	}
	public int getMax(int variable, BDDManager ctrlManager) {
		BDD check = ctrlManager.solution.id();
		if (!check.and(ctrlManager.factory.ithVar(variable)).isZero()) return 1;
		else return 0;
	}

	public int getMax(String variable) {
		return getMax(variable,controlManager);
	}
	public int getMax(String variable, BDDManager ctrlManager) {
		BDD check = ctrlManager.solution.id();
		if (!check.and(ctrlManager.factory.ithVar(ctrlManager.getNumberByVariableName(variable))).isZero()) return 1;
		else return 0;
	}

	/* (non-Javadoc)
	 * @see featureMdl.Configurator#getValueName(int, int)
	 */
	public String getValueName(int variable, int value) {
    //IMPROPER IMPLEMENATION!!!!
		return null;
	}

	/* (non-Javadoc)
	 * @see featureMdl.Configurator#assignValue(java.lang.String, int, boolean)
	 */
	public void assignValue(String variable, int value, boolean force) {
		assignValue(variable,value,force,controlManager);
	}

	public void assignValue(String variable, int value, boolean force, BDDManager ctrlManager) {
		controlManager = ctrlManager;
		ctrlManager.setChanged();
		getSolutionCount();
		if (ctrlManager.isValueAssigned(variable)) 
    		ctrlManager.removeValueAssignment(variable);
        if (value == 1) {
        	ctrlManager.removeValueAssignment(variable);
            ctrlManager.storeValue(variable, true);
        } else {
        	ctrlManager.removeValueAssignment(variable);
            ctrlManager.storeValue(variable, false);
        }
        ctrlManager.setChanged();
		getSolutionCount();
	}

	/* (non-Javadoc)
	 * @see featureMdl.Configriurator#assignValue(int, int, boolean)
	 */
	public void assignValue(int variable, int value, boolean force) {
		assignValue(variable,value,force,controlManager);
	}

	public void assignValue(int variable, int value, boolean force, BDDManager ctrlManager) {
		controlManager.setChanged();
		getSolutionCount();
		if (controlManager.isValueAssigned(controlManager.getVariableNameByNumber(variable))) 
    		ctrlManager.removeValueAssignment(controlManager.getVariableNameByNumber(variable));
        if (value == 1) {
            controlManager.storeValue(controlManager.getVariableNameByNumber(variable), true);
        } else {
            controlManager.storeValue(controlManager.getVariableNameByNumber(variable), false);
        }
		controlManager.setChanged();
		getSolutionCount();
	}

	/* (non-Javadoc)
	 * @see featureMdl.Configurator#getSolutionCount()
	 */
	public double getSolutionCount() {
		return getSolutionCount(controlManager);
	}
	private double getSolutionCount(BDDManager ctrlManager) {
		// TODO Auto-generated method stub
		if (!ctrlManager.hasChanged()) 
			return ctrlManager.solution.satCount();
		if (ctrlManager != null) {
			ctrlManager.solution = ctrlManager.problem.id();
			for (int i=0; i <= ctrlManager.getActVarCount(); i++){
				String var = ctrlManager.getVariableNameByNumber(i);
				if (ctrlManager.isValueAssigned(var)) {
					if (ctrlManager.getValueAssignment(var) == true)
					   ctrlManager.solution.andWith(ctrlManager.getBDDofVariableName(var).id());
					if (ctrlManager.getValueAssignment(var) == false)
					   ctrlManager.solution.andWith(ctrlManager.getBDDofVariableName(var).id().not());
				}
			}
			ctrlManager.setNoChange();
			return ctrlManager.solution.satCount();
			
		}
		else return -1;
	}

	/* (non-Javadoc)
	 * @see featureMdl.Configurator#setRequestedSolCount(int)
	 */
	public void setRequestedSolCount(int solCount) {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see featureMdl.Configurator#getSolutions()
	 */
	public int[] getSolutions() {
		// TO BE REIMPLEMENTED!!!!!
		return null;
		//// TO BE IMPLEMENTED!!!!!
	}

	/* (non-Javadoc)
	 * @see featureMdl.Configurator#getSelectedValue(int)
	 */
	public int getSelectedValue(int variable) {
		return 1; // TO BE IMPLEMENTED;
	}

/*	 (non-Javadoc)
	 * @see featureMdl.Configurator#applySelection(com.configit_software.ctrlmngr.CS_VarSelection)
	 
	public void applySelection(CS_VarSelection selection) {
	 // TO BE IMPLEMENTED;
	}

	 (non-Javadoc)
	 * @see featureMdl.Configurator#applySelections(com.configit_software.ctrlmngr.CS_VarSelection[])
	 
	public void applySelections(CS_VarSelection[] selection_array) {
		// TO BE IMPLEMENTED;
	}

	 (non-Javadoc)
	 * @see featureMdl.Configurator#getSelection(int)
	 
	public CS_VarSelection getSelection(int variable) {		
		return null;// TO BE IMPLEMENTED;
	}

	 (non-Javadoc)
	 * @see featureMdl.Configurator#getSelections()
	 
	public CS_VarSelection[] getSelections() {
		return null; // TO BE IMPLEMENTED;
	}
*/
	/* (non-Javadoc)
	 * @see featureMdl.Configurator#removeSelection(java.lang.String, boolean, boolean, boolean)
	 */
	public void removeSelection(String variable, boolean values, boolean min, boolean max) {
		// TO BE IMPLEMENTED;
//		controlManager.removeVariableAssignment(variable);
		controlManager.removeValueAssignment(variable);
		controlManager.setChanged();
		getSolutionCount();
	
	}

	/* (non-Javadoc)
	 * @see featureMdl.Configurator#removeSelection(int, boolean, boolean, boolean)
	 */
	public void removeSelection(int variable, boolean values, boolean min, boolean max) {
		// TO BE IMPLEMENTED;
//		controlManager.removeVariableAssignment(controlManager.getVariableNameByNumber(variable));
		controlManager.removeValueAssignment(controlManager.getVariableNameByNumber(variable));
		controlManager.setChanged();
		getSolutionCount();
	}

	/* (non-Javadoc)
	 * @see featureMdl.Configurator#getConflicts()
	 */
	public Conflicts getConflicts(String name) {
		// TODO Auto-generated method stub
		if (controlManager != null) {

			BDD solution;
			if (controlManager.isValueAssigned(name)) {
				// set the value for variable "name"
				if (controlManager.getValueAssignment(name) == true) 
					solution= controlManager.problem.id().andWith(controlManager.getBDDofVariableName(name).id());
				else
					solution= controlManager.problem.id().andWith(controlManager.getBDDofVariableName(name).id().not());
			}
			else
				solution = controlManager.problem.id();
				
			// test if it conflicts with other variables 
			int j = 0;
			int[] conflictingSelections = new int[controlManager.getActVarCount()+1];
			for (int z = 0; z <= controlManager.getActVarCount(); z++) conflictingSelections[z] = -1; 

			for (int i=0; i <= controlManager.getActVarCount(); i++){
				BDD temporal = solution.id();
				String var = controlManager.getVariableNameByNumber(i);

				if (!var.equals(name) && controlManager.isValueAssigned(var) ) {

					if (controlManager.getValueAssignment(var) == true) {
						if (temporal.andWith(controlManager.getBDDofVariableName(var).id()).satCount() > 0)
					      solution.andWith(controlManager.getBDDofVariableName(var).id());
						else
						  conflictingSelections[j++]=i;
					}

					if (controlManager.getValueAssignment(var) == false) {
						if (temporal.andWith(controlManager.getBDDofVariableName(var).id().not()).satCount() > 0)
 					      solution.andWith(controlManager.getBDDofVariableName(var).id().not());
						else
						  conflictingSelections[j++]=i;
					}
				}
			}

			// if there are conflicts, create Conflicts object
				if (j > 0) {
					Conflicts conflicts = new Conflicts();
					conflicts.m_conflicting_selections = new int[j];
					conflicts.m_forced_selection = controlManager.getNumberByVariableName(name); 
					for (int z = 0; z < conflictingSelections.length && conflictingSelections[z] > 0; z++){
						conflicts.m_conflicting_selections[z] = conflictingSelections[z];
					}
					
					return conflicts;
				}
				else return null;
			
		}
        return null;
	}

	/* (non-Javadoc)
	 * @see featureMdl.Configurator#getPMNodeCount()
	 */
	public int getPMNodeCount() {
		// TODO Auto-generated method stub
		return 	1; // TO BE IMPLEMENTED;

	}

	/* (non-Javadoc)
	 * @see featureMdl.Configurator#getPMNodeChildren(int)
	 */
	public int[] getPMNodeChildren(int node) {
		return new int[1]; // TO BE IMPLEMENTED;
	}

	/* (non-Javadoc)
	 * @see featureMdl.Configurator#getPMNodeParent(int)
	 */
	public int getPMNodeParent(int node) {

		return 1; // TO BE IMPLEMENTED;
	}

	/* (non-Javadoc)
	 * @see featureMdl.Configurator#getPMNodeName(int)
	 */
	public String getPMNodeName(int node) {
		return null; // TO BE IMPLEMENTED;
	}

	/* (non-Javadoc)
	 * @see featureMdl.Configurator#getPMNodeIndex(int)
	 */
	public int getPMNodeIndex(int node) {
		return 1; // TO BE IMPLEMENTED;
	}

	/* (non-Javadoc)
	 * @see featureMdl.Configurator#getPMNodeFromVariable(int)
	 */
	public int getPMNodeFromVariable(int variable) {
		return 1; // TO BE IMPLEMENTED;
	}

	/* (non-Javadoc)
	 * @see featureMdl.Configurator#getPMNodeFromPath(java.lang.String)
	 */
	public int getPMNodeFromPath(String path) {
		return 1; // TO BE IMPLEMENTED;
	}

	/* (non-Javadoc)
	 * @see fmp.constraints.Configurator#getNode(int)
	 */
	public Node getNode(int pmNode) {
		return getNode(getVarName(pmNode));
	}

	/* (non-Javadoc)
	 * @see fmp.constraints.Configurator#getNode(java.lang.String)
	 */
	public Node getNode(String pmNode) {
		Dictionary rootFeatureDictionary = NodeIdDictionary.INSTANCE.getRootDictionary(feature);
		return (Node)rootFeatureDictionary.get(pmNode);
	}

	/* (non-Javadoc)
	 * @see fmp.constraints.Configurator#getPMNodeInt(fmp.Node)
	 */
	public int getPMNodeInt(Node node) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see fmp.constraints.Configurator#getPMNodeStr(fmp.Node)
	 */
	public String getPMNodeStr(Node node) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* (non-Javadoc)
	 * @see fmp.constraints.Configurator#evaluateBoolean(fmp.constraints.PropositionalFormula)
	 */
	public boolean evaluateBoolean(PropositionalFormula additionalFormula, String exprId) {

		// TO BE REIMPLEMENTED!!!!!
		boolean returnValue = false;
		
		PropositionalFormula combinedFormula = new PropositionalFormula();
		List newVariableList = new Vector();
		newVariableList.addAll(propositionalFormula.getVariables());
		newVariableList.add(exprId);
		combinedFormula.setVariables(newVariableList);
		combinedFormula.setRules(propositionalFormula.getRules().replaceAll(";"," and ") + " and (" +additionalFormula.getRules().replaceAll(";"," and ")+")");

		// the templateControlManager should have its entries set to the states of every node in the configuration 
		BDDManager templateControlManager = createControlManager(this.feature, combinedFormula, true);		
			
		if(getSolutionCount(templateControlManager) > 0 && getMin(exprId,templateControlManager) == 1 && getMax(exprId,templateControlManager) == 1)
			returnValue = true;
		else
			returnValue = false;
		
		return returnValue;

	}
	
	/**
	 * Assigns values to the specified control manager from the state of each node of the tree
	 * specified by "node"
	 * @param node
	 * @param ctrlMngr
	 */
	protected void assignValuesToCtrlMngr(Node node, BDDManager ctrlMngr, boolean treatUndecidedAsEliminated)
	{
		if(node instanceof Clonable && ((Clonable)node).isOptional())
		{
			ConfigState configState = ((Clonable)node).getState();
			if(configState == ConfigState.USER_SELECTED_LITERAL) { //  || configState == ConfigState.MACHINE_SELECTED_LITERAL)
				assignValue(node.getId(), 1, true, ctrlMngr);
			}
			else if(configState == ConfigState.USER_ELIMINATED_LITERAL || (treatUndecidedAsEliminated && configState == ConfigState.UNDECIDED_LITERAL)) // || configState == ConfigState.MACHINE_ELIMINATED_LITERAL)
				assignValue(node.getId(), 0, true, ctrlMngr);
		}
		for(Iterator childrenIterator = node.getChildren().iterator(); childrenIterator.hasNext();)
			assignValuesToCtrlMngr((Node)childrenIterator.next(), ctrlMngr, treatUndecidedAsEliminated);
	}

	/* (non-Javadoc)
	 * @see fmp.constraints.Configurator#evaluateString(fmp.constraints.PropositionalFormula)
	 */
	public String evaluateString(PropositionalFormula constraint, String exprId) {
		// TODO Auto-generated method stub
		return null;
	}
}
