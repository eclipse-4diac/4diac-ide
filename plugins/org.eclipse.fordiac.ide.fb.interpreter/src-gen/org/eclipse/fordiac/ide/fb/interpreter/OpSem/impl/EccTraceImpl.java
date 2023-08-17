/**
 * *******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License 2.0 which is available at http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Antonio Garmendía, Bianca Wiesmayr
 *          - initial implementation and/or documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EccTrace;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.TransitionTrace;
import org.eclipse.fordiac.ide.fb.interpreter.mm.TracingAnnotations;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;

/** <!-- begin-user-doc --> An implementation of the model object '<em><b>Ecc Trace</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.EccTraceImpl#getTransitionTraces <em>Transition
 * Traces</em>}</li>
 * </ul>
 *
 * @generated */
public class EccTraceImpl extends TraceImpl implements EccTrace {
	/** The cached value of the '{@link #getTransitionTraces() <em>Transition Traces</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getTransitionTraces()
	 * @generated
	 * @ordered */
	protected EList<TransitionTrace> transitionTraces;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	protected EccTraceImpl() {
		super();
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	protected EClass eStaticClass() {
		return OperationalSemanticsPackage.Literals.ECC_TRACE;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EList<TransitionTrace> getTransitionTraces() {
		if (transitionTraces == null) {
			transitionTraces = new EObjectContainmentEList.Resolving<>(TransitionTrace.class, this,
					OperationalSemanticsPackage.ECC_TRACE__TRANSITION_TRACES);
		}
		return transitionTraces;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EList<ECTransition> getTransitions(final ECC ecc) {
		return TracingAnnotations.getTransitions(ecc, this);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case OperationalSemanticsPackage.ECC_TRACE__TRANSITION_TRACES:
			return ((InternalEList<?>) getTransitionTraces()).basicRemove(otherEnd, msgs);
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
		case OperationalSemanticsPackage.ECC_TRACE__TRANSITION_TRACES:
			return getTransitionTraces();
		default:
			return super.eGet(featureID, resolve, coreType);
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case OperationalSemanticsPackage.ECC_TRACE__TRANSITION_TRACES:
			getTransitionTraces().clear();
			getTransitionTraces().addAll((Collection<? extends TransitionTrace>) newValue);
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
		case OperationalSemanticsPackage.ECC_TRACE__TRANSITION_TRACES:
			getTransitionTraces().clear();
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
		case OperationalSemanticsPackage.ECC_TRACE__TRANSITION_TRACES:
			return transitionTraces != null && !transitionTraces.isEmpty();
		default:
			return super.eIsSet(featureID);
		}
	}

} // EccTraceImpl
