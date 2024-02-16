/**
 */
package org.eclipse.fordiac.ide.library.model.library;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.fordiac.ide.library.model.library.LibraryFactory
 * @model kind="package"
 *        extendedMetaData="qualified='false'"
 * @generated
 */
public interface LibraryPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "Library"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "platform:/resource/org.eclipse.fordiac.ide.library.model/model/library.xsd"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "Library"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	LibraryPackage eINSTANCE = org.eclipse.fordiac.ide.library.model.library.impl.LibraryPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.library.model.library.impl.DependenciesImpl <em>Dependencies</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.library.model.library.impl.DependenciesImpl
	 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibraryPackageImpl#getDependencies()
	 * @generated
	 */
	int DEPENDENCIES = 0;

	/**
	 * The feature id for the '<em><b>Required</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPENDENCIES__REQUIRED = 0;

	/**
	 * The number of structural features of the '<em>Dependencies</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPENDENCIES_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Dependencies</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPENDENCIES_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.library.model.library.impl.ExcludesImpl <em>Excludes</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.library.model.library.impl.ExcludesImpl
	 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibraryPackageImpl#getExcludes()
	 * @generated
	 */
	int EXCLUDES = 1;

	/**
	 * The feature id for the '<em><b>Library Element</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXCLUDES__LIBRARY_ELEMENT = 0;

	/**
	 * The number of structural features of the '<em>Excludes</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXCLUDES_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Excludes</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXCLUDES_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.library.model.library.impl.IncludesImpl <em>Includes</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.library.model.library.impl.IncludesImpl
	 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibraryPackageImpl#getIncludes()
	 * @generated
	 */
	int INCLUDES = 2;

	/**
	 * The feature id for the '<em><b>Library Element</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCLUDES__LIBRARY_ELEMENT = 0;

	/**
	 * The number of structural features of the '<em>Includes</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCLUDES_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Includes</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCLUDES_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.library.model.library.impl.LibrariesImpl <em>Libraries</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibrariesImpl
	 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibraryPackageImpl#getLibraries()
	 * @generated
	 */
	int LIBRARIES = 3;

	/**
	 * The feature id for the '<em><b>Library</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARIES__LIBRARY = 0;

	/**
	 * The number of structural features of the '<em>Libraries</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARIES_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Libraries</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARIES_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.library.model.library.impl.LibraryImpl <em>Library</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibraryImpl
	 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibraryPackageImpl#getLibrary()
	 * @generated
	 */
	int LIBRARY = 4;

	/**
	 * The feature id for the '<em><b>Includes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY__INCLUDES = 0;

	/**
	 * The feature id for the '<em><b>Excludes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY__EXCLUDES = 1;

	/**
	 * The feature id for the '<em><b>Attribute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY__ATTRIBUTE = 2;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY__COMMENT = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY__NAME = 4;

	/**
	 * The feature id for the '<em><b>Symbolic Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY__SYMBOLIC_NAME = 5;

	/**
	 * The number of structural features of the '<em>Library</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_FEATURE_COUNT = 6;

	/**
	 * The number of operations of the '<em>Library</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.library.model.library.impl.LibraryElementImpl <em>Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibraryElementImpl
	 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibraryPackageImpl#getLibraryElement()
	 * @generated
	 */
	int LIBRARY_ELEMENT = 5;

	/**
	 * The number of structural features of the '<em>Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_ELEMENT_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_ELEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.library.model.library.impl.ManifestImpl <em>Manifest</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.library.model.library.impl.ManifestImpl
	 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibraryPackageImpl#getManifest()
	 * @generated
	 */
	int MANIFEST = 6;

	/**
	 * The feature id for the '<em><b>Product</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANIFEST__PRODUCT = 0;

	/**
	 * The feature id for the '<em><b>Libraries</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANIFEST__LIBRARIES = 1;

	/**
	 * The feature id for the '<em><b>Scope</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANIFEST__SCOPE = 2;

	/**
	 * The number of structural features of the '<em>Manifest</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANIFEST_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Manifest</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANIFEST_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.library.model.library.impl.ProductImpl <em>Product</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.library.model.library.impl.ProductImpl
	 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibraryPackageImpl#getProduct()
	 * @generated
	 */
	int PRODUCT = 7;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT__VERSION_INFO = 0;

	/**
	 * The feature id for the '<em><b>Dependencies</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT__DEPENDENCIES = 1;

	/**
	 * The feature id for the '<em><b>Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT__ATTRIBUTE = 2;

	/**
	 * The number of structural features of the '<em>Product</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Product</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.library.model.library.impl.RequiredImpl <em>Required</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.library.model.library.impl.RequiredImpl
	 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibraryPackageImpl#getRequired()
	 * @generated
	 */
	int REQUIRED = 8;

	/**
	 * The feature id for the '<em><b>Symbolic Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED__SYMBOLIC_NAME = 0;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED__VERSION = 1;

	/**
	 * The number of structural features of the '<em>Required</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Required</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.library.model.library.impl.VersionInfoImpl <em>Version Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.library.model.library.impl.VersionInfoImpl
	 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibraryPackageImpl#getVersionInfo()
	 * @generated
	 */
	int VERSION_INFO = 9;

	/**
	 * The feature id for the '<em><b>Author</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_INFO__AUTHOR = 0;

	/**
	 * The feature id for the '<em><b>Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_INFO__DATE = 1;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_INFO__VERSION = 2;

	/**
	 * The number of structural features of the '<em>Version Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_INFO_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Version Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_INFO_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.library.model.library.Dependencies <em>Dependencies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dependencies</em>'.
	 * @see org.eclipse.fordiac.ide.library.model.library.Dependencies
	 * @generated
	 */
	EClass getDependencies();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.library.model.library.Dependencies#getRequired <em>Required</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Required</em>'.
	 * @see org.eclipse.fordiac.ide.library.model.library.Dependencies#getRequired()
	 * @see #getDependencies()
	 * @generated
	 */
	EReference getDependencies_Required();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.library.model.library.Excludes <em>Excludes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Excludes</em>'.
	 * @see org.eclipse.fordiac.ide.library.model.library.Excludes
	 * @generated
	 */
	EClass getExcludes();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.library.model.library.Excludes#getLibraryElement <em>Library Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Library Element</em>'.
	 * @see org.eclipse.fordiac.ide.library.model.library.Excludes#getLibraryElement()
	 * @see #getExcludes()
	 * @generated
	 */
	EReference getExcludes_LibraryElement();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.library.model.library.Includes <em>Includes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Includes</em>'.
	 * @see org.eclipse.fordiac.ide.library.model.library.Includes
	 * @generated
	 */
	EClass getIncludes();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.library.model.library.Includes#getLibraryElement <em>Library Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Library Element</em>'.
	 * @see org.eclipse.fordiac.ide.library.model.library.Includes#getLibraryElement()
	 * @see #getIncludes()
	 * @generated
	 */
	EReference getIncludes_LibraryElement();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.library.model.library.Libraries <em>Libraries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Libraries</em>'.
	 * @see org.eclipse.fordiac.ide.library.model.library.Libraries
	 * @generated
	 */
	EClass getLibraries();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.library.model.library.Libraries#getLibrary <em>Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Library</em>'.
	 * @see org.eclipse.fordiac.ide.library.model.library.Libraries#getLibrary()
	 * @see #getLibraries()
	 * @generated
	 */
	EReference getLibraries_Library();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.library.model.library.Library <em>Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Library</em>'.
	 * @see org.eclipse.fordiac.ide.library.model.library.Library
	 * @generated
	 */
	EClass getLibrary();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.library.model.library.Library#getIncludes <em>Includes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Includes</em>'.
	 * @see org.eclipse.fordiac.ide.library.model.library.Library#getIncludes()
	 * @see #getLibrary()
	 * @generated
	 */
	EReference getLibrary_Includes();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.library.model.library.Library#getExcludes <em>Excludes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Excludes</em>'.
	 * @see org.eclipse.fordiac.ide.library.model.library.Library#getExcludes()
	 * @see #getLibrary()
	 * @generated
	 */
	EReference getLibrary_Excludes();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.library.model.library.Library#getAttribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Attribute</em>'.
	 * @see org.eclipse.fordiac.ide.library.model.library.Library#getAttribute()
	 * @see #getLibrary()
	 * @generated
	 */
	EAttribute getLibrary_Attribute();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.library.model.library.Library#getComment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Comment</em>'.
	 * @see org.eclipse.fordiac.ide.library.model.library.Library#getComment()
	 * @see #getLibrary()
	 * @generated
	 */
	EAttribute getLibrary_Comment();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.library.model.library.Library#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.fordiac.ide.library.model.library.Library#getName()
	 * @see #getLibrary()
	 * @generated
	 */
	EAttribute getLibrary_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.library.model.library.Library#getSymbolicName <em>Symbolic Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Symbolic Name</em>'.
	 * @see org.eclipse.fordiac.ide.library.model.library.Library#getSymbolicName()
	 * @see #getLibrary()
	 * @generated
	 */
	EAttribute getLibrary_SymbolicName();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.library.model.library.LibraryElement <em>Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Element</em>'.
	 * @see org.eclipse.fordiac.ide.library.model.library.LibraryElement
	 * @generated
	 */
	EClass getLibraryElement();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.library.model.library.Manifest <em>Manifest</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Manifest</em>'.
	 * @see org.eclipse.fordiac.ide.library.model.library.Manifest
	 * @generated
	 */
	EClass getManifest();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.library.model.library.Manifest#getProduct <em>Product</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Product</em>'.
	 * @see org.eclipse.fordiac.ide.library.model.library.Manifest#getProduct()
	 * @see #getManifest()
	 * @generated
	 */
	EReference getManifest_Product();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.library.model.library.Manifest#getLibraries <em>Libraries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Libraries</em>'.
	 * @see org.eclipse.fordiac.ide.library.model.library.Manifest#getLibraries()
	 * @see #getManifest()
	 * @generated
	 */
	EReference getManifest_Libraries();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.library.model.library.Manifest#getScope <em>Scope</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Scope</em>'.
	 * @see org.eclipse.fordiac.ide.library.model.library.Manifest#getScope()
	 * @see #getManifest()
	 * @generated
	 */
	EAttribute getManifest_Scope();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.library.model.library.Product <em>Product</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Product</em>'.
	 * @see org.eclipse.fordiac.ide.library.model.library.Product
	 * @generated
	 */
	EClass getProduct();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.library.model.library.Product#getVersionInfo <em>Version Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Version Info</em>'.
	 * @see org.eclipse.fordiac.ide.library.model.library.Product#getVersionInfo()
	 * @see #getProduct()
	 * @generated
	 */
	EReference getProduct_VersionInfo();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.library.model.library.Product#getDependencies <em>Dependencies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Dependencies</em>'.
	 * @see org.eclipse.fordiac.ide.library.model.library.Product#getDependencies()
	 * @see #getProduct()
	 * @generated
	 */
	EReference getProduct_Dependencies();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.fordiac.ide.library.model.library.Product#getAttribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Attribute</em>'.
	 * @see org.eclipse.fordiac.ide.library.model.library.Product#getAttribute()
	 * @see #getProduct()
	 * @generated
	 */
	EAttribute getProduct_Attribute();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.library.model.library.Required <em>Required</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Required</em>'.
	 * @see org.eclipse.fordiac.ide.library.model.library.Required
	 * @generated
	 */
	EClass getRequired();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.library.model.library.Required#getSymbolicName <em>Symbolic Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Symbolic Name</em>'.
	 * @see org.eclipse.fordiac.ide.library.model.library.Required#getSymbolicName()
	 * @see #getRequired()
	 * @generated
	 */
	EAttribute getRequired_SymbolicName();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.library.model.library.Required#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see org.eclipse.fordiac.ide.library.model.library.Required#getVersion()
	 * @see #getRequired()
	 * @generated
	 */
	EAttribute getRequired_Version();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.library.model.library.VersionInfo <em>Version Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Version Info</em>'.
	 * @see org.eclipse.fordiac.ide.library.model.library.VersionInfo
	 * @generated
	 */
	EClass getVersionInfo();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.library.model.library.VersionInfo#getAuthor <em>Author</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Author</em>'.
	 * @see org.eclipse.fordiac.ide.library.model.library.VersionInfo#getAuthor()
	 * @see #getVersionInfo()
	 * @generated
	 */
	EAttribute getVersionInfo_Author();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.library.model.library.VersionInfo#getDate <em>Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Date</em>'.
	 * @see org.eclipse.fordiac.ide.library.model.library.VersionInfo#getDate()
	 * @see #getVersionInfo()
	 * @generated
	 */
	EAttribute getVersionInfo_Date();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.library.model.library.VersionInfo#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see org.eclipse.fordiac.ide.library.model.library.VersionInfo#getVersion()
	 * @see #getVersionInfo()
	 * @generated
	 */
	EAttribute getVersionInfo_Version();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	LibraryFactory getLibraryFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.library.model.library.impl.DependenciesImpl <em>Dependencies</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.library.model.library.impl.DependenciesImpl
		 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibraryPackageImpl#getDependencies()
		 * @generated
		 */
		EClass DEPENDENCIES = eINSTANCE.getDependencies();

		/**
		 * The meta object literal for the '<em><b>Required</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEPENDENCIES__REQUIRED = eINSTANCE.getDependencies_Required();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.library.model.library.impl.ExcludesImpl <em>Excludes</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.library.model.library.impl.ExcludesImpl
		 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibraryPackageImpl#getExcludes()
		 * @generated
		 */
		EClass EXCLUDES = eINSTANCE.getExcludes();

		/**
		 * The meta object literal for the '<em><b>Library Element</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXCLUDES__LIBRARY_ELEMENT = eINSTANCE.getExcludes_LibraryElement();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.library.model.library.impl.IncludesImpl <em>Includes</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.library.model.library.impl.IncludesImpl
		 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibraryPackageImpl#getIncludes()
		 * @generated
		 */
		EClass INCLUDES = eINSTANCE.getIncludes();

		/**
		 * The meta object literal for the '<em><b>Library Element</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INCLUDES__LIBRARY_ELEMENT = eINSTANCE.getIncludes_LibraryElement();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.library.model.library.impl.LibrariesImpl <em>Libraries</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibrariesImpl
		 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibraryPackageImpl#getLibraries()
		 * @generated
		 */
		EClass LIBRARIES = eINSTANCE.getLibraries();

		/**
		 * The meta object literal for the '<em><b>Library</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LIBRARIES__LIBRARY = eINSTANCE.getLibraries_Library();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.library.model.library.impl.LibraryImpl <em>Library</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibraryImpl
		 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibraryPackageImpl#getLibrary()
		 * @generated
		 */
		EClass LIBRARY = eINSTANCE.getLibrary();

		/**
		 * The meta object literal for the '<em><b>Includes</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LIBRARY__INCLUDES = eINSTANCE.getLibrary_Includes();

		/**
		 * The meta object literal for the '<em><b>Excludes</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LIBRARY__EXCLUDES = eINSTANCE.getLibrary_Excludes();

		/**
		 * The meta object literal for the '<em><b>Attribute</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LIBRARY__ATTRIBUTE = eINSTANCE.getLibrary_Attribute();

		/**
		 * The meta object literal for the '<em><b>Comment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LIBRARY__COMMENT = eINSTANCE.getLibrary_Comment();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LIBRARY__NAME = eINSTANCE.getLibrary_Name();

		/**
		 * The meta object literal for the '<em><b>Symbolic Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LIBRARY__SYMBOLIC_NAME = eINSTANCE.getLibrary_SymbolicName();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.library.model.library.impl.LibraryElementImpl <em>Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibraryElementImpl
		 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibraryPackageImpl#getLibraryElement()
		 * @generated
		 */
		EClass LIBRARY_ELEMENT = eINSTANCE.getLibraryElement();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.library.model.library.impl.ManifestImpl <em>Manifest</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.library.model.library.impl.ManifestImpl
		 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibraryPackageImpl#getManifest()
		 * @generated
		 */
		EClass MANIFEST = eINSTANCE.getManifest();

		/**
		 * The meta object literal for the '<em><b>Product</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MANIFEST__PRODUCT = eINSTANCE.getManifest_Product();

		/**
		 * The meta object literal for the '<em><b>Libraries</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MANIFEST__LIBRARIES = eINSTANCE.getManifest_Libraries();

		/**
		 * The meta object literal for the '<em><b>Scope</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MANIFEST__SCOPE = eINSTANCE.getManifest_Scope();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.library.model.library.impl.ProductImpl <em>Product</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.library.model.library.impl.ProductImpl
		 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibraryPackageImpl#getProduct()
		 * @generated
		 */
		EClass PRODUCT = eINSTANCE.getProduct();

		/**
		 * The meta object literal for the '<em><b>Version Info</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PRODUCT__VERSION_INFO = eINSTANCE.getProduct_VersionInfo();

		/**
		 * The meta object literal for the '<em><b>Dependencies</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PRODUCT__DEPENDENCIES = eINSTANCE.getProduct_Dependencies();

		/**
		 * The meta object literal for the '<em><b>Attribute</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PRODUCT__ATTRIBUTE = eINSTANCE.getProduct_Attribute();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.library.model.library.impl.RequiredImpl <em>Required</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.library.model.library.impl.RequiredImpl
		 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibraryPackageImpl#getRequired()
		 * @generated
		 */
		EClass REQUIRED = eINSTANCE.getRequired();

		/**
		 * The meta object literal for the '<em><b>Symbolic Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REQUIRED__SYMBOLIC_NAME = eINSTANCE.getRequired_SymbolicName();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REQUIRED__VERSION = eINSTANCE.getRequired_Version();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.library.model.library.impl.VersionInfoImpl <em>Version Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.library.model.library.impl.VersionInfoImpl
		 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibraryPackageImpl#getVersionInfo()
		 * @generated
		 */
		EClass VERSION_INFO = eINSTANCE.getVersionInfo();

		/**
		 * The meta object literal for the '<em><b>Author</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VERSION_INFO__AUTHOR = eINSTANCE.getVersionInfo_Author();

		/**
		 * The meta object literal for the '<em><b>Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VERSION_INFO__DATE = eINSTANCE.getVersionInfo_Date();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VERSION_INFO__VERSION = eINSTANCE.getVersionInfo_Version();

	}

} //LibraryPackage
