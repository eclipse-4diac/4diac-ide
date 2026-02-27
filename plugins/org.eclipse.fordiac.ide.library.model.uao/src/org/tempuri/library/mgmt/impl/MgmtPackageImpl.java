/**
 */
package org.tempuri.library.mgmt.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import org.tempuri.library.mgmt.DependenciesType;
import org.tempuri.library.mgmt.DependencyType;
import org.tempuri.library.mgmt.DocumentRoot;
import org.tempuri.library.mgmt.LibraryType;
import org.tempuri.library.mgmt.LicenseType;
import org.tempuri.library.mgmt.ManifestType;
import org.tempuri.library.mgmt.MgmtFactory;
import org.tempuri.library.mgmt.MgmtPackage;
import org.tempuri.library.mgmt.ReadMeType;
import org.tempuri.library.mgmt.SourcesType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class MgmtPackageImpl extends EPackageImpl implements MgmtPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dependenciesTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dependencyTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass documentRootEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass libraryTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass licenseTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass manifestTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass readMeTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sourcesTypeEClass = null;

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
	 * @see org.tempuri.library.mgmt.MgmtPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private MgmtPackageImpl() {
		super(eNS_URI, MgmtFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link MgmtPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static MgmtPackage init() {
		if (isInited) return (MgmtPackage)EPackage.Registry.INSTANCE.getEPackage(MgmtPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredMgmtPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		MgmtPackageImpl theMgmtPackage = registeredMgmtPackage instanceof MgmtPackageImpl ? (MgmtPackageImpl)registeredMgmtPackage : new MgmtPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		XMLTypePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theMgmtPackage.createPackageContents();

		// Initialize created meta-data
		theMgmtPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theMgmtPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(MgmtPackage.eNS_URI, theMgmtPackage);
		return theMgmtPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDependenciesType() {
		return dependenciesTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDependenciesType_Dependency() {
		return (EReference)dependenciesTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDependencyType() {
		return dependencyTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDependencyType_Name() {
		return (EAttribute)dependencyTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDependencyType_Version() {
		return (EAttribute)dependencyTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDocumentRoot() {
		return documentRootEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_Mixed() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_XMLNSPrefixMap() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_XSISchemaLocation() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_Comment() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Dependencies() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Dependency() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Library() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_License() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Manifest() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_Path() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_ReadMe() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Sources() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_Vendor() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLibraryType() {
		return libraryTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLibraryType_ReadMe() {
		return (EReference)libraryTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLibraryType_Sources() {
		return (EReference)libraryTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLibraryType_Vendor() {
		return (EAttribute)libraryTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLibraryType_Comment() {
		return (EAttribute)libraryTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLibraryType_License() {
		return (EReference)libraryTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLibraryType_Name() {
		return (EAttribute)libraryTypeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLibraryType_DisplayName() {
		return (EAttribute)libraryTypeEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLibraryType_Version() {
		return (EAttribute)libraryTypeEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLicenseType() {
		return licenseTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLicenseType_Identifier() {
		return (EAttribute)licenseTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLicenseType_Path() {
		return (EAttribute)licenseTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getManifestType() {
		return manifestTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getManifestType_Library() {
		return (EReference)manifestTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getManifestType_Dependencies() {
		return (EReference)manifestTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getReadMeType() {
		return readMeTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getReadMeType_Path() {
		return (EAttribute)readMeTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSourcesType() {
		return sourcesTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSourcesType_Path() {
		return (EAttribute)sourcesTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MgmtFactory getMgmtFactory() {
		return (MgmtFactory)getEFactoryInstance();
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
		dependenciesTypeEClass = createEClass(DEPENDENCIES_TYPE);
		createEReference(dependenciesTypeEClass, DEPENDENCIES_TYPE__DEPENDENCY);

		dependencyTypeEClass = createEClass(DEPENDENCY_TYPE);
		createEAttribute(dependencyTypeEClass, DEPENDENCY_TYPE__NAME);
		createEAttribute(dependencyTypeEClass, DEPENDENCY_TYPE__VERSION);

		documentRootEClass = createEClass(DOCUMENT_ROOT);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__MIXED);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__COMMENT);
		createEReference(documentRootEClass, DOCUMENT_ROOT__DEPENDENCIES);
		createEReference(documentRootEClass, DOCUMENT_ROOT__DEPENDENCY);
		createEReference(documentRootEClass, DOCUMENT_ROOT__LIBRARY);
		createEReference(documentRootEClass, DOCUMENT_ROOT__LICENSE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__MANIFEST);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__PATH);
		createEReference(documentRootEClass, DOCUMENT_ROOT__READ_ME);
		createEReference(documentRootEClass, DOCUMENT_ROOT__SOURCES);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__VENDOR);

		libraryTypeEClass = createEClass(LIBRARY_TYPE);
		createEReference(libraryTypeEClass, LIBRARY_TYPE__READ_ME);
		createEReference(libraryTypeEClass, LIBRARY_TYPE__SOURCES);
		createEAttribute(libraryTypeEClass, LIBRARY_TYPE__VENDOR);
		createEAttribute(libraryTypeEClass, LIBRARY_TYPE__COMMENT);
		createEReference(libraryTypeEClass, LIBRARY_TYPE__LICENSE);
		createEAttribute(libraryTypeEClass, LIBRARY_TYPE__NAME);
		createEAttribute(libraryTypeEClass, LIBRARY_TYPE__DISPLAY_NAME);
		createEAttribute(libraryTypeEClass, LIBRARY_TYPE__VERSION);

		licenseTypeEClass = createEClass(LICENSE_TYPE);
		createEAttribute(licenseTypeEClass, LICENSE_TYPE__IDENTIFIER);
		createEAttribute(licenseTypeEClass, LICENSE_TYPE__PATH);

		manifestTypeEClass = createEClass(MANIFEST_TYPE);
		createEReference(manifestTypeEClass, MANIFEST_TYPE__LIBRARY);
		createEReference(manifestTypeEClass, MANIFEST_TYPE__DEPENDENCIES);

		readMeTypeEClass = createEClass(READ_ME_TYPE);
		createEAttribute(readMeTypeEClass, READ_ME_TYPE__PATH);

		sourcesTypeEClass = createEClass(SOURCES_TYPE);
		createEAttribute(sourcesTypeEClass, SOURCES_TYPE__PATH);
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

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(dependenciesTypeEClass, DependenciesType.class, "DependenciesType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDependenciesType_Dependency(), this.getDependencyType(), null, "dependency", null, 0, -1, DependenciesType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dependencyTypeEClass, DependencyType.class, "DependencyType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDependencyType_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, DependencyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDependencyType_Version(), theXMLTypePackage.getString(), "version", null, 1, 1, DependencyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(documentRootEClass, DocumentRoot.class, "DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDocumentRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_Comment(), theXMLTypePackage.getString(), "comment", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Dependencies(), this.getDependenciesType(), null, "dependencies", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Dependency(), this.getDependencyType(), null, "dependency", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Library(), this.getLibraryType(), null, "library", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_License(), this.getLicenseType(), null, "license", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Manifest(), this.getManifestType(), null, "manifest", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_Path(), theXMLTypePackage.getString(), "path", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_ReadMe(), this.getReadMeType(), null, "readMe", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Sources(), this.getSourcesType(), null, "sources", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_Vendor(), theXMLTypePackage.getString(), "vendor", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(libraryTypeEClass, LibraryType.class, "LibraryType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getLibraryType_ReadMe(), this.getReadMeType(), null, "readMe", null, 0, 1, LibraryType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLibraryType_Sources(), this.getSourcesType(), null, "sources", null, 0, 1, LibraryType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLibraryType_Vendor(), theXMLTypePackage.getString(), "vendor", null, 0, 1, LibraryType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLibraryType_Comment(), theXMLTypePackage.getString(), "comment", null, 0, 1, LibraryType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLibraryType_License(), this.getLicenseType(), null, "license", null, 0, 1, LibraryType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLibraryType_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, LibraryType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLibraryType_DisplayName(), theXMLTypePackage.getString(), "displayName", null, 0, 1, LibraryType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLibraryType_Version(), theXMLTypePackage.getString(), "version", null, 1, 1, LibraryType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(licenseTypeEClass, LicenseType.class, "LicenseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLicenseType_Identifier(), theXMLTypePackage.getString(), "identifier", null, 0, 1, LicenseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLicenseType_Path(), theXMLTypePackage.getString(), "path", null, 0, 1, LicenseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(manifestTypeEClass, ManifestType.class, "ManifestType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getManifestType_Library(), this.getLibraryType(), null, "library", null, 1, 1, ManifestType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getManifestType_Dependencies(), this.getDependenciesType(), null, "dependencies", null, 1, 1, ManifestType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(readMeTypeEClass, ReadMeType.class, "ReadMeType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getReadMeType_Path(), theXMLTypePackage.getString(), "path", null, 1, 1, ReadMeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(sourcesTypeEClass, SourcesType.class, "SourcesType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSourcesType_Path(), theXMLTypePackage.getString(), "path", null, 1, -1, SourcesType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http:///org/eclipse/emf/ecore/util/ExtendedMetaData
		createExtendedMetaDataAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createExtendedMetaDataAnnotations() {
		String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";
		addAnnotation
		  (dependenciesTypeEClass,
		   source,
		   new String[] {
			   "name", "Dependencies_._type",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getDependenciesType_Dependency(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "Dependency",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (dependencyTypeEClass,
		   source,
		   new String[] {
			   "name", "Dependency_._type",
			   "kind", "empty"
		   });
		addAnnotation
		  (getDependencyType_Name(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "Name"
		   });
		addAnnotation
		  (getDependencyType_Version(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "Version"
		   });
		addAnnotation
		  (documentRootEClass,
		   source,
		   new String[] {
			   "name", "",
			   "kind", "mixed"
		   });
		addAnnotation
		  (getDocumentRoot_Mixed(),
		   source,
		   new String[] {
			   "kind", "elementWildcard",
			   "name", ":mixed"
		   });
		addAnnotation
		  (getDocumentRoot_XMLNSPrefixMap(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "xmlns:prefix"
		   });
		addAnnotation
		  (getDocumentRoot_XSISchemaLocation(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "xsi:schemaLocation"
		   });
		addAnnotation
		  (getDocumentRoot_Comment(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "Comment",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getDocumentRoot_Dependencies(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "Dependencies",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getDocumentRoot_Dependency(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "Dependency",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getDocumentRoot_Library(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "Library",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getDocumentRoot_License(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "License",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getDocumentRoot_Manifest(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "Manifest",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getDocumentRoot_Path(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "Path",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getDocumentRoot_ReadMe(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "ReadMe",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getDocumentRoot_Sources(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "Sources",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getDocumentRoot_Vendor(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "Vendor",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (libraryTypeEClass,
		   source,
		   new String[] {
			   "name", "Library_._type",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getLibraryType_ReadMe(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "ReadMe",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getLibraryType_Sources(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "Sources",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getLibraryType_Vendor(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "Vendor",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getLibraryType_Comment(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "Comment",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getLibraryType_License(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "License",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getLibraryType_Name(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "Name"
		   });
		addAnnotation
		  (getLibraryType_DisplayName(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "DisplayName"
		   });
		addAnnotation
		  (getLibraryType_Version(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "Version"
		   });
		addAnnotation
		  (licenseTypeEClass,
		   source,
		   new String[] {
			   "name", "License_._type",
			   "kind", "empty"
		   });
		addAnnotation
		  (getLicenseType_Identifier(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "Identifier"
		   });
		addAnnotation
		  (getLicenseType_Path(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "Path"
		   });
		addAnnotation
		  (manifestTypeEClass,
		   source,
		   new String[] {
			   "name", "Manifest_._type",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getManifestType_Library(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "Library",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getManifestType_Dependencies(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "Dependencies",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (readMeTypeEClass,
		   source,
		   new String[] {
			   "name", "ReadMe_._type",
			   "kind", "empty"
		   });
		addAnnotation
		  (getReadMeType_Path(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "Path"
		   });
		addAnnotation
		  (sourcesTypeEClass,
		   source,
		   new String[] {
			   "name", "Sources_._type",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getSourcesType_Path(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "Path",
			   "namespace", "##targetNamespace"
		   });
	}

} //MgmtPackageImpl
