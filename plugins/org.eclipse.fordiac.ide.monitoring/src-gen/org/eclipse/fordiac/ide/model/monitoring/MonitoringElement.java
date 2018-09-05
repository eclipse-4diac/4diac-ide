/*******************************************************************************
 * Copyright (c) 2012, 2013, 2015 - 2017 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Gerd Kainz
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.monitoring;

import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Element</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.monitoring.MonitoringElement#isForce <em>Force</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.monitoring.MonitoringElement#getForceValue <em>Force Value</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.monitoring.MonitoringElement#getCurrentValue <em>Current Value</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.monitoring.MonitoringElement#getSec <em>Sec</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.monitoring.MonitoringElement#getUsec <em>Usec</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.monitoring.MonitoringPackage#getMonitoringElement()
 * @model
 * @generated
 */
public interface MonitoringElement extends MonitoringBaseElement{
	/**
	 * Returns the value of the '<em><b>Force</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Force</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Force</em>' attribute.
	 * @see #setForce(boolean)
	 * @see org.eclipse.fordiac.ide.model.monitoring.MonitoringPackage#getMonitoringElement_Force()
	 * @model default="false" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	boolean isForce();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.monitoring.MonitoringElement#isForce <em>Force</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Force</em>' attribute.
	 * @see #isForce()
	 * @generated
	 */
	void setForce(boolean value);

	/**
	 * Returns the value of the '<em><b>Force Value</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Force Value</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Force Value</em>' attribute.
	 * @see #setForceValue(String)
	 * @see org.eclipse.fordiac.ide.model.monitoring.MonitoringPackage#getMonitoringElement_ForceValue()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getForceValue();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.fordiac.ide.model.monitoring.MonitoringElement#getForceValue
	 * <em>Force Value</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value
	 *          the new value of the '<em>Force Value</em>' attribute.
	 * @see #getForceValue()
	 * @generated
	 */
	void setForceValue(String value);

	/**
	 * Returns the value of the '<em><b>Current Value</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Current Value</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Current Value</em>' attribute.
	 * @see #setCurrentValue(String)
	 * @see org.eclipse.fordiac.ide.model.monitoring.MonitoringPackage#getMonitoringElement_CurrentValue()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getCurrentValue();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.monitoring.MonitoringElement#getCurrentValue <em>Current Value</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Current Value</em>' attribute.
	 * @see #getCurrentValue()
	 * @generated
	 */
	void setCurrentValue(String value);

	/**
	 * Returns the value of the '<em><b>Sec</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sec</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sec</em>' attribute.
	 * @see #setSec(long)
	 * @see org.eclipse.fordiac.ide.model.monitoring.MonitoringPackage#getMonitoringElement_Sec()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Long"
	 * @generated
	 */
	long getSec();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.monitoring.MonitoringElement#getSec <em>Sec</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sec</em>' attribute.
	 * @see #getSec()
	 * @generated
	 */
	void setSec(long value);

	/**
	 * Returns the value of the '<em><b>Usec</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Usec</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Usec</em>' attribute.
	 * @see #setUsec(long)
	 * @see org.eclipse.fordiac.ide.model.monitoring.MonitoringPackage#getMonitoringElement_Usec()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Long"
	 * @generated
	 */
	long getUsec();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.monitoring.MonitoringElement#getUsec <em>Usec</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Usec</em>' attribute.
	 * @see #getUsec()
	 * @generated
	 */
	void setUsec(long value);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @model valueDataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	void forceValue(String value);
	
} // MonitoringElement
