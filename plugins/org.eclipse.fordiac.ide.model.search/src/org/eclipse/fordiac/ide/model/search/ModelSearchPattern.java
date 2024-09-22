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
 *   Dunja Životin
 *    - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.search;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModelSearchPattern { // extends SearchPattern

	private final String toTest;
	private Pattern searchPattern;
	private final String searchString;

	public ModelSearchPattern(final String toTest, final ModelQuerySpec modelQuerySpec) {
		this.toTest = toTest;
		this.searchString = modelQuerySpec.getSearchString();
	}

	private String convertSearchStringToPattern() {
		String temp = searchString;
			if (searchString.contains("?")) { //$NON-NLS-1$
				temp = searchString.replace("?", "[a-zA-Z0-9_]"); //$NON-NLS-1$ //$NON-NLS-2$
		}
			if (searchString.contains("*")) { //$NON-NLS-1$
				temp = searchString.replace("*", ".*");  //$NON-NLS-1$//$NON-NLS-2$
		}
		return temp;
	}

	public boolean matchSearchString() {
		final String bah = convertSearchStringToPattern();
		searchPattern = Pattern.compile(bah);
		final Matcher matcher = searchPattern.matcher(toTest);
		return matcher.matches();
	}

}
