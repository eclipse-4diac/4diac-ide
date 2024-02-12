/**
 */
package org.eclipse.fordiac.ide.library.model.library;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Manifest</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.library.model.library.Manifest#getProduct <em>Product</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.library.model.library.Manifest#getLibraries <em>Libraries</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.library.model.library.Manifest#getScope <em>Scope</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.library.model.library.LibraryPackage#getManifest()
 * @model extendedMetaData="name='Manifest' kind='elementOnly'"
 * @generated
 */
public interface Manifest extends EObject {
	/**
	 * Returns the value of the '<em><b>Product</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Product</em>' containment reference.
	 * @see #setProduct(Product)
	 * @see org.eclipse.fordiac.ide.library.model.library.LibraryPackage#getManifest_Product()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='Product' namespace='##targetNamespace'"
	 * @generated
	 */
	Product getProduct();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.library.model.library.Manifest#getProduct <em>Product</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Product</em>' containment reference.
	 * @see #getProduct()
	 * @generated
	 */
	void setProduct(Product value);

	/**
	 * Returns the value of the '<em><b>Libraries</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Libraries</em>' containment reference.
	 * @see #setLibraries(Libraries)
	 * @see org.eclipse.fordiac.ide.library.model.library.LibraryPackage#getManifest_Libraries()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='Libraries' namespace='##targetNamespace'"
	 * @generated
	 */
	Libraries getLibraries();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.library.model.library.Manifest#getLibraries <em>Libraries</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Libraries</em>' containment reference.
	 * @see #getLibraries()
	 * @generated
	 */
	void setLibraries(Libraries value);

	/**
	 * Returns the value of the '<em><b>Scope</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Scope</em>' attribute.
	 * @see #setScope(String)
	 * @see org.eclipse.fordiac.ide.library.model.library.LibraryPackage#getManifest_Scope()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='Scope' namespace='##targetNamespace'"
	 * @generated
	 */
	String getScope();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.library.model.library.Manifest#getScope <em>Scope</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Scope</em>' attribute.
	 * @see #getScope()
	 * @generated
	 */
	void setScope(String value);

} // Manifest
