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

import org.eclipse.core.runtime.preferences.InstanceScope;

public final class DeploymentDebugPreferences {

	public static final String QUALIFIER = "org.eclipse.fordiac.ide.deployment.debug"; //$NON-NLS-1$
	public static final String MONITORING_VALUE_TRANSPARENCY = "monitoringValueTransparency"; //$NON-NLS-1$
	public static final String MONITORING_VALUE_WRITE_THROUGH = "monitoringValueWriteThrough"; //$NON-NLS-1$

	public static int getMonitoringValueTransparency() {
		return InstanceScope.INSTANCE.getNode(QUALIFIER).getInt(MONITORING_VALUE_TRANSPARENCY, 190);
	}

	public static boolean isMonitoringValueWriteThrough() {
		return InstanceScope.INSTANCE.getNode(QUALIFIER).getBoolean(MONITORING_VALUE_WRITE_THROUGH, false);
	}

	private DeploymentDebugPreferences() {
		throw new UnsupportedOperationException();
	}
}
