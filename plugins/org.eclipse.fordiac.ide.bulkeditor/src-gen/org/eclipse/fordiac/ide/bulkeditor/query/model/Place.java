/**
 * *******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.bulkeditor.query.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Place</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#isIgnoreLinkedLibraries <em>Ignore Linked Libraries</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#isSubappHierarchy <em>Subapp Hierarchy</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getSubappHierarchyElements <em>Subapp Hierarchy Elements</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getSimpleFB <em>Simple FB</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getBasicFB <em>Basic FB</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getCompositeFB <em>Composite FB</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getServiceInterfaceFB <em>Service Interface FB</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getFunctionFB <em>Function FB</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getTypedSubapp <em>Typed Subapp</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getUntypedSubapp <em>Untyped Subapp</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getConnection <em>Connection</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getSimpleType <em>Simple Type</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getBasicType <em>Basic Type</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getCompositeType <em>Composite Type</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getServiceInterfaceType <em>Service Interface Type</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getFunctionType <em>Function Type</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getSubappType <em>Subapp Type</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getStructType <em>Struct Type</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getAttributeType <em>Attribute Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage#getPlace()
 * @model
 * @generated
 */
public interface Place extends EObject {
	/**
	 * Returns the value of the '<em><b>Ignore Linked Libraries</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ignore Linked Libraries</em>' attribute.
	 * @see #setIgnoreLinkedLibraries(boolean)
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage#getPlace_IgnoreLinkedLibraries()
	 * @model default="true"
	 * @generated
	 */
	boolean isIgnoreLinkedLibraries();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#isIgnoreLinkedLibraries <em>Ignore Linked Libraries</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ignore Linked Libraries</em>' attribute.
	 * @see #isIgnoreLinkedLibraries()
	 * @generated
	 */
	void setIgnoreLinkedLibraries(boolean value);

	/**
	 * Returns the value of the '<em><b>Subapp Hierarchy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Subapp Hierarchy</em>' attribute.
	 * @see #setSubappHierarchy(boolean)
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage#getPlace_SubappHierarchy()
	 * @model
	 * @generated
	 */
	boolean isSubappHierarchy();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#isSubappHierarchy <em>Subapp Hierarchy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Subapp Hierarchy</em>' attribute.
	 * @see #isSubappHierarchy()
	 * @generated
	 */
	void setSubappHierarchy(boolean value);

	/**
	 * Returns the value of the '<em><b>Subapp Hierarchy Elements</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Subapp Hierarchy Elements</em>' attribute list.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage#getPlace_SubappHierarchyElements()
	 * @model
	 * @generated
	 */
	EList<String> getSubappHierarchyElements();

	/**
	 * Returns the value of the '<em><b>Simple FB</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Simple FB</em>' containment reference.
	 * @see #setSimpleFB(SimpleFB)
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage#getPlace_SimpleFB()
	 * @model containment="true"
	 * @generated
	 */
	SimpleFB getSimpleFB();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getSimpleFB <em>Simple FB</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Simple FB</em>' containment reference.
	 * @see #getSimpleFB()
	 * @generated
	 */
	void setSimpleFB(SimpleFB value);

	/**
	 * Returns the value of the '<em><b>Basic FB</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Basic FB</em>' containment reference.
	 * @see #setBasicFB(BasicFB)
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage#getPlace_BasicFB()
	 * @model containment="true"
	 * @generated
	 */
	BasicFB getBasicFB();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getBasicFB <em>Basic FB</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Basic FB</em>' containment reference.
	 * @see #getBasicFB()
	 * @generated
	 */
	void setBasicFB(BasicFB value);

	/**
	 * Returns the value of the '<em><b>Composite FB</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Composite FB</em>' containment reference.
	 * @see #setCompositeFB(CompositeFB)
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage#getPlace_CompositeFB()
	 * @model containment="true"
	 * @generated
	 */
	CompositeFB getCompositeFB();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getCompositeFB <em>Composite FB</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Composite FB</em>' containment reference.
	 * @see #getCompositeFB()
	 * @generated
	 */
	void setCompositeFB(CompositeFB value);

	/**
	 * Returns the value of the '<em><b>Service Interface FB</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Service Interface FB</em>' containment reference.
	 * @see #setServiceInterfaceFB(ServiceInterfaceFB)
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage#getPlace_ServiceInterfaceFB()
	 * @model containment="true"
	 * @generated
	 */
	ServiceInterfaceFB getServiceInterfaceFB();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getServiceInterfaceFB <em>Service Interface FB</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Service Interface FB</em>' containment reference.
	 * @see #getServiceInterfaceFB()
	 * @generated
	 */
	void setServiceInterfaceFB(ServiceInterfaceFB value);

	/**
	 * Returns the value of the '<em><b>Function FB</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Function FB</em>' containment reference.
	 * @see #setFunctionFB(FunctionFB)
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage#getPlace_FunctionFB()
	 * @model containment="true"
	 * @generated
	 */
	FunctionFB getFunctionFB();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getFunctionFB <em>Function FB</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Function FB</em>' containment reference.
	 * @see #getFunctionFB()
	 * @generated
	 */
	void setFunctionFB(FunctionFB value);

