/**
 * *******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH
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

import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.fordiac.ide.model.data.DataType;

import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;

/** <!-- begin-user-doc --> An implementation of the model object '<em><b>Var Declaration</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.VarDeclarationImpl#isIsInput <em>Is Input</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.VarDeclarationImpl#getInputConnections <em>Input
 * Connections</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.VarDeclarationImpl#getOutputConnections <em>Output
 * Connections</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.VarDeclarationImpl#getType <em>Type</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.VarDeclarationImpl#getTypeName <em>Type Name</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.VarDeclarationImpl#getArraySize <em>Array
 * Size</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.VarDeclarationImpl#getWiths <em>Withs</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.VarDeclarationImpl#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @generated */
public class VarDeclarationImpl extends ConfigurableObjectImpl implements VarDeclaration {
	/** The default value of the '{@link #isIsInput() <em>Is Input</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #isIsInput()
	 * @generated
	 * @ordered */
	protected static final boolean IS_INPUT_EDEFAULT = false;

	/** The cached value of the '{@link #isIsInput() <em>Is Input</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #isIsInput()
	 * @generated
	 * @ordered */
	protected boolean isInput = IS_INPUT_EDEFAULT;

	/** The cached value of the '{@link #getInputConnections() <em>Input Connections</em>}' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getInputConnections()
	 * @generated
	 * @ordered */
	protected EList<Connection> inputConnections;

	/** The cached value of the '{@link #getOutputConnections() <em>Output Connections</em>}' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOutputConnections()
	 * @generated
	 * @ordered */
	protected EList<Connection> outputConnections;

	/** The cached value of the '{@link #getType() <em>Type</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see #getType()
	 * @generated
	 * @ordered */
	protected DataType type;

	/** The default value of the '{@link #getTypeName() <em>Type Name</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getTypeName()
	 * @generated
	 * @ordered */
	protected static final String TYPE_NAME_EDEFAULT = null;

	/** The cached value of the '{@link #getTypeName() <em>Type Name</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getTypeName()
	 * @generated
	 * @ordered */
	protected String typeName = TYPE_NAME_EDEFAULT;

	/** The default value of the '{@link #getArraySize() <em>Array Size</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getArraySize()
	 * @generated
	 * @ordered */
	protected static final int ARRAY_SIZE_EDEFAULT = 0;

	/** The cached value of the '{@link #getArraySize() <em>Array Size</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getArraySize()
	 * @generated
	 * @ordered */
	protected int arraySize = ARRAY_SIZE_EDEFAULT;

	/** The cached value of the '{@link #getWiths() <em>Withs</em>}' reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getWiths()
	 * @generated
	 * @ordered */
	protected EList<With> withs;

