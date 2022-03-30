/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.model.value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import java.math.BigInteger;

import org.eclipse.fordiac.ide.model.value.NumericValueConverter;
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
		}, NumericValueConverter.INVALID_NUMBER_LITERAL);
	}

	@Test
	void twoOrMoreConsecutiveUnderscoresForbiddenTest() {
		assertThrowsExactly(IllegalArgumentException.class, () -> {
			NumericValueConverter.INSTANCE.toValue("1__00"); //$NON-NLS-1$
		}, NumericValueConverter.CONSECUTIVE_UNDERSCORES_ERROR_MESSAGE);
	}

}
