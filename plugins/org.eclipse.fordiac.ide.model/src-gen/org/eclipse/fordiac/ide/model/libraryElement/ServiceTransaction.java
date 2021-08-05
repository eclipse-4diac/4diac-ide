/**
 * *******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH
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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/** <!-- begin-user-doc --> A representation of the model object '<em><b>Service Transaction</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction#getInputPrimitive <em>Input
 * Primitive</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction#getOutputPrimitive <em>Output
 * Primitive</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getServiceTransaction()
 * @model
 * @generated */
public interface ServiceTransaction extends EObject {
	/** Returns the value of the '<em><b>Input Primitive</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @return the value of the '<em>Input Primitive</em>' containment reference.
	 * @see #setInputPrimitive(InputPrimitive)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getServiceTransaction_InputPrimitive()
	 * @model containment="true" extendedMetaData="kind='element' name='InputPrimitive' namespace='##targetNamespace'"
	 * @generated */
	InputPrimitive getInputPrimitive();

	/** Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction#getInputPrimitive
	 * <em>Input Primitive</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Input Primitive</em>' containment reference.
	 * @see #getInputPrimitive()
	 * @generated */
	void setInputPrimitive(InputPrimitive value);

	/** Returns the value of the '<em><b>Output Primitive</b></em>' containment reference list. The list contents are of
	 * type {@link org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive}. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @return the value of the '<em>Output Primitive</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getServiceTransaction_OutputPrimitive()
	 * @model containment="true" extendedMetaData="kind='element' name='OutputPrimitive' namespace='##targetNamespace'"
	 * @generated */
	EList<OutputPrimitive> getOutputPrimitive();

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @model kind="operation" required="true"
	 * @generated */
	ServiceSequence getServiceSequence();

} // ServiceTransaction
