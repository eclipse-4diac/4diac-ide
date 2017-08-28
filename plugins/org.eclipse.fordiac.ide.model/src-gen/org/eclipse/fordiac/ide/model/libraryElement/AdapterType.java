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

import org.eclipse.fordiac.ide.model.data.DataType;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Adapter Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.AdapterType#getAdapterFBType <em>Adapter FB Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getAdapterType()
 * @model
 * @generated
 */
public interface AdapterType extends DataType {
	/**
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Interface List</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return getAdapterFBType().getInterfaceList();'"
	 * @generated
	 */
	InterfaceList getInterfaceList();

	/**
	 * Returns the value of the '<em><b>Adapter FB Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Adapter FB Type</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Adapter FB Type</em>' reference.
	 * @see #setAdapterFBType(AdapterFBType)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getAdapterType_AdapterFBType()
	 * @model resolveProxies="false"
	 *        extendedMetaData="kind='element' name='Service' namespace='##targetNamespace'"
	 * @generated
	 */
	AdapterFBType getAdapterFBType();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.AdapterType#getAdapterFBType <em>Adapter FB Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Adapter FB Type</em>' reference.
	 * @see #getAdapterFBType()
	 * @generated
	 */
	void setAdapterFBType(AdapterFBType value);

	/**
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Plug Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='\t\tAdapterFBType temp = (AdapterFBType)EcoreUtil.copy(getAdapterFBType());\r\n\t\t// fetch the interface to invert it; \r\n\t\tArrayList<Event> inputEvents =  new ArrayList<Event>(temp.getInterfaceList().getEventOutputs());\r\n\t\tfor (Event event : inputEvents) {\r\n\t\t\tevent.setIsInput(true);\r\n\t\t}\r\n\t\tArrayList<Event> outputEvents =  new ArrayList<Event>(temp.getInterfaceList().getEventInputs());\r\n\t\tfor (Event event : outputEvents) {\r\n\t\t\tevent.setIsInput(false);\r\n\t\t}\r\n\t\tArrayList<VarDeclaration> inputVars =  new ArrayList<VarDeclaration>(temp.getInterfaceList().getOutputVars());\r\n\t\tfor (VarDeclaration varDecl : inputVars) {\r\n\t\t\tvarDecl.setIsInput(true);\r\n\t\t}\r\n\t\tArrayList<VarDeclaration> outputVars =  new ArrayList<VarDeclaration>(temp.getInterfaceList().getInputVars());\r\n\t\tfor (VarDeclaration varDecl : outputVars) {\r\n\t\t\tvarDecl.setIsInput(false);\r\n\t\t}\r\n\t\t\r\n\t\ttemp.getInterfaceList().getEventInputs().clear();\r\n\t\ttemp.getInterfaceList().getEventOutputs().clear();\r\n\t\ttemp.getInterfaceList().getInputVars().clear();\r\n\t\ttemp.getInterfaceList().getOutputVars().clear();\r\n\t\t\r\n\t\ttemp.getInterfaceList().getEventInputs().addAll(inputEvents);\r\n\t\ttemp.getInterfaceList().getEventOutputs().addAll(outputEvents);\r\n\t\ttemp.getInterfaceList().getInputVars().addAll(inputVars);\r\n\t\ttemp.getInterfaceList().getOutputVars().addAll(outputVars);\r\n\t\t\r\n\t\treturn temp;'"
	 * @generated
	 */
	AdapterFBType getPlugType();

	/**
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Socket Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return (AdapterFBType)<%org.eclipse.emf.ecore.util.EcoreUtil%>.copy(getAdapterFBType());'"
	 * @generated
	 */
	AdapterFBType getSocketType();
	

} // AdapterType
