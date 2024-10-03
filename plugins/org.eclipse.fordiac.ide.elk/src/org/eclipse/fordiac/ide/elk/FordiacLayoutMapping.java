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

import org.eclipse.elk.core.service.LayoutMapping;
import org.eclipse.elk.graph.ElkGraphElement;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.elk.graph.util.ElkGraphUtil;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.application.editparts.EditorWithInterfaceEditPart;
import org.eclipse.fordiac.ide.application.editparts.UnfoldedSubappContentEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.network.viewer.CompositeNetworkViewerEditPart;
import org.eclipse.fordiac.ide.gef.editparts.AbstractFBNetworkEditPart;
import org.eclipse.gef.GraphicalEditPart;

public class FordiacLayoutMapping extends LayoutMapping {

	private static final long serialVersionUID = 363049909751709783L;

	private final List<ConnectionEditPart> connections = new ArrayList<>();
	private final Map<GraphicalEditPart, ElkGraphElement> reverseMapping = new HashMap<>();
	private final FordiacLayoutData layoutData = new FordiacLayoutData();

	private final AbstractFBNetworkEditPart ep;

	public enum LayoutType {
		Application, Unfolded, Typed
	}

	public LayoutType type;

	public FordiacLayoutMapping(final AbstractFBNetworkEditPart ep) {
		super(null); // WorkbenchPart only needed for configuration store, which we don't use
		this.ep = ep;
		type = getLayoutType(ep);

		final ElkNode graph = ElkGraphUtil.createGraph();
		final ElkNode parent = ElkGraphUtil.createGraph();
		graph.setParent(parent);

		final var bounds = ep.getFigure().getBounds();
		graph.setDimensions(bounds.preciseWidth(), bounds.preciseHeight());
		graph.setLocation(bounds.preciseX(), bounds.preciseY());

		setLayoutGraph(graph);
		setParentElement(ep);

		getGraphMap().put(graph, ep);
		reverseMapping.put(ep, graph);
	}

	private static LayoutType getLayoutType(final AbstractFBNetworkEditPart ep) {
		if (ep instanceof UnfoldedSubappContentEditPart) {
			return LayoutType.Unfolded;
		}
		if (ep instanceof EditorWithInterfaceEditPart && !(ep instanceof CompositeNetworkViewerEditPart)) {
			return LayoutType.Typed;
		}
		return LayoutType.Application;
	}

	@Override
	public GraphicalEditPart getParentElement() {
		return (GraphicalEditPart) super.getParentElement();
	}

	public List<ConnectionEditPart> getConnections() {
		return connections;
	}

	public Map<GraphicalEditPart, ElkGraphElement> getReverseMapping() {
		return reverseMapping;
	}

	public FordiacLayoutData getLayoutData() {
		return layoutData;
	}

	public AbstractFBNetworkEditPart getNetworkEditPart() {
		return ep;
	}

}
