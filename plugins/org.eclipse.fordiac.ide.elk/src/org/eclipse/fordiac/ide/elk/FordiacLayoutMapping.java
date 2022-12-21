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
package org.eclipse.fordiac.ide.elk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.elk.core.service.LayoutMapping;
import org.eclipse.elk.graph.ElkEdge;
import org.eclipse.elk.graph.ElkGraphElement;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.elk.graph.ElkPort;
import org.eclipse.elk.graph.properties.IProperty;
import org.eclipse.elk.graph.properties.Property;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.application.editparts.EditorWithInterfaceEditPart;
import org.eclipse.fordiac.ide.elk.helpers.FordiacLayoutFactory;
import org.eclipse.fordiac.ide.gef.editparts.AbstractFBNetworkEditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.IWorkbenchPart;

public class FordiacLayoutMapping extends LayoutMapping {

	private static final long serialVersionUID = 363049909751709783L;

	public static final IProperty<AbstractFBNetworkEditPart> NETWORK_EDIT_PART = new Property<>("gef.networkEditPart"); //$NON-NLS-1$
	public static final IProperty<CommandStack> COMMAND_STACK = new Property<>("gef.commandStack"); //$NON-NLS-1$
	public static final IProperty<List<ConnectionEditPart>> CONNECTIONS = new Property<>("gef.connections"); //$NON-NLS-1$
	public static final IProperty<List<ConnectionEditPart>> HIERARCHY_CROSSING_CONNECTIONS = new Property<>("gef.hierarchyCrossingConnections"); //$NON-NLS-1$
	public static final IProperty<Map<ConnectionEditPart, List<ElkEdge>>> HIERARCHY_CROSSING_CONNECTIONS_MAPPING = new Property<>("gef.hierarchyCrossingConnectionsMapping"); //$NON-NLS-1$
	public static final IProperty<Map<ElkEdge, ConnectionEditPart>> HIERARCHY_CROSSING_CONNECTIONS_REVERSE_MAPPING = new Property<>("gef.hierarchyCrossingConnectionsReverseMapping"); //$NON-NLS-1$
	public static final IProperty<Map<GraphicalEditPart, ElkGraphElement>> REVERSE_MAPPING = new Property<>("gef.reverseMapping"); //$NON-NLS-1$
	public static final IProperty<Map<ElkPort, ElkPort>> DUMMY_PORTS = new Property<>("gef.dummyPorts"); //$NON-NLS-1$
	public static final IProperty<FordiacLayoutData> LAYOUT_DATA = new Property<>("gef.layoutData"); //$NON-NLS-1$

	private boolean hasNetwork = true;

	public boolean hasNetwork() {
		return hasNetwork;
	}

	public static FordiacLayoutMapping create(final IWorkbenchPart workbenchPart, final boolean hasProperties) {
		final FordiacLayoutMapping mapping = new FordiacLayoutMapping(workbenchPart);
		mapping.setProperty(FordiacLayoutMapping.COMMAND_STACK, workbenchPart.getAdapter(CommandStack.class));
		mapping.setProperty(FordiacLayoutMapping.CONNECTIONS, new ArrayList<>());
		mapping.setProperty(FordiacLayoutMapping.HIERARCHY_CROSSING_CONNECTIONS, new ArrayList<>());
		mapping.setProperty(FordiacLayoutMapping.HIERARCHY_CROSSING_CONNECTIONS_MAPPING, new HashMap<>());
		mapping.setProperty(FordiacLayoutMapping.HIERARCHY_CROSSING_CONNECTIONS_REVERSE_MAPPING, new HashMap<>());
		mapping.setProperty(FordiacLayoutMapping.REVERSE_MAPPING, new HashMap<>());
		mapping.setProperty(FordiacLayoutMapping.DUMMY_PORTS, new HashMap<>());
		mapping.setProperty(FordiacLayoutMapping.LAYOUT_DATA, new FordiacLayoutData());

		findRootEditPart(mapping, workbenchPart);

		if (mapping.getProperty(FordiacLayoutMapping.NETWORK_EDIT_PART) != null) {
			createGraphRoot(mapping, hasProperties);
		} else {
			mapping.hasNetwork = false;
		}

		return mapping;
	}

	private static void createGraphRoot(final LayoutMapping mapping, final boolean hasProperties) {
		final AbstractFBNetworkEditPart networkEditPart = mapping.getProperty(FordiacLayoutMapping.NETWORK_EDIT_PART);
		final ElkNode graph = FordiacLayoutFactory.createFordiacLayoutGraph(hasProperties);
		setGraphBounds(graph, networkEditPart);
		mapping.setLayoutGraph(graph);
		mapping.setParentElement(networkEditPart);
		mapping.getGraphMap().put(graph, networkEditPart);
		mapping.getProperty(FordiacLayoutMapping.REVERSE_MAPPING).put(networkEditPart, graph);
	}

	private static void setGraphBounds(final ElkNode graph, final AbstractFBNetworkEditPart networkEditPart) {
		Rectangle bounds = null;
		if (networkEditPart instanceof EditorWithInterfaceEditPart) {
			@SuppressWarnings("unchecked")
			final Object figure = ((IFigure) networkEditPart.getFigure().getChildren().get(0)).getChildren()
					.stream()
					.filter(FreeformLayer.class::isInstance)
					.findFirst()
					.orElse(null);
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

	private static void findRootEditPart(final LayoutMapping mapping, final IWorkbenchPart workbenchPart) {
		final Object ep = workbenchPart.getAdapter(GraphicalViewer.class)
							.getRootEditPart()
							.getChildren()
							.get(0);
		mapping.setProperty(FordiacLayoutMapping.NETWORK_EDIT_PART, (AbstractFBNetworkEditPart) ep);
	}

	private FordiacLayoutMapping(final IWorkbenchPart workbenchPart) {
		super(workbenchPart);
	}

}
