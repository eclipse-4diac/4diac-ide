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
package org.eclipse.fordiac.ide.elk.helpers;

import static org.eclipse.fordiac.ide.elk.FordiacLayoutMapping.HIERARCHY_CROSSING_CONNECTIONS_MAPPING;
import static org.eclipse.fordiac.ide.elk.FordiacLayoutMapping.HIERARCHY_CROSSING_CONNECTIONS_REVERSE_MAPPING;
import static org.eclipse.fordiac.ide.elk.FordiacLayoutMapping.LAYOUT_DATA;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.elk.core.options.PortSide;
import org.eclipse.elk.core.service.LayoutMapping;
import org.eclipse.elk.graph.ElkBendPoint;
import org.eclipse.elk.graph.ElkEdge;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.elk.graph.ElkPort;
import org.eclipse.fordiac.ide.application.editparts.AbstractContainerContentEditPart;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.application.editparts.GroupEditPart;
import org.eclipse.fordiac.ide.application.editparts.UnfoldedSubappContentEditPart;
import org.eclipse.fordiac.ide.elk.FordiacLayoutData;
import org.eclipse.fordiac.ide.elk.FordiacLayoutMapping;
import org.eclipse.fordiac.ide.gef.editparts.AbstractFBNetworkEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.gef.GraphicalEditPart;

public class FordiacGraphDataHelper {

	private static final int INSTANCE_COMMENT_OFFSET = 8;

	public static FordiacLayoutData calculate(final LayoutMapping mapping) {
		if (mapping.getProperty(
				FordiacLayoutMapping.NETWORK_EDIT_PART) instanceof final UnfoldedSubappContentEditPart subapp) {
			final int y = subapp.getParent().getFigure().getBounds().y;
			final int input = subapp.getParent().getInterfacePositionMap().getInputDirectEnd() - y;
			final int output = subapp.getParent().getInterfacePositionMap().getOutputDirectEnd() - y;
			calculateNodePositionsRecursively(mapping, mapping.getLayoutGraph(), 0,
					Math.max(Math.max(input, output), 0));
		} else {
			calculateNodePositionsRecursively(mapping, mapping.getLayoutGraph(), 0, INSTANCE_COMMENT_OFFSET);
		}
		createPinOffsetData(mapping);
		addFlatConnections(mapping);
		return mapping.getProperty(LAYOUT_DATA);
	}

	private static void calculateNodePositionsRecursively(final LayoutMapping mapping, final ElkNode node,
			final double parentX, final double parentY) {
		final GraphicalEditPart ep = (GraphicalEditPart) mapping.getGraphMap().get(node);
		final int calculatedX = (int) (node.getX() + parentX);
		final int calculatedY = (int) (node.getY() + parentY);

		if (ep != mapping.getProperty(FordiacLayoutMapping.NETWORK_EDIT_PART)) {
			setPosition(mapping, node, ep, calculatedX, calculatedY);
		}

		processConnections(mapping, node, calculatedX, calculatedY);

		// position inside of group -> relative
		// outside -> absolute
		if (ep instanceof GroupEditPart) {
			final int groupCommentHeight = (int) node.getLabels().get(0).getHeight();
			node.getChildren()
					.forEach(child -> calculateNodePositionsRecursively(mapping, child, 0, -groupCommentHeight));
		} else {
			node.getChildren()
					.forEach(child -> calculateNodePositionsRecursively(mapping, child, calculatedX, calculatedY));
		}
	}

	private static void setPosition(final LayoutMapping mapping, final ElkNode node, final GraphicalEditPart ep,
			final int calculatedX, final int calculatedY) {
		final boolean isContainer = ep instanceof AbstractContainerContentEditPart;
		final boolean isNoNetwork = !(ep instanceof AbstractFBNetworkEditPart);
		// holds for FBs, groups and unfolded subapps(currently deactivated)
		if (isContainer || isNoNetwork) {
			final Position pos = LibraryElementFactory.eINSTANCE.createPosition();
			pos.setX(calculatedX);
			pos.setY(calculatedY);
			mapping.getProperty(LAYOUT_DATA).addPosition((FBNetworkElement) ep.getModel(), pos);

			if (ep instanceof GroupEditPart) {
				mapping.getProperty(LAYOUT_DATA).addGroup((Group) ep.getModel(), (int) node.getHeight(),
						(int) node.getWidth());
			}
		}
	}

	private static void addFlatConnections(final LayoutMapping mapping) {
		// ensure that all connections are redrawn and no artifacts remain
		for (final var connEp : mapping.getProperty(FordiacLayoutMapping.FLAT_CONNECTIONS)) {
			mapping.getProperty(LAYOUT_DATA).addConnectionPoints(connEp.getModel(), new PointList());
		}
	}

	private static void processConnections(final LayoutMapping mapping, final ElkNode node, final int calculatedX,
			final int calculatedY) {
		for (final ElkEdge edge : node.getContainedEdges()) {
			final ConnectionEditPart connEp = (ConnectionEditPart) mapping.getGraphMap().get(edge);
			if (connEp == null) {
				processHierarchyCrossingConnection(mapping, node, edge);
			} else {
				processNormalConnection(mapping, node, calculatedX, calculatedY, edge, connEp);
			}
		}
	}

	private static void processNormalConnection(final LayoutMapping mapping, final ElkNode node, final int calculatedX,
			final int calculatedY, final ElkEdge edge, final ConnectionEditPart connEp) {
		final ElkPort startPort = (ElkPort) edge.getSources().get(0);
		final ElkPort endPort = (ElkPort) edge.getTargets().get(0);
		final List<ElkBendPoint> bendPoints = edge.getSections().get(0).getBendPoints();

		mapping.getProperty(LAYOUT_DATA).addConnectionPoints(connEp.getModel(),
				createPointList(node, startPort, endPort, bendPoints, calculatedX, calculatedY));
	}

