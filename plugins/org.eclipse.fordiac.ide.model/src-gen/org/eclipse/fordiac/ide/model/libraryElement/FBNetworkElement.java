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
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>FB Network Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement#getInterface <em>Interface</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement#getMapping <em>Mapping</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getFBNetworkElement()
 * @model
 * @generated
 */
public interface FBNetworkElement extends TypedConfigureableObject, PositionableElement {
	/**
	 * Returns the value of the '<em><b>Interface</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Interface</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Interface</em>' containment reference.
	 * @see #setInterface(InterfaceList)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getFBNetworkElement_Interface()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	InterfaceList getInterface();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement#getInterface <em>Interface</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Interface</em>' containment reference.
	 * @see #getInterface()
	 * @generated
	 */
	void setInterface(InterfaceList value);

	/**
	 * Returns the value of the '<em><b>Mapping</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mapping</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mapping</em>' reference.
	 * @see #setMapping(Mapping)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getFBNetworkElement_Mapping()
	 * @model
	 * @generated
	 */
	Mapping getMapping();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement#getMapping <em>Mapping</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mapping</em>' reference.
	 * @see #getMapping()
	 * @generated
	 */
	void setMapping(Mapping value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='if(null != getFbNetwork() && getFbNetwork().eContainer() instanceof Resource){\n\treturn (Resource)getFbNetwork().eContainer();\n} else if(isMapped()){\n\t//get the Resource of the mapped FB\n\treturn getMapping().getTo().getResource();\n}\nreturn null;'"
	 * @generated
	 */
	Resource getResource();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model required="true" nameRequired="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='if (getInterface() != null) {\n\treturn getInterface().getInterfaceElement(name);\n}\nreturn null;'"
	 * @generated
	 */
	IInterfaceElement getInterfaceElement(String name);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='//try to find the other coresponding mapped entity if this FBNetworkElement is mapped\nif(isMapped()){\n\treturn (this == getMapping().getFrom()) ? getMapping().getTo() : getMapping().getFrom();  \n}else{\n\t//TODO model refactoring - if element part of subapp that is mapped recursivly find the according mapped entity \n}\nreturn null;'"
	 * @generated
	 */
	FBNetworkElement getOpposite();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='//an FB should always be put in an fbNetwork this is at the same time also a null check\nreturn (eContainer() instanceof FBNetwork) ? (FBNetwork)eContainer() : null;\n'"
	 * @generated
	 */
	FBNetwork getFbNetwork();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='for (IInterfaceElement element : getInterface().getAllInterfaceElements()) {\r\n\t//todo when lambdas are better allowed in EMF replace with .forEach(conn -> conn.checkIfConnectionBroken());\r\n\tfor (org.eclipse.fordiac.ide.model.libraryElement.Connection conn : element.getInputConnections()) {\r\n\t\tconn.checkIfConnectionBroken();\r\n\t}\r\n\tfor (org.eclipse.fordiac.ide.model.libraryElement.Connection conn : element.getOutputConnections()) {\r\n\t\tconn.checkIfConnectionBroken();\r\n\t}\r\n}'"
	 * @generated
	 */
	void checkConnections();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return null != getMapping();'"
	 * @generated
	 */
	boolean isMapped();

} // FBNetworkElement
