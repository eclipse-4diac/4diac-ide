/*******************************************************************************
 * Copyright (c) 2026 Jose Cabral
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.debug.replaydebugging.ui.editpart;

import org.eclipse.fordiac.ide.debug.replaydebugging.core.ReplayNavigator.EventPosition;
import org.eclipse.gef.Request;

/**
 * @brief Request to select an event marker
 *
 *        This request is created when the user clicks on an EventMarkerFigure
 *        and is processed by the edit part to create a SelectEventCommand.
 */
public class SelectEventRequest extends Request {

	public static final String REQUEST_TYPE = "SelectEvent"; //$NON-NLS-1$

	private final EventPosition eventPosition;

	public SelectEventRequest(final EventPosition eventPosition) {
		super(REQUEST_TYPE);
		this.eventPosition = eventPosition;
	}

	public EventPosition getEventPosition() {
		return eventPosition;
	}

}