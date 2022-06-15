/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
 * 				 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *   Martin Melik Merkumians - added some functions
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.eval.function;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.function.BinaryOperator;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import org.eclipse.fordiac.ide.model.eval.value.AnyBitValue;
import org.eclipse.fordiac.ide.model.eval.value.AnyDurationValue;
import org.eclipse.fordiac.ide.model.eval.value.AnyElementaryValue;
import org.eclipse.fordiac.ide.model.eval.value.AnyIntValue;
import org.eclipse.fordiac.ide.model.eval.value.AnyNumValue;
import org.eclipse.fordiac.ide.model.eval.value.AnyRealValue;
import org.eclipse.fordiac.ide.model.eval.value.AnyStringValue;
import org.eclipse.fordiac.ide.model.eval.value.AnyUnsignedValue;
import org.eclipse.fordiac.ide.model.eval.value.AnyValue;
import org.eclipse.fordiac.ide.model.eval.value.BoolValue;
import org.eclipse.fordiac.ide.model.eval.value.ByteValue;
import org.eclipse.fordiac.ide.model.eval.value.CharValue;
import org.eclipse.fordiac.ide.model.eval.value.DIntValue;
import org.eclipse.fordiac.ide.model.eval.value.DWordValue;
import org.eclipse.fordiac.ide.model.eval.value.DateAndTimeValue;
import org.eclipse.fordiac.ide.model.eval.value.DateValue;
import org.eclipse.fordiac.ide.model.eval.value.IntValue;
import org.eclipse.fordiac.ide.model.eval.value.LDateAndTimeValue;
import org.eclipse.fordiac.ide.model.eval.value.LDateValue;
import org.eclipse.fordiac.ide.model.eval.value.LIntValue;
import org.eclipse.fordiac.ide.model.eval.value.LRealValue;
import org.eclipse.fordiac.ide.model.eval.value.LTimeOfDayValue;
import org.eclipse.fordiac.ide.model.eval.value.LTimeValue;
import org.eclipse.fordiac.ide.model.eval.value.LWordValue;
import org.eclipse.fordiac.ide.model.eval.value.RealValue;
import org.eclipse.fordiac.ide.model.eval.value.SIntValue;
import org.eclipse.fordiac.ide.model.eval.value.StringValue;
import org.eclipse.fordiac.ide.model.eval.value.TimeOfDayValue;
import org.eclipse.fordiac.ide.model.eval.value.TimeValue;
import org.eclipse.fordiac.ide.model.eval.value.UDIntValue;
import org.eclipse.fordiac.ide.model.eval.value.UIntValue;
import org.eclipse.fordiac.ide.model.eval.value.ULIntValue;
import org.eclipse.fordiac.ide.model.eval.value.USIntValue;
import org.eclipse.fordiac.ide.model.eval.value.ValueOperations;
import org.eclipse.fordiac.ide.model.eval.value.WCharValue;
import org.eclipse.fordiac.ide.model.eval.value.WStringValue;
import org.eclipse.fordiac.ide.model.eval.value.WordValue;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;

@SuppressWarnings("squid:S100") // ST Name conventions must be used here
public interface StandardFunctions extends Functions {

	/* Numeric functions */
	@SuppressWarnings("unchecked")
	static <T extends AnyNumValue> T ABS(final T value) {
		return (T) ValueOperations.abs(value);
	}

	@SuppressWarnings("unchecked")
	static <T extends AnyRealValue> T SQRT(final T value) {
		return (T) ValueOperations.sqrt(value);
	}

	static <T extends AnyRealValue> T LN(final T value) {
		return apply(value, Math::log);
	}

	static <T extends AnyRealValue> T LOG(final T value) {
		return apply(value, Math::log10);
	}

	static <T extends AnyRealValue> T EXP(final T value) {
		return apply(value, Math::exp);
	}

	static <T extends AnyRealValue> T SIN(final T value) {
		return apply(value, Math::sin);
	}

	static <T extends AnyRealValue> T COS(final T value) {
		return apply(value, Math::cos);
	}

	static <T extends AnyRealValue> T TAN(final T value) {
		return apply(value, Math::tan);
	}

	static <T extends AnyRealValue> T ASIN(final T value) {
		return apply(value, Math::asin);
	}

	static <T extends AnyRealValue> T ACOS(final T value) {
		return apply(value, Math::acos);
	}

	static <T extends AnyRealValue> T ATAN(final T value) {
		return apply(value, Math::atan);
	}

	static <T extends AnyRealValue> T ATAN2(final T x, final T y) {
		return apply(x, y, Math::atan2);
	}

	@SuppressWarnings("unchecked")
	static <T extends AnyNumValue> T ADD(final T... values) {
		return Stream.of(values).reduce((a, b) -> (T) ValueOperations.add(a, b)).orElseThrow();
	}

	@SuppressWarnings("unchecked")
	static <T extends AnyNumValue> T MUL(final T... values) {
		return Stream.of(values).reduce((a, b) -> (T) ValueOperations.multiply(a, b)).orElseThrow();
	}

	@SuppressWarnings("unchecked")
	static <T extends AnyNumValue> T SUB(final T first, final T second) {
		return (T) ValueOperations.subtract(first, second);
	}

	@SuppressWarnings("unchecked")
	static <T extends AnyNumValue> T DIV(final T first, final T second) {
		return (T) ValueOperations.divideBy(first, second);
	}

	@SuppressWarnings("unchecked")
	static <T extends AnyIntValue> T MOD(final T first, final T second) {
		return (T) ValueOperations.remainderBy(first, second);
	}

	@SuppressWarnings("unchecked")
	static <T extends AnyRealValue, U extends AnyNumValue> T EXPT(final T first, final U second) {
		return (T) ValueOperations.power(first, second);
	}

	/* Bit operations */

	@SuppressWarnings("unchecked")
	static <T extends AnyBitValue, U extends AnyIntValue> T SHL(final T value, final U moveby) {
		return (T) ValueOperations.shiftLeft(value, moveby);
	}

	@SuppressWarnings("unchecked")
	static <T extends AnyBitValue, U extends AnyIntValue> T SHR(final T value, final U moveby) {
		return (T) ValueOperations.shiftRight(value, moveby);

	}

	@SuppressWarnings("unchecked")
	static <T extends AnyBitValue, U extends AnyIntValue> T ROL(final T value, final U moveby) {
		return (T) ValueOperations.rotateLeft(value, moveby);
	}

	@SuppressWarnings("unchecked")
	static <T extends AnyBitValue, U extends AnyIntValue> T ROR(final T value, final U moveby) {
		return (T) ValueOperations.rotateRight(value, moveby);
	}

	@SuppressWarnings("unchecked")
	static <T extends AnyValue> T AND(final T... values) {
		return Stream.of(values).reduce((a, b) -> (T) ValueOperations.bitwiseAnd(a, b)).orElseThrow();
	}

	@SuppressWarnings("unchecked")
	static <T extends AnyBitValue> T OR(final T... values) {
		return Stream.of(values).reduce((a, b) -> (T) ValueOperations.bitwiseOr(a, b)).orElseThrow();
	}

	@SuppressWarnings("unchecked")
	static <T extends AnyBitValue> T XOR(final T... values) {
		return Stream.of(values).reduce((a, b) -> (T) ValueOperations.bitwiseXor(a, b)).orElseThrow();
	}

	@SuppressWarnings("unchecked")
	static <T extends AnyBitValue> T NOT(final T first) {
		return (T) ValueOperations.bitwiseNot(first);
	}

	/* Selection and comparison */
	static <T extends AnyValue> T MOVE(final T value) {
		return value;
	}

	static <T extends AnyValue> T SEL(final BoolValue g, final T in0, final T in1) {
		return g.boolValue() ? in1 : in0;
	}

	@SafeVarargs
	static <T extends AnyElementaryValue> T MAX(final T... values) {
		return Stream.of(values).max(ValueOperations::compareTo).orElseThrow();
	}

	@SafeVarargs
	static <T extends AnyElementaryValue> T MIN(final T... values) {
		return Stream.of(values).min(ValueOperations::compareTo).orElseThrow();
	}

	static <T extends AnyElementaryValue> T LIMIT(final T min, final T value, final T max) {
		if (ValueOperations.operator_greaterThan(value, max)) {
			return max;
		} else if (ValueOperations.operator_lessThan(value, min)) {
			return min;
		} else {
			return value;
		}
	}

	@SafeVarargs
	static <T extends AnyIntValue, U extends AnyValue> U MUX(final T k, final U... values) {
		return values[k.intValue()];
	}

	/* Comparisons */