	/**
	 * Returns the value of the '<em><b>Typed Subapp</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Typed Subapp</em>' containment reference.
	 * @see #setTypedSubapp(TypedSubapp)
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage#getPlace_TypedSubapp()
	 * @model containment="true"
	 * @generated
	 */
	TypedSubapp getTypedSubapp();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getTypedSubapp <em>Typed Subapp</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Typed Subapp</em>' containment reference.
	 * @see #getTypedSubapp()
	 * @generated
	 */
	void setTypedSubapp(TypedSubapp value);

	/**
	 * Returns the value of the '<em><b>Untyped Subapp</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Untyped Subapp</em>' containment reference.
	 * @see #setUntypedSubapp(UntypedSubapp)
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage#getPlace_UntypedSubapp()
	 * @model containment="true"
	 * @generated
	 */
	UntypedSubapp getUntypedSubapp();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getUntypedSubapp <em>Untyped Subapp</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Untyped Subapp</em>' containment reference.
	 * @see #getUntypedSubapp()
	 * @generated
	 */
	void setUntypedSubapp(UntypedSubapp value);

	/**
	 * Returns the value of the '<em><b>Connection</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Connection</em>' containment reference.
	 * @see #setConnection(Connection)
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage#getPlace_Connection()
	 * @model containment="true"
	 * @generated
	 */
	Connection getConnection();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getConnection <em>Connection</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Connection</em>' containment reference.
	 * @see #getConnection()
	 * @generated
	 */
	void setConnection(Connection value);

	/**
	 * Returns the value of the '<em><b>Simple Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Simple Type</em>' containment reference.
	 * @see #setSimpleType(SimpleType)
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage#getPlace_SimpleType()
	 * @model containment="true"
	 * @generated
	 */
	SimpleType getSimpleType();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getSimpleType <em>Simple Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Simple Type</em>' containment reference.
	 * @see #getSimpleType()
	 * @generated
	 */
	void setSimpleType(SimpleType value);

	/**
	 * Returns the value of the '<em><b>Basic Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Basic Type</em>' containment reference.
	 * @see #setBasicType(BasicType)
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage#getPlace_BasicType()
	 * @model containment="true"
	 * @generated
	 */
	BasicType getBasicType();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getBasicType <em>Basic Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Basic Type</em>' containment reference.
	 * @see #getBasicType()
	 * @generated
	 */
	void setBasicType(BasicType value);

	/**
	 * Returns the value of the '<em><b>Composite Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Composite Type</em>' containment reference.
	 * @see #setCompositeType(CompositeType)
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage#getPlace_CompositeType()
	 * @model containment="true"
	 * @generated
	 */
	CompositeType getCompositeType();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getCompositeType <em>Composite Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Composite Type</em>' containment reference.
	 * @see #getCompositeType()
	 * @generated
	 */
	void setCompositeType(CompositeType value);

	/**
	 * Returns the value of the '<em><b>Service Interface Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Service Interface Type</em>' containment reference.
	 * @see #setServiceInterfaceType(ServiceInterfaceType)
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage#getPlace_ServiceInterfaceType()
	 * @model containment="true"
	 * @generated
	 */
	ServiceInterfaceType getServiceInterfaceType();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getServiceInterfaceType <em>Service Interface Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Service Interface Type</em>' containment reference.
	 * @see #getServiceInterfaceType()
	 * @generated
	 */
	void setServiceInterfaceType(ServiceInterfaceType value);

	/**
	 * Returns the value of the '<em><b>Function Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Function Type</em>' containment reference.
	 * @see #setFunctionType(FunctionType)
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage#getPlace_FunctionType()
	 * @model containment="true"
	 * @generated
	 */
	FunctionType getFunctionType();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getFunctionType <em>Function Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Function Type</em>' containment reference.
	 * @see #getFunctionType()
	 * @generated
	 */
	void setFunctionType(FunctionType value);

	/**
	 * Returns the value of the '<em><b>Subapp Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Subapp Type</em>' containment reference.
	 * @see #setSubappType(SubappType)
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage#getPlace_SubappType()
	 * @model containment="true"
	 * @generated
	 */
	SubappType getSubappType();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getSubappType <em>Subapp Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Subapp Type</em>' containment reference.
	 * @see #getSubappType()
	 * @generated
	 */
	void setSubappType(SubappType value);

	/**
	 * Returns the value of the '<em><b>Struct Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Struct Type</em>' containment reference.
	 * @see #setStructType(StructType)
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage#getPlace_StructType()
	 * @model containment="true"
	 * @generated
	 */
	StructType getStructType();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getStructType <em>Struct Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Struct Type</em>' containment reference.
	 * @see #getStructType()
	 * @generated
	 */
	void setStructType(StructType value);

	/**
	 * Returns the value of the '<em><b>Attribute Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Type</em>' containment reference.
	 * @see #setAttributeType(AttributeType)
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage#getPlace_AttributeType()
	 * @model containment="true"
	 * @generated
	 */
	AttributeType getAttributeType();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getAttributeType <em>Attribute Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute Type</em>' containment reference.
	 * @see #getAttributeType()
	 * @generated
	 */
	void setAttributeType(AttributeType value);

} // Place
