/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.debug.fb;

import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public enum FBDebugClockMode {

	SYSTEM, INCREMENT, MANUAL;

	public static FBDebugClockMode fromString(final String val) {
		try {
			return valueOf(val);
		} catch (final IllegalArgumentException | NullPointerException ex) {
			FordiacLogHelper.logWarning("Could not convert clock mode from string: " + val, ex); //$NON-NLS-1$
		}
		return SYSTEM;
	}

}
