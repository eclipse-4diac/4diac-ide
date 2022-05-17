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
package org.eclipse.fordiac.ide.fb.interpreter.OpSem;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains
 * accessors for the meta objects to represent
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
 * @generated
 */
public interface OperationalSemanticsPackage extends EPackage {
	/**
	 * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "OpSem"; //$NON-NLS-1$

	/**
	 * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://OperationalSemantics/1.0"; //$NON-NLS-1$

	/**
	 * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "opsem"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 */
	OperationalSemanticsPackage eINSTANCE = org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.OperationalSemanticsPackageImpl
			.init();

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.EventOccurrenceImpl
	 * <em>Event Occurrence</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.EventOccurrenceImpl
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.OperationalSemanticsPackageImpl#getEventOccurrence()
	 * @generated
	 */
	int EVENT_OCCURRENCE = 0;

	/**
	 * The feature id for the '<em><b>Event</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int EVENT_OCCURRENCE__EVENT = 0;

	/**
	 * The feature id for the '<em><b>Active</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int EVENT_OCCURRENCE__ACTIVE = 1;

	/**
	 * The feature id for the '<em><b>Ignored</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int EVENT_OCCURRENCE__IGNORED = 2;

	/**
	 * The feature id for the '<em><b>Fb Runtime</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int EVENT_OCCURRENCE__FB_RUNTIME = 3;

	/**
	 * The feature id for the '<em><b>Created Transactions</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int EVENT_OCCURRENCE__CREATED_TRANSACTIONS = 4;

	/**
	 * The feature id for the '<em><b>Parent FB</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int EVENT_OCCURRENCE__PARENT_FB = 5;

	/**
	 * The number of structural features of the '<em>Event Occurrence</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int EVENT_OCCURRENCE_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.EventManagerImpl
	 * <em>Event Manager</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.EventManagerImpl
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.OperationalSemanticsPackageImpl#getEventManager()
	 * @generated
	 */
	int EVENT_MANAGER = 1;

	/**
	 * The feature id for the '<em><b>Transactions</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int EVENT_MANAGER__TRANSACTIONS = 0;

	/**
	 * The feature id for the '<em><b>Ready Queue</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int EVENT_MANAGER__READY_QUEUE = 1;

	/**
	 * The number of structural features of the '<em>Event Manager</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int EVENT_MANAGER_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.FBRuntimeAbstractImpl
	 * <em>FB Runtime Abstract</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.FBRuntimeAbstractImpl
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.OperationalSemanticsPackageImpl#getFBRuntimeAbstract()
	 * @generated
	 */
	int FB_RUNTIME_ABSTRACT = 2;

	/**
	 * The number of structural features of the '<em>FB Runtime Abstract</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int FB_RUNTIME_ABSTRACT_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.BasicFBTypeRuntimeImpl
	 * <em>Basic FB Type Runtime</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.BasicFBTypeRuntimeImpl
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.OperationalSemanticsPackageImpl#getBasicFBTypeRuntime()
	 * @generated
	 */
	int BASIC_FB_TYPE_RUNTIME = 3;

	/**
	 * The feature id for the '<em><b>Basicfbtype</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int BASIC_FB_TYPE_RUNTIME__BASICFBTYPE = FB_RUNTIME_ABSTRACT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Active State</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int BASIC_FB_TYPE_RUNTIME__ACTIVE_STATE = FB_RUNTIME_ABSTRACT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Basic FB Type Runtime</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int BASIC_FB_TYPE_RUNTIME_FEATURE_COUNT = FB_RUNTIME_ABSTRACT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.SimpleFBTypeRuntimeImpl
	 * <em>Simple FB Type Runtime</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.SimpleFBTypeRuntimeImpl
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.OperationalSemanticsPackageImpl#getSimpleFBTypeRuntime()
	 * @generated
	 */
	int SIMPLE_FB_TYPE_RUNTIME = 4;

