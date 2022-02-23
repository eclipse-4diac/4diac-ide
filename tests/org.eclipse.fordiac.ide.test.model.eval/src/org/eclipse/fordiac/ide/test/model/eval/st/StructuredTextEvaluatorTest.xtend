/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.test.model.eval.st

import java.math.BigDecimal
import java.util.Collection
import java.util.Queue
import java.util.concurrent.ArrayBlockingQueue
import java.util.stream.Stream
import java.util.stream.StreamSupport
import org.eclipse.fordiac.ide.model.data.AnyIntType
import org.eclipse.fordiac.ide.model.data.AnyRealType
import org.eclipse.fordiac.ide.model.data.AnyUnsignedType
import org.eclipse.fordiac.ide.model.data.BoolType
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes
import org.eclipse.fordiac.ide.model.eval.Evaluator
import org.eclipse.fordiac.ide.model.eval.st.StructuredTextEvaluator
import org.eclipse.fordiac.ide.model.eval.value.BoolValue
import org.eclipse.fordiac.ide.model.eval.variable.Variable
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary
import org.eclipse.fordiac.ide.structuredtextalgorithm.STAlgorithmStandaloneSetup
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmBody
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryOperator
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STContinue
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExit
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STNumericLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STReturn
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STUnaryOperator
import org.eclipse.xtend.lib.annotations.Accessors
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

import static org.junit.jupiter.params.provider.Arguments.*

import static extension org.eclipse.fordiac.ide.model.eval.value.BoolValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.DIntValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.IntValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.LIntValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.LRealValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.RealValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.SIntValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.UDIntValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.UIntValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.ULIntValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.USIntValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.ValueOperations.*
import static extension org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil.*
import static extension org.junit.jupiter.api.Assertions.*

class StructuredTextEvaluatorTest {

	@BeforeAll
	def static void setupXtext() {
		STAlgorithmStandaloneSetup.doSetup
	}

	@Test
	def void testExpressionParseError() {
		Exception.assertThrows["-".evaluateExpression]
	}

