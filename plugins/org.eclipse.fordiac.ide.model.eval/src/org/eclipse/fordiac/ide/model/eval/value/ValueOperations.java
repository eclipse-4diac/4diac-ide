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
package org.eclipse.fordiac.ide.model.eval.value;

import static org.eclipse.fordiac.ide.model.eval.value.BoolValue.toBoolValue;
import static org.eclipse.fordiac.ide.model.eval.value.ByteValue.toByteValue;
import static org.eclipse.fordiac.ide.model.eval.value.CharValue.toCharValue;
import static org.eclipse.fordiac.ide.model.eval.value.DIntValue.toDIntValue;
import static org.eclipse.fordiac.ide.model.eval.value.DWordValue.toDWordValue;
import static org.eclipse.fordiac.ide.model.eval.value.DateAndTimeValue.toDateAndTimeValue;
import static org.eclipse.fordiac.ide.model.eval.value.DateValue.toDateValue;
import static org.eclipse.fordiac.ide.model.eval.value.IntValue.toIntValue;
import static org.eclipse.fordiac.ide.model.eval.value.LDateAndTimeValue.toLDateAndTimeValue;
import static org.eclipse.fordiac.ide.model.eval.value.LDateValue.toLDateValue;
import static org.eclipse.fordiac.ide.model.eval.value.LIntValue.toLIntValue;
import static org.eclipse.fordiac.ide.model.eval.value.LRealValue.toLRealValue;
import static org.eclipse.fordiac.ide.model.eval.value.LTimeOfDayValue.toLTimeOfDayValue;
import static org.eclipse.fordiac.ide.model.eval.value.LTimeValue.toLTimeValue;
import static org.eclipse.fordiac.ide.model.eval.value.LWordValue.toLWordValue;
import static org.eclipse.fordiac.ide.model.eval.value.RealValue.toRealValue;
import static org.eclipse.fordiac.ide.model.eval.value.SIntValue.toSIntValue;
import static org.eclipse.fordiac.ide.model.eval.value.StringValue.toStringValue;
import static org.eclipse.fordiac.ide.model.eval.value.TimeOfDayValue.toTimeOfDayValue;
import static org.eclipse.fordiac.ide.model.eval.value.TimeValue.toTimeValue;
import static org.eclipse.fordiac.ide.model.eval.value.UDIntValue.toUDIntValue;
import static org.eclipse.fordiac.ide.model.eval.value.UIntValue.toUIntValue;
import static org.eclipse.fordiac.ide.model.eval.value.ULIntValue.toULIntValue;
import static org.eclipse.fordiac.ide.model.eval.value.USIntValue.toUSIntValue;
import static org.eclipse.fordiac.ide.model.eval.value.WCharValue.toWCharValue;
import static org.eclipse.fordiac.ide.model.eval.value.WStringValue.toWStringValue;
import static org.eclipse.fordiac.ide.model.eval.value.WordValue.toWordValue;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

import org.eclipse.fordiac.ide.model.data.AnyBitType;
import org.eclipse.fordiac.ide.model.data.AnyCharType;
import org.eclipse.fordiac.ide.model.data.AnyCharsType;
import org.eclipse.fordiac.ide.model.data.AnyDateType;
import org.eclipse.fordiac.ide.model.data.AnyDerivedType;
import org.eclipse.fordiac.ide.model.data.AnyDurationType;
import org.eclipse.fordiac.ide.model.data.AnyElementaryType;
import org.eclipse.fordiac.ide.model.data.AnyIntType;
import org.eclipse.fordiac.ide.model.data.AnyMagnitudeType;
import org.eclipse.fordiac.ide.model.data.AnyNumType;
import org.eclipse.fordiac.ide.model.data.AnyRealType;
import org.eclipse.fordiac.ide.model.data.AnySignedType;
import org.eclipse.fordiac.ide.model.data.AnyStringType;
import org.eclipse.fordiac.ide.model.data.AnyType;
import org.eclipse.fordiac.ide.model.data.AnyUnsignedType;
import org.eclipse.fordiac.ide.model.data.ArrayType;
import org.eclipse.fordiac.ide.model.data.BoolType;
import org.eclipse.fordiac.ide.model.data.ByteType;
import org.eclipse.fordiac.ide.model.data.CharType;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.DateAndTimeType;
import org.eclipse.fordiac.ide.model.data.DateType;
import org.eclipse.fordiac.ide.model.data.DintType;
import org.eclipse.fordiac.ide.model.data.DwordType;
import org.eclipse.fordiac.ide.model.data.EnumeratedType;
import org.eclipse.fordiac.ide.model.data.EnumeratedValue;
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
import org.eclipse.fordiac.ide.model.data.StringType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.data.TimeOfDayType;
import org.eclipse.fordiac.ide.model.data.TimeType;
import org.eclipse.fordiac.ide.model.data.UdintType;
import org.eclipse.fordiac.ide.model.data.UintType;
import org.eclipse.fordiac.ide.model.data.UlintType;
import org.eclipse.fordiac.ide.model.data.UsintType;
import org.eclipse.fordiac.ide.model.data.WcharType;
import org.eclipse.fordiac.ide.model.data.WordType;
import org.eclipse.fordiac.ide.model.data.WstringType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes;
import org.eclipse.fordiac.ide.model.eval.Messages;
import org.eclipse.fordiac.ide.model.eval.variable.ArrayVariable;
import org.eclipse.fordiac.ide.model.eval.variable.StructVariable;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.model.value.TypedValue;
import org.eclipse.fordiac.ide.model.value.TypedValueConverter;

public final class ValueOperations {

	public static Value abs(final Value value) {
		return switch (value) {
		case final LRealValue lrealValue -> toLRealValue(Math.abs(lrealValue.doubleValue()));
		case final RealValue realValue -> toRealValue(Math.abs(realValue.floatValue()));
		case final LIntValue lintValue -> toLIntValue(Math.abs(lintValue.longValue()));
		case final DIntValue dintValue -> toDIntValue(Math.abs(dintValue.intValue()));
		case final IntValue intValue -> toIntValue((short) Math.abs(intValue.shortValue()));
		case final SIntValue sintValue -> toSIntValue((byte) Math.abs(sintValue.byteValue()));
		case final ULIntValue ulintValue -> ulintValue;
		case final UDIntValue udintValue -> udintValue;
		case final UIntValue uintValue -> uintValue;
		case final USIntValue usintValue -> usintValue;
		case final LTimeValue ltimeValue -> toLTimeValue(ltimeValue.longValue());
		case final TimeValue timeValue -> toTimeValue(timeValue.longValue());
		case null, default -> throw createUnsupportedUnaryOperationException(Messages.ValueOperations_Absolute, value);
		};
	}

	public static Value negate(final Value value) {
		return switch (value) {
		case final LRealValue lrealValue -> toLRealValue(-lrealValue.doubleValue());
		case final RealValue realValue -> toRealValue(-realValue.floatValue());
		case final LIntValue lintValue -> toLIntValue(-lintValue.longValue());
		case final DIntValue dintValue -> toDIntValue(-dintValue.intValue());
		case final IntValue intValue -> toIntValue((short) -intValue.shortValue());
		case final SIntValue sintValue -> toSIntValue((byte) -sintValue.byteValue());
		case final ULIntValue ulintValue -> toULIntValue(-ulintValue.longValue());
		case final UDIntValue udintValue -> toUDIntValue(-udintValue.intValue());
		case final UIntValue uintValue -> toUIntValue((short) -uintValue.shortValue());
		case final USIntValue usintValue -> toUSIntValue((byte) -usintValue.byteValue());
		case final LTimeValue ltimeValue -> toLTimeValue(-ltimeValue.longValue());
		case final TimeValue timeValue -> toTimeValue(-timeValue.longValue());
		case null, default -> throw createUnsupportedUnaryOperationException(Messages.ValueOperations_Negate, value);
		};
	}

	public static Value bitwiseNot(final Value value) {
		return switch (value) {
		case final LWordValue lwordValue -> toLWordValue(~lwordValue.longValue());
		case final DWordValue dwordValue -> toDWordValue(~dwordValue.intValue());
		case final WordValue wordValue -> toWordValue((short) ~wordValue.intValue());
		case final ByteValue byteValue -> toByteValue((byte) ~byteValue.intValue());
		case final BoolValue boolValue -> toBoolValue(!boolValue.boolValue());
		case null, default -> throw createUnsupportedUnaryOperationException(Messages.ValueOperations_Not, value);
		};
	}

	public static Value reverseBytes(final Value value) {
		return switch (value) {
		case final LRealValue lrealValue -> toLRealValue(Double.longBitsToDouble(//
				Long.reverseBytes(Double.doubleToRawLongBits(lrealValue.doubleValue()))));
		case final RealValue realValue -> toRealValue(Float.intBitsToFloat(//
				Integer.reverseBytes(Float.floatToRawIntBits(realValue.floatValue()))));
		case final LIntValue lintValue -> toLIntValue(Long.reverseBytes(lintValue.longValue()));
		case final DIntValue dintValue -> toDIntValue(Integer.reverseBytes(dintValue.intValue()));
		case final IntValue intValue -> toIntValue(Short.reverseBytes(intValue.shortValue()));
		case final SIntValue sintValue -> sintValue;
		case final ULIntValue ulintValue -> toULIntValue(Long.reverseBytes(ulintValue.longValue()));
		case final UDIntValue udintValue -> toUDIntValue(Integer.reverseBytes(udintValue.intValue()));
		case final UIntValue uintValue -> toUIntValue(Short.reverseBytes(uintValue.shortValue()));
		case final USIntValue usintValue -> usintValue;
		case final LTimeValue ltimeValue -> toLTimeValue(Long.reverseBytes(ltimeValue.longValue()));
		case final TimeValue timeValue -> toTimeValue(Long.reverseBytes(timeValue.longValue()));
		case final LWordValue lwordValue -> toLWordValue(Long.reverseBytes(lwordValue.longValue()));
		case final DWordValue dwordValue -> toDWordValue(Integer.reverseBytes(dwordValue.intValue()));
		case final WordValue wordValue -> toWordValue(Short.reverseBytes(wordValue.shortValue()));
		case final ByteValue byteValue -> byteValue;
		case final BoolValue boolValue -> boolValue;
		case final StringValue stringValue -> stringValue;
		case final CharValue charValue -> charValue;
		case final WStringValue wstringValue -> toWStringValue(
				new String(wstringValue.stringValue().getBytes(StandardCharsets.UTF_16BE), StandardCharsets.UTF_16LE));
		case final WCharValue wcharValue -> toWCharValue(Character.reverseBytes(wcharValue.charValue()));
		case final LDateValue ldateValue -> ldateValue;
		case final DateValue dateValue -> dateValue;
		case final LTimeOfDayValue ltimeOfDayValue -> ltimeOfDayValue;
		case final TimeOfDayValue timeOfDayValue -> timeOfDayValue;
		case final LDateAndTimeValue ldateAndTimeValue -> ldateAndTimeValue;
		case final DateAndTimeValue dateAndTimeValue -> dateAndTimeValue;
		case final ArrayValue arrayValue -> {
			final var temp = new ArrayVariable("TEMP", arrayValue.getType(), arrayValue); //$NON-NLS-1$
			temp.forEach(variable -> variable.setValue(reverseBytes(variable.getValue())));
			yield temp.getValue();
		}
		case final StructValue structValue -> {
			final var temp = new StructVariable("TEMP", structValue.getType(), structValue); //$NON-NLS-1$
			temp.forEach(variable -> variable.setValue(reverseBytes(variable.getValue())));
			yield temp.getValue();
		}
		case null, default ->
			throw createUnsupportedUnaryOperationException(Messages.ValueOperations_ReverseBytes, value);
		};
	}