	/**
	 * The feature id for the '<em><b>Simple FB Type</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SIMPLE_FB_TYPE_RUNTIME__SIMPLE_FB_TYPE = FB_RUNTIME_ABSTRACT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Simple FB Type Runtime</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SIMPLE_FB_TYPE_RUNTIME_FEATURE_COUNT = FB_RUNTIME_ABSTRACT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.TransactionImpl
	 * <em>Transaction</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.TransactionImpl
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.OperationalSemanticsPackageImpl#getTransaction()
	 * @generated
	 */
	int TRANSACTION = 5;

	/**
	 * The feature id for the '<em><b>Input Event Occurrence</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRANSACTION__INPUT_EVENT_OCCURRENCE = 0;

	/**
	 * The number of structural features of the '<em>Transaction</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRANSACTION_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.FBNetworkRuntimeImpl
	 * <em>FB Network Runtime</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.FBNetworkRuntimeImpl
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.OperationalSemanticsPackageImpl#getFBNetworkRuntime()
	 * @generated
	 */
	int FB_NETWORK_RUNTIME = 6;

	/**
	 * The feature id for the '<em><b>Fbnetwork</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int FB_NETWORK_RUNTIME__FBNETWORK = FB_RUNTIME_ABSTRACT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Fb Runtimes</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int FB_NETWORK_RUNTIME__FB_RUNTIMES = FB_RUNTIME_ABSTRACT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Transfer Data</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int FB_NETWORK_RUNTIME__TRANSFER_DATA = FB_RUNTIME_ABSTRACT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>FB Network Runtime</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int FB_NETWORK_RUNTIME_FEATURE_COUNT = FB_RUNTIME_ABSTRACT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.TransferDataImpl
	 * <em>Transfer Data</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.TransferDataImpl
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.OperationalSemanticsPackageImpl#getTransferData()
	 * @generated
	 */
	int TRANSFER_DATA = 7;

	/**
	 * The feature id for the '<em><b>Connection</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRANSFER_DATA__CONNECTION = 0;

	/**
	 * The feature id for the '<em><b>Current Value</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRANSFER_DATA__CURRENT_VALUE = 1;

	/**
	 * The number of structural features of the '<em>Transfer Data</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TRANSFER_DATA_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.FBTransactionImpl
	 * <em>FB Transaction</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.FBTransactionImpl
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.OperationalSemanticsPackageImpl#getFBTransaction()
	 * @generated
	 */
	int FB_TRANSACTION = 8;

	/**
	 * The feature id for the '<em><b>Input Event Occurrence</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int FB_TRANSACTION__INPUT_EVENT_OCCURRENCE = TRANSACTION__INPUT_EVENT_OCCURRENCE;

	/**
	 * The feature id for the '<em><b>Output Event Occurrences</b></em>' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int FB_TRANSACTION__OUTPUT_EVENT_OCCURRENCES = TRANSACTION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>FB Transaction</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int FB_TRANSACTION_FEATURE_COUNT = TRANSACTION_FEATURE_COUNT + 1;

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence
	 * <em>Event Occurrence</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Event Occurrence</em>'.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence
	 * @generated
	 */
	EClass getEventOccurrence();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence#getEvent
	 * <em>Event</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Event</em>'.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence#getEvent()
	 * @see #getEventOccurrence()
	 * @generated
	 */
	EReference getEventOccurrence_Event();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence#isActive
	 * <em>Active</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Active</em>'.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence#isActive()
	 * @see #getEventOccurrence()
	 * @generated
	 */
	EAttribute getEventOccurrence_Active();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence#isIgnored
	 * <em>Ignored</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Ignored</em>'.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence#isIgnored()
	 * @see #getEventOccurrence()
	 * @generated
	 */
	EAttribute getEventOccurrence_Ignored();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence#getFbRuntime
	 * <em>Fb Runtime</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Fb Runtime</em>'.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence#getFbRuntime()
	 * @see #getEventOccurrence()
	 * @generated
	 */
	EReference getEventOccurrence_FbRuntime();

