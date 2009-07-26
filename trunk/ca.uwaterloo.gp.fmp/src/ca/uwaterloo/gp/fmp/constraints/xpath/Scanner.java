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

import Data.Automat;
import Data.ScannerInput;
import Data.ScannerScanner;
import Data.State;
import Data.Transition;

/**
 * 
 * @author Vinay Kumar Singh
 *
 */

public class Scanner extends ScannerScanner {

     public Scanner() {
     super("Scanner",new ScannerInput());

     ScannerInput input = super.getScannerInput();
     String[][] verbatims = new String[1][2];

     verbatims[0][0] = "<";

     verbatims[0][1] = ">";

     input.setVerbatims(verbatims);

     String[][] comments = new String[2][2];

     comments[0][0] = "--";

     comments[0][1] = "JACCIE_EndOfLine";

     comments[1][0] = "/*";

     comments[1][1] = "*/";

     input.setComments(comments);


     Automat automat;
     State state;

          automat = new Automat("JACCIE_Separators");
          state= new State(0,".Separators",false);
          state.add(new Transition("9-10,12-13,32",1));
          automat.add(state);
          state= new State(1,"Separators.",true);
          automat.add(state);
          add(automat);

          automat = new Automat("JACCIE_Comments");
          state= new State(0,"(.--|./*)",false);
          state.add(new Transition("47",2));
          state.add(new Transition("45",1));
          automat.add(state);
          state= new State(1,"(-.-|/*)",false);
          state.add(new Transition("45",3));
          automat.add(state);
          state= new State(2,"(--|/.*)",false);
          state.add(new Transition("42",3));
          automat.add(state);
          state= new State(3,"(--|/*).",true);
          automat.add(state);
          add(automat);

          automat = new Automat("JACCIE_Verbatims");
          state= new State(0,"(.<)",false);
          state.add(new Transition("60",1));
          automat.add(state);
          state= new State(1,"(<).",true);
          automat.add(state);
          add(automat);

          automat = new Automat("if");
          state= new State(0,".if",false);
          state.add(new Transition("105",1));
          automat.add(state);
          state= new State(1,"i.f",false);
          state.add(new Transition("102",2));
          automat.add(state);
          state= new State(2,"if.",true);
          automat.add(state);
          add(automat);

          automat = new Automat("then");
          state= new State(0,".then",false);
          state.add(new Transition("116",1));
          automat.add(state);
          state= new State(1,"t.hen",false);
          state.add(new Transition("104",2));
          automat.add(state);
          state= new State(2,"th.en",false);
          state.add(new Transition("101",3));
          automat.add(state);
          state= new State(3,"the.n",false);
          state.add(new Transition("110",4));
          automat.add(state);
          state= new State(4,"then.",true);
          automat.add(state);
          add(automat);

          automat = new Automat("else");
          state= new State(0,".else",false);
          state.add(new Transition("101",1));
          automat.add(state);
          state= new State(1,"e.lse",false);
          state.add(new Transition("108",2));
          automat.add(state);
          state= new State(2,"el.se",false);
          state.add(new Transition("115",3));
          automat.add(state);
          state= new State(3,"els.e",false);
          state.add(new Transition("101",4));
          automat.add(state);
          state= new State(4,"else.",true);
          automat.add(state);
          add(automat);

          automat = new Automat("union");
          state= new State(0,".union",false);
          state.add(new Transition("117",1));
          automat.add(state);
          state= new State(1,"u.nion",false);
          state.add(new Transition("110",2));
          automat.add(state);
          state= new State(2,"un.ion",false);
          state.add(new Transition("105",3));
          automat.add(state);
          state= new State(3,"uni.on",false);
          state.add(new Transition("111",4));
          automat.add(state);
          state= new State(4,"unio.n",false);
          state.add(new Transition("110",5));
          automat.add(state);
          state= new State(5,"union.",true);
          automat.add(state);
          add(automat);

          automat = new Automat("digits");
          state= new State(0,"(.{0-9})[1-*]",false);
          state.add(new Transition("48-57",1));
          automat.add(state);
          state= new State(1,"@({0-9})[1-*]@",false);
          state.setInternState(true);
          state.setLoops(0,0);
          state.setEndlessLoop(true);
          state.setFollowState(0,0);
          state.setFollowState(2,2);
          automat.add(state);
          state= new State(2,"(.{0-9})[1-*].",true);
          state.add(new Transition("48-57",1));
          automat.add(state);
          add(automat);

          automat = new Automat("openingParenth");
          state= new State(0,".(",false);
          state.add(new Transition("40",1));
          automat.add(state);
          state= new State(1,"(.",true);
          automat.add(state);
          add(automat);

          automat = new Automat("closingParenth");
          state= new State(0,".)",false);
          state.add(new Transition("41",1));
          automat.add(state);
          state= new State(1,").",true);
          automat.add(state);
          add(automat);

          automat = new Automat("backslash");
          state= new State(0,"./",false);
          state.add(new Transition("47",1));
          automat.add(state);
          state= new State(1,"/.",true);
          automat.add(state);
          add(automat);

          automat = new Automat("booleanFunc");
          state= new State(0,"(.true()|.false())",false);
          state.add(new Transition("102",2));
          state.add(new Transition("116",1));
          automat.add(state);
          state= new State(1,"(t.rue()|false())",false);
          state.add(new Transition("114",3));
          automat.add(state);
          state= new State(2,"(true()|f.alse())",false);
          state.add(new Transition("97",4));
          automat.add(state);
          state= new State(3,"(tr.ue()|false())",false);
          state.add(new Transition("117",5));
          automat.add(state);
          state= new State(4,"(true()|fa.lse())",false);
          state.add(new Transition("108",7));
          automat.add(state);
          state= new State(5,"(tru.e()|false())",false);
          state.add(new Transition("101",6));
          automat.add(state);
          state= new State(6,"(true.()|false())",false);
          state.add(new Transition("40",9));
          automat.add(state);
          state= new State(7,"(true()|fal.se())",false);
          state.add(new Transition("115",8));
          automat.add(state);
          state= new State(8,"(true()|fals.e())",false);
          state.add(new Transition("101",11));
          automat.add(state);
          state= new State(9,"(true(.)|false())",false);
          state.add(new Transition("41",10));
          automat.add(state);
          state= new State(10,"(true()|false()).",true);
          automat.add(state);
          state= new State(11,"(true()|false.())",false);
          state.add(new Transition("40",12));
          automat.add(state);
          state= new State(12,"(true()|false(.))",false);
          state.add(new Transition("41",10));
          automat.add(state);
          add(automat);

          automat = new Automat("add");
          state= new State(0,".+",false);
          state.add(new Transition("43",1));
          automat.add(state);
          state= new State(1,"+.",true);
          automat.add(state);
          add(automat);

          automat = new Automat("subtract");
          state= new State(0,".-",false);
          state.add(new Transition("45",1));
          automat.add(state);
          state= new State(1,"-.",true);
          automat.add(state);
          add(automat);

          automat = new Automat("divide");
          state= new State(0,".div",false);
          state.add(new Transition("100",1));
          automat.add(state);
          state= new State(1,"d.iv",false);
          state.add(new Transition("105",2));
          automat.add(state);
          state= new State(2,"di.v",false);
          state.add(new Transition("118",3));
          automat.add(state);
          state= new State(3,"div.",true);
          automat.add(state);
          add(automat);

          automat = new Automat("multiply");
          state= new State(0,".*",false);
          state.add(new Transition("42",1));
          automat.add(state);
          state= new State(1,"*.",true);
          automat.add(state);
          add(automat);

          automat = new Automat("dollar");
          state= new State(0,".$",false);
          state.add(new Transition("36",1));
          automat.add(state);
          state= new State(1,"$.",true);
          automat.add(state);
          add(automat);

          automat = new Automat("some");
          state= new State(0,".some",false);
          state.add(new Transition("115",1));
          automat.add(state);
          state= new State(1,"s.ome",false);
          state.add(new Transition("111",2));
          automat.add(state);
          state= new State(2,"so.me",false);
          state.add(new Transition("109",3));
          automat.add(state);
          state= new State(3,"som.e",false);
          state.add(new Transition("101",4));
          automat.add(state);
          state= new State(4,"some.",true);
          automat.add(state);
          add(automat);

          automat = new Automat("every");
          state= new State(0,".every",false);
          state.add(new Transition("101",1));
          automat.add(state);
          state= new State(1,"e.very",false);
          state.add(new Transition("118",2));
          automat.add(state);
          state= new State(2,"ev.ery",false);
          state.add(new Transition("101",3));
          automat.add(state);
          state= new State(3,"eve.ry",false);
          state.add(new Transition("114",4));
          automat.add(state);
          state= new State(4,"ever.y",false);
          state.add(new Transition("121",5));
          automat.add(state);
          state= new State(5,"every.",true);
          automat.add(state);
          add(automat);

          automat = new Automat("satisfies");
          state= new State(0,".satisfies",false);
          state.add(new Transition("115",1));
          automat.add(state);
          state= new State(1,"s.atisfies",false);
          state.add(new Transition("97",2));
          automat.add(state);
          state= new State(2,"sa.tisfies",false);
          state.add(new Transition("116",3));
          automat.add(state);
          state= new State(3,"sat.isfies",false);
          state.add(new Transition("105",4));
          automat.add(state);
          state= new State(4,"sati.sfies",false);
          state.add(new Transition("115",5));
          automat.add(state);
          state= new State(5,"satis.fies",false);
          state.add(new Transition("102",6));
          automat.add(state);
          state= new State(6,"satisf.ies",false);
          state.add(new Transition("105",7));
          automat.add(state);
          state= new State(7,"satisfi.es",false);
          state.add(new Transition("101",8));
          automat.add(state);
          state= new State(8,"satisfie.s",false);
          state.add(new Transition("115",9));
          automat.add(state);
          state= new State(9,"satisfies.",true);
          automat.add(state);
          add(automat);

          automat = new Automat("in");
          state= new State(0,".in",false);
          state.add(new Transition("105",1));
          automat.add(state);
          state= new State(1,"i.n",false);
          state.add(new Transition("110",2));
          automat.add(state);
          state= new State(2,"in.",true);
          automat.add(state);
          add(automat);

          automat = new Automat("implication");
          state= new State(0,".->",false);
          state.add(new Transition("45",1));
          automat.add(state);
          state= new State(1,"-.>",false);
          state.add(new Transition("62",2));
          automat.add(state);
          state= new State(2,"->.",true);
          automat.add(state);
          add(automat);

          automat = new Automat("equivalence");
          state= new State(0,".<->",false);
          state.add(new Transition("60",1));
          automat.add(state);
          state= new State(1,"<.->",false);
          state.add(new Transition("45",2));
          automat.add(state);
          state= new State(2,"<-.>",false);
          state.add(new Transition("62",3));
          automat.add(state);
          state= new State(3,"<->.",true);
          automat.add(state);
          add(automat);

          automat = new Automat("comparison");
          state= new State(0,"(.<|.<=|.>|.>=|.=|.<>)",false);
          state.add(new Transition("61",3));
          state.add(new Transition("62",2));
          state.add(new Transition("60",1));
          automat.add(state);
          state= new State(1,"(<|<.=|>|>=|=|<.>).",true);
          state.add(new Transition("61,62",3));
          automat.add(state);
          state= new State(2,"(<|<=|>|>.=|=|<>).",true);
          state.add(new Transition("61",3));
          automat.add(state);
          state= new State(3,"(<|<=|>|>=|=|<>).",true);
          automat.add(state);
          add(automat);

          automat = new Automat("and");
          state= new State(0,".and",false);
          state.add(new Transition("97",1));
          automat.add(state);
          state= new State(1,"a.nd",false);
          state.add(new Transition("110",2));
          automat.add(state);
          state= new State(2,"an.d",false);
          state.add(new Transition("100",3));
          automat.add(state);
          state= new State(3,"and.",true);
          automat.add(state);
          add(automat);

          automat = new Automat("or");
          state= new State(0,".or",false);
          state.add(new Transition("111",1));
          automat.add(state);
          state= new State(1,"o.r",false);
          state.add(new Transition("114",2));
          automat.add(state);
          state= new State(2,"or.",true);
          automat.add(state);
          add(automat);

          automat = new Automat("except");
          state= new State(0,".except",false);
          state.add(new Transition("101",1));
          automat.add(state);
          state= new State(1,"e.xcept",false);
          state.add(new Transition("120",2));
          automat.add(state);
          state= new State(2,"ex.cept",false);
          state.add(new Transition("99",3));
          automat.add(state);
          state= new State(3,"exc.ept",false);
          state.add(new Transition("101",4));
          automat.add(state);
          state= new State(4,"exce.pt",false);
          state.add(new Transition("112",5));
          automat.add(state);
          state= new State(5,"excep.t",false);
          state.add(new Transition("116",6));
          automat.add(state);
          state= new State(6,"except.",true);
          automat.add(state);
          add(automat);

          automat = new Automat("intersect");
          state= new State(0,".intersect",false);
          state.add(new Transition("105",1));
          automat.add(state);
          state= new State(1,"i.ntersect",false);
          state.add(new Transition("110",2));
          automat.add(state);
          state= new State(2,"in.tersect",false);
          state.add(new Transition("116",3));
          automat.add(state);
          state= new State(3,"int.ersect",false);
          state.add(new Transition("101",4));
          automat.add(state);
          state= new State(4,"inte.rsect",false);
          state.add(new Transition("114",5));
          automat.add(state);
          state= new State(5,"inter.sect",false);
          state.add(new Transition("115",6));
          automat.add(state);
          state= new State(6,"inters.ect",false);
          state.add(new Transition("101",7));
          automat.add(state);
          state= new State(7,"interse.ct",false);
          state.add(new Transition("99",8));
          automat.add(state);
          state= new State(8,"intersec.t",false);
          state.add(new Transition("116",9));
          automat.add(state);
          state= new State(9,"intersect.",true);
          automat.add(state);
          add(automat);

          automat = new Automat("not");
          state= new State(0,".not",false);
          state.add(new Transition("110",1));
          automat.add(state);
          state= new State(1,"n.ot",false);
          state.add(new Transition("111",2));
          automat.add(state);
          state= new State(2,"no.t",false);
          state.add(new Transition("116",3));
          automat.add(state);
          state= new State(3,"not.",true);
          automat.add(state);
          add(automat);

          automat = new Automat("terminatingCharacter");
          state= new State(0,".;",false);
          state.add(new Transition("59",1));
          automat.add(state);
          state= new State(1,";.",true);
          automat.add(state);
          add(automat);

          automat = new Automat("name");
          state= new State(0,"(.{a-z}|.{A-Z})(({a-z}|{A-Z})|{0-9})[0-*]",false);
          state.add(new Transition("97-122,65-90",1));
          automat.add(state);
          state= new State(1,"({a-z}|{A-Z})((.{a-z}|.{A-Z})|.{0-9})[0-*].",true);
          state.add(new Transition("97-122,65-90,48-57",2));
          automat.add(state);
          state= new State(2,"({a-z}|{A-Z})@(({a-z}|{A-Z})|{0-9})[0-*]@",false);
          state.setInternState(true);
          state.setLoops(0,0);
          state.setEndlessLoop(true);
          state.setFollowState(0,0);
          state.setFollowState(2,1);
          automat.add(state);
          add(automat);

     } // End of constructor

} // End of class Scanner

