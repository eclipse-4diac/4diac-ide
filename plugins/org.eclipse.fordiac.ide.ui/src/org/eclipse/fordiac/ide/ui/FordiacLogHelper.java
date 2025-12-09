/*******************************************************************************
 * Copyright (c) 2021, 2024 Johannes Kepler University Linz
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui;

import java.lang.StackWalker.Option;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

public final class FordiacLogHelper {

	private static final StackWalker STACK_WALKER = StackWalker.getInstance(Option.RETAIN_CLASS_REFERENCE);

	public static void logError(final String msg, final Throwable t) {
		getLogger(STACK_WALKER.getCallerClass()).error(msg, t);
	}

	public static void logError(final String msg) {
		getLogger(STACK_WALKER.getCallerClass()).error(msg);
	}

	public static void logWarning(final String msg, final Exception e) {
		getLogger(STACK_WALKER.getCallerClass()).warn(msg, e);
	}

	public static void logWarning(final String msg) {
		getLogger(STACK_WALKER.getCallerClass()).warn(msg);
	}

	public static void logInfo(final String msg) {
		getLogger(STACK_WALKER.getCallerClass()).info(msg);
	}

	private static ILog getLogger(final Class<?> classFromBundle) {
		return Platform.getLog(getBundle(classFromBundle));
	}

	private static Bundle getBundle(final Class<?> classFromBundle) {
		return FrameworkUtil.getBundle(classFromBundle);
	}

	private FordiacLogHelper() {
		throw new UnsupportedOperationException("Helper class should not be instatiated"); //$NON-NLS-1$
	}

}
