/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 * 				 2020 Primetals Technologies Germany GmbH
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
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.elk.alg.layered.options.CrossingMinimizationStrategy;
import org.eclipse.elk.alg.layered.options.FixedAlignment;
import org.eclipse.elk.alg.layered.options.LayeredMetaDataProvider;
import org.eclipse.elk.alg.layered.options.NodePlacementStrategy;
import org.eclipse.elk.alg.layered.options.NodePromotionStrategy;
import org.eclipse.elk.core.RecursiveGraphLayoutEngine;
import org.eclipse.elk.core.math.ElkPadding;
import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.elk.core.options.Direction;
import org.eclipse.elk.core.options.EdgeRouting;
import org.eclipse.elk.core.options.NodeLabelPlacement;
import org.eclipse.elk.core.options.PortConstraints;
import org.eclipse.elk.core.options.PortSide;
import org.eclipse.elk.core.util.NullElkProgressMonitor;
import org.eclipse.elk.graph.ElkEdge;
import org.eclipse.elk.graph.ElkLabel;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.elk.graph.ElkPort;
import org.eclipse.elk.graph.util.ElkGraphUtil;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.application.editparts.EditorWithInterfaceEditPart;
import org.eclipse.fordiac.ide.application.editparts.SubAppInternalInterfaceEditPart;
import org.eclipse.fordiac.ide.application.editparts.UISubAppNetworkEditPart;
import org.eclipse.fordiac.ide.application.figures.FBNetworkElementFigure;
import org.eclipse.fordiac.ide.fbtypeeditor.network.editparts.CompositeInternalInterfaceEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.network.editparts.CompositeNetworkEditPart;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.gef.commands.Command;

public class LayoutCommand extends Command {

	private final Map<FBNetworkElement, FBNetworkElementFigure> fbNetworkElements;
	private final Map<org.eclipse.fordiac.ide.model.libraryElement.Connection, org.eclipse.draw2d.Connection> connections;
	private final Map<Value, Label> values;
	private final Map<Value, Point> valueLocations;

	private final Map<FBNetworkElement, ElkNode> nodes = new HashMap<>();
	private final Map<IInterfaceElement, ElkPort> ports = new HashMap<>();
	private final Map<ElkEdge, org.eclipse.fordiac.ide.model.libraryElement.Connection> connEditParts = new HashMap<>();
	private final Map<ElkNode, FBNetworkElement> nodeMapping = new HashMap<>();

	private final Map<FBNetworkElement, Point> oldFBPositions = new HashMap<>();

	private ElkNode layoutGraph;

	private static final PrecisionPoint START_POINT = new PrecisionPoint();
	private static final PrecisionPoint END_POINT = new PrecisionPoint();

	private final int maxIOLabelSize = 60;

	/** An ELK driven layouting command.
	 *
	 * @param fbNetworkElements Maps a FBNetworkElement to its figure
	 * @param connections       Maps a Connection to its figure
	 * @param values            Maps a Value to its figure
	 * @param valueLocations    Maps a Value to its Location(left upper corner) */
	public LayoutCommand(Map<FBNetworkElement, FBNetworkElementFigure> fbNetworkElements,
			Map<org.eclipse.fordiac.ide.model.libraryElement.Connection, org.eclipse.draw2d.Connection> connections,
			Map<Value, Label> values, Map<Value, Point> valueLocations) {
		super();
		this.fbNetworkElements = fbNetworkElements;
		this.connections = connections;
		this.values = values;
		this.valueLocations = valueLocations;
	}

	@Override
	public void execute() {
		// build up the Elk DataStructure
		layoutGraph = createElkGraph();
		// empty parent graph for SubApps
		final ElkNode parent = ElkGraphUtil.createGraph();
		layoutGraph.setParent(parent);

		// save FB positions for undo
		for (final FBNetworkElement elem : nodeMapping.values()) {
			oldFBPositions.put(elem, new Point(elem.getX(), elem.getY()));
		}

		setGraphProperties(layoutGraph);

		// run layout algorithms
		final RecursiveGraphLayoutEngine engine = new RecursiveGraphLayoutEngine();
		engine.layout(layoutGraph, new NullElkProgressMonitor());

		updateLayout(layoutGraph);
	}

	@Override
	public void redo() {
		updateLayout(layoutGraph);
	}

