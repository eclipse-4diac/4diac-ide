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
 *    Hesam Rezaee
 *      - add Hovering features
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.structuredtextcore.stcore;

import java.lang.reflect.Method;
import org.eclipse.emf.common.util.EList;

import org.eclipse.fordiac.ide.model.data.DataType;

import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>ST Standard Function</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction#getReturnValueComment <em>Return Value Comment</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction#getSignature <em>Signature</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction#getReturnType <em>Return Type</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction#getInputParameters <em>Input Parameters</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction#getOutputParameters <em>Output Parameters</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction#getInOutParameters <em>In Out Parameters</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction#isVarargs <em>Varargs</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction#getOnlySupportedBy <em>Only Supported By</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction#getJavaMethod <em>Java Method</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage#getSTStandardFunction()
 * @model
 * @generated
 */
public interface STStandardFunction extends ICallable {
	/**
	 * Returns the value of the '<em><b>Return Value Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Return Value Comment</em>' attribute.
	 * @see #setReturnValueComment(String)
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage#getSTStandardFunction_ReturnValueComment()
	 * @model
	 * @generated
	 */
	String getReturnValueComment();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction#getReturnValueComment <em>Return Value Comment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Return Value Comment</em>' attribute.
	 * @see #getReturnValueComment()
	 * @generated
	 */
	void setReturnValueComment(String value);

	/**
	 * Returns the value of the '<em><b>Signature</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Signature</em>' attribute.
	 * @see #setSignature(String)
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage#getSTStandardFunction_Signature()
	 * @model id="true"
	 * @generated
	 */
	String getSignature();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction#getSignature <em>Signature</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Signature</em>' attribute.
	 * @see #getSignature()
	 * @generated
	 */
	void setSignature(String value);

	/**
	 * Returns the value of the '<em><b>Return Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Return Type</em>' reference.
	 * @see #setReturnType(DataType)
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage#getSTStandardFunction_ReturnType()
	 * @model
	 * @generated
	 */
	DataType getReturnType();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction#getReturnType <em>Return Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Return Type</em>' reference.
	 * @see #getReturnType()
	 * @generated
	 */
	void setReturnType(DataType value);

	/**
	 * Returns the value of the '<em><b>Input Parameters</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.model.libraryElement.ITypedElement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Input Parameters</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage#getSTStandardFunction_InputParameters()
	 * @model containment="true"
	 * @generated
	 */
	EList<ITypedElement> getInputParameters();

	/**
	 * Returns the value of the '<em><b>Output Parameters</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.model.libraryElement.ITypedElement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Output Parameters</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage#getSTStandardFunction_OutputParameters()
	 * @model containment="true"
	 * @generated
	 */
	EList<ITypedElement> getOutputParameters();

	/**
	 * Returns the value of the '<em><b>In Out Parameters</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.model.libraryElement.ITypedElement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>In Out Parameters</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage#getSTStandardFunction_InOutParameters()
	 * @model containment="true"
	 * @generated
	 */
	EList<ITypedElement> getInOutParameters();

	/**
	 * Returns the value of the '<em><b>Varargs</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Varargs</em>' attribute.
	 * @see #setVarargs(boolean)
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage#getSTStandardFunction_Varargs()
	 * @model
	 * @generated
	 */
	boolean isVarargs();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction#isVarargs <em>Varargs</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Varargs</em>' attribute.
	 * @see #isVarargs()
	 * @generated
	 */
	void setVarargs(boolean value);

	/**
	 * Returns the value of the '<em><b>Only Supported By</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Only Supported By</em>' attribute list.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage#getSTStandardFunction_OnlySupportedBy()
	 * @model
	 * @generated
	 */
	EList<String> getOnlySupportedBy();

	/**
	 * Returns the value of the '<em><b>Java Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Java Method</em>' attribute.
	 * @see #setJavaMethod(Method)
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage#getSTStandardFunction_JavaMethod()
	 * @model dataType="org.eclipse.fordiac.ide.structuredtextcore.stcore.STJavaMethod" transient="true"
	 * @generated
	 */
	Method getJavaMethod();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction#getJavaMethod <em>Java Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Java Method</em>' attribute.
	 * @see #getJavaMethod()
	 * @generated
	 */
	void setJavaMethod(Method value);

	

} // STStandardFunction
