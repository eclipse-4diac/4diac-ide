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
package org.eclipse.fordiac.ide.model.eval.value

import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Objects
import org.eclipse.fordiac.ide.model.data.BoolType
import org.eclipse.fordiac.ide.model.data.ByteType
import org.eclipse.fordiac.ide.model.data.CharType
import org.eclipse.fordiac.ide.model.data.DataType
import org.eclipse.fordiac.ide.model.data.DateAndTimeType
import org.eclipse.fordiac.ide.model.data.DateType
import org.eclipse.fordiac.ide.model.data.DintType
import org.eclipse.fordiac.ide.model.data.DwordType
import org.eclipse.fordiac.ide.model.data.IntType
import org.eclipse.fordiac.ide.model.data.LdateType
import org.eclipse.fordiac.ide.model.data.LdtType
import org.eclipse.fordiac.ide.model.data.LintType
import org.eclipse.fordiac.ide.model.data.LrealType
import org.eclipse.fordiac.ide.model.data.LtimeType
import org.eclipse.fordiac.ide.model.data.LtodType
import org.eclipse.fordiac.ide.model.data.LwordType
import org.eclipse.fordiac.ide.model.data.RealType
import org.eclipse.fordiac.ide.model.data.SintType
import org.eclipse.fordiac.ide.model.data.StringType
import org.eclipse.fordiac.ide.model.data.TimeOfDayType
import org.eclipse.fordiac.ide.model.data.TimeType
import org.eclipse.fordiac.ide.model.data.UdintType
import org.eclipse.fordiac.ide.model.data.UintType
import org.eclipse.fordiac.ide.model.data.UlintType
import org.eclipse.fordiac.ide.model.data.UsintType
import org.eclipse.fordiac.ide.model.data.WcharType
import org.eclipse.fordiac.ide.model.data.WordType
import org.eclipse.fordiac.ide.model.data.WstringType

final class ValueOperations {

	private new() {
	}

	def static +(Value value) { value.abs }

	def static -(Value value) { value.negate }

	def static +(Value first, Value second) { first.add(second) }

	def static -(Value first, Value second) { first.subtract(second) }

	def static *(Value first, Value second) { first.multiply(second) }

	def static /(Value first, Value second) { first.divideBy(second) }

	def static %(Value first, Value second) { first.remainderBy(second) }

	def static **(Value first, Value second) { first.power(second) }

	def static ==(Value first, Value second) { equals(first, second) }

	def static !=(Value first, Value second) { !equals(first, second) }

	def static <(Value first, Value second) { first.compareTo(second) < 0 }

	def static <=(Value first, Value second) { first.compareTo(second) <= 0 }

	def static >(Value first, Value second) { first.compareTo(second) > 0 }

	def static >=(Value first, Value second) { first.compareTo(second) >= 0 }

	def static Value abs(Value value) {
		switch (value) {
			LRealValue:
				LRealValue.toLRealValue(Math.abs(value.doubleValue))
			RealValue:
				RealValue.toRealValue(Math.abs(value.floatValue))
			LIntValue:
				LIntValue.toLIntValue(Math.abs(value.longValue))
			DIntValue:
				DIntValue.toDIntValue(Math.abs(value.intValue))
			IntValue:
				IntValue.toIntValue(Math.abs(value.shortValue) as short)
			SIntValue:
				SIntValue.toSIntValue(Math.abs(value.byteValue) as byte)
			ULIntValue,
			UDIntValue,
			UIntValue,
			USIntValue:
				value
			LTimeValue:
				LTimeValue.toLTimeValue(value.longValue)
			TimeValue:
				TimeValue.toTimeValue(value.longValue)
			default:
				throw new UnsupportedOperationException('''The absolute operation is not supported for type «value.type.name»''')
		}
	}