	public static Value sqrt(final Value value) {
		return switch (value) {
		case final LRealValue lrealValue -> toLRealValue(Math.sqrt(lrealValue.doubleValue()));
		case final RealValue realValue -> toRealValue((float) Math.sqrt(realValue.floatValue()));
		case null, default -> throw createUnsupportedUnaryOperationException(Messages.ValueOperations_Sqrt, value);
		};
	}

	public static Value add(final Value first, final Value second) {
		if (first instanceof final AnyMagnitudeValue firstAnyMagnitudeValue) {
			if (second instanceof final AnyMagnitudeValue secondAnyMagnitudeValue) {
				return add(firstAnyMagnitudeValue, secondAnyMagnitudeValue);
			}
		} else if (first instanceof final AnyDateValue firstAnyDateValue) {
			if (second instanceof final TimeValue secondTimeValue) {
				return add(firstAnyDateValue, secondTimeValue);
			}
			if (second instanceof final LTimeValue secondLTimeValue) {
				return add(firstAnyDateValue, secondLTimeValue);
			}
		}
		throw createUnsupportedBinaryOperationException(Messages.ValueOperations_Add, first, second);
	}

	private static AnyMagnitudeValue add(final AnyMagnitudeValue first, final AnyMagnitudeValue second) {
		return switch (resultType(first.getType(), second.getType())) {
		case final LrealType _ -> toLRealValue(first.doubleValue() + second.doubleValue());
		case final RealType _ -> toRealValue(first.floatValue() + second.floatValue());
		case final LintType _ -> toLIntValue(first.longValue() + second.longValue());
		case final DintType _ -> toDIntValue(first.intValue() + second.intValue());
		case final IntType _ -> toIntValue((short) (first.shortValue() + second.shortValue()));
		case final SintType _ -> toSIntValue((byte) (first.byteValue() + second.byteValue()));
		case final UlintType _ -> toULIntValue(first.longValue() + second.longValue());
		case final UdintType _ -> toUDIntValue(first.intValue() + second.intValue());
		case final UintType _ -> toUIntValue((short) (first.shortValue() + second.shortValue()));
		case final UsintType _ -> toUSIntValue((byte) (first.byteValue() + second.byteValue()));
		case final LtimeType _ -> toLTimeValue(first.longValue() + second.longValue());
		case final TimeType _ -> toTimeValue(first.longValue() + second.longValue());
		case null, default ->
			throw createUnsupportedBinaryOperationException(Messages.ValueOperations_Add, first, second);
		};
	}

	private static AnyDateValue add(final AnyDateValue first, final TimeValue second) {
		return switch (first) {
		case final TimeOfDayValue _ -> toTimeOfDayValue(first.toNanos() + second.longValue());
		case final LTimeOfDayValue _ -> toLTimeOfDayValue(first.toNanos() + second.longValue());
		case final DateAndTimeValue _ -> toDateAndTimeValue(first.toNanos() + second.longValue());
		case final LDateAndTimeValue _ -> toLDateAndTimeValue(first.toNanos() + second.longValue());
		case null, default ->
			throw createUnsupportedBinaryOperationException(Messages.ValueOperations_Add, first, second);
		};
	}

	private static AnyDateValue add(final AnyDateValue first, final LTimeValue second) {
		return switch (first) {
		case final TimeOfDayValue _ -> toLTimeOfDayValue(first.toNanos() + second.longValue());
		case final LTimeOfDayValue _ -> toLTimeOfDayValue(first.toNanos() + second.longValue());
		case final DateAndTimeValue _ -> toLDateAndTimeValue(first.toNanos() + second.longValue());
		case final LDateAndTimeValue _ -> toLDateAndTimeValue(first.toNanos() + second.longValue());
		case null, default ->
			throw createUnsupportedBinaryOperationException(Messages.ValueOperations_Add, first, second);
		};
	}

	public static Value subtract(final Value first, final Value second) {
		if (first instanceof final AnyMagnitudeValue firstAnyMagnitudeValue) {
			if (second instanceof final AnyMagnitudeValue secondAnyMagnitudeValue) {
				return subtract(firstAnyMagnitudeValue, secondAnyMagnitudeValue);
			}
		} else if (first instanceof final AnyDateValue firstAnyDateValue) {
			if (second instanceof final LTimeValue secondLTimeValue) {
				return subtract(firstAnyDateValue, secondLTimeValue);
			}
			if (second instanceof final TimeValue secondTimeValue) {
				return subtract(firstAnyDateValue, secondTimeValue);
			}
			if (second instanceof final AnyDateValue secondAnyDateValue) {
				return subtract(firstAnyDateValue, secondAnyDateValue);
			}
		}
		throw createUnsupportedBinaryOperationException(Messages.ValueOperations_Subtract, first, second);
	}

	private static AnyMagnitudeValue subtract(final AnyMagnitudeValue first, final AnyMagnitudeValue second) {
		return switch (resultType(first.getType(), second.getType())) {
		case final LrealType _ -> toLRealValue(first.doubleValue() - second.doubleValue());
		case final RealType _ -> toRealValue(first.floatValue() - second.floatValue());
		case final LintType _ -> toLIntValue(first.longValue() - second.longValue());
		case final DintType _ -> toDIntValue(first.intValue() - second.intValue());
		case final IntType _ -> toIntValue((short) (first.shortValue() - second.shortValue()));
		case final SintType _ -> toSIntValue((byte) (first.byteValue() - second.byteValue()));
		case final UlintType _ -> toULIntValue(first.longValue() - second.longValue());
		case final UdintType _ -> toUDIntValue(first.intValue() - second.intValue());
		case final UintType _ -> toUIntValue((short) (first.shortValue() - second.shortValue()));
		case final UsintType _ -> toUSIntValue((byte) (first.byteValue() - second.byteValue()));
		case final LtimeType _ -> toLTimeValue(first.longValue() - second.longValue());
		case final TimeType _ -> toTimeValue(first.longValue() - second.longValue());
		case null, default ->
			throw createUnsupportedBinaryOperationException(Messages.ValueOperations_Subtract, first, second);
		};
	}

	private static AnyDateValue subtract(final AnyDateValue first, final TimeValue second) {
		return switch (first) {
		case final TimeOfDayValue _ -> toTimeOfDayValue(first.toNanos() - second.longValue());
		case final LTimeOfDayValue _ -> toLTimeOfDayValue(first.toNanos() - second.longValue());
		case final DateAndTimeValue _ -> toDateAndTimeValue(first.toNanos() - second.longValue());
		case final LDateAndTimeValue _ -> toLDateAndTimeValue(first.toNanos() - second.longValue());
		case null, default ->
			throw createUnsupportedBinaryOperationException(Messages.ValueOperations_Subtract, first, second);
		};
	}

	private static AnyDateValue subtract(final AnyDateValue first, final LTimeValue second) {
		return switch (first) {
		case final TimeOfDayValue _ -> toLTimeOfDayValue(first.toNanos() - second.longValue());
		case final LTimeOfDayValue _ -> toLTimeOfDayValue(first.toNanos() - second.longValue());
		case final DateAndTimeValue _ -> toLDateAndTimeValue(first.toNanos() - second.longValue());
		case final LDateAndTimeValue _ -> toLDateAndTimeValue(first.toNanos() - second.longValue());
		case null, default ->
			throw createUnsupportedBinaryOperationException(Messages.ValueOperations_Subtract, first, second);
		};
	}

	private static AnyDurationValue subtract(final AnyDateValue first, final AnyDateValue second) {
		return switch (resultType(first.getType(), second.getType())) {
		case final DateType _ -> toTimeValue(first.toNanos() - second.toNanos());
		case final TimeOfDayType _ -> toTimeValue(first.toNanos() - second.toNanos());
		case final DateAndTimeType _ -> toTimeValue(first.toNanos() - second.toNanos());
		case final LdateType _ -> toLTimeValue(first.toNanos() - second.toNanos());
		case final LtodType _ -> toLTimeValue(first.toNanos() - second.toNanos());
		case final LdtType _ -> toLTimeValue(first.toNanos() - second.toNanos());
		case null, default ->
			throw createUnsupportedBinaryOperationException(Messages.ValueOperations_Subtract, first, second);
		};
	}

	public static Value multiply(final Value first, final Value second) {
		if (first instanceof final AnyMagnitudeValue firstAnyMagnitudeValue
				&& second instanceof final AnyMagnitudeValue secondAnyMagnitudeValue) {
			return multiply(firstAnyMagnitudeValue, secondAnyMagnitudeValue);
		}
		throw createUnsupportedBinaryOperationException(Messages.ValueOperations_Multiply, first, second);
	}

