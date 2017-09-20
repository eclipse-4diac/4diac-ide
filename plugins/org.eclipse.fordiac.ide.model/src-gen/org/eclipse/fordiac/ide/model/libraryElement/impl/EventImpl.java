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
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.With;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Event</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.EventImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.EventImpl#getComment <em>Comment</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.EventImpl#isIsInput <em>Is Input</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.EventImpl#getInputConnections <em>Input Connections</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.EventImpl#getOutputConnections <em>Output Connections</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.EventImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.EventImpl#getValue <em>Value</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.EventImpl#getTypeName <em>Type Name</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.EventImpl#getWith <em>With</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EventImpl extends I4DIACElementImpl implements Event {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = "";

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
	protected static final String COMMENT_EDEFAULT = "";

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
	 * The default value of the '{@link #isIsInput() <em>Is Input</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsInput()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_INPUT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIsInput() <em>Is Input</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsInput()
	 * @generated
	 * @ordered
	 */
	protected boolean isInput = IS_INPUT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getInputConnections() <em>Input Connections</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInputConnections()
	 * @generated
	 * @ordered
	 */
	protected EList<Connection> inputConnections;

	/**
	 * The cached value of the '{@link #getOutputConnections() <em>Output Connections</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutputConnections()
	 * @generated
	 * @ordered
	 */
	protected EList<Connection> outputConnections;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected DataType type;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected Value value;

	/**
	 * The default value of the '{@link #getTypeName() <em>Type Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeName()
	 * @generated
	 * @ordered
	 */
	protected static final String TYPE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTypeName() <em>Type Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeName()
	 * @generated
	 * @ordered
	 */
	protected String typeName = TYPE_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getWith() <em>With</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWith()
	 * @generated
	 * @ordered
	 */
	protected EList<With> with;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EventImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.EVENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<With> getWith() {
		if (with == null) {
			with = new EObjectContainmentEList<With>(With.class, this, LibraryElementPackage.EVENT__WITH);
		}
		return with;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FBNetworkElement getFBNetworkElement() {
		return (eContainer() instanceof InterfaceList) ? ((InterfaceList)eContainer()).getFBNetworkElement() : null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComment(String newComment) {
		String oldComment = comment;
		comment = newComment;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.EVENT__COMMENT, oldComment, comment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		
		NameRepository.checkNameIdentifier(this);
		
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.EVENT__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIsInput() {
		return isInput;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsInput(boolean newIsInput) {
		boolean oldIsInput = isInput;
		isInput = newIsInput;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.EVENT__IS_INPUT, oldIsInput, isInput));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Connection> getInputConnections() {
		if (inputConnections == null) {
			inputConnections = new EObjectWithInverseResolvingEList<Connection>(Connection.class, this, LibraryElementPackage.EVENT__INPUT_CONNECTIONS, LibraryElementPackage.CONNECTION__DESTINATION);
		}
		return inputConnections;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Connection> getOutputConnections() {
		if (outputConnections == null) {
			outputConnections = new EObjectWithInverseResolvingEList<Connection>(Connection.class, this, LibraryElementPackage.EVENT__OUTPUT_CONNECTIONS, LibraryElementPackage.CONNECTION__SOURCE);
		}
		return outputConnections;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataType getType() {
		if (type != null && type.eIsProxy()) {
			InternalEObject oldType = (InternalEObject)type;
			type = (DataType)eResolveProxy(oldType);
			if (type != oldType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LibraryElementPackage.EVENT__TYPE, oldType, type));
			}
		}
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataType basicGetType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(DataType newType) {
		DataType oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.EVENT__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Value getValue() {
		if (value != null && value.eIsProxy()) {
			InternalEObject oldValue = (InternalEObject)value;
			value = (Value)eResolveProxy(oldValue);
			if (value != oldValue) {
				InternalEObject newValue = (InternalEObject)value;
				NotificationChain msgs = oldValue.eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.EVENT__VALUE, null, null);
				if (newValue.eInternalContainer() == null) {
					msgs = newValue.eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.EVENT__VALUE, null, msgs);
				}
				if (msgs != null) msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LibraryElementPackage.EVENT__VALUE, oldValue, value));
			}
		}
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Value basicGetValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetValue(Value newValue, NotificationChain msgs) {
		Value oldValue = value;
		value = newValue;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, LibraryElementPackage.EVENT__VALUE, oldValue, newValue);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValue(Value newValue) {
		if (newValue != value) {
			NotificationChain msgs = null;
			if (value != null)
				msgs = ((InternalEObject)value).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.EVENT__VALUE, null, msgs);
			if (newValue != null)
				msgs = ((InternalEObject)newValue).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.EVENT__VALUE, null, msgs);
			msgs = basicSetValue(newValue, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.EVENT__VALUE, newValue, newValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTypeName(String newTypeName) {
		String oldTypeName = typeName;
		typeName = newTypeName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.EVENT__TYPE_NAME, oldTypeName, typeName));
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
			case LibraryElementPackage.EVENT__INPUT_CONNECTIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getInputConnections()).basicAdd(otherEnd, msgs);
			case LibraryElementPackage.EVENT__OUTPUT_CONNECTIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getOutputConnections()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LibraryElementPackage.EVENT__INPUT_CONNECTIONS:
				return ((InternalEList<?>)getInputConnections()).basicRemove(otherEnd, msgs);
			case LibraryElementPackage.EVENT__OUTPUT_CONNECTIONS:
				return ((InternalEList<?>)getOutputConnections()).basicRemove(otherEnd, msgs);
			case LibraryElementPackage.EVENT__VALUE:
				return basicSetValue(null, msgs);
			case LibraryElementPackage.EVENT__WITH:
				return ((InternalEList<?>)getWith()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case LibraryElementPackage.EVENT__NAME:
				return getName();
			case LibraryElementPackage.EVENT__COMMENT:
				return getComment();
			case LibraryElementPackage.EVENT__IS_INPUT:
				return isIsInput();
			case LibraryElementPackage.EVENT__INPUT_CONNECTIONS:
				return getInputConnections();
			case LibraryElementPackage.EVENT__OUTPUT_CONNECTIONS:
				return getOutputConnections();
			case LibraryElementPackage.EVENT__TYPE:
				if (resolve) return getType();
				return basicGetType();
			case LibraryElementPackage.EVENT__VALUE:
				if (resolve) return getValue();
				return basicGetValue();
			case LibraryElementPackage.EVENT__TYPE_NAME:
				return getTypeName();
			case LibraryElementPackage.EVENT__WITH:
				return getWith();
		}
		return super.eGet(featureID, resolve, coreType);
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
			case LibraryElementPackage.EVENT__NAME:
				setName((String)newValue);
				return;
			case LibraryElementPackage.EVENT__COMMENT:
				setComment((String)newValue);
				return;
			case LibraryElementPackage.EVENT__IS_INPUT:
				setIsInput((Boolean)newValue);
				return;
			case LibraryElementPackage.EVENT__INPUT_CONNECTIONS:
				getInputConnections().clear();
				getInputConnections().addAll((Collection<? extends Connection>)newValue);
				return;
			case LibraryElementPackage.EVENT__OUTPUT_CONNECTIONS:
				getOutputConnections().clear();
				getOutputConnections().addAll((Collection<? extends Connection>)newValue);
				return;
			case LibraryElementPackage.EVENT__TYPE:
				setType((DataType)newValue);
				return;
			case LibraryElementPackage.EVENT__VALUE:
				setValue((Value)newValue);
				return;
			case LibraryElementPackage.EVENT__TYPE_NAME:
				setTypeName((String)newValue);
				return;
			case LibraryElementPackage.EVENT__WITH:
				getWith().clear();
				getWith().addAll((Collection<? extends With>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case LibraryElementPackage.EVENT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case LibraryElementPackage.EVENT__COMMENT:
				setComment(COMMENT_EDEFAULT);
				return;
			case LibraryElementPackage.EVENT__IS_INPUT:
				setIsInput(IS_INPUT_EDEFAULT);
				return;
			case LibraryElementPackage.EVENT__INPUT_CONNECTIONS:
				getInputConnections().clear();
				return;
			case LibraryElementPackage.EVENT__OUTPUT_CONNECTIONS:
				getOutputConnections().clear();
				return;
			case LibraryElementPackage.EVENT__TYPE:
				setType((DataType)null);
				return;
			case LibraryElementPackage.EVENT__VALUE:
				setValue((Value)null);
				return;
			case LibraryElementPackage.EVENT__TYPE_NAME:
				setTypeName(TYPE_NAME_EDEFAULT);
				return;
			case LibraryElementPackage.EVENT__WITH:
				getWith().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case LibraryElementPackage.EVENT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case LibraryElementPackage.EVENT__COMMENT:
				return COMMENT_EDEFAULT == null ? comment != null : !COMMENT_EDEFAULT.equals(comment);
			case LibraryElementPackage.EVENT__IS_INPUT:
				return isInput != IS_INPUT_EDEFAULT;
			case LibraryElementPackage.EVENT__INPUT_CONNECTIONS:
				return inputConnections != null && !inputConnections.isEmpty();
			case LibraryElementPackage.EVENT__OUTPUT_CONNECTIONS:
				return outputConnections != null && !outputConnections.isEmpty();
			case LibraryElementPackage.EVENT__TYPE:
				return type != null;
			case LibraryElementPackage.EVENT__VALUE:
				return value != null;
			case LibraryElementPackage.EVENT__TYPE_NAME:
				return TYPE_NAME_EDEFAULT == null ? typeName != null : !TYPE_NAME_EDEFAULT.equals(typeName);
			case LibraryElementPackage.EVENT__WITH:
				return with != null && !with.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", comment: ");
		result.append(comment);
		result.append(", isInput: ");
		result.append(isInput);
		result.append(", typeName: ");
		result.append(typeName);
		result.append(')');
		return result.toString();
	}

} //EventImpl
