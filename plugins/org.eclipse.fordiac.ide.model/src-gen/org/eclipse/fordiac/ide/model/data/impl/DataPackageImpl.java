/********************************************************************************
 * Copyright (c) 2008, 2010, 2012 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Gerhard Ebenhofer, Alois Zoitl, Monika Wenger, Martin Jobst
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.data.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.fordiac.ide.model.Palette.PalettePackage;
import org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl;
import org.eclipse.fordiac.ide.model.data.ArrayType;
import org.eclipse.fordiac.ide.model.data.BaseType1;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.DataPackage;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.DerivedType;
import org.eclipse.fordiac.ide.model.data.DirectlyDerivedType;
import org.eclipse.fordiac.ide.model.data.ElementaryType;
import org.eclipse.fordiac.ide.model.data.EnumeratedType;
import org.eclipse.fordiac.ide.model.data.EnumeratedValue;
import org.eclipse.fordiac.ide.model.data.EventType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.data.Subrange;
import org.eclipse.fordiac.ide.model.data.SubrangeType;
import org.eclipse.fordiac.ide.model.data.ValueType;
import org.eclipse.fordiac.ide.model.data.VarInitialization;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DataPackageImpl extends EPackageImpl implements DataPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass arrayTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dataTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass directlyDerivedTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass enumeratedTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass enumeratedValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass structuredTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass subrangeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass subrangeTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass varInitializationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass valueTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass elementaryTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass derivedTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eventTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum baseType1EEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.fordiac.ide.model.data.DataPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private DataPackageImpl() {
		super(eNS_URI, DataFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link DataPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static DataPackage init() {
		if (isInited) return (DataPackage)EPackage.Registry.INSTANCE.getEPackage(DataPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredDataPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		DataPackageImpl theDataPackage = registeredDataPackage instanceof DataPackageImpl ? (DataPackageImpl)registeredDataPackage : new DataPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		XMLTypePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(PalettePackage.eNS_URI);
		PalettePackageImpl thePalettePackage = (PalettePackageImpl)(registeredPackage instanceof PalettePackageImpl ? registeredPackage : PalettePackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(LibraryElementPackage.eNS_URI);
		LibraryElementPackageImpl theLibraryElementPackage = (LibraryElementPackageImpl)(registeredPackage instanceof LibraryElementPackageImpl ? registeredPackage : LibraryElementPackage.eINSTANCE);

		// Create package meta-data objects
		theDataPackage.createPackageContents();
		thePalettePackage.createPackageContents();
		theLibraryElementPackage.createPackageContents();

		// Initialize created meta-data
		theDataPackage.initializePackageContents();
		thePalettePackage.initializePackageContents();
		theLibraryElementPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theDataPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(DataPackage.eNS_URI, theDataPackage);
		return theDataPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getArrayType() {
		return arrayTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getArrayType_Subranges() {
		return (EReference)arrayTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getArrayType_InitialValues() {
		return (EAttribute)arrayTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getArrayType_BaseType() {
		return (EReference)arrayTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDataType() {
		return dataTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDirectlyDerivedType() {
		return directlyDerivedTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getEnumeratedType() {
		return enumeratedTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getEnumeratedType_EnumeratedValue() {
		return (EReference)enumeratedTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getEnumeratedValue() {
		return enumeratedValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getEnumeratedValue_Comment() {
		return (EAttribute)enumeratedValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getEnumeratedValue_Name() {
		return (EAttribute)enumeratedValueEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getStructuredType() {
		return structuredTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getStructuredType_VarDeclaration() {
		return (EReference)structuredTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSubrange() {
		return subrangeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSubrange_LowerLimit() {
		return (EAttribute)subrangeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSubrange_UpperLimit() {
		return (EAttribute)subrangeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSubrangeType() {
		return subrangeTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSubrangeType_Subrange() {
		return (EReference)subrangeTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getVarInitialization() {
		return varInitializationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVarInitialization_InitialValue() {
		return (EAttribute)varInitializationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getValueType() {
		return valueTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getElementaryType() {
		return elementaryTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDerivedType() {
		return derivedTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDerivedType_BaseType() {
		return (EReference)derivedTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getEventType() {
		return eventTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getBaseType1() {
		return baseType1EEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DataFactory getDataFactory() {
		return (DataFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		arrayTypeEClass = createEClass(ARRAY_TYPE);
		createEReference(arrayTypeEClass, ARRAY_TYPE__SUBRANGES);
		createEAttribute(arrayTypeEClass, ARRAY_TYPE__INITIAL_VALUES);
		createEReference(arrayTypeEClass, ARRAY_TYPE__BASE_TYPE);

		dataTypeEClass = createEClass(DATA_TYPE);

		directlyDerivedTypeEClass = createEClass(DIRECTLY_DERIVED_TYPE);

		enumeratedTypeEClass = createEClass(ENUMERATED_TYPE);
		createEReference(enumeratedTypeEClass, ENUMERATED_TYPE__ENUMERATED_VALUE);

		enumeratedValueEClass = createEClass(ENUMERATED_VALUE);
		createEAttribute(enumeratedValueEClass, ENUMERATED_VALUE__COMMENT);
		createEAttribute(enumeratedValueEClass, ENUMERATED_VALUE__NAME);

		structuredTypeEClass = createEClass(STRUCTURED_TYPE);
		createEReference(structuredTypeEClass, STRUCTURED_TYPE__VAR_DECLARATION);

		subrangeEClass = createEClass(SUBRANGE);
		createEAttribute(subrangeEClass, SUBRANGE__LOWER_LIMIT);
		createEAttribute(subrangeEClass, SUBRANGE__UPPER_LIMIT);

		subrangeTypeEClass = createEClass(SUBRANGE_TYPE);
		createEReference(subrangeTypeEClass, SUBRANGE_TYPE__SUBRANGE);

		varInitializationEClass = createEClass(VAR_INITIALIZATION);
		createEAttribute(varInitializationEClass, VAR_INITIALIZATION__INITIAL_VALUE);

		valueTypeEClass = createEClass(VALUE_TYPE);

		elementaryTypeEClass = createEClass(ELEMENTARY_TYPE);

		derivedTypeEClass = createEClass(DERIVED_TYPE);
		createEReference(derivedTypeEClass, DERIVED_TYPE__BASE_TYPE);

		eventTypeEClass = createEClass(EVENT_TYPE);

		// Create enums
		baseType1EEnum = createEEnum(BASE_TYPE1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		XMLTypePackage theXMLTypePackage = (XMLTypePackage)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);
		LibraryElementPackage theLibraryElementPackage = (LibraryElementPackage)EPackage.Registry.INSTANCE.getEPackage(LibraryElementPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		arrayTypeEClass.getESuperTypes().add(this.getDataType());
		dataTypeEClass.getESuperTypes().add(theLibraryElementPackage.getLibraryElement());
		directlyDerivedTypeEClass.getESuperTypes().add(this.getDerivedType());
		enumeratedTypeEClass.getESuperTypes().add(this.getValueType());
		structuredTypeEClass.getESuperTypes().add(this.getDataType());
		subrangeTypeEClass.getESuperTypes().add(this.getDerivedType());
		valueTypeEClass.getESuperTypes().add(this.getDataType());
		elementaryTypeEClass.getESuperTypes().add(this.getValueType());
		derivedTypeEClass.getESuperTypes().add(this.getValueType());
		eventTypeEClass.getESuperTypes().add(this.getDataType());

		// Initialize classes and features; add operations and parameters
		initEClass(arrayTypeEClass, ArrayType.class, "ArrayType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getArrayType_Subranges(), this.getSubrange(), null, "subranges", null, 1, -1, ArrayType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getArrayType_InitialValues(), theXMLTypePackage.getString(), "initialValues", null, 0, 1, ArrayType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getArrayType_BaseType(), this.getDataType(), null, "baseType", null, 0, 1, ArrayType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(dataTypeEClass, DataType.class, "DataType", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(directlyDerivedTypeEClass, DirectlyDerivedType.class, "DirectlyDerivedType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(enumeratedTypeEClass, EnumeratedType.class, "EnumeratedType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getEnumeratedType_EnumeratedValue(), this.getEnumeratedValue(), null, "enumeratedValue", null, 1, -1, EnumeratedType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(enumeratedValueEClass, EnumeratedValue.class, "EnumeratedValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getEnumeratedValue_Comment(), theXMLTypePackage.getString(), "comment", null, 0, 1, EnumeratedValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getEnumeratedValue_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, EnumeratedValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(structuredTypeEClass, StructuredType.class, "StructuredType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getStructuredType_VarDeclaration(), theLibraryElementPackage.getVarDeclaration(), null, "varDeclaration", null, 0, -1, StructuredType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(subrangeEClass, Subrange.class, "Subrange", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getSubrange_LowerLimit(), theXMLTypePackage.getInt(), "lowerLimit", null, 1, 1, Subrange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getSubrange_UpperLimit(), theXMLTypePackage.getInt(), "upperLimit", null, 1, 1, Subrange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(subrangeTypeEClass, SubrangeType.class, "SubrangeType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getSubrangeType_Subrange(), this.getSubrange(), null, "subrange", null, 1, 1, SubrangeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(varInitializationEClass, VarInitialization.class, "VarInitialization", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getVarInitialization_InitialValue(), ecorePackage.getEString(), "initialValue", "", 0, 1, VarInitialization.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

		initEClass(valueTypeEClass, ValueType.class, "ValueType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(elementaryTypeEClass, ElementaryType.class, "ElementaryType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(derivedTypeEClass, DerivedType.class, "DerivedType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getDerivedType_BaseType(), this.getElementaryType(), null, "baseType", null, 1, 1, DerivedType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(eventTypeEClass, EventType.class, "EventType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		// Initialize enums and add enum literals
		initEEnum(baseType1EEnum, BaseType1.class, "BaseType1"); //$NON-NLS-1$
		addEEnumLiteral(baseType1EEnum, BaseType1.DATEANDTIME);
		addEEnumLiteral(baseType1EEnum, BaseType1.BYTE);
		addEEnumLiteral(baseType1EEnum, BaseType1.SINT);
		addEEnumLiteral(baseType1EEnum, BaseType1.USINT);
		addEEnumLiteral(baseType1EEnum, BaseType1.LWORD);
		addEEnumLiteral(baseType1EEnum, BaseType1.TIME);
		addEEnumLiteral(baseType1EEnum, BaseType1.WORD);
		addEEnumLiteral(baseType1EEnum, BaseType1.STRING);
		addEEnumLiteral(baseType1EEnum, BaseType1.BOOL);
		addEEnumLiteral(baseType1EEnum, BaseType1.LREAL);
		addEEnumLiteral(baseType1EEnum, BaseType1.REAL);
		addEEnumLiteral(baseType1EEnum, BaseType1.LINT);
		addEEnumLiteral(baseType1EEnum, BaseType1.ULINT);
		addEEnumLiteral(baseType1EEnum, BaseType1.UINT);
		addEEnumLiteral(baseType1EEnum, BaseType1.DATE);
		addEEnumLiteral(baseType1EEnum, BaseType1.DWORD);
		addEEnumLiteral(baseType1EEnum, BaseType1.INT);
		addEEnumLiteral(baseType1EEnum, BaseType1.TIMEOFDAY);
		addEEnumLiteral(baseType1EEnum, BaseType1.WSTRING);
		addEEnumLiteral(baseType1EEnum, BaseType1.DINT);
		addEEnumLiteral(baseType1EEnum, BaseType1.UDINT);
		addEEnumLiteral(baseType1EEnum, BaseType1.ANY);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http:///org/eclipse/emf/ecore/util/ExtendedMetaData
		createExtendedMetaDataAnnotations();
		// http://www.obeo.fr/dsl/dnc/archetype
		createArchetypeAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createExtendedMetaDataAnnotations() {
		String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData"; //$NON-NLS-1$
		addAnnotation
		  (getArrayType_Subranges(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Subrange", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getArrayType_InitialValues(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "InitialValues" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getEnumeratedType_EnumeratedValue(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "EnumeratedValue", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getEnumeratedValue_Comment(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Comment" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getEnumeratedValue_Name(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Name" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getStructuredType_VarDeclaration(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "VarDeclaration", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "http://www.fordiac.org/61499/lib", //$NON-NLS-1$ //$NON-NLS-2$
			   "group", "#group:0" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getSubrange_LowerLimit(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "LowerLimit" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getSubrange_UpperLimit(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "UpperLimit" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getSubrangeType_Subrange(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Subrange", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
	}

	/**
	 * Initializes the annotations for <b>http://www.obeo.fr/dsl/dnc/archetype</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createArchetypeAnnotations() {
		String source = "http://www.obeo.fr/dsl/dnc/archetype"; //$NON-NLS-1$
		addAnnotation
		  (subrangeEClass,
		   source,
		   new String[] {
			   "archetype", "Description" //$NON-NLS-1$ //$NON-NLS-2$
		   });
	}

} //DataPackageImpl
