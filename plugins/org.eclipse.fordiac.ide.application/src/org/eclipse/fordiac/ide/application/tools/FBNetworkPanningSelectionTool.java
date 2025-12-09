/*******************************************************************************
 * Copyright (c) 2008, 2022 Profactor GmbH, TU Wien ACIN, AIT, fortiss GmbH,
 * 							Johannes Kepler University,
 *                          Primetals Technologies Germany GmbH
 *                          Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Matthias Plasch, Filip Andren,
 *   Monika Wenger
 *   - initial API and implementation and/or initial documentation
 *   Alois Zoitl - fixed copy/paste handling
 *               - extracted FBNetworkRootEditPart from FBNetworkEditor
 *               - extracted panning and selection tool added inline connection
 *                 creation
 *               - added checking code to deactivate connection creation when alt
 *                 key is not pressed anymore
 *               - show however feedback for collocated connections
 *               - extracted inline connection creation to be used for connection
 *                 duplication as well
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.tools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.gef.tools.AdvancedPanningSelectionTool;
import org.eclipse.fordiac.ide.gef.tools.InlineConnectionCreationTool;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.requests.SelectionRequest;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseEvent;

public class FBNetworkPanningSelectionTool extends AdvancedPanningSelectionTool {

	/**
	 * Key to indicate that connection creation mode should be activated.
	 *
	 * The current default is on most system the Ctrl key.
	 */
	private static final int CONNECTION_CREATION_MOD_KEY = SWT.MOD1;

	private static final int LEFT_MOUSE = 1;
	private static final double TYPE_DISTANCE = 10.0; // the max distance the mouse may move between left click and
	// typing
	private org.eclipse.draw2d.geometry.Point lastLeftClick = new org.eclipse.draw2d.geometry.Point(0, 0);
	private InlineConnectionCreationTool connectionCreationTool;

	@Override
	public void mouseDown(final MouseEvent me, final EditPartViewer viewer) {
		if (!checkConnCreationState(me.stateMask)) {
			super.mouseDown(me, viewer);
		}
	}

	@Override
	public void mouseUp(final MouseEvent me, final EditPartViewer viewer) {
		if (checkConnCreationState(me.stateMask)) {
			connectionCreationTool.mouseUp(me, viewer);
		} else {
			if (LEFT_MOUSE == me.button) {
				lastLeftClick = getLocation();
			}
			super.mouseUp(me, viewer);
		}
	}

	@Override
	public void keyDown(final KeyEvent evt, final EditPartViewer viewer) {
		if ((Character.isLetterOrDigit(evt.character)) && (TYPE_DISTANCE > getLocation().getDistance(lastLeftClick))) {
			final EditPart editPart = viewer.findObjectAt(getLocation());
			if (null != editPart) {
				final SelectionRequest request = new SelectionRequest();
				request.setLocation(lastLeftClick);
				request.setType(RequestConstants.REQ_OPEN);
				final Map<String, String> map = new HashMap<>();
				map.put(String.valueOf(evt.character), String.valueOf(evt.character));
				request.setExtendedData(map);
				editPart.performRequest(request);
				return;
			}
		}
		checkConnCreationState(evt.keyCode);
		super.keyDown(evt, viewer);
	}

	@Override
	public void mouseMove(final MouseEvent me, final EditPartViewer viewer) {
		// the super call has to be first so that the target editpart is updated
		// accordingly
		super.mouseMove(me, viewer);
		if (checkConnCreationState(me.stateMask)) {
			connectionCreationTool.mouseDrag(me, viewer);
		}
	}

	@Override
	public void keyUp(final KeyEvent evt, final EditPartViewer viewer) {
		if ((evt.keyCode == CONNECTION_CREATION_MOD_KEY) && (null != connectionCreationTool)) {
			deactivateConnectionCreation();
		}
		super.keyUp(evt, viewer);
	}

	@Override
	protected void showTargetFeedback() {
		if (getTargetEditPart() instanceof final ConnectionEditPart connEP) {
			showConnectionTargetFeedback(connEP);
		}
		super.showTargetFeedback();
	}

	@Override
	protected void eraseTargetFeedback() {
		if (getTargetEditPart() instanceof final ConnectionEditPart connEP) {
			eraseConnectionTargetFeedback(connEP);
		}
		super.eraseTargetFeedback();
	}

	private void showConnectionTargetFeedback(final ConnectionEditPart targetEditPart) {
		final EditPartViewer viewer = getCurrentViewer();
		final List<ConnectionEditPart> connections = ColLocatedConnectionFinder.getCoLocatedConnections(targetEditPart,
				viewer, getLocation());
		connections.forEach(con -> con.showTargetFeedback(getTargetRequest()));
	}

	private void eraseConnectionTargetFeedback(final ConnectionEditPart targetEditPart) {
		final EditPartViewer viewer = getCurrentViewer();
		final List<ConnectionEditPart> connections = ColLocatedConnectionFinder
				.getLeftCoLocatedConnections(targetEditPart, viewer, getLocation());
		connections.forEach(con -> con.eraseTargetFeedback(getTargetRequest()));
	}

	private void activateConnectionCreation(final EditPartViewer viewer) {
		final List<? extends EditPart> editParts = viewer.getSelectedEditParts();
		if ((editParts.size() == 1) && (editParts.get(0) instanceof InterfaceEditPart)) {
			connectionCreationTool = InlineConnectionCreationTool.createInlineConnCreationTool(editParts.get(0),
					getDomain(), viewer, getLocation());
		}
	}

	private void deactivateConnectionCreation() {
		connectionCreationTool.deactivate();
		connectionCreationTool = null;
	}

	private boolean checkConnCreationState(final int stateMask) {
		if (connectionCreationTool != null) {
			if (((stateMask & CONNECTION_CREATION_MOD_KEY) == 0)
					|| (!isConnectionCreationTarget(getTargetEditPart()))) {
				deactivateConnectionCreation();
			}
		} else if (((stateMask & CONNECTION_CREATION_MOD_KEY) == CONNECTION_CREATION_MOD_KEY)
				&& (isConnectionCreationTarget(getTargetEditPart()))) {
			activateConnectionCreation(getCurrentViewer());
		}
		return connectionCreationTool != null;
	}

	private static boolean isConnectionCreationTarget(final EditPart targetEditPart) {
		return (targetEditPart != null) && (targetEditPart.getModel() instanceof IInterfaceElement);
	}

}