/**
 */
package org.tempuri.library.mgmt;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Manifest Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.tempuri.library.mgmt.ManifestType#getLibrary <em>Library</em>}</li>
 *   <li>{@link org.tempuri.library.mgmt.ManifestType#getDependencies <em>Dependencies</em>}</li>
 * </ul>
 *
 * @see org.tempuri.library.mgmt.MgmtPackage#getManifestType()
 * @model extendedMetaData="name='Manifest_._type' kind='elementOnly'"
 * @generated
 */
public interface ManifestType extends EObject {
	/**
	 * Returns the value of the '<em><b>Library</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Library</em>' containment reference.
	 * @see #setLibrary(LibraryType)
	 * @see org.tempuri.library.mgmt.MgmtPackage#getManifestType_Library()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='Library' namespace='##targetNamespace'"
	 * @generated
	 */
	LibraryType getLibrary();

	/**
	 * Sets the value of the '{@link org.tempuri.library.mgmt.ManifestType#getLibrary <em>Library</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Library</em>' containment reference.
	 * @see #getLibrary()
	 * @generated
	 */
	void setLibrary(LibraryType value);

	/**
	 * Returns the value of the '<em><b>Dependencies</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dependencies</em>' containment reference.
	 * @see #setDependencies(DependenciesType)
	 * @see org.tempuri.library.mgmt.MgmtPackage#getManifestType_Dependencies()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='Dependencies' namespace='##targetNamespace'"
	 * @generated
	 */
	DependenciesType getDependencies();

	/**
	 * Sets the value of the '{@link org.tempuri.library.mgmt.ManifestType#getDependencies <em>Dependencies</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dependencies</em>' containment reference.
	 * @see #getDependencies()
	 * @generated
	 */
	void setDependencies(DependenciesType value);

} // ManifestType
