package ca.uwaterloo.gp.fmp.constraints;
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

/*
 * Created on Sep 28, 2005
 
package fmp.constraints;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.configit_software.ctrlmngr.CS_Conflicts;
import com.configit_software.ctrlmngr.CS_CtrlMngr;
import com.configit_software.ctrlmngr.CS_VarSelection;

import fmp.Clonable;
import fmp.ConfigState;
import fmp.Feature;
import fmp.Node;
import fmp.constraints.xpath.ConstraintToPropositionTranslator;
import fmp.system.NodeIdDictionary;

*//**
 * @author Chang Hwan Peter Kim <chpkim@swen.uwaterloo.ca>
 *//*
public class ConfigitConfigurator implements Configurator
{
	protected int mode;
	protected Feature feature;
	protected CS_CtrlMngr controlManager;
	protected String fileNamePrefix = null;
	protected PropositionalFormula propositionalFormula = null;
	
	public ConfigitConfigurator()
	{
		mode = Configurator.EDIT_MODE_AUTORESOLVE_CONFLICTS; 
	}

	 (non-Javadoc)
	 * @see fmp.constraints.Configurator#getSolutions(java.lang.Object, fmp.constraints.PropositionalFormula, long, int)
	 
	public Result getSolutions(Object obj, PropositionalFormula additionalFormula, List variableList, long timeLimitInMillis, int requestedNumOfSolutions) 
	{
		setFeature((Feature) obj, additionalFormula);

		Result result = new Result();
		List variableAssignmentsList = new ArrayList();

		if (controlManager != null) {
			controlManager.setRequestedSolCount(requestedNumOfSolutions);
			if (controlManager.getSolutionCount() > 0) {
				variableAssignmentsList.add(new Integer((int) controlManager
						.getSolutionCount()));
				int[] solutions = controlManager.getSolutions();
				List variableAssignments = new ArrayList();
				// for now, I need only the first solution
				for (int i = 0; i < controlManager.getVarCount(); i++) {
					if (variableList.contains(controlManager.getVarName(i))) {
						VariableAssignment varAssign = new VariableAssignment();
						varAssign.value = solutions[i + 1] == 1 ? true : false;
						varAssign.variable = controlManager.getVarName(i);
						variableAssignments.add(varAssign);
					}
				}
				variableAssignmentsList.add(variableAssignments);
				result.setMessage("There exists an invalid instance of model");
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
	

	*//**
	 * Create a control manager whose pm and vt filename will have
	 * "aFileNamePrefix" and whose values will be those assigned from the
	 * feature diagram specified by "rootFeature"
	 * 
	 * @param rootFeature
	 * @param aFileNamePrefix
	 * @return
	 *//*
	protected CS_CtrlMngr createControlManager(Node rootFeature, String aFileNamePrefix, boolean treatUndecidedAsEliminated)
	{
		CS_CtrlMngr aControlManager = new CS_CtrlMngr(aFileNamePrefix + ".vt", CS_CtrlMngr.CONFIG_STATIC);
		
		// delete the pm and the vt files
		new File(aFileNamePrefix + ".pm");
		//pmFile.delete();
		
		File vtFile = new File(aFileNamePrefix + ".vt");
		vtFile.delete();
		
		// assign values to the control manager by traversing the rootFeature
		if(rootFeature != null) {
			this.assignValuesToCtrlMngr(rootFeature, aControlManager, treatUndecidedAsEliminated);
		}
		
		return aControlManager;
	}
	
	*//**
	 * @param feature2
	 * @param additionalFormula
	 *//*
	public Result setFeature(Feature f) {
		return setFeature(f,null);
	}
	*//**
	 * @param rootFeature The rootFeature to set.
	 *//*
	public Result setFeature(Feature feature, PropositionalFormula additionalFormula) {
		this.feature = feature;
		fileNamePrefix = null;
		
		// Generate a boolean expression from the given model
		ModelToPropositionTranslator propositionTranslator = new ModelToPropositionTranslator();			
		propositionalFormula = propositionTranslator.translate(feature, false);
		propositionalFormula.setRules(propositionalFormula.getRules() + (new ConstraintToPropositionTranslator((Feature)feature.getOrigin())).translate());
		if (additionalFormula != null) {
			propositionalFormula.setRules(propositionalFormula.getRules()+additionalFormula.getRules());
		}
		
		// make PM file
		makePMFile(getPMFileName(), propositionalFormula.getText());
		
		// make VT file (by compiling PM file)
		makeVTFile(getPMFileName());
		
		// create a Control Manager whose state reflects the VT file
		controlManager = createControlManager(this.feature, getFileNamePrefix(), false);
		
		return null;
	}

	
	*//**
	 * @param mode The mode to set.
	 *//*
	public void setMode(int mode) {
		this.mode = mode;
	}
	
	*//**
	 * Returns the file name prefix shared by the compiled file (Virtual Table)
	 * and product model (PM) file.
	 * @return
	 *//*
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
			} else {
				guid = (new UID()).toString();
			}
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
	
	protected void makePMFile(String pmFileName, String formula)
	{
		try
		{
			// Write the expression generated into a file
			FileOutputStream pmStream = new FileOutputStream(pmFileName);
			pmStream.write(formula.getBytes());
			pmStream.close();
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}		
	}
	
	*//**
	 * Compiles the model represented by this class. 
	 * @return True if successful.  False otherwise
	 *//*
	protected void makeVTFile(String pmFileName)
	{
		try {
			// Compile the model into a virtual table (VT) and wait until the VT file is actually there
			Process process = Runtime.getRuntime().exec(new String[] {"pmc", pmFileName});
			process.waitFor();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	 (non-Javadoc)
	 * @see featureMdl.Configurator#getMode()
	 
	public int getMode() {		
		return mode;
	}

	 (non-Javadoc)
	 * @see featureMdl.Configurator#getVarCount()
	 
	public int getVarCount() {
		return controlManager.getVarCount();
	}

	 (non-Javadoc)
	 * @see featureMdl.Configurator#getVarName(int)
	 
	public String getVarName(int variable) {
		return controlManager.getVarName(variable);
	}

	 (non-Javadoc)
	 * @see featureMdl.Configurator#getVarIndex(java.lang.String)
	 
	public int getVarIndex(String variable_name) {
		return controlManager.getVarIndex(variable_name);
	}

	 (non-Javadoc)
	 * @see featureMdl.Configurator#getMin(int)
	 
	public int getMin(int variable) {
		return controlManager.getMin(variable);
	}

	 (non-Javadoc)
	 * @see featureMdl.Configurator#getMin(java.lang.String)
	 
	public int getMin(String variable) {
		return controlManager.getMin(variable);
	}

	 (non-Javadoc)
	 * @see featureMdl.Configurator#getMax(int)
	 
	public int getMax(int variable) {
		return controlManager.getMax(variable);
	}

	 (non-Javadoc)
	 * @see featureMdl.Configurator#getMax(java.lang.String)
	 
	public int getMax(String variable) {
		return controlManager.getMax(variable);
	}

	 (non-Javadoc)
	 * @see featureMdl.Configurator#getValueName(int, int)
	 
	public String getValueName(int variable, int value) {
		return controlManager.getValueName(variable, value);
	}

	 (non-Javadoc)
	 * @see featureMdl.Configurator#assignValue(java.lang.String, int, boolean)
	 
	public void assignValue(String variable, int value, boolean force) {
		controlManager.assignValue(variable, value, force);
	}

	 (non-Javadoc)
	 * @see featureMdl.Configurator#assignValue(int, int, boolean)
	 
	public void assignValue(int variable, int value, boolean force) {		
		controlManager.assignValue(variable, value, force);	
	}

	 (non-Javadoc)
	 * @see featureMdl.Configurator#getSolutionCount()
	 
	public double getSolutionCount() {
		// TODO Auto-generated method stub
		return controlManager.getSolutionCount();
	}

	 (non-Javadoc)
	 * @see featureMdl.Configurator#setRequestedSolCount(int)
	 
	public void setRequestedSolCount(int solCount) {
		// TODO Auto-generated method stub
		controlManager.setRequestedSolCount(solCount);
	}

	 (non-Javadoc)
	 * @see featureMdl.Configurator#getSolutions()
	 
	public int[] getSolutions() {
		return controlManager.getSolutions();
	}

	 (non-Javadoc)
	 * @see featureMdl.Configurator#getSelectedValue(int)
	 
	public int getSelectedValue(int variable) {
		return controlManager.getSelectedValue(variable);
	}

	 (non-Javadoc)
	 * @see featureMdl.Configurator#applySelection(com.configit_software.ctrlmngr.CS_VarSelection)
	 
	public void applySelection(CS_VarSelection selection) {
		controlManager.applySelection(selection);
	}

	 (non-Javadoc)
	 * @see featureMdl.Configurator#applySelections(com.configit_software.ctrlmngr.CS_VarSelection[])
	 
	public void applySelections(CS_VarSelection[] selection_array) {
		controlManager.applySelections(selection_array);
	}

	 (non-Javadoc)
	 * @see featureMdl.Configurator#getSelection(int)
	 
	public CS_VarSelection getSelection(int variable) {		
		return controlManager.getSelection(variable);
	}

	 (non-Javadoc)
	 * @see featureMdl.Configurator#getSelections()
	 
	public CS_VarSelection[] getSelections() {
		return controlManager.getSelections();
	}

	 (non-Javadoc)
	 * @see featureMdl.Configurator#removeSelection(java.lang.String, boolean, boolean, boolean)
	 
	public void removeSelection(String variable, boolean values, boolean min, boolean max) {
		controlManager.removeSelection(variable, values, min, max);
	}

	 (non-Javadoc)
	 * @see featureMdl.Configurator#removeSelection(int, boolean, boolean, boolean)
	 
	public void removeSelection(int variable, boolean values, boolean min, boolean max) {
		controlManager.removeSelection(variable, values, min, max);
	}

	 (non-Javadoc)
	 * @see featureMdl.Configurator#getConflicts()
	 
	public Conflicts getConflicts(String name) {
		// TODO Auto-generated method stub
		CS_Conflicts confl = controlManager.getConflicts();
		if (confl != null) {
		Conflicts conflicts = new Conflicts();
		conflicts.m_forced_selection = confl.m_forced_selection.m_variable;
		conflicts.m_conflicting_selections = new int[confl.m_conflicting_selections.length];
		for (int i = 0; i < confl.m_conflicting_selections.length; i++) {
			conflicts.m_conflicting_selections[i] = confl.m_conflicting_selections[i].m_variable;
		}
		return conflicts;
		} else {
			return null;
		}
	}

	 (non-Javadoc)
	 * @see featureMdl.Configurator#getPMNodeCount()
	 
	public int getPMNodeCount() {
		// TODO Auto-generated method stub
		return controlManager.getPMNodeCount();
	}

	 (non-Javadoc)
	 * @see featureMdl.Configurator#getPMNodeChildren(int)
	 
	public int[] getPMNodeChildren(int node) {
		return controlManager.getPMNodeChildren(node);
	}

	 (non-Javadoc)
	 * @see featureMdl.Configurator#getPMNodeParent(int)
	 
	public int getPMNodeParent(int node) {

		return controlManager.getPMNodeParent(node);
	}

	 (non-Javadoc)
	 * @see featureMdl.Configurator#getPMNodeName(int)
	 
	public String getPMNodeName(int node) {
		return controlManager.getPMNodeName(node);
	}

	 (non-Javadoc)
	 * @see featureMdl.Configurator#getPMNodeIndex(int)
	 
	public int getPMNodeIndex(int node) {
		return controlManager.getPMNodeIndex(node);
	}

	 (non-Javadoc)
	 * @see featureMdl.Configurator#getPMNodeFromVariable(int)
	 
	public int getPMNodeFromVariable(int variable) {
		return controlManager.getPMNodeFromVariable(variable);
	}

	 (non-Javadoc)
	 * @see featureMdl.Configurator#getPMNodeFromPath(java.lang.String)
	 
	public int getPMNodeFromPath(String path) {
		return controlManager.getPMNodeFromPath(path);
	}

	 (non-Javadoc)
	 * @see fmp.constraints.Configurator#getNode(int)
	 
	public Node getNode(int pmNode) {
		
		return getNode(getVarName(pmNode));
	}

	 (non-Javadoc)
	 * @see fmp.constraints.Configurator#getNode(java.lang.String)
	 
	public Node getNode(String pmNode) {
		Dictionary rootFeatureDictionary = NodeIdDictionary.INSTANCE.getRootDictionary(feature);
		return (Node)rootFeatureDictionary.get(pmNode);
	}

	 (non-Javadoc)
	 * @see fmp.constraints.Configurator#getPMNodeInt(fmp.Node)
	 
	public int getPMNodeInt(Node node) {
		// TODO Auto-generated method stub
		return 0;
	}

	 (non-Javadoc)
	 * @see fmp.constraints.Configurator#getPMNodeStr(fmp.Node)
	 
	public String getPMNodeStr(Node node) {
		// TODO Auto-generated method stub
		return null;
	}
	
	 (non-Javadoc)
	 * @see fmp.constraints.Configurator#evaluateBoolean(fmp.constraints.PropositionalFormula)
	 
	public boolean evaluateBoolean(PropositionalFormula additionalFormula, String exprId) {
		boolean returnValue = false;
		
		// make PM file
		PropositionalFormula combinedFormula = new PropositionalFormula();
		List newVariableList = new Vector();
		newVariableList.addAll(propositionalFormula.getVariables());
		newVariableList.add(exprId);
		combinedFormula.setVariables(newVariableList);
		combinedFormula.setRules(propositionalFormula.getRules() + additionalFormula.getRules());
		
		String filePrefix = replaceIllegalCharactersInFileName(exprId + "@" + (new UID()).toString());
		
		makePMFile(filePrefix + ".pm", combinedFormula.getText());
		
		// make VT file (by compiling PM file)
		makeVTFile(filePrefix + ".pm");
		
		// the templateControlManager should have its entries set to the states of every node in the configuration 
		CS_CtrlMngr templateControlManager = createControlManager(this.feature, filePrefix, true);		
			
		if((templateControlManager.getMin(exprId) == 1) && (templateControlManager.getMax(exprId) == 1)) {
			returnValue = true;
		} else {
			returnValue = false;
		}
		
		return returnValue;
	}
	
	*//**
	 * Assigns values to the specified control manager from the state of each node of the tree
	 * specified by "node"
	 * @param node
	 * @param ctrlMngr
	 *//*
	protected void assignValuesToCtrlMngr(Node node, CS_CtrlMngr ctrlMngr, boolean treatUndecidedAsEliminated)
	{
		if((node instanceof Clonable) && ((Clonable)node).isOptional())
		{
			ConfigState configState = ((Clonable)node).getState();
			if(configState == ConfigState.USER_SELECTED_LITERAL) {
				ctrlMngr.assignValue(node.getId(), 1, true);
			} else if((configState == ConfigState.USER_ELIMINATED_LITERAL) || (treatUndecidedAsEliminated && (configState == ConfigState.UNDECIDED_LITERAL))) {
				ctrlMngr.assignValue(node.getId(), 0, true);
			}
		}
		for(Iterator childrenIterator = node.getChildren().iterator(); childrenIterator.hasNext();) {
			assignValuesToCtrlMngr((Node)childrenIterator.next(), ctrlMngr, treatUndecidedAsEliminated);
		}
	}

	 (non-Javadoc)
	 * @see fmp.constraints.Configurator#evaluateString(fmp.constraints.PropositionalFormula)
	 
	public String evaluateString(PropositionalFormula constraint, String exprId) {
		// TODO Auto-generated method stub
		return null;
	}
}
*/