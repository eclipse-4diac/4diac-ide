/**
 */
package org.eclipse.fordiac.ide.library.model.library;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Includes</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.library.model.library.Includes#getLibraryElement <em>Library Element</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.library.model.library.LibraryPackage#getIncludes()
 * @model extendedMetaData="name='Includes' kind='elementOnly'"
 * @generated
 */
public interface Includes extends EObject {
	/**
	 * Returns the value of the '<em><b>Library Element</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.library.model.library.LibraryElement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Library Element</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.library.model.library.LibraryPackage#getIncludes_LibraryElement()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='LibraryElement' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<LibraryElement> getLibraryElement();

} // Includes
