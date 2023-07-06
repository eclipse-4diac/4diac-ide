/*******************************************************************************
 * Copyright (c) 2013 fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECStateEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.ui.actions.SelectAllAction;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchPart;

public class ECCSelectAllAction extends SelectAllAction {

	private final IWorkbenchPart part;

	public ECCSelectAllAction(final IWorkbenchPart part) {
		super(part);
		this.part = part;
	}

	/** Selects all ECC State and Transition edit parts in the active workbench part. */
	@Override
	public void run() {
		final GraphicalViewer viewer = part.getAdapter(GraphicalViewer.class);
		if (viewer != null) {
			viewer.setSelection(new StructuredSelection(getSelectableEditParts(viewer)));
		}
	}

	private static List<EditPart> getSelectableEditParts(final GraphicalViewer viewer) {
		final List<EditPart> selectableChildren = new ArrayList<>();

		final List<? extends EditPart> children = viewer.getContents().getChildren();

		for (final EditPart child : children) {
			if ((child instanceof final ECStateEditPart ecSateEP) && child.isSelectable()) {
				selectableChildren.add(child);
				selectableChildren.addAll(ecSateEP.getSourceConnections());
			}
		}
		return selectableChildren;
	}

}
