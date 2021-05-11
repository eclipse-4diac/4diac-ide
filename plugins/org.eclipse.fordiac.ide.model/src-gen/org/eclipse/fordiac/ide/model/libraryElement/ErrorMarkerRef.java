/**
 * *******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH
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
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.model.libraryElement;

import org.eclipse.emf.ecore.EObject;

/** <!-- begin-user-doc --> A representation of the model object '<em><b>Error Marker Ref</b></em>'. <!-- end-user-doc
 * -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerRef#getFileMarkerId <em>File Marker Id</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getErrorMarkerRef()
 * @model
 * @generated */
public interface ErrorMarkerRef extends EObject {
	/** Returns the value of the '<em><b>File Marker Id</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the value of the '<em>File Marker Id</em>' attribute.
	 * @see #setFileMarkerId(long)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getErrorMarkerRef_FileMarkerId()
	 * @model
	 * @generated */
	long getFileMarkerId();

	/** Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerRef#getFileMarkerId
	 * <em>File Marker Id</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>File Marker Id</em>' attribute.
	 * @see #getFileMarkerId()
	 * @generated */
	void setFileMarkerId(long value);

} // ErrorMarkerRef
