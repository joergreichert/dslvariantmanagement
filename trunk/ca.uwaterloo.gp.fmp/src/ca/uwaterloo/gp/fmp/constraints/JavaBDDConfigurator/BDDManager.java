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
import java.util.Hashtable;

import net.sf.javabdd.BDD;
import net.sf.javabdd.BDDFactory;

/**
 * @author Krzysztof Pietroszek <kmpietro@swen.uwaterloo.ca>
 */
public class BDDManager {

    public BDDFactory factory;
    public BDDManager manager;
    private boolean changed=true;
    public BDD vars; /* BDD representation of variables */
    public BDD solution; /* BDD represention one solution for problem boolean formula */
    public BDD problem; /* BDD represention boolean formula */
	public Hashtable variableName2Number = new Hashtable();
	public Hashtable number2VariableName = new Hashtable();
	public Hashtable valueAssignments = new Hashtable();
	public Hashtable minofVariable = new Hashtable();
	public Hashtable maxofVariable = new Hashtable();
	int numberOfNodes = 0;
	
	private BDDManager(){};
	public Object clone() {
		BDDManager manager = new BDDManager();
		manager.vars = vars.id();
		manager.solution = solution.id();
		manager.problem = problem.id();
		manager.factory = factory;
		manager.problem = problem.id();
		manager.solution = null;
		manager.number2VariableName = number2VariableName;
		manager.valueAssignments = valueAssignments;
		manager.minofVariable = minofVariable;
		manager.maxofVariable = maxofVariable;
		manager.numberOfNodes = numberOfNodes;
		return manager;
	}
    
    private int actVarCount = -1;
    
	public void setActVarCount(int actVarCount) {
		this.actVarCount = actVarCount;
	}
    public BDDManager(int nofN, int cs) {
        int numberOfNodes = nofN;
        int cacheSize = cs;
        factory = BDDFactory.init("j",numberOfNodes, cacheSize);
        factory.setVarNum(numberOfNodes);
        vars = factory.one();
   }
    
	public BDD getBDDofVariableName(String identi) {
		int n = getNumberByVariableName(identi);
		for (int i=0; i < factory.varNum();i++){
			if (factory.ithVar(i).var() == n){
				return factory.ithVar(i);
			}
		}
		return null;
	}

	public boolean variableNameSeenBefore(String identi) {
		return variableName2Number.containsKey(identi);
	}

	public String getVariableNameOfBDD(BDD bdd) {
		return getVariableNameByNumber(bdd.var());
	}

	
	public int getActVarCount() {
		return actVarCount;
	}

	public int increaseActVarCount(){
		actVarCount++;
		return actVarCount;
	}

	public void setSolution(BDD solution) {
		// TODO Auto-generated method stub
		this.solution=solution;
	}
	public BDD getSolution(){
		return solution;
	}

	public void setProblem(BDD problem) {
		this.problem=problem;
	}
	public BDD getProblem(BDD problem){
		return problem;
	}
	public int getNumberByVariableName(String variableName) {
		Integer i = (Integer) variableName2Number.get(variableName);
		return i.intValue();
	}

	public void addVatiableName2NumberMapping(String identi, Integer integer) {
		variableName2Number.put(identi,integer);
	}

	public String getVariableNameByNumber(int integer) {
		return (String) number2VariableName.get(new Integer(integer));
	}
	
	public void addNumber2VariableNameMapping(Integer integer, String identi) {
		number2VariableName.put(integer, identi);
	}
	public boolean isValueAssigned(String variable) {
		// TODO Auto-generated method stub
		return valueAssignments.containsKey(variable);
	}
	public void storeValue(String variable, boolean assignedValue) {
		// TODO Auto-generated method stub
		valueAssignments.put(variable,new Boolean(assignedValue));
	}
	public boolean getValueAssignment(String variable) {
		// TODO Auto-generated method stub
		return ((Boolean) valueAssignments.get(variable)).booleanValue();
	}
	public void removeValueAssignment(String variable) {
		if (valueAssignments.containsKey(variable)) valueAssignments.remove(variable);
	}
	public boolean hasChanged() {
		// TODO Auto-generated method stub
		return changed;
	}
	public void setNoChange(){
		changed = false;
	}
	public void setChanged(){
		changed = true;
	}
	
//	public void setMin(String var, int value) {
//		minofVariable.put(var,new Integer(value));
//	}
//
//	public void setMax(String var, int value) {
//		maxofVariable.put(var,new Integer(value));
//	}
//
//	public int getMin(String var) {
//		return ((Integer) minofVariable.get(var)).intValue();
//	}
//
//	public int getMax(String var) {
//		return ((Integer) maxofVariable.get(var)).intValue();
//	}
	
}