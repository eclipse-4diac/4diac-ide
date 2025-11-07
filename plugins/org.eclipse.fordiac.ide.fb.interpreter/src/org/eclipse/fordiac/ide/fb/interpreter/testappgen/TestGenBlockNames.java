/*******************************************************************************
 * Copyright (c) 2023, 2025 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Melanie Winter
 *     - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr - updated identifiers
 *******************************************************************************/

package org.eclipse.fordiac.ide.fb.interpreter.testappgen;

public class TestGenBlockNames {

	public static final String TIMEOUT_ADAPTER_NAME = "iec61499::events::atimeout"; //$NON-NLS-1$
	public static final String TIMEOUT_PIN_NAME = "DT1"; //$NON-NLS-1$
	public static final String TIMEOUT_COMPOSITE_NAME = "iec61499::events::E_TimeOut"; //$NON-NLS-1$
	public static final String MATCH_TIMEOUT_PINNAME = "timeout"; //$NON-NLS-1$

	private TestGenBlockNames() {
		// empty private constructor
	}
}
