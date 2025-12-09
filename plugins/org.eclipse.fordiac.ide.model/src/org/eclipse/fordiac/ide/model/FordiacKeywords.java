/********************************************************************************
 * Copyright (c) 2020, 2023 Johannes Kepler University Linz
 *                          Primetals Technologies Austria GmbH
 *                          Martin Erich Jobst
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
 *  Martin Jobst
 *    - add allowed contexts
 ********************************************************************************/
package org.eclipse.fordiac.ide.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterface;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public final class FordiacKeywords {

	@SuppressWarnings("squid:S2386") // This is a final, immutable Set
	public static final Set<String> RESERVED_ST_KEYWORDS = getKeywordsOfType(KeywordTypes.STRUCTURED_TEXT);

	@SuppressWarnings("squid:S2386") // This is a final, immutable Set
	public static final Set<String> RESERVED_DATATYPE_KEYWORDS = getKeywordsOfType(KeywordTypes.DATATYPE);

	@SuppressWarnings("squid:S2386") // This is a final, immutable Set
	public static final Set<String> RESERVED_DATATYPE_CLASS_KEYWORDS = getKeywordsOfType(KeywordTypes.DATATYPE_CLASS);

	@SuppressWarnings("squid:S2386") // This is a final, immutable Set
	public static final Set<String> RESERVED_TIME_UNIT_KEYWORDS = getKeywordsOfType(KeywordTypes.TIME_UNIT);

	@SuppressWarnings("squid:S2386") // This is a final, immutable Set
	public static final Set<String> RESERVED_KEYWORDS = Stream.of(RESERVED_ST_KEYWORDS, RESERVED_DATATYPE_KEYWORDS,
			RESERVED_DATATYPE_CLASS_KEYWORDS, RESERVED_TIME_UNIT_KEYWORDS).flatMap(Set::stream)
			.collect(Collectors.toUnmodifiableSet());

	public static final Map<String, AllowedContexts> ALLOWED_CONTEXTS = Stream
			.of(FordiacKeywords.class.getDeclaredFields())
			.filter(f -> f.getAnnotation(AllowedContexts.class) != null
					&& f.getAnnotation(AllowedContexts.class).value() != null)
			.collect(Collectors.toUnmodifiableMap(FordiacKeywords::getName, FordiacKeywords::getAllowedContexts));

	public enum KeywordTypes {
		DATATYPE, DATATYPE_CLASS, TIME_UNIT, STRUCTURED_TEXT
	}

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
	@Keyword(KeywordTypes.DATATYPE_CLASS)
	public static final String ANY_SCHARS = "ANY_SCHARS"; //$NON-NLS-1$
	@Keyword(KeywordTypes.DATATYPE_CLASS)
	public static final String ANY_WCHARS = "ANY_WCHARS"; //$NON-NLS-1$

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
	@AllowedContexts(VarDeclaration.class) // allow for IEC 61499 standard blocks
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
	@AllowedContexts(VarDeclaration.class) // allow for IEC 61499 standard blocks
	public static final String DAY = "D"; //$NON-NLS-1$
	@Keyword(KeywordTypes.TIME_UNIT)
	public static final String HOUR = "H"; //$NON-NLS-1$
	@Keyword(KeywordTypes.TIME_UNIT)
	public static final String MINUTE = "M"; //$NON-NLS-1$
	@Keyword(KeywordTypes.TIME_UNIT)
	@AllowedContexts({ VarDeclaration.class, Event.class }) // allow for IEC 61499 standard blocks
	public static final String SECOND = "S"; //$NON-NLS-1$
	@Keyword(KeywordTypes.TIME_UNIT)
	public static final String MILLISECOND = "MS"; //$NON-NLS-1$
	@Keyword(KeywordTypes.TIME_UNIT)
	public static final String MICROSECOND = "US"; //$NON-NLS-1$
	@Keyword(KeywordTypes.TIME_UNIT)
	public static final String NANOSECOND = "NS"; //$NON-NLS-1$

	/** @brief Keep in sync with ST editor keywords */
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String ABS = "ABS"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String ABSTRACT = "ABSTRACT"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String ACOS = "ACOS"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String ACTION = "ACTION"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String ADD = "ADD"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String ALGORITHM = "ALGORITHM"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String AND = "AND"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String ARRAY = "ARRAY"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String ASIN = "ASIN"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String AT = "AT"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String ATAN = "ATAN"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String ATAN2 = "ATAN2"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String BY = "BY"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String CASE = "CASE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String CLASS = "CLASS"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String CONCAT = "CONCAT"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String CONFIGURATION = "CONFIGURATION"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String CONSTANT = "CONSTANT"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String CONTINUE = "CONTINUE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String COS = "COS"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String CTD = "CTD"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String CTU = "CTU"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String CTUD = "CTUD"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String DELETE = "DELETE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String DIV = "DIV"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String DO = "DO"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String EXPONENT = "E"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String ELSE = "ELSE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String ELSIF = "ELSIF"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String END_ACTION = "END_ACTION"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String END_ALGORITHM = "END_ALGORITHM"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String END_CASE = "END_CASE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String END_CLASS = "END_CLASS"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String END_CONFIGURATION = "END_CONFIGURATION"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String END_FOR = "END_FOR"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String END_FUNCTION = "END_FUNCTION"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String END_FUNCTION_BLOCK = "END_FUNCTION_BLOCK"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String END_INTERFACE = "END_INTERFACE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String END_IF = "END_IF"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String END_METHOD = "END_METHOD"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String END_NAMESPACE = "END_NAMESPACE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String END_PROGRAM = "END_PROGRAM"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String END_REPEAT = "END_REPEAT"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String END_RESOURCE = "END_RESOURCE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String END_STEP = "END_STEP"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String END_STRUCT = "END_STRUCT"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String END_TRANSITION = "END_TRANSITION"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String END_TYPE = "END_TYPE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String END_VAR = "END_VAR"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String END_WHILE = "END_WHILE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String EQ = "EQ"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String EXIT = "EXIT"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String EXP = "EXP"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String EXPT = "EXPT"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String EXTENDS = "EXTENDS"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String F_EDGE = "F_EDGE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String F_TRIG = "F_TRIG"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String FALSE = "FALSE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String FINAL = "FINAL"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String FIND = "FIND"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String FOR = "FOR"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String FROM = "FROM"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String FUNCTION = "FUNCTION"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String FUNCTION_BLOCK = "FUNCTION_BLOCK"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String GE = "GE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String GT = "GT"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String IF = "IF"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String IMPLEMENTS = "IMPLEMENTS"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String INITIAL_STEP = "INITIAL_STEP"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String INSERT = "INSERT"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String INTERFACE = "INTERFACE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String INTERNAL = "INTERNAL"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String INTERVAL = "INTERVAL"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String LE = "LE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String LT = "LT"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String LEFT = "LEFT"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String LEN = "LEN"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String LIMIT = "LIMIT"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String LN = "LN"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String LOG = "LOG"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String MAX = "MAX"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String METHOD = "METHOD"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String MID = "MID"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String MIN = "MIN"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String MOD = "MOD"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String MOVE = "MOVE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String MUL = "MUL"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String MUX = "MUX"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String NAMESPACE = "NAMESPACE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String NE = "NE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String NON_RETAIN = "NON_RETAIN"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String NOT = "NOT"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String NULL = "NULL"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String OF = "OF"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String ON = "ON"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String OR = "OR"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String OVERLAP = "OVERLAP"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String OVERRIDE = "OVERRIDE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String PRIORITY = "PRIORITY"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String PRIVATE = "PRIVATE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String PROGRAM = "PROGRAM"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String PROTECTED = "PROTECTED"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String PUBLIC = "PUBLIC"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String R_EDGE = "R_EDGE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String R_TRIG = "R_TRIG"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String READ_ONLY = "READ_ONLY"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String READ_WRITE = "READ_WRITE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String REF = "REF"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String REF_TO = "REF_TO"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String REPEAT = "REPEAT"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String REPLACE = "REPLACE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	@AllowedContexts(ServiceInterface.class) // allowed for service sequences
	public static final String RESOURCE = "RESOURCE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String RETAIN = "RETAIN"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String RETURN = "RETURN"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String RIGHT = "RIGHT"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String ROL = "ROL"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String ROR = "ROR"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String RS = "RS"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String SEL = "SEL"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String SHL = "SHL"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String SHR = "SHR"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String SIN = "SIN"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String SINGLE = "SINGLE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String SQRT = "SQRT"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String SR = "SR"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String STEP = "STEP"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String STRUCT = "STRUCT"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String SUB = "SUB"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String SUPER = "SUPER"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String T = "T"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String TAN = "TAN"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String TASK = "TASK"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String THEN = "THEN"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String THIS = "THIS"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String TO = "TO"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String TOF = "TOF"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String TON = "TON"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String TP = "TP"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String TRANSITION = "TRANSITION"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String TRUE = "TRUE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String TRUNC = "TRUNC"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String TYPE = "TYPE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String UNTIL = "UNTIL"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String USING = "USING"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String VAR = "VAR"; //$NON-NLS-1$
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
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String WHILE = "WHILE"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String WITH = "WITH"; //$NON-NLS-1$
	@Keyword(KeywordTypes.STRUCTURED_TEXT)
	public static final String XOR = "XOR"; //$NON-NLS-1$

	@ModelString
	public static final String VARIABLE_INTERNAL = "InternalVar1"; //$NON-NLS-1$

	@ModelString
	public static final String VARIABLE_INTERNAL_CONST = "InternalConstVar1"; //$NON-NLS-1$

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
	public static final String VARINOUT = "VARINOUT1"; //$NON-NLS-1$

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
	private @interface AllowedContexts {
		Class<?>[] value();
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	private @interface ModelString {
		// Used only for annotation
	}

	private static String getName(final Field f) {
		try {
			return (String) f.get(null); // Static field, so get with null
		} catch (final IllegalArgumentException | IllegalAccessException e) {
			FordiacLogHelper.logError("Exception in keyword access", e); //$NON-NLS-1$
			throw new IllegalStateException(e);
		}
	}

	private static AllowedContexts getAllowedContexts(final Field f) {
		try {
			return f.getAnnotation(AllowedContexts.class);
		} catch (final IllegalArgumentException e) {
			FordiacLogHelper.logError("Exception in allowed context access", e); //$NON-NLS-1$
			throw new IllegalStateException(e);
		}
	}

	private static Set<String> getKeywordsOfType(final KeywordTypes type) {
		return List.of(FordiacKeywords.class.getDeclaredFields()).stream()
				.filter(field -> isKeyword(field) && field.getAnnotation(Keyword.class).value() == type)
				.map(FordiacKeywords::getName).collect(Collectors.toUnmodifiableSet());
	}

	public static boolean isReservedKeyword(final String nameProposal) {
		return RESERVED_KEYWORDS.contains(nameProposal.toUpperCase());
	}

	public static boolean isReservedKeyword(final String name, final Object context) {
		if (context == null) {
			return isReservedKeyword(name);
		}

		final String upperCaseName = name.toUpperCase();
		return RESERVED_KEYWORDS.contains(upperCaseName) && !matchesAllowedContext(upperCaseName, context);
	}

	private static boolean isKeyword(final Field field) {
		return field.getType().equals(String.class) && field.isAnnotationPresent(Keyword.class);
	}

	private static boolean matchesAllowedContext(final String upperCaseName, final Object context) {
		final var allowedContexts = ALLOWED_CONTEXTS.get(upperCaseName);
		return allowedContexts != null
				&& Stream.of(allowedContexts.value()).anyMatch(value -> value.isAssignableFrom(context.getClass()));
	}
}