	/** The cached value of the '{@link #getValue() <em>Value</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getValue()
	 * @generated
	 * @ordered */
	protected Value value;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	protected VarDeclarationImpl() {
		super();
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.VAR_DECLARATION;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public boolean isIsInput() {
		return isInput;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public void setIsInput(boolean newIsInput) {
		boolean oldIsInput = isInput;
		isInput = newIsInput;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.VAR_DECLARATION__IS_INPUT,
					oldIsInput, isInput));
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EList<Connection> getInputConnections() {
		if (inputConnections == null) {
			inputConnections = new EObjectWithInverseResolvingEList<Connection>(Connection.class, this,
					LibraryElementPackage.VAR_DECLARATION__INPUT_CONNECTIONS,
					LibraryElementPackage.CONNECTION__DESTINATION);
		}
		return inputConnections;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EList<Connection> getOutputConnections() {
		if (outputConnections == null) {
			outputConnections = new EObjectWithInverseResolvingEList<Connection>(Connection.class, this,
					LibraryElementPackage.VAR_DECLARATION__OUTPUT_CONNECTIONS,
					LibraryElementPackage.CONNECTION__SOURCE);
		}
		return outputConnections;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public DataType getType() {
		if (type != null && type.eIsProxy()) {
			InternalEObject oldType = (InternalEObject) type;
			type = (DataType) eResolveProxy(oldType);
			if (type != oldType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							LibraryElementPackage.VAR_DECLARATION__TYPE, oldType, type));
			}
		}
		return type;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	public DataType basicGetType() {
		return type;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public void setType(DataType newType) {
		DataType oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.VAR_DECLARATION__TYPE, oldType,
					type));
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public String getTypeName() {
		return typeName;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public void setTypeName(String newTypeName) {
		String oldTypeName = typeName;
		typeName = newTypeName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.VAR_DECLARATION__TYPE_NAME,
					oldTypeName, typeName));
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public int getArraySize() {
		return arraySize;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public void setArraySize(int newArraySize) {
		int oldArraySize = arraySize;
		arraySize = newArraySize;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.VAR_DECLARATION__ARRAY_SIZE,
					oldArraySize, arraySize));
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EList<With> getWiths() {
		if (withs == null) {
			withs = new EObjectWithInverseResolvingEList<With>(With.class, this,
					LibraryElementPackage.VAR_DECLARATION__WITHS, LibraryElementPackage.WITH__VARIABLES);
		}
		return withs;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public Value getValue() {
		if (value != null && value.eIsProxy()) {
			InternalEObject oldValue = (InternalEObject) value;
			value = (Value) eResolveProxy(oldValue);
			if (value != oldValue) {
				InternalEObject newValue = (InternalEObject) value;
				NotificationChain msgs = oldValue.eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - LibraryElementPackage.VAR_DECLARATION__VALUE, null, null);
				if (newValue.eInternalContainer() == null) {
					msgs = newValue.eInverseAdd(this,
							EOPPOSITE_FEATURE_BASE - LibraryElementPackage.VAR_DECLARATION__VALUE, null, msgs);
				}
				if (msgs != null)
					msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							LibraryElementPackage.VAR_DECLARATION__VALUE, oldValue, value));
			}
		}
		return value;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	public Value basicGetValue() {
		return value;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	public NotificationChain basicSetValue(Value newValue, NotificationChain msgs) {
		Value oldValue = value;
		value = newValue;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					LibraryElementPackage.VAR_DECLARATION__VALUE, oldValue, newValue);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public void setValue(Value newValue) {
		if (newValue != value) {
			NotificationChain msgs = null;
			if (value != null)
				msgs = ((InternalEObject) value).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - LibraryElementPackage.VAR_DECLARATION__VALUE, null, msgs);
			if (newValue != null)
				msgs = ((InternalEObject) newValue).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - LibraryElementPackage.VAR_DECLARATION__VALUE, null, msgs);
			msgs = basicSetValue(newValue, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.VAR_DECLARATION__VALUE,
					newValue, newValue));
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public boolean isArray() {
		return org.eclipse.fordiac.ide.model.Annotations.isArray(this);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public FBNetworkElement getFBNetworkElement() {
		return org.eclipse.fordiac.ide.model.Annotations.getFBNetworkElement(this);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case LibraryElementPackage.VAR_DECLARATION__INPUT_CONNECTIONS:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getInputConnections()).basicAdd(otherEnd, msgs);
		case LibraryElementPackage.VAR_DECLARATION__OUTPUT_CONNECTIONS:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getOutputConnections()).basicAdd(otherEnd,
					msgs);
		case LibraryElementPackage.VAR_DECLARATION__WITHS:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getWiths()).basicAdd(otherEnd, msgs);
		default:
			return super.eInverseAdd(otherEnd, featureID, msgs);
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case LibraryElementPackage.VAR_DECLARATION__INPUT_CONNECTIONS:
			return ((InternalEList<?>) getInputConnections()).basicRemove(otherEnd, msgs);
		case LibraryElementPackage.VAR_DECLARATION__OUTPUT_CONNECTIONS:
			return ((InternalEList<?>) getOutputConnections()).basicRemove(otherEnd, msgs);
		case LibraryElementPackage.VAR_DECLARATION__WITHS:
			return ((InternalEList<?>) getWiths()).basicRemove(otherEnd, msgs);
		case LibraryElementPackage.VAR_DECLARATION__VALUE:
			return basicSetValue(null, msgs);
		default:
			return super.eInverseRemove(otherEnd, featureID, msgs);
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case LibraryElementPackage.VAR_DECLARATION__IS_INPUT:
			return isIsInput();
		case LibraryElementPackage.VAR_DECLARATION__INPUT_CONNECTIONS:
			return getInputConnections();
		case LibraryElementPackage.VAR_DECLARATION__OUTPUT_CONNECTIONS:
			return getOutputConnections();
		case LibraryElementPackage.VAR_DECLARATION__TYPE:
			if (resolve)
				return getType();
			return basicGetType();
		case LibraryElementPackage.VAR_DECLARATION__TYPE_NAME:
			return getTypeName();
		case LibraryElementPackage.VAR_DECLARATION__ARRAY_SIZE:
			return getArraySize();
		case LibraryElementPackage.VAR_DECLARATION__WITHS:
			return getWiths();
		case LibraryElementPackage.VAR_DECLARATION__VALUE:
			if (resolve)
				return getValue();
			return basicGetValue();
		default:
			return super.eGet(featureID, resolve, coreType);
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case LibraryElementPackage.VAR_DECLARATION__IS_INPUT:
			setIsInput((Boolean) newValue);
			return;
		case LibraryElementPackage.VAR_DECLARATION__INPUT_CONNECTIONS:
			getInputConnections().clear();
			getInputConnections().addAll((Collection<? extends Connection>) newValue);
			return;
		case LibraryElementPackage.VAR_DECLARATION__OUTPUT_CONNECTIONS:
			getOutputConnections().clear();
			getOutputConnections().addAll((Collection<? extends Connection>) newValue);
			return;
		case LibraryElementPackage.VAR_DECLARATION__TYPE:
			setType((DataType) newValue);
			return;
		case LibraryElementPackage.VAR_DECLARATION__TYPE_NAME:
			setTypeName((String) newValue);
			return;
		case LibraryElementPackage.VAR_DECLARATION__ARRAY_SIZE:
			setArraySize((Integer) newValue);
			return;
		case LibraryElementPackage.VAR_DECLARATION__WITHS:
			getWiths().clear();
			getWiths().addAll((Collection<? extends With>) newValue);
			return;
		case LibraryElementPackage.VAR_DECLARATION__VALUE:
			setValue((Value) newValue);
			return;
		default:
			super.eSet(featureID, newValue);
			return;
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case LibraryElementPackage.VAR_DECLARATION__IS_INPUT:
			setIsInput(IS_INPUT_EDEFAULT);
			return;
		case LibraryElementPackage.VAR_DECLARATION__INPUT_CONNECTIONS:
			getInputConnections().clear();
			return;
		case LibraryElementPackage.VAR_DECLARATION__OUTPUT_CONNECTIONS:
			getOutputConnections().clear();
			return;
		case LibraryElementPackage.VAR_DECLARATION__TYPE:
			setType((DataType) null);
			return;
		case LibraryElementPackage.VAR_DECLARATION__TYPE_NAME:
			setTypeName(TYPE_NAME_EDEFAULT);
			return;
		case LibraryElementPackage.VAR_DECLARATION__ARRAY_SIZE:
			setArraySize(ARRAY_SIZE_EDEFAULT);
			return;
		case LibraryElementPackage.VAR_DECLARATION__WITHS:
			getWiths().clear();
			return;
		case LibraryElementPackage.VAR_DECLARATION__VALUE:
			setValue((Value) null);
			return;
		default:
			super.eUnset(featureID);
			return;
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case LibraryElementPackage.VAR_DECLARATION__IS_INPUT:
			return isInput != IS_INPUT_EDEFAULT;
		case LibraryElementPackage.VAR_DECLARATION__INPUT_CONNECTIONS:
			return inputConnections != null && !inputConnections.isEmpty();
		case LibraryElementPackage.VAR_DECLARATION__OUTPUT_CONNECTIONS:
			return outputConnections != null && !outputConnections.isEmpty();
		case LibraryElementPackage.VAR_DECLARATION__TYPE:
			return type != null;
		case LibraryElementPackage.VAR_DECLARATION__TYPE_NAME:
			return TYPE_NAME_EDEFAULT == null ? typeName != null : !TYPE_NAME_EDEFAULT.equals(typeName);
		case LibraryElementPackage.VAR_DECLARATION__ARRAY_SIZE:
			return arraySize != ARRAY_SIZE_EDEFAULT;
		case LibraryElementPackage.VAR_DECLARATION__WITHS:
			return withs != null && !withs.isEmpty();
		case LibraryElementPackage.VAR_DECLARATION__VALUE:
			return value != null;
		default:
			return super.eIsSet(featureID);
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (isInput: "); //$NON-NLS-1$
		result.append(isInput);
		result.append(", typeName: "); //$NON-NLS-1$
		result.append(typeName);
		result.append(", arraySize: "); //$NON-NLS-1$
		result.append(arraySize);
		result.append(')');
		return result.toString();
	}

} // VarDeclarationImpl
