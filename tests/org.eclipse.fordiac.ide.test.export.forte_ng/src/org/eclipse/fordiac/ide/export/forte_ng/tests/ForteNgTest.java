/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Ernst Blecha
 *     - test for forte_ng
 *******************************************************************************/

package org.eclipse.fordiac.ide.export.forte_ng.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.export.forte_ng.st.STAlgorithmFilter;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.structuredtext.StructuredTextStandaloneSetup;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.model.xtext.fbt.FBTypeStandaloneSetup;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

public class ForteNgTest {
	private STAlgorithmFilter stAlgorithmFilter = new STAlgorithmFilter();
	private BasicFBType functionBlock;
	private List<String> errors;

	@BeforeClass
	public static void doSetup() {
		// initialize the Equinox extension registry substitute
		FBTypeStandaloneSetup.doSetup();
		StructuredTextStandaloneSetup.doSetup();
	}

	@Before
	public void clearEnvironment() {
		// prepare a function block object including an interface list
		functionBlock = LibraryElementFactory.eINSTANCE.createBasicFBType();
		functionBlock.setInterfaceList(LibraryElementFactory.eINSTANCE.createInterfaceList());

		// clear the errors-list
		errors = new ArrayList<>();
	}

	private VarDeclaration createVarDeclaration(String variableName, String dataType) {
		// create a VarDeclaration with given name and data-type
		VarDeclaration variable = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		variable.setName(variableName);
		variable.setType(DataTypeLibrary.getInstance().getType(dataType));
		return variable;
	}

	private STAlgorithm createSTAlgorithm(String algorithmName, String algorithmText) {
		// create a STAlgorithm with given name and content
		STAlgorithm stAlg = LibraryElementFactory.eINSTANCE.createSTAlgorithm();
		stAlg.setName(algorithmName);
		stAlg.setText(algorithmText);
		return stAlg;
	}

	private STAlgorithm castAlgorithm(Algorithm algorithm) {
		// cast a given algorithm, but check first if all needed interfaces are
		// available
		if (algorithm instanceof STAlgorithm) {
			return (STAlgorithm) algorithm;
		} else {
			fail("Programming error in JUnit test: incompatible algorithm (Only STAlgorithm is allowed here).");
			return null;
		}
	}

	@Test
	public void emptyExpression() {
		CharSequence generatedCode = stAlgorithmFilter.generate("", functionBlock, errors);

		assertFalse("Error message expected: Expression can not be empty.", errors.isEmpty());
		assertNull(generatedCode);
	}

	@Test
	public void assignmentExpression() {
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration("B", "BOOL"));

		CharSequence generatedCode = stAlgorithmFilter.generate("B := 1", functionBlock, errors);

		assertFalse("Error message expected: Expression can not be an assignment.", errors.isEmpty());
		assertNull(generatedCode);
	}

	@Test
	public void simpleAssignmentAlgorithm() {
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration("B", "BOOL"));
		functionBlock.getAlgorithm().add(createSTAlgorithm("algorithm", "B := 1;"));

		CharSequence generatedCode = stAlgorithmFilter
				.generate(castAlgorithm(functionBlock.getAlgorithmNamed("algorithm")), errors);

		assertTrue("No error messages expected.", errors.isEmpty());
		assertNotNull(generatedCode);
		assertEquals("B() = 1;\n", generatedCode.toString());
	}
}
