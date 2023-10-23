/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.ui.contentassist;

import org.eclipse.xtext.ui.editor.contentassist.PrefixMatcher;

public class STCorePrefixMatcher extends PrefixMatcher.IgnoreCase {

	private static final String DELIMITER = "::"; //$NON-NLS-1$

	@Override
	public boolean isCandidateMatchingPrefix(final String name, final String prefix) {
		final int delimiterIndex = name.lastIndexOf(DELIMITER);
		return super.isCandidateMatchingPrefix(name, prefix) || (delimiterIndex >= 0
				&& super.isCandidateMatchingPrefix(name.substring(delimiterIndex + DELIMITER.length()), prefix));
	}
}
