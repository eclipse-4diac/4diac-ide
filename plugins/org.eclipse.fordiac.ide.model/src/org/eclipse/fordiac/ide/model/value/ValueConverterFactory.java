/**
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
 */
package org.eclipse.fordiac.ide.model.value;

import org.eclipse.fordiac.ide.model.data.AnyBitType;
import org.eclipse.fordiac.ide.model.data.AnyDurationType;
import org.eclipse.fordiac.ide.model.data.AnyNumType;
import org.eclipse.fordiac.ide.model.data.BoolType;
import org.eclipse.fordiac.ide.model.data.CharType;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.DateAndTimeType;
import org.eclipse.fordiac.ide.model.data.DateType;
import org.eclipse.fordiac.ide.model.data.LdateType;
import org.eclipse.fordiac.ide.model.data.LdtType;
import org.eclipse.fordiac.ide.model.data.LtodType;
import org.eclipse.fordiac.ide.model.data.StringType;
import org.eclipse.fordiac.ide.model.data.TimeOfDayType;
import org.eclipse.fordiac.ide.model.data.WcharType;
import org.eclipse.fordiac.ide.model.data.WstringType;

public final class ValueConverterFactory {
	public static ValueConverter<?> createValueConverter(final DataType type) {
		if (type instanceof BoolType) {
			return BoolValueConverter.INSTANCE;
		} else if (type instanceof AnyNumType || type instanceof AnyBitType) {
			return NumericValueConverter.INSTANCE;
		} else if (type instanceof AnyDurationType) {
			return TimeValueConverter.INSTANCE;
		} else if (type instanceof CharType || type instanceof StringType) {
			return StringValueConverter.INSTANCE;
		} else if (type instanceof WcharType || type instanceof WstringType) {
			return WStringValueConverter.INSTANCE;
		} else if (type instanceof DateType || type instanceof LdateType) {
			return DateValueConverter.INSTANCE;
		} else if (type instanceof TimeOfDayType || type instanceof LtodType) {
			return TimeOfDayValueConverter.INSTANCE;
		} else if (type instanceof DateAndTimeType || type instanceof LdtType) {
			return DateAndTimeValueConverter.INSTANCE;
		}
		return null;
	}

	private ValueConverterFactory() {
		throw new IllegalStateException("Utility class should not be instantiated!"); //$NON-NLS-1$
	}
}
