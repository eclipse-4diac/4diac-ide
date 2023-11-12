/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.application.dnd;

import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.gef.handlers.AdvancedGraphicalViewerKeyHandler;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.dnd.AbstractTransferDragSourceListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DragSourceEvent;

public class ConnectionDragSourceListener extends AbstractTransferDragSourceListener {

	public ConnectionDragSourceListener(final EditPartViewer viewer) {
		super(viewer, ConnSourceTransfer.getInstance());
		Assert.isTrue(viewer.getKeyHandler() instanceof AdvancedGraphicalViewerKeyHandler);
	}

	@Override
	public void dragStart(final DragSourceEvent event) {
		final InterfaceEditPart selectedInterfaceEditPart = getSelectedInterfaceEditPart();

		if (!isAltKeyPressed() || selectedInterfaceEditPart == null) {
			event.doit = false;
			return;
		}
		ConnSourceTransfer.getInstance().setObject(selectedInterfaceEditPart);
	}

	@Override
	public void dragSetData(final DragSourceEvent event) {
		event.data = getSelectedInterfaceEditPart();
	}

	@Override
	public void dragFinished(final DragSourceEvent event) {
		super.dragFinished(event);
		ConnSourceTransfer.getInstance().setObject(null);
	}

	private InterfaceEditPart getSelectedInterfaceEditPart() {
		final List<? extends EditPart> selectedEditParts = getViewer().getSelectedEditParts();
		if (selectedEditParts.size() == 1 && selectedEditParts.get(0) instanceof final InterfaceEditPart iep) {
			return iep;
		}
		return null;
	}

	private boolean isAltKeyPressed() {
		final AdvancedGraphicalViewerKeyHandler keyHandler = (AdvancedGraphicalViewerKeyHandler) getViewer()
				.getKeyHandler();
		return keyHandler.getCurrentKeyCode() == SWT.ALT;
	}

}