	def static Value negate(Value value) {
		switch (value) {
			LRealValue:
				LRealValue.toLRealValue(-value.doubleValue)
			RealValue:
				RealValue.toRealValue(-value.floatValue)
			LIntValue:
				LIntValue.toLIntValue(-value.longValue)
			DIntValue:
				DIntValue.toDIntValue(-value.intValue)
			IntValue:
				IntValue.toIntValue((-value.shortValue) as short)
			SIntValue:
				SIntValue.toSIntValue((-value.byteValue) as byte)
			ULIntValue:
				ULIntValue.toULIntValue(-value.longValue)
			UDIntValue:
				UDIntValue.toUDIntValue(-value.intValue)
			UIntValue:
				UIntValue.toUIntValue((-value.shortValue) as short)
			USIntValue:
				USIntValue.toUSIntValue((-value.byteValue) as byte)
			LTimeValue:
				LTimeValue.toLTimeValue(-value.longValue)
			TimeValue:
				TimeValue.toTimeValue(-value.longValue)
			default:
				throw new UnsupportedOperationException('''The negate operation is not supported for type «value.type.name»''')
		}
	}

	def static Value bitwiseNot(Value value) {
		switch (value) {
			LWordValue:
				LWordValue.toLWordValue(value.longValue.bitwiseNot)
			DWordValue:
				DWordValue.toDWordValue(value.intValue.bitwiseNot)
			WordValue:
				WordValue.toWordValue((value.intValue.bitwiseNot) as short)
			ByteValue:
				ByteValue.toByteValue((value.intValue.bitwiseNot) as byte)
			BoolValue:
				BoolValue.toBoolValue(!value.boolValue)
			default:
				throw new UnsupportedOperationException('''The not operation is not supported for type «value.type.name»''')
		}
	}

	def static dispatch Value add(Value first, Value second) {
		throw new UnsupportedOperationException('''The add operation is not supported for types «first.type.name» and «second.type.name»''')
	}

	def static dispatch AnyMagnitudeValue add(AnyMagnitudeValue first, AnyMagnitudeValue second) {
		switch (first.type.resultType(second.type)) {
			LrealType:
				LRealValue.toLRealValue(first.doubleValue + second.doubleValue)
			RealType:
				RealValue.toRealValue(first.floatValue + second.floatValue)
			LintType:
				LIntValue.toLIntValue(first.longValue + second.longValue)
			DintType:
				DIntValue.toDIntValue(first.intValue + second.intValue)
			IntType:
				IntValue.toIntValue((first.shortValue + second.shortValue) as short)
			SintType:
				SIntValue.toSIntValue((first.byteValue + second.byteValue) as byte)
			UlintType:
				ULIntValue.toULIntValue(first.longValue + second.longValue)
			UdintType:
				UDIntValue.toUDIntValue(first.intValue + second.intValue)
			UintType:
				UIntValue.toUIntValue((first.shortValue + second.shortValue) as short)
			UsintType:
				USIntValue.toUSIntValue((first.byteValue + second.byteValue) as byte)
			LtimeType:
				LTimeValue.toLTimeValue(first.longValue + second.longValue)
			TimeType:
				TimeValue.toTimeValue(first.longValue + second.longValue)
		}
	}

	def static dispatch Value subtract(Value first, Value second) {
		throw new UnsupportedOperationException('''The subtract operation is not supported for types «first.type.name» and «second.type.name»''')
	}

	def static dispatch AnyMagnitudeValue subtract(AnyMagnitudeValue first, AnyMagnitudeValue second) {
		switch (first.type.resultType(second.type)) {
			LrealType:
				LRealValue.toLRealValue(first.doubleValue - second.doubleValue)
			RealType:
				RealValue.toRealValue(first.floatValue - second.floatValue)
			LintType:
				LIntValue.toLIntValue(first.longValue - second.longValue)
			DintType:
				DIntValue.toDIntValue(first.intValue - second.intValue)
			IntType:
				IntValue.toIntValue((first.shortValue - second.shortValue) as short)
			SintType:
				SIntValue.toSIntValue((first.byteValue - second.byteValue) as byte)
			UlintType:
				ULIntValue.toULIntValue(first.longValue - second.longValue)
			UdintType:
				UDIntValue.toUDIntValue(first.intValue - second.intValue)
			UintType:
				UIntValue.toUIntValue((first.shortValue - second.shortValue) as short)
			UsintType:
				USIntValue.toUSIntValue((first.byteValue - second.byteValue) as byte)
			LtimeType:
				LTimeValue.toLTimeValue(first.longValue - second.longValue)
			TimeType:
				TimeValue.toTimeValue(first.longValue - second.longValue)
		}
	}

