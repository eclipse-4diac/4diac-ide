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
 *   Daniel Lindhuber
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.elk;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.elk.alg.layered.options.CrossingMinimizationStrategy;
import org.eclipse.elk.alg.layered.options.LayeredMetaDataProvider;
import org.eclipse.elk.alg.layered.options.NodePlacementStrategy;
import org.eclipse.elk.core.LayoutConfigurator;
import org.eclipse.elk.core.math.ElkPadding;
import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.elk.core.options.Direction;
import org.eclipse.elk.core.options.EdgeRouting;
import org.eclipse.elk.core.options.PortConstraints;
import org.eclipse.elk.core.service.DiagramLayoutEngine;
import org.eclipse.elk.graph.ElkConnectableShape;
import org.eclipse.elk.graph.ElkEdge;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.elk.graph.ElkPort;
import org.eclipse.elk.graph.util.ElkGraphUtil;
import org.eclipse.fordiac.ide.application.editparts.AbstractFBNElementEditPart;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;

public class FordiacLayoutFactory {

	private FordiacLayoutFactory() {
		throw new IllegalStateException("Factory class");
	}

	public static ElkNode createFordiacLayoutGraph() {
		final ElkNode graph = ElkGraphUtil.createGraph();
		final ElkNode parent = ElkGraphUtil.createGraph();
		graph.setParent(parent);
		configureGraph(graph);
		return graph;
	}

	public static ElkNode createFordiacLayoutNode(AbstractFBNElementEditPart editPart, ElkNode parent) {
		final ElkNode node = ElkGraphUtil.createNode(parent);
		configureNode(node);
		if (editPart instanceof SubAppForFBNetworkEditPart) {
			if (((SubAppForFBNetworkEditPart) editPart).getModel().isUnfolded()) {
				configureUnfoldedSubapp(node);
			} else {
				configureSubapp(node);
			}
		}
		return node;
	}

	public static ElkEdge createFordiacLayoutEdge(ConnectionEditPart editPart, ElkNode parent, ElkConnectableShape source, ElkConnectableShape target) {
		return ElkGraphUtil.createSimpleEdge(source, target);
	}

	public static ElkPort createFordiacLayoutPort(InterfaceEditPart editPart, ElkNode parent, Point point) {
		final ElkPort port = ElkGraphUtil.createPort(parent);
		port.setLocation(point.preciseX() - parent.getX(), point.preciseY() - parent.getY());
		port.setDimensions(0, 0);
		return port;
	}
	
	private static void configureGraph(ElkNode graph) {
		graph.setProperty(CoreOptions.ALGORITHM, "org.eclipse.elk.layered") //$NON-NLS-1$
			.setProperty(CoreOptions.EDGE_ROUTING, EdgeRouting.ORTHOGONAL)
			.setProperty(CoreOptions.DIRECTION, Direction.RIGHT)
			.setProperty(CoreOptions.SEPARATE_CONNECTED_COMPONENTS, false)
			.setProperty(LayeredMetaDataProvider.NODE_PLACEMENT_STRATEGY, NodePlacementStrategy.NETWORK_SIMPLEX)
			.setProperty(LayeredMetaDataProvider.CROSSING_MINIMIZATION_STRATEGY,CrossingMinimizationStrategy.INTERACTIVE)
			.setProperty(LayeredMetaDataProvider.THOROUGHNESS, 10)
			.setProperty(CoreOptions.PADDING, new ElkPadding(100.0, 19.0)); // specific height padding to compensate for instance comment
		configureSpacing(graph);
		configureNode(graph); // port constraints in case of editor with interfaces
	}
	
	private static void configureNode(ElkNode node) {
		node.setProperty(CoreOptions.PORT_CONSTRAINTS, PortConstraints.FIXED_POS);	
	}

	private static void configureSubapp(ElkNode node) {
		// nothing to do for now. Allows for a finer configuration if necessary.
	}

	private static void configureUnfoldedSubapp(ElkNode node) {
		node.setProperty(CoreOptions.PADDING, new ElkPadding(50.0));
		configureSpacing(node);
	}

	private static void configureSpacing(ElkNode node) {
		node.setProperty(CoreOptions.SPACING_NODE_NODE, 25.0)
			.setProperty(CoreOptions.SPACING_EDGE_NODE, 25.0)
			.setProperty(CoreOptions.SPACING_EDGE_EDGE, 20.0)
			.setProperty(LayeredMetaDataProvider.SPACING_NODE_NODE_BETWEEN_LAYERS, 80.0)
			.setProperty(LayeredMetaDataProvider.SPACING_EDGE_NODE_BETWEEN_LAYERS, 20.0)
			.setProperty(LayeredMetaDataProvider.SPACING_EDGE_EDGE_BETWEEN_LAYERS, 15.0);
	}

	public static DiagramLayoutEngine.Parameters createLayoutParams() {
		final DiagramLayoutEngine.Parameters params = new DiagramLayoutEngine.Parameters();
		params.addLayoutRun(createConfigurator());
		return params;
	}


	private static LayoutConfigurator createConfigurator() {
		return new LayoutConfigurator();
	}

}
