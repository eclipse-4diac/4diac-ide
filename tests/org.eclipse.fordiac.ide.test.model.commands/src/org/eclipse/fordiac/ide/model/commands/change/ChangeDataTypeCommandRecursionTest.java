/*******************************************************************************
 * Copyright (c) 2026 Huzaifa Abdul Rehman
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.fordiac.ide.model.data.ArrayType;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.DirectlyDerivedType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.util.ErrorMessenger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChangeDataTypeCommandRecursionTest {

	@BeforeEach
	void pauseErrorMessages() {
		ErrorMessenger.pauseMessages();
	}

	@AfterEach
	void resumeErrorMessages() {
		ErrorMessenger.unpauseMessages();
	}

	@Test
	void rejectsCircularArrayType() {
		final StructuredType structure = structure("Structure"); //$NON-NLS-1$
		final ArrayType array = DataFactory.eINSTANCE.createArrayType();
		array.setBaseType(structure);

		assertFalse(ChangeDataTypeCommand.forDataType(member(structure), array).canExecute());
	}

	@Test
	void rejectsCircularDerivedType() {
		final StructuredType structure = structure("Structure"); //$NON-NLS-1$
		final DirectlyDerivedType derived = DataFactory.eINSTANCE.createDirectlyDerivedType();
		derived.setBaseType(structure);

		assertFalse(ChangeDataTypeCommand.forDataType(member(structure), derived).canExecute());
	}

	@Test
	void acceptsAcyclicType() {
		final StructuredType structure = structure("Structure"); //$NON-NLS-1$
		final StructuredType other = structure("Other"); //$NON-NLS-1$

		assertTrue(ChangeDataTypeCommand.forDataType(member(structure), other).canExecute());
	}

	private static StructuredType structure(final String name) {
		final StructuredType structure = DataFactory.eINSTANCE.createStructuredType();
		structure.setName(name);
		return structure;
	}

	private static VarDeclaration member(final StructuredType owner) {
		final VarDeclaration member = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		owner.getMemberVariables().add(member);
		return member;
	}
}
