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

import static org.eclipse.fordiac.ide.elk.FordiacLayoutMapping.COMMAND_STACK;
import static org.eclipse.fordiac.ide.elk.FordiacLayoutMapping.HIERARCHY_CROSSING_CONNECTIONS_MAPPING;
import static org.eclipse.fordiac.ide.elk.FordiacLayoutMapping.HIERARCHY_CROSSING_CONNECTIONS_REVERSE_MAPPING;
import static org.eclipse.fordiac.ide.elk.FordiacLayoutMapping.LAYOUT_DATA;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.elk.core.service.IDiagramLayoutConnector;
import org.eclipse.elk.core.service.LayoutMapping;
import org.eclipse.elk.graph.ElkBendPoint;
import org.eclipse.elk.graph.ElkEdge;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.elk.graph.ElkPort;
import org.eclipse.elk.graph.properties.IPropertyHolder;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.application.editparts.GroupEditPart;
import org.eclipse.fordiac.ide.application.editparts.UnfoldedSubappContentEditPart;
import org.eclipse.fordiac.ide.elk.commands.LayoutCommand;
import org.eclipse.fordiac.ide.elk.helpers.FordiacGraphBuilder;
import org.eclipse.fordiac.ide.gef.editparts.AbstractFBNetworkEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.ui.IWorkbenchPart;

public class FordiacLayoutConnector implements IDiagramLayoutConnector {

	@Override
	public LayoutMapping buildLayoutGraph(final IWorkbenchPart workbenchPart, final Object diagramPart) {
		final FordiacLayoutMapping mapping = FordiacLayoutMapping.create(workbenchPart);

		if (mapping.hasNetwork()) {
			FordiacGraphBuilder.build(mapping);
		}

		return mapping;
	}

	@Override
	public void applyLayout(final LayoutMapping mapping, final IPropertyHolder settings) {
		final int INSTANCE_COMMENT_OFFSET = 8;
		calculateNodePositionsRecursively(mapping, mapping.getLayoutGraph(), 0, INSTANCE_COMMENT_OFFSET);
		createPinOffsetData(mapping);
		final Command layoutCommand = new LayoutCommand(mapping.getProperty(LAYOUT_DATA));
		mapping.getProperty(COMMAND_STACK).execute(layoutCommand);
	}

	private static void createPinOffsetData(final LayoutMapping mapping) {
		if (!mapping.getLayoutGraph().getPorts().isEmpty()) {
			final InterfaceList interfaceList = (InterfaceList) ((IInterfaceElement) mapping.getGraphMap()
					.get(mapping.getLayoutGraph().getPorts().get(0))).eContainer();

			final List<IInterfaceElement> allIEs = interfaceList.getAllInterfaceElements();

			mapping.getLayoutGraph().getPorts().forEach(port -> {
				final IInterfaceElement pin = (IInterfaceElement) mapping.getGraphMap().get(port);
				final int index = allIEs.indexOf(pin);
				int padding = (int) port.getY();
				if (index > 0 && pin.isIsInput() == allIEs.get(index - 1).isIsInput()) {
					final IInterfaceElement abovePin = allIEs.get(index - 1);
					final ElkPort abovePort = (ElkPort) mapping.getGraphMap().inverse().get(abovePin);
					padding -= (int) abovePort.getY() + port.getHeight(); // the port height represents the edit parts
				}
				/* For the first input var, additional padding has to be added. This results in "ugly" adapter
				 * connections but fixes data connections. The exact reason for this additional offset is not yet
				 * known. */
				if (isFirstInputVar(interfaceList, pin)) {
					padding += 8;
				}
				mapping.getProperty(LAYOUT_DATA).addPin(pin, Integer.valueOf(padding));
			});
		}
	}

	private static boolean isFirstInputVar(final InterfaceList interfaceList, final IInterfaceElement pin) {
		return !interfaceList.getInputVars().isEmpty() && pin.equals(interfaceList.getInputVars().get(0));
	}

