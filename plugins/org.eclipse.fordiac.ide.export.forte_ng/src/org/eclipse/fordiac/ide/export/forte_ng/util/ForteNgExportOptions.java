/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.export.forte_ng.util;

import org.eclipse.core.runtime.Platform;

public final class ForteNgExportOptions {

	private static final String USE_SYSTEM_INCLUDES = "-exportSystemIncludes"; //$NON-NLS-1$

	private static boolean systemIncludes;

	static {
		for (final String arg : Platform.getCommandLineArgs()) {
			if (USE_SYSTEM_INCLUDES.equals(arg)) {
				systemIncludes = true;
				break;
			}
		}
	}

	public static boolean useSystemIncludes() {
		return systemIncludes;
	}

	private ForteNgExportOptions() {
		throw new UnsupportedOperationException();
	}
}
