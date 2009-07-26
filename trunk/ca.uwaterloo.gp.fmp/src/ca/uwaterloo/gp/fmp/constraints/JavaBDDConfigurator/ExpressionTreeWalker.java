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

// $ANTLR 2.7.5 (20050201): "t5.g" -> "ExpressionTreeWalker.java"$

package ca.uwaterloo.gp.fmp.constraints.JavaBDDConfigurator;
import net.sf.javabdd.BDD;
import antlr.NoViableAltException;
import antlr.RecognitionException;
import antlr.collections.AST;

public class ExpressionTreeWalker extends antlr.TreeParser       implements BooleanExpressionParserTokenTypes
 {
	BDDManager manager;
    public ExpressionTreeWalker(BDDManager manager) {
	    tokenNames = _tokenNames;
	    this.manager = manager;
    }


	public final BDD  expression(AST _t) throws RecognitionException {
		BDD value=null;
		
		BDD x,y; String t=null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case IMPLIES:
			{
				AST __t29 = _t;
				AST tmp1_AST_in = _t;
				match(_t,IMPLIES);
				_t = _t.getFirstChild();
				y=expression(_t);
				_t = _retTree;
				x=expression(_t);
				_t = _retTree;
				_t = __t29;
				_t = _t.getNextSibling();
				value = y.imp(x);
				break;
			}
			case AND:
			{
				AST __t30 = _t;
				AST tmp2_AST_in = _t;
				match(_t,AND);
				_t = _t.getFirstChild();
				y=expression(_t);
				_t = _retTree;
				x=expression(_t);
				_t = _retTree;
				_t = __t30;
				_t = _t.getNextSibling();
				value = y.and(x);
				break;
			}
			case OR:
			{
				AST __t31 = _t;
				AST tmp3_AST_in = _t;
				match(_t,OR);
				_t = _t.getFirstChild();
				y=expression(_t);
				_t = _retTree;
				x=expression(_t);
				_t = _retTree;
				_t = __t31;
				_t = _t.getNextSibling();
				value = y.or(x);
				break;
			}
			case NOT:
			{
				AST __t32 = _t;
				AST tmp4_AST_in = _t;
				match(_t,NOT);
				_t = _t.getFirstChild();
				x=expression(_t);
				_t = _retTree;
				_t = __t32;
				_t = _t.getNextSibling();
				value = x.id().not();
				break;
			}
			case EQUALS:
			{
				AST __t33 = _t;
				AST tmp5_AST_in = _t;
				match(_t,EQUALS);
				_t = _t.getFirstChild();
				x=variableReference(_t);
				_t = _retTree;
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case TRUE:
				{
					AST tmp6_AST_in = _t;
					match(_t,TRUE);
					_t = _t.getNextSibling();
					t="true";
					break;
				}
				case FALSE:
				{
					AST tmp7_AST_in = _t;
					match(_t,FALSE);
					_t = _t.getNextSibling();
					t="false";
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				_t = __t33;
				_t = _t.getNextSibling();
				
				
					    	        if (t.equals("true")) {
				
					    	    	        value = x.and(manager.factory.one()); 
				
					    	        } else value = x.id().not().or(manager.factory.zero()); 
				
					
				break;
			}
			case LPAREN:
			{
				AST __t35 = _t;
				AST tmp8_AST_in = _t;
				match(_t,LPAREN);
				_t = _t.getFirstChild();
				x=expression(_t);
				_t = _retTree;
				_t = __t35;
				_t = _t.getNextSibling();
				value = x;
				break;
			}
			case IDENT:
			{
				y=variableReference(_t);
				_t = _retTree;
				value=y;
				break;
			}
			case TRUE:
			{
				AST tmp9_AST_in = _t;
				match(_t,TRUE);
				_t = _t.getNextSibling();
				value=manager.factory.one();
				break;
			}
			case FALSE:
			{
				AST tmp10_AST_in = _t;
				match(_t,FALSE);
				_t = _t.getNextSibling();
				value=manager.factory.zero();
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return value;
	}
	
	public final BDD  variableReference(AST _t) throws RecognitionException {
		BDD bdd=null;
		
		AST variableReference_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST j = null;
		BDD x;
		
		try {      // for error handling
			j = _t;
			match(_t,IDENT);
			_t = _t.getNextSibling();
			
			
									String identi=j.getText();
			
												if (identi.equals("true")) {
													bdd = manager.factory.one();
												}
												else if (identi.equals("false")) {
													bdd = manager.factory.zero();
												}
												else if (manager.variableNameSeenBefore(identi)){
												 bdd = manager.getBDDofVariableName(identi);
												}
												else {
//													 System.out.print("\n"+identi);
													 bdd = manager.factory.ithVar(manager.increaseActVarCount());
													 manager.vars = manager.vars.and(bdd);
													 manager.addVatiableName2NumberMapping(identi,new Integer(bdd.var()));
													 manager.addNumber2VariableNameMapping(new Integer(bdd.var()), identi);
												}  
			
				
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return bdd;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"\"and\"",
		"\"or\"",
		"\"not\"",
		"\"true\"",
		"\"false\"",
		"\"for all\"",
		"\"exists\"",
		"EQUALS",
		"IMPLIES",
		"LPAREN",
		"RPAREN",
		"IDENT",
		"WS"
	};
	
	}
	