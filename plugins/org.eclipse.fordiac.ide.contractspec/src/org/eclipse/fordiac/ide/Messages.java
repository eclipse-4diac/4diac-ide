/*******************************************************************************
 * Copyright (c) 2025 Felix Schmid
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Schmid
 *     - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide;

import org.eclipse.osgi.util.NLS;

public final class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.messages"; //$NON-NLS-1$

	public static String EmptyIntervalWarning;

	public static String DegenerateIntervalWarning;

	public static String OutputPortExpectedError;

	public static String InputPortExpectedError;

	public static String EmptyIntervalQuickfixLabel;

	public static String EmptyIntervalQuickfixDescription;

	public static String DegenerateIntervalQuickfixLabel;

	public static String DegenerateIntervalQuickfixDescription;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}
