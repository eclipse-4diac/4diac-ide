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
package org.eclipse.fordiac.ide.model.data.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.eclipse.fordiac.ide.model.data.*;

import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.eclipse.fordiac.ide.model.data.DataPackage
 * @generated
 */
public class DataSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static DataPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataSwitch() {
		if (modelPackage == null) {
			modelPackage = DataPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case DataPackage.ANY_DERIVED_TYPE: {
				AnyDerivedType anyDerivedType = (AnyDerivedType)theEObject;
				T result = caseAnyDerivedType(anyDerivedType);
				if (result == null) result = caseAnyType(anyDerivedType);
				if (result == null) result = caseDataType(anyDerivedType);
				if (result == null) result = caseLibraryElement(anyDerivedType);
				if (result == null) result = caseINamedElement(anyDerivedType);
				if (result == null) result = caseConfigurableObject(anyDerivedType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.ARRAY_TYPE: {
				ArrayType arrayType = (ArrayType)theEObject;
				T result = caseArrayType(arrayType);
				if (result == null) result = caseAnyDerivedType(arrayType);
				if (result == null) result = caseAnyType(arrayType);
				if (result == null) result = caseDataType(arrayType);
				if (result == null) result = caseLibraryElement(arrayType);
				if (result == null) result = caseINamedElement(arrayType);
				if (result == null) result = caseConfigurableObject(arrayType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.DATA_TYPE: {
				DataType dataType = (DataType)theEObject;
				T result = caseDataType(dataType);
				if (result == null) result = caseLibraryElement(dataType);
				if (result == null) result = caseINamedElement(dataType);
				if (result == null) result = caseConfigurableObject(dataType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.DIRECTLY_DERIVED_TYPE: {
				DirectlyDerivedType directlyDerivedType = (DirectlyDerivedType)theEObject;
				T result = caseDirectlyDerivedType(directlyDerivedType);
				if (result == null) result = caseAnyDerivedType(directlyDerivedType);
				if (result == null) result = caseAnyType(directlyDerivedType);
				if (result == null) result = caseDataType(directlyDerivedType);
				if (result == null) result = caseLibraryElement(directlyDerivedType);
				if (result == null) result = caseINamedElement(directlyDerivedType);
				if (result == null) result = caseConfigurableObject(directlyDerivedType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.ENUMERATED_TYPE: {
				EnumeratedType enumeratedType = (EnumeratedType)theEObject;
				T result = caseEnumeratedType(enumeratedType);
				if (result == null) result = caseAnyDerivedType(enumeratedType);
				if (result == null) result = caseAnyType(enumeratedType);
				if (result == null) result = caseDataType(enumeratedType);
				if (result == null) result = caseLibraryElement(enumeratedType);
				if (result == null) result = caseINamedElement(enumeratedType);
				if (result == null) result = caseConfigurableObject(enumeratedType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.ENUMERATED_VALUE: {
				EnumeratedValue enumeratedValue = (EnumeratedValue)theEObject;
				T result = caseEnumeratedValue(enumeratedValue);
				if (result == null) result = caseINamedElement(enumeratedValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.STRUCTURED_TYPE: {
				StructuredType structuredType = (StructuredType)theEObject;
				T result = caseStructuredType(structuredType);
				if (result == null) result = caseAnyDerivedType(structuredType);
				if (result == null) result = caseAnyType(structuredType);
				if (result == null) result = caseDataType(structuredType);
				if (result == null) result = caseLibraryElement(structuredType);
				if (result == null) result = caseINamedElement(structuredType);
				if (result == null) result = caseConfigurableObject(structuredType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.SUBRANGE: {
				Subrange subrange = (Subrange)theEObject;
				T result = caseSubrange(subrange);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.SUBRANGE_TYPE: {
				SubrangeType subrangeType = (SubrangeType)theEObject;
				T result = caseSubrangeType(subrangeType);
				if (result == null) result = caseDerivedType(subrangeType);
				if (result == null) result = caseValueType(subrangeType);
				if (result == null) result = caseDataType(subrangeType);
				if (result == null) result = caseLibraryElement(subrangeType);
				if (result == null) result = caseINamedElement(subrangeType);
				if (result == null) result = caseConfigurableObject(subrangeType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.VALUE_TYPE: {
				ValueType valueType = (ValueType)theEObject;
				T result = caseValueType(valueType);
				if (result == null) result = caseDataType(valueType);
				if (result == null) result = caseLibraryElement(valueType);
				if (result == null) result = caseINamedElement(valueType);
				if (result == null) result = caseConfigurableObject(valueType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.DERIVED_TYPE: {
				DerivedType derivedType = (DerivedType)theEObject;
				T result = caseDerivedType(derivedType);
				if (result == null) result = caseValueType(derivedType);
				if (result == null) result = caseDataType(derivedType);
				if (result == null) result = caseLibraryElement(derivedType);
				if (result == null) result = caseINamedElement(derivedType);
				if (result == null) result = caseConfigurableObject(derivedType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.EVENT_TYPE: {
				EventType eventType = (EventType)theEObject;
				T result = caseEventType(eventType);
				if (result == null) result = caseDataType(eventType);
				if (result == null) result = caseLibraryElement(eventType);
				if (result == null) result = caseINamedElement(eventType);
				if (result == null) result = caseConfigurableObject(eventType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.ANY_TYPE: {
				AnyType anyType = (AnyType)theEObject;
				T result = caseAnyType(anyType);
				if (result == null) result = caseDataType(anyType);
				if (result == null) result = caseLibraryElement(anyType);
				if (result == null) result = caseINamedElement(anyType);
				if (result == null) result = caseConfigurableObject(anyType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.ANY_ELEMENTARY_TYPE: {
				AnyElementaryType anyElementaryType = (AnyElementaryType)theEObject;
				T result = caseAnyElementaryType(anyElementaryType);
				if (result == null) result = caseAnyType(anyElementaryType);
				if (result == null) result = caseDataType(anyElementaryType);
				if (result == null) result = caseLibraryElement(anyElementaryType);
				if (result == null) result = caseINamedElement(anyElementaryType);
				if (result == null) result = caseConfigurableObject(anyElementaryType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.ANY_MAGNITUDE_TYPE: {
				AnyMagnitudeType anyMagnitudeType = (AnyMagnitudeType)theEObject;
				T result = caseAnyMagnitudeType(anyMagnitudeType);
				if (result == null) result = caseAnyElementaryType(anyMagnitudeType);
				if (result == null) result = caseAnyType(anyMagnitudeType);
				if (result == null) result = caseDataType(anyMagnitudeType);
				if (result == null) result = caseLibraryElement(anyMagnitudeType);
				if (result == null) result = caseINamedElement(anyMagnitudeType);
				if (result == null) result = caseConfigurableObject(anyMagnitudeType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.ANY_NUM_TYPE: {
				AnyNumType anyNumType = (AnyNumType)theEObject;
				T result = caseAnyNumType(anyNumType);
				if (result == null) result = caseAnyMagnitudeType(anyNumType);
				if (result == null) result = caseAnyElementaryType(anyNumType);
				if (result == null) result = caseAnyType(anyNumType);
				if (result == null) result = caseDataType(anyNumType);
				if (result == null) result = caseLibraryElement(anyNumType);
				if (result == null) result = caseINamedElement(anyNumType);
				if (result == null) result = caseConfigurableObject(anyNumType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.ANY_REAL_TYPE: {
				AnyRealType anyRealType = (AnyRealType)theEObject;
				T result = caseAnyRealType(anyRealType);
				if (result == null) result = caseAnyNumType(anyRealType);
				if (result == null) result = caseAnyMagnitudeType(anyRealType);
				if (result == null) result = caseAnyElementaryType(anyRealType);
				if (result == null) result = caseAnyType(anyRealType);
				if (result == null) result = caseDataType(anyRealType);
				if (result == null) result = caseLibraryElement(anyRealType);
				if (result == null) result = caseINamedElement(anyRealType);
				if (result == null) result = caseConfigurableObject(anyRealType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.REAL_TYPE: {
				RealType realType = (RealType)theEObject;
				T result = caseRealType(realType);
				if (result == null) result = caseAnyRealType(realType);
				if (result == null) result = caseAnyNumType(realType);
				if (result == null) result = caseAnyMagnitudeType(realType);
				if (result == null) result = caseAnyElementaryType(realType);
				if (result == null) result = caseAnyType(realType);
				if (result == null) result = caseDataType(realType);
				if (result == null) result = caseLibraryElement(realType);
				if (result == null) result = caseINamedElement(realType);
				if (result == null) result = caseConfigurableObject(realType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.LREAL_TYPE: {
				LrealType lrealType = (LrealType)theEObject;
				T result = caseLrealType(lrealType);
				if (result == null) result = caseAnyRealType(lrealType);
				if (result == null) result = caseAnyNumType(lrealType);
				if (result == null) result = caseAnyMagnitudeType(lrealType);
				if (result == null) result = caseAnyElementaryType(lrealType);
				if (result == null) result = caseAnyType(lrealType);
				if (result == null) result = caseDataType(lrealType);
				if (result == null) result = caseLibraryElement(lrealType);
				if (result == null) result = caseINamedElement(lrealType);
				if (result == null) result = caseConfigurableObject(lrealType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.ANY_INT_TYPE: {
				AnyIntType anyIntType = (AnyIntType)theEObject;
				T result = caseAnyIntType(anyIntType);
				if (result == null) result = caseAnyNumType(anyIntType);
				if (result == null) result = caseAnyMagnitudeType(anyIntType);
				if (result == null) result = caseAnyElementaryType(anyIntType);
				if (result == null) result = caseAnyType(anyIntType);
				if (result == null) result = caseDataType(anyIntType);
				if (result == null) result = caseLibraryElement(anyIntType);
				if (result == null) result = caseINamedElement(anyIntType);
				if (result == null) result = caseConfigurableObject(anyIntType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.ANY_UNSIGNED_TYPE: {
				AnyUnsignedType anyUnsignedType = (AnyUnsignedType)theEObject;
				T result = caseAnyUnsignedType(anyUnsignedType);
				if (result == null) result = caseAnyIntType(anyUnsignedType);
				if (result == null) result = caseAnyNumType(anyUnsignedType);
				if (result == null) result = caseAnyMagnitudeType(anyUnsignedType);
				if (result == null) result = caseAnyElementaryType(anyUnsignedType);
				if (result == null) result = caseAnyType(anyUnsignedType);
				if (result == null) result = caseDataType(anyUnsignedType);
				if (result == null) result = caseLibraryElement(anyUnsignedType);
				if (result == null) result = caseINamedElement(anyUnsignedType);
				if (result == null) result = caseConfigurableObject(anyUnsignedType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.USINT_TYPE: {
				UsintType usintType = (UsintType)theEObject;
				T result = caseUsintType(usintType);
				if (result == null) result = caseAnyUnsignedType(usintType);
				if (result == null) result = caseAnyIntType(usintType);
				if (result == null) result = caseAnyNumType(usintType);
				if (result == null) result = caseAnyMagnitudeType(usintType);
				if (result == null) result = caseAnyElementaryType(usintType);
				if (result == null) result = caseAnyType(usintType);
				if (result == null) result = caseDataType(usintType);
				if (result == null) result = caseLibraryElement(usintType);
				if (result == null) result = caseINamedElement(usintType);
				if (result == null) result = caseConfigurableObject(usintType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.UINT_TYPE: {
				UintType uintType = (UintType)theEObject;
				T result = caseUintType(uintType);
				if (result == null) result = caseAnyUnsignedType(uintType);
				if (result == null) result = caseAnyIntType(uintType);
				if (result == null) result = caseAnyNumType(uintType);
				if (result == null) result = caseAnyMagnitudeType(uintType);
				if (result == null) result = caseAnyElementaryType(uintType);
				if (result == null) result = caseAnyType(uintType);
				if (result == null) result = caseDataType(uintType);
				if (result == null) result = caseLibraryElement(uintType);
				if (result == null) result = caseINamedElement(uintType);
				if (result == null) result = caseConfigurableObject(uintType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.UDINT_TYPE: {
				UdintType udintType = (UdintType)theEObject;
				T result = caseUdintType(udintType);
				if (result == null) result = caseAnyUnsignedType(udintType);
				if (result == null) result = caseAnyIntType(udintType);
				if (result == null) result = caseAnyNumType(udintType);
				if (result == null) result = caseAnyMagnitudeType(udintType);
				if (result == null) result = caseAnyElementaryType(udintType);
				if (result == null) result = caseAnyType(udintType);
				if (result == null) result = caseDataType(udintType);
				if (result == null) result = caseLibraryElement(udintType);
				if (result == null) result = caseINamedElement(udintType);
				if (result == null) result = caseConfigurableObject(udintType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.ULINT_TYPE: {
				UlintType ulintType = (UlintType)theEObject;
				T result = caseUlintType(ulintType);
				if (result == null) result = caseAnyUnsignedType(ulintType);
				if (result == null) result = caseAnyIntType(ulintType);
				if (result == null) result = caseAnyNumType(ulintType);
				if (result == null) result = caseAnyMagnitudeType(ulintType);
				if (result == null) result = caseAnyElementaryType(ulintType);
				if (result == null) result = caseAnyType(ulintType);
				if (result == null) result = caseDataType(ulintType);
				if (result == null) result = caseLibraryElement(ulintType);
				if (result == null) result = caseINamedElement(ulintType);
				if (result == null) result = caseConfigurableObject(ulintType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.ANY_SIGNED_TYPE: {
				AnySignedType anySignedType = (AnySignedType)theEObject;
				T result = caseAnySignedType(anySignedType);
				if (result == null) result = caseAnyIntType(anySignedType);
				if (result == null) result = caseAnyNumType(anySignedType);
				if (result == null) result = caseAnyMagnitudeType(anySignedType);
				if (result == null) result = caseAnyElementaryType(anySignedType);
				if (result == null) result = caseAnyType(anySignedType);
				if (result == null) result = caseDataType(anySignedType);
				if (result == null) result = caseLibraryElement(anySignedType);
				if (result == null) result = caseINamedElement(anySignedType);
				if (result == null) result = caseConfigurableObject(anySignedType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.SINT_TYPE: {
				SintType sintType = (SintType)theEObject;
				T result = caseSintType(sintType);
				if (result == null) result = caseAnySignedType(sintType);
				if (result == null) result = caseAnyIntType(sintType);
				if (result == null) result = caseAnyNumType(sintType);
				if (result == null) result = caseAnyMagnitudeType(sintType);
				if (result == null) result = caseAnyElementaryType(sintType);
				if (result == null) result = caseAnyType(sintType);
				if (result == null) result = caseDataType(sintType);
				if (result == null) result = caseLibraryElement(sintType);
				if (result == null) result = caseINamedElement(sintType);
				if (result == null) result = caseConfigurableObject(sintType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.INT_TYPE: {
				IntType intType = (IntType)theEObject;
				T result = caseIntType(intType);
				if (result == null) result = caseAnySignedType(intType);
				if (result == null) result = caseAnyIntType(intType);
				if (result == null) result = caseAnyNumType(intType);
				if (result == null) result = caseAnyMagnitudeType(intType);
				if (result == null) result = caseAnyElementaryType(intType);
				if (result == null) result = caseAnyType(intType);
				if (result == null) result = caseDataType(intType);
				if (result == null) result = caseLibraryElement(intType);
				if (result == null) result = caseINamedElement(intType);
				if (result == null) result = caseConfigurableObject(intType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.DINT_TYPE: {
				DintType dintType = (DintType)theEObject;
				T result = caseDintType(dintType);
				if (result == null) result = caseAnySignedType(dintType);
				if (result == null) result = caseAnyIntType(dintType);
				if (result == null) result = caseAnyNumType(dintType);
				if (result == null) result = caseAnyMagnitudeType(dintType);
				if (result == null) result = caseAnyElementaryType(dintType);
				if (result == null) result = caseAnyType(dintType);
				if (result == null) result = caseDataType(dintType);
				if (result == null) result = caseLibraryElement(dintType);
				if (result == null) result = caseINamedElement(dintType);
				if (result == null) result = caseConfigurableObject(dintType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.LINT_TYPE: {
				LintType lintType = (LintType)theEObject;
				T result = caseLintType(lintType);
				if (result == null) result = caseAnySignedType(lintType);
				if (result == null) result = caseAnyIntType(lintType);
				if (result == null) result = caseAnyNumType(lintType);
				if (result == null) result = caseAnyMagnitudeType(lintType);
				if (result == null) result = caseAnyElementaryType(lintType);
				if (result == null) result = caseAnyType(lintType);
				if (result == null) result = caseDataType(lintType);
				if (result == null) result = caseLibraryElement(lintType);
				if (result == null) result = caseINamedElement(lintType);
				if (result == null) result = caseConfigurableObject(lintType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.ANY_DURATION_TYPE: {
				AnyDurationType anyDurationType = (AnyDurationType)theEObject;
				T result = caseAnyDurationType(anyDurationType);
				if (result == null) result = caseAnyMagnitudeType(anyDurationType);
				if (result == null) result = caseAnyElementaryType(anyDurationType);
				if (result == null) result = caseAnyType(anyDurationType);
				if (result == null) result = caseDataType(anyDurationType);
				if (result == null) result = caseLibraryElement(anyDurationType);
				if (result == null) result = caseINamedElement(anyDurationType);
				if (result == null) result = caseConfigurableObject(anyDurationType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.TIME_TYPE: {
				TimeType timeType = (TimeType)theEObject;
				T result = caseTimeType(timeType);
				if (result == null) result = caseAnyDurationType(timeType);
				if (result == null) result = caseAnyMagnitudeType(timeType);
				if (result == null) result = caseAnyElementaryType(timeType);
				if (result == null) result = caseAnyType(timeType);
				if (result == null) result = caseDataType(timeType);
				if (result == null) result = caseLibraryElement(timeType);
				if (result == null) result = caseINamedElement(timeType);
				if (result == null) result = caseConfigurableObject(timeType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.LTIME_TYPE: {
				LtimeType ltimeType = (LtimeType)theEObject;
				T result = caseLtimeType(ltimeType);
				if (result == null) result = caseAnyDurationType(ltimeType);
				if (result == null) result = caseAnyMagnitudeType(ltimeType);
				if (result == null) result = caseAnyElementaryType(ltimeType);
				if (result == null) result = caseAnyType(ltimeType);
				if (result == null) result = caseDataType(ltimeType);
				if (result == null) result = caseLibraryElement(ltimeType);
				if (result == null) result = caseINamedElement(ltimeType);
				if (result == null) result = caseConfigurableObject(ltimeType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.ANY_BIT_TYPE: {
				AnyBitType anyBitType = (AnyBitType)theEObject;
				T result = caseAnyBitType(anyBitType);
				if (result == null) result = caseAnyElementaryType(anyBitType);
				if (result == null) result = caseAnyType(anyBitType);
				if (result == null) result = caseDataType(anyBitType);
				if (result == null) result = caseLibraryElement(anyBitType);
				if (result == null) result = caseINamedElement(anyBitType);
				if (result == null) result = caseConfigurableObject(anyBitType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.BOOL_TYPE: {
				BoolType boolType = (BoolType)theEObject;
				T result = caseBoolType(boolType);
				if (result == null) result = caseAnyBitType(boolType);
				if (result == null) result = caseAnyElementaryType(boolType);
				if (result == null) result = caseAnyType(boolType);
				if (result == null) result = caseDataType(boolType);
				if (result == null) result = caseLibraryElement(boolType);
				if (result == null) result = caseINamedElement(boolType);
				if (result == null) result = caseConfigurableObject(boolType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.BYTE_TYPE: {
				ByteType byteType = (ByteType)theEObject;
				T result = caseByteType(byteType);
				if (result == null) result = caseAnyBitType(byteType);
				if (result == null) result = caseAnyElementaryType(byteType);
				if (result == null) result = caseAnyType(byteType);
				if (result == null) result = caseDataType(byteType);
				if (result == null) result = caseLibraryElement(byteType);
				if (result == null) result = caseINamedElement(byteType);
				if (result == null) result = caseConfigurableObject(byteType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.WORD_TYPE: {
				WordType wordType = (WordType)theEObject;
				T result = caseWordType(wordType);
				if (result == null) result = caseAnyBitType(wordType);
				if (result == null) result = caseAnyElementaryType(wordType);
				if (result == null) result = caseAnyType(wordType);
				if (result == null) result = caseDataType(wordType);
				if (result == null) result = caseLibraryElement(wordType);
				if (result == null) result = caseINamedElement(wordType);
				if (result == null) result = caseConfigurableObject(wordType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.DWORD_TYPE: {
				DwordType dwordType = (DwordType)theEObject;
				T result = caseDwordType(dwordType);
				if (result == null) result = caseAnyBitType(dwordType);
				if (result == null) result = caseAnyElementaryType(dwordType);
				if (result == null) result = caseAnyType(dwordType);
				if (result == null) result = caseDataType(dwordType);
				if (result == null) result = caseLibraryElement(dwordType);
				if (result == null) result = caseINamedElement(dwordType);
				if (result == null) result = caseConfigurableObject(dwordType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.LWORD_TYPE: {
				LwordType lwordType = (LwordType)theEObject;
				T result = caseLwordType(lwordType);
				if (result == null) result = caseAnyBitType(lwordType);
				if (result == null) result = caseAnyElementaryType(lwordType);
				if (result == null) result = caseAnyType(lwordType);
				if (result == null) result = caseDataType(lwordType);
				if (result == null) result = caseLibraryElement(lwordType);
				if (result == null) result = caseINamedElement(lwordType);
				if (result == null) result = caseConfigurableObject(lwordType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.ANY_CHARS_TYPE: {
				AnyCharsType anyCharsType = (AnyCharsType)theEObject;
				T result = caseAnyCharsType(anyCharsType);
				if (result == null) result = caseAnyElementaryType(anyCharsType);
				if (result == null) result = caseAnyType(anyCharsType);
				if (result == null) result = caseDataType(anyCharsType);
				if (result == null) result = caseLibraryElement(anyCharsType);
				if (result == null) result = caseINamedElement(anyCharsType);
				if (result == null) result = caseConfigurableObject(anyCharsType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.ANY_SCHARS_TYPE: {
				AnySCharsType anySCharsType = (AnySCharsType)theEObject;
				T result = caseAnySCharsType(anySCharsType);
				if (result == null) result = caseAnyCharsType(anySCharsType);
				if (result == null) result = caseAnyElementaryType(anySCharsType);
				if (result == null) result = caseAnyType(anySCharsType);
				if (result == null) result = caseDataType(anySCharsType);
				if (result == null) result = caseLibraryElement(anySCharsType);
				if (result == null) result = caseINamedElement(anySCharsType);
				if (result == null) result = caseConfigurableObject(anySCharsType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.ANY_WCHARS_TYPE: {
				AnyWCharsType anyWCharsType = (AnyWCharsType)theEObject;
				T result = caseAnyWCharsType(anyWCharsType);
				if (result == null) result = caseAnyCharsType(anyWCharsType);
				if (result == null) result = caseAnyElementaryType(anyWCharsType);
				if (result == null) result = caseAnyType(anyWCharsType);
				if (result == null) result = caseDataType(anyWCharsType);
				if (result == null) result = caseLibraryElement(anyWCharsType);
				if (result == null) result = caseINamedElement(anyWCharsType);
				if (result == null) result = caseConfigurableObject(anyWCharsType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.ANY_STRING_TYPE: {
				AnyStringType anyStringType = (AnyStringType)theEObject;
				T result = caseAnyStringType(anyStringType);
				if (result == null) result = caseAnyCharsType(anyStringType);
				if (result == null) result = caseAnyElementaryType(anyStringType);
				if (result == null) result = caseAnyType(anyStringType);
				if (result == null) result = caseDataType(anyStringType);
				if (result == null) result = caseLibraryElement(anyStringType);
				if (result == null) result = caseINamedElement(anyStringType);
				if (result == null) result = caseConfigurableObject(anyStringType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.STRING_TYPE: {
				StringType stringType = (StringType)theEObject;
				T result = caseStringType(stringType);
				if (result == null) result = caseAnyStringType(stringType);
				if (result == null) result = caseAnySCharsType(stringType);
				if (result == null) result = caseAnyCharsType(stringType);
				if (result == null) result = caseAnyElementaryType(stringType);
				if (result == null) result = caseAnyType(stringType);
				if (result == null) result = caseDataType(stringType);
				if (result == null) result = caseLibraryElement(stringType);
				if (result == null) result = caseINamedElement(stringType);
				if (result == null) result = caseConfigurableObject(stringType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.WSTRING_TYPE: {
				WstringType wstringType = (WstringType)theEObject;
				T result = caseWstringType(wstringType);
				if (result == null) result = caseAnyStringType(wstringType);
				if (result == null) result = caseAnyWCharsType(wstringType);
				if (result == null) result = caseAnyCharsType(wstringType);
				if (result == null) result = caseAnyElementaryType(wstringType);
				if (result == null) result = caseAnyType(wstringType);
				if (result == null) result = caseDataType(wstringType);
				if (result == null) result = caseLibraryElement(wstringType);
				if (result == null) result = caseINamedElement(wstringType);
				if (result == null) result = caseConfigurableObject(wstringType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.ANY_CHAR_TYPE: {
				AnyCharType anyCharType = (AnyCharType)theEObject;
				T result = caseAnyCharType(anyCharType);
				if (result == null) result = caseAnyCharsType(anyCharType);
				if (result == null) result = caseAnyElementaryType(anyCharType);
				if (result == null) result = caseAnyType(anyCharType);
				if (result == null) result = caseDataType(anyCharType);
				if (result == null) result = caseLibraryElement(anyCharType);
				if (result == null) result = caseINamedElement(anyCharType);
				if (result == null) result = caseConfigurableObject(anyCharType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.CHAR_TYPE: {
				CharType charType = (CharType)theEObject;
				T result = caseCharType(charType);
				if (result == null) result = caseAnyCharType(charType);
				if (result == null) result = caseAnySCharsType(charType);
				if (result == null) result = caseAnyCharsType(charType);
				if (result == null) result = caseAnyElementaryType(charType);
				if (result == null) result = caseAnyType(charType);
				if (result == null) result = caseDataType(charType);
				if (result == null) result = caseLibraryElement(charType);
				if (result == null) result = caseINamedElement(charType);
				if (result == null) result = caseConfigurableObject(charType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.WCHAR_TYPE: {
				WcharType wcharType = (WcharType)theEObject;
				T result = caseWcharType(wcharType);
				if (result == null) result = caseAnyCharType(wcharType);
				if (result == null) result = caseAnyWCharsType(wcharType);
				if (result == null) result = caseAnyCharsType(wcharType);
				if (result == null) result = caseAnyElementaryType(wcharType);
				if (result == null) result = caseAnyType(wcharType);
				if (result == null) result = caseDataType(wcharType);
				if (result == null) result = caseLibraryElement(wcharType);
				if (result == null) result = caseINamedElement(wcharType);
				if (result == null) result = caseConfigurableObject(wcharType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.ANY_DATE_TYPE: {
				AnyDateType anyDateType = (AnyDateType)theEObject;
				T result = caseAnyDateType(anyDateType);
				if (result == null) result = caseAnyElementaryType(anyDateType);
				if (result == null) result = caseAnyType(anyDateType);
				if (result == null) result = caseDataType(anyDateType);
				if (result == null) result = caseLibraryElement(anyDateType);
				if (result == null) result = caseINamedElement(anyDateType);
				if (result == null) result = caseConfigurableObject(anyDateType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.DATE_AND_TIME_TYPE: {
				DateAndTimeType dateAndTimeType = (DateAndTimeType)theEObject;
				T result = caseDateAndTimeType(dateAndTimeType);
				if (result == null) result = caseAnyDateType(dateAndTimeType);
				if (result == null) result = caseAnyElementaryType(dateAndTimeType);
				if (result == null) result = caseAnyType(dateAndTimeType);
				if (result == null) result = caseDataType(dateAndTimeType);
				if (result == null) result = caseLibraryElement(dateAndTimeType);
				if (result == null) result = caseINamedElement(dateAndTimeType);
				if (result == null) result = caseConfigurableObject(dateAndTimeType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.LDT_TYPE: {
				LdtType ldtType = (LdtType)theEObject;
				T result = caseLdtType(ldtType);
				if (result == null) result = caseAnyDateType(ldtType);
				if (result == null) result = caseAnyElementaryType(ldtType);
				if (result == null) result = caseAnyType(ldtType);
				if (result == null) result = caseDataType(ldtType);
				if (result == null) result = caseLibraryElement(ldtType);
				if (result == null) result = caseINamedElement(ldtType);
				if (result == null) result = caseConfigurableObject(ldtType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.DATE_TYPE: {
				DateType dateType = (DateType)theEObject;
				T result = caseDateType(dateType);
				if (result == null) result = caseAnyDateType(dateType);
				if (result == null) result = caseAnyElementaryType(dateType);
				if (result == null) result = caseAnyType(dateType);
				if (result == null) result = caseDataType(dateType);
				if (result == null) result = caseLibraryElement(dateType);
				if (result == null) result = caseINamedElement(dateType);
				if (result == null) result = caseConfigurableObject(dateType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.TIME_OF_DAY_TYPE: {
				TimeOfDayType timeOfDayType = (TimeOfDayType)theEObject;
				T result = caseTimeOfDayType(timeOfDayType);
				if (result == null) result = caseAnyDateType(timeOfDayType);
				if (result == null) result = caseAnyElementaryType(timeOfDayType);
				if (result == null) result = caseAnyType(timeOfDayType);
				if (result == null) result = caseDataType(timeOfDayType);
				if (result == null) result = caseLibraryElement(timeOfDayType);
				if (result == null) result = caseINamedElement(timeOfDayType);
				if (result == null) result = caseConfigurableObject(timeOfDayType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.LTOD_TYPE: {
				LtodType ltodType = (LtodType)theEObject;
				T result = caseLtodType(ltodType);
				if (result == null) result = caseAnyDateType(ltodType);
				if (result == null) result = caseAnyElementaryType(ltodType);
				if (result == null) result = caseAnyType(ltodType);
				if (result == null) result = caseDataType(ltodType);
				if (result == null) result = caseLibraryElement(ltodType);
				if (result == null) result = caseINamedElement(ltodType);
				if (result == null) result = caseConfigurableObject(ltodType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.LDATE_TYPE: {
				LdateType ldateType = (LdateType)theEObject;
				T result = caseLdateType(ldateType);
				if (result == null) result = caseAnyDateType(ldateType);
				if (result == null) result = caseAnyElementaryType(ldateType);
				if (result == null) result = caseAnyType(ldateType);
				if (result == null) result = caseDataType(ldateType);
				if (result == null) result = caseLibraryElement(ldateType);
				if (result == null) result = caseINamedElement(ldateType);
				if (result == null) result = caseConfigurableObject(ldateType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.INTERNAL_DATA_TYPE: {
				InternalDataType internalDataType = (InternalDataType)theEObject;
				T result = caseInternalDataType(internalDataType);
				if (result == null) result = caseDataType(internalDataType);
				if (result == null) result = caseLibraryElement(internalDataType);
				if (result == null) result = caseINamedElement(internalDataType);
				if (result == null) result = caseConfigurableObject(internalDataType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Any Derived Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Any Derived Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAnyDerivedType(AnyDerivedType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Array Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Array Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseArrayType(ArrayType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDataType(DataType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Directly Derived Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Directly Derived Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDirectlyDerivedType(DirectlyDerivedType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Enumerated Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Enumerated Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEnumeratedType(EnumeratedType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Enumerated Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Enumerated Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEnumeratedValue(EnumeratedValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Structured Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Structured Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStructuredType(StructuredType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Subrange</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Subrange</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSubrange(Subrange object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Subrange Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Subrange Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSubrangeType(SubrangeType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Value Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Value Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseValueType(ValueType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Derived Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Derived Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDerivedType(DerivedType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Event Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Event Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEventType(EventType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Any Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Any Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAnyType(AnyType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Any Elementary Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Any Elementary Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAnyElementaryType(AnyElementaryType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Any Magnitude Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Any Magnitude Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAnyMagnitudeType(AnyMagnitudeType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Any Num Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Any Num Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAnyNumType(AnyNumType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Any Real Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Any Real Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAnyRealType(AnyRealType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Real Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Real Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRealType(RealType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Lreal Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Lreal Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLrealType(LrealType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Any Int Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Any Int Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAnyIntType(AnyIntType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Any Unsigned Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Any Unsigned Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAnyUnsignedType(AnyUnsignedType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Usint Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Usint Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUsintType(UsintType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Uint Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Uint Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUintType(UintType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Udint Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Udint Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUdintType(UdintType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Ulint Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Ulint Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUlintType(UlintType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Any Signed Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Any Signed Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAnySignedType(AnySignedType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Sint Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Sint Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSintType(SintType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Int Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Int Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIntType(IntType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Dint Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Dint Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDintType(DintType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Lint Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Lint Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLintType(LintType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Any Duration Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Any Duration Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAnyDurationType(AnyDurationType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Time Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Time Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTimeType(TimeType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Ltime Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Ltime Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLtimeType(LtimeType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Any Bit Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Any Bit Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAnyBitType(AnyBitType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Bool Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Bool Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBoolType(BoolType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Byte Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Byte Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseByteType(ByteType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Word Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Word Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseWordType(WordType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Dword Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Dword Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDwordType(DwordType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Lword Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Lword Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLwordType(LwordType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Any Chars Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Any Chars Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAnyCharsType(AnyCharsType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Any SChars Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Any SChars Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAnySCharsType(AnySCharsType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Any WChars Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Any WChars Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAnyWCharsType(AnyWCharsType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Any String Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Any String Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAnyStringType(AnyStringType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>String Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>String Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStringType(StringType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Wstring Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Wstring Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseWstringType(WstringType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Any Char Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Any Char Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAnyCharType(AnyCharType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Char Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Char Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCharType(CharType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Wchar Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Wchar Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseWcharType(WcharType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Any Date Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Any Date Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAnyDateType(AnyDateType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Date And Time Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Date And Time Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDateAndTimeType(DateAndTimeType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Ldt Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Ldt Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLdtType(LdtType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Date Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Date Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDateType(DateType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Time Of Day Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Time Of Day Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTimeOfDayType(TimeOfDayType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Ltod Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Ltod Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLtodType(LtodType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Ldate Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Ldate Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLdateType(LdateType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Internal Data Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Internal Data Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInternalDataType(InternalDataType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>INamed Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>INamed Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseINamedElement(INamedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Configurable Object</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Configurable Object</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConfigurableObject(ConfigurableObject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Library Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Library Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLibraryElement(LibraryElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //DataSwitch
