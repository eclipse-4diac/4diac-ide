/*******************************************************************************
 * Copyright (c) 2026
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dimitrios Kalligaridis - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.tests;

/**
 * Directory names of the standard libraries under data/typelibrary that the
 * tests link through {@link RefactoringTestSupport#linkStandardLibraries}.
 */
public final class StandardLibrary {

	public static final String CORE = "core-3.0.0"; //$NON-NLS-1$
	public static final String CONVERT = "convert-3.0.0"; //$NON-NLS-1$
	public static final String IEC_61131_3 = "iec61131-3-3.0.0"; //$NON-NLS-1$

	private StandardLibrary() {
		throw new UnsupportedOperationException();
	}
}
