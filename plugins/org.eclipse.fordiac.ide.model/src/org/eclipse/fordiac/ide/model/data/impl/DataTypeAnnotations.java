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
package org.eclipse.fordiac.ide.model.data.impl;

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
import org.eclipse.fordiac.ide.model.data.AnySCharsType;
import org.eclipse.fordiac.ide.model.data.AnySignedType;
import org.eclipse.fordiac.ide.model.data.AnyStringType;
import org.eclipse.fordiac.ide.model.data.AnyType;
import org.eclipse.fordiac.ide.model.data.AnyUnsignedType;
import org.eclipse.fordiac.ide.model.data.AnyWCharsType;
import org.eclipse.fordiac.ide.model.data.ArrayType;
import org.eclipse.fordiac.ide.model.data.BoolType;
import org.eclipse.fordiac.ide.model.data.ByteType;
import org.eclipse.fordiac.ide.model.data.CharType;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.DateAndTimeType;
import org.eclipse.fordiac.ide.model.data.DateType;
import org.eclipse.fordiac.ide.model.data.DintType;
import org.eclipse.fordiac.ide.model.data.DirectlyDerivedType;
import org.eclipse.fordiac.ide.model.data.DwordType;
import org.eclipse.fordiac.ide.model.data.EnumeratedType;
import org.eclipse.fordiac.ide.model.data.EventType;
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
import org.eclipse.fordiac.ide.model.data.SubrangeType;
import org.eclipse.fordiac.ide.model.data.TimeOfDayType;
import org.eclipse.fordiac.ide.model.data.TimeType;
import org.eclipse.fordiac.ide.model.data.UdintType;
import org.eclipse.fordiac.ide.model.data.UintType;
import org.eclipse.fordiac.ide.model.data.UlintType;
import org.eclipse.fordiac.ide.model.data.UsintType;
import org.eclipse.fordiac.ide.model.data.WcharType;
import org.eclipse.fordiac.ide.model.data.WordType;
import org.eclipse.fordiac.ide.model.data.WstringType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;

final class DataTypeAnnotations {

	private DataTypeAnnotations() {
	}

	static boolean isAssignableFrom(final EventType type, final DataType other) {
		return other instanceof EventType && type != null && type.getName().equals(other.getName());
	}

	static boolean isAssignableFrom(final DataType type, final DataType other) {
		return type == other;
	}

	static boolean isAssignableFrom(final AnyType type, final DataType other) {
		return type == other || type.eClass().isSuperTypeOf(other.eClass());
	}

	static boolean isAssignableFrom(final AnyDerivedType type, final DataType other) {
		return type == other || type.eClass().isSuperTypeOf(other.eClass());
	}

	static boolean isAssignableFrom(final DirectlyDerivedType type, final DataType other) {
		return type == other || type.getBaseType().isAssignableFrom(other);
	}

	static boolean isAssignableFrom(final ArrayType type, final DataType other) {
		if (type == other) {
			return true;
		}
		if (other instanceof final ArrayType otherArray) {
			// arrays are assignable if the base type is assignable and they have the same
			// amount of dimensions
			return type.getBaseType().isAssignableFrom(otherArray.getBaseType())
					&& type.getSubranges().size() == otherArray.getSubranges().size();
		}
		return false;
	}

	static boolean isAssignableFrom(final StructuredType type, final DataType other) {
		// only strict name equality for structs or assignment to any_struct
		return PackageNameHelper.getFullTypeName(type).equalsIgnoreCase(PackageNameHelper.getFullTypeName(other))
				|| (GenericTypes.ANY_STRUCT == type && other instanceof StructuredType);
	}

	static boolean isAssignableFrom(final AnyElementaryType type, final DataType other) {
		return type == other || type.eClass().isSuperTypeOf(other.eClass());
	}

	static boolean isAssignableFrom(final AnyBitType type, final DataType other) {
		return type == other || type.eClass().isSuperTypeOf(other.eClass());
	}

	@SuppressWarnings("unused")
	static boolean isAssignableFrom(final BoolType type, final DataType other) {
		return other instanceof BoolType;
	}

