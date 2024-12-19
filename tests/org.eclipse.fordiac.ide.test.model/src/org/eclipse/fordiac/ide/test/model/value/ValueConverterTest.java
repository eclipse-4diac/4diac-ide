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
 *   Martin Melik Merkumians
 *     - initial API and implementation and/or initial documentation
 *   Martin Jobst
 *     - added further tests
 *     - refactored tests and added struct test
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.model.value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Named.named;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.value.ArrayValueConverter;
import org.eclipse.fordiac.ide.model.value.NumericValueConverter;
import org.eclipse.fordiac.ide.model.value.StringValueConverter;
import org.eclipse.fordiac.ide.model.value.StructValueConverter;
import org.eclipse.fordiac.ide.model.value.TypedValueConverter;
import org.eclipse.fordiac.ide.model.value.ValueConverter;
import org.eclipse.fordiac.ide.model.value.WStringValueConverter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method", "nls" })
class ValueConverterTest {

	private static final String NAME_BACKSLASH_FRT = "4diac\\f\\r\\tIDE";
	private static final String NAME_BACKSLASHN = "4diac\\nIDE";
	private static final String NAME_DOLLAR = "4diac$IDE";
	private static final String NAME_TWO_DOLLAR = "'4diac$$IDE'";
	private static final String NAME_BACKSLASH = "4diac\"IDE";
	private static final String NAME_ACUTE = "4diac'IDE";

