/**
 */
package org.tempuri.library.mgmt;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>License Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.tempuri.library.mgmt.LicenseType#getIdentifier <em>Identifier</em>}</li>
 *   <li>{@link org.tempuri.library.mgmt.LicenseType#getPath <em>Path</em>}</li>
 * </ul>
 *
 * @see org.tempuri.library.mgmt.MgmtPackage#getLicenseType()
 * @model extendedMetaData="name='License_._type' kind='empty'"
 * @generated
 */
public interface LicenseType extends EObject {
	/**
	 * Returns the value of the '<em><b>Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Identifier</em>' attribute.
	 * @see #setIdentifier(String)
	 * @see org.tempuri.library.mgmt.MgmtPackage#getLicenseType_Identifier()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='Identifier'"
	 * @generated
	 */
	String getIdentifier();

	/**
	 * Sets the value of the '{@link org.tempuri.library.mgmt.LicenseType#getIdentifier <em>Identifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Identifier</em>' attribute.
	 * @see #getIdentifier()
	 * @generated
	 */
	void setIdentifier(String value);

	/**
	 * Returns the value of the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Path</em>' attribute.
	 * @see #setPath(String)
	 * @see org.tempuri.library.mgmt.MgmtPackage#getLicenseType_Path()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='Path'"
	 * @generated
	 */
	String getPath();

	/**
	 * Sets the value of the '{@link org.tempuri.library.mgmt.LicenseType#getPath <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Path</em>' attribute.
	 * @see #getPath()
	 * @generated
	 */
	void setPath(String value);

} // LicenseType
