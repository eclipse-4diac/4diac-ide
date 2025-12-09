/**
 * *******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *               2022-2023 Martin Erich Jobst
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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.TextFunction;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Text Function</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.TextFunctionImpl#getInputParameters <em>Input Parameters</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.TextFunctionImpl#getOutputParameters <em>Output Parameters</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.TextFunctionImpl#getInOutParameters <em>In Out Parameters</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.TextFunctionImpl#getReturnType <em>Return Type</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.TextFunctionImpl#isVarargs <em>Varargs</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.TextFunctionImpl#getText <em>Text</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class TextFunctionImpl extends FunctionImpl implements TextFunction {
	/**
	 * The cached value of the '{@link #getInputParameters() <em>Input Parameters</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInputParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<ITypedElement> inputParameters;

	/**
	 * The cached value of the '{@link #getOutputParameters() <em>Output Parameters</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutputParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<ITypedElement> outputParameters;

	/**
	 * The cached value of the '{@link #getInOutParameters() <em>In Out Parameters</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInOutParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<ITypedElement> inOutParameters;

	/**
	 * The cached value of the '{@link #getReturnType() <em>Return Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReturnType()
	 * @generated
	 * @ordered
	 */
	protected DataType returnType;

	/**
	 * The default value of the '{@link #isVarargs() <em>Varargs</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isVarargs()
	 * @generated
	 * @ordered
	 */
	protected static final boolean VARARGS_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isVarargs() <em>Varargs</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isVarargs()
	 * @generated
	 * @ordered
	 */
	protected boolean varargs = VARARGS_EDEFAULT;

	/**
	 * The default value of the '{@link #getText() <em>Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getText()
	 * @generated
	 * @ordered
	 */
	protected static final String TEXT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getText() <em>Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getText()
	 * @generated
	 * @ordered
	 */
	protected String text = TEXT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TextFunctionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.TEXT_FUNCTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ITypedElement> getInputParameters() {
		if (inputParameters == null) {
			inputParameters = new EObjectContainmentEList.Resolving<ITypedElement>(ITypedElement.class, this, LibraryElementPackage.TEXT_FUNCTION__INPUT_PARAMETERS);
		}
		return inputParameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ITypedElement> getOutputParameters() {
		if (outputParameters == null) {
			outputParameters = new EObjectContainmentEList.Resolving<ITypedElement>(ITypedElement.class, this, LibraryElementPackage.TEXT_FUNCTION__OUTPUT_PARAMETERS);
		}
		return outputParameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ITypedElement> getInOutParameters() {
		if (inOutParameters == null) {
			inOutParameters = new EObjectContainmentEList.Resolving<ITypedElement>(ITypedElement.class, this, LibraryElementPackage.TEXT_FUNCTION__IN_OUT_PARAMETERS);
		}
		return inOutParameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DataType getReturnType() {
		if (returnType != null && returnType.eIsProxy()) {
			InternalEObject oldReturnType = (InternalEObject)returnType;
			returnType = (DataType)eResolveProxy(oldReturnType);
			if (returnType != oldReturnType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LibraryElementPackage.TEXT_FUNCTION__RETURN_TYPE, oldReturnType, returnType));
			}
		}
		return returnType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataType basicGetReturnType() {
		return returnType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setReturnType(DataType newReturnType) {
		DataType oldReturnType = returnType;
		returnType = newReturnType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.TEXT_FUNCTION__RETURN_TYPE, oldReturnType, returnType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isVarargs() {
		return varargs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setVarargs(boolean newVarargs) {
		boolean oldVarargs = varargs;
		varargs = newVarargs;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.TEXT_FUNCTION__VARARGS, oldVarargs, varargs));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText() {
		return text;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setText(String newText) {
		String oldText = text;
		text = newText;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.TEXT_FUNCTION__TEXT, oldText, text));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LibraryElementPackage.TEXT_FUNCTION__INPUT_PARAMETERS:
				return ((InternalEList<?>)getInputParameters()).basicRemove(otherEnd, msgs);
			case LibraryElementPackage.TEXT_FUNCTION__OUTPUT_PARAMETERS:
				return ((InternalEList<?>)getOutputParameters()).basicRemove(otherEnd, msgs);
			case LibraryElementPackage.TEXT_FUNCTION__IN_OUT_PARAMETERS:
				return ((InternalEList<?>)getInOutParameters()).basicRemove(otherEnd, msgs);
			default:
				return super.eInverseRemove(otherEnd, featureID, msgs);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case LibraryElementPackage.TEXT_FUNCTION__INPUT_PARAMETERS:
				return getInputParameters();
			case LibraryElementPackage.TEXT_FUNCTION__OUTPUT_PARAMETERS:
				return getOutputParameters();
			case LibraryElementPackage.TEXT_FUNCTION__IN_OUT_PARAMETERS:
				return getInOutParameters();
			case LibraryElementPackage.TEXT_FUNCTION__RETURN_TYPE:
				if (resolve) return getReturnType();
				return basicGetReturnType();
			case LibraryElementPackage.TEXT_FUNCTION__VARARGS:
				return isVarargs();
			case LibraryElementPackage.TEXT_FUNCTION__TEXT:
				return getText();
			default:
				return super.eGet(featureID, resolve, coreType);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case LibraryElementPackage.TEXT_FUNCTION__INPUT_PARAMETERS:
				getInputParameters().clear();
				getInputParameters().addAll((Collection<? extends ITypedElement>)newValue);
				return;
			case LibraryElementPackage.TEXT_FUNCTION__OUTPUT_PARAMETERS:
				getOutputParameters().clear();
				getOutputParameters().addAll((Collection<? extends ITypedElement>)newValue);
				return;
			case LibraryElementPackage.TEXT_FUNCTION__IN_OUT_PARAMETERS:
				getInOutParameters().clear();
				getInOutParameters().addAll((Collection<? extends ITypedElement>)newValue);
				return;
			case LibraryElementPackage.TEXT_FUNCTION__RETURN_TYPE:
				setReturnType((DataType)newValue);
				return;
			case LibraryElementPackage.TEXT_FUNCTION__VARARGS:
				setVarargs((Boolean)newValue);
				return;
			case LibraryElementPackage.TEXT_FUNCTION__TEXT:
				setText((String)newValue);
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
			case LibraryElementPackage.TEXT_FUNCTION__INPUT_PARAMETERS:
				getInputParameters().clear();
				return;
			case LibraryElementPackage.TEXT_FUNCTION__OUTPUT_PARAMETERS:
				getOutputParameters().clear();
				return;
			case LibraryElementPackage.TEXT_FUNCTION__IN_OUT_PARAMETERS:
				getInOutParameters().clear();
				return;
			case LibraryElementPackage.TEXT_FUNCTION__RETURN_TYPE:
				setReturnType((DataType)null);
				return;
			case LibraryElementPackage.TEXT_FUNCTION__VARARGS:
				setVarargs(VARARGS_EDEFAULT);
				return;
			case LibraryElementPackage.TEXT_FUNCTION__TEXT:
				setText(TEXT_EDEFAULT);
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
			case LibraryElementPackage.TEXT_FUNCTION__INPUT_PARAMETERS:
				return inputParameters != null && !inputParameters.isEmpty();
			case LibraryElementPackage.TEXT_FUNCTION__OUTPUT_PARAMETERS:
				return outputParameters != null && !outputParameters.isEmpty();
			case LibraryElementPackage.TEXT_FUNCTION__IN_OUT_PARAMETERS:
				return inOutParameters != null && !inOutParameters.isEmpty();
			case LibraryElementPackage.TEXT_FUNCTION__RETURN_TYPE:
				return returnType != null;
			case LibraryElementPackage.TEXT_FUNCTION__VARARGS:
				return varargs != VARARGS_EDEFAULT;
			case LibraryElementPackage.TEXT_FUNCTION__TEXT:
				return TEXT_EDEFAULT == null ? text != null : !TEXT_EDEFAULT.equals(text);
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
		result.append(" (varargs: "); //$NON-NLS-1$
		result.append(varargs);
		result.append(", text: "); //$NON-NLS-1$
		result.append(text);
		result.append(')');
		return result.toString();
	}

} //TextFunctionImpl