	/**
	 * Returns the meta object for the reference list
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence#getCreatedTransactions
	 * <em>Created Transactions</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @return the meta object for the reference list '<em>Created
	 *         Transactions</em>'.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence#getCreatedTransactions()
	 * @see #getEventOccurrence()
	 * @generated
	 */
	EReference getEventOccurrence_CreatedTransactions();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence#getParentFB
	 * <em>Parent FB</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Parent FB</em>'.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence#getParentFB()
	 * @see #getEventOccurrence()
	 * @generated
	 */
	EReference getEventOccurrence_ParentFB();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager <em>Event
	 * Manager</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Event Manager</em>'.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager
	 * @generated
	 */
	EClass getEventManager();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager#getTransactions
	 * <em>Transactions</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list
	 *         '<em>Transactions</em>'.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager#getTransactions()
	 * @see #getEventManager()
	 * @generated
	 */
	EReference getEventManager_Transactions();

	/**
	 * Returns the meta object for the reference list
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager#getReadyQueue
	 * <em>Ready Queue</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Ready Queue</em>'.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager#getReadyQueue()
	 * @see #getEventManager()
	 * @generated
	 */
	EReference getEventManager_ReadyQueue();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract <em>FB
	 * Runtime Abstract</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>FB Runtime Abstract</em>'.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract
	 * @generated
	 */
	EClass getFBRuntimeAbstract();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime
	 * <em>Basic FB Type Runtime</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @return the meta object for class '<em>Basic FB Type Runtime</em>'.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime
	 * @generated
	 */
	EClass getBasicFBTypeRuntime();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime#getBasicfbtype
	 * <em>Basicfbtype</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Basicfbtype</em>'.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime#getBasicfbtype()
	 * @see #getBasicFBTypeRuntime()
	 * @generated
	 */
	EReference getBasicFBTypeRuntime_Basicfbtype();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime#getActiveState
	 * <em>Active State</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Active State</em>'.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime#getActiveState()
	 * @see #getBasicFBTypeRuntime()
	 * @generated
	 */
	EReference getBasicFBTypeRuntime_ActiveState();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.SimpleFBTypeRuntime
	 * <em>Simple FB Type Runtime</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @return the meta object for class '<em>Simple FB Type Runtime</em>'.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.SimpleFBTypeRuntime
	 * @generated
	 */
	EClass getSimpleFBTypeRuntime();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.SimpleFBTypeRuntime#getSimpleFBType
	 * <em>Simple FB Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Simple FB
	 *         Type</em>'.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.SimpleFBTypeRuntime#getSimpleFBType()
	 * @see #getSimpleFBTypeRuntime()
	 * @generated
	 */
	EReference getSimpleFBTypeRuntime_SimpleFBType();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction
	 * <em>Transaction</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Transaction</em>'.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction
	 * @generated
	 */
	EClass getTransaction();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction#getInputEventOccurrence
	 * <em>Input Event Occurrence</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @return the meta object for the containment reference '<em>Input Event
	 *         Occurrence</em>'.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction#getInputEventOccurrence()
	 * @see #getTransaction()
	 * @generated
	 */
	EReference getTransaction_InputEventOccurrence();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime <em>FB
	 * Network Runtime</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>FB Network Runtime</em>'.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime
	 * @generated
	 */
	EClass getFBNetworkRuntime();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime#getFbnetwork
	 * <em>Fbnetwork</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Fbnetwork</em>'.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime#getFbnetwork()
	 * @see #getFBNetworkRuntime()
	 * @generated
	 */
	EReference getFBNetworkRuntime_Fbnetwork();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime#getFbRuntimes
	 * <em>Fb Runtimes</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Fb
	 *         Runtimes</em>'.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime#getFbRuntimes()
	 * @see #getFBNetworkRuntime()
	 * @generated
	 */
	EReference getFBNetworkRuntime_FbRuntimes();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime#getTransferData
	 * <em>Transfer Data</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Transfer
	 *         Data</em>'.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime#getTransferData()
	 * @see #getFBNetworkRuntime()
	 * @generated
	 */
	EReference getFBNetworkRuntime_TransferData();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.TransferData
	 * <em>Transfer Data</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Transfer Data</em>'.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.TransferData
	 * @generated
	 */
	EClass getTransferData();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.TransferData#getConnection
	 * <em>Connection</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Connection</em>'.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.TransferData#getConnection()
	 * @see #getTransferData()
	 * @generated
	 */
	EReference getTransferData_Connection();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.TransferData#getCurrentValue
	 * <em>Current Value</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Current
	 *         Value</em>'.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.TransferData#getCurrentValue()
	 * @see #getTransferData()
	 * @generated
	 */
	EReference getTransferData_CurrentValue();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction <em>FB
	 * Transaction</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>FB Transaction</em>'.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction
	 * @generated
	 */
	EClass getFBTransaction();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction#getOutputEventOccurrences
	 * <em>Output Event Occurrences</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Output Event
	 *         Occurrences</em>'.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction#getOutputEventOccurrences()
	 * @see #getFBTransaction()
	 * @generated
	 */
	EReference getFBTransaction_OutputEventOccurrences();

