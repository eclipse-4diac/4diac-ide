/*******************************************************************************
 * Copyright (c) 2022 - 2023 Martin Erich Jobst
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
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Collection
import java.util.Queue
import java.util.concurrent.ArrayBlockingQueue
import java.util.stream.Stream
import java.util.stream.StreamSupport
import org.eclipse.fordiac.ide.globalconstantseditor.GlobalConstantsStandaloneSetup
import org.eclipse.fordiac.ide.model.data.AnyBitType
import org.eclipse.fordiac.ide.model.data.AnyIntType
import org.eclipse.fordiac.ide.model.data.AnyNumType
import org.eclipse.fordiac.ide.model.data.AnyRealType
import org.eclipse.fordiac.ide.model.data.AnyUnsignedType
import org.eclipse.fordiac.ide.model.data.BoolType
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes
import org.eclipse.fordiac.ide.model.eval.Evaluator
import org.eclipse.fordiac.ide.model.eval.st.STAlgorithmEvaluator
import org.eclipse.fordiac.ide.model.eval.st.ScopedExpressionEvaluator
import org.eclipse.fordiac.ide.model.eval.value.AnyStringValue
import org.eclipse.fordiac.ide.model.eval.value.ArrayValue
import org.eclipse.fordiac.ide.model.eval.value.BoolValue
import org.eclipse.fordiac.ide.model.eval.variable.Variable
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary
import org.eclipse.fordiac.ide.structuredtextalgorithm.STAlgorithmStandaloneSetup
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STAssignment
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryOperator
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STContinue
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExit
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STNumericLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STReturn
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STUnaryOperator
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.STFunctionStandaloneSetup
import org.eclipse.xtend.lib.annotations.Accessors
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

import static org.junit.jupiter.params.provider.Arguments.*

import static extension org.eclipse.fordiac.ide.model.eval.value.BoolValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.ByteValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.CharValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.DIntValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.DWordValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.DateAndTimeValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.DateValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.IntValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.LDateAndTimeValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.LDateValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.LIntValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.LRealValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.LTimeOfDayValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.LTimeValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.LWordValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.RealValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.SIntValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.StringValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.TimeOfDayValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.TimeValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.UDIntValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.UIntValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.ULIntValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.USIntValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.ValueOperations.*
import static extension org.eclipse.fordiac.ide.model.eval.value.WCharValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.WStringValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.WordValue.*
import static extension org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil.*
import static extension org.junit.jupiter.api.Assertions.*

class StructuredTextEvaluatorTest {

	@BeforeAll
	def static void setupXtext() {
		new DataTypeLibrary
		GlobalConstantsStandaloneSetup.doSetup
		STFunctionStandaloneSetup.doSetup
		STAlgorithmStandaloneSetup.doSetup
	}

	@Test
	def void testExpressionParseError() {
		Exception.assertThrows["-".evaluateExpression]
	}

	@Test
	def void testLiterals() {
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
		Byte.MIN_VALUE.toSIntValue.assertEquals("SINT#-128".evaluateExpression)
		Byte.MAX_VALUE.toSIntValue.assertEquals("SINT#127".evaluateExpression)
		Byte.MAX_VALUE.toSIntValue.assertEquals("SINT#16#7f".evaluateExpression)
		// INT
		0.toIntValue.assertEquals("INT#0".evaluateExpression)
		17.toIntValue.assertEquals("INT#17".evaluateExpression)
		17.toIntValue.assertEquals("INT#16#11".evaluateExpression)
		(-4).toIntValue.assertEquals("INT#-4".evaluateExpression)
		Short.MIN_VALUE.toIntValue.assertEquals("INT#-32768".evaluateExpression)
		Short.MAX_VALUE.toIntValue.assertEquals("INT#32767".evaluateExpression)
		Short.MAX_VALUE.toIntValue.assertEquals("INT#16#7fff".evaluateExpression)
		// DINT
		0.toDIntValue.assertEquals("DINT#0".evaluateExpression)
		17.toDIntValue.assertEquals("DINT#17".evaluateExpression)
		17.toDIntValue.assertEquals("DINT#16#11".evaluateExpression)
		(-4).toDIntValue.assertEquals("DINT#-4".evaluateExpression)
		Integer.MIN_VALUE.toDIntValue.assertEquals("DINT#-2147483648".evaluateExpression)
		Integer.MAX_VALUE.toDIntValue.assertEquals("DINT#2147483647".evaluateExpression)
		Integer.MAX_VALUE.toDIntValue.assertEquals("DINT#16#7fffffff".evaluateExpression)
		// LINT
		0.toLIntValue.assertEquals("LINT#0".evaluateExpression)
		17.toLIntValue.assertEquals("LINT#17".evaluateExpression)
		17.toLIntValue.assertEquals("LINT#16#11".evaluateExpression)
		(-4).toLIntValue.assertEquals("LINT#-4".evaluateExpression)
		Long.MIN_VALUE.toLIntValue.assertEquals("LINT#-9223372036854775808".evaluateExpression)
		Long.MAX_VALUE.toLIntValue.assertEquals("LINT#9223372036854775807".evaluateExpression)
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
		65535.toUIntValue.assertEquals("UINT#65535".evaluateExpression)
		65535.toUIntValue.assertEquals("UINT#16#ffff".evaluateExpression)
		// UDINT
		0.toUDIntValue.assertEquals("UDINT#0".evaluateExpression)
		17.toUDIntValue.assertEquals("UDINT#17".evaluateExpression)
		17.toUDIntValue.assertEquals("UDINT#16#11".evaluateExpression)
		0xffffffff.toUDIntValue.assertEquals("UDINT#4294967295".evaluateExpression)
		0xffffffff.toUDIntValue.assertEquals("UDINT#16#ffffffff".evaluateExpression)
		// ULINT
		0.toULIntValue.assertEquals("ULINT#0".evaluateExpression)
		17.toULIntValue.assertEquals("ULINT#17".evaluateExpression)
		17.toULIntValue.assertEquals("ULINT#16#11".evaluateExpression)
		0xffffffffffffffff#L.toULIntValue.assertEquals("ULINT#18446744073709551615".evaluateExpression)
		0xffffffffffffffff#L.toULIntValue.assertEquals("ULINT#16#ffffffffffffffff".evaluateExpression)
		// REAL
		0.toRealValue.assertEquals("REAL#0.0".evaluateExpression)
		17.toRealValue.assertEquals("REAL#17.0".evaluateExpression)
		(-4).toRealValue.assertEquals("REAL#-4.0".evaluateExpression)
		(3.1415).toRealValue.assertEquals("REAL#3.1415".evaluateExpression)
		// LREAL
		0.toLRealValue.assertEquals("LREAL#0.0".evaluateExpression)
		17.toLRealValue.assertEquals("LREAL#17.0".evaluateExpression)
		(-4).toLRealValue.assertEquals("LREAL#-4.0".evaluateExpression)
		(3.1415).toLRealValue.assertEquals("LREAL#3.1415".evaluateExpression)
		// BYTE
		0.toByteValue.assertEquals("BYTE#0".evaluateExpression)
		17.toByteValue.assertEquals("BYTE#17".evaluateExpression)
		17.toByteValue.assertEquals("BYTE#16#11".evaluateExpression)
		255.toByteValue.assertEquals("BYTE#255".evaluateExpression)
		255.toByteValue.assertEquals("BYTE#16#ff".evaluateExpression)
		// WORD
		0.toWordValue.assertEquals("WORD#0".evaluateExpression)
		17.toWordValue.assertEquals("WORD#17".evaluateExpression)
		17.toWordValue.assertEquals("WORD#16#11".evaluateExpression)
		65535.toWordValue.assertEquals("WORD#65535".evaluateExpression)
		65535.toWordValue.assertEquals("WORD#16#ffff".evaluateExpression)
		// DWORD
		0.toDWordValue.assertEquals("DWORD#0".evaluateExpression)
		17.toDWordValue.assertEquals("DWORD#17".evaluateExpression)
		17.toDWordValue.assertEquals("DWORD#16#11".evaluateExpression)
		0xffffffff.toDWordValue.assertEquals("DWORD#4294967295".evaluateExpression)
		0xffffffff.toDWordValue.assertEquals("DWORD#16#ffffffff".evaluateExpression)
		// LWORD
		0.toLWordValue.assertEquals("LWORD#0".evaluateExpression)
		17.toLWordValue.assertEquals("LWORD#17".evaluateExpression)
		17.toLWordValue.assertEquals("LWORD#16#11".evaluateExpression)
		0xffffffffffffffff#L.toLWordValue.assertEquals("LWORD#18446744073709551615".evaluateExpression)
		0xffffffffffffffff#L.toLWordValue.assertEquals("LWORD#16#ffffffffffffffff".evaluateExpression)
		// TIME
		0.toTimeValue.assertEquals("TIME#0s".evaluateExpression)
		Duration.ofNanos(17).toTimeValue.assertEquals("TIME#17ns".evaluateExpression)
		Duration.ofNanos(-4).toTimeValue.assertEquals("TIME#-4ns".evaluateExpression)
		Duration.ofNanos(17004000000L).toTimeValue.assertEquals("TIME#17s4ms".evaluateExpression)
		// LTIME
		0.toLTimeValue.assertEquals("LTIME#0s".evaluateExpression)
		Duration.ofNanos(17).toLTimeValue.assertEquals("LTIME#17ns".evaluateExpression)
		Duration.ofNanos(-4).toLTimeValue.assertEquals("LTIME#-4ns".evaluateExpression)
		Duration.ofNanos(17004000000L).toLTimeValue.assertEquals("LTIME#17s4ms".evaluateExpression)
		// DATE
		0.toDateValue.assertEquals("DATE#1970-01-01".evaluateExpression)
		LocalDate.of(1970, 1, 1).toDateValue.assertEquals("DATE#1970-01-01".evaluateExpression)
		LocalDate.of(2021, 4, 17).toDateValue.assertEquals("DATE#2021-04-17".evaluateExpression)
		LocalDate.of(1969, 4, 17).toDateValue.assertEquals("DATE#1969-04-17".evaluateExpression)
		// LDATE
		0.toLDateValue.assertEquals("LDATE#1970-01-01".evaluateExpression)
		LocalDate.of(1970, 1, 1).toLDateValue.assertEquals("LDATE#1970-01-01".evaluateExpression)
		LocalDate.of(2021, 4, 17).toLDateValue.assertEquals("LDATE#2021-04-17".evaluateExpression)
		LocalDate.of(1969, 4, 17).toLDateValue.assertEquals("LDATE#1969-04-17".evaluateExpression)
		// TOD
		0.toTimeOfDayValue.assertEquals("TOD#00:00:00".evaluateExpression)
		LocalTime.of(0, 0, 0).toTimeOfDayValue.assertEquals("TOD#00:00:00".evaluateExpression)
		LocalTime.of(4, 17, 21).toTimeOfDayValue.assertEquals("TOD#04:17:21".evaluateExpression)
		LocalTime.of(4, 17, 21, 420000000).toTimeOfDayValue.assertEquals("TOD#04:17:21.42".evaluateExpression)
		// LTOD
		0.toLTimeOfDayValue.assertEquals("LTOD#00:00:00".evaluateExpression)
		LocalTime.of(0, 0, 0).toLTimeOfDayValue.assertEquals("LTOD#00:00:00".evaluateExpression)
		LocalTime.of(4, 17, 21).toLTimeOfDayValue.assertEquals("LTOD#04:17:21".evaluateExpression)
		LocalTime.of(4, 17, 21, 420000000).toLTimeOfDayValue.assertEquals("LTOD#04:17:21.42".evaluateExpression)
		// DT
		0.toDateAndTimeValue.assertEquals("DT#1970-01-01-00:00:00".evaluateExpression)
		LocalDateTime.of(1970, 1, 1, 0, 0, 0).toDateAndTimeValue.assertEquals(
			"DT#1970-01-01-00:00:00".evaluateExpression)
		LocalDateTime.of(2021, 4, 17, 4, 17, 21, 420000000).toDateAndTimeValue.assertEquals(
			"DT#2021-04-17-04:17:21.42".evaluateExpression)
		LocalDateTime.of(1969, 4, 17, 4, 17, 21, 420000000).toDateAndTimeValue.assertEquals(
			"DT#1969-04-17-04:17:21.42".evaluateExpression)
		// LDT
		0.toLDateAndTimeValue.assertEquals("LDT#1970-01-01-00:00:00".evaluateExpression)
		LocalDateTime.of(1970, 1, 1, 0, 0, 0).toLDateAndTimeValue.assertEquals(
			"LDT#1970-01-01-00:00:00".evaluateExpression)
		LocalDateTime.of(2021, 4, 17, 4, 17, 21, 420000000).toLDateAndTimeValue.assertEquals(
			"LDT#2021-04-17-04:17:21.42".evaluateExpression)
		LocalDateTime.of(1969, 4, 17, 4, 17, 21, 420000000).toLDateAndTimeValue.assertEquals(
			"LDT#1969-04-17-04:17:21.42".evaluateExpression)
		// CHAR
		'\u0000'.toCharValue.assertEquals("CHAR#'$00'".evaluateExpression)
		'0'.toCharValue.assertEquals("CHAR#'$30'".evaluateExpression)
		'\n'.toCharValue.assertEquals("CHAR#'$N'".evaluateExpression)
		'a'.toCharValue.assertEquals("CHAR#'a'".evaluateExpression)
		// WCHAR
		'\u0000'.toWCharValue.assertEquals("WCHAR#\"$0000\"".evaluateExpression)
		'0'.toWCharValue.assertEquals("WCHAR#\"$0030\"".evaluateExpression)
		'\n'.toWCharValue.assertEquals("WCHAR#\"$N\"".evaluateExpression)
		'a'.toWCharValue.assertEquals("WCHAR#\"a\"".evaluateExpression)
		// STRING
		"\u0000".toStringValue.assertEquals("STRING#'$00'".evaluateExpression)
		"0".toStringValue.assertEquals("STRING#'$30'".evaluateExpression)
		"\n".toStringValue.assertEquals("STRING#'$N'".evaluateExpression)
		"abc".toStringValue.assertEquals("STRING#'abc'".evaluateExpression)
		// WSTRING
		"\u0000".toWStringValue.assertEquals("WSTRING#\"$0000\"".evaluateExpression)
		"0".toWStringValue.assertEquals("WSTRING#\"$0030\"".evaluateExpression)
		"\n".toWStringValue.assertEquals("WSTRING#\"$N\"".evaluateExpression)
		"abc".toWStringValue.assertEquals("WSTRING#\"abc\"".evaluateExpression)
	}

	@Test
	def void testUntypedNumericLiterals() {
		// BOOL
		true.toBoolValue.assertEquals("TRUE".evaluateExpression)
		false.toBoolValue.assertEquals("FALSE".evaluateExpression)
		// SINT
		0.toSIntValue.assertEquals("0".evaluateExpression)
		17.toSIntValue.assertEquals("17".evaluateExpression)
		17.toSIntValue.assertEquals("16#11".evaluateExpression)
		(-4).toSIntValue.assertEquals("-4".evaluateExpression)
		Byte.MIN_VALUE.toSIntValue.assertEquals("-128".evaluateExpression)
		Byte.MAX_VALUE.toSIntValue.assertEquals("127".evaluateExpression)
		Byte.MAX_VALUE.toSIntValue.assertEquals("16#7f".evaluateExpression)
		// INT
		128.toIntValue.assertEquals("128".evaluateExpression)
		128.toIntValue.assertEquals("16#80".evaluateExpression)
		255.toIntValue.assertEquals("255".evaluateExpression)
		255.toIntValue.assertEquals("16#ff".evaluateExpression)
		Short.MIN_VALUE.toIntValue.assertEquals("-32768".evaluateExpression)
		Short.MAX_VALUE.toIntValue.assertEquals("32767".evaluateExpression)
		Short.MAX_VALUE.toIntValue.assertEquals("16#7fff".evaluateExpression)
		// DINT
		32768.toDIntValue.assertEquals("32768".evaluateExpression)
		32768.toDIntValue.assertEquals("16#8000".evaluateExpression)
		65535.toDIntValue.assertEquals("65535".evaluateExpression)
		65535.toDIntValue.assertEquals("16#ffff".evaluateExpression)
		Integer.MIN_VALUE.toDIntValue.assertEquals("-2147483648".evaluateExpression)
		Integer.MAX_VALUE.toDIntValue.assertEquals("2147483647".evaluateExpression)
		Integer.MAX_VALUE.toDIntValue.assertEquals("16#7fffffff".evaluateExpression)
		// LINT
		0x80000000#L.toLIntValue.assertEquals("2147483648".evaluateExpression)
		0x80000000#L.toLIntValue.assertEquals("16#80000000".evaluateExpression)
		0xffffffff#L.toLIntValue.assertEquals("4294967295".evaluateExpression)
		0xffffffff#L.toLIntValue.assertEquals("16#ffffffff".evaluateExpression)
		Long.MAX_VALUE.toLIntValue.assertEquals("9223372036854775807".evaluateExpression)
		Long.MAX_VALUE.toLIntValue.assertEquals("16#7fffffffffffffff".evaluateExpression)
		// ULINT
		0x8000000000000000#L.toULIntValue.assertEquals("9223372036854775808".evaluateExpression)
		0x8000000000000000#L.toULIntValue.assertEquals("16#8000000000000000".evaluateExpression)
		0xffffffffffffffff#L.toULIntValue.assertEquals("18446744073709551615".evaluateExpression)
		0xffffffffffffffff#L.toULIntValue.assertEquals("16#ffffffffffffffff".evaluateExpression)
		// LREAL
		0.toLRealValue.assertEquals("0.0".evaluateExpression)
		17.toLRealValue.assertEquals("17.0".evaluateExpression)
		(-4).toLRealValue.assertEquals("-4.0".evaluateExpression)
		(3.1415).toLRealValue.assertEquals("3.1415".evaluateExpression)
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
			STUnaryOperator.VALUES.flatMap [ operator |
				ElementaryTypes.allElementaryType.filter[it instanceof AnyNumType || it instanceof AnyBitType].filter [
					operator.isApplicableTo(it)
				].flatMap [ type |
					#["0", "1", "-1", "17", "-4"].reject [
						((type instanceof AnyUnsignedType || type instanceof AnyBitType) && contains('-')) ||
							(type instanceof BoolType && length > 1)
					].map[type instanceof AnyRealType ? concat(".0") : it].map [ value |
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

	@Test
	def void testBinaryBitExpression() {
		0x00000000.toDWordValue.assertEquals("DWORD#16#DEADBEEF AND BOOL#0".evaluateExpression)
		0x00000000.toDWordValue.assertEquals("BOOL#0 AND DWORD#16#DEADBEEF".evaluateExpression)
		0x00000001.toDWordValue.assertEquals("DWORD#16#DEADBEEF AND BOOL#1".evaluateExpression)
		0x00000001.toDWordValue.assertEquals("BOOL#1 AND DWORD#16#DEADBEEF".evaluateExpression)
		0x12345678.toDWordValue.assertEquals("DWORD#16#12345678 OR BOOL#0".evaluateExpression)
		0x12345678.toDWordValue.assertEquals("BOOL#0 OR DWORD#16#12345678".evaluateExpression)
		0x12345679.toDWordValue.assertEquals("DWORD#16#12345678 OR BOOL#1".evaluateExpression)
		0x12345679.toDWordValue.assertEquals("BOOL#1 OR DWORD#16#12345678".evaluateExpression)
		0xdeadbeef.toDWordValue.assertEquals("DWORD#16#DEADBEEF XOR BOOL#0".evaluateExpression)
		0xdeadbeef.toDWordValue.assertEquals("BOOL#0 XOR DWORD#16#DEADBEEF".evaluateExpression)
		0xdeadbeee.toDWordValue.assertEquals("DWORD#16#DEADBEEF XOR BOOL#1".evaluateExpression)
		0xdeadbeee.toDWordValue.assertEquals("BOOL#1 XOR DWORD#16#DEADBEEF".evaluateExpression)
		0x21524110.toDWordValue.assertEquals("NOT DWORD#16#DEADBEEF".evaluateExpression)
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
			STBinaryOperator.VALUES.flatMap [ operator |
				ElementaryTypes.allElementaryType.filter[it instanceof AnyNumType || it instanceof AnyBitType].filter [
					operator.isApplicableTo(it, it) && !operator.range
				].flatMap [ type |
					#["0", "1", "-1", "17", "-4"].reject [
						((type instanceof AnyUnsignedType || type instanceof AnyBitType) && contains('-')) ||
							(type instanceof BoolType && length > 1)
					].map[type instanceof AnyRealType ? concat(".0") : it].flatMap [ value |
						#[
							arguments(operator, type.name, value, if(type instanceof AnyRealType) "1.0" else "1"),
							arguments(operator, type.name, value,
								if(type instanceof BoolType) "0" else if(type instanceof AnyRealType) "7.0" else "7")
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
	def void testBinaryExpressionTimeArithmetic() {
		// TIME
		Duration.ofSeconds(21).toTimeValue.assertEquals("T#17s + T#4s".evaluateExpression)
		Duration.ofSeconds(4).toTimeValue.assertEquals("T#21s - T#17s".evaluateExpression)
		Duration.ofSeconds(42).toTimeValue.assertEquals("T#21s * 2".evaluateExpression)
		Duration.ofSeconds(21).toTimeValue.assertEquals("T#42s / 2".evaluateExpression)
		Duration.ofSeconds(21).toTimeValue.assertEquals("T#42s * 0.5".evaluateExpression)
		Duration.ofSeconds(42).toTimeValue.assertEquals("T#21s / 0.5".evaluateExpression)
		// LTIME
		Duration.ofSeconds(21).toLTimeValue.assertEquals("LT#17s + LT#4s".evaluateExpression)
		Duration.ofSeconds(4).toLTimeValue.assertEquals("LT#21s - LT#17s".evaluateExpression)
		Duration.ofSeconds(42).toLTimeValue.assertEquals("LT#21s * 2".evaluateExpression)
		Duration.ofSeconds(21).toLTimeValue.assertEquals("LT#42s / 2".evaluateExpression)
		Duration.ofSeconds(21).toLTimeValue.assertEquals("LT#42s * 0.5".evaluateExpression)
		Duration.ofSeconds(42).toLTimeValue.assertEquals("LT#21s / 0.5".evaluateExpression)
		// promotion
		Duration.ofSeconds(21).toLTimeValue.assertEquals("LT#17s + T#4s".evaluateExpression)
		Duration.ofSeconds(4).toLTimeValue.assertEquals("LT#21s - T#17s".evaluateExpression)
		Duration.ofSeconds(21).toLTimeValue.assertEquals("T#17s + LT#4s".evaluateExpression)
		Duration.ofSeconds(4).toLTimeValue.assertEquals("T#21s - LT#17s".evaluateExpression)
	}

	@Test
	def void testBinaryExpressionDateAndTimeArithmetic() {
		// TOD
		LocalTime.of(21, 21, 42).toTimeOfDayValue.assertEquals("TOD#17:04:21 + T#4h17m21s".evaluateExpression)
		LocalTime.of(17, 04, 21).toTimeOfDayValue.assertEquals("TOD#21:21:42 - T#4h17m21s".evaluateExpression)
		// LTOD
		LocalTime.of(21, 21, 42).toLTimeOfDayValue.assertEquals("LTOD#17:04:21 + LT#4h17m21s".evaluateExpression)
		LocalTime.of(17, 04, 21).toLTimeOfDayValue.assertEquals("LTOD#21:21:42 - LT#4h17m21s".evaluateExpression)
		// DT
		LocalDateTime.of(2021, 04, 17, 21, 21, 42).toDateAndTimeValue.assertEquals(
			"DT#2021-04-17-17:04:21 + T#4h17m21s".evaluateExpression)
		LocalDateTime.of(2021, 04, 17, 17, 04, 21).toDateAndTimeValue.assertEquals(
			"DT#2021-04-17-21:21:42 - T#4h17m21s".evaluateExpression)
		// LDT
		LocalDateTime.of(2021, 04, 17, 21, 21, 42).toLDateAndTimeValue.assertEquals(
			"LDT#2021-04-17-17:04:21 + LT#4h17m21s".evaluateExpression)
		LocalDateTime.of(2021, 04, 17, 17, 04, 21).toLDateAndTimeValue.assertEquals(
			"LDT#2021-04-17-21:21:42 - LT#4h17m21s".evaluateExpression)
		// promotion
		LocalTime.of(21, 21, 42).toLTimeOfDayValue.assertEquals("LTOD#17:04:21 + T#4h17m21s".evaluateExpression)
		LocalTime.of(21, 21, 42).toLTimeOfDayValue.assertEquals("TOD#17:04:21 + LT#4h17m21s".evaluateExpression)
		LocalTime.of(17, 04, 21).toLTimeOfDayValue.assertEquals("LTOD#21:21:42 - T#4h17m21s".evaluateExpression)
		LocalTime.of(17, 04, 21).toLTimeOfDayValue.assertEquals("TOD#21:21:42 - LT#4h17m21s".evaluateExpression)
		LocalDateTime.of(2021, 04, 17, 21, 21, 42).toLDateAndTimeValue.assertEquals(
			"LDT#2021-04-17-17:04:21 + T#4h17m21s".evaluateExpression)
		LocalDateTime.of(2021, 04, 17, 21, 21, 42).toLDateAndTimeValue.assertEquals(
			"DT#2021-04-17-17:04:21 + LT#4h17m21s".evaluateExpression)
		LocalDateTime.of(2021, 04, 17, 17, 04, 21).toLDateAndTimeValue.assertEquals(
			"LDT#2021-04-17-21:21:42 - T#4h17m21s".evaluateExpression)
		LocalDateTime.of(2021, 04, 17, 17, 04, 21).toLDateAndTimeValue.assertEquals(
			"DT#2021-04-17-21:21:42 - LT#4h17m21s".evaluateExpression)
	}

	@Test
	def void testBinaryExpressionDateArithmetic() {
		// DATE
		Duration.ofDays(4).toTimeValue.assertEquals("D#2021-04-21 - D#2021-04-17".evaluateExpression)
		// LDATE
		Duration.ofDays(4).toLTimeValue.assertEquals("LD#2021-04-21 - LD#2021-04-17".evaluateExpression)
		// TOD
		Duration.ofSeconds(4).toTimeValue.assertEquals("TOD#21:04:21 - TOD#21:04:17".evaluateExpression)
		// LTOD
		Duration.ofSeconds(4).toLTimeValue.assertEquals("LTOD#21:04:21 - LTOD#21:04:17".evaluateExpression)
		// DT
		Duration.ofSeconds(4).toTimeValue.assertEquals(
			"DT#2021-04-17-21:04:21 - DT#2021-04-17-21:04:17".evaluateExpression)
		// LDT
		Duration.ofSeconds(4).toLTimeValue.assertEquals(
			"LDT#2021-04-17-21:04:21 - LDT#2021-04-17-21:04:17".evaluateExpression)
		// promotion
		Duration.ofDays(4).toLTimeValue.assertEquals("LD#2021-04-21 - D#2021-04-17".evaluateExpression)
		Duration.ofDays(4).toLTimeValue.assertEquals("D#2021-04-21 - LD#2021-04-17".evaluateExpression)
		Duration.ofSeconds(4).toLTimeValue.assertEquals("LTOD#21:04:21 - TOD#21:04:17".evaluateExpression)
		Duration.ofSeconds(4).toLTimeValue.assertEquals("TOD#21:04:21 - LTOD#21:04:17".evaluateExpression)
		Duration.ofSeconds(4).toLTimeValue.assertEquals(
			"LDT#2021-04-17-21:04:21 - DT#2021-04-17-21:04:17".evaluateExpression)
		Duration.ofSeconds(4).toLTimeValue.assertEquals(
			"DT#2021-04-17-21:04:21 - LDT#2021-04-17-21:04:17".evaluateExpression)
	}

	@Test
	def void testBinaryExpressionRealComparison() {
		false.toBoolValue.assertEquals("-REAL#0.0 < REAL#0.0".evaluateExpression)
		false.toBoolValue.assertEquals("REAL#0.0 < -REAL#0.0".evaluateExpression)
		true.toBoolValue.assertEquals("-REAL#0.0 <= REAL#0.0".evaluateExpression)
		true.toBoolValue.assertEquals("REAL#0.0 <= -REAL#0.0".evaluateExpression)
		false.toBoolValue.assertEquals("-REAL#0.0 > REAL#0.0".evaluateExpression)
		false.toBoolValue.assertEquals("REAL#0.0 > -REAL#0.0".evaluateExpression)
		true.toBoolValue.assertEquals("-REAL#0.0 >= REAL#0.0".evaluateExpression)
		true.toBoolValue.assertEquals("REAL#0.0 >= -REAL#0.0".evaluateExpression)

		false.toBoolValue.assertEquals("-LREAL#0.0 < LREAL#0.0".evaluateExpression)
		false.toBoolValue.assertEquals("LREAL#0.0 < -LREAL#0.0".evaluateExpression)
		true.toBoolValue.assertEquals("-LREAL#0.0 <= LREAL#0.0".evaluateExpression)
		true.toBoolValue.assertEquals("LREAL#0.0 <= -LREAL#0.0".evaluateExpression)
		false.toBoolValue.assertEquals("-LREAL#0.0 > LREAL#0.0".evaluateExpression)
		false.toBoolValue.assertEquals("LREAL#0.0 > -LREAL#0.0".evaluateExpression)
		true.toBoolValue.assertEquals("-LREAL#0.0 >= LREAL#0.0".evaluateExpression)
		true.toBoolValue.assertEquals("LREAL#0.0 >= -LREAL#0.0".evaluateExpression)
	}

	@Test
	def void testStandardFunctions() {
		21.toSIntValue.assertEquals("ADD(17, 4)".evaluateExpression)
		42.toSIntValue.assertEquals("ADD(17, 4, 21)".evaluateExpression)
	}

	@Test
	def void testVariable() {
		21.toIntValue.assertTrace(#[STAssignment], '''
			VAR_TEMP
				test: INT := INT#17;
			END_VAR
			
			test := test + INT#4;
		'''.evaluateAlgorithm)
	}

	@Test
	def void testPartial() {
		21.toWordValue.assertTrace(#[STAssignment], '''
			VAR_TEMP
				test: WORD := WORD#17;
			END_VAR
			
			test.%X2 := test.%X0;
		'''.evaluateAlgorithm)
	}

	@Test
	def void testPartialExpression() {
		21.toWordValue.assertTrace(STAssignment.repeat(4), '''
			VAR_TEMP
				test: WORD := WORD#17;
			END_VAR
			
			test.%B1 := test.%B0;
			test.%X(8 + 2) := test.%X(8 + 0);
			test.%B0 := test.%B1;
			test.%B1 := BYTE#0;
		'''.evaluateAlgorithm)
	}

	@ParameterizedTest(name="{index}: {1} := '4diac IDE'{0}")
	@MethodSource("stringSubscriptArgumentsProvider")
	def void testStringSubscript(int index, String result) {
		result.toCharValue.assertTrace(#[STAssignment], '''
			VAR_TEMP
				test: CHAR;
				str: STRING := '4diac IDE';
			END_VAR
			
			test := str[«index»];
		'''.evaluateAlgorithm)
	}

	@ParameterizedTest(name="{index}: {1} := '4diac IDE'{0}")
	@MethodSource("stringSubscriptArgumentsProvider")
	def void testWStringSubscript(int index, String result) {
		result.toWCharValue.assertTrace(#[STAssignment], '''
			VAR_TEMP
				test: WCHAR;
				str: WSTRING := "4diac IDE";
			END_VAR
			
			test := str[«index»];
		'''.evaluateAlgorithm)
	}

	def static Stream<Arguments> stringSubscriptArgumentsProvider() {
		Stream.of(
			arguments(0, '\u0000'),
			arguments(1, '4'),
			arguments(4, 'a'),
			arguments(9, 'E'),
			arguments(10, '\u0000'),
			arguments(42, '\u0000'),
			arguments(-1, '\u0000'),
			arguments(-42, '\u0000')
		)
	}

	@Test
	def void testStringSubscriptModify() {
		"4diac IDE".toStringValue.assertTrace(STAssignment.repeat(3), '''
			VAR_TEMP
				test: STRING := '4diac ???';
			END_VAR
			
			test[7] := 'I';
			test[8] := 'D';
			test[9] := 'E';
		'''.evaluateAlgorithm)
	}

	@Test
	def void testWStringSubscriptModify() {
		"4diac IDE".toWStringValue.assertTrace(STAssignment.repeat(3), '''
			VAR_TEMP
				test: WSTRING := "4diac ???";
			END_VAR
			
			test[7] := "I";
			test[8] := "D";
			test[9] := "E";
		'''.evaluateAlgorithm)
	}

	@Test
	def void testStringSubscriptModifyOutOfBounds() {
		"4diac\u0000IDE".toStringValue.assertTrace(STAssignment.repeat(3), '''
			VAR_TEMP
				test: STRING := '4diac';
			END_VAR
			
			test[7] := 'I';
			test[8] := 'D';
			test[9] := 'E';
		'''.evaluateAlgorithm)
		StringIndexOutOfBoundsException.assertThrows [
			'''
				VAR_TEMP
					test: STRING := '4diac';
				END_VAR
				
				test[0] := '?';
			'''.evaluateAlgorithm
		]
		StringIndexOutOfBoundsException.assertThrows [
			'''
				VAR_TEMP
					test: STRING := '4diac';
				END_VAR
				
				test[-1] := '?';
			'''.evaluateAlgorithm
		]
		StringIndexOutOfBoundsException.assertThrows [
			'''
				VAR_TEMP
					test: STRING := '4diac';
				END_VAR
				
				test[-4] := '?';
			'''.evaluateAlgorithm
		]
	}

	@Test
	def void testWStringSubscriptModifyOutOfBounds() {
		"4diac\u0000IDE".toWStringValue.assertTrace(STAssignment.repeat(3), '''
			VAR_TEMP
				test: WSTRING := "4diac";
			END_VAR
			
			test[7] := "I";
			test[8] := "D";
			test[9] := "E";
		'''.evaluateAlgorithm)
		StringIndexOutOfBoundsException.assertThrows [
			'''
				VAR_TEMP
					test: WSTRING := "4diac IDE";
				END_VAR
				
				test[0] := "?";
			'''.evaluateAlgorithm
		]
		StringIndexOutOfBoundsException.assertThrows [
			'''
				VAR_TEMP
					test: WSTRING := "4diac IDE";
				END_VAR
				
				test[-1] := "?";
			'''.evaluateAlgorithm
		]
		StringIndexOutOfBoundsException.assertThrows [
			'''
				VAR_TEMP
					test: WSTRING := "4diac IDE";
				END_VAR
				
				test[-4] := "?";
			'''.evaluateAlgorithm
		]
	}

	@Test
	def void testStringMaxLength() {
		StringIndexOutOfBoundsException.assertThrows [
			'''
				VAR_TEMP
					test: STRING[5] := '4diac IDE';
				END_VAR
				
				test[6] := '?';
			'''.evaluateAlgorithm
		]
	}

	@Test
	def void testStringMaxLengthDefault() {
		StringIndexOutOfBoundsException.assertThrows [
			'''
				VAR_TEMP
					test: STRING := '4diac IDE';
				END_VAR
				
				test[«AnyStringValue.MAX_LENGTH + 1»] := '?';
			'''.evaluateAlgorithm
		]
	}

	@Test
	def void testWStringMaxLength() {
		StringIndexOutOfBoundsException.assertThrows [
			'''
				VAR_TEMP
					test: WSTRING[5] := "4diac IDE";
				END_VAR
				
				test[6] := "?";
			'''.evaluateAlgorithm
		]
	}

	@Test
	def void testWStringMaxLengthDefault() {
		StringIndexOutOfBoundsException.assertThrows [
			'''
				VAR_TEMP
					test: WSTRING := "4diac IDE";
				END_VAR
				
				test[«AnyStringValue.MAX_LENGTH + 1»] := "?";
			'''.evaluateAlgorithm
		]
	}

	@Test
	def void testArray() {
		#[17.toIntValue, 4.toIntValue, 21.toIntValue].assertIterableTrace(STAssignment.repeat(3), '''
			VAR_TEMP
				test: ARRAY [ 0 .. 2 ] OF INT;
			END_VAR
			
			test[0] := 17;
			test[1] := 4;
			test[2] := test[0] + test[1];
		'''.evaluateAlgorithm)
	}

	@Test
	def void testArrayMulti() {
		#[17.toIntValue, 4.toIntValue, 21.toIntValue].assertIterableTrace(STAssignment.repeat(10), '''
			VAR_TEMP
				test: ARRAY [ 0 .. 2 ] OF INT;
				testMulti: ARRAY [ 0 .. 2, 0 .. 1] OF INT;
				testMultiPart: ARRAY [ 0 .. 1] OF INT;
			END_VAR
			
			testMulti[0, 0] := 17;
			testMulti[0, 1] := 4;
			testMulti[1, 0] := 17;
			testMulti[1, 1] := 4;
			testMulti[2, 0] := 17;
			testMulti[2, 1] := 4;
			test[0] := testMulti[0, 0];
			test[1] := testMulti[0, 1];
			testMultiPart := testMulti[0];
			test[2] := testMultiPart[0] + testMultiPart[1];
		'''.evaluateAlgorithm)
	}

	@Test
	def void testArrayInitializer() {
		#[17.toIntValue, 4.toIntValue, 21.toIntValue].assertIterableTrace(#[STAssignment], '''
			VAR_TEMP
				test: ARRAY [ 0 .. 2 ] OF INT := [ 17, 4 ];
			END_VAR
			
			test[2] := test[0] + test[1];
		'''.evaluateAlgorithm)
	}

	@Test
	def void testArrayMultiInitializer() {
		#[17.toIntValue, 4.toIntValue, 21.toIntValue].assertIterableTrace(STAssignment.repeat(3), '''
			VAR_TEMP
				test: ARRAY [ 0 .. 2 ] OF INT;
				testMulti: ARRAY [ 0 .. 2, 0 .. 1 ] OF INT := [ 2([17, 4]), [21, 42] ];
			END_VAR
			
			test[0] := testMulti[0, 0];
			test[1] := testMulti[1, 1];
			test[2] := testMulti[2, 0];
		'''.evaluateAlgorithm)
	}

	@Test
	def void testArrayInitializerWithIndex() {
		#[17.toIntValue, 4.toIntValue, 21.toIntValue, 17.toIntValue, 4.toIntValue, 21.toIntValue].assertIterableTrace(
			STAssignment.repeat(2), '''
				VAR_TEMP
					test: ARRAY [ 0 .. 5 ] OF INT := [ 2(17, 4, 0) ];
				END_VAR
				
				test[2] := test[0] + test[1];
				test[5] := test[3] + test[4];
			'''.evaluateAlgorithm)
	}

	@Test
	def void testArrayExcessInitializer() {
		#[17.toIntValue, 4.toIntValue, 21.toIntValue].assertIterableTrace(emptyList, '''
			VAR_TEMP
				test: ARRAY [ 0 .. 2 ] OF INT := [ 17, 4, 21, 42 ];
			END_VAR
		'''.evaluateAlgorithm)
	}

	@Test
	def void testArrayExcessRepeatInitializer() {
		#[17.toIntValue, 4.toIntValue, 21.toIntValue].assertIterableTrace(emptyList, '''
			VAR_TEMP
				test: ARRAY [ 0 .. 2 ] OF INT := [ 2(17, 4, 21) ];
			END_VAR
		'''.evaluateAlgorithm)
	}

	@Test
	def void testArrayIndexOutOfBounds() {
		ArrayIndexOutOfBoundsException.assertThrows [
			'''
				VAR_TEMP
					test: ARRAY [ 0 .. 1 ] OF INT := [ 17, 4 ];
					invalidIndex : INT := 3;
				END_VAR
				
				test[invalidIndex] := test[0] + test[1];
			'''.evaluateAlgorithm
		]
	}

	@Test
	def void testArrayEquals() {
		true.toBoolValue.assertTrace(STAssignment.repeat(3), '''
			VAR_TEMP
				test: BOOL;
				test1: ARRAY [ 0 .. 2 ] OF INT := [ 17, 4 ];
				test2: ARRAY [ 0 .. 2 ] OF INT := [ 17, 4 ];
				test3: ARRAY [ 0 .. 2 ] OF INT := [ 17, 4 ];
			END_VAR
			
			test1[2] := test1[0] + test1[1];
			test2[2] := test2[0] + test2[1];
			test := test1 = test2 AND test1 <> test3;
		'''.evaluateAlgorithm)
	}

	@Test
	def void testArrayCopy() {
		#[17.toIntValue, 4.toIntValue, 21.toIntValue].assertIterableTrace(STAssignment.repeat(2), '''
			VAR_TEMP
				test: ARRAY [ 0 .. 2 ] OF INT;
				test2: ARRAY [ 0 .. 2 ] OF INT := [ 17, 4 ];
			END_VAR
			
			test2[2] := test2[0] + test2[1];
			test := test2;
		'''.evaluateAlgorithm)
	}

	@Test
	def void testArrayCopyBounds() {
		#[17.toIntValue, 4.toIntValue, 21.toIntValue].assertIterableTrace(
			STAssignment.repeat(5), '''
			VAR_TEMP
				test: ARRAY [ 0 .. 2 ] OF INT;
				test2: ARRAY [ 1 .. 3 ] OF INT := [ 17, 4 ];
			END_VAR
			
			test2[3] := test2[1] + test2[2];
			test := test2;
			test[0] := test[1];
			test[1] := test[2];
			test[2] := test2[3];
		'''.evaluateAlgorithm)
	}

	@Test
	def void testArraySubrange() {
		#[17.toIntValue, 4.toIntValue, 21.toIntValue].assertIterableTrace(STAssignment.repeat(3), '''
			VAR_TEMP
				test: ARRAY [ 1 .. 3 ] OF INT;
			END_VAR
			
			test[1] := 17;
			test[2] := 4;
			test[3] := test[1] + test[2];
		'''.evaluateAlgorithm)
	}

	@Test
	def void testArraySubrangeInitializer() {
		#[17.toIntValue, 4.toIntValue, 21.toIntValue].assertIterableTrace(#[STAssignment], '''
			VAR_TEMP
				test: ARRAY [ 1 .. 3 ] OF INT := [ 17, 4 ];
			END_VAR
			
			test[3] := test[1] + test[2];
		'''.evaluateAlgorithm)
	}

	@Test
	def void testIfStatement() {
		21.toIntValue.assertTrace(#[STBinaryExpression, STAssignment], '''
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
		21.toIntValue.assertTrace(STBinaryExpression.repeat(2) + #[STAssignment], '''
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
		21.toIntValue.assertTrace(STBinaryExpression.repeat(2) + #[STAssignment], '''
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
		21.toIntValue.assertTrace(#[STFeatureExpression, STNumericLiteral, STAssignment], '''
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
		21.toIntValue.assertTrace(#[STFeatureExpression] + STNumericLiteral.repeat(2) + #[STAssignment], '''
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
		21.toIntValue.assertTrace(#[STNumericLiteral] + #[STAssignment, STNumericLiteral].repeat(4), '''
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
		21.toIntValue.assertTrace(#[STNumericLiteral] + #[STBinaryExpression, STContinue, STNumericLiteral, // if taken 
		STBinaryExpression, STAssignment, STNumericLiteral].repeat(4), '''
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
		21.toIntValue.assertTrace(#[STNumericLiteral, STBinaryExpression, STAssignment].repeat(4) +
			#[STNumericLiteral, STBinaryExpression, STExit, STAssignment], '''
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
		21.toIntValue.assertTrace(#[STNumericLiteral, STBinaryExpression, STAssignment].repeat(4) +
			#[STNumericLiteral, STBinaryExpression, STReturn], '''
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
		21.toIntValue.assertTrace(#[STNumericLiteral] +
			(#[STNumericLiteral] + #[STAssignment, STNumericLiteral].repeat(2) + #[STNumericLiteral]).repeat(2), '''
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
		21.toIntValue.assertTrace(#[STNumericLiteral] +
			(#[STNumericLiteral] + #[STBinaryExpression, STContinue, STNumericLiteral, // if taken 
			STBinaryExpression, STAssignment, STNumericLiteral].repeat(4) + #[STNumericLiteral]).repeat(3), '''
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
		21.toIntValue.assertTrace(#[STNumericLiteral] +
			(#[STNumericLiteral] + (#[STBinaryExpression, STAssignment, STNumericLiteral]).repeat(4) +
				#[STBinaryExpression, STExit, STAssignment, STNumericLiteral]).repeat(3), '''
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
			STNumericLiteral.repeat(2) + (#[STBinaryExpression, STAssignment, STNumericLiteral]).repeat(4) +
				#[STBinaryExpression, STReturn], '''
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
		21.toIntValue.assertTrace(#[STNumericLiteral] + #[STAssignment, STNumericLiteral].repeat(4), '''
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
		21.toIntValue.assertTrace(#[STNumericLiteral] + #[STAssignment, STNumericLiteral].repeat(4), '''
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
		21.toIntValue.assertTrace(#[STNumericLiteral] + #[STBinaryExpression, STAssignment, STNumericLiteral, // if not taken
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
			#[STNumericLiteral] + #[STBinaryExpression, STAssignment, STNumericLiteral].repeat(4) +
				#[STBinaryExpression, STExit, STAssignment], '''
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
			#[STNumericLiteral] + #[STBinaryExpression, STAssignment, STNumericLiteral].repeat(4) +
				#[STBinaryExpression, STReturn], '''
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
		21.toIntValue.assertTrace(#[STBinaryExpression, STAssignment, STAssignment].repeat(4) + #[STBinaryExpression],
			'''
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
		21.toIntValue.assertTrace(
			#[STBinaryExpression, STAssignment, STBinaryExpression, STAssignment, STBinaryExpression, STAssignment,
				STBinaryExpression, STContinue].repeat(4) + #[STBinaryExpression], '''
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
		21.toIntValue.assertTrace(#[STBinaryExpression, STAssignment, STBinaryExpression, STAssignment].repeat(4) +
			#[STBinaryExpression, STAssignment, STBinaryExpression, STExit, STAssignment], '''
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
		21.toIntValue.assertTrace(#[STBinaryExpression, STAssignment, STBinaryExpression, STAssignment].repeat(4) +
			#[STBinaryExpression, STAssignment, STBinaryExpression, STReturn], '''
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
		21.toIntValue.assertTrace(#[STAssignment, STAssignment, STBinaryExpression].repeat(4), '''
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
		21.toIntValue.assertTrace(
			#[STAssignment, STBinaryExpression, STAssignment, STBinaryExpression, STAssignment, STBinaryExpression,
				STContinue, STBinaryExpression].repeat(4), '''
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
		21.toIntValue.assertTrace(#[STAssignment, STBinaryExpression, STAssignment, STBinaryExpression].repeat(4) +
			#[STAssignment, STBinaryExpression, STExit, STAssignment], '''
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
		21.toIntValue.assertTrace(#[STAssignment, STBinaryExpression, STAssignment, STBinaryExpression].repeat(4) +
			#[STAssignment, STBinaryExpression, STReturn], '''
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
		21.toIntValue.assertTrace(#[STAssignment, STReturn], '''
			VAR_TEMP
				test: INT := INT#17;
			END_VAR
			
			test := test + INT#4;
			RETURN;
			test := INT#0;
		'''.evaluateAlgorithm)
	}

	@Test
	def void testStandardFunctionCall() {
		21.toIntValue.assertTrace(#[STAssignment], '''
			VAR_TEMP
				test: INT;
			END_VAR
			
			test := DINT_TO_INT(DINT#21);
		'''.evaluateAlgorithm)
	}

	@Test
	def void testStandardFunctionCallWithInference() {
		21.toIntValue.assertTrace(#[STAssignment], '''
			VAR_TEMP
				test: INT;
			END_VAR
			
			test := ADD(SINT#17, INT#4);
		'''.evaluateAlgorithm)
	}

	@Test
	def void testStandardFunctionCallWithGenericReturn() {
		5.toIntValue.assertTrace(#[STAssignment], '''
			VAR_TEMP
				test: INT;
			END_VAR
			
			test := LEN('4diac');
		'''.evaluateAlgorithm)
	}

	def static evaluateExpression(CharSequence expression) {
		expression.evaluateExpression(emptyList)
	}

	def static evaluateExpression(CharSequence expression, Variable<?> variable) {
		expression.evaluateExpression(#[variable])
	}

	def static evaluateExpression(CharSequence expression, Collection<Variable<?>> variables) {
		new ScopedExpressionEvaluator(expression.toString, null, variables, null).evaluate
	}

	def static evaluateAlgorithm(CharSequence algorithm) {
		algorithm.evaluateAlgorithm(emptyList)
	}

	def static evaluateAlgorithm(CharSequence algorithm, Iterable<Variable<?>> variables) {
		val alg = LibraryElementFactory.eINSTANCE.createSTAlgorithm
		alg.name = "TEST_ALGORITHM"
		alg.text = algorithm.toString
		val eval = new TracingStructuredTextEvaluator(alg, null, variables, null)
		eval.evaluate
		return eval
	}

	def static void assertTrace(Object expectedResult, Iterable<? extends Class<?>> expectedTrace,
		TracingStructuredTextEvaluator actual) {
		expectedResult.assertEquals(actual.variables.get("test").value)
		(expectedTrace + #[org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithm]).
			assertIterableEquals(
				actual.trace.filterNull.map[class.interfaces.head].filterNull
			)
	}

	def static void assertIterableTrace(Iterable<? extends Object> expectedResult,
		Iterable<? extends Class<?>> expectedTrace, TracingStructuredTextEvaluator actual) {
		expectedResult.assertIterableEquals(actual.variables.get("test").value as ArrayValue)
		(expectedTrace + #[org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithm]).
			assertIterableEquals(
				actual.trace.filterNull.map[class.interfaces.head].filterNull
			)
	}

	def static Iterable<? extends Class<?>> repeat(Class<?> clazz, int repeat) {
		(0 ..< repeat).map[clazz]
	}

	def static Iterable<? extends Class<?>> repeat(Iterable<? extends Class<?>> clazz, int repeat) {
		(0 ..< repeat).map[clazz].flatten
	}

	static class TracingStructuredTextEvaluator extends STAlgorithmEvaluator {
		@Accessors
		final Queue<Object> trace = new ArrayBlockingQueue(1000)

		new(STAlgorithm alg, Variable<?> context, Iterable<Variable<?>> variables, Evaluator parent) {
			super(alg, context, variables, parent)
		}

		override protected <T> T trap(T context) {
			trace.add(context)
			return super.trap(context)
		}
	}
}
