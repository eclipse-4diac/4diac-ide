/**
 * *******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *               2022 Martin Erich Jobst
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

import java.util.Map;
import java.util.stream.Stream;
import org.eclipse.draw2d.geometry.Point;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>EC State</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ECStateImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ECStateImpl#getComment <em>Comment</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ECStateImpl#getPosition <em>Position</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ECStateImpl#getECAction <em>EC Action</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ECStateImpl#getOutTransitions <em>Out Transitions</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ECStateImpl#getInTransitions <em>In Transitions</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ECStateImpl#getECC <em>ECC</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ECStateImpl extends EObjectImpl implements ECState {
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
	 * The cached value of the '{@link #getPosition() <em>Position</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPosition()
	 * @generated
	 * @ordered
	 */
	protected Position position;

	/**
	 * The cached value of the '{@link #getECAction() <em>EC Action</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getECAction()
	 * @generated
	 * @ordered
	 */
	protected EList<ECAction> eCAction;

	/**
	 * The cached value of the '{@link #getOutTransitions() <em>Out Transitions</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutTransitions()
	 * @generated
	 * @ordered
	 */
	protected EList<ECTransition> outTransitions;

	/**
	 * The cached value of the '{@link #getInTransitions() <em>In Transitions</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInTransitions()
	 * @generated
	 * @ordered
	 */
	protected EList<ECTransition> inTransitions;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ECStateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.EC_STATE;
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
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.EC_STATE__NAME, oldName, name));
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
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.EC_STATE__COMMENT, oldComment, comment));
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
	public Position getPosition() {
		if (position != null && position.eIsProxy()) {
			InternalEObject oldPosition = (InternalEObject)position;
			position = (Position)eResolveProxy(oldPosition);
			if (position != oldPosition) {
				InternalEObject newPosition = (InternalEObject)position;
				NotificationChain msgs = oldPosition.eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.EC_STATE__POSITION, null, null);
				if (newPosition.eInternalContainer() == null) {
					msgs = newPosition.eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.EC_STATE__POSITION, null, msgs);
				}
				if (msgs != null) msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LibraryElementPackage.EC_STATE__POSITION, oldPosition, position));
			}
		}
		return position;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Position basicGetPosition() {
		return position;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPosition(Position newPosition, NotificationChain msgs) {
		Position oldPosition = position;
		position = newPosition;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, LibraryElementPackage.EC_STATE__POSITION, oldPosition, newPosition);
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
	public void setPosition(Position newPosition) {
		if (newPosition != position) {
			NotificationChain msgs = null;
			if (position != null)
				msgs = ((InternalEObject)position).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.EC_STATE__POSITION, null, msgs);
			if (newPosition != null)
				msgs = ((InternalEObject)newPosition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.EC_STATE__POSITION, null, msgs);
			msgs = basicSetPosition(newPosition, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.EC_STATE__POSITION, newPosition, newPosition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ECAction> getECAction() {
		if (eCAction == null) {
			eCAction = new EObjectContainmentWithInverseEList<ECAction>(ECAction.class, this, LibraryElementPackage.EC_STATE__EC_ACTION, LibraryElementPackage.EC_ACTION__EC_STATE);
		}
		return eCAction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
	public ECC getECC() {
		if (eContainerFeatureID() != LibraryElementPackage.EC_STATE__ECC) return null;
		return (ECC)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ECC basicGetECC() {
		if (eContainerFeatureID() != LibraryElementPackage.EC_STATE__ECC) return null;
		return (ECC)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetECC(ECC newECC, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newECC, LibraryElementPackage.EC_STATE__ECC, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setECC(ECC newECC) {
		if (newECC != eInternalContainer() || (eContainerFeatureID() != LibraryElementPackage.EC_STATE__ECC && newECC != null)) {
			if (EcoreUtil.isAncestor(this, newECC))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newECC != null)
				msgs = ((InternalEObject)newECC).eInverseAdd(this, LibraryElementPackage.ECC__EC_STATE, ECC.class, msgs);
			msgs = basicSetECC(newECC, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.EC_STATE__ECC, newECC, newECC));
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void updatePositionFromScreenCoordinates(final int x, final int y) {
		PositionAnnotation.updatePositionFromScreenCoordinates(this, x,y);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void updatePositionFromScreenCoordinates(final Point newPos) {
		updatePositionFromScreenCoordinates(newPos.x, newPos.y);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LibraryElementPackage.EC_STATE__EC_ACTION:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getECAction()).basicAdd(otherEnd, msgs);
			case LibraryElementPackage.EC_STATE__OUT_TRANSITIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getOutTransitions()).basicAdd(otherEnd, msgs);
			case LibraryElementPackage.EC_STATE__IN_TRANSITIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getInTransitions()).basicAdd(otherEnd, msgs);
			case LibraryElementPackage.EC_STATE__ECC:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetECC((ECC)otherEnd, msgs);
			default:
				return super.eInverseAdd(otherEnd, featureID, msgs);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LibraryElementPackage.EC_STATE__POSITION:
				return basicSetPosition(null, msgs);
			case LibraryElementPackage.EC_STATE__EC_ACTION:
				return ((InternalEList<?>)getECAction()).basicRemove(otherEnd, msgs);
			case LibraryElementPackage.EC_STATE__OUT_TRANSITIONS:
				return ((InternalEList<?>)getOutTransitions()).basicRemove(otherEnd, msgs);
			case LibraryElementPackage.EC_STATE__IN_TRANSITIONS:
				return ((InternalEList<?>)getInTransitions()).basicRemove(otherEnd, msgs);
			case LibraryElementPackage.EC_STATE__ECC:
				return basicSetECC(null, msgs);
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
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case LibraryElementPackage.EC_STATE__ECC:
				return eInternalContainer().eInverseRemove(this, LibraryElementPackage.ECC__EC_STATE, ECC.class, msgs);
			default:
				return super.eBasicRemoveFromContainerFeature(msgs);
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
			case LibraryElementPackage.EC_STATE__NAME:
				return getName();
			case LibraryElementPackage.EC_STATE__COMMENT:
				return getComment();
			case LibraryElementPackage.EC_STATE__POSITION:
				if (resolve) return getPosition();
				return basicGetPosition();
			case LibraryElementPackage.EC_STATE__EC_ACTION:
				return getECAction();
			case LibraryElementPackage.EC_STATE__OUT_TRANSITIONS:
				return getOutTransitions();
			case LibraryElementPackage.EC_STATE__IN_TRANSITIONS:
				return getInTransitions();
			case LibraryElementPackage.EC_STATE__ECC:
				if (resolve) return getECC();
				return basicGetECC();
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
			case LibraryElementPackage.EC_STATE__NAME:
				setName((String)newValue);
				return;
			case LibraryElementPackage.EC_STATE__COMMENT:
				setComment((String)newValue);
				return;
			case LibraryElementPackage.EC_STATE__POSITION:
				setPosition((Position)newValue);
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
			case LibraryElementPackage.EC_STATE__ECC:
				setECC((ECC)newValue);
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
			case LibraryElementPackage.EC_STATE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case LibraryElementPackage.EC_STATE__COMMENT:
				setComment(COMMENT_EDEFAULT);
				return;
			case LibraryElementPackage.EC_STATE__POSITION:
				setPosition((Position)null);
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
			case LibraryElementPackage.EC_STATE__ECC:
				setECC((ECC)null);
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
			case LibraryElementPackage.EC_STATE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case LibraryElementPackage.EC_STATE__COMMENT:
				return COMMENT_EDEFAULT == null ? comment != null : !COMMENT_EDEFAULT.equals(comment);
			case LibraryElementPackage.EC_STATE__POSITION:
				return position != null;
			case LibraryElementPackage.EC_STATE__EC_ACTION:
				return eCAction != null && !eCAction.isEmpty();
			case LibraryElementPackage.EC_STATE__OUT_TRANSITIONS:
				return outTransitions != null && !outTransitions.isEmpty();
			case LibraryElementPackage.EC_STATE__IN_TRANSITIONS:
				return inTransitions != null && !inTransitions.isEmpty();
			case LibraryElementPackage.EC_STATE__ECC:
				return basicGetECC() != null;
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
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == PositionableElement.class) {
			switch (derivedFeatureID) {
				case LibraryElementPackage.EC_STATE__POSITION: return LibraryElementPackage.POSITIONABLE_ELEMENT__POSITION;
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
				case LibraryElementPackage.POSITIONABLE_ELEMENT__POSITION: return LibraryElementPackage.EC_STATE__POSITION;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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

} //ECStateImpl
