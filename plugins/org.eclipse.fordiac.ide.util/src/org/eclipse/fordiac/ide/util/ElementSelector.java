/*******************************************************************************
 * Copyright (c) 2013, 2017 AIT, fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Filip Adren, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

public class ElementSelector {
	/**
	 * Selects the provided objects in the current editor
	 * 
	 * @param viewObjects list with objects to select
	 */
	@SuppressWarnings("rawtypes")
	public void selectViewObjects(Collection viewObjects) {
		IWorkbenchPart part = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart();
		GraphicalViewer viewer = part.getAdapter(GraphicalViewer.class);
		if (viewer != null) {
			viewer.setSelection(new StructuredSelection(getSelectableEditParts(viewer, viewObjects)));
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List getSelectableEditParts(GraphicalViewer viewer, Collection viewObjects) {
		List selectableChildren = new ArrayList();
		List children = viewer.getContents().getChildren();
		for (Iterator viewIter = viewObjects.iterator(); viewIter.hasNext();) {
			Object view = viewIter.next();
			for (Iterator iter = children.iterator(); iter.hasNext();) {
				Object child = iter.next();
				if (child instanceof EditPart) {
					EditPart childPart = (EditPart) child;
					if (childPart.getModel().equals(view)) {
						selectableChildren.add(childPart);
						break;
					}
				}
			}
		}

		return selectableChildren;
	}
}
