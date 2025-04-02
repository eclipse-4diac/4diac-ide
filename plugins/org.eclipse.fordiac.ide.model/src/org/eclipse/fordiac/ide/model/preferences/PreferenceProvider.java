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
 *   Patrick Aigner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;

/**
 * Wrapper for {@code Platform.getPreferencesService()} functions to enable
 * workspace/project scoped preference access
 */
public class PreferenceProvider {
	/**
	 * Flag signifying if project specific settings are active
	 */
	public static final String PROJECT_STORE_ACTIVE = "ProjectStoreActive"; //$NON-NLS-1$

	private static IScopeContext[] getContexts(final String qualifier, final IProject project) {
		if (project != null) {
			final ProjectScope projectScope = new ProjectScope(project);

			if (Platform.getPreferencesService().getBoolean(qualifier, PROJECT_STORE_ACTIVE, false,
					new IScopeContext[] { projectScope })) {
				return new IScopeContext[] { projectScope, InstanceScope.INSTANCE };
			}
		}

		return new IScopeContext[] { InstanceScope.INSTANCE };
	}

	public static boolean getBoolean(final String qualifier, final String key, final boolean defaultValue,
			final IProject project) {
		return Platform.getPreferencesService().getBoolean(qualifier, key, defaultValue,
				getContexts(qualifier, project));
	}

	public static byte[] getByteArray(final String qualifier, final String key, final byte[] defaultValue,
			final IProject project) {
		return Platform.getPreferencesService().getByteArray(qualifier, key, defaultValue,
				getContexts(qualifier, project));
	}

	public static double getDouble(final String qualifier, final String key, final double defaultValue,
			final IProject project) {
		return Platform.getPreferencesService().getDouble(qualifier, key, defaultValue,
				getContexts(qualifier, project));
	}

	public static float getFloat(final String qualifier, final String key, final float defaultValue,
			final IProject project) {
		return Platform.getPreferencesService().getFloat(qualifier, key, defaultValue, getContexts(qualifier, project));
	}

	public static int getInt(final String qualifier, final String key, final int defaultValue, final IProject project) {
		return Platform.getPreferencesService().getInt(qualifier, key, defaultValue, getContexts(qualifier, project));
	}

	public static long getLong(final String qualifier, final String key, final long defaultValue,
			final IProject project) {
		return Platform.getPreferencesService().getLong(qualifier, key, defaultValue, getContexts(qualifier, project));
	}

	public static String getString(final String qualifier, final String key, final String defaultValue,
			final IProject project) {
		return Platform.getPreferencesService().getString(qualifier, key, defaultValue,
				getContexts(qualifier, project));
	}

	private PreferenceProvider() {
		throw new UnsupportedOperationException();
	}
}
