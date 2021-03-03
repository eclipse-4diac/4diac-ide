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

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>EC
 * Transition</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.ECTransition#getComment
 * <em>Comment</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.ECTransition#getConditionExpression
 * <em>Condition Expression</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.ECTransition#getSource
 * <em>Source</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.ECTransition#getDestination
 * <em>Destination</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.ECTransition#getConditionEvent
 * <em>Condition Event</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.ECTransition#getECC
 * <em>ECC</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getECTransition()
 * @model
 * @generated
 */
public interface ECTransition extends PositionableElement {
	/**
	 * Returns the value of the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Comment</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Comment</em>' attribute.
	 * @see #setComment(String)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getECTransition_Comment()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='Comment'"
	 * @generated
	 */
	String getComment();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.ECTransition#getComment
	 * <em>Comment</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Comment</em>' attribute.
	 * @see #getComment()
	 * @generated
	 */
	void setComment(String value);

	/**
	 * Returns the value of the '<em><b>Condition Expression</b></em>' attribute.
	 * The default value is <code>"1"</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Condition Expression</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Condition Expression</em>' attribute.
	 * @see #setConditionExpression(String)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getECTransition_ConditionExpression()
	 * @model default="1" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        required="true" extendedMetaData="kind='attribute' name='Condition'"
	 * @generated
	 */
	String getConditionExpression();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.ECTransition#getConditionExpression
	 * <em>Condition Expression</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Condition Expression</em>' attribute.
	 * @see #getConditionExpression()
	 * @generated
	 */
	void setConditionExpression(String value);

	/**
	 * Returns the value of the '<em><b>Destination</b></em>' reference. It is
	 * bidirectional and its opposite is
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.ECState#getInTransitions
	 * <em>In Transitions</em>}'. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Destination</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Destination</em>' reference.
	 * @see #setDestination(ECState)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getECTransition_Destination()
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECState#getInTransitions
	 * @model opposite="inTransitions" required="true"
	 * @generated
	 */
	ECState getDestination();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.ECTransition#getDestination
	 * <em>Destination</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value the new value of the '<em>Destination</em>' reference.
	 * @see #getDestination()
	 * @generated
	 */
	void setDestination(ECState value);

	/**
	 * Returns the value of the '<em><b>Condition Event</b></em>' reference. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Condition Event</em>' reference isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Condition Event</em>' reference.
	 * @see #setConditionEvent(Event)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getECTransition_ConditionEvent()
	 * @model
	 * @generated
	 */
	Event getConditionEvent();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.ECTransition#getConditionEvent
	 * <em>Condition Event</em>}' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Condition Event</em>' reference.
	 * @see #getConditionEvent()
	 * @generated
	 */
	void setConditionEvent(Event value);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model kind="operation" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        required="true"
	 * @generated
	 */
	String getConditionText();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model kind="operation" required="true"
	 * @generated
	 */
	int getPriority();

	/**
	 * Returns the value of the '<em><b>ECC</b></em>' container reference. It is
	 * bidirectional and its opposite is
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.ECC#getECTransition
	 * <em>EC Transition</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>ECC</em>' container reference.
	 * @see #setECC(ECC)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getECTransition_ECC()
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECC#getECTransition
	 * @model opposite="eCTransition" required="true" transient="false"
	 * @generated
	 */
	ECC getECC();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.ECTransition#getECC
	 * <em>ECC</em>}' container reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value the new value of the '<em>ECC</em>' container reference.
	 * @see #getECC()
	 * @generated
	 */
	void setECC(ECC value);

	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference. It is
	 * bidirectional and its opposite is
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.ECState#getOutTransitions
	 * <em>Out Transitions</em>}'. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(ECState)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getECTransition_Source()
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECState#getOutTransitions
	 * @model opposite="outTransitions" required="true"
	 * @generated
	 */
	ECState getSource();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.ECTransition#getSource
	 * <em>Source</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(ECState value);

} // ECTransition
