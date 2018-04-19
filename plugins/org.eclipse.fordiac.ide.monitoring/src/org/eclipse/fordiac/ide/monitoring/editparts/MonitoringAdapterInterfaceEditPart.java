/*******************************************************************************
 * Copyright (c) 2015 fortiss GmbH
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
