/********************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.libraryElement.impl;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Mapping;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;

/** <!-- begin-user-doc --> An implementation of the model object '<em><b>FB Network Element</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.FBNetworkElementImpl#getPosition <em>Position</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.FBNetworkElementImpl#getInterface
 * <em>Interface</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.FBNetworkElementImpl#getMapping <em>Mapping</em>}</li>
 * </ul>
 *
 * @generated */
public class FBNetworkElementImpl extends TypedConfigureableObjectImpl implements FBNetworkElement {
	/** The cached value of the '{@link #getPosition() <em>Position</em>}' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getPosition()
	 * @generated
	 * @ordered */
	protected Position position;

	/** The cached value of the '{@link #getInterface() <em>Interface</em>}' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getInterface()
	 * @generated
	 * @ordered */
	protected InterfaceList interface_;

	/** The cached value of the '{@link #getMapping() <em>Mapping</em>}' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getMapping()
	 * @generated
	 * @ordered */
	protected Mapping mapping;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	protected FBNetworkElementImpl() {
		super();
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.FB_NETWORK_ELEMENT;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public Position getPosition() {
		if (position != null && position.eIsProxy()) {
			final InternalEObject oldPosition = (InternalEObject) position;
			position = (Position) eResolveProxy(oldPosition);
			if (position != oldPosition) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							LibraryElementPackage.FB_NETWORK_ELEMENT__POSITION, oldPosition, position));
				}
			}
		}
		return position;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	public Position basicGetPosition() {
		return position;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public void setPosition(final Position newPosition) {
		final Position oldPosition = position;
		position = newPosition;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.FB_NETWORK_ELEMENT__POSITION,
					oldPosition, position));
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public InterfaceList getInterface() {
		if (interface_ != null && interface_.eIsProxy()) {
			final InternalEObject oldInterface = (InternalEObject) interface_;
			interface_ = (InterfaceList) eResolveProxy(oldInterface);
			if (interface_ != oldInterface) {
				final InternalEObject newInterface = (InternalEObject) interface_;
				NotificationChain msgs = oldInterface.eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - LibraryElementPackage.FB_NETWORK_ELEMENT__INTERFACE, null, null);
				if (newInterface.eInternalContainer() == null) {
					msgs = newInterface.eInverseAdd(this,
							EOPPOSITE_FEATURE_BASE - LibraryElementPackage.FB_NETWORK_ELEMENT__INTERFACE, null, msgs);
				}
				if (msgs != null) {
					msgs.dispatch();
				}
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							LibraryElementPackage.FB_NETWORK_ELEMENT__INTERFACE, oldInterface, interface_));
				}
			}
		}
		return interface_;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	public InterfaceList basicGetInterface() {
		return interface_;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	public NotificationChain basicSetInterface(final InterfaceList newInterface, NotificationChain msgs) {
		final InterfaceList oldInterface = interface_;
		interface_ = newInterface;
		if (eNotificationRequired()) {
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					LibraryElementPackage.FB_NETWORK_ELEMENT__INTERFACE, oldInterface, newInterface);
			if (msgs == null) {
				msgs = notification;
			} else {
				msgs.add(notification);
			}
		}
		return msgs;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public void setInterface(final InterfaceList newInterface) {
		if (newInterface != interface_) {
			NotificationChain msgs = null;
			if (interface_ != null) {
				msgs = ((InternalEObject) interface_).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - LibraryElementPackage.FB_NETWORK_ELEMENT__INTERFACE, null, msgs);
			}
			if (newInterface != null) {
				msgs = ((InternalEObject) newInterface).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - LibraryElementPackage.FB_NETWORK_ELEMENT__INTERFACE, null, msgs);
			}
			msgs = basicSetInterface(newInterface, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.FB_NETWORK_ELEMENT__INTERFACE,
					newInterface, newInterface));
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public Mapping getMapping() {
		if (mapping != null && mapping.eIsProxy()) {
			final InternalEObject oldMapping = (InternalEObject) mapping;
			mapping = (Mapping) eResolveProxy(oldMapping);
			if (mapping != oldMapping) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							LibraryElementPackage.FB_NETWORK_ELEMENT__MAPPING, oldMapping, mapping));
				}
			}
		}
		return mapping;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	public Mapping basicGetMapping() {
		return mapping;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated not */
	@Override
	public void setMapping(final Mapping newMapping) {
		setMappingGen(newMapping);
		checkConnections();
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	public void setMappingGen(final Mapping newMapping) {
		final Mapping oldMapping = mapping;
		mapping = newMapping;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.FB_NETWORK_ELEMENT__MAPPING,
					oldMapping, mapping));
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public Resource getResource() {
		return org.eclipse.fordiac.ide.model.Annotations.getResource(this);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public IInterfaceElement getInterfaceElement(final String name) {
		return org.eclipse.fordiac.ide.model.Annotations.getInterfaceElement(this, name);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public FBNetworkElement getOpposite() {
		return org.eclipse.fordiac.ide.model.Annotations.getOpposite(this);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public FBNetwork getFbNetwork() {
		return org.eclipse.fordiac.ide.model.Annotations.getFbNetwork(this);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public void checkConnections() {
		org.eclipse.fordiac.ide.model.Annotations.checkConnections(this);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public boolean isMapped() {
		return org.eclipse.fordiac.ide.model.Annotations.isMapped(this);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public FBType getType() {
		// the base class of all types a FBNetworkElement can be (FB, Subapp, Adapter) is a FBType. Therefore we can
		// have this here.

		// this cannot be moved to the annotation class because there we don't have the super access!!!

		return super.getType() instanceof FBType ? (FBType) super.getType() : null;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public boolean isNestedInSubApp() {
		final EObject parent = this.eContainer();
		if (parent == null) {
			return false;
		}
		final EObject grandParent = parent.eContainer();
		if (grandParent == null) {
			return false;
		}
		return grandParent instanceof org.eclipse.fordiac.ide.model.libraryElement.SubApp;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public FBNetworkElement getOuterFBNetworkElement() {
		if ((this.eContainer() == null) || !(this.eContainer().eContainer() instanceof FBNetworkElement)) {
			return null;
		}
		return (FBNetworkElement) this.eContainer().eContainer();
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public void updatePosition(final int x, final int y) {
		final Position pos = org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory.eINSTANCE
				.createPosition();
		pos.setX(x);
		pos.setY(y);
		setPosition(pos);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public void updatePosition(final Point newPos) {
		updatePosition(newPos.x, newPos.y);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public NotificationChain eInverseRemove(final InternalEObject otherEnd, final int featureID,
			final NotificationChain msgs) {
		switch (featureID) {
		case LibraryElementPackage.FB_NETWORK_ELEMENT__INTERFACE:
			return basicSetInterface(null, msgs);
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
		case LibraryElementPackage.FB_NETWORK_ELEMENT__POSITION:
			if (resolve) {
				return getPosition();
			}
			return basicGetPosition();
		case LibraryElementPackage.FB_NETWORK_ELEMENT__INTERFACE:
			if (resolve) {
				return getInterface();
			}
			return basicGetInterface();
		case LibraryElementPackage.FB_NETWORK_ELEMENT__MAPPING:
			if (resolve) {
				return getMapping();
			}
			return basicGetMapping();
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
		case LibraryElementPackage.FB_NETWORK_ELEMENT__POSITION:
			setPosition((Position) newValue);
			return;
		case LibraryElementPackage.FB_NETWORK_ELEMENT__INTERFACE:
			setInterface((InterfaceList) newValue);
			return;
		case LibraryElementPackage.FB_NETWORK_ELEMENT__MAPPING:
			setMapping((Mapping) newValue);
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
		case LibraryElementPackage.FB_NETWORK_ELEMENT__POSITION:
			setPosition((Position) null);
			return;
		case LibraryElementPackage.FB_NETWORK_ELEMENT__INTERFACE:
			setInterface((InterfaceList) null);
			return;
		case LibraryElementPackage.FB_NETWORK_ELEMENT__MAPPING:
			setMapping((Mapping) null);
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
		case LibraryElementPackage.FB_NETWORK_ELEMENT__POSITION:
			return position != null;
		case LibraryElementPackage.FB_NETWORK_ELEMENT__INTERFACE:
			return interface_ != null;
		case LibraryElementPackage.FB_NETWORK_ELEMENT__MAPPING:
			return mapping != null;
		default:
			return super.eIsSet(featureID);
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public int eBaseStructuralFeatureID(final int derivedFeatureID, final Class<?> baseClass) {
		if (baseClass == PositionableElement.class) {
			switch (derivedFeatureID) {
			case LibraryElementPackage.FB_NETWORK_ELEMENT__POSITION:
				return LibraryElementPackage.POSITIONABLE_ELEMENT__POSITION;
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
		if (baseClass == PositionableElement.class) {
			switch (baseFeatureID) {
			case LibraryElementPackage.POSITIONABLE_ELEMENT__POSITION:
				return LibraryElementPackage.FB_NETWORK_ELEMENT__POSITION;
			default:
				return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} // FBNetworkElementImpl
