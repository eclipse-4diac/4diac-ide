/**
 * *******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Martin Jobst
 *      - initial API and implementation and/or initial documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.xmiexport.xmiexport.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.eclipse.fordiac.ide.xmiexport.xmiexport.*;

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
 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportPackage
 * @generated
 */
public class XMIExportSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static XMIExportPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XMIExportSwitch() {
		if (modelPackage == null) {
			modelPackage = XMIExportPackage.eINSTANCE;
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
			case XMIExportPackage.XMI_EXPORT_ATTRIBUTE_VALUES: {
				XMIExportAttributeValues xmiExportAttributeValues = (XMIExportAttributeValues)theEObject;
				T result = caseXMIExportAttributeValues(xmiExportAttributeValues);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case XMIExportPackage.XMI_EXPORT_ATTRIBUTE_VALUE: {
				XMIExportAttributeValue xmiExportAttributeValue = (XMIExportAttributeValue)theEObject;
				T result = caseXMIExportAttributeValue(xmiExportAttributeValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case XMIExportPackage.XMI_EXPORT_INITIAL_VALUES: {
				XMIExportInitialValues xmiExportInitialValues = (XMIExportInitialValues)theEObject;
				T result = caseXMIExportInitialValues(xmiExportInitialValues);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case XMIExportPackage.XMI_EXPORT_INITIAL_VALUE: {
				XMIExportInitialValue xmiExportInitialValue = (XMIExportInitialValue)theEObject;
				T result = caseXMIExportInitialValue(xmiExportInitialValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case XMIExportPackage.XMI_EXPORT_TYPE_DECLARATIONS: {
				XMIExportTypeDeclarations xmiExportTypeDeclarations = (XMIExportTypeDeclarations)theEObject;
				T result = caseXMIExportTypeDeclarations(xmiExportTypeDeclarations);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case XMIExportPackage.XMI_EXPORT_TYPE_DECLARATION: {
				XMIExportTypeDeclaration xmiExportTypeDeclaration = (XMIExportTypeDeclaration)theEObject;
				T result = caseXMIExportTypeDeclaration(xmiExportTypeDeclaration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Attribute Values</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Attribute Values</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXMIExportAttributeValues(XMIExportAttributeValues object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Attribute Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Attribute Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXMIExportAttributeValue(XMIExportAttributeValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Initial Values</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Initial Values</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXMIExportInitialValues(XMIExportInitialValues object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Initial Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Initial Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXMIExportInitialValue(XMIExportInitialValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Type Declarations</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Type Declarations</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXMIExportTypeDeclarations(XMIExportTypeDeclarations object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Type Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Type Declaration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXMIExportTypeDeclaration(XMIExportTypeDeclaration object) {
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

} //XMIExportSwitch
