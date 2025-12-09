/**
 * *******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *               2022 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger, Martin Jobst
 *      - initial API and implementation and/or initial documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.model.data;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.fordiac.ide.model.data.DataPackage
 * @generated
 */
public interface DataFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DataFactory eINSTANCE = org.eclipse.fordiac.ide.model.data.impl.DataFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Any Derived Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Any Derived Type</em>'.
	 * @generated
	 */
	AnyDerivedType createAnyDerivedType();

	/**
	 * Returns a new object of class '<em>Array Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Array Type</em>'.
	 * @generated
	 */
	ArrayType createArrayType();

	/**
	 * Returns a new object of class '<em>Directly Derived Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Directly Derived Type</em>'.
	 * @generated
	 */
	DirectlyDerivedType createDirectlyDerivedType();

	/**
	 * Returns a new object of class '<em>Enumerated Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Enumerated Type</em>'.
	 * @generated
	 */
	EnumeratedType createEnumeratedType();

	/**
	 * Returns a new object of class '<em>Enumerated Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Enumerated Value</em>'.
	 * @generated
	 */
	EnumeratedValue createEnumeratedValue();

	/**
	 * Returns a new object of class '<em>Structured Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Structured Type</em>'.
	 * @generated
	 */
	StructuredType createStructuredType();

	/**
	 * Returns a new object of class '<em>Subrange</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Subrange</em>'.
	 * @generated
	 */
	Subrange createSubrange();

	/**
	 * Returns a new object of class '<em>Subrange Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Subrange Type</em>'.
	 * @generated
	 */
	SubrangeType createSubrangeType();

	/**
	 * Returns a new object of class '<em>Value Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Value Type</em>'.
	 * @generated
	 */
	ValueType createValueType();

	/**
	 * Returns a new object of class '<em>Derived Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Derived Type</em>'.
	 * @generated
	 */
	DerivedType createDerivedType();

	/**
	 * Returns a new object of class '<em>Event Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Event Type</em>'.
	 * @generated
	 */
	EventType createEventType();

	/**
	 * Returns a new object of class '<em>Any Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Any Type</em>'.
	 * @generated
	 */
	AnyType createAnyType();

	/**
	 * Returns a new object of class '<em>Any Elementary Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Any Elementary Type</em>'.
	 * @generated
	 */
	AnyElementaryType createAnyElementaryType();

	/**
	 * Returns a new object of class '<em>Any Magnitude Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Any Magnitude Type</em>'.
	 * @generated
	 */
	AnyMagnitudeType createAnyMagnitudeType();

	/**
	 * Returns a new object of class '<em>Any Num Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Any Num Type</em>'.
	 * @generated
	 */
	AnyNumType createAnyNumType();

	/**
	 * Returns a new object of class '<em>Any Real Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Any Real Type</em>'.
	 * @generated
	 */
	AnyRealType createAnyRealType();

	/**
	 * Returns a new object of class '<em>Real Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Real Type</em>'.
	 * @generated
	 */
	RealType createRealType();

	/**
	 * Returns a new object of class '<em>Lreal Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Lreal Type</em>'.
	 * @generated
	 */
	LrealType createLrealType();

	/**
	 * Returns a new object of class '<em>Any Int Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Any Int Type</em>'.
	 * @generated
	 */
	AnyIntType createAnyIntType();

	/**
	 * Returns a new object of class '<em>Any Unsigned Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Any Unsigned Type</em>'.
	 * @generated
	 */
	AnyUnsignedType createAnyUnsignedType();

	/**
	 * Returns a new object of class '<em>Usint Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Usint Type</em>'.
	 * @generated
	 */
	UsintType createUsintType();

	/**
	 * Returns a new object of class '<em>Uint Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Uint Type</em>'.
	 * @generated
	 */
	UintType createUintType();

	/**
	 * Returns a new object of class '<em>Udint Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Udint Type</em>'.
	 * @generated
	 */
	UdintType createUdintType();

	/**
	 * Returns a new object of class '<em>Ulint Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Ulint Type</em>'.
	 * @generated
	 */
	UlintType createUlintType();

	/**
	 * Returns a new object of class '<em>Any Signed Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Any Signed Type</em>'.
	 * @generated
	 */
	AnySignedType createAnySignedType();

	/**
	 * Returns a new object of class '<em>Sint Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Sint Type</em>'.
	 * @generated
	 */
	SintType createSintType();

	/**
	 * Returns a new object of class '<em>Int Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Int Type</em>'.
	 * @generated
	 */
	IntType createIntType();

	/**
	 * Returns a new object of class '<em>Dint Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Dint Type</em>'.
	 * @generated
	 */
	DintType createDintType();

	/**
	 * Returns a new object of class '<em>Lint Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Lint Type</em>'.
	 * @generated
	 */
	LintType createLintType();

	/**
	 * Returns a new object of class '<em>Any Duration Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Any Duration Type</em>'.
	 * @generated
	 */
	AnyDurationType createAnyDurationType();

	/**
	 * Returns a new object of class '<em>Time Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Time Type</em>'.
	 * @generated
	 */
	TimeType createTimeType();

	/**
	 * Returns a new object of class '<em>Ltime Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Ltime Type</em>'.
	 * @generated
	 */
	LtimeType createLtimeType();

	/**
	 * Returns a new object of class '<em>Any Bit Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Any Bit Type</em>'.
	 * @generated
	 */
	AnyBitType createAnyBitType();

	/**
	 * Returns a new object of class '<em>Bool Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Bool Type</em>'.
	 * @generated
	 */
	BoolType createBoolType();

	/**
	 * Returns a new object of class '<em>Byte Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Byte Type</em>'.
	 * @generated
	 */
	ByteType createByteType();

	/**
	 * Returns a new object of class '<em>Word Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Word Type</em>'.
	 * @generated
	 */
	WordType createWordType();

	/**
	 * Returns a new object of class '<em>Dword Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Dword Type</em>'.
	 * @generated
	 */
	DwordType createDwordType();

	/**
	 * Returns a new object of class '<em>Lword Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Lword Type</em>'.
	 * @generated
	 */
	LwordType createLwordType();

	/**
	 * Returns a new object of class '<em>Any Chars Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Any Chars Type</em>'.
	 * @generated
	 */
	AnyCharsType createAnyCharsType();

	/**
	 * Returns a new object of class '<em>Any SChars Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Any SChars Type</em>'.
	 * @generated
	 */
	AnySCharsType createAnySCharsType();

	/**
	 * Returns a new object of class '<em>Any WChars Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Any WChars Type</em>'.
	 * @generated
	 */
	AnyWCharsType createAnyWCharsType();

	/**
	 * Returns a new object of class '<em>Any String Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Any String Type</em>'.
	 * @generated
	 */
	AnyStringType createAnyStringType();

	/**
	 * Returns a new object of class '<em>String Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>String Type</em>'.
	 * @generated
	 */
	StringType createStringType();

	/**
	 * Returns a new object of class '<em>Wstring Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Wstring Type</em>'.
	 * @generated
	 */
	WstringType createWstringType();

	/**
	 * Returns a new object of class '<em>Any Char Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Any Char Type</em>'.
	 * @generated
	 */
	AnyCharType createAnyCharType();

	/**
	 * Returns a new object of class '<em>Char Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Char Type</em>'.
	 * @generated
	 */
	CharType createCharType();

	/**
	 * Returns a new object of class '<em>Wchar Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Wchar Type</em>'.
	 * @generated
	 */
	WcharType createWcharType();

	/**
	 * Returns a new object of class '<em>Any Date Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Any Date Type</em>'.
	 * @generated
	 */
	AnyDateType createAnyDateType();

	/**
	 * Returns a new object of class '<em>Date And Time Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Date And Time Type</em>'.
	 * @generated
	 */
	DateAndTimeType createDateAndTimeType();

	/**
	 * Returns a new object of class '<em>Ldt Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Ldt Type</em>'.
	 * @generated
	 */
	LdtType createLdtType();

	/**
	 * Returns a new object of class '<em>Date Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Date Type</em>'.
	 * @generated
	 */
	DateType createDateType();

	/**
	 * Returns a new object of class '<em>Time Of Day Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Time Of Day Type</em>'.
	 * @generated
	 */
	TimeOfDayType createTimeOfDayType();

	/**
	 * Returns a new object of class '<em>Ltod Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Ltod Type</em>'.
	 * @generated
	 */
	LtodType createLtodType();

	/**
	 * Returns a new object of class '<em>Ldate Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Ldate Type</em>'.
	 * @generated
	 */
	LdateType createLdateType();

	/**
	 * Returns a new object of class '<em>Internal Data Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Internal Data Type</em>'.
	 * @generated
	 */
	InternalDataType createInternalDataType();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	DataPackage getDataPackage();

} //DataFactory
