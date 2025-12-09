/**
 */
package org.eclipse.fordiac.ide.library.model.library;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Exports</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.library.model.library.Exports#getLibrary <em>Library</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.library.model.library.LibraryPackage#getExports()
 * @model extendedMetaData="name='Exports' kind='elementOnly'"
 * @generated
 */
public interface Exports extends EObject {
	/**
	 * Returns the value of the '<em><b>Library</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.library.model.library.Library}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Library</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.library.model.library.LibraryPackage#getExports_Library()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Library' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<Library> getLibrary();

} // Exports
