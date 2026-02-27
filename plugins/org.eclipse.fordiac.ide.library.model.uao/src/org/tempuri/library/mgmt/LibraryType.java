/**
 */
package org.tempuri.library.mgmt;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Library Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.tempuri.library.mgmt.LibraryType#getReadMe <em>Read Me</em>}</li>
 *   <li>{@link org.tempuri.library.mgmt.LibraryType#getSources <em>Sources</em>}</li>
 *   <li>{@link org.tempuri.library.mgmt.LibraryType#getVendor <em>Vendor</em>}</li>
 *   <li>{@link org.tempuri.library.mgmt.LibraryType#getComment <em>Comment</em>}</li>
 *   <li>{@link org.tempuri.library.mgmt.LibraryType#getLicense <em>License</em>}</li>
 *   <li>{@link org.tempuri.library.mgmt.LibraryType#getName <em>Name</em>}</li>
 *   <li>{@link org.tempuri.library.mgmt.LibraryType#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link org.tempuri.library.mgmt.LibraryType#getVersion <em>Version</em>}</li>
 * </ul>
 *
 * @see org.tempuri.library.mgmt.MgmtPackage#getLibraryType()
 * @model extendedMetaData="name='Library_._type' kind='elementOnly'"
 * @generated
 */
public interface LibraryType extends EObject {
	/**
	 * Returns the value of the '<em><b>Read Me</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Read Me</em>' containment reference.
	 * @see #setReadMe(ReadMeType)
	 * @see org.tempuri.library.mgmt.MgmtPackage#getLibraryType_ReadMe()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='ReadMe' namespace='##targetNamespace'"
	 * @generated
	 */
	ReadMeType getReadMe();

	/**
	 * Sets the value of the '{@link org.tempuri.library.mgmt.LibraryType#getReadMe <em>Read Me</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Read Me</em>' containment reference.
	 * @see #getReadMe()
	 * @generated
	 */
	void setReadMe(ReadMeType value);

	/**
	 * Returns the value of the '<em><b>Sources</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sources</em>' containment reference.
	 * @see #setSources(SourcesType)
	 * @see org.tempuri.library.mgmt.MgmtPackage#getLibraryType_Sources()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Sources' namespace='##targetNamespace'"
	 * @generated
	 */
	SourcesType getSources();

	/**
	 * Sets the value of the '{@link org.tempuri.library.mgmt.LibraryType#getSources <em>Sources</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sources</em>' containment reference.
	 * @see #getSources()
	 * @generated
	 */
	void setSources(SourcesType value);

	/**
	 * Returns the value of the '<em><b>Vendor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Vendor</em>' attribute.
	 * @see #setVendor(String)
	 * @see org.tempuri.library.mgmt.MgmtPackage#getLibraryType_Vendor()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='Vendor' namespace='##targetNamespace'"
	 * @generated
	 */
	String getVendor();

	/**
	 * Sets the value of the '{@link org.tempuri.library.mgmt.LibraryType#getVendor <em>Vendor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Vendor</em>' attribute.
	 * @see #getVendor()
	 * @generated
	 */
	void setVendor(String value);

	/**
	 * Returns the value of the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Comment</em>' attribute.
	 * @see #setComment(String)
	 * @see org.tempuri.library.mgmt.MgmtPackage#getLibraryType_Comment()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='Comment' namespace='##targetNamespace'"
	 * @generated
	 */
	String getComment();

	/**
	 * Sets the value of the '{@link org.tempuri.library.mgmt.LibraryType#getComment <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Comment</em>' attribute.
	 * @see #getComment()
	 * @generated
	 */
	void setComment(String value);

	/**
	 * Returns the value of the '<em><b>License</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>License</em>' containment reference.
	 * @see #setLicense(LicenseType)
	 * @see org.tempuri.library.mgmt.MgmtPackage#getLibraryType_License()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='License' namespace='##targetNamespace'"
	 * @generated
	 */
	LicenseType getLicense();

	/**
	 * Sets the value of the '{@link org.tempuri.library.mgmt.LibraryType#getLicense <em>License</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>License</em>' containment reference.
	 * @see #getLicense()
	 * @generated
	 */
	void setLicense(LicenseType value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.tempuri.library.mgmt.MgmtPackage#getLibraryType_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='Name'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.tempuri.library.mgmt.LibraryType#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Display Name</em>' attribute.
	 * @see #setDisplayName(String)
	 * @see org.tempuri.library.mgmt.MgmtPackage#getLibraryType_DisplayName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='DisplayName'"
	 * @generated
	 */
	String getDisplayName();

	/**
	 * Sets the value of the '{@link org.tempuri.library.mgmt.LibraryType#getDisplayName <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Display Name</em>' attribute.
	 * @see #getDisplayName()
	 * @generated
	 */
	void setDisplayName(String value);

	/**
	 * Returns the value of the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version</em>' attribute.
	 * @see #setVersion(String)
	 * @see org.tempuri.library.mgmt.MgmtPackage#getLibraryType_Version()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='Version'"
	 * @generated
	 */
	String getVersion();

	/**
	 * Sets the value of the '{@link org.tempuri.library.mgmt.LibraryType#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(String value);

} // LibraryType
