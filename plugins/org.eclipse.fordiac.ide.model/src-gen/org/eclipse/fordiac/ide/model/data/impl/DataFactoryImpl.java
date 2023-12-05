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
package org.eclipse.fordiac.ide.model.data.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.fordiac.ide.model.data.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DataFactoryImpl extends EFactoryImpl implements DataFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DataFactory init() {
		try {
			DataFactory theDataFactory = (DataFactory)EPackage.Registry.INSTANCE.getEFactory(DataPackage.eNS_URI);
			if (theDataFactory != null) {
				return theDataFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new DataFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case DataPackage.ANY_DERIVED_TYPE: return createAnyDerivedType();
			case DataPackage.ARRAY_TYPE: return createArrayType();
			case DataPackage.DIRECTLY_DERIVED_TYPE: return createDirectlyDerivedType();
			case DataPackage.ENUMERATED_TYPE: return createEnumeratedType();
			case DataPackage.ENUMERATED_VALUE: return createEnumeratedValue();
			case DataPackage.STRUCTURED_TYPE: return createStructuredType();
			case DataPackage.SUBRANGE: return createSubrange();
			case DataPackage.SUBRANGE_TYPE: return createSubrangeType();
			case DataPackage.VALUE_TYPE: return createValueType();
			case DataPackage.ELEMENTARY_TYPE: return createElementaryType();
			case DataPackage.DERIVED_TYPE: return createDerivedType();
			case DataPackage.EVENT_TYPE: return createEventType();
			case DataPackage.ANY_TYPE: return createAnyType();
			case DataPackage.ANY_ELEMENTARY_TYPE: return createAnyElementaryType();
			case DataPackage.ANY_MAGNITUDE_TYPE: return createAnyMagnitudeType();
			case DataPackage.ANY_NUM_TYPE: return createAnyNumType();
			case DataPackage.ANY_REAL_TYPE: return createAnyRealType();
			case DataPackage.REAL_TYPE: return createRealType();
			case DataPackage.LREAL_TYPE: return createLrealType();
			case DataPackage.ANY_INT_TYPE: return createAnyIntType();
			case DataPackage.ANY_UNSIGNED_TYPE: return createAnyUnsignedType();
			case DataPackage.USINT_TYPE: return createUsintType();
			case DataPackage.UINT_TYPE: return createUintType();
			case DataPackage.UDINT_TYPE: return createUdintType();
			case DataPackage.ULINT_TYPE: return createUlintType();
			case DataPackage.ANY_SIGNED_TYPE: return createAnySignedType();
			case DataPackage.SINT_TYPE: return createSintType();
			case DataPackage.INT_TYPE: return createIntType();
			case DataPackage.DINT_TYPE: return createDintType();
			case DataPackage.LINT_TYPE: return createLintType();
			case DataPackage.ANY_DURATION_TYPE: return createAnyDurationType();
			case DataPackage.TIME_TYPE: return createTimeType();
			case DataPackage.LTIME_TYPE: return createLtimeType();
			case DataPackage.ANY_BIT_TYPE: return createAnyBitType();
			case DataPackage.BOOL_TYPE: return createBoolType();
			case DataPackage.BYTE_TYPE: return createByteType();
			case DataPackage.WORD_TYPE: return createWordType();
			case DataPackage.DWORD_TYPE: return createDwordType();
			case DataPackage.LWORD_TYPE: return createLwordType();
			case DataPackage.ANY_CHARS_TYPE: return createAnyCharsType();
			case DataPackage.ANY_SCHARS_TYPE: return createAnySCharsType();
			case DataPackage.ANY_WCHARS_TYPE: return createAnyWCharsType();
			case DataPackage.ANY_STRING_TYPE: return createAnyStringType();
			case DataPackage.STRING_TYPE: return createStringType();
			case DataPackage.WSTRING_TYPE: return createWstringType();
			case DataPackage.ANY_CHAR_TYPE: return createAnyCharType();
			case DataPackage.CHAR_TYPE: return createCharType();
			case DataPackage.WCHAR_TYPE: return createWcharType();
			case DataPackage.ANY_DATE_TYPE: return createAnyDateType();
			case DataPackage.DATE_AND_TIME_TYPE: return createDateAndTimeType();
			case DataPackage.LDT_TYPE: return createLdtType();
			case DataPackage.DATE_TYPE: return createDateType();
			case DataPackage.TIME_OF_DAY_TYPE: return createTimeOfDayType();
			case DataPackage.LTOD_TYPE: return createLtodType();
			case DataPackage.LDATE_TYPE: return createLdateType();
			case DataPackage.INTERNAL_DATA_TYPE: return createInternalDataType();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case DataPackage.BASE_TYPE1:
				return createBaseType1FromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case DataPackage.BASE_TYPE1:
				return convertBaseType1ToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AnyDerivedType createAnyDerivedType() {
		AnyDerivedTypeImpl anyDerivedType = new AnyDerivedTypeImpl();
		return anyDerivedType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ArrayType createArrayType() {
		ArrayTypeImpl arrayType = new ArrayTypeImpl();
		return arrayType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DirectlyDerivedType createDirectlyDerivedType() {
		DirectlyDerivedTypeImpl directlyDerivedType = new DirectlyDerivedTypeImpl();
		return directlyDerivedType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EnumeratedType createEnumeratedType() {
		EnumeratedTypeImpl enumeratedType = new EnumeratedTypeImpl();
		return enumeratedType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EnumeratedValue createEnumeratedValue() {
		EnumeratedValueImpl enumeratedValue = new EnumeratedValueImpl();
		return enumeratedValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public StructuredType createStructuredType() {
		StructuredTypeImpl structuredType = new StructuredTypeImpl();
		return structuredType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Subrange createSubrange() {
		SubrangeImpl subrange = new SubrangeImpl();
		return subrange;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SubrangeType createSubrangeType() {
		SubrangeTypeImpl subrangeType = new SubrangeTypeImpl();
		return subrangeType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ValueType createValueType() {
		ValueTypeImpl valueType = new ValueTypeImpl();
		return valueType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ElementaryType createElementaryType() {
		ElementaryTypeImpl elementaryType = new ElementaryTypeImpl();
		return elementaryType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DerivedType createDerivedType() {
		DerivedTypeImpl derivedType = new DerivedTypeImpl();
		return derivedType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EventType createEventType() {
		EventTypeImpl eventType = new EventTypeImpl();
		return eventType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AnyType createAnyType() {
		AnyTypeImpl anyType = new AnyTypeImpl();
		return anyType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AnyElementaryType createAnyElementaryType() {
		AnyElementaryTypeImpl anyElementaryType = new AnyElementaryTypeImpl();
		return anyElementaryType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AnyMagnitudeType createAnyMagnitudeType() {
		AnyMagnitudeTypeImpl anyMagnitudeType = new AnyMagnitudeTypeImpl();
		return anyMagnitudeType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AnyNumType createAnyNumType() {
		AnyNumTypeImpl anyNumType = new AnyNumTypeImpl();
		return anyNumType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AnyRealType createAnyRealType() {
		AnyRealTypeImpl anyRealType = new AnyRealTypeImpl();
		return anyRealType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RealType createRealType() {
		RealTypeImpl realType = new RealTypeImpl();
		return realType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LrealType createLrealType() {
		LrealTypeImpl lrealType = new LrealTypeImpl();
		return lrealType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AnyIntType createAnyIntType() {
		AnyIntTypeImpl anyIntType = new AnyIntTypeImpl();
		return anyIntType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AnyUnsignedType createAnyUnsignedType() {
		AnyUnsignedTypeImpl anyUnsignedType = new AnyUnsignedTypeImpl();
		return anyUnsignedType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public UsintType createUsintType() {
		UsintTypeImpl usintType = new UsintTypeImpl();
		return usintType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public UintType createUintType() {
		UintTypeImpl uintType = new UintTypeImpl();
		return uintType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public UdintType createUdintType() {
		UdintTypeImpl udintType = new UdintTypeImpl();
		return udintType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public UlintType createUlintType() {
		UlintTypeImpl ulintType = new UlintTypeImpl();
		return ulintType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AnySignedType createAnySignedType() {
		AnySignedTypeImpl anySignedType = new AnySignedTypeImpl();
		return anySignedType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SintType createSintType() {
		SintTypeImpl sintType = new SintTypeImpl();
		return sintType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IntType createIntType() {
		IntTypeImpl intType = new IntTypeImpl();
		return intType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DintType createDintType() {
		DintTypeImpl dintType = new DintTypeImpl();
		return dintType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LintType createLintType() {
		LintTypeImpl lintType = new LintTypeImpl();
		return lintType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AnyDurationType createAnyDurationType() {
		AnyDurationTypeImpl anyDurationType = new AnyDurationTypeImpl();
		return anyDurationType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TimeType createTimeType() {
		TimeTypeImpl timeType = new TimeTypeImpl();
		return timeType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LtimeType createLtimeType() {
		LtimeTypeImpl ltimeType = new LtimeTypeImpl();
		return ltimeType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AnyBitType createAnyBitType() {
		AnyBitTypeImpl anyBitType = new AnyBitTypeImpl();
		return anyBitType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BoolType createBoolType() {
		BoolTypeImpl boolType = new BoolTypeImpl();
		return boolType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ByteType createByteType() {
		ByteTypeImpl byteType = new ByteTypeImpl();
		return byteType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public WordType createWordType() {
		WordTypeImpl wordType = new WordTypeImpl();
		return wordType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DwordType createDwordType() {
		DwordTypeImpl dwordType = new DwordTypeImpl();
		return dwordType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LwordType createLwordType() {
		LwordTypeImpl lwordType = new LwordTypeImpl();
		return lwordType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AnyCharsType createAnyCharsType() {
		AnyCharsTypeImpl anyCharsType = new AnyCharsTypeImpl();
		return anyCharsType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AnySCharsType createAnySCharsType() {
		AnySCharsTypeImpl anySCharsType = new AnySCharsTypeImpl();
		return anySCharsType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AnyWCharsType createAnyWCharsType() {
		AnyWCharsTypeImpl anyWCharsType = new AnyWCharsTypeImpl();
		return anyWCharsType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AnyStringType createAnyStringType() {
		AnyStringTypeImpl anyStringType = new AnyStringTypeImpl();
		return anyStringType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public StringType createStringType() {
		StringTypeImpl stringType = new StringTypeImpl();
		return stringType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public WstringType createWstringType() {
		WstringTypeImpl wstringType = new WstringTypeImpl();
		return wstringType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AnyCharType createAnyCharType() {
		AnyCharTypeImpl anyCharType = new AnyCharTypeImpl();
		return anyCharType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CharType createCharType() {
		CharTypeImpl charType = new CharTypeImpl();
		return charType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public WcharType createWcharType() {
		WcharTypeImpl wcharType = new WcharTypeImpl();
		return wcharType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AnyDateType createAnyDateType() {
		AnyDateTypeImpl anyDateType = new AnyDateTypeImpl();
		return anyDateType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DateAndTimeType createDateAndTimeType() {
		DateAndTimeTypeImpl dateAndTimeType = new DateAndTimeTypeImpl();
		return dateAndTimeType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LdtType createLdtType() {
		LdtTypeImpl ldtType = new LdtTypeImpl();
		return ldtType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DateType createDateType() {
		DateTypeImpl dateType = new DateTypeImpl();
		return dateType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TimeOfDayType createTimeOfDayType() {
		TimeOfDayTypeImpl timeOfDayType = new TimeOfDayTypeImpl();
		return timeOfDayType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LtodType createLtodType() {
		LtodTypeImpl ltodType = new LtodTypeImpl();
		return ltodType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LdateType createLdateType() {
		LdateTypeImpl ldateType = new LdateTypeImpl();
		return ldateType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public InternalDataType createInternalDataType() {
		InternalDataTypeImpl internalDataType = new InternalDataTypeImpl();
		return internalDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BaseType1 createBaseType1FromString(EDataType eDataType, String initialValue) {
		BaseType1 result = BaseType1.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertBaseType1ToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DataPackage getDataPackage() {
		return (DataPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static DataPackage getPackage() {
		return DataPackage.eINSTANCE;
	}

} //DataFactoryImpl
