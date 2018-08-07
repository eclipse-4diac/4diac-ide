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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
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
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionImpl#getDx1 <em>Dx1</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionImpl#getDx2 <em>Dx2</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionImpl#getDy <em>Dy</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionImpl#isResTypeConnection <em>Res Type Connection</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionImpl#isBrokenConnection <em>Broken Connection</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionImpl#getSource <em>Source</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionImpl#getDestination <em>Destination</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class ConnectionImpl extends ConfigurableObjectImpl implements Connection {
	/**
	 * The default value of the '{@link #getDx1() <em>Dx1</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDx1()
	 * @generated
	 * @ordered
	 */
	protected static final int DX1_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getDx1() <em>Dx1</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDx1()
	 * @generated
	 * @ordered
	 */
	protected int dx1 = DX1_EDEFAULT;

	/**
	 * The default value of the '{@link #getDx2() <em>Dx2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDx2()
	 * @generated
	 * @ordered
	 */
	protected static final int DX2_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getDx2() <em>Dx2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDx2()
	 * @generated
	 * @ordered
	 */
	protected int dx2 = DX2_EDEFAULT;

	/**
	 * The default value of the '{@link #getDy() <em>Dy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDy()
	 * @generated
	 * @ordered
	 */
	protected static final int DY_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getDy() <em>Dy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDy()
	 * @generated
	 * @ordered
	 */
	protected int dy = DY_EDEFAULT;

	/**
	 * The default value of the '{@link #isResTypeConnection() <em>Res Type Connection</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isResTypeConnection()
	 * @generated
	 * @ordered
	 */
	protected static final boolean RES_TYPE_CONNECTION_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isResTypeConnection() <em>Res Type Connection</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isResTypeConnection()
	 * @generated
	 * @ordered
	 */
	protected boolean resTypeConnection = RES_TYPE_CONNECTION_EDEFAULT;

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
	public int getDx1() {
		return dx1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDx1(int newDx1) {
		int oldDx1 = dx1;
		dx1 = newDx1;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.CONNECTION__DX1, oldDx1, dx1));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getDx2() {
		return dx2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDx2(int newDx2) {
		int oldDx2 = dx2;
		dx2 = newDx2;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.CONNECTION__DX2, oldDx2, dx2));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getDy() {
		return dy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDy(int newDy) {
		int oldDy = dy;
		dy = newDy;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.CONNECTION__DY, oldDy, dy));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isResTypeConnection() {
		return resTypeConnection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setResTypeConnection(boolean newResTypeConnection) {
		boolean oldResTypeConnection = resTypeConnection;
		resTypeConnection = newResTypeConnection;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.CONNECTION__RES_TYPE_CONNECTION, oldResTypeConnection, resTypeConnection));
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
		return org.eclipse.fordiac.ide.model.Annotations.getFBNetwork(this);
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
	 * @generated not
	 */
	@Override
	public void setSource(IInterfaceElement newSource) {
		setSourceGen(newSource);
		if(null != getSource() && null != getDestination()){
			checkIfConnectionBroken();
		}
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSourceGen(IInterfaceElement newSource) {
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
	 * @generated not
	 */
	@Override
	public void setDestination(IInterfaceElement newDestination) {
		setDestinationGen(newDestination);
		if(null != getSource() && null != getDestination()){
			checkIfConnectionBroken();
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDestinationGen(IInterfaceElement newDestination) {
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
			case LibraryElementPackage.CONNECTION__SOURCE:
				return basicSetSource(null, msgs);
			case LibraryElementPackage.CONNECTION__DESTINATION:
				return basicSetDestination(null, msgs);
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
			case LibraryElementPackage.CONNECTION__DX1:
				return getDx1();
			case LibraryElementPackage.CONNECTION__DX2:
				return getDx2();
			case LibraryElementPackage.CONNECTION__DY:
				return getDy();
			case LibraryElementPackage.CONNECTION__RES_TYPE_CONNECTION:
				return isResTypeConnection();
			case LibraryElementPackage.CONNECTION__BROKEN_CONNECTION:
				return isBrokenConnection();
			case LibraryElementPackage.CONNECTION__SOURCE:
				if (resolve) return getSource();
				return basicGetSource();
			case LibraryElementPackage.CONNECTION__DESTINATION:
				if (resolve) return getDestination();
				return basicGetDestination();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case LibraryElementPackage.CONNECTION__DX1:
				setDx1((Integer)newValue);
				return;
			case LibraryElementPackage.CONNECTION__DX2:
				setDx2((Integer)newValue);
				return;
			case LibraryElementPackage.CONNECTION__DY:
				setDy((Integer)newValue);
				return;
			case LibraryElementPackage.CONNECTION__RES_TYPE_CONNECTION:
				setResTypeConnection((Boolean)newValue);
				return;
			case LibraryElementPackage.CONNECTION__BROKEN_CONNECTION:
				setBrokenConnection((Boolean)newValue);
				return;
			case LibraryElementPackage.CONNECTION__SOURCE:
				setSource((IInterfaceElement)newValue);
				return;
			case LibraryElementPackage.CONNECTION__DESTINATION:
				setDestination((IInterfaceElement)newValue);
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
			case LibraryElementPackage.CONNECTION__DX1:
				setDx1(DX1_EDEFAULT);
				return;
			case LibraryElementPackage.CONNECTION__DX2:
				setDx2(DX2_EDEFAULT);
				return;
			case LibraryElementPackage.CONNECTION__DY:
				setDy(DY_EDEFAULT);
				return;
			case LibraryElementPackage.CONNECTION__RES_TYPE_CONNECTION:
				setResTypeConnection(RES_TYPE_CONNECTION_EDEFAULT);
				return;
			case LibraryElementPackage.CONNECTION__BROKEN_CONNECTION:
				setBrokenConnection(BROKEN_CONNECTION_EDEFAULT);
				return;
			case LibraryElementPackage.CONNECTION__SOURCE:
				setSource((IInterfaceElement)null);
				return;
			case LibraryElementPackage.CONNECTION__DESTINATION:
				setDestination((IInterfaceElement)null);
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
			case LibraryElementPackage.CONNECTION__DX1:
				return dx1 != DX1_EDEFAULT;
			case LibraryElementPackage.CONNECTION__DX2:
				return dx2 != DX2_EDEFAULT;
			case LibraryElementPackage.CONNECTION__DY:
				return dy != DY_EDEFAULT;
			case LibraryElementPackage.CONNECTION__RES_TYPE_CONNECTION:
				return resTypeConnection != RES_TYPE_CONNECTION_EDEFAULT;
			case LibraryElementPackage.CONNECTION__BROKEN_CONNECTION:
				return brokenConnection != BROKEN_CONNECTION_EDEFAULT;
			case LibraryElementPackage.CONNECTION__SOURCE:
				return source != null;
			case LibraryElementPackage.CONNECTION__DESTINATION:
				return destination != null;
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

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (dx1: "); //$NON-NLS-1$
		result.append(dx1);
		result.append(", dx2: "); //$NON-NLS-1$
		result.append(dx2);
		result.append(", dy: "); //$NON-NLS-1$
		result.append(dy);
		result.append(", resTypeConnection: "); //$NON-NLS-1$
		result.append(resTypeConnection);
		result.append(", brokenConnection: "); //$NON-NLS-1$
		result.append(brokenConnection);
		result.append(')');
		return result.toString();
	}

} //ConnectionImpl
