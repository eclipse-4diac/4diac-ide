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

import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.fordiac.ide.model.data.AnyDerivedType;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Identification;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.VersionInfo;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Attribute Declaration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AttributeDeclarationImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AttributeDeclarationImpl#getComment <em>Comment</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AttributeDeclarationImpl#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AttributeDeclarationImpl#getVersionInfo <em>Version Info</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AttributeDeclarationImpl#getIdentification <em>Identification</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AttributeDeclarationImpl#getCompilerInfo <em>Compiler Info</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AttributeDeclarationImpl#getTypeEntry <em>Type Entry</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AttributeDeclarationImpl#getType <em>Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AttributeDeclarationImpl extends EObjectImpl implements AttributeDeclaration {
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
	 * The cached value of the '{@link #getAttributes() <em>Attributes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributes()
	 * @generated
	 * @ordered
	 */
	protected EList<Attribute> attributes;

	/**
	 * The cached value of the '{@link #getVersionInfo() <em>Version Info</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersionInfo()
	 * @generated
	 * @ordered
	 */
	protected EList<VersionInfo> versionInfo;

	/**
	 * The cached value of the '{@link #getIdentification() <em>Identification</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIdentification()
	 * @generated
	 * @ordered
	 */
	protected Identification identification;

	/**
	 * The cached value of the '{@link #getCompilerInfo() <em>Compiler Info</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompilerInfo()
	 * @generated
	 * @ordered
	 */
	protected CompilerInfo compilerInfo;

	/**
	 * The default value of the '{@link #getTypeEntry() <em>Type Entry</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeEntry()
	 * @generated
	 * @ordered
	 */
	protected static final TypeEntry TYPE_ENTRY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTypeEntry() <em>Type Entry</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeEntry()
	 * @generated
	 * @ordered
	 */
	protected TypeEntry typeEntry = TYPE_ENTRY_EDEFAULT;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected AnyDerivedType type;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AttributeDeclarationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.ATTRIBUTE_DECLARATION;
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
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.ATTRIBUTE_DECLARATION__NAME, oldName, name));
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
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.ATTRIBUTE_DECLARATION__COMMENT, oldComment, comment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Attribute> getAttributes() {
		if (attributes == null) {
			attributes = new EObjectContainmentEList.Resolving<Attribute>(Attribute.class, this, LibraryElementPackage.ATTRIBUTE_DECLARATION__ATTRIBUTES);
		}
		return attributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<VersionInfo> getVersionInfo() {
		if (versionInfo == null) {
			versionInfo = new EObjectContainmentEList.Resolving<VersionInfo>(VersionInfo.class, this, LibraryElementPackage.ATTRIBUTE_DECLARATION__VERSION_INFO);
		}
		return versionInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Identification getIdentification() {
		if (identification != null && identification.eIsProxy()) {
			InternalEObject oldIdentification = (InternalEObject)identification;
			identification = (Identification)eResolveProxy(oldIdentification);
			if (identification != oldIdentification) {
				InternalEObject newIdentification = (InternalEObject)identification;
				NotificationChain msgs = oldIdentification.eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.ATTRIBUTE_DECLARATION__IDENTIFICATION, null, null);
				if (newIdentification.eInternalContainer() == null) {
					msgs = newIdentification.eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.ATTRIBUTE_DECLARATION__IDENTIFICATION, null, msgs);
				}
				if (msgs != null) msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LibraryElementPackage.ATTRIBUTE_DECLARATION__IDENTIFICATION, oldIdentification, identification));
			}
		}
		return identification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Identification basicGetIdentification() {
		return identification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIdentification(Identification newIdentification, NotificationChain msgs) {
		Identification oldIdentification = identification;
		identification = newIdentification;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, LibraryElementPackage.ATTRIBUTE_DECLARATION__IDENTIFICATION, oldIdentification, newIdentification);
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
	public void setIdentification(Identification newIdentification) {
		if (newIdentification != identification) {
			NotificationChain msgs = null;
			if (identification != null)
				msgs = ((InternalEObject)identification).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.ATTRIBUTE_DECLARATION__IDENTIFICATION, null, msgs);
			if (newIdentification != null)
				msgs = ((InternalEObject)newIdentification).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.ATTRIBUTE_DECLARATION__IDENTIFICATION, null, msgs);
			msgs = basicSetIdentification(newIdentification, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.ATTRIBUTE_DECLARATION__IDENTIFICATION, newIdentification, newIdentification));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CompilerInfo getCompilerInfo() {
		if (compilerInfo != null && compilerInfo.eIsProxy()) {
			InternalEObject oldCompilerInfo = (InternalEObject)compilerInfo;
			compilerInfo = (CompilerInfo)eResolveProxy(oldCompilerInfo);
			if (compilerInfo != oldCompilerInfo) {
				InternalEObject newCompilerInfo = (InternalEObject)compilerInfo;
				NotificationChain msgs = oldCompilerInfo.eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.ATTRIBUTE_DECLARATION__COMPILER_INFO, null, null);
				if (newCompilerInfo.eInternalContainer() == null) {
					msgs = newCompilerInfo.eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.ATTRIBUTE_DECLARATION__COMPILER_INFO, null, msgs);
				}
				if (msgs != null) msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LibraryElementPackage.ATTRIBUTE_DECLARATION__COMPILER_INFO, oldCompilerInfo, compilerInfo));
			}
		}
		return compilerInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompilerInfo basicGetCompilerInfo() {
		return compilerInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCompilerInfo(CompilerInfo newCompilerInfo, NotificationChain msgs) {
		CompilerInfo oldCompilerInfo = compilerInfo;
		compilerInfo = newCompilerInfo;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, LibraryElementPackage.ATTRIBUTE_DECLARATION__COMPILER_INFO, oldCompilerInfo, newCompilerInfo);
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
	public void setCompilerInfo(CompilerInfo newCompilerInfo) {
		if (newCompilerInfo != compilerInfo) {
			NotificationChain msgs = null;
			if (compilerInfo != null)
				msgs = ((InternalEObject)compilerInfo).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.ATTRIBUTE_DECLARATION__COMPILER_INFO, null, msgs);
			if (newCompilerInfo != null)
				msgs = ((InternalEObject)newCompilerInfo).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.ATTRIBUTE_DECLARATION__COMPILER_INFO, null, msgs);
			msgs = basicSetCompilerInfo(newCompilerInfo, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.ATTRIBUTE_DECLARATION__COMPILER_INFO, newCompilerInfo, newCompilerInfo));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeEntry getTypeEntry() {
		return typeEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTypeEntry(TypeEntry newTypeEntry) {
		TypeEntry oldTypeEntry = typeEntry;
		typeEntry = newTypeEntry;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.ATTRIBUTE_DECLARATION__TYPE_ENTRY, oldTypeEntry, typeEntry));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getQualifiedName() {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.NamedElementAnnotations.getQualifiedName(this);
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
	public boolean validateName(final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.NamedElementAnnotations.validateName(this, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LibraryElementPackage.ATTRIBUTE_DECLARATION__ATTRIBUTES:
				return ((InternalEList<?>)getAttributes()).basicRemove(otherEnd, msgs);
			case LibraryElementPackage.ATTRIBUTE_DECLARATION__VERSION_INFO:
				return ((InternalEList<?>)getVersionInfo()).basicRemove(otherEnd, msgs);
			case LibraryElementPackage.ATTRIBUTE_DECLARATION__IDENTIFICATION:
				return basicSetIdentification(null, msgs);
			case LibraryElementPackage.ATTRIBUTE_DECLARATION__COMPILER_INFO:
				return basicSetCompilerInfo(null, msgs);
			case LibraryElementPackage.ATTRIBUTE_DECLARATION__TYPE:
				return basicSetType(null, msgs);
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
	public AnyDerivedType getType() {
		if (type != null && type.eIsProxy()) {
			InternalEObject oldType = (InternalEObject)type;
			type = (AnyDerivedType)eResolveProxy(oldType);
			if (type != oldType) {
				InternalEObject newType = (InternalEObject)type;
				NotificationChain msgs = oldType.eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.ATTRIBUTE_DECLARATION__TYPE, null, null);
				if (newType.eInternalContainer() == null) {
					msgs = newType.eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.ATTRIBUTE_DECLARATION__TYPE, null, msgs);
				}
				if (msgs != null) msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LibraryElementPackage.ATTRIBUTE_DECLARATION__TYPE, oldType, type));
			}
		}
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AnyDerivedType basicGetType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetType(AnyDerivedType newType, NotificationChain msgs) {
		AnyDerivedType oldType = type;
		type = newType;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, LibraryElementPackage.ATTRIBUTE_DECLARATION__TYPE, oldType, newType);
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
	public void setType(AnyDerivedType newType) {
		if (newType != type) {
			NotificationChain msgs = null;
			if (type != null)
				msgs = ((InternalEObject)type).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.ATTRIBUTE_DECLARATION__TYPE, null, msgs);
			if (newType != null)
				msgs = ((InternalEObject)newType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.ATTRIBUTE_DECLARATION__TYPE, null, msgs);
			msgs = basicSetType(newType, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.ATTRIBUTE_DECLARATION__TYPE, newType, newType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTarget(final StructuredType target) {
		org.eclipse.fordiac.ide.model.Annotations.setTarget(this,target);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public StructuredType getTarget() {
		return org.eclipse.fordiac.ide.model.Annotations.getTarget(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isValidObject(final ConfigurableObject object) {
		return org.eclipse.fordiac.ide.model.Annotations.isValidObject(this,object);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeLibrary getTypeLibrary() {
		if (null != getTypeEntry()) {
			return getTypeEntry().getTypeLibrary();
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDocumentation(final String value) {
		org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementAnnotations.setDocumentation(this, value);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getDocumentation() {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementAnnotations.getDocumentation(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAttribute(final String attributeName, final DataType type, final String value, final String comment) {
		org.eclipse.fordiac.ide.model.Annotations.setAttribute(this, attributeName, type, value, comment);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAttribute(final AttributeDeclaration attributeDeclaration, final String value, final String comment) {
		org.eclipse.fordiac.ide.model.Annotations.setAttribute(this, attributeDeclaration, value, comment);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Attribute getAttribute(final String attributeName) {
		return org.eclipse.fordiac.ide.model.Annotations.getAttribute(this, attributeName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getAttributeValue(final String attributeName) {
		return org.eclipse.fordiac.ide.model.Annotations.getAttributeValue(this, attributeName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean deleteAttribute(final String attributeName) {
		return org.eclipse.fordiac.ide.model.Annotations.deleteAttribute(this, attributeName);
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
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case LibraryElementPackage.ATTRIBUTE_DECLARATION__NAME:
				return getName();
			case LibraryElementPackage.ATTRIBUTE_DECLARATION__COMMENT:
				return getComment();
			case LibraryElementPackage.ATTRIBUTE_DECLARATION__ATTRIBUTES:
				return getAttributes();
			case LibraryElementPackage.ATTRIBUTE_DECLARATION__VERSION_INFO:
				return getVersionInfo();
			case LibraryElementPackage.ATTRIBUTE_DECLARATION__IDENTIFICATION:
				if (resolve) return getIdentification();
				return basicGetIdentification();
			case LibraryElementPackage.ATTRIBUTE_DECLARATION__COMPILER_INFO:
				if (resolve) return getCompilerInfo();
				return basicGetCompilerInfo();
			case LibraryElementPackage.ATTRIBUTE_DECLARATION__TYPE_ENTRY:
				return getTypeEntry();
			case LibraryElementPackage.ATTRIBUTE_DECLARATION__TYPE:
				if (resolve) return getType();
				return basicGetType();
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
			case LibraryElementPackage.ATTRIBUTE_DECLARATION__NAME:
				setName((String)newValue);
				return;
			case LibraryElementPackage.ATTRIBUTE_DECLARATION__COMMENT:
				setComment((String)newValue);
				return;
			case LibraryElementPackage.ATTRIBUTE_DECLARATION__ATTRIBUTES:
				getAttributes().clear();
				getAttributes().addAll((Collection<? extends Attribute>)newValue);
				return;
			case LibraryElementPackage.ATTRIBUTE_DECLARATION__VERSION_INFO:
				getVersionInfo().clear();
				getVersionInfo().addAll((Collection<? extends VersionInfo>)newValue);
				return;
			case LibraryElementPackage.ATTRIBUTE_DECLARATION__IDENTIFICATION:
				setIdentification((Identification)newValue);
				return;
			case LibraryElementPackage.ATTRIBUTE_DECLARATION__COMPILER_INFO:
				setCompilerInfo((CompilerInfo)newValue);
				return;
			case LibraryElementPackage.ATTRIBUTE_DECLARATION__TYPE_ENTRY:
				setTypeEntry((TypeEntry)newValue);
				return;
			case LibraryElementPackage.ATTRIBUTE_DECLARATION__TYPE:
				setType((AnyDerivedType)newValue);
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
			case LibraryElementPackage.ATTRIBUTE_DECLARATION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case LibraryElementPackage.ATTRIBUTE_DECLARATION__COMMENT:
				setComment(COMMENT_EDEFAULT);
				return;
			case LibraryElementPackage.ATTRIBUTE_DECLARATION__ATTRIBUTES:
				getAttributes().clear();
				return;
			case LibraryElementPackage.ATTRIBUTE_DECLARATION__VERSION_INFO:
				getVersionInfo().clear();
				return;
			case LibraryElementPackage.ATTRIBUTE_DECLARATION__IDENTIFICATION:
				setIdentification((Identification)null);
				return;
			case LibraryElementPackage.ATTRIBUTE_DECLARATION__COMPILER_INFO:
				setCompilerInfo((CompilerInfo)null);
				return;
			case LibraryElementPackage.ATTRIBUTE_DECLARATION__TYPE_ENTRY:
				setTypeEntry(TYPE_ENTRY_EDEFAULT);
				return;
			case LibraryElementPackage.ATTRIBUTE_DECLARATION__TYPE:
				setType((AnyDerivedType)null);
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
			case LibraryElementPackage.ATTRIBUTE_DECLARATION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case LibraryElementPackage.ATTRIBUTE_DECLARATION__COMMENT:
				return COMMENT_EDEFAULT == null ? comment != null : !COMMENT_EDEFAULT.equals(comment);
			case LibraryElementPackage.ATTRIBUTE_DECLARATION__ATTRIBUTES:
				return attributes != null && !attributes.isEmpty();
			case LibraryElementPackage.ATTRIBUTE_DECLARATION__VERSION_INFO:
				return versionInfo != null && !versionInfo.isEmpty();
			case LibraryElementPackage.ATTRIBUTE_DECLARATION__IDENTIFICATION:
				return identification != null;
			case LibraryElementPackage.ATTRIBUTE_DECLARATION__COMPILER_INFO:
				return compilerInfo != null;
			case LibraryElementPackage.ATTRIBUTE_DECLARATION__TYPE_ENTRY:
				return TYPE_ENTRY_EDEFAULT == null ? typeEntry != null : !TYPE_ENTRY_EDEFAULT.equals(typeEntry);
			case LibraryElementPackage.ATTRIBUTE_DECLARATION__TYPE:
				return type != null;
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
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == ConfigurableObject.class) {
			switch (derivedFeatureID) {
				case LibraryElementPackage.ATTRIBUTE_DECLARATION__ATTRIBUTES: return LibraryElementPackage.CONFIGURABLE_OBJECT__ATTRIBUTES;
				default: return -1;
			}
		}
		if (baseClass == LibraryElement.class) {
			switch (derivedFeatureID) {
				case LibraryElementPackage.ATTRIBUTE_DECLARATION__VERSION_INFO: return LibraryElementPackage.LIBRARY_ELEMENT__VERSION_INFO;
				case LibraryElementPackage.ATTRIBUTE_DECLARATION__IDENTIFICATION: return LibraryElementPackage.LIBRARY_ELEMENT__IDENTIFICATION;
				case LibraryElementPackage.ATTRIBUTE_DECLARATION__COMPILER_INFO: return LibraryElementPackage.LIBRARY_ELEMENT__COMPILER_INFO;
				case LibraryElementPackage.ATTRIBUTE_DECLARATION__TYPE_ENTRY: return LibraryElementPackage.LIBRARY_ELEMENT__TYPE_ENTRY;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == ConfigurableObject.class) {
			switch (baseFeatureID) {
				case LibraryElementPackage.CONFIGURABLE_OBJECT__ATTRIBUTES: return LibraryElementPackage.ATTRIBUTE_DECLARATION__ATTRIBUTES;
				default: return -1;
			}
		}
		if (baseClass == LibraryElement.class) {
			switch (baseFeatureID) {
				case LibraryElementPackage.LIBRARY_ELEMENT__VERSION_INFO: return LibraryElementPackage.ATTRIBUTE_DECLARATION__VERSION_INFO;
				case LibraryElementPackage.LIBRARY_ELEMENT__IDENTIFICATION: return LibraryElementPackage.ATTRIBUTE_DECLARATION__IDENTIFICATION;
				case LibraryElementPackage.LIBRARY_ELEMENT__COMPILER_INFO: return LibraryElementPackage.ATTRIBUTE_DECLARATION__COMPILER_INFO;
				case LibraryElementPackage.LIBRARY_ELEMENT__TYPE_ENTRY: return LibraryElementPackage.ATTRIBUTE_DECLARATION__TYPE_ENTRY;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		result.append(", typeEntry: "); //$NON-NLS-1$
		result.append(typeEntry);
		result.append(')');
		return result.toString();
	}

} //AttributeDeclarationImpl
