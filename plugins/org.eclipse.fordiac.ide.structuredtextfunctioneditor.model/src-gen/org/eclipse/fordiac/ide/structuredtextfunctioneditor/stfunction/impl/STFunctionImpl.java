/**
 * *******************************************************************************
 * Copyright (c) 2022 Primetals Technologies GmbH,
 *               2022 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Martin Jobst, Martin Melik Merkumians
 *      - initial API and implementation and/or initial documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.impl;

import java.util.Collection;

import java.util.Map;
import java.util.stream.Stream;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.fordiac.ide.model.data.DataType;

import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;

import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclarationBlock;

import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>ST Function</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.impl.STFunctionImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.impl.STFunctionImpl#getComment <em>Comment</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.impl.STFunctionImpl#getReturnType <em>Return Type</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.impl.STFunctionImpl#getVarDeclarations <em>Var Declarations</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.impl.STFunctionImpl#getCode <em>Code</em>}</li>
 * </ul>
 *
 * @generated
 */
public class STFunctionImpl extends MinimalEObjectImpl.Container implements STFunction {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = ""; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getComment() <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComment()
	 * @generated
	 * @ordered
	 */
	protected static final String COMMENT_EDEFAULT = ""; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getComment() <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComment()
	 * @generated
	 * @ordered
	 */
	protected String comment = COMMENT_EDEFAULT;

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
	 * The cached value of the '{@link #getVarDeclarations() <em>Var Declarations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVarDeclarations()
	 * @generated
	 * @ordered
	 */
	protected EList<STVarDeclarationBlock> varDeclarations;

	/**
	 * The cached value of the '{@link #getCode() <em>Code</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCode()
	 * @generated
	 * @ordered
	 */
	protected EList<STStatement> code;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected STFunctionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return STFunctionPackage.Literals.ST_FUNCTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, STFunctionPackage.ST_FUNCTION__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getComment() {
		return comment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setComment(String newComment) {
		String oldComment = comment;
		comment = newComment;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, STFunctionPackage.ST_FUNCTION__COMMENT, oldComment, comment));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, STFunctionPackage.ST_FUNCTION__RETURN_TYPE, oldReturnType, returnType));
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
			eNotify(new ENotificationImpl(this, Notification.SET, STFunctionPackage.ST_FUNCTION__RETURN_TYPE, oldReturnType, returnType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<STVarDeclarationBlock> getVarDeclarations() {
		if (varDeclarations == null) {
			varDeclarations = new EObjectContainmentEList<STVarDeclarationBlock>(STVarDeclarationBlock.class, this, STFunctionPackage.ST_FUNCTION__VAR_DECLARATIONS);
		}
		return varDeclarations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<STStatement> getCode() {
		if (code == null) {
			code = new EObjectContainmentEList<STStatement>(STStatement.class, this, STFunctionPackage.ST_FUNCTION__CODE);
		}
		return code;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ITypedElement> getInputParameters() {
		return org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.impl.STFunctionAnnotations.getInputParameters(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ITypedElement> getOutputParameters() {
		return org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.impl.STFunctionAnnotations.getOutputParameters(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ITypedElement> getInOutParameters() {
		return org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.impl.STFunctionAnnotations.getInOutParameters(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSignature() {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.CallableAnnotations.getSignature(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isVarargs() {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.CallableAnnotations.isVarargs(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getQualifiedName() {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.NamedElementAnnotations.getQualifiedName(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Stream<INamedElement> findBySimpleName(final String name) {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.NamedElementAnnotations.findBySimpleName(this, name);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Stream<INamedElement> findByQualifiedName(final String name) {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.NamedElementAnnotations.findByQualifiedName(this, name);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean validateName(final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.NamedElementAnnotations.validateName(this, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case STFunctionPackage.ST_FUNCTION__VAR_DECLARATIONS:
				return ((InternalEList<?>)getVarDeclarations()).basicRemove(otherEnd, msgs);
			case STFunctionPackage.ST_FUNCTION__CODE:
				return ((InternalEList<?>)getCode()).basicRemove(otherEnd, msgs);
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
			case STFunctionPackage.ST_FUNCTION__NAME:
				return getName();
			case STFunctionPackage.ST_FUNCTION__COMMENT:
				return getComment();
			case STFunctionPackage.ST_FUNCTION__RETURN_TYPE:
				if (resolve) return getReturnType();
				return basicGetReturnType();
			case STFunctionPackage.ST_FUNCTION__VAR_DECLARATIONS:
				return getVarDeclarations();
			case STFunctionPackage.ST_FUNCTION__CODE:
				return getCode();
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
			case STFunctionPackage.ST_FUNCTION__NAME:
				setName((String)newValue);
				return;
			case STFunctionPackage.ST_FUNCTION__COMMENT:
				setComment((String)newValue);
				return;
			case STFunctionPackage.ST_FUNCTION__RETURN_TYPE:
				setReturnType((DataType)newValue);
				return;
			case STFunctionPackage.ST_FUNCTION__VAR_DECLARATIONS:
				getVarDeclarations().clear();
				getVarDeclarations().addAll((Collection<? extends STVarDeclarationBlock>)newValue);
				return;
			case STFunctionPackage.ST_FUNCTION__CODE:
				getCode().clear();
				getCode().addAll((Collection<? extends STStatement>)newValue);
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
			case STFunctionPackage.ST_FUNCTION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case STFunctionPackage.ST_FUNCTION__COMMENT:
				setComment(COMMENT_EDEFAULT);
				return;
			case STFunctionPackage.ST_FUNCTION__RETURN_TYPE:
				setReturnType((DataType)null);
				return;
			case STFunctionPackage.ST_FUNCTION__VAR_DECLARATIONS:
				getVarDeclarations().clear();
				return;
			case STFunctionPackage.ST_FUNCTION__CODE:
				getCode().clear();
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
			case STFunctionPackage.ST_FUNCTION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case STFunctionPackage.ST_FUNCTION__COMMENT:
				return COMMENT_EDEFAULT == null ? comment != null : !COMMENT_EDEFAULT.equals(comment);
			case STFunctionPackage.ST_FUNCTION__RETURN_TYPE:
				return returnType != null;
			case STFunctionPackage.ST_FUNCTION__VAR_DECLARATIONS:
				return varDeclarations != null && !varDeclarations.isEmpty();
			case STFunctionPackage.ST_FUNCTION__CODE:
				return code != null && !code.isEmpty();
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
		result.append(" (name: "); //$NON-NLS-1$
		result.append(name);
		result.append(", comment: "); //$NON-NLS-1$
		result.append(comment);
		result.append(')');
		return result.toString();
	}

} //STFunctionImpl
