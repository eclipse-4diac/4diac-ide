/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *               2022 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Ulzii Jargalsaikhan
 *       - initial API and implementation and/or initial documentation
 *   Martin Jobst
 *       - validation for reserved identifiers
 *       - validation for calls
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.tests

import com.google.inject.Inject
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage
import org.eclipse.fordiac.ide.structuredtextcore.validation.STCoreValidator
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.validation.STFunctionValidator
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.extensions.InjectionExtension
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.^extension.ExtendWith

@ExtendWith(InjectionExtension)
@InjectWith(STFunctionInjectorProvider)
class STFunctionValidatorTest {

	@Inject extension ParseHelper<STFunctionSource> parseHelper
	@Inject extension ValidationTestHelper

	@BeforeAll
	def static void setup() {
		new DataTypeLibrary
	}

	@Test
	def void testWrongCasedIdentifierWarning() {
		'''
		FUNCTION hubert
		VAR
		    bol1 : BOOL := FALSE;
		    bol2 : BOOL := TRUE;
		END_VAR
		IF bol1 THEN
			bol1 := TRUE;
		ELSIF bol2 THEN
			bol1 := TRUE;
		ELSE
			bol1 := FALSE;
			bOl1 := FALSE;
		END_IF;
			bOl1 := 1;
		END_FUNCTION'''.parse.assertWarning(STCorePackage.Literals.ST_FEATURE_EXPRESSION,
			STFunctionValidator.WRONG_NAME_CASE)
	}

	@Test
	def void testConsecutiveUnderscoreErrorValidator() {
		'''
		FUNCTION hubert
		VAR
		    bo__l1 : BOOL := FALSE;
		    bol2 : BOOL := TRUE;
		END_VAR
		END_FUNCTION'''.parse.assertError(STCorePackage.eINSTANCE.STVarDeclaration,
			STCoreValidator.CONSECUTIVE_UNDERSCORE_IN_IDENTIFIER_ERROR)
	}

	@Test
	def void testTrailingUnderscoreErrorValidator() {
		'''
		FUNCTION hubert
		VAR
		    bol1_ : BOOL := FALSE;
		    bol2 : BOOL := TRUE;
		END_VAR
		END_FUNCTION'''.parse.assertError(STCorePackage.eINSTANCE.STVarDeclaration,
			STCoreValidator.TRAILING_UNDERSCORE_IN_IDENTIFIER_ERROR)
	}

