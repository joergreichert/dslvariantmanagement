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

import Data.EvaluatorParallel;

/**
 * @author Chang Hwan Peter Kim <chpkim@swen.uwaterloo.ca>
 */

public class Evaluator extends EvaluatorParallel {		
    public static String getBool(String func){
    	if(func.equals("true()"))
    		return "true";
    	else if(func.equals("false()"))
    		return "false";
    	else 
    		return func;
    }

    protected void insertAttributes() {
        insertAttribute(this.tree,"XPath","boolExpr");
        insertAttribute(this.tree,"XPath","needParenth");
        insertAttribute(this.tree,"ExprSequence","boolExpr");
        insertAttribute(this.tree,"ExprSequence","needParenth");
        insertAttribute(this.tree,"Expr","boolExpr");
        insertAttribute(this.tree,"Expr","needParenth");
        insertAttribute(this.tree,"OrExpr","boolExpr");
        insertAttribute(this.tree,"OrExpr","needParenth");
        insertAttribute(this.tree,"AndExpr","boolExpr");
        insertAttribute(this.tree,"AndExpr","needParenth");
        insertAttribute(this.tree,"ForExpr","boolExpr");
        insertAttribute(this.tree,"ForExpr","needParenth");
        insertAttribute(this.tree,"QuantifiedExpr","boolExpr");
        insertAttribute(this.tree,"QuantifiedExpr","needParenth");
        insertAttribute(this.tree,"IfExpr","boolExpr");
        insertAttribute(this.tree,"IfExpr","needParenth");
        insertAttribute(this.tree,"InstanceOfExpr","boolExpr");
        insertAttribute(this.tree,"InstanceOfExpr","needParenth");
        insertAttribute(this.tree,"CastableExpr","boolExpr");
        insertAttribute(this.tree,"CastableExpr","needParenth");
        insertAttribute(this.tree,"ComparisonExpr","boolExpr");
        insertAttribute(this.tree,"ComparisonExpr","needParenth");
        insertAttribute(this.tree,"RangeExpr","boolExpr");
        insertAttribute(this.tree,"RangeExpr","needParenth");
        insertAttribute(this.tree,"AdditiveExpr","boolExpr");
        insertAttribute(this.tree,"AdditiveExpr","needParenth");
        insertAttribute(this.tree,"MultiplicativeExpr","boolExpr");
        insertAttribute(this.tree,"MultiplicativeExpr","needParenth");
        insertAttribute(this.tree,"UnaryExpr","boolExpr");
        insertAttribute(this.tree,"UnaryExpr","needParenth");
        insertAttribute(this.tree,"UnionExpr","boolExpr");
        insertAttribute(this.tree,"UnionExpr","needParenth");
        insertAttribute(this.tree,"IntersectExceptExpr","boolExpr");
        insertAttribute(this.tree,"IntersectExceptExpr","needParenth");
        insertAttribute(this.tree,"ValueExpr","boolExpr");
        insertAttribute(this.tree,"ValueExpr","needParenth");
        insertAttribute(this.tree,"PathExpr","boolExpr");
        insertAttribute(this.tree,"PathExpr","needParenth");
        insertAttribute(this.tree,"RelativePathExpr","boolExpr");
        insertAttribute(this.tree,"RelativePathExpr","needParenth");
        insertAttribute(this.tree,"StepExpr","boolExpr");
        insertAttribute(this.tree,"StepExpr","needParenth");
        insertAttribute(this.tree,"PrimaryExpr","boolExpr");
        insertAttribute(this.tree,"PrimaryExpr","needParenth");
        insertAttribute(this.tree,"Literal","boolExpr");
        insertAttribute(this.tree,"Literal","needParenth");
        insertAttribute(this.tree,"FunctionCall","boolExpr");
        insertAttribute(this.tree,"FunctionCall","needParenth");
        insertAttribute(this.tree,"ParenthesizeExpr","boolExpr");
        insertAttribute(this.tree,"ParenthesizeExpr","needParenth");
        insertAttribute(this.tree,"NumericLiteral","boolExpr");
        insertAttribute(this.tree,"NumericLiteral","needParenth");
        insertAttribute(this.tree,"StringLiteral","boolExpr");
        insertAttribute(this.tree,"StringLiteral","needParenth");
        insertAttribute(this.tree,"IntegerLiteral","boolExpr");
        insertAttribute(this.tree,"IntegerLiteral","needParenth");
    }

    protected void addAttributes() {
        addAttribute("boolExpr","0");
        addAttribute("needParenth","0");
    }