	static Stream<Arguments> toValueTest() {
		return Stream.of(//
				arguments(NumericValueConverter.INSTANCE, Boolean.TRUE, "true"), //
				arguments(NumericValueConverter.INSTANCE, Boolean.FALSE, "false"), //
				arguments(NumericValueConverter.INSTANCE, Boolean.TRUE, "TRUE"), //
				arguments(NumericValueConverter.INSTANCE, Boolean.FALSE, "FALSE"), //
				arguments(NumericValueConverter.INSTANCE, BigInteger.valueOf(100), "1_00"), //
				arguments(NumericValueConverter.INSTANCE, BigInteger.valueOf(16), "16#10"), //
				arguments(NumericValueConverter.INSTANCE, BigDecimal.valueOf(3.1415), "3.1415"), //
				arguments(NumericValueConverter.INSTANCE, IllegalArgumentException.class, "NoNumber"), //
				arguments(NumericValueConverter.INSTANCE, IllegalArgumentException.class, "1__00"), //
				arguments(NumericValueConverter.INSTANCE, IllegalArgumentException.class, "a1"), //
				arguments(StringValueConverter.INSTANCE, "", "''"), //
				arguments(StringValueConverter.INSTANCE, IllegalArgumentException.class, "\"\""), //
				arguments(StringValueConverter.INSTANCE, IllegalArgumentException.class, ""), //
				arguments(StringValueConverter.INSTANCE, IllegalArgumentException.class, "'"), //
				arguments(StringValueConverter.INSTANCE, IllegalArgumentException.class, "'\""), //
				arguments(StringValueConverter.INSTANCE, IllegalArgumentException.class, "aa"), //
				arguments(StringValueConverter.INSTANCE, "abc", "'abc'"), //
				arguments(StringValueConverter.INSTANCE, NAME_ACUTE, "'4diac$'IDE'"), //
				arguments(StringValueConverter.INSTANCE, NAME_BACKSLASH, "'4diac\"IDE'"), //
				arguments(StringValueConverter.INSTANCE, NAME_DOLLAR, NAME_TWO_DOLLAR), //
				arguments(StringValueConverter.INSTANCE, NAME_BACKSLASHN, "'4diac$NIDE'"), //
				arguments(StringValueConverter.INSTANCE, NAME_BACKSLASH_FRT, "'4diac$P$R$TIDE'"), //
				arguments(StringValueConverter.INSTANCE, "4diac IDE", "'4diac$20IDE'"), //
				arguments(StringValueConverter.INSTANCE, "4diac\u000020IDE", "'4diac$0020IDE'"), //
				arguments(WStringValueConverter.INSTANCE, "", "\"\""), //
				arguments(WStringValueConverter.INSTANCE, IllegalArgumentException.class, "''"), //
				arguments(WStringValueConverter.INSTANCE, IllegalArgumentException.class, ""), //
				arguments(WStringValueConverter.INSTANCE, IllegalArgumentException.class, "\""), //
				arguments(WStringValueConverter.INSTANCE, IllegalArgumentException.class, "\"'"), //
				arguments(WStringValueConverter.INSTANCE, IllegalArgumentException.class, "aa"), //
				arguments(WStringValueConverter.INSTANCE, "abc", "\"abc\""), //
				arguments(StringValueConverter.INSTANCE, NAME_ACUTE, "'4diac$'IDE'"), //
				arguments(StringValueConverter.INSTANCE, NAME_BACKSLASH, "'4diac$\"IDE'"), //
				arguments(StringValueConverter.INSTANCE, NAME_DOLLAR, NAME_TWO_DOLLAR), //
				arguments(WStringValueConverter.INSTANCE, NAME_BACKSLASHN, "\"4diac$NIDE\""), //
				arguments(WStringValueConverter.INSTANCE, NAME_BACKSLASH_FRT, "\"4diac$P$R$TIDE\""), //
				arguments(WStringValueConverter.INSTANCE, "4diac IDE", "\"4diac$0020IDE\""), //
				arguments(WStringValueConverter.INSTANCE, IllegalArgumentException.class, "\"4diac$20IDE\""), //
				arguments(new ArrayValueConverter<>(NumericValueConverter.INSTANCE),
						List.of(BigInteger.valueOf(17), BigInteger.valueOf(4), BigInteger.valueOf(21),
								BigInteger.valueOf(42)),
						"[17,4,21,42]"), //
				arguments(new ArrayValueConverter<>(NumericValueConverter.INSTANCE),
						List.of(Boolean.TRUE, BigInteger.valueOf(4), BigInteger.valueOf(21),
								BigDecimal.valueOf(3.14159)),
						"[true,4,16#15,3.14159]"), //
				arguments(new ArrayValueConverter<>(NumericValueConverter.INSTANCE),
						List.of(BigInteger.valueOf(1), BigInteger.valueOf(2), BigInteger.valueOf(3),
								BigInteger.valueOf(1), BigInteger.valueOf(2), BigInteger.valueOf(3)),
						"[2(1,2,3)]"), //
				arguments(new ArrayValueConverter<>(StringValueConverter.INSTANCE), List.of("abc", "ab,xy", "ab,',xy"),
						"['abc','ab,xy','ab,$',xy']"), //
				arguments(new ArrayValueConverter<>(new TypedValueConverter(ElementaryTypes.TIME)),
						List.of(Duration.ZERO, Duration.ofDays(17).plusMinutes(4).plusSeconds(21)),
						"[T#0ns, T#17d4m21s]"), //
				arguments(new ArrayValueConverter<>(NumericValueConverter.INSTANCE), IllegalArgumentException.class,
						""), //
				arguments(new ArrayValueConverter<>(NumericValueConverter.INSTANCE), IllegalArgumentException.class,
						""), //
				arguments(new ArrayValueConverter<>(NumericValueConverter.INSTANCE), IllegalArgumentException.class,
						"NoArray"), //
				arguments(new ArrayValueConverter<>(NumericValueConverter.INSTANCE), IllegalArgumentException.class,
						"[17,,]"), //
				arguments(new ArrayValueConverter<>(NumericValueConverter.INSTANCE), IllegalArgumentException.class,
						"[4"), //
				arguments(
						named("StructValueConverter [NumericValueConverter]",
								new StructValueConverter(unused -> NumericValueConverter.INSTANCE)),
						named("{a=17, b=4}", Map.of("a", BigInteger.valueOf(17), "b", BigInteger.valueOf(4))),
						"(a:=17,b:=4)"), //
				arguments(
						named("StructValueConverter [StringValueConverter]",
								new StructValueConverter(unused -> StringValueConverter.INSTANCE)),
						named("{a=ab,xy, b=ab,',xy}", Map.of("a", "ab,xy", "b", "ab,',xy")),
						"(a:='ab,xy',b:='ab,$',xy')"), //
				arguments(
						named("StructValueConverter [TimeValueConverter]",
								new StructValueConverter(unused -> new TypedValueConverter(ElementaryTypes.TIME))),
						named("{a=T#0ns, b=T#17d4m21.42s}",
								Map.of("a", Duration.ZERO, "b", Duration.ofDays(17).plusMinutes(4).plusSeconds(21))),
						"(a:=T#0ns, b:=T#17d4m21s)"), //
				arguments(
						named("StructValueConverter [ArrayValueConverter [StringValueConverter]]",
								new StructValueConverter(
										unused -> new ArrayValueConverter<>(StringValueConverter.INSTANCE))),
						named("{a=[abc, ab,xy, ab,',xy], b=[test, value]}",
								Map.of("a", List.of("abc", "ab,xy", "ab,',xy"), "b", List.of("test", "value"))),
						"(a:=['abc','ab,xy','ab,$',xy'],b:=['test','value'])"), //
				arguments(
						named("StructValueConverter [NumericValueConverter]",
								new StructValueConverter(unused -> NumericValueConverter.INSTANCE)),
						IllegalArgumentException.class, "(a:=invalid,b:=4)") //
		);
	}

