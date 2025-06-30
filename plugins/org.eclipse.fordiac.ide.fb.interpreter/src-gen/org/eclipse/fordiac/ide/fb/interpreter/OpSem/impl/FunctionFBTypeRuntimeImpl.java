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
package org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FunctionFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Function FB Type Runtime</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.FunctionFBTypeRuntimeImpl#getFunctionFBType
 * <em>Function FB Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FunctionFBTypeRuntimeImpl extends FBRuntimeAbstractImpl implements FunctionFBTypeRuntime {
	/**
	 * The cached value of the '{@link #getFunctionFBType() <em>Function FB
	 * Type</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @see #getFunctionFBType()
	 * @generated
	 * @ordered
	 */
	protected FunctionFBType functionFBType;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected FunctionFBTypeRuntimeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OperationalSemanticsPackage.Literals.FUNCTION_FB_TYPE_RUNTIME;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public FunctionFBType getFunctionFBType() {
		if (functionFBType != null && functionFBType.eIsProxy()) {
			InternalEObject oldFunctionFBType = (InternalEObject) functionFBType;
			functionFBType = (FunctionFBType) eResolveProxy(oldFunctionFBType);
			if (functionFBType != oldFunctionFBType) {
				InternalEObject newFunctionFBType = (InternalEObject) functionFBType;
				NotificationChain msgs = oldFunctionFBType.eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - OperationalSemanticsPackage.FUNCTION_FB_TYPE_RUNTIME__FUNCTION_FB_TYPE,
						null, null);
				if (newFunctionFBType.eInternalContainer() == null) {
					msgs = newFunctionFBType.eInverseAdd(this,
							EOPPOSITE_FEATURE_BASE
									- OperationalSemanticsPackage.FUNCTION_FB_TYPE_RUNTIME__FUNCTION_FB_TYPE,
							null, msgs);
				}
				if (msgs != null) {
					msgs.dispatch();
				}
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							OperationalSemanticsPackage.FUNCTION_FB_TYPE_RUNTIME__FUNCTION_FB_TYPE, oldFunctionFBType,
							functionFBType));
				}
			}
		}
		return functionFBType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public FunctionFBType basicGetFunctionFBType() {
		return functionFBType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetFunctionFBType(FunctionFBType newFunctionFBType, NotificationChain msgs) {
		FunctionFBType oldFunctionFBType = functionFBType;
		functionFBType = newFunctionFBType;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					OperationalSemanticsPackage.FUNCTION_FB_TYPE_RUNTIME__FUNCTION_FB_TYPE, oldFunctionFBType,
					newFunctionFBType);
			if (msgs == null) {
				msgs = notification;
			} else {
				msgs.add(notification);
			}
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setFunctionFBType(FunctionFBType newFunctionFBType) {
		if (newFunctionFBType != functionFBType) {
			NotificationChain msgs = null;
			if (functionFBType != null) {
				msgs = ((InternalEObject) functionFBType).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - OperationalSemanticsPackage.FUNCTION_FB_TYPE_RUNTIME__FUNCTION_FB_TYPE,
						null, msgs);
			}
			if (newFunctionFBType != null) {
				msgs = ((InternalEObject) newFunctionFBType).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - OperationalSemanticsPackage.FUNCTION_FB_TYPE_RUNTIME__FUNCTION_FB_TYPE,
						null, msgs);
			}
			msgs = basicSetFunctionFBType(newFunctionFBType, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					OperationalSemanticsPackage.FUNCTION_FB_TYPE_RUNTIME__FUNCTION_FB_TYPE, newFunctionFBType,
					newFunctionFBType));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public FunctionFBType getModel() {
		return functionFBType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case OperationalSemanticsPackage.FUNCTION_FB_TYPE_RUNTIME__FUNCTION_FB_TYPE:
			return basicSetFunctionFBType(null, msgs);
		default:
			return super.eInverseRemove(otherEnd, featureID, msgs);
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case OperationalSemanticsPackage.FUNCTION_FB_TYPE_RUNTIME__FUNCTION_FB_TYPE:
			if (resolve) {
				return getFunctionFBType();
			}
			return basicGetFunctionFBType();
		default:
			return super.eGet(featureID, resolve, coreType);
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case OperationalSemanticsPackage.FUNCTION_FB_TYPE_RUNTIME__FUNCTION_FB_TYPE:
			setFunctionFBType((FunctionFBType) newValue);
			return;
		default:
			super.eSet(featureID, newValue);
			return;
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case OperationalSemanticsPackage.FUNCTION_FB_TYPE_RUNTIME__FUNCTION_FB_TYPE:
			setFunctionFBType((FunctionFBType) null);
			return;
		default:
			super.eUnset(featureID);
			return;
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case OperationalSemanticsPackage.FUNCTION_FB_TYPE_RUNTIME__FUNCTION_FB_TYPE:
			return functionFBType != null;
		default:
			return super.eIsSet(featureID);
		}
	}

} // FunctionFBTypeRuntimeImpl