	@Override
	public void undo() {
		for (final FBNetworkElement elem : nodeMapping.values()) {
			final Point p = oldFBPositions.get(elem);
			elem.setX(p.x);
			elem.setY(p.y);
		}
		for (final org.eclipse.fordiac.ide.model.libraryElement.Connection conn : connEditParts.values()) {
			// set everything to 0 so that the connection gets routed
			conn.setDx1(0);
			conn.setDx2(0);
			conn.setDy(0);
		}
		// FIXME resetInterfaceElements();
	}

	public ElkNode createElkGraph() {
		// Root node T is the editor view (drawing area), graph itself is represented by
		// an ElkNode because each ElkNode can contain other Elk nodes
		final ElkNode elkGraph = ElkGraphUtil.createGraph(); // root node

		// simple nodes are the subapps and function blocks (FBNetworkElements)
		createFBNetworkElementNodes(elkGraph);

		// simple edges for the event and data connections
		// also creates ports for the data and event connections
		createConnectionNodes(elkGraph);

		createValueLabels(elkGraph);

		// Everything in an ELK graph is an ElkGraphElement
		// ElkConnectableShape.... each rectangular shape with ports
		// ElkEdge .... connections between elk nodes
		// ElkNode .... the FBs and Subapps

		return elkGraph;
	}

	private void setGraphProperties(ElkNode layoutGraph) {
		final double SPACING = 50.0;
		layoutGraph.setProperty(CoreOptions.ALGORITHM, "org.eclipse.elk.layered"); //$NON-NLS-1$
		layoutGraph.setProperty(CoreOptions.EDGE_ROUTING, EdgeRouting.ORTHOGONAL);
		layoutGraph.setProperty(CoreOptions.DIRECTION, Direction.RIGHT);
		layoutGraph.setProperty(LayeredMetaDataProvider.NODE_PLACEMENT_STRATEGY, NodePlacementStrategy.NETWORK_SIMPLEX);
		layoutGraph.setProperty(LayeredMetaDataProvider.THOROUGHNESS, 5000);
		layoutGraph.setProperty(LayeredMetaDataProvider.LAYERING_NODE_PROMOTION_STRATEGY,
				NodePromotionStrategy.NO_BOUNDARY);
		layoutGraph.setProperty(LayeredMetaDataProvider.SPACING_EDGE_NODE_BETWEEN_LAYERS, SPACING);
		layoutGraph.setProperty(LayeredMetaDataProvider.SPACING_NODE_NODE_BETWEEN_LAYERS, SPACING);
		layoutGraph.setProperty(CoreOptions.SPACING_COMPONENT_COMPONENT, SPACING);
		layoutGraph.setProperty(CoreOptions.ASPECT_RATIO, 1.0); // increase to prevent line break
		layoutGraph.setProperty(CoreOptions.SEPARATE_CONNECTED_COMPONENTS, true);
		layoutGraph.setProperty(LayeredMetaDataProvider.NODE_PLACEMENT_FAVOR_STRAIGHT_EDGES, false);
		// set padding according to the number of in and outputs (for subapps)
		layoutGraph.setProperty(CoreOptions.PADDING, new ElkPadding(60, maxIOLabelSize + 30, 0, maxIOLabelSize + 30));
		layoutGraph.setProperty(LayeredMetaDataProvider.NODE_PLACEMENT_FAVOR_STRAIGHT_EDGES, false);
	}

	private void createValueLabels(ElkNode elkGraph) {
		for (final Value elem : values.keySet()) {
			final Point point = valueLocations.get(elem);
			final ElkPort port = getPort(point, elem.getVarDeclaration(), elkGraph);
			final ElkLabel label = ElkGraphUtil.createLabel(elem.getValue(), port);
			final Rectangle bounds = values.get(elem).getBounds();
			label.setLocation(bounds.preciseX() - port.getX() - port.getParent().getX(),
					bounds.preciseY() - port.getY() - port.getParent().getY());
			label.setDimensions(bounds.preciseWidth(), bounds.preciseHeight());

		}
	}

	private ElkPort getPort(Point point, IInterfaceElement interfaceElement, ElkNode graph) {
		return ports.computeIfAbsent(interfaceElement, ie -> createPort(point, ie, graph));
	}

	private static void setPortProperties(ElkPort port, boolean isInput) {
		if (isInput) {
			port.setProperty(CoreOptions.PORT_SIDE, PortSide.WEST);
		} else {
			port.setProperty(CoreOptions.PORT_SIDE, PortSide.EAST);
		}
	}

