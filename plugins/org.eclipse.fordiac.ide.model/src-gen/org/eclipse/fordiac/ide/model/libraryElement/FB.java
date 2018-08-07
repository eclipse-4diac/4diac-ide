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


/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>FB</b></em>'. <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getFB()
 * @model
 * @generated
 */
public interface FB extends FBNetworkElement {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='//this cannot be moved to the annotation class because there we don\'t have the super access!!!\r\norg.eclipse.fordiac.ide.model.libraryElement.LibraryElement type = super.getType();\r\nif(type instanceof FBType){\r\n\treturn (FBType) type; \r\n}\r\nreturn null;'"
	 * @generated
	 */
	@Override
	FBType getType();

	/**
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resource Type FB</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return org.eclipse.fordiac.ide.model.Annotations.isResourceTypeFB(this);'"
	 * @generated
	 */
	boolean isResourceTypeFB();

	/**
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resource FB</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return org.eclipse.fordiac.ide.model.Annotations.isResourceFB(this);'"
	 * @generated
	 */
	boolean isResourceFB();


} // FB