	@Test
	def void testNumericLiterals() {
		// BOOL
		true.toBoolValue.assertEquals("BOOL#TRUE".evaluateExpression)
		false.toBoolValue.assertEquals("BOOL#FALSE".evaluateExpression)
		true.toBoolValue.assertEquals("BOOL#1".evaluateExpression)
		false.toBoolValue.assertEquals("BOOL#0".evaluateExpression)
		// SINT
		0.toSIntValue.assertEquals("SINT#0".evaluateExpression)
		17.toSIntValue.assertEquals("SINT#17".evaluateExpression)
		17.toSIntValue.assertEquals("SINT#16#11".evaluateExpression)
		(-4).toSIntValue.assertEquals("SINT#-4".evaluateExpression)
		(-4).toSIntValue.assertEquals("SINT#16#fc".evaluateExpression)
		Byte.MIN_VALUE.toSIntValue.assertEquals("SINT#-128".evaluateExpression)
		Byte.MAX_VALUE.toSIntValue.assertEquals("SINT#127".evaluateExpression)
		Byte.MIN_VALUE.toSIntValue.assertEquals("SINT#16#80".evaluateExpression)
		Byte.MAX_VALUE.toSIntValue.assertEquals("SINT#16#7f".evaluateExpression)
		// INT
		0.toIntValue.assertEquals("INT#0".evaluateExpression)
		17.toIntValue.assertEquals("INT#17".evaluateExpression)
		17.toIntValue.assertEquals("INT#16#11".evaluateExpression)
		(-4).toIntValue.assertEquals("INT#-4".evaluateExpression)
		(-4).toIntValue.assertEquals("INT#16#fffc".evaluateExpression)
		Short.MIN_VALUE.toIntValue.assertEquals("INT#-32768".evaluateExpression)
		Short.MAX_VALUE.toIntValue.assertEquals("INT#32767".evaluateExpression)
		Short.MIN_VALUE.toIntValue.assertEquals("INT#16#8000".evaluateExpression)
		Short.MAX_VALUE.toIntValue.assertEquals("INT#16#7fff".evaluateExpression)
		// DINT
		0.toDIntValue.assertEquals("DINT#0".evaluateExpression)
		17.toDIntValue.assertEquals("DINT#17".evaluateExpression)
		17.toDIntValue.assertEquals("DINT#16#11".evaluateExpression)
		(-4).toDIntValue.assertEquals("DINT#-4".evaluateExpression)
		(-4).toDIntValue.assertEquals("DINT#16#fffffffc".evaluateExpression)
		Integer.MIN_VALUE.toDIntValue.assertEquals("DINT#-2147483648".evaluateExpression)
		Integer.MAX_VALUE.toDIntValue.assertEquals("DINT#2147483647".evaluateExpression)
		Integer.MIN_VALUE.toDIntValue.assertEquals("DINT#16#80000000".evaluateExpression)
		Integer.MAX_VALUE.toDIntValue.assertEquals("DINT#16#7fffffff".evaluateExpression)
		// LINT
		0.toLIntValue.assertEquals("LINT#0".evaluateExpression)
		17.toLIntValue.assertEquals("LINT#17".evaluateExpression)
		17.toLIntValue.assertEquals("LINT#16#11".evaluateExpression)
		(-4).toLIntValue.assertEquals("LINT#-4".evaluateExpression)
		(-4).toLIntValue.assertEquals("LINT#16#fffffffffffffffc".evaluateExpression)
		Long.MIN_VALUE.toLIntValue.assertEquals("LINT#-9223372036854775808".evaluateExpression)
		Long.MAX_VALUE.toLIntValue.assertEquals("LINT#9223372036854775807".evaluateExpression)
		Long.MIN_VALUE.toLIntValue.assertEquals("LINT#16#8000000000000000".evaluateExpression)
		Long.MAX_VALUE.toLIntValue.assertEquals("LINT#16#7fffffffffffffff".evaluateExpression)
		// USINT
		0.toUSIntValue.assertEquals("USINT#0".evaluateExpression)
		17.toUSIntValue.assertEquals("USINT#17".evaluateExpression)
		17.toUSIntValue.assertEquals("USINT#16#11".evaluateExpression)
		255.toUSIntValue.assertEquals("USINT#255".evaluateExpression)
		255.toUSIntValue.assertEquals("USINT#16#ff".evaluateExpression)
		// UINT
		0.toUIntValue.assertEquals("UINT#0".evaluateExpression)
		17.toUIntValue.assertEquals("UINT#17".evaluateExpression)
		17.toUIntValue.assertEquals("UINT#16#11".evaluateExpression)
		(-4).toUIntValue.assertEquals("UINT#-4".evaluateExpression)
		(-4).toUIntValue.assertEquals("UINT#16#fffc".evaluateExpression)
		65535.toUIntValue.assertEquals("UINT#65535".evaluateExpression)
		65535.toUIntValue.assertEquals("UINT#16#ffff".evaluateExpression)
		// UDINT
		0.toUDIntValue.assertEquals("UDINT#0".evaluateExpression)
		17.toUDIntValue.assertEquals("UDINT#17".evaluateExpression)
		17.toUDIntValue.assertEquals("UDINT#16#11".evaluateExpression)
		(-4).toUDIntValue.assertEquals("UDINT#-4".evaluateExpression)
		(-4).toUDIntValue.assertEquals("UDINT#16#fffffffc".evaluateExpression)
		0xffffffff.toUDIntValue.assertEquals("UDINT#4294967295".evaluateExpression)
		0xffffffff.toUDIntValue.assertEquals("UDINT#16#ffffffff".evaluateExpression)
		// ULINT
		0.toULIntValue.assertEquals("ULINT#0".evaluateExpression)
		17.toULIntValue.assertEquals("ULINT#17".evaluateExpression)
		17.toULIntValue.assertEquals("ULINT#16#11".evaluateExpression)
		(-4).toULIntValue.assertEquals("ULINT#-4".evaluateExpression)
		(-4).toULIntValue.assertEquals("ULINT#16#fffffffffffffffc".evaluateExpression)
		0xffffffffffffffff#L.toULIntValue.assertEquals("ULINT#18446744073709551615".evaluateExpression)
		0xffffffffffffffff#L.toULIntValue.assertEquals("ULINT#16#ffffffffffffffff".evaluateExpression)
		// REAL
		0.toRealValue.assertEquals("REAL#0".evaluateExpression)
		17.toRealValue.assertEquals("REAL#17".evaluateExpression)
		(-4).toRealValue.assertEquals("REAL#-4".evaluateExpression)
		(3.1415).toRealValue.assertEquals("REAL#3.1415".evaluateExpression)
		// LREAL
		0.toLRealValue.assertEquals("LREAL#0".evaluateExpression)
		17.toLRealValue.assertEquals("LREAL#17".evaluateExpression)
		(-4).toLRealValue.assertEquals("LREAL#-4".evaluateExpression)
		(3.1415).toLRealValue.assertEquals("LREAL#3.1415".evaluateExpression)
	}

