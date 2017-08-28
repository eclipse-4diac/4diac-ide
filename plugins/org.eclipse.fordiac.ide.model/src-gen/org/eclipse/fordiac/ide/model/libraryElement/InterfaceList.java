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
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Interface List</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.InterfaceList#getPlugs <em>Plugs</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.InterfaceList#getSockets <em>Sockets</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.InterfaceList#getEventInputs <em>Event Inputs</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.InterfaceList#getEventOutputs <em>Event Outputs</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.InterfaceList#getInputVars <em>Input Vars</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.InterfaceList#getOutputVars <em>Output Vars</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getInterfaceList()
 * @model
 * @generated
 */
public interface InterfaceList extends EObject {
	/**
	 * Returns the value of the '<em><b>Plugs</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Plugs</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Plugs</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getInterfaceList_Plugs()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='AdapterDeclaration' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<AdapterDeclaration> getPlugs();

	/**
	 * Returns the value of the '<em><b>Sockets</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sockets</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sockets</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getInterfaceList_Sockets()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='AdapterDeclaration' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<AdapterDeclaration> getSockets();
	
	/**
	 * Returns the value of the '<em><b>Event Inputs</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.model.libraryElement.Event}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Event Inputs</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Event Inputs</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getInterfaceList_EventInputs()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='SubAppEventInputs' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<Event> getEventInputs();

	/**
	 * Returns the value of the '<em><b>Event Outputs</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.model.libraryElement.Event}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Event Outputs</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Event Outputs</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getInterfaceList_EventOutputs()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='SubAppEventOutputs' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<Event> getEventOutputs();

	/**
	 * Returns the value of the '<em><b>Input Vars</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Input Vars</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Input Vars</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getInterfaceList_InputVars()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='InputVars' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<VarDeclaration> getInputVars();

	/**
	 * Returns the value of the '<em><b>Output Vars</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Output Vars</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Output Vars</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getInterfaceList_OutputVars()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='OutputVars' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<VarDeclaration> getOutputVars();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true" many="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='EList&lt;IInterfaceElement&gt; retVal = new BasicEList&lt;IInterfaceElement&gt;();\t\t\nretVal.addAll(getEventInputs());\nretVal.addAll(getEventOutputs());\nretVal.addAll(getInputVars());\nretVal.addAll(getOutputVars());\nretVal.addAll(getPlugs());\nretVal.addAll(getSockets());\nreturn retVal;'"
	 * @generated
	 */
	EList<IInterfaceElement> getAllInterfaceElements();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model nameRequired="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='for (Event event : getEventInputs()) {\n\tif (event.getName().equals(name))\r\n\treturn event;\r\n}\r\nfor (Event event : getEventOutputs()) {\r\n\tif (event.getName().equals(name))\r\n\t\treturn event;\r\n}\r\n\nreturn null;'"
	 * @generated
	 */
	Event getEvent(String name);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model nameRequired="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='for (VarDeclaration var : getInputVars()) {\r\n\tif (var.getName().equals(name))\r\n\t\treturn var;\r\n}\r\nfor (VarDeclaration var : getOutputVars()) {\r\n\tif (var.getName().equals(name))\r\n\t\treturn var;\r\n}\r\nreturn null;'"
	 * @generated
	 */
	VarDeclaration getVariable(String name);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model required="true" nameRequired="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='IInterfaceElement element = getEvent(name);\nif (element == null) {\n\telement = getVariable(name);\n}\nif (element == null) {\n\telement = getAdapter(name);\n}\nreturn element;\n'"
	 * @generated
	 */
	IInterfaceElement getInterfaceElement(String name);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='//an FB should mostly in an FBNetworkElement otherwise it is in CFB interface this is at the same time also a null check\nreturn (eContainer() instanceof FBNetworkElement) ? (FBNetworkElement)eContainer() : null;'"
	 * @generated
	 */
	FBNetworkElement getFBNetworkElement();

} // InterfaceList
