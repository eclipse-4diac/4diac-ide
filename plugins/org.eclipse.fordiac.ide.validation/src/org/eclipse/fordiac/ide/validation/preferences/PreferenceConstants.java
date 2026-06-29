/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.validation.preferences;

import org.eclipse.fordiac.ide.validation.Activator;

public final class PreferenceConstants {

	public static final String VALIDATION_PREFERENCES_ID = Activator.PLUGIN_ID;

	public static final String ENABLE_OCL_VALIDATION_BUILDER = "ENABLE_OCL_VALIDATION_BUILDER"; //$NON-NLS-1$

	public static final boolean DEFAULT_ENABLE_OCL_VALIDATION_BUILDER = false;

	private PreferenceConstants() {
		throw new UnsupportedOperationException();
	}
}
