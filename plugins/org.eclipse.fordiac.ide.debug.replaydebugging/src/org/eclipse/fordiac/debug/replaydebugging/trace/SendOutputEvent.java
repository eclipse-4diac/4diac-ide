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

package org.eclipse.fordiac.debug.replaydebugging.trace;

import java.util.List;

/**
 * @brief Representation of a sendOutputEvent in the trace.
 *
 *        This record stores the type name, instance name, event ID, event
 *        counter, and a list of outputs associated with the event.
 */
public final record SendOutputEvent(String typeName, String instanceName, int eventId, int eventCounter,
		List<String> outputs) {

	// for debugging purposes
	@Override
	public String toString() {
		return "SendOutputEvent{" + "typeName='" + typeName + '\'' + ", instanceName='" + instanceName + '\''
				+ ", eventId=" + eventId + ", eventCounter=" + eventCounter + ", outputs=" + outputs + '}';
	}
}
