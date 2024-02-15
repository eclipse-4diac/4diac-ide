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
 *   <li>{@link org.eclipse.fordiac.ide.library.model.library.Manifest#getDependencies <em>Dependencies</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.library.model.library.Manifest#getProduct <em>Product</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.library.model.library.Manifest#getExports <em>Exports</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.library.model.library.Manifest#getScope <em>Scope</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.library.model.library.LibraryPackage#getManifest()
 * @model extendedMetaData="name='Manifest' kind='elementOnly'"
 * @generated
 */
public interface Manifest extends EObject {
	/**
	 * Returns the value of the '<em><b>Dependencies</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dependencies</em>' containment reference.
	 * @see #setDependencies(Dependencies)
	 * @see org.eclipse.fordiac.ide.library.model.library.LibraryPackage#getManifest_Dependencies()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='Dependencies' namespace='##targetNamespace'"
	 * @generated
	 */
	Dependencies getDependencies();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.library.model.library.Manifest#getDependencies <em>Dependencies</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dependencies</em>' containment reference.
	 * @see #getDependencies()
	 * @generated
	 */
	void setDependencies(Dependencies value);

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
	 * Returns the value of the '<em><b>Exports</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exports</em>' containment reference.
	 * @see #setExports(Exports)
	 * @see org.eclipse.fordiac.ide.library.model.library.LibraryPackage#getManifest_Exports()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='Exports' namespace='##targetNamespace'"
	 * @generated
	 */
	Exports getExports();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.library.model.library.Manifest#getExports <em>Exports</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exports</em>' containment reference.
	 * @see #getExports()
	 * @generated
	 */
	void setExports(Exports value);

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
