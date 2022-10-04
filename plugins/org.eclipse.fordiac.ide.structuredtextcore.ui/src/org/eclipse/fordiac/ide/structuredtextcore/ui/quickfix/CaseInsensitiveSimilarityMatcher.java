/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Melik Merkumians
 *       - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextcore.ui.quickfix;

import org.eclipse.xtext.ui.editor.quickfix.ISimilarityMatcher;
import org.eclipse.xtext.util.Strings;

public class CaseInsensitiveSimilarityMatcher implements ISimilarityMatcher {

	@Override
	public boolean isSimilar(final String s0, final String s1) {
		if (Strings.isEmpty(s0) || Strings.isEmpty(s1)) {
			return false;
		}
		final double levenshteinDistance = Strings.getLevenshteinDistance(s0.toLowerCase(), s1.toLowerCase());
		return levenshteinDistance <= 1;
	}

}
