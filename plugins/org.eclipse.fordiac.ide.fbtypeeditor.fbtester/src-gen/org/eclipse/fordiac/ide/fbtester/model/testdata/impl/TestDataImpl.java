/*******************************************************************************
 * Copyright (c) 2012 Profactor GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtester.model.testdata.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.fordiac.ide.fbtester.model.testdata.TestData;
import org.eclipse.fordiac.ide.fbtester.model.testdata.TestdataFactory;
import org.eclipse.fordiac.ide.fbtester.model.testdata.TestdataPackage;
import org.eclipse.fordiac.ide.fbtester.model.testdata.ValuedVarDecl;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;


/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Test Data</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.fbtester.model.testdata.impl.TestDataImpl#getTestName
 * <em>Test Name</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fbtester.model.testdata.impl.TestDataImpl#getEvent
 * <em>Event</em>}</li>
 * <li>
 * {@link org.eclipse.fordiac.ide.fbtester.model.testdata.impl.TestDataImpl#getTestIntstance
 * <em>Test Intstance</em>}</li>
 * <li>
 * {@link org.eclipse.fordiac.ide.fbtester.model.testdata.impl.TestDataImpl#getEventOutputs
 * <em>Event Outputs</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fbtester.model.testdata.impl.TestDataImpl#getValues
 * <em>Values</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fbtester.model.testdata.impl.TestDataImpl#getResults
 * <em>Results</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fbtester.model.testdata.impl.TestDataImpl#getLine <em>
 * Line</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fbtester.model.testdata.impl.TestDataImpl#getType <em>
 * Type</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class TestDataImpl extends EObjectImpl implements TestData {
	/**
	 * The default value of the '{@link #getTestName() <em>Test Name</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getTestName()
	 * @generated
	 * @ordered
	 */
	protected static final String TEST_NAME_EDEFAULT = "testName";

	/**
	 * The cached value of the '{@link #getTestName() <em>Test Name</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getTestName()
	 * @generated
	 * @ordered
	 */
	protected String testName = TEST_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getEvent() <em>Event</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getEvent()
	 * @generated
	 * @ordered
	 */
	protected Event event;

	/**
	 * The default value of the '{@link #getTestIntstance()
	 * <em>Test Intstance</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getTestIntstance()
	 * @generated
	 * @ordered
	 */
	protected static final String TEST_INTSTANCE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTestIntstance()
	 * <em>Test Intstance</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getTestIntstance()
	 * @generated
	 * @ordered
	 */
	protected String testIntstance = TEST_INTSTANCE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getEventOutputs()
	 * <em>Event Outputs</em>}' reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getEventOutputs()
	 * @generated
	 * @ordered
	 */
	protected EList<Event> eventOutputs;

	/**
	 * The cached value of the '{@link #getValues() <em>Values</em>}' reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getValues()
	 * @generated
	 * @ordered
	 */
	protected EList<ValuedVarDecl> values;

	/**
	 * The cached value of the '{@link #getResults() <em>Results</em>}'
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getResults()
	 * @generated
	 * @ordered
	 */
	protected EList<ValuedVarDecl> results;

	/**
	 * The default value of the '{@link #getLine() <em>Line</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLine()
	 * @generated
	 * @ordered
	 */
	protected static final String LINE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLine() <em>Line</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLine()
	 * @generated
	 * @ordered
	 */
	protected String line = LINE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected FBType type;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected TestDataImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TestdataPackage.Literals.TEST_DATA;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getTestName() {
		return testName;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setTestName(String newTestName) {
		String oldTestName = testName;
		testName = newTestName;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					TestdataPackage.TEST_DATA__TEST_NAME, oldTestName, testName));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Event getEvent() {
		if (event != null && event.eIsProxy()) {
			InternalEObject oldEvent = (InternalEObject) event;
			event = (Event) eResolveProxy(oldEvent);
			if (event != oldEvent) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							TestdataPackage.TEST_DATA__EVENT, oldEvent, event));
				}
			}
		}
		return event;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Event basicGetEvent() {
		return event;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setEvent(Event newEvent) {
		Event oldEvent = event;
		event = newEvent;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					TestdataPackage.TEST_DATA__EVENT, oldEvent, event));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getTestIntstance() {
		return testIntstance;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setTestIntstance(String newTestIntstance) {
		String oldTestIntstance = testIntstance;
		testIntstance = newTestIntstance;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					TestdataPackage.TEST_DATA__TEST_INTSTANCE,
					oldTestIntstance, testIntstance));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Event> getEventOutputs() {
		if (eventOutputs == null) {
			eventOutputs = new EObjectResolvingEList<Event>(Event.class, this,
					TestdataPackage.TEST_DATA__EVENT_OUTPUTS);
		}
		return eventOutputs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<ValuedVarDecl> getValues() {
		if (values == null) {
			values = new EObjectResolvingEList<ValuedVarDecl>(
					ValuedVarDecl.class, this,
					TestdataPackage.TEST_DATA__VALUES);
		}
		return values;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<ValuedVarDecl> getResults() {
		if (results == null) {
			results = new EObjectResolvingEList<ValuedVarDecl>(
					ValuedVarDecl.class, this,
					TestdataPackage.TEST_DATA__RESULTS);
		}
		return results;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getLine() {
		return line;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public void setLine(String newLine) {
		String oldLine = line;
		line = newLine;
		parseData(line);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					TestdataPackage.TEST_DATA__LINE, oldLine, line));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public FBType getType() {
		if (type != null && type.eIsProxy()) {
			InternalEObject oldType = (InternalEObject) type;
			type = (FBType) eResolveProxy(oldType);
			if (type != (FBType)oldType) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							TestdataPackage.TEST_DATA__TYPE, oldType, type));
				}
			}
		}
		return type;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public FBType basicGetType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setType(FBType newType) {
		FBType oldType = type;
		type = newType;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					TestdataPackage.TEST_DATA__TYPE, oldType, type));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case TestdataPackage.TEST_DATA__TEST_NAME:
			return getTestName();
		case TestdataPackage.TEST_DATA__EVENT:
			if (resolve) {
				return getEvent();
			}
			return basicGetEvent();
		case TestdataPackage.TEST_DATA__TEST_INTSTANCE:
			return getTestIntstance();
		case TestdataPackage.TEST_DATA__EVENT_OUTPUTS:
			return getEventOutputs();
		case TestdataPackage.TEST_DATA__VALUES:
			return getValues();
		case TestdataPackage.TEST_DATA__RESULTS:
			return getResults();
		case TestdataPackage.TEST_DATA__LINE:
			return getLine();
		case TestdataPackage.TEST_DATA__TYPE:
			if (resolve) {
				return getType();
			}
			return basicGetType();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case TestdataPackage.TEST_DATA__TEST_NAME:
			setTestName((String) newValue);
			return;
		case TestdataPackage.TEST_DATA__EVENT:
			setEvent((Event) newValue);
			return;
		case TestdataPackage.TEST_DATA__TEST_INTSTANCE:
			setTestIntstance((String) newValue);
			return;
		case TestdataPackage.TEST_DATA__EVENT_OUTPUTS:
			getEventOutputs().clear();
			getEventOutputs().addAll((Collection<? extends Event>) newValue);
			return;
		case TestdataPackage.TEST_DATA__VALUES:
			getValues().clear();
			getValues().addAll((Collection<? extends ValuedVarDecl>) newValue);
			return;
		case TestdataPackage.TEST_DATA__RESULTS:
			getResults().clear();
			getResults().addAll((Collection<? extends ValuedVarDecl>) newValue);
			return;
		case TestdataPackage.TEST_DATA__LINE:
			setLine((String) newValue);
			return;
		case TestdataPackage.TEST_DATA__TYPE:
			setType((FBType) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case TestdataPackage.TEST_DATA__TEST_NAME:
			setTestName(TEST_NAME_EDEFAULT);
			return;
		case TestdataPackage.TEST_DATA__EVENT:
			setEvent((Event) null);
			return;
		case TestdataPackage.TEST_DATA__TEST_INTSTANCE:
			setTestIntstance(TEST_INTSTANCE_EDEFAULT);
			return;
		case TestdataPackage.TEST_DATA__EVENT_OUTPUTS:
			getEventOutputs().clear();
			return;
		case TestdataPackage.TEST_DATA__VALUES:
			getValues().clear();
			return;
		case TestdataPackage.TEST_DATA__RESULTS:
			getResults().clear();
			return;
		case TestdataPackage.TEST_DATA__LINE:
			setLine(LINE_EDEFAULT);
			return;
		case TestdataPackage.TEST_DATA__TYPE:
			setType((FBType) null);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case TestdataPackage.TEST_DATA__TEST_NAME:
			return TEST_NAME_EDEFAULT == null ? testName != null
					: !TEST_NAME_EDEFAULT.equals(testName);
		case TestdataPackage.TEST_DATA__EVENT:
			return event != null;
		case TestdataPackage.TEST_DATA__TEST_INTSTANCE:
			return TEST_INTSTANCE_EDEFAULT == null ? testIntstance != null
					: !TEST_INTSTANCE_EDEFAULT.equals(testIntstance);
		case TestdataPackage.TEST_DATA__EVENT_OUTPUTS:
			return eventOutputs != null && !eventOutputs.isEmpty();
		case TestdataPackage.TEST_DATA__VALUES:
			return values != null && !values.isEmpty();
		case TestdataPackage.TEST_DATA__RESULTS:
			return results != null && !results.isEmpty();
		case TestdataPackage.TEST_DATA__LINE:
			return LINE_EDEFAULT == null ? line != null : !LINE_EDEFAULT
					.equals(line);
		case TestdataPackage.TEST_DATA__TYPE:
			return type != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (testName: ");
		result.append(testName);
		result.append(", testIntstance: ");
		result.append(testIntstance);
		result.append(", line: ");
		result.append(line);
		result.append(')');
		return result.toString();
	}

	private void parseData(String line) {
		System.out.println(line);
		String[] lineArray = line
				.split(";(?=(?:[^\"]*\"[^\"]*\")*(?![^\"]*\"))");
		System.out.println(lineArray.length);
		if (lineArray.length > 1) {
			setTestName(lineArray[0]);
			setTestIntstance(lineArray[1]);
		}

		int currentPos = 0;
		if (type.getInterfaceList() != null) {
			int nrOfInputs = Integer.parseInt(lineArray[2]);
			if (lineArray.length > nrOfInputs * 2 + 2) {
				int nrOfValues = Integer.parseInt(lineArray[2]);
				currentPos = 3;
				for (int i = 0; i < nrOfValues; i++) { // start at pos 2 //
					// after
					// testName and
					// testInstance
					String port = lineArray[currentPos];
					currentPos++;
					String val = lineArray[currentPos];
					currentPos++;
					VarDeclaration varDec = type.getInterfaceList()
							.getVariable(port);
					if (varDec != null) {
						ValuedVarDecl data = TestdataFactory.eINSTANCE
								.createValuedVarDecl();
						data.setValue(val);
						data.setVarDeclaration(varDec);
						getValues().add(data);
					}
				}
			}
			event = type.getInterfaceList().getEvent(lineArray[currentPos]);
			currentPos++;
			int nrOfEvents = Integer.parseInt(lineArray[currentPos]);
			currentPos++;
			for (int i = 0; i < nrOfEvents; i++) {
				Event outputEvent = type.getInterfaceList().getEvent(
						lineArray[currentPos]);
				getEventOutputs().add(outputEvent);
				currentPos++;
				int nrOfValues = Integer.parseInt(lineArray[currentPos]);
				currentPos++;
				for (int j = 0; j < nrOfValues; j++) {
					VarDeclaration outputVar = type.getInterfaceList()
							.getVariable(lineArray[currentPos]);
					currentPos++;
					String result = lineArray[currentPos];
					ValuedVarDecl var = TestdataFactory.eINSTANCE
							.createValuedVarDecl();
					var.setValue(result);
					var.setVarDeclaration(outputVar);
					getResults().add(var);
				}
			}

		}
	}

	@Override
	public String getValueFor(String text) {
		for (ValuedVarDecl valueVarDecl : getValues()){
			if (valueVarDecl.getVarDeclaration().getName().equals(text)) {
				return valueVarDecl.getValue();
			}
		}
		return null;
	}

	@Override
	public void setValueFor(String text, String value) {
		for (ValuedVarDecl valueVarDecl : getValues()){
			if (valueVarDecl.getVarDeclaration().getName().equals(text)) {
				valueVarDecl.setValue(value);
			}
		}
	}

	//
	@Override
	public String getResultFor(String text) {
		for (ValuedVarDecl valueVarDecl : getResults()){
			if (valueVarDecl.getVarDeclaration() != null) {

				if (valueVarDecl.getVarDeclaration().getName().equals(text)) {
					return valueVarDecl.getValue();
				}
			}
		}
		return null;

	}

	@Override
	public void setResultFor(String text, String value) {
		for (ValuedVarDecl valueVarDecl : getResults()){
			if (valueVarDecl.getVarDeclaration().getName().equals(text)) {
				valueVarDecl.setValue(value);
			}
		}
	}

	@Override
	public String getOutputEvents() {
		String events = "";
		for (int i = 0; i < getEventOutputs().size(); i++) {
			Event e = getEventOutputs().get(i);
			events += e.getName();
			if (i + 1 < getEventOutputs().size()) {
				events += ", ";
			}
		}
		return events;
	}

	@Override
	public String _getLine() {
		String dataLine = getTestName() + ";";
		dataLine += type.getName();
		dataLine += ";";
		Event event = null;
		if (type.getInterfaceList().getEventInputs().size() > 0) {
			event = type.getInterfaceList().getEventInputs().get(0);
		}

		dataLine += event.getWith().size() + ";"; // nr of inputs are the number
		// of connected withs

		for (With with : event.getWith()) {
			VarDeclaration var = with.getVariables();
			dataLine += var.getName() + ";" + getValueFor(var.getName()) + ";";
		}
		dataLine += event.getName() + ";";

		if (type.getInterfaceList().getEventOutputs().size() > 0) {
			dataLine += "1;";
			dataLine += type.getInterfaceList().getEventOutputs().get(0)
					.getName();
			dataLine += ";";
		} else {
			dataLine += "0;";
		}
		if (type.getInterfaceList().getOutputVars().size() > 0) {
			dataLine += type.getInterfaceList().getOutputVars().size();
			dataLine += ";";
			for (VarDeclaration var: type.getInterfaceList().getOutputVars()) {
				dataLine += var.getName() + ";" + getResultFor(var.getName()) + ";";
			}
		}

		return dataLine;
	}

} // TestDataImpl
