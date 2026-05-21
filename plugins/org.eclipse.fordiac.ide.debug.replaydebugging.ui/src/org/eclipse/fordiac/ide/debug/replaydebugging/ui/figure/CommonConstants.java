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
package org.eclipse.fordiac.ide.debug.replaydebugging.ui.figure;

/**
 * @brief Common constants for the replay debugging UI
 */
public class CommonConstants {

	private CommonConstants() {
		// we don't want any instances of this
	}

	public static final int MARKER_SIZE = 20;
	public static final int EVENT_SPACING = 5;
	public static final int TOTAL_MARKER_SPACE = MARKER_SIZE + EVENT_SPACING;
}