	@SafeVarargs
	static <T extends AnyElementaryValue> BoolValue GT(final T... values) {
		T last = null;
		for (final T element : values) {
			if (last != null && !ValueOperations.operator_greaterThan(last, element)) {
				return BoolValue.FALSE;
			}
			last = element;
		}
		return BoolValue.TRUE;
	}

	@SuppressWarnings("unchecked")
	static <T extends AnyElementaryValue> BoolValue GE(final T... values) {
		T last = null;
		for (final T element : values) {
			if (last != null && !ValueOperations.operator_greaterEqualsThan(last, element)) {
				return BoolValue.FALSE;
			}
			last = element;
		}
		return BoolValue.TRUE;
	}

	@SuppressWarnings("unchecked")
	static <T extends AnyElementaryValue> BoolValue EQ(final T... values) {
		T last = null;
		for (final T element : values) {
			if (last != null && !ValueOperations.equals(last, element)) {
				return BoolValue.FALSE;
			}
			last = element;
		}
		return BoolValue.TRUE;
	}

	@SuppressWarnings("unchecked")
	static <T extends AnyElementaryValue> BoolValue LE(final T... values) {
		T last = null;
		for (final T element : values) {
			if (last != null && !ValueOperations.operator_lessEqualsThan(last, element)) {
				return BoolValue.FALSE;
			}
			last = element;
		}
		return BoolValue.TRUE;
	}

	@SuppressWarnings("unchecked")
	static <T extends AnyElementaryValue> BoolValue LT(final T... values) {
		T last = null;
		for (final T element : values) {
			if (last != null && !ValueOperations.operator_lessThan(last, element)) {
				return BoolValue.FALSE;
			}
			last = element;
		}
		return BoolValue.TRUE;
	}

	static <T extends AnyElementaryValue> BoolValue NE(final T first, final T second) {
		return ValueOperations.equals(first, second) ? BoolValue.FALSE : BoolValue.TRUE;
	}

	/* ANY_CHARS functions */
	static <T extends AnyStringValue> ULIntValue LEN(final T string) {
		return ULIntValue.toULIntValue(string.length());
	}

	static <T extends AnyStringValue, U extends AnyIntValue> T LEFT(final T string, final U length) {
		return apply(string, value -> value.substring(0, length.intValue()));
	}

	static <T extends AnyStringValue, U extends AnyIntValue> T RIGHT(final T string, final U length) {
		return apply(string, value -> value.substring(value.length() - length.intValue()));
	}

	static <T extends AnyStringValue, U extends AnyIntValue, V extends AnyIntValue> T MID(final T string,
			final U length, final V position) {
		return apply(string,
				value -> value.substring(position.intValue() - 1, position.intValue() + length.intValue() - 1));
	}

	@SafeVarargs
	static <T extends AnyStringValue> T CONCAT(final T... strings) {
		return Stream.of(strings).reduce((value1, value2) -> apply(value1, value2, String::concat)).orElseThrow();
	}

	static <T extends AnyStringValue, U extends AnyIntValue> T INSERT(final T first, final T second, final U position) {
		return apply(first, value -> value.substring(0, position.intValue()).concat(second.stringValue())
				.concat(value.substring(position.intValue())));
	}

	static <T extends AnyStringValue, U extends AnyIntValue, V extends AnyIntValue> T DELETE(final T string,
			final U length, final V position) {
		return apply(string, value -> value.substring(0, position.intValue() - 1)
				.concat(value.substring(position.intValue() + length.intValue() - 1)));
	}

	static <T extends AnyStringValue, U extends AnyIntValue, V extends AnyIntValue> T REPLACE(final T first,
			final T second, final U length, final V position) {
		return apply(first, value -> value.substring(0, position.intValue() - 1).concat(second.stringValue())
				.concat(value.substring(position.intValue() + length.intValue() - 1)));
	}

	static <T extends AnyStringValue> ULIntValue FIND(final T first, final T second) {
		return ULIntValue.toULIntValue(first.stringValue().indexOf(second.stringValue()) + 1L);
	}

	/* Numeric functions for time and date */

	@SuppressWarnings("unchecked")
	static <T extends AnyDurationValue> T ADD(final T first, final T second) {
		return (T) ValueOperations.add(first, second);
	}

	static TimeValue ADD_TIME(final TimeValue first, final TimeValue second) {
		return ADD(first, second);
	}

	static LTimeValue ADD_LTIME(final LTimeValue first, final LTimeValue second) {
		return ADD(first, second);
	}

	static TimeOfDayValue ADD(final TimeOfDayValue first, final TimeValue second) {
		return TimeOfDayValue.toTimeOfDayValue(first.toNanos() + second.longValue());
	}

	static LTimeOfDayValue ADD(final LTimeOfDayValue first, final LTimeValue second) {
		return LTimeOfDayValue.toLTimeOfDayValue(first.toNanos() + second.longValue());
	}

	static TimeOfDayValue ADD_TOD_TIME(final TimeOfDayValue first, final TimeValue second) {
		return ADD(first, second);
	}

	static LTimeOfDayValue ADD_LTOD_LTIME(final LTimeOfDayValue first, final LTimeValue second) {
		return ADD(first, second);
	}

	static DateAndTimeValue ADD(final DateAndTimeValue first, final TimeValue second) {
		return DateAndTimeValue.toDateAndTimeValue(first.toNanos() + second.longValue());
	}

	static LDateAndTimeValue ADD(final LDateAndTimeValue first, final LTimeValue second) {
		return LDateAndTimeValue.toLDateAndTimeValue(first.toNanos() + second.longValue());
	}

	static DateAndTimeValue ADD_DT_TIME(final DateAndTimeValue first, final TimeValue second) {
		return ADD(first, second);
	}

	static LDateAndTimeValue ADD_LDT_LTIME(final LDateAndTimeValue first, final LTimeValue second) {
		return ADD(first, second);
	}

	@SuppressWarnings("unchecked")
	static <T extends AnyDurationValue> T SUB(final T first, final T second) {
		return (T) ValueOperations.subtract(first, second);
	}

	static TimeValue SUB_TIME(final TimeValue first, final TimeValue second) {
		return SUB(first, second);
	}

	static LTimeValue SUB_LTIME(final LTimeValue first, final LTimeValue second) {
		return SUB(first, second);
	}

	static TimeValue SUB(final DateValue first, final DateValue second) {
		return TimeValue.toTimeValue(first.toNanos() - second.toNanos());
	}

	static LTimeValue SUB(final LDateValue first, final LDateValue second) {
		return LTimeValue.toLTimeValue(first.toNanos() - second.toNanos());
	}

	static TimeValue SUB_DATE_DATE(final DateValue first, final DateValue second) {
		return SUB(first, second);
	}

	static LTimeValue SUB_LDATE_LDATE(final LDateValue first, final LDateValue second) {
		return SUB(first, second);
	}

	static TimeOfDayValue SUB(final TimeOfDayValue first, final TimeValue second) {
		return TimeOfDayValue.toTimeOfDayValue(first.toNanos() - second.longValue());
	}

	static LTimeOfDayValue SUB(final LTimeOfDayValue first, final LTimeValue second) {
		return LTimeOfDayValue.toLTimeOfDayValue(first.toNanos() - second.longValue());
	}

	static TimeOfDayValue SUB_TOD_TIME(final TimeOfDayValue first, final TimeValue second) {
		return SUB(first, second);
	}

	static LTimeOfDayValue SUB_LTOD_LTIME(final LTimeOfDayValue first, final LTimeValue second) {
		return SUB(first, second);
	}

	static TimeValue SUB(final TimeOfDayValue first, final TimeOfDayValue second) {
		return TimeValue.toTimeValue(first.toNanos() - second.toNanos());
	}

	static LTimeValue SUB(final LTimeOfDayValue first, final LTimeOfDayValue second) {
		return LTimeValue.toLTimeValue(first.toNanos() - second.toNanos());
	}

	static TimeValue SUB_TOD_TOD(final TimeOfDayValue first, final TimeOfDayValue second) {
		return SUB(first, second);
	}

	static LTimeValue SUB_LTOD_LTOD(final LTimeOfDayValue first, final LTimeOfDayValue second) {
		return SUB(first, second);
	}

	static DateAndTimeValue SUB(final DateAndTimeValue first, final TimeValue second) {
		return DateAndTimeValue.toDateAndTimeValue(first.toNanos() - second.longValue());
	}

	static LDateAndTimeValue SUB(final LDateAndTimeValue first, final LTimeValue second) {
		return LDateAndTimeValue.toLDateAndTimeValue(first.toNanos() - second.longValue());
	}

	static DateAndTimeValue SUB_DT_TIME(final DateAndTimeValue first, final TimeValue second) {
		return SUB(first, second);
	}

	static LDateAndTimeValue SUB_LDT_LTIME(final LDateAndTimeValue first, final LTimeValue second) {
		return SUB(first, second);
	}

