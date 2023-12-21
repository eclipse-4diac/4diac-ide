/**
 * Copyright (c) 2022, 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.model.eval.value;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.eclipse.fordiac.ide.model.data.AnyMagnitudeType;

public sealed interface AnyMagnitudeValue extends AnyElementaryValue permits AnyNumValue, AnyDurationValue {
	@Override
	AnyMagnitudeType getType();

	byte byteValue();

	short shortValue();

	int intValue();

	long longValue();

	float floatValue();

	double doubleValue();

	BigInteger bigIntegerValue();

	BigDecimal bigDecimalValue();
}
