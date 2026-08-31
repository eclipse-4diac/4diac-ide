/*******************************************************************************
 * Copyright (c) 2020, 2025 Johannes Kepler University Linz,
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

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.elk.core.options.PortConstraints;
import org.eclipse.elk.core.options.PortSide;
import org.eclipse.elk.graph.ElkEdge;
import org.eclipse.elk.graph.ElkLabel;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.elk.graph.ElkPort;
import org.eclipse.elk.graph.util.ElkGraphUtil;
import org.eclipse.fordiac.ide.application.editparts.AbstractBlockFBNElementEditPart;
import org.eclipse.fordiac.ide.application.editparts.CommentEditPart;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.application.editparts.EditorWithInterfaceEditPart;
import org.eclipse.fordiac.ide.application.editparts.GroupContentEditPart;
import org.eclipse.fordiac.ide.application.editparts.GroupEditPart;
import org.eclipse.fordiac.ide.application.editparts.UnfoldedSubappContentEditPart;
import org.eclipse.fordiac.ide.application.editparts.UntypedSubAppInterfaceElementEditPart;
import org.eclipse.fordiac.ide.application.figures.FBNetworkConnection;
import org.eclipse.fordiac.ide.gef.editparts.AbstractFBNetworkEditPart;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.gef.editparts.ValueEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.ui.IEditorPart;

public final class FordiacGraphBuilder {

	private final FordiacLayoutMapping mapping;

	public FordiacGraphBuilder(final IEditorPart part, final AbstractFBNetworkEditPart ep) {
		this.mapping = createFordiacLayoutMapping(part, ep);
	}

	public FordiacLayoutMapping build() {
		if (isParentInterfaceLayout(mapping.getParentElement())) {
			processParentInterfaces();
		}

		for (final Object child : mapping.getParentElement().getChildren()) {
			processChild(child);
		}

		processConnections();

		return mapping;
	}

	private void processParentInterfaces() {
		final ElkNode inputInterfaceBarNode = createInputIBNode();
		final ElkNode outputInterfaceBarNode = createOutputIBNode();

		final List<? extends GraphicalEditPart> children = switch (mapping.getParentElement()) {
		case final EditorWithInterfaceEditPart edWithEp -> edWithEp.getChildren();
		case final UnfoldedSubappContentEditPart unfoldedSubappContentEP ->
			unfoldedSubappContentEP.getParent().getChildren();
		default -> throw new IllegalArgumentException("Wrong parent editpart given"); //$NON-NLS-1$
		};

		// @formatter:off
		children.stream()
				.filter(InterfaceEditPart.class::isInstance)
				.map(InterfaceEditPart.class::cast)
				.forEach(ie -> {
					createParentElementPort(ie, inputInterfaceBarNode, outputInterfaceBarNode);
					processInterface(ie);
				});
		// @formatter:on
	}

	private ElkNode createInputIBNode() {
		final ElkNode inputNode = createNode(getInputIBFigure());
		inputNode.setProperty(CoreOptions.PORT_CONSTRAINTS, PortConstraints.FIXED_POS);
		return inputNode;
	}

	private ElkNode createOutputIBNode() {
		final ElkNode outputNode = createNode(getOutputIBFigure());
		outputNode.setProperty(CoreOptions.PORT_CONSTRAINTS, PortConstraints.FIXED_POS);
		return outputNode;
	}

	private IFigure getInputIBFigure() {
		return switch (mapping.getParentElement()) {
		case final EditorWithInterfaceEditPart edWithEp -> edWithEp.getLeftInterfaceContainer();
		case final UnfoldedSubappContentEditPart unfoldedSubappContentEP ->
			unfoldedSubappContentEP.getParent().getFigure().getExpandedInputFigure();
		default -> throw new IllegalArgumentException("Wrong parent editpart given"); //$NON-NLS-1$
		};
	}

	private IFigure getOutputIBFigure() {
		return switch (mapping.getParentElement()) {
		case final EditorWithInterfaceEditPart edWithEp -> edWithEp.getRightInterfaceContainer();
		case final UnfoldedSubappContentEditPart unfoldedSubappContentEP ->
			unfoldedSubappContentEP.getParent().getFigure().getExpandedOutputFigure();
		default -> throw new IllegalArgumentException("Wrong parent editpart given"); //$NON-NLS-1$
		};
	}

	private void processChild(final Object child) {
		switch (child) {
		case final GroupEditPart _ -> {
			// TODO
		}
		case final CommentEditPart commentEp -> processComment(commentEp);
		case final AbstractBlockFBNElementEditPart fbnEl -> processFB(fbnEl);
		case final ValueEditPart value -> processValue(value);
		default -> {// nothing to be done in the default case
		}
		}
	}

	private void processComment(final CommentEditPart commentEp) {
		createNode(commentEp);
	}

	private void processFB(final AbstractBlockFBNElementEditPart ep) {
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
		if (valueEditPart.getModel().eContainer() instanceof final VarDeclaration varDecl
				&& !varDecl.getInputConnections().isEmpty()) {
			// ignore values for connected pins
			return;
		}
		final EditPart iePart = valueEditPart.getViewer().getEditPartForModel(valueEditPart.getModel().getParentIE());
		final ElkPort port = getPort((InterfaceEditPart) iePart);
		final ElkLabel label = ElkGraphUtil.createLabel(valueEditPart.getModel().getValue(), port);
		final Rectangle bounds = valueEditPart.getFigure().getBounds();
		label.setLocation(bounds.preciseX() - port.getX() - port.getParent().getX(),
				bounds.preciseY() - port.getY() - port.getParent().getY());
		label.setDimensions(bounds.preciseWidth(), bounds.preciseHeight());
	}

	private static boolean isParentInterfaceLayout(final GraphicalEditPart parentElement) {
		return parentElement instanceof EditorWithInterfaceEditPart
				|| parentElement instanceof UnfoldedSubappContentEditPart;
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

	private ElkNode createNode(final IFigure figure) {
		final ElkNode node = ElkGraphUtil.createNode(mapping.getLayoutGraph());
		final Rectangle bounds = figure.getBounds();
		node.setLocation(bounds.x, bounds.y);
		node.setDimensions(bounds.preciseWidth(), bounds.preciseHeight());
		return node;
	}

	private ElkNode createNode(final GraphicalEditPart editPart) {
		final ElkNode node = createNode(editPart.getFigure());

		mapping.getGraphMap().put(node, editPart);
		mapping.getReverseMapping().put(editPart, node);
		return node;
	}

	private void createFBNode(final AbstractBlockFBNElementEditPart editPart) {
		final ElkNode node = createNode(editPart);
		node.setProperty(CoreOptions.PORT_CONSTRAINTS, PortConstraints.FIXED_POS);

		final ElkLabel label = ElkGraphUtil.createLabel(editPart.getModel().getName(), node);
		final Rectangle labelBounds = editPart.getFigure().getLabelBounds();
		label.setDimensions(labelBounds.width(), labelBounds.height());
	}

	private void processConnections() {
		for (final ConnectionEditPart conn : mapping.getConnections()) {
			final ElkPort sourcePort = getPort((InterfaceEditPart) conn.getSource());
			final ElkPort destinationPort = getPort((InterfaceEditPart) conn.getTarget());

			final ElkEdge edge = ElkGraphUtil.createSimpleEdge(sourcePort, destinationPort);

			mapping.getGraphMap().put(edge, conn);
			mapping.getReverseMapping().put(conn, edge);
		}
	}

	private ElkPort getPort(final InterfaceEditPart interfaceEditPart) {
		return (ElkPort) mapping.getReverseMapping().computeIfAbsent(interfaceEditPart,
				_ -> createPort(interfaceEditPart));
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

	private static void setPortLocation(final ElkPort port, final InterfaceEditPart interfaceEditPart,
			final ElkNode parentNode) {
		final int x = interfaceEditPart.isInput() ? 0 : (int) parentNode.getWidth() - 1;
		final int y = interfaceEditPart.getFigure().getLocation().y - (int) parentNode.getY();
		port.setLocation(x, y);
	}

	private ElkPort createParentElementPort(final InterfaceEditPart ie, final ElkNode inputInterfaceBarNode,
			final ElkNode outputInterfaceBarNode) {
		final boolean isInput = ie.getModel().isIsInput();
		final ElkNode parentNode = isInput ? inputInterfaceBarNode : outputInterfaceBarNode;

		final ElkPort port = ElkGraphUtil.createPort(parentNode);
		port.setDimensions(1, ie.getFigure().getBounds().height);

		final int y = ie.getFigure().getLocation().y;
		if (isInput) {
			port.setLocation(parentNode.getWidth() - 1, y - parentNode.getY());
		} else {
			port.setLocation(0, y - parentNode.getY());
		}

		// this is the opposite of FBs
		port.setProperty(CoreOptions.PORT_SIDE, isInput ? PortSide.EAST : PortSide.WEST);

		mapping.getGraphMap().put(port, ie.getModel());
		mapping.getReverseMapping().put(ie, port);
		return port;
	}

	private static FordiacLayoutMapping createFordiacLayoutMapping(final IEditorPart part,
			final AbstractFBNetworkEditPart ep) {
		final FordiacLayoutMapping newMapping = new FordiacLayoutMapping(part, ep);

		final ElkNode graph = ElkGraphUtil.createGraph();

		newMapping.setLayoutGraph(graph);
		newMapping.setParentElement(ep);

		newMapping.getGraphMap().put(graph, ep);
		newMapping.getReverseMapping().put(ep, graph);
		return newMapping;
	}

}
