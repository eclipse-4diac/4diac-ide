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
package org.eclipse.fordiac.ide.test.model.value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes;
import org.eclipse.fordiac.ide.model.value.TypedValueConverter;
import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
class TypedValueConverterTest {

	@Test
	void toValueTest() {
		assertEquals(Boolean.TRUE, new TypedValueConverter(ElementaryTypes.BOOL).toValue("TRUE")); //$NON-NLS-1$
		assertEquals(Boolean.FALSE, new TypedValueConverter(ElementaryTypes.BOOL).toValue("FALSE")); //$NON-NLS-1$
		assertEquals(BigInteger.valueOf(17), new TypedValueConverter(ElementaryTypes.INT).toValue("17")); //$NON-NLS-1$
		assertEquals(BigDecimal.valueOf(3.1415), new TypedValueConverter(ElementaryTypes.LREAL).toValue("3.1415")); //$NON-NLS-1$
		assertEquals("4diac IDE", new TypedValueConverter(ElementaryTypes.STRING).toValue("'4diac IDE'")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("4diac IDE", new TypedValueConverter(ElementaryTypes.WSTRING).toValue("\"4diac IDE\"")); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	void toValueOptionalPrefix() {
		assertEquals(Boolean.TRUE, new TypedValueConverter(ElementaryTypes.BOOL).toValue("BOOL#TRUE")); //$NON-NLS-1$
		assertEquals(Boolean.FALSE, new TypedValueConverter(ElementaryTypes.BOOL).toValue("BOOL#FALSE")); //$NON-NLS-1$
		assertEquals(BigInteger.valueOf(17), new TypedValueConverter(ElementaryTypes.INT).toValue("INT#17")); //$NON-NLS-1$
		assertEquals(BigDecimal.valueOf(3.1415),
				new TypedValueConverter(ElementaryTypes.LREAL).toValue("LREAL#3.1415")); //$NON-NLS-1$
		assertEquals("4diac IDE", new TypedValueConverter(ElementaryTypes.STRING).toValue("STRING#'4diac IDE'")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("4diac IDE", new TypedValueConverter(ElementaryTypes.WSTRING).toValue("WSTRING#\"4diac IDE\"")); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	void toValueOptionalInvalidPrefix() {
		assertThrowsExactly(IllegalArgumentException.class,
				() -> new TypedValueConverter(ElementaryTypes.BOOL).toValue("INT#17"), //$NON-NLS-1$
				Messages.VALIDATOR_LITERAL_TYPE_INCOMPATIBLE_WITH_INPUT_TYPE);
	}

	@Test
	void toValueRequiredShortPrefix() {
		assertEquals(Duration.ofSeconds(17), new TypedValueConverter(ElementaryTypes.TIME).toValue("T#17s")); //$NON-NLS-1$
		assertEquals(Duration.ofSeconds(17), new TypedValueConverter(ElementaryTypes.LTIME).toValue("LT#17s")); //$NON-NLS-1$
		assertEquals(LocalDate.of(2017, 04, 21), new TypedValueConverter(ElementaryTypes.DATE).toValue("D#2017-04-21")); //$NON-NLS-1$
		assertEquals(LocalDate.of(2017, 04, 21),
				new TypedValueConverter(ElementaryTypes.LDATE).toValue("LD#2017-04-21")); //$NON-NLS-1$
		assertEquals(LocalTime.of(21, 04, 17),
				new TypedValueConverter(ElementaryTypes.TIME_OF_DAY).toValue("TOD#21:04:17")); //$NON-NLS-1$
		assertEquals(LocalTime.of(21, 04, 17),
				new TypedValueConverter(ElementaryTypes.LTIME_OF_DAY).toValue("LTOD#21:04:17")); //$NON-NLS-1$
		assertEquals(LocalDateTime.of(2017, 04, 21, 21, 04, 17),
				new TypedValueConverter(ElementaryTypes.DATE_AND_TIME).toValue("DT#2017-04-21-21:04:17")); //$NON-NLS-1$
		assertEquals(LocalDateTime.of(2017, 04, 21, 21, 04, 17),
				new TypedValueConverter(ElementaryTypes.LDATE_AND_TIME).toValue("LDT#2017-04-21-21:04:17")); //$NON-NLS-1$
	}

	@Test
	void toValueRequiredLongPrefix() {
		assertEquals(Duration.ofSeconds(17), new TypedValueConverter(ElementaryTypes.TIME).toValue("TIME#17s")); //$NON-NLS-1$
		assertEquals(Duration.ofSeconds(17), new TypedValueConverter(ElementaryTypes.LTIME).toValue("LTIME#17s")); //$NON-NLS-1$
		assertEquals(LocalDate.of(2017, 04, 21),
				new TypedValueConverter(ElementaryTypes.DATE).toValue("DATE#2017-04-21")); //$NON-NLS-1$
		assertEquals(LocalDate.of(2017, 04, 21),
				new TypedValueConverter(ElementaryTypes.LDATE).toValue("LDATE#2017-04-21")); //$NON-NLS-1$
		assertEquals(LocalTime.of(21, 04, 17),
				new TypedValueConverter(ElementaryTypes.TIME_OF_DAY).toValue("TIME_OF_DAY#21:04:17")); //$NON-NLS-1$
		assertEquals(LocalTime.of(21, 04, 17),
				new TypedValueConverter(ElementaryTypes.LTIME_OF_DAY).toValue("LTIME_OF_DAY#21:04:17")); //$NON-NLS-1$
		assertEquals(LocalDateTime.of(2017, 04, 21, 21, 04, 17),
				new TypedValueConverter(ElementaryTypes.DATE_AND_TIME).toValue("DATE_AND_TIME#2017-04-21-21:04:17")); //$NON-NLS-1$
		assertEquals(LocalDateTime.of(2017, 04, 21, 21, 04, 17),
				new TypedValueConverter(ElementaryTypes.LDATE_AND_TIME).toValue("LDATE_AND_TIME#2017-04-21-21:04:17")); //$NON-NLS-1$
	}

	@Test
	void toValueRequiredInvalidPrefix() {
		assertThrowsExactly(IllegalArgumentException.class,
				() -> new TypedValueConverter(ElementaryTypes.TIME).toValue("LTIME#17s"), //$NON-NLS-1$
				Messages.VALIDATOR_LITERAL_TYPE_INCOMPATIBLE_WITH_INPUT_TYPE);
	}

	@Test
	void toValueRequiredMissingPrefix() {
		assertThrowsExactly(IllegalArgumentException.class,
				() -> new TypedValueConverter(ElementaryTypes.TIME).toValue("17s"), //$NON-NLS-1$
				Messages.VALIDATOR_DatatypeRequiresTypeSpecifier);
	}

	@Test
	void toValueRequiredAnyPrefix() {
		assertEquals(BigInteger.valueOf(17), new TypedValueConverter(GenericTypes.ANY_INT).toValue("INT#17")); //$NON-NLS-1$
		assertEquals(Duration.ofSeconds(17), new TypedValueConverter(GenericTypes.ANY_DURATION).toValue("TIME#17s")); //$NON-NLS-1$
		assertEquals("abc", new TypedValueConverter(GenericTypes.ANY_STRING).toValue("STRING#'abc'")); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	void toValueRequiredAnyInvalidPrefix() {
		assertThrowsExactly(IllegalArgumentException.class,
				() -> new TypedValueConverter(GenericTypes.ANY_INT).toValue("REAL#3.1415"), //$NON-NLS-1$
				Messages.VALIDATOR_LITERAL_TYPE_INCOMPATIBLE_WITH_INPUT_TYPE);
	}

	@Test
	void toValueRequiredAnyMissingPrefix() {
		assertThrowsExactly(IllegalArgumentException.class,
				() -> new TypedValueConverter(GenericTypes.ANY_INT).toValue("17"), //$NON-NLS-1$
				Messages.VALIDATOR_DatatypeRequiresTypeSpecifier);
	}
}
