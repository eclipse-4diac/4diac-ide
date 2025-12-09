/**
 */
package org.eclipse.fordiac.ide.library.model.library;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.library.model.library.LibraryElement#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.library.model.library.LibraryPackage#getLibraryElement()
 * @model extendedMetaData="name='LibraryElement' kind='simple'"
 * @generated
 */
public interface LibraryElement extends EObject {

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see org.eclipse.fordiac.ide.library.model.library.LibraryPackage#getLibraryElement_Value()
	 * @model dataType="org.eclipse.fordiac.ide.library.model.library.NameSpaceFilter"
	 *        extendedMetaData="name=':0' kind='simple'"
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.library.model.library.LibraryElement#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);
} // LibraryElement
