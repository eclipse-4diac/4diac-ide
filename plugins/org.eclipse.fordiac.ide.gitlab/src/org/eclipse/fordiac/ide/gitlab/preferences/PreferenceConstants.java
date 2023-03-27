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

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.fordiac.ide.gitlab.Messages;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

public final class PreferenceConstants {
	
	private PreferenceConstants() {
		// Shall not be instantiated 
	}
	
	public static final String P_GITLAB_TOKEN = Messages.GitLab_Token; 

	public static final String P_GITLAB_URL = Messages.GitLab_Url; 
	
	public static final String P_GITLAB_PREFERENCE_ID = Messages.GitLab_PreferenceId;
	
	public static String getURL() {
		return new ScopedPreferenceStore(InstanceScope.INSTANCE, P_GITLAB_PREFERENCE_ID).getString(P_GITLAB_URL);
	}

	public static String getToken() {
		return new ScopedPreferenceStore(InstanceScope.INSTANCE, P_GITLAB_PREFERENCE_ID).getString(P_GITLAB_TOKEN);
	}
}
