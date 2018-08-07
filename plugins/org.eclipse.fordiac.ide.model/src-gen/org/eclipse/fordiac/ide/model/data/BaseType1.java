/********************************************************************************
 * Copyright (c) 2008, 2010, 2012 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Gerhard Ebenhofer, Alois Zoitl, Monika Wenger, Martin Jobst
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.data;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Base Type1</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.fordiac.ide.model.data.DataPackage#getBaseType1()
 * @model
 * @generated
 */
public enum BaseType1 implements Enumerator {
	/**
	 * The '<em><b>DATEANDTIME</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DATEANDTIME_VALUE
	 * @generated
	 * @ordered
	 */
	DATEANDTIME(0, "DATEANDTIME", "DATE_AND_TIME"),

	/**
	 * The '<em><b>BYTE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BYTE_VALUE
	 * @generated
	 * @ordered
	 */
	BYTE(1, "BYTE", "BYTE"),

	/**
	 * The '<em><b>SINT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SINT_VALUE
	 * @generated
	 * @ordered
	 */
	SINT(2, "SINT", "SINT"),

	/**
	 * The '<em><b>USINT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #USINT_VALUE
	 * @generated
	 * @ordered
	 */
	USINT(3, "USINT", "USINT"),

	/**
	 * The '<em><b>LWORD</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LWORD_VALUE
	 * @generated
	 * @ordered
	 */
	LWORD(4, "LWORD", "LWORD"),

	/**
	 * The '<em><b>TIME</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TIME_VALUE
	 * @generated
	 * @ordered
	 */
	TIME(5, "TIME", "TIME"),

	/**
	 * The '<em><b>WORD</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #WORD_VALUE
	 * @generated
	 * @ordered
	 */
	WORD(6, "WORD", "WORD"),

	/**
	 * The '<em><b>STRING</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #STRING_VALUE
	 * @generated
	 * @ordered
	 */
	STRING(7, "STRING", "STRING"),

	/**
	 * The '<em><b>BOOL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BOOL_VALUE
	 * @generated
	 * @ordered
	 */
	BOOL(8, "BOOL", "BOOL"),

	/**
	 * The '<em><b>LREAL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LREAL_VALUE
	 * @generated
	 * @ordered
	 */
	LREAL(9, "LREAL", "LREAL"),

	/**
	 * The '<em><b>REAL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #REAL_VALUE
	 * @generated
	 * @ordered
	 */
	REAL(10, "REAL", "REAL"),

	/**
	 * The '<em><b>LINT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LINT_VALUE
	 * @generated
	 * @ordered
	 */
	LINT(11, "LINT", "LINT"),

	/**
	 * The '<em><b>ULINT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ULINT_VALUE
	 * @generated
	 * @ordered
	 */
	ULINT(12, "ULINT", "ULINT"),

	/**
	 * The '<em><b>UINT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #UINT_VALUE
	 * @generated
	 * @ordered
	 */
	UINT(13, "UINT", "UINT"),

	/**
	 * The '<em><b>DATE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DATE_VALUE
	 * @generated
	 * @ordered
	 */
	DATE(14, "DATE", "DATE"),

	/**
	 * The '<em><b>DWORD</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DWORD_VALUE
	 * @generated
	 * @ordered
	 */
	DWORD(15, "DWORD", "DWORD"),

	/**
	 * The '<em><b>INT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #INT_VALUE
	 * @generated
	 * @ordered
	 */
	INT(16, "INT", "INT"),

	/**
	 * The '<em><b>TIMEOFDAY</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TIMEOFDAY_VALUE
	 * @generated
	 * @ordered
	 */
	TIMEOFDAY(17, "TIMEOFDAY", "TIME_OF_DAY"),

	/**
	 * The '<em><b>WSTRING</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #WSTRING_VALUE
	 * @generated
	 * @ordered
	 */
	WSTRING(18, "WSTRING", "WSTRING"),

	/**
	 * The '<em><b>DINT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DINT_VALUE
	 * @generated
	 * @ordered
	 */
	DINT(19, "DINT", "DINT"),

	/**
	 * The '<em><b>UDINT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #UDINT_VALUE
	 * @generated
	 * @ordered
	 */
	UDINT(20, "UDINT", "UDINT"),

	/**
	 * The '<em><b>ANY</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ANY_VALUE
	 * @generated
	 * @ordered
	 */
	ANY(21, "ANY", "ANY");

	/**
	 * The '<em><b>DATEANDTIME</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>DATEANDTIME</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DATEANDTIME
	 * @model literal="DATE_AND_TIME"
	 * @generated
	 * @ordered
	 */
	public static final int DATEANDTIME_VALUE = 0;

