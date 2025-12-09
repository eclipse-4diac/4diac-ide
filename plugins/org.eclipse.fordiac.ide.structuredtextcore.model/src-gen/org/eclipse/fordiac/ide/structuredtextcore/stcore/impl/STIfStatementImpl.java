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
package org.eclipse.fordiac.ide.structuredtextcore.stcore.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STElseIfPart;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STElsePart;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STIfStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStatement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>ST If Statement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STIfStatementImpl#getCondition <em>Condition</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STIfStatementImpl#getStatements <em>Statements</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STIfStatementImpl#getElseifs <em>Elseifs</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STIfStatementImpl#getElse <em>Else</em>}</li>
 * </ul>
 *
 * @generated
 */
public class STIfStatementImpl extends STStatementImpl implements STIfStatement {
	/**
	 * The cached value of the '{@link #getCondition() <em>Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCondition()
	 * @generated
	 * @ordered
	 */
	protected STExpression condition;

	/**
	 * The cached value of the '{@link #getStatements() <em>Statements</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatements()
	 * @generated
	 * @ordered
	 */
	protected EList<STStatement> statements;

	/**
	 * The cached value of the '{@link #getElseifs() <em>Elseifs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElseifs()
	 * @generated
	 * @ordered
	 */
	protected EList<STElseIfPart> elseifs;

	/**
	 * The cached value of the '{@link #getElse() <em>Else</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElse()
	 * @generated
	 * @ordered
	 */
	protected STElsePart else_;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected STIfStatementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return STCorePackage.Literals.ST_IF_STATEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STExpression getCondition() {
		return condition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCondition(STExpression newCondition, NotificationChain msgs) {
		STExpression oldCondition = condition;
		condition = newCondition;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, STCorePackage.ST_IF_STATEMENT__CONDITION, oldCondition, newCondition);
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
	public void setCondition(STExpression newCondition) {
		if (newCondition != condition) {
			NotificationChain msgs = null;
			if (condition != null)
				msgs = ((InternalEObject)condition).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - STCorePackage.ST_IF_STATEMENT__CONDITION, null, msgs);
			if (newCondition != null)
				msgs = ((InternalEObject)newCondition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - STCorePackage.ST_IF_STATEMENT__CONDITION, null, msgs);
			msgs = basicSetCondition(newCondition, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, STCorePackage.ST_IF_STATEMENT__CONDITION, newCondition, newCondition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<STStatement> getStatements() {
		if (statements == null) {
			statements = new EObjectContainmentEList<STStatement>(STStatement.class, this, STCorePackage.ST_IF_STATEMENT__STATEMENTS);
		}
		return statements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<STElseIfPart> getElseifs() {
		if (elseifs == null) {
			elseifs = new EObjectContainmentEList<STElseIfPart>(STElseIfPart.class, this, STCorePackage.ST_IF_STATEMENT__ELSEIFS);
		}
		return elseifs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STElsePart getElse() {
		return else_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetElse(STElsePart newElse, NotificationChain msgs) {
		STElsePart oldElse = else_;
		else_ = newElse;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, STCorePackage.ST_IF_STATEMENT__ELSE, oldElse, newElse);
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
	public void setElse(STElsePart newElse) {
		if (newElse != else_) {
			NotificationChain msgs = null;
			if (else_ != null)
				msgs = ((InternalEObject)else_).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - STCorePackage.ST_IF_STATEMENT__ELSE, null, msgs);
			if (newElse != null)
				msgs = ((InternalEObject)newElse).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - STCorePackage.ST_IF_STATEMENT__ELSE, null, msgs);
			msgs = basicSetElse(newElse, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, STCorePackage.ST_IF_STATEMENT__ELSE, newElse, newElse));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case STCorePackage.ST_IF_STATEMENT__CONDITION:
				return basicSetCondition(null, msgs);
			case STCorePackage.ST_IF_STATEMENT__STATEMENTS:
				return ((InternalEList<?>)getStatements()).basicRemove(otherEnd, msgs);
			case STCorePackage.ST_IF_STATEMENT__ELSEIFS:
				return ((InternalEList<?>)getElseifs()).basicRemove(otherEnd, msgs);
			case STCorePackage.ST_IF_STATEMENT__ELSE:
				return basicSetElse(null, msgs);
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
			case STCorePackage.ST_IF_STATEMENT__CONDITION:
				return getCondition();
			case STCorePackage.ST_IF_STATEMENT__STATEMENTS:
				return getStatements();
			case STCorePackage.ST_IF_STATEMENT__ELSEIFS:
				return getElseifs();
			case STCorePackage.ST_IF_STATEMENT__ELSE:
				return getElse();
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
			case STCorePackage.ST_IF_STATEMENT__CONDITION:
				setCondition((STExpression)newValue);
				return;
			case STCorePackage.ST_IF_STATEMENT__STATEMENTS:
				getStatements().clear();
				getStatements().addAll((Collection<? extends STStatement>)newValue);
				return;
			case STCorePackage.ST_IF_STATEMENT__ELSEIFS:
				getElseifs().clear();
				getElseifs().addAll((Collection<? extends STElseIfPart>)newValue);
				return;
			case STCorePackage.ST_IF_STATEMENT__ELSE:
				setElse((STElsePart)newValue);
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
			case STCorePackage.ST_IF_STATEMENT__CONDITION:
				setCondition((STExpression)null);
				return;
			case STCorePackage.ST_IF_STATEMENT__STATEMENTS:
				getStatements().clear();
				return;
			case STCorePackage.ST_IF_STATEMENT__ELSEIFS:
				getElseifs().clear();
				return;
			case STCorePackage.ST_IF_STATEMENT__ELSE:
				setElse((STElsePart)null);
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
			case STCorePackage.ST_IF_STATEMENT__CONDITION:
				return condition != null;
			case STCorePackage.ST_IF_STATEMENT__STATEMENTS:
				return statements != null && !statements.isEmpty();
			case STCorePackage.ST_IF_STATEMENT__ELSEIFS:
				return elseifs != null && !elseifs.isEmpty();
			case STCorePackage.ST_IF_STATEMENT__ELSE:
				return else_ != null;
			default:
				return super.eIsSet(featureID);
		}
	}

} //STIfStatementImpl
