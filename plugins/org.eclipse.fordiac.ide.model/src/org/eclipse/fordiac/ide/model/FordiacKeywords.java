/********************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 * 				 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Ernst Blecha
 *    - initial API and implementation and/or initial documentation
 *  Martin Melik Merkumians - adds DT keyword
 ********************************************************************************/
package org.eclipse.fordiac.ide.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Set;
import java.util.TreeSet;

public final class FordiacKeywords {

	@SuppressWarnings("squid:S2386") // This is a final, immutable Set
	public static final Set<String> RESERVED_KEYWORDS = getKeywords();

	public enum KeywordTypes {
		DATATYPE, DATATYPE_CLASS, TIME_UNIT, STRUCTURED_TEXT
	}

	@Keyword(KeywordTypes.DATATYPE_CLASS)
	public static final String ANY_ADAPTER = "ANY_ADAPTER"; //$NON-NLS-1$
	@Keyword(KeywordTypes.DATATYPE_CLASS)
	public static final String ANY_DATE = "ANY_DATE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.DATATYPE_CLASS)
	public static final String ANY_STRING = "ANY_STRING"; //$NON-NLS-1$
	@Keyword(KeywordTypes.DATATYPE_CLASS)
	public static final String ANY_BIT = "ANY_BIT"; //$NON-NLS-1$
	@Keyword(KeywordTypes.DATATYPE_CLASS)
	public static final String ANY_REAL = "ANY_REAL"; //$NON-NLS-1$
	@Keyword(KeywordTypes.DATATYPE_CLASS)
	public static final String ANY_INT = "ANY_INT"; //$NON-NLS-1$
	@Keyword(KeywordTypes.DATATYPE_CLASS)
	public static final String ANY_NUM = "ANY_NUM"; //$NON-NLS-1$
	@Keyword(KeywordTypes.DATATYPE_CLASS)
	public static final String ANY_MAGNITUDE = "ANY_MAGNITUDE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.DATATYPE_CLASS)
	public static final String ANY_ELEMENTARY = "ANY_ELEMENTARY"; //$NON-NLS-1$
	@Keyword(KeywordTypes.DATATYPE_CLASS)
	public static final String ANY = "ANY"; //$NON-NLS-1$
	@Keyword(KeywordTypes.DATATYPE_CLASS)
	public static final String ANY_STRUCT = "ANY_STRUCT"; //$NON-NLS-1$
	@Keyword(KeywordTypes.DATATYPE_CLASS)
	public static final String ANY_DERIVED = "ANY_DERIVED"; //$NON-NLS-1$
	@Keyword(KeywordTypes.DATATYPE_CLASS)
	public static final String ANY_SIGNED = "ANY_SIGNED"; //$NON-NLS-1$
	@Keyword(KeywordTypes.DATATYPE_CLASS)
	public static final String ANY_UNSIGNED = "ANY_UNSIGNED"; //$NON-NLS-1$
	@Keyword(KeywordTypes.DATATYPE_CLASS)
	public static final String ANY_DURATION = "ANY_DURATION"; //$NON-NLS-1$
	@Keyword(KeywordTypes.DATATYPE_CLASS)
	public static final String ANY_CHAR = "ANY_CHAR"; //$NON-NLS-1$
	@Keyword(KeywordTypes.DATATYPE_CLASS)
	public static final String ANY_CHARS = "ANY_CHARS"; //$NON-NLS-1$

