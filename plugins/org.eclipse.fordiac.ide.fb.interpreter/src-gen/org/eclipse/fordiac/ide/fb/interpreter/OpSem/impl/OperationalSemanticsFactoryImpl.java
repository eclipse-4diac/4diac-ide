/**
 * Copyright (c) 2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License 2.0 which is available at http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Antonio Garmendía, Bianca Wiesmayr
 *          - initial implementation and/or documentation
 */
package org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsFactory;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;

/** <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 *
 * @generated */
public class OperationalSemanticsFactoryImpl extends EFactoryImpl implements OperationalSemanticsFactory {
	/** Creates the default factory implementation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	public static OperationalSemanticsFactory init() {
		try {
			OperationalSemanticsFactory theOperationalSemanticsFactory = (OperationalSemanticsFactory) EPackage.Registry.INSTANCE
					.getEFactory(OperationalSemanticsPackage.eNS_URI);
			if (theOperationalSemanticsFactory != null) {
				return theOperationalSemanticsFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new OperationalSemanticsFactoryImpl();
	}

	/** Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	public OperationalSemanticsFactoryImpl() {
		super();
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case OperationalSemanticsPackage.EVENT_OCCURRENCE:
			return createEventOccurrence();
		case OperationalSemanticsPackage.EVENT_MANAGER:
			return createEventManager();
		case OperationalSemanticsPackage.BASIC_FB_TYPE_RUNTIME:
			return createBasicFBTypeRuntime();
		case OperationalSemanticsPackage.FB_TYPE_RUNTIME:
			return createFBTypeRuntime();
		case OperationalSemanticsPackage.TRANSACTION:
			return createTransaction();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EventOccurrence createEventOccurrence() {
		EventOccurrenceImpl eventOccurrence = new EventOccurrenceImpl();
		return eventOccurrence;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EventManager createEventManager() {
		EventManagerImpl eventManager = new EventManagerImpl();
		return eventManager;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public BasicFBTypeRuntime createBasicFBTypeRuntime() {
		BasicFBTypeRuntimeImpl basicFBTypeRuntime = new BasicFBTypeRuntimeImpl();
		return basicFBTypeRuntime;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public FBTypeRuntime createFBTypeRuntime() {
		FBTypeRuntimeImpl fbTypeRuntime = new FBTypeRuntimeImpl();
		return fbTypeRuntime;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public Transaction createTransaction() {
		TransactionImpl transaction = new TransactionImpl();
		return transaction;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public OperationalSemanticsPackage getOperationalSemanticsPackage() {
		return (OperationalSemanticsPackage) getEPackage();
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @deprecated
	 * @generated */
	@Deprecated
	public static OperationalSemanticsPackage getPackage() {
		return OperationalSemanticsPackage.eINSTANCE;
	}

} // OperationalSemanticsFactoryImpl
