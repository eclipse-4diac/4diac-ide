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
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.elk.core.service.LayoutMapping;
import org.eclipse.elk.graph.ElkEdge;
import org.eclipse.elk.graph.ElkGraphElement;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.elk.graph.ElkPort;
import org.eclipse.elk.graph.properties.IProperty;
import org.eclipse.elk.graph.properties.Property;
import org.eclipse.fordiac.ide.application.editors.FBNetworkContextMenuProvider;
import org.eclipse.fordiac.ide.application.editparts.AbstractContainerContentEditPart;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.application.editparts.EditorWithInterfaceEditPart;
import org.eclipse.fordiac.ide.application.utilities.GetEditPartFromGraficalViewerHelper;
import org.eclipse.fordiac.ide.elk.helpers.FordiacLayoutFactory;
import org.eclipse.fordiac.ide.gef.editparts.AbstractFBNetworkEditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;

public class FordiacLayoutMapping extends LayoutMapping {

	private static final long serialVersionUID = 363049909751709783L;

	public static final IProperty<AbstractFBNetworkEditPart> NETWORK_EDIT_PART = new Property<>("gef.networkEditPart"); //$NON-NLS-1$
	public static final IProperty<CommandStack> COMMAND_STACK = new Property<>("gef.commandStack"); //$NON-NLS-1$
	public static final IProperty<List<ConnectionEditPart>> CONNECTIONS = new Property<>("gef.connections"); //$NON-NLS-1$
	public static final IProperty<List<ConnectionEditPart>> FLAT_CONNECTIONS = new Property<>("gef.flatConnections"); //$NON-NLS-1$
	public static final IProperty<List<ConnectionEditPart>> HIERARCHY_CROSSING_CONNECTIONS = new Property<>(
			"gef.hierarchyCrossingConnections"); //$NON-NLS-1$
	public static final IProperty<Map<ConnectionEditPart, List<ElkEdge>>> HIERARCHY_CROSSING_CONNECTIONS_MAPPING = new Property<>(
			"gef.hierarchyCrossingConnectionsMapping"); //$NON-NLS-1$
	public static final IProperty<Map<ElkEdge, ConnectionEditPart>> HIERARCHY_CROSSING_CONNECTIONS_REVERSE_MAPPING = new Property<>(
			"gef.hierarchyCrossingConnectionsReverseMapping"); //$NON-NLS-1$
	public static final IProperty<Map<GraphicalEditPart, ElkGraphElement>> REVERSE_MAPPING = new Property<>(
			"gef.reverseMapping"); //$NON-NLS-1$
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
		mapping.setProperty(FordiacLayoutMapping.FLAT_CONNECTIONS, new ArrayList<>());
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
			final Object figure = ((IFigure) networkEditPart.getFigure().getChildren().get(0)).getChildren().stream()
					.filter(FreeformLayer.class::isInstance).findFirst().orElse(null);
			if (figure instanceof IFigure) {
				bounds = ((IFigure) figure).getBounds();
			}
		} else {
			bounds = networkEditPart.getFigure().getBounds();
		}

		if (bounds != null) {
			if (networkEditPart instanceof AbstractContainerContentEditPart) {
				graph.setLocation(0, 0); // can be ignored as this is handled by the new coordinate utils
			} else {
				graph.setLocation(bounds.preciseX(), bounds.preciseY());
			}
			graph.setDimensions(bounds.preciseWidth(), bounds.preciseHeight());
		}
	}

	private static void findRootEditPart(final LayoutMapping mapping, final IWorkbenchPart workbenchPart) {
		final var networkEP = (AbstractFBNetworkEditPart) workbenchPart.getAdapter(GraphicalViewer.class)
				.getRootEditPart().getChildren().get(0);

		final Point pt = getPositionInViewer((IEditorPart) workbenchPart);
		final AbstractContainerContentEditPart containerEP = GetEditPartFromGraficalViewerHelper
				.findAbstractContainerContentEditPartAtPosition((IEditorPart) workbenchPart, pt, networkEP.getModel());

		mapping.setProperty(FordiacLayoutMapping.NETWORK_EDIT_PART, (containerEP != null) ? containerEP : networkEP);
	}

	private static Point getPositionInViewer(final IEditorPart editor) {
		final GraphicalViewer viewer = editor.getAdapter(GraphicalViewer.class);
		return ((FBNetworkContextMenuProvider) viewer.getContextMenu()).getTranslatedAndZoomedPoint();
	}

	private FordiacLayoutMapping(final IWorkbenchPart workbenchPart) {
		super(workbenchPart);
	}

}
