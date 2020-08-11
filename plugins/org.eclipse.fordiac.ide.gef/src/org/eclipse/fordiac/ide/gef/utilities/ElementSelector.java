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
package org.eclipse.fordiac.ide.gef.utilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.fordiac.ide.gef.AdvancedScrollingGraphicalViewer;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

public final class ElementSelector {
	/**
	 * Selects the provided objects in the current editor
	 *
	 * @param viewObjects list with objects to select
	 */
	public static void selectViewObjects(Collection<? extends Object> viewObjects) {
		IWorkbenchPart part = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart();
		GraphicalViewer viewer = part.getAdapter(GraphicalViewer.class);
		if (viewer != null) {
			viewer.flush();
			List<EditPart> editParts = getSelectableEditParts(viewer, viewObjects);
			if (!editParts.isEmpty()) {
				viewer.setSelection(new StructuredSelection(editParts));
				if (viewer instanceof AdvancedScrollingGraphicalViewer) {
					((AdvancedScrollingGraphicalViewer) viewer).revealEditPart(editParts.get(0));
				} else {
					viewer.reveal(editParts.get(0));
				}
			}
		}

	}

	private static List<EditPart> getSelectableEditParts(GraphicalViewer viewer, Collection<?> viewObjects) {
		List<EditPart> selectableChildren = new ArrayList<>();
		List<?> children = viewer.getContents().getChildren();
		for (Object view : viewObjects) {
			for (Object child : children) {
				if ((child instanceof EditPart) && ((EditPart) child).getModel().equals(view)) {
					selectableChildren.add((EditPart) child);
					break;
				}
			}
		}
		return selectableChildren;
	}

	private ElementSelector() {
		throw new UnsupportedOperationException("do not instantiate this class");
	}
}
