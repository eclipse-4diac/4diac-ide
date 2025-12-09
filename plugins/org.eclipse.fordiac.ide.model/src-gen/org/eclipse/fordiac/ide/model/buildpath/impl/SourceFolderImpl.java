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
package org.eclipse.fordiac.ide.model.buildpath.impl;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.fordiac.ide.model.buildpath.Attribute;
import org.eclipse.fordiac.ide.model.buildpath.Buildpath;
import org.eclipse.fordiac.ide.model.buildpath.BuildpathPackage;
import org.eclipse.fordiac.ide.model.buildpath.Pattern;
import org.eclipse.fordiac.ide.model.buildpath.SourceFolder;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Source Folder</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.buildpath.impl.SourceFolderImpl#getIncludes <em>Includes</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.buildpath.impl.SourceFolderImpl#getExcludes <em>Excludes</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.buildpath.impl.SourceFolderImpl#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.buildpath.impl.SourceFolderImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.buildpath.impl.SourceFolderImpl#getBuildpath <em>Buildpath</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SourceFolderImpl extends EObjectImpl implements SourceFolder {
	/**
	 * The cached value of the '{@link #getIncludes() <em>Includes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIncludes()
	 * @generated
	 * @ordered
	 */
	protected EList<Pattern> includes;

	/**
	 * The cached value of the '{@link #getExcludes() <em>Excludes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExcludes()
	 * @generated
	 * @ordered
	 */
	protected EList<Pattern> excludes;

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
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SourceFolderImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BuildpathPackage.Literals.SOURCE_FOLDER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Pattern> getIncludes() {
		if (includes == null) {
			includes = new EObjectContainmentEList<Pattern>(Pattern.class, this, BuildpathPackage.SOURCE_FOLDER__INCLUDES);
		}
		return includes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Pattern> getExcludes() {
		if (excludes == null) {
			excludes = new EObjectContainmentEList<Pattern>(Pattern.class, this, BuildpathPackage.SOURCE_FOLDER__EXCLUDES);
		}
		return excludes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Attribute> getAttributes() {
		if (attributes == null) {
			attributes = new EObjectContainmentEList<Attribute>(Attribute.class, this, BuildpathPackage.SOURCE_FOLDER__ATTRIBUTES);
		}
		return attributes;
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
			eNotify(new ENotificationImpl(this, Notification.SET, BuildpathPackage.SOURCE_FOLDER__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Buildpath getBuildpath() {
		if (eContainerFeatureID() != BuildpathPackage.SOURCE_FOLDER__BUILDPATH) return null;
		return (Buildpath)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Buildpath basicGetBuildpath() {
		if (eContainerFeatureID() != BuildpathPackage.SOURCE_FOLDER__BUILDPATH) return null;
		return (Buildpath)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBuildpath(Buildpath newBuildpath, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newBuildpath, BuildpathPackage.SOURCE_FOLDER__BUILDPATH, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBuildpath(Buildpath newBuildpath) {
		if (newBuildpath != eInternalContainer() || (eContainerFeatureID() != BuildpathPackage.SOURCE_FOLDER__BUILDPATH && newBuildpath != null)) {
			if (EcoreUtil.isAncestor(this, newBuildpath))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newBuildpath != null)
				msgs = ((InternalEObject)newBuildpath).eInverseAdd(this, BuildpathPackage.BUILDPATH__SOURCE_FOLDERS, Buildpath.class, msgs);
			msgs = basicSetBuildpath(newBuildpath, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BuildpathPackage.SOURCE_FOLDER__BUILDPATH, newBuildpath, newBuildpath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BuildpathPackage.SOURCE_FOLDER__BUILDPATH:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetBuildpath((Buildpath)otherEnd, msgs);
			default:
				return super.eInverseAdd(otherEnd, featureID, msgs);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BuildpathPackage.SOURCE_FOLDER__INCLUDES:
				return ((InternalEList<?>)getIncludes()).basicRemove(otherEnd, msgs);
			case BuildpathPackage.SOURCE_FOLDER__EXCLUDES:
				return ((InternalEList<?>)getExcludes()).basicRemove(otherEnd, msgs);
			case BuildpathPackage.SOURCE_FOLDER__ATTRIBUTES:
				return ((InternalEList<?>)getAttributes()).basicRemove(otherEnd, msgs);
			case BuildpathPackage.SOURCE_FOLDER__BUILDPATH:
				return basicSetBuildpath(null, msgs);
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
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case BuildpathPackage.SOURCE_FOLDER__BUILDPATH:
				return eInternalContainer().eInverseRemove(this, BuildpathPackage.BUILDPATH__SOURCE_FOLDERS, Buildpath.class, msgs);
			default:
				return super.eBasicRemoveFromContainerFeature(msgs);
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
			case BuildpathPackage.SOURCE_FOLDER__INCLUDES:
				return getIncludes();
			case BuildpathPackage.SOURCE_FOLDER__EXCLUDES:
				return getExcludes();
			case BuildpathPackage.SOURCE_FOLDER__ATTRIBUTES:
				return getAttributes();
			case BuildpathPackage.SOURCE_FOLDER__NAME:
				return getName();
			case BuildpathPackage.SOURCE_FOLDER__BUILDPATH:
				if (resolve) return getBuildpath();
				return basicGetBuildpath();
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
			case BuildpathPackage.SOURCE_FOLDER__INCLUDES:
				getIncludes().clear();
				getIncludes().addAll((Collection<? extends Pattern>)newValue);
				return;
			case BuildpathPackage.SOURCE_FOLDER__EXCLUDES:
				getExcludes().clear();
				getExcludes().addAll((Collection<? extends Pattern>)newValue);
				return;
			case BuildpathPackage.SOURCE_FOLDER__ATTRIBUTES:
				getAttributes().clear();
				getAttributes().addAll((Collection<? extends Attribute>)newValue);
				return;
			case BuildpathPackage.SOURCE_FOLDER__NAME:
				setName((String)newValue);
				return;
			case BuildpathPackage.SOURCE_FOLDER__BUILDPATH:
				setBuildpath((Buildpath)newValue);
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
			case BuildpathPackage.SOURCE_FOLDER__INCLUDES:
				getIncludes().clear();
				return;
			case BuildpathPackage.SOURCE_FOLDER__EXCLUDES:
				getExcludes().clear();
				return;
			case BuildpathPackage.SOURCE_FOLDER__ATTRIBUTES:
				getAttributes().clear();
				return;
			case BuildpathPackage.SOURCE_FOLDER__NAME:
				setName(NAME_EDEFAULT);
				return;
			case BuildpathPackage.SOURCE_FOLDER__BUILDPATH:
				setBuildpath((Buildpath)null);
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
			case BuildpathPackage.SOURCE_FOLDER__INCLUDES:
				return includes != null && !includes.isEmpty();
			case BuildpathPackage.SOURCE_FOLDER__EXCLUDES:
				return excludes != null && !excludes.isEmpty();
			case BuildpathPackage.SOURCE_FOLDER__ATTRIBUTES:
				return attributes != null && !attributes.isEmpty();
			case BuildpathPackage.SOURCE_FOLDER__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case BuildpathPackage.SOURCE_FOLDER__BUILDPATH:
				return basicGetBuildpath() != null;
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
		result.append(')');
		return result.toString();
	}

} //SourceFolderImpl
