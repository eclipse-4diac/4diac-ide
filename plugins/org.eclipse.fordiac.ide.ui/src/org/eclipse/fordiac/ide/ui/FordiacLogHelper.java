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
package org.eclipse.fordiac.ide.ui;

import org.eclipse.e4.core.services.log.Logger;
import org.eclipse.ui.PlatformUI;

@SuppressWarnings("restriction")
public final class FordiacLogHelper {

	private static Logger logger = PlatformUI.getWorkbench().getService(org.eclipse.e4.core.services.log.Logger.class);

	public static void logError(final String msg, final Exception e) {
		logger.error(msg, e);
	}

	public static void logError(final String msg) {
		logger.error(msg);
	}

	public static void logWarning(final String msg, final Exception e) {
		logger.warn(msg, e);
	}

	public static void logWarning(final String msg) {
		logger.warn(msg);
	}

	public static void logInfo(final String msg) {
		logger.info(msg);
	}

	private FordiacLogHelper() {
		throw new UnsupportedOperationException("Helper class should not be instatiated"); //$NON-NLS-1$
	}

}
