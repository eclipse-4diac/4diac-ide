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

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Palette</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.Palette.Palette#getRootGroup <em>Root Group</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.Palette.Palette#getProject <em>Project</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.Palette.Palette#getAutomationSystem <em>Automation System</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.Palette.PalettePackage#getPalette()
 * @model
 * @generated
 */
public interface Palette extends EObject {
	/**
	 * Returns the value of the '<em><b>Root Group</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Root Group</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Root Group</em>' containment reference.
	 * @see #setRootGroup(PaletteGroup)
	 * @see org.eclipse.fordiac.ide.model.Palette.PalettePackage#getPalette_RootGroup()
	 * @model containment="true" resolveProxies="true" required="true"
	 * @generated
	 */
	PaletteGroup getRootGroup();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.Palette.Palette#getRootGroup <em>Root Group</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Root Group</em>' containment reference.
	 * @see #getRootGroup()
	 * @generated
	 */
	void setRootGroup(PaletteGroup value);

	/**
	 * Returns the value of the '<em><b>Project</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Project</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Project</em>' attribute.
	 * @see #setProject(IProject)
	 * @see org.eclipse.fordiac.ide.model.Palette.PalettePackage#getPalette_Project()
	 * @model dataType="org.eclipse.fordiac.ide.model.Palette.IProject"
	 * @generated
	 */
	IProject getProject();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.Palette.Palette#getProject <em>Project</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Project</em>' attribute.
	 * @see #getProject()
	 * @generated
	 */
	void setProject(IProject value);

	/**
	 * Returns the value of the '<em><b>Automation System</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem#getPalette <em>Palette</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Automation System</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Automation System</em>' reference.
	 * @see #setAutomationSystem(AutomationSystem)
	 * @see org.eclipse.fordiac.ide.model.Palette.PalettePackage#getPalette_AutomationSystem()
	 * @see org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem#getPalette
	 * @model opposite="palette"
	 * @generated
	 */
	AutomationSystem getAutomationSystem();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.Palette.Palette#getAutomationSystem <em>Automation System</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Automation System</em>' reference.
	 * @see #getAutomationSystem()
	 * @generated
	 */
	void setAutomationSystem(AutomationSystem value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model required="true" typeNameRequired="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='PaletteEntry entry = null;\n//TODO reconsider when namespaces are coming how to retrieve the type\nList&lt;PaletteEntry&gt; entries = getTypeEntries(typeName);\nif (!entries.isEmpty()) {\n\tentry = entries.get(0);\n} \nreturn entry;'"
	 * @generated
	 */
	PaletteEntry getTypeEntry(String typeName);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model required="true" adapterTypeNameRequired="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return org.eclipse.fordiac.ide.model.annotations.PaletteAnnotations.getAdapterTypeEntry(this, adapterTypeName);'"
	 * @generated
	 */
	AdapterTypePaletteEntry getAdapterTypeEntry(String adapterTypeName);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return org.eclipse.fordiac.ide.model.annotations.PaletteAnnotations.getAdapterTypes(this);'"
	 * @generated
	 */
	EList<AdapterTypePaletteEntry> getAdapterTypes();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return org.eclipse.fordiac.ide.model.annotations.PaletteAnnotations.getAdapterTypesSorted(this);'"
	 * @generated
	 */
	EList<AdapterTypePaletteEntry> getAdapterTypesSorted();

	public PaletteEntry createFBTypeEntry(IFile file, PaletteGroup parent);
	public PaletteEntry createDeviceEntry(IFile file, PaletteGroup parent);
	public PaletteEntry createResourceTypeEntry(IFile file, PaletteGroup parent);
	public PaletteEntry createSegmentTypeEntry(IFile file, PaletteGroup parent);
	public PaletteEntry createAdapterEntry(IFile file, PaletteGroup parent);
	public PaletteEntry createSubApplicationEntry(IFile file, PaletteGroup parent);
		
	public List<FBType> getFBTypes(String typeName);
	public List<PaletteEntry> getTypeEntries(String typeName);
	public PaletteEntry getTypeEntryForPath(String typePath, String typeEnding);
	public PaletteGroup getGroup(List<String> path, boolean createDir);
	
	public PaletteGroup findGroup(final List<String> path);
	
	/**
	 * returns a list with all containing subgroups starting at pGroup
	 * @param the starting group 
	 * @return all subgroups of pGroup (pGroup is not in the list!)
	 */
	public List<PaletteGroup> getAllGroups(PaletteGroup pGroup);
	

	/**
	 * returns a list with all parents
	 * @param pGroup the group where to start
	 * @return all direct and indirect parents
	 */
	public List<PaletteGroup> getAllParentGroups(PaletteGroup pGroup);
	
	/**
	 * Removes all the groups (including all subgroups) specified in pGroups
	 * @param pGroups the groups to be removed
	 * @return true if the groups were removed
	 */
	public boolean removeGroups(List<PaletteGroup>pGroups);
	
	public PaletteGroup createGroup(PaletteGroup parent, String groupName);

	public PaletteGroup createGroupWithFolder(PaletteGroup parent, String groupName);

	
} // Palette
