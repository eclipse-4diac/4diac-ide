/*******************************************************************************
 * Copyright (c) 2021, 2022 Primetals Technologies Austria GmbH
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

package org.eclipse.fordiac.ide.elk.helpers;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.elk.alg.layered.options.CrossingMinimizationStrategy;
import org.eclipse.elk.alg.layered.options.LayeredMetaDataProvider;
import org.eclipse.elk.alg.layered.options.LayeringStrategy;
import org.eclipse.elk.core.LayoutConfigurator;
import org.eclipse.elk.core.math.ElkPadding;
import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.elk.core.options.Direction;
import org.eclipse.elk.core.options.EdgeRouting;
import org.eclipse.elk.core.options.HierarchyHandling;
import org.eclipse.elk.core.options.PortConstraints;
import org.eclipse.elk.core.options.PortSide;
import org.eclipse.elk.core.service.DiagramLayoutEngine;
import org.eclipse.elk.graph.ElkConnectableShape;
import org.eclipse.elk.graph.ElkEdge;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.elk.graph.ElkPort;
import org.eclipse.elk.graph.util.ElkGraphUtil;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.application.editparts.EditorWithInterfaceEditPart;
import org.eclipse.fordiac.ide.application.editparts.GroupEditPart;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.gef.editparts.AbstractPositionableElementEditPart;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;

public class FordiacLayoutFactory {

	private FordiacLayoutFactory() {
		throw new IllegalStateException("Factory class"); //$NON-NLS-1$
	}

	public static ElkNode createFordiacLayoutGraph(final boolean hasProperties) {
		final ElkNode graph = ElkGraphUtil.createGraph();
		final ElkNode parent = ElkGraphUtil.createGraph();
		graph.setParent(parent);
		if (hasProperties) {
			configureGraph(graph);
		}
		return graph;
	}

	public static ElkNode createFordiacLayoutNode(final AbstractPositionableElementEditPart editPart, final ElkNode parent) {
		final ElkNode node = ElkGraphUtil.createNode(parent);
		configureNode(node);
		if (editPart instanceof GroupEditPart) {
			node.setProperty(CoreOptions.PORT_CONSTRAINTS, PortConstraints.FREE); // dummy ports can move freely
			node.setProperty(CoreOptions.PADDING, new ElkPadding(50.0, 50.0));
		}
		if (editPart instanceof SubAppForFBNetworkEditPart) {
			if (((SubAppForFBNetworkEditPart) editPart).getModel().isUnfolded()) {
				configureUnfoldedSubapp(node);
			} else {
				configureSubapp(node);
			}
		}
		return node;
	}

	public static ElkEdge createFordiacLayoutEdge(final ConnectionEditPart editPart, final ElkNode parent, final ElkConnectableShape source, final ElkConnectableShape target) {
		return ElkGraphUtil.createSimpleEdge(source, target);
	}

	public static ElkPort createFordiacLayoutPort(final InterfaceEditPart editPart, final ElkNode parent, final Point point) {
		final ElkPort port = ElkGraphUtil.createPort(parent);
		port.setDimensions(1, editPart.getFigure().getBounds().height);
		port.setProperty(CoreOptions.PORT_SIDE, editPart.isInput() ? PortSide.WEST : PortSide.EAST);
		if (!(editPart.getParent() instanceof EditorWithInterfaceEditPart)) {
			port.setLocation(point.preciseX() - parent.getX(), point.preciseY() - parent.getY());
		}
		return port;
	}

	private static void configureGraph(final ElkNode graph) {
		graph.setProperty(CoreOptions.ALGORITHM, "org.eclipse.elk.layered") //$NON-NLS-1$
		.setProperty(CoreOptions.EDGE_ROUTING, EdgeRouting.ORTHOGONAL)
		.setProperty(CoreOptions.DIRECTION, Direction.RIGHT)
		.setProperty(CoreOptions.PORT_CONSTRAINTS, PortConstraints.FIXED_ORDER)
		.setProperty(CoreOptions.SPACING_PORT_PORT, Double.valueOf(0))
		.setProperty(LayeredMetaDataProvider.THOROUGHNESS, Integer.valueOf(10))
		.setProperty(CoreOptions.PADDING, new ElkPadding(50.0, 20.0))
        .setProperty(LayeredMetaDataProvider.CROSSING_MINIMIZATION_STRATEGY, CrossingMinimizationStrategy.LAYER_SWEEP)
		.setProperty(CoreOptions.HIERARCHY_HANDLING, HierarchyHandling.INCLUDE_CHILDREN)
		.setProperty(LayeredMetaDataProvider.LAYERING_STRATEGY, LayeringStrategy.MIN_WIDTH);
		configureSpacing(graph);

	}

	private static void configureNode(final ElkNode node) {
		node.setProperty(CoreOptions.PORT_CONSTRAINTS, PortConstraints.FIXED_POS);
	}

	private static void configureSubapp(final ElkNode node) {
		// nothing to do for now. Allows for a finer configuration if necessary.
	}

	private static void configureUnfoldedSubapp(final ElkNode node) {
		node.setProperty(CoreOptions.PADDING, new ElkPadding(100.0));
		configureSpacing(node);
	}

	private static void configureSpacing(final ElkNode node) {
		node.setProperty(CoreOptions.SPACING_NODE_NODE, Double.valueOf(25))
		.setProperty(LayeredMetaDataProvider.SPACING_NODE_NODE_BETWEEN_LAYERS, Double.valueOf(80));
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