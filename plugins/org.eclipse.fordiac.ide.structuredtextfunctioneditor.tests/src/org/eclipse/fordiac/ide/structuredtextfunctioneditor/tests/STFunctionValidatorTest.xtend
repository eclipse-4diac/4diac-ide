/*******************************************************************************
 * Copyright (c) 2022, 2024 Primetals Technologies Austria GmbH
 *                          Martin Erich Jobst
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
 *       - validation for truncated string literals
 *       - linking diagnostics
 *       - test binary expression result type
 *   Martin Melik Merkumians
 * 		- validation for duplicate names on FUNCTIONs
 *      - validation of return types
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.tests

import com.google.inject.Inject
import java.util.stream.Stream
import org.eclipse.fordiac.ide.model.data.AnyStringType
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryOperator
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STUnaryExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STUnaryOperator
import org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil
import org.eclipse.fordiac.ide.structuredtextcore.validation.STCoreValidator
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionPackage
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.validation.STFunctionValidator
import org.eclipse.xtext.diagnostics.Diagnostic
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.extensions.InjectionExtension
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.^extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.params.provider.Arguments.*

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
			// comment
			bOl1 := FALSE;
		END_IF;
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
		'''.parse.assertError(STCorePackage.eINSTANCE.STAssignment, STCoreValidator.VALUE_NOT_ASSIGNABLE)
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
		'''.parse.assertError(STCorePackage.eINSTANCE.STAssignment, STCoreValidator.NON_COMPATIBLE_TYPES)

	}

	@Test
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
		'''.parse.assertError(STCorePackage.eINSTANCE.STAssignment, STCoreValidator.NON_COMPATIBLE_TYPES)
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
		'''.parse.assertError(STCorePackage.eINSTANCE.STAssignment, STCoreValidator.NON_COMPATIBLE_TYPES)
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
		'''.parse.assertError(STCorePackage.eINSTANCE.STAssignment, STCoreValidator.NON_COMPATIBLE_TYPES)
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
				int1 := sint1;
			END_FUNCTION
		'''.parse.assertNoErrors(STCorePackage.eINSTANCE.STAssignment, STCoreValidator.NON_COMPATIBLE_TYPES);
	}

	@Test
	def void testInvalidInitializer() {
		'''
			FUNCTION hubert
			VAR
				int1 : INT := LINT#17;
			END_VAR
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STElementaryInitializerExpression,
			STCoreValidator.NON_COMPATIBLE_TYPES)

	}

	@Test
	def void testNestedAssignment() {
		'''
			FUNCTION hubert
			VAR
				int1 : INT;
				int2 : INT;
			END_VAR
				int1 := int2 := 17;
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STAssignment, STCoreValidator.NESTED_ASSIGNMENT)

	}

	@Test
	def void testInvalidArrayInitializer() {
		'''
			FUNCTION hubert
			VAR
				testArray: ARRAY [ 0 .. 3 ] OF INT := [LINT#17, 4];
			END_VAR
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STElementaryInitializerExpression,
			STCoreValidator.NON_COMPATIBLE_TYPES)
		'''
			FUNCTION hubert
			VAR
				testArray: ARRAY [ 0 .. 3 ] OF INT := [17, '4diac'];
			END_VAR
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STElementaryInitializerExpression,
			STCoreValidator.NON_COMPATIBLE_TYPES)
	}

	@Test
	def void testInvalidArrayDimensionsInitializer() {
		'''
			FUNCTION hubert
			VAR
				testArray: ARRAY [ 0 .. 3 ] OF INT := [[17, 4]];
			END_VAR
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STArrayInitializerExpression,
			STCoreValidator.INSUFFICIENT_ARRAY_DIMENSIONS)
		'''
			FUNCTION hubert
			VAR
				testArray: ARRAY [ 0 .. 3, 0 .. 2 ] OF INT := [17, 4, 21];
			END_VAR
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STElementaryInitializerExpression,
			STCoreValidator.NON_COMPATIBLE_TYPES)
		'''
			FUNCTION hubert
			VAR
				testArray: ARRAY [ 0 .. 3, 0 .. 2 ] OF INT := [[17, 4], 21];
			END_VAR
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STElementaryInitializerExpression,
			STCoreValidator.NON_COMPATIBLE_TYPES)
	}

	@Test
	def void testValidInitializer() {
		'''
			FUNCTION hubert
			VAR
				int1 : INT := 17;
				int2 : INT := SINT#4;
				bool1 : BOOL := 0;
			END_VAR
			END_FUNCTION
		'''.parse.assertNoErrors
	}

	@Test
	def void testValidArrayInitializer() {
		'''
			FUNCTION hubert
			VAR
				testArray: ARRAY [ 0 .. 3 ] OF INT := [17, 4];
			END_VAR
			END_FUNCTION
		'''.parse.assertNoErrors
	}

	@Test
	def void testInvalidIfConditionType() {
		'''
			FUNCTION hubert
			VAR
				int1 : INT;
			END_VAR
			
			IF int1 THEN
				int1 := 17;
			END_IF;
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STIfStatement, STCoreValidator.NON_COMPATIBLE_TYPES)
	}

	@Test
	def void testInvalidWhileConditionType() {
		'''
			FUNCTION hubert
			VAR
				int1 : INT;
			END_VAR
			
			WHILE int1 DO
				int1 := 17;
			END_WHILE;
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STWhileStatement, STCoreValidator.NON_COMPATIBLE_TYPES)
	}

	@Test
	def void testInvalidRepeatConditionType() {
		'''
			FUNCTION hubert
			VAR
				int1 : INT;
			END_VAR
			
			REPEAT
				int1 := 17;
			UNTIL int1
			END_REPEAT;
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STRepeatStatement, STCoreValidator.NON_COMPATIBLE_TYPES)
	}

	@Test
	def void testInvalidForTypes() {
		'''
			FUNCTION hubert
			VAR
				int1 : STRING;
				int2 : INT;
			END_VAR
			
			FOR int1 := 4 TO 17 DO
				int2 := 17;
			END_FOR;
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STForStatement, STCoreValidator.FOR_VARIABLE_NOT_INTEGRAL_TYPE)
		'''
			FUNCTION hubert
			VAR
				int1 : INT;
				int2 : INT;
			END_VAR
			
			FOR int1 := LINT#4 TO 17 DO
				int2 := 17;
			END_FOR;
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STForStatement, STCoreValidator.NON_COMPATIBLE_TYPES)
		'''
			FUNCTION hubert
			VAR
				int1 : INT;
				int2 : INT;
			END_VAR
			
			FOR int1 := 4 TO LINT#17 DO
				int2 := 17;
			END_FOR;
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STForStatement, STCoreValidator.NON_COMPATIBLE_TYPES)
		'''
			FUNCTION hubert
			VAR
				int1 : INT;
				int2 : INT;
			END_VAR
			
			FOR int1 := 4 TO 17 BY LINT#2 DO
				int2 := 17;
			END_FOR;
			END_FUNCTION
		'''.parse.assertNoErrors
		'''
			FUNCTION hubert
			VAR
				int1 : INT;
				int2 : INT;
			END_VAR
			
			FOR int1 := 4 TO 17 BY "2" DO
				int2 := 17;
			END_FOR;
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STForStatement, STCoreValidator.NON_COMPATIBLE_TYPES)
	}

	@Test
	def void testInvalidForVariable() {
		'''
			FUNCTION hubert
			VAR
				int1 : INT;
			END_VAR
			
			FOR int1 := 4 TO 17 DO
			END_FOR;
			END_FUNCTION
		'''.parse.assertNoErrors
		'''
			FUNCTION hubert
			FOR 4 := 4 TO 17 DO
			END_FOR;
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STForStatement, STCoreValidator.VALUE_NOT_ASSIGNABLE)
		'''
			FUNCTION hubert
			VAR CONSTANT
				int1 : INT;
			END_VAR
			
			FOR int1 := 4 TO 17 DO
			END_FOR;
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STForStatement, STCoreValidator.VALUE_NOT_ASSIGNABLE)
	}

	@Test
	def void testInvalidForVariableNonTemporary() {
		'''
			FUNCTION hubert
			VAR_TEMP
				int1 : INT;
			END_VAR
			
			FOR int1 := 4 TO 17 DO
			END_FOR;
			END_FUNCTION
		'''.parse.assertNoIssues
		'''
			FUNCTION hubert
			VAR_OUTPUT
				int1 : INT;
			END_VAR
			
			FOR int1 := 4 TO 17 DO
			END_FOR;
			END_FUNCTION
		'''.parse.assertWarning(STCorePackage.eINSTANCE.STForStatement,
			STCoreValidator.FOR_CONTROL_VARIABLE_NON_TEMPORARY)
	}

	@Test
	def void testInvalidForVariableModification() {
		'''
			FUNCTION hubert
			VAR
				int1 : INT;
				int2 : INT;
			END_VAR
			
			FOR int1 := 4 TO 17 DO
				int2 := 17;
			END_FOR;
			END_FUNCTION
		'''.parse.assertNoErrors
		'''
			FUNCTION hubert
			VAR
				int1 : INT;
			END_VAR
			
			FOR int1 := 4 TO 17 DO
				int1 := 17;
			END_FOR;
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STFeatureExpression,
			STCoreValidator.FOR_CONTROL_VARIABLE_MODIFICATION)
		'''
			FUNCTION hubert
			VAR
				int1 : INT;
				int2 : INT;
			END_VAR
			
			FOR int1 := 4 TO 17 DO
				FOR int2 := 4 TO 17 DO
					int1 := 17;
				END_FOR;
			END_FOR;
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STFeatureExpression,
			STCoreValidator.FOR_CONTROL_VARIABLE_MODIFICATION)
		'''
			FUNCTION hubert
			VAR
				int1 : INT;
				int2 : INT;
			END_VAR
			
			FOR int1 := 4 TO 17 DO
				FOR int1 := 4 TO 17 DO
					int2 := 17;
				END_FOR;
			END_FOR;
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STFeatureExpression,
			STCoreValidator.FOR_CONTROL_VARIABLE_MODIFICATION)
	}

	@Test
	def void testInvalidForVariableUndefined() {
		'''
			FUNCTION hubert
			VAR
				int1 : INT;
				int2 : INT;
			END_VAR
			
			FOR int1 := 4 TO 17 DO
				int2 := 17;
			END_FOR;
			FOR int1 := 4 TO 17 DO // undefined but write access -> ok
				int2 := 17;
			END_FOR;
			int1 := int2; // write access -> ok
			int2 := int1 + 1; // was written -> ok
			END_FUNCTION
		'''.parse.assertNoIssues
		'''
			FUNCTION hubert
			VAR
				int1 : INT;
				int2 : INT;
			END_VAR
			
			FOR int1 := 4 TO 17 DO
				int2 := 17;
			END_FOR;
			
			IF int2 <> 0 THEN
				int1 := int2 + 1; // write access -> ok
			ELSE
				int1 := int2 + 2; // write access -> ok
			END_IF;
			
			int2 := int1 + 1; // read access, but was written in all IF clauses -> ok
			END_FUNCTION
		'''.parse.assertNoIssues
		'''
			FUNCTION hubert
			VAR
				int1 : INT;
				int2 : INT;
			END_VAR
			
			FOR int1 := 4 TO 17 DO
			END_FOR;
			int2 := int1; // read access -> warning
			END_FUNCTION
		'''.parse.assertWarning(STCorePackage.eINSTANCE.STFeatureExpression,
			STCoreValidator.FOR_CONTROL_VARIABLE_UNDEFINED)
		'''
			FUNCTION hubert
			VAR
				int1 : INT;
				int2 : INT;
			END_VAR
			
			FOR int1 := 4 TO 17 DO
			END_FOR;
			
			IF int2 <> 0 THEN
				int1 := int2 + 1; // write access -> ok
			END_IF;
			
			WHILE int2 <> 0 DO
				int1 := int2 + 1; // write access -> ok
			END_WHILE;
			
			REPEAT
				int1 := int2 + 1; // write access -> ok
			UNTIL int2 <> 0
			END_REPEAT;
			
			int2 := int1; // read access and may not have been written if IF, WHILE, and REPEAT not taken -> warning
			END_FUNCTION
		'''.parse.assertWarning(STCorePackage.eINSTANCE.STFeatureExpression,
			STCoreValidator.FOR_CONTROL_VARIABLE_UNDEFINED)
		'''
			FUNCTION hubert
			VAR
				int1 : INT;
				int2 : INT;
			END_VAR
			
			FOR int1 := 4 TO 17 DO
				FOR int2 := 4 TO 17 DO
				END_FOR;
			END_FOR;
			int1 := int2; // read access and undefined from inner FOR loop -> warning
			END_FUNCTION
		'''.parse.assertWarning(STCorePackage.eINSTANCE.STFeatureExpression,
			STCoreValidator.FOR_CONTROL_VARIABLE_UNDEFINED)
		'''
			FUNCTION hubert
			VAR
				int1 : INT;
				int2 : INT;
			END_VAR
			
			WHILE int2 <> 0 DO // read access and may be undefined from inner FOR loop -> warning
				FOR int2 := 4 TO 17 DO
				END_FOR;
			END_WHILE;
		'''.parse.assertWarning(STCorePackage.eINSTANCE.STFeatureExpression,
			STCoreValidator.FOR_CONTROL_VARIABLE_UNDEFINED)
		'''
			FUNCTION hubert
			VAR
				int1 : INT;
				int2 : INT;
			END_VAR
			
			REPEAT
				FOR int2 := 4 TO 17 DO
				END_FOR;
			UNTIL int2 <> 0 // read access and may be undefined from inner FOR loop -> warning
			END_REPEAT;
			END_FUNCTION
		'''.parse.assertWarning(STCorePackage.eINSTANCE.STFeatureExpression,
			STCoreValidator.FOR_CONTROL_VARIABLE_UNDEFINED)
	}

	@Test
	def void testInvalidCaseConditionType() {
		'''
			FUNCTION hubert
			VAR
				int1 : INT;
			END_VAR
			
			CASE int1 OF
				1: int1 := 17;
				LINT#2: int1 := 17;
			END_CASE;
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STCaseCases, STCoreValidator.NON_COMPATIBLE_TYPES)
	}

	@Test
	def void testInvalidNumericLiteral() {
		'''
			FUNCTION hubert
			VAR
				bool1 : BOOL := BOOL#2;
			END_VAR
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STNumericLiteral, STCoreValidator.INVALID_NUMERIC_LITERAL)
		'''
			FUNCTION hubert
			VAR
				int1 : SINT := SINT#1024;
			END_VAR
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STNumericLiteral, STCoreValidator.INVALID_NUMERIC_LITERAL)
		'''
			FUNCTION hubert
			VAR
				int1 : USINT := USINT#-1;
			END_VAR
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STNumericLiteral, STCoreValidator.INVALID_NUMERIC_LITERAL)
	}

	@Test
	def void testNumericLiteralImplicitConversion() {
		val func1 = '''
			FUNCTION hubert
			VAR
				bool1 : REAL := 17;
			END_VAR
			END_FUNCTION
		'''.parse
		func1.assertNoErrors
		func1.assertWarning(STCorePackage.eINSTANCE.STNumericLiteral, STCoreValidator.LITERAL_IMPLICIT_CONVERSION)
		val func2 = '''
			FUNCTION hubert
			VAR
				bool1 : INT := SINT#17;
			END_VAR
			END_FUNCTION
		'''.parse
		func2.assertNoErrors
		func2.assertWarning(STCorePackage.eINSTANCE.STNumericLiteral, STCoreValidator.LITERAL_IMPLICIT_CONVERSION)
		val func3 = '''
			FUNCTION hubert
			VAR
				bool1 : INT := 17.0;
			END_VAR
			END_FUNCTION
		'''.parse
		func3.assertNoIssue(STCorePackage.eINSTANCE.STNumericLiteral, STCoreValidator.LITERAL_IMPLICIT_CONVERSION)
	}

	@Test
	def void testInvalidStringLiteral() {
		'''
			FUNCTION hubert
			VAR
				str : CHAR := CHAR#'abc';
			END_VAR
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STStringLiteral, STCoreValidator.INVALID_STRING_LITERAL)
		'''
			FUNCTION hubert
			VAR
				str : CHAR := CHAR#"a";
			END_VAR
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STStringLiteral, STCoreValidator.INVALID_STRING_LITERAL)
		'''
			FUNCTION hubert
			VAR
				str : STRING := STRING#"abc";
			END_VAR
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STStringLiteral, STCoreValidator.INVALID_STRING_LITERAL)
		'''
			FUNCTION hubert
			VAR
				str : WCHAR := WCHAR#"abc";
			END_VAR
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STStringLiteral, STCoreValidator.INVALID_STRING_LITERAL)
		'''
			FUNCTION hubert
			VAR
				str : WCHAR := WCHAR#'a';
			END_VAR
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STStringLiteral, STCoreValidator.INVALID_STRING_LITERAL)
		'''
			FUNCTION hubert
			VAR
				str : WSTRING := WSTRING#'abc';
			END_VAR
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STStringLiteral, STCoreValidator.INVALID_STRING_LITERAL)
	}

	@Test
	def void testStringLiteralImplicitConversion() {
		val func1 = '''
			FUNCTION hubert
			VAR
				str : STRING := CHAR#'a';
			END_VAR
			END_FUNCTION
		'''.parse
		func1.assertNoErrors
		func1.assertWarning(STCorePackage.eINSTANCE.STStringLiteral, STCoreValidator.LITERAL_IMPLICIT_CONVERSION)
		val func2 = '''
			FUNCTION hubert
			VAR
				str : WSTRING := WCHAR#"a";
			END_VAR
			END_FUNCTION
		'''.parse
		func2.assertNoErrors
		func2.assertWarning(STCorePackage.eINSTANCE.STStringLiteral, STCoreValidator.LITERAL_IMPLICIT_CONVERSION)
		val func3 = '''
			FUNCTION hubert
			VAR
				str : INT := 'a';
			END_VAR
			END_FUNCTION
		'''.parse
		func3.assertNoIssue(STCorePackage.eINSTANCE.STStringLiteral, STCoreValidator.LITERAL_IMPLICIT_CONVERSION)
	}

	@Test
	def void testStringLiteralTruncated() {
		val func1 = '''
			FUNCTION hubert
			VAR
				str : STRING[5] := '4diac IDE';
			END_VAR
			END_FUNCTION
		'''.parse
		func1.assertNoErrors
		func1.assertWarning(STCorePackage.eINSTANCE.STStringLiteral, STCoreValidator.TRUNCATED_LITERAL)
		val func2 = '''
			FUNCTION hubert
			VAR
				str : WSTRING[5] := "4diac IDE";
			END_VAR
			END_FUNCTION
		'''.parse
		func2.assertNoErrors
		func2.assertWarning(STCorePackage.eINSTANCE.STStringLiteral, STCoreValidator.TRUNCATED_LITERAL)
		val func3 = '''
			FUNCTION hubert
			VAR
				str1 : STRING := "4diac IDE";
				str2 : STRING[5] := "4diac";
				str3 : WSTRING := "4diac IDE";
				str4 : WSTRING[5] := "4diac";
			END_VAR
			END_FUNCTION
		'''.parse
		func3.assertNoIssue(STCorePackage.eINSTANCE.STStringLiteral, STCoreValidator.TRUNCATED_LITERAL)
	}

	@Test
	def void testReservedIdentifierErrorValidator() {
		'''
		FUNCTION hubert
		VAR
		    add : BOOL;
		END_VAR
		END_FUNCTION'''.parse.assertError(STCorePackage.eINSTANCE.STVarDeclaration,
			STCoreValidator.RESERVED_IDENTIFIER_ERROR)
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
		END_FUNCTION'''.parse.assertError(STCorePackage.eINSTANCE.STFeatureExpression,
			STCoreValidator.VALUE_NOT_ASSIGNABLE)
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
		END_FUNCTION'''.parse.assertError(STCorePackage.eINSTANCE.STFeatureExpression,
			STCoreValidator.VALUE_NOT_ASSIGNABLE)
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

	@Test
	def void testCallStandardFunctionFormalArguments() {
		'''
		FUNCTION hubert
		ADD(IN0 := 17, IN1 := 4);
		END_FUNCTION'''.parse.assertError(STCorePackage.eINSTANCE.STFeatureExpression,
			STCoreValidator.STANDARD_FUNCTION_WITH_FORMAL_ARGUMENTS)
	}

	@ParameterizedTest(name="{index}: {0} with {1}")
	@MethodSource("typeUnaryOperatorArgumentsCartesianProvider")
	def void testUnaryOperatorNotApplicableErrorValidator(String operatorName, String typeName) {
		val operator = STUnaryOperator.getByName(operatorName)
		val type = ElementaryTypes.getTypeByName(typeName)
		val result = '''
		FUNCTION hubert
		VAR
		    var1 : «type.name»;
		END_VAR
		var1 := «operator.literal» var1;
		END_FUNCTION'''.parse
		if (STCoreUtil.isApplicableTo(operator, type))
			result.assertNoErrors
		else
			result.assertError(STCorePackage.eINSTANCE.STUnaryExpression, STCoreValidator.OPERATOR_NOT_APPLICABLE)
	}

	@ParameterizedTest(name="{index}: {0} with {1} and {2}")
	@MethodSource("typeBinaryOperatorArgumentsCartesianProvider")
	def void testBinaryOperatorNotApplicableErrorValidator(String operatorName, String leftTypeName,
		String rightTypeName) {
		val operator = STBinaryOperator.getByName(operatorName)
		val leftType = ElementaryTypes.getTypeByName(leftTypeName)
		val rightType = ElementaryTypes.getTypeByName(rightTypeName)
		val result = '''
		FUNCTION hubert
		VAR
		    var1 : «leftType.name»;
		    var2 : «rightType.name»;
		    var3 : BOOL;
		END_VAR
		var3 := (var1 «operator.literal» var2) = (var1 «operator.literal» var2);
		END_FUNCTION'''.parse
		if (STCoreUtil.isApplicableTo(operator, leftType, rightType))
			result.assertNoErrors
		else
			result.assertError(STCorePackage.eINSTANCE.STBinaryExpression, STCoreValidator.OPERATOR_NOT_APPLICABLE)
	}

	@ParameterizedTest(name="{index}: {0} with {1}")
	@MethodSource("typeUnaryOperatorApplicableArgumentsCartesianProvider")
	def void testUnaryOperatorResultType(String operatorName, String typeName) {
		val operator = STUnaryOperator.getByName(operatorName)
		val type = ElementaryTypes.getTypeByName(typeName)
		val result = '''
		FUNCTION hubert
		VAR
		    var1 : «type.name»;
		END_VAR
		«operator.literal» var1;
		END_FUNCTION'''.parse
		val expression = result.functions.head.code.head as STUnaryExpression
		assertNotNull(expression.resultType, "invalid result type from applicable operator")
	}

	@ParameterizedTest(name="{index}: {0} with {1} and {2}")
	@MethodSource("typeBinaryOperatorApplicableArgumentsCartesianProvider")
	def void testBinaryOperatorResultType(String operatorName, String leftTypeName, String rightTypeName) {
		val operator = STBinaryOperator.getByName(operatorName)
		val leftType = ElementaryTypes.getTypeByName(leftTypeName)
		val rightType = ElementaryTypes.getTypeByName(rightTypeName)
		val result = '''
		FUNCTION hubert
		VAR
		    var1 : «leftType.name»;
		    var2 : «rightType.name»;
		END_VAR
		var1 «operator.literal» var2;
		END_FUNCTION'''.parse
		val expression = result.functions.head.code.head as STBinaryExpression
		assertNotNull(expression.resultType, "invalid result type from applicable operator")
	}

	def static Stream<Arguments> typeUnaryOperatorArgumentsCartesianProvider() {
		DataTypeLibrary.nonUserDefinedDataTypes.stream.flatMap [ type |
			STUnaryOperator.VALUES.stream.map [ op |
				arguments(op.getName, type.name)
			]
		]
	}

	def static Stream<Arguments> typeBinaryOperatorArgumentsCartesianProvider() {
		DataTypeLibrary.nonUserDefinedDataTypes.stream.flatMap [ first |
			DataTypeLibrary.nonUserDefinedDataTypes.stream.flatMap [ second |
				STBinaryOperator.VALUES.stream.map [ op |
					arguments(op.getName, first.name, second.name)
				]
			]
		]
	}

	def static Stream<Arguments> typeUnaryOperatorApplicableArgumentsCartesianProvider() {
		DataTypeLibrary.nonUserDefinedDataTypes.stream.flatMap [ type |
			STUnaryOperator.VALUES.stream.filter[op|STCoreUtil.isApplicableTo(op, type)].map [ op |
				arguments(op.getName, type.name)
			]
		]
	}

	def static Stream<Arguments> typeBinaryOperatorApplicableArgumentsCartesianProvider() {
		DataTypeLibrary.nonUserDefinedDataTypes.stream.flatMap [ first |
			DataTypeLibrary.nonUserDefinedDataTypes.stream.flatMap [ second |
				STBinaryOperator.VALUES.stream.filter[op|STCoreUtil.isApplicableTo(op, first, second)].map [ op |
					arguments(op.getName, first.name, second.name)
				]
			]
		]
	}

	@Test
	def void checkReturnValueAssignmentIsValidToContainingName() {
		'''
			FUNCTION other : BOOL
			END_FUNCTION
			FUNCTION current : BOOL
				current := false;
			END_FUNCTION
		'''.parse.assertNoErrors
	}

	@Test
	def void checkReturnValueAssignmentIsInvalidToNotContainingName() {
		'''
			FUNCTION other : BOOL
			END_FUNCTION
			FUNCTION current : BOOL
				other := false;
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STFeatureExpression, STCoreValidator.ICALLABLE_NOT_VISIBLE,
			"Name 'other' not visible in this context")
	}

	@Test
	def void checkCallableNameIsValidCallableInNotContainingName() {
		'''
			FUNCTION other : BOOL
			END_FUNCTION
			FUNCTION current : BOOL
				current := other();
			END_FUNCTION
		'''.parse.assertNoErrors
	}

	@Test
	def void checkCallableNameIsNotAValidVariableInNotContainingName() {
		'''
			FUNCTION other : BOOL
			END_FUNCTION
			FUNCTION current : BOOL
				current := other;
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STFeatureExpression, STCoreValidator.ICALLABLE_NOT_VISIBLE,
			"Name 'other' not visible in this context")
		'''
			FUNCTION other : BYTE
			END_FUNCTION
			FUNCTION current : BYTE
				other.%X0 := current;
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STFeatureExpression, STCoreValidator.ICALLABLE_NOT_VISIBLE,
			"Name 'other' not visible in this context")
		'''
			FUNCTION other : BYTE
			END_FUNCTION
			FUNCTION current : BOOL
				current := other.0;
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STFeatureExpression, STCoreValidator.ICALLABLE_NOT_VISIBLE,
			"Name 'other' not visible in this context")
	}

	@Test
	def void checkCallableNameAssignmentInvalidIfNoReturnType() {
		'''
			FUNCTION current
				current := 1;
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STFeatureExpression, STCoreValidator.ICALLABLE_HAS_NO_RETURN_TYPE,
			"Callable 'current' has return type VOID and cannot be used in assignments")
	}

	@Test
	def void testDuplicateFunctionNameIsForbidden() {
		'''
			FUNCTION duplicate
			END_FUNCTION
			FUNCTION duplicate
			END_FUNCTION
		'''.parse.assertError(STFunctionPackage.eINSTANCE.STFunction, STFunctionValidator.DUPLICATE_FUNCTION_NAME,
			"FUNCTION with duplicate name 'duplicate' found in null")
	}

	@Test
	def void testDuplicateVariableNameIsForbiddenInFunction_0() {
		'''
			FUNCTION hubert
			VAR_INPUT
				bol1 : BOOL;
				bol1 : BOOL;
				bol1 : BOOL;
			END_VAR
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STVarDeclaration, STCoreValidator.DUPLICATE_VARIABLE_NAME,
			"Variable with duplicate name bol1")
	}

	@Test
	def void testDuplicateVariableNameIsForbiddenInFunction_1() {
		'''
			FUNCTION hubert
			VAR_INPUT
				bol1 : BOOL;
			END_VAR
			VAR_INPUT
				bol1 : BOOL;
			END_VAR
			VAR_TEMP
				bol1 : BOOL;
			END_VAR
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STVarDeclaration, STCoreValidator.DUPLICATE_VARIABLE_NAME,
			"Variable with duplicate name bol1")
	}

	@Test
	def void testAnyIntRangesAreValid() {
		'''
			FUNCTION ArrayTestDeclarationTest
			VAR
				arrayTest : ARRAY [-1 .. 65535] OF REAL;
			END_VAR
			END_FUNCTION
		'''.parse.assertNoErrors
	}

	@Test
	def void testInvalidRangeExpression() {
		'''
			FUNCTION ArrayTestDeclarationTest
			VAR
				arrayTest : ARRAY [2] OF REAL;
			END_VAR
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STNumericLiteral, STCoreValidator.INDEX_RANGE_EXPRESSION_INVALID)
	}

	def static Stream<Arguments> invalidArrayRangeOrMaxLengthArgument() {
		return Stream.of(Arguments.of("REAL#1.0", "REAL"), Arguments.of("LREAL#1.0", "LREAL"),
			Arguments.of("\"3\"", "WCHAR"), Arguments.of("'5'", "CHAR"), Arguments.of("WSTRING#\"4\"", "WSTRING"),
			Arguments.of("STRING#'6'", "STRING"), Arguments.of("T#4h", "TIME"), Arguments.of("TOD#12:00:00", "TOD"),
			Arguments.of("DATE#20-03-2017", "DATE"), Arguments.of("DT#20-03-2017-16:48:00", "DT"),
			Arguments.of("LT#4h", "LTIME"), Arguments.of("LTOD#12:00:00", "LTOD"),
			Arguments.of("LDATE#20-03-2017", "LDATE"), Arguments.of("LDT#20-03-2017-16:48:00", "LDT"))
	}

	@ParameterizedTest(name="{index}: argument {0}")
	@MethodSource("invalidArrayRangeOrMaxLengthArgument")
	def void testNonAnyIntRangesAreInvalid(String argument, String argumentTypeName) {
		'''
			FUNCTION ArrayTestDeclarationTest
			VAR
				arrayTest : ARRAY [«argument» .. 65535] OF REAL;
			END_VAR
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STBinaryExpression, STCoreValidator.
			INDEX_RANGE_TYPE_INVALID, '''Type «argumentTypeName» is not valid for defining ranges. Ranges must be of type ANY_INT''')
	}

	def static Stream<Arguments> validTypesForMaxLengthSpecifier() {
		DataTypeLibrary.nonUserDefinedDataTypes.stream.filter[(it instanceof AnyStringType)].map[arguments(it.name)]
	}

	@ParameterizedTest(name="{index}: argument {0}")
	@MethodSource("validTypesForMaxLengthSpecifier")
	def void testTypesValidForMaxLength(String typeName) {
		'''
			FUNCTION ArrayTestDeclarationTest
			VAR
				testVar : «typeName» [5];
			END_VAR
			END_FUNCTION
		'''.parse.assertNoErrors
	}

	def static Stream<Arguments> invalidTypesForMaxLengthSpecifier() {
		DataTypeLibrary.nonUserDefinedDataTypes.stream.filter[!(it instanceof AnyStringType)].map[arguments(it.name)]
	}

	@ParameterizedTest(name="{index}: argument {0}")
	@MethodSource("invalidTypesForMaxLengthSpecifier")
	def void testTypesInvalidForMaxLength(String typeName) {
		'''
			FUNCTION ArrayTestDeclarationTest
			VAR
				testVar : «typeName» [5];
			END_VAR
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STVarDeclaration, STCoreValidator.MAX_LENGTH_NOT_ALLOWED,
			"For types not of ANY_STRING no maximum length may be defined")
	}

	@ParameterizedTest(name="{index}: argument {0}")
	@MethodSource("invalidArrayRangeOrMaxLengthArgument")
	def void testInvalidMaxLengthTypes(String argument, String argumentTypeName) {
		'''
			FUNCTION ArrayTestDeclarationTest
			VAR
				testVar : STRING[«argument»];
			END_VAR
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STVarDeclaration, STCoreValidator.
			MAX_LENGTH_TYPE_INVALID, '''Type «argumentTypeName» is not valid to specify an ANY_STRING max length. Max length must be of type ANY_INT''')
	}

	@Test
	def void testValidArrayAccess() {
		'''
			FUNCTION ArrayTestDeclarationTest
			VAR
				arrayTest : ARRAY [0 .. 10] OF REAL;
			END_VAR
			arrayTest[0] := arrayTest[1];
			END_FUNCTION
		'''.parse.assertNoErrors
	}

	@Test
	def void testValidStringAccess() {
		'''
			FUNCTION ArrayTestDeclarationTest
				VAR
					stringTest : STRING;
					wstringTest : STRING;
				END_VAR
			stringTest[1] := stringTest[1];
			wstringTest[1] := wstringTest[1];
			END_FUNCTION
		'''.parse.assertNoErrors
	}

	@Test
	def void testValidStringReturnVariableAccess() {
		'''
			FUNCTION ArrayTestDeclarationTest : STRING
			ArrayTestDeclarationTest[1] := 'I';
			END_FUNCTION
		'''.parse.assertNoErrors
		'''
			FUNCTION ArrayTestDeclarationTest : WSTRING
			ArrayTestDeclarationTest[1] := "I";
			END_FUNCTION
		'''.parse.assertNoErrors
	}

	@Test
	def void testInvalidReceiverArrayAccess() {
		'''
			FUNCTION ArrayTestDeclarationTest
			VAR
				intTest : INT;
			END_VAR
			intTest := intTest[1];
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STArrayAccessExpression,
			STCoreValidator.ARRAY_ACCESS_RECEIVER_INVALID)
	}

	@Test
	def void testInvalidIndexTypeArrayAccess() {
		'''
			FUNCTION ArrayTestDeclarationTest
			VAR
				arrayTest : ARRAY [0 .. 10] OF REAL;
			END_VAR
			arrayTest[0] := arrayTest['abc'];
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STArrayAccessExpression, STCoreValidator.NON_COMPATIBLE_TYPES,
			"Cannot convert from STRING to ANY_INT")
	}

	@Test
	def void testOutOfBoundsArrayAccess() {
		'''
			FUNCTION ArrayTestDeclarationTest
			VAR
				arrayTest : ARRAY [0 .. 10] OF REAL;
			END_VAR
			arrayTest[0] := arrayTest[-1];
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STArrayAccessExpression,
			STCoreValidator.ARRAY_INDEX_OUT_OF_BOUNDS, "Index -1 out of array dimension bounds 0..10")
		'''
			FUNCTION ArrayTestDeclarationTest
			VAR
				arrayTest : ARRAY [0 .. 10] OF REAL;
			END_VAR
			arrayTest[0] := arrayTest[11];
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STArrayAccessExpression,
			STCoreValidator.ARRAY_INDEX_OUT_OF_BOUNDS, "Index 11 out of array dimension bounds 0..10")
	}

	@Test
	def void testOutOfBoundsStringAccess() {
		'''
			FUNCTION ArrayTestDeclarationTest
			VAR
				stringTest : STRING;
			END_VAR
			stringTest[1] := stringTest[0];
			END_FUNCTION
		'''.parse.assertWarning(STCorePackage.eINSTANCE.STArrayAccessExpression,
			STCoreValidator.STRING_INDEX_OUT_OF_BOUNDS, "Index 0 out of bounds for STRING")
		'''
			FUNCTION ArrayTestDeclarationTest
			VAR
				stringTest : STRING[10];
			END_VAR
			stringTest[1] := stringTest[11];
			END_FUNCTION
		'''.parse.assertWarning(STCorePackage.eINSTANCE.STArrayAccessExpression,
			STCoreValidator.STRING_INDEX_OUT_OF_BOUNDS, "Index 11 out of bounds for STRING[10]")
	}

	@Test
	def void testTooManyIndicesArrayAccess() {
		'''
			FUNCTION ArrayTestDeclarationTest
			VAR
				arrayTest : ARRAY [0 .. 10] OF REAL;
			END_VAR
			arrayTest[1] := arrayTest[1,1];
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STArrayAccessExpression, STCoreValidator.TOO_MANY_INDICES_GIVEN,
			"Too many indices in subscript access: 2 indices given, but only 1 allowed for ARRAY [0..10] OF REAL")
	}

	@Test
	def void testTooManyIndicesStringAccess() {
		'''
			FUNCTION ArrayTestDeclarationTest
			VAR
				stringTest : STRING;
			END_VAR
			stringTest[1] := stringTest[1,1];
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STArrayAccessExpression, STCoreValidator.TOO_MANY_INDICES_GIVEN,
			"Too many indices in subscript access: 2 indices given, but only 1 allowed for STRING")
	}

	@Test
	def void testFunctionWithReturnTypeAssginedToCorrectVariableType() {
		'''
			FUNCTION called : REAL
			END_FUNCTION
			
			FUNCTION callee
			VAR_TEMP
				assigned : REAL;
			END_VAR
			assigned := called();
			END_FUNCTION
		'''.parse.assertNoErrors
	}

	@Test
	def void testFunctionWithReturnTypeAssginedToWrongVariableType() {
		'''
			FUNCTION called : REAL
			END_FUNCTION
			
			FUNCTION callee
			VAR_TEMP
				assigned : STRING;
			END_VAR
			assigned := called();
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STAssignment, STCoreValidator.NON_COMPATIBLE_TYPES,
			"Cannot convert from REAL to STRING")
	}

	@Test
	def void testFunctionWithoutReturnTypeAssginedToVariable() {
		'''
			FUNCTION called
			END_FUNCTION
			
			FUNCTION callee
			VAR_TEMP
				assigned : STRING;
			END_VAR
			assigned := called();
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STFeatureExpression, STCoreValidator.RETURNED_TYPE_IS_VOID,
			"Call on 'called' returns VOID, which cannot be assigned to a variable or used as a call argument")
	}

	@Test
	def void testWriteOnInputIsNotAllowed() { // currently a warning
		'''
			FUNCTION test
			VAR_INPUT
				in1 : INT;
			END_VAR
			in1 := 2;
			END_FUNCTION
		'''.parse.assertWarning(STCorePackage.eINSTANCE.STAssignment, STCoreValidator.VALUE_NOT_ASSIGNABLE,
			"Inputs shall not be be assigned. This will be elevated to an error in the future")
	}

	@Test
	def void testWriteOnConstantVarTempIsNotAllowed() {
		'''
			FUNCTION test
			VAR_TEMP CONSTANT
				in1 : INT;
			END_VAR
			in1 := 2;
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STAssignment, STCoreValidator.VALUE_NOT_ASSIGNABLE,
			"Constants cannot be assigned.")
	}

	@Test
	def void testWriteOnConstantVarIsNotAllowed() { // same as VAR_TEMP in a function
		'''
			FUNCTION test
			VAR CONSTANT
				in1 : INT;
			END_VAR
			in1 := 2;
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STAssignment, STCoreValidator.VALUE_NOT_ASSIGNABLE,
			"Constants cannot be assigned.")
	}

	@Test
	def void testUnnecessaryConversions() {
		'''
			FUNCTION test
			VAR_TEMP
				INT_VAR: INT;
				DINT_VAR: DINT;
			END_VAR
			DINT_VAR := INT_TO_SINT(INT_VAR);
			END_FUNCTION
		'''.parse.assertWarning(STCorePackage.eINSTANCE.STFeatureExpression, STCoreValidator.UNNECESSARY_CONVERSION,
			"Unnecessary conversion from INT to SINT")
		'''
			FUNCTION test
			VAR_TEMP
				DINT_VAR: DINT;
				LINT_VAR: LINT;
			END_VAR
			DINT_VAR := LINT_TO_INT(LINT_VAR);
			END_FUNCTION
		'''.parse.assertWarning(STCorePackage.eINSTANCE.STFeatureExpression,
			STCoreValidator.UNNECESSARY_NARROW_CONVERSION, "Unnecessary narrow conversion to INT")
		'''
			FUNCTION test
			VAR_TEMP
				INT_VAR: INT;
				DINT_VAR: DINT;
			END_VAR
			INT_VAR := LINT_TO_INT(DINT_VAR);
			END_FUNCTION
		'''.parse.assertWarning(STCorePackage.eINSTANCE.STFeatureExpression,
			STCoreValidator.UNNECESSARY_WIDE_CONVERSION, "Unnecessary wide conversion from LINT")
		'''
			FUNCTION test
			VAR_TEMP
				INT_VAR: INT;
				INT_VAR2: INT;
				REAL_VAR: REAL;
			END_VAR
			REAL_VAR := INT_TO_REAL(INT_VAR) / INT_VAR2;
			END_FUNCTION
		'''.parse.assertNoIssues
	}

	@Test
	def void testUnnecessaryLiteralConversions() {
		'''
			FUNCTION test
			VAR_TEMP
				USINT_VAR: USINT;
			END_VAR
			USINT_VAR := INT_TO_USINT(INT#128);
			END_FUNCTION
		'''.parse.assertWarning(STCorePackage.eINSTANCE.STFeatureExpression,
			STCoreValidator.UNNECESSARY_LITERAL_CONVERSION, "Unnecessary conversion of literal to USINT")
		'''
			FUNCTION test
			VAR_TEMP
				INT_VAR: INT;
			END_VAR
			INT_VAR := UINT_TO_INT(UINT#16#800);
			END_FUNCTION
		'''.parse.assertWarning(STCorePackage.eINSTANCE.STFeatureExpression,
			STCoreValidator.UNNECESSARY_LITERAL_CONVERSION, "Unnecessary conversion of literal to INT")
		'''
			FUNCTION test
			VAR_TEMP
				BYTE_VAR: BYTE;
			END_VAR
			BYTE_VAR := WORD_TO_BYTE(WORD#16#80);
			END_FUNCTION
		'''.parse.assertWarning(STCorePackage.eINSTANCE.STFeatureExpression,
			STCoreValidator.UNNECESSARY_LITERAL_CONVERSION, "Unnecessary conversion of literal to BYTE")
		'''
			FUNCTION test
			VAR_TEMP
				CHAR_VAR: CHAR;
			END_VAR
			CHAR_VAR := STRING_TO_CHAR('a');
			END_FUNCTION
		'''.parse.assertWarning(STCorePackage.eINSTANCE.STFeatureExpression,
			STCoreValidator.UNNECESSARY_LITERAL_CONVERSION, "Unnecessary conversion of literal to CHAR")
	}


	@Test
	def void testTruncatingLiteralConversions() {
		'''
			FUNCTION test
			VAR_TEMP
				USINT_VAR: USINT;
			END_VAR
			USINT_VAR := INT_TO_USINT(INT#1024);
			END_FUNCTION
		'''.parse.assertWarning(STCorePackage.eINSTANCE.STFeatureExpression,
			STCoreValidator.TRUNCATING_LITERAL_CONVERSION, "Truncating conversion of literal to USINT")
		'''
			FUNCTION test
			VAR_TEMP
				INT_VAR: INT;
			END_VAR
			INT_VAR := UINT_TO_INT(UINT#16#8000);
			END_FUNCTION
		'''.parse.assertWarning(STCorePackage.eINSTANCE.STFeatureExpression,
			STCoreValidator.TRUNCATING_LITERAL_CONVERSION, "Truncating conversion of literal to INT")
		'''
			FUNCTION test
			VAR_TEMP
				BYTE_VAR: BYTE;
			END_VAR
			BYTE_VAR := WORD_TO_BYTE(WORD#16#8000);
			END_FUNCTION
		'''.parse.assertWarning(STCorePackage.eINSTANCE.STFeatureExpression,
			STCoreValidator.TRUNCATING_LITERAL_CONVERSION, "Truncating conversion of literal to BYTE")
		'''
			FUNCTION test
			VAR_TEMP
				CHAR_VAR: CHAR;
			END_VAR
			CHAR_VAR := STRING_TO_CHAR('abc');
			END_FUNCTION
		'''.parse.assertWarning(STCorePackage.eINSTANCE.STFeatureExpression,
			STCoreValidator.TRUNCATING_LITERAL_CONVERSION, "Truncating conversion of literal to CHAR")
	}

	@Test
	def void testDataTypeLinkingDiagnostic() {
		'''
			FUNCTION test
			VAR_TEMP
				in1 : FLOAT;
			END_VAR
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STVarDeclaration, Diagnostic.LINKING_DIAGNOSTIC,
			"The data type FLOAT is undefined")
	}

	@Test
	def void testReturnTypeLinkingDiagnostic() {
		'''
			FUNCTION test : FLOAT
			END_FUNCTION
		'''.parse.assertError(STFunctionPackage.eINSTANCE.STFunction, Diagnostic.LINKING_DIAGNOSTIC,
			"The data type FLOAT is undefined")
	}

	@Test
	def void testVariableLinkingDiagnostic() {
		'''
			FUNCTION test
			XY := 0;
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STFeatureExpression, Diagnostic.LINKING_DIAGNOSTIC,
			"The variable XY is undefined")
	}

	@Test
	def void testCallableLinkingDiagnostic() {
		'''
			FUNCTION test
			XY(0);
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STFeatureExpression, Diagnostic.LINKING_DIAGNOSTIC,
			"The callable XY(SINT) is undefined")
	}

	@Test
	def void testParameterLinkingDiagnostic() {
		'''
			FUNCTION test
			test2(IN2 := 0);
			END_FUNCTION
			
			FUNCTION test2
			VAR_INPUT
				IN1: DINT;
			END_VAR
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STCallNamedInputArgument, Diagnostic.LINKING_DIAGNOSTIC,
			"The parameter IN2 is undefined for the callable test2")
	}

	@Test
	def void testNonConstantExpressionInVariableDeclaration() {
		'''
			FUNCTION test
			VAR_TEMP
				in1 : INT;
				in2 : INT := in1;
			END_VAR
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STFeatureExpression, STCoreValidator.NON_CONSTANT_DECLARATION)
		'''
			FUNCTION test
			VAR_TEMP CONSTANT
				in1 : INT;
				in2 : INT := in1;
			END_VAR
			END_FUNCTION
		'''.parse.assertNoErrors
	}

	@Test
	def void testMaybeNotInitialized() {
		'''
			FUNCTION test
			VAR_TEMP CONSTANT
				in2 : INT := in1;
				in1 : INT;
			END_VAR
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STFeatureExpression, STCoreValidator.MAYBE_NOT_INITIALIZED)
		'''
			FUNCTION test
			VAR_TEMP CONSTANT
				in1 : INT;
				in2 : INT := in1;
			END_VAR
			END_FUNCTION
		'''.parse.assertNoErrors
	}

	@Test
	def void testExitInForIsOk() {
		'''
			FUNCTION test
			VAR_TEMP
				I : INT;
			END_VAR
			
			FOR I := 0 TO 10 DO
				EXIT;
			END_FOR;
			END_FUNCTION
		'''.parse.assertNoErrors
	}

	@Test
	def void testExitInWhileIsOk() {
		'''
			FUNCTION test
			VAR_TEMP
				I : INT;
			END_VAR
			
			WHILE I < 0 DO
				EXIT;
			END_WHILE;
			END_FUNCTION
		'''.parse.assertNoErrors
	}

	@Test
	def void testExitInRepeatIsOk() {
		'''
			FUNCTION test
			VAR_TEMP
				I : INT;
			END_VAR
			
			REPEAT
				EXIT;
			UNTIL I < 0
			END_REPEAT;
			END_FUNCTION
		'''.parse.assertNoErrors
	}

	@Test
	def void testExitNotInALoopIsAnError() {
		'''
			FUNCTION test
			EXIT;
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STExit, STCoreValidator.EXIT_NOT_IN_LOOP,
			"EXIT statement is only valid inside a loop statement (FOR/WHILE/REPEAT)")
	}

	@Test
	def void testContinueNotInALoopIsAnError() {
		'''
			FUNCTION test
			CONTINUE;
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STContinue, STCoreValidator.CONTINUE_NOT_IN_LOOP,
			"CONTINUE statement is only valid inside a loop statement (FOR/WHILE/REPEAT)")
	}

}
