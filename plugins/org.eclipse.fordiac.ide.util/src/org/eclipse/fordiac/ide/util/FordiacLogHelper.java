/*******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Linz
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

import java.lang.StackWalker.Option;

import org.eclipse.core.runtime.ILog;

/**
 * @deprecated use {@link ILog} directly
 */
@Deprecated(forRemoval = true)
public final class FordiacLogHelper {

	private static final StackWalker STACK_WALKER = StackWalker.getInstance(Option.RETAIN_CLASS_REFERENCE);

	/**
	 * @see ILog
	 * @deprecated replace with `ILog.get().error(...)`
	 */
	@Deprecated(forRemoval = true)
	public static void logError(final String msg, final Throwable t) {
		ILog.of(STACK_WALKER.getCallerClass()).error(msg, t);
	}

	/**
	 * @see ILog
	 * @deprecated replace with `ILog.get().error(...)`
	 */
	@Deprecated
	public static void logError(final String msg) {
		ILog.of(STACK_WALKER.getCallerClass()).error(msg);
	}

	/**
	 * @see ILog
	 * @deprecated replace with `ILog.get().warn(...)`
	 */
	@Deprecated
	public static void logWarning(final String msg, final Exception e) {
		ILog.of(STACK_WALKER.getCallerClass()).warn(msg, e);
	}

	/**
	 * @see ILog
	 * @deprecated replace with `ILog.get().warn(...)`
	 */
	@Deprecated
	public static void logWarning(final String msg) {
		ILog.of(STACK_WALKER.getCallerClass()).warn(msg);
	}

	/**
	 * @see ILog
	 * @deprecated replace with `ILog.get().info(...)`
	 */
	@Deprecated
	public static void logInfo(final String msg) {
		ILog.of(STACK_WALKER.getCallerClass()).info(msg);
	}

	private FordiacLogHelper() {
		throw new UnsupportedOperationException("Helper class should not be instatiated"); //$NON-NLS-1$
	}

}
