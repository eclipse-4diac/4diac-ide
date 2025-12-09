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
package org.eclipse.fordiac.ide.test.model.search.st;

import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.search.NameMatcher;
import org.junit.jupiter.api.Test;

@SuppressWarnings({ "nls", "static-method" })
class STFunctionSearchSupportTest extends StructuredTextSearchSupportTest {

	@Test
	void testNameSearch() {
		final FunctionFBType type = createFunctionFBType("TEST_FUNC", """
				FUNCTION TEST_FUNC
				END_FUNCTION
				""");
		assertNoMatch(type.getBody(), new NameMatcher("ABC"));
		assertMatch(type.getBody(), new NameMatcher("TEST_FUNC"), 0, 9, 9);
	}
}
