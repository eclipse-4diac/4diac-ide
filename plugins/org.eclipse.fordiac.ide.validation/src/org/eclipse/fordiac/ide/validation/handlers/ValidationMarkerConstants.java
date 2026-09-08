/*******************************************************************************
 * Copyright (c) 2020 Sandor Bacsi, Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sandor Bacsi - initial API and implementation and/or initial documentation
 *   Michael Oberlehner - add OCL specific marker
 *******************************************************************************/
package org.eclipse.fordiac.ide.validation.handlers;

public final class ValidationMarkerConstants {
	public static final String VALIDATION_PLUGIN_ID = "org.eclipse.fordiac.ide.validation"; //$NON-NLS-1$

	public static final String LEGACY_TYPE = VALIDATION_PLUGIN_ID + ".ValidationMarker"; //$NON-NLS-1$

	public static final String TYPE = VALIDATION_PLUGIN_ID + ".OCLMarker"; //$NON-NLS-1$

	private ValidationMarkerConstants() {
		throw new UnsupportedOperationException("Helper class should not be instantiated"); //$NON-NLS-1$
	}

}
