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

import org.junit.Test;

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

public class ForteNgTest extends ForteNgTestBase {

	@Test
	public void emptyExpression() {
		CharSequence generatedCode = stAlgorithmFilter.generate("", functionBlock, errors);

		assertErrors(errors); // Expression can not be empty
		assertNull(generatedCode);
	}

	@Test
	public void assignmentExpression() {
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration("B", "BOOL"));

		CharSequence generatedCode = stAlgorithmFilter.generate("B := 1", functionBlock, errors);

		assertErrors(errors); // Expression can not be an assignment
		assertNull(generatedCode);
	}

	@Test
	public void simpleAssignmentAlgorithm() {
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration("B", "BOOL"));
		functionBlock.getAlgorithm().add(createSTAlgorithm("algorithm", "B := 1;"));

		CharSequence generatedCode = stAlgorithmFilter
				.generate(castAlgorithm(functionBlock.getAlgorithmNamed("algorithm")), errors);

		assertNoErrors(errors);
		assertNotNull(generatedCode);
		assertEquals("B() = 1;\n", generatedCode.toString());
	}

	@Test
	public void functionSQRTExpression() {
		functionBlock.getInterfaceList().getInputVars().add(createVarDeclaration("variableA", "REAL"));

		CharSequence generatedCode = stAlgorithmFilter.generate("SQRT(variableA)", functionBlock, errors);

		assertNoErrors(errors); // Expression can not be an assignment
		assertNotNull(generatedCode);
		assertEquals("SQRT(variableA())", generatedCode.toString());
	}
}
