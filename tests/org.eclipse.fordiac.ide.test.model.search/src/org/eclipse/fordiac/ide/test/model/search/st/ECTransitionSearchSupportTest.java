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
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.search.CrossReferenceMatcher;
import org.junit.jupiter.api.Test;

@SuppressWarnings({ "nls", "static-method" })
class ECTransitionSearchSupportTest extends StructuredTextSearchSupportTest {

	@Test
	void testReferenceSearch() {
		final BasicFBType type = createBasicFBType("Test");
		final VarDeclaration varDeclaration = createVarDeclaration("DI1", ElementaryTypes.DINT);
		final VarDeclaration varDeclaration2 = createVarDeclaration("DI2", ElementaryTypes.DINT);
		type.getInterfaceList().getInputVars().add(varDeclaration);
		type.getInterfaceList().getInputVars().add(varDeclaration2);
		type.getECC().getECTransition().add(createECTransition("DI2 < 17"));
		assertNoMatch(type.getECC().getECTransition().getFirst(), new CrossReferenceMatcher(varDeclaration));
		assertMatch(type.getECC().getECTransition().getFirst(), new CrossReferenceMatcher(varDeclaration2), 0, 0, 3);
	}
}
