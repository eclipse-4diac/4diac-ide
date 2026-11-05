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
package org.eclipse.fordiac.ide.bulkeditor.query.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

import org.eclipse.fordiac.ide.bulkeditor.query.model.AttributeType;
import org.eclipse.fordiac.ide.bulkeditor.query.model.BasicFB;
import org.eclipse.fordiac.ide.bulkeditor.query.model.BasicType;
import org.eclipse.fordiac.ide.bulkeditor.query.model.CompositeFB;
import org.eclipse.fordiac.ide.bulkeditor.query.model.CompositeType;
import org.eclipse.fordiac.ide.bulkeditor.query.model.Connection;
import org.eclipse.fordiac.ide.bulkeditor.query.model.FunctionFB;
import org.eclipse.fordiac.ide.bulkeditor.query.model.FunctionType;
import org.eclipse.fordiac.ide.bulkeditor.query.model.Place;
import org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage;
import org.eclipse.fordiac.ide.bulkeditor.query.model.ServiceInterfaceFB;
import org.eclipse.fordiac.ide.bulkeditor.query.model.ServiceInterfaceType;
import org.eclipse.fordiac.ide.bulkeditor.query.model.SimpleFB;
import org.eclipse.fordiac.ide.bulkeditor.query.model.SimpleType;
import org.eclipse.fordiac.ide.bulkeditor.query.model.StructType;
import org.eclipse.fordiac.ide.bulkeditor.query.model.SubappType;
import org.eclipse.fordiac.ide.bulkeditor.query.model.TypedSubapp;
import org.eclipse.fordiac.ide.bulkeditor.query.model.UntypedSubapp;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Place</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.PlaceImpl#isIgnoreLinkedLibraries <em>Ignore Linked Libraries</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.PlaceImpl#isSubappHierarchy <em>Subapp Hierarchy</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.PlaceImpl#getSubappHierarchyElements <em>Subapp Hierarchy Elements</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.PlaceImpl#getSimpleFB <em>Simple FB</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.PlaceImpl#getBasicFB <em>Basic FB</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.PlaceImpl#getCompositeFB <em>Composite FB</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.PlaceImpl#getServiceInterfaceFB <em>Service Interface FB</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.PlaceImpl#getFunctionFB <em>Function FB</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.PlaceImpl#getTypedSubapp <em>Typed Subapp</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.PlaceImpl#getUntypedSubapp <em>Untyped Subapp</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.PlaceImpl#getConnection <em>Connection</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.PlaceImpl#getSimpleType <em>Simple Type</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.PlaceImpl#getBasicType <em>Basic Type</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.PlaceImpl#getCompositeType <em>Composite Type</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.PlaceImpl#getServiceInterfaceType <em>Service Interface Type</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.PlaceImpl#getFunctionType <em>Function Type</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.PlaceImpl#getSubappType <em>Subapp Type</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.PlaceImpl#getStructType <em>Struct Type</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.PlaceImpl#getAttributeType <em>Attribute Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PlaceImpl extends EObjectImpl implements Place {
	/**
	 * The default value of the '{@link #isIgnoreLinkedLibraries() <em>Ignore Linked Libraries</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIgnoreLinkedLibraries()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IGNORE_LINKED_LIBRARIES_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isIgnoreLinkedLibraries() <em>Ignore Linked Libraries</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIgnoreLinkedLibraries()
	 * @generated
	 * @ordered
	 */
	protected boolean ignoreLinkedLibraries = IGNORE_LINKED_LIBRARIES_EDEFAULT;

	/**
	 * The default value of the '{@link #isSubappHierarchy() <em>Subapp Hierarchy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSubappHierarchy()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SUBAPP_HIERARCHY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isSubappHierarchy() <em>Subapp Hierarchy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSubappHierarchy()
	 * @generated
	 * @ordered
	 */
	protected boolean subappHierarchy = SUBAPP_HIERARCHY_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSubappHierarchyElements() <em>Subapp Hierarchy Elements</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubappHierarchyElements()
	 * @generated
	 * @ordered
	 */
	protected EList<String> subappHierarchyElements;

	/**
	 * The cached value of the '{@link #getSimpleFB() <em>Simple FB</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSimpleFB()
	 * @generated
	 * @ordered
	 */
	protected SimpleFB simpleFB;

	/**
	 * The cached value of the '{@link #getBasicFB() <em>Basic FB</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBasicFB()
	 * @generated
	 * @ordered
	 */
	protected BasicFB basicFB;

	/**
	 * The cached value of the '{@link #getCompositeFB() <em>Composite FB</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompositeFB()
	 * @generated
	 * @ordered
	 */
	protected CompositeFB compositeFB;

	/**
	 * The cached value of the '{@link #getServiceInterfaceFB() <em>Service Interface FB</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getServiceInterfaceFB()
	 * @generated
	 * @ordered
	 */
	protected ServiceInterfaceFB serviceInterfaceFB;

	/**
	 * The cached value of the '{@link #getFunctionFB() <em>Function FB</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFunctionFB()
	 * @generated
	 * @ordered
	 */
	protected FunctionFB functionFB;

	/**
	 * The cached value of the '{@link #getTypedSubapp() <em>Typed Subapp</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypedSubapp()
	 * @generated
	 * @ordered
	 */
	protected TypedSubapp typedSubapp;

	/**
	 * The cached value of the '{@link #getUntypedSubapp() <em>Untyped Subapp</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUntypedSubapp()
	 * @generated
	 * @ordered
	 */
	protected UntypedSubapp untypedSubapp;

	/**
	 * The cached value of the '{@link #getConnection() <em>Connection</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConnection()
	 * @generated
	 * @ordered
	 */
	protected Connection connection;

	/**
	 * The cached value of the '{@link #getSimpleType() <em>Simple Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSimpleType()
	 * @generated
	 * @ordered
	 */
	protected SimpleType simpleType;

	/**
	 * The cached value of the '{@link #getBasicType() <em>Basic Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBasicType()
	 * @generated
	 * @ordered
	 */
	protected BasicType basicType;

	/**
	 * The cached value of the '{@link #getCompositeType() <em>Composite Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompositeType()
	 * @generated
	 * @ordered
	 */
	protected CompositeType compositeType;

	/**
	 * The cached value of the '{@link #getServiceInterfaceType() <em>Service Interface Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getServiceInterfaceType()
	 * @generated
	 * @ordered
	 */
	protected ServiceInterfaceType serviceInterfaceType;

	/**
	 * The cached value of the '{@link #getFunctionType() <em>Function Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFunctionType()
	 * @generated
	 * @ordered
	 */
	protected FunctionType functionType;

	/**
	 * The cached value of the '{@link #getSubappType() <em>Subapp Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubappType()
	 * @generated
	 * @ordered
	 */
	protected SubappType subappType;

	/**
	 * The cached value of the '{@link #getStructType() <em>Struct Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStructType()
	 * @generated
	 * @ordered
	 */
	protected StructType structType;

	/**
	 * The cached value of the '{@link #getAttributeType() <em>Attribute Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributeType()
	 * @generated
	 * @ordered
	 */
	protected AttributeType attributeType;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PlaceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return QueryPackage.Literals.PLACE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isIgnoreLinkedLibraries() {
		return ignoreLinkedLibraries;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIgnoreLinkedLibraries(boolean newIgnoreLinkedLibraries) {
		boolean oldIgnoreLinkedLibraries = ignoreLinkedLibraries;
		ignoreLinkedLibraries = newIgnoreLinkedLibraries;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QueryPackage.PLACE__IGNORE_LINKED_LIBRARIES, oldIgnoreLinkedLibraries, ignoreLinkedLibraries));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSubappHierarchy() {
		return subappHierarchy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSubappHierarchy(boolean newSubappHierarchy) {
		boolean oldSubappHierarchy = subappHierarchy;
		subappHierarchy = newSubappHierarchy;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QueryPackage.PLACE__SUBAPP_HIERARCHY, oldSubappHierarchy, subappHierarchy));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getSubappHierarchyElements() {
		if (subappHierarchyElements == null) {
			subappHierarchyElements = new EDataTypeUniqueEList<String>(String.class, this, QueryPackage.PLACE__SUBAPP_HIERARCHY_ELEMENTS);
		}
		return subappHierarchyElements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SimpleFB getSimpleFB() {
		return simpleFB;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSimpleFB(SimpleFB newSimpleFB, NotificationChain msgs) {
		SimpleFB oldSimpleFB = simpleFB;
		simpleFB = newSimpleFB;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, QueryPackage.PLACE__SIMPLE_FB, oldSimpleFB, newSimpleFB);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSimpleFB(SimpleFB newSimpleFB) {
		if (newSimpleFB != simpleFB) {
			NotificationChain msgs = null;
			if (simpleFB != null)
				msgs = ((InternalEObject)simpleFB).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - QueryPackage.PLACE__SIMPLE_FB, null, msgs);
			if (newSimpleFB != null)
				msgs = ((InternalEObject)newSimpleFB).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - QueryPackage.PLACE__SIMPLE_FB, null, msgs);
			msgs = basicSetSimpleFB(newSimpleFB, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QueryPackage.PLACE__SIMPLE_FB, newSimpleFB, newSimpleFB));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BasicFB getBasicFB() {
		return basicFB;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBasicFB(BasicFB newBasicFB, NotificationChain msgs) {
		BasicFB oldBasicFB = basicFB;
		basicFB = newBasicFB;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, QueryPackage.PLACE__BASIC_FB, oldBasicFB, newBasicFB);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBasicFB(BasicFB newBasicFB) {
		if (newBasicFB != basicFB) {
			NotificationChain msgs = null;
			if (basicFB != null)
				msgs = ((InternalEObject)basicFB).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - QueryPackage.PLACE__BASIC_FB, null, msgs);
			if (newBasicFB != null)
				msgs = ((InternalEObject)newBasicFB).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - QueryPackage.PLACE__BASIC_FB, null, msgs);
			msgs = basicSetBasicFB(newBasicFB, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QueryPackage.PLACE__BASIC_FB, newBasicFB, newBasicFB));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CompositeFB getCompositeFB() {
		return compositeFB;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCompositeFB(CompositeFB newCompositeFB, NotificationChain msgs) {
		CompositeFB oldCompositeFB = compositeFB;
		compositeFB = newCompositeFB;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, QueryPackage.PLACE__COMPOSITE_FB, oldCompositeFB, newCompositeFB);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCompositeFB(CompositeFB newCompositeFB) {
		if (newCompositeFB != compositeFB) {
			NotificationChain msgs = null;
			if (compositeFB != null)
				msgs = ((InternalEObject)compositeFB).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - QueryPackage.PLACE__COMPOSITE_FB, null, msgs);
			if (newCompositeFB != null)
				msgs = ((InternalEObject)newCompositeFB).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - QueryPackage.PLACE__COMPOSITE_FB, null, msgs);
			msgs = basicSetCompositeFB(newCompositeFB, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QueryPackage.PLACE__COMPOSITE_FB, newCompositeFB, newCompositeFB));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ServiceInterfaceFB getServiceInterfaceFB() {
		return serviceInterfaceFB;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetServiceInterfaceFB(ServiceInterfaceFB newServiceInterfaceFB, NotificationChain msgs) {
		ServiceInterfaceFB oldServiceInterfaceFB = serviceInterfaceFB;
		serviceInterfaceFB = newServiceInterfaceFB;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, QueryPackage.PLACE__SERVICE_INTERFACE_FB, oldServiceInterfaceFB, newServiceInterfaceFB);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setServiceInterfaceFB(ServiceInterfaceFB newServiceInterfaceFB) {
		if (newServiceInterfaceFB != serviceInterfaceFB) {
			NotificationChain msgs = null;
			if (serviceInterfaceFB != null)
				msgs = ((InternalEObject)serviceInterfaceFB).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - QueryPackage.PLACE__SERVICE_INTERFACE_FB, null, msgs);
			if (newServiceInterfaceFB != null)
				msgs = ((InternalEObject)newServiceInterfaceFB).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - QueryPackage.PLACE__SERVICE_INTERFACE_FB, null, msgs);
			msgs = basicSetServiceInterfaceFB(newServiceInterfaceFB, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QueryPackage.PLACE__SERVICE_INTERFACE_FB, newServiceInterfaceFB, newServiceInterfaceFB));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FunctionFB getFunctionFB() {
		return functionFB;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFunctionFB(FunctionFB newFunctionFB, NotificationChain msgs) {
		FunctionFB oldFunctionFB = functionFB;
		functionFB = newFunctionFB;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, QueryPackage.PLACE__FUNCTION_FB, oldFunctionFB, newFunctionFB);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFunctionFB(FunctionFB newFunctionFB) {
		if (newFunctionFB != functionFB) {
			NotificationChain msgs = null;
			if (functionFB != null)
				msgs = ((InternalEObject)functionFB).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - QueryPackage.PLACE__FUNCTION_FB, null, msgs);
			if (newFunctionFB != null)
				msgs = ((InternalEObject)newFunctionFB).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - QueryPackage.PLACE__FUNCTION_FB, null, msgs);
			msgs = basicSetFunctionFB(newFunctionFB, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QueryPackage.PLACE__FUNCTION_FB, newFunctionFB, newFunctionFB));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypedSubapp getTypedSubapp() {
		return typedSubapp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTypedSubapp(TypedSubapp newTypedSubapp, NotificationChain msgs) {
		TypedSubapp oldTypedSubapp = typedSubapp;
		typedSubapp = newTypedSubapp;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, QueryPackage.PLACE__TYPED_SUBAPP, oldTypedSubapp, newTypedSubapp);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTypedSubapp(TypedSubapp newTypedSubapp) {
		if (newTypedSubapp != typedSubapp) {
			NotificationChain msgs = null;
			if (typedSubapp != null)
				msgs = ((InternalEObject)typedSubapp).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - QueryPackage.PLACE__TYPED_SUBAPP, null, msgs);
			if (newTypedSubapp != null)
				msgs = ((InternalEObject)newTypedSubapp).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - QueryPackage.PLACE__TYPED_SUBAPP, null, msgs);
			msgs = basicSetTypedSubapp(newTypedSubapp, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QueryPackage.PLACE__TYPED_SUBAPP, newTypedSubapp, newTypedSubapp));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public UntypedSubapp getUntypedSubapp() {
		return untypedSubapp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUntypedSubapp(UntypedSubapp newUntypedSubapp, NotificationChain msgs) {
		UntypedSubapp oldUntypedSubapp = untypedSubapp;
		untypedSubapp = newUntypedSubapp;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, QueryPackage.PLACE__UNTYPED_SUBAPP, oldUntypedSubapp, newUntypedSubapp);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setUntypedSubapp(UntypedSubapp newUntypedSubapp) {
		if (newUntypedSubapp != untypedSubapp) {
			NotificationChain msgs = null;
			if (untypedSubapp != null)
				msgs = ((InternalEObject)untypedSubapp).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - QueryPackage.PLACE__UNTYPED_SUBAPP, null, msgs);
			if (newUntypedSubapp != null)
				msgs = ((InternalEObject)newUntypedSubapp).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - QueryPackage.PLACE__UNTYPED_SUBAPP, null, msgs);
			msgs = basicSetUntypedSubapp(newUntypedSubapp, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QueryPackage.PLACE__UNTYPED_SUBAPP, newUntypedSubapp, newUntypedSubapp));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Connection getConnection() {
		return connection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConnection(Connection newConnection, NotificationChain msgs) {
		Connection oldConnection = connection;
		connection = newConnection;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, QueryPackage.PLACE__CONNECTION, oldConnection, newConnection);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setConnection(Connection newConnection) {
		if (newConnection != connection) {
			NotificationChain msgs = null;
			if (connection != null)
				msgs = ((InternalEObject)connection).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - QueryPackage.PLACE__CONNECTION, null, msgs);
			if (newConnection != null)
				msgs = ((InternalEObject)newConnection).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - QueryPackage.PLACE__CONNECTION, null, msgs);
			msgs = basicSetConnection(newConnection, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QueryPackage.PLACE__CONNECTION, newConnection, newConnection));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SimpleType getSimpleType() {
		return simpleType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSimpleType(SimpleType newSimpleType, NotificationChain msgs) {
		SimpleType oldSimpleType = simpleType;
		simpleType = newSimpleType;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, QueryPackage.PLACE__SIMPLE_TYPE, oldSimpleType, newSimpleType);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSimpleType(SimpleType newSimpleType) {
		if (newSimpleType != simpleType) {
			NotificationChain msgs = null;
			if (simpleType != null)
				msgs = ((InternalEObject)simpleType).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - QueryPackage.PLACE__SIMPLE_TYPE, null, msgs);
			if (newSimpleType != null)
				msgs = ((InternalEObject)newSimpleType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - QueryPackage.PLACE__SIMPLE_TYPE, null, msgs);
			msgs = basicSetSimpleType(newSimpleType, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QueryPackage.PLACE__SIMPLE_TYPE, newSimpleType, newSimpleType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BasicType getBasicType() {
		return basicType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBasicType(BasicType newBasicType, NotificationChain msgs) {
		BasicType oldBasicType = basicType;
		basicType = newBasicType;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, QueryPackage.PLACE__BASIC_TYPE, oldBasicType, newBasicType);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBasicType(BasicType newBasicType) {
		if (newBasicType != basicType) {
			NotificationChain msgs = null;
			if (basicType != null)
				msgs = ((InternalEObject)basicType).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - QueryPackage.PLACE__BASIC_TYPE, null, msgs);
			if (newBasicType != null)
				msgs = ((InternalEObject)newBasicType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - QueryPackage.PLACE__BASIC_TYPE, null, msgs);
			msgs = basicSetBasicType(newBasicType, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QueryPackage.PLACE__BASIC_TYPE, newBasicType, newBasicType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CompositeType getCompositeType() {
		return compositeType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCompositeType(CompositeType newCompositeType, NotificationChain msgs) {
		CompositeType oldCompositeType = compositeType;
		compositeType = newCompositeType;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, QueryPackage.PLACE__COMPOSITE_TYPE, oldCompositeType, newCompositeType);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCompositeType(CompositeType newCompositeType) {
		if (newCompositeType != compositeType) {
			NotificationChain msgs = null;
			if (compositeType != null)
				msgs = ((InternalEObject)compositeType).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - QueryPackage.PLACE__COMPOSITE_TYPE, null, msgs);
			if (newCompositeType != null)
				msgs = ((InternalEObject)newCompositeType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - QueryPackage.PLACE__COMPOSITE_TYPE, null, msgs);
			msgs = basicSetCompositeType(newCompositeType, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QueryPackage.PLACE__COMPOSITE_TYPE, newCompositeType, newCompositeType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ServiceInterfaceType getServiceInterfaceType() {
		return serviceInterfaceType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetServiceInterfaceType(ServiceInterfaceType newServiceInterfaceType, NotificationChain msgs) {
		ServiceInterfaceType oldServiceInterfaceType = serviceInterfaceType;
		serviceInterfaceType = newServiceInterfaceType;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, QueryPackage.PLACE__SERVICE_INTERFACE_TYPE, oldServiceInterfaceType, newServiceInterfaceType);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setServiceInterfaceType(ServiceInterfaceType newServiceInterfaceType) {
		if (newServiceInterfaceType != serviceInterfaceType) {
			NotificationChain msgs = null;
			if (serviceInterfaceType != null)
				msgs = ((InternalEObject)serviceInterfaceType).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - QueryPackage.PLACE__SERVICE_INTERFACE_TYPE, null, msgs);
			if (newServiceInterfaceType != null)
				msgs = ((InternalEObject)newServiceInterfaceType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - QueryPackage.PLACE__SERVICE_INTERFACE_TYPE, null, msgs);
			msgs = basicSetServiceInterfaceType(newServiceInterfaceType, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QueryPackage.PLACE__SERVICE_INTERFACE_TYPE, newServiceInterfaceType, newServiceInterfaceType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FunctionType getFunctionType() {
		return functionType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFunctionType(FunctionType newFunctionType, NotificationChain msgs) {
		FunctionType oldFunctionType = functionType;
		functionType = newFunctionType;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, QueryPackage.PLACE__FUNCTION_TYPE, oldFunctionType, newFunctionType);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFunctionType(FunctionType newFunctionType) {
		if (newFunctionType != functionType) {
			NotificationChain msgs = null;
			if (functionType != null)
				msgs = ((InternalEObject)functionType).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - QueryPackage.PLACE__FUNCTION_TYPE, null, msgs);
			if (newFunctionType != null)
				msgs = ((InternalEObject)newFunctionType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - QueryPackage.PLACE__FUNCTION_TYPE, null, msgs);
			msgs = basicSetFunctionType(newFunctionType, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QueryPackage.PLACE__FUNCTION_TYPE, newFunctionType, newFunctionType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SubappType getSubappType() {
		return subappType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSubappType(SubappType newSubappType, NotificationChain msgs) {
		SubappType oldSubappType = subappType;
		subappType = newSubappType;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, QueryPackage.PLACE__SUBAPP_TYPE, oldSubappType, newSubappType);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSubappType(SubappType newSubappType) {
		if (newSubappType != subappType) {
			NotificationChain msgs = null;
			if (subappType != null)
				msgs = ((InternalEObject)subappType).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - QueryPackage.PLACE__SUBAPP_TYPE, null, msgs);
			if (newSubappType != null)
				msgs = ((InternalEObject)newSubappType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - QueryPackage.PLACE__SUBAPP_TYPE, null, msgs);
			msgs = basicSetSubappType(newSubappType, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QueryPackage.PLACE__SUBAPP_TYPE, newSubappType, newSubappType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public StructType getStructType() {
		return structType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStructType(StructType newStructType, NotificationChain msgs) {
		StructType oldStructType = structType;
		structType = newStructType;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, QueryPackage.PLACE__STRUCT_TYPE, oldStructType, newStructType);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setStructType(StructType newStructType) {
		if (newStructType != structType) {
			NotificationChain msgs = null;
			if (structType != null)
				msgs = ((InternalEObject)structType).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - QueryPackage.PLACE__STRUCT_TYPE, null, msgs);
			if (newStructType != null)
				msgs = ((InternalEObject)newStructType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - QueryPackage.PLACE__STRUCT_TYPE, null, msgs);
			msgs = basicSetStructType(newStructType, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QueryPackage.PLACE__STRUCT_TYPE, newStructType, newStructType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AttributeType getAttributeType() {
		return attributeType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAttributeType(AttributeType newAttributeType, NotificationChain msgs) {
		AttributeType oldAttributeType = attributeType;
		attributeType = newAttributeType;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, QueryPackage.PLACE__ATTRIBUTE_TYPE, oldAttributeType, newAttributeType);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAttributeType(AttributeType newAttributeType) {
		if (newAttributeType != attributeType) {
			NotificationChain msgs = null;
			if (attributeType != null)
				msgs = ((InternalEObject)attributeType).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - QueryPackage.PLACE__ATTRIBUTE_TYPE, null, msgs);
			if (newAttributeType != null)
				msgs = ((InternalEObject)newAttributeType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - QueryPackage.PLACE__ATTRIBUTE_TYPE, null, msgs);
			msgs = basicSetAttributeType(newAttributeType, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QueryPackage.PLACE__ATTRIBUTE_TYPE, newAttributeType, newAttributeType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case QueryPackage.PLACE__SIMPLE_FB:
				return basicSetSimpleFB(null, msgs);
			case QueryPackage.PLACE__BASIC_FB:
				return basicSetBasicFB(null, msgs);
			case QueryPackage.PLACE__COMPOSITE_FB:
				return basicSetCompositeFB(null, msgs);
			case QueryPackage.PLACE__SERVICE_INTERFACE_FB:
				return basicSetServiceInterfaceFB(null, msgs);
			case QueryPackage.PLACE__FUNCTION_FB:
				return basicSetFunctionFB(null, msgs);
			case QueryPackage.PLACE__TYPED_SUBAPP:
				return basicSetTypedSubapp(null, msgs);
			case QueryPackage.PLACE__UNTYPED_SUBAPP:
				return basicSetUntypedSubapp(null, msgs);
			case QueryPackage.PLACE__CONNECTION:
				return basicSetConnection(null, msgs);
			case QueryPackage.PLACE__SIMPLE_TYPE:
				return basicSetSimpleType(null, msgs);
			case QueryPackage.PLACE__BASIC_TYPE:
				return basicSetBasicType(null, msgs);
			case QueryPackage.PLACE__COMPOSITE_TYPE:
				return basicSetCompositeType(null, msgs);
			case QueryPackage.PLACE__SERVICE_INTERFACE_TYPE:
				return basicSetServiceInterfaceType(null, msgs);
			case QueryPackage.PLACE__FUNCTION_TYPE:
				return basicSetFunctionType(null, msgs);
			case QueryPackage.PLACE__SUBAPP_TYPE:
				return basicSetSubappType(null, msgs);
			case QueryPackage.PLACE__STRUCT_TYPE:
				return basicSetStructType(null, msgs);
			case QueryPackage.PLACE__ATTRIBUTE_TYPE:
				return basicSetAttributeType(null, msgs);
			default:
				return super.eInverseRemove(otherEnd, featureID, msgs);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case QueryPackage.PLACE__IGNORE_LINKED_LIBRARIES:
				return isIgnoreLinkedLibraries();
			case QueryPackage.PLACE__SUBAPP_HIERARCHY:
				return isSubappHierarchy();
			case QueryPackage.PLACE__SUBAPP_HIERARCHY_ELEMENTS:
				return getSubappHierarchyElements();
			case QueryPackage.PLACE__SIMPLE_FB:
				return getSimpleFB();
			case QueryPackage.PLACE__BASIC_FB:
				return getBasicFB();
			case QueryPackage.PLACE__COMPOSITE_FB:
				return getCompositeFB();
			case QueryPackage.PLACE__SERVICE_INTERFACE_FB:
				return getServiceInterfaceFB();
			case QueryPackage.PLACE__FUNCTION_FB:
				return getFunctionFB();
			case QueryPackage.PLACE__TYPED_SUBAPP:
				return getTypedSubapp();
			case QueryPackage.PLACE__UNTYPED_SUBAPP:
				return getUntypedSubapp();
			case QueryPackage.PLACE__CONNECTION:
				return getConnection();
			case QueryPackage.PLACE__SIMPLE_TYPE:
				return getSimpleType();
			case QueryPackage.PLACE__BASIC_TYPE:
				return getBasicType();
			case QueryPackage.PLACE__COMPOSITE_TYPE:
				return getCompositeType();
			case QueryPackage.PLACE__SERVICE_INTERFACE_TYPE:
				return getServiceInterfaceType();
			case QueryPackage.PLACE__FUNCTION_TYPE:
				return getFunctionType();
			case QueryPackage.PLACE__SUBAPP_TYPE:
				return getSubappType();
			case QueryPackage.PLACE__STRUCT_TYPE:
				return getStructType();
			case QueryPackage.PLACE__ATTRIBUTE_TYPE:
				return getAttributeType();
			default:
				return super.eGet(featureID, resolve, coreType);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case QueryPackage.PLACE__IGNORE_LINKED_LIBRARIES:
				setIgnoreLinkedLibraries((Boolean)newValue);
				return;
			case QueryPackage.PLACE__SUBAPP_HIERARCHY:
				setSubappHierarchy((Boolean)newValue);
				return;
			case QueryPackage.PLACE__SUBAPP_HIERARCHY_ELEMENTS:
				getSubappHierarchyElements().clear();
				getSubappHierarchyElements().addAll((Collection<? extends String>)newValue);
				return;
			case QueryPackage.PLACE__SIMPLE_FB:
				setSimpleFB((SimpleFB)newValue);
				return;
			case QueryPackage.PLACE__BASIC_FB:
				setBasicFB((BasicFB)newValue);
				return;
			case QueryPackage.PLACE__COMPOSITE_FB:
				setCompositeFB((CompositeFB)newValue);
				return;
			case QueryPackage.PLACE__SERVICE_INTERFACE_FB:
				setServiceInterfaceFB((ServiceInterfaceFB)newValue);
				return;
			case QueryPackage.PLACE__FUNCTION_FB:
				setFunctionFB((FunctionFB)newValue);
				return;
			case QueryPackage.PLACE__TYPED_SUBAPP:
				setTypedSubapp((TypedSubapp)newValue);
				return;
			case QueryPackage.PLACE__UNTYPED_SUBAPP:
				setUntypedSubapp((UntypedSubapp)newValue);
				return;
			case QueryPackage.PLACE__CONNECTION:
				setConnection((Connection)newValue);
				return;
			case QueryPackage.PLACE__SIMPLE_TYPE:
				setSimpleType((SimpleType)newValue);
				return;
			case QueryPackage.PLACE__BASIC_TYPE:
				setBasicType((BasicType)newValue);
				return;
			case QueryPackage.PLACE__COMPOSITE_TYPE:
				setCompositeType((CompositeType)newValue);
				return;
			case QueryPackage.PLACE__SERVICE_INTERFACE_TYPE:
				setServiceInterfaceType((ServiceInterfaceType)newValue);
				return;
			case QueryPackage.PLACE__FUNCTION_TYPE:
				setFunctionType((FunctionType)newValue);
				return;
			case QueryPackage.PLACE__SUBAPP_TYPE:
				setSubappType((SubappType)newValue);
				return;
			case QueryPackage.PLACE__STRUCT_TYPE:
				setStructType((StructType)newValue);
				return;
			case QueryPackage.PLACE__ATTRIBUTE_TYPE:
				setAttributeType((AttributeType)newValue);
				return;
			default:
				super.eSet(featureID, newValue);
				return;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case QueryPackage.PLACE__IGNORE_LINKED_LIBRARIES:
				setIgnoreLinkedLibraries(IGNORE_LINKED_LIBRARIES_EDEFAULT);
				return;
			case QueryPackage.PLACE__SUBAPP_HIERARCHY:
				setSubappHierarchy(SUBAPP_HIERARCHY_EDEFAULT);
				return;
			case QueryPackage.PLACE__SUBAPP_HIERARCHY_ELEMENTS:
				getSubappHierarchyElements().clear();
				return;
			case QueryPackage.PLACE__SIMPLE_FB:
				setSimpleFB((SimpleFB)null);
				return;
			case QueryPackage.PLACE__BASIC_FB:
				setBasicFB((BasicFB)null);
				return;
			case QueryPackage.PLACE__COMPOSITE_FB:
				setCompositeFB((CompositeFB)null);
				return;
			case QueryPackage.PLACE__SERVICE_INTERFACE_FB:
				setServiceInterfaceFB((ServiceInterfaceFB)null);
				return;
			case QueryPackage.PLACE__FUNCTION_FB:
				setFunctionFB((FunctionFB)null);
				return;
			case QueryPackage.PLACE__TYPED_SUBAPP:
				setTypedSubapp((TypedSubapp)null);
				return;
			case QueryPackage.PLACE__UNTYPED_SUBAPP:
				setUntypedSubapp((UntypedSubapp)null);
				return;
			case QueryPackage.PLACE__CONNECTION:
				setConnection((Connection)null);
				return;
			case QueryPackage.PLACE__SIMPLE_TYPE:
				setSimpleType((SimpleType)null);
				return;
			case QueryPackage.PLACE__BASIC_TYPE:
				setBasicType((BasicType)null);
				return;
			case QueryPackage.PLACE__COMPOSITE_TYPE:
				setCompositeType((CompositeType)null);
				return;
			case QueryPackage.PLACE__SERVICE_INTERFACE_TYPE:
				setServiceInterfaceType((ServiceInterfaceType)null);
				return;
			case QueryPackage.PLACE__FUNCTION_TYPE:
				setFunctionType((FunctionType)null);
				return;
			case QueryPackage.PLACE__SUBAPP_TYPE:
				setSubappType((SubappType)null);
				return;
			case QueryPackage.PLACE__STRUCT_TYPE:
				setStructType((StructType)null);
				return;
			case QueryPackage.PLACE__ATTRIBUTE_TYPE:
				setAttributeType((AttributeType)null);
				return;
			default:
				super.eUnset(featureID);
				return;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case QueryPackage.PLACE__IGNORE_LINKED_LIBRARIES:
				return ignoreLinkedLibraries != IGNORE_LINKED_LIBRARIES_EDEFAULT;
			case QueryPackage.PLACE__SUBAPP_HIERARCHY:
				return subappHierarchy != SUBAPP_HIERARCHY_EDEFAULT;
			case QueryPackage.PLACE__SUBAPP_HIERARCHY_ELEMENTS:
				return subappHierarchyElements != null && !subappHierarchyElements.isEmpty();
			case QueryPackage.PLACE__SIMPLE_FB:
				return simpleFB != null;
			case QueryPackage.PLACE__BASIC_FB:
				return basicFB != null;
			case QueryPackage.PLACE__COMPOSITE_FB:
				return compositeFB != null;
			case QueryPackage.PLACE__SERVICE_INTERFACE_FB:
				return serviceInterfaceFB != null;
			case QueryPackage.PLACE__FUNCTION_FB:
				return functionFB != null;
			case QueryPackage.PLACE__TYPED_SUBAPP:
				return typedSubapp != null;
			case QueryPackage.PLACE__UNTYPED_SUBAPP:
				return untypedSubapp != null;
			case QueryPackage.PLACE__CONNECTION:
				return connection != null;
			case QueryPackage.PLACE__SIMPLE_TYPE:
				return simpleType != null;
			case QueryPackage.PLACE__BASIC_TYPE:
				return basicType != null;
			case QueryPackage.PLACE__COMPOSITE_TYPE:
				return compositeType != null;
			case QueryPackage.PLACE__SERVICE_INTERFACE_TYPE:
				return serviceInterfaceType != null;
			case QueryPackage.PLACE__FUNCTION_TYPE:
				return functionType != null;
			case QueryPackage.PLACE__SUBAPP_TYPE:
				return subappType != null;
			case QueryPackage.PLACE__STRUCT_TYPE:
				return structType != null;
			case QueryPackage.PLACE__ATTRIBUTE_TYPE:
				return attributeType != null;
			default:
				return super.eIsSet(featureID);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (ignoreLinkedLibraries: "); //$NON-NLS-1$
		result.append(ignoreLinkedLibraries);
		result.append(", subappHierarchy: "); //$NON-NLS-1$
		result.append(subappHierarchy);
		result.append(", subappHierarchyElements: "); //$NON-NLS-1$
		result.append(subappHierarchyElements);
		result.append(')');
		return result.toString();
	}

} //PlaceImpl
