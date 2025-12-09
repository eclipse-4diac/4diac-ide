/*******************************************************************************
 * Copyright (c) 2020, 2025 Johannes Kepler University Linz,
 *                          Primetals Technologies Germany GmbH,
 *                          Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber, Bianca Wiesmayr, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.elk;

import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.elk.graph.ElkBendPoint;
import org.eclipse.elk.graph.ElkEdge;
import org.eclipse.elk.graph.ElkEdgeSection;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.gef.GraphicalEditPart;

public class FordiacGraphDataHelper {

	public static void calculate(final FordiacLayoutMapping mapping) {
		mapping.getLayoutGraph().getChildren().forEach(child -> {
			final var ep = (GraphicalEditPart) mapping.getGraphMap().get(child);
			if (ep != null) {
				final var pos = LibraryElementFactory.eINSTANCE.createPosition();
				pos.setX((int) child.getX());
				pos.setY((int) child.getY());
				mapping.getLayoutData().addPosition((FBNetworkElement) ep.getModel(), pos);
			}
		});

		mapping.getLayoutGraph().getContainedEdges().forEach(edge -> processConnection(mapping, edge));
	}

	private static void processConnection(final FordiacLayoutMapping mapping, final ElkEdge edge) {
		if (edge.getSources().isEmpty() || edge.getTargets().isEmpty() || edge.getSections().isEmpty()) {
			return;
		}

		final ConnectionEditPart connEp = (ConnectionEditPart) mapping.getGraphMap().get(edge);
		final ElkEdgeSection elkEdgeSection = edge.getSections().get(0);

		mapping.getLayoutData().addConnectionPoints(connEp.getModel(), createPointList(elkEdgeSection));
	}

	private static PointList createPointList(final ElkEdgeSection elkEdgeSection) {
		final PointList list = new PointList();

		list.addPoint((int) elkEdgeSection.getStartX(), (int) elkEdgeSection.getStartY());
		for (final ElkBendPoint point : elkEdgeSection.getBendPoints()) {
			list.addPoint((int) (point.getX()), (int) (point.getY()));
		}
		list.addPoint((int) elkEdgeSection.getEndX(), (int) elkEdgeSection.getEndY());

		return list;
	}

	private FordiacGraphDataHelper() {
		// nothing to do here
	}

}