	def static dispatch Value multiply(Value first, Value second) {
		throw new UnsupportedOperationException('''The multiply operation is not supported for types «first.type.name» and «second.type.name»''')
	}

	def static dispatch AnyMagnitudeValue multiply(AnyMagnitudeValue first, AnyMagnitudeValue second) {
		switch (first.type.resultType(second.type)) {
			LrealType:
				LRealValue.toLRealValue(first.doubleValue * second.doubleValue)
			RealType:
				RealValue.toRealValue(first.floatValue * second.floatValue)
			LintType:
				LIntValue.toLIntValue(first.longValue * second.longValue)
			DintType:
				DIntValue.toDIntValue(first.intValue * second.intValue)
			IntType:
				IntValue.toIntValue((first.shortValue * second.shortValue) as short)
			SintType:
				SIntValue.toSIntValue((first.byteValue * second.byteValue) as byte)
			UlintType:
				ULIntValue.toULIntValue(first.longValue * second.longValue)
			UdintType:
				UDIntValue.toUDIntValue(first.intValue * second.intValue)
			UintType:
				UIntValue.toUIntValue((first.shortValue * second.shortValue) as short)
			UsintType:
				USIntValue.toUSIntValue((first.byteValue * second.byteValue) as byte)
			LtimeType:
				LTimeValue.toLTimeValue(first.longValue * second.longValue)
			TimeType:
				TimeValue.toTimeValue(first.longValue * second.longValue)
		}
	}

	def static dispatch Value divideBy(Value first, Value second) {
		throw new UnsupportedOperationException('''The divide operation is not supported for types «first.type.name» and «second.type.name»''')
	}

	def static dispatch AnyMagnitudeValue divideBy(AnyMagnitudeValue first, AnyMagnitudeValue second) {
		switch (first.type.resultType(second.type)) {
			LrealType:
				LRealValue.toLRealValue(first.doubleValue / second.doubleValue)
			RealType:
				RealValue.toRealValue(first.floatValue / second.floatValue)
			LintType:
				LIntValue.toLIntValue(first.longValue / second.longValue)
			DintType:
				DIntValue.toDIntValue(first.intValue / second.intValue)
			IntType:
				IntValue.toIntValue((first.shortValue / second.shortValue) as short)
			SintType:
				SIntValue.toSIntValue((first.byteValue / second.byteValue) as byte)
			UlintType:
				ULIntValue.toULIntValue(Long.divideUnsigned(first.longValue, second.longValue))
			UdintType:
				UDIntValue.toUDIntValue(Integer.divideUnsigned(first.intValue, second.intValue))
			UintType:
				UIntValue.toUIntValue(Integer.divideUnsigned(first.intValue, second.intValue) as short)
			UsintType:
				USIntValue.toUSIntValue(Integer.divideUnsigned(first.intValue, second.intValue) as byte)
			LtimeType:
				LTimeValue.toLTimeValue(first.longValue / second.longValue)
			TimeType:
				TimeValue.toTimeValue(first.longValue / second.longValue)
		}
	}

	def static dispatch Value remainderBy(Value first, Value second) {
		throw new UnsupportedOperationException('''The remainder operation is not supported for types «first.type.name» and «second.type.name»''')
	}

