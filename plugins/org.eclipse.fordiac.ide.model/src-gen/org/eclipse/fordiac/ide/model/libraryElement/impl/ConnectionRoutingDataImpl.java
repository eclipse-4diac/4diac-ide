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

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.fordiac.ide.model.libraryElement.ConnectionRoutingData;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Connection Routing Data</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionRoutingDataImpl#getDx1 <em>Dx1</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionRoutingDataImpl#getDx2 <em>Dx2</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionRoutingDataImpl#getDy <em>Dy</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionRoutingDataImpl#isNeedsValidation <em>Needs Validation</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConnectionRoutingDataImpl extends EObjectImpl implements ConnectionRoutingData {
	/**
	 * The default value of the '{@link #getDx1() <em>Dx1</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDx1()
	 * @generated
	 * @ordered
	 */
	protected static final double DX1_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getDx1() <em>Dx1</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDx1()
	 * @generated
	 * @ordered
	 */
	protected double dx1 = DX1_EDEFAULT;

	/**
	 * The default value of the '{@link #getDx2() <em>Dx2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDx2()
	 * @generated
	 * @ordered
	 */
	protected static final double DX2_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getDx2() <em>Dx2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDx2()
	 * @generated
	 * @ordered
	 */
	protected double dx2 = DX2_EDEFAULT;

	/**
	 * The default value of the '{@link #getDy() <em>Dy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDy()
	 * @generated
	 * @ordered
	 */
	protected static final double DY_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getDy() <em>Dy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDy()
	 * @generated
	 * @ordered
	 */
	protected double dy = DY_EDEFAULT;

	/**
	 * The default value of the '{@link #isNeedsValidation() <em>Needs Validation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isNeedsValidation()
	 * @generated
	 * @ordered
	 */
	protected static final boolean NEEDS_VALIDATION_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isNeedsValidation() <em>Needs Validation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isNeedsValidation()
	 * @generated
	 * @ordered
	 */
	protected boolean needsValidation = NEEDS_VALIDATION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConnectionRoutingDataImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.CONNECTION_ROUTING_DATA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getDx1() {
		return dx1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDx1(double newDx1) {
		double oldDx1 = dx1;
		dx1 = newDx1;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.CONNECTION_ROUTING_DATA__DX1, oldDx1, dx1));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getDx2() {
		return dx2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDx2(double newDx2) {
		double oldDx2 = dx2;
		dx2 = newDx2;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.CONNECTION_ROUTING_DATA__DX2, oldDx2, dx2));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getDy() {
		return dy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDy(double newDy) {
		double oldDy = dy;
		dy = newDy;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.CONNECTION_ROUTING_DATA__DY, oldDy, dy));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isNeedsValidation() {
		return needsValidation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setNeedsValidation(boolean newNeedsValidation) {
		boolean oldNeedsValidation = needsValidation;
		needsValidation = newNeedsValidation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.CONNECTION_ROUTING_DATA__NEEDS_VALIDATION, oldNeedsValidation, needsValidation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean is1SegementData() {
		return ConnectionRoutingDataAnnotations.connectionRoutingDataValueIsNull(getDx1());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean is3SegementData() {
		return !ConnectionRoutingDataAnnotations.connectionRoutingDataValueIsNull(getDx1()) && ConnectionRoutingDataAnnotations.connectionRoutingDataValueIsNull(getDy());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean is5SegementData() {
		return  !ConnectionRoutingDataAnnotations.connectionRoutingDataValueIsNull(getDx1()) && !ConnectionRoutingDataAnnotations.connectionRoutingDataValueIsNull(getDy());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case LibraryElementPackage.CONNECTION_ROUTING_DATA__DX1:
				return getDx1();
			case LibraryElementPackage.CONNECTION_ROUTING_DATA__DX2:
				return getDx2();
			case LibraryElementPackage.CONNECTION_ROUTING_DATA__DY:
				return getDy();
			case LibraryElementPackage.CONNECTION_ROUTING_DATA__NEEDS_VALIDATION:
				return isNeedsValidation();
			default:
				return super.eGet(featureID, resolve, coreType);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case LibraryElementPackage.CONNECTION_ROUTING_DATA__DX1:
				setDx1((Double)newValue);
				return;
			case LibraryElementPackage.CONNECTION_ROUTING_DATA__DX2:
				setDx2((Double)newValue);
				return;
			case LibraryElementPackage.CONNECTION_ROUTING_DATA__DY:
				setDy((Double)newValue);
				return;
			case LibraryElementPackage.CONNECTION_ROUTING_DATA__NEEDS_VALIDATION:
				setNeedsValidation((Boolean)newValue);
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
			case LibraryElementPackage.CONNECTION_ROUTING_DATA__DX1:
				setDx1(DX1_EDEFAULT);
				return;
			case LibraryElementPackage.CONNECTION_ROUTING_DATA__DX2:
				setDx2(DX2_EDEFAULT);
				return;
			case LibraryElementPackage.CONNECTION_ROUTING_DATA__DY:
				setDy(DY_EDEFAULT);
				return;
			case LibraryElementPackage.CONNECTION_ROUTING_DATA__NEEDS_VALIDATION:
				setNeedsValidation(NEEDS_VALIDATION_EDEFAULT);
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
			case LibraryElementPackage.CONNECTION_ROUTING_DATA__DX1:
				return dx1 != DX1_EDEFAULT;
			case LibraryElementPackage.CONNECTION_ROUTING_DATA__DX2:
				return dx2 != DX2_EDEFAULT;
			case LibraryElementPackage.CONNECTION_ROUTING_DATA__DY:
				return dy != DY_EDEFAULT;
			case LibraryElementPackage.CONNECTION_ROUTING_DATA__NEEDS_VALIDATION:
				return needsValidation != NEEDS_VALIDATION_EDEFAULT;
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
		result.append(" (dx1: "); //$NON-NLS-1$
		result.append(dx1);
		result.append(", dx2: "); //$NON-NLS-1$
		result.append(dx2);
		result.append(", dy: "); //$NON-NLS-1$
		result.append(dy);
		result.append(", needsValidation: "); //$NON-NLS-1$
		result.append(needsValidation);
		result.append(')');
		return result.toString();
	}

} //ConnectionRoutingDataImpl
