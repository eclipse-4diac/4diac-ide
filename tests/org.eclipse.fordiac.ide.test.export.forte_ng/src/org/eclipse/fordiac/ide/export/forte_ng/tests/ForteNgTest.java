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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.text.MessageFormat;

import org.junit.Test;

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

public class ForteNgTest extends ForteNgTestBase implements DatatypeConstants {

	@Test
	public void emptyExpression() {
		CharSequence generatedCode = stAlgorithmFilter.generate("", functionBlock, errors); //$NON-NLS-1$

		assertErrors(errors); // Expression can not be empty
		assertNull(generatedCode);
	}

	@Test
	public void assignmentExpression() {
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration(VARIABLE_NAME, BOOL));

		CharSequence generatedCode = stAlgorithmFilter.generate(MessageFormat.format("{0} := 1", VARIABLE_NAME), //$NON-NLS-1$
				functionBlock, errors);

		assertErrors(errors); // Expression can not be an assignment
		assertNull(generatedCode);
	}

	@Test
	public void simpleAssignmentAlgorithm() {
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration(VARIABLE_NAME, BOOL));
		functionBlock.getAlgorithm()
				.add(createSTAlgorithm(ALGORITHM_NAME, MessageFormat.format("{0} := 1;", VARIABLE_NAME))); //$NON-NLS-1$

		CharSequence generatedCode = stAlgorithmFilter
				.generate(castAlgorithm(functionBlock.getAlgorithmNamed(ALGORITHM_NAME)), errors);

		assertNoErrors(errors);
		assertNotNull(generatedCode);
		assertEquals(MessageFormat.format("{0}() = 1;\n", VARIABLE_NAME), generatedCode.toString()); //$NON-NLS-1$
	}

	@Test
	public void functionSQRTExpression() {
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration(VARIABLE_NAME, REAL));

		CharSequence generatedCode = stAlgorithmFilter.generate(MessageFormat.format("SQRT({0})", VARIABLE_NAME), //$NON-NLS-1$
				functionBlock, errors);

		assertNoErrors(errors); // Expression can not be an assignment
		assertNotNull(generatedCode);
		assertEquals(MessageFormat.format("SQRT({0}())", VARIABLE_NAME), generatedCode.toString()); //$NON-NLS-1$
	}

	@Test
	public void powerExpression() {
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration(VARIABLE_NAME, REAL));
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration(VARIABLE2_NAME, REAL));

		CharSequence generatedCode = stAlgorithmFilter
				.generate(MessageFormat.format("{0} ** {1}", VARIABLE_NAME, VARIABLE2_NAME), functionBlock, errors); //$NON-NLS-1$

		assertNoErrors(errors); // Expression can not be an assignment
		assertNotNull(generatedCode);
		assertEquals(MessageFormat.format("EXPT({0}(), {1}())", VARIABLE_NAME, VARIABLE2_NAME), //$NON-NLS-1$
				generatedCode.toString());
	}
}
