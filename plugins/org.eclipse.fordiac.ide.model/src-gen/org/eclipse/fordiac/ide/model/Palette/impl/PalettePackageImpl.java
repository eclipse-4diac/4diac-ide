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
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.fordiac.ide.model.Palette.AdapterTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.DataTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.DeviceTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PaletteFactory;
import org.eclipse.fordiac.ide.model.Palette.PalettePackage;
import org.eclipse.fordiac.ide.model.Palette.ResourceTypeEntry;
import org.eclipse.fordiac.ide.model.Palette.SegmentTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.SubApplicationTypePaletteEntry;
import org.eclipse.fordiac.ide.model.data.DataPackage;
import org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl;
import org.eclipse.fordiac.ide.model.dataimport.TypeImporter;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

/** <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 * 
 * @generated */
public class PalettePackageImpl extends EPackageImpl implements PalettePackage {
	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	private EClass paletteEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	private EClass stringToFBTypePaletteEntryMapEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	private EClass stringToFDeviceTypePaletteEntryMapEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	private EClass stringToResourceTypeEntryMapEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	private EClass stringToSegmentTypePaletteEntryMapEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	private EClass stringToSubApplicationTypePaletteEntryMapEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	private EClass stringToAdapterTypePaletteEntryMapEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	private EClass fbTypePaletteEntryEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	private EClass paletteEntryEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	private EClass deviceTypePaletteEntryEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	private EClass resourceTypeEntryEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	private EClass segmentTypePaletteEntryEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	private EClass adapterTypePaletteEntryEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	private EClass dataTypePaletteEntryEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	private EClass subApplicationTypePaletteEntryEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	private EDataType iFileEDataType = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	private EDataType iProjectEDataType = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	private EDataType typeImporterEDataType = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	private EDataType xmlStreamExceptionEDataType = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	private EDataType coreExceptionEDataType = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	private EDataType typeLibraryEDataType = null;