	def static dispatch AnyMagnitudeValue remainderBy(AnyMagnitudeValue first, AnyMagnitudeValue second) {
		switch (first.type.resultType(second.type)) {
			LrealType:
				LRealValue.toLRealValue(first.doubleValue % second.doubleValue)
			RealType:
				RealValue.toRealValue(first.floatValue % second.floatValue)
			LintType:
				LIntValue.toLIntValue(first.longValue % second.longValue)
			DintType:
				DIntValue.toDIntValue(first.intValue % second.intValue)
			IntType:
				IntValue.toIntValue((first.shortValue % second.shortValue) as short)
			SintType:
				SIntValue.toSIntValue((first.byteValue % second.byteValue) as byte)
			UlintType:
				ULIntValue.toULIntValue(Long.remainderUnsigned(first.longValue, second.longValue))
			UdintType:
				UDIntValue.toUDIntValue(Integer.remainderUnsigned(first.intValue, second.intValue))
			UintType:
				UIntValue.toUIntValue(Integer.remainderUnsigned(first.intValue, second.intValue) as short)
			UsintType:
				USIntValue.toUSIntValue(Integer.remainderUnsigned(first.intValue, second.intValue) as byte)
			LtimeType:
				LTimeValue.toLTimeValue(first.longValue % second.longValue)
			TimeType:
				TimeValue.toTimeValue(first.longValue % second.longValue)
		}
	}

	def static dispatch Value power(Value first, Value second) {
		throw new UnsupportedOperationException('''The power operation is not supported for types «first.type.name» and «second.type.name»''')
	}

	def static dispatch AnyNumValue power(AnyNumValue first, AnyNumValue second) {
		switch (first) {
			RealValue:
				RealValue.toRealValue(first.floatValue ** second.floatValue)
			LRealValue:
				LRealValue.toLRealValue(first.doubleValue ** second.doubleValue)
			default:
				throw new UnsupportedOperationException('''The power operation is not supported for types «first.type.name» and «second.type.name»''')
		}
	}

	def static dispatch Value bitwiseAnd(Value first, Value second) {
		throw new UnsupportedOperationException('''The and operation is not supported for types «first.type.name» and «second.type.name»''')
	}

	def static dispatch AnyBitValue bitwiseAnd(AnyBitValue first, AnyBitValue second) {
		switch (first.type.resultType(second.type)) {
			LwordType:
				LWordValue.toLWordValue(first.longValue.bitwiseAnd(second.longValue))
			DwordType:
				DWordValue.toDWordValue(first.intValue.bitwiseAnd(second.intValue))
			WordType:
				WordValue.toWordValue(first.shortValue.bitwiseAnd(second.shortValue) as short)
			ByteType:
				ByteValue.toByteValue(first.byteValue.bitwiseAnd(second.byteValue) as byte)
			BoolType:
				BoolValue.toBoolValue(first.boolValue && second.boolValue)
			default:
				throw new UnsupportedOperationException('''The and operation is not supported for types «first.type.name» and «second.type.name»''')
		}
	}

	def static dispatch Value bitwiseOr(Value first, Value second) {
		throw new UnsupportedOperationException('''The or operation is not supported for types «first.type.name» and «second.type.name»''')
	}

	def static dispatch AnyBitValue bitwiseOr(AnyBitValue first, AnyBitValue second) {
		switch (first.type.resultType(second.type)) {
			LwordType:
				LWordValue.toLWordValue(first.longValue.bitwiseOr(second.longValue))
			DwordType:
				DWordValue.toDWordValue(first.intValue.bitwiseOr(second.intValue))
			WordType:
				WordValue.toWordValue(first.shortValue.bitwiseOr(second.shortValue) as short)
			ByteType:
				ByteValue.toByteValue(first.byteValue.bitwiseOr(second.byteValue) as byte)
			BoolType:
				BoolValue.toBoolValue(first.boolValue || second.boolValue)
			default:
				throw new UnsupportedOperationException('''The or operation is not supported for types «first.type.name» and «second.type.name»''')
		}
	}

	def static dispatch Value bitwiseXor(Value first, Value second) {
		throw new UnsupportedOperationException('''The xor operation is not supported for types «first.type.name» and «second.type.name»''')
	}

