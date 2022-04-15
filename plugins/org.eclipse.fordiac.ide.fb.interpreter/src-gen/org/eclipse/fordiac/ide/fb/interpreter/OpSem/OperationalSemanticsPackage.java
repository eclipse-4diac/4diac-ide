/**
 * Copyright (c) 2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License 2.0 which is available at http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Antonio Garmend�a, Bianca Wiesmayr
 *          - initial implementation and/or documentation
 */
package org.eclipse.fordiac.ide.fb.interpreter.OpSem;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/** <!-- begin-user-doc --> The <b>Package</b> for the model. It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsFactory
 * @model kind="package"
 * @generated */
public interface OperationalSemanticsPackage extends EPackage {
	/** The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	String eNAME = "OpSem"; //$NON-NLS-1$

	/** The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	String eNS_URI = "http://OperationalSemantics/1.0"; //$NON-NLS-1$

	/** The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	String eNS_PREFIX = "opsem"; //$NON-NLS-1$

	/** The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	OperationalSemanticsPackage eINSTANCE = org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.OperationalSemanticsPackageImpl
			.init();

	/** The meta object id for the '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.EventOccurrenceImpl
	 * <em>Event Occurrence</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.EventOccurrenceImpl
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.OperationalSemanticsPackageImpl#getEventOccurrence()
	 * @generated */
	int EVENT_OCCURRENCE = 0;

	/** The feature id for the '<em><b>Event</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered */
	int EVENT_OCCURRENCE__EVENT = 0;

	/** The feature id for the '<em><b>Active</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered */
	int EVENT_OCCURRENCE__ACTIVE = 1;

	/** The feature id for the '<em><b>Ignored</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered */
	int EVENT_OCCURRENCE__IGNORED = 2;

	/** The feature id for the '<em><b>Fb Runtime</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered */
	int EVENT_OCCURRENCE__FB_RUNTIME = 3;

	/** The number of structural features of the '<em>Event Occurrence</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered */
	int EVENT_OCCURRENCE_FEATURE_COUNT = 4;

	/** The meta object id for the '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.EventManagerImpl <em>Event
	 * Manager</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.EventManagerImpl
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.OperationalSemanticsPackageImpl#getEventManager()
	 * @generated */
	int EVENT_MANAGER = 1;

	/** The feature id for the '<em><b>Transactions</b></em>' containment reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered */
	int EVENT_MANAGER__TRANSACTIONS = 0;

	/** The number of structural features of the '<em>Event Manager</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered */
	int EVENT_MANAGER_FEATURE_COUNT = 1;

	/** The meta object id for the '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.FBRuntimeAbstractImpl
	 * <em>FB Runtime Abstract</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.FBRuntimeAbstractImpl
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.OperationalSemanticsPackageImpl#getFBRuntimeAbstract()
	 * @generated */
	int FB_RUNTIME_ABSTRACT = 2;

	/** The number of structural features of the '<em>FB Runtime Abstract</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered */
	int FB_RUNTIME_ABSTRACT_FEATURE_COUNT = 0;

	/** The meta object id for the '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.BasicFBTypeRuntimeImpl
	 * <em>Basic FB Type Runtime</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.BasicFBTypeRuntimeImpl
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.OperationalSemanticsPackageImpl#getBasicFBTypeRuntime()
	 * @generated */
	int BASIC_FB_TYPE_RUNTIME = 3;

	/** The feature id for the '<em><b>Basicfbtype</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered */
	int BASIC_FB_TYPE_RUNTIME__BASICFBTYPE = FB_RUNTIME_ABSTRACT_FEATURE_COUNT + 0;

	/** The feature id for the '<em><b>Active State</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered */
	int BASIC_FB_TYPE_RUNTIME__ACTIVE_STATE = FB_RUNTIME_ABSTRACT_FEATURE_COUNT + 1;

	/** The number of structural features of the '<em>Basic FB Type Runtime</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered */
	int BASIC_FB_TYPE_RUNTIME_FEATURE_COUNT = FB_RUNTIME_ABSTRACT_FEATURE_COUNT + 2;

	/** The meta object id for the '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.FBTypeRuntimeImpl <em>FB
	 * Type Runtime</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.FBTypeRuntimeImpl
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.OperationalSemanticsPackageImpl#getFBTypeRuntime()
	 * @generated */
	int FB_TYPE_RUNTIME = 4;

	/** The feature id for the '<em><b>Fbtype</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @generated
	 * @ordered */
	int FB_TYPE_RUNTIME__FBTYPE = FB_RUNTIME_ABSTRACT_FEATURE_COUNT + 0;

