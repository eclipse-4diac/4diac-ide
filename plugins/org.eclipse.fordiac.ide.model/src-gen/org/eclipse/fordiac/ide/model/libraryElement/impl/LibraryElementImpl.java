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
package org.eclipse.fordiac.ide.model.libraryElement.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PalettePackage;
import org.eclipse.fordiac.ide.model.libraryElement.Identification;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.VersionInfo;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

/** <!-- begin-user-doc --> An implementation of the model object '<em><b>Library Element</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementImpl#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementImpl#getComment <em>Comment</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementImpl#getVersionInfo <em>Version
 * Info</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementImpl#getIdentification
 * <em>Identification</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementImpl#getPaletteEntry <em>Palette
 * Entry</em>}</li>
 * </ul>
 *
 * @generated */
public class LibraryElementImpl extends EObjectImpl implements LibraryElement {
	/** The default value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @see #getName()
	 * @generated
	 * @ordered */
	protected static final String NAME_EDEFAULT = ""; //$NON-NLS-1$

	/** The cached value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @see #getName()
	 * @generated
	 * @ordered */
	protected String name = NAME_EDEFAULT;

	/** The default value of the '{@link #getComment() <em>Comment</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getComment()
	 * @generated
	 * @ordered */
	protected static final String COMMENT_EDEFAULT = ""; //$NON-NLS-1$

	/** The cached value of the '{@link #getComment() <em>Comment</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getComment()
	 * @generated
	 * @ordered */
	protected String comment = COMMENT_EDEFAULT;

	/** The cached value of the '{@link #getVersionInfo() <em>Version Info</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getVersionInfo()
	 * @generated
	 * @ordered */
	protected EList<VersionInfo> versionInfo;

	/** The cached value of the '{@link #getIdentification() <em>Identification</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getIdentification()
	 * @generated
	 * @ordered */
	protected Identification identification;

