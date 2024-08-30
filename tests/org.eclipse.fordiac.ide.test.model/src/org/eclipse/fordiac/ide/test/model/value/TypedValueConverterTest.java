/*******************************************************************************
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.model.value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.value.TypedValueConverter;
import org.eclipse.fordiac.ide.test.model.typelibrary.DataTypeEntryMock;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method", "nls" })
class TypedValueConverterTest {

	private static final String TEST_INT = "INT#17";
	private static final String NAME = "4diac IDE";

	private static TypeLibrary typeLibrary;

	@BeforeAll
	static void setupTypeLibrary() {
		typeLibrary = TypeLibraryManager.INSTANCE.getTypeLibrary(null);
	}

	static Stream<Arguments> toValueTest() {
		return Stream.of(//
				arguments(ElementaryTypes.BOOL, Boolean.TRUE, "TRUE"), //
				arguments(ElementaryTypes.BOOL, Boolean.FALSE, "FALSE"), //
				arguments(ElementaryTypes.INT, BigInteger.valueOf(17), "17"), //
				arguments(ElementaryTypes.INT, BigInteger.valueOf(17), "16#11"), //
				arguments(ElementaryTypes.LREAL, BigDecimal.valueOf(3.1415), "3.1415"), //
				arguments(ElementaryTypes.STRING, NAME, "'4diac IDE'"), //
				arguments(ElementaryTypes.WSTRING, NAME, "\"4diac IDE\""), //
				arguments(ElementaryTypes.BOOL, Boolean.TRUE, "BOOL#TRUE"), //
				arguments(ElementaryTypes.BOOL, Boolean.FALSE, "BOOL#FALSE"), //
				arguments(ElementaryTypes.INT, BigInteger.valueOf(17), TEST_INT), //
				arguments(ElementaryTypes.INT, BigInteger.valueOf(17), "INT#16#11"), //
				arguments(ElementaryTypes.LREAL, BigDecimal.valueOf(3.1415), "LREAL#3.1415"), //
				arguments(ElementaryTypes.STRING, NAME, "STRING#'4diac IDE'"), //
				arguments(ElementaryTypes.WSTRING, NAME, "WSTRING#\"4diac IDE\""), //
				arguments(ElementaryTypes.BOOL, IllegalArgumentException.class, TEST_INT), //
				arguments(ElementaryTypes.TIME, Duration.ofSeconds(17), "T#17s"), //
				arguments(ElementaryTypes.LTIME, Duration.ofSeconds(17), "LT#17s"), //
				arguments(ElementaryTypes.DATE, LocalDate.of(2017, 04, 21), "D#2017-04-21"), //
				arguments(ElementaryTypes.LDATE, LocalDate.of(2017, 04, 21), "LD#2017-04-21"), //
				arguments(ElementaryTypes.TIME_OF_DAY, LocalTime.of(21, 04, 17), "TOD#21:04:17"), //
				arguments(ElementaryTypes.LTIME_OF_DAY, LocalTime.of(21, 04, 17), "LTOD#21:04:17"), //
				arguments(ElementaryTypes.DATE_AND_TIME, LocalDateTime.of(2017, 04, 21, 21, 04, 17),
						"DT#2017-04-21-21:04:17"), //
				arguments(ElementaryTypes.LDATE_AND_TIME, LocalDateTime.of(2017, 04, 21, 21, 04, 17),
						"LDT#2017-04-21-21:04:17"), //
				arguments(ElementaryTypes.TIME, Duration.ofSeconds(17), "TIME#17s"), //
				arguments(ElementaryTypes.LTIME, Duration.ofSeconds(17), "LTIME#17s"), //
				arguments(ElementaryTypes.DATE, LocalDate.of(2017, 04, 21), "DATE#2017-04-21"), //
				arguments(ElementaryTypes.LDATE, LocalDate.of(2017, 04, 21), "LDATE#2017-04-21"), //
				arguments(ElementaryTypes.TIME_OF_DAY, LocalTime.of(21, 04, 17), "TIME_OF_DAY#21:04:17"), //
				arguments(ElementaryTypes.LTIME_OF_DAY, LocalTime.of(21, 04, 17), "LTIME_OF_DAY#21:04:17"), //
				arguments(ElementaryTypes.DATE_AND_TIME, LocalDateTime.of(2017, 04, 21, 21, 04, 17),
						"DATE_AND_TIME#2017-04-21-21:04:17"), //
				arguments(ElementaryTypes.LDATE_AND_TIME, LocalDateTime.of(2017, 04, 21, 21, 04, 17),
						"LDATE_AND_TIME#2017-04-21-21:04:17"), //
				arguments(ElementaryTypes.TIME, IllegalArgumentException.class, "LTIME#17s"), //
				arguments(ElementaryTypes.TIME, IllegalArgumentException.class, "17s"), //
				arguments(GenericTypes.ANY_INT, BigInteger.valueOf(17), TEST_INT), //
				arguments(GenericTypes.ANY_DURATION, Duration.ofSeconds(17), "TIME#17s"), //
				arguments(GenericTypes.ANY_STRING, "abc", "STRING#'abc'"), //
				arguments(GenericTypes.ANY_INT, IllegalArgumentException.class, "REAL#3.1415"), //
				arguments(GenericTypes.ANY_INT, IllegalArgumentException.class, "17") //
		);
	}

	@ParameterizedTest
	@MethodSource
	void toValueTest(final DataType type, final Object expected, final String string) {
		final TypedValueConverter converter = new TypedValueConverter(type);
		if (expected instanceof final Class<?> expectedClass && Throwable.class.isAssignableFrom(expectedClass)) {
			assertThrowsExactly(expectedClass.asSubclass(Throwable.class), () -> converter.toValue(string));
			assertThrowsExactly(expectedClass.asSubclass(Throwable.class),
					() -> converter.toValue(new Scanner(string)));
		} else if (expected instanceof final Iterable<?> expectedIterable) {
			assertIterableEquals(expectedIterable, (Iterable<?>) converter.toValue(string));
			assertIterableEquals(expectedIterable, (Iterable<?>) converter.toValue(new Scanner(string)));
		} else {
			assertEquals(expected, converter.toValue(string));
			assertEquals(expected, converter.toValue(new Scanner(string)));
		}
	}

	@Test
	void toValueStructTest() {
		final StructuredType structType = DataFactory.eINSTANCE.createStructuredType();
		structType.setName("TestStruct");
		structType.getMemberVariables().add(createVarDeclaration("a", ElementaryTypes.DINT));
		structType.getMemberVariables().add(createVarDeclaration("b", ElementaryTypes.STRING));
		typeLibrary.addTypeEntry(new DataTypeEntryMock(structType, typeLibrary, null));
		assertEquals(Map.of("a", BigInteger.valueOf(17), "b", "test"),
				new TypedValueConverter(structType).toValue("(a:=17,b:='test')"));
		assertEquals(Map.of("a", BigInteger.valueOf(17), "b", "test"),
				new TypedValueConverter(GenericTypes.ANY, typeLibrary.getDataTypeLibrary())
						.toValue("TestStruct#(a:=17,b:='test')"));
	}

	public static VarDeclaration createVarDeclaration(final String name, final DataType type) {
		final var result = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		result.setName(name);
		result.setType(type);
		return result;
	}
}
