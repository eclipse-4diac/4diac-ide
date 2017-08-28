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
package org.eclipse.fordiac.ide.model.Palette;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.fordiac.ide.model.Palette.PaletteFactory
 * @model kind="package"
 * @generated
 */
public interface PalettePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "Palette";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "org.eclipse.fordiac.ide.model.Palette";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "Palette";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	PalettePackage eINSTANCE = org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.Palette.impl.PaletteImpl <em>Palette</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.PaletteImpl
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getPalette()
	 * @generated
	 */
	int PALETTE = 0;

	/**
	 * The feature id for the '<em><b>Root Group</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE__ROOT_GROUP = 0;

	/**
	 * The feature id for the '<em><b>Project</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE__PROJECT = 1;

	/**
	 * The feature id for the '<em><b>Automation System</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE__AUTOMATION_SYSTEM = 2;

	/**
	 * The number of structural features of the '<em>Palette</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.Palette.impl.PaletteGroupImpl <em>Group</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.PaletteGroupImpl
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getPaletteGroup()
	 * @generated
	 */
	int PALETTE_GROUP = 1;

	/**
	 * The feature id for the '<em><b>Entries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_GROUP__ENTRIES = 0;

	/**
	 * The feature id for the '<em><b>Sub Groups</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_GROUP__SUB_GROUPS = 1;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_GROUP__LABEL = 2;

	/**
	 * The number of structural features of the '<em>Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_GROUP_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.Palette.impl.PaletteEntryImpl <em>Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.PaletteEntryImpl
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getPaletteEntry()
	 * @generated
	 */
	int PALETTE_ENTRY = 3;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_ENTRY__LABEL = 0;

	/**
	 * The feature id for the '<em><b>File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_ENTRY__FILE = 1;

	/**
	 * The feature id for the '<em><b>Last Modification Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_ENTRY__LAST_MODIFICATION_TIMESTAMP = 2;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_ENTRY__TYPE = 3;

	/**
	 * The number of structural features of the '<em>Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_ENTRY_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.Palette.impl.FBTypePaletteEntryImpl <em>FB Type Palette Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.FBTypePaletteEntryImpl
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getFBTypePaletteEntry()
	 * @generated
	 */
	int FB_TYPE_PALETTE_ENTRY = 2;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_TYPE_PALETTE_ENTRY__LABEL = PALETTE_ENTRY__LABEL;

	/**
	 * The feature id for the '<em><b>File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_TYPE_PALETTE_ENTRY__FILE = PALETTE_ENTRY__FILE;

	/**
	 * The feature id for the '<em><b>Last Modification Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_TYPE_PALETTE_ENTRY__LAST_MODIFICATION_TIMESTAMP = PALETTE_ENTRY__LAST_MODIFICATION_TIMESTAMP;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_TYPE_PALETTE_ENTRY__TYPE = PALETTE_ENTRY__TYPE;

	/**
	 * The number of structural features of the '<em>FB Type Palette Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_TYPE_PALETTE_ENTRY_FEATURE_COUNT = PALETTE_ENTRY_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.Palette.impl.DeviceTypePaletteEntryImpl <em>Device Type Palette Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.DeviceTypePaletteEntryImpl
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getDeviceTypePaletteEntry()
	 * @generated
	 */
	int DEVICE_TYPE_PALETTE_ENTRY = 4;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE_TYPE_PALETTE_ENTRY__LABEL = PALETTE_ENTRY__LABEL;

	/**
	 * The feature id for the '<em><b>File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE_TYPE_PALETTE_ENTRY__FILE = PALETTE_ENTRY__FILE;

	/**
	 * The feature id for the '<em><b>Last Modification Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE_TYPE_PALETTE_ENTRY__LAST_MODIFICATION_TIMESTAMP = PALETTE_ENTRY__LAST_MODIFICATION_TIMESTAMP;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE_TYPE_PALETTE_ENTRY__TYPE = PALETTE_ENTRY__TYPE;

	/**
	 * The number of structural features of the '<em>Device Type Palette Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE_TYPE_PALETTE_ENTRY_FEATURE_COUNT = PALETTE_ENTRY_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.Palette.impl.ResourceTypeEntryImpl <em>Resource Type Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.ResourceTypeEntryImpl
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getResourceTypeEntry()
	 * @generated
	 */
	int RESOURCE_TYPE_ENTRY = 5;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE_ENTRY__LABEL = PALETTE_ENTRY__LABEL;

	/**
	 * The feature id for the '<em><b>File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE_ENTRY__FILE = PALETTE_ENTRY__FILE;

	/**
	 * The feature id for the '<em><b>Last Modification Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE_ENTRY__LAST_MODIFICATION_TIMESTAMP = PALETTE_ENTRY__LAST_MODIFICATION_TIMESTAMP;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE_ENTRY__TYPE = PALETTE_ENTRY__TYPE;

	/**
	 * The number of structural features of the '<em>Resource Type Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE_ENTRY_FEATURE_COUNT = PALETTE_ENTRY_FEATURE_COUNT + 0;


	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.Palette.impl.SegmentTypePaletteEntryImpl <em>Segment Type Palette Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.SegmentTypePaletteEntryImpl
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getSegmentTypePaletteEntry()
	 * @generated
	 */
	int SEGMENT_TYPE_PALETTE_ENTRY = 6;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEGMENT_TYPE_PALETTE_ENTRY__LABEL = PALETTE_ENTRY__LABEL;

	/**
	 * The feature id for the '<em><b>File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEGMENT_TYPE_PALETTE_ENTRY__FILE = PALETTE_ENTRY__FILE;

	/**
	 * The feature id for the '<em><b>Last Modification Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEGMENT_TYPE_PALETTE_ENTRY__LAST_MODIFICATION_TIMESTAMP = PALETTE_ENTRY__LAST_MODIFICATION_TIMESTAMP;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEGMENT_TYPE_PALETTE_ENTRY__TYPE = PALETTE_ENTRY__TYPE;

	/**
	 * The number of structural features of the '<em>Segment Type Palette Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEGMENT_TYPE_PALETTE_ENTRY_FEATURE_COUNT = PALETTE_ENTRY_FEATURE_COUNT + 0;


	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.Palette.impl.AdapterTypePaletteEntryImpl <em>Adapter Type Palette Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.AdapterTypePaletteEntryImpl
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getAdapterTypePaletteEntry()
	 * @generated
	 */
	int ADAPTER_TYPE_PALETTE_ENTRY = 7;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_TYPE_PALETTE_ENTRY__LABEL = PALETTE_ENTRY__LABEL;

	/**
	 * The feature id for the '<em><b>File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_TYPE_PALETTE_ENTRY__FILE = PALETTE_ENTRY__FILE;

	/**
	 * The feature id for the '<em><b>Last Modification Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_TYPE_PALETTE_ENTRY__LAST_MODIFICATION_TIMESTAMP = PALETTE_ENTRY__LAST_MODIFICATION_TIMESTAMP;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_TYPE_PALETTE_ENTRY__TYPE = PALETTE_ENTRY__TYPE;

	/**
	 * The number of structural features of the '<em>Adapter Type Palette Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_TYPE_PALETTE_ENTRY_FEATURE_COUNT = PALETTE_ENTRY_FEATURE_COUNT + 0;


	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.Palette.impl.SubApplicationTypePaletteEntryImpl <em>Sub Application Type Palette Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.SubApplicationTypePaletteEntryImpl
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getSubApplicationTypePaletteEntry()
	 * @generated
	 */
	int SUB_APPLICATION_TYPE_PALETTE_ENTRY = 8;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APPLICATION_TYPE_PALETTE_ENTRY__LABEL = PALETTE_ENTRY__LABEL;

	/**
	 * The feature id for the '<em><b>File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APPLICATION_TYPE_PALETTE_ENTRY__FILE = PALETTE_ENTRY__FILE;

	/**
	 * The feature id for the '<em><b>Last Modification Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APPLICATION_TYPE_PALETTE_ENTRY__LAST_MODIFICATION_TIMESTAMP = PALETTE_ENTRY__LAST_MODIFICATION_TIMESTAMP;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APPLICATION_TYPE_PALETTE_ENTRY__TYPE = PALETTE_ENTRY__TYPE;

	/**
	 * The number of structural features of the '<em>Sub Application Type Palette Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APPLICATION_TYPE_PALETTE_ENTRY_FEATURE_COUNT = PALETTE_ENTRY_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '<em>IFile</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.core.resources.IFile
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getIFile()
	 * @generated
	 */
	int IFILE = 9;


	/**
	 * The meta object id for the '<em>IProject</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.core.resources.IProject
	 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getIProject()
	 * @generated
	 */
	int IPROJECT = 10;


	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.Palette.Palette <em>Palette</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Palette</em>'.
	 * @see org.eclipse.fordiac.ide.model.Palette.Palette
	 * @generated
	 */
	EClass getPalette();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.Palette.Palette#getRootGroup <em>Root Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Root Group</em>'.
	 * @see org.eclipse.fordiac.ide.model.Palette.Palette#getRootGroup()
	 * @see #getPalette()
	 * @generated
	 */
	EReference getPalette_RootGroup();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.Palette.Palette#getProject <em>Project</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Project</em>'.
	 * @see org.eclipse.fordiac.ide.model.Palette.Palette#getProject()
	 * @see #getPalette()
	 * @generated
	 */
	EAttribute getPalette_Project();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.Palette.Palette#getAutomationSystem <em>Automation System</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Automation System</em>'.
	 * @see org.eclipse.fordiac.ide.model.Palette.Palette#getAutomationSystem()
	 * @see #getPalette()
	 * @generated
	 */
	EReference getPalette_AutomationSystem();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.Palette.PaletteGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Group</em>'.
	 * @see org.eclipse.fordiac.ide.model.Palette.PaletteGroup
	 * @generated
	 */
	EClass getPaletteGroup();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.Palette.PaletteGroup#getEntries <em>Entries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Entries</em>'.
	 * @see org.eclipse.fordiac.ide.model.Palette.PaletteGroup#getEntries()
	 * @see #getPaletteGroup()
	 * @generated
	 */
	EReference getPaletteGroup_Entries();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.Palette.PaletteGroup#getSubGroups <em>Sub Groups</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sub Groups</em>'.
	 * @see org.eclipse.fordiac.ide.model.Palette.PaletteGroup#getSubGroups()
	 * @see #getPaletteGroup()
	 * @generated
	 */
	EReference getPaletteGroup_SubGroups();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.Palette.PaletteGroup#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see org.eclipse.fordiac.ide.model.Palette.PaletteGroup#getLabel()
	 * @see #getPaletteGroup()
	 * @generated
	 */
	EAttribute getPaletteGroup_Label();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry <em>FB Type Palette Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>FB Type Palette Entry</em>'.
	 * @see org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry
	 * @generated
	 */
	EClass getFBTypePaletteEntry();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.Palette.PaletteEntry <em>Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Entry</em>'.
	 * @see org.eclipse.fordiac.ide.model.Palette.PaletteEntry
	 * @generated
	 */
	EClass getPaletteEntry();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.Palette.PaletteEntry#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see org.eclipse.fordiac.ide.model.Palette.PaletteEntry#getLabel()
	 * @see #getPaletteEntry()
	 * @generated
	 */
	EAttribute getPaletteEntry_Label();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.Palette.PaletteEntry#getFile <em>File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>File</em>'.
	 * @see org.eclipse.fordiac.ide.model.Palette.PaletteEntry#getFile()
	 * @see #getPaletteEntry()
	 * @generated
	 */
	EAttribute getPaletteEntry_File();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.Palette.PaletteEntry#getLastModificationTimestamp <em>Last Modification Timestamp</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Last Modification Timestamp</em>'.
	 * @see org.eclipse.fordiac.ide.model.Palette.PaletteEntry#getLastModificationTimestamp()
	 * @see #getPaletteEntry()
	 * @generated
	 */
	EAttribute getPaletteEntry_LastModificationTimestamp();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.Palette.PaletteEntry#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.Palette.PaletteEntry#getType()
	 * @see #getPaletteEntry()
	 * @generated
	 */
	EReference getPaletteEntry_Type();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.Palette.DeviceTypePaletteEntry <em>Device Type Palette Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Device Type Palette Entry</em>'.
	 * @see org.eclipse.fordiac.ide.model.Palette.DeviceTypePaletteEntry
	 * @generated
	 */
	EClass getDeviceTypePaletteEntry();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.Palette.ResourceTypeEntry <em>Resource Type Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Resource Type Entry</em>'.
	 * @see org.eclipse.fordiac.ide.model.Palette.ResourceTypeEntry
	 * @generated
	 */
	EClass getResourceTypeEntry();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.Palette.SegmentTypePaletteEntry <em>Segment Type Palette Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Segment Type Palette Entry</em>'.
	 * @see org.eclipse.fordiac.ide.model.Palette.SegmentTypePaletteEntry
	 * @generated
	 */
	EClass getSegmentTypePaletteEntry();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.Palette.AdapterTypePaletteEntry <em>Adapter Type Palette Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Adapter Type Palette Entry</em>'.
	 * @see org.eclipse.fordiac.ide.model.Palette.AdapterTypePaletteEntry
	 * @generated
	 */
	EClass getAdapterTypePaletteEntry();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.Palette.SubApplicationTypePaletteEntry <em>Sub Application Type Palette Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sub Application Type Palette Entry</em>'.
	 * @see org.eclipse.fordiac.ide.model.Palette.SubApplicationTypePaletteEntry
	 * @generated
	 */
	EClass getSubApplicationTypePaletteEntry();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.core.resources.IFile <em>IFile</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>IFile</em>'.
	 * @see org.eclipse.core.resources.IFile
	 * @model instanceClass="org.eclipse.core.resources.IFile"
	 * @generated
	 */
	EDataType getIFile();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.core.resources.IProject <em>IProject</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>IProject</em>'.
	 * @see org.eclipse.core.resources.IProject
	 * @model instanceClass="org.eclipse.core.resources.IProject"
	 * @generated
	 */
	EDataType getIProject();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	PaletteFactory getPaletteFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.Palette.impl.PaletteImpl <em>Palette</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.PaletteImpl
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getPalette()
		 * @generated
		 */
		EClass PALETTE = eINSTANCE.getPalette();

		/**
		 * The meta object literal for the '<em><b>Root Group</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PALETTE__ROOT_GROUP = eINSTANCE.getPalette_RootGroup();

		/**
		 * The meta object literal for the '<em><b>Project</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PALETTE__PROJECT = eINSTANCE.getPalette_Project();

		/**
		 * The meta object literal for the '<em><b>Automation System</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PALETTE__AUTOMATION_SYSTEM = eINSTANCE.getPalette_AutomationSystem();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.Palette.impl.PaletteGroupImpl <em>Group</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.PaletteGroupImpl
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getPaletteGroup()
		 * @generated
		 */
		EClass PALETTE_GROUP = eINSTANCE.getPaletteGroup();

		/**
		 * The meta object literal for the '<em><b>Entries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PALETTE_GROUP__ENTRIES = eINSTANCE.getPaletteGroup_Entries();

		/**
		 * The meta object literal for the '<em><b>Sub Groups</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PALETTE_GROUP__SUB_GROUPS = eINSTANCE.getPaletteGroup_SubGroups();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PALETTE_GROUP__LABEL = eINSTANCE.getPaletteGroup_Label();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.Palette.impl.FBTypePaletteEntryImpl <em>FB Type Palette Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.FBTypePaletteEntryImpl
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getFBTypePaletteEntry()
		 * @generated
		 */
		EClass FB_TYPE_PALETTE_ENTRY = eINSTANCE.getFBTypePaletteEntry();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.Palette.impl.PaletteEntryImpl <em>Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.PaletteEntryImpl
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getPaletteEntry()
		 * @generated
		 */
		EClass PALETTE_ENTRY = eINSTANCE.getPaletteEntry();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PALETTE_ENTRY__LABEL = eINSTANCE.getPaletteEntry_Label();

		/**
		 * The meta object literal for the '<em><b>File</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PALETTE_ENTRY__FILE = eINSTANCE.getPaletteEntry_File();

		/**
		 * The meta object literal for the '<em><b>Last Modification Timestamp</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PALETTE_ENTRY__LAST_MODIFICATION_TIMESTAMP = eINSTANCE.getPaletteEntry_LastModificationTimestamp();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PALETTE_ENTRY__TYPE = eINSTANCE.getPaletteEntry_Type();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.Palette.impl.DeviceTypePaletteEntryImpl <em>Device Type Palette Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.DeviceTypePaletteEntryImpl
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getDeviceTypePaletteEntry()
		 * @generated
		 */
		EClass DEVICE_TYPE_PALETTE_ENTRY = eINSTANCE.getDeviceTypePaletteEntry();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.Palette.impl.ResourceTypeEntryImpl <em>Resource Type Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.ResourceTypeEntryImpl
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getResourceTypeEntry()
		 * @generated
		 */
		EClass RESOURCE_TYPE_ENTRY = eINSTANCE.getResourceTypeEntry();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.Palette.impl.SegmentTypePaletteEntryImpl <em>Segment Type Palette Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.SegmentTypePaletteEntryImpl
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getSegmentTypePaletteEntry()
		 * @generated
		 */
		EClass SEGMENT_TYPE_PALETTE_ENTRY = eINSTANCE.getSegmentTypePaletteEntry();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.Palette.impl.AdapterTypePaletteEntryImpl <em>Adapter Type Palette Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.AdapterTypePaletteEntryImpl
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getAdapterTypePaletteEntry()
		 * @generated
		 */
		EClass ADAPTER_TYPE_PALETTE_ENTRY = eINSTANCE.getAdapterTypePaletteEntry();

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
		 * The meta object literal for the '<em>IFile</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.core.resources.IFile
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getIFile()
		 * @generated
		 */
		EDataType IFILE = eINSTANCE.getIFile();

		/**
		 * The meta object literal for the '<em>IProject</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.core.resources.IProject
		 * @see org.eclipse.fordiac.ide.model.Palette.impl.PalettePackageImpl#getIProject()
		 * @generated
		 */
		EDataType IPROJECT = eINSTANCE.getIProject();

	}

} //PalettePackage
