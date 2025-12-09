/**
 */
package org.eclipse.fordiac.ide.library.model.library;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Version Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.library.model.library.VersionInfo#getAuthor <em>Author</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.library.model.library.VersionInfo#getDate <em>Date</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.library.model.library.VersionInfo#getVersion <em>Version</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.library.model.library.LibraryPackage#getVersionInfo()
 * @model extendedMetaData="name='VersionInfo' kind='empty'"
 * @generated
 */
public interface VersionInfo extends EObject {
	/**
	 * Returns the value of the '<em><b>Author</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Author</em>' attribute.
	 * @see #setAuthor(String)
	 * @see org.eclipse.fordiac.ide.library.model.library.LibraryPackage#getVersionInfo_Author()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='Author' namespace='##targetNamespace'"
	 * @generated
	 */
	String getAuthor();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.library.model.library.VersionInfo#getAuthor <em>Author</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Author</em>' attribute.
	 * @see #getAuthor()
	 * @generated
	 */
	void setAuthor(String value);

	/**
	 * Returns the value of the '<em><b>Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Date</em>' attribute.
	 * @see #setDate(String)
	 * @see org.eclipse.fordiac.ide.library.model.library.LibraryPackage#getVersionInfo_Date()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='Date' namespace='##targetNamespace'"
	 * @generated
	 */
	String getDate();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.library.model.library.VersionInfo#getDate <em>Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Date</em>' attribute.
	 * @see #getDate()
	 * @generated
	 */
	void setDate(String value);

	/**
	 * Returns the value of the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version</em>' attribute.
	 * @see #setVersion(String)
	 * @see org.eclipse.fordiac.ide.library.model.library.LibraryPackage#getVersionInfo_Version()
	 * @model dataType="org.eclipse.fordiac.ide.library.model.library.VersionSimple" required="true"
	 *        extendedMetaData="kind='attribute' name='Version' namespace='##targetNamespace'"
	 * @generated
	 */
	String getVersion();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.library.model.library.VersionInfo#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(String value);

} // VersionInfo
