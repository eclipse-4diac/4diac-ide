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

import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsFactory;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.SimpleFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;
import org.eclipse.fordiac.ide.model.data.DataPackage;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;

/** <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 *
 * @generated */
public class OperationalSemanticsPackageImpl extends EPackageImpl implements OperationalSemanticsPackage {
	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass eventOccurrenceEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass eventManagerEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass fbRuntimeAbstractEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass basicFBTypeRuntimeEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass simpleFBTypeRuntimeEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass transactionEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass fbNetworkRuntimeEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass fbTransactionEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass connectionToValueMapEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass runtimeMapEClass = null;

	/** Creates an instance of the model <b>Package</b>, registered with {@link org.eclipse.emf.ecore.EPackage.Registry
	 * EPackage.Registry} by the package package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static factory method {@link #init init()}, which also
	 * performs initialization of the package, or returns the registered package, if one already exists. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage#eNS_URI
	 * @see #init()
	 * @generated */
	private OperationalSemanticsPackageImpl() {
		super(eNS_URI, OperationalSemanticsFactory.eINSTANCE);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private static boolean isInited = false;

	/** Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>
	 * This method is used to initialize {@link OperationalSemanticsPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated */
	public static OperationalSemanticsPackage init() {
		if (isInited)
			return (OperationalSemanticsPackage) EPackage.Registry.INSTANCE
					.getEPackage(OperationalSemanticsPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredOperationalSemanticsPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		OperationalSemanticsPackageImpl theOperationalSemanticsPackage = registeredOperationalSemanticsPackage instanceof OperationalSemanticsPackageImpl
				? (OperationalSemanticsPackageImpl) registeredOperationalSemanticsPackage
				: new OperationalSemanticsPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		LibraryElementPackage.eINSTANCE.eClass();
		DataPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theOperationalSemanticsPackage.createPackageContents();

		// Initialize created meta-data
		theOperationalSemanticsPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theOperationalSemanticsPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(OperationalSemanticsPackage.eNS_URI, theOperationalSemanticsPackage);
		return theOperationalSemanticsPackage;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getEventOccurrence() {
		return eventOccurrenceEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getEventOccurrence_Event() {
		return (EReference) eventOccurrenceEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getEventOccurrence_Active() {
		return (EAttribute) eventOccurrenceEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getEventOccurrence_Ignored() {
		return (EAttribute) eventOccurrenceEClass.getEStructuralFeatures().get(2);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getEventOccurrence_FbRuntime() {
		return (EReference) eventOccurrenceEClass.getEStructuralFeatures().get(3);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getEventOccurrence_CreatedTransactions() {
		return (EReference) eventOccurrenceEClass.getEStructuralFeatures().get(4);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getEventOccurrence_ParentFB() {
		return (EReference) eventOccurrenceEClass.getEStructuralFeatures().get(5);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getEventManager() {
		return eventManagerEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getEventManager_Transactions() {
		return (EReference) eventManagerEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getEventManager_ReadyQueue() {
		return (EReference) eventManagerEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getFBRuntimeAbstract() {
		return fbRuntimeAbstractEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getBasicFBTypeRuntime() {
		return basicFBTypeRuntimeEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getBasicFBTypeRuntime_Basicfbtype() {
		return (EReference) basicFBTypeRuntimeEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getBasicFBTypeRuntime_ActiveState() {
		return (EReference) basicFBTypeRuntimeEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getSimpleFBTypeRuntime() {
		return simpleFBTypeRuntimeEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getSimpleFBTypeRuntime_SimpleFBType() {
		return (EReference) simpleFBTypeRuntimeEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getTransaction() {
		return transactionEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getTransaction_InputEventOccurrence() {
		return (EReference) transactionEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getTransaction_ParentEO() {
		return (EReference) transactionEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getFBNetworkRuntime() {
		return fbNetworkRuntimeEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getFBNetworkRuntime_Fbnetwork() {
		return (EReference) fbNetworkRuntimeEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getFBNetworkRuntime_TransferData() {
		return (EReference) fbNetworkRuntimeEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getFBNetworkRuntime_TypeRuntimes() {
		return (EReference) fbNetworkRuntimeEClass.getEStructuralFeatures().get(2);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getFBTransaction() {
		return fbTransactionEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getFBTransaction_OutputEventOccurrences() {
		return (EReference) fbTransactionEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getFBTransaction_InputVariables() {
		return (EReference) fbTransactionEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getFBTransaction_ResultFBRuntime() {
		return (EReference) fbTransactionEClass.getEStructuralFeatures().get(2);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getConnectionToValueMap() {
		return connectionToValueMapEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getConnectionToValueMap_Key() {
		return (EReference) connectionToValueMapEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getConnectionToValueMap_Value() {
		return (EReference) connectionToValueMapEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getRuntimeMap() {
		return runtimeMapEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getRuntimeMap_Key() {
		return (EReference) runtimeMapEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getRuntimeMap_Value() {
		return (EReference) runtimeMapEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public OperationalSemanticsFactory getOperationalSemanticsFactory() {
		return (OperationalSemanticsFactory) getEFactoryInstance();
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private boolean isCreated = false;

	/** Creates the meta-model objects for the package. This method is guarded to have no affect on any invocation but
	 * its first. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		eventOccurrenceEClass = createEClass(EVENT_OCCURRENCE);
		createEReference(eventOccurrenceEClass, EVENT_OCCURRENCE__EVENT);
		createEAttribute(eventOccurrenceEClass, EVENT_OCCURRENCE__ACTIVE);
		createEAttribute(eventOccurrenceEClass, EVENT_OCCURRENCE__IGNORED);
		createEReference(eventOccurrenceEClass, EVENT_OCCURRENCE__FB_RUNTIME);
		createEReference(eventOccurrenceEClass, EVENT_OCCURRENCE__CREATED_TRANSACTIONS);
		createEReference(eventOccurrenceEClass, EVENT_OCCURRENCE__PARENT_FB);

		eventManagerEClass = createEClass(EVENT_MANAGER);
		createEReference(eventManagerEClass, EVENT_MANAGER__TRANSACTIONS);
		createEReference(eventManagerEClass, EVENT_MANAGER__READY_QUEUE);

		fbRuntimeAbstractEClass = createEClass(FB_RUNTIME_ABSTRACT);

		basicFBTypeRuntimeEClass = createEClass(BASIC_FB_TYPE_RUNTIME);
		createEReference(basicFBTypeRuntimeEClass, BASIC_FB_TYPE_RUNTIME__BASICFBTYPE);
		createEReference(basicFBTypeRuntimeEClass, BASIC_FB_TYPE_RUNTIME__ACTIVE_STATE);

		simpleFBTypeRuntimeEClass = createEClass(SIMPLE_FB_TYPE_RUNTIME);
		createEReference(simpleFBTypeRuntimeEClass, SIMPLE_FB_TYPE_RUNTIME__SIMPLE_FB_TYPE);

		transactionEClass = createEClass(TRANSACTION);
		createEReference(transactionEClass, TRANSACTION__INPUT_EVENT_OCCURRENCE);
		createEReference(transactionEClass, TRANSACTION__PARENT_EO);

		fbNetworkRuntimeEClass = createEClass(FB_NETWORK_RUNTIME);
		createEReference(fbNetworkRuntimeEClass, FB_NETWORK_RUNTIME__FBNETWORK);
		createEReference(fbNetworkRuntimeEClass, FB_NETWORK_RUNTIME__TRANSFER_DATA);
		createEReference(fbNetworkRuntimeEClass, FB_NETWORK_RUNTIME__TYPE_RUNTIMES);

		fbTransactionEClass = createEClass(FB_TRANSACTION);
		createEReference(fbTransactionEClass, FB_TRANSACTION__OUTPUT_EVENT_OCCURRENCES);
		createEReference(fbTransactionEClass, FB_TRANSACTION__INPUT_VARIABLES);
		createEReference(fbTransactionEClass, FB_TRANSACTION__RESULT_FB_RUNTIME);

		connectionToValueMapEClass = createEClass(CONNECTION_TO_VALUE_MAP);
		createEReference(connectionToValueMapEClass, CONNECTION_TO_VALUE_MAP__KEY);
		createEReference(connectionToValueMapEClass, CONNECTION_TO_VALUE_MAP__VALUE);

		runtimeMapEClass = createEClass(RUNTIME_MAP);
		createEReference(runtimeMapEClass, RUNTIME_MAP__KEY);
		createEReference(runtimeMapEClass, RUNTIME_MAP__VALUE);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private boolean isInitialized = false;

	/** Complete the initialization of the package and its meta-model. This method is guarded to have no affect on any
	 * invocation but its first. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		LibraryElementPackage theLibraryElementPackage = (LibraryElementPackage) EPackage.Registry.INSTANCE
				.getEPackage(LibraryElementPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		basicFBTypeRuntimeEClass.getESuperTypes().add(this.getFBRuntimeAbstract());
		simpleFBTypeRuntimeEClass.getESuperTypes().add(this.getFBRuntimeAbstract());
		fbNetworkRuntimeEClass.getESuperTypes().add(this.getFBRuntimeAbstract());
		fbTransactionEClass.getESuperTypes().add(this.getTransaction());

		// Initialize classes and features; add operations and parameters
		initEClass(eventOccurrenceEClass, EventOccurrence.class, "EventOccurrence", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEventOccurrence_Event(), theLibraryElementPackage.getEvent(), null, "event", null, 1, 1, //$NON-NLS-1$
				EventOccurrence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEventOccurrence_Active(), ecorePackage.getEBoolean(), "active", "true", 0, 1, //$NON-NLS-1$ //$NON-NLS-2$
				EventOccurrence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getEventOccurrence_Ignored(), ecorePackage.getEBoolean(), "ignored", null, 0, 1, //$NON-NLS-1$
				EventOccurrence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getEventOccurrence_FbRuntime(), this.getFBRuntimeAbstract(), null, "fbRuntime", null, 1, 1, //$NON-NLS-1$
				EventOccurrence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEventOccurrence_CreatedTransactions(), this.getTransaction(), this.getTransaction_ParentEO(),
				"createdTransactions", null, 0, -1, EventOccurrence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, //$NON-NLS-1$
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEventOccurrence_ParentFB(), theLibraryElementPackage.getFBNetworkElement(), null, "parentFB", //$NON-NLS-1$
				null, 0, 1, EventOccurrence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eventManagerEClass, EventManager.class, "EventManager", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEventManager_Transactions(), this.getTransaction(), null, "transactions", null, 0, -1, //$NON-NLS-1$
				EventManager.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEventManager_ReadyQueue(), this.getTransaction(), null, "readyQueue", null, 0, -1, //$NON-NLS-1$
				EventManager.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		EOperation op = addEOperation(eventManagerEClass, null, "process", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getEventManager(), "eventManager", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(fbRuntimeAbstractEClass, FBRuntimeAbstract.class, "FBRuntimeAbstract", IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);

		addEOperation(fbRuntimeAbstractEClass, this.getEventOccurrence(), "run", 0, -1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(fbRuntimeAbstractEClass, ecorePackage.getEObject(), "getModel", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(basicFBTypeRuntimeEClass, BasicFBTypeRuntime.class, "BasicFBTypeRuntime", !IS_ABSTRACT, //$NON-NLS-1$
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBasicFBTypeRuntime_Basicfbtype(), theLibraryElementPackage.getBasicFBType(), null,
				"basicfbtype", null, 1, 1, BasicFBTypeRuntime.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, //$NON-NLS-1$
				IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBasicFBTypeRuntime_ActiveState(), theLibraryElementPackage.getECState(), null, "activeState", //$NON-NLS-1$
				null, 0, 1, BasicFBTypeRuntime.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(basicFBTypeRuntimeEClass, theLibraryElementPackage.getBasicFBType(), "getModel", 1, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);

		initEClass(simpleFBTypeRuntimeEClass, SimpleFBTypeRuntime.class, "SimpleFBTypeRuntime", !IS_ABSTRACT, //$NON-NLS-1$
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSimpleFBTypeRuntime_SimpleFBType(), theLibraryElementPackage.getSimpleFBType(), null,
				"simpleFBType", null, 1, 1, SimpleFBTypeRuntime.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, //$NON-NLS-1$
				IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(simpleFBTypeRuntimeEClass, theLibraryElementPackage.getSimpleFBType(), "getModel", 1, 1, //$NON-NLS-1$
				IS_UNIQUE, IS_ORDERED);

		initEClass(transactionEClass, Transaction.class, "Transaction", IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTransaction_InputEventOccurrence(), this.getEventOccurrence(), null, "inputEventOccurrence", //$NON-NLS-1$
				null, 1, 1, Transaction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTransaction_ParentEO(), this.getEventOccurrence(),
				this.getEventOccurrence_CreatedTransactions(), "parentEO", null, 0, 1, Transaction.class, !IS_TRANSIENT, //$NON-NLS-1$
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(fbNetworkRuntimeEClass, FBNetworkRuntime.class, "FBNetworkRuntime", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFBNetworkRuntime_Fbnetwork(), theLibraryElementPackage.getFBNetwork(), null, "fbnetwork", //$NON-NLS-1$
				null, 1, 1, FBNetworkRuntime.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFBNetworkRuntime_TransferData(), this.getConnectionToValueMap(), null, "transferData", null, //$NON-NLS-1$
				0, -1, FBNetworkRuntime.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFBNetworkRuntime_TypeRuntimes(), this.getRuntimeMap(), null, "typeRuntimes", null, 0, -1, //$NON-NLS-1$
				FBNetworkRuntime.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(fbNetworkRuntimeEClass, theLibraryElementPackage.getFBNetwork(), "getModel", 1, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);

		initEClass(fbTransactionEClass, FBTransaction.class, "FBTransaction", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFBTransaction_OutputEventOccurrences(), this.getEventOccurrence(), null,
				"outputEventOccurrences", null, 0, -1, FBTransaction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, //$NON-NLS-1$
				IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFBTransaction_InputVariables(), theLibraryElementPackage.getVarDeclaration(), null,
				"inputVariables", null, 0, -1, FBTransaction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, //$NON-NLS-1$
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFBTransaction_ResultFBRuntime(), this.getFBRuntimeAbstract(), null, "resultFBRuntime", null, //$NON-NLS-1$
				0, 1, FBTransaction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(connectionToValueMapEClass, Map.Entry.class, "ConnectionToValueMap", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				!IS_GENERATED_INSTANCE_CLASS);
		initEReference(getConnectionToValueMap_Key(), theLibraryElementPackage.getConnection(), null, "key", null, 0, 1, //$NON-NLS-1$
				Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConnectionToValueMap_Value(), theLibraryElementPackage.getValue(), null, "value", null, 0, 1, //$NON-NLS-1$
				Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(runtimeMapEClass, Map.Entry.class, "RuntimeMap", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				!IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRuntimeMap_Key(), theLibraryElementPackage.getFBNetworkElement(), null, "key", null, 0, 1, //$NON-NLS-1$
				Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRuntimeMap_Value(), this.getFBRuntimeAbstract(), null, "value", null, 0, 1, Map.Entry.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} // OperationalSemanticsPackageImpl
