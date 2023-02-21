/*******************************************************************************
 * Copyright (c) 2022 Johannes Keppler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.segment;

public final class TsnParameters {

	public static final int TSN_MAX_WINDOWS = 8;
	public static final String TSN_WINDOW_NAME = "Channel"; //$NON-NLS-1$

	private TsnParameters() {
		throw new UnsupportedOperationException("Helper class sould not be instantiated!"); //$NON-NLS-1$
	}
}
