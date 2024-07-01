/**
 */
package org.eclipse.fordiac.ide.library.model.library.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.fordiac.ide.library.model.library.Attribute;
import org.eclipse.fordiac.ide.library.model.library.Dependencies;
import org.eclipse.fordiac.ide.library.model.library.Excludes;
import org.eclipse.fordiac.ide.library.model.library.Exports;
import org.eclipse.fordiac.ide.library.model.library.Includes;
import org.eclipse.fordiac.ide.library.model.library.Library;
import org.eclipse.fordiac.ide.library.model.library.LibraryElement;
import org.eclipse.fordiac.ide.library.model.library.LibraryFactory;
import org.eclipse.fordiac.ide.library.model.library.LibraryPackage;
import org.eclipse.fordiac.ide.library.model.library.Manifest;
import org.eclipse.fordiac.ide.library.model.library.Product;
import org.eclipse.fordiac.ide.library.model.library.Required;
import org.eclipse.fordiac.ide.library.model.library.VersionInfo;
import org.eclipse.fordiac.ide.library.model.library.util.LibraryValidator;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class LibraryPackageImpl extends EPackageImpl implements LibraryPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attributeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dependenciesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass excludesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass exportsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass includesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass libraryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass libraryElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass manifestEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass productEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass requiredEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass versionInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType nameSpaceFilterEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType symbolicNameEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType versionRangeEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType versionSimpleEDataType = null;

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
	 * @see org.eclipse.fordiac.ide.library.model.library.LibraryPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private LibraryPackageImpl() {
		super(eNS_URI, LibraryFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link LibraryPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static LibraryPackage init() {
		if (isInited) return (LibraryPackage)EPackage.Registry.INSTANCE.getEPackage(LibraryPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredLibraryPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		LibraryPackageImpl theLibraryPackage = registeredLibraryPackage instanceof LibraryPackageImpl ? (LibraryPackageImpl)registeredLibraryPackage : new LibraryPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		XMLTypePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theLibraryPackage.createPackageContents();

		// Initialize created meta-data
		theLibraryPackage.initializePackageContents();

		// Register package validator
		EValidator.Registry.INSTANCE.put
			(theLibraryPackage,
			 new EValidator.Descriptor() {
				 @Override
				 public EValidator getEValidator() {
					 return LibraryValidator.INSTANCE;
				 }
			 });

		// Mark meta-data to indicate it can't be changed
		theLibraryPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(LibraryPackage.eNS_URI, theLibraryPackage);
		return theLibraryPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAttribute() {
		return attributeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAttribute_Comment() {
		return (EAttribute)attributeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAttribute_Name() {
		return (EAttribute)attributeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAttribute_Type() {
		return (EAttribute)attributeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAttribute_Value() {
		return (EAttribute)attributeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDependencies() {
		return dependenciesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDependencies_Required() {
		return (EReference)dependenciesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getExcludes() {
		return excludesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getExcludes_LibraryElement() {
		return (EReference)excludesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getExports() {
		return exportsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getExports_Library() {
		return (EReference)exportsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIncludes() {
		return includesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIncludes_LibraryElement() {
		return (EReference)includesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLibrary() {
		return libraryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLibrary_Includes() {
		return (EReference)libraryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLibrary_Excludes() {
		return (EReference)libraryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLibrary_Attribute() {
		return (EReference)libraryEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLibrary_Comment() {
		return (EAttribute)libraryEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLibrary_Name() {
		return (EAttribute)libraryEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLibrary_SymbolicName() {
		return (EAttribute)libraryEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLibraryElement() {
		return libraryElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLibraryElement_Value() {
		return (EAttribute)libraryElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getSymbolicName() {
		return symbolicNameEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getVersionRange() {
		return versionRangeEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getVersionSimple() {
		return versionSimpleEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getManifest() {
		return manifestEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getManifest_Dependencies() {
		return (EReference)manifestEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getManifest_Product() {
		return (EReference)manifestEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getManifest_Exports() {
		return (EReference)manifestEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getManifest_Scope() {
		return (EAttribute)manifestEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getProduct() {
		return productEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getProduct_VersionInfo() {
		return (EReference)productEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getProduct_Attribute() {
		return (EReference)productEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getProduct_Comment() {
		return (EAttribute)productEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getProduct_Name() {
		return (EAttribute)productEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getProduct_SymbolicName() {
		return (EAttribute)productEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getRequired() {
		return requiredEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRequired_SymbolicName() {
		return (EAttribute)requiredEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRequired_Version() {
		return (EAttribute)requiredEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getVersionInfo() {
		return versionInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVersionInfo_Author() {
		return (EAttribute)versionInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVersionInfo_Date() {
		return (EAttribute)versionInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVersionInfo_Version() {
		return (EAttribute)versionInfoEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getNameSpaceFilter() {
		return nameSpaceFilterEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LibraryFactory getLibraryFactory() {
		return (LibraryFactory)getEFactoryInstance();
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
		attributeEClass = createEClass(ATTRIBUTE);
		createEAttribute(attributeEClass, ATTRIBUTE__COMMENT);
		createEAttribute(attributeEClass, ATTRIBUTE__NAME);
		createEAttribute(attributeEClass, ATTRIBUTE__TYPE);
		createEAttribute(attributeEClass, ATTRIBUTE__VALUE);

		dependenciesEClass = createEClass(DEPENDENCIES);
		createEReference(dependenciesEClass, DEPENDENCIES__REQUIRED);

		excludesEClass = createEClass(EXCLUDES);
		createEReference(excludesEClass, EXCLUDES__LIBRARY_ELEMENT);

		exportsEClass = createEClass(EXPORTS);
		createEReference(exportsEClass, EXPORTS__LIBRARY);

		includesEClass = createEClass(INCLUDES);
		createEReference(includesEClass, INCLUDES__LIBRARY_ELEMENT);

		libraryEClass = createEClass(LIBRARY);
		createEReference(libraryEClass, LIBRARY__INCLUDES);
		createEReference(libraryEClass, LIBRARY__EXCLUDES);
		createEReference(libraryEClass, LIBRARY__ATTRIBUTE);
		createEAttribute(libraryEClass, LIBRARY__COMMENT);
		createEAttribute(libraryEClass, LIBRARY__NAME);
		createEAttribute(libraryEClass, LIBRARY__SYMBOLIC_NAME);

		libraryElementEClass = createEClass(LIBRARY_ELEMENT);
		createEAttribute(libraryElementEClass, LIBRARY_ELEMENT__VALUE);

		manifestEClass = createEClass(MANIFEST);
		createEReference(manifestEClass, MANIFEST__DEPENDENCIES);
		createEReference(manifestEClass, MANIFEST__PRODUCT);
		createEReference(manifestEClass, MANIFEST__EXPORTS);
		createEAttribute(manifestEClass, MANIFEST__SCOPE);

		productEClass = createEClass(PRODUCT);
		createEReference(productEClass, PRODUCT__VERSION_INFO);
		createEReference(productEClass, PRODUCT__ATTRIBUTE);
		createEAttribute(productEClass, PRODUCT__COMMENT);
		createEAttribute(productEClass, PRODUCT__NAME);
		createEAttribute(productEClass, PRODUCT__SYMBOLIC_NAME);

		requiredEClass = createEClass(REQUIRED);
		createEAttribute(requiredEClass, REQUIRED__SYMBOLIC_NAME);
		createEAttribute(requiredEClass, REQUIRED__VERSION);

		versionInfoEClass = createEClass(VERSION_INFO);
		createEAttribute(versionInfoEClass, VERSION_INFO__AUTHOR);
		createEAttribute(versionInfoEClass, VERSION_INFO__DATE);
		createEAttribute(versionInfoEClass, VERSION_INFO__VERSION);

		// Create data types
		nameSpaceFilterEDataType = createEDataType(NAME_SPACE_FILTER);
		symbolicNameEDataType = createEDataType(SYMBOLIC_NAME);
		versionRangeEDataType = createEDataType(VERSION_RANGE);
		versionSimpleEDataType = createEDataType(VERSION_SIMPLE);
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

		// Initialize classes and features; add operations and parameters
		initEClass(attributeEClass, Attribute.class, "Attribute", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getAttribute_Comment(), theXMLTypePackage.getString(), "comment", null, 0, 1, Attribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getAttribute_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, Attribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getAttribute_Type(), theXMLTypePackage.getString(), "type", null, 1, 1, Attribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getAttribute_Value(), theXMLTypePackage.getString(), "value", null, 1, 1, Attribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(dependenciesEClass, Dependencies.class, "Dependencies", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getDependencies_Required(), this.getRequired(), null, "required", null, 0, -1, Dependencies.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(excludesEClass, Excludes.class, "Excludes", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getExcludes_LibraryElement(), this.getLibraryElement(), null, "libraryElement", null, 0, -1, Excludes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(exportsEClass, Exports.class, "Exports", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getExports_Library(), this.getLibrary(), null, "library", null, 0, -1, Exports.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(includesEClass, Includes.class, "Includes", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getIncludes_LibraryElement(), this.getLibraryElement(), null, "libraryElement", null, 0, -1, Includes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(libraryEClass, Library.class, "Library", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getLibrary_Includes(), this.getIncludes(), null, "includes", null, 0, 1, Library.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getLibrary_Excludes(), this.getExcludes(), null, "excludes", null, 0, 1, Library.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getLibrary_Attribute(), this.getAttribute(), null, "attribute", null, 0, -1, Library.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getLibrary_Comment(), theXMLTypePackage.getString(), "comment", null, 1, 1, Library.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getLibrary_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, Library.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getLibrary_SymbolicName(), this.getSymbolicName(), "symbolicName", null, 1, 1, Library.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(libraryElementEClass, LibraryElement.class, "LibraryElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getLibraryElement_Value(), this.getNameSpaceFilter(), "value", null, 0, 1, LibraryElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(manifestEClass, Manifest.class, "Manifest", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getManifest_Dependencies(), this.getDependencies(), null, "dependencies", null, 0, 1, Manifest.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getManifest_Product(), this.getProduct(), null, "product", null, 1, 1, Manifest.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getManifest_Exports(), this.getExports(), null, "exports", null, 0, 1, Manifest.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getManifest_Scope(), theXMLTypePackage.getString(), "scope", null, 1, 1, Manifest.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(productEClass, Product.class, "Product", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getProduct_VersionInfo(), this.getVersionInfo(), null, "versionInfo", null, 1, 1, Product.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getProduct_Attribute(), this.getAttribute(), null, "attribute", null, 0, -1, Product.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getProduct_Comment(), theXMLTypePackage.getString(), "comment", null, 0, 1, Product.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getProduct_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, Product.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getProduct_SymbolicName(), this.getSymbolicName(), "symbolicName", null, 0, 1, Product.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(requiredEClass, Required.class, "Required", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getRequired_SymbolicName(), this.getSymbolicName(), "symbolicName", null, 1, 1, Required.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getRequired_Version(), this.getVersionRange(), "version", null, 1, 1, Required.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(versionInfoEClass, VersionInfo.class, "VersionInfo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getVersionInfo_Author(), theXMLTypePackage.getString(), "author", null, 1, 1, VersionInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getVersionInfo_Date(), theXMLTypePackage.getString(), "date", null, 1, 1, VersionInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getVersionInfo_Version(), this.getVersionSimple(), "version", null, 1, 1, VersionInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		// Initialize data types
		initEDataType(nameSpaceFilterEDataType, String.class, "NameSpaceFilter", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEDataType(symbolicNameEDataType, String.class, "SymbolicName", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEDataType(versionRangeEDataType, String.class, "VersionRange", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEDataType(versionSimpleEDataType, String.class, "VersionSimple", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

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
		String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData"; //$NON-NLS-1$
		addAnnotation
		  (this,
		   source,
		   new String[] {
			   "qualified", "false" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (attributeEClass,
		   source,
		   new String[] {
			   "name", "Attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getAttribute_Comment(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Comment", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getAttribute_Name(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Name", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getAttribute_Type(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Type", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getAttribute_Value(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Value", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (dependenciesEClass,
		   source,
		   new String[] {
			   "name", "Dependencies", //$NON-NLS-1$ //$NON-NLS-2$
			   "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getDependencies_Required(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Required", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (excludesEClass,
		   source,
		   new String[] {
			   "name", "Excludes", //$NON-NLS-1$ //$NON-NLS-2$
			   "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getExcludes_LibraryElement(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "LibraryElement", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (exportsEClass,
		   source,
		   new String[] {
			   "name", "Exports", //$NON-NLS-1$ //$NON-NLS-2$
			   "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getExports_Library(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Library", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (includesEClass,
		   source,
		   new String[] {
			   "name", "Includes", //$NON-NLS-1$ //$NON-NLS-2$
			   "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getIncludes_LibraryElement(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "LibraryElement", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (libraryEClass,
		   source,
		   new String[] {
			   "name", "Library", //$NON-NLS-1$ //$NON-NLS-2$
			   "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getLibrary_Includes(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Includes", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getLibrary_Excludes(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Excludes", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getLibrary_Attribute(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getLibrary_Comment(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Comment", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getLibrary_Name(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Name", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getLibrary_SymbolicName(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "SymbolicName", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (libraryElementEClass,
		   source,
		   new String[] {
			   "name", "LibraryElement", //$NON-NLS-1$ //$NON-NLS-2$
			   "kind", "simple" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getLibraryElement_Value(),
		   source,
		   new String[] {
			   "name", ":0", //$NON-NLS-1$ //$NON-NLS-2$
			   "kind", "simple" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (manifestEClass,
		   source,
		   new String[] {
			   "name", "Manifest", //$NON-NLS-1$ //$NON-NLS-2$
			   "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getManifest_Dependencies(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Dependencies", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getManifest_Product(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Product", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getManifest_Exports(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Exports", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getManifest_Scope(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Scope", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (nameSpaceFilterEDataType,
		   source,
		   new String[] {
			   "name", "NameSpaceFilter", //$NON-NLS-1$ //$NON-NLS-2$
			   "baseType", "http://www.eclipse.org/emf/2003/XMLType#token", //$NON-NLS-1$ //$NON-NLS-2$
			   "pattern", "((_[a-zA-Z0-9]|[a-zA-Z])(_?[a-zA-Z0-9])*|\\*\\*?)(::((_[a-zA-Z0-9]|[a-zA-Z])(_?[a-zA-Z0-9])*|\\*\\*?))*" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (productEClass,
		   source,
		   new String[] {
			   "name", "Product", //$NON-NLS-1$ //$NON-NLS-2$
			   "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getProduct_VersionInfo(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "VersionInfo", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getProduct_Attribute(),
		   source,
		   new String[] {
			   "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getProduct_Comment(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Comment", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getProduct_Name(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Name", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getProduct_SymbolicName(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "SymbolicName", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (requiredEClass,
		   source,
		   new String[] {
			   "name", "Required", //$NON-NLS-1$ //$NON-NLS-2$
			   "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getRequired_SymbolicName(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "SymbolicName", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getRequired_Version(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Version", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (symbolicNameEDataType,
		   source,
		   new String[] {
			   "name", "SymbolicName", //$NON-NLS-1$ //$NON-NLS-2$
			   "baseType", "http://www.eclipse.org/emf/2003/XMLType#token", //$NON-NLS-1$ //$NON-NLS-2$
			   "pattern", "[a-zA-Z][-_a-zA-Z0-9]*" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (versionInfoEClass,
		   source,
		   new String[] {
			   "name", "VersionInfo", //$NON-NLS-1$ //$NON-NLS-2$
			   "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getVersionInfo_Author(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Author", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getVersionInfo_Date(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Date", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (getVersionInfo_Version(),
		   source,
		   new String[] {
			   "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
			   "name", "Version", //$NON-NLS-1$ //$NON-NLS-2$
			   "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (versionRangeEDataType,
		   source,
		   new String[] {
			   "name", "VersionRange", //$NON-NLS-1$ //$NON-NLS-2$
			   "baseType", "http://www.eclipse.org/emf/2003/XMLType#token", //$NON-NLS-1$ //$NON-NLS-2$
			   "pattern", "[0-9]+(\\.[0-9]+)*|[\\[\\(][0-9]+(\\.[0-9]+)*\\-[0-9]+(\\.[0-9]+)*[\\]\\)]" //$NON-NLS-1$ //$NON-NLS-2$
		   });
		addAnnotation
		  (versionSimpleEDataType,
		   source,
		   new String[] {
			   "name", "VersionSimple", //$NON-NLS-1$ //$NON-NLS-2$
			   "baseType", "http://www.eclipse.org/emf/2003/XMLType#token", //$NON-NLS-1$ //$NON-NLS-2$
			   "pattern", "[0-9]+(\\.[0-9]+)*" //$NON-NLS-1$ //$NON-NLS-2$
		   });
	}

} //LibraryPackageImpl
