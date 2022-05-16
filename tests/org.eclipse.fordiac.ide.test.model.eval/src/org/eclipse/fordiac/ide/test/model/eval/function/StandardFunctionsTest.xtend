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
package org.eclipse.fordiac.ide.test.model.eval.function

import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.stream.Stream
import org.eclipse.fordiac.ide.model.data.AnyCharType
import org.eclipse.fordiac.ide.model.data.AnyIntType
import org.eclipse.fordiac.ide.model.data.AnyStringType
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes
import org.eclipse.fordiac.ide.model.eval.function.Functions
import org.eclipse.fordiac.ide.model.eval.function.StandardFunctions
import org.eclipse.fordiac.ide.model.eval.value.LRealValue
import org.eclipse.fordiac.ide.model.eval.value.RealValue
import org.eclipse.fordiac.ide.model.eval.value.TimeValue
import org.eclipse.fordiac.ide.model.eval.value.Value
import org.eclipse.fordiac.ide.model.eval.variable.ElementaryVariable
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

import static org.junit.jupiter.params.provider.Arguments.*

import static extension org.eclipse.fordiac.ide.model.eval.function.Functions.*
import static extension org.eclipse.fordiac.ide.model.eval.value.BoolValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.ByteValue.*
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
import static extension org.eclipse.fordiac.ide.model.eval.value.ULIntValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.ValueOperations.*
import static extension org.eclipse.fordiac.ide.model.eval.value.WStringValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.WordValue.*
import static extension org.junit.jupiter.api.Assertions.*

class StandardFunctionsTest {

	static final double NUMERIC_DELTA = 0.0000001

	@Test
	def void testAbs() {
		17.toIntValue.assertEquals(StandardFunctions.invoke("ABS", 17.toIntValue))
		4.toIntValue.assertEquals(StandardFunctions.invoke("ABS", -4.toIntValue))
		0.toLIntValue.assertEquals(StandardFunctions.invoke("ABS", 0.toLIntValue))
	}

	@Test
	def void testSqrt() {
		// REAL
		1.assertEquals(StandardFunctions.invokeUnaryOperator("SQRT", 1.toLRealValue), NUMERIC_DELTA)
		2.assertEquals(StandardFunctions.invokeUnaryOperator("SQRT", 4.toLRealValue), NUMERIC_DELTA)
		3.assertEquals(StandardFunctions.invokeUnaryOperator("SQRT", 9.toLRealValue), NUMERIC_DELTA)
		// LREAL
		1.assertEquals(StandardFunctions.invokeUnaryOperator("SQRT", 1.toRealValue), NUMERIC_DELTA)
		2.assertEquals(StandardFunctions.invokeUnaryOperator("SQRT", 4.toRealValue), NUMERIC_DELTA)
		3.assertEquals(StandardFunctions.invokeUnaryOperator("SQRT", 9.toRealValue), NUMERIC_DELTA)
	}

	@Test
	def void testLn() {
		// REAL
		Float.NEGATIVE_INFINITY.assertEquals(StandardFunctions.invokeUnaryOperator("LN", 0.toRealValue), NUMERIC_DELTA)
		0.assertEquals(StandardFunctions.invokeUnaryOperator("LN", 1.toRealValue), NUMERIC_DELTA)
		1.assertEquals(StandardFunctions.invokeUnaryOperator("LN", Math.E.toRealValue), NUMERIC_DELTA)
		// LREAL
		Double.NEGATIVE_INFINITY.assertEquals(StandardFunctions.invokeUnaryOperator("LN", 0.toLRealValue),
			NUMERIC_DELTA)
		0.assertEquals(StandardFunctions.invokeUnaryOperator("LN", 1.toLRealValue), NUMERIC_DELTA)
		1.assertEquals(StandardFunctions.invokeUnaryOperator("LN", Math.E.toLRealValue), NUMERIC_DELTA)
	}

	@Test
	def void testLog() {
		// REAL
		Float.NEGATIVE_INFINITY.assertEquals(StandardFunctions.invokeUnaryOperator("LOG", 0.toRealValue), NUMERIC_DELTA)
		0.assertEquals(StandardFunctions.invokeUnaryOperator("LOG", 1.toRealValue), NUMERIC_DELTA)
		1.assertEquals(StandardFunctions.invokeUnaryOperator("LOG", 10.toRealValue), NUMERIC_DELTA)
		// LREAL
		Double.NEGATIVE_INFINITY.assertEquals(StandardFunctions.invokeUnaryOperator("LOG", 0.toLRealValue),
			NUMERIC_DELTA)
		0.assertEquals(StandardFunctions.invokeUnaryOperator("LOG", 1.toLRealValue), NUMERIC_DELTA)
		1.assertEquals(StandardFunctions.invokeUnaryOperator("LOG", 10.toLRealValue), NUMERIC_DELTA)
	}

	@Test
	def void testExp() {
		// REAL
		1.assertEquals(StandardFunctions.invokeUnaryOperator("EXP", 0.toRealValue), NUMERIC_DELTA)
		Math.E.assertEquals(StandardFunctions.invokeUnaryOperator("EXP", 1.toRealValue), NUMERIC_DELTA)
		(1 / Math.E).assertEquals(StandardFunctions.invokeUnaryOperator("EXP", (-1).toRealValue), NUMERIC_DELTA)
		// LREAL
		1.assertEquals(StandardFunctions.invokeUnaryOperator("EXP", 0.toLRealValue), NUMERIC_DELTA)
		Math.E.assertEquals(StandardFunctions.invokeUnaryOperator("EXP", 1.toLRealValue), NUMERIC_DELTA)
		(1 / Math.E).assertEquals(StandardFunctions.invokeUnaryOperator("EXP", (-1).toLRealValue), NUMERIC_DELTA)
	}

	@Test
	def void testSin() {
		// REAL
		0.assertEquals(StandardFunctions.invokeUnaryOperator("SIN", 0.toRealValue), NUMERIC_DELTA)
		1.assertEquals(StandardFunctions.invokeUnaryOperator("SIN", (Math.PI / 2).toRealValue), NUMERIC_DELTA)
		0.assertEquals(StandardFunctions.invokeUnaryOperator("SIN", Math.PI.toRealValue), NUMERIC_DELTA)
		// LREAL
		0.assertEquals(StandardFunctions.invokeUnaryOperator("SIN", 0.toLRealValue), NUMERIC_DELTA)
		1.assertEquals(StandardFunctions.invokeUnaryOperator("SIN", (Math.PI / 2).toLRealValue), NUMERIC_DELTA)
		0.assertEquals(StandardFunctions.invokeUnaryOperator("SIN", Math.PI.toLRealValue), NUMERIC_DELTA)
	}

	@Test
	def void testCos() {
		// REAL
		1.assertEquals(StandardFunctions.invokeUnaryOperator("COS", 0.toRealValue), NUMERIC_DELTA)
		0.assertEquals(StandardFunctions.invokeUnaryOperator("COS", (Math.PI / 2).toRealValue), NUMERIC_DELTA)
		(-1).assertEquals(StandardFunctions.invokeUnaryOperator("COS", Math.PI.toRealValue), NUMERIC_DELTA)
		// LREAL
		1.assertEquals(StandardFunctions.invokeUnaryOperator("COS", 0.toLRealValue), NUMERIC_DELTA)
		0.assertEquals(StandardFunctions.invokeUnaryOperator("COS", (Math.PI / 2).toLRealValue), NUMERIC_DELTA)
		(-1).assertEquals(StandardFunctions.invokeUnaryOperator("COS", Math.PI.toLRealValue), NUMERIC_DELTA)
	}

	@Test
	def void testTan() {
		// REAL
		0.assertEquals(StandardFunctions.invokeUnaryOperator("TAN", 0.toRealValue), NUMERIC_DELTA)
		1.assertEquals(StandardFunctions.invokeUnaryOperator("TAN", (Math.PI / 4).toRealValue), NUMERIC_DELTA)
		0.assertEquals(StandardFunctions.invokeUnaryOperator("TAN", Math.PI.toRealValue), NUMERIC_DELTA)
		(-1).assertEquals(StandardFunctions.invokeUnaryOperator("TAN", (3 * (Math.PI / 4)).toRealValue), NUMERIC_DELTA)
		// LREAL
		0.assertEquals(StandardFunctions.invokeUnaryOperator("TAN", 0.toLRealValue), NUMERIC_DELTA)
		1.assertEquals(StandardFunctions.invokeUnaryOperator("TAN", (Math.PI / 4).toLRealValue), NUMERIC_DELTA)
		0.assertEquals(StandardFunctions.invokeUnaryOperator("TAN", Math.PI.toLRealValue), NUMERIC_DELTA)
		(-1).assertEquals(StandardFunctions.invokeUnaryOperator("TAN", (3 * (Math.PI / 4)).toLRealValue), NUMERIC_DELTA)
	}