	@SuppressWarnings("unused")
	static boolean isAssignableFrom(final ByteType type, final DataType other) {
		return other instanceof ByteType //
				|| other instanceof BoolType;
	}

	@SuppressWarnings("unused")
	static boolean isAssignableFrom(final WordType type, final DataType other) {
		return other instanceof WordType //
				|| other instanceof ByteType || other instanceof BoolType;
	}

	@SuppressWarnings("unused")
	static boolean isAssignableFrom(final DwordType type, final DataType other) {
		return other instanceof DwordType //
				|| other instanceof WordType || other instanceof ByteType || other instanceof BoolType;
	}

	@SuppressWarnings("unused")
	static boolean isAssignableFrom(final LwordType type, final DataType other) {
		return other instanceof LwordType //
				|| other instanceof DwordType || other instanceof WordType || other instanceof ByteType
				|| other instanceof BoolType;
	}

	static boolean isAssignableFrom(final AnyCharsType type, final DataType other) {
		return type == other || type.eClass().isSuperTypeOf(other.eClass());
	}

	static boolean isAssignableFrom(final AnySCharsType type, final DataType other) {
		return type == other || type.eClass().isSuperTypeOf(other.eClass());
	}

	static boolean isAssignableFrom(final AnyWCharsType type, final DataType other) {
		return type == other || type.eClass().isSuperTypeOf(other.eClass());
	}

	static boolean isAssignableFrom(final AnyCharType type, final DataType other) {
		return type == other || type.eClass().isSuperTypeOf(other.eClass());
	}

	@SuppressWarnings("unused")
	static boolean isAssignableFrom(final CharType type, final DataType other) {
		return other instanceof CharType;
	}

	@SuppressWarnings("unused")
	static boolean isAssignableFrom(final WcharType type, final DataType other) {
		return other instanceof WcharType;
	}

	static boolean isAssignableFrom(final AnyStringType type, final DataType other) {
		return type == other || type.eClass().isSuperTypeOf(other.eClass());
	}

	@SuppressWarnings("unused")
	static boolean isAssignableFrom(final StringType type, final DataType other) {
		return other instanceof StringType || other instanceof CharType;
	}

	@SuppressWarnings("unused")
	static boolean isAssignableFrom(final WstringType type, final DataType other) {
		return other instanceof WstringType || other instanceof WcharType;
	}

	static boolean isAssignableFrom(final AnyDateType type, final DataType other) {
		return type == other || type.eClass().isSuperTypeOf(other.eClass());
	}

	@SuppressWarnings("unused")
	static boolean isAssignableFrom(final DateType type, final DataType other) {
		return other instanceof DateType;
	}

	@SuppressWarnings("unused")
	static boolean isAssignableFrom(final LdateType type, final DataType other) {
		return other instanceof LdateType || other instanceof DateType;
	}

	@SuppressWarnings("unused")
	static boolean isAssignableFrom(final TimeOfDayType type, final DataType other) {
		return other instanceof TimeOfDayType;
	}

	@SuppressWarnings("unused")
	static boolean isAssignableFrom(final LtodType type, final DataType other) {
		return other instanceof LtodType || other instanceof TimeOfDayType;
	}

	@SuppressWarnings("unused")
	static boolean isAssignableFrom(final DateAndTimeType type, final DataType other) {
		return other instanceof DateAndTimeType;
	}

	@SuppressWarnings("unused")
	static boolean isAssignableFrom(final LdtType type, final DataType other) {
		return other instanceof LdtType || other instanceof DateAndTimeType;
	}

	static boolean isAssignableFrom(final AnyMagnitudeType type, final DataType other) {
		return type == other || type.eClass().isSuperTypeOf(other.eClass());
	}

	static boolean isAssignableFrom(final AnyDurationType type, final DataType other) {
		return type == other || type.eClass().isSuperTypeOf(other.eClass());
	}

	@SuppressWarnings("unused")
	static boolean isAssignableFrom(final TimeType type, final DataType other) {
		return other instanceof TimeType;
	}

	@SuppressWarnings("unused")
	static boolean isAssignableFrom(final LtimeType type, final DataType other) {
		return other instanceof LtimeType || other instanceof TimeType;
	}

