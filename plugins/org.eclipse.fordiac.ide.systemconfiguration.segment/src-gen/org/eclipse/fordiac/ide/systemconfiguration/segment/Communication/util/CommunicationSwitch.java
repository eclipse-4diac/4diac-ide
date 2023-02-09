/**
 * *******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License 2.0 which is available at http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr
 *          - initial implementation and/or documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.fordiac.ide.model.libraryElement.CommunicationConfiguration;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.CommunicationPackage;
import org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.TsnConfiguration;
import org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.TsnWindow;

/** <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance hierarchy. It supports the call
 * {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object and proceeding up the inheritance hierarchy until a non-null result is
 * returned, which is the result of the switch. <!-- end-user-doc -->
 *
 * @see org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.CommunicationPackage
 * @generated */
public class CommunicationSwitch<T> extends Switch<T> {
	/** The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	protected static CommunicationPackage modelPackage;

	/** Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	public CommunicationSwitch() {
		if (modelPackage == null) {
			modelPackage = CommunicationPackage.eINSTANCE;
		}
	}

	/** Checks whether this is a switch for the given package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/** Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that
	 * result. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
		case CommunicationPackage.TSN_CONFIGURATION: {
			TsnConfiguration tsnConfiguration = (TsnConfiguration) theEObject;
			T result = caseTsnConfiguration(tsnConfiguration);
			if (result == null)
				result = caseCommunicationConfiguration(tsnConfiguration);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case CommunicationPackage.TSN_WINDOW: {
			TsnWindow tsnWindow = (TsnWindow) theEObject;
			T result = caseTsnWindow(tsnWindow);
			if (result == null)
				result = caseINamedElement(tsnWindow);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		default:
			return defaultCase(theEObject);
		}
	}

	/** Returns the result of interpreting the object as an instance of '<em>Tsn Configuration</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Tsn Configuration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated */
	public T caseTsnConfiguration(TsnConfiguration object) {
		return null;
	}

	/** Returns the result of interpreting the object as an instance of '<em>Tsn Window</em>'. <!-- begin-user-doc -->
	 * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Tsn Window</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated */
	public T caseTsnWindow(TsnWindow object) {
		return null;
	}

	/** Returns the result of interpreting the object as an instance of '<em>Communication Configuration</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Communication Configuration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated */
	public T caseCommunicationConfiguration(CommunicationConfiguration object) {
		return null;
	}

	/** Returns the result of interpreting the object as an instance of '<em>INamed Element</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
	 * -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>INamed Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated */
	public T caseINamedElement(INamedElement object) {
		return null;
	}

	/** Returns the result of interpreting the object as an instance of '<em>EObject</em>'. <!-- begin-user-doc --> This
	 * implementation returns null; returning a non-null result will terminate the switch, but this is the last case
	 * anyway. <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} // CommunicationSwitch