	private static void processHierarchyCrossingConnection(final LayoutMapping mapping, final ElkNode node,
			final ElkEdge edge) {

		final ConnectionEditPart connEp = mapping.getProperty(HIERARCHY_CROSSING_CONNECTIONS_REVERSE_MAPPING).get(edge);
		final List<ElkEdge> edgeList = mapping.getProperty(HIERARCHY_CROSSING_CONNECTIONS_MAPPING).get(connEp);

		// crossing edge has already been processed
		// This is necessary because there are 2(or 3) graph connections for every
		// hierarchy crossing diagram connection.
		// We process the whole thing on first encounter (always the part in the highest
		// level e.g. the network that contains the groups).
		// After this the connection has to be removed so that the other parts (which
		// are nested inside the groups) are not processed again.
		if (edgeList == null) {
			return;
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

		trimConnection(bendPoints);

		mapping.getProperty(LAYOUT_DATA).addConnectionPoints(connEp.getModel(),
				createCrossingPointList(node, startPort, endPort, bendPoints));
		mapping.getProperty(HIERARCHY_CROSSING_CONNECTIONS_MAPPING).remove(connEp); // remove so other iterations don't
																					// process it again
	}

	// The current implementation simple removes middle bendpoints until they can be
	// fit into a 5 segment connection.
	// This can lead to ugly connection routing and should probably be improved.
	private static void trimConnection(final List<ElkBendPoint> bendPoints) {
		// 4 bendpoints result in a five-segment connection, everything above that needs
		// to be trimmed down
		if (bendPoints.size() > 4) {
			final List<ElkBendPoint> list = new ArrayList<>(4);
			list.add(bendPoints.get(0));
			list.add(bendPoints.get(1));
			list.add(bendPoints.get(bendPoints.size() - 1)); // last element
			list.add(bendPoints.get(bendPoints.size() - 2)); // second last element
			bendPoints.retainAll(list);
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
			list.addPoint((int) (startPort.getX() + startPort.getParent().getX() + calculatedX),
					(int) (startPort.getY() + startPort.getParent().getY() + calculatedY));
		}
		for (final ElkBendPoint point : bendPoints) {
			list.addPoint((int) (point.getX() + calculatedX), (int) (point.getY() + calculatedY));
		}
		if (endPort.getParent() == node) {
			// hierarchical port
			list.addPoint((int) (endPort.getX() + calculatedX), (int) (endPort.getY() + calculatedY));
		} else {
			// simple port
			list.addPoint((int) (endPort.getX() + endPort.getParent().getX() + calculatedX),
					(int) (endPort.getY() + endPort.getParent().getY() + calculatedY));
		}
		return list;
	}

	private static PointList createCrossingPointList(final ElkNode node, final ElkPort startPort, final ElkPort endPort,
			final List<ElkBendPoint> bendPoints) {
		final PointList list = new PointList();
		list.addPoint(toAbsolute(startPort.getX(), startPort.getY(), startPort.getParent()));
		for (final ElkBendPoint point : bendPoints) {
			// the "parent" (node) is the containing node of the edge, which in most cases
			// is the network itself
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

	private static void createPinOffsetData(final LayoutMapping mapping) {
		if (!mapping.getLayoutGraph().getPorts().isEmpty()) {
			final InterfaceList interfaceList = (InterfaceList) ((IInterfaceElement) mapping.getGraphMap()
					.get(mapping.getLayoutGraph().getPorts().get(0))).eContainer();

			final Map<Boolean, List<ElkPort>> ports = mapping.getLayoutGraph().getPorts().stream()
					.sorted(Comparator.comparing(ElkPort::getY))
					.collect(Collectors.partitioningBy(FordiacGraphDataHelper::isLeftPort));

			final Map<Boolean, List<IInterfaceElement>> pins = interfaceList.getAllInterfaceElements().stream()
					.collect(Collectors.partitioningBy(IInterfaceElement::isIsInput));

			calculatePins(mapping, interfaceList, ports.get(true), pins.get(true));
			calculatePins(mapping, interfaceList, ports.get(false), pins.get(false));
		}
	}

	private static void calculatePins(final LayoutMapping mapping, final InterfaceList interfaceList,
			final List<ElkPort> ports, final List<IInterfaceElement> pins) {
		ElkPort abovePort = null;

		for (int i = 0; i < ports.size(); i++) {
			final ElkPort port = ports.get(i);
			int padding = (int) port.getY();

			// TODO get rid of all the magic numbers
			if (abovePort != null) {
				padding -= (int) abovePort.getY() - (port.getHeight() - 1);
			}

			final IInterfaceElement pin = pins.get(i);
			if (isFirstInputVar(interfaceList, pin)) {
				padding -= 9;
			}
			mapping.getProperty(LAYOUT_DATA).addPin(pin, Integer.valueOf(padding));

			abovePort = port;
		}
	}

	private static boolean isLeftPort(final ElkPort port) {
		return PortSide.WEST.equals(port.getProperty(CoreOptions.PORT_SIDE));
	}

	private static boolean isFirstInputVar(final InterfaceList interfaceList, final IInterfaceElement pin) {
		return !interfaceList.getInputVars().isEmpty() && pin.equals(interfaceList.getInputVars().get(0));
	}

	private FordiacGraphDataHelper() {
	}

}
