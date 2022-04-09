/*******************************************************************************
 * Copyright (c) 2008, 2022 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 * 							Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger, Martin Jobst
 *      - initial API and implementation and/or initial documentation
 *    Alois Zoitl  - turned the Palette model into POJOs
 ******************************************************************************/
package org.eclipse.fordiac.ide.model.typelibrary;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;

/** <!-- begin-user-doc --> A representation of the model object '<em><b>Entry</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.model.Palette.PaletteEntry#getLabel <em>Label</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.Palette.PaletteEntry#getFile <em>File</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.Palette.PaletteEntry#getLastModificationTimestamp <em>Last Modification
 * Timestamp</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.Palette.PaletteEntry#getType <em>Type</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.Palette.PaletteEntry#getPalette <em>Palette</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.Palette.PaletteEntry#getTypeEditable <em>Type Editable</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.Palette.PalettePackage#getPaletteEntry()
 * @model abstract="true"
 * @generated */
public interface TypeEntry extends EObject {
	/** Returns the value of the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Label</em>' attribute.
	 * @see #setLabel(String)
	 * @see org.eclipse.fordiac.ide.model.Palette.PalettePackage#getPaletteEntry_Label()
	 * @model
	 * @generated */
	String getLabel();

	/** Sets the value of the '{@link org.eclipse.fordiac.ide.model.Palette.PaletteEntry#getLabel <em>Label</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Label</em>' attribute.
	 * @see #getLabel()
	 * @generated */
	void setLabel(String value);

	/** Returns the value of the '<em><b>File</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>File</em>' attribute.
	 * @see #setFile(IFile)
	 * @see org.eclipse.fordiac.ide.model.Palette.PalettePackage#getPaletteEntry_File()
	 * @model dataType="org.eclipse.fordiac.ide.model.Palette.IFile"
	 * @generated */
	IFile getFile();

	/** Sets the value of the '{@link org.eclipse.fordiac.ide.model.Palette.PaletteEntry#getFile <em>File</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>File</em>' attribute.
	 * @see #getFile()
	 * @generated */
	void setFile(IFile value);

	/** Returns the value of the '<em><b>Last Modification Timestamp</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @return the value of the '<em>Last Modification Timestamp</em>' attribute.
	 * @see #setLastModificationTimestamp(long)
	 * @see org.eclipse.fordiac.ide.model.Palette.PalettePackage#getPaletteEntry_LastModificationTimestamp()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.Long" transient="true" ordered="false"
	 * @generated */
	long getLastModificationTimestamp();

	/** Sets the value of the '{@link org.eclipse.fordiac.ide.model.Palette.PaletteEntry#getLastModificationTimestamp
	 * <em>Last Modification Timestamp</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Last Modification Timestamp</em>' attribute.
	 * @see #getLastModificationTimestamp()
	 * @generated */
	void setLastModificationTimestamp(long value);

	/** Returns the value of the '<em><b>Type</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(LibraryElement)
	 * @see org.eclipse.fordiac.ide.model.Palette.PalettePackage#getPaletteEntry_Type()
	 * @model resolveProxies="false" required="true"
	 * @generated */
	LibraryElement getType();

	/** Sets the value of the '{@link org.eclipse.fordiac.ide.model.Palette.PaletteEntry#getType <em>Type</em>}'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated */
	void setType(LibraryElement value);


	/** Returns the value of the '<em><b>Type Editable</b></em>' reference. It is bidirectional and its opposite is
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.LibraryElement#getPaletteEntry <em>Palette Entry</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Type Editable</em>' reference.
	 * @see #setTypeEditable(LibraryElement)
	 * @see org.eclipse.fordiac.ide.model.Palette.PalettePackage#getPaletteEntry_TypeEditable()
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElement#getPaletteEntry
	 * @model opposite="paletteEntry" resolveProxies="false" required="true"
	 * @generated */
	LibraryElement getTypeEditable();

	/** Sets the value of the '{@link org.eclipse.fordiac.ide.model.Palette.PaletteEntry#getTypeEditable <em>Type
	 * Editable</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Type Editable</em>' reference.
	 * @see #getTypeEditable()
	 * @generated */
	void setTypeEditable(LibraryElement value);

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @model kind="operation" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 * @generated */
	String getProjectRelativeTypePath();

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @model kind="operation" dataType="org.eclipse.fordiac.ide.model.Palette.TypeLibrary"
	 * @generated */
	TypeLibrary getTypeLibrary();

	void setTypeLibrary(TypeLibrary typeLib);

} // PaletteEntry