	/** The cached value of the '{@link #getPaletteEntry() <em>Palette Entry</em>}' reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getPaletteEntry()
	 * @generated
	 * @ordered */
	protected PaletteEntry paletteEntry;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	protected LibraryElementImpl() {
		super();
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.LIBRARY_ELEMENT;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public String getName() {
		return name;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.LIBRARY_ELEMENT__NAME, oldName,
					name));
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public String getComment() {
		return comment;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public void setComment(String newComment) {
		String oldComment = comment;
		comment = newComment;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.LIBRARY_ELEMENT__COMMENT,
					oldComment, comment));
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EList<VersionInfo> getVersionInfo() {
		if (versionInfo == null) {
			versionInfo = new EObjectContainmentEList.Resolving<>(VersionInfo.class, this,
					LibraryElementPackage.LIBRARY_ELEMENT__VERSION_INFO);
		}
		return versionInfo;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public Identification getIdentification() {
		if (identification != null && identification.eIsProxy()) {
			InternalEObject oldIdentification = (InternalEObject) identification;
			identification = (Identification) eResolveProxy(oldIdentification);
			if (identification != oldIdentification) {
				InternalEObject newIdentification = (InternalEObject) identification;
				NotificationChain msgs = oldIdentification.eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - LibraryElementPackage.LIBRARY_ELEMENT__IDENTIFICATION, null, null);
				if (newIdentification.eInternalContainer() == null) {
					msgs = newIdentification.eInverseAdd(this,
							EOPPOSITE_FEATURE_BASE - LibraryElementPackage.LIBRARY_ELEMENT__IDENTIFICATION, null, msgs);
				}
				if (msgs != null)
					msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							LibraryElementPackage.LIBRARY_ELEMENT__IDENTIFICATION, oldIdentification, identification));
			}
		}
		return identification;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	public Identification basicGetIdentification() {
		return identification;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	public NotificationChain basicSetIdentification(Identification newIdentification, NotificationChain msgs) {
		Identification oldIdentification = identification;
		identification = newIdentification;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					LibraryElementPackage.LIBRARY_ELEMENT__IDENTIFICATION, oldIdentification, newIdentification);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public void setIdentification(Identification newIdentification) {
		if (newIdentification != identification) {
			NotificationChain msgs = null;
			if (identification != null)
				msgs = ((InternalEObject) identification).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - LibraryElementPackage.LIBRARY_ELEMENT__IDENTIFICATION, null, msgs);
			if (newIdentification != null)
				msgs = ((InternalEObject) newIdentification).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - LibraryElementPackage.LIBRARY_ELEMENT__IDENTIFICATION, null, msgs);
			msgs = basicSetIdentification(newIdentification, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.LIBRARY_ELEMENT__IDENTIFICATION,
					newIdentification, newIdentification));
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public PaletteEntry getPaletteEntry() {
		return paletteEntry;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	public NotificationChain basicSetPaletteEntry(PaletteEntry newPaletteEntry, NotificationChain msgs) {
		PaletteEntry oldPaletteEntry = paletteEntry;
		paletteEntry = newPaletteEntry;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					LibraryElementPackage.LIBRARY_ELEMENT__PALETTE_ENTRY, oldPaletteEntry, newPaletteEntry);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public void setPaletteEntry(PaletteEntry newPaletteEntry) {
		if (newPaletteEntry != paletteEntry) {
			NotificationChain msgs = null;
			if (paletteEntry != null)
				msgs = ((InternalEObject) paletteEntry).eInverseRemove(this, PalettePackage.PALETTE_ENTRY__TYPE,
						PaletteEntry.class, msgs);
			if (newPaletteEntry != null)
				msgs = ((InternalEObject) newPaletteEntry).eInverseAdd(this, PalettePackage.PALETTE_ENTRY__TYPE,
						PaletteEntry.class, msgs);
			msgs = basicSetPaletteEntry(newPaletteEntry, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.LIBRARY_ELEMENT__PALETTE_ENTRY,
					newPaletteEntry, newPaletteEntry));
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public TypeLibrary getTypeLibrary() {
		if (null != getPaletteEntry()) {
			return getPaletteEntry().getTypeLibrary();
		}
		return null;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case LibraryElementPackage.LIBRARY_ELEMENT__PALETTE_ENTRY:
			if (paletteEntry != null)
				msgs = ((InternalEObject) paletteEntry).eInverseRemove(this, PalettePackage.PALETTE_ENTRY__TYPE,
						PaletteEntry.class, msgs);
			return basicSetPaletteEntry((PaletteEntry) otherEnd, msgs);
		default:
			return super.eInverseAdd(otherEnd, featureID, msgs);
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case LibraryElementPackage.LIBRARY_ELEMENT__VERSION_INFO:
			return ((InternalEList<?>) getVersionInfo()).basicRemove(otherEnd, msgs);
		case LibraryElementPackage.LIBRARY_ELEMENT__IDENTIFICATION:
			return basicSetIdentification(null, msgs);
		case LibraryElementPackage.LIBRARY_ELEMENT__PALETTE_ENTRY:
			return basicSetPaletteEntry(null, msgs);
		default:
			return super.eInverseRemove(otherEnd, featureID, msgs);
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case LibraryElementPackage.LIBRARY_ELEMENT__NAME:
			return getName();
		case LibraryElementPackage.LIBRARY_ELEMENT__COMMENT:
			return getComment();
		case LibraryElementPackage.LIBRARY_ELEMENT__VERSION_INFO:
			return getVersionInfo();
		case LibraryElementPackage.LIBRARY_ELEMENT__IDENTIFICATION:
			if (resolve)
				return getIdentification();
			return basicGetIdentification();
		case LibraryElementPackage.LIBRARY_ELEMENT__PALETTE_ENTRY:
			return getPaletteEntry();
		default:
			return super.eGet(featureID, resolve, coreType);
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case LibraryElementPackage.LIBRARY_ELEMENT__NAME:
			setName((String) newValue);
			return;
		case LibraryElementPackage.LIBRARY_ELEMENT__COMMENT:
			setComment((String) newValue);
			return;
		case LibraryElementPackage.LIBRARY_ELEMENT__VERSION_INFO:
			getVersionInfo().clear();
			getVersionInfo().addAll((Collection<? extends VersionInfo>) newValue);
			return;
		case LibraryElementPackage.LIBRARY_ELEMENT__IDENTIFICATION:
			setIdentification((Identification) newValue);
			return;
		case LibraryElementPackage.LIBRARY_ELEMENT__PALETTE_ENTRY:
			setPaletteEntry((PaletteEntry) newValue);
			return;
		default:
			super.eSet(featureID, newValue);
			return;
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case LibraryElementPackage.LIBRARY_ELEMENT__NAME:
			setName(NAME_EDEFAULT);
			return;
		case LibraryElementPackage.LIBRARY_ELEMENT__COMMENT:
			setComment(COMMENT_EDEFAULT);
			return;
		case LibraryElementPackage.LIBRARY_ELEMENT__VERSION_INFO:
			getVersionInfo().clear();
			return;
		case LibraryElementPackage.LIBRARY_ELEMENT__IDENTIFICATION:
			setIdentification((Identification) null);
			return;
		case LibraryElementPackage.LIBRARY_ELEMENT__PALETTE_ENTRY:
			setPaletteEntry((PaletteEntry) null);
			return;
		default:
			super.eUnset(featureID);
			return;
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case LibraryElementPackage.LIBRARY_ELEMENT__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		case LibraryElementPackage.LIBRARY_ELEMENT__COMMENT:
			return COMMENT_EDEFAULT == null ? comment != null : !COMMENT_EDEFAULT.equals(comment);
		case LibraryElementPackage.LIBRARY_ELEMENT__VERSION_INFO:
			return versionInfo != null && !versionInfo.isEmpty();
		case LibraryElementPackage.LIBRARY_ELEMENT__IDENTIFICATION:
			return identification != null;
		case LibraryElementPackage.LIBRARY_ELEMENT__PALETTE_ENTRY:
			return paletteEntry != null;
		default:
			return super.eIsSet(featureID);
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (name: "); //$NON-NLS-1$
		result.append(name);
		result.append(", comment: "); //$NON-NLS-1$
		result.append(comment);
		result.append(')');
		return result.toString();
	}

} // LibraryElementImpl