	private static void setNodeProperties(ElkNode node) {
		node.setProperty(LayeredMetaDataProvider.NODE_PLACEMENT_BK_FIXED_ALIGNMENT, FixedAlignment.NONE);
		node.setProperty(LayeredMetaDataProvider.CROSSING_MINIMIZATION_STRATEGY,
				CrossingMinimizationStrategy.INTERACTIVE);
		node.setProperty(CoreOptions.PORT_CONSTRAINTS, PortConstraints.FIXED_POS);
		node.setProperty(CoreOptions.INSIDE_SELF_LOOPS_ACTIVATE, true);
		node.setProperty(CoreOptions.NODE_LABELS_PLACEMENT, NodeLabelPlacement.insideTopCenter());
	}

	private ElkPort createPort(Point point, IInterfaceElement interfaceElement, ElkNode graph) {
		ElkNode node = (null != interfaceElement.getFBNetworkElement())
				? nodes.get(interfaceElement.getFBNetworkElement())
						: graph;
				boolean isSubApp = false;
				if (node == null) {
					// untyped subapp interfaces
					node = graph;
					isSubApp = true;
				} else if (interfaceElement.getFBNetworkElement() == null) {
					// typed subapp and composite interfaces
					isSubApp = true;
				}
				final ElkPort port = ElkGraphUtil.createPort(node);
				ElkGraphUtil.createLabel(interfaceElement.getName(), port);
				// dont set the location for the interface ports so that they will be properly
				// handled by elk
				if (!isSubApp) {
					port.setLocation(point.preciseX() - node.getX(), point.preciseY() - node.getY());
					setPortProperties(port, interfaceElement.isIsInput());
				} else if (interfaceElement.isIsInput()) {
					// set the input interface inset according to the graph padding
					port.setLocation(maxIOLabelSize, -1);
				}
				// needs dimensions for interface edit parts -> should not be too close together
				port.setDimensions(3, 3);
				return port;
	}

	private void createFBNetworkElementNodes(ElkNode elkGraph) {
		for (final FBNetworkElement elem : fbNetworkElements.keySet()) {
			final ElkNode networkElemElkNode = ElkGraphUtil.createNode(elkGraph);
			setNodeProperties(networkElemElkNode);
			final FBNetworkElementFigure figure = fbNetworkElements.get(elem);
			final Rectangle bounds = figure.getFBBounds();
			networkElemElkNode.setLocation(bounds.x, bounds.y);
			networkElemElkNode.setDimensions(bounds.preciseWidth(), bounds.preciseHeight());
			final ElkLabel label = ElkGraphUtil.createLabel(elem.getName(), networkElemElkNode);
			final Rectangle labelBounds = figure.getLabelBounds();
			label.setDimensions(labelBounds.width(), labelBounds.height());
			nodes.put(elem, networkElemElkNode);
			nodeMapping.put(networkElemElkNode, elem);
		}
	}

	private void createConnectionNodes(ElkNode elkGraph) {
		for (final org.eclipse.fordiac.ide.model.libraryElement.Connection conn : connections.keySet()) {
			final Connection connFig = connections.get(conn);

			START_POINT
			.setLocation(connFig.getSourceAnchor().getLocation(connFig.getSourceAnchor().getReferencePoint()));
			END_POINT.setLocation(connFig.getTargetAnchor().getLocation(connFig.getTargetAnchor().getReferencePoint()));

			connFig.translateToRelative(START_POINT);
			connFig.translateToRelative(END_POINT);

			final ElkPort sourcePort = createPort(START_POINT, conn.getSource(), elkGraph);
			final ElkPort destinationPort = getPort(END_POINT, conn.getDestination(), elkGraph);

			final ElkEdge edge = ElkGraphUtil.createSimpleEdge(sourcePort, destinationPort);
			elkGraph.getContainedEdges().add(edge);
			connEditParts.put(edge, conn);
		}
	}

	private void updateLayout(ElkNode graph) {
		/*
		 * interface elements need to be reset before every layout attempt
		 *
		 * issue: - layout command gets called within subapp/composite - data or event
		 * connections get hidden - layout command is called again - interface elements
		 * with connections attached get placed properly, those without stay where they
		 * were
		 */
		// FIXME resetInterfaceElements();

		graph.getContainedEdges().forEach(edge -> {
			final org.eclipse.fordiac.ide.model.libraryElement.Connection conn = connEditParts.get(edge);
			final org.eclipse.draw2d.Connection connFig = connections.get(conn);
			final PointList pointList = ((AbstractPointListShape) connFig).getPoints();

			final ElkPort startPort = ((ElkPort) edge.getSources().get(0));
			final ElkPort endPort = ((ElkPort) edge.getTargets().get(0));

			pointList.removeAllPoints();
			pointList.addPoint((int) (startPort.getX() + startPort.getParent().getX()),
					(int) (startPort.getY() + startPort.getParent().getY()));
			edge.getSections().forEach(edgeSection -> {
				edgeSection.getBendPoints()
				.forEach(point -> pointList.addPoint((int) point.getX(), (int) point.getY()));
			});
			pointList.addPoint((int) (endPort.getX() + endPort.getParent().getX()),
					(int) (endPort.getY() + endPort.getParent().getY()));
			((AbstractPointListShape) connFig).setPoints(pointList);

			// FIXME layoutSubAppInterfaces(conn, startPort, endPort);
			updateModel(conn, pointList);
		});
		updateFBPositions(graph);
	}

