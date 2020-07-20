/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University, Linz
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

package org.eclipse.fordiac.ide.elk.handlers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.geometry.Point;
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
import org.eclipse.fordiac.ide.application.editparts.AbstractFBNElementEditPart;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.application.editparts.SubAppInternalInterfaceEditPart;
import org.eclipse.fordiac.ide.elk.commands.LayoutCommand;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.gef.editparts.ValueEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class LayoutHandler extends AbstractHandler {

	private Set<AbstractFBNElementEditPart> fbNetworkElements = new HashSet<>();
	private Set<ConnectionEditPart> connections = new HashSet<>();
	private Set<ValueEditPart> values = new HashSet<>();
	private Map<FBNetworkElement, ElkNode> nodes = new HashMap<>();
	private Map<IInterfaceElement, ElkPort> ports = new HashMap<>();
	private Map<ElkEdge, ConnectionEditPart> connEditParts = new HashMap<>();
	private Map<ElkNode, AbstractFBNElementEditPart> nodeMapping = new HashMap<>();

	private static final PrecisionPoint START_POINT = new PrecisionPoint();
	private static final PrecisionPoint END_POINT = new PrecisionPoint();

	private int maxIOLabelSize;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		clear();
		IEditorPart editor = HandlerUtil.getActiveEditor(event);
		GraphicalViewer viewer = editor.getAdapter(GraphicalViewer.class);
		if (null != viewer) {
			collectEditParts(viewer);
			// build up the Elk DataStructure
			ElkNode layoutGraph = createElkGraph();
			// empty parent graph for SubApps
			ElkNode parent = ElkGraphUtil.createGraph();
			layoutGraph.setParent(parent);

			setGraphProperties(layoutGraph);

			// run layout algorithms
			RecursiveGraphLayoutEngine engine = new RecursiveGraphLayoutEngine();
			engine.layout(layoutGraph, new NullElkProgressMonitor());

			// create command to enable undo/redo and saving
			Command cmd = new LayoutCommand(layoutGraph, connEditParts, nodeMapping);

			((AbstractGraphicalEditPart) viewer.getRootEditPart()).getFigure().invalidateTree();
			((AbstractGraphicalEditPart) viewer.getRootEditPart()).getFigure().revalidate();

			editor.getAdapter(CommandStack.class).execute(cmd);
		}
		return null;
	}

	private void setPortProperties(ElkPort port, boolean isInput) {
		if (isInput) {
			port.setProperty(CoreOptions.PORT_SIDE, PortSide.WEST);
		} else {
			port.setProperty(CoreOptions.PORT_SIDE, PortSide.EAST);
		}
	}

	private void setNodeProperties(ElkNode node) {
		node.setProperty(LayeredMetaDataProvider.NODE_PLACEMENT_BK_FIXED_ALIGNMENT, FixedAlignment.NONE);
		node.setProperty(LayeredMetaDataProvider.CROSSING_MINIMIZATION_STRATEGY,
				CrossingMinimizationStrategy.INTERACTIVE);
		node.setProperty(CoreOptions.PORT_CONSTRAINTS, PortConstraints.FIXED_POS);
		node.setProperty(CoreOptions.INSIDE_SELF_LOOPS_ACTIVATE, true);
		node.setProperty(CoreOptions.NODE_LABELS_PLACEMENT, NodeLabelPlacement.insideTopCenter());
	}

	private void setGraphProperties(ElkNode layoutGraph) {
		layoutGraph.setProperty(CoreOptions.ALGORITHM, "org.eclipse.elk.layered"); //$NON-NLS-1$
		layoutGraph.setProperty(CoreOptions.EDGE_ROUTING, EdgeRouting.ORTHOGONAL);
		layoutGraph.setProperty(CoreOptions.DIRECTION, Direction.RIGHT);
		layoutGraph.setProperty(LayeredMetaDataProvider.NODE_PLACEMENT_STRATEGY, NodePlacementStrategy.LINEAR_SEGMENTS);
		layoutGraph.setProperty(LayeredMetaDataProvider.THOROUGHNESS, 10000);
		layoutGraph.setProperty(LayeredMetaDataProvider.LAYERING_NODE_PROMOTION_STRATEGY,
				NodePromotionStrategy.NO_BOUNDARY);
		layoutGraph.setProperty(CoreOptions.SPACING_NODE_NODE, (double) 40);
		layoutGraph.setProperty(LayeredMetaDataProvider.NODE_PLACEMENT_FAVOR_STRAIGHT_EDGES, false);
		// set padding according to the number of in and outputs (for subapps)
		layoutGraph.setProperty(CoreOptions.PADDING, new ElkPadding(30, maxIOLabelSize + 30, 0, maxIOLabelSize + 30));
	}

	private void clear() {
		fbNetworkElements.clear();
		connections.clear();
		nodes.clear();
		ports.clear();
		connEditParts.clear();
		nodeMapping.clear();
		values.clear();
		// set a min for the padding
		maxIOLabelSize = 60;
	}

	public ElkNode createElkGraph() {
		// Root node T is the editor view (drawing area), graph itself is represented by
		// an ElkNode because each ElkNode can contain other Elk nodes
		ElkNode elkGraph = ElkGraphUtil.createGraph(); // root node

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

	private void createValueLabels(ElkNode elkGraph) {
		for (ValueEditPart part : values) {
			Object iePart = part.getViewer().getEditPartRegistry().get(part.getModel().getVarDeclaration());
			if (iePart instanceof InterfaceEditPart) {
				Point point = ((InterfaceEditPart) iePart).getFigure().getBounds().getTopLeft();
				ElkPort port = getPort(point, part.getModel().getVarDeclaration(), elkGraph);
				ElkLabel label = ElkGraphUtil.createLabel(part.getModel().getValue(), port);
				Rectangle bounds = part.getFigure().getBounds();
				label.setLocation(bounds.preciseX() - port.getX() - port.getParent().getX(),
						bounds.preciseY() - port.getY() - port.getParent().getY());
				label.setDimensions(bounds.preciseWidth(), bounds.preciseHeight());
			}

		}
	}

	private void createFBNetworkElementNodes(ElkNode elkGraph) {
		for (AbstractFBNElementEditPart elem : fbNetworkElements) {
			ElkNode networkElemElkNode = ElkGraphUtil.createNode(elkGraph);
			setNodeProperties(networkElemElkNode);
			Rectangle bounds = elem.getFigure().getFBBounds();
			networkElemElkNode.setLocation(bounds.x, bounds.y);
			networkElemElkNode.setDimensions(bounds.preciseWidth(), bounds.preciseHeight());
			ElkLabel label = ElkGraphUtil.createLabel(elem.getModel().getName(), networkElemElkNode);
			Rectangle labelBounds = elem.getFigure().getLabelBounds();
			label.setDimensions(labelBounds.width(), labelBounds.height());
			nodes.put(elem.getModel(), networkElemElkNode);
			nodeMapping.put(networkElemElkNode, elem);
		}
	}

	private void createConnectionNodes(ElkNode elkGraph) {
		for (ConnectionEditPart conn : connections) {
			Connection connFig = (Connection) conn.getFigure();

			START_POINT
					.setLocation(connFig.getSourceAnchor().getLocation(connFig.getSourceAnchor().getReferencePoint()));
			END_POINT.setLocation(connFig.getTargetAnchor().getLocation(connFig.getTargetAnchor().getReferencePoint()));

			connFig.translateToRelative(START_POINT);
			connFig.translateToRelative(END_POINT);

			ElkPort sourcePort = createPort(START_POINT, conn.getModel().getSource(), elkGraph);
			ElkPort destinationPort = getPort(END_POINT, conn.getModel().getDestination(), elkGraph);

			ElkEdge edge = ElkGraphUtil.createSimpleEdge(sourcePort, destinationPort);
			elkGraph.getContainedEdges().add(edge);
			connEditParts.put(edge, conn);
		}
	}

	private ElkPort createPort(Point point, IInterfaceElement interfaceElement, ElkNode graph) {
		ElkNode node = (null != interfaceElement.getFBNetworkElement())
				? nodes.get(interfaceElement.getFBNetworkElement())
				: graph;
		// for untyped subapp interfaces
		boolean isSubApp = false;
		if (node == null) {
			node = graph;
			isSubApp = true;
		}
		ElkPort port = ElkGraphUtil.createPort(node);
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
		port.setDimensions(0, 0);
		return port;
	}

	private ElkPort getPort(Point point, IInterfaceElement interfaceElement, ElkNode graph) {
		return ports.computeIfAbsent(interfaceElement, ie -> createPort(point, ie, graph));
	}

	private void collectEditParts(GraphicalViewer viewer) {
		@SuppressWarnings("unchecked")
		Map<Object, Object> editPartSet = viewer.getEditPartRegistry();
		editPartSet.values().forEach(entry -> {
			if (entry instanceof AbstractFBNElementEditPart) {
				fbNetworkElements.add((AbstractFBNElementEditPart) entry);
			}
			if (entry instanceof ConnectionEditPart) {
				connections.add((ConnectionEditPart) entry);
			}
			if (entry instanceof ValueEditPart) {
				values.add((ValueEditPart) entry);
			}
			if (entry instanceof SubAppInternalInterfaceEditPart) {
				SubAppInternalInterfaceEditPart part = (SubAppInternalInterfaceEditPart) entry;
				calculateSubAppPadding(part);
			}
		});
	}

	private void calculateSubAppPadding(SubAppInternalInterfaceEditPart part) {
		/*
		 * maxIOLabelSize contains the width of the biggest input label and is used to
		 * calculate the padding-left
		 */
		if (part.getFigure().getBounds().width > maxIOLabelSize) {
			maxIOLabelSize = part.getFigure().getBounds().width;
		}
	}

}
