/********************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.libraryElement;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Service
 * Transaction</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction#getInputPrimitive
 * <em>Input Primitive</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction#getOutputPrimitive
 * <em>Output Primitive</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction#getTestResult
 * <em>Test Result</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getServiceTransaction()
 * @model
 * @generated
 */
public interface ServiceTransaction extends EObject {
	/**
	 * Returns the value of the '<em><b>Input Primitive</b></em>' containment
	 * reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Input Primitive</em>' containment reference isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Input Primitive</em>' containment reference.
	 * @see #setInputPrimitive(InputPrimitive)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getServiceTransaction_InputPrimitive()
	 * @model containment="true" extendedMetaData="kind='element'
	 *        name='InputPrimitive' namespace='##targetNamespace'"
	 * @generated
	 */
	InputPrimitive getInputPrimitive();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction#getInputPrimitive
	 * <em>Input Primitive</em>}' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Input Primitive</em>' containment
	 *              reference.
	 * @see #getInputPrimitive()
	 * @generated
	 */
	void setInputPrimitive(InputPrimitive value);

	/**
	 * Returns the value of the '<em><b>Output Primitive</b></em>' containment
	 * reference list. The list contents are of type
	 * {@link org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Output Primitive</em>' containment reference list
	 * isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Output Primitive</em>' containment reference
	 *         list.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getServiceTransaction_OutputPrimitive()
	 * @model containment="true" extendedMetaData="kind='element'
	 *        name='OutputPrimitive' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<OutputPrimitive> getOutputPrimitive();

	/**
	 * Returns the value of the '<em><b>Test Result</b></em>' attribute. The default
	 * value is <code>"0"</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Test Result</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Test Result</em>' attribute.
	 * @see #isSetTestResult()
	 * @see #unsetTestResult()
	 * @see #setTestResult(int)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getServiceTransaction_TestResult()
	 * @model default="0" unique="false" unsettable="true"
	 *        dataType="org.eclipse.emf.ecore.xml.type.Int" volatile="true"
	 *        ordered="false"
	 * @generated
	 */
	int getTestResult();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction#getTestResult
	 * <em>Test Result</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value the new value of the '<em>Test Result</em>' attribute.
	 * @see #isSetTestResult()
	 * @see #unsetTestResult()
	 * @see #getTestResult()
	 * @generated
	 */
	void setTestResult(int value);

	/**
	 * Unsets the value of the
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction#getTestResult
	 * <em>Test Result</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see #isSetTestResult()
	 * @see #getTestResult()
	 * @see #setTestResult(int)
	 * @generated
	 */
	void unsetTestResult();

	/**
	 * Returns whether the value of the
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction#getTestResult
	 * <em>Test Result</em>}' attribute is set. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return whether the value of the '<em>Test Result</em>' attribute is set.
	 * @see #unsetTestResult()
	 * @see #getTestResult()
	 * @see #setTestResult(int)
	 * @generated
	 */
	boolean isSetTestResult();

	public static final int NOT_TESTED = 0;
	public static final int TEST_OK = 1;
	public static final int TEST_FAIL = -1;

} // ServiceTransaction
