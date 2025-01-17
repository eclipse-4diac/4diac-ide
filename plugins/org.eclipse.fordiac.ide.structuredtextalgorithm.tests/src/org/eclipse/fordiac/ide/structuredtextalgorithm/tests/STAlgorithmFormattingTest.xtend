/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Ulzii Jargalsaikhan - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextalgorithm.tests

import org.eclipse.xtext.testing.extensions.InjectionExtension
import org.eclipse.xtext.testing.InjectWith
import org.junit.jupiter.api.^extension.ExtendWith
import com.google.inject.Inject
import org.eclipse.xtext.testing.formatter.FormatterTestHelper
import org.junit.jupiter.api.Test

@ExtendWith(InjectionExtension)
@InjectWith(STAlgorithmInjectorProvider)
class STAlgorithmFormattingTest {

	@Inject extension FormatterTestHelper

	@Test
	def void testAlgorithm() {
		assertFormatted[
			toBeFormatted = '''
				ALGORITHM hubert
				
				IF langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner THEN 
					bol1 := TRUE;
				END_IF;
				
				END_ALGORITHM
				
			'''

			expectation = '''
				ALGORITHM hubert
				
				IF langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner
					OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner
				THEN
					bol1 := TRUE;
				END_IF;
				
				END_ALGORITHM
				
			'''
		]
	}

	@Test
	def void testMethod() {
		assertFormatted[
			toBeFormatted = '''
				METHOD hubert
				
				IF langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner THEN 
					bol1 := TRUE;
				END_IF;
				
				END_METHOD
				
			'''

			expectation = '''
				METHOD hubert
				
				IF langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner
					OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner
				THEN
					bol1 := TRUE;
				END_IF;
				
				END_METHOD
				
			'''
		]
		assertFormatted[
			toBeFormatted = '''
				METHOD hubert : extremelylongTypeName11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111
				END_METHOD
				
			'''

			expectation = '''
				METHOD hubert
					: extremelylongTypeName11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111
				END_METHOD
				
			'''
		]
	}

	@Test
	def void testEmptyLinesInMethod_0() {
		assertFormatted[
			toBeFormatted = '''
				METHOD ADD_IN_ADD : INT
				
				
				
				ADD_IN_ADD := In + AddI;
				END_METHOD
			'''

			expectation = '''
				METHOD ADD_IN_ADD : INT
				
				ADD_IN_ADD := In + AddI;
				END_METHOD
				
			'''
		]
	}

	@Test
	def void testEmptyLinesInMethod_1() {
		assertFormatted[
			toBeFormatted = '''
				METHOD SUB_DIFF
				
				
				VAR_INPUT
					Start : INT;
				END_VAR
				
				
				VAR_OUTPUT
					Result : INT;
				END_VAR
				
				
				Result := Start - DiffI;
				
				
				END_METHOD
			'''

			expectation = '''
				METHOD SUB_DIFF
				
				VAR_INPUT
					Start : INT;
				END_VAR
				
				VAR_OUTPUT
					Result : INT;
				END_VAR
				
				Result := Start - DiffI;
				
				END_METHOD
				
			'''
		]
	}

	@Test
	def void testCorrectLineBreaksAfterComment_0() {
		assertFormatted[
			toBeFormatted = '''
				ALGORITHM REQ
				
				_sum := ADD_IN_ADD(); // returns sum via return value
				
				SUB_DIFF(Start := _sum, Result => _sum); // Use the previous sum as start value and returns result via output
				END_ALGORITHM
			'''

			expectation = '''
				ALGORITHM REQ
				
				_sum := ADD_IN_ADD(); // returns sum via return value
				
				SUB_DIFF(Start := _sum, Result => _sum); // Use the previous sum as start value and returns result via output
				END_ALGORITHM
				
			'''
		]
	}

