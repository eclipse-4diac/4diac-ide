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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.elk.alg.libavoid.options.LibavoidMetaDataProvider;
import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.elk.core.service.LayoutMapping;
import org.eclipse.elk.graph.ElkGraphElement;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.application.editparts.EditorWithInterfaceEditPart;
import org.eclipse.fordiac.ide.application.editparts.GroupEditPart;
import org.eclipse.fordiac.ide.application.editparts.UnfoldedSubappContentEditPart;
import org.eclipse.fordiac.ide.elk.FordiacLayoutData;
import org.eclipse.fordiac.ide.elk.helpers.FordiacLayoutFactory;
import org.eclipse.fordiac.ide.gef.editparts.AbstractFBNetworkEditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.ui.IWorkbenchPart;

public class ConnectionLayoutMapping extends LayoutMapping {

	private static final long serialVersionUID = -1861451931133554207L;

	private final List<ConnectionEditPart> connections = new ArrayList<>();
	private final FordiacLayoutData layoutData = new FordiacLayoutData();
	private final List<GroupEditPart> groups = new ArrayList<>();
	private final Map<Integer, ConnectionEditPart> groupTuples = new HashMap<>();
	private final List<UnfoldedSubappContentEditPart> expandedSubapps = new ArrayList<>();
	private final Map<GraphicalEditPart, ElkGraphElement> reverseMapping = new HashMap<>();
	private final Map<ElkGraphElement, ElkGraphElement> portParentMapping = new HashMap<>();

	private boolean hasNetwork = true;
	private boolean isExpandedSubapp = false;

	public boolean hasNetwork() {
		return hasNetwork;
	}

	public boolean isExpandedSubapp() {
		return isExpandedSubapp;
	}

	public List<ConnectionEditPart> getConnections() {
		return connections;
	}

	public FordiacLayoutData getLayoutData() {
		return layoutData;
	}

	public List<GroupEditPart> getGroups() {
		return groups;
	}

	public Map<Integer, ConnectionEditPart> getGroupTuples() {
		return groupTuples;
	}

	public List<UnfoldedSubappContentEditPart> getExpandedSubapps() {
		return expandedSubapps;
	}

	public Map<GraphicalEditPart, ElkGraphElement> getReverseMapping() {
		return reverseMapping;
	}

	public Map<ElkGraphElement, ElkGraphElement> getPortParentMapping() {
		return portParentMapping;
	}

	public ConnectionLayoutMapping(final IWorkbenchPart workbenchPart) {
		super(workbenchPart);
		final AbstractFBNetworkEditPart root = findRootEditPart(workbenchPart);
		init(root);
	}

	public ConnectionLayoutMapping(final AbstractFBNetworkEditPart network) {
		super(null);
		isExpandedSubapp = true;
		init(network);
	}

	private void init(final AbstractFBNetworkEditPart network) {
		if (network != null) {
			createGraphRoot(network);
		} else {
			hasNetwork = false;
		}
		getLayoutGraph().setProperty(CoreOptions.ALGORITHM, "org.eclipse.elk.alg.libavoid"); //$NON-NLS-1$
		getLayoutGraph().setProperty(LibavoidMetaDataProvider.SHAPE_BUFFER_DISTANCE, Double.valueOf(10));
		getLayoutGraph().setProperty(LibavoidMetaDataProvider.IDEAL_NUDGING_DISTANCE, Double.valueOf(5));
		getLayoutGraph().setProperty(LibavoidMetaDataProvider.CROSSING_PENALTY, Double.valueOf(10));
		getLayoutGraph().setProperty(LibavoidMetaDataProvider.CLUSTER_CROSSING_PENALTY, Double.valueOf(10));
		getLayoutGraph().setProperty(LibavoidMetaDataProvider.NUDGE_SHARED_PATHS_WITH_COMMON_END_POINT,
				Boolean.valueOf(false));
		getLayoutGraph().setProperty(LibavoidMetaDataProvider.ENABLE_HYPEREDGES_FROM_COMMON_SOURCE,
				Boolean.valueOf(true));
		getLayoutGraph().setProperty(
				LibavoidMetaDataProvider.IMPROVE_HYPEREDGE_ROUTES_MOVING_ADDING_AND_DELETING_JUNCTIONS,
				Boolean.valueOf(true));
	}

	private void createGraphRoot(final AbstractFBNetworkEditPart networkEditPart) {
		final ElkNode graph = FordiacLayoutFactory.createFordiacLayoutGraph(false);
		setGraphBounds(graph, networkEditPart);
		setLayoutGraph(graph);
		setParentElement(networkEditPart);
		getGraphMap().put(graph, networkEditPart);
	}

	private static void setGraphBounds(final ElkNode graph, final AbstractFBNetworkEditPart networkEditPart) {
		Rectangle bounds = null;
		if (networkEditPart instanceof EditorWithInterfaceEditPart) {
			final IFigure figure = networkEditPart.getFigure().getChildren().get(0).getChildren().stream()
					.filter(FreeformLayer.class::isInstance).findFirst().orElse(null);
			if (figure != null) {
				bounds = figure.getBounds();
			}
		} else {
			bounds = networkEditPart.getFigure().getBounds();
		}

		if (bounds != null) {
			graph.setLocation(bounds.preciseX(), bounds.preciseY());
			graph.setDimensions(bounds.preciseWidth(), bounds.preciseHeight());
		}
	}

	private static AbstractFBNetworkEditPart findRootEditPart(final IWorkbenchPart workbenchPart) {
		return (AbstractFBNetworkEditPart) workbenchPart.getAdapter(GraphicalViewer.class).getRootEditPart()
				.getChildren().get(0);
	}

}
