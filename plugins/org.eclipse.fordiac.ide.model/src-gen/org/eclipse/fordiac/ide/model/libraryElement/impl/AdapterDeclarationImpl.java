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
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.fordiac.ide.model.data.DataType;

import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.HiddenElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Adapter Declaration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterDeclarationImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterDeclarationImpl#getComment <em>Comment</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterDeclarationImpl#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterDeclarationImpl#isIsInput <em>Is Input</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterDeclarationImpl#getInputConnections <em>Input Connections</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterDeclarationImpl#getOutputConnections <em>Output Connections</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterDeclarationImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterDeclarationImpl#getAdapterFB <em>Adapter FB</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterDeclarationImpl#getAdapterNetworkFB <em>Adapter Network FB</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AdapterDeclarationImpl extends EObjectImpl implements AdapterDeclaration {
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
	 * The cached value of the '{@link #getAdapterFB() <em>Adapter FB</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAdapterFB()
	 * @generated
	 * @ordered
	 */
	protected AdapterFB adapterFB;

	/**
	 * The cached value of the '{@link #getAdapterNetworkFB() <em>Adapter Network FB</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAdapterNetworkFB()
	 * @generated
	 * @ordered
	 */
	protected AdapterFB adapterNetworkFB;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AdapterDeclarationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.ADAPTER_DECLARATION;
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
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.ADAPTER_DECLARATION__NAME, oldName, name));
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
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.ADAPTER_DECLARATION__COMMENT, oldComment, comment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Attribute> getAttributes() {
		if (attributes == null) {
			attributes = new EObjectContainmentEList.Resolving<Attribute>(Attribute.class, this, LibraryElementPackage.ADAPTER_DECLARATION__ATTRIBUTES);
		}
		return attributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isIsInput() {
		return isInput;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIsInput(boolean newIsInput) {
		boolean oldIsInput = isInput;
		isInput = newIsInput;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.ADAPTER_DECLARATION__IS_INPUT, oldIsInput, isInput));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Connection> getInputConnections() {
		if (inputConnections == null) {
			inputConnections = new EObjectWithInverseResolvingEList<Connection>(Connection.class, this, LibraryElementPackage.ADAPTER_DECLARATION__INPUT_CONNECTIONS, LibraryElementPackage.CONNECTION__DESTINATION);
		}
		return inputConnections;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Connection> getOutputConnections() {
		if (outputConnections == null) {
			outputConnections = new EObjectWithInverseResolvingEList<Connection>(Connection.class, this, LibraryElementPackage.ADAPTER_DECLARATION__OUTPUT_CONNECTIONS, LibraryElementPackage.CONNECTION__SOURCE);
		}
		return outputConnections;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataType getType_() {
		if (type != null && type.eIsProxy()) {
			InternalEObject oldType = (InternalEObject)type;
			type = (DataType)eResolveProxy(oldType);
			if (type != oldType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LibraryElementPackage.ADAPTER_DECLARATION__TYPE, oldType, type));
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
	@Override
	public void setType(DataType newType) {
		DataType oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.ADAPTER_DECLARATION__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AdapterFB getAdapterFB() {
		if (adapterFB != null && adapterFB.eIsProxy()) {
			InternalEObject oldAdapterFB = (InternalEObject)adapterFB;
			adapterFB = (AdapterFB)eResolveProxy(oldAdapterFB);
			if (adapterFB != oldAdapterFB) {
				InternalEObject newAdapterFB = (InternalEObject)adapterFB;
				NotificationChain msgs = oldAdapterFB.eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.ADAPTER_DECLARATION__ADAPTER_FB, null, null);
				if (newAdapterFB.eInternalContainer() == null) {
					msgs = newAdapterFB.eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.ADAPTER_DECLARATION__ADAPTER_FB, null, msgs);
				}
				if (msgs != null) msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LibraryElementPackage.ADAPTER_DECLARATION__ADAPTER_FB, oldAdapterFB, adapterFB));
			}
		}
		return adapterFB;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AdapterFB basicGetAdapterFB() {
		return adapterFB;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAdapterFB(AdapterFB newAdapterFB, NotificationChain msgs) {
		AdapterFB oldAdapterFB = adapterFB;
		adapterFB = newAdapterFB;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, LibraryElementPackage.ADAPTER_DECLARATION__ADAPTER_FB, oldAdapterFB, newAdapterFB);
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
	public void setAdapterFB(AdapterFB newAdapterFB) {
		if (newAdapterFB != adapterFB) {
			NotificationChain msgs = null;
			if (adapterFB != null)
				msgs = ((InternalEObject)adapterFB).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.ADAPTER_DECLARATION__ADAPTER_FB, null, msgs);
			if (newAdapterFB != null)
				msgs = ((InternalEObject)newAdapterFB).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.ADAPTER_DECLARATION__ADAPTER_FB, null, msgs);
			msgs = basicSetAdapterFB(newAdapterFB, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.ADAPTER_DECLARATION__ADAPTER_FB, newAdapterFB, newAdapterFB));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AdapterFB getAdapterNetworkFB() {
		if (adapterNetworkFB != null && adapterNetworkFB.eIsProxy()) {
			InternalEObject oldAdapterNetworkFB = (InternalEObject)adapterNetworkFB;
			adapterNetworkFB = (AdapterFB)eResolveProxy(oldAdapterNetworkFB);
			if (adapterNetworkFB != oldAdapterNetworkFB) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LibraryElementPackage.ADAPTER_DECLARATION__ADAPTER_NETWORK_FB, oldAdapterNetworkFB, adapterNetworkFB));
			}
		}
		return adapterNetworkFB;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AdapterFB basicGetAdapterNetworkFB() {
		return adapterNetworkFB;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAdapterNetworkFB(AdapterFB newAdapterNetworkFB) {
		AdapterFB oldAdapterNetworkFB = adapterNetworkFB;
		adapterNetworkFB = newAdapterNetworkFB;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.ADAPTER_DECLARATION__ADAPTER_NETWORK_FB, oldAdapterNetworkFB, adapterNetworkFB));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AdapterType getType() {
		return (AdapterType)getType_();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FBNetworkElement getFBNetworkElement() {
		return org.eclipse.fordiac.ide.model.Annotations.getFBNetworkElement(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getTypeName() {
		org.eclipse.fordiac.ide.model.libraryElement.INamedElement type = getType();
		if(type != null){
			return type.getName();
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getFullTypeName() {
		return getTypeName();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean validateName(final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.InterfaceElementAnnotations.validateName(this, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean validateType(final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.TypedElementAnnotations.validateType(this, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setVisible(final boolean visible) {
		org.eclipse.fordiac.ide.model.annotations.HiddenElementAnnotations.setVisible(this,visible);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isVisible() {
		return org.eclipse.fordiac.ide.model.annotations.HiddenElementAnnotations.isVisible(this);
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
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LibraryElementPackage.ADAPTER_DECLARATION__INPUT_CONNECTIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getInputConnections()).basicAdd(otherEnd, msgs);
			case LibraryElementPackage.ADAPTER_DECLARATION__OUTPUT_CONNECTIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getOutputConnections()).basicAdd(otherEnd, msgs);
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
			case LibraryElementPackage.ADAPTER_DECLARATION__ATTRIBUTES:
				return ((InternalEList<?>)getAttributes()).basicRemove(otherEnd, msgs);
			case LibraryElementPackage.ADAPTER_DECLARATION__INPUT_CONNECTIONS:
				return ((InternalEList<?>)getInputConnections()).basicRemove(otherEnd, msgs);
			case LibraryElementPackage.ADAPTER_DECLARATION__OUTPUT_CONNECTIONS:
				return ((InternalEList<?>)getOutputConnections()).basicRemove(otherEnd, msgs);
			case LibraryElementPackage.ADAPTER_DECLARATION__ADAPTER_FB:
				return basicSetAdapterFB(null, msgs);
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
			case LibraryElementPackage.ADAPTER_DECLARATION__NAME:
				return getName();
			case LibraryElementPackage.ADAPTER_DECLARATION__COMMENT:
				return getComment();
			case LibraryElementPackage.ADAPTER_DECLARATION__ATTRIBUTES:
				return getAttributes();
			case LibraryElementPackage.ADAPTER_DECLARATION__IS_INPUT:
				return isIsInput();
			case LibraryElementPackage.ADAPTER_DECLARATION__INPUT_CONNECTIONS:
				return getInputConnections();
			case LibraryElementPackage.ADAPTER_DECLARATION__OUTPUT_CONNECTIONS:
				return getOutputConnections();
			case LibraryElementPackage.ADAPTER_DECLARATION__TYPE:
				if (resolve) return getType();
				return basicGetType();
			case LibraryElementPackage.ADAPTER_DECLARATION__ADAPTER_FB:
				if (resolve) return getAdapterFB();
				return basicGetAdapterFB();
			case LibraryElementPackage.ADAPTER_DECLARATION__ADAPTER_NETWORK_FB:
				if (resolve) return getAdapterNetworkFB();
				return basicGetAdapterNetworkFB();
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
			case LibraryElementPackage.ADAPTER_DECLARATION__NAME:
				setName((String)newValue);
				return;
			case LibraryElementPackage.ADAPTER_DECLARATION__COMMENT:
				setComment((String)newValue);
				return;
			case LibraryElementPackage.ADAPTER_DECLARATION__ATTRIBUTES:
				getAttributes().clear();
				getAttributes().addAll((Collection<? extends Attribute>)newValue);
				return;
			case LibraryElementPackage.ADAPTER_DECLARATION__IS_INPUT:
				setIsInput((Boolean)newValue);
				return;
			case LibraryElementPackage.ADAPTER_DECLARATION__INPUT_CONNECTIONS:
				getInputConnections().clear();
				getInputConnections().addAll((Collection<? extends Connection>)newValue);
				return;
			case LibraryElementPackage.ADAPTER_DECLARATION__OUTPUT_CONNECTIONS:
				getOutputConnections().clear();
				getOutputConnections().addAll((Collection<? extends Connection>)newValue);
				return;
			case LibraryElementPackage.ADAPTER_DECLARATION__TYPE:
				setType((DataType)newValue);
				return;
			case LibraryElementPackage.ADAPTER_DECLARATION__ADAPTER_FB:
				setAdapterFB((AdapterFB)newValue);
				return;
			case LibraryElementPackage.ADAPTER_DECLARATION__ADAPTER_NETWORK_FB:
				setAdapterNetworkFB((AdapterFB)newValue);
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
			case LibraryElementPackage.ADAPTER_DECLARATION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case LibraryElementPackage.ADAPTER_DECLARATION__COMMENT:
				setComment(COMMENT_EDEFAULT);
				return;
			case LibraryElementPackage.ADAPTER_DECLARATION__ATTRIBUTES:
				getAttributes().clear();
				return;
			case LibraryElementPackage.ADAPTER_DECLARATION__IS_INPUT:
				setIsInput(IS_INPUT_EDEFAULT);
				return;
			case LibraryElementPackage.ADAPTER_DECLARATION__INPUT_CONNECTIONS:
				getInputConnections().clear();
				return;
			case LibraryElementPackage.ADAPTER_DECLARATION__OUTPUT_CONNECTIONS:
				getOutputConnections().clear();
				return;
			case LibraryElementPackage.ADAPTER_DECLARATION__TYPE:
				setType((DataType)null);
				return;
			case LibraryElementPackage.ADAPTER_DECLARATION__ADAPTER_FB:
				setAdapterFB((AdapterFB)null);
				return;
			case LibraryElementPackage.ADAPTER_DECLARATION__ADAPTER_NETWORK_FB:
				setAdapterNetworkFB((AdapterFB)null);
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
			case LibraryElementPackage.ADAPTER_DECLARATION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case LibraryElementPackage.ADAPTER_DECLARATION__COMMENT:
				return COMMENT_EDEFAULT == null ? comment != null : !COMMENT_EDEFAULT.equals(comment);
			case LibraryElementPackage.ADAPTER_DECLARATION__ATTRIBUTES:
				return attributes != null && !attributes.isEmpty();
			case LibraryElementPackage.ADAPTER_DECLARATION__IS_INPUT:
				return isInput != IS_INPUT_EDEFAULT;
			case LibraryElementPackage.ADAPTER_DECLARATION__INPUT_CONNECTIONS:
				return inputConnections != null && !inputConnections.isEmpty();
			case LibraryElementPackage.ADAPTER_DECLARATION__OUTPUT_CONNECTIONS:
				return outputConnections != null && !outputConnections.isEmpty();
			case LibraryElementPackage.ADAPTER_DECLARATION__TYPE:
				return type != null;
			case LibraryElementPackage.ADAPTER_DECLARATION__ADAPTER_FB:
				return adapterFB != null;
			case LibraryElementPackage.ADAPTER_DECLARATION__ADAPTER_NETWORK_FB:
				return adapterNetworkFB != null;
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
				case LibraryElementPackage.ADAPTER_DECLARATION__ATTRIBUTES: return LibraryElementPackage.CONFIGURABLE_OBJECT__ATTRIBUTES;
				default: return -1;
			}
		}
		if (baseClass == HiddenElement.class) {
			switch (derivedFeatureID) {
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
				case LibraryElementPackage.CONFIGURABLE_OBJECT__ATTRIBUTES: return LibraryElementPackage.ADAPTER_DECLARATION__ATTRIBUTES;
				default: return -1;
			}
		}
		if (baseClass == HiddenElement.class) {
			switch (baseFeatureID) {
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
		result.append(", isInput: "); //$NON-NLS-1$
		result.append(isInput);
		result.append(')');
		return result.toString();
	}

} //AdapterDeclarationImpl
