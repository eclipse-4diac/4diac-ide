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
package org.eclipse.fordiac.ide.model.libraryElement.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.helpers.ArraySizeHelper;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
class InOutVarsTest {

	@Test
	void testInOutVars() {
		final InterfaceList interfaceList = LibraryElementFactory.eINSTANCE.createInterfaceList();
		final VarDeclaration inOutVar1 = newInOutVarDeclaration("DIO1"); //$NON-NLS-1$
		final VarDeclaration inOutVar2 = newInOutVarDeclaration("DIO2"); //$NON-NLS-1$
		// add
		interfaceList.getInOutVars().add(inOutVar1);
		final VarDeclaration outMappedInOutVar1 = OutMappedInOutVarAdapter.find(inOutVar1);
		assertIterableEquals(List.of(inOutVar1), interfaceList.getInOutVars());
		assertIterableEquals(List.of(outMappedInOutVar1), interfaceList.getOutMappedInOutVars());
		// add other
		interfaceList.getInOutVars().add(inOutVar2);
		final VarDeclaration outMappedInOutVar2 = OutMappedInOutVarAdapter.find(inOutVar2);
		assertIterableEquals(List.of(inOutVar1, inOutVar2), interfaceList.getInOutVars());
		assertIterableEquals(List.of(outMappedInOutVar1, outMappedInOutVar2), interfaceList.getOutMappedInOutVars());
		// move
		interfaceList.getInOutVars().move(0, 1);
		assertIterableEquals(List.of(inOutVar2, inOutVar1), interfaceList.getInOutVars());
		assertIterableEquals(List.of(outMappedInOutVar2, outMappedInOutVar1), interfaceList.getOutMappedInOutVars());
		// remove
		interfaceList.getInOutVars().remove(0);
		assertIterableEquals(List.of(inOutVar1), interfaceList.getInOutVars());
		assertIterableEquals(List.of(outMappedInOutVar1), interfaceList.getOutMappedInOutVars());
		// re-add
		interfaceList.getInOutVars().add(inOutVar2);
		assertIterableEquals(List.of(inOutVar1, inOutVar2), interfaceList.getInOutVars());
		assertIterableEquals(List.of(outMappedInOutVar1, outMappedInOutVar2), interfaceList.getOutMappedInOutVars());
		// set
		final VarDeclaration inOutVar3 = newInOutVarDeclaration("DIO3"); //$NON-NLS-1$
		final VarDeclaration inOutVar4 = newInOutVarDeclaration("DIO4"); //$NON-NLS-1$
		interfaceList.getInOutVars().set(0, inOutVar3);
		interfaceList.getInOutVars().set(1, inOutVar4);
		final VarDeclaration outMappedInOutVar3 = OutMappedInOutVarAdapter.find(inOutVar3);
		final VarDeclaration outMappedInOutVar4 = OutMappedInOutVarAdapter.find(inOutVar4);
		assertIterableEquals(List.of(inOutVar3, inOutVar4), interfaceList.getInOutVars());
		assertIterableEquals(List.of(outMappedInOutVar3, outMappedInOutVar4), interfaceList.getOutMappedInOutVars());
		// clear
		interfaceList.getInOutVars().clear();
		assertIterableEquals(Collections.emptyList(), interfaceList.getInOutVars());
		assertIterableEquals(Collections.emptyList(), interfaceList.getOutMappedInOutVars());
	}

	@Test
	void testConcurrentModification() {
		final InterfaceList interfaceList = LibraryElementFactory.eINSTANCE.createInterfaceList();
		final VarDeclaration inOutVar1 = newInOutVarDeclaration("DIO1"); //$NON-NLS-1$
		final VarDeclaration inOutVar2 = newInOutVarDeclaration("DIO2"); //$NON-NLS-1$
		// add
		interfaceList.getInOutVars().add(inOutVar1);
		interfaceList.getInOutVars().add(inOutVar2);
		// concurrent modify
		interfaceList.getOutMappedInOutVars().remove(1);
		// assert throws
		final EList<VarDeclaration> inOutVars = interfaceList.getInOutVars();
		assertThrows(ConcurrentModificationException.class, () -> inOutVars.remove(1));
	}

	@Test
	void testChangeVarDeclaration() {
		final InterfaceList interfaceList = LibraryElementFactory.eINSTANCE.createInterfaceList();
		final VarDeclaration inOutVar1 = newInOutVarDeclaration("DIO1"); //$NON-NLS-1$
		final VarDeclaration inOutVar2 = newInOutVarDeclaration("DIO2"); //$NON-NLS-1$
		interfaceList.getInOutVars().add(inOutVar1);
		interfaceList.getInOutVars().add(inOutVar2);
		inOutVar1.setName("NEW"); //$NON-NLS-1$
		assertEquals("NEW", interfaceList.getOutMappedInOutVars().get(0).getName()); //$NON-NLS-1$
	}

