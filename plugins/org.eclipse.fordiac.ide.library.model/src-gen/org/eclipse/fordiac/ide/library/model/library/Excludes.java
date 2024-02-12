/**
 */
package org.eclipse.fordiac.ide.library.model.library;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Excludes</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.library.model.library.Excludes#getLibraryElement <em>Library Element</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.library.model.library.LibraryPackage#getExcludes()
 * @model extendedMetaData="name='Excludes' kind='elementOnly'"
 * @generated
 */
public interface Excludes extends EObject {
	/**
	 * Returns the value of the '<em><b>Library Element</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.library.model.library.LibraryElement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Library Element</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.library.model.library.LibraryPackage#getExcludes_LibraryElement()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='LibraryElement' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<LibraryElement> getLibraryElement();

} // Excludes
