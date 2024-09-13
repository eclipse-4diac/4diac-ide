/**
 * Copyright (c) 2022, 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.test.model.eval.function;

import static org.eclipse.fordiac.ide.model.eval.value.BoolValue.FALSE;
import static org.eclipse.fordiac.ide.model.eval.value.BoolValue.TRUE;
import static org.eclipse.fordiac.ide.model.eval.value.BoolValue.toBoolValue;
import static org.eclipse.fordiac.ide.model.eval.value.ByteValue.toByteValue;
import static org.eclipse.fordiac.ide.model.eval.value.CharValue.toCharValue;
import static org.eclipse.fordiac.ide.model.eval.value.DIntValue.toDIntValue;
import static org.eclipse.fordiac.ide.model.eval.value.DWordValue.toDWordValue;
import static org.eclipse.fordiac.ide.model.eval.value.DateAndTimeValue.toDateAndTimeValue;
import static org.eclipse.fordiac.ide.model.eval.value.DateValue.toDateValue;
import static org.eclipse.fordiac.ide.model.eval.value.IntValue.toIntValue;
import static org.eclipse.fordiac.ide.model.eval.value.LDateAndTimeValue.toLDateAndTimeValue;
import static org.eclipse.fordiac.ide.model.eval.value.LDateValue.toLDateValue;
import static org.eclipse.fordiac.ide.model.eval.value.LIntValue.toLIntValue;
import static org.eclipse.fordiac.ide.model.eval.value.LRealValue.toLRealValue;
import static org.eclipse.fordiac.ide.model.eval.value.LTimeOfDayValue.toLTimeOfDayValue;
import static org.eclipse.fordiac.ide.model.eval.value.LTimeValue.toLTimeValue;
import static org.eclipse.fordiac.ide.model.eval.value.LWordValue.toLWordValue;
import static org.eclipse.fordiac.ide.model.eval.value.RealValue.toRealValue;
import static org.eclipse.fordiac.ide.model.eval.value.SIntValue.toSIntValue;
import static org.eclipse.fordiac.ide.model.eval.value.StringValue.toStringValue;
import static org.eclipse.fordiac.ide.model.eval.value.TimeOfDayValue.toTimeOfDayValue;
import static org.eclipse.fordiac.ide.model.eval.value.TimeValue.toTimeValue;
import static org.eclipse.fordiac.ide.model.eval.value.UDIntValue.toUDIntValue;
import static org.eclipse.fordiac.ide.model.eval.value.UIntValue.toUIntValue;
import static org.eclipse.fordiac.ide.model.eval.value.ULIntValue.toULIntValue;
import static org.eclipse.fordiac.ide.model.eval.value.USIntValue.toUSIntValue;
import static org.eclipse.fordiac.ide.model.eval.value.ValueOperations.castValue;
import static org.eclipse.fordiac.ide.model.eval.value.ValueOperations.defaultValue;
import static org.eclipse.fordiac.ide.model.eval.value.ValueOperations.negate;
import static org.eclipse.fordiac.ide.model.eval.value.ValueOperations.wrapValue;
import static org.eclipse.fordiac.ide.model.eval.value.WCharValue.toWCharValue;
import static org.eclipse.fordiac.ide.model.eval.value.WStringValue.toWStringValue;
import static org.eclipse.fordiac.ide.model.eval.value.WordValue.toWordValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.fordiac.ide.globalconstantseditor.GlobalConstantsStandaloneSetup;
import org.eclipse.fordiac.ide.model.data.AnyBitType;
import org.eclipse.fordiac.ide.model.data.AnyCharType;
import org.eclipse.fordiac.ide.model.data.AnyIntType;
import org.eclipse.fordiac.ide.model.data.AnyMagnitudeType;
import org.eclipse.fordiac.ide.model.data.AnyRealType;
import org.eclipse.fordiac.ide.model.data.AnyStringType;
import org.eclipse.fordiac.ide.model.data.AnyUnsignedType;
import org.eclipse.fordiac.ide.model.data.BoolType;
import org.eclipse.fordiac.ide.model.data.ByteType;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.DwordType;
import org.eclipse.fordiac.ide.model.data.LwordType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.data.UdintType;
import org.eclipse.fordiac.ide.model.data.UintType;
import org.eclipse.fordiac.ide.model.data.UlintType;
import org.eclipse.fordiac.ide.model.data.UsintType;
import org.eclipse.fordiac.ide.model.data.WordType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.eval.function.Functions;
import org.eclipse.fordiac.ide.model.eval.function.StandardFunctions;
import org.eclipse.fordiac.ide.model.eval.st.StructuredTextEvaluatorFactory;
import org.eclipse.fordiac.ide.model.eval.value.AnyElementaryValue;
import org.eclipse.fordiac.ide.model.eval.value.ArrayValue;
import org.eclipse.fordiac.ide.model.eval.value.LRealValue;
import org.eclipse.fordiac.ide.model.eval.value.RealValue;
import org.eclipse.fordiac.ide.model.eval.value.StringValue;
import org.eclipse.fordiac.ide.model.eval.value.StructValue;
import org.eclipse.fordiac.ide.model.eval.value.TimeValue;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.eval.value.WStringValue;
import org.eclipse.fordiac.ide.model.eval.variable.ArrayVariable;
import org.eclipse.fordiac.ide.model.eval.variable.ElementaryVariable;
import org.eclipse.fordiac.ide.model.eval.variable.StructVariable;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.structuredtextalgorithm.STAlgorithmStandaloneSetup;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.STFunctionStandaloneSetup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.google.common.base.Predicates;

@SuppressWarnings({ "static-method", "nls", "java:S5960" })
class StandardFunctionsTest {
	private static final double NUMERIC_DELTA = 0.0000001;

	@BeforeAll
	@SuppressWarnings("unused")
	static void setupXtext() {
		new DataTypeLibrary();
		GlobalConstantsStandaloneSetup.doSetup();
		STFunctionStandaloneSetup.doSetup();
		STAlgorithmStandaloneSetup.doSetup();
		StructuredTextEvaluatorFactory.register();
	}

	@Test
	void testAbs() throws Throwable {
		assertEquals(toIntValue((short) 17), Functions.invoke(StandardFunctions.class, "ABS", toIntValue((short) 17)));
		assertEquals(toIntValue((short) 4),
				Functions.invoke(StandardFunctions.class, "ABS", negate(toIntValue((short) 4))));
		assertEquals(toLIntValue(0), Functions.invoke(StandardFunctions.class, "ABS", toLIntValue(0)));
	}

	@Test
	void testSqrt() throws Throwable {
		assertEqualsWithDelta(1.0, invokeUnaryOperator("SQRT", toLRealValue(1)));
		assertEqualsWithDelta(2.0, invokeUnaryOperator("SQRT", toLRealValue(4)));
		assertEqualsWithDelta(3.0, invokeUnaryOperator("SQRT", toLRealValue(9)));
		assertEqualsWithDelta(1.0, invokeUnaryOperator("SQRT", toRealValue(1)));
		assertEqualsWithDelta(2.0, invokeUnaryOperator("SQRT", toRealValue(4)));
		assertEqualsWithDelta(3.0, invokeUnaryOperator("SQRT", toRealValue(9)));
	}

	@Test
	void testLn() throws Throwable {
		assertEqualsWithDelta(Float.NEGATIVE_INFINITY, invokeUnaryOperator("LN", toRealValue(0)));
		assertEqualsWithDelta(0.0, invokeUnaryOperator("LN", toRealValue(1)));
		assertEqualsWithDelta(1.0, invokeUnaryOperator("LN", toRealValue((float) Math.E)));
		assertEqualsWithDelta(Double.NEGATIVE_INFINITY, invokeUnaryOperator("LN", toLRealValue(0)));
		assertEqualsWithDelta(0.0, invokeUnaryOperator("LN", toLRealValue(1)));
		assertEqualsWithDelta(1.0, invokeUnaryOperator("LN", toLRealValue(Math.E)));
	}

	@Test
	void testLog() throws Throwable {
		assertEqualsWithDelta(Float.NEGATIVE_INFINITY, invokeUnaryOperator("LOG", toRealValue(0)));
		assertEqualsWithDelta(0.0, invokeUnaryOperator("LOG", toRealValue(1)));
		assertEqualsWithDelta(1.0, invokeUnaryOperator("LOG", toRealValue(10)));
		assertEqualsWithDelta(Double.NEGATIVE_INFINITY, invokeUnaryOperator("LOG", toLRealValue(0)));
		assertEqualsWithDelta(0.0, invokeUnaryOperator("LOG", toLRealValue(1)));
		assertEqualsWithDelta(1.0, invokeUnaryOperator("LOG", toLRealValue(10)));
	}

	@Test
	void testExp() throws Throwable {
		assertEqualsWithDelta(1.0, invokeUnaryOperator("EXP", toRealValue(0)));
		assertEqualsWithDelta(Math.E, invokeUnaryOperator("EXP", toRealValue(1)));
		assertEqualsWithDelta((1 / Math.E), invokeUnaryOperator("EXP", toRealValue((-1))));
		assertEqualsWithDelta(1.0, invokeUnaryOperator("EXP", toLRealValue(0)));
		assertEqualsWithDelta(Math.E, invokeUnaryOperator("EXP", toLRealValue(1)));
		assertEqualsWithDelta((1 / Math.E), invokeUnaryOperator("EXP", toLRealValue((-1))));
	}

	@Test
	void testSin() throws Throwable {
		assertEqualsWithDelta(0.0, invokeUnaryOperator("SIN", toRealValue(0)));
		assertEqualsWithDelta(1.0, invokeUnaryOperator("SIN", toRealValue((float) (Math.PI / 2))));
		assertEqualsWithDelta(0.0, invokeUnaryOperator("SIN", toRealValue((float) Math.PI)));
		assertEqualsWithDelta(0.0, invokeUnaryOperator("SIN", toLRealValue(0)));
		assertEqualsWithDelta(1.0, invokeUnaryOperator("SIN", toLRealValue((Math.PI / 2))));
		assertEqualsWithDelta(0.0, invokeUnaryOperator("SIN", toLRealValue(Math.PI)));
	}

	@Test
	void testCos() throws Throwable {
		assertEqualsWithDelta(1.0, invokeUnaryOperator("COS", toRealValue(0)));
		assertEqualsWithDelta(0.0, invokeUnaryOperator("COS", toRealValue((float) (Math.PI / 2))));
		assertEqualsWithDelta((-1), invokeUnaryOperator("COS", toRealValue((float) Math.PI)));
		assertEqualsWithDelta(1.0, invokeUnaryOperator("COS", toLRealValue(0)));
		assertEqualsWithDelta(0.0, invokeUnaryOperator("COS", toLRealValue((Math.PI / 2))));
		assertEqualsWithDelta((-1), invokeUnaryOperator("COS", toLRealValue(Math.PI)));
	}

	@Test
	void testTan() throws Throwable {
		assertEqualsWithDelta(0.0, invokeUnaryOperator("TAN", toRealValue(0)));
		assertEqualsWithDelta(1.0, invokeUnaryOperator("TAN", toRealValue((float) (Math.PI / 4))));
		assertEqualsWithDelta(0.0, invokeUnaryOperator("TAN", toRealValue((float) Math.PI)));
		assertEqualsWithDelta((-1), invokeUnaryOperator("TAN", toRealValue((float) (3 * (Math.PI / 4)))));
		assertEqualsWithDelta(0.0, invokeUnaryOperator("TAN", toLRealValue(0)));
		assertEqualsWithDelta(1.0, invokeUnaryOperator("TAN", toLRealValue((Math.PI / 4))));
		assertEqualsWithDelta(0.0, invokeUnaryOperator("TAN", toLRealValue(Math.PI)));
		assertEqualsWithDelta((-1), invokeUnaryOperator("TAN", toLRealValue((3 * (Math.PI / 4)))));
	}

	@Test
	void testAsin() throws Throwable {
		assertEqualsWithDelta(0.0, invokeUnaryOperator("ASIN", toRealValue(0)));
		assertEqualsWithDelta((Math.PI / 6), invokeUnaryOperator("ASIN", toRealValue((float) 0.5)));
		assertEqualsWithDelta((Math.PI / 2), invokeUnaryOperator("ASIN", toRealValue(1)));
		assertEqualsWithDelta(0.0, invokeUnaryOperator("ASIN", toLRealValue(0)));
		assertEqualsWithDelta((Math.PI / 6), invokeUnaryOperator("ASIN", toLRealValue(0.5)));
		assertEqualsWithDelta((Math.PI / 2), invokeUnaryOperator("ASIN", toLRealValue(1)));
	}

	@Test
	void testAcos() throws Throwable {
		assertEqualsWithDelta((Math.PI / 2), invokeUnaryOperator("ACOS", toRealValue(0)));
		assertEqualsWithDelta((Math.PI / 3), invokeUnaryOperator("ACOS", toRealValue((float) 0.5)));
		assertEqualsWithDelta(0.0, invokeUnaryOperator("ACOS", toRealValue(1)));
		assertEqualsWithDelta((Math.PI / 2), invokeUnaryOperator("ACOS", toLRealValue(0)));
		assertEqualsWithDelta((Math.PI / 3), invokeUnaryOperator("ACOS", toLRealValue(0.5)));
		assertEqualsWithDelta(0.0, invokeUnaryOperator("ACOS", toLRealValue(1)));
	}

	@Test
	void testAtan() throws Throwable {
		assertEqualsWithDelta(0.0, invokeUnaryOperator("ATAN", toRealValue(0)));
		assertEqualsWithDelta((Math.PI / 6), invokeUnaryOperator("ATAN", toRealValue((float) (1 / Math.sqrt(3)))));
		assertEqualsWithDelta((Math.PI / 4), invokeUnaryOperator("ATAN", toRealValue(1)));
		assertEqualsWithDelta(0.0, invokeUnaryOperator("ATAN", toLRealValue(0)));
		assertEqualsWithDelta((Math.PI / 6), invokeUnaryOperator("ATAN", toLRealValue((1 / Math.sqrt(3)))));
		assertEqualsWithDelta((Math.PI / 4), invokeUnaryOperator("ATAN", toLRealValue(1)));
	}

	@Test
	void testAtan2() throws Throwable {
		assertEqualsWithDelta(0.0, invokeBinaryOperator("ATAN2", toRealValue(0), toRealValue(0)));
		assertEqualsWithDelta(0.0, invokeBinaryOperator("ATAN2", toRealValue(0), toRealValue(1)));
		assertEqualsWithDelta((Math.PI / 2), invokeBinaryOperator("ATAN2", toRealValue(1), toRealValue(0)));
		assertEqualsWithDelta(0.0, invokeBinaryOperator("ATAN2", toLRealValue(0), toLRealValue(0)));
		assertEqualsWithDelta(0.0, invokeBinaryOperator("ATAN2", toLRealValue(0), toLRealValue(1)));
		assertEqualsWithDelta((Math.PI / 2), invokeBinaryOperator("ATAN2", toLRealValue(1), toLRealValue(0)));
	}

	@Test
	void testAdd() throws Throwable {
		assertEquals(toIntValue((short) 0),
				Functions.invoke(StandardFunctions.class, "ADD", toIntValue((short) 0), toIntValue((short) 0)));
		assertEquals(toIntValue((short) 4),
				Functions.invoke(StandardFunctions.class, "ADD", toIntValue((short) 2), toIntValue((short) 2)));
		assertEquals(toIntValue((short) 0),
				Functions.invoke(StandardFunctions.class, "ADD", toIntValue((short) 2), toIntValue((short) (-2))));
		assertEquals(toIntValue((short) 42), Functions.invoke(StandardFunctions.class, "ADD", toIntValue((short) 17),
				toIntValue((short) 4), toIntValue((short) 21)));
		assertEqualsWithDelta(0.0, invokeBinaryOperator("ADD", toLRealValue(0), toLRealValue(0)));
		assertEqualsWithDelta(4.0, invokeBinaryOperator("ADD", toLRealValue(2), toLRealValue(2)));
		assertEqualsWithDelta(0.0, invokeBinaryOperator("ADD", toLRealValue(2), toLRealValue((-2))));
		assertEqualsWithDelta(42.0, invokeOperator("ADD", toLRealValue(17), toLRealValue(4), toLRealValue(21)));
	}

	@Test
	void testMul() throws Throwable {
		assertEquals(toIntValue((short) 0),
				Functions.invoke(StandardFunctions.class, "MUL", toIntValue((short) 0), toIntValue((short) 0)));
		assertEquals(toIntValue((short) 4),
				Functions.invoke(StandardFunctions.class, "MUL", toIntValue((short) 2), toIntValue((short) 2)));
		assertEquals(toIntValue((short) (-4)),
				Functions.invoke(StandardFunctions.class, "MUL", toIntValue((short) 2), toIntValue((short) (-2))));
		assertEquals(toIntValue((short) 24), Functions.invoke(StandardFunctions.class, "MUL", toIntValue((short) 2),
				toIntValue((short) 3), toIntValue((short) 4)));
		assertEqualsWithDelta(0.0, invokeBinaryOperator("MUL", toLRealValue(0), toLRealValue(0)));
		assertEqualsWithDelta(4.0, invokeBinaryOperator("MUL", toLRealValue(2), toLRealValue(2)));
		assertEqualsWithDelta((-4), invokeBinaryOperator("MUL", toLRealValue(2), toLRealValue((-2))));
		assertEqualsWithDelta(24.0, invokeOperator("MUL", toLRealValue(2), toLRealValue(3), toLRealValue(4)));
	}

	@Test
	void testSub() throws Throwable {
		assertEquals(toIntValue((short) 0),
				Functions.invoke(StandardFunctions.class, "SUB", toIntValue((short) 0), toIntValue((short) 0)));
		assertEquals(toIntValue((short) 0),
				Functions.invoke(StandardFunctions.class, "SUB", toIntValue((short) 2), toIntValue((short) 2)));
		assertEquals(toIntValue((short) 4),
				Functions.invoke(StandardFunctions.class, "SUB", toIntValue((short) 2), toIntValue((short) (-2))));
		assertEqualsWithDelta(0.0, invokeBinaryOperator("SUB", toLRealValue(0), toLRealValue(0)));
		assertEqualsWithDelta(0.0, invokeBinaryOperator("SUB", toLRealValue(2), toLRealValue(2)));
		assertEqualsWithDelta(4.0, invokeBinaryOperator("SUB", toLRealValue(2), toLRealValue((-2))));
	}

	@Test
	void testDiv() throws Throwable {
		assertThrows(ArithmeticException.class, // NOSONAR
				() -> Functions.invoke(StandardFunctions.class, "DIV", toIntValue((short) 0), toIntValue((short) 0)));
		assertThrows(ArithmeticException.class, // NOSONAR
				() -> Functions.invoke(StandardFunctions.class, "DIV", toIntValue((short) 2), toIntValue((short) 0)));
		assertEquals(toIntValue((short) 1),
				Functions.invoke(StandardFunctions.class, "DIV", toIntValue((short) 2), toIntValue((short) 2)));
		assertEquals(toIntValue((short) (-1)),
				Functions.invoke(StandardFunctions.class, "DIV", toIntValue((short) 2), toIntValue((short) (-2))));
		assertEqualsWithDelta(Double.NaN, invokeBinaryOperator("DIV", toLRealValue(0), toLRealValue(0)));
		assertEqualsWithDelta(Double.POSITIVE_INFINITY, invokeBinaryOperator("DIV", toLRealValue(2), toLRealValue(0)));
		assertEqualsWithDelta(1.0, invokeBinaryOperator("DIV", toLRealValue(2), toLRealValue(2)));
		assertEqualsWithDelta((-1), invokeBinaryOperator("DIV", toLRealValue(2), toLRealValue((-2))));
	}

	@Test
	void testMod() throws Throwable {
		assertEquals(toIntValue((short) 0),
				Functions.invoke(StandardFunctions.class, "MOD", toIntValue((short) 0), toIntValue((short) 0)));
		assertEquals(toIntValue((short) 0),
				Functions.invoke(StandardFunctions.class, "MOD", toIntValue((short) 2), toIntValue((short) 2)));
		assertEquals(toIntValue((short) 1),
				Functions.invoke(StandardFunctions.class, "MOD", toIntValue((short) 3), toIntValue((short) 2)));
		assertEquals(toIntValue((short) 0),
				Functions.invoke(StandardFunctions.class, "MOD", toIntValue((short) 2), toIntValue((short) (-2))));
		assertEquals(toIntValue((short) 1),
				Functions.invoke(StandardFunctions.class, "MOD", toIntValue((short) 3), toIntValue((short) (-2))));
	}

	@Test
	void testExpt() throws Throwable {
		assertEqualsWithDelta(1.0, invokeBinaryOperator("EXPT", toRealValue(0), toRealValue(0)));
		assertEqualsWithDelta(1.0, invokeBinaryOperator("EXPT", toRealValue(1), toRealValue(0)));
		assertEqualsWithDelta(8.0, invokeBinaryOperator("EXPT", toRealValue(2), toRealValue(3)));
		assertEqualsWithDelta(25.0, invokeBinaryOperator("EXPT", toRealValue(5), toRealValue(2)));
		assertEqualsWithDelta(1.0, invokeBinaryOperator("EXPT", toLRealValue(0), toLRealValue(0)));
		assertEqualsWithDelta(1.0, invokeBinaryOperator("EXPT", toLRealValue(1), toLRealValue(0)));
		assertEqualsWithDelta(8.0, invokeBinaryOperator("EXPT", toLRealValue(2), toLRealValue(3)));
		assertEqualsWithDelta(25.0, invokeBinaryOperator("EXPT", toLRealValue(5), toLRealValue(2)));
	}

	@Test
	void testShiftLeft() throws Throwable {
		assertEquals(toByteValue((byte) 6),
				Functions.invoke(StandardFunctions.class, "SHL", toByteValue((byte) 3), toIntValue((short) 1)));
		assertEquals(toWordValue((short) 6),
				Functions.invoke(StandardFunctions.class, "SHL", toWordValue((short) 3), toIntValue((short) 1)));
		assertEquals(toDWordValue(6),
				Functions.invoke(StandardFunctions.class, "SHL", toDWordValue(3), toIntValue((short) 1)));
		assertEquals(toLWordValue(6),
				Functions.invoke(StandardFunctions.class, "SHL", toLWordValue(3), toIntValue((short) 1)));
	}

	@Test
	void testShiftRight() throws Throwable {
		assertEquals(toByteValue((byte) 3),
				Functions.invoke(StandardFunctions.class, "SHR", toByteValue((byte) 6), toIntValue((short) 1)));
		assertEquals(toWordValue((short) 3),
				Functions.invoke(StandardFunctions.class, "SHR", toWordValue((short) 6), toIntValue((short) 1)));
		assertEquals(toDWordValue(3),
				Functions.invoke(StandardFunctions.class, "SHR", toDWordValue(6), toIntValue((short) 1)));
		assertEquals(toLWordValue(3),
				Functions.invoke(StandardFunctions.class, "SHR", toLWordValue(6), toIntValue((short) 1)));
		assertEquals(toByteValue((byte) 0x7f),
				Functions.invoke(StandardFunctions.class, "SHR", toByteValue((byte) 0xff), toIntValue((short) 1)));
		assertEquals(toWordValue((short) 0x7fff),
				Functions.invoke(StandardFunctions.class, "SHR", toWordValue((short) 0xffff), toIntValue((short) 1)));
		assertEquals(toDWordValue(0x7fffffff),
				Functions.invoke(StandardFunctions.class, "SHR", toDWordValue(0xffffffff), toIntValue((short) 1)));
		assertEquals(toLWordValue(0x7fffffffffffffffL), Functions.invoke(StandardFunctions.class, "SHR",
				toLWordValue(0xffffffffffffffffL), toIntValue((short) 1)));
	}

	@Test
	void testRotateLeft() throws Throwable {
		assertEquals(toByteValue((byte) 3),
				Functions.invoke(StandardFunctions.class, "ROL", toByteValue((byte) 0x81), toIntValue((short) 1)));
		assertEquals(toWordValue((short) 3),
				Functions.invoke(StandardFunctions.class, "ROL", toWordValue((short) 0x8001), toIntValue((short) 1)));
		assertEquals(toDWordValue(3),
				Functions.invoke(StandardFunctions.class, "ROL", toDWordValue(0x80000001), toIntValue((short) 1)));
		assertEquals(toLWordValue(3), Functions.invoke(StandardFunctions.class, "ROL",
				toLWordValue(0x8000000000000001L), toIntValue((short) 1)));
	}

	@Test
	void testRotateRight() throws Throwable {
		assertEquals(toByteValue((byte) 0x81),
				Functions.invoke(StandardFunctions.class, "ROR", toByteValue((byte) 3), toIntValue((short) 1)));
		assertEquals(toWordValue((short) 0x8001),
				Functions.invoke(StandardFunctions.class, "ROR", toWordValue((short) 3), toIntValue((short) 1)));
		assertEquals(toDWordValue(0x80000001),
				Functions.invoke(StandardFunctions.class, "ROR", toDWordValue(3), toIntValue((short) 1)));
		assertEquals(toLWordValue(0x8000000000000001L),
				Functions.invoke(StandardFunctions.class, "ROR", toLWordValue(3), toIntValue((short) 1)));
	}

	@Test
	void testAnd() throws Throwable {
		assertEquals(toBoolValue(false),
				Functions.invoke(StandardFunctions.class, "AND", toBoolValue(false), toBoolValue(false)));
		assertEquals(toBoolValue(false),
				Functions.invoke(StandardFunctions.class, "AND", toBoolValue(true), toBoolValue(false)));
		assertEquals(toBoolValue(false),
				Functions.invoke(StandardFunctions.class, "AND", toBoolValue(false), toBoolValue(true)));
		assertEquals(toBoolValue(true),
				Functions.invoke(StandardFunctions.class, "AND", toBoolValue(true), toBoolValue(true)));
		assertEquals(toByteValue((byte) 0x11),
				Functions.invoke(StandardFunctions.class, "AND", toByteValue((byte) 0x17), toByteValue((byte) 0x71)));
		assertEquals(toWordValue((short) 0x301), Functions.invoke(StandardFunctions.class, "AND",
				toWordValue((short) 0x1701), toWordValue((short) 0x2363)));
		assertEquals(toDWordValue(0x201),
				Functions.invoke(StandardFunctions.class, "AND", toDWordValue(0x74205), toDWordValue(0x2371)));
		assertEquals(toLWordValue(0x250),
				Functions.invoke(StandardFunctions.class, "AND", toLWordValue(0x74656), toLWordValue(0x2371)));
		assertEquals(toByteValue((byte) 1), Functions.invoke(StandardFunctions.class, "AND", toByteValue((byte) 7),
				toByteValue((byte) 5), toByteValue((byte) 3)));
	}

	@Test
	void testOr() throws Throwable {
		assertEquals(toBoolValue(false),
				Functions.invoke(StandardFunctions.class, "OR", toBoolValue(false), toBoolValue(false)));
		assertEquals(toBoolValue(true),
				Functions.invoke(StandardFunctions.class, "OR", toBoolValue(true), toBoolValue(false)));
		assertEquals(toBoolValue(true),
				Functions.invoke(StandardFunctions.class, "OR", toBoolValue(false), toBoolValue(true)));
		assertEquals(toBoolValue(true),
				Functions.invoke(StandardFunctions.class, "OR", toBoolValue(true), toBoolValue(true)));
		assertEquals(toByteValue((byte) 21),
				Functions.invoke(StandardFunctions.class, "OR", toByteValue((byte) 17), toByteValue((byte) 4)));
		assertEquals(toWordValue((short) 0x3763), Functions.invoke(StandardFunctions.class, "OR",
				toWordValue((short) 0x1701), toWordValue((short) 0x2363)));
		assertEquals(toDWordValue(0x76375),
				Functions.invoke(StandardFunctions.class, "OR", toDWordValue(0x74205), toDWordValue(0x2371)));
		assertEquals(toLWordValue(0x76777),
				Functions.invoke(StandardFunctions.class, "OR", toLWordValue(0x74656), toLWordValue(0x2371)));
		assertEquals(toByteValue((byte) 7), Functions.invoke(StandardFunctions.class, "OR", toByteValue((byte) 7),
				toByteValue((byte) 5), toByteValue((byte) 3)));
	}

	@Test
	void testXor() throws Throwable {
		assertEquals(toBoolValue(false),
				Functions.invoke(StandardFunctions.class, "XOR", toBoolValue(false), toBoolValue(false)));
		assertEquals(toBoolValue(true),
				Functions.invoke(StandardFunctions.class, "XOR", toBoolValue(true), toBoolValue(false)));
		assertEquals(toBoolValue(true),
				Functions.invoke(StandardFunctions.class, "XOR", toBoolValue(false), toBoolValue(true)));
		assertEquals(toBoolValue(false),
				Functions.invoke(StandardFunctions.class, "XOR", toBoolValue(true), toBoolValue(true)));
		assertEquals(toByteValue((byte) 21),
				Functions.invoke(StandardFunctions.class, "XOR", toByteValue((byte) 17), toByteValue((byte) 4)));
		assertEquals(toWordValue((short) 0x3462), Functions.invoke(StandardFunctions.class, "XOR",
				toWordValue((short) 0x1701), toWordValue((short) 0x2363)));
		assertEquals(toDWordValue(0x76174),
				Functions.invoke(StandardFunctions.class, "XOR", toDWordValue(0x74205), toDWordValue(0x2371)));
		assertEquals(toLWordValue(0x76527),
				Functions.invoke(StandardFunctions.class, "XOR", toLWordValue(0x74656), toLWordValue(0x2371)));
		assertEquals(toByteValue((byte) 1), Functions.invoke(StandardFunctions.class, "XOR", toByteValue((byte) 7),
				toByteValue((byte) 5), toByteValue((byte) 3)));
	}

	@Test
	void testNot() throws Throwable {
		assertEquals(toBoolValue(true), Functions.invoke(StandardFunctions.class, "NOT", toBoolValue(false)));
		assertEquals(toBoolValue(false), Functions.invoke(StandardFunctions.class, "NOT", toBoolValue(true)));
		assertEquals(toWordValue((short) 0xe8fe),
				Functions.invoke(StandardFunctions.class, "NOT", toWordValue((short) 0x1701)));
		assertEquals(toDWordValue(0x1234567),
				Functions.invoke(StandardFunctions.class, "NOT", toDWordValue(0xfedcba98)));
		assertEquals(toLWordValue(0x22224444ddddbbbbL),
				Functions.invoke(StandardFunctions.class, "NOT", toLWordValue(0xddddbbbb22224444L)));
	}

	@Test
	void testMove() throws Throwable {
		assertEquals(toIntValue((short) 17), Functions.invoke(StandardFunctions.class, "MOVE", toIntValue((short) 17)));
		assertEquals(toStringValue("4"), Functions.invoke(StandardFunctions.class, "MOVE", toStringValue("4")));
	}

	@Test
	void testSel() throws Throwable {
		assertEquals(toIntValue((short) 17), Functions.invoke(StandardFunctions.class, "SEL", toBoolValue(false),
				toIntValue((short) 17), toIntValue((short) 4)));
		assertEquals(toStringValue("4"), Functions.invoke(StandardFunctions.class, "SEL", toBoolValue(true),
				toStringValue("0"), toStringValue("4")));
	}

	@Test
	void testMax() throws Throwable {
		assertEquals(toIntValue((short) 17),
				Functions.invoke(StandardFunctions.class, "MAX", toIntValue((short) 17), toIntValue((short) 4)));
		assertEquals(toStringValue("4"),
				Functions.invoke(StandardFunctions.class, "MAX", toStringValue("0"), toStringValue("4")));
		assertEquals(toIntValue((short) 21), Functions.invoke(StandardFunctions.class, "MAX", toIntValue((short) 17),
				toIntValue((short) 4), toIntValue((short) 21)));
	}

	@Test
	void testMin() throws Throwable {
		assertEquals(toIntValue((short) 4),
				Functions.invoke(StandardFunctions.class, "MIN", toIntValue((short) 17), toIntValue((short) 4)));
		assertEquals(toStringValue("0"),
				Functions.invoke(StandardFunctions.class, "MIN", toStringValue("0"), toStringValue("4")));
		assertEquals(toIntValue((short) 4), Functions.invoke(StandardFunctions.class, "MIN", toIntValue((short) 17),
				toIntValue((short) 4), toIntValue((short) 21)));
	}

	@Test
	void testLimit() throws Throwable {
		assertEquals(toIntValue((short) 4), Functions.invoke(StandardFunctions.class, "LIMIT", toIntValue((short) 0),
				toIntValue((short) 4), toIntValue((short) 17)));
		assertEquals(toIntValue((short) 17), Functions.invoke(StandardFunctions.class, "LIMIT", toIntValue((short) 0),
				toIntValue((short) 21), toIntValue((short) 17)));
		assertEquals(toIntValue((short) 0), Functions.invoke(StandardFunctions.class, "LIMIT", toIntValue((short) 0),
				toIntValue((short) (-5)), toIntValue((short) 17)));
	}

	@Test
	void testMux() throws Throwable {
		assertEquals(toIntValue((short) 17), Functions.invoke(StandardFunctions.class, "MUX", toIntValue((short) 0),
				toIntValue((short) 17), toIntValue((short) 4)));
		assertEquals(toIntValue((short) 4), Functions.invoke(StandardFunctions.class, "MUX", toIntValue((short) 1),
				toIntValue((short) 17), toIntValue((short) 4)));
		assertEquals(toIntValue((short) 21), Functions.invoke(StandardFunctions.class, "MUX", toIntValue((short) 2),
				toIntValue((short) 17), toIntValue((short) 4), toIntValue((short) 21), toIntValue((short) 42)));
	}

	@Test
	void testGt() throws Throwable {
		assertEquals(toBoolValue(true),
				Functions.invoke(StandardFunctions.class, "GT", toIntValue((short) 17), toIntValue((short) 4)));
		assertEquals(toBoolValue(false),
				Functions.invoke(StandardFunctions.class, "GT", toIntValue((short) 4), toIntValue((short) 17)));
		assertEquals(toBoolValue(false),
				Functions.invoke(StandardFunctions.class, "GT", toIntValue((short) 4), toIntValue((short) 4)));
		assertEquals(toBoolValue(false),
				Functions.invoke(StandardFunctions.class, "GT", toRealValue(0.0f), toRealValue(-0.0f)));
		assertEquals(toBoolValue(false),
				Functions.invoke(StandardFunctions.class, "GT", toRealValue(-0.0f), toRealValue(0.0f)));
		assertEquals(toBoolValue(false), Functions.invoke(StandardFunctions.class, "GT", toIntValue((short) 17),
				toIntValue((short) 4), toIntValue((short) 21)));
		assertEquals(toBoolValue(false), Functions.invoke(StandardFunctions.class, "GT", toIntValue((short) 4),
				toIntValue((short) 17), toIntValue((short) 21)));
		assertEquals(toBoolValue(false), Functions.invoke(StandardFunctions.class, "GT", toIntValue((short) 4),
				toIntValue((short) 17), toIntValue((short) 17)));
		assertEquals(toBoolValue(true), Functions.invoke(StandardFunctions.class, "GT", toIntValue((short) 21),
				toIntValue((short) 17), toIntValue((short) 4)));
		assertEquals(toBoolValue(false), Functions.invoke(StandardFunctions.class, "GT", toIntValue((short) 21),
				toIntValue((short) 17), toIntValue((short) 17)));
		assertEquals(toBoolValue(false), Functions.invoke(StandardFunctions.class, "GT", toIntValue((short) 17),
				toIntValue((short) 17), toIntValue((short) 17)));
	}

	@Test
	void testGe() throws Throwable {
		assertEquals(toBoolValue(true),
				Functions.invoke(StandardFunctions.class, "GE", toIntValue((short) 17), toIntValue((short) 4)));
		assertEquals(toBoolValue(false),
				Functions.invoke(StandardFunctions.class, "GE", toIntValue((short) 4), toIntValue((short) 17)));
		assertEquals(toBoolValue(true),
				Functions.invoke(StandardFunctions.class, "GE", toIntValue((short) 4), toIntValue((short) 4)));
		assertEquals(toBoolValue(true),
				Functions.invoke(StandardFunctions.class, "GE", toRealValue(0.0f), toRealValue(-0.0f)));
		assertEquals(toBoolValue(true),
				Functions.invoke(StandardFunctions.class, "GE", toRealValue(-0.0f), toRealValue(0.0f)));
		assertEquals(toBoolValue(false), Functions.invoke(StandardFunctions.class, "GE", toIntValue((short) 17),
				toIntValue((short) 4), toIntValue((short) 21)));
		assertEquals(toBoolValue(false), Functions.invoke(StandardFunctions.class, "GE", toIntValue((short) 4),
				toIntValue((short) 17), toIntValue((short) 21)));
		assertEquals(toBoolValue(false), Functions.invoke(StandardFunctions.class, "GE", toIntValue((short) 4),
				toIntValue((short) 17), toIntValue((short) 17)));
		assertEquals(toBoolValue(true), Functions.invoke(StandardFunctions.class, "GE", toIntValue((short) 21),
				toIntValue((short) 17), toIntValue((short) 4)));
		assertEquals(toBoolValue(true), Functions.invoke(StandardFunctions.class, "GE", toIntValue((short) 21),
				toIntValue((short) 17), toIntValue((short) 17)));
		assertEquals(toBoolValue(true), Functions.invoke(StandardFunctions.class, "GE", toIntValue((short) 17),
				toIntValue((short) 17), toIntValue((short) 17)));
	}

	@Test
	void testEq() throws Throwable {
		assertEquals(toBoolValue(false),
				Functions.invoke(StandardFunctions.class, "EQ", toIntValue((short) 17), toIntValue((short) 4)));
		assertEquals(toBoolValue(false),
				Functions.invoke(StandardFunctions.class, "EQ", toIntValue((short) 4), toIntValue((short) 17)));
		assertEquals(toBoolValue(true),
				Functions.invoke(StandardFunctions.class, "EQ", toIntValue((short) 4), toIntValue((short) 4)));
		assertEquals(toBoolValue(true),
				Functions.invoke(StandardFunctions.class, "EQ", toRealValue(0.0f), toRealValue(-0.0f)));
		assertEquals(toBoolValue(true),
				Functions.invoke(StandardFunctions.class, "EQ", toRealValue(-0.0f), toRealValue(0.0f)));
		assertEquals(toBoolValue(false), Functions.invoke(StandardFunctions.class, "EQ", toIntValue((short) 17),
				toIntValue((short) 4), toIntValue((short) 21)));
		assertEquals(toBoolValue(false), Functions.invoke(StandardFunctions.class, "EQ", toIntValue((short) 4),
				toIntValue((short) 17), toIntValue((short) 21)));
		assertEquals(toBoolValue(false), Functions.invoke(StandardFunctions.class, "EQ", toIntValue((short) 4),
				toIntValue((short) 17), toIntValue((short) 17)));
		assertEquals(toBoolValue(false), Functions.invoke(StandardFunctions.class, "EQ", toIntValue((short) 21),
				toIntValue((short) 17), toIntValue((short) 4)));
		assertEquals(toBoolValue(false), Functions.invoke(StandardFunctions.class, "EQ", toIntValue((short) 21),
				toIntValue((short) 17), toIntValue((short) 17)));
		assertEquals(toBoolValue(true), Functions.invoke(StandardFunctions.class, "EQ", toIntValue((short) 17),
				toIntValue((short) 17), toIntValue((short) 17)));
	}

	@Test
	void testLt() throws Throwable {
		assertEquals(toBoolValue(false),
				Functions.invoke(StandardFunctions.class, "LT", toIntValue((short) 17), toIntValue((short) 4)));
		assertEquals(toBoolValue(true),
				Functions.invoke(StandardFunctions.class, "LT", toIntValue((short) 4), toIntValue((short) 17)));
		assertEquals(toBoolValue(false),
				Functions.invoke(StandardFunctions.class, "LT", toIntValue((short) 4), toIntValue((short) 4)));
		assertEquals(toBoolValue(false),
				Functions.invoke(StandardFunctions.class, "LT", toRealValue(0.0f), toRealValue(-0.0f)));
		assertEquals(toBoolValue(false),
				Functions.invoke(StandardFunctions.class, "LT", toRealValue(-0.0f), toRealValue(0.0f)));
		assertEquals(toBoolValue(false), Functions.invoke(StandardFunctions.class, "LT", toIntValue((short) 17),
				toIntValue((short) 4), toIntValue((short) 21)));
		assertEquals(toBoolValue(true), Functions.invoke(StandardFunctions.class, "LT", toIntValue((short) 4),
				toIntValue((short) 17), toIntValue((short) 21)));
		assertEquals(toBoolValue(false), Functions.invoke(StandardFunctions.class, "LT", toIntValue((short) 4),
				toIntValue((short) 17), toIntValue((short) 17)));
		assertEquals(toBoolValue(false), Functions.invoke(StandardFunctions.class, "LT", toIntValue((short) 21),
				toIntValue((short) 17), toIntValue((short) 4)));
		assertEquals(toBoolValue(false), Functions.invoke(StandardFunctions.class, "LT", toIntValue((short) 21),
				toIntValue((short) 17), toIntValue((short) 17)));
		assertEquals(toBoolValue(false), Functions.invoke(StandardFunctions.class, "LT", toIntValue((short) 17),
				toIntValue((short) 17), toIntValue((short) 17)));
	}

	@Test
	void testLe() throws Throwable {
		assertEquals(toBoolValue(false),
				Functions.invoke(StandardFunctions.class, "LE", toIntValue((short) 17), toIntValue((short) 4)));
		assertEquals(toBoolValue(true),
				Functions.invoke(StandardFunctions.class, "LE", toIntValue((short) 4), toIntValue((short) 17)));
		assertEquals(toBoolValue(true),
				Functions.invoke(StandardFunctions.class, "LE", toIntValue((short) 4), toIntValue((short) 4)));
		assertEquals(toBoolValue(true),
				Functions.invoke(StandardFunctions.class, "LE", toRealValue(0.0f), toRealValue(-0.0f)));
		assertEquals(toBoolValue(true),
				Functions.invoke(StandardFunctions.class, "LE", toRealValue(-0.0f), toRealValue(0.0f)));
		assertEquals(toBoolValue(false), Functions.invoke(StandardFunctions.class, "LE", toIntValue((short) 17),
				toIntValue((short) 4), toIntValue((short) 21)));
		assertEquals(toBoolValue(true), Functions.invoke(StandardFunctions.class, "LE", toIntValue((short) 4),
				toIntValue((short) 17), toIntValue((short) 21)));
		assertEquals(toBoolValue(true), Functions.invoke(StandardFunctions.class, "LE", toIntValue((short) 4),
				toIntValue((short) 17), toIntValue((short) 17)));
		assertEquals(toBoolValue(false), Functions.invoke(StandardFunctions.class, "LE", toIntValue((short) 21),
				toIntValue((short) 17), toIntValue((short) 4)));
		assertEquals(toBoolValue(false), Functions.invoke(StandardFunctions.class, "LE", toIntValue((short) 21),
				toIntValue((short) 17), toIntValue((short) 17)));
		assertEquals(toBoolValue(true), Functions.invoke(StandardFunctions.class, "LE", toIntValue((short) 17),
				toIntValue((short) 17), toIntValue((short) 17)));
	}

	@Test
	void testNe() throws Throwable {
		assertEquals(toBoolValue(true),
				Functions.invoke(StandardFunctions.class, "NE", toIntValue((short) 17), toIntValue((short) 4)));
		assertEquals(toBoolValue(true),
				Functions.invoke(StandardFunctions.class, "NE", toIntValue((short) 4), toIntValue((short) 17)));
		assertEquals(toBoolValue(false),
				Functions.invoke(StandardFunctions.class, "NE", toIntValue((short) 4), toIntValue((short) 4)));
		assertEquals(toBoolValue(false),
				Functions.invoke(StandardFunctions.class, "NE", toRealValue(0.0f), toRealValue(-0.0f)));
		assertEquals(toBoolValue(false),
				Functions.invoke(StandardFunctions.class, "NE", toRealValue(-0.0f), toRealValue(0.0f)));
	}

	@Test
	void testLen() throws Throwable {
		assertEquals(toULIntValue(0), Functions.invoke(StandardFunctions.class, "LEN", toStringValue("")));
		assertEquals(toULIntValue(4), Functions.invoke(StandardFunctions.class, "LEN", toStringValue("Test")));
		assertEquals(toULIntValue(9), Functions.invoke(StandardFunctions.class, "LEN", toStringValue("4diac IDE")));
		assertEquals(toULIntValue(0), Functions.invoke(StandardFunctions.class, "LEN", toWStringValue("")));
		assertEquals(toULIntValue(4), Functions.invoke(StandardFunctions.class, "LEN", toWStringValue("Test")));
		assertEquals(toULIntValue(9), Functions.invoke(StandardFunctions.class, "LEN", toWStringValue("4diac IDE")));
	}

	@Test
	void testLeft() throws Throwable {
		assertEquals(toStringValue(""),
				Functions.invoke(StandardFunctions.class, "LEFT", toStringValue(""), toIntValue((short) 0)));
		assertEquals(toStringValue("Test"),
				Functions.invoke(StandardFunctions.class, "LEFT", toStringValue("TestString"), toIntValue((short) 4)));
		assertEquals(toStringValue("4diac"),
				Functions.invoke(StandardFunctions.class, "LEFT", toStringValue("4diac IDE"), toIntValue((short) 5)));
		assertEquals(toWStringValue(""),
				Functions.invoke(StandardFunctions.class, "LEFT", toWStringValue(""), toIntValue((short) 0)));
		assertEquals(toWStringValue("Test"),
				Functions.invoke(StandardFunctions.class, "LEFT", toWStringValue("TestString"), toIntValue((short) 4)));
		assertEquals(toWStringValue("4diac"),
				Functions.invoke(StandardFunctions.class, "LEFT", toWStringValue("4diac IDE"), toIntValue((short) 5)));
	}

	@Test
	void testRight() throws Throwable {
		assertEquals(toStringValue(""),
				Functions.invoke(StandardFunctions.class, "RIGHT", toStringValue(""), toIntValue((short) 0)));
		assertEquals(toStringValue("String"),
				Functions.invoke(StandardFunctions.class, "RIGHT", toStringValue("TestString"), toIntValue((short) 6)));
		assertEquals(toStringValue("IDE"),
				Functions.invoke(StandardFunctions.class, "RIGHT", toStringValue("4diac IDE"), toIntValue((short) 3)));
		assertEquals(toWStringValue(""),
				Functions.invoke(StandardFunctions.class, "RIGHT", toWStringValue(""), toIntValue((short) 0)));
		assertEquals(toWStringValue("String"), Functions.invoke(StandardFunctions.class, "RIGHT",
				toWStringValue("TestString"), toIntValue((short) 6)));
		assertEquals(toWStringValue("IDE"),
				Functions.invoke(StandardFunctions.class, "RIGHT", toWStringValue("4diac IDE"), toIntValue((short) 3)));
	}

	@Test
	void testMid() throws Throwable {
		assertEquals(toStringValue(""), Functions.invoke(StandardFunctions.class, "MID", toStringValue(""),
				toIntValue((short) 0), toIntValue((short) 1)));
		assertEquals(toStringValue("Str"), Functions.invoke(StandardFunctions.class, "MID", toStringValue("TestString"),
				toIntValue((short) 3), toIntValue((short) 5)));
		assertEquals(toStringValue("diac"), Functions.invoke(StandardFunctions.class, "MID", toStringValue("4diac IDE"),
				toIntValue((short) 4), toIntValue((short) 2)));
		assertEquals(toWStringValue(""), Functions.invoke(StandardFunctions.class, "MID", toWStringValue(""),
				toIntValue((short) 0), toIntValue((short) 1)));
		assertEquals(toWStringValue("Str"), Functions.invoke(StandardFunctions.class, "MID",
				toWStringValue("TestString"), toIntValue((short) 3), toIntValue((short) 5)));
		assertEquals(toWStringValue("diac"), Functions.invoke(StandardFunctions.class, "MID",
				toWStringValue("4diac IDE"), toIntValue((short) 4), toIntValue((short) 2)));
	}

	@Test
	void testConcat() throws Throwable {
		assertEquals(toStringValue(""), Functions.invoke(StandardFunctions.class, "CONCAT", toStringValue("")));
		assertEquals(toStringValue("a"), Functions.invoke(StandardFunctions.class, "CONCAT", toCharValue("a")));
		assertEquals(toStringValue("Test"), Functions.invoke(StandardFunctions.class, "CONCAT", toStringValue("Test")));
		assertEquals(toStringValue(""),
				Functions.invoke(StandardFunctions.class, "CONCAT", toStringValue(""), toStringValue("")));
		assertEquals(toStringValue("TestString"),
				Functions.invoke(StandardFunctions.class, "CONCAT", toStringValue("Test"), toStringValue("String")));
		assertEquals(toStringValue("4diac IDE"), Functions.invoke(StandardFunctions.class, "CONCAT",
				toStringValue("4diac"), toStringValue(" "), toStringValue("IDE")));
		assertEquals(toStringValue("4diac IDE"), Functions.invoke(StandardFunctions.class, "CONCAT",
				toStringValue("4diac"), toCharValue(" "), toStringValue("IDE")));
		assertEquals(toWStringValue(""), Functions.invoke(StandardFunctions.class, "CONCAT", toWStringValue("")));
		assertEquals(toWStringValue("a"), Functions.invoke(StandardFunctions.class, "CONCAT", toWCharValue('a')));
		assertEquals(toWStringValue("Test"),
				Functions.invoke(StandardFunctions.class, "CONCAT", toWStringValue("Test")));
		assertEquals(toWStringValue(""),
				Functions.invoke(StandardFunctions.class, "CONCAT", toWStringValue(""), toWStringValue("")));
		assertEquals(toWStringValue("TestString"),
				Functions.invoke(StandardFunctions.class, "CONCAT", toWStringValue("Test"), toWStringValue("String")));
		assertEquals(toWStringValue("4diac IDE"), Functions.invoke(StandardFunctions.class, "CONCAT",
				toWStringValue("4diac"), toWStringValue(" "), toWStringValue("IDE")));
		assertEquals(toWStringValue("4diac IDE"), Functions.invoke(StandardFunctions.class, "CONCAT",
				toWStringValue("4diac"), toWCharValue(' '), toWStringValue("IDE")));
	}

	@Test
	void testInsert() throws Throwable {
		assertEquals(toStringValue(""), Functions.invoke(StandardFunctions.class, "INSERT", toStringValue(""),
				toStringValue(""), toIntValue((short) 0)));
		assertEquals(toStringValue("Test"), Functions.invoke(StandardFunctions.class, "INSERT", toStringValue("Test"),
				toStringValue(""), toIntValue((short) 0)));
		assertEquals(toStringValue("TestString"), Functions.invoke(StandardFunctions.class, "INSERT",
				toStringValue("Test"), toStringValue("String"), toIntValue((short) 4)));
		assertEquals(toStringValue("4diac IDE"), Functions.invoke(StandardFunctions.class, "INSERT",
				toStringValue("4diacIDE"), toStringValue(" "), toIntValue((short) 5)));
		assertEquals(toStringValue("4diac IDE"), Functions.invoke(StandardFunctions.class, "INSERT",
				toStringValue("4diacIDE"), toCharValue(" "), toIntValue((short) 5)));
		assertEquals(toWStringValue(""), Functions.invoke(StandardFunctions.class, "INSERT", toWStringValue(""),
				toWStringValue(""), toIntValue((short) 0)));
		assertEquals(toWStringValue("Test"), Functions.invoke(StandardFunctions.class, "INSERT", toWStringValue("Test"),
				toWStringValue(""), toIntValue((short) 0)));
		assertEquals(toWStringValue("TestString"), Functions.invoke(StandardFunctions.class, "INSERT",
				toWStringValue("Test"), toWStringValue("String"), toIntValue((short) 4)));
		assertEquals(toWStringValue("4diac IDE"), Functions.invoke(StandardFunctions.class, "INSERT",
				toWStringValue("4diacIDE"), toWStringValue(" "), toIntValue((short) 5)));
		assertEquals(toWStringValue("4diac IDE"), Functions.invoke(StandardFunctions.class, "INSERT",
				toWStringValue("4diacIDE"), toWCharValue(' '), toIntValue((short) 5)));
	}

	@Test
	void testDelete() throws Throwable {
		assertEquals(toStringValue(""), Functions.invoke(StandardFunctions.class, "DELETE", toStringValue(""),
				toIntValue((short) 0), toIntValue((short) 1)));
		assertEquals(toStringValue("Test"), Functions.invoke(StandardFunctions.class, "DELETE",
				toStringValue("TestString"), toIntValue((short) 6), toIntValue((short) 5)));
		assertEquals(toStringValue("4diacIDE"), Functions.invoke(StandardFunctions.class, "DELETE",
				toStringValue("4diac IDE"), toIntValue((short) 1), toIntValue((short) 6)));
		assertEquals(toWStringValue(""), Functions.invoke(StandardFunctions.class, "DELETE", toWStringValue(""),
				toIntValue((short) 0), toIntValue((short) 1)));
		assertEquals(toWStringValue("Test"), Functions.invoke(StandardFunctions.class, "DELETE",
				toWStringValue("TestString"), toIntValue((short) 6), toIntValue((short) 5)));
		assertEquals(toWStringValue("4diacIDE"), Functions.invoke(StandardFunctions.class, "DELETE",
				toWStringValue("4diac IDE"), toIntValue((short) 1), toIntValue((short) 6)));
	}

	@Test
	void testReplace() throws Throwable {
		assertEquals(toStringValue(""), Functions.invoke(StandardFunctions.class, "REPLACE", toStringValue(""),
				toStringValue(""), toIntValue((short) 0), toIntValue((short) 1)));
		assertEquals(toStringValue("TeaString"), Functions.invoke(StandardFunctions.class, "REPLACE",
				toStringValue("TestString"), toStringValue("a"), toIntValue((short) 2), toIntValue((short) 3)));
		assertEquals(toStringValue("4diac FORTE"), Functions.invoke(StandardFunctions.class, "REPLACE",
				toStringValue("4diac IDE"), toStringValue("FORT"), toIntValue((short) 2), toIntValue((short) 7)));
		assertEquals(toStringValue("4diac-IDE"), Functions.invoke(StandardFunctions.class, "REPLACE",
				toStringValue("4diac IDE"), toCharValue("-"), toIntValue((short) 1), toIntValue((short) 6)));
		assertEquals(toWStringValue(""), Functions.invoke(StandardFunctions.class, "REPLACE", toWStringValue(""),
				toWStringValue(""), toIntValue((short) 0), toIntValue((short) 1)));
		assertEquals(toWStringValue("TeaString"), Functions.invoke(StandardFunctions.class, "REPLACE",
				toWStringValue("TestString"), toWStringValue("a"), toIntValue((short) 2), toIntValue((short) 3)));
		assertEquals(toWStringValue("4diac FORTE"), Functions.invoke(StandardFunctions.class, "REPLACE",
				toWStringValue("4diac IDE"), toWStringValue("FORT"), toIntValue((short) 2), toIntValue((short) 7)));
		assertEquals(toWStringValue("4diac-IDE"), Functions.invoke(StandardFunctions.class, "REPLACE",
				toWStringValue("4diac IDE"), toWCharValue('-'), toIntValue((short) 1), toIntValue((short) 6)));
	}

	@Test
	void testFind() throws Throwable {
		assertEquals(toULIntValue(1),
				Functions.invoke(StandardFunctions.class, "FIND", toStringValue(""), toStringValue("")));
		assertEquals(toULIntValue(0),
				Functions.invoke(StandardFunctions.class, "FIND", toStringValue(""), toStringValue("Test")));
		assertEquals(toULIntValue(1),
				Functions.invoke(StandardFunctions.class, "FIND", toStringValue("Test"), toStringValue("Test")));
		assertEquals(toULIntValue(5), Functions.invoke(StandardFunctions.class, "FIND", toStringValue("TestString"),
				toStringValue("String")));
		assertEquals(toULIntValue(6),
				Functions.invoke(StandardFunctions.class, "FIND", toStringValue("4diac IDE"), toStringValue(" ")));
		assertEquals(toULIntValue(6),
				Functions.invoke(StandardFunctions.class, "FIND", toStringValue("4diac IDE"), toCharValue(" ")));
		assertEquals(toULIntValue(1),
				Functions.invoke(StandardFunctions.class, "FIND", toWStringValue(""), toWStringValue("")));
		assertEquals(toULIntValue(0),
				Functions.invoke(StandardFunctions.class, "FIND", toWStringValue(""), toWStringValue("Test")));
		assertEquals(toULIntValue(1),
				Functions.invoke(StandardFunctions.class, "FIND", toWStringValue("Test"), toWStringValue("Test")));
		assertEquals(toULIntValue(5), Functions.invoke(StandardFunctions.class, "FIND", toWStringValue("TestString"),
				toWStringValue("String")));
		assertEquals(toULIntValue(6),
				Functions.invoke(StandardFunctions.class, "FIND", toWStringValue("4diac IDE"), toWStringValue(" ")));
		assertEquals(toULIntValue(6),
				Functions.invoke(StandardFunctions.class, "FIND", toWStringValue("4diac IDE"), toWCharValue(' ')));
	}

	@Test
	void testLowerBound() throws Throwable {
		final ArrayValue array = new ArrayVariable("TEST",
				ArrayVariable.newArrayType(IecTypes.ElementaryTypes.INT, ArrayVariable.newSubrange(4, 17))).getValue();
		assertEquals(toDIntValue(4),
				Functions.invoke(StandardFunctions.class, "LOWER_BOUND", array, toIntValue((short) 1)));
		final ArrayValue multiArray = new ArrayVariable("TEST", ArrayVariable.newArrayType(IecTypes.ElementaryTypes.INT,
				ArrayVariable.newSubrange(0, 1), ArrayVariable.newSubrange(4, 17))).getValue();
		assertEquals(toDIntValue(0),
				Functions.invoke(StandardFunctions.class, "LOWER_BOUND", multiArray, toIntValue((short) 1)));
		assertEquals(toDIntValue(4),
				Functions.invoke(StandardFunctions.class, "LOWER_BOUND", multiArray, toIntValue((short) 2)));
	}

	@Test
	void testUpperBound() throws Throwable {
		final ArrayValue array = new ArrayVariable("TEST",
				ArrayVariable.newArrayType(IecTypes.ElementaryTypes.INT, ArrayVariable.newSubrange(4, 17))).getValue();
		assertEquals(toDIntValue(17),
				Functions.invoke(StandardFunctions.class, "UPPER_BOUND", array, toIntValue((short) 1)));
		final ArrayValue multiArray = new ArrayVariable("TEST", ArrayVariable.newArrayType(IecTypes.ElementaryTypes.INT,
				ArrayVariable.newSubrange(0, 1), ArrayVariable.newSubrange(4, 17))).getValue();
		assertEquals(toDIntValue(1),
				Functions.invoke(StandardFunctions.class, "UPPER_BOUND", multiArray, toIntValue((short) 1)));
		assertEquals(toDIntValue(17),
				Functions.invoke(StandardFunctions.class, "UPPER_BOUND", multiArray, toIntValue((short) 2)));
	}

	@Test
	void testAddTime() throws Throwable {
		assertEquals(toTimeValue(Duration.ofSeconds(21)), Functions.invoke(StandardFunctions.class, "ADD",
				toTimeValue(Duration.ofSeconds(17)), toTimeValue(Duration.ofSeconds(4))));
		assertEquals(toTimeValue(Duration.ofSeconds(21)), Functions.invoke(StandardFunctions.class, "ADD_TIME",
				toTimeValue(Duration.ofSeconds(17)), toTimeValue(Duration.ofSeconds(4))));
	}

	@Test
	void testAddLTime() throws Throwable {
		assertEquals(toLTimeValue(Duration.ofSeconds(21)), Functions.invoke(StandardFunctions.class, "ADD",
				toLTimeValue(Duration.ofSeconds(17)), toLTimeValue(Duration.ofSeconds(4))));
		assertEquals(toLTimeValue(Duration.ofSeconds(21)), Functions.invoke(StandardFunctions.class, "ADD_LTIME",
				toLTimeValue(Duration.ofSeconds(17)), toLTimeValue(Duration.ofSeconds(4))));
	}

	@Test
	void testAddTodTime() throws Throwable {
		assertEquals(toTimeOfDayValue(LocalTime.of(21, 42)), Functions.invoke(StandardFunctions.class, "ADD",
				toTimeOfDayValue(LocalTime.of(17, 21)), toTimeValue(Duration.ofHours(4).plusMinutes(21))));
		assertEquals(toTimeOfDayValue(LocalTime.of(21, 42)), Functions.invoke(StandardFunctions.class, "ADD_TOD_TIME",
				toTimeOfDayValue(LocalTime.of(17, 21)), toTimeValue(Duration.ofHours(4).plusMinutes(21))));
	}

	@Test
	void testAddLTodLTime() throws Throwable {
		assertEquals(toLTimeOfDayValue(LocalTime.of(21, 42)), Functions.invoke(StandardFunctions.class, "ADD",
				toLTimeOfDayValue(LocalTime.of(17, 21)), toLTimeValue(Duration.ofHours(4).plusMinutes(21))));
		assertEquals(toLTimeOfDayValue(LocalTime.of(21, 42)),
				Functions.invoke(StandardFunctions.class, "ADD_LTOD_LTIME", toLTimeOfDayValue(LocalTime.of(17, 21)),
						toLTimeValue(Duration.ofHours(4).plusMinutes(21))));
	}

	@Test
	void testAddDTTime() throws Throwable {
		assertEquals(toDateAndTimeValue(LocalDateTime.of(2017, 4, 21, 21, 42)),
				Functions.invoke(StandardFunctions.class, "ADD",
						toDateAndTimeValue(LocalDateTime.of(2017, 4, 21, 17, 21)),
						toTimeValue(Duration.ofHours(4).plusMinutes(21))));
		assertEquals(toDateAndTimeValue(LocalDateTime.of(2017, 4, 21, 21, 42)),
				Functions.invoke(StandardFunctions.class, "ADD_DT_TIME",
						toDateAndTimeValue(LocalDateTime.of(2017, 4, 21, 17, 21)),
						toTimeValue(Duration.ofHours(4).plusMinutes(21))));
	}

	@Test
	void testAddLDTLTime() throws Throwable {
		assertEquals(toLDateAndTimeValue(LocalDateTime.of(2017, 4, 21, 21, 42)),
				Functions.invoke(StandardFunctions.class, "ADD",
						toLDateAndTimeValue(LocalDateTime.of(2017, 4, 21, 17, 21)),
						toLTimeValue(Duration.ofHours(4).plusMinutes(21))));
		assertEquals(toLDateAndTimeValue(LocalDateTime.of(2017, 4, 21, 21, 42)),
				Functions.invoke(StandardFunctions.class, "ADD_LDT_LTIME",
						toLDateAndTimeValue(LocalDateTime.of(2017, 4, 21, 17, 21)),
						toLTimeValue(Duration.ofHours(4).plusMinutes(21))));
	}

	@Test
	void testSubTime() throws Throwable {
		assertEquals(toTimeValue(Duration.ofSeconds(17)), Functions.invoke(StandardFunctions.class, "SUB",
				toTimeValue(Duration.ofSeconds(21)), toTimeValue(Duration.ofSeconds(4))));
		assertEquals(toTimeValue(Duration.ofSeconds(17)), Functions.invoke(StandardFunctions.class, "SUB_TIME",
				toTimeValue(Duration.ofSeconds(21)), toTimeValue(Duration.ofSeconds(4))));
	}

	@Test
	void testSubLTime() throws Throwable {
		assertEquals(toLTimeValue(Duration.ofSeconds(17)), Functions.invoke(StandardFunctions.class, "SUB",
				toLTimeValue(Duration.ofSeconds(21)), toLTimeValue(Duration.ofSeconds(4))));
		assertEquals(toLTimeValue(Duration.ofSeconds(17)), Functions.invoke(StandardFunctions.class, "SUB_LTIME",
				toLTimeValue(Duration.ofSeconds(21)), toLTimeValue(Duration.ofSeconds(4))));
	}

	@Test
	void testSubDate() throws Throwable {
		assertEquals(toTimeValue(Duration.ofDays(17)), Functions.invoke(StandardFunctions.class, "SUB",
				toDateValue(LocalDate.of(2021, 4, 21)), toDateValue(LocalDate.of(2021, 4, 4))));
		assertEquals(toTimeValue(Duration.ofDays(17)), Functions.invoke(StandardFunctions.class, "SUB_DATE_DATE",
				toDateValue(LocalDate.of(2021, 4, 21)), toDateValue(LocalDate.of(2021, 4, 4))));
	}

	@Test
	void testSubLDate() throws Throwable {
		assertEquals(toLTimeValue(Duration.ofDays(17)), Functions.invoke(StandardFunctions.class, "SUB",
				toLDateValue(LocalDate.of(2021, 4, 21)), toLDateValue(LocalDate.of(2021, 4, 4))));
		assertEquals(toLTimeValue(Duration.ofDays(17)), Functions.invoke(StandardFunctions.class, "SUB_LDATE_LDATE",
				toLDateValue(LocalDate.of(2021, 4, 21)), toLDateValue(LocalDate.of(2021, 4, 4))));
	}

	@Test
	void testSubTodTime() throws Throwable {
		assertEquals(toTimeOfDayValue(LocalTime.of(17, 21)), Functions.invoke(StandardFunctions.class, "SUB",
				toTimeOfDayValue(LocalTime.of(21, 42)), toTimeValue(Duration.ofHours(4).plusMinutes(21))));
		assertEquals(toTimeOfDayValue(LocalTime.of(17, 21)), Functions.invoke(StandardFunctions.class, "SUB_TOD_TIME",
				toTimeOfDayValue(LocalTime.of(21, 42)), toTimeValue(Duration.ofHours(4).plusMinutes(21))));
	}

	@Test
	void testSubLTodLTime() throws Throwable {
		assertEquals(toLTimeOfDayValue(LocalTime.of(17, 21)), Functions.invoke(StandardFunctions.class, "SUB",
				toLTimeOfDayValue(LocalTime.of(21, 42)), toLTimeValue(Duration.ofHours(4).plusMinutes(21))));
		assertEquals(toLTimeOfDayValue(LocalTime.of(17, 21)),
				Functions.invoke(StandardFunctions.class, "SUB_LTOD_LTIME", toLTimeOfDayValue(LocalTime.of(21, 42)),
						toLTimeValue(Duration.ofHours(4).plusMinutes(21))));
	}

	@Test
	void testSubTod() throws Throwable {
		assertEquals(toTimeValue(Duration.ofHours(4).plusMinutes(21)), Functions.invoke(StandardFunctions.class, "SUB",
				toTimeOfDayValue(LocalTime.of(21, 42)), toTimeOfDayValue(LocalTime.of(17, 21))));
		assertEquals(toTimeValue(Duration.ofHours(4).plusMinutes(21)), Functions.invoke(StandardFunctions.class,
				"SUB_TOD_TOD", toTimeOfDayValue(LocalTime.of(21, 42)), toTimeOfDayValue(LocalTime.of(17, 21))));
	}

	@Test
	void testSubLTod() throws Throwable {
		assertEquals(toLTimeValue(Duration.ofHours(4).plusMinutes(21)), Functions.invoke(StandardFunctions.class, "SUB",
				toLTimeOfDayValue(LocalTime.of(21, 42)), toLTimeOfDayValue(LocalTime.of(17, 21))));
		assertEquals(toLTimeValue(Duration.ofHours(4).plusMinutes(21)), Functions.invoke(StandardFunctions.class,
				"SUB_LTOD_LTOD", toLTimeOfDayValue(LocalTime.of(21, 42)), toLTimeOfDayValue(LocalTime.of(17, 21))));
	}

	@Test
	void testSubDTTime() throws Throwable {
		assertEquals(toDateAndTimeValue(LocalDateTime.of(2017, 4, 21, 17, 21)),
				Functions.invoke(StandardFunctions.class, "SUB",
						toDateAndTimeValue(LocalDateTime.of(2017, 4, 21, 21, 42)),
						toTimeValue(Duration.ofHours(4).plusMinutes(21))));
		assertEquals(toDateAndTimeValue(LocalDateTime.of(2017, 4, 21, 17, 21)),
				Functions.invoke(StandardFunctions.class, "SUB_DT_TIME",
						toDateAndTimeValue(LocalDateTime.of(2017, 4, 21, 21, 42)),
						toTimeValue(Duration.ofHours(4).plusMinutes(21))));
	}

	@Test
	void testSubLDTLTime() throws Throwable {
		assertEquals(toLDateAndTimeValue(LocalDateTime.of(2017, 4, 21, 17, 21)),
				Functions.invoke(StandardFunctions.class, "SUB",
						toLDateAndTimeValue(LocalDateTime.of(2017, 4, 21, 21, 42)),
						toLTimeValue(Duration.ofHours(4).plusMinutes(21))));
		assertEquals(toLDateAndTimeValue(LocalDateTime.of(2017, 4, 21, 17, 21)),
				Functions.invoke(StandardFunctions.class, "SUB_LDT_LTIME",
						toLDateAndTimeValue(LocalDateTime.of(2017, 4, 21, 21, 42)),
						toLTimeValue(Duration.ofHours(4).plusMinutes(21))));
	}

	@Test
	void testSubDT() throws Throwable {
		assertEquals(toTimeValue(Duration.ofHours(4).plusMinutes(21)),
				Functions.invoke(StandardFunctions.class, "SUB",
						toDateAndTimeValue(LocalDateTime.of(2017, 4, 21, 21, 42)),
						toDateAndTimeValue(LocalDateTime.of(2017, 4, 21, 17, 21))));
		assertEquals(toTimeValue(Duration.ofHours(4).plusMinutes(21)),
				Functions.invoke(StandardFunctions.class, "SUB_DT_DT",
						toDateAndTimeValue(LocalDateTime.of(2017, 4, 21, 21, 42)),
						toDateAndTimeValue(LocalDateTime.of(2017, 4, 21, 17, 21))));
	}

	@Test
	void testSubLDT() throws Throwable {
		assertEquals(toLTimeValue(Duration.ofHours(4).plusMinutes(21)),
				Functions.invoke(StandardFunctions.class, "SUB",
						toLDateAndTimeValue(LocalDateTime.of(2017, 4, 21, 21, 42)),
						toLDateAndTimeValue(LocalDateTime.of(2017, 4, 21, 17, 21))));
		assertEquals(toLTimeValue(Duration.ofHours(4).plusMinutes(21)),
				Functions.invoke(StandardFunctions.class, "SUB_LDT_LDT",
						toLDateAndTimeValue(LocalDateTime.of(2017, 4, 21, 21, 42)),
						toLDateAndTimeValue(LocalDateTime.of(2017, 4, 21, 17, 21))));
	}

	@Test
	void testMulTime() throws Throwable {
		assertEquals(toTimeValue(Duration.ofSeconds(42)), Functions.invoke(StandardFunctions.class, "MUL",
				toTimeValue(Duration.ofSeconds(21)), toIntValue((short) 2)));
		assertEquals(toTimeValue(Duration.ofSeconds(42)),
				Functions.invoke(StandardFunctions.class, "MUL", toTimeValue(Duration.ofSeconds(21)), toRealValue(2)));
		assertEquals(toTimeValue(Duration.ofSeconds(42)), Functions.invoke(StandardFunctions.class, "MUL_TIME",
				toTimeValue(Duration.ofSeconds(21)), toIntValue((short) 2)));
		assertEquals(toTimeValue(Duration.ofSeconds(42)), Functions.invoke(StandardFunctions.class, "MUL_TIME",
				toTimeValue(Duration.ofSeconds(21)), toRealValue(2)));
	}

	@Test
	void testMulLTime() throws Throwable {
		assertEquals(toLTimeValue(Duration.ofSeconds(42)), Functions.invoke(StandardFunctions.class, "MUL",
				toLTimeValue(Duration.ofSeconds(21)), toIntValue((short) 2)));
		assertEquals(toLTimeValue(Duration.ofSeconds(42)),
				Functions.invoke(StandardFunctions.class, "MUL", toLTimeValue(Duration.ofSeconds(21)), toRealValue(2)));
		assertEquals(toLTimeValue(Duration.ofSeconds(42)), Functions.invoke(StandardFunctions.class, "MUL_LTIME",
				toLTimeValue(Duration.ofSeconds(21)), toIntValue((short) 2)));
		assertEquals(toLTimeValue(Duration.ofSeconds(42)), Functions.invoke(StandardFunctions.class, "MUL_LTIME",
				toLTimeValue(Duration.ofSeconds(21)), toRealValue(2)));
	}

	@Test
	void testDivTime() throws Throwable {
		assertEquals(toTimeValue(Duration.ofSeconds(21)), Functions.invoke(StandardFunctions.class, "DIV",
				toTimeValue(Duration.ofSeconds(42)), toIntValue((short) 2)));
		assertEquals(toTimeValue(Duration.ofSeconds(21)),
				Functions.invoke(StandardFunctions.class, "DIV", toTimeValue(Duration.ofSeconds(42)), toRealValue(2)));
		assertEquals(toTimeValue(Duration.ofSeconds(21)), Functions.invoke(StandardFunctions.class, "DIV_TIME",
				toTimeValue(Duration.ofSeconds(42)), toIntValue((short) 2)));
		assertEquals(toTimeValue(Duration.ofSeconds(21)), Functions.invoke(StandardFunctions.class, "DIV_TIME",
				toTimeValue(Duration.ofSeconds(42)), toRealValue(2)));
	}

	@Test
	void testDivLTime() throws Throwable {
		assertEquals(toLTimeValue(Duration.ofSeconds(21)), Functions.invoke(StandardFunctions.class, "DIV",
				toLTimeValue(Duration.ofSeconds(42)), toIntValue((short) 2)));
		assertEquals(toLTimeValue(Duration.ofSeconds(21)),
				Functions.invoke(StandardFunctions.class, "DIV", toLTimeValue(Duration.ofSeconds(42)), toRealValue(2)));
		assertEquals(toLTimeValue(Duration.ofSeconds(21)), Functions.invoke(StandardFunctions.class, "DIV_LTIME",
				toLTimeValue(Duration.ofSeconds(42)), toIntValue((short) 2)));
		assertEquals(toLTimeValue(Duration.ofSeconds(21)), Functions.invoke(StandardFunctions.class, "DIV_LTIME",
				toLTimeValue(Duration.ofSeconds(42)), toRealValue(2)));
	}

	@Test
	void testConcatDateTod() throws Throwable {
		assertEquals(toDateAndTimeValue(LocalDateTime.of(2017, 4, 21, 21, 42)),
				Functions.invoke(StandardFunctions.class, "CONCAT_DATE_TOD", toDateValue(LocalDate.of(2017, 4, 21)),
						toTimeOfDayValue(LocalTime.of(21, 42))));
	}

	@Test
	void testConcatLDateLTod() throws Throwable {
		assertEquals(toLDateAndTimeValue(LocalDateTime.of(2017, 4, 21, 21, 42)),
				Functions.invoke(StandardFunctions.class, "CONCAT_LDATE_LTOD", toLDateValue(LocalDate.of(2017, 4, 21)),
						toLTimeOfDayValue(LocalTime.of(21, 42))));
	}

	@Test
	void testConcatDate() throws Throwable {
		assertEquals(toDateValue(LocalDate.of(2017, 4, 21)), Functions.invoke(StandardFunctions.class, "CONCAT_DATE",
				toIntValue((short) 2017), toIntValue((short) 4), toIntValue((short) 21)));
	}

	@Test
	void testConcatTod() throws Throwable {
		assertEquals(toTimeOfDayValue(LocalTime.of(17, 4, 21)), Functions.invoke(StandardFunctions.class, "CONCAT_TOD",
				toIntValue((short) 17), toIntValue((short) 4), toIntValue((short) 21), toIntValue((short) 0)));
	}

	@Test
	void testConcatLTod() throws Throwable {
		assertEquals(toLTimeOfDayValue(LocalTime.of(17, 4, 21)),
				Functions.invoke(StandardFunctions.class, "CONCAT_LTOD", toIntValue((short) 17), toIntValue((short) 4),
						toIntValue((short) 21), toIntValue((short) 0)));
	}

	@Test
	void testConcatDT() throws Throwable {
		assertEquals(toDateAndTimeValue(LocalDateTime.of(2017, 4, 21, 21, 42)),
				Functions.invoke(StandardFunctions.class, "CONCAT_DT", toIntValue((short) 2017), toIntValue((short) 4),
						toIntValue((short) 21), toIntValue((short) 21), toIntValue((short) 42), toIntValue((short) 0),
						toIntValue((short) 0)));
	}

	@Test
	void testConcatLDT() throws Throwable {
		assertEquals(toLDateAndTimeValue(LocalDateTime.of(2017, 4, 21, 21, 42)),
				Functions.invoke(StandardFunctions.class, "CONCAT_LDT", toIntValue((short) 2017), toIntValue((short) 4),
						toIntValue((short) 21), toIntValue((short) 21), toIntValue((short) 42), toIntValue((short) 0),
						toIntValue((short) 0)));
	}

	@Test
	void testSplitDate() throws Throwable {
		final ElementaryVariable<AnyElementaryValue> year = new ElementaryVariable<>("YEAR",
				IecTypes.ElementaryTypes.INT);
		final ElementaryVariable<AnyElementaryValue> month = new ElementaryVariable<>("MONTH",
				IecTypes.ElementaryTypes.INT);
		final ElementaryVariable<AnyElementaryValue> day = new ElementaryVariable<>("DAY",
				IecTypes.ElementaryTypes.INT);
		Functions.invoke(StandardFunctions.class, "SPLIT_DATE", toDateValue(LocalDate.of(2017, 4, 21)), year, month,
				day);
		assertEquals(toIntValue((short) 2017), year.getValue());
		assertEquals(toIntValue((short) 4), month.getValue());
		assertEquals(toIntValue((short) 21), day.getValue());
	}

	@Test
	void testSplitTod() throws Throwable {
		final ElementaryVariable<AnyElementaryValue> hour = new ElementaryVariable<>("HOUR",
				IecTypes.ElementaryTypes.INT);
		final ElementaryVariable<AnyElementaryValue> minute = new ElementaryVariable<>("MINUTE",
				IecTypes.ElementaryTypes.INT);
		final ElementaryVariable<AnyElementaryValue> second = new ElementaryVariable<>("SECOND",
				IecTypes.ElementaryTypes.INT);
		final ElementaryVariable<AnyElementaryValue> milli = new ElementaryVariable<>("MILLI",
				IecTypes.ElementaryTypes.INT);
		Functions.invoke(StandardFunctions.class, "SPLIT_TOD", toTimeOfDayValue(LocalTime.of(17, 4, 21)), hour, minute,
				second, milli);
		assertEquals(toIntValue((short) 17), hour.getValue());
		assertEquals(toIntValue((short) 4), minute.getValue());
		assertEquals(toIntValue((short) 21), second.getValue());
		assertEquals(toIntValue((short) 0), milli.getValue());
	}

	@Test
	void testSplitLTod() throws Throwable {
		final ElementaryVariable<AnyElementaryValue> hour = new ElementaryVariable<>("HOUR",
				IecTypes.ElementaryTypes.INT);
		final ElementaryVariable<AnyElementaryValue> minute = new ElementaryVariable<>("MINUTE",
				IecTypes.ElementaryTypes.INT);
		final ElementaryVariable<AnyElementaryValue> second = new ElementaryVariable<>("SECOND",
				IecTypes.ElementaryTypes.INT);
		final ElementaryVariable<AnyElementaryValue> milli = new ElementaryVariable<>("MILLI",
				IecTypes.ElementaryTypes.INT);
		Functions.invoke(StandardFunctions.class, "SPLIT_LTOD", toLTimeOfDayValue(LocalTime.of(17, 4, 21)), hour,
				minute, second, milli);
		assertEquals(toIntValue((short) 17), hour.getValue());
		assertEquals(toIntValue((short) 4), minute.getValue());
		assertEquals(toIntValue((short) 21), second.getValue());
		assertEquals(toIntValue((short) 0), milli.getValue());
	}

	@Test
	void testSplitDT() throws Throwable {
		final ElementaryVariable<AnyElementaryValue> year = new ElementaryVariable<>("YEAR",
				IecTypes.ElementaryTypes.INT);
		final ElementaryVariable<AnyElementaryValue> month = new ElementaryVariable<>("MONTH",
				IecTypes.ElementaryTypes.INT);
		final ElementaryVariable<AnyElementaryValue> day = new ElementaryVariable<>("DAY",
				IecTypes.ElementaryTypes.INT);
		final ElementaryVariable<AnyElementaryValue> hour = new ElementaryVariable<>("HOUR",
				IecTypes.ElementaryTypes.INT);
		final ElementaryVariable<AnyElementaryValue> minute = new ElementaryVariable<>("MINUTE",
				IecTypes.ElementaryTypes.INT);
		final ElementaryVariable<AnyElementaryValue> second = new ElementaryVariable<>("SECOND",
				IecTypes.ElementaryTypes.INT);
		final ElementaryVariable<AnyElementaryValue> milli = new ElementaryVariable<>("MILLI",
				IecTypes.ElementaryTypes.INT);
		Functions.invoke(StandardFunctions.class, "SPLIT_DT",
				toDateAndTimeValue(LocalDateTime.of(2017, 4, 21, 21, 42, 17, (7 * 1_000_000))), year, month, day, hour,
				minute, second, milli);
		assertEquals(toIntValue((short) 2017), year.getValue());
		assertEquals(toIntValue((short) 4), month.getValue());
		assertEquals(toIntValue((short) 21), day.getValue());
		assertEquals(toIntValue((short) 21), hour.getValue());
		assertEquals(toIntValue((short) 42), minute.getValue());
		assertEquals(toIntValue((short) 17), second.getValue());
		assertEquals(toIntValue((short) 7), milli.getValue());
	}

	@Test
	void testSplitLDT() throws Throwable {
		final ElementaryVariable<AnyElementaryValue> year = new ElementaryVariable<>("YEAR",
				IecTypes.ElementaryTypes.INT);
		final ElementaryVariable<AnyElementaryValue> month = new ElementaryVariable<>("MONTH",
				IecTypes.ElementaryTypes.INT);
		final ElementaryVariable<AnyElementaryValue> day = new ElementaryVariable<>("DAY",
				IecTypes.ElementaryTypes.INT);
		final ElementaryVariable<AnyElementaryValue> hour = new ElementaryVariable<>("HOUR",
				IecTypes.ElementaryTypes.INT);
		final ElementaryVariable<AnyElementaryValue> minute = new ElementaryVariable<>("MINUTE",
				IecTypes.ElementaryTypes.INT);
		final ElementaryVariable<AnyElementaryValue> second = new ElementaryVariable<>("SECOND",
				IecTypes.ElementaryTypes.INT);
		final ElementaryVariable<AnyElementaryValue> milli = new ElementaryVariable<>("MILLI",
				IecTypes.ElementaryTypes.INT);
		Functions.invoke(StandardFunctions.class, "SPLIT_LDT",
				toLDateAndTimeValue(LocalDateTime.of(2017, 4, 21, 21, 42, 17, (7 * 1_000_000))), year, month, day, hour,
				minute, second, milli);
		assertEquals(toIntValue((short) 2017), year.getValue());
		assertEquals(toIntValue((short) 4), month.getValue());
		assertEquals(toIntValue((short) 21), day.getValue());
		assertEquals(toIntValue((short) 21), hour.getValue());
		assertEquals(toIntValue((short) 42), minute.getValue());
		assertEquals(toIntValue((short) 17), second.getValue());
		assertEquals(toIntValue((short) 7), milli.getValue());
	}

	@Test
	void testDayOfWeek() throws Throwable {
		assertEquals(toUSIntValue((byte) 0), // SUNDAY
				Functions.invoke(StandardFunctions.class, "DAY_OF_WEEK", toDateValue(LocalDate.of(2023, 10, 1))));
		assertEquals(toUSIntValue((byte) 1), // MONDAY
				Functions.invoke(StandardFunctions.class, "DAY_OF_WEEK", toDateValue(LocalDate.of(2023, 10, 2))));
		assertEquals(toUSIntValue((byte) 2), // TUESDAY
				Functions.invoke(StandardFunctions.class, "DAY_OF_WEEK", toDateValue(LocalDate.of(2023, 10, 3))));
		assertEquals(toUSIntValue((byte) 3), // WEDNESDAY
				Functions.invoke(StandardFunctions.class, "DAY_OF_WEEK", toDateValue(LocalDate.of(2023, 10, 4))));
		assertEquals(toUSIntValue((byte) 4), // THURSDAY
				Functions.invoke(StandardFunctions.class, "DAY_OF_WEEK", toDateValue(LocalDate.of(2023, 10, 5))));
		assertEquals(toUSIntValue((byte) 5), // FRIDAY
				Functions.invoke(StandardFunctions.class, "DAY_OF_WEEK", toDateValue(LocalDate.of(2023, 10, 6))));
		assertEquals(toUSIntValue((byte) 6), // SATURDAY
				Functions.invoke(StandardFunctions.class, "DAY_OF_WEEK", toDateValue(LocalDate.of(2023, 10, 7))));
	}

	@Test
	void testToBigEndian() throws Throwable {
		assertEquals(toIntValue((short) 0x1704),
				Functions.invoke(StandardFunctions.class, "TO_BIG_ENDIAN", toIntValue((short) 0x1704)));
	}

	@Test
	void testToLittleEndianAnyMagnitude() throws Throwable {
		assertEquals(toSIntValue((byte) 0x17),
				Functions.invoke(StandardFunctions.class, "TO_LITTLE_ENDIAN", toSIntValue((byte) 0x17)));
		assertEquals(toIntValue((short) 0x1704),
				Functions.invoke(StandardFunctions.class, "TO_LITTLE_ENDIAN", toIntValue((short) 0x0417)));
		assertEquals(toDIntValue(0x1704),
				Functions.invoke(StandardFunctions.class, "TO_LITTLE_ENDIAN", toDIntValue(0x04170000)));
		assertEquals(toLIntValue(0x1704),
				Functions.invoke(StandardFunctions.class, "TO_LITTLE_ENDIAN", toLIntValue(0x0417000000000000L)));
		assertEquals(toUSIntValue((byte) 0x17),
				Functions.invoke(StandardFunctions.class, "TO_LITTLE_ENDIAN", toUSIntValue((byte) 0x17)));
		assertEquals(toUIntValue((short) 0x1704),
				Functions.invoke(StandardFunctions.class, "TO_LITTLE_ENDIAN", toUIntValue((short) 0x0417)));
		assertEquals(toUDIntValue(0x1704),
				Functions.invoke(StandardFunctions.class, "TO_LITTLE_ENDIAN", toUDIntValue(0x04170000)));
		assertEquals(toULIntValue(0x1704),
				Functions.invoke(StandardFunctions.class, "TO_LITTLE_ENDIAN", toULIntValue(0x0417000000000000L)));
		assertEquals(toRealValue(Float.intBitsToFloat(0x00008841)),
				Functions.invoke(StandardFunctions.class, "TO_LITTLE_ENDIAN", toRealValue(17)));
		assertEquals(toLRealValue(Double.longBitsToDouble(0x0000000000003140L)),
				Functions.invoke(StandardFunctions.class, "TO_LITTLE_ENDIAN", toLRealValue(17)));
		assertEquals(toTimeValue(0x0417000000000000L),
				Functions.invoke(StandardFunctions.class, "TO_LITTLE_ENDIAN", toTimeValue(0x1704)));
		assertEquals(toLTimeValue(0x0417000000000000L),
				Functions.invoke(StandardFunctions.class, "TO_LITTLE_ENDIAN", toLTimeValue(0x1704)));
	}

	@Test
	void testToLittleEndianAnyBit() throws Throwable {
		assertEquals(toBoolValue(false),
				Functions.invoke(StandardFunctions.class, "TO_LITTLE_ENDIAN", toBoolValue(false)));
		assertEquals(toBoolValue(true),
				Functions.invoke(StandardFunctions.class, "TO_LITTLE_ENDIAN", toBoolValue(true)));
		assertEquals(toByteValue((byte) 0x17),
				Functions.invoke(StandardFunctions.class, "TO_LITTLE_ENDIAN", toByteValue((byte) 0x0417)));
		assertEquals(toWordValue((short) 0x1704),
				Functions.invoke(StandardFunctions.class, "TO_LITTLE_ENDIAN", toWordValue((short) 0x0417)));
		assertEquals(toDWordValue(0x1704),
				Functions.invoke(StandardFunctions.class, "TO_LITTLE_ENDIAN", toDWordValue(0x04170000)));
		assertEquals(toLWordValue(0x1704),
				Functions.invoke(StandardFunctions.class, "TO_LITTLE_ENDIAN", toLWordValue(0x0417000000000000L)));
	}

	@Test
	void testToLittleEndianAnyDate() throws Throwable {
		assertEquals(toDateValue(0x1704),
				Functions.invoke(StandardFunctions.class, "TO_LITTLE_ENDIAN", toDateValue(0x1704)));
		assertEquals(toLDateValue(0x1704),
				Functions.invoke(StandardFunctions.class, "TO_LITTLE_ENDIAN", toLDateValue(0x1704)));
		assertEquals(toTimeOfDayValue(0x1704),
				Functions.invoke(StandardFunctions.class, "TO_LITTLE_ENDIAN", toTimeOfDayValue(0x1704)));
		assertEquals(toLTimeOfDayValue(0x1704),
				Functions.invoke(StandardFunctions.class, "TO_LITTLE_ENDIAN", toLTimeOfDayValue(0x1704)));
		assertEquals(toDateAndTimeValue(0x1704),
				Functions.invoke(StandardFunctions.class, "TO_LITTLE_ENDIAN", toDateAndTimeValue(0x1704)));
		assertEquals(toLDateAndTimeValue(0x1704),
				Functions.invoke(StandardFunctions.class, "TO_LITTLE_ENDIAN", toLDateAndTimeValue(0x1704)));
	}

	@Test
	void testToLittleEndianAnyChars() throws Throwable {
		assertEquals(toCharValue("a"), Functions.invoke(StandardFunctions.class, "TO_LITTLE_ENDIAN", toCharValue("a")));
		assertEquals(toStringValue("abc"),
				Functions.invoke(StandardFunctions.class, "TO_LITTLE_ENDIAN", toStringValue("abc")));
		assertEquals(toWCharValue("\u6100"),
				Functions.invoke(StandardFunctions.class, "TO_LITTLE_ENDIAN", toWCharValue('a')));
		assertEquals(toWStringValue("\u6100\u6200\u6300"),
				Functions.invoke(StandardFunctions.class, "TO_LITTLE_ENDIAN", toWStringValue("abc")));
	}

	@Test
	void testToLittleEndianArray() throws Throwable {
		final ArrayValue array = new ArrayVariable("TEST",
				ArrayVariable.newArrayType(IecTypes.ElementaryTypes.INT, ArrayVariable.newSubrange(0, 1))).getValue();
		array.get(0).setValue(toIntValue((short) 0x1704));
		array.get(1).setValue(toIntValue((short) 0x2142));
		assertIterableEquals(List.of(toIntValue((short) 0x0417), toIntValue((short) 0x4221)),
				(ArrayValue) Functions.invoke(StandardFunctions.class, "TO_LITTLE_ENDIAN", array));
		assertIterableEquals(List.of(toIntValue((short) 0x1704), toIntValue((short) 0x2142)), array);
	}

	@Test
	void testToLittleEndianStruct() throws Throwable {
		final StructuredType structuredType = DataFactory.eINSTANCE.createStructuredType();
		structuredType.getMemberVariables().add(createVarDeclaration("a", ElementaryTypes.INT));
		structuredType.getMemberVariables().add(createVarDeclaration("b", ElementaryTypes.INT));
		final StructValue struct = new StructVariable("TEST", structuredType).getValue();
		struct.get("a").setValue(toIntValue((short) 0x1704));
		struct.get("b").setValue(toIntValue((short) 0x2142));
		final StructValue reverse = ((StructValue) Functions.invoke(StandardFunctions.class, "TO_LITTLE_ENDIAN",
				struct));
		assertEquals(toIntValue((short) 0x0417), reverse.get("a").getValue());
		assertEquals(toIntValue((short) 0x4221), reverse.get("b").getValue());
		assertEquals(toIntValue((short) 0x1704), struct.get("a").getValue());
		assertEquals(toIntValue((short) 0x2142), struct.get("b").getValue());
	}

	@Test
	void testFromBigEndian() throws Throwable {
		assertEquals(toIntValue((short) 0x1704),
				Functions.invoke(StandardFunctions.class, "FROM_BIG_ENDIAN", toIntValue((short) 0x1704)));
	}

	@Test
	void testFromLittleEndianAnuMagnitude() throws Throwable {
		assertEquals(toSIntValue((byte) 0x17),
				Functions.invoke(StandardFunctions.class, "FROM_LITTLE_ENDIAN", toSIntValue((byte) 0x17)));
		assertEquals(toIntValue((short) 0x1704),
				Functions.invoke(StandardFunctions.class, "FROM_LITTLE_ENDIAN", toIntValue((short) 0x0417)));
		assertEquals(toDIntValue(0x1704),
				Functions.invoke(StandardFunctions.class, "FROM_LITTLE_ENDIAN", toDIntValue(0x04170000)));
		assertEquals(toLIntValue(0x1704),
				Functions.invoke(StandardFunctions.class, "FROM_LITTLE_ENDIAN", toLIntValue(0x0417000000000000L)));
		assertEquals(toUSIntValue((byte) 0x17),
				Functions.invoke(StandardFunctions.class, "FROM_LITTLE_ENDIAN", toUSIntValue((byte) 0x17)));
		assertEquals(toUIntValue((short) 0x1704),
				Functions.invoke(StandardFunctions.class, "FROM_LITTLE_ENDIAN", toUIntValue((short) 0x0417)));
		assertEquals(toUDIntValue(0x1704),
				Functions.invoke(StandardFunctions.class, "FROM_LITTLE_ENDIAN", toUDIntValue(0x04170000)));
		assertEquals(toULIntValue(0x1704),
				Functions.invoke(StandardFunctions.class, "FROM_LITTLE_ENDIAN", toULIntValue(0x0417000000000000L)));
		assertEquals(toRealValue(17), Functions.invoke(StandardFunctions.class, "FROM_LITTLE_ENDIAN",
				toRealValue(Float.intBitsToFloat(0x00008841))));
		assertEquals(toLRealValue(17), Functions.invoke(StandardFunctions.class, "FROM_LITTLE_ENDIAN",
				toLRealValue(Double.longBitsToDouble(0x0000000000003140L))));
		assertEquals(toTimeValue(0x1704),
				Functions.invoke(StandardFunctions.class, "FROM_LITTLE_ENDIAN", toTimeValue(0x0417000000000000L)));
		assertEquals(toLTimeValue(0x1704),
				Functions.invoke(StandardFunctions.class, "FROM_LITTLE_ENDIAN", toLTimeValue(0x0417000000000000L)));
	}

	@Test
	void testFromLittleEndianAnyBit() throws Throwable {
		assertEquals(toBoolValue(false),
				Functions.invoke(StandardFunctions.class, "FROM_LITTLE_ENDIAN", toBoolValue(false)));
		assertEquals(toBoolValue(true),
				Functions.invoke(StandardFunctions.class, "FROM_LITTLE_ENDIAN", toBoolValue(true)));
		assertEquals(toByteValue((byte) 0x17),
				Functions.invoke(StandardFunctions.class, "FROM_LITTLE_ENDIAN", toByteValue((byte) 0x0417)));
		assertEquals(toWordValue((short) 0x1704),
				Functions.invoke(StandardFunctions.class, "FROM_LITTLE_ENDIAN", toWordValue((short) 0x0417)));
		assertEquals(toDWordValue(0x1704),
				Functions.invoke(StandardFunctions.class, "FROM_LITTLE_ENDIAN", toDWordValue(0x04170000)));
		assertEquals(toLWordValue(0x1704),
				Functions.invoke(StandardFunctions.class, "FROM_LITTLE_ENDIAN", toLWordValue(0x0417000000000000L)));
	}

	@Test
	void testFromLittleEndianAnyDate() throws Throwable {
		assertEquals(toDateValue(0x1704),
				Functions.invoke(StandardFunctions.class, "FROM_LITTLE_ENDIAN", toDateValue(0x1704)));
		assertEquals(toLDateValue(0x1704),
				Functions.invoke(StandardFunctions.class, "FROM_LITTLE_ENDIAN", toLDateValue(0x1704)));
		assertEquals(toTimeOfDayValue(0x1704),
				Functions.invoke(StandardFunctions.class, "FROM_LITTLE_ENDIAN", toTimeOfDayValue(0x1704)));
		assertEquals(toLTimeOfDayValue(0x1704),
				Functions.invoke(StandardFunctions.class, "FROM_LITTLE_ENDIAN", toLTimeOfDayValue(0x1704)));
		assertEquals(toDateAndTimeValue(0x1704),
				Functions.invoke(StandardFunctions.class, "FROM_LITTLE_ENDIAN", toDateAndTimeValue(0x1704)));
		assertEquals(toLDateAndTimeValue(0x1704),
				Functions.invoke(StandardFunctions.class, "FROM_LITTLE_ENDIAN", toLDateAndTimeValue(0x1704)));
	}

	@Test
	void testFromLittleEndianAnyChars() throws Throwable {
		assertEquals(toCharValue("a"),
				Functions.invoke(StandardFunctions.class, "FROM_LITTLE_ENDIAN", toCharValue("a")));
		assertEquals(toStringValue("abc"),
				Functions.invoke(StandardFunctions.class, "FROM_LITTLE_ENDIAN", toStringValue("abc")));
		assertEquals(toWCharValue('a'),
				Functions.invoke(StandardFunctions.class, "FROM_LITTLE_ENDIAN", toWCharValue('\u6100')));
		assertEquals(toWStringValue("abc"),
				Functions.invoke(StandardFunctions.class, "FROM_LITTLE_ENDIAN", toWStringValue("\u6100\u6200\u6300")));
	}

	@Test
	void testFromLittleEndianArray() throws Throwable {
		final ArrayValue array = new ArrayVariable("TEST",
				ArrayVariable.newArrayType(IecTypes.ElementaryTypes.INT, ArrayVariable.newSubrange(0, 1))).getValue();
		array.get(0).setValue(toIntValue((short) 0x0417));
		array.get(1).setValue(toIntValue((short) 0x4221));
		assertIterableEquals(List.of(toIntValue((short) 0x1704), toIntValue((short) 0x2142)),
				(ArrayValue) Functions.invoke(StandardFunctions.class, "FROM_LITTLE_ENDIAN", array));
		assertIterableEquals(List.of(toIntValue((short) 0x0417), toIntValue((short) 0x4221)), array);
	}

	@Test
	void testFromLittleEndianStruct() throws Throwable {
		final StructuredType structuredType = DataFactory.eINSTANCE.createStructuredType();
		structuredType.getMemberVariables().add(createVarDeclaration("a", ElementaryTypes.INT));
		structuredType.getMemberVariables().add(createVarDeclaration("b", ElementaryTypes.INT));
		final StructValue struct = new StructVariable("TEST", structuredType).getValue();
		struct.get("a").setValue(toIntValue((short) 0x0417));
		struct.get("b").setValue(toIntValue((short) 0x4221));
		final StructValue reverse = ((StructValue) Functions.invoke(StandardFunctions.class, "FROM_LITTLE_ENDIAN",
				struct));
		assertEquals(toIntValue((short) 0x1704), reverse.get("a").getValue());
		assertEquals(toIntValue((short) 0x2142), reverse.get("b").getValue());
		assertEquals(toIntValue((short) 0x0417), struct.get("a").getValue());
		assertEquals(toIntValue((short) 0x4221), struct.get("b").getValue());
	}

	@Test
	void testIsValid() throws Throwable {
		assertEquals(toBoolValue(true), Functions.invoke(StandardFunctions.class, "IS_VALID", toLRealValue(17)));
		assertEquals(toBoolValue(false),
				Functions.invoke(StandardFunctions.class, "IS_VALID", toLRealValue(Double.POSITIVE_INFINITY)));
		assertEquals(toBoolValue(false),
				Functions.invoke(StandardFunctions.class, "IS_VALID", toLRealValue(Double.NEGATIVE_INFINITY)));
		assertEquals(toBoolValue(false),
				Functions.invoke(StandardFunctions.class, "IS_VALID", toLRealValue(Double.NaN)));
	}

	@Test
	void testTimeConversion() throws Throwable {
		assertEquals(toLIntValue(17L),
				Functions.invoke(StandardFunctions.class, "TIME_IN_S_TO_LINT", toTimeValue(Duration.ofSeconds(17))));
		assertEquals(toLIntValue((17L * 1000)),
				Functions.invoke(StandardFunctions.class, "TIME_IN_MS_TO_LINT", toTimeValue(Duration.ofSeconds(17))));
		assertEquals(toLIntValue((17L * 1_000_000)),
				Functions.invoke(StandardFunctions.class, "TIME_IN_US_TO_LINT", toTimeValue(Duration.ofSeconds(17))));
		assertEquals(toLIntValue((17L * 1_000_000_000)),
				Functions.invoke(StandardFunctions.class, "TIME_IN_NS_TO_LINT", toTimeValue(Duration.ofSeconds(17))));
		assertEquals(toULIntValue(17L),
				Functions.invoke(StandardFunctions.class, "TIME_IN_S_TO_ULINT", toTimeValue(Duration.ofSeconds(17))));
		assertEquals(toULIntValue((17L * 1000)),
				Functions.invoke(StandardFunctions.class, "TIME_IN_MS_TO_ULINT", toTimeValue(Duration.ofSeconds(17))));
		assertEquals(toULIntValue((17L * 1_000_000)),
				Functions.invoke(StandardFunctions.class, "TIME_IN_US_TO_ULINT", toTimeValue(Duration.ofSeconds(17))));
		assertEquals(toULIntValue((17L * 1_000_000_000)),
				Functions.invoke(StandardFunctions.class, "TIME_IN_NS_TO_ULINT", toTimeValue(Duration.ofSeconds(17))));
		assertEquals(toLRealValue(17.0),
				Functions.invoke(StandardFunctions.class, "TIME_IN_S_TO_LREAL", toTimeValue(Duration.ofSeconds(17))));
		assertEquals(toLRealValue((17.0 * 1000)),
				Functions.invoke(StandardFunctions.class, "TIME_IN_MS_TO_LREAL", toTimeValue(Duration.ofSeconds(17))));
		assertEquals(toLRealValue((17.0 * 1_000_000)),
				Functions.invoke(StandardFunctions.class, "TIME_IN_US_TO_LREAL", toTimeValue(Duration.ofSeconds(17))));
		assertEquals(toLRealValue((17.0 * 1_000_000_000)),
				Functions.invoke(StandardFunctions.class, "TIME_IN_NS_TO_LREAL", toTimeValue(Duration.ofSeconds(17))));
	}

	@Test
	void testNowMonotonic() throws Throwable {
		final long before = System.nanoTime();
		final TimeValue now = ((TimeValue) Functions.invoke(StandardFunctions.class, "NOW_MONOTONIC"));
		final long after = System.nanoTime();
		assertTrue(before <= now.longValue());
		assertTrue(after >= now.longValue());
	}

	@ParameterizedTest(name = "{index}: {0} as {1}")
	@MethodSource("typeArgumentsCartesianProvider")
	void testConversions(final String typeName, final String castTypeName) throws Throwable {
		final DataType type = IecTypes.ElementaryTypes.getTypeByName(typeName);
		final DataType castType = IecTypes.ElementaryTypes.getTypeByName(castTypeName);
		final String functionName = typeName + "_TO_" + castTypeName;
		try {
			Functions.findMethodFromDataTypes(StandardFunctions.class, functionName, type);
		} catch (final NoSuchMethodException e) {
			return; // no such conversion
		}
		final Value expected;
		if (castType instanceof AnyStringType && type instanceof AnyCharType) {
			expected = wrapValue("\u0000", castType);
		} else {
			expected = defaultValue(castType);
		}
		assertEquals(expected, Functions.invoke(StandardFunctions.class, functionName, defaultValue(type)));
	}

	@ParameterizedTest(name = "{index}: {0} as {1}")
	@MethodSource("typeAnyRealAndAnyIntArgumentsCartesianProvider")
	void testRealConversions(final String typeName, final String castTypeName) throws Throwable {
		final DataType type = IecTypes.ElementaryTypes.getTypeByName(typeName);
		final DataType castType = IecTypes.ElementaryTypes.getTypeByName(castTypeName);
		for (final String functionName : List.of(typeName + "_TO_" + castTypeName, "TO_" + castTypeName)) {
			assertEquals(wrapValue(BigInteger.valueOf(2), castType),
					Functions.invoke(StandardFunctions.class, functionName, wrapValue(BigDecimal.valueOf(1.6), type)));
			assertEquals(wrapValue(BigInteger.valueOf(-2), castType),
					Functions.invoke(StandardFunctions.class, functionName, wrapValue(BigDecimal.valueOf(-1.6), type)));

			assertEquals(wrapValue(BigInteger.valueOf(2), castType),
					Functions.invoke(StandardFunctions.class, functionName, wrapValue(BigDecimal.valueOf(1.5), type)));
			assertEquals(wrapValue(BigInteger.valueOf(-2), castType),
					Functions.invoke(StandardFunctions.class, functionName, wrapValue(BigDecimal.valueOf(-1.5), type)));

			assertEquals(wrapValue(BigInteger.valueOf(1), castType),
					Functions.invoke(StandardFunctions.class, functionName, wrapValue(BigDecimal.valueOf(1.4), type)));
			assertEquals(wrapValue(BigInteger.valueOf(-1), castType),
					Functions.invoke(StandardFunctions.class, functionName, wrapValue(BigDecimal.valueOf(-1.4), type)));

			assertEquals(wrapValue(BigInteger.valueOf(2), castType),
					Functions.invoke(StandardFunctions.class, functionName, wrapValue(BigDecimal.valueOf(2.5), type)));
			assertEquals(wrapValue(BigInteger.valueOf(-2), castType),
					Functions.invoke(StandardFunctions.class, functionName, wrapValue(BigDecimal.valueOf(-2.5), type)));
		}
	}

	@ParameterizedTest(name = "{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	void testStringConversions(final String typeName) throws Throwable {
		final DataType type = IecTypes.ElementaryTypes.getTypeByName(typeName);
		final String functionTypeAsStringName = typeName + "_AS_STRING";
		final String functionStringAsTypeName = "STRING_AS_" + typeName;
		final String functionTypeAsWStringName = typeName + "_AS_WSTRING";
		final String functionWStringAsTypeName = "WSTRING_AS_" + typeName;
		if (type instanceof AnyMagnitudeType || type instanceof AnyBitType) {
			// *_AS_STRING
			assertEquals(toStringValue(defaultValue(type).toString()),
					Functions.invoke(StandardFunctions.class, functionTypeAsStringName, defaultValue(type)));
			assertEquals(toStringValue(StringValue.toStringValue(wrapValue(Integer.valueOf(17), type).toString())),
					Functions.invoke(StandardFunctions.class, functionTypeAsStringName,
							wrapValue(Integer.valueOf(17), type)));
			// STRING_AS_*
			assertEquals(defaultValue(type), Functions.invoke(StandardFunctions.class, functionStringAsTypeName,
					toStringValue(defaultValue(type).toString())));
			assertEquals(wrapValue(Integer.valueOf(17), type), Functions.invoke(StandardFunctions.class,
					functionStringAsTypeName, toStringValue(wrapValue(Integer.valueOf(17), type).toString())));
			// *_AS_WSTRING
			assertEquals(toWStringValue(defaultValue(type).toString()),
					Functions.invoke(StandardFunctions.class, functionTypeAsWStringName, defaultValue(type)));
			assertEquals(toWStringValue(WStringValue.toWStringValue(wrapValue(Integer.valueOf(17), type).toString())),
					Functions.invoke(StandardFunctions.class, functionTypeAsWStringName,
							wrapValue(Integer.valueOf(17), type)));
			// WSTRING_AS_*
			assertEquals(defaultValue(type), Functions.invoke(StandardFunctions.class, functionWStringAsTypeName,
					toWStringValue(defaultValue(type).toString())));
			assertEquals(wrapValue(Integer.valueOf(17), type), Functions.invoke(StandardFunctions.class,
					functionWStringAsTypeName, toWStringValue(wrapValue(Integer.valueOf(17), type).toString())));
		} else {
			// *_AS_STRING
			assertThrows(NoSuchMethodException.class,
					() -> Functions.findMethodFromDataTypes(StandardFunctions.class, functionTypeAsStringName, type));
			// STRING_AS_*
			assertThrows(NoSuchMethodException.class,
					() -> Functions.findMethodFromDataTypes(StandardFunctions.class, functionStringAsTypeName, type));
			// *_AS_WSTRING
			assertThrows(NoSuchMethodException.class,
					() -> Functions.findMethodFromDataTypes(StandardFunctions.class, functionTypeAsWStringName, type));
			// WSTRING_AS_*
			assertThrows(NoSuchMethodException.class,
					() -> Functions.findMethodFromDataTypes(StandardFunctions.class, functionWStringAsTypeName, type));
		}
	}

	@ParameterizedTest(name = "{index}: {0}")
	@MethodSource("typeAnyIntArgumentsProvider")
	void testTruncConversions(final String typeName) throws Throwable {
		final DataType type = IecTypes.ElementaryTypes.getTypeByName(typeName);
		final String functionName = "TRUNC_" + typeName;
		assertEquals(defaultValue(type), Functions.invoke(StandardFunctions.class, functionName, toRealValue(0)));
		assertEquals(wrapValue(Integer.valueOf(17), type),
				Functions.invoke(StandardFunctions.class, functionName, toRealValue(17)));
		assertEquals(defaultValue(type), Functions.invoke(StandardFunctions.class, functionName, toLRealValue(0)));
		assertEquals(wrapValue(Integer.valueOf(17), type),
				Functions.invoke(StandardFunctions.class, functionName, toLRealValue(17)));
	}

	@ParameterizedTest(name = "{index}: {0}")
	@MethodSource("typeAnyIntArgumentsProvider")
	void testRealTruncConversions(final String typeName) throws Throwable {
		final DataType type = IecTypes.ElementaryTypes.getTypeByName(typeName);
		final String functionName = "REAL_TRUNC_" + typeName;
		assertEquals(defaultValue(type), Functions.invoke(StandardFunctions.class, functionName, toRealValue(0)));
		assertEquals(wrapValue(Integer.valueOf(17), type),
				Functions.invoke(StandardFunctions.class, functionName, toRealValue(17)));
	}

	@ParameterizedTest(name = "{index}: {0}")
	@MethodSource("typeAnyIntArgumentsProvider")
	void testLRealTruncConversions(final String typeName) throws Throwable {
		final DataType type = IecTypes.ElementaryTypes.getTypeByName(typeName);
		final String functionName = "LREAL_TRUNC_" + typeName;
		assertEquals(defaultValue(type), Functions.invoke(StandardFunctions.class, functionName, toLRealValue(0)));
		assertEquals(wrapValue(Integer.valueOf(17), type),
				Functions.invoke(StandardFunctions.class, functionName, toLRealValue(17)));
	}

	@ParameterizedTest(name = "{index}: {0} and {1}")
	@MethodSource("typeAnyUnsignedAndAnyBitExceptBoolArgumentsCartesianProvider")
	void testToBcdFunctions(final String intTypeName, final String bitTypeName) throws Throwable {
		final DataType intType = IecTypes.ElementaryTypes.getTypeByName(intTypeName);
		final DataType bitType = IecTypes.ElementaryTypes.getTypeByName(bitTypeName);
		final String functionName = "TO_BCD_" + bitTypeName;
		assertEquals(defaultValue(bitType),
				Functions.invoke(StandardFunctions.class, functionName, defaultValue(intType)));
		assertEquals(wrapValue(Integer.valueOf(0x17), bitType),
				Functions.invoke(StandardFunctions.class, functionName, wrapValue(Integer.valueOf(17), intType)));
		assertEquals(wrapValue(Integer.valueOf(0x42), bitType),
				Functions.invoke(StandardFunctions.class, functionName, wrapValue(Integer.valueOf(42), intType)));
		assertEquals(wrapValue(Integer.valueOf(0x84), bitType),
				Functions.invoke(StandardFunctions.class, functionName, wrapValue(Integer.valueOf(84), intType)));
		assertEquals(castValue(wrapValue(Long.valueOf(0x8442211784422117L), intType), bitType), Functions
				.invoke(StandardFunctions.class, functionName, toDigitsValue(8442211784422117L, bitType, intType)));
	}

	@ParameterizedTest(name = "{index}: {0} and {1}")
	@MethodSource("typeAnyUnsignedAndAnyBitExceptBoolArgumentsCartesianProvider")
	void testTypeToBcdFunctions(final String intTypeName, final String bitTypeName) throws Throwable {
		final DataType intType = IecTypes.ElementaryTypes.getTypeByName(intTypeName);
		final DataType bitType = IecTypes.ElementaryTypes.getTypeByName(bitTypeName);
		final String functionName = intTypeName + "_TO_BCD_" + bitTypeName;

		assertEquals(defaultValue(bitType),
				Functions.invoke(StandardFunctions.class, functionName, defaultValue(intType)));
		assertEquals(wrapValue(Integer.valueOf(0x17), bitType),
				Functions.invoke(StandardFunctions.class, functionName, wrapValue(Integer.valueOf(17), intType)));
		assertEquals(wrapValue(Integer.valueOf(0x42), bitType),
				Functions.invoke(StandardFunctions.class, functionName, wrapValue(Integer.valueOf(42), intType)));
		assertEquals(wrapValue(Integer.valueOf(0x84), bitType),
				Functions.invoke(StandardFunctions.class, functionName, wrapValue(Integer.valueOf(84), intType)));
		assertEquals(castValue(wrapValue(Long.valueOf(0x8442211784422117L), intType), bitType), Functions
				.invoke(StandardFunctions.class, functionName, toDigitsValue(8442211784422117L, bitType, intType)));
	}

	@ParameterizedTest(name = "{index}: {0} and {1}")
	@MethodSource("typeAnyUnsignedAndAnyBitExceptBoolArgumentsCartesianProvider")
	void testBcdToFunctions(final String intTypeName, final String bitTypeName) throws Throwable {
		final DataType intType = IecTypes.ElementaryTypes.getTypeByName(intTypeName);
		final DataType bitType = IecTypes.ElementaryTypes.getTypeByName(bitTypeName);
		final String functionName = "BCD_TO_" + intTypeName;
		assertEquals(defaultValue(intType),
				Functions.invoke(StandardFunctions.class, functionName, defaultValue(bitType)));
		assertEquals(wrapValue(Integer.valueOf(17), intType),
				Functions.invoke(StandardFunctions.class, functionName, wrapValue(Integer.valueOf(0x17), bitType)));
		assertEquals(wrapValue(Integer.valueOf(42), intType),
				Functions.invoke(StandardFunctions.class, functionName, wrapValue(Integer.valueOf(0x42), bitType)));
		assertEquals(wrapValue(Integer.valueOf(84), intType),
				Functions.invoke(StandardFunctions.class, functionName, wrapValue(Integer.valueOf(0x84), bitType)));
		assertEquals(toDigitsValue(8442211784422117L, bitType, intType), Functions.invoke(StandardFunctions.class,
				functionName, castValue(wrapValue(Long.valueOf(0x8442211784422117L), intType), bitType)));

		assertThrows(IllegalArgumentException.class, () -> Functions.invoke(StandardFunctions.class, functionName, // NOSONAR
				wrapValue(Integer.valueOf(0x1A), bitType)));
	}

	@ParameterizedTest(name = "{index}: {0} and {1}")
	@MethodSource("typeAnyUnsignedAndAnyBitExceptBoolArgumentsCartesianProvider")
	void testTypeBcdToFunctions(final String intTypeName, final String bitTypeName) throws Throwable {
		final DataType intType = IecTypes.ElementaryTypes.getTypeByName(intTypeName);
		final DataType bitType = IecTypes.ElementaryTypes.getTypeByName(bitTypeName);
		final String functionName = bitTypeName + "_BCD_TO_" + intTypeName;
		assertEquals(defaultValue(intType),
				Functions.invoke(StandardFunctions.class, functionName, defaultValue(bitType)));
		assertEquals(wrapValue(Integer.valueOf(17), intType),
				Functions.invoke(StandardFunctions.class, functionName, wrapValue(Integer.valueOf(0x17), bitType)));
		assertEquals(wrapValue(Integer.valueOf(42), intType),
				Functions.invoke(StandardFunctions.class, functionName, wrapValue(Integer.valueOf(0x42), bitType)));
		assertEquals(wrapValue(Integer.valueOf(84), intType),
				Functions.invoke(StandardFunctions.class, functionName, wrapValue(Integer.valueOf(0x84), bitType)));
		assertEquals(toDigitsValue(8442211784422117L, bitType, intType), Functions.invoke(StandardFunctions.class,
				functionName, castValue(wrapValue(Long.valueOf(0x8442211784422117L), intType), bitType)));

		assertThrows(IllegalArgumentException.class, () -> Functions.invoke(StandardFunctions.class, functionName, // NOSONAR
				wrapValue(Integer.valueOf(0x1A), bitType)));
	}

	static Value toDigitsValue(final long value, final DataType originalType, final DataType resultType) {
		return wrapValue(Long.valueOf(value % Math.min(getDigitsMask(originalType), getDigitsMask(resultType))),
				resultType);
	}

	static long getDigitsMask(final DataType type) {
		return switch (type) {
		case final UsintType usintType -> 100L;
		case final UintType uintType -> 10000L;
		case final UdintType udintType -> 100000000L;
		case final UlintType ulintType -> 10000000000000000L;
		case final ByteType byteType -> 100L;
		case final WordType wordType -> 10000L;
		case final DwordType dwordType -> 100000000L;
		case final LwordType lwordType -> 10000000000000000L;
		default -> 0;
		};
	}

	@ParameterizedTest(name = "{index}: {0}")
	@MethodSource("typeAnyBitExceptBoolArgumentsProvider")
	void testIsValidBcdFunctions(final String bitTypeName) throws Throwable {
		final DataType bitType = IecTypes.ElementaryTypes.getTypeByName(bitTypeName);
		assertEquals(TRUE,
				Functions.invoke(StandardFunctions.class, "IS_VALID_BCD", wrapValue(Integer.valueOf(0x17), bitType)));
		assertEquals(TRUE,
				Functions.invoke(StandardFunctions.class, "IS_VALID_BCD", wrapValue(Integer.valueOf(0x42), bitType)));
		assertEquals(TRUE,
				Functions.invoke(StandardFunctions.class, "IS_VALID_BCD", wrapValue(Integer.valueOf(0x84), bitType)));
		assertEquals(FALSE,
				Functions.invoke(StandardFunctions.class, "IS_VALID_BCD", wrapValue(Integer.valueOf(0x1A), bitType)));
	}

	static Stream<String> typeArgumentsProvider() {
		return DataTypeLibrary.getNonUserDefinedDataTypes().stream().map(DataType::getName);
	}

	static Stream<String> typeAnyIntArgumentsProvider() {
		return DataTypeLibrary.getNonUserDefinedDataTypes().stream().filter(AnyIntType.class::isInstance)
				.map(DataType::getName);
	}

	static Stream<String> typeAnyUnsignedArgumentsProvider() {
		return DataTypeLibrary.getNonUserDefinedDataTypes().stream().filter(AnyUnsignedType.class::isInstance)
				.map(DataType::getName);
	}

	static Stream<String> typeAnyRealArgumentsProvider() {
		return DataTypeLibrary.getNonUserDefinedDataTypes().stream().filter(AnyRealType.class::isInstance)
				.map(DataType::getName);
	}

	static Stream<String> typeAnyBitExceptBoolArgumentsProvider() {
		return DataTypeLibrary.getNonUserDefinedDataTypes().stream()
				.filter(Predicates.and(AnyBitType.class::isInstance, Predicates.not(BoolType.class::isInstance)))
				.map(DataType::getName);
	}

	static Stream<Arguments> typeArgumentsCartesianProvider() {
		return typeArgumentsProvider()
				.flatMap(first -> typeArgumentsProvider().map(second -> Arguments.arguments(first, second)));
	}

	static Stream<Arguments> typeAnyUnsignedAndAnyBitExceptBoolArgumentsCartesianProvider() {
		return typeAnyUnsignedArgumentsProvider().flatMap(
				first -> typeAnyBitExceptBoolArgumentsProvider().map(second -> Arguments.arguments(first, second)));
	}

	static Stream<Arguments> typeAnyRealAndAnyIntArgumentsCartesianProvider() {
		return typeAnyRealArgumentsProvider()
				.flatMap(first -> typeAnyIntArgumentsProvider().map(second -> Arguments.arguments(first, second)));
	}

	@SuppressWarnings("unchecked")
	<T extends Value> T invokeUnaryOperator(final String name, final T argument) throws Throwable {
		return (T) Functions.invoke(StandardFunctions.class, name, argument);
	}

	@SuppressWarnings("unchecked")
	<T extends Value> T invokeBinaryOperator(final String name, final T argument1, final T argument2) throws Throwable {
		return (T) Functions.invoke(StandardFunctions.class, name, argument1, argument2);
	}

	@SuppressWarnings("unchecked")
	<T extends Value> T invokeOperator(final String name, final T... arguments) throws Throwable {
		return (T) Functions.invoke(StandardFunctions.class, name, (Object[]) arguments);
	}

	void assertEqualsWithDelta(final double expected, final RealValue actual) {
		assertEquals(expected, actual.doubleValue(), NUMERIC_DELTA);
	}

	void assertEqualsWithDelta(final double expected, final LRealValue actual) {
		assertEquals(expected, actual.doubleValue(), NUMERIC_DELTA);
	}

	VarDeclaration createVarDeclaration(final String name, final DataType type) {
		final VarDeclaration result = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		result.setName(name);
		result.setType(type);
		return result;
	}
}