	@ParameterizedTest(name="{index}: {0} {1}#{2}")
	@MethodSource("testUnaryExpressionArgumentsProvider")
	def void testUnaryExpression(STUnaryOperator operator, String typeName, BigDecimal value) {
		val type = ElementaryTypes.getTypeByName(typeName)
		switch (operator) {
			case PLUS: value.abs
			case MINUS: value.negate
			case NOT: if(type instanceof BoolType) value.signum == 0 else value.toBigIntegerExact.not
		}.wrapValue(type).assertEquals('''«operator» «typeName»#«value»'''.evaluateExpression)
	}

	def static Stream<Arguments> testUnaryExpressionArgumentsProvider() {
		StreamSupport.stream(
			DataTypeLibrary.nonUserDefinedDataTypes.filter [
				GenericTypes.ANY_NUM.isCompatibleWith(it) || it instanceof BoolType
			].flatMap [ type |
				#["0", "1", "-1", "17", "-4"].reject [
					(type instanceof AnyUnsignedType && contains('-')) || (type instanceof BoolType && length > 1)
				].flatMap [ value |
					STUnaryOperator.VALUES.reject [
						(type instanceof AnyRealType && it == STUnaryOperator.NOT) ||
							(type instanceof BoolType && it != STUnaryOperator.NOT)
					].map [ operator |
						arguments(operator, type.name, value)
					]
				]
			].spliterator,
			false
		)
	}

	@Test
	def void testBinaryBooleanExpression() {
		false.toBoolValue.assertEquals("BOOL#FALSE AND BOOL#FALSE".evaluateExpression)
		false.toBoolValue.assertEquals("BOOL#TRUE AND BOOL#FALSE".evaluateExpression)
		false.toBoolValue.assertEquals("BOOL#FALSE AND BOOL#TRUE".evaluateExpression)
		false.toBoolValue.assertEquals("BOOL#FALSE AND BOOL#TRUE".evaluateExpression)
		true.toBoolValue.assertEquals("NOT BOOL#FALSE".evaluateExpression)
	}

	@ParameterizedTest(name="{index}: {1}#{2} {0} {1}#{3}")
	@MethodSource("testBinaryExpressionArgumentsProvider")
	def void testBinaryExpression(STBinaryOperator operator, String typeName, BigDecimal first, BigDecimal second) {
		val type = ElementaryTypes.getTypeByName(typeName)
		val resultType = if(operator.isComparison) ElementaryTypes.BOOL else type
		switch (operator) {
			case ADD:
				first + second
			case SUB:
				first - second
			case DIV:
				if (type instanceof AnyIntType)
					first.toBigIntegerExact / second.toBigIntegerExact
				else
					first / second
			case MOD:
				if (type instanceof AnyIntType)
					first.toBigIntegerExact.remainder(second.toBigIntegerExact)
				else
					first.remainder(second)
			case MUL:
				first * second
			case POWER:
				first ** second.intValue
			case AMPERSAND,
			case AND:
				first.longValueExact.bitwiseAnd(second.longValueExact)
			case OR:
				first.longValueExact.bitwiseOr(second.longValueExact)
			case XOR:
				first.longValueExact.bitwiseXor(second.longValueExact)
			case EQ:
				first == second
			case NE:
				first != second
			case GE:
				first >= second
			case GT:
				first > second
			case LE:
				first <= second
			case LT:
				first < second
			default:
				throw new UnsupportedOperationException
		}.wrapValue(resultType).assertEquals('''«typeName»#«first» «operator» «typeName»#«second»'''.evaluateExpression)
	}

	def static Stream<Arguments> testBinaryExpressionArgumentsProvider() {
		StreamSupport.stream(
			DataTypeLibrary.nonUserDefinedDataTypes.filter [
				GenericTypes.ANY_NUM.isCompatibleWith(it) || it instanceof BoolType
			].flatMap [ type |
				#["0", "1", "-1", "17", "-4"].reject [
					(type instanceof AnyUnsignedType && contains('-')) || (type instanceof BoolType && length > 1)
				].flatMap [ value |
					STBinaryOperator.VALUES.reject [
						it == STBinaryOperator.RANGE || (type instanceof AnyIntType && it == STBinaryOperator.POWER) ||
							(type instanceof AnyRealType && isLogical) || (type instanceof BoolType && !isLogical)
					].flatMap [ operator |
						#[
							arguments(operator, type.name, value, "1"),
							arguments(operator, type.name, value, if(type instanceof BoolType) "0" else "7")
						]
					]
				]
			].spliterator,
			false
		)
	}

	@Test
	def void testBinaryExpressionTypePromotion() {
		21.toSIntValue.assertEquals("SINT#17 + SINT#4".evaluateExpression) // identity
		21.toIntValue.assertEquals("SINT#17 + INT#4".evaluateExpression) // promotion
		21.toIntValue.assertEquals("INT#17 + SINT#4".evaluateExpression) // associative
		42.toIntValue.assertEquals("INT#17 + SINT#4 + SINT#21".evaluateExpression) // transitive
	}

	@Test
	def void testBinaryExpressionLogicalShortCircuit() {
		BoolValue.FALSE.assertEquals("BOOL#FALSE AND ((INT#0 / INT#0) = INT#0)".evaluateExpression)
		ArithmeticException.assertThrows["BOOL#TRUE AND ((INT#0 / INT#0) = INT#0)".evaluateExpression]
		BoolValue.TRUE.assertEquals("BOOL#TRUE OR ((INT#0 / INT#0) = INT#0)".evaluateExpression)
		ArithmeticException.assertThrows["BOOL#FALSE OR ((INT#0 / INT#0) = INT#0)".evaluateExpression]
	}

	@Test
	def void testVariable() {
		21.toIntValue.assertTrace(#[STAlgorithmBody, STBinaryExpression], '''
			VAR_TEMP
				test: INT := INT#17;
			END_VAR
			
			test := test + INT#4;
		'''.evaluateAlgorithm)
	}

	@Test
	def void testIfStatement() {
		21.toIntValue.assertTrace(#[STAlgorithmBody] + STBinaryExpression.repeat(2), '''
			VAR_TEMP
				test: INT := INT#17;
			END_VAR
			
			IF test = INT#17 THEN
				test := test + INT#4;
			END_IF;
		'''.evaluateAlgorithm)
	}

	@Test
	def void testIfWithElsifStatement() {
		21.toIntValue.assertTrace(#[STAlgorithmBody] + STBinaryExpression.repeat(3), '''
			VAR_TEMP
				test: INT := INT#17;
			END_VAR
			
			IF test = INT#4 THEN
				test := INT#0;
			ELSIF test = INT#17 THEN
				test := test + INT#4;
			END_IF;
		'''.evaluateAlgorithm)
	}

	@Test
	def void testIfWithElseStatement() {
		21.toIntValue.assertTrace(#[STAlgorithmBody] + STBinaryExpression.repeat(3), '''
			VAR_TEMP
				test: INT := INT#17;
			END_VAR
			
			IF test = INT#4 THEN
				test := INT#0;
			ELSIF test <> INT#17 THEN
				test := INT#4;
			ELSE
				test := test + INT#4;
			END_IF;
		'''.evaluateAlgorithm)
	}

	@Test
	def void testCaseStatement() {
		21.toIntValue.assertTrace(#[STAlgorithmBody, STFeatureExpression, STNumericLiteral, STBinaryExpression], '''
			VAR_TEMP
				test: INT := INT#17;
			END_VAR
			
			CASE test OF
				INT#17: test := test + INT#4;
			END_CASE;
		'''.evaluateAlgorithm)
	}

	@Test
	def void testCaseWithElseStatement() {
		21.toIntValue.assertTrace(#[STAlgorithmBody, STFeatureExpression] + STNumericLiteral.repeat(2) +
			#[STBinaryExpression], '''
			VAR_TEMP
				test: INT := INT#17;
			END_VAR
			
			CASE test OF
				INT#0: test := test + INT#4;
				INT#1: test := test + INT#4;
				ELSE test := test + INT#4;
			END_CASE;
		'''.evaluateAlgorithm)
	}

	@Test
	def void testForStatement() {
		21.toIntValue.assertTrace(#[STAlgorithmBody, STNumericLiteral] +
			#[STBinaryExpression, STNumericLiteral].repeat(4), '''
			VAR_TEMP
				test: INT := INT#17;
				i: INT;
			END_VAR
			
			FOR i := INT#0 TO INT#3 DO
				test := test + INT#1;
			END_FOR;
		'''.evaluateAlgorithm)
	}

	@Test
	def void testForWithContinueStatement() {
		21.toIntValue.assertTrace(#[STAlgorithmBody, STNumericLiteral] +
			#[STBinaryExpression, STContinue, STNumericLiteral, // if taken 
			STBinaryExpression, STBinaryExpression, STNumericLiteral].repeat(4), '''
			VAR_TEMP
				test: INT := INT#17;
				i: INT;
			END_VAR
			
			FOR i := INT#0 TO INT#7 DO
				IF i MOD INT#2 = INT#0 THEN
					CONTINUE;
				END_IF;
				test := test + INT#1;
			END_FOR;
		'''.evaluateAlgorithm)
	}

	@Test
	def void testForWithExitStatement() {
		21.toIntValue.assertTrace(
			#[STAlgorithmBody, STNumericLiteral] + (STBinaryExpression.repeat(2) + #[STNumericLiteral]).repeat(4) +
				#[STBinaryExpression, STExit, STBinaryExpression], '''
				VAR_TEMP
					test: INT := INT#16;
					i: INT;
				END_VAR
				
				FOR i := INT#0 TO INT#7 DO
					IF i = INT#4 THEN
						EXIT;
					END_IF;
					test := test + INT#1;
				END_FOR;
				test := test + INT#1;
			'''.evaluateAlgorithm)
	}

	@Test
	def void testForWithReturnStatement() {
		21.toIntValue.assertTrace(
			#[STAlgorithmBody, STNumericLiteral] + (STBinaryExpression.repeat(2) + #[STNumericLiteral]).repeat(4) +
				#[STBinaryExpression, STReturn], '''
				VAR_TEMP
					test: INT := INT#17;
					i: INT;
				END_VAR
				
				FOR i := INT#0 TO INT#7 DO
					IF i = INT#4 THEN
						RETURN;
					END_IF;
					test := test + INT#1;
				END_FOR;
			'''.evaluateAlgorithm)
	}

	@Test
	def void testNestedForStatement() {
		21.toIntValue.assertTrace(#[STAlgorithmBody, STNumericLiteral] +
			(#[STNumericLiteral] + #[STBinaryExpression, STNumericLiteral].repeat(2) + #[STNumericLiteral]).repeat(2),
			'''
				VAR_TEMP
					test: INT := INT#17;
					i: INT;
					j: INT;
				END_VAR
				
				FOR i := INT#0 TO INT#1 DO
					FOR j := INT#0 TO INT#1 DO
						test := test + INT#1;
					END_FOR;
				END_FOR;
			'''.evaluateAlgorithm)
	}

	@Test
	def void testNestedForWithContinueStatement() {
		21.toIntValue.assertTrace(#[STAlgorithmBody, STNumericLiteral] +
			(#[STNumericLiteral] + #[STBinaryExpression, STContinue, STNumericLiteral, // if taken 
			STBinaryExpression, STBinaryExpression, STNumericLiteral].repeat(4) + #[STNumericLiteral]).repeat(3), '''
			VAR_TEMP
				test: INT := INT#9;
				i: INT;
				j: INT;
			END_VAR
			
			FOR i := INT#0 TO INT#2 DO
				FOR j := INT#0 TO INT#7 DO
					IF j MOD INT#2 = INT#0 THEN
						CONTINUE;
					END_IF;
					test := test + INT#1;
				END_FOR;
			END_FOR;
		'''.evaluateAlgorithm)
	}

	@Test
	def void testNestedForWithExitStatement() {
		21.toIntValue.assertTrace(#[STAlgorithmBody, STNumericLiteral] +
			(#[STNumericLiteral] + (STBinaryExpression.repeat(2) + #[STNumericLiteral]).repeat(4) +
				#[STBinaryExpression, STExit, STBinaryExpression, STNumericLiteral]).repeat(3), '''
			VAR_TEMP
				test: INT := INT#6;
				i: INT;
				j: INT;
			END_VAR
			
			FOR i := INT#0 TO INT#2 DO
				FOR j := INT#0 TO INT#7 DO
					IF j = INT#4 THEN
						EXIT;
					END_IF;
					test := test + INT#1;
				END_FOR;
				test := test + INT#1;
			END_FOR;
		'''.evaluateAlgorithm)
	}

	@Test
	def void testNestedForWithReturnStatement() {
		21.toIntValue.assertTrace(
			#[STAlgorithmBody] + STNumericLiteral.repeat(2) +
				(STBinaryExpression.repeat(2) + #[STNumericLiteral]).repeat(4) + #[STBinaryExpression, STReturn], '''
				VAR_TEMP
					test: INT := INT#17;
					i: INT;
					j: INT;
				END_VAR
				
				FOR i := INT#0 TO INT#7 DO
					FOR j := INT#0 TO INT#7 DO
						IF j = INT#4 THEN
							RETURN;
						END_IF;
						test := test + INT#1;
					END_FOR;
				END_FOR;
			'''.evaluateAlgorithm)
	}

	@Test
	def void testForWithByStatement() {
		21.toIntValue.assertTrace(#[STAlgorithmBody, STNumericLiteral] +
			#[STBinaryExpression, STNumericLiteral].repeat(4), '''
			VAR_TEMP
				test: INT := INT#17;
				i: INT;
			END_VAR
			
			FOR i := INT#0 TO INT#7 BY INT#2 DO
				test := test + INT#1;
			END_FOR;
		'''.evaluateAlgorithm)
	}

	@Test
	def void testForWithNegativeByStatement() {
		21.toIntValue.assertTrace(#[STAlgorithmBody, STNumericLiteral] +
			#[STBinaryExpression, STNumericLiteral].repeat(4), '''
			VAR_TEMP
				test: INT := INT#17;
				i: INT;
			END_VAR
			
			FOR i := INT#3 TO INT#0 BY INT#-1 DO
				test := test + INT#1;
			END_FOR;
		'''.evaluateAlgorithm)
	}

	@Test
	def void testForWithNegativeByAndContinueStatement() {
		21.toIntValue.assertTrace(#[STAlgorithmBody, STNumericLiteral] +
			#[STBinaryExpression, STBinaryExpression, STNumericLiteral, // if not taken
			STBinaryExpression, STContinue, STNumericLiteral].repeat(4), '''
			VAR_TEMP
				test: INT := INT#17;
				i: INT;
			END_VAR
			
			FOR i := INT#7 TO INT#0 BY INT#-1 DO
				IF i MOD INT#2 = INT#0 THEN
					CONTINUE;
				END_IF;
				test := test + INT#1;
			END_FOR;
		'''.evaluateAlgorithm)
	}

	@Test
	def void testForWithNegativeByAndExitStatement() {
		21.toIntValue.assertTrace(
			#[STAlgorithmBody, STNumericLiteral] +
				#[STBinaryExpression, STBinaryExpression, STNumericLiteral].repeat(4) +
				#[STBinaryExpression, STExit, STBinaryExpression], '''
				VAR_TEMP
					test: INT := INT#16;
					i: INT;
				END_VAR
				
				FOR i := INT#7 TO INT#0 BY INT#-1 DO
					IF i = INT#3 THEN
						EXIT;
					END_IF;
					test := test + INT#1;
				END_FOR;
				test := test + INT#1;
			'''.evaluateAlgorithm)
	}

	@Test
	def void testForWithNegativeByAndReturnStatement() {
		21.toIntValue.assertTrace(
			#[STAlgorithmBody, STNumericLiteral] +
				#[STBinaryExpression, STBinaryExpression, STNumericLiteral].repeat(4) + #[STBinaryExpression, STReturn],
			'''
				VAR_TEMP
					test: INT := INT#17;
					i: INT;
				END_VAR
				
				FOR i := INT#7 TO INT#0 BY INT#-1 DO
					IF i = INT#3 THEN
						RETURN;
					END_IF;
					test := test + INT#1;
				END_FOR;
			'''.evaluateAlgorithm)
	}

	@Test
	def void testWhileStatement() {
		21.toIntValue.assertTrace(#[STAlgorithmBody] + #[STBinaryExpression].repeat(13), '''
			VAR_TEMP
				test: INT := INT#17;
				i: INT := INT#0;
			END_VAR
			
			WHILE i < INT#4 DO
				i := i + INT#1;
				test := test + INT#1;
			END_WHILE;
		'''.evaluateAlgorithm)
	}

	@Test
	def void testWhileWithContinueStatement() {
		21.toIntValue.assertTrace(#[STAlgorithmBody] + (STBinaryExpression.repeat(4) + // if not taken
		STBinaryExpression.repeat(3) + #[STContinue]).repeat(4) + #[STBinaryExpression], '''
			VAR_TEMP
				test: INT := INT#17;
				i: INT := INT#0;
			END_VAR
			
			WHILE i < INT#8 DO
				i := i + INT#1;
				IF i MOD INT#2 = INT#0 THEN
					CONTINUE;
				END_IF;
				test := test + INT#1;
			END_WHILE;
		'''.evaluateAlgorithm)
	}

	@Test
	def void testWhileWithExitStatement() {
		21.toIntValue.assertTrace(#[STAlgorithmBody] + STBinaryExpression.repeat(19) + #[STExit, STBinaryExpression],
			'''
				VAR_TEMP
					test: INT := INT#16;
					i: INT := INT#0;
				END_VAR
				
				WHILE i < INT#8 DO
					i := i + INT#1;
					IF i = INT#5 THEN
						EXIT;
					END_IF;
					test := test + INT#1;
				END_WHILE;
				test := test + INT#1;
			'''.evaluateAlgorithm)
	}

	@Test
	def void testWhileWithReturnStatement() {
		21.toIntValue.assertTrace(#[STAlgorithmBody] + STBinaryExpression.repeat(19) + #[STReturn], '''
			VAR_TEMP
				test: INT := INT#17;
				i: INT := INT#0;
			END_VAR
			
			WHILE i < INT#8 DO
				i := i + INT#1;
				IF i = INT#5 THEN
					RETURN;
				END_IF;
				test := test + INT#1;
			END_WHILE;
		'''.evaluateAlgorithm)
	}

	@Test
	def void testRepeatStatement() {
		21.toIntValue.assertTrace(#[STAlgorithmBody] + STBinaryExpression.repeat(12), '''
			VAR_TEMP
				test: INT := INT#17;
				i: INT := INT#0;
			END_VAR
			
			REPEAT
				i := i + INT#1;
				test := test + INT#1;
			UNTIL i = INT#4
			END_REPEAT;
		'''.evaluateAlgorithm)
	}

	@Test
	def void testRepeatWithContinueStatement() {
		21.toIntValue.assertTrace(#[STAlgorithmBody] + (STBinaryExpression.repeat(4) + // if not taken
		STBinaryExpression.repeat(2) + #[STContinue, STBinaryExpression]).repeat(4), '''
			VAR_TEMP
				test: INT := INT#17;
				i: INT := INT#0;
			END_VAR
			
			REPEAT
				i := i + INT#1;
				IF i MOD INT#2 = INT#0 THEN
					CONTINUE;
				END_IF;
				test := test + INT#1;
			UNTIL i = INT#8
			END_REPEAT;
		'''.evaluateAlgorithm)
	}

	@Test
	def void testRepeatWithExitStatement() {
		21.toIntValue.assertTrace(#[STAlgorithmBody] + STBinaryExpression.repeat(18) + #[STExit, STBinaryExpression],
			'''
				VAR_TEMP
					test: INT := INT#16;
					i: INT := INT#0;
				END_VAR
				
				REPEAT
					i := i + INT#1;
					IF i = INT#5 THEN
						EXIT;
					END_IF;
					test := test + INT#1;
				UNTIL i = INT#8
				END_REPEAT;
				test := test + INT#1;
			'''.evaluateAlgorithm)
	}

	@Test
	def void testRepeatWithReturnStatement() {
		21.toIntValue.assertTrace(#[STAlgorithmBody] + STBinaryExpression.repeat(18) + #[STReturn], '''
			VAR_TEMP
				test: INT := INT#17;
				i: INT := INT#0;
			END_VAR
			
			REPEAT
				i := i + INT#1;
				IF i = INT#5 THEN
					RETURN;
				END_IF;
				test := test + INT#1;
			UNTIL i = INT#8
			END_REPEAT;
		'''.evaluateAlgorithm)
	}

	@Test
	def void testReturnStatement() {
		21.toIntValue.assertTrace(#[STAlgorithmBody, STBinaryExpression, STReturn], '''
			VAR_TEMP
				test: INT := INT#17;
			END_VAR
			
			test := test + INT#4;
			RETURN;
			test := INT#0;
		'''.evaluateAlgorithm)
	}

	def static evaluateExpression(CharSequence expression) {
		expression.evaluateExpression(emptyList)
	}

	def static evaluateExpression(CharSequence expression, Variable variable) {
		expression.evaluateExpression(#[variable])
	}

	def static evaluateExpression(CharSequence expression, Collection<Variable> variables) {
		new StructuredTextEvaluator(expression.toString, variables, null, null).evaluate
	}

	def static evaluateAlgorithm(CharSequence algorithm) {
		val alg = LibraryElementFactory.eINSTANCE.createSTAlgorithm
		alg.name = "TEST"
		alg.text = algorithm.toString
		val eval = new TracingStructuredTextEvaluator(alg, emptyList, null)
		eval.evaluate
		return eval
	}

	def static void assertTrace(Object expectedResult, Iterable<? extends Class<?>> expectedTrace,
		TracingStructuredTextEvaluator actual) {
		expectedResult.assertEquals(actual.variables.get("test").value)
		expectedTrace.assertIterableEquals(actual.trace.filterNull.map[class.interfaces.head].filterNull)
	}

	def static Iterable<? extends Class<?>> repeat(Class<?> clazz, int repeat) {
		(0 ..< repeat).map[clazz]
	}

	def static Iterable<? extends Class<?>> repeat(Iterable<? extends Class<?>> clazz, int repeat) {
		(0 ..< repeat).map[clazz].flatten
	}

	static class TracingStructuredTextEvaluator extends StructuredTextEvaluator {
		@Accessors
		final Queue<Object> trace = new ArrayBlockingQueue(1000)

		new(STAlgorithm alg, Collection<Variable> variables, Evaluator parent) {
			super(alg, variables, parent)
		}

		new(String text, Collection<Variable> variables, BaseFBType fbType, Evaluator parent) {
			super(text, variables, fbType, parent)
		}

		override protected <T> T trap(T context) {
			trace.add(context)
			return super.trap(context)
		}
	}
}