	static TimeValue SUB(final DateAndTimeValue first, final DateAndTimeValue second) {
		return TimeValue.toTimeValue(first.toNanos() - second.toNanos());
	}

	static LTimeValue SUB(final LDateAndTimeValue first, final LDateAndTimeValue second) {
		return LTimeValue.toLTimeValue(first.toNanos() - second.toNanos());
	}

	static TimeValue SUB_DT_DT(final DateAndTimeValue first, final DateAndTimeValue second) {
		return SUB(first, second);
	}

	static LTimeValue SUB_LDT_LDT(final LDateAndTimeValue first, final LDateAndTimeValue second) {
		return SUB(first, second);
	}

	@SuppressWarnings("unchecked")
	static <T extends AnyDurationValue, U extends AnyNumValue> T MUL(final T first, final U second) {
		return (T) ValueOperations.multiply(first, second);
	}

	static <U extends AnyNumValue> TimeValue MUL_TIME(final TimeValue first, final U second) {
		return MUL(first, second);
	}

	static <U extends AnyNumValue> LTimeValue MUL_LTIME(final LTimeValue first, final U second) {
		return MUL(first, second);
	}

	@SuppressWarnings("unchecked")
	static <T extends AnyDurationValue, U extends AnyNumValue> T DIV(final T first, final U second) {
		return (T) ValueOperations.divideBy(first, second);
	}

	static <U extends AnyNumValue> TimeValue DIV_TIME(final TimeValue first, final U second) {
		return DIV(first, second);
	}

	static <U extends AnyNumValue> LTimeValue DIV_LTIME(final LTimeValue first, final U second) {
		return DIV(first, second);
	}

	// Additional time functions CONCAT and SPLIT

	static DateAndTimeValue CONCAT_DATE_TOD(final DateValue first, final TimeOfDayValue second) {
		return DateAndTimeValue.toDateAndTimeValue(first.toLocalDate().atTime(second.toLocalTime()));
	}

	static LDateAndTimeValue CONCAT_DATE_LTOD(final DateValue first, final LTimeOfDayValue second) {
		return LDateAndTimeValue.toLDateAndTimeValue(first.toLocalDate().atTime(second.toLocalTime()));
	}

	static <T extends AnyIntValue> DateValue CONCAT_DATE(final T year, final T month, final T day) {
		return DateValue.toDateValue(LocalDate.of(year.intValue(), month.intValue(), day.intValue()));
	}

	static <T extends AnyIntValue> TimeOfDayValue CONCAT_TOD(final T hour, final T minute, final T second,
			final T millisecond) {
		return TimeOfDayValue.toTimeOfDayValue(LocalTime.of(hour.intValue(), minute.intValue(), second.intValue(),
				millisecond.intValue() * 1_000_000));
	}

	static <T extends AnyIntValue> LTimeOfDayValue CONCAT_LTOD(final T hour, final T minute, final T second,
			final T millisecond) {
		return LTimeOfDayValue.toLTimeOfDayValue(LocalTime.of(hour.intValue(), minute.intValue(), second.intValue(),
				millisecond.intValue() * 1_000_000));
	}

	static <T extends AnyIntValue> DateAndTimeValue CONCAT_DT(final T year, final T month, final T day, final T hour,
			final T minute, final T second, final T millisecond) {
		return DateAndTimeValue.toDateAndTimeValue(LocalDateTime.of(year.intValue(), month.intValue(), day.intValue(),
				hour.intValue(), minute.intValue(), second.intValue(), millisecond.intValue() * 1_000_000));
	}

	static <T extends AnyIntValue> LDateAndTimeValue CONCAT_LDT(final T year, final T month, final T day, final T hour,
			final T minute, final T second, final T millisecond) {
		return LDateAndTimeValue.toLDateAndTimeValue(LocalDateTime.of(year.intValue(), month.intValue(), day.intValue(),
				hour.intValue(), minute.intValue(), second.intValue(), millisecond.intValue() * 1_000_000));
	}

	static <T extends AnyIntValue, U extends AnyIntValue, V extends AnyIntValue> void SPLIT_DATE(final DateValue in,
			final Variable<T> year, final Variable<U> month, final Variable<V> day) {
		final LocalDate value = in.toLocalDate();
		year.setValue(DIntValue.toDIntValue(value.getYear()));
		month.setValue(DIntValue.toDIntValue(value.getMonthValue()));
		day.setValue(DIntValue.toDIntValue(value.getDayOfMonth()));
	}

	static <T extends AnyIntValue, U extends AnyIntValue, V extends AnyIntValue, W extends AnyIntValue> void SPLIT_TOD(
			final TimeOfDayValue in, final Variable<T> hour, final Variable<U> minute, final Variable<V> second,
			final Variable<W> millisecond) {
		final LocalTime value = in.toLocalTime();
		hour.setValue(DIntValue.toDIntValue(value.getHour()));
		minute.setValue(DIntValue.toDIntValue(value.getMinute()));
		second.setValue(DIntValue.toDIntValue(value.getSecond()));
		millisecond.setValue(DIntValue.toDIntValue(value.getNano() / 1000000));
	}

	static <T extends AnyIntValue, U extends AnyIntValue, V extends AnyIntValue, W extends AnyIntValue> void SPLIT_LTOD(
			final LTimeOfDayValue in, final Variable<T> hour, final Variable<U> minute, final Variable<V> second,
			final Variable<W> millisecond) {
		final LocalTime value = in.toLocalTime();
		hour.setValue(DIntValue.toDIntValue(value.getHour()));
		minute.setValue(DIntValue.toDIntValue(value.getMinute()));
		second.setValue(DIntValue.toDIntValue(value.getSecond()));
		millisecond.setValue(DIntValue.toDIntValue(value.getNano() / 1000000));
	}

	static <T extends AnyIntValue, U extends AnyIntValue, V extends AnyIntValue, W extends AnyIntValue, X extends AnyIntValue, Y extends AnyIntValue, Z extends AnyIntValue> void SPLIT_DT(
			final DateAndTimeValue in, final Variable<T> year, final Variable<U> month, final Variable<V> day,
			final Variable<W> hour, final Variable<X> minute, final Variable<Y> second, final Variable<Z> millisecond) {
		final LocalDateTime value = in.toLocalDateTime();
		year.setValue(DIntValue.toDIntValue(value.getYear()));
		month.setValue(DIntValue.toDIntValue(value.getMonthValue()));
		day.setValue(DIntValue.toDIntValue(value.getDayOfMonth()));
		hour.setValue(DIntValue.toDIntValue(value.getHour()));
		minute.setValue(DIntValue.toDIntValue(value.getMinute()));
		second.setValue(DIntValue.toDIntValue(value.getSecond()));
		millisecond.setValue(DIntValue.toDIntValue(value.getNano() / 1000000));
	}

	static <T extends AnyIntValue, U extends AnyIntValue, V extends AnyIntValue, W extends AnyIntValue, X extends AnyIntValue, Y extends AnyIntValue, Z extends AnyIntValue> void SPLIT_LDT(
			final LDateAndTimeValue in, final Variable<T> year, final Variable<U> month, final Variable<V> day,
			final Variable<W> hour, final Variable<X> minute, final Variable<Y> second, final Variable<Z> millisecond) {
		final LocalDateTime value = in.toLocalDateTime();
		year.setValue(DIntValue.toDIntValue(value.getYear()));
		month.setValue(DIntValue.toDIntValue(value.getMonthValue()));
		day.setValue(DIntValue.toDIntValue(value.getDayOfMonth()));
		hour.setValue(DIntValue.toDIntValue(value.getHour()));
		minute.setValue(DIntValue.toDIntValue(value.getMinute()));
		second.setValue(DIntValue.toDIntValue(value.getSecond()));
		millisecond.setValue(DIntValue.toDIntValue(value.getNano() / 1000000));
	}

	static SIntValue DAY_OF_WEEK(final DateValue value) {
		return SIntValue.toSIntValue((byte) value.toLocalDate().getDayOfWeek().getValue());
	}

	static <T extends AnyValue> T TO_BIG_ENDIAN(final T value) {
		return value;
	}

	@SuppressWarnings("unchecked")
	static <T extends AnyValue> T TO_LITTLE_ENDIAN(final T value) {
		return (T) ValueOperations.reverseBytes(value);
	}

	static <T extends AnyValue> T FROM_BIG_ENDIAN(final T value) {
		return value;
	}

	@SuppressWarnings("unchecked")
	static <T extends AnyValue> T FROM_LITTLE_ENDIAN(final T value) {
		return (T) ValueOperations.reverseBytes(value);
	}

	/* Validation functions */

	static <T extends AnyRealValue> BoolValue IS_VALID(final T value) {
		return BoolValue.toBoolValue(Double.isFinite(value.doubleValue()));
	}

