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
package ca.uwaterloo.gp.fmp.presentation;


import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableTreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.part.ViewPart;

import ca.uwaterloo.gp.fmp.ConfigState;
import ca.uwaterloo.gp.fmp.Feature;
import ca.uwaterloo.gp.fmp.FeatureGroup;
import ca.uwaterloo.gp.fmp.Node;
import ca.uwaterloo.gp.fmp.Reference;
import ca.uwaterloo.gp.fmp.TypedValue;
import ca.uwaterloo.gp.fmp.ValueType;
import ca.uwaterloo.gp.fmp.provider.fmpEditPlugin;

/**
 * @author Michal Antkiewicz <mantkiew@swen.uwaterloo.ca>
 */
public class FeatureComparisonView extends ViewPart {
	private TableTreeViewer viewer;

	/*
	 * The content provider class is responsible for
	 * providing objects to the view. It can wrap
	 * existing objects in adapters or simply return
	 * objects as-is. These objects may be sensitive
	 * to the current input of the view, or ignore
	 * it and always show the same content 
	 * (like Task List, for example).
	 */
	 
	class ViewContentProvider implements ITreeContentProvider {
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}
		public void dispose() {
		}
		public Object[] getElements(Object parent) {
			return ((Node) parent).getChildren().toArray();
		}
		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
		 */
		public Object[] getChildren(Object parentElement) {
			return ((Node) parentElement).getChildren().toArray();
		}
		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
		 */
		public Object getParent(Object element) {
			return ((Node) element).eContainer();
		}
		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
		 */
		public boolean hasChildren(Object element) {
			return !((Node) element).getChildren().isEmpty();
		}
	}
	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		public String getColumnText(Object obj, int index) {
			if (index == 0) {
				if (obj instanceof Feature)
					return ((Feature) obj).getName();
				else if (obj instanceof FeatureGroup) {
					FeatureGroup fg = (FeatureGroup) obj;
					int min = fg.getMin();
					int max = fg.getMax();
					return "<" + min + "-" + max + ">";
				}
				else if (obj instanceof Reference)
					return "ref: " + ((Reference) obj).getFeature().getName();
				else
					return "unknown";
			}
			else if (obj instanceof Feature){
				Feature feature = (Feature) obj;
				if (index-1 < feature.getConfs().size()) {
					Feature conf = (Feature) feature.getConfs().get(index-1);
					TypedValue value = conf.getTypedValue();
					if (value != null) {
						return feature.getValueType() != ValueType.FEATURE_LITERAL ? 
								value.getValueToString() : 
								((Feature)value.getFeatureValue()).getName();
					}
				}
			}
			return " ";
		}
		public Image getColumnImage(Object obj, int index) {
			if (obj instanceof Feature && index > 0){
				Feature feature = (Feature) obj;
				if (index-1 < feature.getConfs().size()) {
					Feature conf = (Feature) feature.getConfs().get(index-1);
					if (conf.getState() == ConfigState.USER_SELECTED_LITERAL ||	conf.getState() == ConfigState.MACHINE_SELECTED_LITERAL ||
					    (conf.getMin() == 1 && conf.getMax() == 1)) {
						// check if has value
						if (conf.getTypedValue() == null)
							return  ExtendedImageRegistry.getInstance().getImage(fmpEditPlugin.INSTANCE.getImage("full/obj16/Tick"));
						// else
						// show the value!
					}
				}
			}
			return null;
		}
		public Image getImage(Object obj) {
			return null;
		}
	}

	/**
	 * The constructor.
	 */
	public FeatureComparisonView() {
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		viewer = new TableTreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.getTableTree().setLayoutData(new GridData(GridData.FILL_BOTH));
		viewer.setInput(null);
		Table table = viewer.getTableTree().getTable();
		
		new TableColumn(table, SWT.NONE).setText("Features");
	}
	private void showMessage(String message) {
		MessageDialog.openInformation(
			viewer.getControl().getShell(),
			"Feature Comparison",
			message);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
	public void setInput(Object root) {
		viewer.setInput(root);
		
		Feature feature = (Feature) root;
		int size = feature.getConfigurations().size()+1;
		Table table = viewer.getTableTree().getTable();
		
		int colCount = table.getColumnCount();
		for (int i = 1; i < size; i++) {
			if (colCount <= i) {
				Feature configuration = (Feature) feature.getConfigurations().get(i-1);
				String label = null;
				if (configuration.getValueType() != ValueType.NONE_LITERAL) {
					TypedValue value = configuration.getTypedValue();
					if (value != null)
						label = value.getValueToString();
				}
				if (label == null)
					label = Integer.toString(i);
				new TableColumn(table, SWT.CENTER).setText(label);
				TableColumn col = table.getColumn(i);
				col.setAlignment(SWT.CENTER);
			}
		}
		if (colCount > size)
			for (int i = size; i < colCount; i++) {
				table.getColumn(i).setText("");
			}
		
		viewer.expandAll();
		
//		 Pack the columns
	    for (int i = 0, n = table.getColumnCount(); i < n; i++) {
	      table.getColumn(i).pack();
	    }

	    // Turn on the header and the lines
	    table.setHeaderVisible(true);
	    table.setLinesVisible(true);


	    // Scroll to top
	    viewer.reveal(viewer.getElementAt(0));
	    viewer.refresh(true);
	}
}