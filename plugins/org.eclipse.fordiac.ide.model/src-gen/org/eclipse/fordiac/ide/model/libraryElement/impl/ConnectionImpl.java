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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ConnectionRoutingData;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerRef;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;

/** <!-- begin-user-doc --> An implementation of the model object '<em><b>Connection</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionImpl#getFileMarkerId <em>File Marker
 * Id</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionImpl#getErrorMessage <em>Error
 * Message</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionImpl#isResTypeConnection <em>Res Type
 * Connection</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionImpl#isBrokenConnection <em>Broken
 * Connection</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionImpl#getSource <em>Source</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionImpl#getDestination <em>Destination</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionImpl#getRoutingData <em>Routing
 * Data</em>}</li>
 * </ul>
 *
 * @generated */
public abstract class ConnectionImpl extends ConfigurableObjectImpl implements Connection {
	/**
	 * The default value of the '{@link #getFileMarkerId() <em>File Marker Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFileMarkerId()
	 * @generated
	 * @ordered
	 */
	protected static final long FILE_MARKER_ID_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getFileMarkerId() <em>File Marker Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFileMarkerId()
	 * @generated
	 * @ordered
	 */
	protected long fileMarkerId = FILE_MARKER_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getErrorMessage() <em>Error Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getErrorMessage()
	 * @generated
	 * @ordered
	 */
	protected static final String ERROR_MESSAGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getErrorMessage() <em>Error Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getErrorMessage()
	 * @generated
	 * @ordered
	 */
	protected String errorMessage = ERROR_MESSAGE_EDEFAULT;

	/** The default value of the '{@link #isResTypeConnection() <em>Res Type Connection</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #isResTypeConnection()
	 * @generated
	 * @ordered */
	protected static final boolean RES_TYPE_CONNECTION_EDEFAULT = false;

	/** The cached value of the '{@link #isResTypeConnection() <em>Res Type Connection</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #isResTypeConnection()
	 * @generated
	 * @ordered */
	protected boolean resTypeConnection = RES_TYPE_CONNECTION_EDEFAULT;

	/** The default value of the '{@link #isBrokenConnection() <em>Broken Connection</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #isBrokenConnection()
	 * @generated
	 * @ordered */
	protected static final boolean BROKEN_CONNECTION_EDEFAULT = false;