	static <T extends AnyBitValue> BoolValue IS_VALID_BCD(final T value) {
		for (final byte packed : value.bigIntegerValue().toByteArray()) {
			final int packedInt = Byte.toUnsignedInt(packed);
			final int upper = packedInt >>> BCD_BITS;
			final int lower = packedInt & BCD_MASK;
			if (upper > BCD_DIGIT_MAX || lower > BCD_DIGIT_MAX) {
				return BoolValue.FALSE;
			}
		}
		return BoolValue.TRUE;
	}

	/* conversion functions */

	/* LREAL_TO */
	static RealValue LREAL_TO_REAL(final LRealValue value) {
		return RealValue.toRealValue(value);
	}

	static LIntValue LREAL_TO_LINT(final LRealValue value) {
		return LIntValue.toLIntValue(value);
	}

	static DIntValue LREAL_TO_DINT(final LRealValue value) {
		return DIntValue.toDIntValue(value);
	}

	static IntValue LREAL_TO_INT(final LRealValue value) {
		return IntValue.toIntValue(value);
	}

	static SIntValue LREAL_TO_SINT(final LRealValue value) {
		return SIntValue.toSIntValue(value);
	}

	static ULIntValue LREAL_TO_ULINT(final LRealValue value) {
		return ULIntValue.toULIntValue(value);
	}

	static UDIntValue LREAL_TO_UDINT(final LRealValue value) {
		return UDIntValue.toUDIntValue(value);
	}

	static UIntValue LREAL_TO_UINT(final LRealValue value) {
		return UIntValue.toUIntValue(value);
	}

	static USIntValue LREAL_TO_USINT(final LRealValue value) {
		return USIntValue.toUSIntValue(value);
	}

	static LWordValue LREAL_TO_LWORD(final LRealValue value) {
		return LWordValue.toLWordValue(Double.doubleToRawLongBits(value.doubleValue()));
	}

	/* REAL_TO */
	static LRealValue REAL_TO_LREAL(final RealValue value) {
		return LRealValue.toLRealValue(value);
	}

	static LIntValue REAL_TO_LINT(final RealValue value) {
		return LIntValue.toLIntValue(value);
	}

	static DIntValue REAL_TO_DINT(final RealValue value) {
		return DIntValue.toDIntValue(value);
	}

	static IntValue REAL_TO_INT(final RealValue value) {
		return IntValue.toIntValue(value);
	}

	static SIntValue REAL_TO_SINT(final RealValue value) {
		return SIntValue.toSIntValue(value);
	}

	static ULIntValue REAL_TO_ULINT(final RealValue value) {
		return ULIntValue.toULIntValue(value);
	}

	static UDIntValue REAL_TO_UDINT(final RealValue value) {
		return UDIntValue.toUDIntValue(value);
	}

	static UIntValue REAL_TO_UINT(final RealValue value) {
		return UIntValue.toUIntValue(value);
	}

	static USIntValue REAL_TO_USINT(final RealValue value) {
		return USIntValue.toUSIntValue(value);
	}

	static DWordValue REAL_TO_DWORD(final RealValue value) {
		return DWordValue.toDWordValue(Float.floatToRawIntBits(value.floatValue()));
	}

	/* LINT_TO */
	static LRealValue LINT_TO_LREAL(final LIntValue value) {
		return LRealValue.toLRealValue(value);
	}

	static RealValue LINT_TO_REAL(final LIntValue value) {
		return RealValue.toRealValue(value);
	}

	static DIntValue LINT_TO_DINT(final LIntValue value) {
		return DIntValue.toDIntValue(value);
	}

	static IntValue LINT_TO_INT(final LIntValue value) {
		return IntValue.toIntValue(value);
	}

	static SIntValue LINT_TO_SINT(final LIntValue value) {
		return SIntValue.toSIntValue(value);
	}

	static ULIntValue LINT_TO_ULINT(final LIntValue value) {
		return ULIntValue.toULIntValue(value);
	}

	static UDIntValue LINT_TO_UDINT(final LIntValue value) {
		return UDIntValue.toUDIntValue(value);
	}

	static UIntValue LINT_TO_UINT(final LIntValue value) {
		return UIntValue.toUIntValue(value);
	}

	static USIntValue LINT_TO_USINT(final LIntValue value) {
		return USIntValue.toUSIntValue(value);
	}

	static LWordValue LINT_TO_LWORD(final LIntValue value) {
		return LWordValue.toLWordValue(value.longValue());
	}

	static DWordValue LINT_TO_DWORD(final LIntValue value) {
		return DWordValue.toDWordValue(value.intValue());
	}

	static WordValue LINT_TO_WORD(final LIntValue value) {
		return WordValue.toWordValue(value.shortValue());
	}

	static ByteValue LINT_TO_BYTE(final LIntValue value) {
		return ByteValue.toByteValue(value.byteValue());
	}

	/* DINT_TO */
	static RealValue DINT_TO_REAL(final DIntValue value) {
		return RealValue.toRealValue(value);
	}

	static LIntValue DINT_TO_LINT(final DIntValue value) {
		return LIntValue.toLIntValue(value);
	}

	static IntValue DINT_TO_INT(final DIntValue value) {
		return IntValue.toIntValue(value);
	}

	static SIntValue DINT_TO_SINT(final DIntValue value) {
		return SIntValue.toSIntValue(value);
	}

	static ULIntValue DINT_TO_ULINT(final DIntValue value) {
		return ULIntValue.toULIntValue(value);
	}

	static UDIntValue DINT_TO_UDINT(final DIntValue value) {
		return UDIntValue.toUDIntValue(value);
	}

	static UIntValue DINT_TO_UINT(final DIntValue value) {
		return UIntValue.toUIntValue(value);
	}

	static USIntValue DINT_TO_USINT(final DIntValue value) {
		return USIntValue.toUSIntValue(value);
	}

	static LWordValue DINT_TO_LWORD(final DIntValue value) {
		return LWordValue.toLWordValue(value.longValue());
	}

	static DWordValue DINT_TO_DWORD(final DIntValue value) {
		return DWordValue.toDWordValue(value.intValue());
	}

	static WordValue DINT_TO_WORD(final DIntValue value) {
		return WordValue.toWordValue(value.shortValue());
	}

	static ByteValue DINT_TO_BYTE(final DIntValue value) {
		return ByteValue.toByteValue(value.byteValue());
	}

	/* INT_TO */
	static LRealValue INT_TO_LREAL(final IntValue value) {
		return LRealValue.toLRealValue(value);
	}

	static RealValue INT_TO_REAL(final IntValue value) {
		return RealValue.toRealValue(value);
	}

	static LIntValue INT_TO_LINT(final IntValue value) {
		return LIntValue.toLIntValue(value);
	}

	static DIntValue INT_TO_DINT(final IntValue value) {
		return DIntValue.toDIntValue(value);
	}

	static SIntValue INT_TO_SINT(final IntValue value) {
		return SIntValue.toSIntValue(value);
	}

	static ULIntValue INT_TO_ULINT(final IntValue value) {
		return ULIntValue.toULIntValue(value);
	}

	static UDIntValue INT_TO_UDINT(final IntValue value) {
		return UDIntValue.toUDIntValue(value);
	}

	static UIntValue INT_TO_UINT(final IntValue value) {
		return UIntValue.toUIntValue(value);
	}

	static USIntValue INT_TO_USINT(final IntValue value) {
		return USIntValue.toUSIntValue(value);
	}

	static LWordValue INT_TO_LWORD(final IntValue value) {
		return LWordValue.toLWordValue(value.longValue());
	}

	static DWordValue INT_TO_DWORD(final IntValue value) {
		return DWordValue.toDWordValue(value.intValue());
	}

	static WordValue INT_TO_WORD(final IntValue value) {
		return WordValue.toWordValue(value.shortValue());
	}

	static ByteValue INT_TO_BYTE(final IntValue value) {
		return ByteValue.toByteValue(value.byteValue());
	}

	/* SINT_TO */
	static LRealValue SINT_TO_LREAL(final SIntValue value) {
		return LRealValue.toLRealValue(value);
	}

	static RealValue SINT_TO_REAL(final SIntValue value) {
		return RealValue.toRealValue(value);
	}

	static LIntValue SINT_TO_LINT(final SIntValue value) {
		return LIntValue.toLIntValue(value);
	}

	static DIntValue SINT_TO_DINT(final SIntValue value) {
		return DIntValue.toDIntValue(value);
	}

	static IntValue SINT_TO_INT(final SIntValue value) {
		return IntValue.toIntValue(value);
	}

	static ULIntValue SINT_TO_ULINT(final SIntValue value) {
		return ULIntValue.toULIntValue(value);
	}

