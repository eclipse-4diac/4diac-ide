/*******************************************************************************
 * Copyright (c) 2018-2021 Johannes Kepler University, Primetals Technologies Germany GmbH
 * 				 2021, 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Jaeger - initial API and implementation and/or initial documentation
 *   Alois Zoitl	- added ANY_STRUCT to the generic type list
 *   Martin Melik Merkumians - adds DT and TOD
 *   Martin Melik Merkumians - changes arrays with types to immutable lists, and adds missing type keywords
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.datatype.helper;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.fordiac.ide.model.FordiacKeywords;
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
import org.eclipse.fordiac.ide.model.data.BoolType;
import org.eclipse.fordiac.ide.model.data.ByteType;
import org.eclipse.fordiac.ide.model.data.CharType;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.DateAndTimeType;
import org.eclipse.fordiac.ide.model.data.DateType;
import org.eclipse.fordiac.ide.model.data.DintType;
import org.eclipse.fordiac.ide.model.data.DwordType;
import org.eclipse.fordiac.ide.model.data.IntType;
import org.eclipse.fordiac.ide.model.data.InternalDataType;
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

public final class IecTypes {

	public static final class ElementaryTypes {

		public static final RealType REAL = DataFactory.eINSTANCE.createRealType();
		public static final LrealType LREAL = DataFactory.eINSTANCE.createLrealType();

		public static final UsintType USINT = DataFactory.eINSTANCE.createUsintType();
		public static final UintType UINT = DataFactory.eINSTANCE.createUintType();
		public static final UdintType UDINT = DataFactory.eINSTANCE.createUdintType();
		public static final UlintType ULINT = DataFactory.eINSTANCE.createUlintType();

		public static final SintType SINT = DataFactory.eINSTANCE.createSintType();
		public static final IntType INT = DataFactory.eINSTANCE.createIntType();
		public static final DintType DINT = DataFactory.eINSTANCE.createDintType();
		public static final LintType LINT = DataFactory.eINSTANCE.createLintType();

		public static final TimeType TIME = DataFactory.eINSTANCE.createTimeType();
		public static final LtimeType LTIME = DataFactory.eINSTANCE.createLtimeType();

		public static final BoolType BOOL = DataFactory.eINSTANCE.createBoolType();
		public static final ByteType BYTE = DataFactory.eINSTANCE.createByteType();
		public static final WordType WORD = DataFactory.eINSTANCE.createWordType();
		public static final DwordType DWORD = DataFactory.eINSTANCE.createDwordType();
		public static final LwordType LWORD = DataFactory.eINSTANCE.createLwordType();

		public static final StringType STRING = DataFactory.eINSTANCE.createStringType();
		public static final WstringType WSTRING = DataFactory.eINSTANCE.createWstringType();

		public static final CharType CHAR = DataFactory.eINSTANCE.createCharType();
		public static final WcharType WCHAR = DataFactory.eINSTANCE.createWcharType();

		public static final DateType DATE = DataFactory.eINSTANCE.createDateType();
		public static final LdateType LDATE = DataFactory.eINSTANCE.createLdateType();
		public static final DateAndTimeType DATE_AND_TIME = DataFactory.eINSTANCE.createDateAndTimeType();
		public static final DateAndTimeType DT = DataFactory.eINSTANCE.createDateAndTimeType();
		public static final LdtType LDATE_AND_TIME = DataFactory.eINSTANCE.createLdtType();
		public static final LdtType LDT = DataFactory.eINSTANCE.createLdtType();
		public static final TimeOfDayType TIME_OF_DAY = DataFactory.eINSTANCE.createTimeOfDayType();
		public static final TimeOfDayType TOD = DataFactory.eINSTANCE.createTimeOfDayType();
		public static final LtodType LTOD = DataFactory.eINSTANCE.createLtodType();
		public static final LtodType LTIME_OF_DAY = DataFactory.eINSTANCE.createLtodType();

		static {
			REAL.setName(FordiacKeywords.REAL);
			LREAL.setName(FordiacKeywords.LREAL);

			USINT.setName(FordiacKeywords.USINT);
			UINT.setName(FordiacKeywords.UINT);
			UDINT.setName(FordiacKeywords.UDINT);
			ULINT.setName(FordiacKeywords.ULINT);

			SINT.setName(FordiacKeywords.SINT);
			INT.setName(FordiacKeywords.INT);
			DINT.setName(FordiacKeywords.DINT);
			LINT.setName(FordiacKeywords.LINT);

			TIME.setName(FordiacKeywords.TIME);
			LTIME.setName(FordiacKeywords.LTIME);

			BOOL.setName(FordiacKeywords.BOOL);
			BYTE.setName(FordiacKeywords.BYTE);
			WORD.setName(FordiacKeywords.WORD);
			DWORD.setName(FordiacKeywords.DWORD);
			LWORD.setName(FordiacKeywords.LWORD);

			STRING.setName(FordiacKeywords.STRING);
			WSTRING.setName(FordiacKeywords.WSTRING);

			CHAR.setName(FordiacKeywords.CHAR);
			WCHAR.setName(FordiacKeywords.WCHAR);

			DATE.setName(FordiacKeywords.DATE);
			LDATE.setName(FordiacKeywords.LDATE);
			DATE_AND_TIME.setName(FordiacKeywords.DATE_AND_TIME);
			DT.setName(FordiacKeywords.DT);
			LDATE_AND_TIME.setName(FordiacKeywords.LDATE_AND_TIME);
			LDT.setName(FordiacKeywords.LDT);
			TIME_OF_DAY.setName(FordiacKeywords.TIME_OF_DAY);
			TOD.setName(FordiacKeywords.TOD);
			LTIME_OF_DAY.setName(FordiacKeywords.LTIME_OF_DAY);
			LTOD.setName(FordiacKeywords.LTOD);
		}

		private static final List<DataType> ANY_ELEMENTARY_TYPES = List.of(REAL, LREAL, USINT, UINT, UDINT, ULINT, SINT,
				INT, DINT, LINT, TIME, LTIME, BOOL, BYTE, WORD, DWORD, LWORD, STRING, WSTRING, CHAR, WCHAR, DATE, LDATE,
				DATE_AND_TIME, DT, LDATE_AND_TIME, LDT, TIME_OF_DAY, TOD, LTIME_OF_DAY, LTOD);

		public static List<DataType> getAllElementaryType() {
			return ANY_ELEMENTARY_TYPES;
		}

		public static DataType getTypeByName(final String name) {
			return getAllElementaryType().stream().filter(type -> type.getName().equalsIgnoreCase(name)).findFirst()
					.orElse(null);
		}

		static {
			ANY_ELEMENTARY_TYPES.forEach(IecTypes::addTypeToResource);
		}

		private ElementaryTypes() {
			throw new UnsupportedOperationException();
		}
	}

	public static final class GenericTypes {

		public static final AnyType ANY = DataFactory.eINSTANCE.createAnyType();
		public static final AnyElementaryType ANY_ELEMENTARY = DataFactory.eINSTANCE.createAnyElementaryType();
		public static final AnyDerivedType ANY_DERIVED = DataFactory.eINSTANCE.createAnyDerivedType();

		public static final AnyMagnitudeType ANY_MAGNITUDE = DataFactory.eINSTANCE.createAnyMagnitudeType();
		public static final AnyNumType ANY_NUM = DataFactory.eINSTANCE.createAnyNumType();

		public static final AnyRealType ANY_REAL = DataFactory.eINSTANCE.createAnyRealType();
		public static final AnyIntType ANY_INT = DataFactory.eINSTANCE.createAnyIntType();

		public static final AnyUnsignedType ANY_UNSIGNED = DataFactory.eINSTANCE.createAnyUnsignedType();
		public static final AnySignedType ANY_SIGNED = DataFactory.eINSTANCE.createAnySignedType();

		public static final AnyDurationType ANY_DURATION = DataFactory.eINSTANCE.createAnyDurationType();
		public static final AnyBitType ANY_BIT = DataFactory.eINSTANCE.createAnyBitType();

		public static final AnyCharsType ANY_CHARS = DataFactory.eINSTANCE.createAnyCharsType();
		public static final AnyCharsType ANY_SCHARS = DataFactory.eINSTANCE.createAnySCharsType();
		public static final AnyCharsType ANY_WCHARS = DataFactory.eINSTANCE.createAnyWCharsType();
		public static final AnyStringType ANY_STRING = DataFactory.eINSTANCE.createAnyStringType();
		public static final AnyCharType ANY_CHAR = DataFactory.eINSTANCE.createAnyCharType();

		public static final AnyDateType ANY_DATE = DataFactory.eINSTANCE.createAnyDateType();

		public static final StructuredType ANY_STRUCT = DataFactory.eINSTANCE.createStructuredType();

		static {
			ANY.setName(FordiacKeywords.ANY);
			ANY_ELEMENTARY.setName(FordiacKeywords.ANY_ELEMENTARY);
			ANY_DERIVED.setName(FordiacKeywords.ANY_DERIVED);

			ANY_MAGNITUDE.setName(FordiacKeywords.ANY_MAGNITUDE);
			ANY_NUM.setName(FordiacKeywords.ANY_NUM);

			ANY_REAL.setName(FordiacKeywords.ANY_REAL);
			ANY_INT.setName(FordiacKeywords.ANY_INT);

			ANY_UNSIGNED.setName(FordiacKeywords.ANY_UNSIGNED);
			ANY_SIGNED.setName(FordiacKeywords.ANY_SIGNED);

			ANY_DURATION.setName(FordiacKeywords.ANY_DURATION);
			ANY_BIT.setName(FordiacKeywords.ANY_BIT);

			ANY_CHARS.setName(FordiacKeywords.ANY_CHARS);
			ANY_SCHARS.setName(FordiacKeywords.ANY_CHARS);
			ANY_WCHARS.setName(FordiacKeywords.ANY_CHARS);
			ANY_STRING.setName(FordiacKeywords.ANY_STRING);
			ANY_CHAR.setName(FordiacKeywords.ANY_CHAR);
			ANY_DATE.setName(FordiacKeywords.ANY_DATE);

			ANY_STRUCT.setName(FordiacKeywords.ANY_STRUCT);

		}

		public static boolean isAnyType(final DataType type) {
			return ALL_GENERIC_TYPES.contains(type) || HIDDEN_GENERIC_TYPES.contains(type);
		}

		private static final List<DataType> ALL_GENERIC_TYPES = List.of(ANY, ANY_ELEMENTARY, ANY_DERIVED, ANY_MAGNITUDE,
				ANY_NUM, ANY_REAL, ANY_INT, ANY_UNSIGNED, ANY_SIGNED, ANY_DURATION, ANY_BIT, ANY_CHARS, ANY_STRING,
				ANY_CHAR, ANY_DATE, ANY_STRUCT);

		private static final List<DataType> HIDDEN_GENERIC_TYPES = List.of(ANY_SCHARS, ANY_WCHARS);

		public static List<DataType> getAllGenericTypes() {
			return ALL_GENERIC_TYPES;
		}

		static {
			ALL_GENERIC_TYPES.forEach(IecTypes::addTypeToResource);
			HIDDEN_GENERIC_TYPES.forEach(IecTypes::addTypeToResource);
		}

		private GenericTypes() {
			throw new UnsupportedOperationException();
		}

	}

	public static final class HelperTypes {
		public static final InternalDataType CDATA = DataFactory.eINSTANCE.createInternalDataType();

		static {
			CDATA.setName("CDATA"); //$NON-NLS-1$
			addTypeToResource(CDATA);
		}

		private HelperTypes() {
			throw new UnsupportedOperationException();
		}
	}

	private static final String IEC_DATA_LIB_URI = "data.lib"; //$NON-NLS-1$
	private static final Resource IEC_TYPES_RES = new ResourceImpl(URI.createURI(IEC_DATA_LIB_URI));

	private static boolean addTypeToResource(final DataType type) {
		return IEC_TYPES_RES.getContents().add(type);
	}

	private IecTypes() {
		throw new UnsupportedOperationException();
	}

}
