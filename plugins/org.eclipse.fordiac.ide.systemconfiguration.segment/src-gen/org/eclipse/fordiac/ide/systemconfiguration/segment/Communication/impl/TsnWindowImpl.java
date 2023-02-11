/**
 * *******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License 2.0 which is available at http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr
 *          - initial implementation and/or documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.fordiac.ide.model.libraryElement.impl.CommunicationMappingTargetImpl;
import org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.Annotations;
import org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.CommunicationPackage;
import org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.TsnWindow;

/** <!-- begin-user-doc --> An implementation of the model object '<em><b>Tsn Window</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.impl.TsnWindowImpl#getDuration
 * <em>Duration</em>}</li>
 * </ul>
 *
 * @generated */
public class TsnWindowImpl extends CommunicationMappingTargetImpl implements TsnWindow {
	/** The default value of the '{@link #getDuration() <em>Duration</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @see #getDuration()
	 * @generated
	 * @ordered */
	protected static final int DURATION_EDEFAULT = 0;

	/** The cached value of the '{@link #getDuration() <em>Duration</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @see #getDuration()
	 * @generated
	 * @ordered */
	protected int duration = DURATION_EDEFAULT;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	protected TsnWindowImpl() {
		super();
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	protected EClass eStaticClass() {
		return CommunicationPackage.Literals.TSN_WINDOW;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public int getDuration() {
		return duration;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public void setDuration(final int newDuration) {
		final int oldDuration = duration;
		duration = newDuration;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, CommunicationPackage.TSN_WINDOW__DURATION,
					oldDuration, duration));
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public String getName() {
		return Annotations.getTsnWindowName(this);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
		switch (featureID) {
		case CommunicationPackage.TSN_WINDOW__DURATION:
			return getDuration();
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
		case CommunicationPackage.TSN_WINDOW__DURATION:
			setDuration((Integer) newValue);
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
		case CommunicationPackage.TSN_WINDOW__DURATION:
			setDuration(DURATION_EDEFAULT);
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
		case CommunicationPackage.TSN_WINDOW__DURATION:
			return duration != DURATION_EDEFAULT;
		default:
			return super.eIsSet(featureID);
		}
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
		result.append(" (duration: "); //$NON-NLS-1$
		result.append(duration);
		result.append(')');
		return result.toString();
	}

} // TsnWindowImpl
