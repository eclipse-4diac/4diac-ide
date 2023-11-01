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

import java.text.MessageFormat;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.draw2d.geometry.PointList;
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
import org.eclipse.fordiac.ide.application.editparts.CommentEditPart;
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

	private void addSubAppPins(final ConnectionLayoutMapping mapping) {
		final SubAppForFBNetworkEditPart subapp = (SubAppForFBNetworkEditPart) ((UnfoldedSubappContentEditPart) mapping
				.getParentElement()).getParent();
		subapp.getChildren().stream().filter(UntypedSubAppInterfaceElementEditPart.class::isInstance)
				.map(UntypedSubAppInterfaceElementEditPart.class::cast).forEach(pin -> {
					addPort(pin, mapping, true);
					saveConnections(mapping, pin);
				});
	}

	private void addEditorPins(final ConnectionLayoutMapping mapping) {
		final EditorWithInterfaceEditPart editor = (EditorWithInterfaceEditPart) mapping.getParentElement();
		editor.getChildren().stream().filter(SubAppInternalInterfaceEditPart.class::isInstance)
				.map(SubAppInternalInterfaceEditPart.class::cast).forEach(pin -> {
					addPort(pin, mapping, true);
					saveConnections(mapping, pin);
				});
	}

	protected void handleChild(final ConnectionLayoutMapping mapping, final Object child) {
		if (child instanceof final GroupEditPart group) {
			processGroup(mapping, group);
		}
		if (child instanceof final CommentEditPart comment) {
			processComment(mapping, comment);
		}
		if (child instanceof final AbstractFBNElementEditPart fbnEl) {
			processBlock(mapping, fbnEl);
		}
		if (child instanceof final ValueEditPart value) {
			processValue(mapping, value);
		}
	}

	private static void processComment(final ConnectionLayoutMapping mapping, final CommentEditPart comment) {
		final ElkNode node = factory.createElkNode();
		final ElkNode layoutGraph = mapping.getLayoutGraph();

		final Rectangle bounds = comment.getFigure().getBounds();
		// translate from absolute to relative
		node.setLocation(bounds.x - layoutGraph.getX(), bounds.y - layoutGraph.getY());
		node.setDimensions(bounds.preciseWidth(), bounds.preciseHeight());
		layoutGraph.getChildren().add(node);
	}

	private static void processValue(final ConnectionLayoutMapping mapping, final ValueEditPart value) {
		if (value.getModel().getParentIE().getInputConnections().isEmpty()) {
			// only add the value if the pin does not have connections
			final ElkNode node = factory.createElkNode();
			final ElkNode layoutGraph = mapping.getLayoutGraph();

			final Rectangle bounds = value.getFigure().getBounds();
			node.setLocation(bounds.x - layoutGraph.getX(), bounds.y - layoutGraph.getY()); // translate from absolute
																							// to relative
			node.setDimensions(bounds.preciseWidth(), bounds.preciseHeight());
			layoutGraph.getChildren().add(node);
		}
	}

	private void processBlock(final ConnectionLayoutMapping mapping, final AbstractFBNElementEditPart block) {
		final ElkNode node = factory.createElkNode();
		final ElkNode layoutGraph = mapping.getLayoutGraph();

		final Rectangle bounds = block.getFigure().getBounds();
		node.setLocation(bounds.x - layoutGraph.getX(), bounds.y - layoutGraph.getY()); // translate from absolute to
																						// relative
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

		for (final GraphicalEditPart child : block.getChildren()) {
			if (child instanceof final InterfaceEditPart ie) {
				addPort(ie, mapping, false);
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
		return (ep.getModel() instanceof final SubApp subApp) && subApp.isUnfolded();
	}

	protected static Group getGroupFromModel(final Object model) {
		return model instanceof FBNetworkElement ? (Group) ((FBNetworkElement) model).getGroup() : null;
	}

	private static void processConnections(final ConnectionLayoutMapping mapping) {
		for (final ConnectionEditPart conn : mapping.getConnections()) {
			final InterfaceEditPart source = (InterfaceEditPart) conn.getSource();
			final ElkPort sourcePort = getPort(source, mapping);
			Assert.isNotNull(sourcePort, MessageFormat.format("Source port for pin: {0} should not be null!", //$NON-NLS-1$
					source.getModel().getQualifiedName()));

			final InterfaceEditPart target = (InterfaceEditPart) conn.getTarget();
			final ElkPort destinationPort = getPort(target, mapping);
			Assert.isNotNull(destinationPort, MessageFormat.format("Destination port for pin: {0} should not be null!", //$NON-NLS-1$
					target.getModel().getQualifiedName()));

			final ElkEdge edge = factory.createElkEdge();
			mapping.getLayoutGraph().getContainedEdges().add(edge);
			edge.getSources().add(sourcePort);
			edge.getTargets().add(destinationPort);

			mapping.getGraphMap().put(edge, conn);
		}
	}

	private static ElkPort getPort(final InterfaceEditPart interfaceEditPart, final ConnectionLayoutMapping mapping) {
		return (ElkPort) mapping.getReverseMapping().get(interfaceEditPart);
	}

	private static void addPort(final InterfaceEditPart interfaceEditPart, final ConnectionLayoutMapping mapping,
			final boolean isGraphPin) {
		mapping.getReverseMapping().computeIfAbsent(interfaceEditPart,
				ie -> createPort(interfaceEditPart, mapping, isGraphPin));
	}

	private static ElkPort createPort(final InterfaceEditPart ie, final ConnectionLayoutMapping mapping,
			final boolean isGraphPin) {
		final EditPart parent = ie.getParent();
		final ElkNode parentNode = (ElkNode) mapping.getReverseMapping().get(parent);

		final ElkPort port = factory.createElkPort();
		mapping.getGraphMap().put(port, ie.getModel());

		final boolean isInput = ie.getModel().isIsInput(); // use the model to always get the right information
		final Rectangle ieBounds = ie.getFigure().getBounds();
		final ElkNode layoutGraph = mapping.getLayoutGraph();
		double y = ieBounds.getCenter().preciseY() - layoutGraph.getY();

		final ElkNode dummyNode = factory.createElkNode();
		dummyNode.setProperty(CoreOptions.PORT_CONSTRAINTS, PortConstraints.FIXED_POS);
		dummyNode.setDimensions(1, 1); // a min size is needed that the layout alg uses the node.
		dummyNode.getPorts().add(port);
		layoutGraph.getChildren().add(dummyNode);
		mapping.getPortParentMapping().put(port, dummyNode);

		if (isGraphPin) {
			// Dummy node is needed to get better layout results for expanded subapps.
			port.setProperty(CoreOptions.PORT_SIDE, isInput ? PortSide.EAST : PortSide.WEST);
			final double x = isInput ? 0 : layoutGraph.getWidth();
			dummyNode.setLocation(x, y);
		} else {
			// Dummy node ensures that connections are treated as hyperedges.
			port.setProperty(CoreOptions.PORT_SIDE, isInput ? PortSide.WEST : PortSide.EAST);
			final double x = isInput ? 0 : parentNode.getWidth();
			y -= parentNode.getY();
			dummyNode.setLocation(parentNode.getX() + x, parentNode.getY() + y);
		}

		return port;
	}

	protected void flattenGroup(final ConnectionLayoutMapping mapping, final GroupEditPart group) {
		final GraphicalEditPart groupNetwork = group.getContentEP();
		final Rectangle commentBounds = group.getCommentBounds();
		final ElkNode layoutGraph = mapping.getLayoutGraph();

		final ElkNode comment = factory.createElkNode();
		comment.setLocation(commentBounds.preciseX() - layoutGraph.getX() - GROUP_PADDING_HALF,
				commentBounds.preciseY() - layoutGraph.getY() - GROUP_PADDING_HALF);
		comment.setDimensions(commentBounds.preciseWidth() + GROUP_PADDING,
				commentBounds.preciseHeight() + GROUP_PADDING);
		layoutGraph.getChildren().add(comment);

		groupNetwork.getChildren().forEach(child -> handleChild(mapping, child));
	}

	@SuppressWarnings("static-method") // can be overridden for different group handling
	protected void processGroup(final ConnectionLayoutMapping mapping, final GroupEditPart group) {
		final ElkNode node = factory.createElkNode();
		mapping.getLayoutGraph().getChildren().add(node);

		final Rectangle bounds = group.getFigure().getBounds();
		node.setLocation(bounds.x - mapping.getLayoutGraph().getX() - GROUP_PADDING_HALF,
				bounds.y - mapping.getLayoutGraph().getY() - GROUP_PADDING_HALF);
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
			// do not really know why this happens, these lists should normally never be
			// empty
			// probably has something to do with editor pins
			return;
		}

		final ConnectionEditPart connEp = (ConnectionEditPart) mapping.getGraphMap().get(edge);
		final ElkPort startPort = (ElkPort) edge.getSources().get(0);
		final ElkPort endPort = (ElkPort) edge.getTargets().get(0);
		final ElkEdgeSection elkEdgeSection = edge.getSections().get(0);
		final List<ElkBendPoint> bendPoints = elkEdgeSection.getBendPoints();

		mapping.getLayoutData().addConnectionPoints(connEp.getModel(),
				createPointList(mapping, startPort, endPort, bendPoints));
	}

	private static PointList createPointList(final ConnectionLayoutMapping mapping, final ElkPort startPort,
			final ElkPort endPort, final List<ElkBendPoint> bendPoints) {
		// needs to translate coordinates back from relative to absolute

		final PointList list = new PointList();

		final ElkNode startNode = (ElkNode) mapping.getPortParentMapping().get(startPort);
		final ElkNode layoutGraph = startNode.getParent();
		final int startX = (int) (startPort.getX() + startNode.getX() + layoutGraph.getX());
		final int startY = (int) (startPort.getY() + startNode.getY() + layoutGraph.getY());
		list.addPoint(startX, startY);

		for (final ElkBendPoint point : bendPoints) {
			list.addPoint((int) (point.getX() + layoutGraph.getX()), (int) (point.getY() + layoutGraph.getY()));
		}

		final ElkNode endNode = (ElkNode) mapping.getPortParentMapping().get(endPort);
		final int endX = (int) (endPort.getX() + endNode.getX() + layoutGraph.getX());
		final int endY = (int) (endPort.getY() + endNode.getY() + layoutGraph.getY());
		list.addPoint(endX, endY);

		return list;
	}

}
