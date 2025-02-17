/**
 * *******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *               2022 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.model.libraryElement.impl;

import java.util.Map;
import java.util.stream.Stream;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Attribute</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AttributeImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AttributeImpl#getComment <em>Comment</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AttributeImpl#getAttributeDeclaration <em>Attribute Declaration</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AttributeImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AttributeImpl#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AttributeImpl extends EObjectImpl implements Attribute {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = ""; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getComment() <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComment()
	 * @generated
	 * @ordered
	 */
	protected static final String COMMENT_EDEFAULT = ""; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getComment() <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComment()
	 * @generated
	 * @ordered
	 */
	protected String comment = COMMENT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAttributeDeclaration() <em>Attribute Declaration</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributeDeclaration()
	 * @generated
	 * @ordered
	 */
	protected AttributeDeclaration attributeDeclaration;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected DataType type;

	/**
	 * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected static final String VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected String value = VALUE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AttributeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.ATTRIBUTE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.ATTRIBUTE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getComment() {
		return comment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setComment(String newComment) {
		String oldComment = comment;
		comment = newComment;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.ATTRIBUTE__COMMENT, oldComment, comment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getQualifiedName() {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.AttributeAnnotations.getQualifiedName(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean validateName(final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.AttributeAnnotations.validateName(this, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean validateAttributeDeclaration(final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.AttributeAnnotations.validateAttributeDeclaration(this, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DataType getType() {
		if (type != null && type.eIsProxy()) {
			InternalEObject oldType = (InternalEObject)type;
			type = (DataType)eResolveProxy(oldType);
			if (type != oldType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LibraryElementPackage.ATTRIBUTE__TYPE, oldType, type));
			}
		}
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataType basicGetType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setType(DataType newType) {
		DataType oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.ATTRIBUTE__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AttributeDeclaration getAttributeDeclaration() {
		if (attributeDeclaration != null && attributeDeclaration.eIsProxy()) {
			InternalEObject oldAttributeDeclaration = (InternalEObject)attributeDeclaration;
			attributeDeclaration = (AttributeDeclaration)eResolveProxy(oldAttributeDeclaration);
			if (attributeDeclaration != oldAttributeDeclaration) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LibraryElementPackage.ATTRIBUTE__ATTRIBUTE_DECLARATION, oldAttributeDeclaration, attributeDeclaration));
			}
		}
		return attributeDeclaration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeDeclaration basicGetAttributeDeclaration() {
		return attributeDeclaration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAttributeDeclaration(AttributeDeclaration newAttributeDeclaration) {
		AttributeDeclaration oldAttributeDeclaration = attributeDeclaration;
		attributeDeclaration = newAttributeDeclaration;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.ATTRIBUTE__ATTRIBUTE_DECLARATION, oldAttributeDeclaration, attributeDeclaration));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setValue(String newValue) {
		String oldValue = value;
		value = newValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.ATTRIBUTE__VALUE, oldValue, value));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getTypeName() {
		org.eclipse.fordiac.ide.model.libraryElement.INamedElement type = getType();
		if(type != null){
			return type.getName();
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getFullTypeName() {
		return getTypeName();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean validateType(final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.TypedElementAnnotations.validateType(this, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Stream<INamedElement> findBySimpleName(final String name) {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.NamedElementAnnotations.findBySimpleName(this, name);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Stream<INamedElement> findByQualifiedName(final String name) {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.NamedElementAnnotations.findByQualifiedName(this, name);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case LibraryElementPackage.ATTRIBUTE__NAME:
				return getName();
			case LibraryElementPackage.ATTRIBUTE__COMMENT:
				return getComment();
			case LibraryElementPackage.ATTRIBUTE__ATTRIBUTE_DECLARATION:
				if (resolve) return getAttributeDeclaration();
				return basicGetAttributeDeclaration();
			case LibraryElementPackage.ATTRIBUTE__TYPE:
				if (resolve) return getType();
				return basicGetType();
			case LibraryElementPackage.ATTRIBUTE__VALUE:
				return getValue();
			default:
				return super.eGet(featureID, resolve, coreType);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case LibraryElementPackage.ATTRIBUTE__NAME:
				setName((String)newValue);
				return;
			case LibraryElementPackage.ATTRIBUTE__COMMENT:
				setComment((String)newValue);
				return;
			case LibraryElementPackage.ATTRIBUTE__ATTRIBUTE_DECLARATION:
				setAttributeDeclaration((AttributeDeclaration)newValue);
				return;
			case LibraryElementPackage.ATTRIBUTE__TYPE:
				setType((DataType)newValue);
				return;
			case LibraryElementPackage.ATTRIBUTE__VALUE:
				setValue((String)newValue);
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
			case LibraryElementPackage.ATTRIBUTE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case LibraryElementPackage.ATTRIBUTE__COMMENT:
				setComment(COMMENT_EDEFAULT);
				return;
			case LibraryElementPackage.ATTRIBUTE__ATTRIBUTE_DECLARATION:
				setAttributeDeclaration((AttributeDeclaration)null);
				return;
			case LibraryElementPackage.ATTRIBUTE__TYPE:
				setType((DataType)null);
				return;
			case LibraryElementPackage.ATTRIBUTE__VALUE:
				setValue(VALUE_EDEFAULT);
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
			case LibraryElementPackage.ATTRIBUTE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case LibraryElementPackage.ATTRIBUTE__COMMENT:
				return COMMENT_EDEFAULT == null ? comment != null : !COMMENT_EDEFAULT.equals(comment);
			case LibraryElementPackage.ATTRIBUTE__ATTRIBUTE_DECLARATION:
				return attributeDeclaration != null;
			case LibraryElementPackage.ATTRIBUTE__TYPE:
				return type != null;
			case LibraryElementPackage.ATTRIBUTE__VALUE:
				return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT.equals(value);
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
		result.append(" (name: "); //$NON-NLS-1$
		result.append(name);
		result.append(", comment: "); //$NON-NLS-1$
		result.append(comment);
		result.append(", value: "); //$NON-NLS-1$
		result.append(value);
		result.append(')');
		return result.toString();
	}

} //AttributeImpl