	@ParameterizedTest
	@MethodSource
	void toValueTest(final ValueConverter<?> converter, final Object expected, final String string) {
		if (expected instanceof final Class<?> expectedClass && Throwable.class.isAssignableFrom(expectedClass)) {
			assertThrowsExactly(expectedClass.asSubclass(Throwable.class), () -> converter.toValue(string));
			assertThrowsExactly(expectedClass.asSubclass(Throwable.class),
					() -> converter.toValue(new Scanner(string)));
		} else if (expected instanceof final Iterable<?> expectedIterable) {
			assertIterableEquals(expectedIterable, (Iterable<?>) converter.toValue(string));
			assertIterableEquals(expectedIterable, (Iterable<?>) converter.toValue(new Scanner(string)));
		} else if (expected instanceof final String expectedString) {
			assertEquals(expectedString.translateEscapes(), converter.toValue(string));
			assertEquals(expectedString.translateEscapes(), converter.toValue(new Scanner(string)));
		} else {
			assertEquals(expected, converter.toValue(string));
			assertEquals(expected, converter.toValue(new Scanner(string)));
		}
	}

	static Stream<Arguments> toStringTest() {
		return Stream.of(//
				arguments(NumericValueConverter.INSTANCE, "TRUE", Boolean.TRUE), //
				arguments(NumericValueConverter.INSTANCE, "FALSE", Boolean.FALSE), //
				arguments(NumericValueConverter.INSTANCE, "100", BigInteger.valueOf(100)), //
				arguments(NumericValueConverter.INSTANCE, "3.1415", BigDecimal.valueOf(3.1415)), //
				arguments(NumericValueConverter.INSTANCE_BYTE, "16#04", BigInteger.valueOf(4)), //
				arguments(NumericValueConverter.INSTANCE_WORD, "16#0011", BigInteger.valueOf(17)), //
				arguments(NumericValueConverter.INSTANCE_DWORD, "16#00000015", BigInteger.valueOf(21)), //
				arguments(NumericValueConverter.INSTANCE_LWORD, "16#000000000000002A", BigInteger.valueOf(42)), //
				arguments(StringValueConverter.INSTANCE, "''", ""), //
				arguments(StringValueConverter.INSTANCE, "'abc'", "abc"), //
				arguments(StringValueConverter.INSTANCE, "'4diac$'IDE'", NAME_ACUTE), //
				arguments(StringValueConverter.INSTANCE, "'4diac\"IDE'", NAME_BACKSLASH), //
				arguments(StringValueConverter.INSTANCE, NAME_TWO_DOLLAR, NAME_DOLLAR), //
				arguments(StringValueConverter.INSTANCE, "'4diac$NIDE'", NAME_BACKSLASHN), //
				arguments(StringValueConverter.INSTANCE, "'4diac$P$R$TIDE'", NAME_BACKSLASH_FRT), //
				arguments(StringValueConverter.INSTANCE, "'4diac$00IDE'", "4diac\u0000IDE"), //
				arguments(WStringValueConverter.INSTANCE, "\"\"", ""), //
				arguments(WStringValueConverter.INSTANCE, "\"abc\"", "abc"), //
				arguments(WStringValueConverter.INSTANCE, "\"4diac'IDE\"", NAME_ACUTE), //
				arguments(WStringValueConverter.INSTANCE, "\"4diac$\"IDE\"", NAME_BACKSLASH), //
				arguments(WStringValueConverter.INSTANCE, "\"4diac$$IDE\"", NAME_DOLLAR), //
				arguments(WStringValueConverter.INSTANCE, "\"4diac$NIDE\"", NAME_BACKSLASHN), //
				arguments(WStringValueConverter.INSTANCE, "\"4diac$P$R$TIDE\"", NAME_BACKSLASH_FRT), //
				arguments(WStringValueConverter.INSTANCE, "\"4diac$0000IDE\"", "4diac\u0000IDE"), //
				arguments(new ArrayValueConverter<>(NumericValueConverter.INSTANCE), "[17, 4, 21, 42]",
						List.of(BigInteger.valueOf(17), BigInteger.valueOf(4), BigInteger.valueOf(21),
								BigInteger.valueOf(42))), //
				arguments(new ArrayValueConverter<>(NumericValueConverter.INSTANCE), "[TRUE, 4, 21, 3.14159]",
						List.of(Boolean.TRUE, BigInteger.valueOf(4), BigInteger.valueOf(21),
								BigDecimal.valueOf(3.14159))), //
				arguments(new ArrayValueConverter<>(new TypedValueConverter(ElementaryTypes.TIME)),
						"[T#0s, T#17d4m21s]",
						List.of(Duration.ZERO, Duration.ofDays(17).plusMinutes(4).plusSeconds(21))), //
				arguments(
						named("StructValueConverter [NumericValueConverter]",
								new StructValueConverter(unused -> NumericValueConverter.INSTANCE)),
						"(a := 17, b := 4)",
						named("{a=17, b=4}", Map.of("a", BigInteger.valueOf(17), "b", BigInteger.valueOf(4)))), //
				arguments(
						named("StructValueConverter [StringValueConverter]",
								new StructValueConverter(unused -> StringValueConverter.INSTANCE)),
						"(a := 'ab,xy', b := 'ab,$',xy')",
						named("{a=ab,xy, b=ab,',xy}", Map.of("a", "ab,xy", "b", "ab,',xy"))), //
				arguments(
						named("StructValueConverter [TimeValueConverter]",
								new StructValueConverter(unused -> new TypedValueConverter(ElementaryTypes.TIME))),
						"(a := T#0s, b := T#17d4m21s)",
						named("{a=T#0ns, b=T#17d4m21s}",
								Map.of("a", Duration.ZERO, "b", Duration.ofDays(17).plusMinutes(4).plusSeconds(21)))), //
				arguments(
						named("StructValueConverter [ArrayValueConverter [StringValueConverter]]",
								new StructValueConverter(
										unused -> new ArrayValueConverter<>(StringValueConverter.INSTANCE))),
						"(a := ['abc', 'ab,xy', 'ab,$',xy'], b := ['test', 'value'])",
						named("{a=[abc, ab,xy, ab,',xy], b=[test, value]}",
								Map.of("a", List.of("abc", "ab,xy", "ab,',xy"), "b", List.of("test", "value"))))//
		);
	}

	@ParameterizedTest
	@MethodSource
	@SuppressWarnings("unchecked")
	<T> void toStringTest(final ValueConverter<? super T> converter, final String expected, final T value) {
		if (value instanceof final String stringValue) {
			assertEquals(expected, converter.toString((T) stringValue.translateEscapes()));
		} else {
			assertEquals(expected, converter.toString(value));
		}
	}
}
