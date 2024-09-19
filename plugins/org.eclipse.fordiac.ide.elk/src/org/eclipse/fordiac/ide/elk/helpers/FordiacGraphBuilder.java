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
package org.eclipse.fordiac.ide.elk.helpers;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.elk.core.options.NodeLabelPlacement;
import org.eclipse.elk.core.options.PortSide;
import org.eclipse.elk.graph.ElkEdge;
import org.eclipse.elk.graph.ElkGraphElement;
import org.eclipse.elk.graph.ElkLabel;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.elk.graph.ElkPort;
import org.eclipse.elk.graph.util.ElkGraphUtil;
import org.eclipse.fordiac.ide.application.editparts.AbstractFBNElementEditPart;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.application.editparts.EditorWithInterfaceEditPart;
import org.eclipse.fordiac.ide.application.editparts.GroupContentEditPart;
import org.eclipse.fordiac.ide.application.editparts.GroupEditPart;
import org.eclipse.fordiac.ide.application.editparts.UnfoldedSubappContentEditPart;
import org.eclipse.fordiac.ide.application.figures.FBNetworkConnection;
import org.eclipse.fordiac.ide.elk.FordiacLayoutMapping;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.gef.editparts.ValueEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;

public final class FordiacGraphBuilder {

	private static final PrecisionPoint START_POINT = new PrecisionPoint();
	private static final PrecisionPoint END_POINT = new PrecisionPoint();

	public static void build(final FordiacLayoutMapping mapping) {
		buildGraphRecursively(mapping, mapping.getLayoutGraph(), (GraphicalEditPart) mapping.getParentElement(), 0);
		processConnections(mapping);
		processHierarchyCrossingConnections(mapping);
	}

	private static void buildGraphRecursively(final FordiacLayoutMapping mapping, final ElkNode parentLayoutNode,
			final GraphicalEditPart currentEditPart, final int yOffset) {
		for (final GraphicalEditPart child : currentEditPart.getChildren()) {
			if (child instanceof GroupEditPart) {
				processGroup(mapping, parentLayoutNode, child);
			}
			if (child instanceof AbstractFBNElementEditPart) {
				processFB(mapping, parentLayoutNode, child, yOffset);
			}
			if (child instanceof InterfaceEditPart) {
				processInterface(mapping, child);
			}
			if (child instanceof final ValueEditPart vep) {
				processValue(mapping, vep);
			}
		}
	}

	// creates graph elements for normal FBs and subapps
	private static void processFB(final FordiacLayoutMapping mapping, final ElkNode parentLayoutNode,
			final Object child, final int yOffset) {
		final AbstractFBNElementEditPart childEditPart = (AbstractFBNElementEditPart) child;
		final ElkNode node = createNode(mapping, childEditPart, parentLayoutNode, yOffset);
		buildGraphRecursively(mapping, node, childEditPart, yOffset);
	}

