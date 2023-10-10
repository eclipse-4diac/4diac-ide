/*******************************************************************************
 * Copyright (c) 2022 - 2023 Martin Erich Jobst
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

import java.math.BigDecimal
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Objects
import org.eclipse.fordiac.ide.model.data.AnyBitType
import org.eclipse.fordiac.ide.model.data.AnyCharType
import org.eclipse.fordiac.ide.model.data.AnyCharsType
import org.eclipse.fordiac.ide.model.data.AnyDateType
import org.eclipse.fordiac.ide.model.data.AnyDerivedType
import org.eclipse.fordiac.ide.model.data.AnyDurationType
import org.eclipse.fordiac.ide.model.data.AnyElementaryType
import org.eclipse.fordiac.ide.model.data.AnyIntType
import org.eclipse.fordiac.ide.model.data.AnyMagnitudeType
import org.eclipse.fordiac.ide.model.data.AnyNumType
import org.eclipse.fordiac.ide.model.data.AnyRealType
import org.eclipse.fordiac.ide.model.data.AnySignedType
import org.eclipse.fordiac.ide.model.data.AnyStringType
import org.eclipse.fordiac.ide.model.data.AnyType
import org.eclipse.fordiac.ide.model.data.AnyUnsignedType
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
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes
import org.eclipse.fordiac.ide.model.eval.variable.ArrayVariable
import org.eclipse.fordiac.ide.model.eval.variable.StructVariable
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement
import org.eclipse.fordiac.ide.model.value.TypedValueConverter
import org.eclipse.fordiac.ide.model.data.ArrayType
import org.eclipse.fordiac.ide.model.data.StructuredType

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

	def static Value reverseBytes(Value value) {
		switch (value) {
			LRealValue:
				LRealValue.toLRealValue(
					Double.longBitsToDouble(Long.reverseBytes(Double.doubleToRawLongBits(value.doubleValue))))
			RealValue:
				RealValue.toRealValue(
					Float.intBitsToFloat(Integer.reverseBytes(Float.floatToRawIntBits(value.floatValue))))
			LIntValue:
				LIntValue.toLIntValue(Long.reverseBytes(value.longValue))
			DIntValue:
				DIntValue.toDIntValue(Integer.reverseBytes(value.intValue))
			IntValue:
				IntValue.toIntValue(Short.reverseBytes(value.shortValue))
			SIntValue:
				value
			ULIntValue:
				ULIntValue.toULIntValue(Long.reverseBytes(value.longValue))
			UDIntValue:
				UDIntValue.toUDIntValue(Integer.reverseBytes(value.intValue))
			UIntValue:
				UIntValue.toUIntValue(Short.reverseBytes(value.shortValue))
			USIntValue:
				value
			LTimeValue:
				LTimeValue.toLTimeValue(Long.reverseBytes(value.longValue))
			TimeValue:
				TimeValue.toTimeValue(Long.reverseBytes(value.longValue))
			LWordValue:
				LWordValue.toLWordValue(Long.reverseBytes(value.longValue))
			DWordValue:
				DWordValue.toDWordValue(Integer.reverseBytes(value.intValue))
			WordValue:
				WordValue.toWordValue(Short.reverseBytes(value.shortValue))
			ByteValue,
			BoolValue,
			StringValue,
			CharValue:
				value
			WStringValue:
				WStringValue.toWStringValue(
					new String(value.stringValue.getBytes(StandardCharsets.UTF_16BE), StandardCharsets.UTF_16LE))
			WCharValue:
				WCharValue.toWCharValue(Character.reverseBytes(value.charValue))
			LDateValue,
			DateValue,
			LTimeOfDayValue,
			TimeOfDayValue,
			LDateAndTimeValue,
			DateAndTimeValue:
				value // do not convert
			ArrayValue: {
				val temp = new ArrayVariable("TEMP", value.type, value)
				temp.forEach[variable|variable.value = variable.value.reverseBytes]
				temp.value
			}
			StructValue: {
				val temp = new StructVariable("TEMP", value.type, value)
				temp.forEach[variable|variable.value = variable.value.reverseBytes]
				temp.value
			}
			default:
				throw new UnsupportedOperationException('''The reverseBytes operation is not supported for type «value.type.name»''')
		}
	}

	def static Value sqrt(Value value) {
		switch (value) {
			LRealValue:
				LRealValue.toLRealValue(Math.sqrt(value.doubleValue))
			RealValue:
				RealValue.toRealValue(Math.sqrt(value.floatValue) as float)
			default:
				throw new UnsupportedOperationException('''The sqrt operation is not supported for type «value.type.name»''')
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

	def static dispatch AnyDateValue add(AnyDateValue first, TimeValue second) {
		switch (first) {
			TimeOfDayValue:
				TimeOfDayValue.toTimeOfDayValue(first.toNanos + second.longValue)
			LTimeOfDayValue:
				LTimeOfDayValue.toLTimeOfDayValue(first.toNanos + second.longValue)
			DateAndTimeValue:
				DateAndTimeValue.toDateAndTimeValue(first.toNanos + second.longValue)
			LDateAndTimeValue:
				LDateAndTimeValue.toLDateAndTimeValue(first.toNanos + second.longValue)
			default:
				throw new UnsupportedOperationException('''The add operation is not supported for types «first.type.name» and «second.type.name»''')
		}
	}

	def static dispatch AnyDateValue add(AnyDateValue first, LTimeValue second) {
		switch (first) {
			TimeOfDayValue,
			LTimeOfDayValue:
				LTimeOfDayValue.toLTimeOfDayValue(first.toNanos + second.longValue)
			DateAndTimeValue,
			LDateAndTimeValue:
				LDateAndTimeValue.toLDateAndTimeValue(first.toNanos + second.longValue)
			default:
				throw new UnsupportedOperationException('''The add operation is not supported for types «first.type.name» and «second.type.name»''')
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

	def static dispatch AnyDateValue subtract(AnyDateValue first, TimeValue second) {
		switch (first) {
			TimeOfDayValue:
				TimeOfDayValue.toTimeOfDayValue(first.toNanos - second.longValue)
			LTimeOfDayValue:
				LTimeOfDayValue.toLTimeOfDayValue(first.toNanos - second.longValue)
			DateAndTimeValue:
				DateAndTimeValue.toDateAndTimeValue(first.toNanos - second.longValue)
			LDateAndTimeValue:
				LDateAndTimeValue.toLDateAndTimeValue(first.toNanos - second.longValue)
			default:
				throw new UnsupportedOperationException('''The subtract operation is not supported for types «first.type.name» and «second.type.name»''')
		}
	}

	def static dispatch AnyDateValue subtract(AnyDateValue first, LTimeValue second) {
		switch (first) {
			TimeOfDayValue,
			LTimeOfDayValue:
				LTimeOfDayValue.toLTimeOfDayValue(first.toNanos - second.longValue)
			DateAndTimeValue,
			LDateAndTimeValue:
				LDateAndTimeValue.toLDateAndTimeValue(first.toNanos - second.longValue)
			default:
				throw new UnsupportedOperationException('''The subtract operation is not supported for types «first.type.name» and «second.type.name»''')
		}
	}

	def static dispatch AnyDurationValue subtract(AnyDateValue first, AnyDateValue second) {
		switch (first.type.resultType(second.type)) {
			DateType,
			TimeOfDayType,
			DateAndTimeType:
				TimeValue.toTimeValue(first.toNanos - second.toNanos)
			LdateType,
			LtodType,
			LdtType:
				LTimeValue.toLTimeValue(first.toNanos - second.toNanos)
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
				if (second instanceof AnyRealValue)
					LTimeValue.toLTimeValue((first.doubleValue * second.doubleValue) as long)
				else
					LTimeValue.toLTimeValue(first.longValue * second.longValue)
			TimeType:
				if (second instanceof AnyRealValue)
					TimeValue.toTimeValue((first.doubleValue * second.doubleValue) as long)
				else
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
				if (second instanceof AnyRealValue)
					if (second.doubleValue != 0.0)
						LTimeValue.toLTimeValue((first.doubleValue / second.doubleValue) as long)
					else
						throw new ArithmeticException("Division by zero")
				else
					LTimeValue.toLTimeValue(first.longValue / second.longValue)
			TimeType:
				if (second instanceof AnyRealValue)
					if (second.doubleValue != 0.0)
						TimeValue.toTimeValue((first.doubleValue / second.doubleValue) as long)
					else
						throw new ArithmeticException("Division by zero")
				else
					TimeValue.toTimeValue(first.longValue / second.longValue)
		}
	}

	def static dispatch Value remainderBy(Value first, Value second) {
		throw new UnsupportedOperationException('''The remainder operation is not supported for types «first.type.name» and «second.type.name»''')
	}

	def static dispatch AnyIntValue remainderBy(AnyIntValue first, AnyIntValue second) {
		switch (resultType : first.type.resultType(second.type)) {
			case second.longValue == 0: // MOD by 0 defined to return 0
				resultType.defaultValue as AnyIntValue
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

	def static dispatch Value shiftLeft(Value first, Value second) {
		throw new UnsupportedOperationException('''The shift left operation is not supported for types «first.type.name» and «second.type.name»''')
	}

	def static dispatch AnyBitValue shiftLeft(AnyBitValue first, AnyIntValue second) {
		switch (first) {
			LWordValue:
				LWordValue.toLWordValue(first.longValue << second.intValue)
			DWordValue:
				DWordValue.toDWordValue(first.intValue << second.intValue)
			WordValue:
				WordValue.toWordValue((first.shortValue << second.intValue) as short)
			ByteValue:
				ByteValue.toByteValue((first.byteValue << second.intValue) as byte)
			default:
				throw new UnsupportedOperationException('''The shift left operation is not supported for types «first.type.name» and «second.type.name»''')
		}
	}

	def static dispatch Value shiftRight(Value first, Value second) {
		throw new UnsupportedOperationException('''The shift left operation is not supported for types «first.type.name» and «second.type.name»''')
	}

	def static dispatch AnyBitValue shiftRight(AnyBitValue first, AnyIntValue second) {
		switch (first) {
			LWordValue:
				LWordValue.toLWordValue(first.longValue >>> second.intValue)
			DWordValue:
				DWordValue.toDWordValue(first.intValue >>> second.intValue)
			WordValue:
				WordValue.toWordValue((first.intValue >>> second.intValue) as short)
			ByteValue:
				ByteValue.toByteValue((first.intValue >>> second.intValue) as byte)
			default:
				throw new UnsupportedOperationException('''The shift right operation is not supported for types «first.type.name» and «second.type.name»''')
		}
	}

	def static dispatch Value rotateLeft(Value first, Value second) {
		throw new UnsupportedOperationException('''The shift left operation is not supported for types «first.type.name» and «second.type.name»''')
	}

	def static dispatch AnyBitValue rotateLeft(AnyBitValue first, AnyIntValue second) {
		switch (first) {
			LWordValue:
				LWordValue.toLWordValue(Long.rotateLeft(first.longValue, second.intValue))
			DWordValue:
				DWordValue.toDWordValue(Integer.rotateLeft(first.intValue, second.intValue))
			WordValue:
				WordValue.toWordValue(
					((first.intValue << second.intValue).bitwiseOr(first.intValue >>> (16 - second.intValue))) as short)
			ByteValue:
				ByteValue.toByteValue(
					((first.intValue << second.intValue).bitwiseOr(first.intValue >>> (8 - second.intValue))) as byte)
			default:
				throw new UnsupportedOperationException('''The shift left operation is not supported for types «first.type.name» and «second.type.name»''')
		}
	}

	def static dispatch Value rotateRight(Value first, Value second) {
		throw new UnsupportedOperationException('''The shift left operation is not supported for types «first.type.name» and «second.type.name»''')
	}

	def static dispatch AnyBitValue rotateRight(AnyBitValue first, AnyIntValue second) {
		switch (first) {
			LWordValue:
				LWordValue.toLWordValue(Long.rotateRight(first.longValue, second.intValue))
			DWordValue:
				DWordValue.toDWordValue(Integer.rotateRight(first.intValue, second.intValue))
			WordValue:
				WordValue.toWordValue(
					((first.intValue >>> second.intValue).bitwiseOr(first.intValue << (16 - second.intValue))) as short)
			ByteValue:
				ByteValue.toByteValue(
					((first.intValue >>> second.intValue).bitwiseOr(first.intValue << (8 - second.intValue))) as byte)
			default:
				throw new UnsupportedOperationException('''The shift left operation is not supported for types «first.type.name» and «second.type.name»''')
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
		first.stringValue == second.stringValue
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
				throw new UnsupportedOperationException('''The compare operation is not supported for types «first.type.name» and «second.type.name»''')
		}
	}

	def static dispatch int compareTo(AnyBitValue first, AnyBitValue second) {
		switch (first.type.resultType(second.type)) {
			LwordType:
				Long.compareUnsigned(first.longValue, second.longValue)
			DwordType:
				Integer.compareUnsigned(first.intValue, second.intValue)
			WordType:
				Integer.compareUnsigned(first.intValue, second.intValue)
			ByteType:
				Integer.compareUnsigned(first.intValue, second.intValue)
			BoolType:
				Boolean.compare(first.boolValue, second.boolValue)
			default:
				throw new UnsupportedOperationException('''The compare operation is not supported for types «first.type.name» and «second.type.name»''')
		}
	}

	def static dispatch int compareTo(AnyCharsValue first, AnyCharsValue second) {
		first.stringValue.compareTo(second.stringValue)
	}

	def static dispatch int compareTo(AnyDateValue first, AnyDateValue second) {
		Long.compare(first.toNanos, second.toNanos)
	}

	def static dispatch Value partial(Value value, DataType type, int index) {
		throw new UnsupportedOperationException('''The partial operation is not supported for type «value.type.name»''')
	}

	def static dispatch Value partial(ByteValue value, DataType type, int index) {
		switch (type) {
			BoolType: BoolValue.toBoolValue((value.byteValue >>> index).bitwiseAnd(1) != 0)
			default: throw new UnsupportedOperationException('''The partial operation is not supported for type «value.type.name» and partial type «type.name»''')
		}
	}

	def static dispatch Value partial(WordValue value, DataType type, int index) {
		switch (type) {
			BoolType: BoolValue.toBoolValue((value.shortValue >>> index).bitwiseAnd(1) != 0)
			ByteType: ByteValue.toByteValue((value.shortValue >>> (index * 8)) as byte)
			default: throw new UnsupportedOperationException('''The partial operation is not supported for type «value.type.name» and partial type «type.name»''')
		}
	}

	def static dispatch Value partial(DWordValue value, DataType type, int index) {
		switch (type) {
			BoolType: BoolValue.toBoolValue((value.intValue >>> index).bitwiseAnd(1) != 0)
			ByteType: ByteValue.toByteValue((value.intValue >>> (index * 8)) as byte)
			WordType: WordValue.toWordValue((value.intValue >>> (index * 16)) as short)
			default: throw new UnsupportedOperationException('''The partial operation is not supported for type «value.type.name» and partial type «type.name»''')
		}
	}

	def static dispatch Value partial(LWordValue value, DataType type, int index) {
		switch (type) {
			BoolType: BoolValue.toBoolValue((value.longValue >>> index).bitwiseAnd(1) != 0)
			ByteType: ByteValue.toByteValue((value.longValue >>> (index * 8)) as byte)
			WordType: WordValue.toWordValue((value.longValue >>> (index * 16)) as short)
			DwordType: DWordValue.toDWordValue((value.longValue >>> (index * 32)) as int)
			default: throw new UnsupportedOperationException('''The partial operation is not supported for type «value.type.name» and partial type «type.name»''')
		}
	}

	def static dispatch Value partial(Value value, DataType type, int index, Value partial) {
		throw new UnsupportedOperationException('''The partial operation is not supported for type «value.type.name» and partial value type «partial.type.name»''')
	}

	def static dispatch Value partial(ByteValue value, AnyBitType type, int index, AnyBitValue partial) {
		if (type == value.type || !value.type.isAssignableFrom(type))
			throw new UnsupportedOperationException('''The partial operation is not supported for type «value.type.name» and partial type «type.name»''')
		ByteValue.toByteValue(value.byteValue.combine(
			(partial.byteValue << (index * type.bitSize)) as byte,
			(type.bitMask << (index * type.bitSize)) as byte
		))
	}

	def static dispatch Value partial(WordValue value, AnyBitType type, int index, AnyBitValue partial) {
		if (type == value.type || !value.type.isAssignableFrom(type))
			throw new UnsupportedOperationException('''The partial operation is not supported for type «value.type.name» and partial type «type.name»''')
		WordValue.toWordValue(value.shortValue.combine(
			(partial.shortValue << (index * type.bitSize)) as short,
			(type.bitMask << (index * type.bitSize)) as short
		))
	}

	def static dispatch Value partial(DWordValue value, AnyBitType type, int index, AnyBitValue partial) {
		if (type == value.type || !value.type.isAssignableFrom(type))
			throw new UnsupportedOperationException('''The partial operation is not supported for type «value.type.name» and partial type «type.name»''')
		DWordValue.toDWordValue(value.intValue.combine(
			partial.intValue << (index * type.bitSize),
			(type.bitMask << (index * type.bitSize)) as int
		))
	}

	def static dispatch Value partial(LWordValue value, AnyBitType type, int index, AnyBitValue partial) {
		if (type == value.type || !value.type.isAssignableFrom(type))
			throw new UnsupportedOperationException('''The partial operation is not supported for type «value.type.name» and partial type «type.name»''')
		LWordValue.toLWordValue(value.longValue.combine(
			partial.longValue << (index * type.bitSize),
			type.bitMask << (index * type.bitSize)
		))
	}

	def private static byte combine(byte value, byte partial, byte mask) {
		value.bitwiseXor(value.bitwiseXor(partial).bitwiseAnd(mask)) as byte // (value & ~mask) | (partial & mask)
	}

	def private static short combine(short value, short partial, short mask) {
		value.bitwiseXor(value.bitwiseXor(partial).bitwiseAnd(mask)) as short // (value & ~mask) | (partial & mask)
	}

	def private static int combine(int value, int partial, int mask) {
		value.bitwiseXor(value.bitwiseXor(partial).bitwiseAnd(mask)) // (value & ~mask) | (partial & mask)
	}

	def private static long combine(long value, long partial, long mask) {
		value.bitwiseXor(value.bitwiseXor(partial).bitwiseAnd(mask)) // (value & ~mask) | (partial & mask)
	}

	def static long bitMask(AnyBitType type) {
		(0xffffffffffffffff#L >>> (64 - type.bitSize))
	}

	def static Value defaultValue(INamedElement type) {
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
			AnyDateType:
				LDateAndTimeValue.DEFAULT
			AnyCharType:
				WCharValue.DEFAULT
			AnyStringType,
			AnyCharsType:
				WStringValue.DEFAULT
			AnyBitType:
				DWordValue.DEFAULT
			AnyDurationType:
				LTimeValue.DEFAULT
			AnyRealType:
				LRealValue.DEFAULT
			AnyUnsignedType:
				UDIntValue.DEFAULT
			AnySignedType,
			AnyIntType,
			AnyNumType,
			AnyMagnitudeType,
			AnyElementaryType,
			AnyType:
				DIntValue.DEFAULT
			default:
				throw new UnsupportedOperationException('''The type «type.name» does not have a default''')
		}
	}

	def static dispatch Value castValue(Value value, INamedElement type) {
		switch (type) {
			case value.type == type,
			DataType case value.type instanceof DataType && type.isAssignableFrom(value.type as DataType):
				value
			default:
				throw new ClassCastException('''The value «value» with type «value.type.name» cannot be cast to «type.name»''')
		}
	}

	def static dispatch Value castValue(Void value, INamedElement type) { null }

	def static dispatch Value castValue(BoolValue value, INamedElement type) {
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
			DataType case type.isAssignableFrom(value.type):
				value
			default:
				throw new ClassCastException('''The value «value» with type «value.type.name» cannot be cast to «type.name»''')
		}
	}

	def static dispatch Value castValue(AnyMagnitudeValue value, INamedElement type) {
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
			DataType case type.isAssignableFrom(value.type):
				value
			default:
				throw new ClassCastException('''The value «value» with type «value.type.name» cannot be cast to «type.name»''')
		}
	}

	def static dispatch Value castValue(AnyBitValue value, INamedElement type) {
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
			DataType case type.isAssignableFrom(value.type):
				value
			default:
				throw new ClassCastException('''The value «value» with type «value.type.name» cannot be cast to «type.name»''')
		}
	}

	def static dispatch Value castValue(AnyCharsValue value, INamedElement type) {
		switch (type) {
			case null,
			case value.type:
				value
			WstringType:
				if (type.setMaxLength)
					WStringValue.toWStringValue(value, type.maxLength)
				else
					WStringValue.toWStringValue(value)
			StringType:
				if (type.setMaxLength)
					StringValue.toStringValue(value, type.maxLength)
				else
					StringValue.toStringValue(value)
			WcharType:
				WCharValue.toWCharValue(value)
			CharType:
				CharValue.toCharValue(value)
			DataType case type.isAssignableFrom(value.type):
				value
			default:
				throw new ClassCastException('''The value «value» with type «value.type.name» cannot be cast to «type.name»''')
		}
	}

	def static dispatch Value castValue(AnyDateValue value, INamedElement type) {
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
			DataType case type.isAssignableFrom(value.type):
				value
			default:
				throw new ClassCastException('''The value «value» with type «value.type.name» cannot be cast to «type.name»''')
		}
	}

	def static Value wrapValue(Object value, INamedElement type) {
		if (value === null)
			type.defaultValue
		else
			switch (type) {
				case null:
					null
				LrealType:
					switch (value) {
						Number: LRealValue.toLRealValue(value)
						default: LRealValue.toLRealValue(value.toString)
					}
				RealType:
					switch (value) {
						Number: RealValue.toRealValue(value)
						default: RealValue.toRealValue(value.toString)
					}
				LintType:
					switch (value) {
						Number: LIntValue.toLIntValue(value)
						default: LIntValue.toLIntValue(value.toString)
					}
				DintType:
					switch (value) {
						Number: DIntValue.toDIntValue(value)
						default: DIntValue.toDIntValue(value.toString)
					}
				IntType:
					switch (value) {
						Number: IntValue.toIntValue(value)
						default: IntValue.toIntValue(value.toString)
					}
				SintType:
					switch (value) {
						Number: SIntValue.toSIntValue(value)
						default: SIntValue.toSIntValue(value.toString)
					}
				UlintType:
					switch (value) {
						Number: ULIntValue.toULIntValue(value)
						default: ULIntValue.toULIntValue(value.toString)
					}
				UdintType:
					switch (value) {
						Number: UDIntValue.toUDIntValue(value)
						default: UDIntValue.toUDIntValue(value.toString)
					}
				UintType:
					switch (value) {
						Number: UIntValue.toUIntValue(value)
						default: UIntValue.toUIntValue(value.toString)
					}
				UsintType:
					switch (value) {
						Number: USIntValue.toUSIntValue(value)
						default: USIntValue.toUSIntValue(value.toString)
					}
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
					switch (value) {
						Number: LWordValue.toLWordValue(value)
						default: LWordValue.toLWordValue(value.toString)
					}
				DwordType:
					switch (value) {
						Number: DWordValue.toDWordValue(value)
						default: DWordValue.toDWordValue(value.toString)
					}
				WordType:
					switch (value) {
						Number: WordValue.toWordValue(value)
						default: WordValue.toWordValue(value.toString)
					}
				ByteType:
					switch (value) {
						Number: ByteValue.toByteValue(value)
						default: ByteValue.toByteValue(value.toString)
					}
				BoolType:
					BoolValue.toBoolValue(switch (value) {
						Boolean: value.booleanValue
						Number: value.longValue !== 0
						default: value !== null
					})
				WstringType:
					if (type.setMaxLength)
						WStringValue.toWStringValue(value.toString, type.maxLength)
					else
						WStringValue.toWStringValue(value.toString)
				StringType:
					if (type.setMaxLength)
						StringValue.toStringValue(value.toString, type.maxLength)
					else
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
				AnyDateType:
					switch (value) {
						Number: LDateAndTimeValue.toLDateAndTimeValue(value)
						LocalDateTime: LDateAndTimeValue.toLDateAndTimeValue(value)
						LocalTime: LTimeOfDayValue.toLTimeOfDayValue(value)
						LocalDate: LDateValue.toLDateValue(value)
						default: LDateAndTimeValue.toLDateAndTimeValue(value.toString)
					}
				AnyCharType:
					switch (value) {
						Byte: CharValue.toCharValue(value)
						Character: WCharValue.toWCharValue(value.charValue)
						default: WCharValue.toWCharValue(value.toString)
					}
				AnyStringType,
				AnyCharsType:
					WStringValue.toWStringValue(value.toString)
				AnyBitType:
					switch (value) {
						Byte: ByteValue.toByteValue(value)
						Short: WordValue.toWordValue(value)
						Character: WordValue.toWordValue(value.charValue as int)
						Integer: DWordValue.toDWordValue(value)
						Long: LWordValue.toLWordValue(value)
						Number: DWordValue.toDWordValue(value)
						default: DWordValue.toDWordValue(value.toString)
					}
				AnyDurationType:
					switch (value) {
						Number: LTimeValue.toLTimeValue(value)
						Duration: LTimeValue.toLTimeValue(value)
						default: LTimeValue.toLTimeValue(value.toString)
					}
				AnyRealType:
					switch (value) {
						Float: RealValue.toRealValue(value)
						Number: LRealValue.toLRealValue(value)
						default: LRealValue.toLRealValue(value.toString)
					}
				AnyUnsignedType:
					switch (value) {
						Byte: USIntValue.toUSIntValue(value)
						Short: UIntValue.toUIntValue(value)
						Character: UIntValue.toUIntValue(value.charValue as int)
						Integer: UDIntValue.toUDIntValue(value)
						Long: ULIntValue.toULIntValue(value)
						Number: UDIntValue.toUDIntValue(value)
						default: UDIntValue.toUDIntValue(value.toString)
					}
				AnySignedType:
					switch (value) {
						Byte: SIntValue.toSIntValue(value)
						Short: IntValue.toIntValue(value)
						Character: IntValue.toIntValue(value.charValue as int)
						Integer: DIntValue.toDIntValue(value)
						Long: LIntValue.toLIntValue(value)
						Number: DIntValue.toDIntValue(value)
						default: DIntValue.toDIntValue(value.toString)
					}
				AnyIntType:
					switch (value) {
						Byte: SIntValue.toSIntValue(value)
						Short: IntValue.toIntValue(value)
						Character: UIntValue.toUIntValue(value.charValue as int)
						Integer: DIntValue.toDIntValue(value)
						Long: LIntValue.toLIntValue(value)
						Number: DIntValue.toDIntValue(value)
						default: DIntValue.toDIntValue(value.toString)
					}
				AnyNumType,
				AnyMagnitudeType,
				AnyElementaryType,
				AnyType:
					switch (value) {
						Byte: SIntValue.toSIntValue(value)
						Short: IntValue.toIntValue(value)
						Character: UIntValue.toUIntValue(value.charValue as int)
						Integer: DIntValue.toDIntValue(value)
						Long: LIntValue.toLIntValue(value)
						Float: RealValue.toRealValue(value)
						Double: LRealValue.toLRealValue(value)
						BigDecimal: LRealValue.toLRealValue(value)
						Number: DIntValue.toDIntValue(value)
						default: DIntValue.toDIntValue(value.toString)
					}
				default:
					throw new UnsupportedOperationException('''The type «type.name» is not supported''')
			}
	}

	def static Value parseValue(String value, INamedElement type) {
		if (value.nullOrEmpty)
			type.defaultValue
		else if (type instanceof DataType) {
			val converter = new TypedValueConverter(type)
			converter.toValue(value).wrapValue(type)
		} else
			throw new UnsupportedOperationException('''The type «type?.name» is not supported''')
	}

	def static Class<? extends Value> valueType(INamedElement type) {
		switch (type) {
			LrealType: LRealValue
			RealType: RealValue
			LintType: LIntValue
			DintType: DIntValue
			IntType: IntValue
			SintType: SIntValue
			UlintType: ULIntValue
			UdintType: UDIntValue
			UintType: UIntValue
			UsintType: USIntValue
			LtimeType: LTimeValue
			TimeType: TimeValue
			LwordType: LWordValue
			DwordType: DWordValue
			WordType: WordValue
			ByteType: ByteValue
			BoolType: BoolValue
			WstringType: WStringValue
			StringType: StringValue
			WcharType: WCharValue
			CharType: CharValue
			LdtType: LDateAndTimeValue
			DateAndTimeType: DateAndTimeValue
			LtodType: LTimeOfDayValue
			TimeOfDayType: TimeOfDayValue
			LdateType: LDateValue
			DateType: DateValue
			AnySignedType: AnySignedValue
			AnyUnsignedType: AnyUnsignedValue
			AnyIntType: AnyIntValue
			AnyRealType: AnyRealValue
			AnyNumType: AnyNumValue
			AnyDurationType: AnyDurationValue
			AnyMagnitudeType: AnyMagnitudeValue
			AnyBitType: AnyBitValue
			AnyCharType: AnyCharValue
			AnyStringType: AnyStringValue
			AnyCharsType: AnyCharsValue
			AnyDateType: AnyDateValue
			AnyElementaryType: AnyElementaryValue
			ArrayType: ArrayValue
			StructuredType: StructValue
			AnyDerivedType: AnyDerivedValue
			AnyType: AnyValue
			default: null
		}
	}

	def static DataType dataType(Class<?> type) {
		switch (type) {
			case LRealValue: ElementaryTypes.LREAL
			case RealValue: ElementaryTypes.REAL
			case LIntValue: ElementaryTypes.LINT
			case DIntValue: ElementaryTypes.DINT
			case IntValue: ElementaryTypes.INT
			case SIntValue: ElementaryTypes.SINT
			case ULIntValue: ElementaryTypes.ULINT
			case UDIntValue: ElementaryTypes.UDINT
			case UIntValue: ElementaryTypes.UINT
			case USIntValue: ElementaryTypes.USINT
			case LTimeValue: ElementaryTypes.LTIME
			case TimeValue: ElementaryTypes.TIME
			case LWordValue: ElementaryTypes.LWORD
			case DWordValue: ElementaryTypes.DWORD
			case WordValue: ElementaryTypes.WORD
			case ByteValue: ElementaryTypes.BYTE
			case BoolValue: ElementaryTypes.BOOL
			case WStringValue: ElementaryTypes.WSTRING
			case StringValue: ElementaryTypes.STRING
			case WCharValue: ElementaryTypes.WCHAR
			case CharValue: ElementaryTypes.CHAR
			case LDateAndTimeValue: ElementaryTypes.LDATE_AND_TIME
			case DateAndTimeValue: ElementaryTypes.DATE_AND_TIME
			case LTimeOfDayValue: ElementaryTypes.LTIME_OF_DAY
			case TimeOfDayValue: ElementaryTypes.TIME_OF_DAY
			case LDateValue: ElementaryTypes.LDATE
			case DateValue: ElementaryTypes.DATE
			case AnySignedValue: GenericTypes.ANY_SIGNED
			case AnyUnsignedValue: GenericTypes.ANY_UNSIGNED
			case AnyIntValue: GenericTypes.ANY_INT
			case AnyRealValue: GenericTypes.ANY_REAL
			case AnyNumValue: GenericTypes.ANY_NUM
			case AnyDurationValue: GenericTypes.ANY_DURATION
			case AnyMagnitudeValue: GenericTypes.ANY_MAGNITUDE
			case AnyBitValue: GenericTypes.ANY_BIT
			case AnyCharValue: GenericTypes.ANY_CHAR
			case AnyStringValue: GenericTypes.ANY_STRING
			case AnySCharsValue: GenericTypes.ANY_SCHARS
			case AnyWCharsValue: GenericTypes.ANY_WCHARS
			case AnyCharsValue: GenericTypes.ANY_CHARS
			case AnyDateValue: GenericTypes.ANY_DATE
			case AnyElementaryValue: GenericTypes.ANY_ELEMENTARY
			case ArrayValue: GenericTypes.ANY_DERIVED
			case StructValue: GenericTypes.ANY_STRUCT
			case AnyDerivedValue: GenericTypes.ANY_DERIVED
			case AnyValue: GenericTypes.ANY
			default: null
		}
	}

	def static asBoolean(Value value) {
		switch (value) {
			AnyBitValue: value.boolValue
			AnyMagnitudeValue: value.intValue != 0
			default: throw new UnsupportedOperationException('''The type «value.type.name» is not supported''')
		}
	}

	def static int asInteger(Value value) {
		switch (value) {
			AnyMagnitudeValue: value.intValue
			AnyBitValue: value.intValue
			default: throw new UnsupportedOperationException('''The type «value.type.name» is not supported''')
		}
	}

	def static resultType(DataType first, DataType second) {
		if (first instanceof AnyDurationType && second instanceof AnyNumType)
			first
		else if (first.isAssignableFrom(second))
			first
		else if (second.isAssignableFrom(first))
			second
	}
}
