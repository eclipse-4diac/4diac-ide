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
class VarDeclarationSearchSupportTest extends StructuredTextSearchSupportTest {

	@Test
	void testReferenceSearch() {
		final SimpleFBType type = createSimpleFBType("Test");
		final VarDeclaration varDeclarationConst = createVarDeclaration("INTERNALCONST1", ElementaryTypes.DINT, false,
				"21");
		type.getInternalVars().add(varDeclarationConst);
		final VarDeclaration varDeclaration = createVarDeclaration("DI1", ElementaryTypes.DINT, true,
				"INTERNALCONST1 * 2");
		type.getInterfaceList().getInputVars().add(varDeclaration);
		assertNoMatch(type.getInterfaceList().getInputVars().getFirst(), new CrossReferenceMatcher(varDeclaration));
		assertMatch(type.getInterfaceList().getInputVars().getFirst(), new CrossReferenceMatcher(varDeclarationConst),
				0, 0, 14);
	}
}
