/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 * 				 2020 Primetals Technologies Germany GmbH
 * 				 2021 Primetals Technologies Austria GmbH
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

package org.eclipse.fordiac.ide.elk.commands;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.draw2d.AbstractPointListShape;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.elk.core.service.LayoutMapping;
import org.eclipse.elk.graph.ElkEdge;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.elk.graph.ElkPort;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.application.editparts.AbstractFBNElementEditPart;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.application.editparts.SubAppInternalInterfaceEditPart;
import org.eclipse.fordiac.ide.application.editparts.UntypedSubAppInterfaceElementEditPart;
import org.eclipse.fordiac.ide.application.figures.FBNetworkElementFigure;
import org.eclipse.fordiac.ide.fbtypeeditor.network.editparts.CompositeInternalInterfaceEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.ConnectionRoutingData;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.gef.commands.Command;

public class LayoutCommand extends Command {

	private final Map<AbstractFBNElementEditPart, Position> oldFBPositions = new HashMap<>();
	private final LayoutMapping mapping;

	public LayoutCommand(LayoutMapping mapping) {
		super();
		this.mapping = mapping;
	}

	@Override
	public void execute() {
		saveFBPositionsForUndo();
		updateGraphRecusively(mapping.getLayoutGraph().getChildren());
	}

	@Override
	public void redo() {
		updateGraphRecusively(mapping.getLayoutGraph().getChildren());
	}

	@Override
	public void undo() {
		undoRecursively(mapping.getLayoutGraph().getChildren());
	}
	
	private void undoRecursively(EList<ElkNode> nodes) {
		nodes.forEach(child -> {
			undoRecursively(child.getChildren());
			final AbstractFBNElementEditPart editPart = (AbstractFBNElementEditPart) mapping.getGraphMap().get(child);
			editPart.getModel().setPosition(oldFBPositions.get(editPart));
			child.getPorts().forEach(port -> port.getOutgoingEdges().forEach(edge -> {
				final ConnectionEditPart connEditPart = (ConnectionEditPart) mapping.getGraphMap().get(edge);
				connEditPart.getModel().updateRoutingData(0, 0, 0); // set everything to 0 so that the connection gets routed
			}));
		});
	}

	private void saveFBPositionsForUndo() {
		savePositionsRecursively(mapping.getLayoutGraph().getChildren());
	}

	private void savePositionsRecursively(EList<ElkNode> nodes) {
		nodes.forEach(child -> {
			savePositionsRecursively(child.getChildren());
			final AbstractFBNElementEditPart editPart = (AbstractFBNElementEditPart) mapping.getGraphMap().get(child);
			oldFBPositions.put(editPart, editPart.getModel().getPosition());
		});
	}

	private void updateGraphRecusively(EList<ElkNode> nodes) {
		nodes.forEach(node -> {
			updateGraphRecusively(node.getChildren());
			updateFBPosition(node);
			node.getPorts().forEach(port -> {
				// TODO sometimes updates connections twice
				port.getOutgoingEdges().forEach(this::updateConnections);
				port.getIncomingEdges().forEach(this::updateConnections);
			});
		});
	}

	private void updateFBPosition(ElkNode node) {
		final AbstractFBNElementEditPart ep = (AbstractFBNElementEditPart) mapping.getGraphMap().get(node);
		final FBNetworkElement elem = ep.getModel();
		final FBNetworkElementFigure fig = ep.getFigure();

		if (elem != null) {
			double x = node.getX();
			if (fig.getLabelBounds().width() > fig.getFBBounds().width()) {
				x = node.getX() - (fig.getFBBounds().x() - fig.getLabelBounds().x());
			}
			if (node.getParent() != null) {
				// consider the offset caused by the canvas
				elem.updatePosition((int) (x + node.getParent().getX()), (int) (node.getY() + node.getParent().getY()));
			} else {
				elem.updatePosition((int) x, (int) node.getY());
			}
		}
	}

