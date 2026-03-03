/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui;

public final class FordiacStringUtils {
	private FordiacStringUtils() {
		// Utility class
	}

	/**
	 * Extracts the first line of a multi-line comment and appends "..."
	 */
	public static String getShortComment(final String comment) {
		if (comment == null) {
			return ""; //$NON-NLS-1$
		}

		final String[] lines = comment.split("\\R", 2); //$NON-NLS-1$

		if (lines.length > 1) {
			return lines[0] + "..."; //$NON-NLS-1$
		}
		return comment;
	}
}