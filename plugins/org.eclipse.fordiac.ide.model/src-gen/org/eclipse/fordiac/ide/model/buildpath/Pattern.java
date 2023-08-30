/**
 * *******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *               2022-2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.model.buildpath;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Pattern</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.buildpath.Pattern#getValue <em>Value</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.buildpath.Pattern#getSyntax <em>Syntax</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.buildpath.BuildpathPackage#getPattern()
 * @model extendedMetaData="name='pattern' kind='simple'"
 * @generated
 */
public interface Pattern extends EObject {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see org.eclipse.fordiac.ide.model.buildpath.BuildpathPackage#getPattern_Value()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="name=':0' kind='simple'"
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.buildpath.Pattern#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

	/**
	 * Returns the value of the '<em><b>Syntax</b></em>' attribute.
	 * The default value is <code>"glob"</code>.
	 * The literals are from the enumeration {@link org.eclipse.fordiac.ide.model.buildpath.PatternType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Syntax</em>' attribute.
	 * @see org.eclipse.fordiac.ide.model.buildpath.PatternType
	 * @see #isSetSyntax()
	 * @see #unsetSyntax()
	 * @see #setSyntax(PatternType)
	 * @see org.eclipse.fordiac.ide.model.buildpath.BuildpathPackage#getPattern_Syntax()
	 * @model default="glob" unsettable="true"
	 *        extendedMetaData="kind='attribute' name='syntax'"
	 * @generated
	 */
	PatternType getSyntax();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.buildpath.Pattern#getSyntax <em>Syntax</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Syntax</em>' attribute.
	 * @see org.eclipse.fordiac.ide.model.buildpath.PatternType
	 * @see #isSetSyntax()
	 * @see #unsetSyntax()
	 * @see #getSyntax()
	 * @generated
	 */
	void setSyntax(PatternType value);

	/**
	 * Unsets the value of the '{@link org.eclipse.fordiac.ide.model.buildpath.Pattern#getSyntax <em>Syntax</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetSyntax()
	 * @see #getSyntax()
	 * @see #setSyntax(PatternType)
	 * @generated
	 */
	void unsetSyntax();

	/**
	 * Returns whether the value of the '{@link org.eclipse.fordiac.ide.model.buildpath.Pattern#getSyntax <em>Syntax</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Syntax</em>' attribute is set.
	 * @see #unsetSyntax()
	 * @see #getSyntax()
	 * @see #setSyntax(PatternType)
	 * @generated
	 */
	boolean isSetSyntax();

} // Pattern
