/**
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
 */
package org.eclipse.fordiac.ide.model.value;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.data.AnyDurationType;
import org.eclipse.fordiac.ide.model.data.AnyNumType;
import org.eclipse.fordiac.ide.model.data.ArrayType;
import org.eclipse.fordiac.ide.model.data.BoolType;
import org.eclipse.fordiac.ide.model.data.ByteType;
import org.eclipse.fordiac.ide.model.data.CharType;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.DateAndTimeType;
import org.eclipse.fordiac.ide.model.data.DateType;
import org.eclipse.fordiac.ide.model.data.DwordType;
import org.eclipse.fordiac.ide.model.data.EnumeratedType;
import org.eclipse.fordiac.ide.model.data.LdateType;
import org.eclipse.fordiac.ide.model.data.LdtType;
import org.eclipse.fordiac.ide.model.data.LtodType;
import org.eclipse.fordiac.ide.model.data.LwordType;
import org.eclipse.fordiac.ide.model.data.StringType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.data.TimeOfDayType;
import org.eclipse.fordiac.ide.model.data.WcharType;
import org.eclipse.fordiac.ide.model.data.WordType;
import org.eclipse.fordiac.ide.model.data.WstringType;
import org.eclipse.fordiac.ide.model.datatype.helper.TypeDeclarationParser;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;

public final class ValueConverterFactory {
	public static ValueConverter<?> createValueConverter(final DataType type) {
		return switch (type) {
		case final BoolType unused -> BoolValueConverter.INSTANCE;
		case final AnyNumType unused -> NumericValueConverter.INSTANCE;
		case final ByteType unused -> NumericValueConverter.INSTANCE_BYTE;
		case final WordType unused -> NumericValueConverter.INSTANCE_WORD;
		case final DwordType unused -> NumericValueConverter.INSTANCE_DWORD;
		case final LwordType unused -> NumericValueConverter.INSTANCE_LWORD;
		case final AnyDurationType unused -> TimeValueConverter.INSTANCE;
		case final CharType unused -> StringValueConverter.INSTANCE;
		case final StringType unused -> StringValueConverter.INSTANCE;
		case final WcharType unused -> WStringValueConverter.INSTANCE;
		case final WstringType unused -> WStringValueConverter.INSTANCE;
		case final DateType unused -> DateValueConverter.INSTANCE;
		case final LdateType unused -> DateValueConverter.INSTANCE;
		case final TimeOfDayType unused -> TimeOfDayValueConverter.INSTANCE;
		case final LtodType unused -> TimeOfDayValueConverter.INSTANCE;
		case final DateAndTimeType unused -> DateAndTimeValueConverter.INSTANCE;
		case final LdtType unused -> DateAndTimeValueConverter.INSTANCE;
		case final ArrayType arrayType -> new ArrayValueConverter<>(
				new TypedValueConverter(getElementType(arrayType), getDataTypeLibrary(arrayType)));
		case final StructuredType structuredType ->
			new StructValueConverter(name -> new TypedValueConverter(getMemberType(structuredType, name),
					getDataTypeLibrary(structuredType)));
		case final EnumeratedType enumeratedType -> new EnumValueConverter(enumeratedType);
		case null, default -> null;
		};
	}

	private static DataType getElementType(final ArrayType type) {
		if (type.getSubranges().size() > 1) {
			final ArrayType result = EcoreUtil.copy(type);
			result.getSubranges().removeFirst();
			return result;
		}
		return type.getBaseType();
	}

	private static DataType getMemberType(final StructuredType type, final String name) {
		final VarDeclaration member = type.getMemberVariables().stream().filter(v -> name.equals(v.getName()))
				.findFirst().orElseThrow();
		if (member.isArray()) {
			return TypeDeclarationParser.parseTypeDeclaration(member.getType(), member.getArraySize().getValue());
		}
		return member.getType();
	}

	private static DataTypeLibrary getDataTypeLibrary(final DataType type) {
		return type.getTypeLibrary() != null ? type.getTypeLibrary().getDataTypeLibrary() : null;
	}

	private ValueConverterFactory() {
		throw new IllegalStateException("Utility class should not be instantiated!"); //$NON-NLS-1$
	}
}
