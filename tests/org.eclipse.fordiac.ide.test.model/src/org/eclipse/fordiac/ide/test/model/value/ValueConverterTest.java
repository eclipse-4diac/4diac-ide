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
 *   Martin Melik Merkumians
 *     - initial API and implementation and/or initial documentation
 *   Martin Jobst
 *     - added further tests
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.model.value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.MessageFormat;

import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.value.NumericValueConverter;
import org.eclipse.fordiac.ide.model.value.StringValueConverter;
import org.eclipse.fordiac.ide.model.value.WStringValueConverter;
import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
class ValueConverterTest {

	private static final String NAME_BACKSLASH_FRT = "4diac\f\r\tIDE"; //$NON-NLS-1$
	private static final String NAME_BACKSLASHN = "4diac\nIDE"; //$NON-NLS-1$
	private static final String NAME_DOLLAR = "4diac$IDE"; //$NON-NLS-1$
	private static final String NAME_TWO_DOLLAR = "'4diac$$IDE'"; //$NON-NLS-1$
	private static final String NAME_BACKSLASH = "4diac\"IDE"; //$NON-NLS-1$
	private static final String NAME_ACUTE = "4diac'IDE"; //$NON-NLS-1$

	@Test
	void toValueNumericTest() {
		assertEquals(Boolean.TRUE, NumericValueConverter.INSTANCE.toValue("true")); //$NON-NLS-1$
		assertEquals(Boolean.FALSE, NumericValueConverter.INSTANCE.toValue("false")); //$NON-NLS-1$
		assertEquals(Boolean.TRUE, NumericValueConverter.INSTANCE.toValue("TRUE")); //$NON-NLS-1$
		assertEquals(Boolean.FALSE, NumericValueConverter.INSTANCE.toValue("FALSE")); //$NON-NLS-1$
		assertEquals(BigInteger.valueOf(100), NumericValueConverter.INSTANCE.toValue("1_00")); //$NON-NLS-1$
		assertEquals(BigInteger.valueOf(16), NumericValueConverter.INSTANCE.toValue("16#10")); //$NON-NLS-1$
		assertEquals(BigDecimal.valueOf(3.1415), NumericValueConverter.INSTANCE.toValue("3.1415")); //$NON-NLS-1$
		assertThrowsExactly(IllegalArgumentException.class, () -> NumericValueConverter.INSTANCE.toValue("NoNumber"), //$NON-NLS-1$
				MessageFormat.format(Messages.VALIDATOR_INVALID_NUMBER_LITERAL, "NoNumber")); //$NON-NLS-1$
		assertThrowsExactly(IllegalArgumentException.class, () -> NumericValueConverter.INSTANCE.toValue("1__00"), //$NON-NLS-1$
				Messages.VALIDATOR_CONSECUTIVE_UNDERSCORES_ERROR_MESSAGE);
	}

	@Test
	void toStringNumericTest() {
		assertEquals("TRUE", NumericValueConverter.INSTANCE.toString(Boolean.TRUE)); //$NON-NLS-1$
		assertEquals("FALSE", NumericValueConverter.INSTANCE.toString(Boolean.FALSE)); //$NON-NLS-1$
		assertEquals("100", NumericValueConverter.INSTANCE.toString(BigInteger.valueOf(100))); //$NON-NLS-1$
		assertEquals("3.1415", NumericValueConverter.INSTANCE.toString(BigDecimal.valueOf(3.1415))); //$NON-NLS-1$
		assertEquals("16#04", NumericValueConverter.INSTANCE_BYTE.toString(BigInteger.valueOf(4))); //$NON-NLS-1$
		assertEquals("16#0011", NumericValueConverter.INSTANCE_WORD.toString(BigInteger.valueOf(17))); //$NON-NLS-1$
		assertEquals("16#00000015", NumericValueConverter.INSTANCE_DWORD.toString(BigInteger.valueOf(21))); //$NON-NLS-1$
		assertEquals("16#000000000000002A", NumericValueConverter.INSTANCE_LWORD.toString(BigInteger.valueOf(42))); //$NON-NLS-1$
	}