	def static dispatch AnyBitValue bitwiseXor(AnyBitValue first, AnyBitValue second) {
		switch (first.type.resultType(second.type)) {
			LwordType:
				LWordValue.toLWordValue(first.longValue.bitwiseXor(second.longValue))
			DwordType:
				DWordValue.toDWordValue(first.intValue.bitwiseXor(second.intValue))
			WordType:
				WordValue.toWordValue(first.shortValue.bitwiseXor(second.shortValue) as short)
			ByteType:
				ByteValue.toByteValue(first.byteValue.bitwiseXor(second.byteValue) as byte)
			BoolType:
				BoolValue.toBoolValue(first.boolValue.xor(second.boolValue))
			default:
				throw new UnsupportedOperationException('''The xor operation is not supported for types «first.type.name» and «second.type.name»''')
		}
	}

	def static dispatch boolean equals(Value first, Value second) {
		Objects.equals(first, second)
	}

	def static dispatch boolean equals(AnyNumValue first, AnyNumValue second) {
		switch (first.type.resultType(second.type)) {
			LrealType:
				first.doubleValue == second.doubleValue
			RealType:
				first.floatValue == second.floatValue
			LintType,
			UlintType:
				first.longValue == second.longValue
			DintType,
			UdintType:
				first.intValue == second.intValue
			IntType,
			UintType:
				first.shortValue == second.shortValue
			SintType,
			UsintType:
				first.byteValue == second.byteValue
			default:
				false
		}
	}

	def static dispatch boolean equals(AnyBitValue first, AnyBitValue second) {
		switch (first.type.resultType(second.type)) {
			LwordType:
				first.longValue == second.longValue
			DwordType:
				first.intValue == second.intValue
			WordType:
				first.shortValue == second.shortValue
			ByteType:
				first.byteValue == second.byteValue
			BoolType:
				first.boolValue == second.boolValue
			default:
				false
		}
	}

	def static dispatch boolean equals(AnyDurationValue first, AnyDurationValue second) {
		first.longValue == second.longValue
	}

	def static dispatch boolean equals(AnyCharsValue first, AnyCharsValue second) {
		first.toString == second.toString
	}

	def static dispatch boolean equals(AnyDateValue first, AnyDateValue second) {
		first.toNanos == second.toNanos
	}

	def static dispatch int compareTo(Value first, Value second) {
		throw new UnsupportedOperationException('''The compare operation is not supported for types «first.type.name» and «second.type.name»''')
	}

	def static dispatch int compareTo(AnyMagnitudeValue first, AnyMagnitudeValue second) {
		switch (first.type.resultType(second.type)) {
			LrealType:
				Double.compare(first.doubleValue, second.doubleValue)
			RealType:
				Float.compare(first.floatValue, second.floatValue)
			LintType:
				Long.compare(first.longValue, second.longValue)
			DintType:
				Integer.compare(first.intValue, second.intValue)
			IntType:
				Short.compare(first.shortValue, second.shortValue)
			SintType:
				Byte.compare(first.byteValue, second.byteValue)
			UlintType:
				Long.compareUnsigned(first.longValue, second.longValue)
			UdintType:
				Integer.compareUnsigned(first.intValue, second.intValue)
			UintType:
				Integer.compareUnsigned(first.intValue, second.intValue)
			UsintType:
				Integer.compareUnsigned(first.intValue, second.intValue)
			LtimeType,
			TimeType:
				Long.compare(first.longValue, second.longValue)
			default:
				0
		}
	}

	def static dispatch int compareTo(AnyCharsValue first, AnyCharsValue second) {
		first.toString.compareTo(second.toString)
	}

	def static dispatch int compareTo(AnyDateValue first, AnyDateValue second) {
		Long.compare(first.toNanos, second.toNanos)
	}