	private static AnyMagnitudeValue multiply(final AnyMagnitudeValue first, final AnyMagnitudeValue second) {
		return switch (resultType(first.getType(), second.getType())) {
		case final LrealType _ -> toLRealValue(first.doubleValue() * second.doubleValue());
		case final RealType _ -> toRealValue(first.floatValue() * second.floatValue());
		case final LintType _ -> toLIntValue(first.longValue() * second.longValue());
		case final DintType _ -> toDIntValue(first.intValue() * second.intValue());
		case final IntType _ -> toIntValue((short) (first.shortValue() * second.shortValue()));
		case final SintType _ -> toSIntValue((byte) (first.byteValue() * second.byteValue()));
		case final UlintType _ -> toULIntValue(first.longValue() * second.longValue());
		case final UdintType _ -> toUDIntValue(first.intValue() * second.intValue());
		case final UintType _ -> toUIntValue((short) (first.shortValue() * second.shortValue()));
		case final UsintType _ -> toUSIntValue((byte) (first.byteValue() * second.byteValue()));
		case final LtimeType _ -> second instanceof AnyRealValue ? //
				toLTimeValue((long) (first.doubleValue() * second.doubleValue()))
				: toLTimeValue(first.longValue() * second.longValue());
		case final TimeType _ -> second instanceof AnyRealValue ? //
				toTimeValue((long) (first.doubleValue() * second.doubleValue()))
				: toTimeValue(first.longValue() * second.longValue());
		case null, default ->
			throw createUnsupportedBinaryOperationException(Messages.ValueOperations_Multiply, first, second);
		};
	}

	public static Value divideBy(final Value first, final Value second) {
		if (first instanceof final AnyMagnitudeValue firstAnyMagnitudeValue
				&& second instanceof final AnyMagnitudeValue secondAnyMagnitudeValue) {
			return divideBy(firstAnyMagnitudeValue, secondAnyMagnitudeValue);
		}
		throw createUnsupportedBinaryOperationException(Messages.ValueOperations_Divide, first, second);
	}

	private static AnyMagnitudeValue divideBy(final AnyMagnitudeValue first, final AnyMagnitudeValue second) {
		return switch (resultType(first.getType(), second.getType())) {
		case final LrealType _ -> toLRealValue(first.doubleValue() / second.doubleValue());
		case final RealType _ -> toRealValue(first.floatValue() / second.floatValue());
		case final LintType _ -> toLIntValue(first.longValue() / second.longValue());
		case final DintType _ -> toDIntValue(first.intValue() / second.intValue());
		case final IntType _ -> toIntValue((short) (first.shortValue() / second.shortValue()));
		case final SintType _ -> toSIntValue((byte) (first.byteValue() / second.byteValue()));
		case final UlintType _ -> toULIntValue(Long.divideUnsigned(first.longValue(), second.longValue()));
		case final UdintType _ -> toUDIntValue(Integer.divideUnsigned(first.intValue(), second.intValue()));
		case final UintType _ -> toUIntValue((short) Integer.divideUnsigned(first.intValue(), second.intValue()));
		case final UsintType _ -> toUSIntValue((byte) Integer.divideUnsigned(first.intValue(), second.intValue()));
		case final LtimeType _ -> {
			if (second instanceof AnyRealValue) {
				if (second.doubleValue() == 0.0) {
					throw new ArithmeticException(Messages.ValueOperations_DivisionByZero);
				}
				yield toLTimeValue((long) (first.doubleValue() / second.doubleValue()));
			}
			yield toLTimeValue(first.longValue() / second.longValue());
		}
		case final TimeType _ -> {
			if (second instanceof AnyRealValue) {
				if (second.doubleValue() == 0.0) {
					throw new ArithmeticException(Messages.ValueOperations_DivisionByZero);
				}
				yield toTimeValue((long) (first.doubleValue() / second.doubleValue()));
			}
			yield toTimeValue(first.longValue() / second.longValue());
		}
		case null, default ->
			throw createUnsupportedBinaryOperationException(Messages.ValueOperations_Divide, first, second);
		};
	}

	public static Value remainderBy(final Value first, final Value second) {
		if (first instanceof final AnyIntValue firstAnyIntValue
				&& second instanceof final AnyIntValue secondAnyIntValue) {
			return remainderBy(firstAnyIntValue, secondAnyIntValue);
		}
		throw createUnsupportedBinaryOperationException(Messages.ValueOperations_Remainder, first, second);
	}

	private static AnyIntValue remainderBy(final AnyIntValue first, final AnyIntValue second) {
		if (second.longValue() == 0) {// MOD by 0 defined to return 0
			return (AnyIntValue) defaultValue(resultType(first.getType(), second.getType()));
		}
		return switch (resultType(first.getType(), second.getType())) {
		case final LintType _ -> toLIntValue(first.longValue() % second.longValue());
		case final DintType _ -> toDIntValue(first.intValue() % second.intValue());
		case final IntType _ -> toIntValue((short) (first.shortValue() % second.shortValue()));
		case final SintType _ -> toSIntValue((byte) (first.byteValue() % second.byteValue()));
		case final UlintType _ -> toULIntValue(Long.remainderUnsigned(first.longValue(), second.longValue()));
		case final UdintType _ -> toUDIntValue(Integer.remainderUnsigned(first.intValue(), second.intValue()));
		case final UintType _ -> toUIntValue((short) Integer.remainderUnsigned(first.intValue(), second.intValue()));
		case final UsintType _ -> toUSIntValue((byte) Integer.remainderUnsigned(first.intValue(), second.intValue()));
		case null, default ->
			throw createUnsupportedBinaryOperationException(Messages.ValueOperations_Remainder, first, second);
		};
	}

	public static Value power(final Value first, final Value second) {
		if (first instanceof final AnyNumValue firstAnyNumValue
				&& second instanceof final AnyNumValue secondAnyNumValue) {
			return power(firstAnyNumValue, secondAnyNumValue);
		}
		throw createUnsupportedBinaryOperationException(Messages.ValueOperations_Power, first, second);
	}

	private static AnyNumValue power(final AnyNumValue first, final AnyNumValue second) {
		return switch (first) {
		case final RealValue _ -> toRealValue((float) Math.pow(first.floatValue(), second.floatValue()));
		case final LRealValue _ -> toLRealValue(Math.pow(first.doubleValue(), second.doubleValue()));
		case null, default ->
			throw createUnsupportedBinaryOperationException(Messages.ValueOperations_Power, first, second);
		};
	}

	public static Value bitwiseAnd(final Value first, final Value second) {
		if (first instanceof final AnyBitValue firstAnyBitValue
				&& second instanceof final AnyBitValue secondAnyBitValue) {
			return bitwiseAnd(firstAnyBitValue, secondAnyBitValue);
		}
		throw createUnsupportedBinaryOperationException(Messages.ValueOperations_And, first, second);
	}

	private static AnyBitValue bitwiseAnd(final AnyBitValue first, final AnyBitValue second) {
		return switch (resultType(first.getType(), second.getType())) {
		case final LwordType _ -> toLWordValue(first.longValue() & second.longValue());
		case final DwordType _ -> toDWordValue(first.intValue() & second.intValue());
		case final WordType _ -> toWordValue((short) (first.shortValue() & second.shortValue()));
		case final ByteType _ -> toByteValue((byte) (first.byteValue() & second.byteValue()));
		case final BoolType _ -> toBoolValue(first.boolValue() && second.boolValue());
		case null, default ->
			throw createUnsupportedBinaryOperationException(Messages.ValueOperations_And, first, second);
		};
	}

	public static Value bitwiseOr(final Value first, final Value second) {
		if (first instanceof final AnyBitValue firstAnyBitValue
				&& second instanceof final AnyBitValue secondAnyBitValue) {
			return bitwiseOr(firstAnyBitValue, secondAnyBitValue);
		}
		throw createUnsupportedBinaryOperationException(Messages.ValueOperations_Or, first, second);
	}

	private static AnyBitValue bitwiseOr(final AnyBitValue first, final AnyBitValue second) {
		return switch (resultType(first.getType(), second.getType())) {
		case final LwordType _ -> toLWordValue(first.longValue() | second.longValue());
		case final DwordType _ -> toDWordValue(first.intValue() | second.intValue());
		case final WordType _ -> toWordValue((short) (first.shortValue() | second.shortValue()));
		case final ByteType _ -> toByteValue((byte) (first.byteValue() | second.byteValue()));
		case final BoolType _ -> toBoolValue(first.boolValue() || second.boolValue());
		case null, default ->
			throw createUnsupportedBinaryOperationException(Messages.ValueOperations_Or, first, second);
		};
	}

	public static Value bitwiseXor(final Value first, final Value second) {
		if (first instanceof final AnyBitValue firstAnyBitValue
				&& second instanceof final AnyBitValue secondAnyBitValue) {
			return bitwiseXor(firstAnyBitValue, secondAnyBitValue);
		}
		throw createUnsupportedBinaryOperationException(Messages.ValueOperations_Xor, first, second);
	}

	private static AnyBitValue bitwiseXor(final AnyBitValue first, final AnyBitValue second) {
		return switch (resultType(first.getType(), second.getType())) {
		case final LwordType _ -> toLWordValue(first.longValue() ^ second.longValue());
		case final DwordType _ -> toDWordValue(first.intValue() ^ second.intValue());
		case final WordType _ -> toWordValue((short) (first.shortValue() ^ second.shortValue()));
		case final ByteType _ -> toByteValue((byte) (first.byteValue() ^ second.byteValue()));
		case final BoolType _ -> toBoolValue(first.boolValue() ^ second.boolValue());
		case null, default ->
			throw createUnsupportedBinaryOperationException(Messages.ValueOperations_Xor, first, second);
		};
	}

	public static Value shiftLeft(final Value first, final Value second) {
		if (first instanceof final AnyBitValue firstAnyBitValue
				&& second instanceof final AnyIntValue secondAnyIntValue) {
			return shiftLeft(firstAnyBitValue, secondAnyIntValue);
		}
		throw createUnsupportedBinaryOperationException(Messages.ValueOperations_ShiftLeft, first, second);
	}

	private static AnyBitValue shiftLeft(final AnyBitValue first, final AnyIntValue second) {
		return switch (first) {
		case final LWordValue _ -> toLWordValue(first.longValue() << second.intValue());
		case final DWordValue _ -> toDWordValue(first.intValue() << second.intValue());
		case final WordValue _ -> toWordValue((short) (first.shortValue() << second.intValue()));
		case final ByteValue _ -> toByteValue((byte) (first.byteValue() << second.intValue()));
		case null, default ->
			throw createUnsupportedBinaryOperationException(Messages.ValueOperations_ShiftLeft, first, second);
		};
	}

	public static Value shiftRight(final Value first, final Value second) {
		if (first instanceof final AnyBitValue firstAnyBitValue
				&& second instanceof final AnyIntValue secondAnyIntValue) {
			return shiftRight(firstAnyBitValue, secondAnyIntValue);
		}
		throw createUnsupportedBinaryOperationException(Messages.ValueOperations_ShiftRight, first, second);
	}