	/**
	 * Returns the factory that creates the instances of the model. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	OperationalSemanticsFactory getOperationalSemanticsFactory();

	/**
	 * <!-- begin-user-doc --> Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.EventOccurrenceImpl
		 * <em>Event Occurrence</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 *
		 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.EventOccurrenceImpl
		 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.OperationalSemanticsPackageImpl#getEventOccurrence()
		 * @generated
		 */
		EClass EVENT_OCCURRENCE = eINSTANCE.getEventOccurrence();

		/**
		 * The meta object literal for the '<em><b>Event</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference EVENT_OCCURRENCE__EVENT = eINSTANCE.getEventOccurrence_Event();

		/**
		 * The meta object literal for the '<em><b>Active</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute EVENT_OCCURRENCE__ACTIVE = eINSTANCE.getEventOccurrence_Active();

		/**
		 * The meta object literal for the '<em><b>Ignored</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute EVENT_OCCURRENCE__IGNORED = eINSTANCE.getEventOccurrence_Ignored();

		/**
		 * The meta object literal for the '<em><b>Fb Runtime</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference EVENT_OCCURRENCE__FB_RUNTIME = eINSTANCE.getEventOccurrence_FbRuntime();

		/**
		 * The meta object literal for the '<em><b>Created Transactions</b></em>'
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference EVENT_OCCURRENCE__CREATED_TRANSACTIONS = eINSTANCE.getEventOccurrence_CreatedTransactions();

		/**
		 * The meta object literal for the '<em><b>Parent FB</b></em>' reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference EVENT_OCCURRENCE__PARENT_FB = eINSTANCE.getEventOccurrence_ParentFB();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.EventManagerImpl
		 * <em>Event Manager</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.EventManagerImpl
		 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.OperationalSemanticsPackageImpl#getEventManager()
		 * @generated
		 */
		EClass EVENT_MANAGER = eINSTANCE.getEventManager();

		/**
		 * The meta object literal for the '<em><b>Transactions</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference EVENT_MANAGER__TRANSACTIONS = eINSTANCE.getEventManager_Transactions();

		/**
		 * The meta object literal for the '<em><b>Ready Queue</b></em>' reference list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference EVENT_MANAGER__READY_QUEUE = eINSTANCE.getEventManager_ReadyQueue();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.FBRuntimeAbstractImpl
		 * <em>FB Runtime Abstract</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 *
		 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.FBRuntimeAbstractImpl
		 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.OperationalSemanticsPackageImpl#getFBRuntimeAbstract()
		 * @generated
		 */
		EClass FB_RUNTIME_ABSTRACT = eINSTANCE.getFBRuntimeAbstract();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.BasicFBTypeRuntimeImpl
		 * <em>Basic FB Type Runtime</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 *
		 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.BasicFBTypeRuntimeImpl
		 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.OperationalSemanticsPackageImpl#getBasicFBTypeRuntime()
		 * @generated
		 */
		EClass BASIC_FB_TYPE_RUNTIME = eINSTANCE.getBasicFBTypeRuntime();

