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
package org.eclipse.fordiac.ide.model.Palette.impl;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PalettePackage;
import org.eclipse.fordiac.ide.model.dataimport.CommonElementImporter;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

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
 *   <li>{@link org.eclipse.fordiac.ide.model.Palette.impl.PaletteEntryImpl#getPalette <em>Palette</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.Palette.impl.PaletteEntryImpl#getTypeEditable <em>Type Editable</em>}</li>
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
	 * The cached value of the '{@link #getType() <em>Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected LibraryElement type;

	/**
	 * The cached value of the '{@link #getPalette() <em>Palette</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPalette()
	 * @generated
	 * @ordered
	 */
	protected Palette palette;

	/**
	 * The cached value of the '{@link #getTypeEditable() <em>Type Editable</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeEditable()
	 * @generated
	 * @ordered
	 */
	protected LibraryElement typeEditable;

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
	public void setLabel(final String newLabel) {
		final String oldLabel = label;
		label = newLabel;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, PalettePackage.PALETTE_ENTRY__LABEL, oldLabel, label));
		}
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
	public void setFile(final IFile newFile) {
		final IFile oldFile = file;
		file = newFile;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, PalettePackage.PALETTE_ENTRY__FILE, oldFile, file));
		}
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
	public void setLastModificationTimestamp(final long newLastModificationTimestamp) {
		final long oldLastModificationTimestamp = lastModificationTimestamp;
		lastModificationTimestamp = newLastModificationTimestamp;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, PalettePackage.PALETTE_ENTRY__LAST_MODIFICATION_TIMESTAMP, oldLastModificationTimestamp, lastModificationTimestamp));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	@Override
	public LibraryElement getType() {
		if (type != null && type.eIsProxy()) {
			final InternalEObject oldType = (InternalEObject)type;
			type = (LibraryElement)eResolveProxy(oldType);
			if (type != oldType) {
				final InternalEObject newType = (InternalEObject)type;
				NotificationChain msgs = oldType.eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PalettePackage.PALETTE_ENTRY__TYPE, null, null);
				if (newType.eInternalContainer() == null) {
					msgs = newType.eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PalettePackage.PALETTE_ENTRY__TYPE, null, msgs);
				}
				if (msgs != null) {
					msgs.dispatch();
				}
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, PalettePackage.PALETTE_ENTRY__TYPE, oldType, type));
				}
			}
		}else if (((null == type) && (null != getFile()))
				|| (getFile() != null && getFile().getModificationStamp() != IResource.NULL_STAMP
				&& getFile().getModificationStamp() != lastModificationTimestamp)) {
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
	public LibraryElement basicGetType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetType(final LibraryElement newType, NotificationChain msgs) {
		final LibraryElement oldType = type;
		type = newType;
		if (eNotificationRequired()) {
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PalettePackage.PALETTE_ENTRY__TYPE, oldType, newType);
			if (msgs == null) {
				msgs = notification;
			} else {
				msgs.add(notification);
			}
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setType(final LibraryElement newType) {
		if (newType != type) {
			NotificationChain msgs = null;
			if (type != null) {
				msgs = ((InternalEObject)type).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PalettePackage.PALETTE_ENTRY__TYPE, null, msgs);
			}
			if (newType != null) {
				msgs = ((InternalEObject)newType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PalettePackage.PALETTE_ENTRY__TYPE, null, msgs);
			}
			msgs = basicSetType(newType, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		}
		else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, PalettePackage.PALETTE_ENTRY__TYPE, newType, newType));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Palette getPalette() {
		return palette;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPalette(final Palette newPalette) {
		final Palette oldPalette = palette;
		palette = newPalette;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, PalettePackage.PALETTE_ENTRY__PALETTE, oldPalette, palette));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	@Override
	public LibraryElement getTypeEditable() {
		if (typeEditable != null && typeEditable.eIsProxy()) {
			final InternalEObject oldTypeEditable = (InternalEObject)typeEditable;
			typeEditable = (LibraryElement)eResolveProxy(oldTypeEditable);
			if (typeEditable != oldTypeEditable) {
				final InternalEObject newTypeEditable = (InternalEObject)typeEditable;
				NotificationChain msgs = oldTypeEditable.eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PalettePackage.PALETTE_ENTRY__TYPE_EDITABLE, null, null);
				if (newTypeEditable.eInternalContainer() == null) {
					msgs = newTypeEditable.eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PalettePackage.PALETTE_ENTRY__TYPE_EDITABLE, null, msgs);
				}
				if (msgs != null) {
					msgs.dispatch();
				}
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, PalettePackage.PALETTE_ENTRY__TYPE_EDITABLE, oldTypeEditable, typeEditable));
				}
			}
		} else if (typeEditable == null) {
			// if the editable type is null load it from the file and set a copy
			setTypeEditable(EcoreUtil.copy(getType()));
		}
		return typeEditable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LibraryElement basicGetTypeEditable() {
		return typeEditable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTypeEditable(final LibraryElement newTypeEditable, NotificationChain msgs) {
		final LibraryElement oldTypeEditable = typeEditable;
		typeEditable = newTypeEditable;
		if (eNotificationRequired()) {
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PalettePackage.PALETTE_ENTRY__TYPE_EDITABLE, oldTypeEditable, newTypeEditable);
			if (msgs == null) {
				msgs = notification;
			} else {
				msgs.add(notification);
			}
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTypeEditable(final LibraryElement newTypeEditable) {
		if (newTypeEditable != typeEditable) {
			NotificationChain msgs = null;
			if (typeEditable != null) {
				msgs = ((InternalEObject)typeEditable).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PalettePackage.PALETTE_ENTRY__TYPE_EDITABLE, null, msgs);
			}
			if (newTypeEditable != null) {
				msgs = ((InternalEObject)newTypeEditable).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PalettePackage.PALETTE_ENTRY__TYPE_EDITABLE, null, msgs);
			}
			msgs = basicSetTypeEditable(newTypeEditable, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		}
		else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, PalettePackage.PALETTE_ENTRY__TYPE_EDITABLE, newTypeEditable, newTypeEditable));
		}
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
	public LibraryElement loadType() {
		return org.eclipse.fordiac.ide.model.annotations.PaletteAnnotations.loadType(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CommonElementImporter getImporter() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeLibrary getTypeLibrary() {
		return getPalette().getTypeLibrary();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(final InternalEObject otherEnd, final int featureID, final NotificationChain msgs) {
		switch (featureID) {
		case PalettePackage.PALETTE_ENTRY__TYPE:
			return basicSetType(null, msgs);
		case PalettePackage.PALETTE_ENTRY__TYPE_EDITABLE:
			return basicSetTypeEditable(null, msgs);
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
	public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
		switch (featureID) {
		case PalettePackage.PALETTE_ENTRY__LABEL:
			return getLabel();
		case PalettePackage.PALETTE_ENTRY__FILE:
			return getFile();
		case PalettePackage.PALETTE_ENTRY__LAST_MODIFICATION_TIMESTAMP:
			return getLastModificationTimestamp();
		case PalettePackage.PALETTE_ENTRY__TYPE:
			if (resolve) {
				return getType();
			}
			return basicGetType();
		case PalettePackage.PALETTE_ENTRY__PALETTE:
			return getPalette();
		case PalettePackage.PALETTE_ENTRY__TYPE_EDITABLE:
			if (resolve) {
				return getTypeEditable();
			}
			return basicGetTypeEditable();
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
	public void eSet(final int featureID, final Object newValue) {
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
		case PalettePackage.PALETTE_ENTRY__PALETTE:
			setPalette((Palette)newValue);
			return;
		case PalettePackage.PALETTE_ENTRY__TYPE_EDITABLE:
			setTypeEditable((LibraryElement)newValue);
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
	public void eUnset(final int featureID) {
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
		case PalettePackage.PALETTE_ENTRY__PALETTE:
			setPalette((Palette)null);
			return;
		case PalettePackage.PALETTE_ENTRY__TYPE_EDITABLE:
			setTypeEditable((LibraryElement)null);
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
	public boolean eIsSet(final int featureID) {
		switch (featureID) {
		case PalettePackage.PALETTE_ENTRY__LABEL:
			return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
		case PalettePackage.PALETTE_ENTRY__FILE:
			return FILE_EDEFAULT == null ? file != null : !FILE_EDEFAULT.equals(file);
		case PalettePackage.PALETTE_ENTRY__LAST_MODIFICATION_TIMESTAMP:
			return lastModificationTimestamp != LAST_MODIFICATION_TIMESTAMP_EDEFAULT;
		case PalettePackage.PALETTE_ENTRY__TYPE:
			return type != null;
		case PalettePackage.PALETTE_ENTRY__PALETTE:
			return palette != null;
		case PalettePackage.PALETTE_ENTRY__TYPE_EDITABLE:
			return typeEditable != null;
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
		if (eIsProxy()) {
			return super.toString();
		}

		final StringBuilder result = new StringBuilder(super.toString());
		result.append(" (label: "); //$NON-NLS-1$
		result.append(label);
		result.append(", file: "); //$NON-NLS-1$
		result.append(file);
		result.append(", lastModificationTimestamp: "); //$NON-NLS-1$
		result.append(lastModificationTimestamp);
		result.append(')');
		return result.toString();
	}

} //PaletteEntryImpl
