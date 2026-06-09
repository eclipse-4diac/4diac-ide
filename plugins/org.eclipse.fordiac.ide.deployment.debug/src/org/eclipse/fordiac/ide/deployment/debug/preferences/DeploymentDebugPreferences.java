/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.deployment.debug.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IScopeContext;

public final class DeploymentDebugPreferences {

	public static final String QUALIFIER = "org.eclipse.fordiac.ide.deployment.debug"; //$NON-NLS-1$
	public static final String MONITORING_VALUE_WRITE_THROUGH = "monitoringValueWriteThrough"; //$NON-NLS-1$
	public static final String WATCH_INTERNAL_VARIABLES_DEFAULT = "watchInternalVariablesDefault"; //$NON-NLS-1$
	public static final String WATCH_INTERNAL_NETWORKS_DEFAULT = "watchInternalNetworksDefault"; //$NON-NLS-1$

	public static boolean isWatchInternalVariablesDefault(final IProject project) {
		return Platform.getPreferencesService().getBoolean(QUALIFIER, WATCH_INTERNAL_VARIABLES_DEFAULT, false,
				new IScopeContext[] { new ProjectScope(project) });
	}

	public static boolean isWatchInternalNetworksDefault(final IProject project) {
		return Platform.getPreferencesService().getBoolean(QUALIFIER, WATCH_INTERNAL_NETWORKS_DEFAULT, false,
				new IScopeContext[] { new ProjectScope(project) });
	}

	private DeploymentDebugPreferences() {
		throw new UnsupportedOperationException();
	}
}
