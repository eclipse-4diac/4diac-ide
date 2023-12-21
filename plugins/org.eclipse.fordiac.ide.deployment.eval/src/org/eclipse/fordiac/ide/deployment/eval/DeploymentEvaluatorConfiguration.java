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
package org.eclipse.fordiac.ide.deployment.eval;

import java.util.Map;

import org.eclipse.fordiac.ide.model.typelibrary.DeviceTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.ResourceTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

public final class DeploymentEvaluatorConfiguration {

	private static final String PREFIX = "org.eclipse.fordiac.ide.deployment.eval"; //$NON-NLS-1$

	public static final String DEVICE_TYPE = PREFIX + ".DEVICE_TYPE"; //$NON-NLS-1$
	public static final String DEFAULT_DEVICE_TYPE = "FORTE_PC"; //$NON-NLS-1$

	public static final String RESOURCE_TYPE = PREFIX + "RESOURCE_TYPE"; //$NON-NLS-1$
	public static final String DEFAULT_RESOURCE_TYPE = "EMB_RES"; //$NON-NLS-1$

	public static final String DEVICE_PROFILE = PREFIX + ".DEVICE_PROFILE"; //$NON-NLS-1$
	public static final String DEFAULT_DEVICE_PROFILE = "HOLOBLOC"; //$NON-NLS-1$

	public static final String MGR_ID = PREFIX + ".MGR_ID"; //$NON-NLS-1$
	public static final String DEFAULT_MGR_ID = "\"localhost:61499\""; //$NON-NLS-1$

	public static DeviceTypeEntry getDeviceType(final Map<String, Object> context, final TypeLibrary typeLibrary) {
		return typeLibrary.getDeviceTypeEntry((String) context.getOrDefault(DEVICE_TYPE, DEFAULT_DEVICE_TYPE));
	}

	public static ResourceTypeEntry getResourceType(final Map<String, Object> context, final TypeLibrary typeLibrary) {
		return typeLibrary.getResourceTypeEntry((String) context.getOrDefault(RESOURCE_TYPE, DEFAULT_RESOURCE_TYPE));
	}

	public static String getDeviceProfile(final Map<String, Object> context) {
		return (String) context.getOrDefault(DEVICE_PROFILE, DEFAULT_DEVICE_PROFILE);
	}

	public static String getMgrID(final Map<String, Object> context) {
		return (String) context.getOrDefault(MGR_ID, DEFAULT_MGR_ID);
	}

	private DeploymentEvaluatorConfiguration() {
		throw new UnsupportedOperationException();
	}
}
