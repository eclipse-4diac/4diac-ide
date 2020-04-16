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
package org.eclipse.fordiac.ide.model.Palette;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains
 * accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.fordiac.ide.model.Palette.PaletteFactory
 * @model kind="package"
 * @generated
 */
public interface PalettePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "Palette";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "org.eclipse.fordiac.ide.model.Palette";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "Palette";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	PalettePackage eINSTANCE = org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.Palette.impl.PaletteImpl <em>Palette</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.PaletteImpl
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getPalette()
	 * @generated
	 */
	int PALETTE = 0;

	/**
	 * The feature id for the '<em><b>Project</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PALETTE__PROJECT = 0;

	/**
	 * The feature id for the '<em><b>Automation System</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PALETTE__AUTOMATION_SYSTEM = 1;

	/**
	 * The feature id for the '<em><b>Type Library</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE__TYPE_LIBRARY = 2;

	/**
	 * The feature id for the '<em><b>Adapter Types</b></em>' map. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PALETTE__ADAPTER_TYPES = 3;

	/**
	 * The feature id for the '<em><b>Device Types</b></em>' map. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PALETTE__DEVICE_TYPES = 4;

	/**
	 * The feature id for the '<em><b>Fb Types</b></em>' map.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE__FB_TYPES = 5;

	/**
	 * The feature id for the '<em><b>Resource Types</b></em>' map. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PALETTE__RESOURCE_TYPES = 6;

	/**
	 * The feature id for the '<em><b>Segment Types</b></em>' map. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PALETTE__SEGMENT_TYPES = 7;

	/**
	 * The feature id for the '<em><b>Sub App Types</b></em>' map. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PALETTE__SUB_APP_TYPES = 8;

	/**
	 * The number of structural features of the '<em>Palette</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PALETTE_FEATURE_COUNT = 9;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.Palette.impl.StringToFBTypePaletteEntryMapImpl <em>String To FB Type Palette Entry Map</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.StringToFBTypePaletteEntryMapImpl
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getStringToFBTypePaletteEntryMap()
	 * @generated
	 */
	int STRING_TO_FB_TYPE_PALETTE_ENTRY_MAP = 3;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.fordiac.ide.model.Palette.impl.StringToFDeviceTypePaletteEntryMapImpl
	 * <em>String To FDevice Type Palette Entry Map</em>}' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.StringToFDeviceTypePaletteEntryMapImpl
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getStringToFDeviceTypePaletteEntryMap()
	 * @generated
	 */
	int STRING_TO_FDEVICE_TYPE_PALETTE_ENTRY_MAP = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.Palette.impl.StringToResourceTypeEntryMapImpl <em>String To Resource Type Entry Map</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.StringToResourceTypeEntryMapImpl
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getStringToResourceTypeEntryMap()
	 * @generated
	 */
	int STRING_TO_RESOURCE_TYPE_ENTRY_MAP = 4;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.fordiac.ide.model.Palette.impl.StringToSegmentTypePaletteEntryMapImpl
	 * <em>String To Segment Type Palette Entry Map</em>}' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.StringToSegmentTypePaletteEntryMapImpl
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getStringToSegmentTypePaletteEntryMap()
	 * @generated
	 */
	int STRING_TO_SEGMENT_TYPE_PALETTE_ENTRY_MAP = 5;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.fordiac.ide.model.Palette.impl.StringToSubApplicationTypePaletteEntryMapImpl
	 * <em>String To Sub Application Type Palette Entry Map</em>}' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.StringToSubApplicationTypePaletteEntryMapImpl
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getStringToSubApplicationTypePaletteEntryMap()
	 * @generated
	 */
	int STRING_TO_SUB_APPLICATION_TYPE_PALETTE_ENTRY_MAP = 6;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.fordiac.ide.model.Palette.impl.StringToAdapterTypePaletteEntryMapImpl
	 * <em>String To Adapter Type Palette Entry Map</em>}' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.StringToAdapterTypePaletteEntryMapImpl
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getStringToAdapterTypePaletteEntryMap()
	 * @generated
	 */
	int STRING_TO_ADAPTER_TYPE_PALETTE_ENTRY_MAP = 1;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_ADAPTER_TYPE_PALETTE_ENTRY_MAP__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_ADAPTER_TYPE_PALETTE_ENTRY_MAP__VALUE = 1;

	/**
	 * The number of structural features of the '<em>String To Adapter Type Palette Entry Map</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_ADAPTER_TYPE_PALETTE_ENTRY_MAP_FEATURE_COUNT = 2;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_FDEVICE_TYPE_PALETTE_ENTRY_MAP__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_FDEVICE_TYPE_PALETTE_ENTRY_MAP__VALUE = 1;

	/**
	 * The number of structural features of the '<em>String To FDevice Type Palette Entry Map</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_FDEVICE_TYPE_PALETTE_ENTRY_MAP_FEATURE_COUNT = 2;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_FB_TYPE_PALETTE_ENTRY_MAP__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_FB_TYPE_PALETTE_ENTRY_MAP__VALUE = 1;

	/**
	 * The number of structural features of the '<em>String To FB Type Palette Entry Map</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_FB_TYPE_PALETTE_ENTRY_MAP_FEATURE_COUNT = 2;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_RESOURCE_TYPE_ENTRY_MAP__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_RESOURCE_TYPE_ENTRY_MAP__VALUE = 1;

	/**
	 * The number of structural features of the '<em>String To Resource Type Entry Map</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_RESOURCE_TYPE_ENTRY_MAP_FEATURE_COUNT = 2;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_SEGMENT_TYPE_PALETTE_ENTRY_MAP__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_SEGMENT_TYPE_PALETTE_ENTRY_MAP__VALUE = 1;

	/**
	 * The number of structural features of the '<em>String To Segment Type Palette Entry Map</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_SEGMENT_TYPE_PALETTE_ENTRY_MAP_FEATURE_COUNT = 2;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_SUB_APPLICATION_TYPE_PALETTE_ENTRY_MAP__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_SUB_APPLICATION_TYPE_PALETTE_ENTRY_MAP__VALUE = 1;

	/**
	 * The number of structural features of the '<em>String To Sub Application Type Palette Entry Map</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_SUB_APPLICATION_TYPE_PALETTE_ENTRY_MAP_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.Palette.impl.PaletteEntryImpl <em>Entry</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.PaletteEntryImpl
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getPaletteEntry()
	 * @generated
	 */
	int PALETTE_ENTRY = 7;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_ENTRY__LABEL = 0;

	/**
	 * The feature id for the '<em><b>File</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_ENTRY__FILE = 1;

	/**
	 * The feature id for the '<em><b>Last Modification Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_ENTRY__LAST_MODIFICATION_TIMESTAMP = 2;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_ENTRY__TYPE = 3;

	/**
	 * The feature id for the '<em><b>Palette</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PALETTE_ENTRY__PALETTE = 4;

	/**
	 * The number of structural features of the '<em>Entry</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PALETTE_ENTRY_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.Palette.impl.FBTypePaletteEntryImpl <em>FB Type Palette Entry</em>}' class.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.FBTypePaletteEntryImpl
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getFBTypePaletteEntry()
	 * @generated
	 */
	int FB_TYPE_PALETTE_ENTRY = 11;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.Palette.impl.DeviceTypePaletteEntryImpl <em>Device Type Palette Entry</em>}' class.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.DeviceTypePaletteEntryImpl
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getDeviceTypePaletteEntry()
	 * @generated
	 */
	int DEVICE_TYPE_PALETTE_ENTRY = 10;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.Palette.impl.ResourceTypeEntryImpl <em>Resource Type Entry</em>}' class.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.ResourceTypeEntryImpl
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getResourceTypeEntry()
	 * @generated
	 */
	int RESOURCE_TYPE_ENTRY = 12;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.Palette.impl.SegmentTypePaletteEntryImpl <em>Segment Type Palette Entry</em>}' class.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.SegmentTypePaletteEntryImpl
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getSegmentTypePaletteEntry()
	 * @generated
	 */
	int SEGMENT_TYPE_PALETTE_ENTRY = 13;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.Palette.impl.AdapterTypePaletteEntryImpl <em>Adapter Type Palette Entry</em>}' class.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.AdapterTypePaletteEntryImpl
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getAdapterTypePaletteEntry()
	 * @generated
	 */
	int ADAPTER_TYPE_PALETTE_ENTRY = 8;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_TYPE_PALETTE_ENTRY__LABEL = PALETTE_ENTRY__LABEL;

	/**
	 * The feature id for the '<em><b>File</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_TYPE_PALETTE_ENTRY__FILE = PALETTE_ENTRY__FILE;

	/**
	 * The feature id for the '<em><b>Last Modification Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_TYPE_PALETTE_ENTRY__LAST_MODIFICATION_TIMESTAMP = PALETTE_ENTRY__LAST_MODIFICATION_TIMESTAMP;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_TYPE_PALETTE_ENTRY__TYPE = PALETTE_ENTRY__TYPE;

	/**
	 * The feature id for the '<em><b>Palette</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADAPTER_TYPE_PALETTE_ENTRY__PALETTE = PALETTE_ENTRY__PALETTE;

	/**
	 * The number of structural features of the '<em>Adapter Type Palette Entry</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_TYPE_PALETTE_ENTRY_FEATURE_COUNT = PALETTE_ENTRY_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.Palette.impl.DataTypePaletteEntryImpl <em>Data Type Palette Entry</em>}' class.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.DataTypePaletteEntryImpl
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getDataTypePaletteEntry()
	 * @generated
	 */
	int DATA_TYPE_PALETTE_ENTRY = 9;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE_PALETTE_ENTRY__LABEL = PALETTE_ENTRY__LABEL;

	/**
	 * The feature id for the '<em><b>File</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE_PALETTE_ENTRY__FILE = PALETTE_ENTRY__FILE;

	/**
	 * The feature id for the '<em><b>Last Modification Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE_PALETTE_ENTRY__LAST_MODIFICATION_TIMESTAMP = PALETTE_ENTRY__LAST_MODIFICATION_TIMESTAMP;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE_PALETTE_ENTRY__TYPE = PALETTE_ENTRY__TYPE;

	/**
	 * The feature id for the '<em><b>Palette</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE_PALETTE_ENTRY__PALETTE = PALETTE_ENTRY__PALETTE;

	/**
	 * The number of structural features of the '<em>Data Type Palette Entry</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE_PALETTE_ENTRY_FEATURE_COUNT = PALETTE_ENTRY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE_TYPE_PALETTE_ENTRY__LABEL = PALETTE_ENTRY__LABEL;

	/**
	 * The feature id for the '<em><b>File</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE_TYPE_PALETTE_ENTRY__FILE = PALETTE_ENTRY__FILE;

	/**
	 * The feature id for the '<em><b>Last Modification Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE_TYPE_PALETTE_ENTRY__LAST_MODIFICATION_TIMESTAMP = PALETTE_ENTRY__LAST_MODIFICATION_TIMESTAMP;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE_TYPE_PALETTE_ENTRY__TYPE = PALETTE_ENTRY__TYPE;

	/**
	 * The feature id for the '<em><b>Palette</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DEVICE_TYPE_PALETTE_ENTRY__PALETTE = PALETTE_ENTRY__PALETTE;

	/**
	 * The number of structural features of the '<em>Device Type Palette Entry</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE_TYPE_PALETTE_ENTRY_FEATURE_COUNT = PALETTE_ENTRY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_TYPE_PALETTE_ENTRY__LABEL = PALETTE_ENTRY__LABEL;

	/**
	 * The feature id for the '<em><b>File</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_TYPE_PALETTE_ENTRY__FILE = PALETTE_ENTRY__FILE;

	/**
	 * The feature id for the '<em><b>Last Modification Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_TYPE_PALETTE_ENTRY__LAST_MODIFICATION_TIMESTAMP = PALETTE_ENTRY__LAST_MODIFICATION_TIMESTAMP;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_TYPE_PALETTE_ENTRY__TYPE = PALETTE_ENTRY__TYPE;

	/**
	 * The feature id for the '<em><b>Palette</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FB_TYPE_PALETTE_ENTRY__PALETTE = PALETTE_ENTRY__PALETTE;

	/**
	 * The number of structural features of the '<em>FB Type Palette Entry</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_TYPE_PALETTE_ENTRY_FEATURE_COUNT = PALETTE_ENTRY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE_ENTRY__LABEL = PALETTE_ENTRY__LABEL;

	/**
	 * The feature id for the '<em><b>File</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE_ENTRY__FILE = PALETTE_ENTRY__FILE;

	/**
	 * The feature id for the '<em><b>Last Modification Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE_ENTRY__LAST_MODIFICATION_TIMESTAMP = PALETTE_ENTRY__LAST_MODIFICATION_TIMESTAMP;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE_ENTRY__TYPE = PALETTE_ENTRY__TYPE;

	/**
	 * The feature id for the '<em><b>Palette</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE_ENTRY__PALETTE = PALETTE_ENTRY__PALETTE;

	/**
	 * The number of structural features of the '<em>Resource Type Entry</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE_ENTRY_FEATURE_COUNT = PALETTE_ENTRY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEGMENT_TYPE_PALETTE_ENTRY__LABEL = PALETTE_ENTRY__LABEL;

	/**
	 * The feature id for the '<em><b>File</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEGMENT_TYPE_PALETTE_ENTRY__FILE = PALETTE_ENTRY__FILE;

	/**
	 * The feature id for the '<em><b>Last Modification Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEGMENT_TYPE_PALETTE_ENTRY__LAST_MODIFICATION_TIMESTAMP = PALETTE_ENTRY__LAST_MODIFICATION_TIMESTAMP;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEGMENT_TYPE_PALETTE_ENTRY__TYPE = PALETTE_ENTRY__TYPE;

	/**
	 * The feature id for the '<em><b>Palette</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEGMENT_TYPE_PALETTE_ENTRY__PALETTE = PALETTE_ENTRY__PALETTE;

	/**
	 * The number of structural features of the '<em>Segment Type Palette Entry</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEGMENT_TYPE_PALETTE_ENTRY_FEATURE_COUNT = PALETTE_ENTRY_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.Palette.impl.SubApplicationTypePaletteEntryImpl <em>Sub Application Type Palette Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.SubApplicationTypePaletteEntryImpl
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getSubApplicationTypePaletteEntry()
	 * @generated
	 */
	int SUB_APPLICATION_TYPE_PALETTE_ENTRY = 14;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APPLICATION_TYPE_PALETTE_ENTRY__LABEL = PALETTE_ENTRY__LABEL;

	/**
	 * The feature id for the '<em><b>File</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APPLICATION_TYPE_PALETTE_ENTRY__FILE = PALETTE_ENTRY__FILE;

	/**
	 * The feature id for the '<em><b>Last Modification Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APPLICATION_TYPE_PALETTE_ENTRY__LAST_MODIFICATION_TIMESTAMP = PALETTE_ENTRY__LAST_MODIFICATION_TIMESTAMP;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APPLICATION_TYPE_PALETTE_ENTRY__TYPE = PALETTE_ENTRY__TYPE;

	/**
	 * The feature id for the '<em><b>Palette</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUB_APPLICATION_TYPE_PALETTE_ENTRY__PALETTE = PALETTE_ENTRY__PALETTE;

	/**
	 * The number of structural features of the '<em>Sub Application Type Palette Entry</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APPLICATION_TYPE_PALETTE_ENTRY_FEATURE_COUNT = PALETTE_ENTRY_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '<em>IFile</em>' data type.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @see org.eclipse.core.resources.IFile
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getIFile()
	 * @generated
	 */
	int IFILE = 15;

	/**
	 * The meta object id for the '<em>IProject</em>' data type.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @see org.eclipse.core.resources.IProject
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getIProject()
	 * @generated
	 */
	int IPROJECT = 16;

	/**
	 * The meta object id for the '<em>Type Importer</em>' data type. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.fordiac.ide.model.dataimport.TypeImporter
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getTypeImporter()
	 * @generated
	 */
	int TYPE_IMPORTER = 17;

	/**
	 * The meta object id for the '<em>XML Stream Exception</em>' data type. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see javax.xml.stream.XMLStreamException
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getXMLStreamException()
	 * @generated
	 */
	int XML_STREAM_EXCEPTION = 18;

	/**
	 * The meta object id for the '<em>Core Exception</em>' data type. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.core.runtime.CoreException
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getCoreException()
	 * @generated
	 */
	int CORE_EXCEPTION = 19;

	/**
	 * The meta object id for the '<em>Type Library</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getTypeLibrary()
	 * @generated
	 */
	int TYPE_LIBRARY = 20;

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.Palette.Palette <em>Palette</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Palette</em>'.
	 * @see org.eclipse.fordiac.ide.model.Palette.Palette
	 * @generated
	 */
	EClass getPalette();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.Palette.Palette#getProject <em>Project</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Project</em>'.
	 * @see org.eclipse.fordiac.ide.model.Palette.Palette#getProject()
	 * @see #getPalette()
	 * @generated
	 */
	EAttribute getPalette_Project();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.Palette.Palette#getAutomationSystem <em>Automation System</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Automation System</em>'.
	 * @see org.eclipse.fordiac.ide.model.Palette.Palette#getAutomationSystem()
	 * @see #getPalette()
	 * @generated
	 */
	EReference getPalette_AutomationSystem();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.Palette.Palette#getTypeLibrary <em>Type Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type Library</em>'.
	 * @see org.eclipse.fordiac.ide.model.Palette.Palette#getTypeLibrary()
	 * @see #getPalette()
	 * @generated
	 */
	EAttribute getPalette_TypeLibrary();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.fordiac.ide.model.Palette.Palette#getFbTypes <em>Fb Types</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Fb Types</em>'.
	 * @see org.eclipse.fordiac.ide.model.Palette.Palette#getFbTypes()
	 * @see #getPalette()
	 * @generated
	 */
	EReference getPalette_FbTypes();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.fordiac.ide.model.Palette.Palette#getResourceTypes <em>Resource Types</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Resource Types</em>'.
	 * @see org.eclipse.fordiac.ide.model.Palette.Palette#getResourceTypes()
	 * @see #getPalette()
	 * @generated
	 */
	EReference getPalette_ResourceTypes();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.fordiac.ide.model.Palette.Palette#getDeviceTypes <em>Device Types</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Device Types</em>'.
	 * @see org.eclipse.fordiac.ide.model.Palette.Palette#getDeviceTypes()
	 * @see #getPalette()
	 * @generated
	 */
	EReference getPalette_DeviceTypes();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.fordiac.ide.model.Palette.Palette#getSegmentTypes <em>Segment Types</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Segment Types</em>'.
	 * @see org.eclipse.fordiac.ide.model.Palette.Palette#getSegmentTypes()
	 * @see #getPalette()
	 * @generated
	 */
	EReference getPalette_SegmentTypes();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.fordiac.ide.model.Palette.Palette#getSubAppTypes <em>Sub App Types</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Sub App Types</em>'.
	 * @see org.eclipse.fordiac.ide.model.Palette.Palette#getSubAppTypes()
	 * @see #getPalette()
	 * @generated
	 */
	EReference getPalette_SubAppTypes();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.fordiac.ide.model.Palette.Palette#getAdapterTypes <em>Adapter Types</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Adapter Types</em>'.
	 * @see org.eclipse.fordiac.ide.model.Palette.Palette#getAdapterTypes()
	 * @see #getPalette()
	 * @generated
	 */
	EReference getPalette_AdapterTypes();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>String To
	 * FB Type Palette Entry Map</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for class '<em>String To FB Type Palette Entry
	 *         Map</em>'.
	 * @see java.util.Map.Entry
	 * @model keyDataType="org.eclipse.emf.ecore.EString"
	 *        valueType="org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry"
	 * @generated
	 */
	EClass getStringToFBTypePaletteEntryMap();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToFBTypePaletteEntryMap()
	 * @generated
	 */
	EAttribute getStringToFBTypePaletteEntryMap_Key();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToFBTypePaletteEntryMap()
	 * @generated
	 */
	EReference getStringToFBTypePaletteEntryMap_Value();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>String To FDevice Type Palette Entry Map</em>}'.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @return the meta object for class '<em>String To FDevice Type Palette Entry Map</em>'.
	 * @see java.util.Map.Entry
	 * @model keyDataType="org.eclipse.emf.ecore.EString"
	 *        valueType="org.eclipse.fordiac.ide.model.Palette.DeviceTypePaletteEntry"
	 * @generated
	 */
	EClass getStringToFDeviceTypePaletteEntryMap();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToFDeviceTypePaletteEntryMap()
	 * @generated
	 */
	EAttribute getStringToFDeviceTypePaletteEntryMap_Key();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToFDeviceTypePaletteEntryMap()
	 * @generated
	 */
	EReference getStringToFDeviceTypePaletteEntryMap_Value();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>String To Resource Type Entry Map</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>String To Resource Type Entry Map</em>'.
	 * @see java.util.Map.Entry
	 * @model keyDataType="org.eclipse.emf.ecore.EString"
	 *        valueType="org.eclipse.fordiac.ide.model.Palette.ResourceTypeEntry"
	 * @generated
	 */
	EClass getStringToResourceTypeEntryMap();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToResourceTypeEntryMap()
	 * @generated
	 */
	EAttribute getStringToResourceTypeEntryMap_Key();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToResourceTypeEntryMap()
	 * @generated
	 */
	EReference getStringToResourceTypeEntryMap_Value();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>String To Segment Type Palette Entry Map</em>}'.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @return the meta object for class '<em>String To Segment Type Palette Entry Map</em>'.
	 * @see java.util.Map.Entry
	 * @model keyDataType="org.eclipse.emf.ecore.EString"
	 *        valueType="org.eclipse.fordiac.ide.model.Palette.SegmentTypePaletteEntry"
	 * @generated
	 */
	EClass getStringToSegmentTypePaletteEntryMap();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToSegmentTypePaletteEntryMap()
	 * @generated
	 */
	EAttribute getStringToSegmentTypePaletteEntryMap_Key();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToSegmentTypePaletteEntryMap()
	 * @generated
	 */
	EReference getStringToSegmentTypePaletteEntryMap_Value();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>String To Sub Application Type Palette Entry Map</em>}'.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @return the meta object for class '<em>String To Sub Application Type Palette Entry Map</em>'.
	 * @see java.util.Map.Entry
	 * @model keyDataType="org.eclipse.emf.ecore.EString"
	 *        valueType="org.eclipse.fordiac.ide.model.Palette.SubApplicationTypePaletteEntry"
	 * @generated
	 */
	EClass getStringToSubApplicationTypePaletteEntryMap();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToSubApplicationTypePaletteEntryMap()
	 * @generated
	 */
	EAttribute getStringToSubApplicationTypePaletteEntryMap_Key();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToSubApplicationTypePaletteEntryMap()
	 * @generated
	 */
	EReference getStringToSubApplicationTypePaletteEntryMap_Value();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>String To Adapter Type Palette Entry Map</em>}'.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @return the meta object for class '<em>String To Adapter Type Palette Entry Map</em>'.
	 * @see java.util.Map.Entry
	 * @model keyDataType="org.eclipse.emf.ecore.EString"
	 *        valueType="org.eclipse.fordiac.ide.model.Palette.AdapterTypePaletteEntry"
	 * @generated
	 */
	EClass getStringToAdapterTypePaletteEntryMap();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToAdapterTypePaletteEntryMap()
	 * @generated
	 */
	EAttribute getStringToAdapterTypePaletteEntryMap_Key();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToAdapterTypePaletteEntryMap()
	 * @generated
	 */
	EReference getStringToAdapterTypePaletteEntryMap_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry <em>FB Type Palette Entry</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>FB Type Palette Entry</em>'.
	 * @see org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry
	 * @generated
	 */
	EClass getFBTypePaletteEntry();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.Palette.PaletteEntry <em>Entry</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Entry</em>'.
	 * @see org.eclipse.fordiac.ide.model.Palette.PaletteEntry
	 * @generated
	 */
	EClass getPaletteEntry();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.Palette.PaletteEntry#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see org.eclipse.fordiac.ide.model.Palette.PaletteEntry#getLabel()
	 * @see #getPaletteEntry()
	 * @generated
	 */
	EAttribute getPaletteEntry_Label();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.Palette.PaletteEntry#getFile <em>File</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>File</em>'.
	 * @see org.eclipse.fordiac.ide.model.Palette.PaletteEntry#getFile()
	 * @see #getPaletteEntry()
	 * @generated
	 */
	EAttribute getPaletteEntry_File();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.Palette.PaletteEntry#getLastModificationTimestamp <em>Last Modification Timestamp</em>}'.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @return the meta object for the attribute '<em>Last Modification Timestamp</em>'.
	 * @see org.eclipse.fordiac.ide.model.Palette.PaletteEntry#getLastModificationTimestamp()
	 * @see #getPaletteEntry()
	 * @generated
	 */
	EAttribute getPaletteEntry_LastModificationTimestamp();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.Palette.PaletteEntry#getType <em>Type</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.Palette.PaletteEntry#getType()
	 * @see #getPaletteEntry()
	 * @generated
	 */
	EReference getPaletteEntry_Type();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.Palette.PaletteEntry#getPalette <em>Palette</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Palette</em>'.
	 * @see org.eclipse.fordiac.ide.model.Palette.PaletteEntry#getPalette()
	 * @see #getPaletteEntry()
	 * @generated
	 */
	EReference getPaletteEntry_Palette();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.Palette.DeviceTypePaletteEntry <em>Device Type Palette Entry</em>}'.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @return the meta object for class '<em>Device Type Palette Entry</em>'.
	 * @see org.eclipse.fordiac.ide.model.Palette.DeviceTypePaletteEntry
	 * @generated
	 */
	EClass getDeviceTypePaletteEntry();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.Palette.ResourceTypeEntry <em>Resource Type Entry</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Resource Type Entry</em>'.
	 * @see org.eclipse.fordiac.ide.model.Palette.ResourceTypeEntry
	 * @generated
	 */
	EClass getResourceTypeEntry();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.Palette.SegmentTypePaletteEntry <em>Segment Type Palette Entry</em>}'.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @return the meta object for class '<em>Segment Type Palette Entry</em>'.
	 * @see org.eclipse.fordiac.ide.model.Palette.SegmentTypePaletteEntry
	 * @generated
	 */
	EClass getSegmentTypePaletteEntry();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.Palette.AdapterTypePaletteEntry <em>Adapter Type Palette Entry</em>}'.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @return the meta object for class '<em>Adapter Type Palette Entry</em>'.
	 * @see org.eclipse.fordiac.ide.model.Palette.AdapterTypePaletteEntry
	 * @generated
	 */
	EClass getAdapterTypePaletteEntry();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.Palette.DataTypePaletteEntry <em>Data Type Palette Entry</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Data Type Palette Entry</em>'.
	 * @see org.eclipse.fordiac.ide.model.Palette.DataTypePaletteEntry
	 * @generated
	 */
	EClass getDataTypePaletteEntry();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.Palette.SubApplicationTypePaletteEntry <em>Sub Application Type Palette Entry</em>}'.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @return the meta object for class '<em>Sub Application Type Palette Entry</em>'.
	 * @see org.eclipse.fordiac.ide.model.Palette.SubApplicationTypePaletteEntry
	 * @generated
	 */
	EClass getSubApplicationTypePaletteEntry();

	/**
	 * Returns the meta object for data type
	 * '{@link org.eclipse.core.resources.IFile <em>IFile</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for data type '<em>IFile</em>'.
	 * @see org.eclipse.core.resources.IFile
	 * @model instanceClass="org.eclipse.core.resources.IFile"
	 * @generated
	 */
	EDataType getIFile();

	/**
	 * Returns the meta object for data type
	 * '{@link org.eclipse.core.resources.IProject <em>IProject</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for data type '<em>IProject</em>'.
	 * @see org.eclipse.core.resources.IProject
	 * @model instanceClass="org.eclipse.core.resources.IProject"
	 * @generated
	 */
	EDataType getIProject();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.fordiac.ide.model.dataimport.TypeImporter <em>Type Importer</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Type Importer</em>'.
	 * @see org.eclipse.fordiac.ide.model.dataimport.TypeImporter
	 * @model instanceClass="org.eclipse.fordiac.ide.model.dataimport.TypeImporter"
	 * @generated
	 */
	EDataType getTypeImporter();

	/**
	 * Returns the meta object for data type '{@link javax.xml.stream.XMLStreamException <em>XML Stream Exception</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for data type '<em>XML Stream Exception</em>'.
	 * @see javax.xml.stream.XMLStreamException
	 * @model instanceClass="javax.xml.stream.XMLStreamException"
	 * @generated
	 */
	EDataType getXMLStreamException();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.core.runtime.CoreException <em>Core Exception</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Core Exception</em>'.
	 * @see org.eclipse.core.runtime.CoreException
	 * @model instanceClass="org.eclipse.core.runtime.CoreException"
	 * @generated
	 */
	EDataType getCoreException();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary <em>Type Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Type Library</em>'.
	 * @see org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary
	 * @model instanceClass="org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary"
	 * @generated
	 */
	EDataType getTypeLibrary();

	/**
	 * Returns the factory that creates the instances of the model. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	PaletteFactory getPaletteFactory();

	/**
	 * <!-- begin-user-doc --> Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.Palette.impl.PaletteImpl <em>Palette</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.PaletteImpl
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getPalette()
		 * @generated
		 */
		EClass PALETTE = eINSTANCE.getPalette();

		/**
		 * The meta object literal for the '<em><b>Project</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PALETTE__PROJECT = eINSTANCE.getPalette_Project();

		/**
		 * The meta object literal for the '<em><b>Automation System</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference PALETTE__AUTOMATION_SYSTEM = eINSTANCE.getPalette_AutomationSystem();

		/**
		 * The meta object literal for the '<em><b>Type Library</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PALETTE__TYPE_LIBRARY = eINSTANCE.getPalette_TypeLibrary();

		/**
		 * The meta object literal for the '<em><b>Fb Types</b></em>' map feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PALETTE__FB_TYPES = eINSTANCE.getPalette_FbTypes();

		/**
		 * The meta object literal for the '<em><b>Resource Types</b></em>' map feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference PALETTE__RESOURCE_TYPES = eINSTANCE.getPalette_ResourceTypes();

		/**
		 * The meta object literal for the '<em><b>Device Types</b></em>' map feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference PALETTE__DEVICE_TYPES = eINSTANCE.getPalette_DeviceTypes();

		/**
		 * The meta object literal for the '<em><b>Segment Types</b></em>' map feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference PALETTE__SEGMENT_TYPES = eINSTANCE.getPalette_SegmentTypes();

		/**
		 * The meta object literal for the '<em><b>Sub App Types</b></em>' map feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference PALETTE__SUB_APP_TYPES = eINSTANCE.getPalette_SubAppTypes();

		/**
		 * The meta object literal for the '<em><b>Adapter Types</b></em>' map feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference PALETTE__ADAPTER_TYPES = eINSTANCE.getPalette_AdapterTypes();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.Palette.impl.StringToFBTypePaletteEntryMapImpl <em>String To FB Type Palette Entry Map</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.StringToFBTypePaletteEntryMapImpl
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getStringToFBTypePaletteEntryMap()
		 * @generated
		 */
		EClass STRING_TO_FB_TYPE_PALETTE_ENTRY_MAP = eINSTANCE.getStringToFBTypePaletteEntryMap();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute STRING_TO_FB_TYPE_PALETTE_ENTRY_MAP__KEY = eINSTANCE.getStringToFBTypePaletteEntryMap_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference STRING_TO_FB_TYPE_PALETTE_ENTRY_MAP__VALUE = eINSTANCE.getStringToFBTypePaletteEntryMap_Value();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.fordiac.ide.model.Palette.impl.StringToFDeviceTypePaletteEntryMapImpl
		 * <em>String To FDevice Type Palette Entry Map</em>}' class. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.StringToFDeviceTypePaletteEntryMapImpl
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getStringToFDeviceTypePaletteEntryMap()
		 * @generated
		 */
		EClass STRING_TO_FDEVICE_TYPE_PALETTE_ENTRY_MAP = eINSTANCE.getStringToFDeviceTypePaletteEntryMap();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute STRING_TO_FDEVICE_TYPE_PALETTE_ENTRY_MAP__KEY = eINSTANCE.getStringToFDeviceTypePaletteEntryMap_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference STRING_TO_FDEVICE_TYPE_PALETTE_ENTRY_MAP__VALUE = eINSTANCE.getStringToFDeviceTypePaletteEntryMap_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.Palette.impl.StringToResourceTypeEntryMapImpl <em>String To Resource Type Entry Map</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.StringToResourceTypeEntryMapImpl
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getStringToResourceTypeEntryMap()
		 * @generated
		 */
		EClass STRING_TO_RESOURCE_TYPE_ENTRY_MAP = eINSTANCE.getStringToResourceTypeEntryMap();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute STRING_TO_RESOURCE_TYPE_ENTRY_MAP__KEY = eINSTANCE.getStringToResourceTypeEntryMap_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference STRING_TO_RESOURCE_TYPE_ENTRY_MAP__VALUE = eINSTANCE.getStringToResourceTypeEntryMap_Value();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.fordiac.ide.model.Palette.impl.StringToSegmentTypePaletteEntryMapImpl
		 * <em>String To Segment Type Palette Entry Map</em>}' class. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.StringToSegmentTypePaletteEntryMapImpl
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getStringToSegmentTypePaletteEntryMap()
		 * @generated
		 */
		EClass STRING_TO_SEGMENT_TYPE_PALETTE_ENTRY_MAP = eINSTANCE.getStringToSegmentTypePaletteEntryMap();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute STRING_TO_SEGMENT_TYPE_PALETTE_ENTRY_MAP__KEY = eINSTANCE.getStringToSegmentTypePaletteEntryMap_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference STRING_TO_SEGMENT_TYPE_PALETTE_ENTRY_MAP__VALUE = eINSTANCE.getStringToSegmentTypePaletteEntryMap_Value();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.fordiac.ide.model.Palette.impl.StringToSubApplicationTypePaletteEntryMapImpl
		 * <em>String To Sub Application Type Palette Entry Map</em>}' class. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.StringToSubApplicationTypePaletteEntryMapImpl
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getStringToSubApplicationTypePaletteEntryMap()
		 * @generated
		 */
		EClass STRING_TO_SUB_APPLICATION_TYPE_PALETTE_ENTRY_MAP = eINSTANCE.getStringToSubApplicationTypePaletteEntryMap();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute STRING_TO_SUB_APPLICATION_TYPE_PALETTE_ENTRY_MAP__KEY = eINSTANCE.getStringToSubApplicationTypePaletteEntryMap_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference STRING_TO_SUB_APPLICATION_TYPE_PALETTE_ENTRY_MAP__VALUE = eINSTANCE.getStringToSubApplicationTypePaletteEntryMap_Value();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.fordiac.ide.model.Palette.impl.StringToAdapterTypePaletteEntryMapImpl
		 * <em>String To Adapter Type Palette Entry Map</em>}' class. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.StringToAdapterTypePaletteEntryMapImpl
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getStringToAdapterTypePaletteEntryMap()
		 * @generated
		 */
		EClass STRING_TO_ADAPTER_TYPE_PALETTE_ENTRY_MAP = eINSTANCE.getStringToAdapterTypePaletteEntryMap();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute STRING_TO_ADAPTER_TYPE_PALETTE_ENTRY_MAP__KEY = eINSTANCE.getStringToAdapterTypePaletteEntryMap_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference STRING_TO_ADAPTER_TYPE_PALETTE_ENTRY_MAP__VALUE = eINSTANCE.getStringToAdapterTypePaletteEntryMap_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.Palette.impl.FBTypePaletteEntryImpl <em>FB Type Palette Entry</em>}' class.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.FBTypePaletteEntryImpl
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getFBTypePaletteEntry()
		 * @generated
		 */
		EClass FB_TYPE_PALETTE_ENTRY = eINSTANCE.getFBTypePaletteEntry();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.Palette.impl.PaletteEntryImpl <em>Entry</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.PaletteEntryImpl
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getPaletteEntry()
		 * @generated
		 */
		EClass PALETTE_ENTRY = eINSTANCE.getPaletteEntry();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PALETTE_ENTRY__LABEL = eINSTANCE.getPaletteEntry_Label();

		/**
		 * The meta object literal for the '<em><b>File</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PALETTE_ENTRY__FILE = eINSTANCE.getPaletteEntry_File();

		/**
		 * The meta object literal for the '<em><b>Last Modification Timestamp</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PALETTE_ENTRY__LAST_MODIFICATION_TIMESTAMP = eINSTANCE.getPaletteEntry_LastModificationTimestamp();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference PALETTE_ENTRY__TYPE = eINSTANCE.getPaletteEntry_Type();

		/**
		 * The meta object literal for the '<em><b>Palette</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference PALETTE_ENTRY__PALETTE = eINSTANCE.getPaletteEntry_Palette();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.Palette.impl.DeviceTypePaletteEntryImpl <em>Device Type Palette Entry</em>}' class.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.DeviceTypePaletteEntryImpl
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getDeviceTypePaletteEntry()
		 * @generated
		 */
		EClass DEVICE_TYPE_PALETTE_ENTRY = eINSTANCE.getDeviceTypePaletteEntry();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.Palette.impl.ResourceTypeEntryImpl <em>Resource Type Entry</em>}' class.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.ResourceTypeEntryImpl
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getResourceTypeEntry()
		 * @generated
		 */
		EClass RESOURCE_TYPE_ENTRY = eINSTANCE.getResourceTypeEntry();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.Palette.impl.SegmentTypePaletteEntryImpl <em>Segment Type Palette Entry</em>}' class.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.SegmentTypePaletteEntryImpl
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getSegmentTypePaletteEntry()
		 * @generated
		 */
		EClass SEGMENT_TYPE_PALETTE_ENTRY = eINSTANCE.getSegmentTypePaletteEntry();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.Palette.impl.AdapterTypePaletteEntryImpl <em>Adapter Type Palette Entry</em>}' class.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.AdapterTypePaletteEntryImpl
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getAdapterTypePaletteEntry()
		 * @generated
		 */
		EClass ADAPTER_TYPE_PALETTE_ENTRY = eINSTANCE.getAdapterTypePaletteEntry();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.Palette.impl.DataTypePaletteEntryImpl <em>Data Type Palette Entry</em>}' class.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.DataTypePaletteEntryImpl
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getDataTypePaletteEntry()
		 * @generated
		 */
		EClass DATA_TYPE_PALETTE_ENTRY = eINSTANCE.getDataTypePaletteEntry();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.Palette.impl.SubApplicationTypePaletteEntryImpl <em>Sub Application Type Palette Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.SubApplicationTypePaletteEntryImpl
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getSubApplicationTypePaletteEntry()
		 * @generated
		 */
		EClass SUB_APPLICATION_TYPE_PALETTE_ENTRY = eINSTANCE.getSubApplicationTypePaletteEntry();

		/**
		 * The meta object literal for the '<em>IFile</em>' data type. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.core.resources.IFile
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getIFile()
		 * @generated
		 */
		EDataType IFILE = eINSTANCE.getIFile();

		/**
		 * The meta object literal for the '<em>IProject</em>' data type. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.core.resources.IProject
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getIProject()
		 * @generated
		 */
		EDataType IPROJECT = eINSTANCE.getIProject();

		/**
		 * The meta object literal for the '<em>Type Importer</em>' data type. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.fordiac.ide.model.dataimport.TypeImporter
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getTypeImporter()
		 * @generated
		 */
		EDataType TYPE_IMPORTER = eINSTANCE.getTypeImporter();

		/**
		 * The meta object literal for the '<em>XML Stream Exception</em>' data type.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see javax.xml.stream.XMLStreamException
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getXMLStreamException()
		 * @generated
		 */
		EDataType XML_STREAM_EXCEPTION = eINSTANCE.getXMLStreamException();

		/**
		 * The meta object literal for the '<em>Core Exception</em>' data type. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.core.runtime.CoreException
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getCoreException()
		 * @generated
		 */
		EDataType CORE_EXCEPTION = eINSTANCE.getCoreException();

		/**
		 * The meta object literal for the '<em>Type Library</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getTypeLibrary()
		 * @generated
		 */
		EDataType TYPE_LIBRARY = eINSTANCE.getTypeLibrary();

	}

} // PalettePackage