	static UDIntValue SINT_TO_UDINT(final SIntValue value) {
		return UDIntValue.toUDIntValue(value);
	}

	static UIntValue SINT_TO_UINT(final SIntValue value) {
		return UIntValue.toUIntValue(value);
	}

	static USIntValue SINT_TO_USINT(final SIntValue value) {
		return USIntValue.toUSIntValue(value);
	}

	static LWordValue SINT_TO_LWORD(final SIntValue value) {
		return LWordValue.toLWordValue(value.longValue());
	}

	static DWordValue SINT_TO_DWORD(final SIntValue value) {
		return DWordValue.toDWordValue(value.intValue());
	}

	static WordValue SINT_TO_WORD(final SIntValue value) {
		return WordValue.toWordValue(value.shortValue());
	}

	static ByteValue SINT_TO_BYTE(final SIntValue value) {
		return ByteValue.toByteValue(value.byteValue());
	}

	/* ULINT_TO */

	static LRealValue ULINT_TO_LREAL(final ULIntValue value) {
		return LRealValue.toLRealValue(value);
	}

	static RealValue ULINT_TO_REAL(final ULIntValue value) {
		return RealValue.toRealValue(value);
	}

	static LIntValue ULINT_TO_LINT(final ULIntValue value) {
		return LIntValue.toLIntValue(value);
	}

	static DIntValue ULINT_TO_DINT(final ULIntValue value) {
		return DIntValue.toDIntValue(value);
	}

	static IntValue ULINT_TO_INT(final ULIntValue value) {
		return IntValue.toIntValue(value);
	}

	static SIntValue ULINT_TO_SINT(final ULIntValue value) {
		return SIntValue.toSIntValue(value);
	}

	static UDIntValue ULINT_TO_UDINT(final ULIntValue value) {
		return UDIntValue.toUDIntValue(value);
	}

	static UIntValue ULINT_TO_UINT(final ULIntValue value) {
		return UIntValue.toUIntValue(value);
	}

	static USIntValue ULINT_TO_USINT(final ULIntValue value) {
		return USIntValue.toUSIntValue(value);
	}

	static LWordValue ULINT_TO_LWORD(final ULIntValue value) {
		return LWordValue.toLWordValue(value.longValue());
	}

	static DWordValue ULINT_TO_DWORD(final ULIntValue value) {
		return DWordValue.toDWordValue(value.intValue());
	}

	static WordValue ULINT_TO_WORD(final ULIntValue value) {
		return WordValue.toWordValue(value.shortValue());
	}

	static ByteValue ULINT_TO_BYTE(final ULIntValue value) {
		return ByteValue.toByteValue(value.byteValue());
	}

	/* UDINT_TO */

	static LRealValue UDINT_TO_LREAL(final UDIntValue value) {
		return LRealValue.toLRealValue(value);
	}

	static RealValue UDINT_TO_REAL(final UDIntValue value) {
		return RealValue.toRealValue(value);
	}

	static LIntValue UDINT_TO_LINT(final UDIntValue value) {
		return LIntValue.toLIntValue(value);
	}

	static DIntValue UDINT_TO_DINT(final UDIntValue value) {
		return DIntValue.toDIntValue(value);
	}

	static IntValue UDINT_TO_INT(final UDIntValue value) {
		return IntValue.toIntValue(value);
	}

	static SIntValue UDINT_TO_SINT(final UDIntValue value) {
		return SIntValue.toSIntValue(value);
	}

	static ULIntValue UDINT_TO_ULINT(final UDIntValue value) {
		return ULIntValue.toULIntValue(value);
	}

	static UIntValue UDINT_TO_UINT(final UDIntValue value) {
		return UIntValue.toUIntValue(value);
	}

	static USIntValue UDINT_TO_USINT(final UDIntValue value) {
		return USIntValue.toUSIntValue(value);
	}

	static LWordValue UDINT_TO_LWORD(final UDIntValue value) {
		return LWordValue.toLWordValue(value.longValue());
	}

	static DWordValue UDINT_TO_DWORD(final UDIntValue value) {
		return DWordValue.toDWordValue(value.intValue());
	}

	static WordValue UDINT_TO_WORD(final UDIntValue value) {
		return WordValue.toWordValue(value.shortValue());
	}

	static ByteValue UDINT_TO_BYTE(final UDIntValue value) {
		return ByteValue.toByteValue(value.byteValue());
	}

	/* UINT_TO */

	static LRealValue UINT_TO_LREAL(final UIntValue value) {
		return LRealValue.toLRealValue(value);
	}

	static RealValue UINT_TO_REAL(final UIntValue value) {
		return RealValue.toRealValue(value);
	}

	static LIntValue UINT_TO_LINT(final UIntValue value) {
		return LIntValue.toLIntValue(value);
	}

	static DIntValue UINT_TO_DINT(final UIntValue value) {
		return DIntValue.toDIntValue(value);
	}

	static IntValue UINT_TO_INT(final UIntValue value) {
		return IntValue.toIntValue(value);
	}

	static SIntValue UINT_TO_SINT(final UIntValue value) {
		return SIntValue.toSIntValue(value);
	}

	static ULIntValue UINT_TO_ULINT(final UIntValue value) {
		return ULIntValue.toULIntValue(value);
	}

	static UDIntValue UINT_TO_UDINT(final UIntValue value) {
		return UDIntValue.toUDIntValue(value);
	}

	static USIntValue UINT_TO_USINT(final UIntValue value) {
		return USIntValue.toUSIntValue(value);
	}

	static LWordValue UINT_TO_LWORD(final UIntValue value) {
		return LWordValue.toLWordValue(value.longValue());
	}

	static DWordValue UINT_TO_DWORD(final UIntValue value) {
		return DWordValue.toDWordValue(value.intValue());
	}

	static WordValue UINT_TO_WORD(final UIntValue value) {
		return WordValue.toWordValue(value.shortValue());
	}

	static ByteValue UINT_TO_BYTE(final UIntValue value) {
		return ByteValue.toByteValue(value.byteValue());
	}

	/* USINT_TO */

	static LRealValue USINT_TO_LREAL(final USIntValue value) {
		return LRealValue.toLRealValue(value);
	}

	static RealValue USINT_TO_REAL(final USIntValue value) {
		return RealValue.toRealValue(value);
	}

	static LIntValue USINT_TO_LINT(final USIntValue value) {
		return LIntValue.toLIntValue(value);
	}

	static DIntValue USINT_TO_DINT(final USIntValue value) {
		return DIntValue.toDIntValue(value);
	}

	static IntValue USINT_TO_INT(final USIntValue value) {
		return IntValue.toIntValue(value);
	}

	static SIntValue USINT_TO_SINT(final USIntValue value) {
		return SIntValue.toSIntValue(value);
	}

	static ULIntValue USINT_TO_ULINT(final USIntValue value) {
		return ULIntValue.toULIntValue(value);
	}

	static UDIntValue USINT_TO_UDINT(final USIntValue value) {
		return UDIntValue.toUDIntValue(value);
	}

	static UIntValue USINT_TO_UINT(final USIntValue value) {
		return UIntValue.toUIntValue(value);
	}

	static LWordValue USINT_TO_LWORD(final USIntValue value) {
		return LWordValue.toLWordValue(value.longValue());
	}

	static DWordValue USINT_TO_DWORD(final USIntValue value) {
		return DWordValue.toDWordValue(value.intValue());
	}

	static WordValue USINT_TO_WORD(final USIntValue value) {
		return WordValue.toWordValue(value.shortValue());
	}

	static ByteValue USINT_TO_BYTE(final USIntValue value) {
		return ByteValue.toByteValue(value.byteValue());
	}

	/* LWORD_TO */
	static LRealValue LWORD_TO_LREAL(final LWordValue value) {
		return LRealValue.toLRealValue(Double.longBitsToDouble(value.longValue()));
	}

	static LIntValue LWORD_TO_LINT(final LWordValue value) {
		return LIntValue.toLIntValue(value.longValue());
	}

	static DIntValue LWORD_TO_DINT(final LWordValue value) {
		return DIntValue.toDIntValue(value.intValue());
	}

	static IntValue LWORD_TO_INT(final LWordValue value) {
		return IntValue.toIntValue(value.shortValue());
	}

	static SIntValue LWORD_TO_SINT(final LWordValue value) {
		return SIntValue.toSIntValue(value.byteValue());
	}

	static ULIntValue LWORD_TO_ULINT(final LWordValue value) {
		return ULIntValue.toULIntValue(value.longValue());
	}

	static UDIntValue LWORD_TO_UDINT(final LWordValue value) {
		return UDIntValue.toUDIntValue(value.intValue());
	}