	/** The number of structural features of the '<em>FB Type Runtime</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered */
	int FB_TYPE_RUNTIME_FEATURE_COUNT = FB_RUNTIME_ABSTRACT_FEATURE_COUNT + 1;

	/** The meta object id for the '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.TransactionImpl
	 * <em>Transaction</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.TransactionImpl
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.OperationalSemanticsPackageImpl#getTransaction()
	 * @generated */
	int TRANSACTION = 5;

	/** The feature id for the '<em><b>Input Event Occurrence</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered */
	int TRANSACTION__INPUT_EVENT_OCCURRENCE = 0;

	/** The feature id for the '<em><b>Output Event Occurences</b></em>' containment reference list. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered */
	int TRANSACTION__OUTPUT_EVENT_OCCURENCES = 1;

	/** The number of structural features of the '<em>Transaction</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @generated
	 * @ordered */
	int TRANSACTION_FEATURE_COUNT = 2;

	/** Returns the meta object for class '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence <em>Event
	 * Occurrence</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Event Occurrence</em>'.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence
	 * @generated */
	EClass getEventOccurrence();

	/** Returns the meta object for the reference
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence#getEvent <em>Event</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Event</em>'.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence#getEvent()
	 * @see #getEventOccurrence()
	 * @generated */
	EReference getEventOccurrence_Event();

	/** Returns the meta object for the attribute
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence#isActive <em>Active</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Active</em>'.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence#isActive()
	 * @see #getEventOccurrence()
	 * @generated */
	EAttribute getEventOccurrence_Active();

	/** Returns the meta object for the attribute
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence#isIgnored <em>Ignored</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Ignored</em>'.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence#isIgnored()
	 * @see #getEventOccurrence()
	 * @generated */
	EAttribute getEventOccurrence_Ignored();

	/** Returns the meta object for the containment reference
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence#getFbRuntime <em>Fb Runtime</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Fb Runtime</em>'.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence#getFbRuntime()
	 * @see #getEventOccurrence()
	 * @generated */
	EReference getEventOccurrence_FbRuntime();

	/** Returns the meta object for class '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager <em>Event
	 * Manager</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Event Manager</em>'.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager
	 * @generated */
	EClass getEventManager();

	/** Returns the meta object for the containment reference list
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager#getTransactions <em>Transactions</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Transactions</em>'.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager#getTransactions()
	 * @see #getEventManager()
	 * @generated */
	EReference getEventManager_Transactions();

	/** Returns the meta object for class '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract <em>FB
	 * Runtime Abstract</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>FB Runtime Abstract</em>'.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract
	 * @generated */
	EClass getFBRuntimeAbstract();

	/** Returns the meta object for class '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime
	 * <em>Basic FB Type Runtime</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Basic FB Type Runtime</em>'.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime
	 * @generated */
	EClass getBasicFBTypeRuntime();

	/** Returns the meta object for the containment reference
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime#getBasicfbtype <em>Basicfbtype</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Basicfbtype</em>'.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime#getBasicfbtype()
	 * @see #getBasicFBTypeRuntime()
	 * @generated */
	EReference getBasicFBTypeRuntime_Basicfbtype();

	/** Returns the meta object for the reference
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime#getActiveState <em>Active State</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Active State</em>'.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime#getActiveState()
	 * @see #getBasicFBTypeRuntime()
	 * @generated */
	EReference getBasicFBTypeRuntime_ActiveState();

	/** Returns the meta object for class '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTypeRuntime <em>FB Type
	 * Runtime</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>FB Type Runtime</em>'.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTypeRuntime
	 * @generated */
	EClass getFBTypeRuntime();

	/** Returns the meta object for the containment reference
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTypeRuntime#getFbtype <em>Fbtype</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Fbtype</em>'.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTypeRuntime#getFbtype()
	 * @see #getFBTypeRuntime()
	 * @generated */
	EReference getFBTypeRuntime_Fbtype();

	/** Returns the meta object for class '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction
	 * <em>Transaction</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Transaction</em>'.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction
	 * @generated */
	EClass getTransaction();

	/** Returns the meta object for the containment reference
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction#getInputEventOccurrence <em>Input Event
	 * Occurrence</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Input Event Occurrence</em>'.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction#getInputEventOccurrence()
	 * @see #getTransaction()
	 * @generated */
	EReference getTransaction_InputEventOccurrence();

	/** Returns the meta object for the containment reference list
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction#getOutputEventOccurences <em>Output Event
	 * Occurences</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Output Event Occurences</em>'.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction#getOutputEventOccurences()
	 * @see #getTransaction()
	 * @generated */
	EReference getTransaction_OutputEventOccurences();

