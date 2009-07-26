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

import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import Data.CompilerAction;
import Data.EvaluatorTree;
import Data.SICParserInput;

/**
 * @author Chang Hwan Peter Kim <chpkim@swen.uwaterloo.ca>
 */

public class Compile extends Observable
              implements CompilerAction, Runnable {

    private String input = "";

    public void setInput(Object o) {
        inputTree = null;
        input = (String)o;
    }

    public void run() {
        Scanner scanner =  new Scanner();
        scanner.getScannerInput().setInput(input);
        try {
            scanner.resetScanner();
            scanner.scanAll();
        } catch (Exception e) {
        	e.printStackTrace();
             update("Error while scanning: "+
                    e.toString());
        }
        update("Scanner finished.");

        SICParserInput pinput = new SICParserInput();
        Vector scannerResult = scanner.getResult();

        for (int i=0; i<scannerResult.size(); i++)
            pinput.addScannerOutput((String)
                scannerResult.elementAt(i));
        Parser parser =  new Parser();
        parser.start(pinput);
        update("Parser finished.");

        einput = parser.getResultAsString();
        Evaluator evaluator = new Evaluator();
        if (inputTree == null) evaluator.setTree(einput);
            else evaluator.setTree(inputTree);
        evaluator.start();
        inputTree = evaluator.getTree();

        update("Evaluator finished.");

        update("Finished.");
    }

    public Object getResult() {
        return inputTree;
    }

    private EvaluatorTree inputTree = null;
    private String einput = "";

    private Observer observer = null;

    public void setObserver(Observer o) {
         observer = o;
    }

    private void update(String message) {
        if (observer != null) observer.update(this,message);
    }
}