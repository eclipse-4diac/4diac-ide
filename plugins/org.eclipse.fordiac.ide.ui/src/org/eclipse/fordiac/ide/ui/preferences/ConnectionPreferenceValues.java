/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.preferences;

public final class ConnectionPreferenceValues {

	public static final int NORMAL_LINE_WIDTH = 1;

	public static final int HIGHLIGTHED_LINE_WIDTH = 3;

	public static final int SELECTED_LINE_WIDTH = 5;

	public static final int HANDLE_SIZE = 13;

	public static final int MIN_HANDLE_SIZE = 5;

	private ConnectionPreferenceValues() {
		throw new UnsupportedOperationException("ConnectionPreferenceValues utility class should not be instantiated!"); //$NON-NLS-1$
	}

}