	static boolean isAssignableFrom(final AnyNumType type, final DataType other) {
		return type == other || type.eClass().isSuperTypeOf(other.eClass());
	}

	static boolean isAssignableFrom(final AnyIntType type, final DataType other) {
		return type == other || type.eClass().isSuperTypeOf(other.eClass());
	}

	static boolean isAssignableFrom(final AnySignedType type, final DataType other) {
		return type == other || type.eClass().isSuperTypeOf(other.eClass());
	}

	@SuppressWarnings("unused")
	static boolean isAssignableFrom(final SintType type, final DataType other) {
		return other instanceof SintType;
	}

	@SuppressWarnings("unused")
	static boolean isAssignableFrom(final IntType type, final DataType other) {
		return other instanceof IntType //
				|| other instanceof SintType //
				|| other instanceof UsintType;
	}

	@SuppressWarnings("unused")
	static boolean isAssignableFrom(final DintType type, final DataType other) {
		return other instanceof DintType //
				|| other instanceof IntType || other instanceof SintType //
				|| other instanceof UintType || other instanceof UsintType;
	}

	@SuppressWarnings("unused")
	static boolean isAssignableFrom(final LintType type, final DataType other) {
		return other instanceof LintType //
				|| other instanceof DintType || other instanceof IntType || other instanceof SintType //
				|| other instanceof UdintType || other instanceof UintType || other instanceof UsintType;
	}

	static boolean isAssignableFrom(final AnyUnsignedType type, final DataType other) {
		return type == other || type.eClass().isSuperTypeOf(other.eClass());
	}

	@SuppressWarnings("unused")
	static boolean isAssignableFrom(final UsintType type, final DataType other) {
		return other instanceof UsintType;
	}

	@SuppressWarnings("unused")
	static boolean isAssignableFrom(final UintType type, final DataType other) {
		return other instanceof UintType //
				|| other instanceof UsintType;
	}

	@SuppressWarnings("unused")
	static boolean isAssignableFrom(final UdintType type, final DataType other) {
		return other instanceof UdintType //
				|| other instanceof UintType || other instanceof UsintType;
	}

	@SuppressWarnings("unused")
	static boolean isAssignableFrom(final UlintType type, final DataType other) {
		return other instanceof UlintType //
				|| other instanceof UdintType || other instanceof UintType || other instanceof UsintType;
	}

	static boolean isAssignableFrom(final AnyRealType type, final DataType other) {
		return type == other || type.eClass().isSuperTypeOf(other.eClass());
	}

	@SuppressWarnings("unused")
	static boolean isAssignableFrom(final RealType type, final DataType other) {
		return other instanceof RealType //
				|| other instanceof IntType || other instanceof SintType//
				|| other instanceof UintType || other instanceof UsintType;
	}

	@SuppressWarnings("unused")
	static boolean isAssignableFrom(final LrealType type, final DataType other) {
		return other instanceof LrealType || other instanceof RealType //
				|| other instanceof DintType || other instanceof IntType || other instanceof SintType//
				|| other instanceof UdintType || other instanceof UintType || other instanceof UsintType;
	}

	static boolean isAssignableFrom(final SubrangeType type, final DataType other) {
		if (type == other) {
			return true;
		}
		if (other instanceof final SubrangeType otherSubrange) {
			// subranges are assignable if the base type is assignable and they have a
			// subrange that fits
			return type.getBaseType().isAssignableFrom(otherSubrange.getBaseType())
					&& (!type.getSubrange().isSetLowerLimit()
							|| type.getSubrange().getLowerLimit() <= otherSubrange.getSubrange().getLowerLimit())
					&& (!type.getSubrange().isSetUpperLimit()
							|| type.getSubrange().getUpperLimit() >= otherSubrange.getSubrange().getUpperLimit());
		}
		return false;
	}

	static boolean isAssignableFrom(final EnumeratedType type, final DataType other) {
		// only strict name equality for enums
		return PackageNameHelper.getFullTypeName(type).equalsIgnoreCase(PackageNameHelper.getFullTypeName(other));
	}
}
