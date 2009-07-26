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
package ca.uwaterloo.gp.fmp.constraints.ui;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Text;

import ca.uwaterloo.gp.fmp.presentation.MultiLineTableCellEditor;


/**
 * @author Chang Hwan Peter Kim <chpkim@swen.uwaterloo.ca>
 *
 */
public class InsertConstraintTemplateAction extends Action{
	protected String constraintTemplate;

	/**
	 * The CellEditor to insert constraint template for.
	 */
	protected MultiLineTableCellEditor cellEditor;
	
	/**
	 * @param domain
	 */
	public InsertConstraintTemplateAction(MultiLineTableCellEditor cellEditor, String constraintTitle, String constraintTemplate)
	{
		setText(constraintTitle);
		this.constraintTemplate = constraintTemplate;
		this.cellEditor = cellEditor;
	}
	
	public void run()
	{
		Object value = cellEditor.getValue();
		if(value != null)
		{
			if(cellEditor.getControl() instanceof Text)
			{
				// We handle here the actual insertion into the cell editor control
				// We make sure that selected text is overriden by the constraint insertion
				Text textControl = cellEditor.getTextControl();
				Point selectionPoint = textControl.getSelection();
				
				String text = value.toString();
				// The part remaining before the constraint insertion
				String beginPart = "";
				if(selectionPoint.x > 0)
					beginPart = text.substring(0, selectionPoint.x);
				// The part remaining after the constraint insertion
				String endPart = "";
				if(selectionPoint.y < text.length())
					endPart = text.substring(selectionPoint.y);
				
				cellEditor.setValue(beginPart + constraintTemplate + endPart);
			}
			else
				cellEditor.setValue(value.toString()+constraintTemplate);
		}
	}
}