	static UIntValue LWORD_TO_UINT(final LWordValue value) {
		return UIntValue.toUIntValue(value.shortValue());
	}

	static USIntValue LWORD_TO_USINT(final LWordValue value) {
		return USIntValue.toUSIntValue(value.byteValue());
	}

	static DWordValue LWORD_TO_DWORD(final LWordValue value) {
		return DWordValue.toDWordValue(value);
	}

	static WordValue LWORD_TO_WORD(final LWordValue value) {
		return WordValue.toWordValue(value);
	}

	static ByteValue LWORD_TO_BYTE(final LWordValue value) {
		return ByteValue.toByteValue(value);
	}

	static BoolValue LWORD_TO_BOOL(final LWordValue value) {
		return BoolValue.toBoolValue(value);
	}

	/* DWORD_TO */
	static RealValue DWORD_TO_REAL(final DWordValue value) {
		return RealValue.toRealValue(Float.intBitsToFloat(value.intValue()));
	}

	static LIntValue DWORD_TO_LINT(final DWordValue value) {
		return LIntValue.toLIntValue(value.longValue());
	}

	static DIntValue DWORD_TO_DINT(final DWordValue value) {
		return DIntValue.toDIntValue(value.intValue());
	}

	static IntValue DWORD_TO_INT(final DWordValue value) {
		return IntValue.toIntValue(value.shortValue());
	}

	static SIntValue DWORD_TO_SINT(final DWordValue value) {
		return SIntValue.toSIntValue(value.byteValue());
	}

	static ULIntValue DWORD_TO_ULINT(final DWordValue value) {
		return ULIntValue.toULIntValue(value.longValue());
	}

	static UDIntValue DWORD_TO_UDINT(final DWordValue value) {
		return UDIntValue.toUDIntValue(value.intValue());
	}

	static UIntValue DWORD_TO_UINT(final DWordValue value) {
		return UIntValue.toUIntValue(value.shortValue());
	}

	static USIntValue DWORD_TO_USINT(final DWordValue value) {
		return USIntValue.toUSIntValue(value.byteValue());
	}

	static LWordValue DWORD_TO_LWORD(final DWordValue value) {
		return LWordValue.toLWordValue(value);
	}

	static WordValue DWORD_TO_WORD(final DWordValue value) {
		return WordValue.toWordValue(value);
	}

	static ByteValue DWORD_TO_BYTE(final DWordValue value) {
		return ByteValue.toByteValue(value);
	}

	static BoolValue DWORD_TO_BOOL(final DWordValue value) {
		return BoolValue.toBoolValue(value);
	}

	/* WORD_TO */
	static LIntValue WORD_TO_LINT(final WordValue value) {
		return LIntValue.toLIntValue(value.longValue());
	}

	static DIntValue WORD_TO_DINT(final WordValue value) {
		return DIntValue.toDIntValue(value.intValue());
	}

	static IntValue WORD_TO_INT(final WordValue value) {
		return IntValue.toIntValue(value.shortValue());
	}

	static SIntValue WORD_TO_SINT(final WordValue value) {
		return SIntValue.toSIntValue(value.byteValue());
	}

	static ULIntValue WORD_TO_ULINT(final WordValue value) {
		return ULIntValue.toULIntValue(value.longValue());
	}

	static UDIntValue WORD_TO_UDINT(final WordValue value) {
		return UDIntValue.toUDIntValue(value.intValue());
	}

	static UIntValue WORD_TO_UINT(final WordValue value) {
		return UIntValue.toUIntValue(value.shortValue());
	}

	static USIntValue WORD_TO_USINT(final WordValue value) {
		return USIntValue.toUSIntValue(value.byteValue());
	}

	static LWordValue WORD_TO_LWORD(final WordValue value) {
		return LWordValue.toLWordValue(value);
	}

	static DWordValue WORD_TO_DWORD(final WordValue value) {
		return DWordValue.toDWordValue(value);
	}

	static ByteValue WORD_TO_BYTE(final WordValue value) {
		return ByteValue.toByteValue(value);
	}

	static BoolValue WORD_TO_BOOL(final WordValue value) {
		return BoolValue.toBoolValue(value);
	}

	/* BYTE_TO */
	static LIntValue BYTE_TO_LINT(final ByteValue value) {
		return LIntValue.toLIntValue(value.longValue());
	}

	static DIntValue BYTE_TO_DINT(final ByteValue value) {
		return DIntValue.toDIntValue(value.intValue());
	}

	static IntValue BYTE_TO_INT(final ByteValue value) {
		return IntValue.toIntValue(value.shortValue());
	}

	static SIntValue BYTE_TO_SINT(final ByteValue value) {
		return SIntValue.toSIntValue(value.byteValue());
	}

	static ULIntValue BYTE_TO_ULINT(final ByteValue value) {
		return ULIntValue.toULIntValue(value.longValue());
	}

	static UDIntValue BYTE_TO_UDINT(final ByteValue value) {
		return UDIntValue.toUDIntValue(value.intValue());
	}

	static UIntValue BYTE_TO_UINT(final ByteValue value) {
		return UIntValue.toUIntValue(value.shortValue());
	}

	static USIntValue BYTE_TO_USINT(final ByteValue value) {
		return USIntValue.toUSIntValue(value.byteValue());
	}

	static LWordValue BYTE_TO_LWORD(final ByteValue value) {
		return LWordValue.toLWordValue(value);
	}

	static DWordValue BYTE_TO_DWORD(final ByteValue value) {
		return DWordValue.toDWordValue(value);
	}

	static WordValue BYTE_TO_WORD(final ByteValue value) {
		return WordValue.toWordValue(value);
	}

	static BoolValue BYTE_TO_BOOL(final ByteValue value) {
		return BoolValue.toBoolValue(value);
	}

	static CharValue BYTE_TO_CHAR(final ByteValue value) {
		return CharValue.toCharValue(value.byteValue());
	}

	/* BOOL_TO */
	static LIntValue BOOL_TO_LINT(final BoolValue value) {
		return LIntValue.toLIntValue(value.longValue());
	}

	static DIntValue BOOL_TO_DINT(final BoolValue value) {
		return DIntValue.toDIntValue(value.intValue());
	}

	static IntValue BOOL_TO_INT(final BoolValue value) {
		return IntValue.toIntValue(value.shortValue());
	}

	static SIntValue BOOL_TO_SINT(final BoolValue value) {
		return SIntValue.toSIntValue(value.byteValue());
	}

	static ULIntValue BOOL_TO_ULINT(final BoolValue value) {
		return ULIntValue.toULIntValue(value.longValue());
	}

	static UDIntValue BOOL_TO_UDINT(final BoolValue value) {
		return UDIntValue.toUDIntValue(value.intValue());
	}

	static UIntValue BOOL_TO_UINT(final BoolValue value) {
		return UIntValue.toUIntValue(value.shortValue());
	}

	static USIntValue BOOL_TO_USINT(final BoolValue value) {
		return USIntValue.toUSIntValue(value.byteValue());
	}

	static LWordValue BOOL_TO_LWORD(final BoolValue value) {
		return LWordValue.toLWordValue(value);
	}

	static DWordValue BOOL_TO_DWORD(final BoolValue value) {
		return DWordValue.toDWordValue(value);
	}

	static WordValue BOOL_TO_WORD(final BoolValue value) {
		return WordValue.toWordValue(value);
	}

	static ByteValue BOOL_TO_BYTE(final BoolValue value) {
		return ByteValue.toByteValue(value);
	}

	/* CHAR_TO */

	static LWordValue CHAR_TO_LWORD(final CharValue value) {
		return LWordValue.toLWordValue(value.charValue());
	}

	static DWordValue CHAR_TO_DWORD(final CharValue value) {
		return DWordValue.toDWordValue(value.charValue());
	}

	static WordValue CHAR_TO_WORD(final CharValue value) {
		return WordValue.toWordValue((short) value.charValue());
	}

	static ByteValue CHAR_TO_BYTE(final CharValue value) {
		return ByteValue.toByteValue((byte) value.charValue());
	}

	/* WCHAR_TO */

	static LWordValue WCHAR_TO_LWORD(final WCharValue value) {
		return LWordValue.toLWordValue(value.charValue());
	}

	static DWordValue WCHAR_TO_DWORD(final WCharValue value) {
		return DWordValue.toDWordValue(value.charValue());
	}

	static WordValue WCHAR_TO_WORD(final WCharValue value) {
		return WordValue.toWordValue((short) value.charValue());
	}

	/***************************************/
	static <T extends AnyRealValue> SIntValue TRUNC_SINT(final T value) {
		return SIntValue.toSIntValue(value);
	}

	static <T extends AnyRealValue> IntValue TRUNC_INT(final T value) {
		return IntValue.toIntValue(value);
	}

