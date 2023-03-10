/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.elk.connection;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.elk.core.options.PortConstraints;
import org.eclipse.elk.core.options.PortSide;
import org.eclipse.elk.graph.ElkBendPoint;
import org.eclipse.elk.graph.ElkEdge;
import org.eclipse.elk.graph.ElkEdgeSection;
import org.eclipse.elk.graph.ElkGraphFactory;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.elk.graph.ElkPort;
import org.eclipse.fordiac.ide.application.editparts.AbstractFBNElementEditPart;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.application.editparts.EditorWithInterfaceEditPart;
import org.eclipse.fordiac.ide.application.editparts.GroupEditPart;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.application.editparts.SubAppInternalInterfaceEditPart;
import org.eclipse.fordiac.ide.application.editparts.UnfoldedSubappContentEditPart;
import org.eclipse.fordiac.ide.application.editparts.UntypedSubAppInterfaceElementEditPart;
import org.eclipse.fordiac.ide.elk.FordiacLayoutData;
import org.eclipse.fordiac.ide.elk.helpers.FordiacGraphBuilder;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.gef.editparts.ValueEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;

public abstract class AbstractConnectionRoutingHelper {

	protected static ElkGraphFactory factory = ElkGraphFactory.eINSTANCE;
	private static final PrecisionPoint START_POINT = new PrecisionPoint();
	private static final PrecisionPoint END_POINT = new PrecisionPoint();
	private static final double GROUP_PADDING = 20;
	private static final double GROUP_PADDING_HALF = GROUP_PADDING / 2;

	public void buildGraph(final ConnectionLayoutMapping mapping) {
		if (mapping.getParentElement() instanceof EditorWithInterfaceEditPart) {
			addEditorPins(mapping);
		} else if (mapping.isExpandedSubapp()) {
			addSubAppPins(mapping);
		}

		for (final Object child : ((GraphicalEditPart) mapping.getParentElement()).getChildren()) {
			handleChild(mapping, child);
		}

		processConnections(mapping);
	}

	@SuppressWarnings("unchecked")
	private void addSubAppPins(final ConnectionLayoutMapping mapping) {
		final SubAppForFBNetworkEditPart subapp = (SubAppForFBNetworkEditPart) ((UnfoldedSubappContentEditPart) mapping.getParentElement()).getParent();
		final List<UntypedSubAppInterfaceElementEditPart> pins = (List<UntypedSubAppInterfaceElementEditPart>) subapp.getChildren()
				.stream()
				.filter(UntypedSubAppInterfaceElementEditPart.class::isInstance)
				.collect(Collectors.toList());

		for (final UntypedSubAppInterfaceElementEditPart pin : pins) {
			final ElkPort port = getPort(null, pin, mapping, true);
			port.setProperty(CoreOptions.PORT_SIDE, !pin.isInput() ? PortSide.WEST : PortSide.EAST);
			saveConnections(mapping, pin);
		}
	}

	@SuppressWarnings({ "unchecked" })
	private void addEditorPins(final ConnectionLayoutMapping mapping) {
		final EditorWithInterfaceEditPart editor = (EditorWithInterfaceEditPart) mapping.getParentElement();
		final List<SubAppInternalInterfaceEditPart> pins = (List<SubAppInternalInterfaceEditPart>) editor.getChildren()
				.stream()
				.filter(InterfaceEditPart.class::isInstance)
				.collect(Collectors.toList());

		for (final InterfaceEditPart pin : pins) {
			final ElkPort port = getPort(null, pin, mapping, true);
			port.setProperty(CoreOptions.PORT_SIDE, !pin.isInput() ? PortSide.WEST : PortSide.EAST);
			saveConnections(mapping, pin);
		}
	}

	protected void handleChild(final ConnectionLayoutMapping mapping, final Object child) {
		if (child instanceof GroupEditPart) {
			processGroup(mapping, (GroupEditPart) child);
		}
		if (child instanceof AbstractFBNElementEditPart) {
			processBlock(mapping, (AbstractFBNElementEditPart) child);
		}
		if (child instanceof ValueEditPart) {
			processValue(mapping, (ValueEditPart) child);
		}
	}

	private static void processValue(final ConnectionLayoutMapping mapping, final ValueEditPart value) {
		if (value.getModel().getParentIE().getInputConnections().isEmpty()) {
			// only add the value if the pin does not have connections
			final ElkNode node = factory.createElkNode();
			final ElkNode layoutGraph = mapping.getLayoutGraph();

			final Rectangle bounds = value.getFigure().getBounds();
			node.setLocation(bounds.x - layoutGraph.getX(), bounds.y - layoutGraph.getY()); // translate from absolute to relative
			node.setDimensions(bounds.preciseWidth(), bounds.preciseHeight());
			layoutGraph.getChildren().add(node);
		}
	}

