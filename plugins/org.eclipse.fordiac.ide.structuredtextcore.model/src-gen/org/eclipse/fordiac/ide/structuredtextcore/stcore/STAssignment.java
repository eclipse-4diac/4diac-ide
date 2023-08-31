/**
 * *******************************************************************************
 * Copyright (c) 2022 Primetals Technologies GmbH,
 *               2022 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Martin Jobst, Martin Melik Merkumians
 *      - initial API and implementation and/or initial documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.structuredtextcore.stcore;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>ST Assignment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STAssignment#getLeft <em>Left</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STAssignment#getRight <em>Right</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage#getSTAssignment()
 * @model
 * @generated
 */
public interface STAssignment extends STExpression {
	/**
	 * Returns the value of the '<em><b>Left</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Left</em>' containment reference.
	 * @see #setLeft(STExpression)
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage#getSTAssignment_Left()
	 * @model containment="true"
	 * @generated
	 */
	STExpression getLeft();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STAssignment#getLeft <em>Left</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Left</em>' containment reference.
	 * @see #getLeft()
	 * @generated
	 */
	void setLeft(STExpression value);

	/**
	 * Returns the value of the '<em><b>Right</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Right</em>' containment reference.
	 * @see #setRight(STExpression)
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage#getSTAssignment_Right()
	 * @model containment="true"
	 * @generated
	 */
	STExpression getRight();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STAssignment#getRight <em>Right</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Right</em>' containment reference.
	 * @see #getRight()
	 * @generated
	 */
	void setRight(STExpression value);

} // STAssignment
