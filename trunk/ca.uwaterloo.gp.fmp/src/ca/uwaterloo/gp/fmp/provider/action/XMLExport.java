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
package ca.uwaterloo.gp.fmp.provider.action;

import java.util.Iterator;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import ca.uwaterloo.gp.fmp.ConfigState;
import ca.uwaterloo.gp.fmp.Feature;
import ca.uwaterloo.gp.fmp.FeatureGroup;
import ca.uwaterloo.gp.fmp.Node;
import ca.uwaterloo.gp.fmp.Reference;
import ca.uwaterloo.gp.fmp.system.RoleQuery;


/**
 * @author Michal Antkiewicz <mantkiew@swen.uwaterloo.ca>
 * Exports the subtree of selected elements. 
 * Does not include configurations.
 */
public class XMLExport extends Action {
	ISelectionProvider selectionProvider;
	Node object;
	Shell shell;
	Text fText;
	
	public XMLExport(Node object, Shell shell) {
		super("XML Export");
		this.object  = object;
		this.shell = shell;
	}
	public void run() {
		StringBuffer output = new StringBuffer();
		
		// run recursion
		exportNode(output, object, 0);
		
		final String text = output.toString();

		//System.out.println(text);
		
		Dialog dialog = new MessageDialog(shell, "XML Export", null, null, MessageDialog.NONE, new String[] {"OK"}, 0) {
			protected Control createDialogArea(Composite parent) {
				Color white = shell.getDisplay().getSystemColor(SWT.COLOR_WHITE);
				parent.setBackground(white);
				// Composite composite = (Composite) super.createDialogArea(parent);
				// composite.setBackground(white);
				// Text field				
				fText= new Text(parent, SWT.MULTI);
				fText.setEditable(false);
				fText.setText(text);
				fText.selectAll();
				fText.setBackground(white);
				return fText;
			}
		};
		/*
		 * Michal: unfortunately this only works in Eclipse 3.2
		PopupDialog dialog = new PopupDialog(shell, PopupDialog.INFOPOPUPRESIZE_SHELLSTYLE, true, false, false, false, 
				"XML Export", "Use <CTRL>-C to copy to clipboard, <ESC> to dismiss.") {
			protected Control createDialogArea(Composite parent) {
				Composite composite = (Composite) super.createDialogArea(parent);
				// Text field				
				fText= new Text(composite, SWT.MULTI);
				fText.setEditable(false);
				fText.setText(text);
				fText.selectAll();
				return composite;
			}
		};*/
		dialog.open();
    }
	private void exportNode(StringBuffer output, Node node, int indent) {
		boolean inConfiguration = RoleQuery.INSTANCE.getLocationType(node) == RoleQuery.CONFIGURATION;
		
		boolean proceed = outputNode(output,	node, indent, inConfiguration);
		if (proceed) {
			for (Iterator i = node.getChildren().iterator(); i.hasNext(); ) {
				Node aux = (Node) i.next();
				exportNode(output, aux, indent + 1);
			}
			closeNode(output, node, indent, inConfiguration);
		}
	}
	private boolean outputNode(StringBuffer output, Node node, int indent, boolean inConfiguration) {
		int min = node.getMin();
		int max = node.getMax();
		String id = node.getId();
		if (node instanceof Feature) {
			Feature feature = (Feature) node;
			boolean selected = 
				feature.getState() == ConfigState.USER_SELECTED_LITERAL || 
				feature.getState() == ConfigState.MACHINE_SELECTED_LITERAL ||
				(min == 1 && max == 1);
			if (inConfiguration && !selected)
				return false;
			outputIndent(output, indent);
			output.append("<feature ");
			if (!inConfiguration)
				output.append("min=\"" + min + "\" max=\"" + max + "\"");
			output.append(" name=\"" + feature.getName() + "\" type=\"" + feature.getValueType().getName() + "\"");
			if (feature.getTypedValue() != null)
				output.append(" value=\""+ feature.getTypedValue().getValueToString() + "\"");
			 output.append(" id=\"" + id + "\">\n");
		} else if (node instanceof FeatureGroup && !inConfiguration) {
			outputIndent(output, indent);
			output.append("<featureGroup min=\"" + min + "\" max=\"" + max + "\" id=\"" + id + "\">\n");
		} else if (node instanceof Reference  && !inConfiguration) {
			outputIndent(output, indent);
			output.append("<reference min=\"" + min + "\" max=\"" + max + "\" id=\"" + id + "\">\n");
		}
		return true;
	}
	private void closeNode(StringBuffer output, Node node, int indent, boolean inConfiguration) {
		if (node instanceof Feature) {
			Feature feature = (Feature) node;
			boolean selected = 
				feature.getState() == ConfigState.USER_SELECTED_LITERAL || 
				feature.getState() == ConfigState.MACHINE_SELECTED_LITERAL ||
				(node.getMin() == 1 && node.getMax() == 1);
			if (inConfiguration && !selected)
				return;
			outputIndent(output, indent);
			output.append("</feature>\n");
		} else if (node instanceof FeatureGroup  && !inConfiguration) {
			outputIndent(output, indent);
			output.append("</featureGroup>\n");
		} else if (node instanceof Reference && !inConfiguration) {
			outputIndent(output, indent);
			output.append("</reference>\n");
		}
	}
	/**
	 * @param output
	 * @param indent
	 */
	private void outputIndent(StringBuffer output, int indent) {
		for (int i = 0; i < indent; i++)
			output.append("  ");
	}
}
