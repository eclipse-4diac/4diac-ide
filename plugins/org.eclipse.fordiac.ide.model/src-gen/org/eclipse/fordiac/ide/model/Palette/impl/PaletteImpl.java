/********************************************************************************
 * Copyright (c) 2008, 2010 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.Palette.impl;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.fordiac.ide.model.Palette.AdapterTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.DeviceTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PalettePackage;
import org.eclipse.fordiac.ide.model.Palette.ResourceTypeEntry;
import org.eclipse.fordiac.ide.model.Palette.SegmentTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.SubApplicationTypePaletteEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Palette</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.Palette.impl.PaletteImpl#getTypeLibrary <em>Type Library</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.Palette.impl.PaletteImpl#getAdapterTypes <em>Adapter Types</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.Palette.impl.PaletteImpl#getDeviceTypes <em>Device Types</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.Palette.impl.PaletteImpl#getFbTypes <em>Fb Types</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.Palette.impl.PaletteImpl#getResourceTypes <em>Resource Types</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.Palette.impl.PaletteImpl#getSegmentTypes <em>Segment Types</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.Palette.impl.PaletteImpl#getSubAppTypes <em>Sub App Types</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PaletteImpl extends EObjectImpl implements Palette {
	/**
	 * The default value of the '{@link #getTypeLibrary() <em>Type Library</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getTypeLibrary()
	 * @generated
	 * @ordered
	 */
	protected static final TypeLibrary TYPE_LIBRARY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTypeLibrary() <em>Type Library</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getTypeLibrary()
	 * @generated
	 * @ordered
	 */
	protected TypeLibrary typeLibrary = TYPE_LIBRARY_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAdapterTypes() <em>Adapter Types</em>}' map.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getAdapterTypes()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, AdapterTypePaletteEntry> adapterTypes;

	/**
	 * The cached value of the '{@link #getDeviceTypes() <em>Device Types</em>}' map.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getDeviceTypes()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, DeviceTypePaletteEntry> deviceTypes;

	/**
	 * The cached value of the '{@link #getFbTypes() <em>Fb Types</em>}' map. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getFbTypes()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, FBTypePaletteEntry> fbTypes;

	/**
	 * The cached value of the '{@link #getResourceTypes() <em>Resource Types</em>}' map.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getResourceTypes()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, ResourceTypeEntry> resourceTypes;

	/**
	 * The cached value of the '{@link #getSegmentTypes() <em>Segment Types</em>}' map.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getSegmentTypes()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, SegmentTypePaletteEntry> segmentTypes;

	/**
	 * The cached value of the '{@link #getSubAppTypes() <em>Sub App Types</em>}' map.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getSubAppTypes()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, SubApplicationTypePaletteEntry> subAppTypes;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected PaletteImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PalettePackage.Literals.PALETTE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IProject getProject() {
		return getTypeLibrary().getProject();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeLibrary getTypeLibrary() {
		return typeLibrary;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTypeLibrary(TypeLibrary newTypeLibrary) {
		TypeLibrary oldTypeLibrary = typeLibrary;
		typeLibrary = newTypeLibrary;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PalettePackage.PALETTE__TYPE_LIBRARY, oldTypeLibrary, typeLibrary));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EMap<String, FBTypePaletteEntry> getFbTypes() {
		if (fbTypes == null) {
			fbTypes = new EcoreEMap<String,FBTypePaletteEntry>(PalettePackage.Literals.STRING_TO_FB_TYPE_PALETTE_ENTRY_MAP, StringToFBTypePaletteEntryMapImpl.class, this, PalettePackage.PALETTE__FB_TYPES);
		}
		return fbTypes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EMap<String, ResourceTypeEntry> getResourceTypes() {
		if (resourceTypes == null) {
			resourceTypes = new EcoreEMap<String,ResourceTypeEntry>(PalettePackage.Literals.STRING_TO_RESOURCE_TYPE_ENTRY_MAP, StringToResourceTypeEntryMapImpl.class, this, PalettePackage.PALETTE__RESOURCE_TYPES);
		}
		return resourceTypes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EMap<String, DeviceTypePaletteEntry> getDeviceTypes() {
		if (deviceTypes == null) {
			deviceTypes = new EcoreEMap<String,DeviceTypePaletteEntry>(PalettePackage.Literals.STRING_TO_FDEVICE_TYPE_PALETTE_ENTRY_MAP, StringToFDeviceTypePaletteEntryMapImpl.class, this, PalettePackage.PALETTE__DEVICE_TYPES);
		}
		return deviceTypes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EMap<String, SegmentTypePaletteEntry> getSegmentTypes() {
		if (segmentTypes == null) {
			segmentTypes = new EcoreEMap<String,SegmentTypePaletteEntry>(PalettePackage.Literals.STRING_TO_SEGMENT_TYPE_PALETTE_ENTRY_MAP, StringToSegmentTypePaletteEntryMapImpl.class, this, PalettePackage.PALETTE__SEGMENT_TYPES);
		}
		return segmentTypes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EMap<String, SubApplicationTypePaletteEntry> getSubAppTypes() {
		if (subAppTypes == null) {
			subAppTypes = new EcoreEMap<String,SubApplicationTypePaletteEntry>(PalettePackage.Literals.STRING_TO_SUB_APPLICATION_TYPE_PALETTE_ENTRY_MAP, StringToSubApplicationTypePaletteEntryMapImpl.class, this, PalettePackage.PALETTE__SUB_APP_TYPES);
		}
		return subAppTypes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EMap<String, AdapterTypePaletteEntry> getAdapterTypes() {
		if (adapterTypes == null) {
			adapterTypes = new EcoreEMap<String,AdapterTypePaletteEntry>(PalettePackage.Literals.STRING_TO_ADAPTER_TYPE_PALETTE_ENTRY_MAP, StringToAdapterTypePaletteEntryMapImpl.class, this, PalettePackage.PALETTE__ADAPTER_TYPES);
		}
		return adapterTypes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<AdapterTypePaletteEntry> getAdapterTypesSorted() {
		return org.eclipse.fordiac.ide.model.annotations.PaletteAnnotations.getAdapterTypesSorted(this);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AdapterTypePaletteEntry getAdapterTypeEntry(final String typeName) {
		return getAdapterTypes().get(typeName);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DeviceTypePaletteEntry getDeviceTypeEntry(final String typeName) {
		return getDeviceTypes().get(typeName);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FBTypePaletteEntry getFBTypeEntry(final String typeName) {
		return getFbTypes().get(typeName);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceTypeEntry getResourceTypeEntry(final String typeName) {
		return getResourceTypes().get(typeName);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SegmentTypePaletteEntry getSegmentTypeEntry(final String typeName) {
		return getSegmentTypes().get(typeName);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SubApplicationTypePaletteEntry getSubAppTypeEntry(final String typeName) {
		return getSubAppTypes().get(typeName);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void addPaletteEntry(final PaletteEntry entry) {
		org.eclipse.fordiac.ide.model.annotations.PaletteAnnotations.addTypeEntry(this, entry);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void removePaletteEntry(final PaletteEntry entry) {
		org.eclipse.fordiac.ide.model.annotations.PaletteAnnotations.removeTypeEntry(this, entry);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PalettePackage.PALETTE__ADAPTER_TYPES:
				return ((InternalEList<?>)getAdapterTypes()).basicRemove(otherEnd, msgs);
			case PalettePackage.PALETTE__DEVICE_TYPES:
				return ((InternalEList<?>)getDeviceTypes()).basicRemove(otherEnd, msgs);
			case PalettePackage.PALETTE__FB_TYPES:
				return ((InternalEList<?>)getFbTypes()).basicRemove(otherEnd, msgs);
			case PalettePackage.PALETTE__RESOURCE_TYPES:
				return ((InternalEList<?>)getResourceTypes()).basicRemove(otherEnd, msgs);
			case PalettePackage.PALETTE__SEGMENT_TYPES:
				return ((InternalEList<?>)getSegmentTypes()).basicRemove(otherEnd, msgs);
			case PalettePackage.PALETTE__SUB_APP_TYPES:
				return ((InternalEList<?>)getSubAppTypes()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PalettePackage.PALETTE__TYPE_LIBRARY:
				return getTypeLibrary();
			case PalettePackage.PALETTE__ADAPTER_TYPES:
				if (coreType) return getAdapterTypes();
				else return getAdapterTypes().map();
			case PalettePackage.PALETTE__DEVICE_TYPES:
				if (coreType) return getDeviceTypes();
				else return getDeviceTypes().map();
			case PalettePackage.PALETTE__FB_TYPES:
				if (coreType) return getFbTypes();
				else return getFbTypes().map();
			case PalettePackage.PALETTE__RESOURCE_TYPES:
				if (coreType) return getResourceTypes();
				else return getResourceTypes().map();
			case PalettePackage.PALETTE__SEGMENT_TYPES:
				if (coreType) return getSegmentTypes();
				else return getSegmentTypes().map();
			case PalettePackage.PALETTE__SUB_APP_TYPES:
				if (coreType) return getSubAppTypes();
				else return getSubAppTypes().map();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case PalettePackage.PALETTE__TYPE_LIBRARY:
				setTypeLibrary((TypeLibrary)newValue);
				return;
			case PalettePackage.PALETTE__ADAPTER_TYPES:
				((EStructuralFeature.Setting)getAdapterTypes()).set(newValue);
				return;
			case PalettePackage.PALETTE__DEVICE_TYPES:
				((EStructuralFeature.Setting)getDeviceTypes()).set(newValue);
				return;
			case PalettePackage.PALETTE__FB_TYPES:
				((EStructuralFeature.Setting)getFbTypes()).set(newValue);
				return;
			case PalettePackage.PALETTE__RESOURCE_TYPES:
				((EStructuralFeature.Setting)getResourceTypes()).set(newValue);
				return;
			case PalettePackage.PALETTE__SEGMENT_TYPES:
				((EStructuralFeature.Setting)getSegmentTypes()).set(newValue);
				return;
			case PalettePackage.PALETTE__SUB_APP_TYPES:
				((EStructuralFeature.Setting)getSubAppTypes()).set(newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case PalettePackage.PALETTE__TYPE_LIBRARY:
				setTypeLibrary(TYPE_LIBRARY_EDEFAULT);
				return;
			case PalettePackage.PALETTE__ADAPTER_TYPES:
				getAdapterTypes().clear();
				return;
			case PalettePackage.PALETTE__DEVICE_TYPES:
				getDeviceTypes().clear();
				return;
			case PalettePackage.PALETTE__FB_TYPES:
				getFbTypes().clear();
				return;
			case PalettePackage.PALETTE__RESOURCE_TYPES:
				getResourceTypes().clear();
				return;
			case PalettePackage.PALETTE__SEGMENT_TYPES:
				getSegmentTypes().clear();
				return;
			case PalettePackage.PALETTE__SUB_APP_TYPES:
				getSubAppTypes().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case PalettePackage.PALETTE__TYPE_LIBRARY:
				return TYPE_LIBRARY_EDEFAULT == null ? typeLibrary != null : !TYPE_LIBRARY_EDEFAULT.equals(typeLibrary);
			case PalettePackage.PALETTE__ADAPTER_TYPES:
				return adapterTypes != null && !adapterTypes.isEmpty();
			case PalettePackage.PALETTE__DEVICE_TYPES:
				return deviceTypes != null && !deviceTypes.isEmpty();
			case PalettePackage.PALETTE__FB_TYPES:
				return fbTypes != null && !fbTypes.isEmpty();
			case PalettePackage.PALETTE__RESOURCE_TYPES:
				return resourceTypes != null && !resourceTypes.isEmpty();
			case PalettePackage.PALETTE__SEGMENT_TYPES:
				return segmentTypes != null && !segmentTypes.isEmpty();
			case PalettePackage.PALETTE__SUB_APP_TYPES:
				return subAppTypes != null && !subAppTypes.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (typeLibrary: "); //$NON-NLS-1$
		result.append(typeLibrary);
		result.append(')');
		return result.toString();
	}

} // PaletteImpl