	@Test
	def void testAsin() {
		// REAL
		0.assertEquals(StandardFunctions.invokeUnaryOperator("ASIN", 0.toRealValue), NUMERIC_DELTA)
		(Math.PI / 6).assertEquals(StandardFunctions.invokeUnaryOperator("ASIN", 0.5.toRealValue), NUMERIC_DELTA)
		(Math.PI / 2).assertEquals(StandardFunctions.invokeUnaryOperator("ASIN", 1.toRealValue), NUMERIC_DELTA)
		// LREAL
		0.assertEquals(StandardFunctions.invokeUnaryOperator("ASIN", 0.toLRealValue), NUMERIC_DELTA)
		(Math.PI / 6).assertEquals(StandardFunctions.invokeUnaryOperator("ASIN", 0.5.toLRealValue), NUMERIC_DELTA)
		(Math.PI / 2).assertEquals(StandardFunctions.invokeUnaryOperator("ASIN", 1.toLRealValue), NUMERIC_DELTA)
	}

	@Test
	def void testAcon() {
		// REAL
		(Math.PI / 2).assertEquals(StandardFunctions.invokeUnaryOperator("ACON", 0.toRealValue), NUMERIC_DELTA)
		(Math.PI / 3).assertEquals(StandardFunctions.invokeUnaryOperator("ACON", 0.5.toRealValue), NUMERIC_DELTA)
		0.assertEquals(StandardFunctions.invokeUnaryOperator("ACON", 1.toRealValue), NUMERIC_DELTA)
		// LREAL
		(Math.PI / 2).assertEquals(StandardFunctions.invokeUnaryOperator("ACON", 0.toLRealValue), NUMERIC_DELTA)
		(Math.PI / 3).assertEquals(StandardFunctions.invokeUnaryOperator("ACON", 0.5.toLRealValue), NUMERIC_DELTA)
		0.assertEquals(StandardFunctions.invokeUnaryOperator("ACON", 1.toLRealValue), NUMERIC_DELTA)
	}

	@Test
	def void testAtan() {
		// REAL
		0.assertEquals(StandardFunctions.invokeUnaryOperator("ATAN", 0.toRealValue), NUMERIC_DELTA)
		(Math.PI / 6).assertEquals(StandardFunctions.invokeUnaryOperator("ATAN", (1 / Math.sqrt(3)).toRealValue),
			NUMERIC_DELTA)
		(Math.PI / 4).assertEquals(StandardFunctions.invokeUnaryOperator("ATAN", 1.toRealValue), NUMERIC_DELTA)
		// LREAL
		0.assertEquals(StandardFunctions.invokeUnaryOperator("ATAN", 0.toLRealValue), NUMERIC_DELTA)
		(Math.PI / 6).assertEquals(StandardFunctions.invokeUnaryOperator("ATAN", (1 / Math.sqrt(3)).toLRealValue),
			NUMERIC_DELTA)
		(Math.PI / 4).assertEquals(StandardFunctions.invokeUnaryOperator("ATAN", 1.toLRealValue), NUMERIC_DELTA)
	}

	@Test
	def void testAtan2() {
		// REAL
		0.assertEquals(StandardFunctions.invokeBinaryOperator("ATAN2", 0.toRealValue, 0.toRealValue), NUMERIC_DELTA)
		0.assertEquals(StandardFunctions.invokeBinaryOperator("ATAN2", 0.toRealValue, 1.toRealValue), NUMERIC_DELTA)
		(Math.PI / 2).assertEquals(StandardFunctions.invokeBinaryOperator("ATAN2", 1.toRealValue, 0.toRealValue),
			NUMERIC_DELTA)
		// LREAL
		0.assertEquals(StandardFunctions.invokeBinaryOperator("ATAN2", 0.toLRealValue, 0.toLRealValue), NUMERIC_DELTA)
		0.assertEquals(StandardFunctions.invokeBinaryOperator("ATAN2", 0.toLRealValue, 1.toLRealValue), NUMERIC_DELTA)
		(Math.PI / 2).assertEquals(StandardFunctions.invokeBinaryOperator("ATAN2", 1.toLRealValue, 0.toLRealValue),
			NUMERIC_DELTA)
	}

	@Test
	def void testAdd() {
		// INT
		0.toIntValue.assertEquals(StandardFunctions.invoke("ADD", 0.toIntValue, 0.toIntValue))
		4.toIntValue.assertEquals(StandardFunctions.invoke("ADD", 2.toIntValue, 2.toIntValue))
		0.toIntValue.assertEquals(StandardFunctions.invoke("ADD", 2.toIntValue, (-2).toIntValue))
		42.toIntValue.assertEquals(StandardFunctions.invoke("ADD", 17.toIntValue, 4.toIntValue, 21.toIntValue))
		// LREAL
		0.assertEquals(StandardFunctions.invokeBinaryOperator("ADD", 0.toLRealValue, 0.toLRealValue), NUMERIC_DELTA)
		4.assertEquals(StandardFunctions.invokeBinaryOperator("ADD", 2.toLRealValue, 2.toLRealValue), NUMERIC_DELTA)
		0.assertEquals(StandardFunctions.invokeBinaryOperator("ADD", 2.toLRealValue, (-2).toLRealValue), NUMERIC_DELTA)
		42.assertEquals(StandardFunctions.invokeOperator("ADD", 17.toLRealValue, 4.toLRealValue, 21.toLRealValue),
			NUMERIC_DELTA)
	}

	@Test
	def void testMul() {
		// INT
		0.toIntValue.assertEquals(StandardFunctions.invoke("MUL", 0.toIntValue, 0.toIntValue))
		4.toIntValue.assertEquals(StandardFunctions.invoke("MUL", 2.toIntValue, 2.toIntValue))
		(-4).toIntValue.assertEquals(StandardFunctions.invoke("MUL", 2.toIntValue, (-2).toIntValue))
		24.toIntValue.assertEquals(StandardFunctions.invoke("MUL", 2.toIntValue, 3.toIntValue, 4.toIntValue))
		// LREAL
		0.assertEquals(StandardFunctions.invokeBinaryOperator("MUL", 0.toLRealValue, 0.toLRealValue), NUMERIC_DELTA)
		4.assertEquals(StandardFunctions.invokeBinaryOperator("MUL", 2.toLRealValue, 2.toLRealValue), NUMERIC_DELTA)
		(-4).assertEquals(StandardFunctions.invokeBinaryOperator("MUL", 2.toLRealValue, (-2).toLRealValue),
			NUMERIC_DELTA)
		24.assertEquals(StandardFunctions.invokeOperator("MUL", 2.toLRealValue, 3.toLRealValue, 4.toLRealValue),
			NUMERIC_DELTA)
	}

	@Test
	def void testSub() {
		// INT
		0.toIntValue.assertEquals(StandardFunctions.invoke("SUB", 0.toIntValue, 0.toIntValue))
		0.toIntValue.assertEquals(StandardFunctions.invoke("SUB", 2.toIntValue, 2.toIntValue))
		4.toIntValue.assertEquals(StandardFunctions.invoke("SUB", 2.toIntValue, (-2).toIntValue))
		// LREAL
		0.assertEquals(StandardFunctions.invokeBinaryOperator("SUB", 0.toLRealValue, 0.toLRealValue), NUMERIC_DELTA)
		0.assertEquals(StandardFunctions.invokeBinaryOperator("SUB", 2.toLRealValue, 2.toLRealValue), NUMERIC_DELTA)
		4.assertEquals(StandardFunctions.invokeBinaryOperator("SUB", 2.toLRealValue, (-2).toLRealValue), NUMERIC_DELTA)
	}

