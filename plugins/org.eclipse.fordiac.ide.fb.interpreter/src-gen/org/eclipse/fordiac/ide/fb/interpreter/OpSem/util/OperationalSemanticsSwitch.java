/**
 * *******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License 2.0 which is available at http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Antonio Garmendía, Bianca Wiesmayr
 *          - initial implementation and/or documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.fb.interpreter.OpSem.util;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.SimpleFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Value;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance
 * hierarchy. It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object and proceeding up the
 * inheritance hierarchy until a non-null result is returned, which is the
 * result of the switch. <!-- end-user-doc -->
 *
 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage
 * @generated
 */
public class OperationalSemanticsSwitch<T> extends Switch<T> {
	/**
	 * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected static OperationalSemanticsPackage modelPackage;

	/**
	 * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @generated
	 */
	public OperationalSemanticsSwitch() {
		if (modelPackage == null) {
			modelPackage = OperationalSemanticsPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a
	 * non null result; it yields that result. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
		case OperationalSemanticsPackage.EVENT_OCCURRENCE: {
			EventOccurrence eventOccurrence = (EventOccurrence) theEObject;
			T result = caseEventOccurrence(eventOccurrence);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case OperationalSemanticsPackage.EVENT_MANAGER: {
			EventManager eventManager = (EventManager) theEObject;
			T result = caseEventManager(eventManager);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case OperationalSemanticsPackage.FB_RUNTIME_ABSTRACT: {
			FBRuntimeAbstract fbRuntimeAbstract = (FBRuntimeAbstract) theEObject;
			T result = caseFBRuntimeAbstract(fbRuntimeAbstract);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case OperationalSemanticsPackage.BASIC_FB_TYPE_RUNTIME: {
			BasicFBTypeRuntime basicFBTypeRuntime = (BasicFBTypeRuntime) theEObject;
			T result = caseBasicFBTypeRuntime(basicFBTypeRuntime);
			if (result == null)
				result = caseFBRuntimeAbstract(basicFBTypeRuntime);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case OperationalSemanticsPackage.SIMPLE_FB_TYPE_RUNTIME: {
			SimpleFBTypeRuntime simpleFBTypeRuntime = (SimpleFBTypeRuntime) theEObject;
			T result = caseSimpleFBTypeRuntime(simpleFBTypeRuntime);
			if (result == null)
				result = caseFBRuntimeAbstract(simpleFBTypeRuntime);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case OperationalSemanticsPackage.TRANSACTION: {
			Transaction transaction = (Transaction) theEObject;
			T result = caseTransaction(transaction);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case OperationalSemanticsPackage.FB_NETWORK_RUNTIME: {
			FBNetworkRuntime fbNetworkRuntime = (FBNetworkRuntime) theEObject;
			T result = caseFBNetworkRuntime(fbNetworkRuntime);
			if (result == null)
				result = caseFBRuntimeAbstract(fbNetworkRuntime);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case OperationalSemanticsPackage.FB_TRANSACTION: {
			FBTransaction fbTransaction = (FBTransaction) theEObject;
			T result = caseFBTransaction(fbTransaction);
			if (result == null)
				result = caseTransaction(fbTransaction);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case OperationalSemanticsPackage.CONNECTION_TO_VALUE_MAP: {
			@SuppressWarnings("unchecked")
			Map.Entry<Connection, Value> connectionToValueMap = (Map.Entry<Connection, Value>) theEObject;
			T result = caseConnectionToValueMap(connectionToValueMap);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case OperationalSemanticsPackage.RUNTIME_MAP: {
			@SuppressWarnings("unchecked")
			Map.Entry<FBNetworkElement, FBRuntimeAbstract> runtimeMap = (Map.Entry<FBNetworkElement, FBRuntimeAbstract>) theEObject;
			T result = caseRuntimeMap(runtimeMap);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		default:
			return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Event
	 * Occurrence</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Event
	 *         Occurrence</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEventOccurrence(EventOccurrence object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Event
	 * Manager</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Event
	 *         Manager</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEventManager(EventManager object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>FB
	 * Runtime Abstract</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>FB
	 *         Runtime Abstract</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFBRuntimeAbstract(FBRuntimeAbstract object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Basic FB
	 * Type Runtime</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Basic FB
	 *         Type Runtime</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBasicFBTypeRuntime(BasicFBTypeRuntime object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Simple
	 * FB Type Runtime</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Simple
	 *         FB Type Runtime</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSimpleFBTypeRuntime(SimpleFBTypeRuntime object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Transaction</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Transaction</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTransaction(Transaction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>FB
	 * Network Runtime</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>FB
	 *         Network Runtime</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFBNetworkRuntime(FBNetworkRuntime object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>FB
	 * Transaction</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>FB
	 *         Transaction</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFBTransaction(FBTransaction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Connection To Value Map</em>'. <!-- begin-user-doc --> This
	 * implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Connection To Value Map</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConnectionToValueMap(Map.Entry<Connection, Value> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Runtime
	 * Map</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Runtime
	 *         Map</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRuntimeMap(Map.Entry<FBNetworkElement, FBRuntimeAbstract> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>EObject</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last
	 * case anyway. <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} // OperationalSemanticsSwitch
