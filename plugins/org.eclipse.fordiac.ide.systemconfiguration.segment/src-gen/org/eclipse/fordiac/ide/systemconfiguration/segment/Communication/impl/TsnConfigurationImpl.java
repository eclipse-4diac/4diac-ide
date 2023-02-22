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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.fordiac.ide.model.libraryElement.CommunicationMappingTarget;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.Annotations;
import org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.CommunicationPackage;
import org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.TsnConfiguration;
import org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.TsnWindow;

/** <!-- begin-user-doc --> An implementation of the model object '<em><b>Tsn Configuration</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.impl.TsnConfigurationImpl#getCycleTime
 * <em>Cycle Time</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.impl.TsnConfigurationImpl#getWindows
 * <em>Windows</em>}</li>
 * </ul>
 *
 * @generated */
public class TsnConfigurationImpl extends MinimalEObjectImpl.Container implements TsnConfiguration {
	/** The default value of the '{@link #getCycleTime() <em>Cycle Time</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @see #getCycleTime()
	 * @generated
	 * @ordered */
	protected static final int CYCLE_TIME_EDEFAULT = 0;

	/** The cached value of the '{@link #getCycleTime() <em>Cycle Time</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @see #getCycleTime()
	 * @generated
	 * @ordered */
	protected int cycleTime = CYCLE_TIME_EDEFAULT;

	/** The cached value of the '{@link #getWindows() <em>Windows</em>}' containment reference list. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @see #getWindows()
	 * @generated
	 * @ordered */
	protected EList<TsnWindow> windows;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	protected TsnConfigurationImpl() {
		super();
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	protected EClass eStaticClass() {
		return CommunicationPackage.Literals.TSN_CONFIGURATION;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public int getCycleTime() {
		return cycleTime;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public void setCycleTime(final int newCycleTime) {
		final int oldCycleTime = cycleTime;
		cycleTime = newCycleTime;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, CommunicationPackage.TSN_CONFIGURATION__CYCLE_TIME,
					oldCycleTime, cycleTime));
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EList<TsnWindow> getWindows() {
		if (windows == null) {
			windows = new EObjectContainmentEList.Resolving<>(TsnWindow.class, this,
					CommunicationPackage.TSN_CONFIGURATION__WINDOWS);
		}
		return windows;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EList<CommunicationMappingTarget> getMappingTargets() {
		return ECollections.toEList(windows);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc --> */
	@Override
	public EList<VarDeclaration> getParameters() {
		return Annotations.getParameters(windows);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public Segment getSegment() {
		return (Segment) eContainer();
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public NotificationChain eInverseRemove(final InternalEObject otherEnd, final int featureID,
			final NotificationChain msgs) {
		switch (featureID) {
		case CommunicationPackage.TSN_CONFIGURATION__WINDOWS:
			return ((InternalEList<?>) getWindows()).basicRemove(otherEnd, msgs);
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
		case CommunicationPackage.TSN_CONFIGURATION__CYCLE_TIME:
			return getCycleTime();
		case CommunicationPackage.TSN_CONFIGURATION__WINDOWS:
			return getWindows();
		default:
			return super.eGet(featureID, resolve, coreType);
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(final int featureID, final Object newValue) {
		switch (featureID) {
		case CommunicationPackage.TSN_CONFIGURATION__CYCLE_TIME:
			setCycleTime((Integer) newValue);
			return;
		case CommunicationPackage.TSN_CONFIGURATION__WINDOWS:
			getWindows().clear();
			getWindows().addAll((Collection<? extends TsnWindow>) newValue);
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
		case CommunicationPackage.TSN_CONFIGURATION__CYCLE_TIME:
			setCycleTime(CYCLE_TIME_EDEFAULT);
			return;
		case CommunicationPackage.TSN_CONFIGURATION__WINDOWS:
			getWindows().clear();
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
		case CommunicationPackage.TSN_CONFIGURATION__CYCLE_TIME:
			return cycleTime != CYCLE_TIME_EDEFAULT;
		case CommunicationPackage.TSN_CONFIGURATION__WINDOWS:
			return windows != null && !windows.isEmpty();
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
		result.append(" (cycleTime: "); //$NON-NLS-1$
		result.append(cycleTime);
		result.append(')');
		return result.toString();
	}

} // TsnConfigurationImpl
