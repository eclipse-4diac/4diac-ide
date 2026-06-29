/*******************************************************************************
 * Copyright (c) 2020 Sandor Bacsi
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
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

import org.eclipse.fordiac.ide.validation.Activator;

public interface IValidationMarker {
	String LEGACY_TYPE = Activator.PLUGIN_ID + ".ValidationMarker"; //$NON-NLS-1$
	String TYPE = Activator.PLUGIN_ID + ".OCLMarker"; //$NON-NLS-1$
}
