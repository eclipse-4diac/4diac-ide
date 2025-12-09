/*******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.providers;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.FrameworkUtil;

public final class DialogSettingsProvider {

	public static IDialogSettings getDialogSettings(final Class<?> clazz) {
		return PlatformUI.getDialogSettingsProvider(FrameworkUtil.getBundle(clazz)).getDialogSettings();
	}

	private DialogSettingsProvider() {
		throw new UnsupportedOperationException("Helper class should not be instatiated"); //$NON-NLS-1$
	}
}
