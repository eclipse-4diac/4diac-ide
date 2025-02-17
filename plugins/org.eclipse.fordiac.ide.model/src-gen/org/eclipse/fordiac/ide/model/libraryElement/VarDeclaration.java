/**
 * *******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *               2022 Martin Erich Jobst
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

import java.util.Map;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Var Declaration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration#getArraySize <em>Array Size</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration#getWiths <em>Withs</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getVarDeclaration()
 * @model
 * @generated
 */
public interface VarDeclaration extends IInterfaceElement {
	/**
	 * Returns the value of the '<em><b>Array Size</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.fordiac.ide.model.libraryElement.ArraySize#getVarDeclaration <em>Var Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Array Size</em>' containment reference.
	 * @see #setArraySize(ArraySize)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getVarDeclaration_ArraySize()
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ArraySize#getVarDeclaration
	 * @model opposite="varDeclaration" containment="true" resolveProxies="true"
	 * @generated
	 */
	ArraySize getArraySize();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration#getArraySize <em>Array Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Array Size</em>' containment reference.
	 * @see #getArraySize()
	 * @generated
	 */
	void setArraySize(ArraySize value);

	/**
	 * Returns the value of the '<em><b>Withs</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.model.libraryElement.With}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.fordiac.ide.model.libraryElement.With#getVariables <em>Variables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Withs</em>' reference list.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getVarDeclaration_Withs()
	 * @see org.eclipse.fordiac.ide.model.libraryElement.With#getVariables
	 * @model opposite="variables"
	 * @generated
	 */
	EList<With> getWiths();

	/**
	 * Returns the value of the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' containment reference.
	 * @see #setValue(Value)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getVarDeclaration_Value()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	Value getValue();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration#getValue <em>Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' containment reference.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(Value value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	boolean isVarConfig();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	boolean isArray();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model configDataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	void setVarConfig(boolean config);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 * @generated
	 */
	String getFullTypeName();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 * @generated
	 */
	boolean isInOutVar();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/Ecore invariant='true'"
	 * @generated
	 */
	boolean validateVarInOutSourceTypeIsWellDefined(DiagnosticChain diagnostics, Map<Object, Object> context);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	VarDeclaration getInOutVarOpposite();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/Ecore invariant='true'"
	 * @generated
	 */
	boolean validateMultipleInputConnections(DiagnosticChain diagnostics, Map<Object, Object> context);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/Ecore invariant='true'"
	 * @generated
	 */
	boolean validateNoValueForGenericTypeVariable(DiagnosticChain diagnostics, Map<Object, Object> context);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/Ecore invariant='true'"
	 * @generated
	 */
	boolean validateNoValueForVariableLengthArrayVariable(DiagnosticChain diagnostics, Map<Object, Object> context);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/Ecore invariant='true'"
	 * @generated
	 */
	boolean validateValueForGenericInstanceVariable(DiagnosticChain diagnostics, Map<Object, Object> context);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/Ecore invariant='true'"
	 * @generated
	 */
	boolean validateVarInOutIsWithed(DiagnosticChain diagnostics, Map<Object, Object> context);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/Ecore invariant='true'"
	 * @generated
	 */
	boolean validateVarInOutSubappInterface(DiagnosticChain diagnostics, Map<Object, Object> context);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/Ecore invariant='true'"
	 * @generated
	 */
	boolean validateVarInOutSubappNetwork(DiagnosticChain diagnostics, Map<Object, Object> context);

} // VarDeclaration
