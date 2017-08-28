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

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Configurable Object</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject#getParameter <em>Parameter</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getConfigurableObject()
 * @model
 * @generated
 */
public interface ConfigurableObject extends INamedElement {
	/**
	 * Returns the value of the '<em><b>Parameter</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.model.libraryElement.Parameter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameter</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameter</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getConfigurableObject_Parameter()
	 * @model containment="true" resolveProxies="true" keys="name"
	 * @generated
	 */
	EList<Parameter> getParameter();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model parameterNameDataType="org.eclipse.emf.ecore.xml.type.String" valueDataType="org.eclipse.emf.ecore.xml.type.String"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='\t\tParameter parameter = null;\r\n\t\t// find already existing parameter with parameterName\r\n\t\tfor (Parameter param : getParameter()) {\r\n\t\t\tif (param.getName().equalsIgnoreCase(parameterName)) {\r\n\t\t\t\tparameter = param;\r\n\t\t\t\tbreak;\r\n\t\t\t}\r\n\t\t}\r\n\t\tif (parameter == null) {\r\n\t\t\tparameter = LibraryElementFactory.eINSTANCE.createParameter();\r\n\t\t\tparameter.setName(parameterName);\r\n\t\t\tparameter.setValue(value);\r\n\t\t\tgetParameter().add(parameter);\r\n\t\t} else {\r\n\t\t\tparameter.setValue(value);\r\n\t\t}'"
	 * @generated
	 */
	void setParameter(String parameterName, String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" parameterNameDataType="org.eclipse.emf.ecore.xml.type.String"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='\t\tif (parameterName == null) {\r\n\t\t\treturn null;\r\n\t\t}\r\n\t\tfor (Parameter param : getParameter()) {\r\n\t\t\tif (param.getName().equalsIgnoreCase(parameterName)) {\r\n\t\t\t\treturn param.getValue();\r\n\t\t\t}\r\n\t\t}\r\n\t\treturn null;'"
	 * @generated
	 */
	String getParameter(String parameterName);

} // ConfigurableObject
