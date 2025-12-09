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
package org.eclipse.fordiac.ide.xmiexport.xmiexport.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

import org.eclipse.fordiac.ide.structuredtextcore.stcore.STTypeDeclaration;

import org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportPackage;
import org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportTypeDeclaration;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Type Declaration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.impl.XMIExportTypeDeclarationImpl#getVariable <em>Variable</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.impl.XMIExportTypeDeclarationImpl#getTypeDeclaration <em>Type Declaration</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.impl.XMIExportTypeDeclarationImpl#getResultType <em>Result Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class XMIExportTypeDeclarationImpl extends MinimalEObjectImpl.Container implements XMIExportTypeDeclaration {
	/**
	 * The cached value of the '{@link #getVariable() <em>Variable</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVariable()
	 * @generated
	 * @ordered
	 */
	protected VarDeclaration variable;

	/**
	 * The cached value of the '{@link #getTypeDeclaration() <em>Type Declaration</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeDeclaration()
	 * @generated
	 * @ordered
	 */
	protected STTypeDeclaration typeDeclaration;

	/**
	 * The cached value of the '{@link #getResultType() <em>Result Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResultType()
	 * @generated
	 * @ordered
	 */
	protected INamedElement resultType;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XMIExportTypeDeclarationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return XMIExportPackage.Literals.XMI_EXPORT_TYPE_DECLARATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public VarDeclaration getVariable() {
		if (variable != null && variable.eIsProxy()) {
			InternalEObject oldVariable = (InternalEObject)variable;
			variable = (VarDeclaration)eResolveProxy(oldVariable);
			if (variable != oldVariable) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, XMIExportPackage.XMI_EXPORT_TYPE_DECLARATION__VARIABLE, oldVariable, variable));
			}
		}
		return variable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VarDeclaration basicGetVariable() {
		return variable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setVariable(VarDeclaration newVariable) {
		VarDeclaration oldVariable = variable;
		variable = newVariable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, XMIExportPackage.XMI_EXPORT_TYPE_DECLARATION__VARIABLE, oldVariable, variable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STTypeDeclaration getTypeDeclaration() {
		return typeDeclaration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTypeDeclaration(STTypeDeclaration newTypeDeclaration, NotificationChain msgs) {
		STTypeDeclaration oldTypeDeclaration = typeDeclaration;
		typeDeclaration = newTypeDeclaration;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, XMIExportPackage.XMI_EXPORT_TYPE_DECLARATION__TYPE_DECLARATION, oldTypeDeclaration, newTypeDeclaration);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTypeDeclaration(STTypeDeclaration newTypeDeclaration) {
		if (newTypeDeclaration != typeDeclaration) {
			NotificationChain msgs = null;
			if (typeDeclaration != null)
				msgs = ((InternalEObject)typeDeclaration).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - XMIExportPackage.XMI_EXPORT_TYPE_DECLARATION__TYPE_DECLARATION, null, msgs);
			if (newTypeDeclaration != null)
				msgs = ((InternalEObject)newTypeDeclaration).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - XMIExportPackage.XMI_EXPORT_TYPE_DECLARATION__TYPE_DECLARATION, null, msgs);
			msgs = basicSetTypeDeclaration(newTypeDeclaration, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, XMIExportPackage.XMI_EXPORT_TYPE_DECLARATION__TYPE_DECLARATION, newTypeDeclaration, newTypeDeclaration));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public INamedElement getResultType() {
		return resultType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetResultType(INamedElement newResultType, NotificationChain msgs) {
		INamedElement oldResultType = resultType;
		resultType = newResultType;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, XMIExportPackage.XMI_EXPORT_TYPE_DECLARATION__RESULT_TYPE, oldResultType, newResultType);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setResultType(INamedElement newResultType) {
		if (newResultType != resultType) {
			NotificationChain msgs = null;
			if (resultType != null)
				msgs = ((InternalEObject)resultType).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - XMIExportPackage.XMI_EXPORT_TYPE_DECLARATION__RESULT_TYPE, null, msgs);
			if (newResultType != null)
				msgs = ((InternalEObject)newResultType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - XMIExportPackage.XMI_EXPORT_TYPE_DECLARATION__RESULT_TYPE, null, msgs);
			msgs = basicSetResultType(newResultType, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, XMIExportPackage.XMI_EXPORT_TYPE_DECLARATION__RESULT_TYPE, newResultType, newResultType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case XMIExportPackage.XMI_EXPORT_TYPE_DECLARATION__TYPE_DECLARATION:
				return basicSetTypeDeclaration(null, msgs);
			case XMIExportPackage.XMI_EXPORT_TYPE_DECLARATION__RESULT_TYPE:
				return basicSetResultType(null, msgs);
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
			case XMIExportPackage.XMI_EXPORT_TYPE_DECLARATION__VARIABLE:
				if (resolve) return getVariable();
				return basicGetVariable();
			case XMIExportPackage.XMI_EXPORT_TYPE_DECLARATION__TYPE_DECLARATION:
				return getTypeDeclaration();
			case XMIExportPackage.XMI_EXPORT_TYPE_DECLARATION__RESULT_TYPE:
				return getResultType();
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
			case XMIExportPackage.XMI_EXPORT_TYPE_DECLARATION__VARIABLE:
				setVariable((VarDeclaration)newValue);
				return;
			case XMIExportPackage.XMI_EXPORT_TYPE_DECLARATION__TYPE_DECLARATION:
				setTypeDeclaration((STTypeDeclaration)newValue);
				return;
			case XMIExportPackage.XMI_EXPORT_TYPE_DECLARATION__RESULT_TYPE:
				setResultType((INamedElement)newValue);
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
			case XMIExportPackage.XMI_EXPORT_TYPE_DECLARATION__VARIABLE:
				setVariable((VarDeclaration)null);
				return;
			case XMIExportPackage.XMI_EXPORT_TYPE_DECLARATION__TYPE_DECLARATION:
				setTypeDeclaration((STTypeDeclaration)null);
				return;
			case XMIExportPackage.XMI_EXPORT_TYPE_DECLARATION__RESULT_TYPE:
				setResultType((INamedElement)null);
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
			case XMIExportPackage.XMI_EXPORT_TYPE_DECLARATION__VARIABLE:
				return variable != null;
			case XMIExportPackage.XMI_EXPORT_TYPE_DECLARATION__TYPE_DECLARATION:
				return typeDeclaration != null;
			case XMIExportPackage.XMI_EXPORT_TYPE_DECLARATION__RESULT_TYPE:
				return resultType != null;
			default:
				return super.eIsSet(featureID);
		}
	}

} //XMIExportTypeDeclarationImpl
