/*******************************************************************************
 * Copyright (c) 2013 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECStateEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.ui.actions.SelectAllAction;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchPart;

public class ECCSelectAllAction extends SelectAllAction {
	
	private IWorkbenchPart part;

	public ECCSelectAllAction(IWorkbenchPart part) {
		super(part);
		this.part = part;
	}
	
	/**
	 * Selects all ECC State and Transition edit parts in the active workbench part.
	 */
	@Override
	public void run() {
		GraphicalViewer viewer = part.getAdapter(GraphicalViewer.class);
		if (viewer != null) {
			viewer.setSelection(new StructuredSelection(
					getSelectableEditParts(viewer)));
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List getSelectableEditParts(GraphicalViewer viewer) {
		List selectableChildren = new ArrayList();
		List children = viewer.getContents().getChildren();
		for (Iterator iter = children.iterator(); iter.hasNext();) {
			Object child = iter.next();
			if (child instanceof ECStateEditPart) {
				EditPart childPart = (EditPart) child;
				if (childPart.isSelectable() == true) {
					selectableChildren.add(childPart);
					selectableChildren.addAll(((ECStateEditPart)childPart).getSourceConnections());
				}
			}
		}
		
		return selectableChildren;
	}

}
