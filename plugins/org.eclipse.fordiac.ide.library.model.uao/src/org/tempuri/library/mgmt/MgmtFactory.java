/**
 */
package org.tempuri.library.mgmt;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.tempuri.library.mgmt.MgmtPackage
 * @generated
 */
public interface MgmtFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	MgmtFactory eINSTANCE = org.tempuri.library.mgmt.impl.MgmtFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Dependencies Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Dependencies Type</em>'.
	 * @generated
	 */
	DependenciesType createDependenciesType();

	/**
	 * Returns a new object of class '<em>Dependency Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Dependency Type</em>'.
	 * @generated
	 */
	DependencyType createDependencyType();

	/**
	 * Returns a new object of class '<em>Document Root</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Document Root</em>'.
	 * @generated
	 */
	DocumentRoot createDocumentRoot();

	/**
	 * Returns a new object of class '<em>Library Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Library Type</em>'.
	 * @generated
	 */
	LibraryType createLibraryType();

	/**
	 * Returns a new object of class '<em>License Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>License Type</em>'.
	 * @generated
	 */
	LicenseType createLicenseType();

	/**
	 * Returns a new object of class '<em>Manifest Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Manifest Type</em>'.
	 * @generated
	 */
	ManifestType createManifestType();

	/**
	 * Returns a new object of class '<em>Read Me Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Read Me Type</em>'.
	 * @generated
	 */
	ReadMeType createReadMeType();

	/**
	 * Returns a new object of class '<em>Sources Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Sources Type</em>'.
	 * @generated
	 */
	SourcesType createSourcesType();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	MgmtPackage getMgmtPackage();

} //MgmtFactory
