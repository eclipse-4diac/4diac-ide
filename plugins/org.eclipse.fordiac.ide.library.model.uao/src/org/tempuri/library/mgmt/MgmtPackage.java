/**
 */
package org.tempuri.library.mgmt;

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
 * @see org.tempuri.library.mgmt.MgmtFactory
 * @model kind="package"
 * @generated
 */
public interface MgmtPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "mgmt";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://tempuri.org/library-mgmt";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "mgmt";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	MgmtPackage eINSTANCE = org.tempuri.library.mgmt.impl.MgmtPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.tempuri.library.mgmt.impl.DependenciesTypeImpl <em>Dependencies Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.tempuri.library.mgmt.impl.DependenciesTypeImpl
	 * @see org.tempuri.library.mgmt.impl.MgmtPackageImpl#getDependenciesType()
	 * @generated
	 */
	int DEPENDENCIES_TYPE = 0;

	/**
	 * The feature id for the '<em><b>Dependency</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPENDENCIES_TYPE__DEPENDENCY = 0;

	/**
	 * The number of structural features of the '<em>Dependencies Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPENDENCIES_TYPE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Dependencies Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPENDENCIES_TYPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.tempuri.library.mgmt.impl.DependencyTypeImpl <em>Dependency Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.tempuri.library.mgmt.impl.DependencyTypeImpl
	 * @see org.tempuri.library.mgmt.impl.MgmtPackageImpl#getDependencyType()
	 * @generated
	 */
	int DEPENDENCY_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPENDENCY_TYPE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPENDENCY_TYPE__VERSION = 1;

	/**
	 * The number of structural features of the '<em>Dependency Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPENDENCY_TYPE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Dependency Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPENDENCY_TYPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.tempuri.library.mgmt.impl.DocumentRootImpl <em>Document Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.tempuri.library.mgmt.impl.DocumentRootImpl
	 * @see org.tempuri.library.mgmt.impl.MgmtPackageImpl#getDocumentRoot()
	 * @generated
	 */
	int DOCUMENT_ROOT = 2;

	/**
	 * The feature id for the '<em><b>Mixed</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MIXED = 0;

	/**
	 * The feature id for the '<em><b>XMLNS Prefix Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__XMLNS_PREFIX_MAP = 1;

	/**
	 * The feature id for the '<em><b>XSI Schema Location</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = 2;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__COMMENT = 3;

	/**
	 * The feature id for the '<em><b>Dependencies</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__DEPENDENCIES = 4;

	/**
	 * The feature id for the '<em><b>Dependency</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__DEPENDENCY = 5;

	/**
	 * The feature id for the '<em><b>Library</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__LIBRARY = 6;

	/**
	 * The feature id for the '<em><b>License</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__LICENSE = 7;

	/**
	 * The feature id for the '<em><b>Manifest</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MANIFEST = 8;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__PATH = 9;

	/**
	 * The feature id for the '<em><b>Read Me</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__READ_ME = 10;

	/**
	 * The feature id for the '<em><b>Sources</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SOURCES = 11;

	/**
	 * The feature id for the '<em><b>Vendor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__VENDOR = 12;

	/**
	 * The number of structural features of the '<em>Document Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT_FEATURE_COUNT = 13;

	/**
	 * The number of operations of the '<em>Document Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.tempuri.library.mgmt.impl.LibraryTypeImpl <em>Library Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.tempuri.library.mgmt.impl.LibraryTypeImpl
	 * @see org.tempuri.library.mgmt.impl.MgmtPackageImpl#getLibraryType()
	 * @generated
	 */
	int LIBRARY_TYPE = 3;

	/**
	 * The feature id for the '<em><b>Read Me</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_TYPE__READ_ME = 0;

	/**
	 * The feature id for the '<em><b>Sources</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_TYPE__SOURCES = 1;

	/**
	 * The feature id for the '<em><b>Vendor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_TYPE__VENDOR = 2;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_TYPE__COMMENT = 3;

	/**
	 * The feature id for the '<em><b>License</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_TYPE__LICENSE = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_TYPE__NAME = 5;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_TYPE__DISPLAY_NAME = 6;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_TYPE__VERSION = 7;

	/**
	 * The number of structural features of the '<em>Library Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_TYPE_FEATURE_COUNT = 8;

	/**
	 * The number of operations of the '<em>Library Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_TYPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.tempuri.library.mgmt.impl.LicenseTypeImpl <em>License Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.tempuri.library.mgmt.impl.LicenseTypeImpl
	 * @see org.tempuri.library.mgmt.impl.MgmtPackageImpl#getLicenseType()
	 * @generated
	 */
	int LICENSE_TYPE = 4;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LICENSE_TYPE__IDENTIFIER = 0;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LICENSE_TYPE__PATH = 1;

	/**
	 * The number of structural features of the '<em>License Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LICENSE_TYPE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>License Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LICENSE_TYPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.tempuri.library.mgmt.impl.ManifestTypeImpl <em>Manifest Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.tempuri.library.mgmt.impl.ManifestTypeImpl
	 * @see org.tempuri.library.mgmt.impl.MgmtPackageImpl#getManifestType()
	 * @generated
	 */
	int MANIFEST_TYPE = 5;

	/**
	 * The feature id for the '<em><b>Library</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANIFEST_TYPE__LIBRARY = 0;

	/**
	 * The feature id for the '<em><b>Dependencies</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANIFEST_TYPE__DEPENDENCIES = 1;

	/**
	 * The number of structural features of the '<em>Manifest Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANIFEST_TYPE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Manifest Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANIFEST_TYPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.tempuri.library.mgmt.impl.ReadMeTypeImpl <em>Read Me Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.tempuri.library.mgmt.impl.ReadMeTypeImpl
	 * @see org.tempuri.library.mgmt.impl.MgmtPackageImpl#getReadMeType()
	 * @generated
	 */
	int READ_ME_TYPE = 6;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int READ_ME_TYPE__PATH = 0;

	/**
	 * The number of structural features of the '<em>Read Me Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int READ_ME_TYPE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Read Me Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int READ_ME_TYPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.tempuri.library.mgmt.impl.SourcesTypeImpl <em>Sources Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.tempuri.library.mgmt.impl.SourcesTypeImpl
	 * @see org.tempuri.library.mgmt.impl.MgmtPackageImpl#getSourcesType()
	 * @generated
	 */
	int SOURCES_TYPE = 7;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCES_TYPE__PATH = 0;

	/**
	 * The number of structural features of the '<em>Sources Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCES_TYPE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Sources Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCES_TYPE_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.tempuri.library.mgmt.DependenciesType <em>Dependencies Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dependencies Type</em>'.
	 * @see org.tempuri.library.mgmt.DependenciesType
	 * @generated
	 */
	EClass getDependenciesType();

	/**
	 * Returns the meta object for the containment reference list '{@link org.tempuri.library.mgmt.DependenciesType#getDependency <em>Dependency</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Dependency</em>'.
	 * @see org.tempuri.library.mgmt.DependenciesType#getDependency()
	 * @see #getDependenciesType()
	 * @generated
	 */
	EReference getDependenciesType_Dependency();

	/**
	 * Returns the meta object for class '{@link org.tempuri.library.mgmt.DependencyType <em>Dependency Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dependency Type</em>'.
	 * @see org.tempuri.library.mgmt.DependencyType
	 * @generated
	 */
	EClass getDependencyType();

	/**
	 * Returns the meta object for the attribute '{@link org.tempuri.library.mgmt.DependencyType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.tempuri.library.mgmt.DependencyType#getName()
	 * @see #getDependencyType()
	 * @generated
	 */
	EAttribute getDependencyType_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.tempuri.library.mgmt.DependencyType#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see org.tempuri.library.mgmt.DependencyType#getVersion()
	 * @see #getDependencyType()
	 * @generated
	 */
	EAttribute getDependencyType_Version();

	/**
	 * Returns the meta object for class '{@link org.tempuri.library.mgmt.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Document Root</em>'.
	 * @see org.tempuri.library.mgmt.DocumentRoot
	 * @generated
	 */
	EClass getDocumentRoot();

	/**
	 * Returns the meta object for the attribute list '{@link org.tempuri.library.mgmt.DocumentRoot#getMixed <em>Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mixed</em>'.
	 * @see org.tempuri.library.mgmt.DocumentRoot#getMixed()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Mixed();

	/**
	 * Returns the meta object for the map '{@link org.tempuri.library.mgmt.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
	 * @see org.tempuri.library.mgmt.DocumentRoot#getXMLNSPrefixMap()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XMLNSPrefixMap();

	/**
	 * Returns the meta object for the map '{@link org.tempuri.library.mgmt.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XSI Schema Location</em>'.
	 * @see org.tempuri.library.mgmt.DocumentRoot#getXSISchemaLocation()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XSISchemaLocation();

	/**
	 * Returns the meta object for the attribute '{@link org.tempuri.library.mgmt.DocumentRoot#getComment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Comment</em>'.
	 * @see org.tempuri.library.mgmt.DocumentRoot#getComment()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Comment();

	/**
	 * Returns the meta object for the containment reference '{@link org.tempuri.library.mgmt.DocumentRoot#getDependencies <em>Dependencies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Dependencies</em>'.
	 * @see org.tempuri.library.mgmt.DocumentRoot#getDependencies()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Dependencies();

	/**
	 * Returns the meta object for the containment reference '{@link org.tempuri.library.mgmt.DocumentRoot#getDependency <em>Dependency</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Dependency</em>'.
	 * @see org.tempuri.library.mgmt.DocumentRoot#getDependency()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Dependency();

	/**
	 * Returns the meta object for the containment reference '{@link org.tempuri.library.mgmt.DocumentRoot#getLibrary <em>Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Library</em>'.
	 * @see org.tempuri.library.mgmt.DocumentRoot#getLibrary()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Library();

	/**
	 * Returns the meta object for the containment reference '{@link org.tempuri.library.mgmt.DocumentRoot#getLicense <em>License</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>License</em>'.
	 * @see org.tempuri.library.mgmt.DocumentRoot#getLicense()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_License();

	/**
	 * Returns the meta object for the containment reference '{@link org.tempuri.library.mgmt.DocumentRoot#getManifest <em>Manifest</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Manifest</em>'.
	 * @see org.tempuri.library.mgmt.DocumentRoot#getManifest()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Manifest();

	/**
	 * Returns the meta object for the attribute '{@link org.tempuri.library.mgmt.DocumentRoot#getPath <em>Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Path</em>'.
	 * @see org.tempuri.library.mgmt.DocumentRoot#getPath()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Path();

	/**
	 * Returns the meta object for the containment reference '{@link org.tempuri.library.mgmt.DocumentRoot#getReadMe <em>Read Me</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Read Me</em>'.
	 * @see org.tempuri.library.mgmt.DocumentRoot#getReadMe()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_ReadMe();

	/**
	 * Returns the meta object for the containment reference '{@link org.tempuri.library.mgmt.DocumentRoot#getSources <em>Sources</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Sources</em>'.
	 * @see org.tempuri.library.mgmt.DocumentRoot#getSources()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Sources();

	/**
	 * Returns the meta object for the attribute '{@link org.tempuri.library.mgmt.DocumentRoot#getVendor <em>Vendor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Vendor</em>'.
	 * @see org.tempuri.library.mgmt.DocumentRoot#getVendor()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Vendor();

	/**
	 * Returns the meta object for class '{@link org.tempuri.library.mgmt.LibraryType <em>Library Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Library Type</em>'.
	 * @see org.tempuri.library.mgmt.LibraryType
	 * @generated
	 */
	EClass getLibraryType();

	/**
	 * Returns the meta object for the containment reference '{@link org.tempuri.library.mgmt.LibraryType#getReadMe <em>Read Me</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Read Me</em>'.
	 * @see org.tempuri.library.mgmt.LibraryType#getReadMe()
	 * @see #getLibraryType()
	 * @generated
	 */
	EReference getLibraryType_ReadMe();

	/**
	 * Returns the meta object for the containment reference '{@link org.tempuri.library.mgmt.LibraryType#getSources <em>Sources</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Sources</em>'.
	 * @see org.tempuri.library.mgmt.LibraryType#getSources()
	 * @see #getLibraryType()
	 * @generated
	 */
	EReference getLibraryType_Sources();

	/**
	 * Returns the meta object for the attribute '{@link org.tempuri.library.mgmt.LibraryType#getVendor <em>Vendor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Vendor</em>'.
	 * @see org.tempuri.library.mgmt.LibraryType#getVendor()
	 * @see #getLibraryType()
	 * @generated
	 */
	EAttribute getLibraryType_Vendor();

	/**
	 * Returns the meta object for the attribute '{@link org.tempuri.library.mgmt.LibraryType#getComment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Comment</em>'.
	 * @see org.tempuri.library.mgmt.LibraryType#getComment()
	 * @see #getLibraryType()
	 * @generated
	 */
	EAttribute getLibraryType_Comment();

	/**
	 * Returns the meta object for the containment reference '{@link org.tempuri.library.mgmt.LibraryType#getLicense <em>License</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>License</em>'.
	 * @see org.tempuri.library.mgmt.LibraryType#getLicense()
	 * @see #getLibraryType()
	 * @generated
	 */
	EReference getLibraryType_License();

	/**
	 * Returns the meta object for the attribute '{@link org.tempuri.library.mgmt.LibraryType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.tempuri.library.mgmt.LibraryType#getName()
	 * @see #getLibraryType()
	 * @generated
	 */
	EAttribute getLibraryType_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.tempuri.library.mgmt.LibraryType#getDisplayName <em>Display Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Display Name</em>'.
	 * @see org.tempuri.library.mgmt.LibraryType#getDisplayName()
	 * @see #getLibraryType()
	 * @generated
	 */
	EAttribute getLibraryType_DisplayName();

	/**
	 * Returns the meta object for the attribute '{@link org.tempuri.library.mgmt.LibraryType#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see org.tempuri.library.mgmt.LibraryType#getVersion()
	 * @see #getLibraryType()
	 * @generated
	 */
	EAttribute getLibraryType_Version();

	/**
	 * Returns the meta object for class '{@link org.tempuri.library.mgmt.LicenseType <em>License Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>License Type</em>'.
	 * @see org.tempuri.library.mgmt.LicenseType
	 * @generated
	 */
	EClass getLicenseType();

	/**
	 * Returns the meta object for the attribute '{@link org.tempuri.library.mgmt.LicenseType#getIdentifier <em>Identifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Identifier</em>'.
	 * @see org.tempuri.library.mgmt.LicenseType#getIdentifier()
	 * @see #getLicenseType()
	 * @generated
	 */
	EAttribute getLicenseType_Identifier();

	/**
	 * Returns the meta object for the attribute '{@link org.tempuri.library.mgmt.LicenseType#getPath <em>Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Path</em>'.
	 * @see org.tempuri.library.mgmt.LicenseType#getPath()
	 * @see #getLicenseType()
	 * @generated
	 */
	EAttribute getLicenseType_Path();

	/**
	 * Returns the meta object for class '{@link org.tempuri.library.mgmt.ManifestType <em>Manifest Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Manifest Type</em>'.
	 * @see org.tempuri.library.mgmt.ManifestType
	 * @generated
	 */
	EClass getManifestType();

	/**
	 * Returns the meta object for the containment reference '{@link org.tempuri.library.mgmt.ManifestType#getLibrary <em>Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Library</em>'.
	 * @see org.tempuri.library.mgmt.ManifestType#getLibrary()
	 * @see #getManifestType()
	 * @generated
	 */
	EReference getManifestType_Library();

	/**
	 * Returns the meta object for the containment reference '{@link org.tempuri.library.mgmt.ManifestType#getDependencies <em>Dependencies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Dependencies</em>'.
	 * @see org.tempuri.library.mgmt.ManifestType#getDependencies()
	 * @see #getManifestType()
	 * @generated
	 */
	EReference getManifestType_Dependencies();

	/**
	 * Returns the meta object for class '{@link org.tempuri.library.mgmt.ReadMeType <em>Read Me Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Read Me Type</em>'.
	 * @see org.tempuri.library.mgmt.ReadMeType
	 * @generated
	 */
	EClass getReadMeType();

	/**
	 * Returns the meta object for the attribute '{@link org.tempuri.library.mgmt.ReadMeType#getPath <em>Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Path</em>'.
	 * @see org.tempuri.library.mgmt.ReadMeType#getPath()
	 * @see #getReadMeType()
	 * @generated
	 */
	EAttribute getReadMeType_Path();

	/**
	 * Returns the meta object for class '{@link org.tempuri.library.mgmt.SourcesType <em>Sources Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sources Type</em>'.
	 * @see org.tempuri.library.mgmt.SourcesType
	 * @generated
	 */
	EClass getSourcesType();

	/**
	 * Returns the meta object for the attribute list '{@link org.tempuri.library.mgmt.SourcesType#getPath <em>Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Path</em>'.
	 * @see org.tempuri.library.mgmt.SourcesType#getPath()
	 * @see #getSourcesType()
	 * @generated
	 */
	EAttribute getSourcesType_Path();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	MgmtFactory getMgmtFactory();

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
		 * The meta object literal for the '{@link org.tempuri.library.mgmt.impl.DependenciesTypeImpl <em>Dependencies Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.tempuri.library.mgmt.impl.DependenciesTypeImpl
		 * @see org.tempuri.library.mgmt.impl.MgmtPackageImpl#getDependenciesType()
		 * @generated
		 */
		EClass DEPENDENCIES_TYPE = eINSTANCE.getDependenciesType();

		/**
		 * The meta object literal for the '<em><b>Dependency</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEPENDENCIES_TYPE__DEPENDENCY = eINSTANCE.getDependenciesType_Dependency();

		/**
		 * The meta object literal for the '{@link org.tempuri.library.mgmt.impl.DependencyTypeImpl <em>Dependency Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.tempuri.library.mgmt.impl.DependencyTypeImpl
		 * @see org.tempuri.library.mgmt.impl.MgmtPackageImpl#getDependencyType()
		 * @generated
		 */
		EClass DEPENDENCY_TYPE = eINSTANCE.getDependencyType();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEPENDENCY_TYPE__NAME = eINSTANCE.getDependencyType_Name();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEPENDENCY_TYPE__VERSION = eINSTANCE.getDependencyType_Version();

		/**
		 * The meta object literal for the '{@link org.tempuri.library.mgmt.impl.DocumentRootImpl <em>Document Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.tempuri.library.mgmt.impl.DocumentRootImpl
		 * @see org.tempuri.library.mgmt.impl.MgmtPackageImpl#getDocumentRoot()
		 * @generated
		 */
		EClass DOCUMENT_ROOT = eINSTANCE.getDocumentRoot();

		/**
		 * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__MIXED = eINSTANCE.getDocumentRoot_Mixed();

		/**
		 * The meta object literal for the '<em><b>XMLNS Prefix Map</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__XMLNS_PREFIX_MAP = eINSTANCE.getDocumentRoot_XMLNSPrefixMap();

		/**
		 * The meta object literal for the '<em><b>XSI Schema Location</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = eINSTANCE.getDocumentRoot_XSISchemaLocation();

		/**
		 * The meta object literal for the '<em><b>Comment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__COMMENT = eINSTANCE.getDocumentRoot_Comment();

		/**
		 * The meta object literal for the '<em><b>Dependencies</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__DEPENDENCIES = eINSTANCE.getDocumentRoot_Dependencies();

		/**
		 * The meta object literal for the '<em><b>Dependency</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__DEPENDENCY = eINSTANCE.getDocumentRoot_Dependency();

		/**
		 * The meta object literal for the '<em><b>Library</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__LIBRARY = eINSTANCE.getDocumentRoot_Library();

		/**
		 * The meta object literal for the '<em><b>License</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__LICENSE = eINSTANCE.getDocumentRoot_License();

		/**
		 * The meta object literal for the '<em><b>Manifest</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__MANIFEST = eINSTANCE.getDocumentRoot_Manifest();

		/**
		 * The meta object literal for the '<em><b>Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__PATH = eINSTANCE.getDocumentRoot_Path();

		/**
		 * The meta object literal for the '<em><b>Read Me</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__READ_ME = eINSTANCE.getDocumentRoot_ReadMe();

		/**
		 * The meta object literal for the '<em><b>Sources</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__SOURCES = eINSTANCE.getDocumentRoot_Sources();

		/**
		 * The meta object literal for the '<em><b>Vendor</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__VENDOR = eINSTANCE.getDocumentRoot_Vendor();

		/**
		 * The meta object literal for the '{@link org.tempuri.library.mgmt.impl.LibraryTypeImpl <em>Library Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.tempuri.library.mgmt.impl.LibraryTypeImpl
		 * @see org.tempuri.library.mgmt.impl.MgmtPackageImpl#getLibraryType()
		 * @generated
		 */
		EClass LIBRARY_TYPE = eINSTANCE.getLibraryType();

		/**
		 * The meta object literal for the '<em><b>Read Me</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LIBRARY_TYPE__READ_ME = eINSTANCE.getLibraryType_ReadMe();

		/**
		 * The meta object literal for the '<em><b>Sources</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LIBRARY_TYPE__SOURCES = eINSTANCE.getLibraryType_Sources();

		/**
		 * The meta object literal for the '<em><b>Vendor</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LIBRARY_TYPE__VENDOR = eINSTANCE.getLibraryType_Vendor();

		/**
		 * The meta object literal for the '<em><b>Comment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LIBRARY_TYPE__COMMENT = eINSTANCE.getLibraryType_Comment();

		/**
		 * The meta object literal for the '<em><b>License</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LIBRARY_TYPE__LICENSE = eINSTANCE.getLibraryType_License();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LIBRARY_TYPE__NAME = eINSTANCE.getLibraryType_Name();

		/**
		 * The meta object literal for the '<em><b>Display Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LIBRARY_TYPE__DISPLAY_NAME = eINSTANCE.getLibraryType_DisplayName();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LIBRARY_TYPE__VERSION = eINSTANCE.getLibraryType_Version();

		/**
		 * The meta object literal for the '{@link org.tempuri.library.mgmt.impl.LicenseTypeImpl <em>License Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.tempuri.library.mgmt.impl.LicenseTypeImpl
		 * @see org.tempuri.library.mgmt.impl.MgmtPackageImpl#getLicenseType()
		 * @generated
		 */
		EClass LICENSE_TYPE = eINSTANCE.getLicenseType();

		/**
		 * The meta object literal for the '<em><b>Identifier</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LICENSE_TYPE__IDENTIFIER = eINSTANCE.getLicenseType_Identifier();

		/**
		 * The meta object literal for the '<em><b>Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LICENSE_TYPE__PATH = eINSTANCE.getLicenseType_Path();

		/**
		 * The meta object literal for the '{@link org.tempuri.library.mgmt.impl.ManifestTypeImpl <em>Manifest Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.tempuri.library.mgmt.impl.ManifestTypeImpl
		 * @see org.tempuri.library.mgmt.impl.MgmtPackageImpl#getManifestType()
		 * @generated
		 */
		EClass MANIFEST_TYPE = eINSTANCE.getManifestType();

		/**
		 * The meta object literal for the '<em><b>Library</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MANIFEST_TYPE__LIBRARY = eINSTANCE.getManifestType_Library();

		/**
		 * The meta object literal for the '<em><b>Dependencies</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MANIFEST_TYPE__DEPENDENCIES = eINSTANCE.getManifestType_Dependencies();

		/**
		 * The meta object literal for the '{@link org.tempuri.library.mgmt.impl.ReadMeTypeImpl <em>Read Me Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.tempuri.library.mgmt.impl.ReadMeTypeImpl
		 * @see org.tempuri.library.mgmt.impl.MgmtPackageImpl#getReadMeType()
		 * @generated
		 */
		EClass READ_ME_TYPE = eINSTANCE.getReadMeType();

		/**
		 * The meta object literal for the '<em><b>Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute READ_ME_TYPE__PATH = eINSTANCE.getReadMeType_Path();

		/**
		 * The meta object literal for the '{@link org.tempuri.library.mgmt.impl.SourcesTypeImpl <em>Sources Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.tempuri.library.mgmt.impl.SourcesTypeImpl
		 * @see org.tempuri.library.mgmt.impl.MgmtPackageImpl#getSourcesType()
		 * @generated
		 */
		EClass SOURCES_TYPE = eINSTANCE.getSourcesType();

		/**
		 * The meta object literal for the '<em><b>Path</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SOURCES_TYPE__PATH = eINSTANCE.getSourcesType_Path();

	}

} //MgmtPackage
