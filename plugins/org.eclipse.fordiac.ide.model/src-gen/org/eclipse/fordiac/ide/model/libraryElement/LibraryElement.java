/********************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.libraryElement;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Library Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.LibraryElement#getVersionInfo <em>Version Info</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.LibraryElement#getIdentification <em>Identification</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.LibraryElement#getPaletteEntry <em>Palette Entry</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getLibraryElement()
 * @model
 * @generated
 */
public interface LibraryElement extends INamedElement {
	/**
	 * Returns the value of the '<em><b>Version Info</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.model.libraryElement.VersionInfo}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Version Info</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version Info</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getLibraryElement_VersionInfo()
	 * @model containment="true" resolveProxies="true" required="true"
	 * @generated
	 */
	EList<VersionInfo> getVersionInfo();

	/**
	 * Returns the value of the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Identification</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Identification</em>' containment reference.
	 * @see #setIdentification(Identification)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getLibraryElement_Identification()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	Identification getIdentification();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.LibraryElement#getIdentification <em>Identification</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Identification</em>' containment reference.
	 * @see #getIdentification()
	 * @generated
	 */
	void setIdentification(Identification value);

	/**
	 * Returns the value of the '<em><b>Palette Entry</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.fordiac.ide.model.Palette.PaletteEntry#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Palette Entry</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Palette Entry</em>' reference.
	 * @see #setPaletteEntry(PaletteEntry)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getLibraryElement_PaletteEntry()
	 * @see org.eclipse.fordiac.ide.model.Palette.PaletteEntry#getType
	 * @model opposite="type" resolveProxies="false" transient="true"
	 * @generated
	 */
	PaletteEntry getPaletteEntry();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.LibraryElement#getPaletteEntry <em>Palette Entry</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Palette Entry</em>' reference.
	 * @see #getPaletteEntry()
	 * @generated
	 */
	void setPaletteEntry(PaletteEntry value);

} // LibraryElement
