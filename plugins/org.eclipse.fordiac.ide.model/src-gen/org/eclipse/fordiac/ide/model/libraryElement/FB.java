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
	 * <p>
	 * If the meaning of the '<em>Resource Type FB</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return false;'"
	 * @generated
	 */
	boolean isResourceTypeFB();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='LibraryElement type = super.getType();\nif(null != type){\n\treturn (FBType) type; \n}\nreturn null;'"
	 * @generated
	 */
	FBType getType();

	/**
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resource FB</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='//A fB is a resource FB if the FB is in the fbnetwork of a resource and\n// the mapping is null or as preperation when we allow to map resource FBs \n//to applications when the mapping from is equal to the fb \nif(getFbNetwork().eContainer() instanceof Resource){\n\treturn (null == getMapping()) || (equals(getMapping().getFrom()));\n}\t\t\nreturn false;'"
	 * @generated
	 */
	boolean isResourceFB();


} // FB
