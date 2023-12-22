/**
 * *******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Martin Jobst
 *      - initial API and implementation and/or initial documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.xmiexport.xmiexport.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.fordiac.ide.model.data.DataPackage;

import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;

import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage;
import org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportAttributeValue;
import org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportAttributeValues;
import org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportFactory;
import org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportInitialValue;
import org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportInitialValues;
import org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportPackage;
import org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportTypeDeclaration;
import org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportTypeDeclarations;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class XMIExportPackageImpl extends EPackageImpl implements XMIExportPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmiExportAttributeValuesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmiExportAttributeValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmiExportInitialValuesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmiExportInitialValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmiExportTypeDeclarationsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmiExportTypeDeclarationEClass = null;

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
	 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private XMIExportPackageImpl() {
		super(eNS_URI, XMIExportFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link XMIExportPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static XMIExportPackage init() {
		if (isInited) return (XMIExportPackage)EPackage.Registry.INSTANCE.getEPackage(XMIExportPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredXMIExportPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		XMIExportPackageImpl theXMIExportPackage = registeredXMIExportPackage instanceof XMIExportPackageImpl ? (XMIExportPackageImpl)registeredXMIExportPackage : new XMIExportPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		LibraryElementPackage.eINSTANCE.eClass();
		DataPackage.eINSTANCE.eClass();
		STCorePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theXMIExportPackage.createPackageContents();

		// Initialize created meta-data
		theXMIExportPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theXMIExportPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(XMIExportPackage.eNS_URI, theXMIExportPackage);
		return theXMIExportPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getXMIExportAttributeValues() {
		return xmiExportAttributeValuesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getXMIExportAttributeValues_AttributeValues() {
		return (EReference)xmiExportAttributeValuesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getXMIExportAttributeValue() {
		return xmiExportAttributeValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getXMIExportAttributeValue_Attribute() {
		return (EReference)xmiExportAttributeValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getXMIExportAttributeValue_Expression() {
		return (EReference)xmiExportAttributeValueEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getXMIExportAttributeValue_Value() {
		return (EAttribute)xmiExportAttributeValueEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getXMIExportInitialValues() {
		return xmiExportInitialValuesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getXMIExportInitialValues_InitialValues() {
		return (EReference)xmiExportInitialValuesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getXMIExportInitialValue() {
		return xmiExportInitialValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getXMIExportInitialValue_Variable() {
		return (EReference)xmiExportInitialValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getXMIExportInitialValue_Expression() {
		return (EReference)xmiExportInitialValueEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getXMIExportInitialValue_Value() {
		return (EAttribute)xmiExportInitialValueEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getXMIExportTypeDeclarations() {
		return xmiExportTypeDeclarationsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getXMIExportTypeDeclarations_TypeDeclarations() {
		return (EReference)xmiExportTypeDeclarationsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getXMIExportTypeDeclaration() {
		return xmiExportTypeDeclarationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getXMIExportTypeDeclaration_Variable() {
		return (EReference)xmiExportTypeDeclarationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getXMIExportTypeDeclaration_TypeDeclaration() {
		return (EReference)xmiExportTypeDeclarationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getXMIExportTypeDeclaration_ResultType() {
		return (EReference)xmiExportTypeDeclarationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public XMIExportFactory getXMIExportFactory() {
		return (XMIExportFactory)getEFactoryInstance();
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
		xmiExportAttributeValuesEClass = createEClass(XMI_EXPORT_ATTRIBUTE_VALUES);
		createEReference(xmiExportAttributeValuesEClass, XMI_EXPORT_ATTRIBUTE_VALUES__ATTRIBUTE_VALUES);

		xmiExportAttributeValueEClass = createEClass(XMI_EXPORT_ATTRIBUTE_VALUE);
		createEReference(xmiExportAttributeValueEClass, XMI_EXPORT_ATTRIBUTE_VALUE__ATTRIBUTE);
		createEReference(xmiExportAttributeValueEClass, XMI_EXPORT_ATTRIBUTE_VALUE__EXPRESSION);
		createEAttribute(xmiExportAttributeValueEClass, XMI_EXPORT_ATTRIBUTE_VALUE__VALUE);

		xmiExportInitialValuesEClass = createEClass(XMI_EXPORT_INITIAL_VALUES);
		createEReference(xmiExportInitialValuesEClass, XMI_EXPORT_INITIAL_VALUES__INITIAL_VALUES);

		xmiExportInitialValueEClass = createEClass(XMI_EXPORT_INITIAL_VALUE);
		createEReference(xmiExportInitialValueEClass, XMI_EXPORT_INITIAL_VALUE__VARIABLE);
		createEReference(xmiExportInitialValueEClass, XMI_EXPORT_INITIAL_VALUE__EXPRESSION);
		createEAttribute(xmiExportInitialValueEClass, XMI_EXPORT_INITIAL_VALUE__VALUE);

		xmiExportTypeDeclarationsEClass = createEClass(XMI_EXPORT_TYPE_DECLARATIONS);
		createEReference(xmiExportTypeDeclarationsEClass, XMI_EXPORT_TYPE_DECLARATIONS__TYPE_DECLARATIONS);

		xmiExportTypeDeclarationEClass = createEClass(XMI_EXPORT_TYPE_DECLARATION);
		createEReference(xmiExportTypeDeclarationEClass, XMI_EXPORT_TYPE_DECLARATION__VARIABLE);
		createEReference(xmiExportTypeDeclarationEClass, XMI_EXPORT_TYPE_DECLARATION__TYPE_DECLARATION);
		createEReference(xmiExportTypeDeclarationEClass, XMI_EXPORT_TYPE_DECLARATION__RESULT_TYPE);
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
		LibraryElementPackage theLibraryElementPackage = (LibraryElementPackage)EPackage.Registry.INSTANCE.getEPackage(LibraryElementPackage.eNS_URI);
		STCorePackage theSTCorePackage = (STCorePackage)EPackage.Registry.INSTANCE.getEPackage(STCorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(xmiExportAttributeValuesEClass, XMIExportAttributeValues.class, "XMIExportAttributeValues", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getXMIExportAttributeValues_AttributeValues(), this.getXMIExportAttributeValue(), null, "attributeValues", null, 0, -1, XMIExportAttributeValues.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(xmiExportAttributeValueEClass, XMIExportAttributeValue.class, "XMIExportAttributeValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getXMIExportAttributeValue_Attribute(), theLibraryElementPackage.getAttribute(), null, "attribute", null, 0, 1, XMIExportAttributeValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getXMIExportAttributeValue_Expression(), theSTCorePackage.getSTInitializerExpression(), null, "expression", null, 0, 1, XMIExportAttributeValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getXMIExportAttributeValue_Value(), ecorePackage.getEString(), "value", null, 0, 1, XMIExportAttributeValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(xmiExportInitialValuesEClass, XMIExportInitialValues.class, "XMIExportInitialValues", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getXMIExportInitialValues_InitialValues(), this.getXMIExportInitialValue(), null, "initialValues", null, 0, -1, XMIExportInitialValues.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(xmiExportInitialValueEClass, XMIExportInitialValue.class, "XMIExportInitialValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getXMIExportInitialValue_Variable(), theLibraryElementPackage.getVarDeclaration(), null, "variable", null, 0, 1, XMIExportInitialValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getXMIExportInitialValue_Expression(), theSTCorePackage.getSTInitializerExpression(), null, "expression", null, 0, 1, XMIExportInitialValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getXMIExportInitialValue_Value(), ecorePackage.getEString(), "value", null, 0, 1, XMIExportInitialValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(xmiExportTypeDeclarationsEClass, XMIExportTypeDeclarations.class, "XMIExportTypeDeclarations", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getXMIExportTypeDeclarations_TypeDeclarations(), this.getXMIExportTypeDeclaration(), null, "typeDeclarations", null, 0, -1, XMIExportTypeDeclarations.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(xmiExportTypeDeclarationEClass, XMIExportTypeDeclaration.class, "XMIExportTypeDeclaration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getXMIExportTypeDeclaration_Variable(), theLibraryElementPackage.getVarDeclaration(), null, "variable", null, 0, 1, XMIExportTypeDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getXMIExportTypeDeclaration_TypeDeclaration(), theSTCorePackage.getSTTypeDeclaration(), null, "typeDeclaration", null, 0, 1, XMIExportTypeDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getXMIExportTypeDeclaration_ResultType(), theLibraryElementPackage.getINamedElement(), null, "resultType", null, 0, 1, XMIExportTypeDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		// Create resource
		createResource(eNS_URI);
	}

} //XMIExportPackageImpl