	// creates a group node and DIRECTLY passes the group content to the recursion
	// this can be done because groups do not have interface pins
	private static void processGroup(final FordiacLayoutMapping mapping, final ElkNode parentLayoutNode,
			final Object child) {
		final GroupEditPart group = (GroupEditPart) child;
		final ElkNode node = createGroupNode(mapping, group, parentLayoutNode);
		// TODO get rid of magic number
		// don't yet know how to calculate the exact offset but 7 seems to do the trick
		final int offset = group.getCommentBounds().height + 7;
		buildGraphRecursively(mapping, node, group.getContentEP(), offset);
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

	private static void processInterface(final FordiacLayoutMapping mapping, final Object child) {
		final InterfaceEditPart ep = ((InterfaceEditPart) child);

		// target connections would be inside the subapp and do not need to be saved
		if (isUnfoldedSubAppInterface(ep) && !ep.isInput()) {
			return;
		}

		// since we iterate over every interface its enough to only save target
		// connections
		ep.getTargetConnections().stream().filter(ConnectionEditPart.class::isInstance)
				.filter(con -> isVisible((ConnectionEditPart) con))
				.forEach(conn -> saveConnection(mapping, (ConnectionEditPart) conn));

		// add all editor interfaces to the elk graph to ensure the right order in the
		// sidebar
		if (ep.getParent() instanceof EditorWithInterfaceEditPart) {
			final ElkPort port = getPort(new Point(0, 0), ep, mapping); // point is irrelevant since the interface
																		// element gets moved along the graph border
																		// (sidebar)
			// override the port side because isInput() is negated for internal subapp pins
			port.setProperty(CoreOptions.PORT_SIDE, !ep.isInput() ? PortSide.WEST : PortSide.EAST);
		}
	}

	public static boolean isVisible(final ConnectionEditPart con) {
		final FBNetworkConnection conFigure = con.getFigure();
		return conFigure.isVisible() && !conFigure.isHidden();
	}

	private static boolean isUnfoldedSubAppInterface(final InterfaceEditPart ep) {
		return (ep.getParent().getModel() instanceof final SubApp subApp && subApp.isUnfolded());
	}

	private static void saveConnection(final FordiacLayoutMapping mapping, final ConnectionEditPart conn) {
		if (!mapping.getConnections().contains(conn)) {
			final Object sourceContainer = conn.getSource().getParent().getParent();
			final Object targetContainer = conn.getTarget().getParent().getParent();

			if (sourceContainer instanceof UnfoldedSubappContentEditPart
					|| targetContainer instanceof UnfoldedSubappContentEditPart) {
				mapping.getFlatConnections().add(conn);
			} else if ((sourceContainer instanceof GroupContentEditPart
					|| targetContainer instanceof GroupContentEditPart) && sourceContainer != targetContainer) {
				mapping.getHierarchyCrossingConnections().add(conn);
			} else {
				mapping.getConnections().add(conn);
			}
		}
	}

	private static ElkNode createGroupNode(final FordiacLayoutMapping mapping, final GroupEditPart editPart,
			final ElkNode parent) {
		final ElkNode node = FordiacLayoutFactory.createFordiacLayoutNode(editPart, parent);

		final Rectangle bounds = editPart.getFigure().getBounds();
		node.setLocation(bounds.x, bounds.y);
		node.setDimensions(bounds.preciseWidth(), bounds.preciseHeight());

		final ElkLabel label = ElkGraphUtil.createLabel(editPart.getModel().getName(), node);
		final Rectangle commentBounds = editPart.getCommentBounds();
		if (commentBounds != null) {
			final int BORDER_THICKNESS = 2;
			label.setDimensions(commentBounds.preciseWidth() + BORDER_THICKNESS,
					commentBounds.preciseHeight() + BORDER_THICKNESS);
		}

		// position properties
		node.setProperty(CoreOptions.NODE_LABELS_PLACEMENT, NodeLabelPlacement.insideTopCenter());
		label.setProperty(CoreOptions.NODE_LABELS_PLACEMENT, NodeLabelPlacement.insideTopCenter());

		mapping.getGraphMap().put(node, editPart);
		mapping.getReverseMapping().put(editPart, node);

		return node;
	}

	private static ElkNode createNode(final FordiacLayoutMapping mapping, final AbstractFBNElementEditPart editPart,
			final ElkNode parent, final int yOffset) {
		final ElkNode node = FordiacLayoutFactory.createFordiacLayoutNode(editPart, parent);

		final Rectangle bounds = editPart.getFigure().getBounds();
		node.setLocation(bounds.x, bounds.y - yOffset);
		node.setDimensions(bounds.preciseWidth(), bounds.preciseHeight());

		final ElkLabel label = ElkGraphUtil.createLabel(editPart.getModel().getName(), node);
		final Rectangle labelBounds = editPart.getFigure().getLabelBounds();
		label.setDimensions(labelBounds.width(), labelBounds.height());

		mapping.getGraphMap().put(node, editPart);
		mapping.getReverseMapping().put(editPart, node);

		return node;
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

			final ElkEdge edge = FordiacLayoutFactory.createFordiacLayoutEdge(conn, mapping.getLayoutGraph(),
					sourcePort, destinationPort);
			mapping.getGraphMap().put(edge, conn);
			mapping.getReverseMapping().put(conn, edge);
		}
	}

	private static void processHierarchyCrossingConnections(final FordiacLayoutMapping mapping) {
		/*
		 * The idea for hierarchy crossing connections (in the context of groups) is to
		 * split the connection and connect them with a dummy port on the group node.
		 * All connections that share a source/destination are connected to the same
		 * dummy port. This leads to more straight lines inside of groups and reduces
		 * the problems that arise from too many bendpoints. (max. 5 segment
		 * connections)
		 */
		for (final ConnectionEditPart conn : mapping.getHierarchyCrossingConnections()) {
			final Object sourceContainer = conn.getSource().getParent().getParent();
			final Object targetContainer = conn.getTarget().getParent().getParent();

			final org.eclipse.draw2d.Connection connFig = conn.getFigure();
			START_POINT
					.setLocation(connFig.getSourceAnchor().getLocation(connFig.getSourceAnchor().getReferencePoint()));
			END_POINT.setLocation(connFig.getTargetAnchor().getLocation(connFig.getTargetAnchor().getReferencePoint()));
			connFig.translateToRelative(START_POINT);
			connFig.translateToRelative(END_POINT);

			final boolean incoming = targetContainer instanceof GroupContentEditPart;
			final boolean outgoing = sourceContainer instanceof GroupContentEditPart;

			if (incoming && outgoing) {
				// from group to group
				process3PartConnection(mapping, conn, sourceContainer, targetContainer);
			} else if (incoming || outgoing) {
				// to/from network to/from group
				process2PartConnection(mapping, conn, sourceContainer, targetContainer, incoming);
			}
		}
	}

