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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.fordiac.ide.fb.interpreter.DefaultRunFBType;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>FB
 * Runtime Abstract</b></em>'. <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class FBRuntimeAbstractImpl extends MinimalEObjectImpl.Container implements FBRuntimeAbstract {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected FBRuntimeAbstractImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OperationalSemanticsPackage.Literals.FB_RUNTIME_ABSTRACT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<EventOccurrence> run(final EventManager eventManager) {
		if (this.eContainer instanceof EventOccurrence) {
			return DefaultRunFBType.runFBType(this, (EventOccurrence) this.eContainer, eventManager);
		}
		throw new IllegalArgumentException("The eContainer of the FBRuntimeAbstract object must be an EventOccurrence");

	}

} // FBRuntimeAbstractImpl
