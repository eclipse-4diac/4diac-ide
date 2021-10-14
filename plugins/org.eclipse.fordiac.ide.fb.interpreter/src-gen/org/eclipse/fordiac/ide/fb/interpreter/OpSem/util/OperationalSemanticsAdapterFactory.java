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
package org.eclipse.fordiac.ide.fb.interpreter.OpSem.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;

/** <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides an adapter <code>createXXX</code>
 * method for each class of the model. <!-- end-user-doc -->
 *
 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage
 * @generated */
public class OperationalSemanticsAdapterFactory extends AdapterFactoryImpl {
	/** The cached model package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	protected static OperationalSemanticsPackage modelPackage;

	/** Creates an instance of the adapter factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	public OperationalSemanticsAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = OperationalSemanticsPackage.eINSTANCE;
		}
	}

	/** Returns whether this factory is applicable for the type of the object. <!-- begin-user-doc --> This
	 * implementation returns <code>true</code> if the object is either the model's package or is an instance object of
	 * the model. <!-- end-user-doc -->
	 *
	 * @return whether this factory is applicable for the type of the object.
	 * @generated */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject) object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/** The switch that delegates to the <code>createXXX</code> methods. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	protected OperationalSemanticsSwitch<Adapter> modelSwitch = new OperationalSemanticsSwitch<>() {
		@Override
		public Adapter caseEventOccurrence(EventOccurrence object) {
			return createEventOccurrenceAdapter();
		}

		@Override
		public Adapter caseEventManager(EventManager object) {
			return createEventManagerAdapter();
		}

		@Override
		public Adapter caseFBRuntimeAbstract(FBRuntimeAbstract object) {
			return createFBRuntimeAbstractAdapter();
		}

		@Override
		public Adapter caseBasicFBTypeRuntime(BasicFBTypeRuntime object) {
			return createBasicFBTypeRuntimeAdapter();
		}

		@Override
		public Adapter caseFBTypeRuntime(FBTypeRuntime object) {
			return createFBTypeRuntimeAdapter();
		}

		@Override
		public Adapter caseTransaction(Transaction object) {
			return createTransactionAdapter();
		}

		@Override
		public Adapter defaultCase(EObject object) {
			return createEObjectAdapter();
		}
	};

	/** Creates an adapter for the <code>target</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject) target);
	}

	/** Creates a new adapter for an object of class
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence <em>Event Occurrence</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
	 * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence
	 * @generated */
	public Adapter createEventOccurrenceAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager
	 * <em>Event Manager</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager
	 * @generated */
	public Adapter createEventManagerAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract <em>FB Runtime Abstract</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
	 * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract
	 * @generated */
	public Adapter createFBRuntimeAbstractAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime <em>Basic FB Type Runtime</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
	 * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime
	 * @generated */
	public Adapter createBasicFBTypeRuntimeAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTypeRuntime
	 * <em>FB Type Runtime</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTypeRuntime
	 * @generated */
	public Adapter createFBTypeRuntimeAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction
	 * <em>Transaction</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction
	 * @generated */
	public Adapter createTransactionAdapter() {
		return null;
	}

	/** Creates a new adapter for the default case. <!-- begin-user-doc --> This default implementation returns null.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @generated */
	public Adapter createEObjectAdapter() {
		return null;
	}

} // OperationalSemanticsAdapterFactory
