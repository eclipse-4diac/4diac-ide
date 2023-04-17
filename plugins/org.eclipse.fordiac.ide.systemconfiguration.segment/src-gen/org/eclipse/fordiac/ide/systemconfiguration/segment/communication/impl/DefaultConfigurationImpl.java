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
package org.eclipse.fordiac.ide.systemconfiguration.segment.communication.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.fordiac.ide.model.libraryElement.CommunicationMappingTarget;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.systemconfiguration.segment.communication.CommunicationPackage;
import org.eclipse.fordiac.ide.systemconfiguration.segment.communication.DefaultConfiguration;

/** <!-- begin-user-doc --> An implementation of the model object '<em><b>Default Configuration</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.systemconfiguration.segment.communication.impl.DefaultConfigurationImpl#getTarget
 * <em>Target</em>}</li>
 * </ul>
 *
 * @generated */
public class DefaultConfigurationImpl extends MinimalEObjectImpl.Container implements DefaultConfiguration {
	/** The cached value of the '{@link #getTarget() <em>Target</em>}' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getTarget()
	 * @generated
	 * @ordered */
	protected CommunicationMappingTarget target;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	protected DefaultConfigurationImpl() {
		super();
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	protected EClass eStaticClass() {
		return CommunicationPackage.Literals.DEFAULT_CONFIGURATION;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public CommunicationMappingTarget getTarget() {
		if (target != null && target.eIsProxy()) {
			InternalEObject oldTarget = (InternalEObject) target;
			target = (CommunicationMappingTarget) eResolveProxy(oldTarget);
			if (target != oldTarget) {
				InternalEObject newTarget = (InternalEObject) target;
				NotificationChain msgs = oldTarget.eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - CommunicationPackage.DEFAULT_CONFIGURATION__TARGET, null, null);
				if (newTarget.eInternalContainer() == null) {
					msgs = newTarget.eInverseAdd(this,
							EOPPOSITE_FEATURE_BASE - CommunicationPackage.DEFAULT_CONFIGURATION__TARGET, null, msgs);
				}
				if (msgs != null)
					msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							CommunicationPackage.DEFAULT_CONFIGURATION__TARGET, oldTarget, target));
			}
		}
		return target;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	public CommunicationMappingTarget basicGetTarget() {
		return target;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	public NotificationChain basicSetTarget(CommunicationMappingTarget newTarget, NotificationChain msgs) {
		CommunicationMappingTarget oldTarget = target;
		target = newTarget;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					CommunicationPackage.DEFAULT_CONFIGURATION__TARGET, oldTarget, newTarget);
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
	public void setTarget(CommunicationMappingTarget newTarget) {
		if (newTarget != target) {
			NotificationChain msgs = null;
			if (target != null)
				msgs = ((InternalEObject) target).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - CommunicationPackage.DEFAULT_CONFIGURATION__TARGET, null, msgs);
			if (newTarget != null)
				msgs = ((InternalEObject) newTarget).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - CommunicationPackage.DEFAULT_CONFIGURATION__TARGET, null, msgs);
			msgs = basicSetTarget(newTarget, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CommunicationPackage.DEFAULT_CONFIGURATION__TARGET,
					newTarget, newTarget));
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public String getId() {
		return "defaultConfig";
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EList<CommunicationMappingTarget> getMappingTargets() {
		return ECollections.asEList(target);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EList<VarDeclaration> getParameters() {
		return ECollections.emptyEList();
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
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case CommunicationPackage.DEFAULT_CONFIGURATION__TARGET:
			return basicSetTarget(null, msgs);
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
		case CommunicationPackage.DEFAULT_CONFIGURATION__TARGET:
			if (resolve)
				return getTarget();
			return basicGetTarget();
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
		case CommunicationPackage.DEFAULT_CONFIGURATION__TARGET:
			setTarget((CommunicationMappingTarget) newValue);
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
		case CommunicationPackage.DEFAULT_CONFIGURATION__TARGET:
			setTarget((CommunicationMappingTarget) null);
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
		case CommunicationPackage.DEFAULT_CONFIGURATION__TARGET:
			return target != null;
		default:
			return super.eIsSet(featureID);
		}
	}

} // DefaultConfigurationImpl
