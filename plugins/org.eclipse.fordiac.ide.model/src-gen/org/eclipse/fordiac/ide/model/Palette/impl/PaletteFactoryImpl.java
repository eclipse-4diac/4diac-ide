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

import java.util.Map;
import javax.xml.stream.XMLStreamException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.fordiac.ide.model.Palette.*;
import org.eclipse.fordiac.ide.model.Palette.AdapterTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.DeviceTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteFactory;
import org.eclipse.fordiac.ide.model.Palette.PalettePackage;
import org.eclipse.fordiac.ide.model.Palette.ResourceTypeEntry;
import org.eclipse.fordiac.ide.model.Palette.SegmentTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.SubApplicationTypePaletteEntry;
import org.eclipse.fordiac.ide.model.dataimport.TypeImporter;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class PaletteFactoryImpl extends EFactoryImpl implements PaletteFactory {
	/**
	 * Creates the default factory implementation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public static PaletteFactory init() {
		try {
			PaletteFactory thePaletteFactory = (PaletteFactory) EPackage.Registry.INSTANCE
					.getEFactory(PalettePackage.eNS_URI);
			if (thePaletteFactory != null) {
				return thePaletteFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new PaletteFactoryImpl();
	}

	/**
	 * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	public PaletteFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case PalettePackage.PALETTE:
			return createPalette();
		case PalettePackage.STRING_TO_ADAPTER_TYPE_PALETTE_ENTRY_MAP:
			return (EObject) createStringToAdapterTypePaletteEntryMap();
		case PalettePackage.STRING_TO_FDEVICE_TYPE_PALETTE_ENTRY_MAP:
			return (EObject) createStringToFDeviceTypePaletteEntryMap();
		case PalettePackage.STRING_TO_FB_TYPE_PALETTE_ENTRY_MAP:
			return (EObject) createStringToFBTypePaletteEntryMap();
		case PalettePackage.STRING_TO_RESOURCE_TYPE_ENTRY_MAP:
			return (EObject) createStringToResourceTypeEntryMap();
		case PalettePackage.STRING_TO_SEGMENT_TYPE_PALETTE_ENTRY_MAP:
			return (EObject) createStringToSegmentTypePaletteEntryMap();
		case PalettePackage.STRING_TO_SUB_APPLICATION_TYPE_PALETTE_ENTRY_MAP:
			return (EObject) createStringToSubApplicationTypePaletteEntryMap();
		case PalettePackage.ADAPTER_TYPE_PALETTE_ENTRY:
			return createAdapterTypePaletteEntry();
		case PalettePackage.DATA_TYPE_PALETTE_ENTRY:
			return createDataTypePaletteEntry();
		case PalettePackage.DEVICE_TYPE_PALETTE_ENTRY:
			return createDeviceTypePaletteEntry();
		case PalettePackage.FB_TYPE_PALETTE_ENTRY:
			return createFBTypePaletteEntry();
		case PalettePackage.RESOURCE_TYPE_ENTRY:
			return createResourceTypeEntry();
		case PalettePackage.SEGMENT_TYPE_PALETTE_ENTRY:
			return createSegmentTypePaletteEntry();
		case PalettePackage.SUB_APPLICATION_TYPE_PALETTE_ENTRY:
			return createSubApplicationTypePaletteEntry();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
		case PalettePackage.IFILE:
			return createIFileFromString(eDataType, initialValue);
		case PalettePackage.IPROJECT:
			return createIProjectFromString(eDataType, initialValue);
		case PalettePackage.TYPE_IMPORTER:
			return createTypeImporterFromString(eDataType, initialValue);
		case PalettePackage.XML_STREAM_EXCEPTION:
			return createXMLStreamExceptionFromString(eDataType, initialValue);
		case PalettePackage.CORE_EXCEPTION:
			return createCoreExceptionFromString(eDataType, initialValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
		case PalettePackage.IFILE:
			return convertIFileToString(eDataType, instanceValue);
		case PalettePackage.IPROJECT:
			return convertIProjectToString(eDataType, instanceValue);
		case PalettePackage.TYPE_IMPORTER:
			return convertTypeImporterToString(eDataType, instanceValue);
		case PalettePackage.XML_STREAM_EXCEPTION:
			return convertXMLStreamExceptionToString(eDataType, instanceValue);
		case PalettePackage.CORE_EXCEPTION:
			return convertCoreExceptionToString(eDataType, instanceValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Palette createPalette() {
		PaletteImpl palette = new PaletteImpl();
		return palette;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Map.Entry<String, FBTypePaletteEntry> createStringToFBTypePaletteEntryMap() {
		StringToFBTypePaletteEntryMapImpl stringToFBTypePaletteEntryMap = new StringToFBTypePaletteEntryMapImpl();
		return stringToFBTypePaletteEntryMap;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Map.Entry<String, DeviceTypePaletteEntry> createStringToFDeviceTypePaletteEntryMap() {
		StringToFDeviceTypePaletteEntryMapImpl stringToFDeviceTypePaletteEntryMap = new StringToFDeviceTypePaletteEntryMapImpl();
		return stringToFDeviceTypePaletteEntryMap;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Map.Entry<String, ResourceTypeEntry> createStringToResourceTypeEntryMap() {
		StringToResourceTypeEntryMapImpl stringToResourceTypeEntryMap = new StringToResourceTypeEntryMapImpl();
		return stringToResourceTypeEntryMap;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Map.Entry<String, SegmentTypePaletteEntry> createStringToSegmentTypePaletteEntryMap() {
		StringToSegmentTypePaletteEntryMapImpl stringToSegmentTypePaletteEntryMap = new StringToSegmentTypePaletteEntryMapImpl();
		return stringToSegmentTypePaletteEntryMap;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Map.Entry<String, SubApplicationTypePaletteEntry> createStringToSubApplicationTypePaletteEntryMap() {
		StringToSubApplicationTypePaletteEntryMapImpl stringToSubApplicationTypePaletteEntryMap = new StringToSubApplicationTypePaletteEntryMapImpl();
		return stringToSubApplicationTypePaletteEntryMap;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Map.Entry<String, AdapterTypePaletteEntry> createStringToAdapterTypePaletteEntryMap() {
		StringToAdapterTypePaletteEntryMapImpl stringToAdapterTypePaletteEntryMap = new StringToAdapterTypePaletteEntryMapImpl();
		return stringToAdapterTypePaletteEntryMap;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public FBTypePaletteEntry createFBTypePaletteEntry() {
		FBTypePaletteEntryImpl fbTypePaletteEntry = new FBTypePaletteEntryImpl();
		return fbTypePaletteEntry;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public DeviceTypePaletteEntry createDeviceTypePaletteEntry() {
		DeviceTypePaletteEntryImpl deviceTypePaletteEntry = new DeviceTypePaletteEntryImpl();
		return deviceTypePaletteEntry;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ResourceTypeEntry createResourceTypeEntry() {
		ResourceTypeEntryImpl resourceTypeEntry = new ResourceTypeEntryImpl();
		return resourceTypeEntry;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public SegmentTypePaletteEntry createSegmentTypePaletteEntry() {
		SegmentTypePaletteEntryImpl segmentTypePaletteEntry = new SegmentTypePaletteEntryImpl();
		return segmentTypePaletteEntry;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public AdapterTypePaletteEntry createAdapterTypePaletteEntry() {
		AdapterTypePaletteEntryImpl adapterTypePaletteEntry = new AdapterTypePaletteEntryImpl();
		return adapterTypePaletteEntry;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public DataTypePaletteEntry createDataTypePaletteEntry() {
		DataTypePaletteEntryImpl dataTypePaletteEntry = new DataTypePaletteEntryImpl();
		return dataTypePaletteEntry;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public SubApplicationTypePaletteEntry createSubApplicationTypePaletteEntry() {
		SubApplicationTypePaletteEntryImpl subApplicationTypePaletteEntry = new SubApplicationTypePaletteEntryImpl();
		return subApplicationTypePaletteEntry;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public IFile createIFileFromString(EDataType eDataType, String initialValue) {
		return (IFile) super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertIFileToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public IProject createIProjectFromString(EDataType eDataType, String initialValue) {
		return (IProject) super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertIProjectToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TypeImporter createTypeImporterFromString(EDataType eDataType, String initialValue) {
		return (TypeImporter) super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertTypeImporterToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public XMLStreamException createXMLStreamExceptionFromString(EDataType eDataType, String initialValue) {
		return (XMLStreamException) super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertXMLStreamExceptionToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public CoreException createCoreExceptionFromString(EDataType eDataType, String initialValue) {
		return (CoreException) super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertCoreExceptionToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public PalettePackage getPalettePackage() {
		return (PalettePackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static PalettePackage getPackage() {
		return PalettePackage.eINSTANCE;
	}

} // PaletteFactoryImpl