	@Test
	def void testInvalidLeftSide() {
		'''
			FUNCTION hubert
			VAR
				int1 : INT := 3;
				int2 : INT := 10;
			END_VAR
			3 := 4;
			2+3 := 5;
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STAssignmentStatement, STCoreValidator.NOT_ASSIGNABLE)
	}

	@Test
	def void testInvalidAssignment() {
		'''
			FUNCTION hubert
			VAR
				int1 : INT := 3;
				int2 : INT := 5;
				real1 : REAL := 3.14;
				sint1 : SINT := 4;
			END_VAR
				sint1 := int2;
				int2 := sint1;
				int1 := real;
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STAssignmentStatement, STCoreValidator.NON_COMPATIBLE_TYPES)

	}

	def void testInvalidArrayAssignment() {
		'''
			FUNCTION hubert
			VAR
				testArray: ARRAY [ 0 .. 10 ] OF INT;
				testArray2: ARRAY [ 0 .. 10, 0 .. 10] OF INT;
				testArray3: ARRAY [ 0 .. 10] OF BOOL;
				testArray4: ARRAY [ 0 .. 10 ] OF REAL;
			END_VAR
			testArray := testArray2[0];
			testArray3 := testArray[1];
			testArray := testArray4;
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STAssignmentStatement, STCoreValidator.NON_COMPATIBLE_TYPES)
	}

	@Test
	def void testValidAssignment() {
		'''
			FUNCTION hubert
			VAR
				int1 : INT := 3;
				int2 : INT := 10;
				bool1 : BOOL := 0;
				sint1 : SINT := 4;
			END_VAR
				int1 := int2;
				int2 := sint1;
			END_FUNCTION
		'''.parse.assertNoErrors
	}

	@Test
	def void testValidArrayAssignment() {
		'''
			FUNCTION hubert
			VAR
				testArray: ARRAY [ 0 .. 10 ] OF INT;
				testArray2: ARRAY [ 0 .. 10, 0 .. 10] OF INT;
				testArray3: ARRAY [ 0 .. 10] OF INT;
				testArray4: ARRAY [ 0 .. 10 ] OF REAL;
			END_VAR
			testArray := testArray3;
			testArray := testArray2[0];
			testArray2[0] := testArray2[1];
			testArray2[0, 2] := 5;
			END_FUNCTION
		'''.parse.assertNoErrors
	}

	@Test
	def void testNoCast() {
		'''
			FUNCTION hubert
			VAR
				lreal1 : LREAL;
				usint1 : USINT;
				word1 : WORD;
				bool1 : BOOL;
				ldt1 : LDT;
				string1 : STRING;
			END_VAR
				bool1 := usint1;
				word1 := ltime1;
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STAssignmentStatement, STCoreValidator.NO_CAST_AVAILABLE)
	}

	@Test
	def void testArrayNoCast() {
		'''
			FUNCTION hubert
			VAR
				testArray: ARRAY [ 0 .. 10 ] OF BOOL;
				testArray2: ARRAY [ 0 .. 10, 0 .. 10] OF INT;
				testArray3: ARRAY [ 0 .. 10] OF INT;
				testArray4: ARRAY [ 0 .. 10 ] OF REAL;
				int1 : INT := 3;
			END_VAR
			testArray := testArray3;
			testArray := testArray2[0];
			testArray2[0] := testArray2[1];
			testArray2[0, 2] := 5;
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STAssignmentStatement, STCoreValidator.NO_CAST_AVAILABLE)
	}

	@Test
	def void testCast() {
		'''
			FUNCTION hubert
			VAR
				int1 : INT;
				sint1 : SINT;
				word1 : WORD;
				byte1 : BYTE;
				char1 : CHAR;
				wchar1 : WCHAR;
				dword1 : DWORD;
				word1 : WORD; 
				byte1 : BYTE;
				ldt1 : LDT;
				ltod1 : LTOD;
			END_VAR
				ltod1 := LDT_TO_LTOD(ldt1);
				dword1 := WCHAR_TO_DWORD(wchar1);
				char1 := byte1;
				int1 := sint1;
				char1 := wchar1;
			END_FUNCTION
		'''.parse.assertNoErrors(STCorePackage.eINSTANCE.STAssignmentStatement, STCoreValidator.NO_CAST_AVAILABLE);
	}

	@Test
	def void testReservedIdentifierErrorValidator() {
		'''
		FUNCTION hubert
		VAR
		    add : BOOL;
		END_VAR
		END_FUNCTION'''.parse.assertError(STCorePackage.eINSTANCE.STVarDeclaration, STCoreValidator.RESERVED_IDENTIFIER_ERROR)
	}

	@Test
	def void testCallNonCallableErrorValidator() {
		'''
		FUNCTION hubert
		VAR
		    X : INT;
		END_VAR
		X();
		END_FUNCTION'''.parse.assertError(STCorePackage.eINSTANCE.STFeatureExpression,
			STCoreValidator.FEATURE_NOT_CALLABLE)
	}

	@Test
	def void testCallMixedFormalErrorValidator() {
		'''
		FUNCTION emil
		VAR_INPUT
		    A : INT;
		    B : INT;
		END_VAR
		END_FUNCTION
		
		FUNCTION hubert
		emil(17, B := 4);
		END_FUNCTION'''.parse.assertError(STCorePackage.eINSTANCE.STFeatureExpression,
			STCoreValidator.MIXING_FORMAL_AND_NON_FORMAL_ARGUMENTS)
	}

	@Test
	def void testCallWrongNumberArgumentsErrorValidator() {
		'''
		FUNCTION emil
		VAR_INPUT
		    A : INT;
		    B : INT;
		END_VAR
		END_FUNCTION
		
		FUNCTION hubert
		emil(17);
		END_FUNCTION'''.parse.assertError(STCorePackage.eINSTANCE.STFeatureExpression,
			STCoreValidator.WRONG_NUMBER_OF_ARGUMENTS)
	}

	@Test
	def void testCallNonFormalNotAssignable() {
		'''
		FUNCTION emil
		VAR_INPUT
		    A : INT;
		    B : INT;
		END_VAR
		VAR_OUTPUT
		    X : INT;
		END_VAR
		END_FUNCTION
		
		FUNCTION hubert
		emil(17, 4, 21);
		END_FUNCTION'''.parse.assertError(STCorePackage.eINSTANCE.STFeatureExpression, STCoreValidator.NOT_ASSIGNABLE)
	}

	@Test
	def void testCallFormalNotAssignable() {
		'''
		FUNCTION emil
		VAR_INPUT
		    A : INT;
		    B : INT;
		END_VAR
		VAR_IN_OUT
		    X : INT;
		END_VAR
		END_FUNCTION
		
		FUNCTION hubert
		emil(A := 17, B := 4, X := 21);
		END_FUNCTION'''.parse.assertError(STCorePackage.eINSTANCE.STFeatureExpression, STCoreValidator.NOT_ASSIGNABLE)
	}

	@Test
	def void testCallIncompatibleInputTypes() {
		'''
		FUNCTION emil
		VAR_INPUT
		    A : INT;
		    B : INT;
		END_VAR
		END_FUNCTION
		
		FUNCTION hubert
		emil(17, LINT#4);
		END_FUNCTION'''.parse.assertError(STCorePackage.eINSTANCE.STFeatureExpression,
			STCoreValidator.NON_COMPATIBLE_TYPES)
	}

	@Test
	def void testCallIncompatibleOutputTypes() {
		'''
		FUNCTION emil
		VAR_INPUT
		    A : INT;
		    B : INT;
		END_VAR
		VAR_OUTPUT
		    X : INT;
		END_VAR
		END_FUNCTION
		
		FUNCTION hubert
		VAR_TEMP
			X: SINT;
		END_VAR
		
		emil(17, 4, X);
		END_FUNCTION'''.parse.assertError(STCorePackage.eINSTANCE.STFeatureExpression,
			STCoreValidator.NON_COMPATIBLE_TYPES)
	}

	@Test
	def void testCallCompatibleOutputTypes() {
		'''
		FUNCTION emil
		VAR_INPUT
		    A : INT;
		    B : INT;
		END_VAR
		VAR_OUTPUT
		    X : INT;
		END_VAR
		END_FUNCTION
		
		FUNCTION hubert
		VAR_TEMP
			X: LINT;
		END_VAR
		
		emil(17, 4, X);
		END_FUNCTION'''.parse.assertNoErrors
	}

	@Test
	def void testCallIncompatibleInOutTypes() {
		'''
		FUNCTION emil
		VAR_INPUT
		    A : INT;
		    B : INT;
		END_VAR
		VAR_IN_OUT
		    X : INT;
		END_VAR
		END_FUNCTION
		
		FUNCTION hubert
		VAR_TEMP
			X: LINT;
		END_VAR
		
		emil(17, 4, X);
		END_FUNCTION'''.parse.assertError(STCorePackage.eINSTANCE.STFeatureExpression,
			STCoreValidator.NON_COMPATIBLE_TYPES)
		'''
		FUNCTION emil
		VAR_INPUT
		    A : INT;
		    B : INT;
		END_VAR
		VAR_IN_OUT
		    X : INT;
		END_VAR
		END_FUNCTION
		
		FUNCTION hubert
		VAR_TEMP
			X: SINT;
		END_VAR
		
		emil(17, 4, X);
		END_FUNCTION'''.parse.assertError(STCorePackage.eINSTANCE.STFeatureExpression,
			STCoreValidator.NON_COMPATIBLE_TYPES)
	}
}
