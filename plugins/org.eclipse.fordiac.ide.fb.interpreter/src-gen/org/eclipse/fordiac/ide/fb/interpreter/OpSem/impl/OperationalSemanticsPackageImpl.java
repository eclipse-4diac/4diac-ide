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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsFactory;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;
import org.eclipse.fordiac.ide.model.Palette.PalettePackage;
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
	private EClass fbTypeRuntimeEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass transactionEClass = null;

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
		PalettePackage.eINSTANCE.eClass();
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
	public EClass getFBTypeRuntime() {
		return fbTypeRuntimeEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getFBTypeRuntime_Fbtype() {
		return (EReference) fbTypeRuntimeEClass.getEStructuralFeatures().get(0);
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
	public EReference getTransaction_OutputEventOccurences() {
		return (EReference) transactionEClass.getEStructuralFeatures().get(1);
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

		eventManagerEClass = createEClass(EVENT_MANAGER);
		createEReference(eventManagerEClass, EVENT_MANAGER__TRANSACTIONS);

		fbRuntimeAbstractEClass = createEClass(FB_RUNTIME_ABSTRACT);

		basicFBTypeRuntimeEClass = createEClass(BASIC_FB_TYPE_RUNTIME);
		createEReference(basicFBTypeRuntimeEClass, BASIC_FB_TYPE_RUNTIME__BASICFBTYPE);
		createEReference(basicFBTypeRuntimeEClass, BASIC_FB_TYPE_RUNTIME__ACTIVE_STATE);

		fbTypeRuntimeEClass = createEClass(FB_TYPE_RUNTIME);
		createEReference(fbTypeRuntimeEClass, FB_TYPE_RUNTIME__FBTYPE);

		transactionEClass = createEClass(TRANSACTION);
		createEReference(transactionEClass, TRANSACTION__INPUT_EVENT_OCCURRENCE);
		createEReference(transactionEClass, TRANSACTION__OUTPUT_EVENT_OCCURENCES);
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
		fbTypeRuntimeEClass.getESuperTypes().add(this.getFBRuntimeAbstract());

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

		initEClass(eventManagerEClass, EventManager.class, "EventManager", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEventManager_Transactions(), this.getTransaction(), null, "transactions", null, 0, -1, //$NON-NLS-1$
				EventManager.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		EOperation op = addEOperation(eventManagerEClass, null, "process", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getEventManager(), "eventManager", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(fbRuntimeAbstractEClass, FBRuntimeAbstract.class, "FBRuntimeAbstract", IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);

		addEOperation(fbRuntimeAbstractEClass, this.getEventOccurrence(), "run", 0, -1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(basicFBTypeRuntimeEClass, BasicFBTypeRuntime.class, "BasicFBTypeRuntime", !IS_ABSTRACT, //$NON-NLS-1$
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBasicFBTypeRuntime_Basicfbtype(), theLibraryElementPackage.getBasicFBType(), null,
				"basicfbtype", null, 1, 1, BasicFBTypeRuntime.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, //$NON-NLS-1$
				IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBasicFBTypeRuntime_ActiveState(), theLibraryElementPackage.getECState(), null, "activeState", //$NON-NLS-1$
				null, 0, 1, BasicFBTypeRuntime.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(fbTypeRuntimeEClass, FBTypeRuntime.class, "FBTypeRuntime", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFBTypeRuntime_Fbtype(), theLibraryElementPackage.getFBType(), null, "fbtype", null, 1, 1, //$NON-NLS-1$
				FBTypeRuntime.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(transactionEClass, Transaction.class, "Transaction", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTransaction_InputEventOccurrence(), this.getEventOccurrence(), null, "inputEventOccurrence", //$NON-NLS-1$
				null, 1, 1, Transaction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTransaction_OutputEventOccurences(), this.getEventOccurrence(), null, "outputEventOccurences", //$NON-NLS-1$
				null, 0, -1, Transaction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} // OperationalSemanticsPackageImpl
