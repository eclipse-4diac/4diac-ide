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

import java.math.BigInteger;

import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.value.NumericValueConverter;
import org.eclipse.fordiac.ide.model.value.StringValueConverter;
import org.eclipse.fordiac.ide.model.value.WStringValueConverter;
import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
class ValueConverterTest {

	@Test
	void validNumericConverterTest() {
		final BigInteger result = (BigInteger) NumericValueConverter.INSTANCE.toValue("1_00"); //$NON-NLS-1$
		assertEquals(result.intValue(), 100);
	}

	@Test
	void invalidNumericConverterTest() {
		assertThrowsExactly(IllegalArgumentException.class, () -> {
			NumericValueConverter.INSTANCE.toValue("NoNumber"); //$NON-NLS-1$
		}, Messages.VALIDATOR_INVALID_NUMBER_LITERAL);
	}

	@Test
	void twoOrMoreConsecutiveUnderscoresForbiddenTest() {
		assertThrowsExactly(IllegalArgumentException.class, () -> {
			NumericValueConverter.INSTANCE.toValue("1__00"); //$NON-NLS-1$
		}, Messages.VALIDATOR_CONSECUTIVE_UNDERSCORES_ERROR_MESSAGE);
	}

	@Test
	void toValueStringTest() {
		assertEquals("", StringValueConverter.INSTANCE.toValue("''")); //$NON-NLS-1$ //$NON-NLS-2$
		assertThrowsExactly(IllegalArgumentException.class, () -> {
			StringValueConverter.INSTANCE.toValue("\"\""); //$NON-NLS-1$
		}, Messages.VALIDATOR_IllegalStringLiteral);
		assertThrowsExactly(IllegalArgumentException.class, () -> {
			StringValueConverter.INSTANCE.toValue(""); //$NON-NLS-1$
		}, Messages.VALIDATOR_IllegalStringLiteral);
		assertThrowsExactly(IllegalArgumentException.class, () -> {
			StringValueConverter.INSTANCE.toValue("'"); //$NON-NLS-1$
		}, Messages.VALIDATOR_IllegalStringLiteral);
		assertThrowsExactly(IllegalArgumentException.class, () -> {
			StringValueConverter.INSTANCE.toValue("'\""); //$NON-NLS-1$
		}, Messages.VALIDATOR_UnevenlyQuotedStringLiteral);
		assertThrowsExactly(IllegalArgumentException.class, () -> {
			StringValueConverter.INSTANCE.toValue("aa"); //$NON-NLS-1$
		}, Messages.VALIDATOR_UnevenlyQuotedStringLiteral);
		assertEquals("abc", StringValueConverter.INSTANCE.toValue("'abc'")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("4diac'IDE", StringValueConverter.INSTANCE.toValue("'4diac$'IDE'")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("4diac\"IDE", StringValueConverter.INSTANCE.toValue("'4diac\"IDE'")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("4diac$IDE", StringValueConverter.INSTANCE.toValue("'4diac$$IDE'")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("4diac\nIDE", StringValueConverter.INSTANCE.toValue("'4diac$NIDE'")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("4diac\f\r\tIDE", StringValueConverter.INSTANCE.toValue("'4diac$P$R$TIDE'")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("4diac IDE", StringValueConverter.INSTANCE.toValue("'4diac$20IDE'")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("4diac\u000020IDE", StringValueConverter.INSTANCE.toValue("'4diac$0020IDE'")); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	void toValueWStringTest() {
		assertEquals("", WStringValueConverter.INSTANCE.toValue("\"\"")); //$NON-NLS-1$ //$NON-NLS-2$
		assertThrowsExactly(IllegalArgumentException.class, () -> {
			WStringValueConverter.INSTANCE.toValue("''"); //$NON-NLS-1$
		}, Messages.VALIDATOR_IllegalStringLiteral);
		assertThrowsExactly(IllegalArgumentException.class, () -> {
			WStringValueConverter.INSTANCE.toValue(""); //$NON-NLS-1$
		}, Messages.VALIDATOR_IllegalStringLiteral);
		assertThrowsExactly(IllegalArgumentException.class, () -> {
			WStringValueConverter.INSTANCE.toValue("\""); //$NON-NLS-1$
		}, Messages.VALIDATOR_IllegalStringLiteral);
		assertThrowsExactly(IllegalArgumentException.class, () -> {
			WStringValueConverter.INSTANCE.toValue("\"'"); //$NON-NLS-1$
		}, Messages.VALIDATOR_UnevenlyQuotedStringLiteral);
		assertThrowsExactly(IllegalArgumentException.class, () -> {
			WStringValueConverter.INSTANCE.toValue("aa"); //$NON-NLS-1$
		}, Messages.VALIDATOR_IllegalStringLiteral);
		assertEquals("abc", WStringValueConverter.INSTANCE.toValue("\"abc\"")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("4diac'IDE", StringValueConverter.INSTANCE.toValue("'4diac'IDE'")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("4diac\"IDE", StringValueConverter.INSTANCE.toValue("'4diac$\"IDE'")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("4diac$IDE", StringValueConverter.INSTANCE.toValue("'4diac$$IDE'")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("4diac\nIDE", WStringValueConverter.INSTANCE.toValue("\"4diac$NIDE\"")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("4diac\f\r\tIDE", WStringValueConverter.INSTANCE.toValue("\"4diac$P$R$TIDE\"")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("4diac IDE", WStringValueConverter.INSTANCE.toValue("\"4diac$0020IDE\"")); //$NON-NLS-1$ //$NON-NLS-2$
		assertThrowsExactly(IllegalArgumentException.class, () -> {
			WStringValueConverter.INSTANCE.toValue("\"4diac$20IDE\""); //$NON-NLS-1$
		}, Messages.VALIDATOR_IllegalStringLiteral);
	}

	@Test
	void toStringStringTest() {
		assertEquals("''", StringValueConverter.INSTANCE.toString("")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("'abc'", StringValueConverter.INSTANCE.toString("abc")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("'4diac$'IDE'", StringValueConverter.INSTANCE.toString("4diac'IDE")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("'4diac\"IDE'", StringValueConverter.INSTANCE.toString("4diac\"IDE")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("'4diac$$IDE'", StringValueConverter.INSTANCE.toString("4diac$IDE")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("'4diac$NIDE'", StringValueConverter.INSTANCE.toString("4diac\nIDE")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("'4diac$P$R$TIDE'", StringValueConverter.INSTANCE.toString("4diac\f\r\tIDE")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("'4diac$00IDE'", StringValueConverter.INSTANCE.toString("4diac\u0000IDE")); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	void toStringWStringTest() {
		assertEquals("\"\"", WStringValueConverter.INSTANCE.toString("")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("\"abc\"", WStringValueConverter.INSTANCE.toString("abc")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("\"4diac'IDE\"", WStringValueConverter.INSTANCE.toString("4diac'IDE")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("\"4diac$\"IDE\"", WStringValueConverter.INSTANCE.toString("4diac\"IDE")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("\"4diac$$IDE\"", WStringValueConverter.INSTANCE.toString("4diac$IDE")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("\"4diac$NIDE\"", WStringValueConverter.INSTANCE.toString("4diac\nIDE")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("\"4diac$P$R$TIDE\"", WStringValueConverter.INSTANCE.toString("4diac\f\r\tIDE")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("\"4diac$0000IDE\"", WStringValueConverter.INSTANCE.toString("4diac\u0000IDE")); //$NON-NLS-1$ //$NON-NLS-2$
	}
}
