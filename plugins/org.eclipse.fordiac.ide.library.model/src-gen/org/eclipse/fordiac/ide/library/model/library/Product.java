/**
 */
package org.eclipse.fordiac.ide.library.model.library;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Product</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.library.model.library.Product#getVersionInfo <em>Version Info</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.library.model.library.Product#getAttribute <em>Attribute</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.library.model.library.LibraryPackage#getProduct()
 * @model extendedMetaData="name='Product' kind='elementOnly'"
 * @generated
 */
public interface Product extends EObject {
	/**
	 * Returns the value of the '<em><b>Version Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version Info</em>' containment reference.
	 * @see #setVersionInfo(VersionInfo)
	 * @see org.eclipse.fordiac.ide.library.model.library.LibraryPackage#getProduct_VersionInfo()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='VersionInfo' namespace='##targetNamespace'"
	 * @generated
	 */
	VersionInfo getVersionInfo();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.library.model.library.Product#getVersionInfo <em>Version Info</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version Info</em>' containment reference.
	 * @see #getVersionInfo()
	 * @generated
	 */
	void setVersionInfo(VersionInfo value);

	/**
	 * Returns the value of the '<em><b>Attribute</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute</em>' attribute list.
	 * @see org.eclipse.fordiac.ide.library.model.library.LibraryPackage#getProduct_Attribute()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='Attribute' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<String> getAttribute();

} // Product
