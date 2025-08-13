/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gitlab.preferences;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;

public final class PreferenceConstants {

	private PreferenceConstants() {
		// Shall not be instantiated
	}

	public static final String P_GITLAB_TOKEN = "gitLabToken"; //$NON-NLS-1$

	public static final String P_GITLAB_URL = "gitLabURL"; //$NON-NLS-1$

	public static final String P_GITLAB_PREFERENCE_ID = "org.eclipse.fordiac.ide.gitlab"; //$NON-NLS-1$

	public static String getURL() {
		return Platform.getPreferencesService().getString(P_GITLAB_PREFERENCE_ID, P_GITLAB_URL, "", //$NON-NLS-1$
				new IScopeContext[] { InstanceScope.INSTANCE, DefaultScope.INSTANCE });
	}

	public static String getToken() {
		return Platform.getPreferencesService().getString(P_GITLAB_PREFERENCE_ID, P_GITLAB_TOKEN, "", //$NON-NLS-1$
				new IScopeContext[] { InstanceScope.INSTANCE, DefaultScope.INSTANCE });
	}
}
