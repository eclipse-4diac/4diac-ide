/*******************************************************************************
 * Copyright (c) 2012 Profactor GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtester.model.testdata;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Test Data</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.fbtester.model.testdata.TestData#getTestName <em>Test
 * Name</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fbtester.model.testdata.TestData#getEvent <em>Event
 * </em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fbtester.model.testdata.TestData#getTestIntstance <em>
 * Test Intstance</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fbtester.model.testdata.TestData#getEventOutputs <em>
 * Event Outputs</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fbtester.model.testdata.TestData#getValues <em>Values
 * </em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fbtester.model.testdata.TestData#getResults <em>
 * Results</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fbtester.model.testdata.TestData#getLine <em>Line
 * </em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fbtester.model.testdata.TestData#getType <em>Type
 * </em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.fordiac.ide.fbtester.model.testdata.TestdataPackage#getTestData()
 * @model
 * @generated
 */
public interface TestData extends EObject {
	/**
	 * Returns the value of the '<em><b>Test Name</b></em>' attribute. The default
	 * value is <code>"testName"</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Test Name</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Test Name</em>' attribute.
	 * @see #setTestName(String)
	 * @see org.eclipse.fordiac.ide.fbtester.model.testdata.TestdataPackage#getTestData_TestName()
	 * @model default="testName"
	 * @generated
	 */
	String getTestName();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.fordiac.ide.fbtester.model.testdata.TestData#getTestName
	 * <em>Test Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value
	 *          the new value of the '<em>Test Name</em>' attribute.
	 * @see #getTestName()
	 * @generated
	 */
	void setTestName(String value);

	/**
	 * Returns the value of the '<em><b>Event</b></em>' reference. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Event</em>' reference isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Event</em>' reference.
	 * @see #setEvent(Event)
	 * @see org.eclipse.fordiac.ide.fbtester.model.testdata.TestdataPackage#getTestData_Event()
	 * @model
	 * @generated
	 */
	Event getEvent();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.fordiac.ide.fbtester.model.testdata.TestData#getEvent
	 * <em>Event</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *          the new value of the '<em>Event</em>' reference.
	 * @see #getEvent()
	 * @generated
	 */
	void setEvent(Event value);

	/**
	 * Returns the value of the '<em><b>Test Intstance</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Test Intstance</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Test Intstance</em>' attribute.
	 * @see #setTestIntstance(String)
	 * @see org.eclipse.fordiac.ide.fbtester.model.testdata.TestdataPackage#getTestData_TestIntstance()
	 * @model
	 * @generated
	 */
	String getTestIntstance();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.fordiac.ide.fbtester.model.testdata.TestData#getTestIntstance
	 * <em>Test Intstance</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *          the new value of the '<em>Test Intstance</em>' attribute.
	 * @see #getTestIntstance()
	 * @generated
	 */
	void setTestIntstance(String value);

	/**
	 * Returns the value of the '<em><b>Event Outputs</b></em>' reference list.
	 * The list contents are of type
	 * {@link org.eclipse.fordiac.ide.model.libraryElement.Event}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Event Outputs</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Event Outputs</em>' reference list.
	 * @see org.eclipse.fordiac.ide.fbtester.model.testdata.TestdataPackage#getTestData_EventOutputs()
	 * @model
	 * @generated
	 */
	EList<Event> getEventOutputs();

	/**
	 * Returns the value of the '<em><b>Values</b></em>' reference list. The list
	 * contents are of type
	 * {@link org.eclipse.fordiac.ide.fbtester.model.testdata.ValuedVarDecl}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Values</em>' reference list isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Values</em>' reference list.
	 * @see org.eclipse.fordiac.ide.fbtester.model.testdata.TestdataPackage#getTestData_Values()
	 * @model
	 * @generated
	 */
	EList<ValuedVarDecl> getValues();

	/**
	 * Returns the value of the '<em><b>Results</b></em>' reference list. The list
	 * contents are of type
	 * {@link org.eclipse.fordiac.ide.fbtester.model.testdata.ValuedVarDecl}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Results</em>' reference list isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Results</em>' reference list.
	 * @see org.eclipse.fordiac.ide.fbtester.model.testdata.TestdataPackage#getTestData_Results()
	 * @model
	 * @generated
	 */
	EList<ValuedVarDecl> getResults();

	/**
	 * Returns the value of the '<em><b>Line</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Line</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Line</em>' attribute.
	 * @see #setLine(String)
	 * @see org.eclipse.fordiac.ide.fbtester.model.testdata.TestdataPackage#getTestData_Line()
	 * @model
	 * @generated
	 */
	String getLine();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.fordiac.ide.fbtester.model.testdata.TestData#getLine <em>Line</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *          the new value of the '<em>Line</em>' attribute.
	 * @see #getLine()
	 * @generated
	 */
	void setLine(String value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' reference isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(FBType)
	 * @see org.eclipse.fordiac.ide.fbtester.model.testdata.TestdataPackage#getTestData_Type()
	 * @model
	 * @generated
	 */
	FBType getType();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.fordiac.ide.fbtester.model.testdata.TestData#getType <em>Type</em>}'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *          the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(FBType value);

	String getValueFor(String portString);

	void setValueFor(String text, String value);

	String getResultFor(String portString);

	void setResultFor(String text, String value);

	String getOutputEvents();

	public String _getLine();

} // TestData
