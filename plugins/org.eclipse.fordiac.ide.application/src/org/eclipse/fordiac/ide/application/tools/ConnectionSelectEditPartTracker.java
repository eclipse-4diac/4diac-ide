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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
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

	private Connection getModel() {
		return getSourceEditPart().getModel();
	}

	@Override
	protected void performSelection() {
		if (hasSelectionOccurred()) {
			return;
		}
		setFlag(FLAG_SELECTION_PERFORMED, true);

		final List<ConnectionEditPart> connections = getCoLocatedConnections();

		final EditPartViewer viewer = getCurrentViewer();
		final List<Object> selectedObjects = viewer.getSelectedEditParts();

		boolean first = true;
		for (final ConnectionEditPart con : connections){
			if (getCurrentInput().isModKeyDown(SWT.MOD1)) {
				if (selectedObjects.contains(con)) {
					viewer.deselect(con);
				} else {
					viewer.appendSelection(con);
				}
			} else if (getCurrentInput().isShiftKeyDown()) {
				viewer.appendSelection(con);
			} else {
				if (first) {
					viewer.select(con);
					first = false;
				} else {
					viewer.appendSelection(con);
				}
			}
		}

	}

	private List<ConnectionEditPart> getCoLocatedConnections() {
		final Set<Connection> conns = new HashSet<>(getModel().getSource().getOutputConnections());
		conns.addAll(getModel().getDestination().getInputConnections());
		final EditPartViewer viewer = getCurrentViewer();
		final List<ConnectionEditPart> retVal = new ArrayList<>();
		final Point location = getRelativeLocation(getSourceEditPart().getFigure());
		conns.forEach(con -> {
			final ConnectionEditPart conEditPart = (ConnectionEditPart) viewer.getEditPartRegistry().get(con);
			if (conEditPart.getFigure().findFigureAt(location) != null) {
				// the figure if the connection is under the mouse
				retVal.add(conEditPart);
			}
		});

		return retVal;
	}

	private Point getRelativeLocation(final IFigure figure) {
		if(figure.getParent() != null) {
			final Point location = getRelativeLocation(figure.getParent());
			figure.translateFromParent(location);
			return location;
		}
		return getLocation();
	}

}