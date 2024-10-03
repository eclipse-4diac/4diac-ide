/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 * 				 2020 Primetals Technologies Germany GmbH
 * 				 2021, 2022 Primetals Technologies Austria GmbH
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

import java.util.List;

import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.elk.graph.ElkBendPoint;
import org.eclipse.elk.graph.ElkEdge;
import org.eclipse.elk.graph.ElkEdgeSection;
import org.eclipse.elk.graph.ElkGraphFactory;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.elk.graph.ElkPort;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.gef.GraphicalEditPart;

public class FordiacGraphDataHelper {

	public static void calculate(final FordiacLayoutMapping mapping) {
		mapping.getLayoutGraph().getChildren().forEach(child -> {
			final var ep = (GraphicalEditPart) mapping.getGraphMap().get(child);
			final var pos = LibraryElementFactory.eINSTANCE.createPosition();
			pos.setX((int) child.getX());
			pos.setY((int) child.getY());
			mapping.getLayoutData().addPosition((FBNetworkElement) ep.getModel(), pos);
		});

		mapping.getLayoutGraph().getContainedEdges().forEach(edge -> processConnection(mapping, edge));
	}

	private static void processConnection(final FordiacLayoutMapping mapping, final ElkEdge edge) {
		if (edge.getSources().isEmpty() || edge.getTargets().isEmpty() || edge.getSections().isEmpty()) {
			return;
		}

		final ConnectionEditPart connEp = (ConnectionEditPart) mapping.getGraphMap().get(edge);
		final ElkPort startPort = (ElkPort) edge.getSources().get(0);
		final ElkPort endPort = (ElkPort) edge.getTargets().get(0);
		final ElkEdgeSection elkEdgeSection = edge.getSections().get(0);
		final List<ElkBendPoint> bendPoints = elkEdgeSection.getBendPoints();

		mapping.getLayoutData().addConnectionPoints(connEp.getModel(),
				createPointList(mapping, startPort, endPort, bendPoints));
	}

	private static PointList createPointList(final FordiacLayoutMapping mapping, final ElkPort startPort,
			final ElkPort endPort, final List<ElkBendPoint> bendPoints) {
		// needs to translate coordinates back from relative to absolute

		final PointList list = new PointList();
		final ElkNode layoutGraph = mapping.getLayoutGraph();

		ElkNode startNode = startPort.getParent();
		// edge case for expanded subapps (inside source connections)
		if (startNode == layoutGraph) {
			// defaults to (0, 0), effectively acting as toRelative()
			startNode = ElkGraphFactory.eINSTANCE.createElkNode();
		}
		final int startX = (int) (startPort.getX() + startNode.getX() + layoutGraph.getX());
		final int startY = (int) (startPort.getY() + startNode.getY() + layoutGraph.getY());
		list.addPoint(startX, startY);

		for (final ElkBendPoint point : bendPoints) {
			list.addPoint((int) (point.getX() + layoutGraph.getX()), (int) (point.getY() + layoutGraph.getY()));
		}

		final ElkNode endNode = endPort.getParent();
		final int endX = (int) (endPort.getX() + endNode.getX() + layoutGraph.getX());
		final int endY = (int) (endPort.getY() + endNode.getY() + layoutGraph.getY());
		list.addPoint(endX, endY);

		return list;
	}

	private FordiacGraphDataHelper() {
		// nothing to do here
	}

}
