/*******************************************************************************
 * Copyright (c) 2015 fortiss GmbH
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Alois Zoitl - initial contribution and API
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.editparts;

import org.eclipse.fordiac.ide.application.editparts.InterfaceEditPartForFBNetwork;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.Request;

public class MonitoringAdapterInterfaceEditPart extends InterfaceEditPartForFBNetwork {

	@Override
	public DragTracker getDragTracker(Request request) {
		return new org.eclipse.gef.tools.DragEditPartsTracker(this);
	}

}
