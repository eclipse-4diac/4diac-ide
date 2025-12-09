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
import org.junit.jupiter.api.Test;

@SuppressWarnings({ "nls", "static-method" })
class AttributeSearchSupportTest extends StructuredTextSearchSupportTest {

	@Test
	void testReferenceSearch() {
		final SimpleFBType type = createSimpleFBType("Test");
		final VarDeclaration varDeclarationConst = createVarDeclaration("INTERNALCONST1", ElementaryTypes.DINT, false,
				"21");
		final VarDeclaration varDeclarationConst2 = createVarDeclaration("INTERNALCONST2", ElementaryTypes.DINT, false,
				"42");
		type.getInternalVars().add(varDeclarationConst);
		type.getInternalVars().add(varDeclarationConst2);
		type.getAttributes().add(createAttribute("TestAttribute", ElementaryTypes.DINT, "INTERNALCONST2 * 2"));
		assertNoMatch(type.getAttributes().getFirst(), new CrossReferenceMatcher(varDeclarationConst));
		assertMatch(type.getAttributes().getFirst(), new CrossReferenceMatcher(varDeclarationConst2), 0, 0, 14);
	}
}