	def static Value defaultValue(DataType type) {
		switch (type) {
			case null:
				null
			LrealType:
				LRealValue.DEFAULT
			RealType:
				RealValue.DEFAULT
			LintType:
				LIntValue.DEFAULT
			DintType:
				DIntValue.DEFAULT
			IntType:
				IntValue.DEFAULT
			SintType:
				SIntValue.DEFAULT
			UlintType:
				ULIntValue.DEFAULT
			UdintType:
				UDIntValue.DEFAULT
			UintType:
				UIntValue.DEFAULT
			UsintType:
				USIntValue.DEFAULT
			LtimeType:
				LTimeValue.DEFAULT
			TimeType:
				TimeValue.DEFAULT
			LwordType:
				LWordValue.DEFAULT
			DwordType:
				DWordValue.DEFAULT
			WordType:
				WordValue.DEFAULT
			ByteType:
				ByteValue.DEFAULT
			BoolType:
				BoolValue.DEFAULT
			WstringType:
				WStringValue.DEFAULT
			StringType:
				StringValue.DEFAULT
			WcharType:
				WCharValue.DEFAULT
			CharType:
				CharValue.DEFAULT
			LdtType:
				LDateAndTimeValue.DEFAULT
			DateAndTimeType:
				DateAndTimeValue.DEFAULT
			LtodType:
				LTimeOfDayValue.DEFAULT
			TimeOfDayType:
				TimeOfDayValue.DEFAULT
			LdateType:
				LDateValue.DEFAULT
			DateType:
				DateValue.DEFAULT
			default:
				throw new UnsupportedOperationException('''The type «type.name» does not have a default''')
		}
	}

	def static dispatch Value castValue(Value value, DataType type) {
		if (value.type != type) {
			throw new ClassCastException('''The value «value» with type «value.type.name» cannot be cast to «type.name»''')
		}
		return value
	}

	def static dispatch Value castValue(Void value, DataType type) { null }

	def static dispatch Value castValue(BoolValue value, DataType type) {
		switch (type) {
			case null,
			case value.type:
				value
			LwordType:
				LWordValue.toLWordValue(value.longValue)
			DwordType:
				DWordValue.toDWordValue(value.intValue)
			WordType:
				WordValue.toWordValue(value.shortValue)
			ByteType:
				ByteValue.toByteValue(value.byteValue)
			default:
				throw new ClassCastException('''The value «value» with type «value.type.name» cannot be cast to «type.name»''')
		}
	}

	def static dispatch Value castValue(AnyMagnitudeValue value, DataType type) {
		switch (type) {
			case null,
			case value.type:
				value
			LrealType:
				LRealValue.toLRealValue(value)
			RealType:
				RealValue.toRealValue(value)
			LintType:
				LIntValue.toLIntValue(value)
			DintType:
				DIntValue.toDIntValue(value)
			IntType:
				IntValue.toIntValue(value)
			SintType:
				SIntValue.toSIntValue(value)
			UlintType:
				ULIntValue.toULIntValue(value)
			UdintType:
				UDIntValue.toUDIntValue(value)
			UintType:
				UIntValue.toUIntValue(value)
			UsintType:
				USIntValue.toUSIntValue(value)
			LtimeType:
				LTimeValue.toLTimeValue(value)
			TimeType:
				TimeValue.toTimeValue(value)
			LwordType:
				LWordValue.toLWordValue(value.longValue)
			DwordType:
				DWordValue.toDWordValue(value.intValue)
			WordType:
				WordValue.toWordValue(value.shortValue)
			ByteType:
				ByteValue.toByteValue(value.byteValue)
			default:
				throw new ClassCastException('''The value «value» with type «value.type.name» cannot be cast to «type.name»''')
		}
	}

