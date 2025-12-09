/*******************************************************************************
 * Copyright (c) 2015, 2024 fortiss GmbH, Martin Erich Jobst
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Alois Zoitl - initial contribution and API
 * Martin Erich Jobst - rewrite based on MonitoringAdapterInterfaceEditPart
 *                      for new deployment monitoring framework
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.debug.ui.editparts;

import java.util.List;

import org.eclipse.fordiac.ide.application.editparts.InterfaceEditPartForFBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.Request;
import org.eclipse.gef.tools.DragEditPartsTracker;

public class AdapterWatchValueInterfaceEditPart extends InterfaceEditPartForFBNetwork {

	public AdapterWatchValueInterfaceEditPart(final IInterfaceElement model) {
		setModel(model);
	}

	@Override
	public DragTracker getDragTracker(final Request request) {
		return new DragEditPartsTracker(this);
	}

	@Override
	protected List<?> getModelSourceConnections() {
		return List.of();
	}

	@Override
	protected List<?> getModelTargetConnections() {
		return List.of();
	}
}