	@Test
	def void testDiv() {
		// INT
		ArithmeticException.assertThrows[StandardFunctions.invoke("DIV", 0.toIntValue, 0.toIntValue)]
		ArithmeticException.assertThrows[StandardFunctions.invoke("DIV", 2.toIntValue, 0.toIntValue)]
		1.toIntValue.assertEquals(StandardFunctions.invoke("DIV", 2.toIntValue, 2.toIntValue))
		(-1).toIntValue.assertEquals(StandardFunctions.invoke("DIV", 2.toIntValue, (-2).toIntValue))
		// LREAL
		Double.NaN.assertEquals(StandardFunctions.invokeBinaryOperator("DIV", 0.toLRealValue, 0.toLRealValue),
			NUMERIC_DELTA)
		Double.POSITIVE_INFINITY.assertEquals(
			StandardFunctions.invokeBinaryOperator("DIV", 2.toLRealValue, 0.toLRealValue), NUMERIC_DELTA)
		1.assertEquals(StandardFunctions.invokeBinaryOperator("DIV", 2.toLRealValue, 2.toLRealValue), NUMERIC_DELTA)
		(-1).assertEquals(StandardFunctions.invokeBinaryOperator("DIV", 2.toLRealValue, (-2).toLRealValue),
			NUMERIC_DELTA)
	}

	@Test
	def void testMod() {
		// INT
		0.toIntValue.assertEquals(StandardFunctions.invoke("MOD", 0.toIntValue, 0.toIntValue))
		0.toIntValue.assertEquals(StandardFunctions.invoke("MOD", 2.toIntValue, 2.toIntValue))
		1.toIntValue.assertEquals(StandardFunctions.invoke("MOD", 3.toIntValue, 2.toIntValue))
		0.toIntValue.assertEquals(StandardFunctions.invoke("MOD", 2.toIntValue, (-2).toIntValue))
		1.toIntValue.assertEquals(StandardFunctions.invoke("MOD", 3.toIntValue, (-2).toIntValue))
	}

	@Test
	def void testExpt() {
		// REAL
		1.assertEquals(StandardFunctions.invokeBinaryOperator("EXPT", 0.toRealValue, 0.toRealValue), NUMERIC_DELTA)
		1.assertEquals(StandardFunctions.invokeBinaryOperator("EXPT", 1.toRealValue, 0.toRealValue), NUMERIC_DELTA)
		8.assertEquals(StandardFunctions.invokeBinaryOperator("EXPT", 2.toRealValue, 3.toRealValue), NUMERIC_DELTA)
		25.assertEquals(StandardFunctions.invokeBinaryOperator("EXPT", 5.toRealValue, 2.toRealValue), NUMERIC_DELTA)
		// LREAL
		1.assertEquals(StandardFunctions.invokeBinaryOperator("EXPT", 0.toLRealValue, 0.toLRealValue), NUMERIC_DELTA)
		1.assertEquals(StandardFunctions.invokeBinaryOperator("EXPT", 1.toLRealValue, 0.toLRealValue), NUMERIC_DELTA)
		8.assertEquals(StandardFunctions.invokeBinaryOperator("EXPT", 2.toLRealValue, 3.toLRealValue), NUMERIC_DELTA)
		25.assertEquals(StandardFunctions.invokeBinaryOperator("EXPT", 5.toLRealValue, 2.toLRealValue), NUMERIC_DELTA)
	}

	@Test
	def void testShiftLeft() {
		6.toByteValue.assertEquals(StandardFunctions.invoke("SHL", 3.toByteValue, 1.toIntValue))
		6.toWordValue.assertEquals(StandardFunctions.invoke("SHL", 3.toWordValue, 1.toIntValue))
		6.toDWordValue.assertEquals(StandardFunctions.invoke("SHL", 3.toDWordValue, 1.toIntValue))
		6.toLWordValue.assertEquals(StandardFunctions.invoke("SHL", 3.toLWordValue, 1.toIntValue))
	}

	@Test
	def void testShiftRight() {
		3.toByteValue.assertEquals(StandardFunctions.invoke("SHR", 6.toByteValue, 1.toIntValue))
		3.toWordValue.assertEquals(StandardFunctions.invoke("SHR", 6.toWordValue, 1.toIntValue))
		3.toDWordValue.assertEquals(StandardFunctions.invoke("SHR", 6.toDWordValue, 1.toIntValue))
		3.toLWordValue.assertEquals(StandardFunctions.invoke("SHR", 6.toLWordValue, 1.toIntValue))
	}

