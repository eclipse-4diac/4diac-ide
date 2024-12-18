/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug.preferences;

import org.eclipse.core.runtime.preferences.InstanceScope;

public final class FordiacDebugPreferences {

	public static final String DEBUG_PREFERENCES_ID = "org.eclipse.fordiac.ide.debug"; //$NON-NLS-1$
	public static final String VALUE_MAX_DISPLAY_LENGTH = "valueMaxDisplayLength"; //$NON-NLS-1$

	private FordiacDebugPreferences() {
		throw new UnsupportedOperationException();
	}

	public static int getValueMaxDisplayLength() {
		return InstanceScope.INSTANCE.getNode(DEBUG_PREFERENCES_ID).getInt(VALUE_MAX_DISPLAY_LENGTH, 20);
	}
}