	def static dispatch Value castValue(AnyBitValue value, DataType type) {
		switch (type) {
			case null,
			case value.type:
				value
			LrealType:
				LRealValue.toLRealValue(value.longValue)
			RealType:
				RealValue.toRealValue(value.longValue)
			LintType:
				LIntValue.toLIntValue(value.longValue)
			DintType:
				DIntValue.toDIntValue(value.intValue)
			IntType:
				IntValue.toIntValue(value.shortValue)
			SintType:
				SIntValue.toSIntValue(value.byteValue)
			UlintType:
				ULIntValue.toULIntValue(value.longValue)
			UdintType:
				UDIntValue.toUDIntValue(value.intValue)
			UintType:
				UIntValue.toUIntValue(value.shortValue)
			UsintType:
				USIntValue.toUSIntValue(value.byteValue)
			LwordType:
				LWordValue.toLWordValue(value)
			DwordType:
				DWordValue.toDWordValue(value)
			WordType:
				WordValue.toWordValue(value)
			ByteType:
				ByteValue.toByteValue(value)
			default:
				throw new ClassCastException('''The value «value» with type «value.type.name» cannot be cast to «type.name»''')
		}
	}

	def static dispatch Value castValue(AnyCharsValue value, DataType type) {
		switch (type) {
			case null,
			case value.type:
				value
			WstringType:
				WStringValue.toWStringValue(value)
			StringType:
				StringValue.toStringValue(value)
			WcharType:
				WCharValue.toWCharValue(value)
			CharType:
				CharValue.toCharValue(value)
			default:
				throw new ClassCastException('''The value «value» with type «value.type.name» cannot be cast to «type.name»''')
		}
	}

	def static dispatch Value castValue(AnyDateValue value, DataType type) {
		switch (type) {
			case null,
			case value.type:
				value
			LdtType:
				LDateAndTimeValue.toLDateAndTimeValue(value)
			DateAndTimeType:
				DateAndTimeValue.toDateAndTimeValue(value)
			LtodType:
				LTimeOfDayValue.toLTimeOfDayValue(value)
			TimeOfDayType:
				TimeOfDayValue.toTimeOfDayValue(value)
			LdateType:
				LDateValue.toLDateValue(value)
			DateType:
				DateValue.toDateValue(value)
			default:
				throw new ClassCastException('''The value «value» with type «value.type.name» cannot be cast to «type.name»''')
		}
	}

	def static Value wrapValue(Object value, DataType type) {
		if (value === null)
			type.defaultValue
		else
			switch (type) {
				case null:
					null
				LrealType:
					LRealValue.toLRealValue(value as Number)
				RealType:
					RealValue.toRealValue(value as Number)
				LintType:
					LIntValue.toLIntValue(value as Number)
				DintType:
					DIntValue.toDIntValue(value as Number)
				IntType:
					IntValue.toIntValue(value as Number)
				SintType:
					SIntValue.toSIntValue(value as Number)
				UlintType:
					ULIntValue.toULIntValue(value as Number)
				UdintType:
					UDIntValue.toUDIntValue(value as Number)
				UintType:
					UIntValue.toUIntValue(value as Number)
				UsintType:
					USIntValue.toUSIntValue(value as Number)
				LtimeType:
					switch (value) {
						Number: LTimeValue.toLTimeValue(value)
						Duration: LTimeValue.toLTimeValue(value)
						default: LTimeValue.toLTimeValue(value.toString)
					}
				TimeType:
					switch (value) {
						Number: TimeValue.toTimeValue(value)
						Duration: TimeValue.toTimeValue(value)
						default: TimeValue.toTimeValue(value.toString)
					}
				LwordType:
					LWordValue.toLWordValue(value as Number)
				DwordType:
					DWordValue.toDWordValue(value as Number)
				WordType:
					WordValue.toWordValue(value as Number)
				ByteType:
					ByteValue.toByteValue(value as Number)
				BoolType:
					BoolValue.toBoolValue(switch (value) {
						Boolean: value.booleanValue
						Number: value.longValue !== 0
						default: value !== null
					})
				WstringType:
					WStringValue.toWStringValue(value.toString)
				StringType:
					StringValue.toStringValue(value.toString)
				WcharType:
					switch (value) {
						Character: WCharValue.toWCharValue(value)
						default: WCharValue.toWCharValue(value.toString)
					}
				CharType:
					switch (value) {
						Byte: CharValue.toCharValue(value)
						Character: CharValue.toCharValue(value.charValue as byte)
						default: CharValue.toCharValue(value.toString)
					}
				LdtType:
					switch (value) {
						Number: LDateAndTimeValue.toLDateAndTimeValue(value)
						LocalDateTime: LDateAndTimeValue.toLDateAndTimeValue(value)
						default: LDateAndTimeValue.toLDateAndTimeValue(value.toString)
					}
				DateAndTimeType:
					switch (value) {
						Number: DateAndTimeValue.toDateAndTimeValue(value)
						LocalDateTime: DateAndTimeValue.toDateAndTimeValue(value)
						default: DateAndTimeValue.toDateAndTimeValue(value.toString)
					}
				LtodType:
					switch (value) {
						Number: LTimeOfDayValue.toLTimeOfDayValue(value)
						LocalTime: LTimeOfDayValue.toLTimeOfDayValue(value)
						default: LTimeOfDayValue.toLTimeOfDayValue(value.toString)
					}
				TimeOfDayType:
					switch (value) {
						Number: TimeOfDayValue.toTimeOfDayValue(value)
						LocalTime: TimeOfDayValue.toTimeOfDayValue(value)
						default: TimeOfDayValue.toTimeOfDayValue(value.toString)
					}
				LdateType:
					switch (value) {
						Number: LDateValue.toLDateValue(value)
						LocalDate: LDateValue.toLDateValue(value)
						default: LDateValue.toLDateValue(value.toString)
					}
				DateType:
					switch (value) {
						Number: DateValue.toDateValue(value)
						LocalDate: DateValue.toDateValue(value)
						default: DateValue.toDateValue(value.toString)
					}
				default:
					throw new UnsupportedOperationException('''The type «type.name» is not supported''')
			}
	}

