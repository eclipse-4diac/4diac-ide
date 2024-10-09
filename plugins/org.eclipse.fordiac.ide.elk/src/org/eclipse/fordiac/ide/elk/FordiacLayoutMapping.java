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

	private final List<ConnectionEditPart> connections = new ArrayList<>();
	private final List<ConnectionEditPart> flatConnections = new ArrayList<>();
	private final List<ConnectionEditPart> hierarchyCrossingConnections = new ArrayList<>();
	private final Map<ConnectionEditPart, List<ElkEdge>> hierarchyCrossingConnectionsMapping = new HashMap<>();
	private final Map<ElkEdge, ConnectionEditPart> hierarchyCrossingConnectionsReverseMapping = new HashMap<>();
	private final Map<GraphicalEditPart, ElkGraphElement> reverseMapping = new HashMap<>();
	private final Map<ElkPort, ElkPort> dummyPorts = new HashMap<>();
	private final FordiacLayoutData layoutData = new FordiacLayoutData();

	private final AbstractFBNetworkEditPart networkEditPart;
	private final CommandStack commandStack;

	private final boolean hasNetwork;

	public FordiacLayoutMapping(final IWorkbenchPart workbenchPart, final boolean hasProperties) {
		super(workbenchPart);
		commandStack = workbenchPart.getAdapter(CommandStack.class);

		networkEditPart = findRootEditPart(workbenchPart);

		hasNetwork = (networkEditPart != null);
		if (hasNetwork) {
			createGraphRoot(hasProperties);
		}
	}

	public FordiacLayoutMapping(final IWorkbenchPart workbenchPart, final boolean hasProperties,
			final AbstractFBNetworkEditPart networkEditPart) {
		super(workbenchPart);
		commandStack = workbenchPart.getAdapter(CommandStack.class);

		this.networkEditPart = networkEditPart;

		hasNetwork = (networkEditPart != null);
		if (hasNetwork) {
			createGraphRoot(hasProperties);
		}
	}

	public boolean hasNetwork() {
		return hasNetwork;
	}

	public List<ConnectionEditPart> getConnections() {
		return connections;
	}

	public List<ConnectionEditPart> getFlatConnections() {
		return flatConnections;
	}

	public List<ConnectionEditPart> getHierarchyCrossingConnections() {
		return hierarchyCrossingConnections;
	}

	public Map<ConnectionEditPart, List<ElkEdge>> getHierarchyCrossingConnectionsMapping() {
		return hierarchyCrossingConnectionsMapping;
	}

	public Map<ElkEdge, ConnectionEditPart> getHierarchyCrossingConnectionsReverseMapping() {
		return hierarchyCrossingConnectionsReverseMapping;
	}

	public Map<GraphicalEditPart, ElkGraphElement> getReverseMapping() {
		return reverseMapping;
	}

	public Map<ElkPort, ElkPort> getDummyPorts() {
		return dummyPorts;
	}

	public FordiacLayoutData getLayoutData() {
		return layoutData;
	}

	public AbstractFBNetworkEditPart getNetworkEditPart() {
		return networkEditPart;
	}

	public CommandStack getCommandStack() {
		return commandStack;
	}

	private void createGraphRoot(final boolean hasProperties) {
		final ElkNode graph = FordiacLayoutFactory.createFordiacLayoutGraph(hasProperties);
		setGraphBounds(graph, networkEditPart);
		setLayoutGraph(graph);
		setParentElement(networkEditPart);
		getGraphMap().put(graph, networkEditPart);
		reverseMapping.put(networkEditPart, graph);
	}

	private static void setGraphBounds(final ElkNode graph, final AbstractFBNetworkEditPart networkEditPart) {
		Rectangle bounds = null;
		if (networkEditPart instanceof EditorWithInterfaceEditPart) {
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

	private static AbstractFBNetworkEditPart findRootEditPart(final IWorkbenchPart workbenchPart) {
		final var networkEP = (AbstractFBNetworkEditPart) workbenchPart.getAdapter(GraphicalViewer.class)
				.getRootEditPart().getChildren().get(0);

		final Point pt = getPositionInViewer((IEditorPart) workbenchPart);
		final AbstractContainerContentEditPart containerEP = GetEditPartFromGraficalViewerHelper
				.findAbstractContainerContentEditPartAtPosition((IEditorPart) workbenchPart, pt, networkEP.getModel());

		return (containerEP != null) ? containerEP : networkEP;
	}

	private static Point getPositionInViewer(final IEditorPart editor) {
		final GraphicalViewer viewer = editor.getAdapter(GraphicalViewer.class);
		return ((FBNetworkContextMenuProvider) viewer.getContextMenu()).getTranslatedAndZoomedPoint();
	}

}