	/** Creates an instance of the model <b>Package</b>, registered with {@link org.eclipse.emf.ecore.EPackage.Registry
	 * EPackage.Registry} by the package package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static factory method {@link #init init()}, which also
	 * performs initialization of the package, or returns the registered package, if one already exists. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.fordiac.ide.model.Palette.PalettePackage#eNS_URI
	 * @see #init()
	 * @generated */
	private PalettePackageImpl() {
		super(eNS_URI, PaletteFactory.eINSTANCE);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	private static boolean isInited = false;

	/** Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>
	 * This method is used to initialize {@link PalettePackage#eINSTANCE} when that field is accessed. Clients should
	 * not invoke it directly. Instead, they should simply access that field to obtain the package. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated */
	public static PalettePackage init() {
		if (isInited)
			return (PalettePackage) EPackage.Registry.INSTANCE.getEPackage(PalettePackage.eNS_URI);

		// Obtain or create and register package
		Object registeredPalettePackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		PalettePackageImpl thePalettePackage = registeredPalettePackage instanceof PalettePackageImpl
				? (PalettePackageImpl) registeredPalettePackage
				: new PalettePackageImpl();

		isInited = true;

		// Initialize simple dependencies
		XMLTypePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(LibraryElementPackage.eNS_URI);
		LibraryElementPackageImpl theLibraryElementPackage = (LibraryElementPackageImpl) (registeredPackage instanceof LibraryElementPackageImpl
				? registeredPackage
				: LibraryElementPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(DataPackage.eNS_URI);
		DataPackageImpl theDataPackage = (DataPackageImpl) (registeredPackage instanceof DataPackageImpl
				? registeredPackage
				: DataPackage.eINSTANCE);

		// Create package meta-data objects
		thePalettePackage.createPackageContents();
		theLibraryElementPackage.createPackageContents();
		theDataPackage.createPackageContents();

		// Initialize created meta-data
		thePalettePackage.initializePackageContents();
		theLibraryElementPackage.initializePackageContents();
		theDataPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		thePalettePackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(PalettePackage.eNS_URI, thePalettePackage);
		return thePalettePackage;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EClass getPalette() {
		return paletteEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EAttribute getPalette_TypeLibrary() {
		return (EAttribute) paletteEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EReference getPalette_FbTypes() {
		return (EReference) paletteEClass.getEStructuralFeatures().get(3);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EReference getPalette_ResourceTypes() {
		return (EReference) paletteEClass.getEStructuralFeatures().get(4);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EReference getPalette_DeviceTypes() {
		return (EReference) paletteEClass.getEStructuralFeatures().get(2);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EReference getPalette_SegmentTypes() {
		return (EReference) paletteEClass.getEStructuralFeatures().get(5);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EReference getPalette_SubAppTypes() {
		return (EReference) paletteEClass.getEStructuralFeatures().get(6);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EReference getPalette_AdapterTypes() {
		return (EReference) paletteEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EClass getStringToFBTypePaletteEntryMap() {
		return stringToFBTypePaletteEntryMapEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EAttribute getStringToFBTypePaletteEntryMap_Key() {
		return (EAttribute) stringToFBTypePaletteEntryMapEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EReference getStringToFBTypePaletteEntryMap_Value() {
		return (EReference) stringToFBTypePaletteEntryMapEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EClass getStringToFDeviceTypePaletteEntryMap() {
		return stringToFDeviceTypePaletteEntryMapEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EAttribute getStringToFDeviceTypePaletteEntryMap_Key() {
		return (EAttribute) stringToFDeviceTypePaletteEntryMapEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EReference getStringToFDeviceTypePaletteEntryMap_Value() {
		return (EReference) stringToFDeviceTypePaletteEntryMapEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EClass getStringToResourceTypeEntryMap() {
		return stringToResourceTypeEntryMapEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EAttribute getStringToResourceTypeEntryMap_Key() {
		return (EAttribute) stringToResourceTypeEntryMapEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EReference getStringToResourceTypeEntryMap_Value() {
		return (EReference) stringToResourceTypeEntryMapEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EClass getStringToSegmentTypePaletteEntryMap() {
		return stringToSegmentTypePaletteEntryMapEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EAttribute getStringToSegmentTypePaletteEntryMap_Key() {
		return (EAttribute) stringToSegmentTypePaletteEntryMapEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EReference getStringToSegmentTypePaletteEntryMap_Value() {
		return (EReference) stringToSegmentTypePaletteEntryMapEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EClass getStringToSubApplicationTypePaletteEntryMap() {
		return stringToSubApplicationTypePaletteEntryMapEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EAttribute getStringToSubApplicationTypePaletteEntryMap_Key() {
		return (EAttribute) stringToSubApplicationTypePaletteEntryMapEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EReference getStringToSubApplicationTypePaletteEntryMap_Value() {
		return (EReference) stringToSubApplicationTypePaletteEntryMapEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EClass getStringToAdapterTypePaletteEntryMap() {
		return stringToAdapterTypePaletteEntryMapEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EAttribute getStringToAdapterTypePaletteEntryMap_Key() {
		return (EAttribute) stringToAdapterTypePaletteEntryMapEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EReference getStringToAdapterTypePaletteEntryMap_Value() {
		return (EReference) stringToAdapterTypePaletteEntryMapEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EClass getFBTypePaletteEntry() {
		return fbTypePaletteEntryEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EClass getPaletteEntry() {
		return paletteEntryEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EAttribute getPaletteEntry_Label() {
		return (EAttribute) paletteEntryEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EAttribute getPaletteEntry_File() {
		return (EAttribute) paletteEntryEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EAttribute getPaletteEntry_LastModificationTimestamp() {
		return (EAttribute) paletteEntryEClass.getEStructuralFeatures().get(2);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EReference getPaletteEntry_Type() {
		return (EReference) paletteEntryEClass.getEStructuralFeatures().get(3);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EReference getPaletteEntry_Palette() {
		return (EReference) paletteEntryEClass.getEStructuralFeatures().get(4);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EClass getDeviceTypePaletteEntry() {
		return deviceTypePaletteEntryEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EClass getResourceTypeEntry() {
		return resourceTypeEntryEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EClass getSegmentTypePaletteEntry() {
		return segmentTypePaletteEntryEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EClass getAdapterTypePaletteEntry() {
		return adapterTypePaletteEntryEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EClass getDataTypePaletteEntry() {
		return dataTypePaletteEntryEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EClass getSubApplicationTypePaletteEntry() {
		return subApplicationTypePaletteEntryEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EDataType getIFile() {
		return iFileEDataType;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EDataType getIProject() {
		return iProjectEDataType;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EDataType getTypeImporter() {
		return typeImporterEDataType;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EDataType getXMLStreamException() {
		return xmlStreamExceptionEDataType;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EDataType getCoreException() {
		return coreExceptionEDataType;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EDataType getTypeLibrary() {
		return typeLibraryEDataType;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public PaletteFactory getPaletteFactory() {
		return (PaletteFactory) getEFactoryInstance();
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	private boolean isCreated = false;

	/** Creates the meta-model objects for the package. This method is guarded to have no affect on any invocation but
	 * its first. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		paletteEClass = createEClass(PALETTE);
		createEAttribute(paletteEClass, PALETTE__TYPE_LIBRARY);
		createEReference(paletteEClass, PALETTE__ADAPTER_TYPES);
		createEReference(paletteEClass, PALETTE__DEVICE_TYPES);
		createEReference(paletteEClass, PALETTE__FB_TYPES);
		createEReference(paletteEClass, PALETTE__RESOURCE_TYPES);
		createEReference(paletteEClass, PALETTE__SEGMENT_TYPES);
		createEReference(paletteEClass, PALETTE__SUB_APP_TYPES);

		stringToAdapterTypePaletteEntryMapEClass = createEClass(STRING_TO_ADAPTER_TYPE_PALETTE_ENTRY_MAP);
		createEAttribute(stringToAdapterTypePaletteEntryMapEClass, STRING_TO_ADAPTER_TYPE_PALETTE_ENTRY_MAP__KEY);
		createEReference(stringToAdapterTypePaletteEntryMapEClass, STRING_TO_ADAPTER_TYPE_PALETTE_ENTRY_MAP__VALUE);

		stringToFDeviceTypePaletteEntryMapEClass = createEClass(STRING_TO_FDEVICE_TYPE_PALETTE_ENTRY_MAP);
		createEAttribute(stringToFDeviceTypePaletteEntryMapEClass, STRING_TO_FDEVICE_TYPE_PALETTE_ENTRY_MAP__KEY);
		createEReference(stringToFDeviceTypePaletteEntryMapEClass, STRING_TO_FDEVICE_TYPE_PALETTE_ENTRY_MAP__VALUE);

		stringToFBTypePaletteEntryMapEClass = createEClass(STRING_TO_FB_TYPE_PALETTE_ENTRY_MAP);
		createEAttribute(stringToFBTypePaletteEntryMapEClass, STRING_TO_FB_TYPE_PALETTE_ENTRY_MAP__KEY);
		createEReference(stringToFBTypePaletteEntryMapEClass, STRING_TO_FB_TYPE_PALETTE_ENTRY_MAP__VALUE);

		stringToResourceTypeEntryMapEClass = createEClass(STRING_TO_RESOURCE_TYPE_ENTRY_MAP);
		createEAttribute(stringToResourceTypeEntryMapEClass, STRING_TO_RESOURCE_TYPE_ENTRY_MAP__KEY);
		createEReference(stringToResourceTypeEntryMapEClass, STRING_TO_RESOURCE_TYPE_ENTRY_MAP__VALUE);

		stringToSegmentTypePaletteEntryMapEClass = createEClass(STRING_TO_SEGMENT_TYPE_PALETTE_ENTRY_MAP);
		createEAttribute(stringToSegmentTypePaletteEntryMapEClass, STRING_TO_SEGMENT_TYPE_PALETTE_ENTRY_MAP__KEY);
		createEReference(stringToSegmentTypePaletteEntryMapEClass, STRING_TO_SEGMENT_TYPE_PALETTE_ENTRY_MAP__VALUE);

		stringToSubApplicationTypePaletteEntryMapEClass = createEClass(
				STRING_TO_SUB_APPLICATION_TYPE_PALETTE_ENTRY_MAP);
		createEAttribute(stringToSubApplicationTypePaletteEntryMapEClass,
				STRING_TO_SUB_APPLICATION_TYPE_PALETTE_ENTRY_MAP__KEY);
		createEReference(stringToSubApplicationTypePaletteEntryMapEClass,
				STRING_TO_SUB_APPLICATION_TYPE_PALETTE_ENTRY_MAP__VALUE);

		paletteEntryEClass = createEClass(PALETTE_ENTRY);
		createEAttribute(paletteEntryEClass, PALETTE_ENTRY__LABEL);
		createEAttribute(paletteEntryEClass, PALETTE_ENTRY__FILE);
		createEAttribute(paletteEntryEClass, PALETTE_ENTRY__LAST_MODIFICATION_TIMESTAMP);
		createEReference(paletteEntryEClass, PALETTE_ENTRY__TYPE);
		createEReference(paletteEntryEClass, PALETTE_ENTRY__PALETTE);

		adapterTypePaletteEntryEClass = createEClass(ADAPTER_TYPE_PALETTE_ENTRY);

		dataTypePaletteEntryEClass = createEClass(DATA_TYPE_PALETTE_ENTRY);

		deviceTypePaletteEntryEClass = createEClass(DEVICE_TYPE_PALETTE_ENTRY);

		fbTypePaletteEntryEClass = createEClass(FB_TYPE_PALETTE_ENTRY);

		resourceTypeEntryEClass = createEClass(RESOURCE_TYPE_ENTRY);

		segmentTypePaletteEntryEClass = createEClass(SEGMENT_TYPE_PALETTE_ENTRY);

		subApplicationTypePaletteEntryEClass = createEClass(SUB_APPLICATION_TYPE_PALETTE_ENTRY);

		// Create data types
		iFileEDataType = createEDataType(IFILE);
		iProjectEDataType = createEDataType(IPROJECT);
		typeImporterEDataType = createEDataType(TYPE_IMPORTER);
		xmlStreamExceptionEDataType = createEDataType(XML_STREAM_EXCEPTION);
		coreExceptionEDataType = createEDataType(CORE_EXCEPTION);
		typeLibraryEDataType = createEDataType(TYPE_LIBRARY);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	private boolean isInitialized = false;

	/** Complete the initialization of the package and its meta-model. This method is guarded to have no affect on any
	 * invocation but its first. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		XMLTypePackage theXMLTypePackage = (XMLTypePackage) EPackage.Registry.INSTANCE
				.getEPackage(XMLTypePackage.eNS_URI);
		LibraryElementPackage theLibraryElementPackage = (LibraryElementPackage) EPackage.Registry.INSTANCE
				.getEPackage(LibraryElementPackage.eNS_URI);
		DataPackage theDataPackage = (DataPackage) EPackage.Registry.INSTANCE.getEPackage(DataPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		adapterTypePaletteEntryEClass.getESuperTypes().add(this.getPaletteEntry());
		dataTypePaletteEntryEClass.getESuperTypes().add(this.getPaletteEntry());
		deviceTypePaletteEntryEClass.getESuperTypes().add(this.getPaletteEntry());
		fbTypePaletteEntryEClass.getESuperTypes().add(this.getPaletteEntry());
		resourceTypeEntryEClass.getESuperTypes().add(this.getPaletteEntry());
		segmentTypePaletteEntryEClass.getESuperTypes().add(this.getPaletteEntry());
		subApplicationTypePaletteEntryEClass.getESuperTypes().add(this.getPaletteEntry());

		// Initialize classes and features; add operations and parameters
		initEClass(paletteEClass, Palette.class, "Palette", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getPalette_TypeLibrary(), this.getTypeLibrary(), "typeLibrary", null, 0, 1, Palette.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPalette_AdapterTypes(), this.getStringToAdapterTypePaletteEntryMap(), null, "adapterTypes", //$NON-NLS-1$
				null, 0, -1, Palette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPalette_DeviceTypes(), this.getStringToFDeviceTypePaletteEntryMap(), null, "deviceTypes", //$NON-NLS-1$
				null, 0, -1, Palette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPalette_FbTypes(), this.getStringToFBTypePaletteEntryMap(), null, "fbTypes", null, 0, -1, //$NON-NLS-1$
				Palette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPalette_ResourceTypes(), this.getStringToResourceTypeEntryMap(), null, "resourceTypes", null, //$NON-NLS-1$
				0, -1, Palette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPalette_SegmentTypes(), this.getStringToSegmentTypePaletteEntryMap(), null, "segmentTypes", //$NON-NLS-1$
				null, 0, -1, Palette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPalette_SubAppTypes(), this.getStringToSubApplicationTypePaletteEntryMap(), null,
				"subAppTypes", null, 0, -1, Palette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, //$NON-NLS-1$
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(paletteEClass, this.getAdapterTypePaletteEntry(), "getAdapterTypesSorted", 0, -1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);

		EOperation op = addEOperation(paletteEClass, this.getAdapterTypePaletteEntry(), "getAdapterTypeEntry", 1, 1, //$NON-NLS-1$
				IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "typeName", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(paletteEClass, this.getDeviceTypePaletteEntry(), "getDeviceTypeEntry", 1, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "typeName", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(paletteEClass, this.getFBTypePaletteEntry(), "getFBTypeEntry", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEString(), "typeName", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(paletteEClass, this.getResourceTypeEntry(), "getResourceTypeEntry", 1, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "typeName", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(paletteEClass, this.getSegmentTypePaletteEntry(), "getSegmentTypeEntry", 1, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "typeName", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(paletteEClass, this.getSubApplicationTypePaletteEntry(), "getSubAppTypeEntry", 1, 1, //$NON-NLS-1$
				IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "typeName", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(paletteEClass, null, "addPaletteEntry", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getPaletteEntry(), "entry", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(paletteEClass, null, "removePaletteEntry", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getPaletteEntry(), "entry", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(paletteEClass, this.getIProject(), "getProject", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(stringToAdapterTypePaletteEntryMapEClass, Map.Entry.class, "StringToAdapterTypePaletteEntryMap", //$NON-NLS-1$
				!IS_ABSTRACT, !IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStringToAdapterTypePaletteEntryMap_Key(), ecorePackage.getEString(), "key", null, 0, 1, //$NON-NLS-1$
				Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getStringToAdapterTypePaletteEntryMap_Value(), this.getAdapterTypePaletteEntry(), null, "value", //$NON-NLS-1$
				null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(stringToFDeviceTypePaletteEntryMapEClass, Map.Entry.class, "StringToFDeviceTypePaletteEntryMap", //$NON-NLS-1$
				!IS_ABSTRACT, !IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStringToFDeviceTypePaletteEntryMap_Key(), ecorePackage.getEString(), "key", null, 0, 1, //$NON-NLS-1$
				Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getStringToFDeviceTypePaletteEntryMap_Value(), this.getDeviceTypePaletteEntry(), null, "value", //$NON-NLS-1$
				null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(stringToFBTypePaletteEntryMapEClass, Map.Entry.class, "StringToFBTypePaletteEntryMap", !IS_ABSTRACT, //$NON-NLS-1$
				!IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStringToFBTypePaletteEntryMap_Key(), ecorePackage.getEString(), "key", null, 0, 1, //$NON-NLS-1$
				Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getStringToFBTypePaletteEntryMap_Value(), this.getFBTypePaletteEntry(), null, "value", null, 0, //$NON-NLS-1$
				1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(stringToResourceTypeEntryMapEClass, Map.Entry.class, "StringToResourceTypeEntryMap", !IS_ABSTRACT, //$NON-NLS-1$
				!IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStringToResourceTypeEntryMap_Key(), ecorePackage.getEString(), "key", null, 0, 1, //$NON-NLS-1$
				Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getStringToResourceTypeEntryMap_Value(), this.getResourceTypeEntry(), null, "value", null, 0, 1, //$NON-NLS-1$
				Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(stringToSegmentTypePaletteEntryMapEClass, Map.Entry.class, "StringToSegmentTypePaletteEntryMap", //$NON-NLS-1$
				!IS_ABSTRACT, !IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStringToSegmentTypePaletteEntryMap_Key(), ecorePackage.getEString(), "key", null, 0, 1, //$NON-NLS-1$
				Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getStringToSegmentTypePaletteEntryMap_Value(), this.getSegmentTypePaletteEntry(), null, "value", //$NON-NLS-1$
				null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(stringToSubApplicationTypePaletteEntryMapEClass, Map.Entry.class,
				"StringToSubApplicationTypePaletteEntryMap", !IS_ABSTRACT, !IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getStringToSubApplicationTypePaletteEntryMap_Key(), ecorePackage.getEString(), "key", null, 0, 1, //$NON-NLS-1$
				Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getStringToSubApplicationTypePaletteEntryMap_Value(), this.getSubApplicationTypePaletteEntry(),
				null, "value", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, //$NON-NLS-1$
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(paletteEntryEClass, PaletteEntry.class, "PaletteEntry", IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPaletteEntry_Label(), ecorePackage.getEString(), "label", null, 0, 1, PaletteEntry.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPaletteEntry_File(), this.getIFile(), "file", null, 0, 1, PaletteEntry.class, !IS_TRANSIENT, //$NON-NLS-1$
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPaletteEntry_LastModificationTimestamp(), theXMLTypePackage.getLong(),
				"lastModificationTimestamp", null, 0, 1, PaletteEntry.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, //$NON-NLS-1$
				!IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getPaletteEntry_Type(), theLibraryElementPackage.getLibraryElement(),
				theLibraryElementPackage.getLibraryElement_PaletteEntry(), "type", null, 1, 1, PaletteEntry.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPaletteEntry_Palette(), this.getPalette(), null, "palette", null, 1, 1, PaletteEntry.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(paletteEntryEClass, theXMLTypePackage.getString(), "getProjectRelativeTypePath", 1, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);

		addEOperation(paletteEntryEClass, theLibraryElementPackage.getLibraryElement(), "loadType", 0, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);

		addEOperation(paletteEntryEClass, this.getTypeImporter(), "getTypeImporter", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(paletteEntryEClass, this.getTypeLibrary(), "getTypeLibrary", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(adapterTypePaletteEntryEClass, AdapterTypePaletteEntry.class, "AdapterTypePaletteEntry", //$NON-NLS-1$
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		addEOperation(adapterTypePaletteEntryEClass, theLibraryElementPackage.getAdapterType(), "getType", 0, 1, //$NON-NLS-1$
				IS_UNIQUE, IS_ORDERED);

		op = addEOperation(adapterTypePaletteEntryEClass, null, "setType", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theLibraryElementPackage.getLibraryElement(), "type", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(adapterTypePaletteEntryEClass, this.getTypeImporter(), "getTypeImporter", 0, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);

		initEClass(dataTypePaletteEntryEClass, DataTypePaletteEntry.class, "DataTypePaletteEntry", !IS_ABSTRACT, //$NON-NLS-1$
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		addEOperation(dataTypePaletteEntryEClass, theDataPackage.getAnyDerivedType(), "getType", 1, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);

		op = addEOperation(dataTypePaletteEntryEClass, null, "setType", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theLibraryElementPackage.getLibraryElement(), "type", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(dataTypePaletteEntryEClass, this.getTypeImporter(), "getTypeImporter", 0, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);

		initEClass(deviceTypePaletteEntryEClass, DeviceTypePaletteEntry.class, "DeviceTypePaletteEntry", !IS_ABSTRACT, //$NON-NLS-1$
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		addEOperation(deviceTypePaletteEntryEClass, theLibraryElementPackage.getDeviceType(), "getDeviceType", 1, 1, //$NON-NLS-1$
				IS_UNIQUE, IS_ORDERED);

		op = addEOperation(deviceTypePaletteEntryEClass, null, "setType", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theLibraryElementPackage.getLibraryElement(), "type", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(deviceTypePaletteEntryEClass, this.getTypeImporter(), "getTypeImporter", 0, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);

		initEClass(fbTypePaletteEntryEClass, FBTypePaletteEntry.class, "FBTypePaletteEntry", !IS_ABSTRACT, //$NON-NLS-1$
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		addEOperation(fbTypePaletteEntryEClass, theLibraryElementPackage.getFBType(), "getFBType", 1, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);

		op = addEOperation(fbTypePaletteEntryEClass, null, "setType", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theLibraryElementPackage.getLibraryElement(), "type", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(fbTypePaletteEntryEClass, this.getTypeImporter(), "getTypeImporter", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(resourceTypeEntryEClass, ResourceTypeEntry.class, "ResourceTypeEntry", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);

		addEOperation(resourceTypeEntryEClass, theLibraryElementPackage.getResourceType(), "getResourceType", 1, 1, //$NON-NLS-1$
				IS_UNIQUE, IS_ORDERED);

		op = addEOperation(resourceTypeEntryEClass, null, "setType", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theLibraryElementPackage.getLibraryElement(), "type", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(resourceTypeEntryEClass, this.getTypeImporter(), "getTypeImporter", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(segmentTypePaletteEntryEClass, SegmentTypePaletteEntry.class, "SegmentTypePaletteEntry", //$NON-NLS-1$
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		addEOperation(segmentTypePaletteEntryEClass, theLibraryElementPackage.getSegmentType(), "getSegmentType", 1, 1, //$NON-NLS-1$
				IS_UNIQUE, IS_ORDERED);

		op = addEOperation(segmentTypePaletteEntryEClass, null, "setType", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theLibraryElementPackage.getLibraryElement(), "type", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(segmentTypePaletteEntryEClass, this.getTypeImporter(), "getTypeImporter", 0, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);

		initEClass(subApplicationTypePaletteEntryEClass, SubApplicationTypePaletteEntry.class,
				"SubApplicationTypePaletteEntry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		addEOperation(subApplicationTypePaletteEntryEClass, theLibraryElementPackage.getSubAppType(),
				"getSubApplicationType", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(subApplicationTypePaletteEntryEClass, null, "setType", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theLibraryElementPackage.getLibraryElement(), "type", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(subApplicationTypePaletteEntryEClass, this.getTypeImporter(), "getTypeImporter", 0, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);

		// Initialize data types
		initEDataType(iFileEDataType, IFile.class, "IFile", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEDataType(iProjectEDataType, IProject.class, "IProject", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEDataType(typeImporterEDataType, TypeImporter.class, "TypeImporter", IS_SERIALIZABLE, //$NON-NLS-1$
				!IS_GENERATED_INSTANCE_CLASS);
		initEDataType(xmlStreamExceptionEDataType, XMLStreamException.class, "XMLStreamException", IS_SERIALIZABLE, //$NON-NLS-1$
				!IS_GENERATED_INSTANCE_CLASS);
		initEDataType(coreExceptionEDataType, CoreException.class, "CoreException", IS_SERIALIZABLE, //$NON-NLS-1$
				!IS_GENERATED_INSTANCE_CLASS);
		initEDataType(typeLibraryEDataType, TypeLibrary.class, "TypeLibrary", IS_SERIALIZABLE, //$NON-NLS-1$
				!IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} // PalettePackageImpl
