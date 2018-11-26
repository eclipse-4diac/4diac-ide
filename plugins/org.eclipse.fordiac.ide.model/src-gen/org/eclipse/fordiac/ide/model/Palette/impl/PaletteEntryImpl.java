/********************************************************************************
 * Copyright (c) 2008, 2010 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.Palette.impl;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PaletteGroup;
import org.eclipse.fordiac.ide.model.Palette.PalettePackage;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Entry</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.Palette.impl.PaletteEntryImpl#getLabel <em>Label</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.Palette.impl.PaletteEntryImpl#getFile <em>File</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.Palette.impl.PaletteEntryImpl#getLastModificationTimestamp <em>Last Modification Timestamp</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.Palette.impl.PaletteEntryImpl#getType <em>Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class PaletteEntryImpl extends EObjectImpl implements PaletteEntry {
	/**
	 * The default value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected static final String LABEL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected String label = LABEL_EDEFAULT;

	/**
	 * The default value of the '{@link #getFile() <em>File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFile()
	 * @generated
	 * @ordered
	 */
	protected static final IFile FILE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFile() <em>File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFile()
	 * @generated
	 * @ordered
	 */
	protected IFile file = FILE_EDEFAULT;

	/**
	 * The default value of the '{@link #getLastModificationTimestamp() <em>Last Modification Timestamp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastModificationTimestamp()
	 * @generated
	 * @ordered
	 */
	protected static final long LAST_MODIFICATION_TIMESTAMP_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getLastModificationTimestamp() <em>Last Modification Timestamp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastModificationTimestamp()
	 * @generated
	 * @ordered
	 */
	protected long lastModificationTimestamp = LAST_MODIFICATION_TIMESTAMP_EDEFAULT;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected LibraryElement type;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PaletteEntryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PalettePackage.Literals.PALETTE_ENTRY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getLabel() {
		return label;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLabel(String newLabel) {
		String oldLabel = label;
		label = newLabel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PalettePackage.PALETTE_ENTRY__LABEL, oldLabel, label));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IFile getFile() {
		return file;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFile(IFile newFile) {
		IFile oldFile = file;
		file = newFile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PalettePackage.PALETTE_ENTRY__FILE, oldFile, file));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public long getLastModificationTimestamp() {
		return lastModificationTimestamp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLastModificationTimestamp(long newLastModificationTimestamp) {
		long oldLastModificationTimestamp = lastModificationTimestamp;
		lastModificationTimestamp = newLastModificationTimestamp;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PalettePackage.PALETTE_ENTRY__LAST_MODIFICATION_TIMESTAMP, oldLastModificationTimestamp, lastModificationTimestamp));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	@Override
	public LibraryElement getType() {
		if (type != null && type.eIsProxy()) {
			InternalEObject oldType = (InternalEObject)type;
			type = (LibraryElement)eResolveProxy(oldType);
			if (type != oldType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, PalettePackage.PALETTE_ENTRY__TYPE, oldType, type));
			}
		}else if(((null == type) && (null != getFile()))  || (getFile() != null && getFile().getModificationStamp() != IResource.NULL_STAMP && getFile().getModificationStamp() != lastModificationTimestamp )) {
			lastModificationTimestamp = getFile().getModificationStamp();
			setType(loadType());
		}

		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetType(LibraryElement newType, NotificationChain msgs) {
		LibraryElement oldType = type;
		type = newType;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PalettePackage.PALETTE_ENTRY__TYPE, oldType, newType);
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
	public void setType(LibraryElement newType) {
		if (newType != type) {
			NotificationChain msgs = null;
			if (type != null)
				msgs = ((InternalEObject)type).eInverseRemove(this, LibraryElementPackage.LIBRARY_ELEMENT__PALETTE_ENTRY, LibraryElement.class, msgs);
			if (newType != null)
				msgs = ((InternalEObject)newType).eInverseAdd(this, LibraryElementPackage.LIBRARY_ELEMENT__PALETTE_ENTRY, LibraryElement.class, msgs);
			msgs = basicSetType(newType, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PalettePackage.PALETTE_ENTRY__TYPE, newType, newType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PaletteGroup getGroup() {
		if(null != eContainer()){
		  return (PaletteGroup)eContainer();
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getProjectRelativeTypePath() {
		return getFile().getProjectRelativePath().toOSString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PalettePackage.PALETTE_ENTRY__TYPE:
				if (type != null)
					msgs = ((InternalEObject)type).eInverseRemove(this, LibraryElementPackage.LIBRARY_ELEMENT__PALETTE_ENTRY, LibraryElement.class, msgs);
				return basicSetType((LibraryElement)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PalettePackage.PALETTE_ENTRY__TYPE:
				return basicSetType(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PalettePackage.PALETTE_ENTRY__LABEL:
				return getLabel();
			case PalettePackage.PALETTE_ENTRY__FILE:
				return getFile();
			case PalettePackage.PALETTE_ENTRY__LAST_MODIFICATION_TIMESTAMP:
				return getLastModificationTimestamp();
			case PalettePackage.PALETTE_ENTRY__TYPE:
				return getType();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case PalettePackage.PALETTE_ENTRY__LABEL:
				setLabel((String)newValue);
				return;
			case PalettePackage.PALETTE_ENTRY__FILE:
				setFile((IFile)newValue);
				return;
			case PalettePackage.PALETTE_ENTRY__LAST_MODIFICATION_TIMESTAMP:
				setLastModificationTimestamp((Long)newValue);
				return;
			case PalettePackage.PALETTE_ENTRY__TYPE:
				setType((LibraryElement)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case PalettePackage.PALETTE_ENTRY__LABEL:
				setLabel(LABEL_EDEFAULT);
				return;
			case PalettePackage.PALETTE_ENTRY__FILE:
				setFile(FILE_EDEFAULT);
				return;
			case PalettePackage.PALETTE_ENTRY__LAST_MODIFICATION_TIMESTAMP:
				setLastModificationTimestamp(LAST_MODIFICATION_TIMESTAMP_EDEFAULT);
				return;
			case PalettePackage.PALETTE_ENTRY__TYPE:
				setType((LibraryElement)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case PalettePackage.PALETTE_ENTRY__LABEL:
				return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
			case PalettePackage.PALETTE_ENTRY__FILE:
				return FILE_EDEFAULT == null ? file != null : !FILE_EDEFAULT.equals(file);
			case PalettePackage.PALETTE_ENTRY__LAST_MODIFICATION_TIMESTAMP:
				return lastModificationTimestamp != LAST_MODIFICATION_TIMESTAMP_EDEFAULT;
			case PalettePackage.PALETTE_ENTRY__TYPE:
				return type != null;
		}
		return super.eIsSet(featureID);
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
		result.append(" (label: "); //$NON-NLS-1$
		result.append(label);
		result.append(", file: "); //$NON-NLS-1$
		result.append(file);
		result.append(", lastModificationTimestamp: "); //$NON-NLS-1$
		result.append(lastModificationTimestamp);
		result.append(')');
		return result.toString();
	}
		
		
	protected abstract LibraryElement loadType();
} //PaletteEntryImpl