	@Keyword(KeywordTypes.DATATYPE)
	public static final String DINT = "DINT"; //$NON-NLS-1$
	@Keyword(KeywordTypes.DATATYPE)
	public static final String INT = "INT"; //$NON-NLS-1$
	@Keyword(KeywordTypes.DATATYPE)
	public static final String SINT = "SINT"; //$NON-NLS-1$
	@Keyword(KeywordTypes.DATATYPE)
	public static final String LINT = "LINT"; //$NON-NLS-1$
	@Keyword(KeywordTypes.DATATYPE)
	public static final String UINT = "UINT"; //$NON-NLS-1$
	@Keyword(KeywordTypes.DATATYPE)
	public static final String USINT = "USINT"; //$NON-NLS-1$
	@Keyword(KeywordTypes.DATATYPE)
	public static final String UDINT = "UDINT"; //$NON-NLS-1$
	@Keyword(KeywordTypes.DATATYPE)
	public static final String ULINT = "ULINT"; //$NON-NLS-1$
	@Keyword(KeywordTypes.DATATYPE)
	public static final String REAL = "REAL"; //$NON-NLS-1$
	@Keyword(KeywordTypes.DATATYPE)
	public static final String LREAL = "LREAL"; //$NON-NLS-1$
	@Keyword(KeywordTypes.DATATYPE)
	public static final String STRING = "STRING"; //$NON-NLS-1$
	@Keyword(KeywordTypes.DATATYPE)
	public static final String WSTRING = "WSTRING"; //$NON-NLS-1$
	@Keyword(KeywordTypes.DATATYPE)
	public static final String CHAR = "CHAR"; //$NON-NLS-1$
	@Keyword(KeywordTypes.DATATYPE)
	public static final String WCHAR = "WCHAR"; //$NON-NLS-1$
	@Keyword(KeywordTypes.DATATYPE)
	public static final String ANY_TIME = "ANY_TIME"; //$NON-NLS-1$
	@Keyword(KeywordTypes.DATATYPE)
	public static final String TIME = "TIME"; //$NON-NLS-1$
	@Keyword(KeywordTypes.DATATYPE)
	public static final String LTIME = "LTIME"; //$NON-NLS-1$
	@Keyword(KeywordTypes.DATATYPE)
	public static final String TIME_OF_DAY = "TIME_OF_DAY"; //$NON-NLS-1$
	@Keyword(KeywordTypes.DATATYPE)
	public static final String LTIME_OF_DAY = "LTIME_OF_DAY"; //$NON-NLS-1$
	@Keyword(KeywordTypes.DATATYPE)
	public static final String TOD = "TOD"; //$NON-NLS-1$
	@Keyword(KeywordTypes.DATATYPE)
	public static final String LTOD = "LTOD"; //$NON-NLS-1$
	@Keyword(KeywordTypes.DATATYPE)
	public static final String DATE = "DATE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.DATATYPE)
	public static final String LDATE = "LDATE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.DATATYPE)
	public static final String DATE_AND_TIME = "DATE_AND_TIME"; //$NON-NLS-1$
	@Keyword(KeywordTypes.DATATYPE)
	public static final String DT = "DT"; //$NON-NLS-1$
	@Keyword(KeywordTypes.DATATYPE)
	public static final String LDATE_AND_TIME = "LDATE_AND_TIME"; //$NON-NLS-1$
	@Keyword(KeywordTypes.DATATYPE)
	public static final String LDT = "LDT";//$NON-NLS-1$
	@Keyword(KeywordTypes.DATATYPE)
	public static final String BOOL = "BOOL"; //$NON-NLS-1$
	@Keyword(KeywordTypes.DATATYPE)
	public static final String BYTE = "BYTE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.DATATYPE)
	public static final String WORD = "WORD"; //$NON-NLS-1$
	@Keyword(KeywordTypes.DATATYPE)
	public static final String DWORD = "DWORD"; //$NON-NLS-1$
	@Keyword(KeywordTypes.DATATYPE)
	public static final String LWORD = "LWORD"; //$NON-NLS-1$

	@Keyword(KeywordTypes.TIME_UNIT)
	public static final String DAY = "D"; //$NON-NLS-1$
	@Keyword(KeywordTypes.TIME_UNIT)
	public static final String HOUR = "H"; //$NON-NLS-1$
	@Keyword(KeywordTypes.TIME_UNIT)
	public static final String MINUTE = "M"; //$NON-NLS-1$
	@Keyword(KeywordTypes.TIME_UNIT)
	public static final String SECOND = "S"; //$NON-NLS-1$
	@Keyword(KeywordTypes.TIME_UNIT)
	public static final String MILLISECOND = "MS"; //$NON-NLS-1$
	@Keyword(KeywordTypes.TIME_UNIT)
	public static final String MICROSECOND = "US"; //$NON-NLS-1$
	@Keyword(KeywordTypes.TIME_UNIT)
	public static final String NANOSECOND = "NS"; //$NON-NLS-1$

