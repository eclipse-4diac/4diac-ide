/*******************************************************************************
 * Copyright (c) 2025 Jose Cabral
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
package org.eclipse.fordiac.ide.debug.replaydebugging;

import java.util.List;

/**
 * @brief Represents an event that was triggered and the values that have
 *        changed with it.
 *
 *        This class encapsulates information about the triggered event,
 *        including its number in the overall execution, the name of the
 *        triggered event (qualified name), and a collection of values that have
 *        changed as a result of the event.
 */
public final record EventChange(int eventNumber, String triggeredEvent, List<DataPointChange> newValues) {
}