	private void calculateNodePositionsRecursively(final LayoutMapping mapping, final ElkNode node, final double parentX, final double parentY) {
		final GraphicalEditPart ep = (GraphicalEditPart) mapping.getGraphMap().get(node);
		final int calculatedX = (int) (node.getX() + parentX);
		final int calculatedY = (int) (node.getY() + parentY);
		// TODO rewrite condition
		if (!(ep instanceof AbstractFBNetworkEditPart 
				&& !(ep instanceof UnfoldedSubappContentEditPart)
				&& !(ep instanceof GroupEditPart))) {
			final Position pos = LibraryElementFactory.eINSTANCE.createPosition();
			pos.setX(calculatedX);
			pos.setY(calculatedY);
			mapping.getProperty(LAYOUT_DATA).addPosition((FBNetworkElement) ep.getModel(), pos);
			if (ep instanceof GroupEditPart) {
				mapping.getProperty(LAYOUT_DATA).addGroup((Group) ep.getModel(), (int) node.getHeight(), (int) node.getWidth());
			}
		}
		for (final ElkEdge edge : node.getContainedEdges()) {
			ConnectionEditPart connEp = (ConnectionEditPart) mapping.getGraphMap().get(edge);
			if (connEp == null) {
				connEp = mapping.getProperty(HIERARCHY_CROSSING_CONNECTIONS_REVERSE_MAPPING).get(edge);
				final List<ElkEdge> edgeList = mapping.getProperty(HIERARCHY_CROSSING_CONNECTIONS_MAPPING).get(connEp);
				if (edgeList == null) {
					continue; // crossing edge has already been processed
				}
				final ElkPort startPort = (ElkPort) edgeList.get(0).getSources().get(0);
				final ElkPort endPort = (ElkPort) edgeList.get(edgeList.size() - 1).getTargets().get(0);
				final List<ElkBendPoint> bendPoints = new LinkedList<>();
				for (final ElkEdge e : edgeList) {
					// is in group
					if (e.getContainingNode() != node) {
						// bendpoints inside groups are relative to the group and not the network
						for (final ElkBendPoint point : e.getSections().get(0).getBendPoints()) {
							point.setX(point.getX() + e.getContainingNode().getX());
							point.setY(point.getY() + e.getContainingNode().getY());
						}
					}
					bendPoints.addAll(e.getSections().get(0).getBendPoints());
				}
				// 4 bendpoints result in a five-segment connection, everything above that needs to be trimmed down
				if (bendPoints.size() > 4) {
					final List<ElkBendPoint> list = new ArrayList<>(4);
					list.add(bendPoints.get(0));
					list.add(bendPoints.get(1));
					list.add(bendPoints.get(bendPoints.size() - 1)); // last element
					list.add(bendPoints.get(bendPoints.size() - 2)); // second last element
					bendPoints.retainAll(list);
				}
				mapping.getProperty(LAYOUT_DATA).addConnectionPoints(connEp.getModel(), createCrossingPointList(node, startPort, endPort, bendPoints));
				mapping.getProperty(HIERARCHY_CROSSING_CONNECTIONS_MAPPING).remove(connEp); // remove so other iterations don't process it again
			} else {
				final ElkPort startPort = (ElkPort) edge.getSources().get(0);
				final ElkPort endPort = (ElkPort) edge.getTargets().get(0);
				final List<ElkBendPoint> bendPoints = edge.getSections().get(0).getBendPoints();
				
				mapping.getProperty(LAYOUT_DATA).addConnectionPoints(connEp.getModel(), createPointList(node, startPort, endPort, bendPoints, calculatedX, calculatedY));				
			}
		}
		// position inside of group -> relative
		// outside -> absolute
		if (ep instanceof GroupEditPart) {
			final int groupCommentHeight = (int) node.getLabels().get(0).getHeight();
			node.getChildren().forEach(child -> calculateNodePositionsRecursively(mapping, child, 0, -groupCommentHeight));
		} else {			
			node.getChildren().forEach(child -> calculateNodePositionsRecursively(mapping, child, calculatedX, calculatedY));
		}
	}
	
	private static PointList createPointList(final ElkNode node, final ElkPort startPort, final ElkPort endPort,
			final List<ElkBendPoint> bendPoints, final int calculatedX, final int calculatedY) {
		final PointList list = new PointList();
		if (startPort.getParent() == node) {
			// hierarchical port
			list.addPoint((int) (startPort.getX() + calculatedX), (int) (startPort.getY() + calculatedY));
		} else {
			// simple port
			list.addPoint((int) (startPort.getX() + startPort.getParent().getX() + calculatedX), (int) (startPort.getY() + startPort.getParent().getY() + calculatedY));
		}
		for (final ElkBendPoint point : bendPoints) {
			list.addPoint((int) (point.getX() + calculatedX), (int) (point.getY() + calculatedY));
		}
		if (endPort.getParent() == node) {
			// hierarchical port
			list.addPoint((int) (endPort.getX() + calculatedX), (int) (endPort.getY() + calculatedY));
		} else {
			// simple port
			list.addPoint((int) (endPort.getX() + endPort.getParent().getX() + calculatedX), (int) (endPort.getY() + endPort.getParent().getY() + calculatedY));
		}
		return list;
	}
	
	private static PointList createCrossingPointList(final ElkNode node, final ElkPort startPort, final ElkPort endPort,
			final List<ElkBendPoint> bendPoints) {
		final PointList list = new PointList();
		list.addPoint(toAbsolute(startPort.getX(), startPort.getY(), startPort.getParent()));
		for (final ElkBendPoint point : bendPoints) {
			// the "parent" (node) is the containing node of the edge, which in most cases is the network itself
			list.addPoint(toAbsolute(point.getX(), point.getY(), node));
		}
		list.addPoint(toAbsolute(endPort.getX(), endPort.getY(), endPort.getParent()));
		return list;
	}
	
	private static PrecisionPoint toAbsolute(final double startX, final double startY, final ElkNode parent) {
		double x = startX;
		double y = startY;
		ElkNode tmp = parent;
		while (tmp != null) {
			x += tmp.getX();
			y += tmp.getY();
			tmp = tmp.getParent();
		}
		return new PrecisionPoint(x, y);
	}

}