    protected boolean checkIfPossible(String attr) {
        if (activeRule.equals("XPath ExprSequence")) {
            if (attr.equals("XPath.boolExpr")) {
                return (check("ExprSequence.boolExpr"));
            }
            else if (attr.equals("XPath.needParenth")) {
                return true;
            }
            else return false;
        }
        else if (activeRule.equals("ExprSequence Expr terminatingCharacter")) {
            if (attr.equals("ExprSequence.boolExpr")) {
                return (check("Expr.boolExpr"));
            }
            else if (attr.equals("ExprSequence.needParenth")) {
                return true;
            }
            else return false;
        }
        else if (activeRule.equals("Expr ParenthesizeExpr1 equivalence ParenthesizeExpr2")) {
            if (attr.equals("Expr.boolExpr")) {
                return (check("ParenthesizeExpr1.boolExpr") && 
                        check("ParenthesizeExpr1.needParenth") && 
                        check("ParenthesizeExpr2.boolExpr") && 
                        check("ParenthesizeExpr2.needParenth"));
            }
            else if (attr.equals("Expr.needParenth")) {
                return true;
            }
            else return false;
        }
        else if (activeRule.equals("Expr ParenthesizeExpr1 implication ParenthesizeExpr2")) {
            if (attr.equals("Expr.boolExpr")) {
                return (check("ParenthesizeExpr1.boolExpr") && 
                        check("ParenthesizeExpr1.needParenth") && 
                        check("ParenthesizeExpr2.boolExpr") && 
                        check("ParenthesizeExpr2.needParenth"));
            }
            else if (attr.equals("Expr.needParenth")) {
                return true;
            }
            else return false;
        }
        else if (activeRule.equals("Expr not OrExpr")) {
            if (attr.equals("Expr.boolExpr")) {
                return (check("OrExpr.boolExpr"));
            }
            else if (attr.equals("Expr.needParenth")) {
                return true;
            }
            else return false;
        }
        else if (activeRule.equals("Expr OrExpr")) {
            if (attr.equals("Expr.boolExpr")) {
                return (check("OrExpr.boolExpr"));
            }
            else if (attr.equals("Expr.needParenth")) {
                return (check("OrExpr.needParenth"));
            }
            else return false;
        }
        else if (activeRule.equals("OrExpr1 AndExpr or OrExpr2")) {
            if (attr.equals("OrExpr1.boolExpr")) {
                return (check("AndExpr.boolExpr") && 
                        check("AndExpr.needParenth") && 
                        check("OrExpr2.boolExpr") && 
                        check("OrExpr2.needParenth"));
            }
            else if (attr.equals("OrExpr1.needParenth")) {
                return true;
            }
            else return false;
        }
        else if (activeRule.equals("OrExpr AndExpr")) {
            if (attr.equals("OrExpr.boolExpr")) {
                return (check("AndExpr.boolExpr"));
            }
            else if (attr.equals("OrExpr.needParenth")) {
                return (check("AndExpr.needParenth"));
            }
            else return false;
        }
        else if (activeRule.equals("AndExpr1 ForExpr and AndExpr2")) {
            if (attr.equals("AndExpr1.boolExpr")) {
                return (check("ForExpr.boolExpr") && 
                        check("ForExpr.needParenth") && 
                        check("AndExpr2.boolExpr") && 
                        check("AndExpr2.needParenth"));
            }
            else if (attr.equals("AndExpr1.needParenth")) {
                return true;
            }
            else return false;
        }
        else if (activeRule.equals("AndExpr ForExpr")) {
            if (attr.equals("AndExpr.boolExpr")) {
                return (check("ForExpr.boolExpr"));
            }
            else if (attr.equals("AndExpr.needParenth")) {
                return (check("ForExpr.needParenth"));
            }
            else return false;
        }
        else if (activeRule.equals("ForExpr QuantifiedExpr")) {
            if (attr.equals("ForExpr.boolExpr")) {
                return (check("QuantifiedExpr.boolExpr"));
            }
            else if (attr.equals("ForExpr.needParenth")) {
                return (check("QuantifiedExpr.needParenth"));
            }
            else return false;
        }
        else if (activeRule.equals("QuantifiedExpr1 every dollar name in Expr satisfies QuantifiedExpr2")) {
            if (attr.equals("QuantifiedExpr1.boolExpr")) {
                return (check("name.string") && 
                        check("Expr.boolExpr") && 
                        check("QuantifiedExpr2.boolExpr"));
            }
            else if (attr.equals("QuantifiedExpr1.needParenth")) {
                return true;
            }
            else return false;
        }
        else if (activeRule.equals("QuantifiedExpr1 some dollar name in Expr satisfies QuantifiedExpr2")) {
            if (attr.equals("QuantifiedExpr1.boolExpr")) {
                return (check("name.string") && 
                        check("Expr.boolExpr") && 
                        check("QuantifiedExpr2.boolExpr"));
            }
            else if (attr.equals("QuantifiedExpr1.needParenth")) {
                return true;
            }
            else return false;
        }
        else if (activeRule.equals("QuantifiedExpr IfExpr")) {
            if (attr.equals("QuantifiedExpr.boolExpr")) {
                return (check("IfExpr.boolExpr"));
            }
            else if (attr.equals("QuantifiedExpr.needParenth")) {
                return (check("IfExpr.boolExpr") && 
                        check("IfExpr.needParenth"));
            }
            else return false;
        }
        else if (activeRule.equals("IfExpr1 if openingParenth Expr1 closingParenth then Expr2 else IfExpr2")) {
            if (attr.equals("IfExpr1.boolExpr")) {
                return (check("Expr1.boolExpr") && 
                        check("Expr1.needParenth") && 
                        check("Expr2.boolExpr") && 
                        check("Expr2.needParenth") && 
                        check("IfExpr2.boolExpr") && 
                        check("IfExpr2.needParenth"));
            }
            else if (attr.equals("IfExpr1.needParenth")) {
                return true;
            }
            else return false;
        }
        else if (activeRule.equals("IfExpr InstanceOfExpr")) {
            if (attr.equals("IfExpr.boolExpr")) {
                return (check("InstanceOfExpr.boolExpr"));
            }
            else if (attr.equals("IfExpr.needParenth")) {
                return (check("InstanceOfExpr.needParenth"));
            }
            else return false;
        }
        else if (activeRule.equals("InstanceOfExpr CastableExpr")) {
            if (attr.equals("InstanceOfExpr.boolExpr")) {
                return (check("CastableExpr.boolExpr"));
            }
            else if (attr.equals("InstanceOfExpr.needParenth")) {
                return (check("CastableExpr.needParenth"));
            }
            else return false;
        }
        else if (activeRule.equals("CastableExpr ComparisonExpr")) {
            if (attr.equals("CastableExpr.boolExpr")) {
                return (check("ComparisonExpr.boolExpr"));
            }
            else if (attr.equals("CastableExpr.needParenth")) {
                return (check("ComparisonExpr.needParenth"));
            }
            else return false;
        }
        else if (activeRule.equals("ComparisonExpr RangeExpr1 comparison RangeExpr2")) {
            if (attr.equals("ComparisonExpr.boolExpr")) {
                return (check("RangeExpr1.boolExpr") && 
                        check("RangeExpr1.needParenth") && 
                        check("comparison.string") && 
                        check("RangeExpr2.boolExpr") && 
                        check("RangeExpr2.needParenth"));
            }
            else if (attr.equals("ComparisonExpr.needParenth")) {
                return true;
            }
            else return false;
        }
        else if (activeRule.equals("ComparisonExpr RangeExpr")) {
            if (attr.equals("ComparisonExpr.boolExpr")) {
                return (check("RangeExpr.boolExpr"));
            }
            else if (attr.equals("ComparisonExpr.needParenth")) {
                return (check("RangeExpr.needParenth"));
            }
            else return false;
        }
        else if (activeRule.equals("RangeExpr AdditiveExpr")) {
            if (attr.equals("RangeExpr.boolExpr")) {
                return (check("AdditiveExpr.boolExpr"));
            }
            else if (attr.equals("RangeExpr.needParenth")) {
                return (check("AdditiveExpr.needParenth"));
            }
            else return false;
        }
        else if (activeRule.equals("AdditiveExpr1 MultiplicativeExpr subtract AdditiveExpr2")) {
            if (attr.equals("AdditiveExpr1.boolExpr")) {
                return (check("MultiplicativeExpr.boolExpr") && 
                        check("MultiplicativeExpr.needParenth") && 
                        check("AdditiveExpr2.boolExpr") && 
                        check("AdditiveExpr2.needParenth"));
            }
            else if (attr.equals("AdditiveExpr1.needParenth")) {
                return true;
            }
            else return false;
        }
        else if (activeRule.equals("AdditiveExpr1 MultiplicativeExpr add AdditiveExpr2")) {
            if (attr.equals("AdditiveExpr1.boolExpr")) {
                return (check("MultiplicativeExpr.boolExpr") && 
                        check("MultiplicativeExpr.needParenth") && 
                        check("AdditiveExpr2.boolExpr") && 
                        check("AdditiveExpr2.needParenth"));
            }
            else if (attr.equals("AdditiveExpr1.needParenth")) {
                return true;
            }
            else return false;
        }
        else if (activeRule.equals("AdditiveExpr MultiplicativeExpr")) {
            if (attr.equals("AdditiveExpr.boolExpr")) {
                return (check("MultiplicativeExpr.boolExpr"));
            }
            else if (attr.equals("AdditiveExpr.needParenth")) {
                return (check("MultiplicativeExpr.needParenth"));
            }
            else return false;
        }
        else if (activeRule.equals("MultiplicativeExpr1 UnaryExpr divide MultiplicativeExpr2")) {
            if (attr.equals("MultiplicativeExpr1.boolExpr")) {
                return (check("UnaryExpr.boolExpr") && 
                        check("UnaryExpr.needParenth") && 
                        check("MultiplicativeExpr2.boolExpr") && 
                        check("MultiplicativeExpr2.needParenth"));
            }
            else if (attr.equals("MultiplicativeExpr1.needParenth")) {
                return true;
            }
            else return false;
        }
        else if (activeRule.equals("MultiplicativeExpr1 UnaryExpr multiply MultiplicativeExpr2")) {
            if (attr.equals("MultiplicativeExpr1.boolExpr")) {
                return (check("UnaryExpr.boolExpr") && 
                        check("UnaryExpr.needParenth") && 
                        check("MultiplicativeExpr2.boolExpr") && 
                        check("MultiplicativeExpr2.needParenth"));
            }
            else if (attr.equals("MultiplicativeExpr1.needParenth")) {
                return true;
            }
            else return false;
        }
        else if (activeRule.equals("MultiplicativeExpr UnaryExpr")) {
            if (attr.equals("MultiplicativeExpr.boolExpr")) {
                return (check("UnaryExpr.boolExpr"));
            }
            else if (attr.equals("MultiplicativeExpr.needParenth")) {
                return (check("UnaryExpr.needParenth"));
            }
            else return false;
        }
        else if (activeRule.equals("UnaryExpr1 add UnaryExpr2")) {
            if (attr.equals("UnaryExpr1.boolExpr")) {
                return (check("UnaryExpr2.boolExpr") && 
                        check("UnaryExpr2.needParenth"));
            }
            else if (attr.equals("UnaryExpr1.needParenth")) {
                return true;
            }
            else return false;
        }
        else if (activeRule.equals("UnaryExpr1 subtract UnaryExpr2")) {
            if (attr.equals("UnaryExpr1.boolExpr")) {
                return (check("UnaryExpr2.boolExpr") && 
                        check("UnaryExpr2.needParenth"));
            }
            else if (attr.equals("UnaryExpr1.needParenth")) {
                return true;
            }
            else return false;
        }
        else if (activeRule.equals("UnaryExpr UnionExpr")) {
            if (attr.equals("UnaryExpr.boolExpr")) {
                return (check("UnionExpr.boolExpr"));
            }
            else if (attr.equals("UnaryExpr.needParenth")) {
                return (check("UnionExpr.needParenth"));
            }
            else return false;
        }
        else if (activeRule.equals("UnionExpr1 UnionExpr2 union IntersectExceptExpr")) {
            if (attr.equals("UnionExpr1.boolExpr")) {
                return (check("UnionExpr2.boolExpr") && 
                        check("UnionExpr2.needParenth") && 
                        check("IntersectExceptExpr.boolExpr"));
            }
            else if (attr.equals("UnionExpr1.needParenth")) {
                return true;
            }
            else return false;
        }
        else if (activeRule.equals("UnionExpr IntersectExceptExpr")) {
            if (attr.equals("UnionExpr.boolExpr")) {
                return (check("IntersectExceptExpr.boolExpr"));
            }
            else if (attr.equals("UnionExpr.needParenth")) {
                return (check("IntersectExceptExpr.needParenth"));
            }
            else return false;
        }
        else if (activeRule.equals("IntersectExceptExpr ValueExpr")) {
            if (attr.equals("IntersectExceptExpr.boolExpr")) {
                return (check("ValueExpr.boolExpr"));
            }
            else if (attr.equals("IntersectExceptExpr.needParenth")) {
                return (check("ValueExpr.needParenth"));
            }
            else return false;
        }
        else if (activeRule.equals("ValueExpr PathExpr")) {
            if (attr.equals("ValueExpr.boolExpr")) {
                return (check("PathExpr.boolExpr"));
            }
            else if (attr.equals("ValueExpr.needParenth")) {
                return (check("PathExpr.needParenth"));
            }
            else return false;
        }
        else if (activeRule.equals("PathExpr RelativePathExpr")) {
            if (attr.equals("PathExpr.boolExpr")) {
                return (check("RelativePathExpr.boolExpr"));
            }
            else if (attr.equals("PathExpr.needParenth")) {
                return (check("RelativePathExpr.needParenth"));
            }
            else return false;
        }
        else if (activeRule.equals("PathExpr backslash1 backslash2 RelativePathExpr")) {
            if (attr.equals("PathExpr.boolExpr")) {
                return (check("RelativePathExpr.boolExpr"));
            }
            else if (attr.equals("PathExpr.needParenth")) {
                return (check("RelativePathExpr.needParenth"));
            }
            else return false;
        }
        else if (activeRule.equals("PathExpr backslash RelativePathExpr")) {
            if (attr.equals("PathExpr.boolExpr")) {
                return (check("RelativePathExpr.boolExpr"));
            }
            else if (attr.equals("PathExpr.needParenth")) {
                return (check("RelativePathExpr.needParenth"));
            }
            else return false;
        }
        else if (activeRule.equals("PathExpr backslash")) {
            if (attr.equals("PathExpr.boolExpr")) {
                return true;
            }
            else if (attr.equals("PathExpr.needParenth")) {
                return true;
            }
            else return false;
        }
        else if (activeRule.equals("RelativePathExpr1 RelativePathExpr2 backslash1 backslash2 StepExpr")) {
            if (attr.equals("RelativePathExpr1.boolExpr")) {
                return (check("StepExpr.boolExpr"));
            }
            else if (attr.equals("RelativePathExpr1.needParenth")) {
                return (check("StepExpr.needParenth"));
            }
            else return false;
        }
        else if (activeRule.equals("RelativePathExpr1 RelativePathExpr2 backslash StepExpr")) {
            if (attr.equals("RelativePathExpr1.boolExpr")) {
                return (check("StepExpr.boolExpr"));
            }
            else if (attr.equals("RelativePathExpr1.needParenth")) {
                return (check("StepExpr.needParenth"));
            }
            else return false;
        }
        else if (activeRule.equals("RelativePathExpr StepExpr")) {
            if (attr.equals("RelativePathExpr.boolExpr")) {
                return (check("StepExpr.boolExpr"));
            }
            else if (attr.equals("RelativePathExpr.needParenth")) {
                return (check("StepExpr.needParenth"));
            }
            else return false;
        }
        else if (activeRule.equals("StepExpr PrimaryExpr")) {
            if (attr.equals("StepExpr.boolExpr")) {
                return (check("PrimaryExpr.boolExpr"));
            }
            else if (attr.equals("StepExpr.needParenth")) {
                return (check("PrimaryExpr.needParenth"));
            }
            else return false;
        }
        else if (activeRule.equals("PrimaryExpr dollar name")) {
            if (attr.equals("PrimaryExpr.boolExpr")) {
                return (check("name.string"));
            }
            else if (attr.equals("PrimaryExpr.needParenth")) {
                return true;
            }
            else return false;
        }
        else if (activeRule.equals("PrimaryExpr Literal")) {
            if (attr.equals("PrimaryExpr.boolExpr")) {
                return (check("Literal.boolExpr"));
            }
            else if (attr.equals("PrimaryExpr.needParenth")) {
                return true;
            }
            else return false;
        }
        else if (activeRule.equals("PrimaryExpr ParenthesizeExpr")) {
            if (attr.equals("PrimaryExpr.boolExpr")) {
                return (check("ParenthesizeExpr.boolExpr"));
            }
            else if (attr.equals("PrimaryExpr.needParenth")) {
                return (check("ParenthesizeExpr.needParenth"));
            }
            else return false;
        }
        else if (activeRule.equals("PrimaryExpr FunctionCall")) {
            if (attr.equals("PrimaryExpr.boolExpr")) {
                return (check("FunctionCall.boolExpr"));
            }
            else if (attr.equals("PrimaryExpr.needParenth")) {
                return true;
            }
            else return false;
        }
        else if (activeRule.equals("Literal StringLiteral")) {
            if (attr.equals("Literal.boolExpr")) {
                return (check("StringLiteral.boolExpr"));
            }
            else if (attr.equals("Literal.needParenth")) {
                return true;
            }
            else return false;
        }
        else if (activeRule.equals("Literal NumericLiteral")) {
            if (attr.equals("Literal.boolExpr")) {
                return (check("NumericLiteral.boolExpr"));
            }
            else if (attr.equals("Literal.needParenth")) {
                return true;
            }
            else return false;
        }
        else if (activeRule.equals("FunctionCall booleanFunc")) {
            if (attr.equals("FunctionCall.boolExpr")) {
                return (check("booleanFunc.string"));
            }
            else if (attr.equals("FunctionCall.needParenth")) {
                return true;
            }
            else return false;
        }
        else if (activeRule.equals("ParenthesizeExpr openingParenth Expr closingParenth")) {
            if (attr.equals("ParenthesizeExpr.boolExpr")) {
                return (check("Expr.boolExpr"));
            }
            else if (attr.equals("ParenthesizeExpr.needParenth")) {
                return (check("Expr.needParenth"));
            }
            else return false;
        }
        else if (activeRule.equals("NumericLiteral digits")) {
            if (attr.equals("NumericLiteral.boolExpr")) {
                return (check("digits.string"));
            }
            else if (attr.equals("NumericLiteral.needParenth")) {
                return true;
            }
            else return false;
        }
        else if (activeRule.equals("StringLiteral name")) {
            if (attr.equals("StringLiteral.boolExpr")) {
                return (check("name.string"));
            }
            else if (attr.equals("StringLiteral.needParenth")) {
                return true;
            }
            else return false;
        }
        else return false;
    }

