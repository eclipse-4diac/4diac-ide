/*******************************************************************************
 * Copyright (c) 2021, 2025 Primetals Technologies Austria GmbH
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
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.gef.editparts.AbstractFBNetworkEditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.ui.IEditorPart;

public class FordiacLayoutMapping extends LayoutMapping {

	private static final long serialVersionUID = 363049909751709783L;

	private final List<ConnectionEditPart> connections = new ArrayList<>();
	private final Map<GraphicalEditPart, ElkGraphElement> reverseMapping = new HashMap<>();
	private final FordiacLayoutData layoutData = new FordiacLayoutData();

	private final AbstractFBNetworkEditPart ep;

	public FordiacLayoutMapping(final IEditorPart part, final AbstractFBNetworkEditPart ep) {
		super(part);
		this.ep = ep;
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
