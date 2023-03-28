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
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage;
import org.eclipse.fordiac.ide.fb.interpreter.mm.EventManagerUtils;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

/** <!-- begin-user-doc --> An implementation of the model object '<em><b>FB Transaction</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.FBTransactionImpl#getOutputEventOccurrences <em>Output
 * Event Occurrences</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.FBTransactionImpl#getInputVariables <em>Input
 * Variables</em>}</li>
 * </ul>
 *
 * @generated */
public class FBTransactionImpl extends TransactionImpl implements FBTransaction {
	/** The cached value of the '{@link #getOutputEventOccurrences() <em>Output Event Occurrences</em>}' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getOutputEventOccurrences()
	 * @generated
	 * @ordered */
	protected EList<EventOccurrence> outputEventOccurrences;

	/** The cached value of the '{@link #getInputVariables() <em>Input Variables</em>}' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getInputVariables()
	 * @generated
	 * @ordered */
	protected EList<VarDeclaration> inputVariables;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	protected FBTransactionImpl() {
		super();
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	protected EClass eStaticClass() {
		return OperationalSemanticsPackage.Literals.FB_TRANSACTION;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EList<EventOccurrence> getOutputEventOccurrences() {
		if (outputEventOccurrences == null) {
			outputEventOccurrences = new EObjectContainmentEList.Resolving<>(EventOccurrence.class, this,
					OperationalSemanticsPackage.FB_TRANSACTION__OUTPUT_EVENT_OCCURRENCES);
		}
		return outputEventOccurrences;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EList<VarDeclaration> getInputVariables() {
		if (inputVariables == null) {
			inputVariables = new EObjectResolvingEList<>(VarDeclaration.class, this,
					OperationalSemanticsPackage.FB_TRANSACTION__INPUT_VARIABLES);
		}
		return inputVariables;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public void process() {
		EventManagerUtils.processFbTransaction(this);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case OperationalSemanticsPackage.FB_TRANSACTION__OUTPUT_EVENT_OCCURRENCES:
			return ((InternalEList<?>) getOutputEventOccurrences()).basicRemove(otherEnd, msgs);
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
		case OperationalSemanticsPackage.FB_TRANSACTION__OUTPUT_EVENT_OCCURRENCES:
			return getOutputEventOccurrences();
		case OperationalSemanticsPackage.FB_TRANSACTION__INPUT_VARIABLES:
			return getInputVariables();
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
		case OperationalSemanticsPackage.FB_TRANSACTION__OUTPUT_EVENT_OCCURRENCES:
			getOutputEventOccurrences().clear();
			getOutputEventOccurrences().addAll((Collection<? extends EventOccurrence>) newValue);
			return;
		case OperationalSemanticsPackage.FB_TRANSACTION__INPUT_VARIABLES:
			getInputVariables().clear();
			getInputVariables().addAll((Collection<? extends VarDeclaration>) newValue);
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
		case OperationalSemanticsPackage.FB_TRANSACTION__OUTPUT_EVENT_OCCURRENCES:
			getOutputEventOccurrences().clear();
			return;
		case OperationalSemanticsPackage.FB_TRANSACTION__INPUT_VARIABLES:
			getInputVariables().clear();
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
		case OperationalSemanticsPackage.FB_TRANSACTION__OUTPUT_EVENT_OCCURRENCES:
			return outputEventOccurrences != null && !outputEventOccurrences.isEmpty();
		case OperationalSemanticsPackage.FB_TRANSACTION__INPUT_VARIABLES:
			return inputVariables != null && !inputVariables.isEmpty();
		default:
			return super.eIsSet(featureID);
		}
	}

} // FBTransactionImpl
