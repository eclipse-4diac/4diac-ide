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
import java.util.Map;
import java.util.stream.Stream;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleECAction;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleECState;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Simple EC State</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.SimpleECStateImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.SimpleECStateImpl#getComment <em>Comment</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.SimpleECStateImpl#getSimpleECActions <em>Simple EC Actions</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.SimpleECStateImpl#getInputEvent <em>Input Event</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.SimpleECStateImpl#getSimpleFBType <em>Simple FB Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SimpleECStateImpl extends EObjectImpl implements SimpleECState {
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
	 * The cached value of the '{@link #getSimpleECActions() <em>Simple EC Actions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSimpleECActions()
	 * @generated
	 * @ordered
	 */
	protected EList<SimpleECAction> simpleECActions;

	/**
	 * The cached value of the '{@link #getInputEvent() <em>Input Event</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInputEvent()
	 * @generated
	 * @ordered
	 */
	protected Event inputEvent;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SimpleECStateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.SIMPLE_EC_STATE;
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
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.SIMPLE_EC_STATE__NAME, oldName, name));
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
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.SIMPLE_EC_STATE__COMMENT, oldComment, comment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<SimpleECAction> getSimpleECActions() {
		if (simpleECActions == null) {
			simpleECActions = new EObjectContainmentWithInverseEList<SimpleECAction>(SimpleECAction.class, this, LibraryElementPackage.SIMPLE_EC_STATE__SIMPLE_EC_ACTIONS, LibraryElementPackage.SIMPLE_EC_ACTION__SIMPLE_EC_STATE);
		}
		return simpleECActions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Event getInputEvent() {
		if (inputEvent != null && inputEvent.eIsProxy()) {
			InternalEObject oldInputEvent = (InternalEObject)inputEvent;
			inputEvent = (Event)eResolveProxy(oldInputEvent);
			if (inputEvent != oldInputEvent) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LibraryElementPackage.SIMPLE_EC_STATE__INPUT_EVENT, oldInputEvent, inputEvent));
			}
		}
		return inputEvent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Event basicGetInputEvent() {
		return inputEvent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setInputEvent(Event newInputEvent) {
		Event oldInputEvent = inputEvent;
		inputEvent = newInputEvent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.SIMPLE_EC_STATE__INPUT_EVENT, oldInputEvent, inputEvent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SimpleFBType getSimpleFBType() {
		if (eContainerFeatureID() != LibraryElementPackage.SIMPLE_EC_STATE__SIMPLE_FB_TYPE) return null;
		return (SimpleFBType)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimpleFBType basicGetSimpleFBType() {
		if (eContainerFeatureID() != LibraryElementPackage.SIMPLE_EC_STATE__SIMPLE_FB_TYPE) return null;
		return (SimpleFBType)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSimpleFBType(SimpleFBType newSimpleFBType, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newSimpleFBType, LibraryElementPackage.SIMPLE_EC_STATE__SIMPLE_FB_TYPE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSimpleFBType(SimpleFBType newSimpleFBType) {
		if (newSimpleFBType != eInternalContainer() || (eContainerFeatureID() != LibraryElementPackage.SIMPLE_EC_STATE__SIMPLE_FB_TYPE && newSimpleFBType != null)) {
			if (EcoreUtil.isAncestor(this, newSimpleFBType))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newSimpleFBType != null)
				msgs = ((InternalEObject)newSimpleFBType).eInverseAdd(this, LibraryElementPackage.SIMPLE_FB_TYPE__SIMPLE_EC_STATES, SimpleFBType.class, msgs);
			msgs = basicSetSimpleFBType(newSimpleFBType, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.SIMPLE_EC_STATE__SIMPLE_FB_TYPE, newSimpleFBType, newSimpleFBType));
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
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LibraryElementPackage.SIMPLE_EC_STATE__SIMPLE_EC_ACTIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getSimpleECActions()).basicAdd(otherEnd, msgs);
			case LibraryElementPackage.SIMPLE_EC_STATE__SIMPLE_FB_TYPE:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetSimpleFBType((SimpleFBType)otherEnd, msgs);
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
			case LibraryElementPackage.SIMPLE_EC_STATE__SIMPLE_EC_ACTIONS:
				return ((InternalEList<?>)getSimpleECActions()).basicRemove(otherEnd, msgs);
			case LibraryElementPackage.SIMPLE_EC_STATE__SIMPLE_FB_TYPE:
				return basicSetSimpleFBType(null, msgs);
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
			case LibraryElementPackage.SIMPLE_EC_STATE__SIMPLE_FB_TYPE:
				return eInternalContainer().eInverseRemove(this, LibraryElementPackage.SIMPLE_FB_TYPE__SIMPLE_EC_STATES, SimpleFBType.class, msgs);
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
			case LibraryElementPackage.SIMPLE_EC_STATE__NAME:
				return getName();
			case LibraryElementPackage.SIMPLE_EC_STATE__COMMENT:
				return getComment();
			case LibraryElementPackage.SIMPLE_EC_STATE__SIMPLE_EC_ACTIONS:
				return getSimpleECActions();
			case LibraryElementPackage.SIMPLE_EC_STATE__INPUT_EVENT:
				if (resolve) return getInputEvent();
				return basicGetInputEvent();
			case LibraryElementPackage.SIMPLE_EC_STATE__SIMPLE_FB_TYPE:
				if (resolve) return getSimpleFBType();
				return basicGetSimpleFBType();
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
			case LibraryElementPackage.SIMPLE_EC_STATE__NAME:
				setName((String)newValue);
				return;
			case LibraryElementPackage.SIMPLE_EC_STATE__COMMENT:
				setComment((String)newValue);
				return;
			case LibraryElementPackage.SIMPLE_EC_STATE__SIMPLE_EC_ACTIONS:
				getSimpleECActions().clear();
				getSimpleECActions().addAll((Collection<? extends SimpleECAction>)newValue);
				return;
			case LibraryElementPackage.SIMPLE_EC_STATE__INPUT_EVENT:
				setInputEvent((Event)newValue);
				return;
			case LibraryElementPackage.SIMPLE_EC_STATE__SIMPLE_FB_TYPE:
				setSimpleFBType((SimpleFBType)newValue);
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
			case LibraryElementPackage.SIMPLE_EC_STATE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case LibraryElementPackage.SIMPLE_EC_STATE__COMMENT:
				setComment(COMMENT_EDEFAULT);
				return;
			case LibraryElementPackage.SIMPLE_EC_STATE__SIMPLE_EC_ACTIONS:
				getSimpleECActions().clear();
				return;
			case LibraryElementPackage.SIMPLE_EC_STATE__INPUT_EVENT:
				setInputEvent((Event)null);
				return;
			case LibraryElementPackage.SIMPLE_EC_STATE__SIMPLE_FB_TYPE:
				setSimpleFBType((SimpleFBType)null);
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
			case LibraryElementPackage.SIMPLE_EC_STATE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case LibraryElementPackage.SIMPLE_EC_STATE__COMMENT:
				return COMMENT_EDEFAULT == null ? comment != null : !COMMENT_EDEFAULT.equals(comment);
			case LibraryElementPackage.SIMPLE_EC_STATE__SIMPLE_EC_ACTIONS:
				return simpleECActions != null && !simpleECActions.isEmpty();
			case LibraryElementPackage.SIMPLE_EC_STATE__INPUT_EVENT:
				return inputEvent != null;
			case LibraryElementPackage.SIMPLE_EC_STATE__SIMPLE_FB_TYPE:
				return basicGetSimpleFBType() != null;
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

} //SimpleECStateImpl
