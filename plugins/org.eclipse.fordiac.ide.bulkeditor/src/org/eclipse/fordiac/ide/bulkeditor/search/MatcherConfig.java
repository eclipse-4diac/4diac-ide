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
package org.eclipse.fordiac.ide.bulkeditor.search;

public record MatcherConfig(boolean active, String value, boolean caseSensitive, boolean wholeWord, boolean exactMatch,
		boolean regex) {

	public static final MatcherConfig INACTIVE = new MatcherConfig(false, "", false, false, false, false); //$NON-NLS-1$
}