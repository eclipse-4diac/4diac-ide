/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.elk.preferences;

import org.eclipse.core.runtime.Platform;

public class ElkPreferences {
	public static final String ELK_PREFERENCES_ID = "org.eclipse.fordiac.ide.elk"; //$NON-NLS-1$

	public static final String CONNECTION_LAYOUT_TIMEOUT = "ConnectionLayoutTimeOut"; //$NON-NLS-1$

	public static int getConnectionLaoyutTimeout() {
		return Platform.getPreferencesService().getInt(ELK_PREFERENCES_ID, CONNECTION_LAYOUT_TIMEOUT,
				ElkPreferenceInitializer.CONNECTION_LAYOUT_TIMEOUT_DEFAULT_VALUE, null);
	}

	private ElkPreferences() {
		throw new UnsupportedOperationException("Helper class must not be instantiated!"); //$NON-NLS-1$
	}
}
