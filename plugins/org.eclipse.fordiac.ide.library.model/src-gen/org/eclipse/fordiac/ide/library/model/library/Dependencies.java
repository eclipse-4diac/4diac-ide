/**
 */
package org.eclipse.fordiac.ide.library.model.library;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Dependencies</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.library.model.library.Dependencies#getRequired <em>Required</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.library.model.library.LibraryPackage#getDependencies()
 * @model extendedMetaData="name='Dependencies' kind='elementOnly'"
 * @generated
 */
public interface Dependencies extends EObject {
	/**
	 * Returns the value of the '<em><b>Required</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.library.model.library.Required}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Required</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.library.model.library.LibraryPackage#getDependencies_Required()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Required' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<Required> getRequired();

} // Dependencies