	private static AnyBitValue shiftRight(final AnyBitValue first, final AnyIntValue second) {
		return switch (first) {
		case final LWordValue _ -> toLWordValue(first.longValue() >>> second.intValue());
		case final DWordValue _ -> toDWordValue(first.intValue() >>> second.intValue());
		case final WordValue _ -> toWordValue((short) (first.intValue() >>> second.intValue()));
		case final ByteValue _ -> toByteValue((byte) (first.intValue() >>> second.intValue()));
		case null, default ->
			throw createUnsupportedBinaryOperationException(Messages.ValueOperations_ShiftRight, first, second);
		};
	}

	public static Value rotateLeft(final Value first, final Value second) {
		if (first instanceof final AnyBitValue firstAnyBitValue
				&& second instanceof final AnyIntValue secondAnyIntValue) {
			return rotateLeft(firstAnyBitValue, secondAnyIntValue);
		}
		throw createUnsupportedBinaryOperationException(Messages.ValueOperations_RotateLeft, first, second);
	}

	private static AnyBitValue rotateLeft(final AnyBitValue first, final AnyIntValue second) {
		return switch (first) {
		case final LWordValue _ -> toLWordValue(Long.rotateLeft(first.longValue(), second.intValue()));
		case final DWordValue _ -> toDWordValue(Integer.rotateLeft(first.intValue(), second.intValue()));
		case final WordValue _ -> toWordValue(
				(short) ((first.intValue() << second.intValue()) | (first.intValue() >>> (16 - second.intValue()))));
		case final ByteValue _ -> toByteValue(
				(byte) ((first.intValue() << second.intValue()) | (first.intValue() >>> (8 - second.intValue()))));
		case null, default ->
			throw createUnsupportedBinaryOperationException(Messages.ValueOperations_RotateLeft, first, second);
		};
	}

	public static Value rotateRight(final Value first, final Value second) {
		if (first instanceof final AnyBitValue firstAnyBitValue
				&& second instanceof final AnyIntValue secondAnyIntValue) {
			return rotateRight(firstAnyBitValue, secondAnyIntValue);
		}
		throw createUnsupportedBinaryOperationException(Messages.ValueOperations_RotateRight, first, second);
	}

	private static AnyBitValue rotateRight(final AnyBitValue first, final AnyIntValue second) {
		return switch (first) {
		case final LWordValue _ -> toLWordValue(Long.rotateRight(first.longValue(), second.intValue()));
		case final DWordValue _ -> toDWordValue(Integer.rotateRight(first.intValue(), second.intValue()));
		case final WordValue _ -> toWordValue(
				(short) ((first.intValue() >>> second.intValue()) | (first.intValue() << (16 - second.intValue()))));
		case final ByteValue _ -> toByteValue(
				(byte) ((first.intValue() >>> second.intValue()) | (first.intValue() << (8 - second.intValue()))));
		case null, default ->
			throw createUnsupportedBinaryOperationException(Messages.ValueOperations_RotateRight, first, second);
		};
	}

	public static boolean equals(final Value first, final Value second) {
		if (first instanceof final AnyDurationValue firstAnyDurationValue
				&& second instanceof final AnyDurationValue secondAnyDurationValue) {
			return equals(firstAnyDurationValue, secondAnyDurationValue);
		}
		if (first instanceof final AnyNumValue firstAnyNumValue
				&& second instanceof final AnyNumValue secondAnyNumValue) {
			return equals(firstAnyNumValue, secondAnyNumValue);
		}
		if (first instanceof final AnyBitValue firstAnyBitValue
				&& second instanceof final AnyBitValue secondAnyBitValue) {
			return equals(firstAnyBitValue, secondAnyBitValue);
		}
		if (first instanceof final AnyIntValue firstAnyIntValue
				&& second instanceof final AnyBitValue secondAnyBitValue) {
			return equals(firstAnyIntValue, secondAnyBitValue);
		}
		if (first instanceof final AnyBitValue firstAnyBitValue
				&& second instanceof final AnyIntValue secondAnyIntValue) {
			return equals(firstAnyBitValue, secondAnyIntValue);
		}
		if (first instanceof final AnyCharsValue firstAnyCharsValue
				&& second instanceof final AnyCharsValue secondAnyCharsValue) {
			return equals(firstAnyCharsValue, secondAnyCharsValue);
		}
		if (first instanceof final AnyDateValue firstAnyDateValue
				&& second instanceof final AnyDateValue secondAnyDateValue) {
			return equals(firstAnyDateValue, secondAnyDateValue);
		}
		return Objects.equals(first, second);
	}

	private static boolean equals(final AnyNumValue first, final AnyNumValue second) {
		return switch (resultType(first.getType(), second.getType())) {
		case final LrealType _ -> first.doubleValue() == second.doubleValue();
		case final RealType _ -> first.floatValue() == second.floatValue();
		case final LintType _ -> first.longValue() == second.longValue();
		case final UlintType _ -> first.longValue() == second.longValue();
		case final DintType _ -> first.intValue() == second.intValue();
		case final UdintType _ -> first.intValue() == second.intValue();
		case final IntType _ -> first.shortValue() == second.shortValue();
		case final UintType _ -> first.shortValue() == second.shortValue();
		case final SintType _ -> first.byteValue() == second.byteValue();
		case final UsintType _ -> first.byteValue() == second.byteValue();
		case null, default -> first.bigDecimalValue().equals(second.bigDecimalValue());
		};
	}

	private static boolean equals(final AnyBitValue first, final AnyBitValue second) {
		return switch (resultType(first.getType(), second.getType())) {
		case final LwordType _ -> first.longValue() == second.longValue();
		case final DwordType _ -> first.intValue() == second.intValue();
		case final WordType _ -> first.shortValue() == second.shortValue();
		case final ByteType _ -> first.byteValue() == second.byteValue();
		case final BoolType _ -> first.boolValue() == second.boolValue();
		case null, default -> first.bigIntegerValue().equals(second.bigIntegerValue());
		};
	}

	private static boolean equals(final AnyIntValue first, final AnyBitValue second) {
		return first.bigIntegerValue().equals(second.bigIntegerValue());
	}

	private static boolean equals(final AnyBitValue first, final AnyIntValue second) {
		return first.bigIntegerValue().equals(second.bigIntegerValue());
	}

	private static boolean equals(final AnyDurationValue first, final AnyDurationValue second) {
		return first.longValue() == second.longValue();
	}

	private static boolean equals(final AnyCharsValue first, final AnyCharsValue second) {
		return first.stringValue().equals(second.stringValue());
	}

	private static boolean equals(final AnyDateValue first, final AnyDateValue second) {
		return first.toNanos() == second.toNanos();
	}

	public static boolean lessThan(final Value first, final Value second) {
		if (first instanceof final AnyMagnitudeValue firstAnyMagnitudeValue
				&& second instanceof final AnyMagnitudeValue secondAnyMagnitudeValue) {
			return lessThan(firstAnyMagnitudeValue, secondAnyMagnitudeValue);
		}
		if (first instanceof final AnyBitValue firstAnyBitValue
				&& second instanceof final AnyBitValue secondAnyBitValue) {
			return compareTo(firstAnyBitValue, secondAnyBitValue) < 0;
		}
		if (first instanceof final AnyCharsValue firstAnyCharsValue
				&& second instanceof final AnyCharsValue secondAnyCharsValue) {
			return compareTo(firstAnyCharsValue, secondAnyCharsValue) < 0;
		}
		if (first instanceof final AnyDateValue firstAnyDateValue
				&& second instanceof final AnyDateValue secondAnyDateValue) {
			return compareTo(firstAnyDateValue, secondAnyDateValue) < 0;
		}
		throw createUnsupportedBinaryOperationException(Messages.ValueOperations_Compare, first, second);
	}

	private static boolean lessThan(final AnyMagnitudeValue first, final AnyMagnitudeValue second) {
		return switch (resultType(first.getType(), second.getType())) {
		case final LrealType _ -> first.doubleValue() < second.doubleValue();
		case final RealType _ -> first.floatValue() < second.floatValue();
		case final LintType _ -> first.longValue() < second.longValue();
		case final DintType _ -> first.intValue() < second.intValue();
		case final IntType _ -> first.shortValue() < second.shortValue();
		case final SintType _ -> first.byteValue() < second.byteValue();
		case final UlintType _ -> Long.compareUnsigned(first.longValue(), second.longValue()) < 0;
		case final UdintType _ -> Integer.compareUnsigned(first.intValue(), second.intValue()) < 0;
		case final UintType _ -> Integer.compareUnsigned(first.intValue(), second.intValue()) < 0;
		case final UsintType _ -> Integer.compareUnsigned(first.intValue(), second.intValue()) < 0;
		case final LtimeType _ -> first.longValue() < second.longValue();
		case final TimeType _ -> first.longValue() < second.longValue();
		case null, default ->
			throw createUnsupportedBinaryOperationException(Messages.ValueOperations_Compare, first, second);
		};
	}

	public static boolean lessEquals(final Value first, final Value second) {
		if (first instanceof final AnyMagnitudeValue firstAnyMagnitudeValue
				&& second instanceof final AnyMagnitudeValue secondAnyMagnitudeValue) {
			return lessEquals(firstAnyMagnitudeValue, secondAnyMagnitudeValue);
		}
		if (first instanceof final AnyBitValue firstAnyBitValue
				&& second instanceof final AnyBitValue secondAnyBitValue) {
			return compareTo(firstAnyBitValue, secondAnyBitValue) <= 0;
		}
		if (first instanceof final AnyCharsValue firstAnyCharsValue
				&& second instanceof final AnyCharsValue secondAnyCharsValue) {
			return compareTo(firstAnyCharsValue, secondAnyCharsValue) <= 0;
		}
		if (first instanceof final AnyDateValue firstAnyDateValue
				&& second instanceof final AnyDateValue secondAnyDateValue) {
			return compareTo(firstAnyDateValue, secondAnyDateValue) <= 0;
		}
		throw createUnsupportedBinaryOperationException(Messages.ValueOperations_Compare, first, second);
	}