	def static Value parseValue(String value, DataType type) {
		if (value.nullOrEmpty)
			type.defaultValue
		else
			switch (type) {
				case null:
					null
				LrealType:
					LRealValue.toLRealValue(value)
				RealType:
					RealValue.toRealValue(value)
				LintType:
					LIntValue.toLIntValue(value)
				DintType:
					DIntValue.toDIntValue(value)
				IntType:
					IntValue.toIntValue(value)
				SintType:
					SIntValue.toSIntValue(value)
				UlintType:
					ULIntValue.toULIntValue(value)
				UdintType:
					UDIntValue.toUDIntValue(value)
				UintType:
					UIntValue.toUIntValue(value)
				UsintType:
					USIntValue.toUSIntValue(value)
				TimeType:
					TimeValue.toTimeValue(value)
				LtimeType:
					LTimeValue.toLTimeValue(value)
				LwordType:
					LWordValue.toLWordValue(value)
				DwordType:
					DWordValue.toDWordValue(value)
				WordType:
					WordValue.toWordValue(value)
				ByteType:
					ByteValue.toByteValue(value)
				BoolType:
					BoolValue.toBoolValue(value)
				WstringType:
					WStringValue.toWStringValue(value)
				StringType:
					StringValue.toStringValue(value)
				WcharType:
					WCharValue.toWCharValue(value)
				CharType:
					CharValue.toCharValue(value)
				LdtType:
					LDateAndTimeValue.toLDateAndTimeValue(value)
				DateAndTimeType:
					DateAndTimeValue.toDateAndTimeValue(value)
				LtodType:
					LTimeOfDayValue.toLTimeOfDayValue(value)
				TimeOfDayType:
					TimeOfDayValue.toTimeOfDayValue(value)
				LdateType:
					LDateValue.toLDateValue(value)
				DateType:
					DateValue.toDateValue(value)
				default:
					throw new UnsupportedOperationException('''The type «type.name» is not supported''')
			}
	}

	def static asBoolean(Value value) {
		(value as BoolValue).boolValue
	}

	def static resultType(DataType first, DataType second) {
		if(first.isCompatibleWith(second)) second else if(second.isCompatibleWith(first)) first
	}
}
