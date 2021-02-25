/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 * 				 2020 Primetals Technologies Germany GmbH
 * 				 2021 Primetals Technologies Austria GmbH
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.elk.core.service.IDiagramLayoutConnector;
import org.eclipse.elk.core.service.LayoutMapping;
import org.eclipse.elk.graph.ElkEdge;
import org.eclipse.elk.graph.ElkGraphElement;
import org.eclipse.elk.graph.ElkLabel;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.elk.graph.ElkPort;
import org.eclipse.elk.graph.properties.IProperty;
import org.eclipse.elk.graph.properties.IPropertyHolder;
import org.eclipse.elk.graph.properties.Property;
import org.eclipse.elk.graph.util.ElkGraphUtil;
import org.eclipse.fordiac.ide.application.editparts.AbstractFBNElementEditPart;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.application.editparts.EditorWithInterfaceEditPart;
import org.eclipse.fordiac.ide.application.editparts.FBEditPart;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.application.editparts.UnfoldedSubappContentEditPart;
import org.eclipse.fordiac.ide.elk.commands.LayoutCommand;
import org.eclipse.fordiac.ide.gef.editparts.AbstractFBNetworkEditPart;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.gef.editparts.ValueEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.IWorkbenchPart;

public class FordiacLayoutConnector implements IDiagramLayoutConnector {

	private static final IProperty<AbstractFBNetworkEditPart> NETWORK_EDIT_PART = new Property<>("gef.networkEditPart");
	private static final IProperty<CommandStack> COMMAND_STACK = new Property<>("gef.commandStack");
	private static final IProperty<List<ConnectionEditPart>> CONNECTIONS = new Property<>("gef.connections");
	private static final IProperty<Map<GraphicalEditPart, ElkGraphElement>> REVERSE_MAPPING = new Property<>("gef.reverseMapping");

	private static final PrecisionPoint START_POINT = new PrecisionPoint();
	private static final PrecisionPoint END_POINT = new PrecisionPoint();

	@Override
	public LayoutMapping buildLayoutGraph(IWorkbenchPart workbenchPart, Object diagramPart) {
		final LayoutMapping mapping = initMapping(workbenchPart);
		findRootEditPart(mapping, workbenchPart);

		if (mapping.getProperty(NETWORK_EDIT_PART) != null) {
			createGraphRoot(mapping);
			buildGraphRecursively(mapping, mapping.getLayoutGraph(), (GraphicalEditPart) mapping.getParentElement());
			processConnections(mapping);
		}

		return mapping;
	}

	private static LayoutMapping initMapping(IWorkbenchPart workbenchPart) {
		final LayoutMapping mapping = new LayoutMapping(workbenchPart);
		mapping.setProperty(COMMAND_STACK, workbenchPart.getAdapter(CommandStack.class));
		mapping.setProperty(CONNECTIONS, new ArrayList<>());
		mapping.setProperty(REVERSE_MAPPING, new HashMap<>());
		return mapping;
	}

	private static void findRootEditPart(LayoutMapping mapping, IWorkbenchPart workbenchPart) {
		// TODO find better way to get the root edit part

		final Map<Object, Object> editPartSet = workbenchPart.getAdapter(GraphicalViewer.class).getEditPartRegistry();
		for (final Object ep : editPartSet.values()) {
			if (ep instanceof AbstractFBNetworkEditPart && !(ep instanceof UnfoldedSubappContentEditPart)) {
				mapping.setProperty(NETWORK_EDIT_PART, (AbstractFBNetworkEditPart) ep);
				break;
			}
		}

	}

	private static void createGraphRoot(LayoutMapping mapping) {
		final AbstractFBNetworkEditPart networkEditPart = mapping.getProperty(NETWORK_EDIT_PART);
		final ElkNode graph = FordiacLayoutFactory.createFordiacLayoutGraph();
		setGraphBounds(graph, networkEditPart);
		mapping.setLayoutGraph(graph);
		mapping.setParentElement(networkEditPart);
		mapping.getGraphMap().put(graph, networkEditPart);
		mapping.getProperty(REVERSE_MAPPING).put(networkEditPart, graph);
	}

	private static void setGraphBounds(ElkNode graph, AbstractFBNetworkEditPart networkEditPart) {
		Rectangle bounds = null;
		if (networkEditPart instanceof EditorWithInterfaceEditPart) {
			final Object figure = ((IFigure) networkEditPart.getFigure().getChildren().get(0)).getChildren().stream()
					.filter(child -> child instanceof FreeformLayer).findFirst().orElse(null);
			if (figure instanceof IFigure) {
				bounds = ((IFigure) figure).getBounds();
			}
		} else {
			bounds = networkEditPart.getFigure().getBounds();
		}

		if (bounds != null) {
			graph.setLocation(bounds.preciseX(), bounds.preciseY());
			graph.setDimensions(bounds.preciseWidth(), bounds.preciseHeight());
		}
	}

	private static void buildGraphRecursively(LayoutMapping mapping, ElkNode parentLayoutNode, GraphicalEditPart currentEditPart) {

		currentEditPart.getChildren().forEach(child -> {
			if (child instanceof FBEditPart) {
				final AbstractFBNElementEditPart childEditPart = (AbstractFBNElementEditPart) child;
				final ElkNode node = createNode(mapping, childEditPart, parentLayoutNode);
				buildGraphRecursively(mapping, node, childEditPart);
			}
			if (child instanceof SubAppForFBNetworkEditPart) {
				processSubApp(mapping, (SubAppForFBNetworkEditPart) child, parentLayoutNode);
			}
			if (child instanceof InterfaceEditPart) {
				((InterfaceEditPart) child).getTargetConnections().forEach(conn -> saveConnection(mapping, conn));
				((InterfaceEditPart) child).getSourceConnections().forEach(conn -> saveConnection(mapping, conn));
			}
			if (child instanceof ValueEditPart) {
				createValueLabels(mapping, (ValueEditPart) child);
			}
		});
	}

