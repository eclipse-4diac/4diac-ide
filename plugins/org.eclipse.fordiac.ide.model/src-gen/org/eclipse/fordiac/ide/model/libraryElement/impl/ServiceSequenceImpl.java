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
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Service;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Service Sequence</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceSequenceImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceSequenceImpl#getComment <em>Comment</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceSequenceImpl#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceSequenceImpl#getServiceTransaction <em>Service Transaction</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceSequenceImpl#getServiceSequenceType <em>Service Sequence Type</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceSequenceImpl#getStartState <em>Start State</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceSequenceImpl#getEventManager <em>Event Manager</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ServiceSequenceImpl extends EObjectImpl implements ServiceSequence {
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
	 * The cached value of the '{@link #getAttributes() <em>Attributes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributes()
	 * @generated
	 * @ordered
	 */
	protected EList<Attribute> attributes;

	/**
	 * The cached value of the '{@link #getServiceTransaction() <em>Service Transaction</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getServiceTransaction()
	 * @generated
	 * @ordered
	 */
	protected EList<ServiceTransaction> serviceTransaction;

	/**
	 * The default value of the '{@link #getServiceSequenceType() <em>Service Sequence Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getServiceSequenceType()
	 * @generated
	 * @ordered
	 */
	protected static final String SERVICE_SEQUENCE_TYPE_EDEFAULT = "POSSIBLE"; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getServiceSequenceType() <em>Service Sequence Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getServiceSequenceType()
	 * @generated
	 * @ordered
	 */
	protected String serviceSequenceType = SERVICE_SEQUENCE_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getStartState() <em>Start State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartState()
	 * @generated
	 * @ordered
	 */
	protected static final String START_STATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getStartState() <em>Start State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartState()
	 * @generated
	 * @ordered
	 */
	protected String startState = START_STATE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getEventManager() <em>Event Manager</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEventManager()
	 * @generated
	 * @ordered
	 */
	protected EObject eventManager;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ServiceSequenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.SERVICE_SEQUENCE;
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
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.SERVICE_SEQUENCE__NAME, oldName, name));
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
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.SERVICE_SEQUENCE__COMMENT, oldComment, comment));
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
	public EList<Attribute> getAttributes() {
		if (attributes == null) {
			attributes = new EObjectContainmentEList.Resolving<Attribute>(Attribute.class, this, LibraryElementPackage.SERVICE_SEQUENCE__ATTRIBUTES);
		}
		return attributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ServiceTransaction> getServiceTransaction() {
		if (serviceTransaction == null) {
			serviceTransaction = new EObjectContainmentEList<ServiceTransaction>(ServiceTransaction.class, this, LibraryElementPackage.SERVICE_SEQUENCE__SERVICE_TRANSACTION);
		}
		return serviceTransaction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getServiceSequenceType() {
		return serviceSequenceType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setServiceSequenceType(String newServiceSequenceType) {
		String oldServiceSequenceType = serviceSequenceType;
		serviceSequenceType = newServiceSequenceType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.SERVICE_SEQUENCE__SERVICE_SEQUENCE_TYPE, oldServiceSequenceType, serviceSequenceType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getStartState() {
		return startState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setStartState(String newStartState) {
		String oldStartState = startState;
		startState = newStartState;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.SERVICE_SEQUENCE__START_STATE, oldStartState, startState));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject getEventManager() {
		return eventManager;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEventManager(EObject newEventManager, NotificationChain msgs) {
		EObject oldEventManager = eventManager;
		eventManager = newEventManager;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, LibraryElementPackage.SERVICE_SEQUENCE__EVENT_MANAGER, oldEventManager, newEventManager);
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
	public void setEventManager(EObject newEventManager) {
		if (newEventManager != eventManager) {
			NotificationChain msgs = null;
			if (eventManager != null)
				msgs = ((InternalEObject)eventManager).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.SERVICE_SEQUENCE__EVENT_MANAGER, null, msgs);
			if (newEventManager != null)
				msgs = ((InternalEObject)newEventManager).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.SERVICE_SEQUENCE__EVENT_MANAGER, null, msgs);
			msgs = basicSetEventManager(newEventManager, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.SERVICE_SEQUENCE__EVENT_MANAGER, newEventManager, newEventManager));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Service getService() {
		return (Service) this.eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAttribute(final String attributeName, final DataType type, final String value, final String comment) {
		org.eclipse.fordiac.ide.model.Annotations.setAttribute(this, attributeName, type, value, comment);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAttribute(final AttributeDeclaration attributeDeclaration, final String value, final String comment) {
		org.eclipse.fordiac.ide.model.Annotations.setAttribute(this, attributeDeclaration, value, comment);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Attribute getAttribute(final String attributeName) {
		return org.eclipse.fordiac.ide.model.Annotations.getAttribute(this, attributeName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getAttributeValue(final String attributeName) {
		return org.eclipse.fordiac.ide.model.Annotations.getAttributeValue(this, attributeName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean deleteAttribute(final String attributeName) {
		return org.eclipse.fordiac.ide.model.Annotations.deleteAttribute(this, attributeName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LibraryElementPackage.SERVICE_SEQUENCE__ATTRIBUTES:
				return ((InternalEList<?>)getAttributes()).basicRemove(otherEnd, msgs);
			case LibraryElementPackage.SERVICE_SEQUENCE__SERVICE_TRANSACTION:
				return ((InternalEList<?>)getServiceTransaction()).basicRemove(otherEnd, msgs);
			case LibraryElementPackage.SERVICE_SEQUENCE__EVENT_MANAGER:
				return basicSetEventManager(null, msgs);
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
			case LibraryElementPackage.SERVICE_SEQUENCE__NAME:
				return getName();
			case LibraryElementPackage.SERVICE_SEQUENCE__COMMENT:
				return getComment();
			case LibraryElementPackage.SERVICE_SEQUENCE__ATTRIBUTES:
				return getAttributes();
			case LibraryElementPackage.SERVICE_SEQUENCE__SERVICE_TRANSACTION:
				return getServiceTransaction();
			case LibraryElementPackage.SERVICE_SEQUENCE__SERVICE_SEQUENCE_TYPE:
				return getServiceSequenceType();
			case LibraryElementPackage.SERVICE_SEQUENCE__START_STATE:
				return getStartState();
			case LibraryElementPackage.SERVICE_SEQUENCE__EVENT_MANAGER:
				return getEventManager();
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
			case LibraryElementPackage.SERVICE_SEQUENCE__NAME:
				setName((String)newValue);
				return;
			case LibraryElementPackage.SERVICE_SEQUENCE__COMMENT:
				setComment((String)newValue);
				return;
			case LibraryElementPackage.SERVICE_SEQUENCE__ATTRIBUTES:
				getAttributes().clear();
				getAttributes().addAll((Collection<? extends Attribute>)newValue);
				return;
			case LibraryElementPackage.SERVICE_SEQUENCE__SERVICE_TRANSACTION:
				getServiceTransaction().clear();
				getServiceTransaction().addAll((Collection<? extends ServiceTransaction>)newValue);
				return;
			case LibraryElementPackage.SERVICE_SEQUENCE__SERVICE_SEQUENCE_TYPE:
				setServiceSequenceType((String)newValue);
				return;
			case LibraryElementPackage.SERVICE_SEQUENCE__START_STATE:
				setStartState((String)newValue);
				return;
			case LibraryElementPackage.SERVICE_SEQUENCE__EVENT_MANAGER:
				setEventManager((EObject)newValue);
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
			case LibraryElementPackage.SERVICE_SEQUENCE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case LibraryElementPackage.SERVICE_SEQUENCE__COMMENT:
				setComment(COMMENT_EDEFAULT);
				return;
			case LibraryElementPackage.SERVICE_SEQUENCE__ATTRIBUTES:
				getAttributes().clear();
				return;
			case LibraryElementPackage.SERVICE_SEQUENCE__SERVICE_TRANSACTION:
				getServiceTransaction().clear();
				return;
			case LibraryElementPackage.SERVICE_SEQUENCE__SERVICE_SEQUENCE_TYPE:
				setServiceSequenceType(SERVICE_SEQUENCE_TYPE_EDEFAULT);
				return;
			case LibraryElementPackage.SERVICE_SEQUENCE__START_STATE:
				setStartState(START_STATE_EDEFAULT);
				return;
			case LibraryElementPackage.SERVICE_SEQUENCE__EVENT_MANAGER:
				setEventManager((EObject)null);
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
			case LibraryElementPackage.SERVICE_SEQUENCE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case LibraryElementPackage.SERVICE_SEQUENCE__COMMENT:
				return COMMENT_EDEFAULT == null ? comment != null : !COMMENT_EDEFAULT.equals(comment);
			case LibraryElementPackage.SERVICE_SEQUENCE__ATTRIBUTES:
				return attributes != null && !attributes.isEmpty();
			case LibraryElementPackage.SERVICE_SEQUENCE__SERVICE_TRANSACTION:
				return serviceTransaction != null && !serviceTransaction.isEmpty();
			case LibraryElementPackage.SERVICE_SEQUENCE__SERVICE_SEQUENCE_TYPE:
				return SERVICE_SEQUENCE_TYPE_EDEFAULT == null ? serviceSequenceType != null : !SERVICE_SEQUENCE_TYPE_EDEFAULT.equals(serviceSequenceType);
			case LibraryElementPackage.SERVICE_SEQUENCE__START_STATE:
				return START_STATE_EDEFAULT == null ? startState != null : !START_STATE_EDEFAULT.equals(startState);
			case LibraryElementPackage.SERVICE_SEQUENCE__EVENT_MANAGER:
				return eventManager != null;
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
		if (baseClass == ConfigurableObject.class) {
			switch (derivedFeatureID) {
				case LibraryElementPackage.SERVICE_SEQUENCE__ATTRIBUTES: return LibraryElementPackage.CONFIGURABLE_OBJECT__ATTRIBUTES;
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
		if (baseClass == ConfigurableObject.class) {
			switch (baseFeatureID) {
				case LibraryElementPackage.CONFIGURABLE_OBJECT__ATTRIBUTES: return LibraryElementPackage.SERVICE_SEQUENCE__ATTRIBUTES;
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
		result.append(", serviceSequenceType: "); //$NON-NLS-1$
		result.append(serviceSequenceType);
		result.append(", startState: "); //$NON-NLS-1$
		result.append(startState);
		result.append(')');
		return result.toString();
	}

} //ServiceSequenceImpl
