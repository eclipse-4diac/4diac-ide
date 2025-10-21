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

/**
 * @brief Representation of a change of a data point (event/variables,
 *        inputs/outputs)
 *
 *        Stores the data point name (qualified name), its old and new values.
 */
public final record DataPointChange(String datapoint, String oldValue, String newValue) {
}
