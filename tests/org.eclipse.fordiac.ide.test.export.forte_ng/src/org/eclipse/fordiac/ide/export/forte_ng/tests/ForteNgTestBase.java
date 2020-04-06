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

import static org.junit.Assert.assertFalse;
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

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

public class ForteNgTestBase {
	private static final DataTypeLibrary dataTypeLib = new DataTypeLibrary();
	protected STAlgorithmFilter stAlgorithmFilter = new STAlgorithmFilter();
	protected BasicFBType functionBlock;
	protected List<String> errors;

	@BeforeClass
	/**
	 * initialize the Equinox extension registry substitute
	 *
	 */
	public static void doSetup() {
		FBTypeStandaloneSetup.doSetup();
		StructuredTextStandaloneSetup.doSetup();
	}

	@Before
	/**
	 * clear all the variables that are specific to a single test
	 *
	 */
	public void clearEnvironment() {
		// prepare a function block object including an interface list
		functionBlock = LibraryElementFactory.eINSTANCE.createBasicFBType();
		functionBlock.setInterfaceList(LibraryElementFactory.eINSTANCE.createInterfaceList());

		// clear the errors-list
		errors = new ArrayList<>();
	}

	/**
	 * create a VarDeclaration with given name and data-type
	 *
	 * @param variableName name of the variable to be created
	 * @param dataType     data-type of the variable to be created
	 *
	 * @return the created variable-object
	 */
	protected VarDeclaration createVarDeclaration(String variableName, String dataType) {
		VarDeclaration variable = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		variable.setName(variableName);
		variable.setType(dataTypeLib.getType(dataType));
		return variable;
	}

	/**
	 * check if an error-list is empty and raise an assertion if not empty
	 *
	 * @param errors list of errormessages
	 */
	protected static void assertNoErrors(List<String> errors) {
		assertTrue("No error messages expected.", errors.isEmpty()); //$NON-NLS-1$
	}

	/**
	 * check if an error-list is not empty and raise an assertion if empty
	 *
	 * @param errors list of errormessages
	 */
	protected static void assertErrors(List<String> errors) {
		assertFalse("Error messages expected.", errors.isEmpty()); //$NON-NLS-1$
	}

	/**
	 * create a STAlgorithm with given name and content
	 *
	 * @param algorithmName name of the algorithm to be created
	 * @param algorithmText content of the algorithm to be created
	 *
	 * @return the created algorithm-object
	 */
	protected STAlgorithm createSTAlgorithm(String algorithmName, String algorithmText) {
		STAlgorithm stAlg = LibraryElementFactory.eINSTANCE.createSTAlgorithm();
		stAlg.setName(algorithmName);
		stAlg.setText(algorithmText);
		return stAlg;
	}

	/**
	 * cast a given algorithm to STAlgorithm, but check first if it's the right type
	 * of object
	 *
	 * @param algorithm
	 *
	 * @return the cast algorithm-object
	 */
	protected STAlgorithm castAlgorithm(Algorithm algorithm) {
		if (algorithm instanceof STAlgorithm) {
			return (STAlgorithm) algorithm;
		} else {
			fail("Programming error in JUnit test: incompatible algorithm (Only STAlgorithm is allowed here)."); //$NON-NLS-1$
			return null;
		}
	}

	/**
	 * check if an error-list contains a set of error messages; raise an assertion
	 * if emtpy
	 *
	 * @param errors   list of errormessages
	 * @param messages list of messages to check for
	 */

	protected static void assertErrorMessages(List<String> errors, String... messages) {
		for (String message : messages) {
			Boolean contained = false;
			for (String error : errors) {
				contained = contained || error.contains(message);
			}
			assertTrue("Missing error message: " + message, contained);
		}
	}

}
