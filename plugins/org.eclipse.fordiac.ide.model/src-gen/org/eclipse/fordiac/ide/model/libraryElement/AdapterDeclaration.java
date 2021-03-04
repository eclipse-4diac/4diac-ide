/********************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.libraryElement;

import org.eclipse.fordiac.ide.model.Palette.AdapterTypePaletteEntry;

/** <!-- begin-user-doc --> A representation of the model object '<em><b>Adapter Declaration</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration#getAdapterFB <em>Adapter FB</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration#getPaletteEntry <em>Palette
 * Entry</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getAdapterDeclaration()
 * @model
 * @generated */
public interface AdapterDeclaration extends VarDeclaration {

	/** Returns the value of the '<em><b>Adapter FB</b></em>' reference. It is bidirectional and its opposite is
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.AdapterFB#getAdapterDecl <em>Adapter Decl</em>}'. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Adapter FB</em>' reference isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Adapter FB</em>' reference.
	 * @see #setAdapterFB(AdapterFB)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getAdapterDeclaration_AdapterFB()
	 * @see org.eclipse.fordiac.ide.model.libraryElement.AdapterFB#getAdapterDecl
	 * @model opposite="adapterDecl"
	 * @generated */
	AdapterFB getAdapterFB();

	/** Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration#getAdapterFB
	 * <em>Adapter FB</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Adapter FB</em>' reference.
	 * @see #getAdapterFB()
	 * @generated */
	void setAdapterFB(AdapterFB value);

	/** Returns the value of the '<em><b>Palette Entry</b></em>' reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Palette Entry</em>' reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Palette Entry</em>' reference.
	 * @see #setPaletteEntry(AdapterTypePaletteEntry)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getAdapterDeclaration_PaletteEntry()
	 * @model
	 * @generated */
	AdapterTypePaletteEntry getPaletteEntry();

	/** Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration#getPaletteEntry
	 * <em>Palette Entry</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Palette Entry</em>' reference.
	 * @see #getPaletteEntry()
	 * @generated */
	void setPaletteEntry(AdapterTypePaletteEntry value);

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model kind="operation"
	 * @generated */
	@Override
	AdapterType getType();

} // AdapterDeclaration
