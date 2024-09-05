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

import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.search.CrossReferenceMatcher;
import org.eclipse.fordiac.ide.model.search.NameMatcher;
import org.junit.jupiter.api.Test;

@SuppressWarnings({ "nls", "static-method" })
class STMethodSearchSupportTest extends StructuredTextSearchSupportTest {

	@Test
	void testNameSearch() {
		final SimpleFBType type = createSimpleFBType("Test");
		type.getCallables().add(createMethod("TEST", """
				METHOD TEST
				END_METHOD
				"""));
		assertNoMatch(type.getCallables().getFirst(), new NameMatcher("ABC"));
		assertMatch(type.getCallables().getFirst(), new NameMatcher("TEST"), 0, 7, 4);
	}

	@Test
	void testReferenceSearch() {
		final SimpleFBType type = createSimpleFBType("Test");
		final VarDeclaration varDeclaration = createVarDeclaration("DO1", ElementaryTypes.DINT);
		final VarDeclaration varDeclaration2 = createVarDeclaration("DO2", ElementaryTypes.DINT);
		type.getInterfaceList().getOutputVars().add(varDeclaration);
		type.getInterfaceList().getOutputVars().add(varDeclaration2);
		type.getCallables().add(createMethod("TEST", """
				METHOD TEST
				DO2 := 17;
				END_METHOD
				"""));
		assertNoMatch(type.getCallables().getFirst(), new CrossReferenceMatcher(varDeclaration));
		assertMatch(type.getCallables().getFirst(), new CrossReferenceMatcher(varDeclaration2), 1, 12, 3);
	}
}