	private void processBlock(final ConnectionLayoutMapping mapping, final AbstractFBNElementEditPart block) {
		final ElkNode node = factory.createElkNode();
		final ElkNode layoutGraph = mapping.getLayoutGraph();

		final Rectangle bounds = block.getFigure().getBounds();
		node.setLocation(bounds.x - layoutGraph.getX(), bounds.y - layoutGraph.getY()); // translate from absolute to relative
		node.setDimensions(bounds.preciseWidth(), bounds.preciseHeight());

		node.setProperty(CoreOptions.PORT_CONSTRAINTS, PortConstraints.FIXED_POS);

		layoutGraph.getChildren().add(node);

		if (isExpandedSubApp(block)) {
			final SubAppForFBNetworkEditPart subapp = (SubAppForFBNetworkEditPart) block;
			final UnfoldedSubappContentEditPart content = (UnfoldedSubappContentEditPart) subapp.getContentEP();
			mapping.getExpandedSubapps().add(content);
		}

		// save for connection processing (ports need to know their parent)
		mapping.getReverseMapping().put(block, node);

		for (final Object child : block.getChildren()) {
			if (child instanceof InterfaceEditPart) {
				final InterfaceEditPart ie = (InterfaceEditPart) child;

				final Rectangle ieBounds = ie.getFigure().getBounds();
				getPort(new PrecisionPoint(ieBounds.preciseX(), ieBounds.preciseY()), ie, mapping, false);

				// target connections would be inside the subapp and does not need to be saved
				if (isExpandedSubAppInterface(ie) && !ie.isInput()) {
					continue;
				}

				saveConnections(mapping, ie);
			}
		}
	}

	private static boolean isExpandedSubAppInterface(final InterfaceEditPart ep) {
		return isExpandedSubApp(ep.getParent());
	}

	private static boolean isExpandedSubApp(final EditPart ep) {
		if (ep instanceof SubAppForFBNetworkEditPart) {
			final SubApp model = ((SubAppForFBNetworkEditPart) ep).getModel();
			return model.isUnfolded();
		}
		return false;
	}

	protected static Group getGroupFromModel(final Object model) {
		return model instanceof FBNetworkElement
				? (Group) ((FBNetworkElement) model).getGroup() : null;
	}

	private void processConnections(final ConnectionLayoutMapping mapping) {
		for (final ConnectionEditPart conn : mapping.getConnections()) {
			final org.eclipse.draw2d.Connection connFig = conn.getFigure();

			START_POINT.setLocation(connFig.getSourceAnchor().getLocation(connFig.getSourceAnchor().getReferencePoint()));
			END_POINT.setLocation(connFig.getTargetAnchor().getLocation(connFig.getTargetAnchor().getReferencePoint()));

			connFig.translateToRelative(START_POINT);
			connFig.translateToRelative(END_POINT);

			final InterfaceEditPart source = (InterfaceEditPart) conn.getSource();
			final ElkPort sourcePort = getPort(START_POINT, source, mapping, false);
			final InterfaceEditPart target = (InterfaceEditPart) conn.getTarget();
			final ElkPort destinationPort = getPort(END_POINT, target, mapping, false);

			final ElkEdge edge = factory.createElkEdge();
			mapping.getLayoutGraph().getContainedEdges().add(edge);
			edge.getSources().add(sourcePort);
			edge.getTargets().add(destinationPort);

			mapping.getGraphMap().put(edge, conn);
		}
	}

	private ElkPort getPort(final Point point, final InterfaceEditPart interfaceEditPart, final ConnectionLayoutMapping mapping, final boolean isGraphPin) {
		return (ElkPort) mapping.getReverseMapping().computeIfAbsent(interfaceEditPart, ie -> createPort(point, interfaceEditPart, mapping, isGraphPin));
	}

	private static ElkPort createPort(final Point point, final InterfaceEditPart ie,
			final ConnectionLayoutMapping mapping, final boolean isGraphPin) {
		final EditPart parent = ie.getParent();
		ElkNode parentNode = (ElkNode) mapping.getReverseMapping().get(parent);

		final ElkPort port = factory.createElkPort();
		port.setProperty(CoreOptions.PORT_SIDE, ie.isInput() ? PortSide.WEST : PortSide.EAST);

		if (isGraphPin) {
			final Rectangle ieBounds = ie.getFigure().getBounds();
			final boolean isLeft = (parent instanceof EditorWithInterfaceEditPart) ? !ie.isInput() : ie.isInput();
			final double x = isLeft ? 0 : mapping.getLayoutGraph().getWidth();
			final double y = ieBounds.preciseY() - mapping.getLayoutGraph().getY() + 5;
			port.setLocation(x, y);

			// because ie parent is the subapp itself but the mapped edit part (for the layout graph)
			// is the content network (because we need to be able to distinguish between expanded and normal subapp)
			parentNode = mapping.getLayoutGraph();
		} else {
			final double x = (ie.isInput()) ? 0 : parentNode.getWidth();
			final double y = point.preciseY() - parentNode.getY() - mapping.getLayoutGraph().getY();
			port.setLocation(x, y);
		}

		parentNode.getPorts().add(port);

		mapping.getGraphMap().put(port, ie.getModel());
		return port;
	}