	@Test
	void testCopy() {
		final InterfaceList interfaceList = LibraryElementFactory.eINSTANCE.createInterfaceList();
		final VarDeclaration inOutVar1 = newInOutVarDeclaration("DIO1"); //$NON-NLS-1$
		final VarDeclaration inOutVar2 = newInOutVarDeclaration("DIO2"); //$NON-NLS-1$
		interfaceList.getInOutVars().add(inOutVar1);
		interfaceList.getInOutVars().add(inOutVar2);
		// copy
		final InterfaceList interfaceListCopy = EcoreUtil.copy(interfaceList);
		final VarDeclaration inOutVar1Copy = interfaceListCopy.getInOutVars().get(0);
		final VarDeclaration inOutVar2Copy = interfaceListCopy.getInOutVars().get(1);
		final VarDeclaration outMappedInOutVar1Copy = OutMappedInOutVarAdapter.find(inOutVar1Copy);
		final VarDeclaration outMappedInOutVar2Copy = OutMappedInOutVarAdapter.find(inOutVar2Copy);
		assertEquals("DIO1", inOutVar1Copy.getName()); //$NON-NLS-1$
		assertEquals("DIO2", inOutVar2Copy.getName()); //$NON-NLS-1$
		assertIterableEquals(List.of(outMappedInOutVar1Copy, outMappedInOutVar2Copy),
				interfaceListCopy.getOutMappedInOutVars());
	}

	@Test
	void testChangeArraySize() {
		final InterfaceList interfaceList = LibraryElementFactory.eINSTANCE.createInterfaceList();
		final VarDeclaration inOutVar1 = newInOutVarDeclaration("DIO1"); //$NON-NLS-1$
		final VarDeclaration inOutVar2 = newInOutVarDeclaration("DIO2"); //$NON-NLS-1$
		ArraySizeHelper.setArraySize(inOutVar2, "0..2"); //$NON-NLS-1$
		interfaceList.getInOutVars().add(inOutVar1);
		interfaceList.getInOutVars().add(inOutVar2);
		// check
		assertFalse(interfaceList.getOutMappedInOutVars().get(0).isArray());
		assertTrue(interfaceList.getOutMappedInOutVars().get(1).isArray());
		assertEquals("0..2", interfaceList.getOutMappedInOutVars().get(1).getArraySize().getValue()); //$NON-NLS-1$
		// change
		ArraySizeHelper.setArraySize(inOutVar1, "0..1"); //$NON-NLS-1$
		ArraySizeHelper.setArraySize(inOutVar2, "1..2"); //$NON-NLS-1$
		// check
		assertTrue(interfaceList.getOutMappedInOutVars().get(0).isArray());
		assertTrue(interfaceList.getOutMappedInOutVars().get(1).isArray());
		assertEquals("0..1", interfaceList.getOutMappedInOutVars().get(0).getArraySize().getValue()); //$NON-NLS-1$
		assertEquals("1..2", interfaceList.getOutMappedInOutVars().get(1).getArraySize().getValue()); //$NON-NLS-1$
	}

	@Test
	void testIsInOutVar() {
		final InterfaceList interfaceList = LibraryElementFactory.eINSTANCE.createInterfaceList();
		final VarDeclaration inOutVar = newInOutVarDeclaration("DIO1"); //$NON-NLS-1$

		assertFalse(inOutVar.isInOutVar());
		interfaceList.getInOutVars().add(inOutVar);
		assertTrue(inOutVar.isInOutVar());

		final VarDeclaration outMappedInOutVar = OutMappedInOutVarAdapter.find(inOutVar);
		assertTrue(outMappedInOutVar.isInOutVar());

		// test input var
		final VarDeclaration inVar = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		assertFalse(inVar.isInOutVar());
		interfaceList.getInputVars().add(inVar);
		assertFalse(inVar.isInOutVar());

		// test input var
		final VarDeclaration outVar = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		interfaceList.getOutputVars().add(outVar);
		assertFalse(outVar.isInOutVar());
	}

	private static VarDeclaration newInOutVarDeclaration(final String name) {
		final VarDeclaration inOutVar = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		inOutVar.setName(name);
		inOutVar.setIsInput(true);
		return inOutVar;
	}
}
