/********************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.libraryElement.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Output Primitive</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.OutputPrimitiveImpl#getTestResult <em>Test Result</em>}</li>
 * </ul>
 *
 * @generated
 */
public class OutputPrimitiveImpl extends PrimitiveImpl implements OutputPrimitive {
	/**
	 * The default value of the '{@link #getTestResult() <em>Test Result</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTestResult()
	 * @generated
	 * @ordered
	 */
	protected static final int TEST_RESULT_EDEFAULT = 0;

	public static final int NOT_TESTED = 0;
	public static final int TEST_OK = 1;
	public static final int TEST_FAIL = -1;
	

	private int testResult = TEST_RESULT_EDEFAULT;
	

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OutputPrimitiveImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.OUTPUT_PRIMITIVE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public int getTestResult() {
		return testResult;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public void setTestResult(int newTestResult) {
		int oldTestResult = testResult;
		testResult=newTestResult;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.OUTPUT_PRIMITIVE__TEST_RESULT, oldTestResult, testResult));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	@Override
	public void unsetTestResult() {
		int oldTestResult = testResult;
		testResult=TEST_RESULT_EDEFAULT;
		if (eNotificationRequired())
		  eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.OUTPUT_PRIMITIVE__TEST_RESULT, oldTestResult, testResult));

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	@Override
	public boolean isSetTestResult() {
		return (testResult != TEST_RESULT_EDEFAULT);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case LibraryElementPackage.OUTPUT_PRIMITIVE__TEST_RESULT:
				return getTestResult();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case LibraryElementPackage.OUTPUT_PRIMITIVE__TEST_RESULT:
				setTestResult((Integer)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case LibraryElementPackage.OUTPUT_PRIMITIVE__TEST_RESULT:
				unsetTestResult();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case LibraryElementPackage.OUTPUT_PRIMITIVE__TEST_RESULT:
				return isSetTestResult();
		}
		return super.eIsSet(featureID);
	}

} //OutputPrimitiveImpl