	private static boolean lessEquals(final AnyMagnitudeValue first, final AnyMagnitudeValue second) {
		return switch (resultType(first.getType(), second.getType())) {
		case final LrealType _ -> first.doubleValue() <= second.doubleValue();
		case final RealType _ -> first.floatValue() <= second.floatValue();
		case final LintType _ -> first.longValue() <= second.longValue();
		case final DintType _ -> first.intValue() <= second.intValue();
		case final IntType _ -> first.shortValue() <= second.shortValue();
		case final SintType _ -> first.byteValue() <= second.byteValue();
		case final UlintType _ -> Long.compareUnsigned(first.longValue(), second.longValue()) <= 0;
		case final UdintType _ -> Integer.compareUnsigned(first.intValue(), second.intValue()) <= 0;
		case final UintType _ -> Integer.compareUnsigned(first.intValue(), second.intValue()) <= 0;
		case final UsintType _ -> Integer.compareUnsigned(first.intValue(), second.intValue()) <= 0;
		case final LtimeType _ -> first.longValue() <= second.longValue();
		case final TimeType _ -> first.longValue() <= second.longValue();
		case null, default ->
			throw createUnsupportedBinaryOperationException(Messages.ValueOperations_Compare, first, second);
		};
	}

	public static boolean greaterThan(final Value first, final Value second) {
		if (first instanceof final AnyMagnitudeValue firstAnyMagnitudeValue
				&& second instanceof final AnyMagnitudeValue secondAnyMagnitudeValue) {
			return greaterThan(firstAnyMagnitudeValue, secondAnyMagnitudeValue);
		}
		if (first instanceof final AnyBitValue firstAnyBitValue
				&& second instanceof final AnyBitValue secondAnyBitValue) {
			return compareTo(firstAnyBitValue, secondAnyBitValue) > 0;
		}
		if (first instanceof final AnyCharsValue firstAnyCharsValue
				&& second instanceof final AnyCharsValue secondAnyCharsValue) {
			return compareTo(firstAnyCharsValue, secondAnyCharsValue) > 0;
		}
		if (first instanceof final AnyDateValue firstAnyDateValue
				&& second instanceof final AnyDateValue secondAnyDateValue) {
			return compareTo(firstAnyDateValue, secondAnyDateValue) > 0;
		}
		throw createUnsupportedBinaryOperationException(Messages.ValueOperations_Compare, first, second);
	}

	private static boolean greaterThan(final AnyMagnitudeValue first, final AnyMagnitudeValue second) {
		return switch (resultType(first.getType(), second.getType())) {
		case final LrealType _ -> first.doubleValue() > second.doubleValue();
		case final RealType _ -> first.floatValue() > second.floatValue();
		case final LintType _ -> first.longValue() > second.longValue();
		case final DintType _ -> first.intValue() > second.intValue();
		case final IntType _ -> first.shortValue() > second.shortValue();
		case final SintType _ -> first.byteValue() > second.byteValue();
		case final UlintType _ -> Long.compareUnsigned(first.longValue(), second.longValue()) > 0;
		case final UdintType _ -> Integer.compareUnsigned(first.intValue(), second.intValue()) > 0;
		case final UintType _ -> Integer.compareUnsigned(first.intValue(), second.intValue()) > 0;
		case final UsintType _ -> Integer.compareUnsigned(first.intValue(), second.intValue()) > 0;
		case final LtimeType _ -> first.longValue() > second.longValue();
		case final TimeType _ -> first.longValue() > second.longValue();
		case null, default ->
			throw createUnsupportedBinaryOperationException(Messages.ValueOperations_Compare, first, second);
		};
	}

	public static boolean greaterEquals(final Value first, final Value second) {
		if (first instanceof final AnyMagnitudeValue firstAnyMagnitudeValue
				&& second instanceof final AnyMagnitudeValue secondAnyMagnitudeValue) {
			return greaterEquals(firstAnyMagnitudeValue, secondAnyMagnitudeValue);
		}
		if (first instanceof final AnyBitValue firstAnyBitValue
				&& second instanceof final AnyBitValue secondAnyBitValue) {
			return compareTo(firstAnyBitValue, secondAnyBitValue) >= 0;
		}
		if (first instanceof final AnyCharsValue firstAnyCharsValue
				&& second instanceof final AnyCharsValue secondAnyCharsValue) {
			return compareTo(firstAnyCharsValue, secondAnyCharsValue) >= 0;
		}
		if (first instanceof final AnyDateValue firstAnyDateValue
				&& second instanceof final AnyDateValue secondAnyDateValue) {
			return compareTo(firstAnyDateValue, secondAnyDateValue) >= 0;
		}
		throw createUnsupportedBinaryOperationException(Messages.ValueOperations_Compare, first, second);
	}

	private static boolean greaterEquals(final AnyMagnitudeValue first, final AnyMagnitudeValue second) {
		return switch (resultType(first.getType(), second.getType())) {
		case final LrealType _ -> first.doubleValue() >= second.doubleValue();
		case final RealType _ -> first.floatValue() >= second.floatValue();
		case final LintType _ -> first.longValue() >= second.longValue();
		case final DintType _ -> first.intValue() >= second.intValue();
		case final IntType _ -> first.shortValue() >= second.shortValue();
		case final SintType _ -> first.byteValue() >= second.byteValue();
		case final UlintType _ -> Long.compareUnsigned(first.longValue(), second.longValue()) >= 0;
		case final UdintType _ -> Integer.compareUnsigned(first.intValue(), second.intValue()) >= 0;
		case final UintType _ -> Integer.compareUnsigned(first.intValue(), second.intValue()) >= 0;
		case final UsintType _ -> Integer.compareUnsigned(first.intValue(), second.intValue()) >= 0;
		case final LtimeType _ -> first.longValue() >= second.longValue();
		case final TimeType _ -> first.longValue() >= second.longValue();
		case null, default ->
			throw createUnsupportedBinaryOperationException(Messages.ValueOperations_Compare, first, second);
		};
	}

	public static int compareTo(final Value first, final Value second) {
		if (first instanceof final AnyBitValue firstAnyBitValue
				&& second instanceof final AnyBitValue secondAnyBitValue) {
			return compareTo(firstAnyBitValue, secondAnyBitValue);
		}
		if (first instanceof final AnyCharsValue firstAnyCharsValue
				&& second instanceof final AnyCharsValue secondAnyCharsValue) {
			return compareTo(firstAnyCharsValue, secondAnyCharsValue);
		}
		if (first instanceof final AnyDateValue firstAnyDateValue
				&& second instanceof final AnyDateValue secondAnyDateValue) {
			return compareTo(firstAnyDateValue, secondAnyDateValue);
		}
		if (first instanceof final AnyMagnitudeValue firstAnyMagnitudeValue
				&& second instanceof final AnyMagnitudeValue secondAnyMagnitudeValue) {
			return compareTo(firstAnyMagnitudeValue, secondAnyMagnitudeValue);
		}
		throw createUnsupportedBinaryOperationException(Messages.ValueOperations_Compare, first, second);
	}

	private static int compareTo(final AnyMagnitudeValue first, final AnyMagnitudeValue second) {
		return switch (resultType(first.getType(), second.getType())) {
		case final LrealType _ -> Double.compare(first.doubleValue(), second.doubleValue());
		case final RealType _ -> Float.compare(first.floatValue(), second.floatValue());
		case final LintType _ -> Long.compare(first.longValue(), second.longValue());
		case final DintType _ -> Integer.compare(first.intValue(), second.intValue());
		case final IntType _ -> Short.compare(first.shortValue(), second.shortValue());
		case final SintType _ -> Byte.compare(first.byteValue(), second.byteValue());
		case final UlintType _ -> Long.compareUnsigned(first.longValue(), second.longValue());
		case final UdintType _ -> Integer.compareUnsigned(first.intValue(), second.intValue());
		case final UintType _ -> Integer.compareUnsigned(first.intValue(), second.intValue());
		case final UsintType _ -> Integer.compareUnsigned(first.intValue(), second.intValue());
		case final LtimeType _ -> Long.compare(first.longValue(), second.longValue());
		case final TimeType _ -> Long.compare(first.longValue(), second.longValue());
		case null, default ->
			throw createUnsupportedBinaryOperationException(Messages.ValueOperations_Compare, first, second);
		};
	}

	private static int compareTo(final AnyBitValue first, final AnyBitValue second) {
		return switch (resultType(first.getType(), second.getType())) {
		case final LwordType _ -> Long.compareUnsigned(first.longValue(), second.longValue());
		case final DwordType _ -> Integer.compareUnsigned(first.intValue(), second.intValue());
		case final WordType _ -> Integer.compareUnsigned(first.intValue(), second.intValue());
		case final ByteType _ -> Integer.compareUnsigned(first.intValue(), second.intValue());
		case final BoolType _ -> Boolean.compare(first.boolValue(), second.boolValue());
		case null, default ->
			throw createUnsupportedBinaryOperationException(Messages.ValueOperations_Compare, first, second);
		};
	}

	private static int compareTo(final AnyCharsValue first, final AnyCharsValue second) {
		return first.stringValue().compareTo(second.stringValue());
	}

	private static int compareTo(final AnyDateValue first, final AnyDateValue second) {
		return Long.compare(first.toNanos(), second.toNanos());
	}

	public static Value partial(final Value value, final DataType type, final int index) {
		if (value instanceof final ByteValue byteValue) {
			return partial(byteValue, type, index);
		}
		if (value instanceof final DWordValue dwordValue) {
			return partial(dwordValue, type, index);
		}
		if (value instanceof final LWordValue lwordValue) {
			return partial(lwordValue, type, index);
		}
		if (value instanceof final WordValue wordValue) {
			return partial(wordValue, type, index);
		}
		throw createUnsupportedPartialOperationException(value, type);
	}

	private static Value partial(final ByteValue value, final DataType type, final int index) {
		return switch (type) {
		case final BoolType _ -> toBoolValue(((value.byteValue() >>> index) & 0x1) != 0);
		case null, default -> throw createUnsupportedPartialOperationException(value, type);
		};
	}

	private static Value partial(final WordValue value, final DataType type, final int index) {
		return switch (type) {
		case final BoolType _ -> toBoolValue(((value.shortValue() >>> index) & 0x1) != 0);
		case final ByteType _ -> toByteValue((byte) (value.shortValue() >>> (index * 8)));
		case null, default -> throw createUnsupportedPartialOperationException(value, type);
		};
	}

	private static Value partial(final DWordValue value, final DataType type, final int index) {
		return switch (type) {
		case final BoolType _ -> toBoolValue(((value.intValue() >>> index) & 0x1) != 0);
		case final ByteType _ -> toByteValue((byte) (value.intValue() >>> (index * 8)));
		case final WordType _ -> toWordValue((short) (value.intValue() >>> (index * 16)));
		case null, default -> throw createUnsupportedPartialOperationException(value, type);
		};
	}