	static <T extends AnyRealValue> DIntValue TRUNC_DINT(final T value) {
		return DIntValue.toDIntValue(value);
	}

	static <T extends AnyRealValue> LIntValue TRUNC_LINT(final T value) {
		return LIntValue.toLIntValue(value);
	}

	static <T extends AnyRealValue> USIntValue TRUNC_USINT(final T value) {
		return USIntValue.toUSIntValue(value);
	}

	static <T extends AnyRealValue> UIntValue TRUNC_UINT(final T value) {
		return UIntValue.toUIntValue(value);
	}

	static <T extends AnyRealValue> UDIntValue TRUNC_UDINT(final T value) {
		return UDIntValue.toUDIntValue(value);
	}

	static <T extends AnyRealValue> ULIntValue TRUNC_ULINT(final T value) {
		return ULIntValue.toULIntValue(value);
	}

	static SIntValue LREAL_TRUNC_SINT(final LRealValue value) {
		return SIntValue.toSIntValue(value);
	}

	static IntValue LREAL_TRUNC_INT(final LRealValue value) {
		return IntValue.toIntValue(value);
	}

	static DIntValue LREAL_TRUNC_DINT(final LRealValue value) {
		return DIntValue.toDIntValue(value);
	}

	static LIntValue LREAL_TRUNC_LINT(final LRealValue value) {
		return LIntValue.toLIntValue(value);
	}

	static USIntValue LREAL_TRUNC_USINT(final LRealValue value) {
		return USIntValue.toUSIntValue(value);
	}

	static UIntValue LREAL_TRUNC_UINT(final LRealValue value) {
		return UIntValue.toUIntValue(value);
	}

	static UDIntValue LREAL_TRUNC_UDINT(final LRealValue value) {
		return UDIntValue.toUDIntValue(value);
	}

	static ULIntValue LREAL_TRUNC_ULINT(final LRealValue value) {
		return ULIntValue.toULIntValue(value);
	}

	static SIntValue REAL_TRUNC_SINT(final RealValue value) {
		return SIntValue.toSIntValue(value);
	}

	static IntValue REAL_TRUNC_INT(final RealValue value) {
		return IntValue.toIntValue(value);
	}

	static DIntValue REAL_TRUNC_DINT(final RealValue value) {
		return DIntValue.toDIntValue(value);
	}

	static LIntValue REAL_TRUNC_LINT(final RealValue value) {
		return LIntValue.toLIntValue(value);
	}

	static USIntValue REAL_TRUNC_USINT(final RealValue value) {
		return USIntValue.toUSIntValue(value);
	}

	static UIntValue REAL_TRUNC_UINT(final RealValue value) {
		return UIntValue.toUIntValue(value);
	}

	static UDIntValue REAL_TRUNC_UDINT(final RealValue value) {
		return UDIntValue.toUDIntValue(value);
	}

	static ULIntValue REAL_TRUNC_ULINT(final RealValue value) {
		return ULIntValue.toULIntValue(value);
	}

	/***************************************/
	static <T extends AnyBitValue> USIntValue BCD_TO_USINT(final T value) {
		return USIntValue.toUSIntValue(convertFromBCD(value.bigIntegerValue()));
	}

	static <T extends AnyBitValue> UIntValue BCD_TO_UINT(final T value) {
		return UIntValue.toUIntValue(convertFromBCD(value.bigIntegerValue()));
	}

	static <T extends AnyBitValue> UDIntValue BCD_TO_UDINT(final T value) {
		return UDIntValue.toUDIntValue(convertFromBCD(value.bigIntegerValue()));
	}

	static <T extends AnyBitValue> ULIntValue BCD_TO_ULINT(final T value) {
		return ULIntValue.toULIntValue(convertFromBCD(value.bigIntegerValue()));
	}

	static <T extends AnyUnsignedValue> ByteValue TO_BCD_BYTE(final T value) {
		return ByteValue.toByteValue(convertToBCD(value.bigIntegerValue()));
	}

	static <T extends AnyUnsignedValue> WordValue TO_BCD_WORD(final T value) {
		return WordValue.toWordValue(convertToBCD(value.bigIntegerValue()));
	}

	static <T extends AnyUnsignedValue> DWordValue TO_BCD_DWORD(final T value) {
		return DWordValue.toDWordValue(convertToBCD(value.bigIntegerValue()));
	}

	static <T extends AnyUnsignedValue> LWordValue TO_BCD_LWORD(final T value) {
		return LWordValue.toLWordValue(convertToBCD(value.bigIntegerValue()));
	}

	static USIntValue BYTE_BCD_TO_USINT(final ByteValue value) {
		return BCD_TO_USINT(value);
	}

	static UIntValue BYTE_BCD_TO_UINT(final ByteValue value) {
		return BCD_TO_UINT(value);
	}

	static UDIntValue BYTE_BCD_TO_UDINT(final ByteValue value) {
		return BCD_TO_UDINT(value);
	}

	static ULIntValue BYTE_BCD_TO_ULINT(final ByteValue value) {
		return BCD_TO_ULINT(value);
	}

	static USIntValue WORD_BCD_TO_USINT(final WordValue value) {
		return BCD_TO_USINT(value);
	}

	static UIntValue WORD_BCD_TO_UINT(final WordValue value) {
		return BCD_TO_UINT(value);
	}

	static UDIntValue WORD_BCD_TO_UDINT(final WordValue value) {
		return BCD_TO_UDINT(value);
	}

	static ULIntValue WORD_BCD_TO_ULINT(final WordValue value) {
		return BCD_TO_ULINT(value);
	}

	static USIntValue DWORD_BCD_TO_USINT(final DWordValue value) {
		return BCD_TO_USINT(value);
	}

	static UIntValue DWORD_BCD_TO_UINT(final DWordValue value) {
		return BCD_TO_UINT(value);
	}

	static UDIntValue DWORD_BCD_TO_UDINT(final DWordValue value) {
		return BCD_TO_UDINT(value);
	}

	static ULIntValue DWORD_BCD_TO_ULINT(final DWordValue value) {
		return BCD_TO_ULINT(value);
	}

	static USIntValue LWORD_BCD_TO_USINT(final LWordValue value) {
		return BCD_TO_USINT(value);
	}

	static UIntValue LWORD_BCD_TO_UINT(final LWordValue value) {
		return BCD_TO_UINT(value);
	}

	static UDIntValue LWORD_BCD_TO_UDINT(final LWordValue value) {
		return BCD_TO_UDINT(value);
	}

	static ULIntValue LWORD_BCD_TO_ULINT(final LWordValue value) {
		return BCD_TO_ULINT(value);
	}

	static ByteValue USINT_TO_BCD_BYTE(final USIntValue value) {
		return TO_BCD_BYTE(value);
	}

	static ByteValue UINT_TO_BCD_BYTE(final UIntValue value) {
		return TO_BCD_BYTE(value);
	}

	static ByteValue UDINT_TO_BCD_BYTE(final UDIntValue value) {
		return TO_BCD_BYTE(value);
	}

	static ByteValue ULINT_TO_BCD_BYTE(final ULIntValue value) {
		return TO_BCD_BYTE(value);
	}

	static WordValue USINT_TO_BCD_WORD(final USIntValue value) {
		return TO_BCD_WORD(value);
	}

	static WordValue UINT_TO_BCD_WORD(final UIntValue value) {
		return TO_BCD_WORD(value);
	}

	static WordValue UDINT_TO_BCD_WORD(final UDIntValue value) {
		return TO_BCD_WORD(value);
	}

	static WordValue ULINT_TO_BCD_WORD(final ULIntValue value) {
		return TO_BCD_WORD(value);
	}

	static DWordValue USINT_TO_BCD_DWORD(final USIntValue value) {
		return TO_BCD_DWORD(value);
	}

	static DWordValue UINT_TO_BCD_DWORD(final UIntValue value) {
		return TO_BCD_DWORD(value);
	}

	static DWordValue UDINT_TO_BCD_DWORD(final UDIntValue value) {
		return TO_BCD_DWORD(value);
	}

	static DWordValue ULINT_TO_BCD_DWORD(final ULIntValue value) {
		return TO_BCD_DWORD(value);
	}

	static LWordValue USINT_TO_BCD_LWORD(final USIntValue value) {
		return TO_BCD_LWORD(value);
	}

	static LWordValue UINT_TO_BCD_LWORD(final UIntValue value) {
		return TO_BCD_LWORD(value);
	}

	static LWordValue UDINT_TO_BCD_LWORD(final UDIntValue value) {
		return TO_BCD_LWORD(value);
	}

	static LWordValue ULINT_TO_BCD_LWORD(final ULIntValue value) {
		return TO_BCD_LWORD(value);
	}