	@Test
	def void testRotateLeft() {
		3.toByteValue.assertEquals(StandardFunctions.invoke("ROL", 0x81.toByteValue, 1.toIntValue))
		3.toWordValue.assertEquals(StandardFunctions.invoke("ROL", 0x8001.toWordValue, 1.toIntValue))
		3.toDWordValue.assertEquals(StandardFunctions.invoke("ROL", 0x80000001.toDWordValue, 1.toIntValue))
		3.toLWordValue.assertEquals(StandardFunctions.invoke("ROL", 0x8000000000000001#L.toLWordValue, 1.toIntValue))
	}

	@Test
	def void testRotateRight() {
		0x81.toByteValue.assertEquals(StandardFunctions.invoke("ROR", 3.toByteValue, 1.toIntValue))
		0x8001.toWordValue.assertEquals(StandardFunctions.invoke("ROR", 3.toWordValue, 1.toIntValue))
		0x80000001.toDWordValue.assertEquals(StandardFunctions.invoke("ROR", 3.toDWordValue, 1.toIntValue))
		0x8000000000000001#L.toLWordValue.assertEquals(StandardFunctions.invoke("ROR", 3.toLWordValue, 1.toIntValue))
	}

	@Test
	def void testAnd() {
		false.toBoolValue.assertEquals(StandardFunctions.invoke("AND", false.toBoolValue, false.toBoolValue))
		false.toBoolValue.assertEquals(StandardFunctions.invoke("AND", true.toBoolValue, false.toBoolValue))
		false.toBoolValue.assertEquals(StandardFunctions.invoke("AND", false.toBoolValue, true.toBoolValue))
		true.toBoolValue.assertEquals(StandardFunctions.invoke("AND", true.toBoolValue, true.toBoolValue))
		0x11.toByteValue.assertEquals(StandardFunctions.invoke("AND", 0x17.toByteValue, 0x71.toByteValue))
		0x301.toWordValue.assertEquals(StandardFunctions.invoke("AND", 0x1701.toWordValue, 0x2363.toWordValue))
		0x201.toDWordValue.assertEquals(StandardFunctions.invoke("AND", 0x74205.toDWordValue, 0x2371.toDWordValue))
		0x250.toLWordValue.assertEquals(StandardFunctions.invoke("AND", 0x74656.toLWordValue, 0x2371.toLWordValue))
		1.toByteValue.assertEquals(StandardFunctions.invoke("AND", 7.toByteValue, 5.toByteValue, 3.toByteValue))
	}

	@Test
	def void testOr() {
		false.toBoolValue.assertEquals(StandardFunctions.invoke("OR", false.toBoolValue, false.toBoolValue))
		true.toBoolValue.assertEquals(StandardFunctions.invoke("OR", true.toBoolValue, false.toBoolValue))
		true.toBoolValue.assertEquals(StandardFunctions.invoke("OR", false.toBoolValue, true.toBoolValue))
		true.toBoolValue.assertEquals(StandardFunctions.invoke("OR", true.toBoolValue, true.toBoolValue))
		21.toByteValue.assertEquals(StandardFunctions.invoke("OR", 17.toByteValue, 4.toByteValue))
		0x3763.toWordValue.assertEquals(StandardFunctions.invoke("OR", 0x1701.toWordValue, 0x2363.toWordValue))
		0x76375.toDWordValue.assertEquals(StandardFunctions.invoke("OR", 0x74205.toDWordValue, 0x2371.toDWordValue))
		0x76777.toLWordValue.assertEquals(StandardFunctions.invoke("OR", 0x74656.toLWordValue, 0x2371.toLWordValue))
		7.toByteValue.assertEquals(StandardFunctions.invoke("OR", 7.toByteValue, 5.toByteValue, 3.toByteValue))
	}

	@Test
	def void testXor() {
		false.toBoolValue.assertEquals(StandardFunctions.invoke("XOR", false.toBoolValue, false.toBoolValue))
		true.toBoolValue.assertEquals(StandardFunctions.invoke("XOR", true.toBoolValue, false.toBoolValue))
		true.toBoolValue.assertEquals(StandardFunctions.invoke("XOR", false.toBoolValue, true.toBoolValue))
		false.toBoolValue.assertEquals(StandardFunctions.invoke("XOR", true.toBoolValue, true.toBoolValue))
		21.toByteValue.assertEquals(StandardFunctions.invoke("XOR", 17.toByteValue, 4.toByteValue))
		0x3462.toWordValue.assertEquals(StandardFunctions.invoke("XOR", 0x1701.toWordValue, 0x2363.toWordValue))
		0x76174.toDWordValue.assertEquals(StandardFunctions.invoke("XOR", 0x74205.toDWordValue, 0x2371.toDWordValue))
		0x76527.toLWordValue.assertEquals(StandardFunctions.invoke("XOR", 0x74656.toLWordValue, 0x2371.toLWordValue))
		1.toByteValue.assertEquals(StandardFunctions.invoke("XOR", 7.toByteValue, 5.toByteValue, 3.toByteValue))
	}

	@Test
	def void testNot() {
		true.toBoolValue.assertEquals(StandardFunctions.invoke("NOT", false.toBoolValue))
		false.toBoolValue.assertEquals(StandardFunctions.invoke("NOT", true.toBoolValue))
		0xe8fe.toWordValue.assertEquals(StandardFunctions.invoke("NOT", 0x1701.toWordValue))
		0x1234567.toDWordValue.assertEquals(StandardFunctions.invoke("NOT", 0xfedcba98.toDWordValue))
		0x22224444ddddbbbb#L.toLWordValue.assertEquals(
			StandardFunctions.invoke("NOT", 0xddddbbbb22224444#L.toLWordValue))
	}

	@Test
	def void testMove() {
		17.toIntValue.assertEquals(StandardFunctions.invoke("MOVE", 17.toIntValue))
		"4".toStringValue.assertEquals(StandardFunctions.invoke("MOVE", "4".toStringValue))
	}

	@Test
	def void testSel() {
		17.toIntValue.assertEquals(StandardFunctions.invoke("SEL", false.toBoolValue, 17.toIntValue, 4.toIntValue))
		"4".toStringValue.assertEquals(
			StandardFunctions.invoke("SEL", true.toBoolValue, "0".toStringValue, "4".toStringValue))
	}

	@Test
	def void testMax() {
		17.toIntValue.assertEquals(StandardFunctions.invoke("MAX", 17.toIntValue, 4.toIntValue))
		"4".toStringValue.assertEquals(StandardFunctions.invoke("MAX", "0".toStringValue, "4".toStringValue))
		21.toIntValue.assertEquals(StandardFunctions.invoke("MAX", 17.toIntValue, 4.toIntValue, 21.toIntValue))
	}

	@Test
	def void testMin() {
		4.toIntValue.assertEquals(StandardFunctions.invoke("MIN", 17.toIntValue, 4.toIntValue))
		"0".toStringValue.assertEquals(StandardFunctions.invoke("MIN", "0".toStringValue, "4".toStringValue))
		4.toIntValue.assertEquals(StandardFunctions.invoke("MIN", 17.toIntValue, 4.toIntValue, 21.toIntValue))
	}

	@Test
	def void testLimit() {
		4.toIntValue.assertEquals(StandardFunctions.invoke("LIMIT", 0.toIntValue, 4.toIntValue, 17.toIntValue))
		17.toIntValue.assertEquals(StandardFunctions.invoke("LIMIT", 0.toIntValue, 21.toIntValue, 17.toIntValue))
		0.toIntValue.assertEquals(StandardFunctions.invoke("LIMIT", 0.toIntValue, (-5).toIntValue, 17.toIntValue))
	}

	@Test
	def void testMux() {
		17.toIntValue.assertEquals(StandardFunctions.invoke("MUX", 0.toIntValue, 17.toIntValue, 4.toIntValue))
		4.toIntValue.assertEquals(StandardFunctions.invoke("MUX", 1.toIntValue, 17.toIntValue, 4.toIntValue))
		21.toIntValue.assertEquals(
			StandardFunctions.invoke("MUX", 2.toIntValue, 17.toIntValue, 4.toIntValue, 21.toIntValue, 42.toIntValue))
	}

	@Test
	def void testGt() {
		true.toBoolValue.assertEquals(StandardFunctions.invoke("GT", 17.toIntValue, 4.toIntValue))
		false.toBoolValue.assertEquals(StandardFunctions.invoke("GT", 4.toIntValue, 17.toIntValue))
		false.toBoolValue.assertEquals(StandardFunctions.invoke("GT", 4.toIntValue, 4.toIntValue))
		false.toBoolValue.assertEquals(StandardFunctions.invoke("GT", 17.toIntValue, 4.toIntValue, 21.toIntValue))
		false.toBoolValue.assertEquals(StandardFunctions.invoke("GT", 4.toIntValue, 17.toIntValue, 21.toIntValue))
		false.toBoolValue.assertEquals(StandardFunctions.invoke("GT", 4.toIntValue, 17.toIntValue, 17.toIntValue))
		true.toBoolValue.assertEquals(StandardFunctions.invoke("GT", 21.toIntValue, 17.toIntValue, 4.toIntValue))
		false.toBoolValue.assertEquals(StandardFunctions.invoke("GT", 21.toIntValue, 17.toIntValue, 17.toIntValue))
		false.toBoolValue.assertEquals(StandardFunctions.invoke("GT", 17.toIntValue, 17.toIntValue, 17.toIntValue))
	}

	@Test
	def void testGe() {
		true.toBoolValue.assertEquals(StandardFunctions.invoke("GE", 17.toIntValue, 4.toIntValue))
		false.toBoolValue.assertEquals(StandardFunctions.invoke("GE", 4.toIntValue, 17.toIntValue))
		true.toBoolValue.assertEquals(StandardFunctions.invoke("GE", 4.toIntValue, 4.toIntValue))
		false.toBoolValue.assertEquals(StandardFunctions.invoke("GE", 17.toIntValue, 4.toIntValue, 21.toIntValue))
		false.toBoolValue.assertEquals(StandardFunctions.invoke("GE", 4.toIntValue, 17.toIntValue, 21.toIntValue))
		false.toBoolValue.assertEquals(StandardFunctions.invoke("GE", 4.toIntValue, 17.toIntValue, 17.toIntValue))
		true.toBoolValue.assertEquals(StandardFunctions.invoke("GE", 21.toIntValue, 17.toIntValue, 4.toIntValue))
		true.toBoolValue.assertEquals(StandardFunctions.invoke("GE", 21.toIntValue, 17.toIntValue, 17.toIntValue))
		true.toBoolValue.assertEquals(StandardFunctions.invoke("GE", 17.toIntValue, 17.toIntValue, 17.toIntValue))
	}

	@Test
	def void testEq() {
		false.toBoolValue.assertEquals(StandardFunctions.invoke("EQ", 17.toIntValue, 4.toIntValue))
		false.toBoolValue.assertEquals(StandardFunctions.invoke("EQ", 4.toIntValue, 17.toIntValue))
		true.toBoolValue.assertEquals(StandardFunctions.invoke("EQ", 4.toIntValue, 4.toIntValue))
		false.toBoolValue.assertEquals(StandardFunctions.invoke("EQ", 17.toIntValue, 4.toIntValue, 21.toIntValue))
		false.toBoolValue.assertEquals(StandardFunctions.invoke("EQ", 4.toIntValue, 17.toIntValue, 21.toIntValue))
		false.toBoolValue.assertEquals(StandardFunctions.invoke("EQ", 4.toIntValue, 17.toIntValue, 17.toIntValue))
		false.toBoolValue.assertEquals(StandardFunctions.invoke("EQ", 21.toIntValue, 17.toIntValue, 4.toIntValue))
		false.toBoolValue.assertEquals(StandardFunctions.invoke("EQ", 21.toIntValue, 17.toIntValue, 17.toIntValue))
		true.toBoolValue.assertEquals(StandardFunctions.invoke("EQ", 17.toIntValue, 17.toIntValue, 17.toIntValue))
	}

	@Test
	def void testLt() {
		false.toBoolValue.assertEquals(StandardFunctions.invoke("LT", 17.toIntValue, 4.toIntValue))
		true.toBoolValue.assertEquals(StandardFunctions.invoke("LT", 4.toIntValue, 17.toIntValue))
		false.toBoolValue.assertEquals(StandardFunctions.invoke("LT", 4.toIntValue, 4.toIntValue))
		false.toBoolValue.assertEquals(StandardFunctions.invoke("LT", 17.toIntValue, 4.toIntValue, 21.toIntValue))
		true.toBoolValue.assertEquals(StandardFunctions.invoke("LT", 4.toIntValue, 17.toIntValue, 21.toIntValue))
		false.toBoolValue.assertEquals(StandardFunctions.invoke("LT", 4.toIntValue, 17.toIntValue, 17.toIntValue))
		false.toBoolValue.assertEquals(StandardFunctions.invoke("LT", 21.toIntValue, 17.toIntValue, 4.toIntValue))
		false.toBoolValue.assertEquals(StandardFunctions.invoke("LT", 21.toIntValue, 17.toIntValue, 17.toIntValue))
		false.toBoolValue.assertEquals(StandardFunctions.invoke("LT", 17.toIntValue, 17.toIntValue, 17.toIntValue))
	}

	@Test
	def void testLe() {
		false.toBoolValue.assertEquals(StandardFunctions.invoke("LE", 17.toIntValue, 4.toIntValue))
		true.toBoolValue.assertEquals(StandardFunctions.invoke("LE", 4.toIntValue, 17.toIntValue))
		true.toBoolValue.assertEquals(StandardFunctions.invoke("LE", 4.toIntValue, 4.toIntValue))
		false.toBoolValue.assertEquals(StandardFunctions.invoke("LE", 17.toIntValue, 4.toIntValue, 21.toIntValue))
		true.toBoolValue.assertEquals(StandardFunctions.invoke("LE", 4.toIntValue, 17.toIntValue, 21.toIntValue))
		true.toBoolValue.assertEquals(StandardFunctions.invoke("LE", 4.toIntValue, 17.toIntValue, 17.toIntValue))
		false.toBoolValue.assertEquals(StandardFunctions.invoke("LE", 21.toIntValue, 17.toIntValue, 4.toIntValue))
		false.toBoolValue.assertEquals(StandardFunctions.invoke("LE", 21.toIntValue, 17.toIntValue, 17.toIntValue))
		true.toBoolValue.assertEquals(StandardFunctions.invoke("LE", 17.toIntValue, 17.toIntValue, 17.toIntValue))
	}

	@Test
	def void testNe() {
		true.toBoolValue.assertEquals(StandardFunctions.invoke("NE", 17.toIntValue, 4.toIntValue))
		true.toBoolValue.assertEquals(StandardFunctions.invoke("NE", 4.toIntValue, 17.toIntValue))
		false.toBoolValue.assertEquals(StandardFunctions.invoke("NE", 4.toIntValue, 4.toIntValue))
	}

	@Test
	def void testLen() {
		// STRING
		0.toULIntValue.assertEquals(StandardFunctions.invoke("LEN", "".toStringValue))
		4.toULIntValue.assertEquals(StandardFunctions.invoke("LEN", "Test".toStringValue))
		9.toULIntValue.assertEquals(StandardFunctions.invoke("LEN", "4diac IDE".toStringValue))
		// WSTRING
		0.toULIntValue.assertEquals(StandardFunctions.invoke("LEN", "".toWStringValue))
		4.toULIntValue.assertEquals(StandardFunctions.invoke("LEN", "Test".toWStringValue))
		9.toULIntValue.assertEquals(StandardFunctions.invoke("LEN", "4diac IDE".toWStringValue))
	}

	@Test
	def void testLeft() {
		// STRING
		"".toStringValue.assertEquals(StandardFunctions.invoke("LEFT", "".toStringValue, 0.toIntValue))
		"Test".toStringValue.assertEquals(StandardFunctions.invoke("LEFT", "TestString".toStringValue, 4.toIntValue))
		"4diac".toStringValue.assertEquals(StandardFunctions.invoke("LEFT", "4diac IDE".toStringValue, 5.toIntValue))
		// WSTRING
		"".toWStringValue.assertEquals(StandardFunctions.invoke("LEFT", "".toWStringValue, 0.toIntValue))
		"Test".toWStringValue.assertEquals(StandardFunctions.invoke("LEFT", "TestString".toWStringValue, 4.toIntValue))
		"4diac".toWStringValue.assertEquals(StandardFunctions.invoke("LEFT", "4diac IDE".toWStringValue, 5.toIntValue))
	}

	@Test
	def void testRight() {
		// STRING
		"".toStringValue.assertEquals(StandardFunctions.invoke("RIGHT", "".toStringValue, 0.toIntValue))
		"String".toStringValue.assertEquals(StandardFunctions.invoke("RIGHT", "TestString".toStringValue, 6.toIntValue))
		"IDE".toStringValue.assertEquals(StandardFunctions.invoke("RIGHT", "4diac IDE".toStringValue, 3.toIntValue))
		// WSTRING
		"".toWStringValue.assertEquals(StandardFunctions.invoke("RIGHT", "".toWStringValue, 0.toIntValue))
		"String".toWStringValue.assertEquals(
			StandardFunctions.invoke("RIGHT", "TestString".toWStringValue, 6.toIntValue))
		"IDE".toWStringValue.assertEquals(StandardFunctions.invoke("RIGHT", "4diac IDE".toWStringValue, 3.toIntValue))
	}

	@Test
	def void testMid() {
		// STRING
		"".toStringValue.assertEquals(StandardFunctions.invoke("MID", "".toStringValue, 0.toIntValue, 1.toIntValue))
		"Str".toStringValue.assertEquals(
			StandardFunctions.invoke("MID", "TestString".toStringValue, 3.toIntValue, 5.toIntValue))
		"diac".toStringValue.assertEquals(
			StandardFunctions.invoke("MID", "4diac IDE".toStringValue, 4.toIntValue, 2.toIntValue))
		// WSTRING
		"".toWStringValue.assertEquals(StandardFunctions.invoke("MID", "".toWStringValue, 0.toIntValue, 1.toIntValue))
		"Str".toWStringValue.assertEquals(
			StandardFunctions.invoke("MID", "TestString".toWStringValue, 3.toIntValue, 5.toIntValue))
		"diac".toWStringValue.assertEquals(
			StandardFunctions.invoke("MID", "4diac IDE".toWStringValue, 4.toIntValue, 2.toIntValue))
	}

	@Test
	def void testConcat() {
		// STRING
		"".toStringValue.assertEquals(StandardFunctions.invoke("CONCAT", "".toStringValue))
		"Test".toStringValue.assertEquals(StandardFunctions.invoke("CONCAT", "Test".toStringValue))
		"".toStringValue.assertEquals(StandardFunctions.invoke("CONCAT", "".toStringValue, "".toStringValue))
		"TestString".toStringValue.assertEquals(
			StandardFunctions.invoke("CONCAT", "Test".toStringValue, "String".toStringValue))
		"4diac IDE".toStringValue.assertEquals(
			StandardFunctions.invoke("CONCAT", "4diac".toStringValue, " ".toStringValue, "IDE".toStringValue))
		// WSTRING
		"".toWStringValue.assertEquals(StandardFunctions.invoke("CONCAT", "".toWStringValue))
		"Test".toWStringValue.assertEquals(StandardFunctions.invoke("CONCAT", "Test".toWStringValue))
		"".toWStringValue.assertEquals(StandardFunctions.invoke("CONCAT", "".toWStringValue, "".toWStringValue))
		"TestString".toWStringValue.assertEquals(
			StandardFunctions.invoke("CONCAT", "Test".toWStringValue, "String".toWStringValue))
		"4diac IDE".toWStringValue.assertEquals(
			StandardFunctions.invoke("CONCAT", "4diac".toWStringValue, " ".toWStringValue, "IDE".toWStringValue))
	}

	@Test
	def void testInsert() {
		// STRING
		"".toStringValue.assertEquals(
			StandardFunctions.invoke("INSERT", "".toStringValue, "".toStringValue, 0.toIntValue))
		"Test".toStringValue.assertEquals(
			StandardFunctions.invoke("INSERT", "Test".toStringValue, "".toStringValue, 0.toIntValue))
		"TestString".toStringValue.assertEquals(
			StandardFunctions.invoke("INSERT", "Test".toStringValue, "String".toStringValue, 4.toIntValue))
		"4diac IDE".toStringValue.assertEquals(
			StandardFunctions.invoke("INSERT", "4diacIDE".toStringValue, " ".toStringValue, 5.toIntValue))
		// WSTRING
		"".toWStringValue.assertEquals(
			StandardFunctions.invoke("INSERT", "".toWStringValue, "".toWStringValue, 0.toIntValue))
		"Test".toWStringValue.assertEquals(
			StandardFunctions.invoke("INSERT", "Test".toWStringValue, "".toWStringValue, 0.toIntValue))
		"TestString".toWStringValue.assertEquals(
			StandardFunctions.invoke("INSERT", "Test".toWStringValue, "String".toWStringValue, 4.toIntValue))
		"4diac IDE".toWStringValue.assertEquals(
			StandardFunctions.invoke("INSERT", "4diacIDE".toWStringValue, " ".toWStringValue, 5.toIntValue))
	}

	@Test
	def void testDelete() {
		// STRING
		"".toStringValue.assertEquals(StandardFunctions.invoke("DELETE", "".toStringValue, 0.toIntValue, 1.toIntValue))
		"Test".toStringValue.assertEquals(
			StandardFunctions.invoke("DELETE", "TestString".toStringValue, 6.toIntValue, 5.toIntValue))
		"4diacIDE".toStringValue.assertEquals(
			StandardFunctions.invoke("DELETE", "4diac IDE".toStringValue, 1.toIntValue, 6.toIntValue))
		// WSTRING
		"".toWStringValue.assertEquals(
			StandardFunctions.invoke("DELETE", "".toWStringValue, 0.toIntValue, 1.toIntValue))
		"Test".toWStringValue.assertEquals(
			StandardFunctions.invoke("DELETE", "TestString".toWStringValue, 6.toIntValue, 5.toIntValue))
		"4diacIDE".toWStringValue.assertEquals(
			StandardFunctions.invoke("DELETE", "4diac IDE".toWStringValue, 1.toIntValue, 6.toIntValue))
	}

	@Test
	def void testReplace() {
		// STRING
		"".toStringValue.assertEquals(
			StandardFunctions.invoke("REPLACE", "".toStringValue, "".toStringValue, 0.toIntValue, 1.toIntValue))
		"TeaString".toStringValue.assertEquals(
			StandardFunctions.invoke("REPLACE", "TestString".toStringValue, "a".toStringValue, 2.toIntValue,
				3.toIntValue))
		"4diac FORTE".toStringValue.assertEquals(
			StandardFunctions.invoke("REPLACE", "4diac IDE".toStringValue, "FORT".toStringValue, 2.toIntValue,
				7.toIntValue))
		// WSTRING
		"".toWStringValue.assertEquals(
			StandardFunctions.invoke("REPLACE", "".toWStringValue, "".toWStringValue, 0.toIntValue, 1.toIntValue))
		"TeaString".toWStringValue.assertEquals(
			StandardFunctions.invoke("REPLACE", "TestString".toWStringValue, "a".toWStringValue, 2.toIntValue,
				3.toIntValue))
		"4diac FORTE".toWStringValue.assertEquals(
			StandardFunctions.invoke("REPLACE", "4diac IDE".toWStringValue, "FORT".toWStringValue, 2.toIntValue,
				7.toIntValue))
	}

	@Test
	def void testFind() {
		// STRING
		1.toULIntValue.assertEquals(StandardFunctions.invoke("FIND", "".toStringValue, "".toStringValue))
		0.toULIntValue.assertEquals(StandardFunctions.invoke("FIND", "".toStringValue, "Test".toStringValue))
		1.toULIntValue.assertEquals(StandardFunctions.invoke("FIND", "Test".toStringValue, "Test".toStringValue))
		5.toULIntValue.assertEquals(
			StandardFunctions.invoke("FIND", "TestString".toStringValue, "String".toStringValue))
		6.toULIntValue.assertEquals(StandardFunctions.invoke("FIND", "4diac IDE".toStringValue, " ".toStringValue))
		// WSTRING
		1.toULIntValue.assertEquals(StandardFunctions.invoke("FIND", "".toWStringValue, "".toWStringValue))
		0.toULIntValue.assertEquals(StandardFunctions.invoke("FIND", "".toWStringValue, "Test".toWStringValue))
		1.toULIntValue.assertEquals(StandardFunctions.invoke("FIND", "Test".toWStringValue, "Test".toWStringValue))
		5.toULIntValue.assertEquals(
			StandardFunctions.invoke("FIND", "TestString".toWStringValue, "String".toWStringValue))
		6.toULIntValue.assertEquals(StandardFunctions.invoke("FIND", "4diac IDE".toWStringValue, " ".toWStringValue))
	}

	@Test
	def void testAddTime() {
		Duration.ofSeconds(21).toTimeValue.assertEquals(
			StandardFunctions.invoke("ADD_TIME", Duration.ofSeconds(17).toTimeValue, Duration.ofSeconds(4).toTimeValue))
	}

	@Test
	def void testAddLTime() {
		Duration.ofSeconds(21).toLTimeValue.assertEquals(
			StandardFunctions.invoke("ADD_LTIME", Duration.ofSeconds(17).toLTimeValue,
				Duration.ofSeconds(4).toLTimeValue))
	}

	@Test
	def void testAddTodTime() {
		LocalTime.of(21, 42).toTimeOfDayValue.assertEquals(
			StandardFunctions.invoke("ADD_TOD_TIME", LocalTime.of(17, 21).toTimeOfDayValue,
				Duration.ofHours(4).plusMinutes(21).toTimeValue))
	}

	@Test
	def void testAddLTodLTime() {
		LocalTime.of(21, 42).toLTimeOfDayValue.assertEquals(
			StandardFunctions.invoke("ADD_LTOD_LTIME", LocalTime.of(17, 21).toLTimeOfDayValue,
				Duration.ofHours(4).plusMinutes(21).toLTimeValue))
	}

	@Test
	def void testAddDTTime() {
		LocalDateTime.of(2017, 4, 21, 21, 42).toDateAndTimeValue.assertEquals(
			StandardFunctions.invoke("ADD_DT_TIME", LocalDateTime.of(2017, 4, 21, 17, 21).toDateAndTimeValue,
				Duration.ofHours(4).plusMinutes(21).toTimeValue))
	}

	@Test
	def void testAddLDTLTime() {
		LocalDateTime.of(2017, 4, 21, 21, 42).toLDateAndTimeValue.assertEquals(
			StandardFunctions.invoke("ADD_LDT_LTIME", LocalDateTime.of(2017, 4, 21, 17, 21).toLDateAndTimeValue,
				Duration.ofHours(4).plusMinutes(21).toLTimeValue))
	}

	@Test
	def void testSubTime() {
		Duration.ofSeconds(17).toTimeValue.assertEquals(
			StandardFunctions.invoke("SUB_TIME", Duration.ofSeconds(21).toTimeValue, Duration.ofSeconds(4).toTimeValue))
	}

	@Test
	def void testSubLTime() {
		Duration.ofSeconds(17).toLTimeValue.assertEquals(
			StandardFunctions.invoke("SUB_LTIME", Duration.ofSeconds(21).toLTimeValue,
				Duration.ofSeconds(4).toLTimeValue))
	}

	@Test
	def void testSubDate() {
		Duration.ofDays(17).toTimeValue.assertEquals(
			StandardFunctions.invoke("SUB_DATE_DATE", LocalDate.of(2021, 4, 21).toDateValue,
				LocalDate.of(2021, 4, 4).toDateValue))
	}

	@Test
	def void testSubLDate() {
		Duration.ofDays(17).toLTimeValue.assertEquals(
			StandardFunctions.invoke("SUB_LDATE_LDATE", LocalDate.of(2021, 4, 21).toLDateValue,
				LocalDate.of(2021, 4, 4).toLDateValue))
	}

	@Test
	def void testSubTodTime() {
		LocalTime.of(17, 21).toTimeOfDayValue.assertEquals(
			StandardFunctions.invoke("SUB_TOD_TIME", LocalTime.of(21, 42).toTimeOfDayValue,
				Duration.ofHours(4).plusMinutes(21).toTimeValue))
	}

	@Test
	def void testSubLTodLTime() {
		LocalTime.of(17, 21).toLTimeOfDayValue.assertEquals(
			StandardFunctions.invoke("SUB_LTOD_LTIME", LocalTime.of(21, 42).toLTimeOfDayValue,
				Duration.ofHours(4).plusMinutes(21).toLTimeValue))
	}

	@Test
	def void testSubTod() {
		Duration.ofHours(4).plusMinutes(21).toTimeValue.assertEquals(
			StandardFunctions.invoke("SUB_TOD_TOD", LocalTime.of(21, 42).toTimeOfDayValue,
				LocalTime.of(17, 21).toTimeOfDayValue))
	}

	@Test
	def void testSubLTod() {
		Duration.ofHours(4).plusMinutes(21).toLTimeValue.assertEquals(
			StandardFunctions.invoke("SUB_LTOD_LTOD", LocalTime.of(21, 42).toLTimeOfDayValue,
				LocalTime.of(17, 21).toLTimeOfDayValue))
	}

	@Test
	def void testSubDTTime() {
		LocalDateTime.of(2017, 4, 21, 17, 21).toDateAndTimeValue.assertEquals(
			StandardFunctions.invoke("SUB_DT_TIME", LocalDateTime.of(2017, 4, 21, 21, 42).toDateAndTimeValue,
				Duration.ofHours(4).plusMinutes(21).toTimeValue))
	}

	@Test
	def void testSubLDTLTime() {
		LocalDateTime.of(2017, 4, 21, 17, 21).toLDateAndTimeValue.assertEquals(
			StandardFunctions.invoke("SUB_LDT_LTIME", LocalDateTime.of(2017, 4, 21, 21, 42).toLDateAndTimeValue,
				Duration.ofHours(4).plusMinutes(21).toLTimeValue))
	}

	@Test
	def void testSubDT() {
		Duration.ofHours(4).plusMinutes(21).toTimeValue.assertEquals(
			StandardFunctions.invoke("SUB_DT_DT", LocalDateTime.of(2017, 4, 21, 21, 42).toDateAndTimeValue,
				LocalDateTime.of(2017, 4, 21, 17, 21).toDateAndTimeValue))
	}

	@Test
	def void testSubLDT() {
		Duration.ofHours(4).plusMinutes(21).toLTimeValue.assertEquals(
			StandardFunctions.invoke("SUB_LDT_LDT", LocalDateTime.of(2017, 4, 21, 21, 42).toLDateAndTimeValue,
				LocalDateTime.of(2017, 4, 21, 17, 21).toLDateAndTimeValue))
	}

	@Test
	def void testMulTime() {
		Duration.ofSeconds(42).toTimeValue.assertEquals(
			StandardFunctions.invoke("MUL_TIME", Duration.ofSeconds(21).toTimeValue, 2.toIntValue))
	}

	@Test
	def void testMulLTime() {
		Duration.ofSeconds(42).toLTimeValue.assertEquals(
			StandardFunctions.invoke("MUL_LTIME", Duration.ofSeconds(21).toLTimeValue, 2.toIntValue))
	}

	@Test
	def void testDivTime() {
		Duration.ofSeconds(21).toTimeValue.assertEquals(
			StandardFunctions.invoke("DIV_TIME", Duration.ofSeconds(42).toTimeValue, 2.toIntValue))
	}

	@Test
	def void testDivLTime() {
		Duration.ofSeconds(21).toLTimeValue.assertEquals(
			StandardFunctions.invoke("DIV_LTIME", Duration.ofSeconds(42).toLTimeValue, 2.toIntValue))
	}

	@Test
	def void testConcatDateTod() {
		LocalDateTime.of(2017, 4, 21, 21, 42).toDateAndTimeValue.assertEquals(
			StandardFunctions.invoke("CONCAT_DATE_TOD", LocalDate.of(2017, 4, 21).toDateValue,
				LocalTime.of(21, 42).toTimeOfDayValue))
	}

	@Test
	def void testConcatDateLTod() {
		LocalDateTime.of(2017, 4, 21, 21, 42).toLDateAndTimeValue.assertEquals(
			StandardFunctions.invoke("CONCAT_DATE_LTOD", LocalDate.of(2017, 4, 21).toDateValue,
				LocalTime.of(21, 42).toLTimeOfDayValue))
	}

	@Test
	def void testConcatDate() {
		LocalDate.of(2017, 4, 21).toDateValue.assertEquals(
			StandardFunctions.invoke("CONCAT_DATE", 2017.toIntValue, 4.toIntValue, 21.toIntValue))
	}

	@Test
	def void testConcatTod() {
		LocalTime.of(17, 4, 21).toTimeOfDayValue.assertEquals(
			StandardFunctions.invoke("CONCAT_TOD", 17.toIntValue, 4.toIntValue, 21.toIntValue, 0.toIntValue))
	}

	@Test
	def void testConcatLTod() {
		LocalTime.of(17, 4, 21).toLTimeOfDayValue.assertEquals(
			StandardFunctions.invoke("CONCAT_LTOD", 17.toIntValue, 4.toIntValue, 21.toIntValue, 0.toIntValue))
	}

	@Test
	def void testConcatDT() {
		LocalDateTime.of(2017, 4, 21, 21, 42).toDateAndTimeValue.assertEquals(
			StandardFunctions.invoke("CONCAT_DT", 2017.toIntValue, 4.toIntValue, 21.toIntValue, 21.toIntValue,
				42.toIntValue, 0.toIntValue, 0.toIntValue))
	}

	@Test
	def void testConcatLDT() {
		LocalDateTime.of(2017, 4, 21, 21, 42).toLDateAndTimeValue.assertEquals(
			StandardFunctions.invoke("CONCAT_LDT", 2017.toIntValue, 4.toIntValue, 21.toIntValue, 21.toIntValue,
				42.toIntValue, 0.toIntValue, 0.toIntValue))
	}

	@Test
	def void testSplitDate() {
		val year = new ElementaryVariable("YEAR", ElementaryTypes.INT)
		val month = new ElementaryVariable("MONTH", ElementaryTypes.INT)
		val day = new ElementaryVariable("DAY", ElementaryTypes.INT)
		StandardFunctions.invoke("SPLIT_DATE", LocalDate.of(2017, 4, 21).toDateValue, year, month, day)
		2017.toIntValue.assertEquals(year.value)
		4.toIntValue.assertEquals(month.value)
		21.toIntValue.assertEquals(day.value)
	}

	@Test
	def void testSplitTod() {
		val hour = new ElementaryVariable("HOUR", ElementaryTypes.INT)
		val minute = new ElementaryVariable("MINUTE", ElementaryTypes.INT)
		val second = new ElementaryVariable("SECOND", ElementaryTypes.INT)
		val milli = new ElementaryVariable("MILLI", ElementaryTypes.INT)
		StandardFunctions.invoke("SPLIT_TOD", LocalTime.of(17, 4, 21).toTimeOfDayValue, hour, minute, second, milli)
		17.toIntValue.assertEquals(hour.value)
		4.toIntValue.assertEquals(minute.value)
		21.toIntValue.assertEquals(second.value)
		0.toIntValue.assertEquals(milli.value)
	}

	@Test
	def void testSplitLTod() {
		val hour = new ElementaryVariable("HOUR", ElementaryTypes.INT)
		val minute = new ElementaryVariable("MINUTE", ElementaryTypes.INT)
		val second = new ElementaryVariable("SECOND", ElementaryTypes.INT)
		val milli = new ElementaryVariable("MILLI", ElementaryTypes.INT)
		StandardFunctions.invoke("SPLIT_LTOD", LocalTime.of(17, 4, 21).toLTimeOfDayValue, hour, minute, second, milli)
		17.toIntValue.assertEquals(hour.value)
		4.toIntValue.assertEquals(minute.value)
		21.toIntValue.assertEquals(second.value)
		0.toIntValue.assertEquals(milli.value)
	}

	@Test
	def void testSplitDT() {
		val year = new ElementaryVariable("YEAR", ElementaryTypes.INT)
		val month = new ElementaryVariable("MONTH", ElementaryTypes.INT)
		val day = new ElementaryVariable("DAY", ElementaryTypes.INT)
		val hour = new ElementaryVariable("HOUR", ElementaryTypes.INT)
		val minute = new ElementaryVariable("MINUTE", ElementaryTypes.INT)
		val second = new ElementaryVariable("SECOND", ElementaryTypes.INT)
		val milli = new ElementaryVariable("MILLI", ElementaryTypes.INT)
		StandardFunctions.invoke("SPLIT_DT",
			LocalDateTime.of(2017, 4, 21, 21, 42, 17, 7 * 1_000_000).toDateAndTimeValue, year, month, day, hour, minute,
			second, milli)
		2017.toIntValue.assertEquals(year.value)
		4.toIntValue.assertEquals(month.value)
		21.toIntValue.assertEquals(day.value)
		21.toIntValue.assertEquals(hour.value)
		42.toIntValue.assertEquals(minute.value)
		17.toIntValue.assertEquals(second.value)
		7.toIntValue.assertEquals(milli.value)
	}

	@Test
	def void testSplitLDT() {
		val year = new ElementaryVariable("YEAR", ElementaryTypes.INT)
		val month = new ElementaryVariable("MONTH", ElementaryTypes.INT)
		val day = new ElementaryVariable("DAY", ElementaryTypes.INT)
		val hour = new ElementaryVariable("HOUR", ElementaryTypes.INT)
		val minute = new ElementaryVariable("MINUTE", ElementaryTypes.INT)
		val second = new ElementaryVariable("SECOND", ElementaryTypes.INT)
		val milli = new ElementaryVariable("MILLI", ElementaryTypes.INT)
		StandardFunctions.invoke("SPLIT_LDT",
			LocalDateTime.of(2017, 4, 21, 21, 42, 17, 7 * 1_000_000).toLDateAndTimeValue, year, month, day, hour,
			minute, second, milli)
		2017.toIntValue.assertEquals(year.value)
		4.toIntValue.assertEquals(month.value)
		21.toIntValue.assertEquals(day.value)
		21.toIntValue.assertEquals(hour.value)
		42.toIntValue.assertEquals(minute.value)
		17.toIntValue.assertEquals(second.value)
		7.toIntValue.assertEquals(milli.value)
	}

	@Test
	def void testDayOfWeek() {
		5.toSIntValue.assertEquals(StandardFunctions.invoke("DAY_OF_WEEK", LocalDate.of(2022, 5, 13).toDateValue))
	}

	@Test
	def void testToBigEndian() {
		UnsupportedOperationException.assertThrows[StandardFunctions.invoke("TO_BIG_ENDIAN", 17.toIntValue)]
	}

	@Test
	def void testToLittleEndian() {
		UnsupportedOperationException.assertThrows[StandardFunctions.invoke("TO_LITTLE_ENDIAN", 17.toIntValue)]
	}

	@Test
	def void testFromBigEndian() {
		UnsupportedOperationException.assertThrows[StandardFunctions.invoke("FROM_BIG_ENDIAN", 17.toIntValue)]
	}

	@Test
	def void testFromLittleEndian() {
		UnsupportedOperationException.assertThrows[StandardFunctions.invoke("FROM_LITTLE_ENDIAN", 17.toIntValue)]
	}

	@Test
	def void testIsValid() {
		true.toBoolValue.assertEquals(StandardFunctions.invoke("IS_VALID", 17.toLRealValue))
		false.toBoolValue.assertEquals(StandardFunctions.invoke("IS_VALID", Double.POSITIVE_INFINITY.toLRealValue))
		false.toBoolValue.assertEquals(StandardFunctions.invoke("IS_VALID", Double.NEGATIVE_INFINITY.toLRealValue))
		false.toBoolValue.assertEquals(StandardFunctions.invoke("IS_VALID", Double.NaN.toLRealValue))
	}

	@Test
	def void testIsValidBCD() {
		UnsupportedOperationException.assertThrows[StandardFunctions.invoke("IS_VALID_BCD", 17.toByteValue)]
	}

	@Test
	def void testTimeConversion() {
		17L.toLIntValue.assertEquals(StandardFunctions.invoke("TIME_IN_S_TO_LINT", Duration.ofSeconds(17).toTimeValue))
		(17L * 1000).toLIntValue.assertEquals(
			StandardFunctions.invoke("TIME_IN_MS_TO_LINT", Duration.ofSeconds(17).toTimeValue))
		(17L * 1_000_000).toLIntValue.assertEquals(
			StandardFunctions.invoke("TIME_IN_US_TO_LINT", Duration.ofSeconds(17).toTimeValue))
		(17L * 1_000_000_000).toLIntValue.assertEquals(
			StandardFunctions.invoke("TIME_IN_NS_TO_LINT", Duration.ofSeconds(17).toTimeValue))
		17L.toULIntValue.assertEquals(
			StandardFunctions.invoke("TIME_IN_S_TO_ULINT", Duration.ofSeconds(17).toTimeValue))
		(17L * 1000).toULIntValue.assertEquals(
			StandardFunctions.invoke("TIME_IN_MS_TO_ULINT", Duration.ofSeconds(17).toTimeValue))
		(17L * 1_000_000).toULIntValue.assertEquals(
			StandardFunctions.invoke("TIME_IN_US_TO_ULINT", Duration.ofSeconds(17).toTimeValue))
		(17L * 1_000_000_000).toULIntValue.assertEquals(
			StandardFunctions.invoke("TIME_IN_NS_TO_ULINT", Duration.ofSeconds(17).toTimeValue))
		(17.0).toLRealValue.assertEquals(
			StandardFunctions.invoke("TIME_IN_S_TO_LREAL", Duration.ofSeconds(17).toTimeValue))
		(17.0 * 1000).toLRealValue.assertEquals(
			StandardFunctions.invoke("TIME_IN_MS_TO_LREAL", Duration.ofSeconds(17).toTimeValue))
		(17.0 * 1_000_000).toLRealValue.assertEquals(
			StandardFunctions.invoke("TIME_IN_US_TO_LREAL", Duration.ofSeconds(17).toTimeValue))
		(17.0 * 1_000_000_000).toLRealValue.assertEquals(
			StandardFunctions.invoke("TIME_IN_NS_TO_LREAL", Duration.ofSeconds(17).toTimeValue))
	}

	@Test
	def void testNowMonotonic() {
		val before = System.nanoTime()
		val now = StandardFunctions.invoke("NOW_MONOTONIC") as TimeValue
		val after = System.nanoTime()
		assertTrue(before <= now.longValue)
		assertTrue(after >= now.longValue)
	}

	@ParameterizedTest(name="{index}: {0} as {1}")
	@MethodSource("typeArgumentsCartesianProvider")
	def void testConversions(String typeName, String castTypeName) {
		val type = ElementaryTypes.getTypeByName(typeName)
		val castType = ElementaryTypes.getTypeByName(castTypeName)
		val exists = try {
				StandardFunctions.findMethodFromDataTypes('''«typeName»_TO_«castTypeName»''', type)
				true
			} catch (NoSuchMethodError e) {
				false
			}
		if (exists) {
			(if (castType instanceof AnyStringType && type instanceof AnyCharType)
				"\u0000".wrapValue(castType)
			else
				castType.defaultValue
			).assertEquals(StandardFunctions.invoke('''«typeName»_TO_«castTypeName»''', type.defaultValue))
		}
	}

	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeAnyIntArgumentsProvider")
	def void testTruncConversions(String typeName) {
		val type = ElementaryTypes.getTypeByName(typeName)
		// REAL
		type.defaultValue.assertEquals(StandardFunctions.invoke('''TRUNC_«typeName»''', 0.toRealValue))
		type.defaultValue.assertEquals(StandardFunctions.invoke('''REAL_TRUNC_«typeName»''', 0.toRealValue))
		17.wrapValue(type).assertEquals(StandardFunctions.invoke('''TRUNC_«typeName»''', 17.toRealValue))
		17.wrapValue(type).assertEquals(StandardFunctions.invoke('''REAL_TRUNC_«typeName»''', 17.toRealValue))
		// LREAL
		type.defaultValue.assertEquals(StandardFunctions.invoke('''TRUNC_«typeName»''', 0.toLRealValue))
		type.defaultValue.assertEquals(StandardFunctions.invoke('''LREAL_TRUNC_«typeName»''', 0.toLRealValue))
		17.wrapValue(type).assertEquals(StandardFunctions.invoke('''TRUNC_«typeName»''', 17.toLRealValue))
		17.wrapValue(type).assertEquals(StandardFunctions.invoke('''LREAL_TRUNC_«typeName»''', 17.toLRealValue))
	}

	def static Stream<String> typeAnyIntArgumentsProvider() {
		DataTypeLibrary.nonUserDefinedDataTypes.stream.filter[it instanceof AnyIntType].map[name]
	}

	def static Stream<Arguments> typeArgumentsCartesianProvider() {
		DataTypeLibrary.nonUserDefinedDataTypes.stream.flatMap [ first |
			DataTypeLibrary.nonUserDefinedDataTypes.stream.map[second|arguments(first.name, second.name)]
		]
	}

	def <T extends Value> T invokeUnaryOperator(Class<? extends Functions> clazz, String name, T argument) {
		clazz.invoke(name, argument) as T
	}

	def <T extends Value> T invokeBinaryOperator(Class<? extends Functions> clazz, String name, T argument1,
		T argument2) {
		clazz.invoke(name, argument1, argument2) as T
	}

	def <T extends Value> T invokeOperator(Class<? extends Functions> clazz, String name, T... arguments) {
		clazz.invoke(name, arguments) as T
	}

	def void assertEquals(double expected, RealValue actual, double delta) {
		assertEquals(expected, actual.doubleValue, delta)
	}

	def void assertEquals(double expected, LRealValue actual, double delta) {
		assertEquals(expected, actual.doubleValue, delta)
	}
}
