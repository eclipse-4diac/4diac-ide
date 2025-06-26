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
import org.eclipse.elk.core.options.PortConstraints;
import org.eclipse.elk.core.options.PortSide;
import org.eclipse.elk.graph.ElkEdge;
import org.eclipse.elk.graph.ElkLabel;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.elk.graph.ElkPort;
import org.eclipse.elk.graph.util.ElkGraphUtil;
import org.eclipse.fordiac.ide.application.editparts.AbstractFBNElementEditPart;
import org.eclipse.fordiac.ide.application.editparts.CommentEditPart;
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

	private final FordiacLayoutMapping mapping;

	public FordiacGraphBuilder(final FordiacLayoutMapping mapping) {
		this.mapping = mapping;
	}

	public void build() {
		if (mapping.type != LayoutType.Application) {
			processParentInterfaces();
		}

		for (final Object child : mapping.getParentElement().getChildren()) {
			processChild(child);
		}

		processConnections();
	}

	private void processParentInterfaces() {
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
					createParentElementPort(ie);
					processInterface(ie);
				});
		// @formatter:on
	}

	private void processChild(final Object child) {
		switch (child) {
		case final GroupEditPart group -> {
			// TODO
		}
		case final CommentEditPart commentEp -> processComment(commentEp);
		case final AbstractFBNElementEditPart fbnEl -> processFB(fbnEl);
		case final ValueEditPart value -> processValue(value);
		default -> {// nothing to be done in the default case
		}
		}
	}

	private void processComment(final CommentEditPart commentEp) {
		createNode(commentEp);
	}

	private void processFB(final AbstractFBNElementEditPart ep) {
		createFBNode(ep);
		for (final Object child : ep.getChildren()) {
			if (child instanceof final InterfaceEditPart ie) {
				processInterface(ie);
			}
		}
	}

	private void processInterface(final Object child) {
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
				.forEach(conn -> saveConnection((ConnectionEditPart) conn));
		// @formatter:on
	}

	private void processValue(final ValueEditPart valueEditPart) {
		final EditPart iePart = valueEditPart.getViewer().getEditPartForModel(valueEditPart.getModel().getParentIE());
		final Point point = ((InterfaceEditPart) iePart).getFigure().getBounds().getTopLeft();
		final ElkPort port = getPort(point, (InterfaceEditPart) iePart);
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

	private void saveConnection(final ConnectionEditPart conn) {
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

	private ElkNode createNode(final GraphicalEditPart editPart) {
		final ElkNode node = ElkGraphUtil.createNode(mapping.getLayoutGraph());
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

		mapping.getGraphMap().put(node, editPart);
		mapping.getReverseMapping().put(editPart, node);
		return node;
	}

	private void createFBNode(final AbstractFBNElementEditPart editPart) {
		final ElkNode node = createNode(editPart);
		node.setProperty(CoreOptions.PORT_CONSTRAINTS, PortConstraints.FIXED_POS);

		final ElkLabel label = ElkGraphUtil.createLabel(editPart.getModel().getName(), node);
		final Rectangle labelBounds = editPart.getFigure().getLabelBounds();
		label.setDimensions(labelBounds.width(), labelBounds.height());
	}

	private void processConnections() {
		for (final ConnectionEditPart conn : mapping.getConnections()) {
			final org.eclipse.draw2d.Connection connFig = conn.getFigure();

			START_POINT
					.setLocation(connFig.getSourceAnchor().getLocation(connFig.getSourceAnchor().getReferencePoint()));
			END_POINT.setLocation(connFig.getTargetAnchor().getLocation(connFig.getTargetAnchor().getReferencePoint()));

			connFig.translateToRelative(START_POINT);
			connFig.translateToRelative(END_POINT);

			final ElkPort sourcePort = getPort(START_POINT, (InterfaceEditPart) conn.getSource());
			final ElkPort destinationPort = getPort(END_POINT, (InterfaceEditPart) conn.getTarget());

			final ElkEdge edge = ElkGraphUtil.createSimpleEdge(sourcePort, destinationPort);

			mapping.getGraphMap().put(edge, conn);
			mapping.getReverseMapping().put(conn, edge);
		}
	}

	private ElkPort getPort(final Point point, final InterfaceEditPart interfaceEditPart) {
		return (ElkPort) mapping.getReverseMapping().computeIfAbsent(interfaceEditPart,
				ie -> createPort(interfaceEditPart));
	}

	private ElkPort createPort(final InterfaceEditPart interfaceEditPart) {
		final EditPart parent = interfaceEditPart.getParent();
		final ElkNode parentNode = determineParentNode(parent);
		final ElkPort port = ElkGraphUtil.createPort(parentNode);

		configurePortDimensions(port, interfaceEditPart);
		setPortLocation(port, interfaceEditPart, parentNode);
		port.setProperty(CoreOptions.PORT_SIDE, interfaceEditPart.isInput() ? PortSide.WEST : PortSide.EAST);
		mapping.getGraphMap().put(port, interfaceEditPart.getModel());
		return port;
	}

	private ElkNode determineParentNode(final EditPart parent) {
		ElkNode parentNode = (ElkNode) mapping.getReverseMapping().get(parent);
		if (parent == mapping.getParentElement().getParent()) {
			parentNode = mapping.getLayoutGraph();
		}
		return parentNode;
	}

	private static void configurePortDimensions(final ElkPort port, final InterfaceEditPart interfaceEditPart) {
		final var figure = interfaceEditPart.getFigure();
		port.setDimensions(1, figure.getBounds().preciseHeight());
	}

	private void setPortLocation(final ElkPort port, final InterfaceEditPart interfaceEditPart,
			final ElkNode parentNode) {
		final int x = interfaceEditPart.isInput() ? 0 : (int) parentNode.getWidth();
		final int yOffset = interfaceEditPart.getFigure().getLocation().y - (int) parentNode.getY();
		final int y = (mapping.type == LayoutType.Application) ? yOffset
				: yOffset - (int) mapping.getLayoutGraph().getY();
		port.setLocation(x, y);
	}

	private ElkPort createParentElementPort(final InterfaceEditPart ie) {
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

}
