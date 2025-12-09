/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.tools;

import java.util.List;

import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.tools.SelectEditPartTracker;
import org.eclipse.swt.SWT;

public class ConnectionSelectEditPartTracker extends SelectEditPartTracker {
	public ConnectionSelectEditPartTracker(final ConnectionEditPart owner) {
		super(owner);
	}

	@Override
	protected ConnectionEditPart getSourceEditPart() {
		return (ConnectionEditPart) super.getSourceEditPart();
	}

	@Override
	protected void performSelection() {
		if (hasSelectionOccurred()) {
			return;
		}
		setFlag(FLAG_SELECTION_PERFORMED, true);

		final EditPartViewer viewer = getCurrentViewer();
		final List<ConnectionEditPart> connections = ColLocatedConnectionFinder
				.getCoLocatedConnections(getSourceEditPart(), viewer, getLocation());
		final List<? extends EditPart> selectedObjects = viewer.getSelectedEditParts();

		boolean first = true;
		for (final ConnectionEditPart con : connections) {
			if (getCurrentInput().isModKeyDown(SWT.MOD1)) {
				if (selectedObjects.contains(con)) {
					viewer.deselect(con);
				} else {
					viewer.appendSelection(con);
				}
			} else if (getCurrentInput().isShiftKeyDown()) {
				viewer.appendSelection(con);
			} else if (first) {
				viewer.select(con);
				first = false;
			} else {
				viewer.appendSelection(con);
			}
		}

	}

}