		/**
		 * The meta object literal for the '<em><b>Basicfbtype</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference BASIC_FB_TYPE_RUNTIME__BASICFBTYPE = eINSTANCE.getBasicFBTypeRuntime_Basicfbtype();

		/**
		 * The meta object literal for the '<em><b>Active State</b></em>' reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference BASIC_FB_TYPE_RUNTIME__ACTIVE_STATE = eINSTANCE.getBasicFBTypeRuntime_ActiveState();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.SimpleFBTypeRuntimeImpl
		 * <em>Simple FB Type Runtime</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 *
		 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.SimpleFBTypeRuntimeImpl
		 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.OperationalSemanticsPackageImpl#getSimpleFBTypeRuntime()
		 * @generated
		 */
		EClass SIMPLE_FB_TYPE_RUNTIME = eINSTANCE.getSimpleFBTypeRuntime();

		/**
		 * The meta object literal for the '<em><b>Simple FB Type</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference SIMPLE_FB_TYPE_RUNTIME__SIMPLE_FB_TYPE = eINSTANCE.getSimpleFBTypeRuntime_SimpleFBType();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.TransactionImpl
		 * <em>Transaction</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.TransactionImpl
		 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.OperationalSemanticsPackageImpl#getTransaction()
		 * @generated
		 */
		EClass TRANSACTION = eINSTANCE.getTransaction();

		/**
		 * The meta object literal for the '<em><b>Input Event Occurrence</b></em>'
		 * containment reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference TRANSACTION__INPUT_EVENT_OCCURRENCE = eINSTANCE.getTransaction_InputEventOccurrence();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.FBNetworkRuntimeImpl
		 * <em>FB Network Runtime</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 *
		 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.FBNetworkRuntimeImpl
		 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.OperationalSemanticsPackageImpl#getFBNetworkRuntime()
		 * @generated
		 */
		EClass FB_NETWORK_RUNTIME = eINSTANCE.getFBNetworkRuntime();

		/**
		 * The meta object literal for the '<em><b>Fbnetwork</b></em>' reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference FB_NETWORK_RUNTIME__FBNETWORK = eINSTANCE.getFBNetworkRuntime_Fbnetwork();

		/**
		 * The meta object literal for the '<em><b>Fb Runtimes</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference FB_NETWORK_RUNTIME__FB_RUNTIMES = eINSTANCE.getFBNetworkRuntime_FbRuntimes();

		/**
		 * The meta object literal for the '<em><b>Transfer Data</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference FB_NETWORK_RUNTIME__TRANSFER_DATA = eINSTANCE.getFBNetworkRuntime_TransferData();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.TransferDataImpl
		 * <em>Transfer Data</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.TransferDataImpl
		 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.OperationalSemanticsPackageImpl#getTransferData()
		 * @generated
		 */
		EClass TRANSFER_DATA = eINSTANCE.getTransferData();

		/**
		 * The meta object literal for the '<em><b>Connection</b></em>' reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference TRANSFER_DATA__CONNECTION = eINSTANCE.getTransferData_Connection();

		/**
		 * The meta object literal for the '<em><b>Current Value</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference TRANSFER_DATA__CURRENT_VALUE = eINSTANCE.getTransferData_CurrentValue();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.FBTransactionImpl
		 * <em>FB Transaction</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 *
		 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.FBTransactionImpl
		 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.OperationalSemanticsPackageImpl#getFBTransaction()
		 * @generated
		 */
		EClass FB_TRANSACTION = eINSTANCE.getFBTransaction();

		/**
		 * The meta object literal for the '<em><b>Output Event Occurrences</b></em>'
		 * containment reference list feature. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 *
		 * @generated
		 */
		EReference FB_TRANSACTION__OUTPUT_EVENT_OCCURRENCES = eINSTANCE.getFBTransaction_OutputEventOccurrences();

	}

} // OperationalSemanticsPackage
