/**
 */
package org.eclipse.fordiac.ide.library.model.library;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
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
	String eNAME = "library";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "platform:/resource/org.eclipse.fordiac.ide.library.model/model/library.xsd";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "library";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	LibraryPackage eINSTANCE = org.eclipse.fordiac.ide.library.model.library.impl.LibraryPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.library.model.library.impl.AttributeImpl <em>Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.library.model.library.impl.AttributeImpl
	 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibraryPackageImpl#getAttribute()
	 * @generated
	 */
	int ATTRIBUTE = 0;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__COMMENT = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__NAME = 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__TYPE = 2;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__VALUE = 3;

	/**
	 * The number of structural features of the '<em>Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.library.model.library.impl.DependenciesImpl <em>Dependencies</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.library.model.library.impl.DependenciesImpl
	 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibraryPackageImpl#getDependencies()
	 * @generated
	 */
	int DEPENDENCIES = 1;

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
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.library.model.library.impl.ExcludesImpl <em>Excludes</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.library.model.library.impl.ExcludesImpl
	 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibraryPackageImpl#getExcludes()
	 * @generated
	 */
	int EXCLUDES = 2;

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
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.library.model.library.impl.ExportsImpl <em>Exports</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.library.model.library.impl.ExportsImpl
	 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibraryPackageImpl#getExports()
	 * @generated
	 */
	int EXPORTS = 3;

	/**
	 * The feature id for the '<em><b>Library</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTS__LIBRARY = 0;

	/**
	 * The number of structural features of the '<em>Exports</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTS_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.library.model.library.impl.IncludesImpl <em>Includes</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.library.model.library.impl.IncludesImpl
	 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibraryPackageImpl#getIncludes()
	 * @generated
	 */
	int INCLUDES = 4;

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
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.library.model.library.impl.LibraryImpl <em>Library</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibraryImpl
	 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibraryPackageImpl#getLibrary()
	 * @generated
	 */
	int LIBRARY = 5;

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
	 * The feature id for the '<em><b>Attribute</b></em>' containment reference list.
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
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.library.model.library.impl.LibraryElementImpl <em>Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibraryElementImpl
	 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibraryPackageImpl#getLibraryElement()
	 * @generated
	 */
	int LIBRARY_ELEMENT = 6;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_ELEMENT__VALUE = 0;

	/**
	 * The number of structural features of the '<em>Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '<em>Symbolic Name</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibraryPackageImpl#getSymbolicName()
	 * @generated
	 */
	int SYMBOLIC_NAME = 12;

	/**
	 * The meta object id for the '<em>Version Range</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibraryPackageImpl#getVersionRange()
	 * @generated
	 */
	int VERSION_RANGE = 13;

	/**
	 * The meta object id for the '<em>Version Simple</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibraryPackageImpl#getVersionSimple()
	 * @generated
	 */
	int VERSION_SIMPLE = 14;

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.library.model.library.Attribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute</em>'.
	 * @see org.eclipse.fordiac.ide.library.model.library.Attribute
	 * @generated
	 */
	EClass getAttribute();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.library.model.library.Attribute#getComment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Comment</em>'.
	 * @see org.eclipse.fordiac.ide.library.model.library.Attribute#getComment()
	 * @see #getAttribute()
	 * @generated
	 */
	EAttribute getAttribute_Comment();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.library.model.library.Attribute#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.fordiac.ide.library.model.library.Attribute#getName()
	 * @see #getAttribute()
	 * @generated
	 */
	EAttribute getAttribute_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.library.model.library.Attribute#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.fordiac.ide.library.model.library.Attribute#getType()
	 * @see #getAttribute()
	 * @generated
	 */
	EAttribute getAttribute_Type();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.library.model.library.Attribute#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.fordiac.ide.library.model.library.Attribute#getValue()
	 * @see #getAttribute()
	 * @generated
	 */
	EAttribute getAttribute_Value();

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.library.model.library.impl.ManifestImpl <em>Manifest</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.library.model.library.impl.ManifestImpl
	 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibraryPackageImpl#getManifest()
	 * @generated
	 */
	int MANIFEST = 7;

	/**
	 * The feature id for the '<em><b>Dependencies</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANIFEST__DEPENDENCIES = 0;

	/**
	 * The feature id for the '<em><b>Product</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANIFEST__PRODUCT = 1;

	/**
	 * The feature id for the '<em><b>Exports</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANIFEST__EXPORTS = 2;

	/**
	 * The feature id for the '<em><b>Scope</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANIFEST__SCOPE = 3;

	/**
	 * The number of structural features of the '<em>Manifest</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANIFEST_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.library.model.library.impl.ProductImpl <em>Product</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.library.model.library.impl.ProductImpl
	 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibraryPackageImpl#getProduct()
	 * @generated
	 */
	int PRODUCT = 8;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT__VERSION_INFO = 0;

	/**
	 * The feature id for the '<em><b>Attribute</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT__ATTRIBUTE = 1;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT__COMMENT = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT__NAME = 3;

	/**
	 * The feature id for the '<em><b>Symbolic Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT__SYMBOLIC_NAME = 4;

	/**
	 * The number of structural features of the '<em>Product</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.library.model.library.impl.RequiredImpl <em>Required</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.library.model.library.impl.RequiredImpl
	 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibraryPackageImpl#getRequired()
	 * @generated
	 */
	int REQUIRED = 9;

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
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.library.model.library.impl.VersionInfoImpl <em>Version Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.library.model.library.impl.VersionInfoImpl
	 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibraryPackageImpl#getVersionInfo()
	 * @generated
	 */
	int VERSION_INFO = 10;

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
	 * The meta object id for the '<em>Name Space Filter</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibraryPackageImpl#getNameSpaceFilter()
	 * @generated
	 */
	int NAME_SPACE_FILTER = 11;

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
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.library.model.library.Exports <em>Exports</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Exports</em>'.
	 * @see org.eclipse.fordiac.ide.library.model.library.Exports
	 * @generated
	 */
	EClass getExports();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.library.model.library.Exports#getLibrary <em>Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Library</em>'.
	 * @see org.eclipse.fordiac.ide.library.model.library.Exports#getLibrary()
	 * @see #getExports()
	 * @generated
	 */
	EReference getExports_Library();

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
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.library.model.library.Library#getAttribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attribute</em>'.
	 * @see org.eclipse.fordiac.ide.library.model.library.Library#getAttribute()
	 * @see #getLibrary()
	 * @generated
	 */
	EReference getLibrary_Attribute();

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
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.library.model.library.LibraryElement#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.fordiac.ide.library.model.library.LibraryElement#getValue()
	 * @see #getLibraryElement()
	 * @generated
	 */
	EAttribute getLibraryElement_Value();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Symbolic Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Symbolic Name</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='SymbolicName' baseType='http://www.eclipse.org/emf/2003/XMLType#token' pattern='[a-zA-Z][-_a-zA-Z0-9]*'"
	 * @generated
	 */
	EDataType getSymbolicName();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Version Range</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Version Range</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='VersionRange' baseType='http://www.eclipse.org/emf/2003/XMLType#token' pattern='[0-9]+(\\.[0-9]+)*|[\\[\\(][0-9]+(\\.[0-9]+)*\\-[0-9]+(\\.[0-9]+)*[\\]\\)]'"
	 * @generated
	 */
	EDataType getVersionRange();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Version Simple</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Version Simple</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='VersionSimple' baseType='http://www.eclipse.org/emf/2003/XMLType#token' pattern='[0-9]+(\\.[0-9]+)*'"
	 * @generated
	 */
	EDataType getVersionSimple();

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
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.library.model.library.Manifest#getDependencies <em>Dependencies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Dependencies</em>'.
	 * @see org.eclipse.fordiac.ide.library.model.library.Manifest#getDependencies()
	 * @see #getManifest()
	 * @generated
	 */
	EReference getManifest_Dependencies();

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
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.library.model.library.Manifest#getExports <em>Exports</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Exports</em>'.
	 * @see org.eclipse.fordiac.ide.library.model.library.Manifest#getExports()
	 * @see #getManifest()
	 * @generated
	 */
	EReference getManifest_Exports();

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
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.library.model.library.Product#getAttribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attribute</em>'.
	 * @see org.eclipse.fordiac.ide.library.model.library.Product#getAttribute()
	 * @see #getProduct()
	 * @generated
	 */
	EReference getProduct_Attribute();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.library.model.library.Product#getComment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Comment</em>'.
	 * @see org.eclipse.fordiac.ide.library.model.library.Product#getComment()
	 * @see #getProduct()
	 * @generated
	 */
	EAttribute getProduct_Comment();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.library.model.library.Product#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.fordiac.ide.library.model.library.Product#getName()
	 * @see #getProduct()
	 * @generated
	 */
	EAttribute getProduct_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.library.model.library.Product#getSymbolicName <em>Symbolic Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Symbolic Name</em>'.
	 * @see org.eclipse.fordiac.ide.library.model.library.Product#getSymbolicName()
	 * @see #getProduct()
	 * @generated
	 */
	EAttribute getProduct_SymbolicName();

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
	 * Returns the meta object for data type '{@link java.lang.String <em>Name Space Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Name Space Filter</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='NameSpaceFilter' baseType='http://www.eclipse.org/emf/2003/XMLType#token' pattern='((_[a-zA-Z0-9]|[a-zA-Z])(_?[a-zA-Z0-9])*|\\*\\*?)(::((_[a-zA-Z0-9]|[a-zA-Z])(_?[a-zA-Z0-9])*|\\*\\*?))*'"
	 * @generated
	 */
	EDataType getNameSpaceFilter();

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
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.library.model.library.impl.AttributeImpl <em>Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.library.model.library.impl.AttributeImpl
		 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibraryPackageImpl#getAttribute()
		 * @generated
		 */
		EClass ATTRIBUTE = eINSTANCE.getAttribute();

		/**
		 * The meta object literal for the '<em><b>Comment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE__COMMENT = eINSTANCE.getAttribute_Comment();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE__NAME = eINSTANCE.getAttribute_Name();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE__TYPE = eINSTANCE.getAttribute_Type();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE__VALUE = eINSTANCE.getAttribute_Value();

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
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.library.model.library.impl.ExportsImpl <em>Exports</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.library.model.library.impl.ExportsImpl
		 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibraryPackageImpl#getExports()
		 * @generated
		 */
		EClass EXPORTS = eINSTANCE.getExports();

		/**
		 * The meta object literal for the '<em><b>Library</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXPORTS__LIBRARY = eINSTANCE.getExports_Library();

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
		 * The meta object literal for the '<em><b>Attribute</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LIBRARY__ATTRIBUTE = eINSTANCE.getLibrary_Attribute();

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
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LIBRARY_ELEMENT__VALUE = eINSTANCE.getLibraryElement_Value();

		/**
		 * The meta object literal for the '<em>Symbolic Name</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibraryPackageImpl#getSymbolicName()
		 * @generated
		 */
		EDataType SYMBOLIC_NAME = eINSTANCE.getSymbolicName();

		/**
		 * The meta object literal for the '<em>Version Range</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibraryPackageImpl#getVersionRange()
		 * @generated
		 */
		EDataType VERSION_RANGE = eINSTANCE.getVersionRange();

		/**
		 * The meta object literal for the '<em>Version Simple</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibraryPackageImpl#getVersionSimple()
		 * @generated
		 */
		EDataType VERSION_SIMPLE = eINSTANCE.getVersionSimple();

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
		 * The meta object literal for the '<em><b>Dependencies</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MANIFEST__DEPENDENCIES = eINSTANCE.getManifest_Dependencies();

		/**
		 * The meta object literal for the '<em><b>Product</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MANIFEST__PRODUCT = eINSTANCE.getManifest_Product();

		/**
		 * The meta object literal for the '<em><b>Exports</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MANIFEST__EXPORTS = eINSTANCE.getManifest_Exports();

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
		 * The meta object literal for the '<em><b>Attribute</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PRODUCT__ATTRIBUTE = eINSTANCE.getProduct_Attribute();

		/**
		 * The meta object literal for the '<em><b>Comment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PRODUCT__COMMENT = eINSTANCE.getProduct_Comment();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PRODUCT__NAME = eINSTANCE.getProduct_Name();

		/**
		 * The meta object literal for the '<em><b>Symbolic Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PRODUCT__SYMBOLIC_NAME = eINSTANCE.getProduct_SymbolicName();

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

		/**
		 * The meta object literal for the '<em>Name Space Filter</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see org.eclipse.fordiac.ide.library.model.library.impl.LibraryPackageImpl#getNameSpaceFilter()
		 * @generated
		 */
		EDataType NAME_SPACE_FILTER = eINSTANCE.getNameSpaceFilter();

	}

} //LibraryPackage
