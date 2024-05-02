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

import java.util.Map;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ConnectionRoutingData;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Connection</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionImpl#isBrokenConnection <em>Broken Connection</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionImpl#getSource <em>Source</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionImpl#getDestination <em>Destination</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionImpl#getRoutingData <em>Routing Data</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionImpl#getComment <em>Comment</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class ConnectionImpl extends ConfigurableObjectImpl implements Connection {
	/**
	 * The default value of the '{@link #isBrokenConnection() <em>Broken Connection</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isBrokenConnection()
	 * @generated
	 * @ordered
	 */
	protected static final boolean BROKEN_CONNECTION_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isBrokenConnection() <em>Broken Connection</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isBrokenConnection()
	 * @generated
	 * @ordered
	 */
	protected boolean brokenConnection = BROKEN_CONNECTION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSource() <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected IInterfaceElement source;

	/**
	 * The cached value of the '{@link #getDestination() <em>Destination</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDestination()
	 * @generated
	 * @ordered
	 */
	protected IInterfaceElement destination;

	/**
	 * The cached value of the '{@link #getRoutingData() <em>Routing Data</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRoutingData()
	 * @generated
	 * @ordered
	 */
	protected ConnectionRoutingData routingData;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConnectionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.CONNECTION;
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
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.CONNECTION__COMMENT, oldComment, comment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isBrokenConnection() {
		return brokenConnection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBrokenConnection(boolean newBrokenConnection) {
		boolean oldBrokenConnection = brokenConnection;
		brokenConnection = newBrokenConnection;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.CONNECTION__BROKEN_CONNECTION, oldBrokenConnection, brokenConnection));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IInterfaceElement getSource() {
		if (source != null && source.eIsProxy()) {
			InternalEObject oldSource = (InternalEObject)source;
			source = (IInterfaceElement)eResolveProxy(oldSource);
			if (source != oldSource) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LibraryElementPackage.CONNECTION__SOURCE, oldSource, source));
			}
		}
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IInterfaceElement basicGetSource() {
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSource(IInterfaceElement newSource, NotificationChain msgs) {
		IInterfaceElement oldSource = source;
		source = newSource;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, LibraryElementPackage.CONNECTION__SOURCE, oldSource, newSource);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	@Override
	public void setSource(final IInterfaceElement newSource) {
		setSourceGen(newSource);
		if (null != getSource() && null != getDestination()) {
			checkIfConnectionBroken();
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	public void setSourceGen(final IInterfaceElement newSource) {
		if (newSource != source) {
			NotificationChain msgs = null;
			if (source != null)
				msgs = ((InternalEObject)source).eInverseRemove(this, LibraryElementPackage.IINTERFACE_ELEMENT__OUTPUT_CONNECTIONS, IInterfaceElement.class, msgs);
			if (newSource != null)
				msgs = ((InternalEObject)newSource).eInverseAdd(this, LibraryElementPackage.IINTERFACE_ELEMENT__OUTPUT_CONNECTIONS, IInterfaceElement.class, msgs);
			msgs = basicSetSource(newSource, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.CONNECTION__SOURCE, newSource, newSource));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IInterfaceElement getDestination() {
		if (destination != null && destination.eIsProxy()) {
			InternalEObject oldDestination = (InternalEObject)destination;
			destination = (IInterfaceElement)eResolveProxy(oldDestination);
			if (destination != oldDestination) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LibraryElementPackage.CONNECTION__DESTINATION, oldDestination, destination));
			}
		}
		return destination;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IInterfaceElement basicGetDestination() {
		return destination;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDestination(IInterfaceElement newDestination, NotificationChain msgs) {
		IInterfaceElement oldDestination = destination;
		destination = newDestination;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, LibraryElementPackage.CONNECTION__DESTINATION, oldDestination, newDestination);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	@Override
	public void setDestination(final IInterfaceElement newDestination) {
		setDestinationGen(newDestination);
		if (null != getSource() && null != getDestination()) {
			checkIfConnectionBroken();
		}
	}

	/** 
	 * <!-- begin-user-doc --> 
	 * <!-- end-user-doc -->
	 * @generated */
	public void setDestinationGen(final IInterfaceElement newDestination) {
		if (newDestination != destination) {
			NotificationChain msgs = null;
			if (destination != null)
				msgs = ((InternalEObject)destination).eInverseRemove(this, LibraryElementPackage.IINTERFACE_ELEMENT__INPUT_CONNECTIONS, IInterfaceElement.class, msgs);
			if (newDestination != null)
				msgs = ((InternalEObject)newDestination).eInverseAdd(this, LibraryElementPackage.IINTERFACE_ELEMENT__INPUT_CONNECTIONS, IInterfaceElement.class, msgs);
			msgs = basicSetDestination(newDestination, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.CONNECTION__DESTINATION, newDestination, newDestination));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ConnectionRoutingData getRoutingData() {
		if (routingData != null && routingData.eIsProxy()) {
			InternalEObject oldRoutingData = (InternalEObject)routingData;
			routingData = (ConnectionRoutingData)eResolveProxy(oldRoutingData);
			if (routingData != oldRoutingData) {
				InternalEObject newRoutingData = (InternalEObject)routingData;
				NotificationChain msgs = oldRoutingData.eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.CONNECTION__ROUTING_DATA, null, null);
				if (newRoutingData.eInternalContainer() == null) {
					msgs = newRoutingData.eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.CONNECTION__ROUTING_DATA, null, msgs);
				}
				if (msgs != null) msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LibraryElementPackage.CONNECTION__ROUTING_DATA, oldRoutingData, routingData));
			}
		}
		return routingData;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConnectionRoutingData basicGetRoutingData() {
		return routingData;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRoutingData(ConnectionRoutingData newRoutingData, NotificationChain msgs) {
		ConnectionRoutingData oldRoutingData = routingData;
		routingData = newRoutingData;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, LibraryElementPackage.CONNECTION__ROUTING_DATA, oldRoutingData, newRoutingData);
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
	public void setRoutingData(ConnectionRoutingData newRoutingData) {
		if (newRoutingData != routingData) {
			NotificationChain msgs = null;
			if (routingData != null)
				msgs = ((InternalEObject)routingData).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.CONNECTION__ROUTING_DATA, null, msgs);
			if (newRoutingData != null)
				msgs = ((InternalEObject)newRoutingData).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.CONNECTION__ROUTING_DATA, null, msgs);
			msgs = basicSetRoutingData(newRoutingData, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.CONNECTION__ROUTING_DATA, newRoutingData, newRoutingData));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FBNetworkElement getSourceElement() {
		return org.eclipse.fordiac.ide.model.Annotations.getSourceElement(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FBNetworkElement getDestinationElement() {
		return org.eclipse.fordiac.ide.model.Annotations.getDestinationElement(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isResourceConnection() {
		return org.eclipse.fordiac.ide.model.Annotations.isResourceConnection(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FBNetwork getFBNetwork() {
		throw new UnsupportedOperationException("subclasses will need to provide it");
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void checkIfConnectionBroken() {
		org.eclipse.fordiac.ide.model.Annotations.checkifConnectionBroken(this);
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
	public boolean isInterfaceConnection() {
		return org.eclipse.fordiac.ide.model.Annotations.isInterfaceConnection(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean validateMissingSource(final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionAnnotations.validateMissingSource(this, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean validateMissingSourceEndpoint(final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionAnnotations.validateMissingSourceEndpoint(this, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean validateMissingDestination(final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionAnnotations.validateMissingDestination(this, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean validateMissingDestinationEndpoint(final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionAnnotations.validateMissingDestinationEndpoint(this, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean validateDuplicate(final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionAnnotations.validateDuplicate(this, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean validateTypeMismatch(final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionAnnotations.validateTypeMismatch(this, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean validateMappedVarInOutsDoNotCrossResourceBoundaries(final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionAnnotations.validateMappedVarInOutsDoNotCrossResourceBoundaries(this, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean validateVarInOutArraySizesAreCompatible(final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionAnnotations.validateVarInOutArraySizesAreCompatible(this, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean validateVarInOutStringLengthsMatch(final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionAnnotations.validateVarInOutStringLengthsMatch(this, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean validateVarInOutsAreNotConnectedToOuts(final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionAnnotations.validateVarInOutsAreNotConnectedToOuts(this, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean validateVarInOutConnectionsFormsNoLoop(final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionAnnotations.validateVarInOutConnectionsFormsNoLoop(this, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LibraryElementPackage.CONNECTION__SOURCE:
				if (source != null)
					msgs = ((InternalEObject)source).eInverseRemove(this, LibraryElementPackage.IINTERFACE_ELEMENT__OUTPUT_CONNECTIONS, IInterfaceElement.class, msgs);
				return basicSetSource((IInterfaceElement)otherEnd, msgs);
			case LibraryElementPackage.CONNECTION__DESTINATION:
				if (destination != null)
					msgs = ((InternalEObject)destination).eInverseRemove(this, LibraryElementPackage.IINTERFACE_ELEMENT__INPUT_CONNECTIONS, IInterfaceElement.class, msgs);
				return basicSetDestination((IInterfaceElement)otherEnd, msgs);
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
			case LibraryElementPackage.CONNECTION__SOURCE:
				return basicSetSource(null, msgs);
			case LibraryElementPackage.CONNECTION__DESTINATION:
				return basicSetDestination(null, msgs);
			case LibraryElementPackage.CONNECTION__ROUTING_DATA:
				return basicSetRoutingData(null, msgs);
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
			case LibraryElementPackage.CONNECTION__BROKEN_CONNECTION:
				return isBrokenConnection();
			case LibraryElementPackage.CONNECTION__SOURCE:
				if (resolve) return getSource();
				return basicGetSource();
			case LibraryElementPackage.CONNECTION__DESTINATION:
				if (resolve) return getDestination();
				return basicGetDestination();
			case LibraryElementPackage.CONNECTION__ROUTING_DATA:
				if (resolve) return getRoutingData();
				return basicGetRoutingData();
			case LibraryElementPackage.CONNECTION__COMMENT:
				return getComment();
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
			case LibraryElementPackage.CONNECTION__BROKEN_CONNECTION:
				setBrokenConnection((Boolean)newValue);
				return;
			case LibraryElementPackage.CONNECTION__SOURCE:
				setSource((IInterfaceElement)newValue);
				return;
			case LibraryElementPackage.CONNECTION__DESTINATION:
				setDestination((IInterfaceElement)newValue);
				return;
			case LibraryElementPackage.CONNECTION__ROUTING_DATA:
				setRoutingData((ConnectionRoutingData)newValue);
				return;
			case LibraryElementPackage.CONNECTION__COMMENT:
				setComment((String)newValue);
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
			case LibraryElementPackage.CONNECTION__BROKEN_CONNECTION:
				setBrokenConnection(BROKEN_CONNECTION_EDEFAULT);
				return;
			case LibraryElementPackage.CONNECTION__SOURCE:
				setSource((IInterfaceElement)null);
				return;
			case LibraryElementPackage.CONNECTION__DESTINATION:
				setDestination((IInterfaceElement)null);
				return;
			case LibraryElementPackage.CONNECTION__ROUTING_DATA:
				setRoutingData((ConnectionRoutingData)null);
				return;
			case LibraryElementPackage.CONNECTION__COMMENT:
				setComment(COMMENT_EDEFAULT);
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
			case LibraryElementPackage.CONNECTION__BROKEN_CONNECTION:
				return brokenConnection != BROKEN_CONNECTION_EDEFAULT;
			case LibraryElementPackage.CONNECTION__SOURCE:
				return source != null;
			case LibraryElementPackage.CONNECTION__DESTINATION:
				return destination != null;
			case LibraryElementPackage.CONNECTION__ROUTING_DATA:
				return routingData != null;
			case LibraryElementPackage.CONNECTION__COMMENT:
				return COMMENT_EDEFAULT == null ? comment != null : !COMMENT_EDEFAULT.equals(comment);
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
		result.append(" (brokenConnection: "); //$NON-NLS-1$
		result.append(brokenConnection);
		result.append(", comment: "); //$NON-NLS-1$
		result.append(comment);
		result.append(')');
		return result.toString();
	}

} //ConnectionImpl
