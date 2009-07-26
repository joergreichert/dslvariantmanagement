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

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import ca.uwaterloo.gp.fmp.Constraint;



/**
 * Label provider for the TableViewerExample
 * 
 * @see org.eclipse.jface.viewers.LabelProvider 
 */
public class ConstraintLabelProvider 
	extends LabelProvider
	implements ITableLabelProvider
{
    
	protected ConstraintsList constraintsList;

    public ConstraintLabelProvider(ConstraintsList constraintsList){
        this.constraintsList = constraintsList;
    }
	
    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
     */
    public String getColumnText(Object constraint, int columnIndex) {
        switch (columnIndex)
		{
        	case 1:
        		return ((Constraint)constraint).getText();
        }
        return "";
    }
    
	public Image getColumnImage(Object constraint, int columnIndex)
	{
		if (columnIndex == 0){
			ImageDescriptor id = getImageDescriptor("elcl16/showcomplete_tsk.gif");
			if (id != null)
				return id.createImage();
		}
	    return null;
	}
	
    /**
     * Taken from MetaConfigurationView. Returns the image descriptor with the
     * given relative path.
     */
    protected ImageDescriptor getImageDescriptor(String relativePath) {
    	return AbstractUIPlugin.imageDescriptorFromPlugin("org.eclipse.ui", "icons/full/" + relativePath);
    }

}
