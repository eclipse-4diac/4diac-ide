/********************************************************************************
 * Copyright (c) 2008, 2010 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.Palette;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.CompilableType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Group</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.Palette.PaletteGroup#getEntries <em>Entries</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.Palette.PaletteGroup#getSubGroups <em>Sub Groups</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.Palette.PaletteGroup#getLabel <em>Label</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.Palette.PalettePackage#getPaletteGroup()
 * @model
 * @generated
 */
public interface PaletteGroup extends EObject {
	/**
	 * Returns the value of the '<em><b>Entries</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.model.Palette.PaletteEntry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entries</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.model.Palette.PalettePackage#getPaletteGroup_Entries()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	EList<PaletteEntry> getEntries();

	/**
	 * Returns the value of the '<em><b>Sub Groups</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.model.Palette.PaletteGroup}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sub Groups</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sub Groups</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.model.Palette.PalettePackage#getPaletteGroup_SubGroups()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	EList<PaletteGroup> getSubGroups();

	/**
	 * Returns the value of the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Label</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Label</em>' attribute.
	 * @see #setLabel(String)
	 * @see org.eclipse.fordiac.ide.model.Palette.PalettePackage#getPaletteGroup_Label()
	 * @model
	 * @generated
	 */
	String getLabel();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.Palette.PaletteGroup#getLabel <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label</em>' attribute.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(String value);

	public void addEntry(PaletteEntry entry);

	boolean isEmpty();

	PaletteGroup getGroup(String label);

	CompilableType getTypeforName(String name);

	Palette getPallete();

	void removeEntryNamed(String entryName);

	PaletteEntry getEntry(String entryName);

	PaletteGroup getParentGroup();

} // PaletteGroup
