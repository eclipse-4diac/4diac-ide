/**
 * *******************************************************************************
 * Copyright (c) 2008, 2026 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 *                                                       Martin Erich Jobst, Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.model.data.util;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.EObjectValidator;

import org.eclipse.fordiac.ide.model.data.*;

import org.eclipse.fordiac.ide.model.libraryElement.util.LibraryElementValidator;

/**
 * <!-- begin-user-doc --> The <b>Validator</b> for the model. <!-- end-user-doc
 * -->
 *
 * @see org.eclipse.fordiac.ide.model.data.DataPackage
 * @generated
 */
public class DataValidator extends EObjectValidator {
	/**
	 * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static final DataValidator INSTANCE = new DataValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource()
	 * source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode()
	 * codes} from this package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "org.eclipse.fordiac.ide.model.data"; //$NON-NLS-1$

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for
	 * constraint 'Validate No Circular References' of 'Structured Type'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static final int STRUCTURED_TYPE__VALIDATE_NO_CIRCULAR_REFERENCES = 1;

	/**
	 * A constant with a fixed name that can be used as the base value for
	 * additional hand written constants. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @generated
	 */
	private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 1;

	/**
	 * A constant with a fixed name that can be used as the base value for
	 * additional hand written constants in a derived class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected static final int DIAGNOSTIC_CODE_COUNT = GENERATED_DIAGNOSTIC_CODE_COUNT;

	/**
	 * The cached base package validator. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @generated
	 */
	protected LibraryElementValidator libraryElementValidator;

	/**
	 * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @generated
	 */
	public DataValidator() {
		super();
		libraryElementValidator = LibraryElementValidator.INSTANCE;
	}

