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
package org.eclipse.fordiac.ide.model.value;

import java.text.MessageFormat;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.data.AnyDateType;
import org.eclipse.fordiac.ide.model.data.AnyDurationType;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.DateAndTimeType;
import org.eclipse.fordiac.ide.model.data.DateType;
import org.eclipse.fordiac.ide.model.data.LdateType;
import org.eclipse.fordiac.ide.model.data.LdtType;
import org.eclipse.fordiac.ide.model.data.LtimeType;
import org.eclipse.fordiac.ide.model.data.LtodType;
import org.eclipse.fordiac.ide.model.data.TimeOfDayType;
import org.eclipse.fordiac.ide.model.data.TimeType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;

public final class TypedValueConverter implements ValueConverter<Object> {
	public static final TypedValueConverter INSTANCE_TIME = new TypedValueConverter(ElementaryTypes.TIME);
	public static final TypedValueConverter INSTANCE_LTIME = new TypedValueConverter(ElementaryTypes.LTIME);
	public static final TypedValueConverter INSTANCE_DATE = new TypedValueConverter(ElementaryTypes.DATE);
	public static final TypedValueConverter INSTANCE_LDATE = new TypedValueConverter(ElementaryTypes.LDATE);
	public static final TypedValueConverter INSTANCE_TIME_OF_DAY = new TypedValueConverter(ElementaryTypes.TIME_OF_DAY);
	public static final TypedValueConverter INSTANCE_LTIME_OF_DAY = new TypedValueConverter(
			ElementaryTypes.LTIME_OF_DAY);
	public static final TypedValueConverter INSTANCE_DATE_AND_TIME = new TypedValueConverter(
			ElementaryTypes.DATE_AND_TIME);
	public static final TypedValueConverter INSTANCE_LDATE_AND_TIME = new TypedValueConverter(
			ElementaryTypes.LDATE_AND_TIME);

	private static final String TYPE_PREFIX_FORMAT = "%s#%s"; //$NON-NLS-1$
	private static final Pattern TYPE_PREFIX_PATTERN = Pattern.compile("(?:([a-zA-Z_]+)#)?(.+)"); //$NON-NLS-1$

	private static final String TIME_SHORT_FORM = "T"; //$NON-NLS-1$
	private static final String LTIME_SHORT_FORM = "LT"; //$NON-NLS-1$
	private static final String DATE_SHORT_FORM = "D"; //$NON-NLS-1$
	private static final String LDATE_SHORT_FORM = "LD"; //$NON-NLS-1$
	private static final String TIME_OF_DAY_SHORT_FORM = "TOD"; //$NON-NLS-1$
	private static final String LTIME_OF_DAY_SHORT_FORM = "LTOD"; //$NON-NLS-1$
	private static final String DATE_AND_TIME_SHORT_FORM = "DT"; //$NON-NLS-1$
	private static final String LDATE_AND_TIME_SHORT_FORM = "LDT"; //$NON-NLS-1$

	private static final Map<String, DataType> SHORT_FORM_TRANSLATIONS = Map.of(//
			TIME_SHORT_FORM, ElementaryTypes.TIME, // TIME
			LTIME_SHORT_FORM, ElementaryTypes.LTIME, // LTIME
			DATE_SHORT_FORM, ElementaryTypes.DATE, // DATE
			LDATE_SHORT_FORM, ElementaryTypes.LDATE, // LDATE
			TIME_OF_DAY_SHORT_FORM, ElementaryTypes.TIME_OF_DAY, // TIME_OF_DAY
			LTIME_OF_DAY_SHORT_FORM, ElementaryTypes.LTIME_OF_DAY, // LTIME_OF_DAY
			DATE_AND_TIME_SHORT_FORM, ElementaryTypes.DATE_AND_TIME, // DATE_AND_TIME
			LDATE_AND_TIME_SHORT_FORM, ElementaryTypes.LDATE_AND_TIME // LDATE_AND_TIME
			);

	private final DataType type;

	public TypedValueConverter(final DataType type) {
		this.type = type;
	}

	@Override
	public Object toValue(final String string) throws IllegalArgumentException {
		String value = string;
		DataType valueType = type;
		final Matcher matcher = TYPE_PREFIX_PATTERN.matcher(string);
		if (matcher.find() && matcher.group(1) != null) {
			valueType = getTypeFromPrefix(matcher.group(1));
			if (!valueType.isCompatibleWith(type)) {
				throw new IllegalArgumentException(
						MessageFormat.format(Messages.VALIDATOR_LITERAL_TYPE_INCOMPATIBLE_WITH_INPUT_TYPE,
								valueType.getName(), type.getName()));
			}
			value = matcher.group(2);
		} else if (isTypePrefixRequired(type)) {
			throw new IllegalArgumentException(
					MessageFormat.format(Messages.VALIDATOR_DatatypeRequiresTypeSpecifier, type.getName()));
		}
		final ValueConverter<?> delegate = getValueConverter(valueType);
		return delegate.toValue(value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String toString(final Object value) {
		final ValueConverter<Object> delegate = (ValueConverter<Object>) getValueConverter(type);
		final String result = delegate.toString(value);
		final String prefix = getPrefixFromType(type);
		if (!prefix.isEmpty()) {
			return String.format(TYPE_PREFIX_FORMAT, prefix, result);
		}
		return result;
	}

	private static DataType getTypeFromPrefix(final String prefix) throws IllegalArgumentException {
		DataType result = SHORT_FORM_TRANSLATIONS.get(prefix);
		if (result == null) {
			result = ElementaryTypes.getTypeByName(prefix);
		}
		if (result == null) {
			throw new IllegalArgumentException(Messages.VALIDATOR_UNKNOWN_LITERAL_TYPE);
		}
		return result;
	}

	private static String getPrefixFromType(final DataType type) throws IllegalArgumentException {
		if (type instanceof TimeType) {
			return TIME_SHORT_FORM;
		} else if (type instanceof LtimeType) {
			return LTIME_SHORT_FORM;
		} else if (type instanceof DateType) {
			return DATE_SHORT_FORM;
		} else if (type instanceof LdateType) {
			return LDATE_SHORT_FORM;
		} else if (type instanceof TimeOfDayType) {
			return TIME_OF_DAY_SHORT_FORM;
		} else if (type instanceof LtodType) {
			return LTIME_OF_DAY_SHORT_FORM;
		} else if (type instanceof DateAndTimeType) {
			return DATE_AND_TIME_SHORT_FORM;
		} else if (type instanceof LdtType) {
			return LDATE_AND_TIME_SHORT_FORM;
		}
		return ""; //$NON-NLS-1$
	}

	private static ValueConverter<?> getValueConverter(final DataType type) throws IllegalArgumentException {
		final ValueConverter<?> valueConverter = ValueConverterFactory.createValueConverter(type);
		if (valueConverter == null) {
			throw new IllegalArgumentException(Messages.VALIDATOR_TypeNotSupported);
		}
		return valueConverter;
	}

	private static boolean isTypePrefixRequired(final DataType type) {
		return IecTypes.GenericTypes.isAnyType(type) || type instanceof AnyDurationType || type instanceof AnyDateType;
	}
}
