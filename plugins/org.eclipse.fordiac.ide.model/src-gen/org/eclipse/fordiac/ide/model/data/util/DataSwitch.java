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
package org.eclipse.fordiac.ide.model.data.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.fordiac.ide.model.data.ArrayType;
import org.eclipse.fordiac.ide.model.data.DataPackage;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.DerivedType;
import org.eclipse.fordiac.ide.model.data.DirectlyDerivedType;
import org.eclipse.fordiac.ide.model.data.ElementaryType;
import org.eclipse.fordiac.ide.model.data.EnumeratedType;
import org.eclipse.fordiac.ide.model.data.EnumeratedValue;
import org.eclipse.fordiac.ide.model.data.EventType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.data.Subrange;
import org.eclipse.fordiac.ide.model.data.SubrangeType;
import org.eclipse.fordiac.ide.model.data.ValueType;
import org.eclipse.fordiac.ide.model.data.VarInitialization;
import org.eclipse.fordiac.ide.model.libraryElement.I4DIACElement;
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
			case DataPackage.ARRAY_TYPE: {
				ArrayType arrayType = (ArrayType)theEObject;
				T result = caseArrayType(arrayType);
				if (result == null) result = caseDataType(arrayType);
				if (result == null) result = caseLibraryElement(arrayType);
				if (result == null) result = caseINamedElement(arrayType);
				if (result == null) result = caseI4DIACElement(arrayType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.DATA_TYPE: {
				DataType dataType = (DataType)theEObject;
				T result = caseDataType(dataType);
				if (result == null) result = caseLibraryElement(dataType);
				if (result == null) result = caseINamedElement(dataType);
				if (result == null) result = caseI4DIACElement(dataType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.DIRECTLY_DERIVED_TYPE: {
				DirectlyDerivedType directlyDerivedType = (DirectlyDerivedType)theEObject;
				T result = caseDirectlyDerivedType(directlyDerivedType);
				if (result == null) result = caseDerivedType(directlyDerivedType);
				if (result == null) result = caseValueType(directlyDerivedType);
				if (result == null) result = caseDataType(directlyDerivedType);
				if (result == null) result = caseLibraryElement(directlyDerivedType);
				if (result == null) result = caseINamedElement(directlyDerivedType);
				if (result == null) result = caseI4DIACElement(directlyDerivedType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.ENUMERATED_TYPE: {
				EnumeratedType enumeratedType = (EnumeratedType)theEObject;
				T result = caseEnumeratedType(enumeratedType);
				if (result == null) result = caseValueType(enumeratedType);
				if (result == null) result = caseDataType(enumeratedType);
				if (result == null) result = caseLibraryElement(enumeratedType);
				if (result == null) result = caseINamedElement(enumeratedType);
				if (result == null) result = caseI4DIACElement(enumeratedType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.ENUMERATED_VALUE: {
				EnumeratedValue enumeratedValue = (EnumeratedValue)theEObject;
				T result = caseEnumeratedValue(enumeratedValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.STRUCTURED_TYPE: {
				StructuredType structuredType = (StructuredType)theEObject;
				T result = caseStructuredType(structuredType);
				if (result == null) result = caseDataType(structuredType);
				if (result == null) result = caseLibraryElement(structuredType);
				if (result == null) result = caseINamedElement(structuredType);
				if (result == null) result = caseI4DIACElement(structuredType);
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
				if (result == null) result = caseI4DIACElement(subrangeType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.VAR_INITIALIZATION: {
				VarInitialization varInitialization = (VarInitialization)theEObject;
				T result = caseVarInitialization(varInitialization);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.VALUE_TYPE: {
				ValueType valueType = (ValueType)theEObject;
				T result = caseValueType(valueType);
				if (result == null) result = caseDataType(valueType);
				if (result == null) result = caseLibraryElement(valueType);
				if (result == null) result = caseINamedElement(valueType);
				if (result == null) result = caseI4DIACElement(valueType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.ELEMENTARY_TYPE: {
				ElementaryType elementaryType = (ElementaryType)theEObject;
				T result = caseElementaryType(elementaryType);
				if (result == null) result = caseValueType(elementaryType);
				if (result == null) result = caseDataType(elementaryType);
				if (result == null) result = caseLibraryElement(elementaryType);
				if (result == null) result = caseINamedElement(elementaryType);
				if (result == null) result = caseI4DIACElement(elementaryType);
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
				if (result == null) result = caseI4DIACElement(derivedType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DataPackage.EVENT_TYPE: {
				EventType eventType = (EventType)theEObject;
				T result = caseEventType(eventType);
				if (result == null) result = caseDataType(eventType);
				if (result == null) result = caseLibraryElement(eventType);
				if (result == null) result = caseINamedElement(eventType);
				if (result == null) result = caseI4DIACElement(eventType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
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
	 * Returns the result of interpreting the object as an instance of '<em>Var Initialization</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Var Initialization</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVarInitialization(VarInitialization object) {
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
	 * Returns the result of interpreting the object as an instance of '<em>Elementary Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Elementary Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseElementaryType(ElementaryType object) {
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
	 * Returns the result of interpreting the object as an instance of '<em>I4DIAC Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>I4DIAC Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseI4DIACElement(I4DIACElement object) {
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
