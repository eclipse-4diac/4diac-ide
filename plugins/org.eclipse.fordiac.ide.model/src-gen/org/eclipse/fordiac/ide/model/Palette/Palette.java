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

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Palette</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.model.Palette.Palette#getProject
 * <em>Project</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.Palette.Palette#getAutomationSystem
 * <em>Automation System</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.Palette.Palette#getAdapterTypes
 * <em>Adapter Types</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.Palette.Palette#getDeviceTypes
 * <em>Device Types</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.Palette.Palette#getFbTypes <em>Fb
 * Types</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.Palette.Palette#getResourceTypes
 * <em>Resource Types</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.Palette.Palette#getSegmentTypes
 * <em>Segment Types</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.Palette.Palette#getSubAppTypes
 * <em>Sub App Types</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.Palette.PalettePackage#getPalette()
 * @model
 * @generated
 */
public interface Palette extends EObject {
	/**
	 * Returns the value of the '<em><b>Project</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Project</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Project</em>' attribute.
	 * @see #setProject(IProject)
	 * @see org.eclipse.fordiac.ide.model.Palette.PalettePackage#getPalette_Project()
	 * @model dataType="org.eclipse.fordiac.ide.model.Palette.IProject"
	 * @generated
	 */
	IProject getProject();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.fordiac.ide.model.Palette.Palette#getProject
	 * <em>Project</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Project</em>' attribute.
	 * @see #getProject()
	 * @generated
	 */
	void setProject(IProject value);