	protected void flattenGroup(final ConnectionLayoutMapping mapping, final GroupEditPart group) {
		final GraphicalEditPart groupNetwork = group.getContentEP();
		final Rectangle commentBounds = group.getCommentBounds();
		final ElkNode layoutGraph = mapping.getLayoutGraph();

		final ElkNode comment = factory.createElkNode();
		comment.setLocation(commentBounds.preciseX() - layoutGraph.getX() - GROUP_PADDING_HALF, commentBounds.preciseY() - layoutGraph.getY() - GROUP_PADDING_HALF);
		comment.setDimensions(commentBounds.preciseWidth() + GROUP_PADDING, commentBounds.preciseHeight() + GROUP_PADDING);
		layoutGraph.getChildren().add(comment);

		groupNetwork.getChildren().forEach(child -> handleChild(mapping, child));
	}

	@SuppressWarnings("static-method") // can be overridden for different group handling
	protected void processGroup(final ConnectionLayoutMapping mapping, final GroupEditPart group) {
		final ElkNode node = factory.createElkNode();
		mapping.getLayoutGraph().getChildren().add(node);

		final Rectangle bounds = group.getFigure().getBounds();
		node.setLocation(bounds.x - mapping.getLayoutGraph().getX() - GROUP_PADDING_HALF, bounds.y - mapping.getLayoutGraph().getY() - GROUP_PADDING_HALF);
		node.setDimensions(bounds.preciseWidth() + GROUP_PADDING, bounds.preciseHeight() + GROUP_PADDING);

		mapping.getGroups().add(group);
	}

	protected void saveConnections(final ConnectionLayoutMapping mapping, final InterfaceEditPart ie) {
		ie.getTargetConnections().stream().filter(ConnectionEditPart.class::isInstance)
		.filter(con -> FordiacGraphBuilder.isVisible((ConnectionEditPart) con))
		.forEach(con -> saveConnection(mapping, (ConnectionEditPart) con));
	}

	protected abstract void saveConnection(ConnectionLayoutMapping mapping, ConnectionEditPart con);

	public static FordiacLayoutData calculateConnections(final ConnectionLayoutMapping mapping) {
		mapping.getLayoutGraph().getContainedEdges().forEach(edge -> processConnection(mapping, edge));
		return mapping.getLayoutData();
	}

	private static void processConnection(final ConnectionLayoutMapping mapping, final ElkEdge edge) {

		if (edge.getSources().isEmpty() || edge.getTargets().isEmpty() || edge.getSections().isEmpty()) {
			// do not really know why this happens, these lists should normally never be empty
			// probably has something to do with editor pins
			return;
		}

		final ConnectionEditPart connEp = (ConnectionEditPart) mapping.getGraphMap().get(edge);
		final ElkPort startPort = (ElkPort) edge.getSources().get(0);
		final ElkPort endPort = (ElkPort) edge.getTargets().get(0);
		final ElkEdgeSection elkEdgeSection = edge.getSections().get(0);
		final List<ElkBendPoint> bendPoints = elkEdgeSection.getBendPoints();

		mapping.getLayoutData().addConnectionPoints(connEp.getModel(), createPointList(startPort, endPort, bendPoints));
	}

	private static PointList createPointList(final ElkPort startPort, final ElkPort endPort,
			final List<ElkBendPoint> bendPoints) {
		// needs to translate coordinates back from relative to absolute

		final PointList list = new PointList();

		final ElkNode layoutGraph;
		if (isEditorPort(startPort)) {
			layoutGraph = startPort.getParent();
			final int startX = (int) (startPort.getX() + layoutGraph.getX());
			final int startY = (int) (startPort.getY() + layoutGraph.getY());
			list.addPoint(startX, startY);
		} else {
			final ElkNode fb = startPort.getParent();
			layoutGraph = fb.getParent();
			final int startX = (int) (startPort.getX() + fb.getX() + layoutGraph.getX());
			final int startY = (int) (startPort.getY() + fb.getY() + layoutGraph.getY());
			list.addPoint(startX, startY);
		}

		for (final ElkBendPoint point : bendPoints) {
			list.addPoint((int) (point.getX() + layoutGraph.getX()), (int) (point.getY() + layoutGraph.getY()));
		}

		if (isEditorPort(endPort)) {
			final int endX = (int) (endPort.getX() + layoutGraph.getX());
			final int endY = (int) (endPort.getY() + layoutGraph.getY());
			list.addPoint(endX, endY);
		} else {
			final ElkNode fb = endPort.getParent();
			final int endX = (int) (endPort.getX() + fb.getX() + layoutGraph.getX());
			final int endY = (int) (endPort.getY() + fb.getY() + layoutGraph.getY());
			list.addPoint(endX, endY);
		}

		return list;
	}

	private static boolean isEditorPort(final ElkPort port) {
		/*
		 * FB 		-> 	1: FB Node, 2: layout graph, 3: dummy parent graph
		 * Editor 	-> 	1: layout graph, 2: dummy parent
		 */
		return port.getParent().getParent().getParent() == null;
	}

}
