/*******************************************************************************
 * Copyright (c) 2008, 2012 - 2017 Profactor GbmH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger 
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.editparts;

import java.util.Collections;
import java.util.List;

import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.Request;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.tools.SelectEditPartTracker;

/**
 * The Class DeviceInterfaceEditPart.
 * 
 * @author Gerhard Ebenhofer, gerhard.ebenhofer@profactor.at
 */
public class DeviceInterfaceEditPart extends InterfaceEditPart {

	@Override
	protected GraphicalNodeEditPolicy getNodeEditPolicy() {
		return null;
	}
	
	@Override
	public DragTracker getDragTracker(Request request) {
		return new SelectEditPartTracker(this);
	}
	
	@Override
	protected List<?> getModelSourceConnections() {
		return Collections.emptyList();
	}

	@Override
	protected List<?> getModelTargetConnections() {
		return Collections.emptyList();
	}

}
