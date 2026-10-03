/*******************************************************************************
 * Copyright (c) 2021, 2026 Johannes Kepler University Linz
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *               - moved to the platform get log to have a logger also when there
 *                 is no workbench
 *   Martin Jobst - change logError argument to Throwable
 *   Alexander Fedorov - simplify implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.util;

import org.eclipse.core.runtime.ILog;

public final class FordiacLogHelper {

	public static void logError(final String msg, final Throwable t) {
		ILog.get().error(msg, t);
	}

	public static void logError(final String msg) {
		ILog.get().error(msg);
	}

	public static void logWarning(final String msg, final Exception e) {
		ILog.get().warn(msg, e);
	}

	public static void logWarning(final String msg) {
		ILog.get().warn(msg);
	}

	public static void logInfo(final String msg) {
		ILog.get().info(msg);
	}

	private FordiacLogHelper() {
		throw new UnsupportedOperationException("Helper class should not be instatiated"); //$NON-NLS-1$
	}

}