	private static void process2PartConnection(final FordiacLayoutMapping mapping, final ConnectionEditPart conn,
			final Object sourceContainer, final Object targetContainer, final boolean incoming) {
		final EditPart group = incoming ? ((EditPart) targetContainer).getParent()
				: ((EditPart) sourceContainer).getParent();
		final ElkGraphElement groupNode = mapping.getReverseMapping().get(group);

		final ElkPort sourcePort = getPort(START_POINT, (InterfaceEditPart) conn.getSource(), mapping);
		final ElkPort targetPort = getPort(END_POINT, (InterfaceEditPart) conn.getTarget(), mapping);

		ElkPort dummyPort = mapping.getDummyPorts().get(incoming ? targetPort : sourcePort);
		if (dummyPort == null) {
			// not yet created
			dummyPort = ElkGraphUtil.createPort((ElkNode) groupNode);
			mapping.getDummyPorts().put(incoming ? targetPort : sourcePort, dummyPort);
		}

		final ElkEdge edge1 = FordiacLayoutFactory.createFordiacLayoutEdge(conn, mapping.getLayoutGraph(), sourcePort,
				dummyPort);
		final ElkEdge edge2 = FordiacLayoutFactory.createFordiacLayoutEdge(conn, mapping.getLayoutGraph(), dummyPort,
				targetPort);

		mapping.getHierarchyCrossingConnectionsReverseMapping().put(edge1, conn);
		mapping.getHierarchyCrossingConnectionsReverseMapping().put(edge2, conn);

		final List<ElkEdge> list = new LinkedList<>();
		list.add(edge1);
		list.add(edge2);
		mapping.getHierarchyCrossingConnectionsMapping().put(conn, list);
	}

	private static void process3PartConnection(final FordiacLayoutMapping mapping, final ConnectionEditPart conn,
			final Object sourceContainer, final Object targetContainer) {
		final EditPart sourceGroup = ((EditPart) sourceContainer).getParent();
		final EditPart targetGroup = ((EditPart) targetContainer).getParent();

		final ElkGraphElement sourceGroupNode = mapping.getReverseMapping().get(sourceGroup);
		final ElkGraphElement targetGroupNode = mapping.getReverseMapping().get(targetGroup);

		final ElkPort sourcePort = getPort(START_POINT, (InterfaceEditPart) conn.getSource(), mapping);
		final ElkPort targetPort = getPort(END_POINT, (InterfaceEditPart) conn.getTarget(), mapping);

		ElkPort sourceGroupDummyPort = mapping.getDummyPorts().get(sourcePort);
		ElkPort targetGroupDummyPort = mapping.getDummyPorts().get(targetPort);

		if (sourceGroupDummyPort == null) {
			// not yet created
			sourceGroupDummyPort = ElkGraphUtil.createPort((ElkNode) sourceGroupNode);
			mapping.getDummyPorts().put(sourcePort, sourceGroupDummyPort);
		}
		if (targetGroupDummyPort == null) {
			// not yet created
			targetGroupDummyPort = ElkGraphUtil.createPort((ElkNode) targetGroupNode);
			mapping.getDummyPorts().put(targetPort, targetGroupDummyPort);
		}

		final ElkEdge edge1 = FordiacLayoutFactory.createFordiacLayoutEdge(conn, mapping.getLayoutGraph(), sourcePort,
				sourceGroupDummyPort);
		final ElkEdge edge2 = FordiacLayoutFactory.createFordiacLayoutEdge(conn, mapping.getLayoutGraph(),
				sourceGroupDummyPort, targetGroupDummyPort);
		final ElkEdge edge3 = FordiacLayoutFactory.createFordiacLayoutEdge(conn, mapping.getLayoutGraph(),
				targetGroupDummyPort, targetPort);

		mapping.getHierarchyCrossingConnectionsReverseMapping().put(edge1, conn);
		mapping.getHierarchyCrossingConnectionsReverseMapping().put(edge2, conn);
		mapping.getHierarchyCrossingConnectionsReverseMapping().put(edge3, conn);

		final List<ElkEdge> list = new LinkedList<>();
		list.add(edge1);
		list.add(edge2);
		list.add(edge3);
		mapping.getHierarchyCrossingConnectionsMapping().put(conn, list);
	}

	private static ElkPort getPort(final Point point, final InterfaceEditPart interfaceEditPart,
			final FordiacLayoutMapping mapping) {
		return (ElkPort) mapping.getReverseMapping().computeIfAbsent(interfaceEditPart,
				ie -> createPort(point, interfaceEditPart, mapping));
	}

	private static ElkPort createPort(final Point point, final InterfaceEditPart interfaceEditPart,
			final FordiacLayoutMapping mapping) {
		final EditPart parent = interfaceEditPart.getParent();
		final ElkNode parentNode = (ElkNode) mapping.getReverseMapping().get(parent);
		final ElkPort port = FordiacLayoutFactory.createFordiacLayoutPort(interfaceEditPart, parentNode, point);
		mapping.getGraphMap().put(port, interfaceEditPart.getModel());
		return port;
	}

	private FordiacGraphBuilder() {
	}

}
