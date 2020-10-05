/*******************************************************************************
 * Copyright (c) 2020 Primetals Technologies Germany GmbH
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Ernst Blecha - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.helpers;

import java.util.Arrays;

public class JUnitDetector {

	private JUnitDetector() {
		throw new UnsupportedOperationException();
	}

	private static final boolean JUNIT_RUNNING = junitRunning();

	private static boolean junitRunning() {
		return Arrays.stream(Thread.currentThread().getStackTrace())
				.anyMatch((StackTraceElement e) -> e.getClassName().startsWith("org.junit."));//$NON-NLS-1$
	}

	public static boolean detect() {
		return JUNIT_RUNNING;
	}
}