	/**
	 * Returns the package of this validator switch. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EPackage getEPackage() {
		return DataPackage.eINSTANCE;
	}

	/**
	 * Calls <code>validateXXX</code> for the corresponding classifier of the model.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected boolean validate(int classifierID, Object value, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		switch (classifierID) {
		case DataPackage.ANY_DERIVED_TYPE:
			return validateAnyDerivedType((AnyDerivedType) value, diagnostics, context);
		case DataPackage.ARRAY_TYPE:
			return validateArrayType((ArrayType) value, diagnostics, context);
		case DataPackage.DATA_TYPE:
			return validateDataType((DataType) value, diagnostics, context);
		case DataPackage.DIRECTLY_DERIVED_TYPE:
			return validateDirectlyDerivedType((DirectlyDerivedType) value, diagnostics, context);
		case DataPackage.ENUMERATED_TYPE:
			return validateEnumeratedType((EnumeratedType) value, diagnostics, context);
		case DataPackage.ENUMERATED_VALUE:
			return validateEnumeratedValue((EnumeratedValue) value, diagnostics, context);
		case DataPackage.ERROR_DATA_TYPE:
			return validateErrorDataType((ErrorDataType) value, diagnostics, context);
		case DataPackage.STRUCTURED_TYPE:
			return validateStructuredType((StructuredType) value, diagnostics, context);
		case DataPackage.SUBRANGE:
			return validateSubrange((Subrange) value, diagnostics, context);
		case DataPackage.SUBRANGE_TYPE:
			return validateSubrangeType((SubrangeType) value, diagnostics, context);
		case DataPackage.VALUE_TYPE:
			return validateValueType((ValueType) value, diagnostics, context);
		case DataPackage.DERIVED_TYPE:
			return validateDerivedType((DerivedType) value, diagnostics, context);
		case DataPackage.EVENT_TYPE:
			return validateEventType((EventType) value, diagnostics, context);
		case DataPackage.ANY_TYPE:
			return validateAnyType((AnyType) value, diagnostics, context);
		case DataPackage.ANY_ELEMENTARY_TYPE:
			return validateAnyElementaryType((AnyElementaryType) value, diagnostics, context);
		case DataPackage.ANY_MAGNITUDE_TYPE:
			return validateAnyMagnitudeType((AnyMagnitudeType) value, diagnostics, context);
		case DataPackage.ANY_NUM_TYPE:
			return validateAnyNumType((AnyNumType) value, diagnostics, context);
		case DataPackage.ANY_REAL_TYPE:
			return validateAnyRealType((AnyRealType) value, diagnostics, context);
		case DataPackage.REAL_TYPE:
			return validateRealType((RealType) value, diagnostics, context);
		case DataPackage.LREAL_TYPE:
			return validateLrealType((LrealType) value, diagnostics, context);
		case DataPackage.ANY_INT_TYPE:
			return validateAnyIntType((AnyIntType) value, diagnostics, context);
		case DataPackage.ANY_UNSIGNED_TYPE:
			return validateAnyUnsignedType((AnyUnsignedType) value, diagnostics, context);
		case DataPackage.USINT_TYPE:
			return validateUsintType((UsintType) value, diagnostics, context);
		case DataPackage.UINT_TYPE:
			return validateUintType((UintType) value, diagnostics, context);
		case DataPackage.UDINT_TYPE:
			return validateUdintType((UdintType) value, diagnostics, context);
		case DataPackage.ULINT_TYPE:
			return validateUlintType((UlintType) value, diagnostics, context);
		case DataPackage.ANY_SIGNED_TYPE:
			return validateAnySignedType((AnySignedType) value, diagnostics, context);
		case DataPackage.SINT_TYPE:
			return validateSintType((SintType) value, diagnostics, context);
		case DataPackage.INT_TYPE:
			return validateIntType((IntType) value, diagnostics, context);
		case DataPackage.DINT_TYPE:
			return validateDintType((DintType) value, diagnostics, context);
		case DataPackage.LINT_TYPE:
			return validateLintType((LintType) value, diagnostics, context);
		case DataPackage.ANY_DURATION_TYPE:
			return validateAnyDurationType((AnyDurationType) value, diagnostics, context);
		case DataPackage.TIME_TYPE:
			return validateTimeType((TimeType) value, diagnostics, context);
		case DataPackage.LTIME_TYPE:
			return validateLtimeType((LtimeType) value, diagnostics, context);
		case DataPackage.ANY_BIT_TYPE:
			return validateAnyBitType((AnyBitType) value, diagnostics, context);
		case DataPackage.BOOL_TYPE:
			return validateBoolType((BoolType) value, diagnostics, context);
		case DataPackage.BYTE_TYPE:
			return validateByteType((ByteType) value, diagnostics, context);
		case DataPackage.WORD_TYPE:
			return validateWordType((WordType) value, diagnostics, context);
		case DataPackage.DWORD_TYPE:
			return validateDwordType((DwordType) value, diagnostics, context);
		case DataPackage.LWORD_TYPE:
			return validateLwordType((LwordType) value, diagnostics, context);
		case DataPackage.ANY_CHARS_TYPE:
			return validateAnyCharsType((AnyCharsType) value, diagnostics, context);
		case DataPackage.ANY_SCHARS_TYPE:
			return validateAnySCharsType((AnySCharsType) value, diagnostics, context);
		case DataPackage.ANY_WCHARS_TYPE:
			return validateAnyWCharsType((AnyWCharsType) value, diagnostics, context);
		case DataPackage.ANY_STRING_TYPE:
			return validateAnyStringType((AnyStringType) value, diagnostics, context);
		case DataPackage.STRING_TYPE:
			return validateStringType((StringType) value, diagnostics, context);
		case DataPackage.WSTRING_TYPE:
			return validateWstringType((WstringType) value, diagnostics, context);
		case DataPackage.ANY_CHAR_TYPE:
			return validateAnyCharType((AnyCharType) value, diagnostics, context);
		case DataPackage.CHAR_TYPE:
			return validateCharType((CharType) value, diagnostics, context);
		case DataPackage.WCHAR_TYPE:
			return validateWcharType((WcharType) value, diagnostics, context);
		case DataPackage.ANY_DATE_TYPE:
			return validateAnyDateType((AnyDateType) value, diagnostics, context);
		case DataPackage.DATE_AND_TIME_TYPE:
			return validateDateAndTimeType((DateAndTimeType) value, diagnostics, context);
		case DataPackage.LDT_TYPE:
			return validateLdtType((LdtType) value, diagnostics, context);
		case DataPackage.DATE_TYPE:
			return validateDateType((DateType) value, diagnostics, context);
		case DataPackage.TIME_OF_DAY_TYPE:
			return validateTimeOfDayType((TimeOfDayType) value, diagnostics, context);
		case DataPackage.LTOD_TYPE:
			return validateLtodType((LtodType) value, diagnostics, context);
		case DataPackage.LDATE_TYPE:
			return validateLdateType((LdateType) value, diagnostics, context);
		case DataPackage.INTERNAL_DATA_TYPE:
			return validateInternalDataType((InternalDataType) value, diagnostics, context);
		case DataPackage.BASE_TYPE1:
			return validateBaseType1((BaseType1) value, diagnostics, context);
		default:
			return true;
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateAnyDerivedType(AnyDerivedType anyDerivedType, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (!validate_NoCircularContainment(anyDerivedType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(anyDerivedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(anyDerivedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(anyDerivedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(anyDerivedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(anyDerivedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(anyDerivedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(anyDerivedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(anyDerivedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(anyDerivedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(anyDerivedType, diagnostics,
					context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateArrayType(ArrayType arrayType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(arrayType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(arrayType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(arrayType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(arrayType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(arrayType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(arrayType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(arrayType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(arrayType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(arrayType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(arrayType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(arrayType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateDataType(DataType dataType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(dataType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(dataType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(dataType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(dataType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(dataType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(dataType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(dataType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(dataType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(dataType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(dataType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(dataType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateDirectlyDerivedType(DirectlyDerivedType directlyDerivedType, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (!validate_NoCircularContainment(directlyDerivedType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(directlyDerivedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(directlyDerivedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(directlyDerivedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(directlyDerivedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(directlyDerivedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(directlyDerivedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(directlyDerivedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(directlyDerivedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(directlyDerivedType, diagnostics,
					context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(directlyDerivedType, diagnostics,
					context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateEnumeratedType(EnumeratedType enumeratedType, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (!validate_NoCircularContainment(enumeratedType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(enumeratedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(enumeratedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(enumeratedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(enumeratedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(enumeratedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(enumeratedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(enumeratedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(enumeratedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(enumeratedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(enumeratedType, diagnostics,
					context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateEnumeratedValue(EnumeratedValue enumeratedValue, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (!validate_NoCircularContainment(enumeratedValue, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(enumeratedValue, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(enumeratedValue, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(enumeratedValue, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(enumeratedValue, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(enumeratedValue, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(enumeratedValue, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(enumeratedValue, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(enumeratedValue, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateINamedElement_validateName(enumeratedValue, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateErrorDataType(ErrorDataType errorDataType, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (!validate_NoCircularContainment(errorDataType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(errorDataType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(errorDataType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(errorDataType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(errorDataType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(errorDataType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(errorDataType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(errorDataType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(errorDataType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(errorDataType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(errorDataType, diagnostics,
					context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateStructuredType(StructuredType structuredType, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (!validate_NoCircularContainment(structuredType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(structuredType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(structuredType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(structuredType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(structuredType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(structuredType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(structuredType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(structuredType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(structuredType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(structuredType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(structuredType, diagnostics,
					context);
		if (result || diagnostics != null)
			result &= validateStructuredType_validateNoCircularReferences(structuredType, diagnostics, context);
		return result;
	}

	/**
	 * Validates the validateNoCircularReferences constraint of '<em>Structured
	 * Type</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateStructuredType_validateNoCircularReferences(StructuredType structuredType,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
		return structuredType.validateNoCircularReferences(diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateSubrange(Subrange subrange, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(subrange, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateSubrangeType(SubrangeType subrangeType, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (!validate_NoCircularContainment(subrangeType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(subrangeType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(subrangeType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(subrangeType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(subrangeType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(subrangeType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(subrangeType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(subrangeType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(subrangeType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(subrangeType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(subrangeType, diagnostics,
					context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateValueType(ValueType valueType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(valueType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(valueType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(valueType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(valueType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(valueType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(valueType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(valueType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(valueType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(valueType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(valueType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(valueType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateDerivedType(DerivedType derivedType, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (!validate_NoCircularContainment(derivedType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(derivedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(derivedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(derivedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(derivedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(derivedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(derivedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(derivedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(derivedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(derivedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(derivedType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateEventType(EventType eventType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(eventType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(eventType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(eventType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(eventType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(eventType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(eventType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(eventType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(eventType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(eventType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(eventType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(eventType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateAnyType(AnyType anyType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(anyType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(anyType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(anyType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(anyType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(anyType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(anyType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(anyType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(anyType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(anyType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(anyType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(anyType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateAnyElementaryType(AnyElementaryType anyElementaryType, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (!validate_NoCircularContainment(anyElementaryType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(anyElementaryType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(anyElementaryType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(anyElementaryType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(anyElementaryType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(anyElementaryType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(anyElementaryType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(anyElementaryType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(anyElementaryType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(anyElementaryType, diagnostics,
					context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(anyElementaryType, diagnostics,
					context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateAnyMagnitudeType(AnyMagnitudeType anyMagnitudeType, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (!validate_NoCircularContainment(anyMagnitudeType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(anyMagnitudeType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(anyMagnitudeType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(anyMagnitudeType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(anyMagnitudeType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(anyMagnitudeType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(anyMagnitudeType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(anyMagnitudeType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(anyMagnitudeType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(anyMagnitudeType, diagnostics,
					context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(anyMagnitudeType, diagnostics,
					context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateAnyNumType(AnyNumType anyNumType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(anyNumType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(anyNumType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(anyNumType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(anyNumType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(anyNumType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(anyNumType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(anyNumType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(anyNumType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(anyNumType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(anyNumType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(anyNumType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateAnyRealType(AnyRealType anyRealType, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (!validate_NoCircularContainment(anyRealType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(anyRealType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(anyRealType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(anyRealType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(anyRealType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(anyRealType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(anyRealType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(anyRealType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(anyRealType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(anyRealType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(anyRealType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateRealType(RealType realType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(realType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(realType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(realType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(realType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(realType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(realType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(realType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(realType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(realType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(realType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(realType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateLrealType(LrealType lrealType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(lrealType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(lrealType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(lrealType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(lrealType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(lrealType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(lrealType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(lrealType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(lrealType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(lrealType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(lrealType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(lrealType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateAnyIntType(AnyIntType anyIntType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(anyIntType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(anyIntType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(anyIntType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(anyIntType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(anyIntType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(anyIntType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(anyIntType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(anyIntType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(anyIntType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(anyIntType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(anyIntType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateAnyUnsignedType(AnyUnsignedType anyUnsignedType, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (!validate_NoCircularContainment(anyUnsignedType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(anyUnsignedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(anyUnsignedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(anyUnsignedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(anyUnsignedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(anyUnsignedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(anyUnsignedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(anyUnsignedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(anyUnsignedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(anyUnsignedType, diagnostics,
					context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(anyUnsignedType, diagnostics,
					context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateUsintType(UsintType usintType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(usintType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(usintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(usintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(usintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(usintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(usintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(usintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(usintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(usintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(usintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(usintType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateUintType(UintType uintType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(uintType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(uintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(uintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(uintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(uintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(uintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(uintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(uintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(uintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(uintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(uintType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateUdintType(UdintType udintType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(udintType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(udintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(udintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(udintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(udintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(udintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(udintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(udintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(udintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(udintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(udintType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateUlintType(UlintType ulintType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(ulintType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(ulintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(ulintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(ulintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(ulintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(ulintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(ulintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(ulintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(ulintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(ulintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(ulintType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateAnySignedType(AnySignedType anySignedType, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (!validate_NoCircularContainment(anySignedType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(anySignedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(anySignedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(anySignedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(anySignedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(anySignedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(anySignedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(anySignedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(anySignedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(anySignedType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(anySignedType, diagnostics,
					context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateSintType(SintType sintType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(sintType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(sintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(sintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(sintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(sintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(sintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(sintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(sintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(sintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(sintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(sintType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateIntType(IntType intType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(intType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(intType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(intType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(intType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(intType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(intType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(intType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(intType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(intType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(intType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(intType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateDintType(DintType dintType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(dintType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(dintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(dintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(dintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(dintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(dintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(dintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(dintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(dintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(dintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(dintType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateLintType(LintType lintType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(lintType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(lintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(lintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(lintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(lintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(lintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(lintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(lintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(lintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(lintType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(lintType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateAnyDurationType(AnyDurationType anyDurationType, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (!validate_NoCircularContainment(anyDurationType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(anyDurationType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(anyDurationType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(anyDurationType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(anyDurationType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(anyDurationType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(anyDurationType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(anyDurationType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(anyDurationType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(anyDurationType, diagnostics,
					context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(anyDurationType, diagnostics,
					context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateTimeType(TimeType timeType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(timeType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(timeType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(timeType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(timeType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(timeType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(timeType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(timeType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(timeType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(timeType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(timeType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(timeType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateLtimeType(LtimeType ltimeType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(ltimeType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(ltimeType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(ltimeType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(ltimeType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(ltimeType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(ltimeType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(ltimeType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(ltimeType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(ltimeType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(ltimeType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(ltimeType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateAnyBitType(AnyBitType anyBitType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(anyBitType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(anyBitType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(anyBitType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(anyBitType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(anyBitType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(anyBitType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(anyBitType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(anyBitType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(anyBitType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(anyBitType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(anyBitType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateBoolType(BoolType boolType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(boolType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(boolType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(boolType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(boolType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(boolType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(boolType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(boolType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(boolType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(boolType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(boolType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(boolType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateByteType(ByteType byteType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(byteType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(byteType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(byteType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(byteType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(byteType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(byteType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(byteType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(byteType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(byteType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(byteType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(byteType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateWordType(WordType wordType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(wordType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(wordType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(wordType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(wordType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(wordType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(wordType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(wordType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(wordType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(wordType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(wordType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(wordType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateDwordType(DwordType dwordType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(dwordType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(dwordType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(dwordType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(dwordType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(dwordType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(dwordType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(dwordType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(dwordType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(dwordType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(dwordType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(dwordType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateLwordType(LwordType lwordType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(lwordType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(lwordType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(lwordType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(lwordType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(lwordType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(lwordType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(lwordType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(lwordType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(lwordType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(lwordType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(lwordType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateAnyCharsType(AnyCharsType anyCharsType, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (!validate_NoCircularContainment(anyCharsType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(anyCharsType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(anyCharsType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(anyCharsType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(anyCharsType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(anyCharsType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(anyCharsType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(anyCharsType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(anyCharsType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(anyCharsType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(anyCharsType, diagnostics,
					context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateAnySCharsType(AnySCharsType anySCharsType, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (!validate_NoCircularContainment(anySCharsType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(anySCharsType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(anySCharsType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(anySCharsType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(anySCharsType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(anySCharsType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(anySCharsType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(anySCharsType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(anySCharsType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(anySCharsType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(anySCharsType, diagnostics,
					context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateAnyWCharsType(AnyWCharsType anyWCharsType, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (!validate_NoCircularContainment(anyWCharsType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(anyWCharsType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(anyWCharsType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(anyWCharsType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(anyWCharsType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(anyWCharsType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(anyWCharsType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(anyWCharsType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(anyWCharsType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(anyWCharsType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(anyWCharsType, diagnostics,
					context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateAnyStringType(AnyStringType anyStringType, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (!validate_NoCircularContainment(anyStringType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(anyStringType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(anyStringType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(anyStringType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(anyStringType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(anyStringType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(anyStringType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(anyStringType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(anyStringType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(anyStringType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(anyStringType, diagnostics,
					context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateStringType(StringType stringType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(stringType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(stringType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(stringType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(stringType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(stringType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(stringType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(stringType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(stringType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(stringType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(stringType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(stringType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateWstringType(WstringType wstringType, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (!validate_NoCircularContainment(wstringType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(wstringType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(wstringType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(wstringType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(wstringType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(wstringType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(wstringType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(wstringType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(wstringType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(wstringType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(wstringType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateAnyCharType(AnyCharType anyCharType, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (!validate_NoCircularContainment(anyCharType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(anyCharType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(anyCharType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(anyCharType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(anyCharType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(anyCharType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(anyCharType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(anyCharType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(anyCharType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(anyCharType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(anyCharType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateCharType(CharType charType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(charType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(charType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(charType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(charType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(charType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(charType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(charType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(charType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(charType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(charType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(charType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateWcharType(WcharType wcharType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(wcharType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(wcharType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(wcharType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(wcharType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(wcharType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(wcharType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(wcharType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(wcharType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(wcharType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(wcharType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(wcharType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateAnyDateType(AnyDateType anyDateType, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (!validate_NoCircularContainment(anyDateType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(anyDateType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(anyDateType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(anyDateType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(anyDateType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(anyDateType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(anyDateType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(anyDateType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(anyDateType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(anyDateType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(anyDateType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateDateAndTimeType(DateAndTimeType dateAndTimeType, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (!validate_NoCircularContainment(dateAndTimeType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(dateAndTimeType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(dateAndTimeType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(dateAndTimeType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(dateAndTimeType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(dateAndTimeType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(dateAndTimeType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(dateAndTimeType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(dateAndTimeType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(dateAndTimeType, diagnostics,
					context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(dateAndTimeType, diagnostics,
					context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateLdtType(LdtType ldtType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(ldtType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(ldtType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(ldtType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(ldtType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(ldtType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(ldtType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(ldtType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(ldtType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(ldtType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(ldtType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(ldtType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateDateType(DateType dateType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(dateType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(dateType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(dateType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(dateType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(dateType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(dateType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(dateType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(dateType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(dateType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(dateType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(dateType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateTimeOfDayType(TimeOfDayType timeOfDayType, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (!validate_NoCircularContainment(timeOfDayType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(timeOfDayType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(timeOfDayType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(timeOfDayType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(timeOfDayType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(timeOfDayType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(timeOfDayType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(timeOfDayType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(timeOfDayType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(timeOfDayType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(timeOfDayType, diagnostics,
					context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateLtodType(LtodType ltodType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(ltodType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(ltodType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(ltodType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(ltodType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(ltodType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(ltodType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(ltodType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(ltodType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(ltodType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(ltodType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(ltodType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateLdateType(LdateType ldateType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(ldateType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(ldateType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(ldateType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(ldateType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(ldateType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(ldateType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(ldateType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(ldateType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(ldateType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(ldateType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(ldateType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateInternalDataType(InternalDataType internalDataType, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		if (!validate_NoCircularContainment(internalDataType, diagnostics, context))
			return false;
		boolean result = validate_EveryMultiplicityConforms(internalDataType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryDataValueConforms(internalDataType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryReferenceIsContained(internalDataType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryBidirectionalReferenceIsPaired(internalDataType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryProxyResolves(internalDataType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_UniqueID(internalDataType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryKeyUnique(internalDataType, diagnostics, context);
		if (result || diagnostics != null)
			result &= validate_EveryMapEntryUnique(internalDataType, diagnostics, context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validateName(internalDataType, diagnostics,
					context);
		if (result || diagnostics != null)
			result &= libraryElementValidator.validateLibraryElement_validatePackage(internalDataType, diagnostics,
					context);
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateBaseType1(BaseType1 baseType1, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * Returns the resource locator that will be used to fetch messages for this
	 * validator's diagnostics. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		// TODO
		// Specialize this to return a resource locator for messages specific to this
		// validator.
		// Ensure that you remove @generated or mark it @generated NOT
		return super.getResourceLocator();
	}

} // DataValidator
