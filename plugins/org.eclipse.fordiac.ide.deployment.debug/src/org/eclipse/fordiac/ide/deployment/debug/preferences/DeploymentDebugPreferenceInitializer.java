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

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;

public class DeploymentDebugPreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		DefaultScope.INSTANCE.getNode(DeploymentDebugPreferences.QUALIFIER)
				.putInt(DeploymentDebugPreferences.MONITORING_VALUE_TRANSPARENCY, 190);
		DefaultScope.INSTANCE.getNode(DeploymentDebugPreferences.QUALIFIER)
				.putBoolean(DeploymentDebugPreferences.MONITORING_VALUE_WRITE_THROUGH, false);
	}
}
