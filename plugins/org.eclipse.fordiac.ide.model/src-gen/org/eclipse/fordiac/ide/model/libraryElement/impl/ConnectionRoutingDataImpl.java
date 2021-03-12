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

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.fordiac.ide.model.libraryElement.ConnectionRoutingData;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;

/** <!-- begin-user-doc --> An implementation of the model object '<em><b>Connection Routing Data</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionRoutingDataImpl#getDx1 <em>Dx1</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionRoutingDataImpl#getDx2 <em>Dx2</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionRoutingDataImpl#getDy <em>Dy</em>}</li>
 * </ul>
 *
 * @generated */
public class ConnectionRoutingDataImpl extends EObjectImpl implements ConnectionRoutingData {
	/** The default value of the '{@link #getDx1() <em>Dx1</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see #getDx1()
	 * @generated
	 * @ordered */
	protected static final int DX1_EDEFAULT = 0;

	/** The cached value of the '{@link #getDx1() <em>Dx1</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see #getDx1()
	 * @generated
	 * @ordered */
	protected int dx1 = DX1_EDEFAULT;

	/** The default value of the '{@link #getDx2() <em>Dx2</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see #getDx2()
	 * @generated
	 * @ordered */
	protected static final int DX2_EDEFAULT = 0;

	/** The cached value of the '{@link #getDx2() <em>Dx2</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see #getDx2()
	 * @generated
	 * @ordered */
	protected int dx2 = DX2_EDEFAULT;

	/** The default value of the '{@link #getDy() <em>Dy</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getDy()
	 * @generated
	 * @ordered */
	protected static final int DY_EDEFAULT = 0;

	/** The cached value of the '{@link #getDy() <em>Dy</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getDy()
	 * @generated
	 * @ordered */
	protected int dy = DY_EDEFAULT;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	protected ConnectionRoutingDataImpl() {
		super();
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.CONNECTION_ROUTING_DATA;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public int getDx1() {
		return dx1;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public void setDx1(int newDx1) {
		int oldDx1 = dx1;
		dx1 = newDx1;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.CONNECTION_ROUTING_DATA__DX1,
					oldDx1, dx1));
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public int getDx2() {
		return dx2;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public void setDx2(int newDx2) {
		int oldDx2 = dx2;
		dx2 = newDx2;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.CONNECTION_ROUTING_DATA__DX2,
					oldDx2, dx2));
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public int getDy() {
		return dy;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public void setDy(int newDy) {
		int oldDy = dy;
		dy = newDy;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.CONNECTION_ROUTING_DATA__DY,
					oldDy, dy));
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case LibraryElementPackage.CONNECTION_ROUTING_DATA__DX1:
			return getDx1();
		case LibraryElementPackage.CONNECTION_ROUTING_DATA__DX2:
			return getDx2();
		case LibraryElementPackage.CONNECTION_ROUTING_DATA__DY:
			return getDy();
		default:
			return super.eGet(featureID, resolve, coreType);
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case LibraryElementPackage.CONNECTION_ROUTING_DATA__DX1:
			setDx1((Integer) newValue);
			return;
		case LibraryElementPackage.CONNECTION_ROUTING_DATA__DX2:
			setDx2((Integer) newValue);
			return;
		case LibraryElementPackage.CONNECTION_ROUTING_DATA__DY:
			setDy((Integer) newValue);
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
		case LibraryElementPackage.CONNECTION_ROUTING_DATA__DX1:
			setDx1(DX1_EDEFAULT);
			return;
		case LibraryElementPackage.CONNECTION_ROUTING_DATA__DX2:
			setDx2(DX2_EDEFAULT);
			return;
		case LibraryElementPackage.CONNECTION_ROUTING_DATA__DY:
			setDy(DY_EDEFAULT);
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
		case LibraryElementPackage.CONNECTION_ROUTING_DATA__DX1:
			return dx1 != DX1_EDEFAULT;
		case LibraryElementPackage.CONNECTION_ROUTING_DATA__DX2:
			return dx2 != DX2_EDEFAULT;
		case LibraryElementPackage.CONNECTION_ROUTING_DATA__DY:
			return dy != DY_EDEFAULT;
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
		result.append(" (dx1: "); //$NON-NLS-1$
		result.append(dx1);
		result.append(", dx2: "); //$NON-NLS-1$
		result.append(dx2);
		result.append(", dy: "); //$NON-NLS-1$
		result.append(dy);
		result.append(')');
		return result.toString();
	}

} // ConnectionRoutingDataImpl