	private static Value partial(final LWordValue value, final DataType type, final int index) {
		return switch (type) {
		case final BoolType _ -> toBoolValue(((value.longValue() >>> index) & 0x1) != 0);
		case final ByteType _ -> toByteValue((byte) (value.longValue() >>> (index * 8)));
		case final WordType _ -> toWordValue((short) (value.longValue() >>> (index * 16)));
		case final DwordType _ -> toDWordValue((int) (value.longValue() >>> (index * 32)));
		case null, default -> throw createUnsupportedPartialOperationException(value, type);
		};
	}

	public static Value partial(final Value value, final DataType type, final int index, final Value partial) {
		if (value.getType() instanceof final AnyBitType valueType && type instanceof final AnyBitType partialType
				&& partial instanceof final AnyBitValue partialValue && type != value.getType()
				&& valueType.isAssignableFrom(partialType)) {
			if (value instanceof final ByteValue byteValue) {
				return partial(byteValue, partialType, index, partialValue);
			}
			if (value instanceof final DWordValue dwordValue) {
				return partial(dwordValue, partialType, index, partialValue);
			}
			if (value instanceof final LWordValue lwordValue) {
				return partial(lwordValue, partialType, index, partialValue);
			}
			if (value instanceof final WordValue wordValue) {
				return partial(wordValue, partialType, index, partialValue);
			}
		}
		throw createUnsupportedPartialOperationException(value, type);
	}

	private static Value partial(final ByteValue value, final AnyBitType type, final int index,
			final AnyBitValue partial) {
		return toByteValue(combine(value.byteValue(), (byte) (partial.byteValue() << (index * type.getBitSize())),
				(byte) (bitMask(type) << (index * type.getBitSize()))));
	}

	private static Value partial(final WordValue value, final AnyBitType type, final int index,
			final AnyBitValue partial) {
		return toWordValue(combine(value.shortValue(), (short) (partial.shortValue() << (index * type.getBitSize())),
				(short) (bitMask(type) << (index * type.getBitSize()))));
	}

	private static Value partial(final DWordValue value, final AnyBitType type, final int index,
			final AnyBitValue partial) {
		return toDWordValue(combine(value.intValue(), partial.intValue() << (index * type.getBitSize()),
				(int) (bitMask(type) << (index * type.getBitSize()))));
	}

	private static Value partial(final LWordValue value, final AnyBitType type, final int index,
			final AnyBitValue partial) {
		return toLWordValue(combine(value.longValue(), partial.longValue() << (index * type.getBitSize()),
				bitMask(type) << (index * type.getBitSize())));
	}

	private static byte combine(final byte value, final byte partial, final byte mask) {
		return (byte) (value ^ ((value ^ partial) & mask)); // (value & ~mask) | (partial & mask)
	}

	private static short combine(final short value, final short partial, final short mask) {
		return (short) (value ^ ((value ^ partial) & mask)); // (value & ~mask) | (partial & mask)
	}

	private static int combine(final int value, final int partial, final int mask) {
		return value ^ ((value ^ partial) & mask); // (value & ~mask) | (partial & mask)
	}

	private static long combine(final long value, final long partial, final long mask) {
		return value ^ ((value ^ partial) & mask); // (value & ~mask) | (partial & mask)
	}

	public static long bitMask(final AnyBitType type) {
		return 0xffffffffffffffffL >>> (64 - type.getBitSize());
	}

	public static Value defaultValue(final LibraryElement type) {
		return switch (type) { // NOSONAR
		case null -> null;
		case final LrealType _ -> LRealValue.DEFAULT;
		case final RealType _ -> RealValue.DEFAULT;
		case final LintType _ -> LIntValue.DEFAULT;
		case final DintType _ -> DIntValue.DEFAULT;
		case final IntType _ -> IntValue.DEFAULT;
		case final SintType _ -> SIntValue.DEFAULT;
		case final UlintType _ -> ULIntValue.DEFAULT;
		case final UdintType _ -> UDIntValue.DEFAULT;
		case final UintType _ -> UIntValue.DEFAULT;
		case final UsintType _ -> USIntValue.DEFAULT;
		case final LtimeType _ -> LTimeValue.DEFAULT;
		case final TimeType _ -> TimeValue.DEFAULT;
		case final LwordType _ -> LWordValue.DEFAULT;
		case final DwordType _ -> DWordValue.DEFAULT;
		case final WordType _ -> WordValue.DEFAULT;
		case final ByteType _ -> ByteValue.DEFAULT;
		case final BoolType _ -> BoolValue.DEFAULT;
		case final WstringType _ -> WStringValue.DEFAULT;
		case final StringType _ -> StringValue.DEFAULT;
		case final WcharType _ -> WCharValue.DEFAULT;
		case final CharType _ -> CharValue.DEFAULT;
		case final LdtType _ -> LDateAndTimeValue.DEFAULT;
		case final DateAndTimeType _ -> DateAndTimeValue.DEFAULT;
		case final LtodType _ -> LTimeOfDayValue.DEFAULT;
		case final TimeOfDayType _ -> TimeOfDayValue.DEFAULT;
		case final LdateType _ -> LDateValue.DEFAULT;
		case final DateType _ -> DateValue.DEFAULT;
		case final AnyDateType _ -> LDateAndTimeValue.DEFAULT;
		case final AnyCharType _ -> WCharValue.DEFAULT;
		case final AnyStringType _ -> WStringValue.DEFAULT;
		case final AnyCharsType _ -> WStringValue.DEFAULT;
		case final AnyBitType _ -> DWordValue.DEFAULT;
		case final AnyDurationType _ -> LTimeValue.DEFAULT;
		case final AnyRealType _ -> LRealValue.DEFAULT;
		case final AnyUnsignedType _ -> UDIntValue.DEFAULT;
		case final AnySignedType _ -> DIntValue.DEFAULT;
		case final AnyIntType _ -> DIntValue.DEFAULT;
		case final AnyNumType _ -> DIntValue.DEFAULT;
		case final AnyMagnitudeType _ -> DIntValue.DEFAULT;
		case final AnyElementaryType _ -> DIntValue.DEFAULT;
		case final ArrayType arrayType -> new ArrayValue(arrayType);
		case final StructuredType structType -> new StructValue(structType);
		case final EnumeratedType enumeratedType -> new EnumValue(enumeratedType);
		case final AnyType _ -> DIntValue.DEFAULT;
		case final FBType fbType -> new FBValue(fbType);
		default -> throw createUnsupportedTypeException(type);
		};
	}

	public static Value castValue(final Value value, final LibraryElement type) {
		if (value == null) {
			return null;
		}
		if (type == null) {
			return value;
		}
		if (value.getType() == type) {
			return value;
		}
		return switch (value) {
		case final BoolValue boolValue -> castValue(boolValue, type);
		case final AnyBitValue anyBitValue -> castValue(anyBitValue, type);
		case final AnyCharsValue anyCharsValue -> castValue(anyCharsValue, type);
		case final AnyDateValue anyDateValue -> castValue(anyDateValue, type);
		case final AnyMagnitudeValue anyMagnitudeValue -> castValue(anyMagnitudeValue, type);
		case final AnyDerivedValue anyDerivedValue -> castValue(anyDerivedValue, type);
		default -> throw createCastException(value, type);
		};
	}

	private static Value castValue(final BoolValue value, final LibraryElement type) {
		return switch (type) {
		case final LwordType _ -> toLWordValue(value.longValue());
		case final DwordType _ -> toDWordValue(value.intValue());
		case final WordType _ -> toWordValue(value.shortValue());
		case final ByteType _ -> toByteValue(value.byteValue());
		case final DataType dataType when dataType.isAssignableFrom(value.getType()) -> value;
		case null, default -> throw createCastException(value, type);
		};
	}

	private static Value castValue(final AnyMagnitudeValue value, final LibraryElement type) {
		return switch (type) {
		case final LrealType _ -> toLRealValue(value);
		case final RealType _ -> toRealValue(value);
		case final LintType _ -> toLIntValue(value);
		case final DintType _ -> toDIntValue(value);
		case final IntType _ -> toIntValue(value);
		case final SintType _ -> toSIntValue(value);
		case final UlintType _ -> toULIntValue(value);
		case final UdintType _ -> toUDIntValue(value);
		case final UintType _ -> toUIntValue(value);
		case final UsintType _ -> toUSIntValue(value);
		case final LtimeType _ -> toLTimeValue(value);
		case final TimeType _ -> toTimeValue(value);
		case final LwordType _ -> toLWordValue(value.longValue());
		case final DwordType _ -> toDWordValue(value.intValue());
		case final WordType _ -> toWordValue(value.shortValue());
		case final ByteType _ -> toByteValue(value.byteValue());
		case final DataType dataType when dataType.isAssignableFrom(value.getType()) -> value;
		case null, default -> throw createCastException(value, type);
		};
	}

	private static Value castValue(final AnyBitValue value, final LibraryElement type) {
		return switch (type) {
		case final LrealType _ -> toLRealValue(value.longValue());
		case final RealType _ -> toRealValue(value.longValue());
		case final LintType _ -> toLIntValue(value.longValue());
		case final DintType _ -> toDIntValue(value.intValue());
		case final IntType _ -> toIntValue(value.shortValue());
		case final SintType _ -> toSIntValue(value.byteValue());
		case final UlintType _ -> toULIntValue(value.longValue());
		case final UdintType _ -> toUDIntValue(value.intValue());
		case final UintType _ -> toUIntValue(value.shortValue());
		case final UsintType _ -> toUSIntValue(value.byteValue());
		case final LwordType _ -> toLWordValue(value);
		case final DwordType _ -> toDWordValue(value);
		case final WordType _ -> toWordValue(value);
		case final ByteType _ -> toByteValue(value);
		case final BoolType _ -> toBoolValue(value);
		case final DataType dataType when dataType.isAssignableFrom(value.getType()) -> value;
		case null, default -> throw createCastException(value, type);
		};
	}

	private static Value castValue(final AnyCharsValue value, final LibraryElement type) {
		return switch (type) {
		case final WstringType wstringType ->
			wstringType.isSetMaxLength() ? toWStringValue(value, wstringType.getMaxLength()) : toWStringValue(value);
		case final StringType stringType ->
			stringType.isSetMaxLength() ? toStringValue(value, stringType.getMaxLength()) : toStringValue(value);
		case final WcharType _ -> toWCharValue(value);
		case final CharType _ -> toCharValue(value);
		case final DataType dataType when dataType.isAssignableFrom(value.getType()) -> value;
		case null, default -> throw createCastException(value, type);
		};
	}

