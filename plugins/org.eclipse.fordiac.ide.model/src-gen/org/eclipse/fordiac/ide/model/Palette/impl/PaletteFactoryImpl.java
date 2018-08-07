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
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.fordiac.ide.model.Palette.AdapterTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.DeviceTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteFactory;
import org.eclipse.fordiac.ide.model.Palette.PaletteGroup;
import org.eclipse.fordiac.ide.model.Palette.PalettePackage;
import org.eclipse.fordiac.ide.model.Palette.ResourceTypeEntry;
import org.eclipse.fordiac.ide.model.Palette.SegmentTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.SubApplicationTypePaletteEntry;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PaletteFactoryImpl extends EFactoryImpl implements PaletteFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PaletteFactory init() {
		try {
			PaletteFactory thePaletteFactory = (PaletteFactory)EPackage.Registry.INSTANCE.getEFactory(PalettePackage.eNS_URI);
			if (thePaletteFactory != null) {
				return thePaletteFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new PaletteFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PaletteFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case PalettePackage.PALETTE: return createPalette();
			case PalettePackage.PALETTE_GROUP: return createPaletteGroup();
			case PalettePackage.FB_TYPE_PALETTE_ENTRY: return createFBTypePaletteEntry();
			case PalettePackage.DEVICE_TYPE_PALETTE_ENTRY: return createDeviceTypePaletteEntry();
			case PalettePackage.RESOURCE_TYPE_ENTRY: return createResourceTypeEntry();
			case PalettePackage.SEGMENT_TYPE_PALETTE_ENTRY: return createSegmentTypePaletteEntry();
			case PalettePackage.ADAPTER_TYPE_PALETTE_ENTRY: return createAdapterTypePaletteEntry();
			case PalettePackage.SUB_APPLICATION_TYPE_PALETTE_ENTRY: return createSubApplicationTypePaletteEntry();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case PalettePackage.IFILE:
				return createIFileFromString(eDataType, initialValue);
			case PalettePackage.IPROJECT:
				return createIProjectFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case PalettePackage.IFILE:
				return convertIFileToString(eDataType, instanceValue);
			case PalettePackage.IPROJECT:
				return convertIProjectToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Palette createPalette() {
		PaletteImpl palette = new PaletteImpl();
		return palette;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PaletteGroup createPaletteGroup() {
		PaletteGroupImpl paletteGroup = new PaletteGroupImpl();
		return paletteGroup;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FBTypePaletteEntry createFBTypePaletteEntry() {
		FBTypePaletteEntryImpl fbTypePaletteEntry = new FBTypePaletteEntryImpl();
		return fbTypePaletteEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DeviceTypePaletteEntry createDeviceTypePaletteEntry() {
		DeviceTypePaletteEntryImpl deviceTypePaletteEntry = new DeviceTypePaletteEntryImpl();
		return deviceTypePaletteEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceTypeEntry createResourceTypeEntry() {
		ResourceTypeEntryImpl resourceTypeEntry = new ResourceTypeEntryImpl();
		return resourceTypeEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SegmentTypePaletteEntry createSegmentTypePaletteEntry() {
		SegmentTypePaletteEntryImpl segmentTypePaletteEntry = new SegmentTypePaletteEntryImpl();
		return segmentTypePaletteEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AdapterTypePaletteEntry createAdapterTypePaletteEntry() {
		AdapterTypePaletteEntryImpl adapterTypePaletteEntry = new AdapterTypePaletteEntryImpl();
		return adapterTypePaletteEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SubApplicationTypePaletteEntry createSubApplicationTypePaletteEntry() {
		SubApplicationTypePaletteEntryImpl subApplicationTypePaletteEntry = new SubApplicationTypePaletteEntryImpl();
		return subApplicationTypePaletteEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IFile createIFileFromString(EDataType eDataType, String initialValue) {
		return (IFile)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertIFileToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IProject createIProjectFromString(EDataType eDataType, String initialValue) {
		return (IProject)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertIProjectToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PalettePackage getPalettePackage() {
		return (PalettePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static PalettePackage getPackage() {
		return PalettePackage.eINSTANCE;
	}

} //PaletteFactoryImpl