	/**
	 * Returns the value of the '<em><b>Automation System</b></em>' reference. It is
	 * bidirectional and its opposite is
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem#getPalette
	 * <em>Palette</em>}'. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Automation System</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Automation System</em>' reference.
	 * @see #setAutomationSystem(AutomationSystem)
	 * @see org.eclipse.fordiac.ide.model.Palette.PalettePackage#getPalette_AutomationSystem()
	 * @see org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem#getPalette
	 * @model opposite="palette"
	 * @generated
	 */
	AutomationSystem getAutomationSystem();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.fordiac.ide.model.Palette.Palette#getAutomationSystem
	 * <em>Automation System</em>}' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Automation System</em>' reference.
	 * @see #getAutomationSystem()
	 * @generated
	 */
	void setAutomationSystem(AutomationSystem value);

	/**
	 * Returns the value of the '<em><b>Fb Types</b></em>' map. The key is of type
	 * {@link java.lang.String}, and the value is of type
	 * {@link org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry}, <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Fb Types</em>' map.
	 * @see org.eclipse.fordiac.ide.model.Palette.PalettePackage#getPalette_FbTypes()
	 * @model mapType="org.eclipse.fordiac.ide.model.Palette.StringToFBTypePaletteEntryMap&lt;org.eclipse.emf.ecore.EString,
	 *        org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry&gt;"
	 * @generated
	 */
	EMap<String, FBTypePaletteEntry> getFbTypes();

	/**
	 * Returns the value of the '<em><b>Resource Types</b></em>' map. The key is of
	 * type {@link java.lang.String}, and the value is of type
	 * {@link org.eclipse.fordiac.ide.model.Palette.ResourceTypeEntry}, <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Resource Types</em>' map.
	 * @see org.eclipse.fordiac.ide.model.Palette.PalettePackage#getPalette_ResourceTypes()
	 * @model mapType="org.eclipse.fordiac.ide.model.Palette.StringToResourceTypeEntryMap&lt;org.eclipse.emf.ecore.EString,
	 *        org.eclipse.fordiac.ide.model.Palette.ResourceTypeEntry&gt;"
	 * @generated
	 */
	EMap<String, ResourceTypeEntry> getResourceTypes();

	/**
	 * Returns the value of the '<em><b>Device Types</b></em>' map. The key is of
	 * type {@link java.lang.String}, and the value is of type
	 * {@link org.eclipse.fordiac.ide.model.Palette.DeviceTypePaletteEntry}, <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Device Types</em>' map.
	 * @see org.eclipse.fordiac.ide.model.Palette.PalettePackage#getPalette_DeviceTypes()
	 * @model mapType="org.eclipse.fordiac.ide.model.Palette.StringToFDeviceTypePaletteEntryMap&lt;org.eclipse.emf.ecore.EString,
	 *        org.eclipse.fordiac.ide.model.Palette.DeviceTypePaletteEntry&gt;"
	 * @generated
	 */
	EMap<String, DeviceTypePaletteEntry> getDeviceTypes();

	/**
	 * Returns the value of the '<em><b>Segment Types</b></em>' map. The key is of
	 * type {@link java.lang.String}, and the value is of type
	 * {@link org.eclipse.fordiac.ide.model.Palette.SegmentTypePaletteEntry}, <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Segment Types</em>' map.
	 * @see org.eclipse.fordiac.ide.model.Palette.PalettePackage#getPalette_SegmentTypes()
	 * @model mapType="org.eclipse.fordiac.ide.model.Palette.StringToSegmentTypePaletteEntryMap&lt;org.eclipse.emf.ecore.EString,
	 *        org.eclipse.fordiac.ide.model.Palette.SegmentTypePaletteEntry&gt;"
	 * @generated
	 */
	EMap<String, SegmentTypePaletteEntry> getSegmentTypes();

	/**
	 * Returns the value of the '<em><b>Sub App Types</b></em>' map. The key is of
	 * type {@link java.lang.String}, and the value is of type
	 * {@link org.eclipse.fordiac.ide.model.Palette.SubApplicationTypePaletteEntry},
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Sub App Types</em>' map.
	 * @see org.eclipse.fordiac.ide.model.Palette.PalettePackage#getPalette_SubAppTypes()
	 * @model mapType="org.eclipse.fordiac.ide.model.Palette.StringToSubApplicationTypePaletteEntryMap&lt;org.eclipse.emf.ecore.EString,
	 *        org.eclipse.fordiac.ide.model.Palette.SubApplicationTypePaletteEntry&gt;"
	 * @generated
	 */
	EMap<String, SubApplicationTypePaletteEntry> getSubAppTypes();

	/**
	 * Returns the value of the '<em><b>Adapter Types</b></em>' map. The key is of
	 * type {@link java.lang.String}, and the value is of type
	 * {@link org.eclipse.fordiac.ide.model.Palette.AdapterTypePaletteEntry}, <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Adapter Types</em>' map.
	 * @see org.eclipse.fordiac.ide.model.Palette.PalettePackage#getPalette_AdapterTypes()
	 * @model mapType="org.eclipse.fordiac.ide.model.Palette.StringToAdapterTypePaletteEntryMap&lt;org.eclipse.emf.ecore.EString,
	 *        org.eclipse.fordiac.ide.model.Palette.AdapterTypePaletteEntry&gt;"
	 * @generated
	 */
	EMap<String, AdapterTypePaletteEntry> getAdapterTypes();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model kind="operation"
	 * @generated
	 */
	EList<AdapterTypePaletteEntry> getAdapterTypesSorted();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model required="true" typeNameRequired="true"
	 * @generated
	 */
	AdapterTypePaletteEntry getAdapterTypeEntry(String typeName);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model required="true" typeNameRequired="true"
	 * @generated
	 */
	DeviceTypePaletteEntry getDeviceTypeEntry(String typeName);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model required="true" typeNameRequired="true"
	 * @generated
	 */
	FBTypePaletteEntry getFBTypeEntry(String typeName);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model required="true" typeNameRequired="true"
	 * @generated
	 */
	ResourceTypeEntry getResourceTypeEntry(String typeName);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model required="true" typeNameRequired="true"
	 * @generated
	 */
	SegmentTypePaletteEntry getSegmentTypeEntry(String typeName);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model required="true" typeNameRequired="true"
	 * @generated
	 */
	SubApplicationTypePaletteEntry getSubAppTypeEntry(String typeName);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model entryRequired="true"
	 * @generated
	 */
	void addPaletteEntry(PaletteEntry entry);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model entryRequired="true"
	 * @generated
	 */
	void removePaletteEntry(PaletteEntry entry);

} // Palette