	private static Value castValue(final AnyDateValue value, final LibraryElement type) {
		return switch (type) {
		case final LdtType _ -> toLDateAndTimeValue(value);
		case final DateAndTimeType _ -> toDateAndTimeValue(value);
		case final LtodType _ -> toLTimeOfDayValue(value);
		case final TimeOfDayType _ -> toTimeOfDayValue(value);
		case final LdateType _ -> toLDateValue(value);
		case final DateType _ -> toDateValue(value);
		case final DataType dataType when dataType.isAssignableFrom(value.getType()) -> value;
		case null, default -> throw createCastException(value, type);
		};
	}

	private static Value castValue(final AnyDerivedValue value, final LibraryElement type) {
		return switch (type) {
		case final DataType dataType when dataType.isAssignableFrom(value.getType()) -> value;
		case null, default -> throw createCastException(value, type);
		};
	}

	public static Value wrapValue(final Object value, final LibraryElement type) {
		if (value == null) {
			return defaultValue(type);
		}
		if (value instanceof final TypedValue typedValue) {
			return castValue(wrapValue(typedValue.value(), typedValue.type()), type);
		}
		return switch (type) { // NOSONAR
		case null -> null;
		case final LrealType _ -> switch (value) {
		case final Number number -> toLRealValue(number);
		default -> toLRealValue(value.toString());
		};
		case final RealType _ -> switch (value) {
		case final Number number -> toRealValue(number);
		default -> toRealValue(value.toString());
		};
		case final LintType _ -> switch (value) {
		case final Number number -> toLIntValue(number);
		default -> toLIntValue(value.toString());
		};
		case final DintType _ -> switch (value) {
		case final Number number -> toDIntValue(number);
		default -> toDIntValue(value.toString());
		};
		case final IntType _ -> switch (value) {
		case final Number number -> toIntValue(number);
		default -> toIntValue(value.toString());
		};
		case final SintType _ -> switch (value) {
		case final Number number -> toSIntValue(number);
		default -> toSIntValue(value.toString());
		};
		case final UlintType _ -> switch (value) {
		case final Number number -> toULIntValue(number);
		default -> toULIntValue(value.toString());
		};
		case final UdintType _ -> switch (value) {
		case final Number number -> toUDIntValue(number);
		default -> toUDIntValue(value.toString());
		};
		case final UintType _ -> switch (value) {
		case final Number number -> toUIntValue(number);
		default -> toUIntValue(value.toString());
		};
		case final UsintType _ -> switch (value) {
		case final Number number -> toUSIntValue(number);
		default -> toUSIntValue(value.toString());
		};
		case final LtimeType _ -> switch (value) {
		case final Number number -> toLTimeValue(number);
		case final Duration duration -> toLTimeValue(duration);
		default -> toLTimeValue(value.toString());
		};
		case final TimeType _ -> switch (value) {
		case final Number number -> toTimeValue(number);
		case final Duration duration -> toTimeValue(duration);
		default -> toTimeValue(value.toString());
		};
		case final LwordType _ -> switch (value) {
		case final Number number -> toLWordValue(number);
		default -> toLWordValue(value.toString());
		};
		case final DwordType _ -> switch (value) {
		case final Number number -> toDWordValue(number);
		default -> toDWordValue(value.toString());
		};
		case final WordType _ -> switch (value) {
		case final Number number -> toWordValue(number);
		default -> toWordValue(value.toString());
		};
		case final ByteType _ -> switch (value) {
		case final Number number -> toByteValue(number);
		default -> toByteValue(value.toString());
		};
		case final BoolType _ -> toBoolValue(switch (value) {
		case final Boolean bool -> bool.booleanValue();
		case final Number number -> number.longValue() != 0;
		default -> true;
		});
		case final WstringType wstringType ->
			wstringType.isSetMaxLength() ? toWStringValue(value.toString(), wstringType.getMaxLength())
					: toWStringValue(value.toString());
		case final StringType stringType ->
			stringType.isSetMaxLength() ? toStringValue(value.toString(), stringType.getMaxLength())
					: toStringValue(value.toString());
		case final WcharType _ -> switch (value) {
		case final Character character -> toWCharValue(character.charValue());
		default -> toWCharValue(value.toString());
		};
		case final CharType _ -> switch (value) {
		case final Byte byteValue -> toCharValue(byteValue.byteValue());
		case final Character character -> toCharValue((byte) character.charValue());
		default -> toCharValue(value.toString());
		};
		case final LdtType _ -> switch (value) {
		case final Number number -> toLDateAndTimeValue(number);
		case final LocalDateTime localDateTime -> toLDateAndTimeValue(localDateTime);
		default -> toLDateAndTimeValue(value.toString());
		};
		case final DateAndTimeType _ -> switch (value) {
		case final Number number -> toDateAndTimeValue(number);
		case final LocalDateTime localDateTime -> toDateAndTimeValue(localDateTime);
		default -> toDateAndTimeValue(value.toString());
		};
		case final LtodType _ -> switch (value) {
		case final Number number -> toLTimeOfDayValue(number);
		case final LocalTime localTime -> toLTimeOfDayValue(localTime);
		default -> toLTimeOfDayValue(value.toString());
		};
		case final TimeOfDayType _ -> switch (value) {
		case final Number number -> toTimeOfDayValue(number);
		case final LocalTime localTime -> toTimeOfDayValue(localTime);
		default -> toTimeOfDayValue(value.toString());
		};
		case final LdateType _ -> switch (value) {
		case final Number number -> toLDateValue(number);
		case final LocalDate localDate -> toLDateValue(localDate);
		default -> toLDateValue(value.toString());
		};
		case final DateType _ -> switch (value) {
		case final Number number -> toDateValue(number);
		case final LocalDate localDate -> toDateValue(localDate);
		default -> toDateValue(value.toString());
		};
		case final AnyDateType _ -> switch (value) {
		case final Number number -> toLDateAndTimeValue(number);
		case final LocalDateTime localDateTime -> toLDateAndTimeValue(localDateTime);
		case final LocalTime localTime -> toLTimeOfDayValue(localTime);
		case final LocalDate localDate -> toLDateValue(localDate);
		default -> toLDateAndTimeValue(value.toString());
		};
		case final AnyCharType _ -> switch (value) {
		case final Byte byteValue -> toCharValue(byteValue.byteValue());
		case final Character character -> toWCharValue(character.charValue());
		default -> toWCharValue(value.toString());
		};
		case final AnyStringType _ -> toWStringValue(value.toString());
		case final AnyCharsType _ -> toWStringValue(value.toString());
		case final AnyBitType _ -> switch (value) {
		case final Byte byteValue -> toByteValue(byteValue);
		case final Short shortValue -> toWordValue(shortValue);
		case final Character character -> toWordValue((short) character.charValue());
		case final Integer integer -> toDWordValue(integer);
		case final Long longValue -> toLWordValue(longValue);
		case final Number number -> toDWordValue(number);
		default -> toDWordValue(value.toString());
		};
		case final AnyDurationType _ -> switch (value) {
		case final Number number -> toLTimeValue(number);
		case final Duration duration -> toLTimeValue(duration);
		default -> toLTimeValue(value.toString());
		};
		case final AnyRealType _ -> switch (value) {
		case final Float floatValue -> toRealValue(floatValue);
		case final Number number -> toLRealValue(number);
		default -> toLRealValue(value.toString());
		};
		case final AnyUnsignedType _ -> switch (value) {
		case final Byte byteValue -> toUSIntValue(byteValue);
		case final Short shortValue -> toUIntValue(shortValue);
		case final Character character -> toUIntValue((short) character.charValue());
		case final Integer integer -> toUDIntValue(integer);
		case final Long longValue -> toULIntValue(longValue);
		case final Number number -> toUDIntValue(number);
		default -> toUDIntValue(value.toString());
		};
		case final AnySignedType _ -> switch (value) {
		case final Byte byteValue -> toSIntValue(byteValue);
		case final Short shortValue -> toIntValue(shortValue);
		case final Character character -> toIntValue((short) character.charValue());
		case final Integer integer -> toDIntValue(integer);
		case final Long longValue -> toLIntValue(longValue);
		case final Number number -> toDIntValue(number);
		default -> toDIntValue(value.toString());
		};
		case final AnyIntType _ -> switch (value) {
		case final Byte byteValue -> toSIntValue(byteValue);
		case final Short shortValue -> toIntValue(shortValue);
		case final Character character -> toUIntValue((short) character.charValue());
		case final Integer integer -> toDIntValue(integer);
		case final Long longValue -> toLIntValue(longValue);
		case final Number number -> toDIntValue(number);
		default -> toDIntValue(value.toString());
		};
		// AnyNumType
		// AnyMagnitudeType
		// AnyElementaryType
		case final ArrayType arrayType -> new ArrayValue(arrayType, (List<?>) value);
		case final StructuredType structType -> new StructValue(structType, castMemberMap((Map<?, ?>) value));
		case final EnumeratedType _ -> new EnumValue((EnumeratedValue) value);
		case final AnyType _ -> switch (value) {
		case final Byte byteValue -> toSIntValue(byteValue);
		case final Short shortValue -> toIntValue(shortValue);
		case final Character character -> toUIntValue((short) character.charValue());
		case final Integer integer -> toDIntValue(integer);
		case final Long longValue -> toLIntValue(longValue);
		case final Float floatValue -> toRealValue(floatValue);
		case final Double doubleValue -> toLRealValue(doubleValue);
		case final BigDecimal bigDecimal -> toLRealValue(bigDecimal);
		case final Number number -> toDIntValue(number);
		default -> toDIntValue(value.toString());
		};
		case final FBType fbType -> new FBValue(fbType, castMemberMap((Map<?, ?>) value));
		default -> throw createUnsupportedTypeException(type);
		};
	}

	public static Value parseValue(final String value, final LibraryElement type) {
		return parseValue(value, type, null);
	}

	public static Value parseValue(final String value, final LibraryElement type, final DataTypeLibrary typeLibrary) {
		if (value == null || value.isEmpty()) {
			return defaultValue(type);
		}
		if (type instanceof final DataType dataType) {
			return wrapValue(new TypedValueConverter(dataType, typeLibrary).toTypedValue(value), type);
		}
		throw createUnsupportedTypeException(type);
	}

