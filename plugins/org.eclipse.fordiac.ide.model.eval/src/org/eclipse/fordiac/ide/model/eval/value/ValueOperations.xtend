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

import org.eclipse.fordiac.ide.model.data.BoolType
import org.eclipse.fordiac.ide.model.data.DataType
import org.eclipse.fordiac.ide.model.data.DintType
import org.eclipse.fordiac.ide.model.data.IntType
import org.eclipse.fordiac.ide.model.data.LintType
import org.eclipse.fordiac.ide.model.data.LrealType
import org.eclipse.fordiac.ide.model.data.RealType
import org.eclipse.fordiac.ide.model.data.SintType
import org.eclipse.fordiac.ide.model.data.UdintType
import org.eclipse.fordiac.ide.model.data.UintType
import org.eclipse.fordiac.ide.model.data.UlintType
import org.eclipse.fordiac.ide.model.data.UsintType

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

	def static <=>(Value first, Value second) { first.compareTo(second) }

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
			default:
				throw new UnsupportedOperationException('''The negate operation is not supported for type «value.type.name»''')
		}
	}

	def static Value bitwiseNot(Value value) {
		switch (value) {
			LIntValue:
				LIntValue.toLIntValue(value.longValue.bitwiseNot)
			DIntValue:
				DIntValue.toDIntValue(value.intValue.bitwiseNot)
			IntValue:
				IntValue.toIntValue((value.intValue.bitwiseNot) as short)
			SIntValue:
				SIntValue.toSIntValue((value.intValue.bitwiseNot) as byte)
			ULIntValue:
				ULIntValue.toULIntValue(value.longValue.bitwiseNot)
			UDIntValue:
				UDIntValue.toUDIntValue(value.intValue.bitwiseNot)
			UIntValue:
				UIntValue.toUIntValue((value.intValue.bitwiseNot) as short)
			USIntValue:
				USIntValue.toUSIntValue((value.intValue.bitwiseNot) as byte)
			default:
				throw new UnsupportedOperationException('''The absolute operation is not supported for type «value.type.name»''')
		}
	}

	def static dispatch Value add(Value first, Value second) {
		throw new UnsupportedOperationException('''The add operation is not supported for types «first.type.name» and «second.type.name»''')
	}

	def static dispatch AnyNumValue add(AnyNumValue first, AnyNumValue second) {
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
			default:
				throw new UnsupportedOperationException('''The add operation is not supported for types «first.type.name» and «second.type.name»''')
		}
	}

	def static dispatch Value subtract(Value first, Value second) {
		throw new UnsupportedOperationException('''The subtract operation is not supported for types «first.type.name» and «second.type.name»''')
	}

	def static dispatch AnyNumValue subtract(AnyNumValue first, AnyNumValue second) {
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
			default:
				throw new UnsupportedOperationException('''The subtract operation is not supported for types «first.type.name» and «second.type.name»''')
		}
	}

	def static dispatch Value multiply(Value first, Value second) {
		throw new UnsupportedOperationException('''The multiply operation is not supported for types «first.type.name» and «second.type.name»''')
	}

	def static dispatch AnyNumValue multiply(AnyNumValue first, AnyNumValue second) {
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
			default:
				throw new UnsupportedOperationException('''The multiply operation is not supported for types «first.type.name» and «second.type.name»''')
		}
	}

	def static dispatch Value divideBy(Value first, Value second) {
		throw new UnsupportedOperationException('''The divide operation is not supported for types «first.type.name» and «second.type.name»''')
	}

	def static dispatch AnyNumValue divideBy(AnyNumValue first, AnyNumValue second) {
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
			default:
				throw new UnsupportedOperationException('''The divide operation is not supported for types «first.type.name» and «second.type.name»''')
		}
	}

	def static dispatch Value remainderBy(Value first, Value second) {
		throw new UnsupportedOperationException('''The remainder operation is not supported for types «first.type.name» and «second.type.name»''')
	}

	def static dispatch AnyNumValue remainderBy(AnyNumValue first, AnyNumValue second) {
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
			default:
				throw new UnsupportedOperationException('''The remainder operation is not supported for types «first.type.name» and «second.type.name»''')
		}
	}

	def static dispatch Value power(Value first, Value second) {
		throw new UnsupportedOperationException('''The power operation is not supported for types «first.type.name» and «second.type.name»''')
	}

	def static dispatch AnyNumValue power(AnyNumValue first, AnyNumValue second) {
		LRealValue.toLRealValue(first.doubleValue ** second.doubleValue)
	}

	def static dispatch boolean equals(Value first, Value second) {
		throw new UnsupportedOperationException('''The equals operation is not supported for types «first.type.name» and «second.type.name»''')
	}

	def static dispatch boolean equals(BoolValue first, BoolValue second) {
		first.boolValue == second.boolValue
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
				throw new UnsupportedOperationException('''The compare operation is not supported for types «first.type.name» and «second.type.name»''')
		}
	}

	def static dispatch int compareTo(Value first, Value second) {
		throw new UnsupportedOperationException('''The compare operation is not supported for types «first.type.name» and «second.type.name»''')
	}

	def static dispatch int compareTo(AnyNumValue first, AnyNumValue second) {
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
			default:
				throw new UnsupportedOperationException('''The compare operation is not supported for types «first.type.name» and «second.type.name»''')
		}
	}

	def static Value defaultValue(DataType type) {
		switch (type) {
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
			BoolType:
				BoolValue.DEFAULT
			default:
				throw new UnsupportedOperationException('''The type «type.name» does not have a default''')
		}
	}

	def static dispatch Value castValue(Value value, DataType type) {
		throw new UnsupportedOperationException('''The cast operation is not supported for types «value.type.name»''')
	}

	def static dispatch Value castValue(Void value, DataType type) { null }

	def static dispatch Value castValue(BoolValue value, DataType type) {
		switch (type) {
			case value.type:
				value
			BoolType:
				value
			default:
				throw new ClassCastException('''The value «value» with type «value.type.name» cannot be cast to «type.name»''')
		}
	}

	def static dispatch Value castValue(AnyNumValue value, DataType type) {
		switch (type) {
			case value.type:
				value
			LrealType:
				LRealValue.toLRealValue(value.doubleValue)
			RealType:
				RealValue.toRealValue(value.floatValue)
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
			default:
				throw new ClassCastException('''The value «value» with type «value.type.name» cannot be cast to «type.name»''')
		}
	}

	def static Value wrapValue(Object value, DataType type) {
		if (value === null)
			type.defaultValue
		else
			switch (type) {
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
				BoolType:
					BoolValue.toBoolValue(value as Boolean)
				default:
					throw new UnsupportedOperationException('''The type «type.name» is not supported''')
			}
	}

	def static Value parseValue(String value, DataType type) {
		if (value.nullOrEmpty)
			type.defaultValue
		else
			switch (type) {
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
				BoolType:
					BoolValue.toBoolValue(value)
				default:
					throw new UnsupportedOperationException('''The type «type.name» is not supported''')
			}
	}

	def static resultType(DataType first, DataType second) {
		if(first.isCompatibleWith(second)) second else if(second.isCompatibleWith(first)) first else null
	}
}
