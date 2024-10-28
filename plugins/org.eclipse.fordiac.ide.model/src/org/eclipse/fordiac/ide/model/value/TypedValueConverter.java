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
package org.eclipse.fordiac.ide.model.value;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.data.AnyBitType;
import org.eclipse.fordiac.ide.model.data.AnyDateType;
import org.eclipse.fordiac.ide.model.data.AnyDurationType;
import org.eclipse.fordiac.ide.model.data.AnyNumType;
import org.eclipse.fordiac.ide.model.data.BoolType;
import org.eclipse.fordiac.ide.model.data.ByteType;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.DateAndTimeType;
import org.eclipse.fordiac.ide.model.data.DateType;
import org.eclipse.fordiac.ide.model.data.DintType;
import org.eclipse.fordiac.ide.model.data.DwordType;
import org.eclipse.fordiac.ide.model.data.IntType;
import org.eclipse.fordiac.ide.model.data.LdateType;
import org.eclipse.fordiac.ide.model.data.LdtType;
import org.eclipse.fordiac.ide.model.data.LintType;
import org.eclipse.fordiac.ide.model.data.LrealType;
import org.eclipse.fordiac.ide.model.data.LtimeType;
import org.eclipse.fordiac.ide.model.data.LtodType;
import org.eclipse.fordiac.ide.model.data.LwordType;
import org.eclipse.fordiac.ide.model.data.RealType;
import org.eclipse.fordiac.ide.model.data.SintType;
import org.eclipse.fordiac.ide.model.data.TimeOfDayType;
import org.eclipse.fordiac.ide.model.data.TimeType;
import org.eclipse.fordiac.ide.model.data.UdintType;
import org.eclipse.fordiac.ide.model.data.UintType;
import org.eclipse.fordiac.ide.model.data.UlintType;
import org.eclipse.fordiac.ide.model.data.UsintType;
import org.eclipse.fordiac.ide.model.data.WordType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;

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
	private static final Pattern TYPE_PREFIX_PATTERN = Pattern.compile("\\G([_a-zA-Z]\\w*+)#"); //$NON-NLS-1$

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
	private final DataTypeLibrary typeLibrary;
	private final boolean strict;

	public TypedValueConverter(final DataType type) {
		this(type, null, false);
	}

	public TypedValueConverter(final DataType type, final boolean strict) {
		this(type, null, strict);
	}

	public TypedValueConverter(final DataType type, final DataTypeLibrary typeLibrary) {
		this(type, typeLibrary, false);
	}

	public TypedValueConverter(final DataType type, final DataTypeLibrary typeLibrary, final boolean strict) {
		this.type = type;
		this.typeLibrary = typeLibrary;
		this.strict = strict;
	}

	@Override
	public Object toValue(final String string) throws IllegalArgumentException {
		return toTypedValue(string).value();
	}

	public TypedValue toTypedValue(final String string) throws IllegalArgumentException {
		String value = string;
		DataType valueType = type;
		final Matcher matcher = TYPE_PREFIX_PATTERN.matcher(string);
		if (matcher.lookingAt()) {
			valueType = getTypeFromPrefix(matcher.group(1));
			if (!type.isAssignableFrom(valueType)) {
				throw new IllegalArgumentException(
						MessageFormat.format(Messages.VALIDATOR_LITERAL_TYPE_INCOMPATIBLE_WITH_INPUT_TYPE,
								valueType.getName(), type.getName()));
			}
			value = string.substring(matcher.end());
		} else if (isTypePrefixRequired(type)) {
			throw new IllegalArgumentException(
					MessageFormat.format(Messages.VALIDATOR_DatatypeRequiresTypeSpecifier, type.getName()));
		}
		final ValueConverter<?> delegate = getValueConverter(valueType);
		return new TypedValue(valueType, checkValue(valueType, string, delegate.toValue(value), strict));
	}

	@Override
	public Object toValue(final Scanner scanner)
			throws IllegalArgumentException, NoSuchElementException, IllegalStateException {
		return toTypedValue(scanner).value();
	}

	public TypedValue toTypedValue(final Scanner scanner)
			throws IllegalArgumentException, NoSuchElementException, IllegalStateException {
		DataType valueType = type;
		if (scanner.findWithinHorizon(TYPE_PREFIX_PATTERN, 0) != null) {
			valueType = getTypeFromPrefix(scanner.match().group(1));
			if (!type.isAssignableFrom(valueType)) {
				throw new IllegalArgumentException(
						MessageFormat.format(Messages.VALIDATOR_LITERAL_TYPE_INCOMPATIBLE_WITH_INPUT_TYPE,
								valueType.getName(), type.getName()));
			}
		} else if (isTypePrefixRequired(type)) {
			throw new IllegalArgumentException(
					MessageFormat.format(Messages.VALIDATOR_DatatypeRequiresTypeSpecifier, type.getName()));
		}
		final ValueConverter<?> delegate = getValueConverter(valueType);
		return new TypedValue(valueType, checkValue(valueType, "<scanner>", delegate.toValue(scanner), strict)); //$NON-NLS-1$
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

	private DataType getTypeFromPrefix(final String prefix) throws IllegalArgumentException {
		DataType result = SHORT_FORM_TRANSLATIONS.get(prefix);
		if (result == null) {
			result = ElementaryTypes.getTypeByName(prefix);
		}
		if (result == null && typeLibrary != null) {
			result = typeLibrary.getTypeIfExists(prefix);
		}
		if (result == null) {
			throw new IllegalArgumentException(MessageFormat.format(Messages.VALIDATOR_UNKNOWN_LITERAL_TYPE, prefix));
		}
		return result;
	}

	private static String getPrefixFromType(final DataType type) throws IllegalArgumentException {
		if (type instanceof TimeType) {
			return TIME_SHORT_FORM;
		}
		if (type instanceof LtimeType) {
			return LTIME_SHORT_FORM;
		}
		if (type instanceof DateType) {
			return DATE_SHORT_FORM;
		}
		if (type instanceof LdateType) {
			return LDATE_SHORT_FORM;
		}
		if (type instanceof TimeOfDayType) {
			return TIME_OF_DAY_SHORT_FORM;
		}
		if (type instanceof LtodType) {
			return LTIME_OF_DAY_SHORT_FORM;
		}
		if (type instanceof DateAndTimeType) {
			return DATE_AND_TIME_SHORT_FORM;
		}
		if (type instanceof LdtType) {
			return LDATE_AND_TIME_SHORT_FORM;
		}
		return ""; //$NON-NLS-1$
	}

	private static ValueConverter<?> getValueConverter(final DataType type) throws IllegalArgumentException {
		final ValueConverter<?> valueConverter = ValueConverterFactory.createValueConverter(type);
		if (valueConverter == null) {
			throw new IllegalArgumentException(
					MessageFormat.format(Messages.VALIDATOR_TypeNotSupported, type.getName()));
		}
		return valueConverter;
	}

	private static boolean isTypePrefixRequired(final DataType type) {
		return IecTypes.GenericTypes.isAnyType(type) || type instanceof AnyDurationType || type instanceof AnyDateType;
	}

	private static Object checkValue(final DataType type, final String string, final Object value,
			final boolean strict) {
		if ((type instanceof AnyNumType || type instanceof AnyBitType) && !isNumericValueValid(type, value, strict)) {
			throw new IllegalArgumentException(MessageFormat.format(Messages.VALIDATOR_INVALID_NUMBER_LITERAL, string));
		}
		return value;
	}

	private static boolean isNumericValueValid(final DataType type, final Object value, final boolean strict) {
		if (value instanceof Boolean) {
			return type instanceof BoolType;
		}
		if (value instanceof final BigDecimal bigDecimal) {
			if (type instanceof RealType) {
				return Float.isFinite(bigDecimal.floatValue());
			}
			if (type instanceof LrealType) {
				return Double.isFinite(bigDecimal.doubleValue());
			}
		}
		if (value instanceof final BigInteger bigInteger) {
			if (type instanceof RealType && !strict) {
				return Float.isFinite(bigInteger.floatValue());
			}
			if (type instanceof LrealType && !strict) {
				return Double.isFinite(bigInteger.doubleValue());
			}
			if (type instanceof SintType) {
				return checkRange(bigInteger, Byte.MIN_VALUE, Byte.MAX_VALUE);
			}
			if (type instanceof IntType) {
				return checkRange(bigInteger, Short.MIN_VALUE, Short.MAX_VALUE);
			}
			if (type instanceof DintType) {
				return checkRange(bigInteger, Integer.MIN_VALUE, Integer.MAX_VALUE);
			}
			if (type instanceof LintType) {
				return checkRange(bigInteger, Long.MIN_VALUE, Long.MAX_VALUE);
			}
			if (type instanceof UsintType) {
				return checkRangeUnsigned(bigInteger, BigInteger.valueOf(0xffL));
			}
			if (type instanceof UintType) {
				return checkRangeUnsigned(bigInteger, BigInteger.valueOf(0xffffL));
			}
			if (type instanceof UdintType) {
				return checkRangeUnsigned(bigInteger, BigInteger.valueOf(0xffffffffL));
			}
			if (type instanceof UlintType) {
				return checkRangeUnsigned(bigInteger, new BigInteger("ffffffffffffffff", 16)); //$NON-NLS-1$
			}
			if (type instanceof BoolType) {
				return checkRangeUnsigned(bigInteger, BigInteger.ONE);
			}
			if (type instanceof ByteType) {
				return checkRangeUnsigned(bigInteger, BigInteger.valueOf(0xffL));
			}
			if (type instanceof WordType) {
				return checkRangeUnsigned(bigInteger, BigInteger.valueOf(0xffffL));
			}
			if (type instanceof DwordType) {
				return checkRangeUnsigned(bigInteger, BigInteger.valueOf(0xffffffffL));
			}
			if (type instanceof LwordType) {
				return checkRangeUnsigned(bigInteger, new BigInteger("ffffffffffffffff", 16)); //$NON-NLS-1$
			}
		}
		return false;
	}

	private static boolean checkRange(final BigInteger value, final long lower, final long upper) {
		return value.compareTo(BigInteger.valueOf(lower)) >= 0 && value.compareTo(BigInteger.valueOf(upper)) <= 0;
	}

	private static boolean checkRangeUnsigned(final BigInteger value, final BigInteger upper) {
		return value.signum() >= 0 && value.compareTo(upper) <= 0;
	}

	@Override
	public String toString() {
		if (strict) {
			return String.format("%s [type=%s, strict]", getClass().getSimpleName(), type.getName()); //$NON-NLS-1$
		}
		return String.format("%s [type=%s]", getClass().getSimpleName(), type.getName()); //$NON-NLS-1$
	}
}