	private void updateFBPositions(ElkNode graph) {
		graph.getChildren().forEach(node -> {
			final FBNetworkElement elem = nodeMapping.get(node);
			final FBNetworkElementFigure fig = fbNetworkElements.get(elem);
			if (null != elem) {
				double x = node.getX();
				if (fig.getLabelBounds().width() > fig.getFBBounds().width()) {
					x = node.getX() - (fig.getFBBounds().x() - fig.getLabelBounds().x());
				}
				elem.setX((int) x);
				elem.setY((int) node.getY());
			}
		});
	}

	private static void layoutSubAppInterfaces(ConnectionEditPart part, ElkPort startPort, ElkPort endPort) {
		if (part.getSource() instanceof SubAppInternalInterfaceEditPart) {
			final SubAppInternalInterfaceEditPart interfacePart = (SubAppInternalInterfaceEditPart) part.getSource();
			freeInterfaceFigure(interfacePart);
			final IFigure fig = interfacePart.getFigure();
			fig.getBounds().setLocation((int) (startPort.getX() - fig.getBounds().preciseWidth()),
					(int) (startPort.getY() - (fig.getBounds().preciseHeight() * 0.5)));
		} else if (part.getSource() instanceof CompositeInternalInterfaceEditPart) {
			final CompositeInternalInterfaceEditPart interfacePart = (CompositeInternalInterfaceEditPart) part
					.getSource();
			freeInterfaceFigure(interfacePart);
			final IFigure fig = interfacePart.getFigure();
			fig.getBounds().setLocation((int) (startPort.getX() - fig.getBounds().preciseWidth()),
					(int) (startPort.getY() - (fig.getBounds().preciseHeight() * 0.5)));
		}
		if (part.getTarget() instanceof SubAppInternalInterfaceEditPart) {
			final SubAppInternalInterfaceEditPart interfacePart = (SubAppInternalInterfaceEditPart) part.getTarget();
			freeInterfaceFigure(interfacePart);
			final IFigure fig = interfacePart.getFigure();
			fig.getBounds().setLocation((int) endPort.getX(),
					(int) (endPort.getY() - (fig.getBounds().preciseHeight() * 0.5)));
		} else if (part.getTarget() instanceof CompositeInternalInterfaceEditPart) {
			final CompositeInternalInterfaceEditPart interfacePart = (CompositeInternalInterfaceEditPart) part
					.getTarget();
			freeInterfaceFigure(interfacePart);
			final IFigure fig = interfacePart.getFigure();
			fig.getBounds().setLocation((int) endPort.getX(),
					(int) (endPort.getY() - (fig.getBounds().preciseHeight() * 0.5)));
		}
	}

	private static void freeInterfaceFigure(InterfaceEditPart interfacePart) {
		if (interfacePart.getParent() instanceof UISubAppNetworkEditPart) {
			((EditorWithInterfaceEditPart) interfacePart.getParent()).enableElkLayouting(interfacePart);
		} else if (interfacePart.getParent() instanceof CompositeNetworkEditPart) {
			((CompositeNetworkEditPart) interfacePart.getParent()).enableElkLayouting(interfacePart);
		}
	}

	private static void updateModel(org.eclipse.fordiac.ide.model.libraryElement.Connection connModel,
			PointList pointList) {
		if (pointList.size() > 2) {
			// 3 segments
			connModel.setDx1(pointList.getPoint(1).x() - pointList.getFirstPoint().x());
			connModel.setDx2(pointList.getLastPoint().x() - pointList.getPoint(pointList.size() - 2).x());
			if (pointList.size() > 4) {
				// 5 segments
				connModel.setDy(pointList.getPoint(2).y() - pointList.getFirstPoint().y());
			} else {
				connModel.setDy(0);
			}
		} else {
			// straight connection
			connModel.setDx1(0);
			connModel.setDx2(0);
			connModel.setDy(0);
		}
	}

}