	public static Class<? extends Value> valueType(final LibraryElement type) {
		return switch (type) { // NOSONAR
		case final LrealType _ -> LRealValue.class;
		case final RealType _ -> RealValue.class;
		case final LintType _ -> LIntValue.class;
		case final DintType _ -> DIntValue.class;
		case final IntType _ -> IntValue.class;
		case final SintType _ -> SIntValue.class;
		case final UlintType _ -> ULIntValue.class;
		case final UdintType _ -> UDIntValue.class;
		case final UintType _ -> UIntValue.class;
		case final UsintType _ -> USIntValue.class;
		case final LtimeType _ -> LTimeValue.class;
		case final TimeType _ -> TimeValue.class;
		case final LwordType _ -> LWordValue.class;
		case final DwordType _ -> DWordValue.class;
		case final WordType _ -> WordValue.class;
		case final ByteType _ -> ByteValue.class;
		case final BoolType _ -> BoolValue.class;
		case final WstringType _ -> WStringValue.class;
		case final StringType _ -> StringValue.class;
		case final WcharType _ -> WCharValue.class;
		case final CharType _ -> CharValue.class;
		case final LdtType _ -> LDateAndTimeValue.class;
		case final DateAndTimeType _ -> DateAndTimeValue.class;
		case final LtodType _ -> LTimeOfDayValue.class;
		case final TimeOfDayType _ -> TimeOfDayValue.class;
		case final LdateType _ -> LDateValue.class;
		case final DateType _ -> DateValue.class;
		case final AnySignedType _ -> AnySignedValue.class;
		case final AnyUnsignedType _ -> AnyUnsignedValue.class;
		case final AnyIntType _ -> AnyIntValue.class;
		case final AnyRealType _ -> AnyRealValue.class;
		case final AnyNumType _ -> AnyNumValue.class;
		case final AnyDurationType _ -> AnyDurationValue.class;
		case final AnyMagnitudeType _ -> AnyMagnitudeValue.class;
		case final AnyBitType _ -> AnyBitValue.class;
		case final AnyCharType _ -> AnyCharValue.class;
		case final AnyStringType _ -> AnyStringValue.class;
		case final AnyCharsType _ -> AnyCharsValue.class;
		case final AnyDateType _ -> AnyDateValue.class;
		case final AnyElementaryType _ -> AnyElementaryValue.class;
		case final ArrayType _ -> ArrayValue.class;
		case final StructuredType _ -> StructValue.class;
		case final EnumeratedType _ -> EnumValue.class;
		case final AnyDerivedType _ -> AnyDerivedValue.class;
		case final AnyType _ -> AnyValue.class;
		case null, default -> null;
		};
	}

	public static DataType dataType(final Class<?> type) {
		if (type == null || !Value.class.isAssignableFrom(type)) {
			return null;
		}
		return switch (type.getSimpleName()) { // NOSONAR
		case "LRealValue" -> ElementaryTypes.LREAL; //$NON-NLS-1$
		case "RealValue" -> ElementaryTypes.REAL; //$NON-NLS-1$
		case "LIntValue" -> ElementaryTypes.LINT; //$NON-NLS-1$
		case "DIntValue" -> ElementaryTypes.DINT; //$NON-NLS-1$
		case "IntValue" -> ElementaryTypes.INT; //$NON-NLS-1$
		case "SIntValue" -> ElementaryTypes.SINT; //$NON-NLS-1$
		case "ULIntValue" -> ElementaryTypes.ULINT; //$NON-NLS-1$
		case "UDIntValue" -> ElementaryTypes.UDINT; //$NON-NLS-1$
		case "UIntValue" -> ElementaryTypes.UINT; //$NON-NLS-1$
		case "USIntValue" -> ElementaryTypes.USINT; //$NON-NLS-1$
		case "LTimeValue" -> ElementaryTypes.LTIME; //$NON-NLS-1$
		case "TimeValue" -> ElementaryTypes.TIME; //$NON-NLS-1$
		case "LWordValue" -> ElementaryTypes.LWORD; //$NON-NLS-1$
		case "DWordValue" -> ElementaryTypes.DWORD; //$NON-NLS-1$
		case "WordValue" -> ElementaryTypes.WORD; //$NON-NLS-1$
		case "ByteValue" -> ElementaryTypes.BYTE; //$NON-NLS-1$
		case "BoolValue" -> ElementaryTypes.BOOL; //$NON-NLS-1$
		case "WStringValue" -> ElementaryTypes.WSTRING; //$NON-NLS-1$
		case "StringValue" -> ElementaryTypes.STRING; //$NON-NLS-1$
		case "WCharValue" -> ElementaryTypes.WCHAR; //$NON-NLS-1$
		case "CharValue" -> ElementaryTypes.CHAR; //$NON-NLS-1$
		case "LDateAndTimeValue" -> ElementaryTypes.LDATE_AND_TIME; //$NON-NLS-1$
		case "DateAndTimeValue" -> ElementaryTypes.DATE_AND_TIME; //$NON-NLS-1$
		case "LTimeOfDayValue" -> ElementaryTypes.LTIME_OF_DAY; //$NON-NLS-1$
		case "TimeOfDayValue" -> ElementaryTypes.TIME_OF_DAY; //$NON-NLS-1$
		case "LDateValue" -> ElementaryTypes.LDATE; //$NON-NLS-1$
		case "DateValue" -> ElementaryTypes.DATE; //$NON-NLS-1$
		case "AnySignedValue" -> GenericTypes.ANY_SIGNED; //$NON-NLS-1$
		case "AnyUnsignedValue" -> GenericTypes.ANY_UNSIGNED; //$NON-NLS-1$
		case "AnyIntValue" -> GenericTypes.ANY_INT; //$NON-NLS-1$
		case "AnyRealValue" -> GenericTypes.ANY_REAL; //$NON-NLS-1$
		case "AnyNumValue" -> GenericTypes.ANY_NUM; //$NON-NLS-1$
		case "AnyDurationValue" -> GenericTypes.ANY_DURATION; //$NON-NLS-1$
		case "AnyMagnitudeValue" -> GenericTypes.ANY_MAGNITUDE; //$NON-NLS-1$
		case "AnyBitValue" -> GenericTypes.ANY_BIT; //$NON-NLS-1$
		case "AnyCharValue" -> GenericTypes.ANY_CHAR; //$NON-NLS-1$
		case "AnyStringValue" -> GenericTypes.ANY_STRING; //$NON-NLS-1$
		case "AnySCharsValue" -> GenericTypes.ANY_SCHARS; //$NON-NLS-1$
		case "AnyWCharsValue" -> GenericTypes.ANY_WCHARS; //$NON-NLS-1$
		case "AnyCharsValue" -> GenericTypes.ANY_CHARS; //$NON-NLS-1$
		case "AnyDateValue" -> GenericTypes.ANY_DATE; //$NON-NLS-1$
		case "AnyElementaryValue" -> GenericTypes.ANY_ELEMENTARY; //$NON-NLS-1$
		case "ArrayValue" -> GenericTypes.ANY_DERIVED; //$NON-NLS-1$
		case "StructValue" -> GenericTypes.ANY_STRUCT; //$NON-NLS-1$
		case "EnumValue" -> GenericTypes.ANY_DERIVED; //$NON-NLS-1$
		case "AnyDerivedValue" -> GenericTypes.ANY_DERIVED; //$NON-NLS-1$
		case "AnyValue" -> GenericTypes.ANY; //$NON-NLS-1$
		case null, default -> null;
		};
	}

	public static boolean asBoolean(final Value value) {
		return switch (value) {
		case final AnyBitValue anyBitValue -> anyBitValue.boolValue();
		case final AnyMagnitudeValue anyMagnitudeValue -> anyMagnitudeValue.intValue() != 0;
		case null, default -> throw createUnsupportedTypeException(value);
		};
	}

	public static int asInteger(final Value value) {
		return switch (value) {
		case final AnyMagnitudeValue anyMagnitudeValue -> anyMagnitudeValue.intValue();
		case final AnyBitValue anyBitValue -> anyBitValue.intValue();
		case null, default -> throw createUnsupportedTypeException(value);
		};
	}

	public static DataType resultType(final DataType first, final DataType second) {
		if (first instanceof AnyDurationType && second instanceof AnyNumType) {
			return first;
		}
		if (first.isAssignableFrom(second)) {
			return first;
		}
		if (second.isAssignableFrom(first)) {
			return second;
		}
		return null;
	}

	private static RuntimeException createUnsupportedUnaryOperationException(final String operation,
			final Value value) {
		return new UnsupportedOperationException(
				MessageFormat.format(Messages.ValueOperations_UnsupportedUnaryOperation, operation, typeName(value)));
	}

	private static RuntimeException createUnsupportedBinaryOperationException(final String operation, final Value first,
			final Value second) {
		return new UnsupportedOperationException(MessageFormat.format(
				Messages.ValueOperations_UnsupportedBinaryOperation, operation, typeName(first), typeName(second)));
	}

	private static RuntimeException createUnsupportedPartialOperationException(final Value value, final DataType type) {
		return new UnsupportedOperationException(MessageFormat
				.format(Messages.ValueOperations_UnsupportedPartialOperation, typeName(value), typeName(type)));
	}

	private static RuntimeException createUnsupportedTypeException(final Value value) {
		return new UnsupportedOperationException(
				MessageFormat.format(Messages.ValueOperations_UnsupportedType, typeName(value)));
	}

	private static RuntimeException createUnsupportedTypeException(final LibraryElement type) {
		return new UnsupportedOperationException(
				MessageFormat.format(Messages.ValueOperations_UnsupportedType, typeName(type)));
	}

	private static RuntimeException createCastException(final Value value, final LibraryElement type) {
		return new ClassCastException(
				MessageFormat.format(Messages.ValueOperations_UnsupportedCast, value, typeName(value), typeName(type)));
	}

	private static String typeName(final Value value) {
		return value != null ? typeName(value.getType()) : null;
	}

	private static String typeName(final LibraryElement type) {
		return PackageNameHelper.getFullTypeName(type);
	}

	@SuppressWarnings("unchecked")
	private static Map<String, ?> castMemberMap(final Map<?, ?> map) {
		map.keySet().stream().filter(Predicate.not(String.class::isInstance)).findAny().ifPresent(key -> {
			throw new ClassCastException(MessageFormat.format(Messages.ValueOperations_MemberMapCastMessage, key,
					key.getClass(), String.class));
		});
		return (Map<String, ?>) map;
	}

	private ValueOperations() {
		throw new UnsupportedOperationException();
	}
}
