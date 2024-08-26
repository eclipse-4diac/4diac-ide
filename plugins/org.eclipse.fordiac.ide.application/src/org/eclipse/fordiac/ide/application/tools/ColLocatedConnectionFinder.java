/*******************************************************************************
 * Copyright (c) 2021, 2024 Primetals Technologies Austria GmbH
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

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.gef.EditPartViewer;

final class ColLocatedConnectionFinder {

	public static List<ConnectionEditPart> getCoLocatedConnections(final ConnectionEditPart connEP,
			final EditPartViewer viewer, final Point loc) {
		final Set<Connection> conns = getAllRelatedConnections(connEP);
		final Point location = getRelativeLocation(connEP.getFigure(), loc);
		return conns.stream().map(con -> (ConnectionEditPart) viewer.getEditPartForModel(con)).filter(Objects::nonNull)
				.filter(ep -> ep.getFigure().findFigureAt(location) != null).toList();
	}

	public static List<ConnectionEditPart> getLeftCoLocatedConnections(final ConnectionEditPart connEP,
			final EditPartViewer viewer, final Point loc) {
		final Set<Connection> conns = getAllRelatedConnections(connEP);
		final Point location = getRelativeLocation(connEP.getFigure(), loc);
		return conns.stream().map(con -> (ConnectionEditPart) viewer.getEditPartForModel(con)).filter(Objects::nonNull)
				.filter(ep -> ep.getFigure().findFigureAt(location) == null).toList();
	}

	private static Set<Connection> getAllRelatedConnections(final ConnectionEditPart connEP) {
		final Set<Connection> conns = new HashSet<>();
		if (connEP.getModel() != null) {
			if (connEP.getModel().getSource() != null) {
				conns.addAll(connEP.getModel().getSource().getOutputConnections());
			}
			if (connEP.getModel().getDestination() != null) {
				conns.addAll(connEP.getModel().getDestination().getInputConnections());
			}
		}
		return conns;
	}

	private static Point getRelativeLocation(final IFigure figure, final Point loc) {
		if (figure.getParent() != null) {
			final Point location = getRelativeLocation(figure.getParent(), loc);
			figure.translateFromParent(location);
			return location;
		}
		return loc;
	}

	private ColLocatedConnectionFinder() {
		throw new UnsupportedOperationException("Helper class should not be instantiated"); //$NON-NLS-1$
	}

}