	int BCD_MASK = 0x0f;
	int BCD_BITS = 4;
	int BCD_DIGIT_MAX = 0x9;

	private static BigInteger convertToBCD(final BigInteger value) {
		// pack lower 4 bits of each digit in each byte
		var result = BigInteger.ZERO;
		final var digits = value.toString();
		for (final char digit : digits.toCharArray()) {
			result = result.shiftLeft(BCD_BITS);
			result = result.or(BigInteger.valueOf(digit & BCD_MASK));
		}
		return result;
	}

	private static BigInteger convertFromBCD(final BigInteger value) {
		if (value.signum() < 0) {
			throw new IllegalArgumentException();
		}
		// unpack lower 4 bits of each digit in each byte
		var result = BigInteger.ZERO;
		for (final byte packed : value.toByteArray()) {
			final int packedInt = Byte.toUnsignedInt(packed);
			final int upper = packedInt >>> BCD_BITS;
			final int lower = packedInt & BCD_MASK;
			if (upper > BCD_DIGIT_MAX || lower > BCD_DIGIT_MAX) {
				throw new IllegalArgumentException();
			}
			result = result.multiply(BigInteger.TEN);
			result = result.add(BigInteger.valueOf(upper));
			result = result.multiply(BigInteger.TEN);
			result = result.add(BigInteger.valueOf(lower));
		}
		return result;
	}

	static TimeValue NOW_MONOTONIC() {
		return TimeValue.toTimeValue(System.nanoTime());
	}

	/* Date and Time conversions */

	static TimeValue LTIME_TO_TIME(final LTimeValue value) {
		return TimeValue.toTimeValue(value);
	}

	static LTimeValue TIME_TO_LTIME(final TimeValue value) {
		return LTimeValue.toLTimeValue(value);
	}

	static DateAndTimeValue LDT_TO_DT(final LDateAndTimeValue value) {
		return DateAndTimeValue.toDateAndTimeValue(value);
	}

	static DateValue LDT_TO_DATE(final LDateAndTimeValue value) {
		return DateValue.toDateValue(value);
	}

	static LTimeOfDayValue LDT_TO_LTOD(final LDateAndTimeValue value) {
		return LTimeOfDayValue.toLTimeOfDayValue(value);
	}

	static TimeOfDayValue LDT_TO_TOD(final LDateAndTimeValue value) {
		return TimeOfDayValue.toTimeOfDayValue(value);
	}

	static LDateAndTimeValue DT_TO_LDT(final DateAndTimeValue value) {
		return LDateAndTimeValue.toLDateAndTimeValue(value);
	}

	static DateValue DT_TO_DATE(final DateAndTimeValue value) {
		return DateValue.toDateValue(value);
	}

	static LTimeOfDayValue DT_TO_LTOD(final DateAndTimeValue value) {
		return LTimeOfDayValue.toLTimeOfDayValue(value);
	}

	static TimeOfDayValue DT_TO_TOD(final DateAndTimeValue value) {
		return TimeOfDayValue.toTimeOfDayValue(value);
	}

	static TimeOfDayValue LTOD_TO_TOD(final LTimeOfDayValue value) {
		return TimeOfDayValue.toTimeOfDayValue(value);
	}

	static LTimeOfDayValue TOD_TO_LTOD(final TimeOfDayValue value) {
		return LTimeOfDayValue.toLTimeOfDayValue(value);
	}

	@OnlySupportedBy("4diac FORTE")
	static LIntValue TIME_IN_S_TO_LINT(final AnyDurationValue value) {
		return LIntValue.toLIntValue(value.toDuration().toSeconds());
	}

	@OnlySupportedBy("4diac FORTE")
	static LIntValue TIME_IN_MS_TO_LINT(final AnyDurationValue value) {
		return LIntValue.toLIntValue(value.toDuration().toMillis());
	}

	@OnlySupportedBy("4diac FORTE")
	static LIntValue TIME_IN_US_TO_LINT(final AnyDurationValue value) {
		return LIntValue.toLIntValue(value.toDuration().toNanos() / 1000);
	}

	@OnlySupportedBy("4diac FORTE")
	static LIntValue TIME_IN_NS_TO_LINT(final AnyDurationValue value) {
		return LIntValue.toLIntValue(value.toDuration().toNanos());
	}

	@OnlySupportedBy("4diac FORTE")
	static ULIntValue TIME_IN_S_TO_ULINT(final AnyDurationValue value) {
		return ULIntValue.toULIntValue(value.toDuration().toSeconds());
	}

	@OnlySupportedBy("4diac FORTE")
	static ULIntValue TIME_IN_MS_TO_ULINT(final AnyDurationValue value) {
		return ULIntValue.toULIntValue(value.toDuration().toMillis());
	}

	@OnlySupportedBy("4diac FORTE")
	static ULIntValue TIME_IN_US_TO_ULINT(final AnyDurationValue value) {
		return ULIntValue.toULIntValue(value.toDuration().toNanos() / 1000);
	}

	@OnlySupportedBy("4diac FORTE")
	static ULIntValue TIME_IN_NS_TO_ULINT(final AnyDurationValue value) {
		return ULIntValue.toULIntValue(value.toDuration().toNanos());
	}

	@OnlySupportedBy("4diac FORTE")
	static LRealValue TIME_IN_S_TO_LREAL(final AnyDurationValue value) {
		return LRealValue.toLRealValue(value.toDuration().toNanos() / 1000000000.0);
	}

	@OnlySupportedBy("4diac FORTE")
	static LRealValue TIME_IN_MS_TO_LREAL(final AnyDurationValue value) {
		return LRealValue.toLRealValue(value.toDuration().toNanos() / 1000000.0);
	}

	@OnlySupportedBy("4diac FORTE")
	static LRealValue TIME_IN_US_TO_LREAL(final AnyDurationValue value) {
		return LRealValue.toLRealValue(value.toDuration().toNanos() / 1000.0);
	}

	@OnlySupportedBy("4diac FORTE")
	static LRealValue TIME_IN_NS_TO_LREAL(final AnyDurationValue value) {
		return LRealValue.toLRealValue(value.toDuration().toNanos());
	}

	/* ANY_CHARS conversions */

	static StringValue WSTRING_TO_STRING(final WStringValue value) {
		return StringValue.toStringValue(value);
	}

	static WCharValue WSTRING_TO_WCHAR(final WStringValue value) {
		return WCharValue.toWCharValue(value);
	}

	static WStringValue STRING_TO_WSTRING(final StringValue value) {
		return WStringValue.toWStringValue(value);
	}

	static CharValue STRING_TO_CHAR(final StringValue value) {
		return CharValue.toCharValue(value);
	}

	static WStringValue WCHAR_TO_WSTRING(final WCharValue value) {
		return WStringValue.toWStringValue(value);
	}

	static CharValue WCHAR_TO_CHAR(final WCharValue value) {
		return CharValue.toCharValue(value);
	}

	static StringValue CHAR_TO_STRING(final CharValue value) {
		return StringValue.toStringValue(value);
	}

	static WCharValue CHAR_TO_WCHAR(final CharValue value) {
		return WCharValue.toWCharValue(value);
	}

	@SuppressWarnings("unchecked")
	private static <T extends AnyRealValue> T apply(final T value, final DoubleUnaryOperator operator) {
		if (value instanceof RealValue) {
			return (T) RealValue.toRealValue((float) operator.applyAsDouble(value.doubleValue()));
		}
		return (T) LRealValue.toLRealValue(operator.applyAsDouble(value.doubleValue()));
	}

	@SuppressWarnings("unchecked")
	private static <T extends AnyRealValue> T apply(final T value1, final T value2,
			final DoubleBinaryOperator operator) {
		if (value1 instanceof RealValue) {
			return (T) RealValue
					.toRealValue((float) operator.applyAsDouble(value1.doubleValue(), value2.doubleValue()));
		}
		return (T) LRealValue.toLRealValue(operator.applyAsDouble(value1.doubleValue(), value2.doubleValue()));
	}

	@SuppressWarnings("unchecked")
	private static <T extends AnyStringValue> T apply(final T value, final UnaryOperator<String> operator) {
		if (value instanceof StringValue) {
			return (T) StringValue.toStringValue(operator.apply(value.stringValue()));
		}
		return (T) WStringValue.toWStringValue(operator.apply(value.stringValue()));
	}

	@SuppressWarnings("unchecked")
	private static <T extends AnyStringValue> T apply(final T value1, final T value2,
			final BinaryOperator<String> operator) {
		if (value1 instanceof StringValue) {
			return (T) StringValue.toStringValue(operator.apply(value1.stringValue(), value2.stringValue()));
		}
		return (T) WStringValue.toWStringValue(operator.apply(value1.stringValue(), value2.stringValue()));
	}
}