	@Test
	def void testCorrectLineBreaksAfterComment_1() {
		assertFormatted[
			toBeFormatted = '''
				ALGORITHM REQ
				VAR_TEMP
					_sum : INT;
				END_VAR
				
				// METHODS share in and outputs of function block, as well as internal/static variables.
				// temporary variables are NOT shared with METHODS.
				_sum := ADD_IN_ADD(); // returns sum via return value
				
				SUB_DIFF(Start := _sum, Result => _sum); // Use the previous sum as start value and returns result via output
				
				// parameter
				_sum := MUL_MUL(Sum := _sum);
				
				_sum := DIV_DIV(_sum);
				
				Res := _sum;
				END_ALGORITHM
				
			'''

			expectation = '''
				ALGORITHM REQ
				VAR_TEMP
					_sum : INT;
				END_VAR
				
				// METHODS share in and outputs of function block, as well as internal/static variables.
				// temporary variables are NOT shared with METHODS.
				_sum := ADD_IN_ADD(); // returns sum via return value
				
				SUB_DIFF(Start := _sum, Result => _sum); // Use the previous sum as start value and returns result via output
				
				// parameter
				_sum := MUL_MUL(Sum := _sum);
				
				_sum := DIV_DIV(_sum);
				
				Res := _sum;
				END_ALGORITHM
				
			'''
		]
	}

	@Test
	def void splitOnSingleOperatorTest() {
		assertFormatted[
			toBeFormatted = '''
				METHOD hubert
				
				IF langerVariablenBezeichnerlangerVariablenBezeichnerlangerVariablenBezeichnerlangerVariablenBezeichner OR langerVariablenBezeichner THEN
					bol1 := TRUE;
				END_IF;
				
				END_METHOD
				
			'''
			expectation = '''
				METHOD hubert
				
				IF langerVariablenBezeichnerlangerVariablenBezeichnerlangerVariablenBezeichnerlangerVariablenBezeichner
					OR langerVariablenBezeichner
				THEN
					bol1 := TRUE;
				END_IF;
				
				END_METHOD
				
			'''
		]
	}

	@Test
	def void splitOnMultipleOperatorsTest() {
		assertFormatted[
			toBeFormatted = '''
				ALGORITHM hubert
				
				IF langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner THEN
					bol1 := TRUE;
				END_IF;
				
				END_ALGORITHM
			'''
			expectation = '''
				ALGORITHM hubert
				
				IF langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner
					OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner
				THEN
					bol1 := TRUE;
				END_IF;
				
				END_ALGORITHM
				
			'''
		]
	}

	@Test
	def void splitOnEndOfLineOperatorTest() {
		assertFormatted[
			toBeFormatted = '''
				ALGORITHM hubert
				
				IF langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR a THEN
					bol1 := TRUE;
				END_IF;
				
				END_ALGORITHM
			'''
			expectation = '''
				ALGORITHM hubert
				
				IF langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR a
				THEN
					bol1 := TRUE;
				END_IF;
				
				END_ALGORITHM
				
			'''
		]
	}

	@Test
	def void splitOnMultipleLinesTest() {
		assertFormatted[
			toBeFormatted = '''
				METHOD hubert
				
				IF langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR
				langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner THEN
					bol1 := TRUE;
				END_IF;
				
				END_METHOD
				
			'''

			expectation = '''
				METHOD hubert
				
				IF langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner
					OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner
					OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner
				THEN
					bol1 := TRUE;
				END_IF;
				
				END_METHOD
				
			'''
		]
	}

	@Test
	def void splitOperatorConsistencyTest() {
		assertFormatted[
			toBeFormatted = '''
				METHOD hubert
				
				IF langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner
					OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner
					OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner
				THEN
					bol1 := TRUE;
				END_IF;
				
				END_METHOD
				
			'''

			expectation = '''
				METHOD hubert
				
				IF langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner
					OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner
					OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner
				THEN
					bol1 := TRUE;
				END_IF;
				
				END_METHOD
				
			'''
		]
	}

	@Test
	def void splitOperatorOnMultipleExpressionsTest() {
		assertFormatted[
			toBeFormatted = '''
				METHOD hubert
				
				IF langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner
					OR langerVariablenBezeichner 
				THEN
					bol1 := langerVariablenBezeichner or langerVariablenBezeichner or langerVariablenBezeichner or langerVariablenBezeichner or langerVariablenBezeichner or langerVariablenBezeichner;
				END_IF;
				
				END_METHOD
				
			'''

			expectation = '''
				METHOD hubert
				
				IF langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner
					OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner
					OR langerVariablenBezeichner
				THEN
					bol1 := langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner
						OR langerVariablenBezeichner OR langerVariablenBezeichner OR langerVariablenBezeichner;
				END_IF;
				
				END_METHOD
				
			'''
		]
	}
}
