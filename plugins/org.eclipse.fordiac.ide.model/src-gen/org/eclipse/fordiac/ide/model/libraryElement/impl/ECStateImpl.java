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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>EC State</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ECStateImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ECStateImpl#getComment <em>Comment</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ECStateImpl#getX <em>X</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ECStateImpl#getY <em>Y</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ECStateImpl#getECAction <em>EC Action</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ECStateImpl#getOutTransitions <em>Out Transitions</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ECStateImpl#getInTransitions <em>In Transitions</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ECStateImpl extends I4DIACElementImpl implements ECState {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getComment() <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getComment()
	 * @generated
	 * @ordered
	 */
	protected static final String COMMENT_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getComment() <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getComment()
	 * @generated
	 * @ordered
	 */
	protected String comment = COMMENT_EDEFAULT;

	/**
	 * The default value of the '{@link #getX() <em>X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getX()
	 * @generated
	 * @ordered
	 */
	protected static final int X_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getX() <em>X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getX()
	 * @generated
	 * @ordered
	 */
	protected int x = X_EDEFAULT;

	/**
	 * The default value of the '{@link #getY() <em>Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getY()
	 * @generated
	 * @ordered
	 */
	protected static final int Y_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getY() <em>Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getY()
	 * @generated
	 * @ordered
	 */
	protected int y = Y_EDEFAULT;

	/**
	 * The cached value of the '{@link #getECAction() <em>EC Action</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getECAction()
	 * @generated
	 * @ordered
	 */
	protected EList<ECAction> eCAction;

	/**
	 * The cached value of the '{@link #getOutTransitions() <em>Out Transitions</em>}' reference list.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getOutTransitions()
	 * @generated
	 * @ordered
	 */
	protected EList<ECTransition> outTransitions;

	/**
	 * The cached value of the '{@link #getInTransitions() <em>In Transitions</em>}' reference list.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getInTransitions()
	 * @generated
	 * @ordered
	 */
	protected EList<ECTransition> inTransitions;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected ECStateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.EC_STATE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ECAction> getECAction() {
		if (eCAction == null) {
			eCAction = new EObjectContainmentEList<ECAction>(ECAction.class, this, LibraryElementPackage.EC_STATE__EC_ACTION);
		}
		return eCAction;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getComment() {
		return comment;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setComment(String newComment) {
		String oldComment = comment;
		comment = newComment;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.EC_STATE__COMMENT, oldComment, comment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getX() {
		return x;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setX(int newX) {
		int oldX = x;
		x = newX;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.EC_STATE__X, oldX, x));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getY() {
		return y;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setY(int newY) {
		int oldY = y;
		y = newY;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.EC_STATE__Y, oldY, y));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		Boolean differentName = true;
		if (oldName != null) {
			differentName = !oldName.equals(name);		
		}
		
		if(differentName){
			//only check and notify if the name has realy changed
			NameRepository.checkNameIdentifier(this);
			if (eNotificationRequired()) {
				eNotify(new ENotificationImpl(this, Notification.SET,
						LibraryElementPackage.EC_STATE__NAME, oldName, name));
			}
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ECTransition> getOutTransitions() {
		if (outTransitions == null) {
			outTransitions = new EObjectWithInverseResolvingEList<ECTransition>(ECTransition.class, this, LibraryElementPackage.EC_STATE__OUT_TRANSITIONS, LibraryElementPackage.EC_TRANSITION__SOURCE);
		}
		return outTransitions;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ECTransition> getInTransitions() {
		if (inTransitions == null) {
			inTransitions = new EObjectWithInverseResolvingEList<ECTransition>(ECTransition.class, this, LibraryElementPackage.EC_STATE__IN_TRANSITIONS, LibraryElementPackage.EC_TRANSITION__DESTINATION);
		}
		return inTransitions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isStartState() {
		return org.eclipse.fordiac.ide.model.Annotations.isStartState(this);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID,
			NotificationChain msgs) {
		switch (featureID) {
			case LibraryElementPackage.EC_STATE__OUT_TRANSITIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getOutTransitions()).basicAdd(otherEnd, msgs);
			case LibraryElementPackage.EC_STATE__IN_TRANSITIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getInTransitions()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LibraryElementPackage.EC_STATE__EC_ACTION:
				return ((InternalEList<?>)getECAction()).basicRemove(otherEnd, msgs);
			case LibraryElementPackage.EC_STATE__OUT_TRANSITIONS:
				return ((InternalEList<?>)getOutTransitions()).basicRemove(otherEnd, msgs);
			case LibraryElementPackage.EC_STATE__IN_TRANSITIONS:
				return ((InternalEList<?>)getInTransitions()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case LibraryElementPackage.EC_STATE__NAME:
				return getName();
			case LibraryElementPackage.EC_STATE__COMMENT:
				return getComment();
			case LibraryElementPackage.EC_STATE__X:
				return getX();
			case LibraryElementPackage.EC_STATE__Y:
				return getY();
			case LibraryElementPackage.EC_STATE__EC_ACTION:
				return getECAction();
			case LibraryElementPackage.EC_STATE__OUT_TRANSITIONS:
				return getOutTransitions();
			case LibraryElementPackage.EC_STATE__IN_TRANSITIONS:
				return getInTransitions();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case LibraryElementPackage.EC_STATE__NAME:
				setName((String)newValue);
				return;
			case LibraryElementPackage.EC_STATE__COMMENT:
				setComment((String)newValue);
				return;
			case LibraryElementPackage.EC_STATE__X:
				setX((Integer)newValue);
				return;
			case LibraryElementPackage.EC_STATE__Y:
				setY((Integer)newValue);
				return;
			case LibraryElementPackage.EC_STATE__EC_ACTION:
				getECAction().clear();
				getECAction().addAll((Collection<? extends ECAction>)newValue);
				return;
			case LibraryElementPackage.EC_STATE__OUT_TRANSITIONS:
				getOutTransitions().clear();
				getOutTransitions().addAll((Collection<? extends ECTransition>)newValue);
				return;
			case LibraryElementPackage.EC_STATE__IN_TRANSITIONS:
				getInTransitions().clear();
				getInTransitions().addAll((Collection<? extends ECTransition>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case LibraryElementPackage.EC_STATE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case LibraryElementPackage.EC_STATE__COMMENT:
				setComment(COMMENT_EDEFAULT);
				return;
			case LibraryElementPackage.EC_STATE__X:
				setX(X_EDEFAULT);
				return;
			case LibraryElementPackage.EC_STATE__Y:
				setY(Y_EDEFAULT);
				return;
			case LibraryElementPackage.EC_STATE__EC_ACTION:
				getECAction().clear();
				return;
			case LibraryElementPackage.EC_STATE__OUT_TRANSITIONS:
				getOutTransitions().clear();
				return;
			case LibraryElementPackage.EC_STATE__IN_TRANSITIONS:
				getInTransitions().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case LibraryElementPackage.EC_STATE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case LibraryElementPackage.EC_STATE__COMMENT:
				return COMMENT_EDEFAULT == null ? comment != null : !COMMENT_EDEFAULT.equals(comment);
			case LibraryElementPackage.EC_STATE__X:
				return x != X_EDEFAULT;
			case LibraryElementPackage.EC_STATE__Y:
				return y != Y_EDEFAULT;
			case LibraryElementPackage.EC_STATE__EC_ACTION:
				return eCAction != null && !eCAction.isEmpty();
			case LibraryElementPackage.EC_STATE__OUT_TRANSITIONS:
				return outTransitions != null && !outTransitions.isEmpty();
			case LibraryElementPackage.EC_STATE__IN_TRANSITIONS:
				return inTransitions != null && !inTransitions.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == PositionableElement.class) {
			switch (derivedFeatureID) {
				case LibraryElementPackage.EC_STATE__X: return LibraryElementPackage.POSITIONABLE_ELEMENT__X;
				case LibraryElementPackage.EC_STATE__Y: return LibraryElementPackage.POSITIONABLE_ELEMENT__Y;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == PositionableElement.class) {
			switch (baseFeatureID) {
				case LibraryElementPackage.POSITIONABLE_ELEMENT__X: return LibraryElementPackage.EC_STATE__X;
				case LibraryElementPackage.POSITIONABLE_ELEMENT__Y: return LibraryElementPackage.EC_STATE__Y;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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
		result.append(", x: "); //$NON-NLS-1$
		result.append(x);
		result.append(", y: "); //$NON-NLS-1$
		result.append(y);
		result.append(')');
		return result.toString();
	}

} // ECStateImpl
