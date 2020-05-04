/**
 * *******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH
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
package org.eclipse.fordiac.ide.model.libraryElement.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.LocalVariable;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Local Variable</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.LocalVariableImpl#getArrayStart <em>Array Start</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.LocalVariableImpl#getArrayStop <em>Array Stop</em>}</li>
 * </ul>
 *
 * @generated
 */
public class LocalVariableImpl extends VarDeclarationImpl implements LocalVariable {
	/**
	 * The default value of the '{@link #getArrayStart() <em>Array Start</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArrayStart()
	 * @generated
	 * @ordered
	 */
	protected static final int ARRAY_START_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getArrayStart() <em>Array Start</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArrayStart()
	 * @generated
	 * @ordered
	 */
	protected int arrayStart = ARRAY_START_EDEFAULT;

	/**
	 * The default value of the '{@link #getArrayStop() <em>Array Stop</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArrayStop()
	 * @generated
	 * @ordered
	 */
	protected static final int ARRAY_STOP_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getArrayStop() <em>Array Stop</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArrayStop()
	 * @generated
	 * @ordered
	 */
	protected int arrayStop = ARRAY_STOP_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LocalVariableImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.LOCAL_VARIABLE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getArrayStart() {
		return arrayStart;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setArrayStart(int newArrayStart) {
		int oldArrayStart = arrayStart;
		arrayStart = newArrayStart;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.LOCAL_VARIABLE__ARRAY_START, oldArrayStart, arrayStart));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getArrayStop() {
		return arrayStop;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setArrayStop(int newArrayStop) {
		int oldArrayStop = arrayStop;
		arrayStop = newArrayStop;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.LOCAL_VARIABLE__ARRAY_STOP, oldArrayStop, arrayStop));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getArraySize() {
		return arrayStop-arrayStart+1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case LibraryElementPackage.LOCAL_VARIABLE__ARRAY_START:
				return getArrayStart();
			case LibraryElementPackage.LOCAL_VARIABLE__ARRAY_STOP:
				return getArrayStop();
			default:
				return super.eGet(featureID, resolve, coreType);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case LibraryElementPackage.LOCAL_VARIABLE__ARRAY_START:
				setArrayStart((Integer)newValue);
				return;
			case LibraryElementPackage.LOCAL_VARIABLE__ARRAY_STOP:
				setArrayStop((Integer)newValue);
				return;
			default:
				super.eSet(featureID, newValue);
				return;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case LibraryElementPackage.LOCAL_VARIABLE__ARRAY_START:
				setArrayStart(ARRAY_START_EDEFAULT);
				return;
			case LibraryElementPackage.LOCAL_VARIABLE__ARRAY_STOP:
				setArrayStop(ARRAY_STOP_EDEFAULT);
				return;
			default:
				super.eUnset(featureID);
				return;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case LibraryElementPackage.LOCAL_VARIABLE__ARRAY_START:
				return arrayStart != ARRAY_START_EDEFAULT;
			case LibraryElementPackage.LOCAL_VARIABLE__ARRAY_STOP:
				return arrayStop != ARRAY_STOP_EDEFAULT;
			default:
				return super.eIsSet(featureID);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (arrayStart: "); //$NON-NLS-1$
		result.append(arrayStart);
		result.append(", arrayStop: "); //$NON-NLS-1$
		result.append(arrayStop);
		result.append(')');
		return result.toString();
	}

} //LocalVariableImpl