	/** @brief Keep in sync with ST editor keywords */
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String EXPONENT = "E"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String VAR = "VAR"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String END_VAR = "END_VAR"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String CONSTANT = "CONSTANT"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String SUPER = "SUPER"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String RETURN = "RETURN"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String IF = "IF"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String THEN = "THEN"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String END_IF = "END_IF"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String ELSIF = "ELSIF"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String ELSE = "ELSE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String CASE = "CASE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String OF = "OF"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String END_CASE = "END_CASE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String EXIT = "EXIT"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String CONTINUE = "CONTINUE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String FOR = "FOR"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String TO = "TO"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String BY = "BY"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String DO = "DO"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String END_FOR = "END_FOR"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String WHILE = "WHILE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String END_WHILE = "END_WHILE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String REPEAT = "REPEAT"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String UNTIL = "UNTIL"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String END_REPEAT = "END_REPEAT"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String OR = "OR"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String XOR = "XOR"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String AND = "AND"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String MOD = "MOD"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String NOT = "NOT"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String ABSTRACT = "ABSTRACT;"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String ALGORITHM = "ALGORITHM"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String END_ALGORITHM = "END_ALGORITHM"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String ACTION = "ACTION"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String END_ACTION = "END_ACTION"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String CLASS = "CLASS"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String END_CLASS = "END_CLASS"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String CONFIGURATION = "CONFIGURATION"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String END_CONFIGURATION = "END_CONFIGURATION"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String FUNCTION = "FUNCTION"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String END_FUNCTION = "END_FUNCTION"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String FUNCTION_BLOCK = "FUNCTION_BLOCK"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String END_FUNCTION_BLOCK = "END_FUNCTION_BLOCK"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String INTERFACE = "INTERFACE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String END_INTERFACE = "END_INTERFACE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String METHOD = "METHOD"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String END_METHOD = "END_METHOD"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String NAMESPACE = "NAMESPACE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String END_NAMESPACE = "END_NAMESPACE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String PROGRAM = "PROGRAM"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String END_PROGRAM = "END_PROGRAM"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String RESOURCE = "RESOURCE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String END_RESOURCE = "END_RESOURCE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String STEP = "STEP"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String END_STEP = "END_STEP"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String STRUCT = "STRUCT"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String END_STRUCT = "END_STRUCT"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String TRANSITION = "TRANSITION"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String END_TRANSITION = "END_TRANSITION"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String TYPE = "TYPE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String END_TYPE = "END_TYPE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String EXTENDS = "EXTENDS"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String FINAL = "FINAL"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String IMPLEMENTS = "IMPLEMENTS"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String INITIAL_STEP = "INITIAL_STEP"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String INTERVAL = "INTERVAL"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String RETAIN = "RETAIN"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String NON_RETAIN = "NON_RETAIN"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String NULL = "NULL"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String ON = "ON"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String OVERLAP = "OVERLAP"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String OVERRIDE = "OVERRIDE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String PRIORITY = "PRIORITY"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String READ_ONLY = "READ_ONLY"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String READ_WRITE = "READ_WRITE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String REF = "REF"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String REF_TO = "REF_TO"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String SINGLE = "SINGLE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String THIS = "THIS"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String VAR_ACCESS = "VAR_ACCESS"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String VAR_CONFIG = "VAR_CONFIG"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String VAR_EXTERNAL = "VAR_EXTERNAL"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String VAR_GLOBAL = "VAR_GLOBAL"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String VAR_INPUT = "VAR_INPUT"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String VAR_IN_OUT = "VAR_IN_OUT"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String VAR_OUTPUT = "VAR_OUTPUT"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String VAR_TEMP = "VAR_TEMP"; //$NON-NLS-1$

	@ModelString
	public static final String VARIABLE_INTERNAL = "InternalVar1"; //$NON-NLS-1$

	@ModelString
	public static final String EVENT_INPUT = "EI1"; //$NON-NLS-1$
	@ModelString
	public static final String EVENT_OUTPUT = "EO1"; //$NON-NLS-1$

	@ModelString
	public static final String DATA_INPUT = "DI1"; //$NON-NLS-1$
	@ModelString
	public static final String DATA_OUTPUT = "DO1"; //$NON-NLS-1$

	@ModelString
	public static final String ADAPTER_SOCKET = "SOCKET1"; //$NON-NLS-1$
	@ModelString
	public static final String ADAPTER_PLUG = "PLUG1"; //$NON-NLS-1$

	@ModelString
	public static final String INTERFACE_Y_POSITION = "YPOSITION"; //$NON-NLS-1$


	private FordiacKeywords() {
		throw new UnsupportedOperationException();
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	private @interface Keyword {
		KeywordTypes value();
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	private @interface ModelString {
		// Used only for annotation
	}

	private static Set<String> getKeywords() {
		final Class<?> clazz = FordiacKeywords.class;

		final Set<String> keywordSet = new TreeSet<>();

		for (final Field field : clazz.getDeclaredFields()) {
			if (field.getType().equals(String.class) && field.isAnnotationPresent(Keyword.class)) {
				try {
					keywordSet.add((String) field.get(null));
				} catch (final Exception e) {
					throw new RuntimeException(e);// NOSONAR
				}
			}
		}

		return Set.copyOf(keywordSet);
	}

}