	@Test
	void toValueStringTest() {
		assertEquals("", StringValueConverter.INSTANCE.toValue("''")); //$NON-NLS-1$ //$NON-NLS-2$
		assertThrowsExactly(IllegalArgumentException.class, () -> StringValueConverter.INSTANCE.toValue("\"\""), //$NON-NLS-1$
				Messages.VALIDATOR_IllegalStringLiteral);
		assertThrowsExactly(IllegalArgumentException.class, () -> StringValueConverter.INSTANCE.toValue(""), //$NON-NLS-1$
				Messages.VALIDATOR_IllegalStringLiteral);
		assertThrowsExactly(IllegalArgumentException.class, () -> StringValueConverter.INSTANCE.toValue("'"), //$NON-NLS-1$
				Messages.VALIDATOR_IllegalStringLiteral);
		assertThrowsExactly(IllegalArgumentException.class, () -> StringValueConverter.INSTANCE.toValue("'\""), //$NON-NLS-1$
				Messages.VALIDATOR_UnevenlyQuotedStringLiteral);
		assertThrowsExactly(IllegalArgumentException.class, () -> StringValueConverter.INSTANCE.toValue("aa"), //$NON-NLS-1$
				Messages.VALIDATOR_UnevenlyQuotedStringLiteral);
		assertEquals("abc", StringValueConverter.INSTANCE.toValue("'abc'")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(NAME_ACUTE, StringValueConverter.INSTANCE.toValue("'4diac$'IDE'")); //$NON-NLS-1$
		assertEquals(NAME_BACKSLASH, StringValueConverter.INSTANCE.toValue("'4diac\"IDE'")); //$NON-NLS-1$
		assertEquals(NAME_DOLLAR, StringValueConverter.INSTANCE.toValue(NAME_TWO_DOLLAR));
		assertEquals(NAME_BACKSLASHN, StringValueConverter.INSTANCE.toValue("'4diac$NIDE'")); //$NON-NLS-1$
		assertEquals(NAME_BACKSLASH_FRT, StringValueConverter.INSTANCE.toValue("'4diac$P$R$TIDE'")); //$NON-NLS-1$
		assertEquals("4diac IDE", StringValueConverter.INSTANCE.toValue("'4diac$20IDE'")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("4diac\u000020IDE", StringValueConverter.INSTANCE.toValue("'4diac$0020IDE'")); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	void toValueWStringTest() {
		assertEquals("", WStringValueConverter.INSTANCE.toValue("\"\"")); //$NON-NLS-1$ //$NON-NLS-2$
		assertThrowsExactly(IllegalArgumentException.class, () -> WStringValueConverter.INSTANCE.toValue("''"), //$NON-NLS-1$
				Messages.VALIDATOR_IllegalStringLiteral);
		assertThrowsExactly(IllegalArgumentException.class, () -> WStringValueConverter.INSTANCE.toValue(""), //$NON-NLS-1$
				Messages.VALIDATOR_IllegalStringLiteral);
		assertThrowsExactly(IllegalArgumentException.class, () -> WStringValueConverter.INSTANCE.toValue("\""), //$NON-NLS-1$
				Messages.VALIDATOR_IllegalStringLiteral);
		assertThrowsExactly(IllegalArgumentException.class, () -> WStringValueConverter.INSTANCE.toValue("\"'"), //$NON-NLS-1$
				Messages.VALIDATOR_UnevenlyQuotedStringLiteral);
		assertThrowsExactly(IllegalArgumentException.class, () -> WStringValueConverter.INSTANCE.toValue("aa"), //$NON-NLS-1$
				Messages.VALIDATOR_IllegalStringLiteral);
		assertEquals("abc", WStringValueConverter.INSTANCE.toValue("\"abc\"")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(NAME_ACUTE, StringValueConverter.INSTANCE.toValue("'4diac'IDE'")); //$NON-NLS-1$
		assertEquals(NAME_BACKSLASH, StringValueConverter.INSTANCE.toValue("'4diac$\"IDE'")); //$NON-NLS-1$
		assertEquals(NAME_DOLLAR, StringValueConverter.INSTANCE.toValue(NAME_TWO_DOLLAR));
		assertEquals(NAME_BACKSLASHN, WStringValueConverter.INSTANCE.toValue("\"4diac$NIDE\"")); //$NON-NLS-1$
		assertEquals(NAME_BACKSLASH_FRT, WStringValueConverter.INSTANCE.toValue("\"4diac$P$R$TIDE\"")); //$NON-NLS-1$
		assertEquals("4diac IDE", WStringValueConverter.INSTANCE.toValue("\"4diac$0020IDE\"")); //$NON-NLS-1$ //$NON-NLS-2$
		assertThrowsExactly(IllegalArgumentException.class,
				() -> WStringValueConverter.INSTANCE.toValue("\"4diac$20IDE\""), //$NON-NLS-1$
				Messages.VALIDATOR_IllegalStringLiteral);
	}

	@Test
	void toStringStringTest() {
		assertEquals("''", StringValueConverter.INSTANCE.toString("")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("'abc'", StringValueConverter.INSTANCE.toString("abc")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("'4diac$'IDE'", StringValueConverter.INSTANCE.toString(NAME_ACUTE)); //$NON-NLS-1$
		assertEquals("'4diac\"IDE'", StringValueConverter.INSTANCE.toString(NAME_BACKSLASH)); //$NON-NLS-1$
		assertEquals(NAME_TWO_DOLLAR, StringValueConverter.INSTANCE.toString(NAME_DOLLAR));
		assertEquals("'4diac$NIDE'", StringValueConverter.INSTANCE.toString(NAME_BACKSLASHN)); //$NON-NLS-1$
		assertEquals("'4diac$P$R$TIDE'", StringValueConverter.INSTANCE.toString(NAME_BACKSLASH_FRT)); //$NON-NLS-1$
		assertEquals("'4diac$00IDE'", StringValueConverter.INSTANCE.toString("4diac\u0000IDE")); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	void toStringWStringTest() {
		assertEquals("\"\"", WStringValueConverter.INSTANCE.toString("")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("\"abc\"", WStringValueConverter.INSTANCE.toString("abc")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("\"4diac'IDE\"", WStringValueConverter.INSTANCE.toString(NAME_ACUTE)); //$NON-NLS-1$
		assertEquals("\"4diac$\"IDE\"", WStringValueConverter.INSTANCE.toString(NAME_BACKSLASH)); //$NON-NLS-1$
		assertEquals("\"4diac$$IDE\"", WStringValueConverter.INSTANCE.toString(NAME_DOLLAR)); //$NON-NLS-1$
		assertEquals("\"4diac$NIDE\"", WStringValueConverter.INSTANCE.toString(NAME_BACKSLASHN)); //$NON-NLS-1$
		assertEquals("\"4diac$P$R$TIDE\"", WStringValueConverter.INSTANCE.toString(NAME_BACKSLASH_FRT)); //$NON-NLS-1$
		assertEquals("\"4diac$0000IDE\"", WStringValueConverter.INSTANCE.toString("4diac\u0000IDE")); //$NON-NLS-1$ //$NON-NLS-2$
	}
}