    protected void calculate(String attr) {
        if (activeRule.equals("XPath ExprSequence")) {
            if (attr.equals("XPath.boolExpr")) {
                String XPath_boolExpr = null;
                String ExprSequence_boolExpr = (String) get("ExprSequence.boolExpr");
                XPath_boolExpr = ExprSequence_boolExpr;
                set("XPath.boolExpr",XPath_boolExpr);
            }
            else if (attr.equals("XPath.needParenth")) {
                boolean XPath_needParenth = false;
                XPath_needParenth = false;
                set("XPath.needParenth",new Boolean(XPath_needParenth));
            }
        }
        else if (activeRule.equals("ExprSequence Expr terminatingCharacter")) {
            if (attr.equals("ExprSequence.boolExpr")) {
                String ExprSequence_boolExpr = null;
                String Expr_boolExpr = (String) get("Expr.boolExpr");
                ExprSequence_boolExpr = Expr_boolExpr + ";";
                set("ExprSequence.boolExpr",ExprSequence_boolExpr);
            }
            else if (attr.equals("ExprSequence.needParenth")) {
                boolean ExprSequence_needParenth = false;
                ExprSequence_needParenth = false;
                set("ExprSequence.needParenth",new Boolean(ExprSequence_needParenth));
            }
        }
        else if (activeRule.equals("Expr ParenthesizeExpr1 equivalence ParenthesizeExpr2")) {
            if (attr.equals("Expr.boolExpr")) {
                String Expr_boolExpr = null;
                String ParenthesizeExpr1_boolExpr = (String) get("ParenthesizeExpr1.boolExpr");
                boolean ParenthesizeExpr1_needParenth = ((Boolean)get("ParenthesizeExpr1.needParenth")).booleanValue();
                String ParenthesizeExpr2_boolExpr = (String) get("ParenthesizeExpr2.boolExpr");
                boolean ParenthesizeExpr2_needParenth = ((Boolean)get("ParenthesizeExpr2.needParenth")).booleanValue();
                if(ParenthesizeExpr1_needParenth)
                	Expr_boolExpr = "(" + ParenthesizeExpr1_boolExpr + ") <-> ";
                else
                	Expr_boolExpr = ParenthesizeExpr1_boolExpr + " <-> ";
                if(ParenthesizeExpr2_needParenth)
                	Expr_boolExpr += "(" + ParenthesizeExpr2_boolExpr + ")";
                else
                	Expr_boolExpr += ParenthesizeExpr2_boolExpr;
                set("Expr.boolExpr",Expr_boolExpr);
            }
            else if (attr.equals("Expr.needParenth")) {
                boolean Expr_needParenth = false;
                Expr_needParenth = true;
                set("Expr.needParenth",new Boolean(Expr_needParenth));
            }
        }
        else if (activeRule.equals("Expr ParenthesizeExpr1 implication ParenthesizeExpr2")) {
            if (attr.equals("Expr.boolExpr")) {
                String Expr_boolExpr = null;
                String ParenthesizeExpr1_boolExpr = (String) get("ParenthesizeExpr1.boolExpr");
                boolean ParenthesizeExpr1_needParenth = ((Boolean)get("ParenthesizeExpr1.needParenth")).booleanValue();
                String ParenthesizeExpr2_boolExpr = (String) get("ParenthesizeExpr2.boolExpr");
                boolean ParenthesizeExpr2_needParenth = ((Boolean)get("ParenthesizeExpr2.needParenth")).booleanValue();
                if(ParenthesizeExpr1_needParenth)
                	Expr_boolExpr = "(" + ParenthesizeExpr1_boolExpr + ") -> ";
                else
                	Expr_boolExpr = ParenthesizeExpr1_boolExpr + " -> ";
                if(ParenthesizeExpr2_needParenth)
                	Expr_boolExpr += "(" + ParenthesizeExpr2_boolExpr + ")";
                else
                	Expr_boolExpr += ParenthesizeExpr2_boolExpr;
                set("Expr.boolExpr",Expr_boolExpr);
            }
            else if (attr.equals("Expr.needParenth")) {
                boolean Expr_needParenth = false;
                Expr_needParenth = true;
                set("Expr.needParenth",new Boolean(Expr_needParenth));
            }
        }
        else if (activeRule.equals("Expr not OrExpr")) {
            if (attr.equals("Expr.boolExpr")) {
                String Expr_boolExpr = null;
                String OrExpr_boolExpr = (String) get("OrExpr.boolExpr");
                Expr_boolExpr = "not " + OrExpr_boolExpr; 
                set("Expr.boolExpr",Expr_boolExpr);
            }
            else if (attr.equals("Expr.needParenth")) {
                boolean Expr_needParenth = false;
                Expr_needParenth = true;
                set("Expr.needParenth",new Boolean(Expr_needParenth));
            }
        }
        else if (activeRule.equals("Expr OrExpr")) {
            if (attr.equals("Expr.boolExpr")) {
                String Expr_boolExpr = null;
                String OrExpr_boolExpr = (String) get("OrExpr.boolExpr");
                Expr_boolExpr = OrExpr_boolExpr; 
                set("Expr.boolExpr",Expr_boolExpr);
            }
            else if (attr.equals("Expr.needParenth")) {
                boolean Expr_needParenth = false;
                boolean OrExpr_needParenth = ((Boolean)get("OrExpr.needParenth")).booleanValue();
                Expr_needParenth = OrExpr_needParenth;
                set("Expr.needParenth",new Boolean(Expr_needParenth));
            }
        }
        else if (activeRule.equals("OrExpr1 AndExpr or OrExpr2")) {
            if (attr.equals("OrExpr1.boolExpr")) {
                String OrExpr1_boolExpr = null;
                String AndExpr_boolExpr = (String) get("AndExpr.boolExpr");
                boolean AndExpr_needParenth = ((Boolean)get("AndExpr.needParenth")).booleanValue();
                String OrExpr2_boolExpr = (String) get("OrExpr2.boolExpr");
                boolean OrExpr2_needParenth = ((Boolean)get("OrExpr2.needParenth")).booleanValue();
                if(AndExpr_needParenth && OrExpr2_needParenth)
                	OrExpr1_boolExpr = "(" + AndExpr_boolExpr + ") or (" + OrExpr2_boolExpr + ")";
                else if(AndExpr_needParenth)
                	OrExpr1_boolExpr = "(" + AndExpr_boolExpr + ") or " + OrExpr2_boolExpr;
                else if(OrExpr2_needParenth)
                	OrExpr1_boolExpr = AndExpr_boolExpr + " or (" + OrExpr2_boolExpr + ")";
                else
                	OrExpr1_boolExpr = AndExpr_boolExpr + " or " + OrExpr2_boolExpr ;
                set("OrExpr1.boolExpr",OrExpr1_boolExpr);
            }
            else if (attr.equals("OrExpr1.needParenth")) {
                boolean OrExpr1_needParenth = false;
                OrExpr1_needParenth = true;
                set("OrExpr1.needParenth",new Boolean(OrExpr1_needParenth));
            }
        }
        else if (activeRule.equals("OrExpr AndExpr")) {
            if (attr.equals("OrExpr.boolExpr")) {
                String OrExpr_boolExpr = null;
                String AndExpr_boolExpr = (String) get("AndExpr.boolExpr");
                OrExpr_boolExpr = AndExpr_boolExpr;
                set("OrExpr.boolExpr",OrExpr_boolExpr);
            }
            else if (attr.equals("OrExpr.needParenth")) {
                boolean OrExpr_needParenth = false;
                boolean AndExpr_needParenth = ((Boolean)get("AndExpr.needParenth")).booleanValue();
                OrExpr_needParenth = AndExpr_needParenth;
                set("OrExpr.needParenth",new Boolean(OrExpr_needParenth));
            }
        }
        else if (activeRule.equals("AndExpr1 ForExpr and AndExpr2")) {
            if (attr.equals("AndExpr1.boolExpr")) {
                String AndExpr1_boolExpr = null;
                String ForExpr_boolExpr = (String) get("ForExpr.boolExpr");
                boolean ForExpr_needParenth = ((Boolean)get("ForExpr.needParenth")).booleanValue();
                String AndExpr2_boolExpr = (String) get("AndExpr2.boolExpr");
                boolean AndExpr2_needParenth = ((Boolean)get("AndExpr2.needParenth")).booleanValue();
                if(ForExpr_needParenth && AndExpr2_needParenth)
                	AndExpr1_boolExpr = "(" + ForExpr_boolExpr + ") and (" + AndExpr2_boolExpr + ")";
                else if(ForExpr_needParenth)
                	AndExpr1_boolExpr = "(" + ForExpr_boolExpr + ") and " + AndExpr2_boolExpr;
                else if(AndExpr2_needParenth)
                	AndExpr1_boolExpr = ForExpr_boolExpr + " and (" + AndExpr2_boolExpr + ")";
                else
                	AndExpr1_boolExpr = ForExpr_boolExpr + " and " + AndExpr2_boolExpr ;
                set("AndExpr1.boolExpr",AndExpr1_boolExpr);
            }
            else if (attr.equals("AndExpr1.needParenth")) {
                boolean AndExpr1_needParenth = false;
                AndExpr1_needParenth = true;
                set("AndExpr1.needParenth",new Boolean(AndExpr1_needParenth));
            }
        }
        else if (activeRule.equals("AndExpr ForExpr")) {
            if (attr.equals("AndExpr.boolExpr")) {
                String AndExpr_boolExpr = null;
                String ForExpr_boolExpr = (String) get("ForExpr.boolExpr");
                AndExpr_boolExpr = ForExpr_boolExpr;
                set("AndExpr.boolExpr",AndExpr_boolExpr);
            }
            else if (attr.equals("AndExpr.needParenth")) {
                boolean AndExpr_needParenth = false;
                boolean ForExpr_needParenth = ((Boolean)get("ForExpr.needParenth")).booleanValue();
                AndExpr_needParenth = ForExpr_needParenth;
                set("AndExpr.needParenth",new Boolean(AndExpr_needParenth));
            }
        }
        else if (activeRule.equals("ForExpr QuantifiedExpr")) {
            if (attr.equals("ForExpr.boolExpr")) {
                String ForExpr_boolExpr = null;
                String QuantifiedExpr_boolExpr = (String) get("QuantifiedExpr.boolExpr");
                ForExpr_boolExpr = QuantifiedExpr_boolExpr;
                set("ForExpr.boolExpr",ForExpr_boolExpr);
            }
            else if (attr.equals("ForExpr.needParenth")) {
                boolean ForExpr_needParenth = false;
                boolean QuantifiedExpr_needParenth = ((Boolean)get("QuantifiedExpr.needParenth")).booleanValue();
                ForExpr_needParenth = QuantifiedExpr_needParenth;
                set("ForExpr.needParenth",new Boolean(ForExpr_needParenth));
            }
        }
        else if (activeRule.equals("QuantifiedExpr1 every dollar name in Expr satisfies QuantifiedExpr2")) {
            if (attr.equals("QuantifiedExpr1.boolExpr")) {
                String QuantifiedExpr1_boolExpr = null;
                String name_string = (String) get("name.string");
                String Expr_boolExpr = (String) get("Expr.boolExpr");
                String QuantifiedExpr2_boolExpr = (String) get("QuantifiedExpr2.boolExpr");
                QuantifiedExpr1_boolExpr = "forall " + name_string + " in [ModelNavigation.findFeaturesWithName(" + Expr_boolExpr + ")] : (" + QuantifiedExpr2_boolExpr + ")" ;    
                set("QuantifiedExpr1.boolExpr",QuantifiedExpr1_boolExpr);
            }
            else if (attr.equals("QuantifiedExpr1.needParenth")) {
                boolean QuantifiedExpr1_needParenth = false;
                QuantifiedExpr1_needParenth = true;
                set("QuantifiedExpr1.needParenth",new Boolean(QuantifiedExpr1_needParenth));
            }
        }
        else if (activeRule.equals("QuantifiedExpr1 some dollar name in Expr satisfies QuantifiedExpr2")) {
            if (attr.equals("QuantifiedExpr1.boolExpr")) {
                String QuantifiedExpr1_boolExpr = null;
                String name_string = (String) get("name.string");
                String Expr_boolExpr = (String) get("Expr.boolExpr");
                String QuantifiedExpr2_boolExpr = (String) get("QuantifiedExpr2.boolExpr");
                QuantifiedExpr1_boolExpr = "exist " + name_string + " in [ModelNavigation.findFeaturesWithName(" + Expr_boolExpr + ")] : (" + QuantifiedExpr2_boolExpr + ")";
                set("QuantifiedExpr1.boolExpr",QuantifiedExpr1_boolExpr);
            }
            else if (attr.equals("QuantifiedExpr1.needParenth")) {
                boolean QuantifiedExpr1_needParenth = false;
                QuantifiedExpr1_needParenth = true;
                set("QuantifiedExpr1.needParenth",new Boolean(QuantifiedExpr1_needParenth));
            }
        }
        else if (activeRule.equals("QuantifiedExpr IfExpr")) {
            if (attr.equals("QuantifiedExpr.boolExpr")) {
                String QuantifiedExpr_boolExpr = null;
                String IfExpr_boolExpr = (String) get("IfExpr.boolExpr");
                QuantifiedExpr_boolExpr = IfExpr_boolExpr;
                set("QuantifiedExpr.boolExpr",QuantifiedExpr_boolExpr);
            }
            else if (attr.equals("QuantifiedExpr.needParenth")) {
                boolean QuantifiedExpr_needParenth = false;
                String IfExpr_boolExpr = (String) get("IfExpr.boolExpr");
                boolean IfExpr_needParenth = ((Boolean)get("IfExpr.needParenth")).booleanValue();
                QuantifiedExpr_needParenth = IfExpr_needParenth;
                set("QuantifiedExpr.needParenth",new Boolean(QuantifiedExpr_needParenth));
            }
        }
        else if (activeRule.equals("IfExpr1 if openingParenth Expr1 closingParenth then Expr2 else IfExpr2")) {
            if (attr.equals("IfExpr1.boolExpr")) {
                String IfExpr1_boolExpr = null;
                String Expr1_boolExpr = (String) get("Expr1.boolExpr");
                boolean Expr1_needParenth = ((Boolean)get("Expr1.needParenth")).booleanValue();
                String Expr2_boolExpr = (String) get("Expr2.boolExpr");
                boolean Expr2_needParenth = ((Boolean)get("Expr2.needParenth")).booleanValue();
                String IfExpr2_boolExpr = (String) get("IfExpr2.boolExpr");
                boolean IfExpr2_needParenth = ((Boolean)get("IfExpr2.needParenth")).booleanValue();
                String temp1,temp2;
                if(Expr1_needParenth)
                	temp1 = "(" + Expr1_boolExpr + ")";
                else 
                	temp1 = Expr1_boolExpr;
                if(Expr2_needParenth)
                	temp2 = "(" + Expr2_boolExpr + ")";
                else 
                	temp2 = Expr2_boolExpr;
                if(IfExpr2_boolExpr.equals("true"))
                	IfExpr1_boolExpr = temp1 + "->" + temp2;
                else if(IfExpr2_boolExpr.equals("false"))
                	IfExpr1_boolExpr = temp1 + " and " + temp2;
                else if(IfExpr2_needParenth)
                	IfExpr1_boolExpr = "(" + temp1 + "->" + temp2 + ")" + " and ((not " + temp1 + ")->(" + IfExpr2_boolExpr +"))";
                else
                	IfExpr1_boolExpr = "(" + temp1 + "->" + temp2 + ")" + " and ((not " + temp1 + ")->" + IfExpr2_boolExpr +")";
                set("IfExpr1.boolExpr",IfExpr1_boolExpr);
            }
            else if (attr.equals("IfExpr1.needParenth")) {
                boolean IfExpr1_needParenth = false;
                IfExpr1_needParenth = true;
                set("IfExpr1.needParenth",new Boolean(IfExpr1_needParenth));
            }
        }
        else if (activeRule.equals("IfExpr InstanceOfExpr")) {
            if (attr.equals("IfExpr.boolExpr")) {
                String IfExpr_boolExpr = null;
                String InstanceOfExpr_boolExpr = (String) get("InstanceOfExpr.boolExpr");
                IfExpr_boolExpr = InstanceOfExpr_boolExpr;
                set("IfExpr.boolExpr",IfExpr_boolExpr);
            }
            else if (attr.equals("IfExpr.needParenth")) {
                boolean IfExpr_needParenth = false;
                boolean InstanceOfExpr_needParenth = ((Boolean)get("InstanceOfExpr.needParenth")).booleanValue();
                IfExpr_needParenth = InstanceOfExpr_needParenth;
                set("IfExpr.needParenth",new Boolean(IfExpr_needParenth));
            }
        }
        else if (activeRule.equals("InstanceOfExpr CastableExpr")) {
            if (attr.equals("InstanceOfExpr.boolExpr")) {
                String InstanceOfExpr_boolExpr = null;
                String CastableExpr_boolExpr = (String) get("CastableExpr.boolExpr");
                InstanceOfExpr_boolExpr = CastableExpr_boolExpr;
                set("InstanceOfExpr.boolExpr",InstanceOfExpr_boolExpr);
            }
            else if (attr.equals("InstanceOfExpr.needParenth")) {
                boolean InstanceOfExpr_needParenth = false;
                boolean CastableExpr_needParenth = ((Boolean)get("CastableExpr.needParenth")).booleanValue();
                InstanceOfExpr_needParenth = CastableExpr_needParenth;
                set("InstanceOfExpr.needParenth",new Boolean(InstanceOfExpr_needParenth));
            }
        }
        else if (activeRule.equals("CastableExpr ComparisonExpr")) {
            if (attr.equals("CastableExpr.boolExpr")) {
                String CastableExpr_boolExpr = null;
                String ComparisonExpr_boolExpr = (String) get("ComparisonExpr.boolExpr");
                CastableExpr_boolExpr = ComparisonExpr_boolExpr;
                set("CastableExpr.boolExpr",CastableExpr_boolExpr);
            }
            else if (attr.equals("CastableExpr.needParenth")) {
                boolean CastableExpr_needParenth = false;
                boolean ComparisonExpr_needParenth = ((Boolean)get("ComparisonExpr.needParenth")).booleanValue();
                CastableExpr_needParenth = ComparisonExpr_needParenth;
                set("CastableExpr.needParenth",new Boolean(CastableExpr_needParenth));
            }
        }
        else if (activeRule.equals("ComparisonExpr RangeExpr1 comparison RangeExpr2")) {
            if (attr.equals("ComparisonExpr.boolExpr")) {
                String ComparisonExpr_boolExpr = null;
                String RangeExpr1_boolExpr = (String) get("RangeExpr1.boolExpr");
                boolean RangeExpr1_needParenth = ((Boolean)get("RangeExpr1.needParenth")).booleanValue();
                String comparison_string = (String) get("comparison.string");
                String RangeExpr2_boolExpr = (String) get("RangeExpr2.boolExpr");
                boolean RangeExpr2_needParenth = ((Boolean)get("RangeExpr2.needParenth")).booleanValue();
                if(RangeExpr1_needParenth && RangeExpr2_needParenth)
                	ComparisonExpr_boolExpr = "(" + RangeExpr1_boolExpr + ")" + comparison_string + "(" + RangeExpr2_boolExpr + ")";
                else if(RangeExpr1_needParenth)
                	ComparisonExpr_boolExpr = "(" + RangeExpr1_boolExpr + ")" + comparison_string + RangeExpr2_boolExpr;
                else if(RangeExpr2_needParenth)
                	ComparisonExpr_boolExpr = RangeExpr1_boolExpr + comparison_string + "(" + RangeExpr2_boolExpr + ")";
                else
                	ComparisonExpr_boolExpr = RangeExpr1_boolExpr + comparison_string + RangeExpr2_boolExpr;	
                set("ComparisonExpr.boolExpr",ComparisonExpr_boolExpr);
            }
            else if (attr.equals("ComparisonExpr.needParenth")) {
                boolean ComparisonExpr_needParenth = false;
                ComparisonExpr_needParenth = true;
                set("ComparisonExpr.needParenth",new Boolean(ComparisonExpr_needParenth));
            }
        }
        else if (activeRule.equals("ComparisonExpr RangeExpr")) {
            if (attr.equals("ComparisonExpr.boolExpr")) {
                String ComparisonExpr_boolExpr = null;
                String RangeExpr_boolExpr = (String) get("RangeExpr.boolExpr");
                ComparisonExpr_boolExpr = RangeExpr_boolExpr;
                set("ComparisonExpr.boolExpr",ComparisonExpr_boolExpr);
            }
            else if (attr.equals("ComparisonExpr.needParenth")) {
                boolean ComparisonExpr_needParenth = false;
                boolean RangeExpr_needParenth = ((Boolean)get("RangeExpr.needParenth")).booleanValue();
                ComparisonExpr_needParenth = RangeExpr_needParenth;
                set("ComparisonExpr.needParenth",new Boolean(ComparisonExpr_needParenth));
            }
        }
        else if (activeRule.equals("RangeExpr AdditiveExpr")) {
            if (attr.equals("RangeExpr.boolExpr")) {
                String RangeExpr_boolExpr = null;
                String AdditiveExpr_boolExpr = (String) get("AdditiveExpr.boolExpr");
                RangeExpr_boolExpr = AdditiveExpr_boolExpr;
                set("RangeExpr.boolExpr",RangeExpr_boolExpr);
            }
            else if (attr.equals("RangeExpr.needParenth")) {
                boolean RangeExpr_needParenth = false;
                boolean AdditiveExpr_needParenth = ((Boolean)get("AdditiveExpr.needParenth")).booleanValue();
                RangeExpr_needParenth = AdditiveExpr_needParenth;
                set("RangeExpr.needParenth",new Boolean(RangeExpr_needParenth));
            }
        }
        else if (activeRule.equals("AdditiveExpr1 MultiplicativeExpr subtract AdditiveExpr2")) {
            if (attr.equals("AdditiveExpr1.boolExpr")) {
                String AdditiveExpr1_boolExpr = null;
                String MultiplicativeExpr_boolExpr = (String) get("MultiplicativeExpr.boolExpr");
                boolean MultiplicativeExpr_needParenth = ((Boolean)get("MultiplicativeExpr.needParenth")).booleanValue();
                String AdditiveExpr2_boolExpr = (String) get("AdditiveExpr2.boolExpr");
                boolean AdditiveExpr2_needParenth = ((Boolean)get("AdditiveExpr2.needParenth")).booleanValue();
                if(MultiplicativeExpr_needParenth && AdditiveExpr2_needParenth)
                	AdditiveExpr1_boolExpr = "(" + MultiplicativeExpr_boolExpr + ")-(" + AdditiveExpr2_boolExpr + ")";
                else if(MultiplicativeExpr_needParenth)
                	AdditiveExpr1_boolExpr = "(" + MultiplicativeExpr_boolExpr + ")-" + AdditiveExpr2_boolExpr;
                else if(AdditiveExpr2_needParenth)
                	AdditiveExpr1_boolExpr = MultiplicativeExpr_boolExpr + "-(" + AdditiveExpr2_boolExpr + ")";
                else
                	AdditiveExpr1_boolExpr = MultiplicativeExpr_boolExpr + "-" + AdditiveExpr2_boolExpr; 
                set("AdditiveExpr1.boolExpr",AdditiveExpr1_boolExpr);
            }
            else if (attr.equals("AdditiveExpr1.needParenth")) {
                boolean AdditiveExpr1_needParenth = false;
                AdditiveExpr1_needParenth = true;
                set("AdditiveExpr1.needParenth",new Boolean(AdditiveExpr1_needParenth));
            }
        }
        else if (activeRule.equals("AdditiveExpr1 MultiplicativeExpr add AdditiveExpr2")) {
            if (attr.equals("AdditiveExpr1.boolExpr")) {
                String AdditiveExpr1_boolExpr = null;
                String MultiplicativeExpr_boolExpr = (String) get("MultiplicativeExpr.boolExpr");
                boolean MultiplicativeExpr_needParenth = ((Boolean)get("MultiplicativeExpr.needParenth")).booleanValue();
                String AdditiveExpr2_boolExpr = (String) get("AdditiveExpr2.boolExpr");
                boolean AdditiveExpr2_needParenth = ((Boolean)get("AdditiveExpr2.needParenth")).booleanValue();
                if(MultiplicativeExpr_needParenth && AdditiveExpr2_needParenth)
                	AdditiveExpr1_boolExpr = "(" + MultiplicativeExpr_boolExpr + ")+(" + AdditiveExpr2_boolExpr + ")";
                else if(MultiplicativeExpr_needParenth)
                	AdditiveExpr1_boolExpr = "(" + MultiplicativeExpr_boolExpr + ")+" + AdditiveExpr2_boolExpr;
                else if(AdditiveExpr2_needParenth)
                	AdditiveExpr1_boolExpr = MultiplicativeExpr_boolExpr + "+(" + AdditiveExpr2_boolExpr + ")";
                else
                	AdditiveExpr1_boolExpr = MultiplicativeExpr_boolExpr + "+" + AdditiveExpr2_boolExpr;  
                set("AdditiveExpr1.boolExpr",AdditiveExpr1_boolExpr);
            }
            else if (attr.equals("AdditiveExpr1.needParenth")) {
                boolean AdditiveExpr1_needParenth = false;
                AdditiveExpr1_needParenth = true;
                set("AdditiveExpr1.needParenth",new Boolean(AdditiveExpr1_needParenth));
            }
        }
        else if (activeRule.equals("AdditiveExpr MultiplicativeExpr")) {
            if (attr.equals("AdditiveExpr.boolExpr")) {
                String AdditiveExpr_boolExpr = null;
                String MultiplicativeExpr_boolExpr = (String) get("MultiplicativeExpr.boolExpr");
                AdditiveExpr_boolExpr = MultiplicativeExpr_boolExpr;
                set("AdditiveExpr.boolExpr",AdditiveExpr_boolExpr);
            }
            else if (attr.equals("AdditiveExpr.needParenth")) {
                boolean AdditiveExpr_needParenth = false;
                boolean MultiplicativeExpr_needParenth = ((Boolean)get("MultiplicativeExpr.needParenth")).booleanValue();
                AdditiveExpr_needParenth = MultiplicativeExpr_needParenth;
                set("AdditiveExpr.needParenth",new Boolean(AdditiveExpr_needParenth));
            }
        }
        else if (activeRule.equals("MultiplicativeExpr1 UnaryExpr divide MultiplicativeExpr2")) {
            if (attr.equals("MultiplicativeExpr1.boolExpr")) {
                String MultiplicativeExpr1_boolExpr = null;
                String UnaryExpr_boolExpr = (String) get("UnaryExpr.boolExpr");
                boolean UnaryExpr_needParenth = ((Boolean)get("UnaryExpr.needParenth")).booleanValue();
                String MultiplicativeExpr2_boolExpr = (String) get("MultiplicativeExpr2.boolExpr");
                boolean MultiplicativeExpr2_needParenth = ((Boolean)get("MultiplicativeExpr2.needParenth")).booleanValue();
                if(MultiplicativeExpr2_needParenth && UnaryExpr_needParenth)
                	MultiplicativeExpr1_boolExpr = "(" + UnaryExpr_boolExpr + ")/(" + MultiplicativeExpr2_boolExpr + ")";
                else if(MultiplicativeExpr2_needParenth)
                	MultiplicativeExpr1_boolExpr = UnaryExpr_boolExpr + "/(" + MultiplicativeExpr2_boolExpr + ")";
                else if(UnaryExpr_needParenth)
                	MultiplicativeExpr1_boolExpr = "(" + UnaryExpr_boolExpr + ")/" + MultiplicativeExpr2_boolExpr ;
                else
                	MultiplicativeExpr1_boolExpr = UnaryExpr_boolExpr + "/" + MultiplicativeExpr2_boolExpr ; 
                set("MultiplicativeExpr1.boolExpr",MultiplicativeExpr1_boolExpr);
            }
            else if (attr.equals("MultiplicativeExpr1.needParenth")) {
                boolean MultiplicativeExpr1_needParenth = false;
                MultiplicativeExpr1_needParenth = true;
                set("MultiplicativeExpr1.needParenth",new Boolean(MultiplicativeExpr1_needParenth));
            }
        }
        else if (activeRule.equals("MultiplicativeExpr1 UnaryExpr multiply MultiplicativeExpr2")) {
            if (attr.equals("MultiplicativeExpr1.boolExpr")) {
                String MultiplicativeExpr1_boolExpr = null;
                String UnaryExpr_boolExpr = (String) get("UnaryExpr.boolExpr");
                boolean UnaryExpr_needParenth = ((Boolean)get("UnaryExpr.needParenth")).booleanValue();
                String MultiplicativeExpr2_boolExpr = (String) get("MultiplicativeExpr2.boolExpr");
                boolean MultiplicativeExpr2_needParenth = ((Boolean)get("MultiplicativeExpr2.needParenth")).booleanValue();
                if(MultiplicativeExpr2_needParenth && UnaryExpr_needParenth)
                	MultiplicativeExpr1_boolExpr = "(" + UnaryExpr_boolExpr + ")*(" + MultiplicativeExpr2_boolExpr + ")";
                else if(MultiplicativeExpr2_needParenth)
                	MultiplicativeExpr1_boolExpr = UnaryExpr_boolExpr + "*(" + MultiplicativeExpr2_boolExpr + ")";
                else if(UnaryExpr_needParenth)
                	MultiplicativeExpr1_boolExpr = "(" + UnaryExpr_boolExpr + ")*" + MultiplicativeExpr2_boolExpr ;
                else
                	MultiplicativeExpr1_boolExpr = UnaryExpr_boolExpr + "*" + MultiplicativeExpr2_boolExpr ; 
                set("MultiplicativeExpr1.boolExpr",MultiplicativeExpr1_boolExpr);
            }
            else if (attr.equals("MultiplicativeExpr1.needParenth")) {
                boolean MultiplicativeExpr1_needParenth = false;
                MultiplicativeExpr1_needParenth = true;
                set("MultiplicativeExpr1.needParenth",new Boolean(MultiplicativeExpr1_needParenth));
            }
        }
        else if (activeRule.equals("MultiplicativeExpr UnaryExpr")) {
            if (attr.equals("MultiplicativeExpr.boolExpr")) {
                String MultiplicativeExpr_boolExpr = null;
                String UnaryExpr_boolExpr = (String) get("UnaryExpr.boolExpr");
                MultiplicativeExpr_boolExpr = UnaryExpr_boolExpr;
                set("MultiplicativeExpr.boolExpr",MultiplicativeExpr_boolExpr);
            }
            else if (attr.equals("MultiplicativeExpr.needParenth")) {
                boolean MultiplicativeExpr_needParenth = false;
                boolean UnaryExpr_needParenth = ((Boolean)get("UnaryExpr.needParenth")).booleanValue();
                MultiplicativeExpr_needParenth = UnaryExpr_needParenth;
                set("MultiplicativeExpr.needParenth",new Boolean(MultiplicativeExpr_needParenth));
            }
        }
        else if (activeRule.equals("UnaryExpr1 add UnaryExpr2")) {
            if (attr.equals("UnaryExpr1.boolExpr")) {
                String UnaryExpr1_boolExpr = null;
                String UnaryExpr2_boolExpr = (String) get("UnaryExpr2.boolExpr");
                boolean UnaryExpr2_needParenth = ((Boolean)get("UnaryExpr2.needParenth")).booleanValue();
                if(UnaryExpr2_needParenth)
                	UnaryExpr1_boolExpr = "+(" + UnaryExpr2_boolExpr + ")";
                else
                	UnaryExpr1_boolExpr = "+" + UnaryExpr2_boolExpr;
                set("UnaryExpr1.boolExpr",UnaryExpr1_boolExpr);
            }
            else if (attr.equals("UnaryExpr1.needParenth")) {
                boolean UnaryExpr1_needParenth = false;
                UnaryExpr1_needParenth = true;
                set("UnaryExpr1.needParenth",new Boolean(UnaryExpr1_needParenth));
            }
        }
        else if (activeRule.equals("UnaryExpr1 subtract UnaryExpr2")) {
            if (attr.equals("UnaryExpr1.boolExpr")) {
                String UnaryExpr1_boolExpr = null;
                String UnaryExpr2_boolExpr = (String) get("UnaryExpr2.boolExpr");
                boolean UnaryExpr2_needParenth = ((Boolean)get("UnaryExpr2.needParenth")).booleanValue();
                if(UnaryExpr2_needParenth)
                	UnaryExpr1_boolExpr = "-(" + UnaryExpr2_boolExpr + ")";
                else
                	UnaryExpr1_boolExpr = "-" + UnaryExpr2_boolExpr;
                set("UnaryExpr1.boolExpr",UnaryExpr1_boolExpr);
            }
            else if (attr.equals("UnaryExpr1.needParenth")) {
                boolean UnaryExpr1_needParenth = false;
                UnaryExpr1_needParenth = true;
                set("UnaryExpr1.needParenth",new Boolean(UnaryExpr1_needParenth));
            }
        }
        else if (activeRule.equals("UnaryExpr UnionExpr")) {
            if (attr.equals("UnaryExpr.boolExpr")) {
                String UnaryExpr_boolExpr = null;
                String UnionExpr_boolExpr = (String) get("UnionExpr.boolExpr");
                UnaryExpr_boolExpr = UnionExpr_boolExpr;
                set("UnaryExpr.boolExpr",UnaryExpr_boolExpr);
            }
            else if (attr.equals("UnaryExpr.needParenth")) {
                boolean UnaryExpr_needParenth = false;
                boolean UnionExpr_needParenth = ((Boolean)get("UnionExpr.needParenth")).booleanValue();
                UnaryExpr_needParenth = UnionExpr_needParenth;
                set("UnaryExpr.needParenth",new Boolean(UnaryExpr_needParenth));
            }
        }
        else if (activeRule.equals("UnionExpr1 UnionExpr2 union IntersectExceptExpr")) {
            if (attr.equals("UnionExpr1.boolExpr")) {
                String UnionExpr1_boolExpr = null;
                String UnionExpr2_boolExpr = (String) get("UnionExpr2.boolExpr");
                boolean UnionExpr2_needParenth = ((Boolean)get("UnionExpr2.needParenth")).booleanValue();
                String IntersectExceptExpr_boolExpr = (String) get("IntersectExceptExpr.boolExpr");
                if(UnionExpr2_needParenth)
                	UnionExpr1_boolExpr = "(" + UnionExpr2_boolExpr + ") or " + IntersectExceptExpr_boolExpr;
                else
                	UnionExpr1_boolExpr = UnionExpr2_boolExpr + " or " + IntersectExceptExpr_boolExpr;
                set("UnionExpr1.boolExpr",UnionExpr1_boolExpr);
            }
            else if (attr.equals("UnionExpr1.needParenth")) {
                boolean UnionExpr1_needParenth = false;
                UnionExpr1_needParenth = true;
                set("UnionExpr1.needParenth",new Boolean(UnionExpr1_needParenth));
            }
        }
        else if (activeRule.equals("UnionExpr IntersectExceptExpr")) {
            if (attr.equals("UnionExpr.boolExpr")) {
                String UnionExpr_boolExpr = null;
                String IntersectExceptExpr_boolExpr = (String) get("IntersectExceptExpr.boolExpr");
                UnionExpr_boolExpr = IntersectExceptExpr_boolExpr;
                set("UnionExpr.boolExpr",UnionExpr_boolExpr);
            }
            else if (attr.equals("UnionExpr.needParenth")) {
                boolean UnionExpr_needParenth = false;
                boolean IntersectExceptExpr_needParenth = ((Boolean)get("IntersectExceptExpr.needParenth")).booleanValue();
                UnionExpr_needParenth = IntersectExceptExpr_needParenth;
                set("UnionExpr.needParenth",new Boolean(UnionExpr_needParenth));
            }
        }
        else if (activeRule.equals("IntersectExceptExpr ValueExpr")) {
            if (attr.equals("IntersectExceptExpr.boolExpr")) {
                String IntersectExceptExpr_boolExpr = null;
                String ValueExpr_boolExpr = (String) get("ValueExpr.boolExpr");
                IntersectExceptExpr_boolExpr = ValueExpr_boolExpr;
                set("IntersectExceptExpr.boolExpr",IntersectExceptExpr_boolExpr);
            }
            else if (attr.equals("IntersectExceptExpr.needParenth")) {
                boolean IntersectExceptExpr_needParenth = false;
                boolean ValueExpr_needParenth = ((Boolean)get("ValueExpr.needParenth")).booleanValue();
                IntersectExceptExpr_needParenth = ValueExpr_needParenth;
                set("IntersectExceptExpr.needParenth",new Boolean(IntersectExceptExpr_needParenth));
            }
        }
        else if (activeRule.equals("ValueExpr PathExpr")) {
            if (attr.equals("ValueExpr.boolExpr")) {
                String ValueExpr_boolExpr = null;
                String PathExpr_boolExpr = (String) get("PathExpr.boolExpr");
                ValueExpr_boolExpr = PathExpr_boolExpr;
                set("ValueExpr.boolExpr",ValueExpr_boolExpr);
            }
            else if (attr.equals("ValueExpr.needParenth")) {
                boolean ValueExpr_needParenth = false;
                boolean PathExpr_needParenth = ((Boolean)get("PathExpr.needParenth")).booleanValue();
                ValueExpr_needParenth = PathExpr_needParenth;
                set("ValueExpr.needParenth",new Boolean(ValueExpr_needParenth));
            }
        }
        else if (activeRule.equals("PathExpr RelativePathExpr")) {
            if (attr.equals("PathExpr.boolExpr")) {
                String PathExpr_boolExpr = null;
                String RelativePathExpr_boolExpr = (String) get("RelativePathExpr.boolExpr");
                PathExpr_boolExpr = RelativePathExpr_boolExpr;
                set("PathExpr.boolExpr",PathExpr_boolExpr);
            }
            else if (attr.equals("PathExpr.needParenth")) {
                boolean PathExpr_needParenth = false;
                boolean RelativePathExpr_needParenth = ((Boolean)get("RelativePathExpr.needParenth")).booleanValue();
                PathExpr_needParenth = RelativePathExpr_needParenth;
                set("PathExpr.needParenth",new Boolean(PathExpr_needParenth));
            }
        }
        else if (activeRule.equals("PathExpr backslash1 backslash2 RelativePathExpr")) {
            if (attr.equals("PathExpr.boolExpr")) {
                String PathExpr_boolExpr = null;
                String RelativePathExpr_boolExpr = (String) get("RelativePathExpr.boolExpr");
                PathExpr_boolExpr = RelativePathExpr_boolExpr;
                set("PathExpr.boolExpr",PathExpr_boolExpr);
            }
            else if (attr.equals("PathExpr.needParenth")) {
                boolean PathExpr_needParenth = false;
                boolean RelativePathExpr_needParenth = ((Boolean)get("RelativePathExpr.needParenth")).booleanValue();
                PathExpr_needParenth = RelativePathExpr_needParenth;
                set("PathExpr.needParenth",new Boolean(PathExpr_needParenth));
            }
        }
        else if (activeRule.equals("PathExpr backslash RelativePathExpr")) {
            if (attr.equals("PathExpr.boolExpr")) {
                String PathExpr_boolExpr = null;
                String RelativePathExpr_boolExpr = (String) get("RelativePathExpr.boolExpr");
                PathExpr_boolExpr = RelativePathExpr_boolExpr;
                set("PathExpr.boolExpr",PathExpr_boolExpr);
            }
            else if (attr.equals("PathExpr.needParenth")) {
                boolean PathExpr_needParenth = false;
                boolean RelativePathExpr_needParenth = ((Boolean)get("RelativePathExpr.needParenth")).booleanValue();
                PathExpr_needParenth = RelativePathExpr_needParenth;
                set("PathExpr.needParenth",new Boolean(PathExpr_needParenth));
            }
        }
        else if (activeRule.equals("PathExpr backslash")) {
            if (attr.equals("PathExpr.boolExpr")) {
                String PathExpr_boolExpr = null;
                PathExpr_boolExpr = "Root";
                set("PathExpr.boolExpr",PathExpr_boolExpr);
            }
            else if (attr.equals("PathExpr.needParenth")) {
                boolean PathExpr_needParenth = false;
                PathExpr_needParenth = false;
                set("PathExpr.needParenth",new Boolean(PathExpr_needParenth));
            }
        }
        else if (activeRule.equals("RelativePathExpr1 RelativePathExpr2 backslash1 backslash2 StepExpr")) {
            if (attr.equals("RelativePathExpr1.boolExpr")) {
                String RelativePathExpr1_boolExpr = null;
                String StepExpr_boolExpr = (String) get("StepExpr.boolExpr");
                RelativePathExpr1_boolExpr = StepExpr_boolExpr;
                set("RelativePathExpr1.boolExpr",RelativePathExpr1_boolExpr);
            }
            else if (attr.equals("RelativePathExpr1.needParenth")) {
                boolean RelativePathExpr1_needParenth = false;
                boolean StepExpr_needParenth = ((Boolean)get("StepExpr.needParenth")).booleanValue();
                RelativePathExpr1_needParenth = StepExpr_needParenth;
                set("RelativePathExpr1.needParenth",new Boolean(RelativePathExpr1_needParenth));
            }
        }
        else if (activeRule.equals("RelativePathExpr1 RelativePathExpr2 backslash StepExpr")) {
            if (attr.equals("RelativePathExpr1.boolExpr")) {
                String RelativePathExpr1_boolExpr = null;
                String StepExpr_boolExpr = (String) get("StepExpr.boolExpr");
                RelativePathExpr1_boolExpr = StepExpr_boolExpr;
                set("RelativePathExpr1.boolExpr",RelativePathExpr1_boolExpr);
            }
            else if (attr.equals("RelativePathExpr1.needParenth")) {
                boolean RelativePathExpr1_needParenth = false;
                boolean StepExpr_needParenth = ((Boolean)get("StepExpr.needParenth")).booleanValue();
                RelativePathExpr1_needParenth = StepExpr_needParenth;
                set("RelativePathExpr1.needParenth",new Boolean(RelativePathExpr1_needParenth));
            }
        }
        else if (activeRule.equals("RelativePathExpr StepExpr")) {
            if (attr.equals("RelativePathExpr.boolExpr")) {
                String RelativePathExpr_boolExpr = null;
                String StepExpr_boolExpr = (String) get("StepExpr.boolExpr");
                RelativePathExpr_boolExpr = StepExpr_boolExpr;
                set("RelativePathExpr.boolExpr",RelativePathExpr_boolExpr);
            }
            else if (attr.equals("RelativePathExpr.needParenth")) {
                boolean RelativePathExpr_needParenth = false;
                boolean StepExpr_needParenth = ((Boolean)get("StepExpr.needParenth")).booleanValue();
                RelativePathExpr_needParenth = StepExpr_needParenth;
                set("RelativePathExpr.needParenth",new Boolean(RelativePathExpr_needParenth));
            }
        }
        else if (activeRule.equals("StepExpr PrimaryExpr")) {
            if (attr.equals("StepExpr.boolExpr")) {
                String StepExpr_boolExpr = null;
                String PrimaryExpr_boolExpr = (String) get("PrimaryExpr.boolExpr");
                StepExpr_boolExpr = PrimaryExpr_boolExpr;
                set("StepExpr.boolExpr",StepExpr_boolExpr);
            }
            else if (attr.equals("StepExpr.needParenth")) {
                boolean StepExpr_needParenth = false;
                boolean PrimaryExpr_needParenth = ((Boolean)get("PrimaryExpr.needParenth")).booleanValue();
                StepExpr_needParenth = PrimaryExpr_needParenth;
                set("StepExpr.needParenth",new Boolean(StepExpr_needParenth));
            }
        }
        else if (activeRule.equals("PrimaryExpr dollar name")) {
            if (attr.equals("PrimaryExpr.boolExpr")) {
                String PrimaryExpr_boolExpr = null;
                String name_string = (String) get("name.string");
                PrimaryExpr_boolExpr = name_string;
                set("PrimaryExpr.boolExpr",PrimaryExpr_boolExpr);
            }
            else if (attr.equals("PrimaryExpr.needParenth")) {
                boolean PrimaryExpr_needParenth = false;
                PrimaryExpr_needParenth = false;
                set("PrimaryExpr.needParenth",new Boolean(PrimaryExpr_needParenth));
            }
        }
        else if (activeRule.equals("PrimaryExpr Literal")) {
            if (attr.equals("PrimaryExpr.boolExpr")) {
                String PrimaryExpr_boolExpr = null;
                String Literal_boolExpr = (String) get("Literal.boolExpr");
                PrimaryExpr_boolExpr = Literal_boolExpr;
                set("PrimaryExpr.boolExpr",PrimaryExpr_boolExpr);
            }
            else if (attr.equals("PrimaryExpr.needParenth")) {
                boolean PrimaryExpr_needParenth = false;
                PrimaryExpr_needParenth = false;
                set("PrimaryExpr.needParenth",new Boolean(PrimaryExpr_needParenth));
            }
        }
        else if (activeRule.equals("PrimaryExpr ParenthesizeExpr")) {
            if (attr.equals("PrimaryExpr.boolExpr")) {
                String PrimaryExpr_boolExpr = null;
                String ParenthesizeExpr_boolExpr = (String) get("ParenthesizeExpr.boolExpr");
                PrimaryExpr_boolExpr = ParenthesizeExpr_boolExpr;
                set("PrimaryExpr.boolExpr",PrimaryExpr_boolExpr);
            }
            else if (attr.equals("PrimaryExpr.needParenth")) {
                boolean PrimaryExpr_needParenth = false;
                boolean ParenthesizeExpr_needParenth = ((Boolean)get("ParenthesizeExpr.needParenth")).booleanValue();
                PrimaryExpr_needParenth = ParenthesizeExpr_needParenth;
                set("PrimaryExpr.needParenth",new Boolean(PrimaryExpr_needParenth));
            }
        }
        else if (activeRule.equals("PrimaryExpr FunctionCall")) {
            if (attr.equals("PrimaryExpr.boolExpr")) {
                String PrimaryExpr_boolExpr = null;
                String FunctionCall_boolExpr = (String) get("FunctionCall.boolExpr");
                PrimaryExpr_boolExpr = FunctionCall_boolExpr;
                set("PrimaryExpr.boolExpr",PrimaryExpr_boolExpr);
            }
            else if (attr.equals("PrimaryExpr.needParenth")) {
                boolean PrimaryExpr_needParenth = false;
                PrimaryExpr_needParenth = false;
                set("PrimaryExpr.needParenth",new Boolean(PrimaryExpr_needParenth));
            }
        }
        else if (activeRule.equals("Literal StringLiteral")) {
            if (attr.equals("Literal.boolExpr")) {
                String Literal_boolExpr = null;
                String StringLiteral_boolExpr = (String) get("StringLiteral.boolExpr");
                Literal_boolExpr = StringLiteral_boolExpr;
                set("Literal.boolExpr",Literal_boolExpr);
            }
            else if (attr.equals("Literal.needParenth")) {
                boolean Literal_needParenth = false;
                Literal_needParenth = false;
                set("Literal.needParenth",new Boolean(Literal_needParenth));
            }
        }
        else if (activeRule.equals("Literal NumericLiteral")) {
            if (attr.equals("Literal.boolExpr")) {
                String Literal_boolExpr = null;
                String NumericLiteral_boolExpr = (String) get("NumericLiteral.boolExpr");
                Literal_boolExpr = NumericLiteral_boolExpr;
                set("Literal.boolExpr",Literal_boolExpr);
            }
            else if (attr.equals("Literal.needParenth")) {
                boolean Literal_needParenth = false;
                Literal_needParenth = false;
                set("Literal.needParenth",new Boolean(Literal_needParenth));
            }
        }
        else if (activeRule.equals("FunctionCall booleanFunc")) {
            if (attr.equals("FunctionCall.boolExpr")) {
                String FunctionCall_boolExpr = null;
                String booleanFunc_string = (String) get("booleanFunc.string");
                FunctionCall_boolExpr = getBool(booleanFunc_string);
                set("FunctionCall.boolExpr",FunctionCall_boolExpr);
            }
            else if (attr.equals("FunctionCall.needParenth")) {
                boolean FunctionCall_needParenth = false;
                FunctionCall_needParenth = false;
                set("FunctionCall.needParenth",new Boolean(FunctionCall_needParenth));
            }
        }
        else if (activeRule.equals("ParenthesizeExpr openingParenth Expr closingParenth")) {
            if (attr.equals("ParenthesizeExpr.boolExpr")) {
                String ParenthesizeExpr_boolExpr = null;
                String Expr_boolExpr = (String) get("Expr.boolExpr");
                ParenthesizeExpr_boolExpr = Expr_boolExpr;
                set("ParenthesizeExpr.boolExpr",ParenthesizeExpr_boolExpr);
            }
            else if (attr.equals("ParenthesizeExpr.needParenth")) {
                boolean ParenthesizeExpr_needParenth = false;
                boolean Expr_needParenth = ((Boolean)get("Expr.needParenth")).booleanValue();
                ParenthesizeExpr_needParenth = Expr_needParenth;
                set("ParenthesizeExpr.needParenth",new Boolean(ParenthesizeExpr_needParenth));
            }
        }
        else if (activeRule.equals("NumericLiteral digits")) {
            if (attr.equals("NumericLiteral.boolExpr")) {
                String NumericLiteral_boolExpr = null;
                String digits_string = (String) get("digits.string");
                NumericLiteral_boolExpr = digits_string;
                set("NumericLiteral.boolExpr",NumericLiteral_boolExpr);
            }
            else if (attr.equals("NumericLiteral.needParenth")) {
                boolean NumericLiteral_needParenth = false;
                NumericLiteral_needParenth = false;
                set("NumericLiteral.needParenth",new Boolean(NumericLiteral_needParenth));
            }
        }
        else if (activeRule.equals("StringLiteral name")) {
            if (attr.equals("StringLiteral.boolExpr")) {
                String StringLiteral_boolExpr = null;
                String name_string = (String) get("name.string");
                StringLiteral_boolExpr = name_string;
                set("StringLiteral.boolExpr",StringLiteral_boolExpr);
            }
            else if (attr.equals("StringLiteral.needParenth")) {
                boolean StringLiteral_needParenth = false;
                StringLiteral_needParenth = false;
                set("StringLiteral.needParenth",new Boolean(StringLiteral_needParenth));
            }
        }
    }

}