	/**
	 * The '<em><b>BYTE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>BYTE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #BYTE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int BYTE_VALUE = 1;

	/**
	 * The '<em><b>SINT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>SINT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SINT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int SINT_VALUE = 2;

	/**
	 * The '<em><b>USINT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>USINT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #USINT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int USINT_VALUE = 3;

	/**
	 * The '<em><b>LWORD</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>LWORD</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #LWORD
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int LWORD_VALUE = 4;

	/**
	 * The '<em><b>TIME</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>TIME</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #TIME
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int TIME_VALUE = 5;

	/**
	 * The '<em><b>WORD</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>WORD</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #WORD
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int WORD_VALUE = 6;

	/**
	 * The '<em><b>STRING</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>STRING</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #STRING
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int STRING_VALUE = 7;

	/**
	 * The '<em><b>BOOL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>BOOL</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #BOOL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int BOOL_VALUE = 8;

	/**
	 * The '<em><b>LREAL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>LREAL</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #LREAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int LREAL_VALUE = 9;

	/**
	 * The '<em><b>REAL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>REAL</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #REAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int REAL_VALUE = 10;

	/**
	 * The '<em><b>LINT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>LINT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #LINT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int LINT_VALUE = 11;

	/**
	 * The '<em><b>ULINT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>ULINT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ULINT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int ULINT_VALUE = 12;

	/**
	 * The '<em><b>UINT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>UINT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #UINT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int UINT_VALUE = 13;

	/**
	 * The '<em><b>DATE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>DATE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DATE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int DATE_VALUE = 14;

	/**
	 * The '<em><b>DWORD</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>DWORD</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DWORD
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int DWORD_VALUE = 15;

	/**
	 * The '<em><b>INT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>INT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #INT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int INT_VALUE = 16;

	/**
	 * The '<em><b>TIMEOFDAY</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>TIMEOFDAY</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #TIMEOFDAY
	 * @model literal="TIME_OF_DAY"
	 * @generated
	 * @ordered
	 */
	public static final int TIMEOFDAY_VALUE = 17;

	/**
	 * The '<em><b>WSTRING</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>WSTRING</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #WSTRING
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int WSTRING_VALUE = 18;

	/**
	 * The '<em><b>DINT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>DINT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DINT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int DINT_VALUE = 19;

	/**
	 * The '<em><b>UDINT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>UDINT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #UDINT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int UDINT_VALUE = 20;

	/**
	 * The '<em><b>ANY</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>ANY</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ANY
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int ANY_VALUE = 21;

	/**
	 * An array of all the '<em><b>Base Type1</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final BaseType1[] VALUES_ARRAY =
		new BaseType1[] {
			DATEANDTIME,
			BYTE,
			SINT,
			USINT,
			LWORD,
			TIME,
			WORD,
			STRING,
			BOOL,
			LREAL,
			REAL,
			LINT,
			ULINT,
			UINT,
			DATE,
			DWORD,
			INT,
			TIMEOFDAY,
			WSTRING,
			DINT,
			UDINT,
			ANY,
		};

	/**
	 * A public read-only list of all the '<em><b>Base Type1</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<BaseType1> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Base Type1</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static BaseType1 get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			BaseType1 result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Base Type1</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static BaseType1 getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			BaseType1 result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Base Type1</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static BaseType1 get(int value) {
		switch (value) {
			case DATEANDTIME_VALUE: return DATEANDTIME;
			case BYTE_VALUE: return BYTE;
			case SINT_VALUE: return SINT;
			case USINT_VALUE: return USINT;
			case LWORD_VALUE: return LWORD;
			case TIME_VALUE: return TIME;
			case WORD_VALUE: return WORD;
			case STRING_VALUE: return STRING;
			case BOOL_VALUE: return BOOL;
			case LREAL_VALUE: return LREAL;
			case REAL_VALUE: return REAL;
			case LINT_VALUE: return LINT;
			case ULINT_VALUE: return ULINT;
			case UINT_VALUE: return UINT;
			case DATE_VALUE: return DATE;
			case DWORD_VALUE: return DWORD;
			case INT_VALUE: return INT;
			case TIMEOFDAY_VALUE: return TIMEOFDAY;
			case WSTRING_VALUE: return WSTRING;
			case DINT_VALUE: return DINT;
			case UDINT_VALUE: return UDINT;
			case ANY_VALUE: return ANY;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private BaseType1(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getValue() {
	  return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
	  return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
	
} //BaseType1