	// TODO comment or rework
	private void updateConnections(ElkEdge edge) {
		final ConnectionEditPart connEditPart = (ConnectionEditPart) mapping.getGraphMap().get(edge);
		final PointList pointList = ((AbstractPointListShape) connEditPart.getFigure()).getPoints();
		final ElkPort startPort = (ElkPort) edge.getSources().get(0);
		final ElkPort endPort = (ElkPort) edge.getTargets().get(0);
		final ElkNode parent = edge.getContainingNode();

		pointList.removeAllPoints();

		final double x = parent.getX();
		final double y = parent.getY();

		if (connEditPart.getSource() instanceof SubAppInternalInterfaceEditPart || connEditPart.getSource() instanceof CompositeInternalInterfaceEditPart) {
			// subapp editor source pin
			addStartPoint(pointList, startPort, x, y);
			addBendpoints(edge, pointList, x, y);
			addEndPoint(pointList, endPort, x, y);
		} else if (connEditPart.getTarget() instanceof SubAppInternalInterfaceEditPart || connEditPart.getTarget() instanceof CompositeInternalInterfaceEditPart) {
			// subapp editor target pin
			addStartPoint(pointList, startPort, x + startPort.getParent().getX(), y + startPort.getParent().getY());
			addBendpoints(edge, pointList, x, y);
			addEndPoint(pointList, endPort, 0, 0);
		} else if (isUnfolded(mapping.getGraphMap().get(parent))) {
			// inside unfolded subapp
			handleUnfoldedSubApp(connEditPart, edge, pointList, startPort, endPort, x, y);
		} else {
			addStartPoint(pointList, startPort, x + startPort.getParent().getX(), y + startPort.getParent().getY());
			addBendpoints(edge, pointList, x, y);
			addEndPoint(pointList, endPort, x, y);
		}

		((AbstractPointListShape) connEditPart.getFigure()).setPoints(pointList);

		updateModel(connEditPart.getModel(), pointList);
	}
	
	// TODO comment or rework
	private void handleUnfoldedSubApp(ConnectionEditPart connEditPart, ElkEdge edge, PointList pointList, ElkPort startPort, ElkPort endPort, double x, double y) {
		final double graphX = mapping.getLayoutGraph().getX();
		final double graphY = mapping.getLayoutGraph().getY();
		if (connEditPart.getSource() instanceof UntypedSubAppInterfaceElementEditPart) {
			// unfolded subapp source pin
			addStartPoint(pointList, startPort, x + graphX, y + graphY);
			addBendpoints(edge, pointList, x + graphX, y + graphY);
			addEndPoint(pointList, endPort, x + graphX, y + graphY);
		} else if (connEditPart.getTarget() instanceof UntypedSubAppInterfaceElementEditPart) {
			// unfolded subapp target pin
			addStartPoint(pointList, startPort, x + graphX + startPort.getParent().getX(), y + graphY + startPort.getParent().getY());
			addBendpoints(edge, pointList, x + graphX, y + graphY);
			addEndPoint(pointList, endPort, graphX, graphY);
		} else {
			addStartPoint(pointList, startPort, x + graphX + startPort.getParent().getX(), y + graphY + startPort.getParent().getY());
			addBendpoints(edge, pointList, x + graphX, y + graphY);
			addEndPoint(pointList, endPort, x + graphX, y + graphY);
		}
	}
	
	private static boolean isUnfolded(Object editPart) {
		if (editPart instanceof SubAppForFBNetworkEditPart) {
			return ((SubAppForFBNetworkEditPart) editPart).getModel().isUnfolded();
		}
		return false;
	}

	private static void addEndPoint(PointList pointList, ElkPort endPort, double xOffset, double yOffset) {
		pointList.addPoint((int) (endPort.getX() + endPort.getParent().getX() + xOffset),
				(int) (endPort.getY() + endPort.getParent().getY() + yOffset));
	}

	private static void addStartPoint(PointList pointList, ElkPort startPort, double xOffset, double yOffset) {
		pointList.addPoint((int) (startPort.getX() + xOffset), (int) (startPort.getY() + yOffset));
	}

	private static void addBendpoints(ElkEdge edge, PointList pointList, double xOffset, double yOffset) {
		edge.getSections().forEach(edgeSection ->
			edgeSection.getBendPoints().forEach(point -> pointList.addPoint((int) (point.getX() + xOffset), (int) (point.getY() + yOffset))));
	}

	private static void updateModel(final org.eclipse.fordiac.ide.model.libraryElement.Connection connModel,
			final PointList pointList) {
		final ConnectionRoutingData routingData = LibraryElementFactory.eINSTANCE.createConnectionRoutingData();
		if (pointList.size() > 2) {
			// 3 segments
			routingData.setDx1(pointList.getPoint(1).x() - pointList.getFirstPoint().x());
			if (pointList.size() > 4) {
				// 5 segments
				routingData.setDy(pointList.getPoint(2).y() - pointList.getFirstPoint().y());
				routingData.setDx2(pointList.getLastPoint().x() - pointList.getPoint(pointList.size() - 2).x());
			}
		}
		connModel.setRoutingData(routingData);
	}

}