	private static void processSubApp(LayoutMapping mapping, SubAppForFBNetworkEditPart editPart, ElkNode parentLayoutNode) {
		final ElkNode node = createNode(mapping, editPart, parentLayoutNode);
		
		editPart.getChildren().forEach(child -> {
			if (child instanceof InterfaceEditPart) {
				final InterfaceEditPart ie = (InterfaceEditPart) child;
				ie.getTargetConnections().forEach(conn -> saveConnection(mapping, conn));
				if (editPart.getModel().isUnfolded()) {
					ie.getSourceConnections().forEach(conn -> saveConnection(mapping, conn));
				}
			}
			if (child instanceof UnfoldedSubappContentEditPart) {
				buildGraphRecursively(mapping, node, (GraphicalEditPart) child);
			}
		});
	}
	
	private static void saveConnection(LayoutMapping mapping, Object conn) {
		if (conn instanceof ConnectionEditPart && !mapping.getProperty(CONNECTIONS).contains(conn)) {
			mapping.getProperty(CONNECTIONS).add((ConnectionEditPart) conn); // save connections for later
		}
	}

	private static ElkNode createNode(LayoutMapping mapping, AbstractFBNElementEditPart editPart, ElkNode parent) {
		final ElkNode node = FordiacLayoutFactory.createFordiacLayoutNode(editPart, parent);
		final Rectangle bounds = editPart.getFigure().getFBBounds();
		node.setLocation(bounds.x, bounds.y);
		node.setDimensions(bounds.preciseWidth(), bounds.preciseHeight());
		final ElkLabel label = ElkGraphUtil.createLabel(editPart.getModel().getName(), node);
		final Rectangle labelBounds = editPart.getFigure().getLabelBounds();
		label.setDimensions(labelBounds.width(), labelBounds.height());

		mapping.getGraphMap().put(node, editPart);
		mapping.getProperty(REVERSE_MAPPING).put(editPart, node);

		return node;
	}

	private static void processConnections(LayoutMapping mapping) {
		for (final ConnectionEditPart conn : mapping.getProperty(CONNECTIONS)) {
			final org.eclipse.draw2d.Connection connFig = (org.eclipse.draw2d.Connection) conn.getFigure();

			START_POINT.setLocation(connFig.getSourceAnchor().getLocation(connFig.getSourceAnchor().getReferencePoint()));
			END_POINT.setLocation(connFig.getTargetAnchor().getLocation(connFig.getTargetAnchor().getReferencePoint()));

			connFig.translateToRelative(START_POINT);
			connFig.translateToRelative(END_POINT);

			final ElkPort sourcePort = getPort(START_POINT, (InterfaceEditPart) conn.getSource(), mapping);
			final ElkPort destinationPort = getPort(END_POINT, (InterfaceEditPart) conn.getTarget(), mapping);

			final ElkEdge edge = FordiacLayoutFactory.createFordiacLayoutEdge(conn, mapping.getLayoutGraph(), sourcePort, destinationPort);
			mapping.getGraphMap().put(edge, conn);
			mapping.getProperty(REVERSE_MAPPING).put(conn, edge);
		}
	}

	private static ElkPort getPort(Point point, InterfaceEditPart interfaceEditPart, LayoutMapping mapping) {
		return (ElkPort) mapping.getProperty(REVERSE_MAPPING).computeIfAbsent(interfaceEditPart, ie -> createPort(point, interfaceEditPart, mapping));
	}

	private static ElkPort createPort(Point point, InterfaceEditPart interfaceEditPart, LayoutMapping mapping) {
		final EditPart parent = interfaceEditPart.getParent();
		final ElkNode parentNode = (ElkNode) mapping.getProperty(REVERSE_MAPPING).get(parent);
		return FordiacLayoutFactory.createFordiacLayoutPort(interfaceEditPart, parentNode, point);
	}

	private static void createValueLabels(LayoutMapping mapping, ValueEditPart valueEditPart) {
		final Object iePart = valueEditPart.getViewer().getEditPartRegistry()
				.get(valueEditPart.getModel().getVarDeclaration());
		final Point point = ((InterfaceEditPart) iePart).getFigure().getBounds().getTopLeft();
		final ElkPort port = getPort(point, (InterfaceEditPart) iePart, mapping);
		final ElkLabel label = ElkGraphUtil.createLabel(valueEditPart.getModel().getValue(), port);
		final Rectangle bounds = valueEditPart.getFigure().getBounds();
		label.setLocation(bounds.preciseX() - port.getX() - port.getParent().getX(),
				bounds.preciseY() - port.getY() - port.getParent().getY());
		label.setDimensions(bounds.preciseWidth(), bounds.preciseHeight());
	}

	@Override
	public void applyLayout(LayoutMapping mapping, IPropertyHolder settings) {
		final Command layoutCommand = new LayoutCommand(mapping);
		mapping.getProperty(COMMAND_STACK).execute(layoutCommand);
	}

}
