/*******************************************************************************
 * Copyright (c) 2020, 2024 Johannes Kepler University Linz,
 * 							Primetals Technologies Germany GmbH,
 * 							Primetals Technologies Austria GmbH
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

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.elk.core.options.PortSide;
import org.eclipse.elk.graph.ElkEdge;
import org.eclipse.elk.graph.ElkLabel;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.elk.graph.ElkPort;
import org.eclipse.elk.graph.util.ElkGraphUtil;
import org.eclipse.fordiac.ide.application.editparts.AbstractFBNElementEditPart;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.application.editparts.GroupContentEditPart;
import org.eclipse.fordiac.ide.application.editparts.GroupEditPart;
import org.eclipse.fordiac.ide.application.editparts.UnfoldedSubappContentEditPart;
import org.eclipse.fordiac.ide.application.editparts.UntypedSubAppInterfaceElementEditPart;
import org.eclipse.fordiac.ide.application.figures.FBNetworkConnection;
import org.eclipse.fordiac.ide.elk.FordiacLayoutMapping.LayoutType;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.gef.editparts.ValueEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;

public final class FordiacGraphBuilder {

	private static final PrecisionPoint START_POINT = new PrecisionPoint();
	private static final PrecisionPoint END_POINT = new PrecisionPoint();

	public static void build(final FordiacLayoutMapping mapping) {
		if (mapping.type != LayoutType.Application) {
			processParentInterfaces(mapping);
		}

		for (final Object child : mapping.getParentElement().getChildren()) {
			processChild(mapping, child);
		}

		processConnections(mapping);
	}

	private static void processParentInterfaces(final FordiacLayoutMapping mapping) {
		final List<? extends EditPart> children = switch (mapping.type) {
		case LayoutType.Typed -> mapping.getParentElement().getChildren();
		case LayoutType.Unfolded -> mapping.getParentElement().getParent().getChildren();
		default -> throw new IllegalArgumentException("Unexpected value: " + mapping.type); //$NON-NLS-1$
		};
		// @formatter:off
		children.stream()
				.filter(InterfaceEditPart.class::isInstance)
				.map(InterfaceEditPart.class::cast)
				.forEach(ie -> {
					createParentElementPort(ie, mapping);
					processInterface(mapping, ie);
				});
		// @formatter:on
	}

	private static void processChild(final FordiacLayoutMapping mapping, final Object child) {
		if (child instanceof final GroupEditPart group) {
			// TODO
		}
		if (child instanceof final AbstractFBNElementEditPart fbnEl) {
			processFB(mapping, fbnEl);
		}
		if (child instanceof final ValueEditPart value) {
			processValue(mapping, value);
		}
	}

	private static void processFB(final FordiacLayoutMapping mapping, final GraphicalEditPart ep) {
		createNode(mapping, (AbstractFBNElementEditPart) ep, mapping.getLayoutGraph());
		for (final Object child : ep.getChildren()) {
			if (child instanceof final InterfaceEditPart ie) {
				processInterface(mapping, ie);
			}
		}
	}

	private static void processInterface(final FordiacLayoutMapping mapping, final Object child) {
		if (child instanceof final UntypedSubAppInterfaceElementEditPart ie && !ie.isInput()
				&& ie.getParent() != mapping.getNetworkEditPart().getParent()) {
			return;
		}
		if (child instanceof final UntypedSubAppInterfaceElementEditPart ie && ie.isInput()
				&& ie.getParent() == mapping.getNetworkEditPart().getParent()) {
			return;
		}

		// @formatter:off
		((InterfaceEditPart) child).getTargetConnections().stream()
				.filter(ConnectionEditPart.class::isInstance)
				.filter(con -> isVisible((ConnectionEditPart) con))
				.forEach(conn -> saveConnection(mapping, (ConnectionEditPart) conn));
		// @formatter:on
	}

	private static void processValue(final FordiacLayoutMapping mapping, final ValueEditPart valueEditPart) {
		final EditPart iePart = valueEditPart.getViewer().getEditPartForModel(valueEditPart.getModel().getParentIE());
		final Point point = ((InterfaceEditPart) iePart).getFigure().getBounds().getTopLeft();
		final ElkPort port = getPort(point, (InterfaceEditPart) iePart, mapping);
		final ElkLabel label = ElkGraphUtil.createLabel(valueEditPart.getModel().getValue(), port);
		final Rectangle bounds = valueEditPart.getFigure().getBounds();
		label.setLocation(bounds.preciseX() - port.getX() - port.getParent().getX(),
				bounds.preciseY() - port.getY() - port.getParent().getY());
		label.setDimensions(bounds.preciseWidth(), bounds.preciseHeight());
	}

	private static boolean isVisible(final ConnectionEditPart con) {
		final FBNetworkConnection conFigure = con.getFigure();
		return conFigure.isVisible() && !conFigure.isHidden();
	}

	private static void saveConnection(final FordiacLayoutMapping mapping, final ConnectionEditPart conn) {
		if (!mapping.getConnections().contains(conn)) {
			final Object sourceContainer = conn.getSource().getParent().getParent();
			final Object targetContainer = conn.getTarget().getParent().getParent();

			if (sourceContainer instanceof UnfoldedSubappContentEditPart
					|| targetContainer instanceof UnfoldedSubappContentEditPart) {
				mapping.getConnections().add(conn);
			} else if ((sourceContainer instanceof GroupContentEditPart
					|| targetContainer instanceof GroupContentEditPart) && sourceContainer != targetContainer) {
				// TODO groups
			} else {
				mapping.getConnections().add(conn);
			}
		}
	}

	private static void createNode(final FordiacLayoutMapping mapping, final AbstractFBNElementEditPart editPart,
			final ElkNode parent) {
		final ElkNode node = ElkGraphUtil.createNode(parent);
		final Rectangle bounds = editPart.getFigure().getBounds();
		if (mapping.type != LayoutType.Application) {
			// @formatter:off
			node.setLocation(
					bounds.x - mapping.getLayoutGraph().getX(),
					bounds.y - mapping.getLayoutGraph().getY()
				);
			// @formatter:on
		} else {
			node.setLocation(bounds.x, bounds.y);
		}

		node.setDimensions(bounds.preciseWidth(), bounds.preciseHeight());

		final ElkLabel label = ElkGraphUtil.createLabel(editPart.getModel().getName(), node);
		final Rectangle labelBounds = editPart.getFigure().getLabelBounds();
		label.setDimensions(labelBounds.width(), labelBounds.height());

		mapping.getGraphMap().put(node, editPart);
		mapping.getReverseMapping().put(editPart, node);
	}

	private static void processConnections(final FordiacLayoutMapping mapping) {
		for (final ConnectionEditPart conn : mapping.getConnections()) {
			final org.eclipse.draw2d.Connection connFig = conn.getFigure();

			START_POINT
					.setLocation(connFig.getSourceAnchor().getLocation(connFig.getSourceAnchor().getReferencePoint()));
			END_POINT.setLocation(connFig.getTargetAnchor().getLocation(connFig.getTargetAnchor().getReferencePoint()));

			connFig.translateToRelative(START_POINT);
			connFig.translateToRelative(END_POINT);

			final ElkPort sourcePort = getPort(START_POINT, (InterfaceEditPart) conn.getSource(), mapping);
			final ElkPort destinationPort = getPort(END_POINT, (InterfaceEditPart) conn.getTarget(), mapping);

			final ElkEdge edge = ElkGraphUtil.createSimpleEdge(sourcePort, destinationPort);

			mapping.getGraphMap().put(edge, conn);
			mapping.getReverseMapping().put(conn, edge);
		}
	}

	private static ElkPort getPort(final Point point, final InterfaceEditPart interfaceEditPart,
			final FordiacLayoutMapping mapping) {
		return (ElkPort) mapping.getReverseMapping().computeIfAbsent(interfaceEditPart,
				ie -> createPort(point, interfaceEditPart, mapping));
	}

	private static ElkPort createPort(final Point point, final InterfaceEditPart ie,
			final FordiacLayoutMapping mapping) {
		final EditPart parent = ie.getParent();
		ElkNode parentNode = (ElkNode) mapping.getReverseMapping().get(parent);
		if (parent == mapping.getParentElement().getParent()) {
			parentNode = mapping.getLayoutGraph();
		}

		final ElkPort port = ElkGraphUtil.createPort(parentNode);
		port.setDimensions(1, 1);
		final int x = ie.isInput() ? 0 : (int) parentNode.getWidth();
		final int y = point.y - (int) parentNode.getY() - (int) mapping.getLayoutGraph().getY();
		port.setLocation(x, y);
		port.setProperty(CoreOptions.PORT_SIDE, ie.isInput() ? PortSide.WEST : PortSide.EAST);

		mapping.getGraphMap().put(port, ie.getModel());
		return port;
	}

	private static ElkPort createParentElementPort(final InterfaceEditPart ie, final FordiacLayoutMapping mapping) {
		final var layoutGraph = mapping.getLayoutGraph();

		final ElkPort port = ElkGraphUtil.createPort(layoutGraph);
		port.setDimensions(1, ie.getFigure().getBounds().height);

		final int y = ie.getFigure().getLocation().y;
		final boolean isInput = mapping.type == LayoutType.Unfolded ? ie.isInput() : !ie.isInput();
		if (isInput) {
			port.setLocation(0, y - layoutGraph.getY());
		} else {
			port.setLocation(layoutGraph.getWidth() - 1, y - layoutGraph.getY());
		}

		port.setProperty(CoreOptions.PORT_SIDE, isInput ? PortSide.WEST : PortSide.EAST);

		mapping.getGraphMap().put(port, ie.getModel());
		mapping.getReverseMapping().put(ie, port);
		return port;
	}

	private FordiacGraphBuilder() {
		throw new UnsupportedOperationException("Utility Class should not be instantiated!"); //$NON-NLS-1$
	}

}