	/** Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated */
	OperationalSemanticsFactory getOperationalSemanticsFactory();

	/** <!-- begin-user-doc --> Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 *
	 * @generated */
	interface Literals {
		/** The meta object literal for the
		 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.EventOccurrenceImpl <em>Event Occurrence</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.EventOccurrenceImpl
		 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.OperationalSemanticsPackageImpl#getEventOccurrence()
		 * @generated */
		EClass EVENT_OCCURRENCE = eINSTANCE.getEventOccurrence();

		/** The meta object literal for the '<em><b>Event</b></em>' reference feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 *
		 * @generated */
		EReference EVENT_OCCURRENCE__EVENT = eINSTANCE.getEventOccurrence_Event();

		/** The meta object literal for the '<em><b>Active</b></em>' attribute feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 *
		 * @generated */
		EAttribute EVENT_OCCURRENCE__ACTIVE = eINSTANCE.getEventOccurrence_Active();

		/** The meta object literal for the '<em><b>Ignored</b></em>' attribute feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 *
		 * @generated */
		EAttribute EVENT_OCCURRENCE__IGNORED = eINSTANCE.getEventOccurrence_Ignored();

		/** The meta object literal for the '<em><b>Fb Runtime</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated */
		EReference EVENT_OCCURRENCE__FB_RUNTIME = eINSTANCE.getEventOccurrence_FbRuntime();

		/** The meta object literal for the '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.EventManagerImpl
		 * <em>Event Manager</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.EventManagerImpl
		 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.OperationalSemanticsPackageImpl#getEventManager()
		 * @generated */
		EClass EVENT_MANAGER = eINSTANCE.getEventManager();

		/** The meta object literal for the '<em><b>Transactions</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated */
		EReference EVENT_MANAGER__TRANSACTIONS = eINSTANCE.getEventManager_Transactions();

		/** The meta object literal for the
		 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.FBRuntimeAbstractImpl <em>FB Runtime
		 * Abstract</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.FBRuntimeAbstractImpl
		 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.OperationalSemanticsPackageImpl#getFBRuntimeAbstract()
		 * @generated */
		EClass FB_RUNTIME_ABSTRACT = eINSTANCE.getFBRuntimeAbstract();

		/** The meta object literal for the
		 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.BasicFBTypeRuntimeImpl <em>Basic FB Type
		 * Runtime</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.BasicFBTypeRuntimeImpl
		 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.OperationalSemanticsPackageImpl#getBasicFBTypeRuntime()
		 * @generated */
		EClass BASIC_FB_TYPE_RUNTIME = eINSTANCE.getBasicFBTypeRuntime();

		/** The meta object literal for the '<em><b>Basicfbtype</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated */
		EReference BASIC_FB_TYPE_RUNTIME__BASICFBTYPE = eINSTANCE.getBasicFBTypeRuntime_Basicfbtype();

		/** The meta object literal for the '<em><b>Active State</b></em>' reference feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated */
		EReference BASIC_FB_TYPE_RUNTIME__ACTIVE_STATE = eINSTANCE.getBasicFBTypeRuntime_ActiveState();

		/** The meta object literal for the '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.FBTypeRuntimeImpl
		 * <em>FB Type Runtime</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.FBTypeRuntimeImpl
		 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.OperationalSemanticsPackageImpl#getFBTypeRuntime()
		 * @generated */
		EClass FB_TYPE_RUNTIME = eINSTANCE.getFBTypeRuntime();

		/** The meta object literal for the '<em><b>Fbtype</b></em>' containment reference feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 *
		 * @generated */
		EReference FB_TYPE_RUNTIME__FBTYPE = eINSTANCE.getFBTypeRuntime_Fbtype();

		/** The meta object literal for the '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.TransactionImpl
		 * <em>Transaction</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.TransactionImpl
		 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.OperationalSemanticsPackageImpl#getTransaction()
		 * @generated */
		EClass TRANSACTION = eINSTANCE.getTransaction();

		/** The meta object literal for the '<em><b>Input Event Occurrence</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated */
		EReference TRANSACTION__INPUT_EVENT_OCCURRENCE = eINSTANCE.getTransaction_InputEventOccurrence();

		/** The meta object literal for the '<em><b>Output Event Occurences</b></em>' containment reference list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated */
		EReference TRANSACTION__OUTPUT_EVENT_OCCURENCES = eINSTANCE.getTransaction_OutputEventOccurences();

	}

} // OperationalSemanticsPackage
