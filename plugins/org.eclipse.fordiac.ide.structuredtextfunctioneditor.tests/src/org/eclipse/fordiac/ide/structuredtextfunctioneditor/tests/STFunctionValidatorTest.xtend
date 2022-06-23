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
 *   Martin Melik Merkumians
 * 		- validation for duplicate names on FUNCTIONs
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.tests

import com.google.inject.Inject
import java.util.stream.Stream
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryOperator
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STUnaryOperator
import org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil
import org.eclipse.fordiac.ide.structuredtextcore.validation.STCoreValidator
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionPackage
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.validation.STFunctionValidator
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
	def void testInvalidInitializer() {
		'''
			FUNCTION hubert
			VAR
				int1 : INT := LINT#17;
			END_VAR
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STVarDeclaration, STCoreValidator.NON_COMPATIBLE_TYPES)

	}

	def void testInvalidArrayInitializer() {
		'''
			FUNCTION hubert
			VAR
				testArray: ARRAY [ 0 .. 3 ] OF INT := [LINT#17, 4];
			END_VAR
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STVarDeclaration, STCoreValidator.NON_COMPATIBLE_TYPES)
	}

	def void testInvalidArrayDimensionsInitializer() {
		'''
			FUNCTION hubert
			VAR
				testArray: ARRAY [ 0 .. 3 ] OF INT := [[17, 4]];
			END_VAR
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STVarDeclaration, STCoreValidator.NON_COMPATIBLE_TYPES)
		'''
			FUNCTION hubert
			VAR
				testArray: ARRAY [ 0 .. 3, 0 .. 2 ] OF INT := [17, 4, 21];
			END_VAR
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STVarDeclaration, STCoreValidator.NON_COMPATIBLE_TYPES)
		'''
			FUNCTION hubert
			VAR
				testArray: ARRAY [ 0 .. 3, 0 .. 2 ] OF INT := [[17, 4], 21];
			END_VAR
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STVarDeclaration, STCoreValidator.NON_COMPATIBLE_TYPES)
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
		'''.parse.assertError(STCorePackage.eINSTANCE.STIfStatement, STCoreValidator.NO_CAST_AVAILABLE)
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
		'''.parse.assertError(STCorePackage.eINSTANCE.STWhileStatement, STCoreValidator.NO_CAST_AVAILABLE)
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
		'''.parse.assertError(STCorePackage.eINSTANCE.STRepeatStatement, STCoreValidator.NO_CAST_AVAILABLE)
	}

	@Test
	def void testInvalidForTypes() {
		'''
			FUNCTION hubert
			VAR
				int1 : STRING;
			END_VAR
			
			FOR int1 := 4 TO 17 DO
				int1 := 17;
			END_FOR;
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STForStatement, STCoreValidator.FOR_VARIABLE_NOT_INTEGRAL_TYPE)
		'''
			FUNCTION hubert
			VAR
				int1 : INT;
			END_VAR
			
			FOR int1 := LINT#4 TO 17 DO
				int1 := 17;
			END_FOR;
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STForStatement, STCoreValidator.NON_COMPATIBLE_TYPES)
		'''
			FUNCTION hubert
			VAR
				int1 : INT;
			END_VAR
			
			FOR int1 := 4 TO LINT#17 DO
				int1 := 17;
			END_FOR;
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STForStatement, STCoreValidator.NON_COMPATIBLE_TYPES)
		'''
			FUNCTION hubert
			VAR
				int1 : INT;
			END_VAR
			
			FOR int1 := 4 TO 17 BY LINT#2 DO
				int1 := 17;
			END_FOR;
			END_FUNCTION
		'''.parse.assertNoErrors
		'''
			FUNCTION hubert
			VAR
				int1 : INT;
			END_VAR
			
			FOR int1 := 4 TO 17 BY "2" DO
				int1 := 17;
			END_FOR;
			END_FUNCTION
		'''.parse.assertError(STCorePackage.eINSTANCE.STForStatement, STCoreValidator.NO_CAST_AVAILABLE)
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
		var3 := (var1 «operator.literal» var2) = var1;
		END_FUNCTION'''.parse
		if (STCoreUtil.isApplicableTo(operator, leftType, rightType))
			result.assertNoErrors
		else
			result.assertError(STCorePackage.eINSTANCE.STBinaryExpression, STCoreValidator.OPERATOR_NOT_APPLICABLE)
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
			"Callable 'current' has no return type")
	}

	@Test
	def void testDuplicateFunctionmNameIsForbidden() {
		'''
			FUNCTION duplicate
			END_FUNCTION
			FUNCTION duplicate
			END_FUNCTION
		'''.parse.assertError(STFunctionPackage.eINSTANCE.STFunction, STFunctionValidator.DUPLICATE_FUNCTION_NAME,
			"FUNCTION with duplicate name 'duplicate' found in null")
	}
}