	/** The cached value of the '{@link #isBrokenConnection() <em>Broken Connection</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #isBrokenConnection()
	 * @generated
	 * @ordered */
	protected boolean brokenConnection = BROKEN_CONNECTION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSource() <em>Source</em>}' reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected IInterfaceElement source;

	/**
	 * The cached value of the '{@link #getDestination() <em>Destination</em>}' reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getDestination()
	 * @generated
	 * @ordered
	 */
	protected IInterfaceElement destination;

	/**
	 * The cached value of the '{@link #getRoutingData() <em>Routing Data</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRoutingData()
	 * @generated
	 * @ordered
	 */
	protected ConnectionRoutingData routingData;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	protected ConnectionImpl() {
		super();
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.CONNECTION;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public long getFileMarkerId() {
		return fileMarkerId;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public void setFileMarkerId(final long newFileMarkerId) {
		final long oldFileMarkerId = fileMarkerId;
		fileMarkerId = newFileMarkerId;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.CONNECTION__FILE_MARKER_ID,
					oldFileMarkerId, fileMarkerId));
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public String getErrorMessage() {
		return errorMessage;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public void setErrorMessage(final String newErrorMessage) {
		final String oldErrorMessage = errorMessage;
		errorMessage = newErrorMessage;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.CONNECTION__ERROR_MESSAGE,
					oldErrorMessage, errorMessage));
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public boolean isResTypeConnection() {
		return resTypeConnection;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public void setResTypeConnection(final boolean newResTypeConnection) {
		final boolean oldResTypeConnection = resTypeConnection;
		resTypeConnection = newResTypeConnection;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.CONNECTION__RES_TYPE_CONNECTION,
					oldResTypeConnection, resTypeConnection));
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public boolean isBrokenConnection() {
		return brokenConnection;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public void setBrokenConnection(final boolean newBrokenConnection) {
		final boolean oldBrokenConnection = brokenConnection;
		brokenConnection = newBrokenConnection;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.CONNECTION__BROKEN_CONNECTION,
					oldBrokenConnection, brokenConnection));
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public IInterfaceElement getSource() {
		if (source != null && source.eIsProxy()) {
			final InternalEObject oldSource = (InternalEObject) source;
			source = (IInterfaceElement) eResolveProxy(oldSource);
			if (source != oldSource) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LibraryElementPackage.CONNECTION__SOURCE,
							oldSource, source));
				}
			}
		}
		return source;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	public IInterfaceElement basicGetSource() {
		return source;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	public NotificationChain basicSetSource(final IInterfaceElement newSource, NotificationChain msgs) {
		final IInterfaceElement oldSource = source;
		source = newSource;
		if (eNotificationRequired()) {
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					LibraryElementPackage.CONNECTION__SOURCE, oldSource, newSource);
			if (msgs == null) {
				msgs = notification;
			} else {
				msgs.add(notification);
			}
		}
		return msgs;
	}

	/** @generated not */
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
			if (source != null) {
				msgs = ((InternalEObject) source).eInverseRemove(this,
						LibraryElementPackage.IINTERFACE_ELEMENT__OUTPUT_CONNECTIONS, IInterfaceElement.class, msgs);
			}
			if (newSource != null) {
				msgs = ((InternalEObject) newSource).eInverseAdd(this,
						LibraryElementPackage.IINTERFACE_ELEMENT__OUTPUT_CONNECTIONS, IInterfaceElement.class, msgs);
			}
			msgs = basicSetSource(newSource, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.CONNECTION__SOURCE, newSource,
					newSource));
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public IInterfaceElement getDestination() {
		if (destination != null && destination.eIsProxy()) {
			final InternalEObject oldDestination = (InternalEObject) destination;
			destination = (IInterfaceElement) eResolveProxy(oldDestination);
			if (destination != oldDestination) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							LibraryElementPackage.CONNECTION__DESTINATION, oldDestination, destination));
				}
			}
		}
		return destination;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	public IInterfaceElement basicGetDestination() {
		return destination;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	public NotificationChain basicSetDestination(final IInterfaceElement newDestination, NotificationChain msgs) {
		final IInterfaceElement oldDestination = destination;
		destination = newDestination;
		if (eNotificationRequired()) {
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					LibraryElementPackage.CONNECTION__DESTINATION, oldDestination, newDestination);
			if (msgs == null) {
				msgs = notification;
			} else {
				msgs.add(notification);
			}
		}
		return msgs;
	}

	/** @generated not */
	@Override
	public void setDestination(final IInterfaceElement newDestination) {
		setDestinationGen(newDestination);
		if (null != getSource() && null != getDestination()) {
			checkIfConnectionBroken();
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	public void setDestinationGen(final IInterfaceElement newDestination) {
		if (newDestination != destination) {
			NotificationChain msgs = null;
			if (destination != null) {
				msgs = ((InternalEObject) destination).eInverseRemove(this,
						LibraryElementPackage.IINTERFACE_ELEMENT__INPUT_CONNECTIONS, IInterfaceElement.class, msgs);
			}
			if (newDestination != null) {
				msgs = ((InternalEObject) newDestination).eInverseAdd(this,
						LibraryElementPackage.IINTERFACE_ELEMENT__INPUT_CONNECTIONS, IInterfaceElement.class, msgs);
			}
			msgs = basicSetDestination(newDestination, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.CONNECTION__DESTINATION,
					newDestination, newDestination));
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public ConnectionRoutingData getRoutingData() {
		if (routingData != null && routingData.eIsProxy()) {
			final InternalEObject oldRoutingData = (InternalEObject) routingData;
			routingData = (ConnectionRoutingData) eResolveProxy(oldRoutingData);
			if (routingData != oldRoutingData) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							LibraryElementPackage.CONNECTION__ROUTING_DATA, oldRoutingData, routingData));
				}
			}
		}
		return routingData;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	public ConnectionRoutingData basicGetRoutingData() {
		return routingData;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public void setRoutingData(final ConnectionRoutingData newRoutingData) {
		final ConnectionRoutingData oldRoutingData = routingData;
		routingData = newRoutingData;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.CONNECTION__ROUTING_DATA,
					oldRoutingData, routingData));
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public FBNetworkElement getSourceElement() {
		return org.eclipse.fordiac.ide.model.Annotations.getSourceElement(this);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public FBNetworkElement getDestinationElement() {
		return org.eclipse.fordiac.ide.model.Annotations.getDestinationElement(this);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public boolean isResourceConnection() {
		return org.eclipse.fordiac.ide.model.Annotations.isResourceConnection(this);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public FBNetwork getFBNetwork() {
		return org.eclipse.fordiac.ide.model.Annotations.getFBNetwork(this);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public void checkIfConnectionBroken() {
		org.eclipse.fordiac.ide.model.Annotations.checkifConnectionBroken(this);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public void updateRoutingData(final int dx1, final int dy, final int dx2) {
		final ConnectionRoutingData newRoutingData = org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory.eINSTANCE
				.createConnectionRoutingData();
		newRoutingData.setDx1(dx1);
		newRoutingData.setDy(dy);
		newRoutingData.setDx2(dx2);
		setRoutingData(newRoutingData);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public void setVisible(final boolean visible) {
		org.eclipse.fordiac.ide.model.annotations.ConnectionAnnotations.setVisible(this, visible);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public boolean isVisible() {
		return org.eclipse.fordiac.ide.model.annotations.ConnectionAnnotations.isVisible(this);
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

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public boolean hasError() {
		return getFileMarkerId() != 0;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public NotificationChain eInverseAdd(final InternalEObject otherEnd, final int featureID, NotificationChain msgs) {
		switch (featureID) {
		case LibraryElementPackage.CONNECTION__SOURCE:
			if (source != null) {
				msgs = ((InternalEObject) source).eInverseRemove(this,
						LibraryElementPackage.IINTERFACE_ELEMENT__OUTPUT_CONNECTIONS, IInterfaceElement.class, msgs);
			}
			return basicSetSource((IInterfaceElement) otherEnd, msgs);
		case LibraryElementPackage.CONNECTION__DESTINATION:
			if (destination != null) {
				msgs = ((InternalEObject) destination).eInverseRemove(this,
						LibraryElementPackage.IINTERFACE_ELEMENT__INPUT_CONNECTIONS, IInterfaceElement.class, msgs);
			}
			return basicSetDestination((IInterfaceElement) otherEnd, msgs);
		default:
			return super.eInverseAdd(otherEnd, featureID, msgs);
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public NotificationChain eInverseRemove(final InternalEObject otherEnd, final int featureID,
			final NotificationChain msgs) {
		switch (featureID) {
		case LibraryElementPackage.CONNECTION__SOURCE:
			return basicSetSource(null, msgs);
		case LibraryElementPackage.CONNECTION__DESTINATION:
			return basicSetDestination(null, msgs);
		default:
			return super.eInverseRemove(otherEnd, featureID, msgs);
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
		switch (featureID) {
		case LibraryElementPackage.CONNECTION__FILE_MARKER_ID:
			return getFileMarkerId();
		case LibraryElementPackage.CONNECTION__ERROR_MESSAGE:
			return getErrorMessage();
		case LibraryElementPackage.CONNECTION__RES_TYPE_CONNECTION:
			return isResTypeConnection();
		case LibraryElementPackage.CONNECTION__BROKEN_CONNECTION:
			return isBrokenConnection();
		case LibraryElementPackage.CONNECTION__SOURCE:
			if (resolve) {
				return getSource();
			}
			return basicGetSource();
		case LibraryElementPackage.CONNECTION__DESTINATION:
			if (resolve) {
				return getDestination();
			}
			return basicGetDestination();
		case LibraryElementPackage.CONNECTION__ROUTING_DATA:
			if (resolve) {
				return getRoutingData();
			}
			return basicGetRoutingData();
		default:
			return super.eGet(featureID, resolve, coreType);
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public void eSet(final int featureID, final Object newValue) {
		switch (featureID) {
		case LibraryElementPackage.CONNECTION__FILE_MARKER_ID:
			setFileMarkerId((Long) newValue);
			return;
		case LibraryElementPackage.CONNECTION__ERROR_MESSAGE:
			setErrorMessage((String) newValue);
			return;
		case LibraryElementPackage.CONNECTION__RES_TYPE_CONNECTION:
			setResTypeConnection((Boolean) newValue);
			return;
		case LibraryElementPackage.CONNECTION__BROKEN_CONNECTION:
			setBrokenConnection((Boolean) newValue);
			return;
		case LibraryElementPackage.CONNECTION__SOURCE:
			setSource((IInterfaceElement) newValue);
			return;
		case LibraryElementPackage.CONNECTION__DESTINATION:
			setDestination((IInterfaceElement) newValue);
			return;
		case LibraryElementPackage.CONNECTION__ROUTING_DATA:
			setRoutingData((ConnectionRoutingData) newValue);
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
	public void eUnset(final int featureID) {
		switch (featureID) {
		case LibraryElementPackage.CONNECTION__FILE_MARKER_ID:
			setFileMarkerId(FILE_MARKER_ID_EDEFAULT);
			return;
		case LibraryElementPackage.CONNECTION__ERROR_MESSAGE:
			setErrorMessage(ERROR_MESSAGE_EDEFAULT);
			return;
		case LibraryElementPackage.CONNECTION__RES_TYPE_CONNECTION:
			setResTypeConnection(RES_TYPE_CONNECTION_EDEFAULT);
			return;
		case LibraryElementPackage.CONNECTION__BROKEN_CONNECTION:
			setBrokenConnection(BROKEN_CONNECTION_EDEFAULT);
			return;
		case LibraryElementPackage.CONNECTION__SOURCE:
			setSource((IInterfaceElement) null);
			return;
		case LibraryElementPackage.CONNECTION__DESTINATION:
			setDestination((IInterfaceElement) null);
			return;
		case LibraryElementPackage.CONNECTION__ROUTING_DATA:
			setRoutingData((ConnectionRoutingData) null);
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
	public boolean eIsSet(final int featureID) {
		switch (featureID) {
		case LibraryElementPackage.CONNECTION__FILE_MARKER_ID:
			return fileMarkerId != FILE_MARKER_ID_EDEFAULT;
		case LibraryElementPackage.CONNECTION__ERROR_MESSAGE:
			return ERROR_MESSAGE_EDEFAULT == null ? errorMessage != null : !ERROR_MESSAGE_EDEFAULT.equals(errorMessage);
		case LibraryElementPackage.CONNECTION__RES_TYPE_CONNECTION:
			return resTypeConnection != RES_TYPE_CONNECTION_EDEFAULT;
		case LibraryElementPackage.CONNECTION__BROKEN_CONNECTION:
			return brokenConnection != BROKEN_CONNECTION_EDEFAULT;
		case LibraryElementPackage.CONNECTION__SOURCE:
			return source != null;
		case LibraryElementPackage.CONNECTION__DESTINATION:
			return destination != null;
		case LibraryElementPackage.CONNECTION__ROUTING_DATA:
			return routingData != null;
		default:
			return super.eIsSet(featureID);
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public int eBaseStructuralFeatureID(final int derivedFeatureID, final Class<?> baseClass) {
		if (baseClass == ErrorMarkerRef.class) {
			switch (derivedFeatureID) {
			case LibraryElementPackage.CONNECTION__FILE_MARKER_ID:
				return LibraryElementPackage.ERROR_MARKER_REF__FILE_MARKER_ID;
			case LibraryElementPackage.CONNECTION__ERROR_MESSAGE:
				return LibraryElementPackage.ERROR_MARKER_REF__ERROR_MESSAGE;
			default:
				return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public int eDerivedStructuralFeatureID(final int baseFeatureID, final Class<?> baseClass) {
		if (baseClass == ErrorMarkerRef.class) {
			switch (baseFeatureID) {
			case LibraryElementPackage.ERROR_MARKER_REF__FILE_MARKER_ID:
				return LibraryElementPackage.CONNECTION__FILE_MARKER_ID;
			case LibraryElementPackage.ERROR_MARKER_REF__ERROR_MESSAGE:
				return LibraryElementPackage.CONNECTION__ERROR_MESSAGE;
			default:
				return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		final StringBuilder result = new StringBuilder(super.toString());
		result.append(" (fileMarkerId: "); //$NON-NLS-1$
		result.append(fileMarkerId);
		result.append(", errorMessage: "); //$NON-NLS-1$
		result.append(errorMessage);
		result.append(", resTypeConnection: "); //$NON-NLS-1$
		result.append(resTypeConnection);
		result.append(", brokenConnection: "); //$NON-NLS-1$
		result.append(brokenConnection);
		result.append(')');
		return result.toString();
	}

} // ConnectionImpl
