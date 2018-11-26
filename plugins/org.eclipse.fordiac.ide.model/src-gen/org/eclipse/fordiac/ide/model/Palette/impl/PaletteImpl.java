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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.fordiac.ide.model.Activator;
import org.eclipse.fordiac.ide.model.Palette.AdapterTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.DeviceTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PaletteFactory;
import org.eclipse.fordiac.ide.model.Palette.PaletteGroup;
import org.eclipse.fordiac.ide.model.Palette.PalettePackage;
import org.eclipse.fordiac.ide.model.Palette.ResourceTypeEntry;
import org.eclipse.fordiac.ide.model.Palette.SegmentTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.SubApplicationTypePaletteEntry;
import org.eclipse.fordiac.ide.model.dataimport.ImportUtils;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Palette</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.Palette.impl.PaletteImpl#getRootGroup <em>Root Group</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.Palette.impl.PaletteImpl#getProject <em>Project</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.Palette.impl.PaletteImpl#getAutomationSystem <em>Automation System</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PaletteImpl extends EObjectImpl implements Palette {
	/**
	 * The cached value of the '{@link #getRootGroup() <em>Root Group</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRootGroup()
	 * @generated
	 * @ordered
	 */
	protected PaletteGroup rootGroup;

	/**
	 * The default value of the '{@link #getProject() <em>Project</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProject()
	 * @generated
	 * @ordered
	 */
	protected static final IProject PROJECT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProject() <em>Project</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProject()
	 * @generated
	 * @ordered
	 */
	protected IProject project = PROJECT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAutomationSystem() <em>Automation System</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAutomationSystem()
	 * @generated
	 * @ordered
	 */
	protected AutomationSystem automationSystem;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PaletteGroup getRootGroupGen() {
		if (rootGroup != null && rootGroup.eIsProxy()) {
			InternalEObject oldRootGroup = (InternalEObject)rootGroup;
			rootGroup = (PaletteGroup)eResolveProxy(oldRootGroup);
			if (rootGroup != oldRootGroup) {
				InternalEObject newRootGroup = (InternalEObject)rootGroup;
				NotificationChain msgs = oldRootGroup.eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PalettePackage.PALETTE__ROOT_GROUP, null, null);
				if (newRootGroup.eInternalContainer() == null) {
					msgs = newRootGroup.eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PalettePackage.PALETTE__ROOT_GROUP, null, msgs);
				}
				if (msgs != null) msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, PalettePackage.PALETTE__ROOT_GROUP, oldRootGroup, rootGroup));
			}
		}
		return rootGroup;
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	@Override
	public PaletteGroup getRootGroup() {
		PaletteGroup root = getRootGroupGen();
		
		if (rootGroup == null) {
			PaletteGroup group = PaletteFactory.eINSTANCE.createPaletteGroup();
			group.setLabel("Root Group");
			// FIX in order to set the parent (eContainer) relation!
			setRootGroup(group);
			root = group;
		}		
		return root;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PaletteGroup basicGetRootGroup() {
		return rootGroup;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRootGroup(PaletteGroup newRootGroup, NotificationChain msgs) {
		PaletteGroup oldRootGroup = rootGroup;
		rootGroup = newRootGroup;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PalettePackage.PALETTE__ROOT_GROUP, oldRootGroup, newRootGroup);
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
	public void setRootGroup(PaletteGroup newRootGroup) {
		if (newRootGroup != rootGroup) {
			NotificationChain msgs = null;
			if (rootGroup != null)
				msgs = ((InternalEObject)rootGroup).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PalettePackage.PALETTE__ROOT_GROUP, null, msgs);
			if (newRootGroup != null)
				msgs = ((InternalEObject)newRootGroup).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PalettePackage.PALETTE__ROOT_GROUP, null, msgs);
			msgs = basicSetRootGroup(newRootGroup, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PalettePackage.PALETTE__ROOT_GROUP, newRootGroup, newRootGroup));
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IProject getProject() {
		return project;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setProject(IProject newProject) {
		IProject oldProject = project;
		project = newProject;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PalettePackage.PALETTE__PROJECT, oldProject, project));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AutomationSystem getAutomationSystem() {
		if (automationSystem != null && automationSystem.eIsProxy()) {
			InternalEObject oldAutomationSystem = (InternalEObject)automationSystem;
			automationSystem = (AutomationSystem)eResolveProxy(oldAutomationSystem);
			if (automationSystem != oldAutomationSystem) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, PalettePackage.PALETTE__AUTOMATION_SYSTEM, oldAutomationSystem, automationSystem));
			}
		}
		return automationSystem;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AutomationSystem basicGetAutomationSystem() {
		return automationSystem;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAutomationSystem(AutomationSystem newAutomationSystem, NotificationChain msgs) {
		AutomationSystem oldAutomationSystem = automationSystem;
		automationSystem = newAutomationSystem;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PalettePackage.PALETTE__AUTOMATION_SYSTEM, oldAutomationSystem, newAutomationSystem);
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
	public void setAutomationSystem(AutomationSystem newAutomationSystem) {
		if (newAutomationSystem != automationSystem) {
			NotificationChain msgs = null;
			if (automationSystem != null)
				msgs = ((InternalEObject)automationSystem).eInverseRemove(this, LibraryElementPackage.AUTOMATION_SYSTEM__PALETTE, AutomationSystem.class, msgs);
			if (newAutomationSystem != null)
				msgs = ((InternalEObject)newAutomationSystem).eInverseAdd(this, LibraryElementPackage.AUTOMATION_SYSTEM__PALETTE, AutomationSystem.class, msgs);
			msgs = basicSetAutomationSystem(newAutomationSystem, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PalettePackage.PALETTE__AUTOMATION_SYSTEM, newAutomationSystem, newAutomationSystem));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PaletteEntry getTypeEntry(final String typeName) {
		PaletteEntry entry = null;
		//TODO reconsider when namespaces are coming how to retrieve the type
		List<PaletteEntry> entries = getTypeEntries(typeName);
		if (!entries.isEmpty()) {
			entry = entries.get(0);
		} 
		return entry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PalettePackage.PALETTE__AUTOMATION_SYSTEM:
				if (automationSystem != null)
					msgs = ((InternalEObject)automationSystem).eInverseRemove(this, LibraryElementPackage.AUTOMATION_SYSTEM__PALETTE, AutomationSystem.class, msgs);
				return basicSetAutomationSystem((AutomationSystem)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PalettePackage.PALETTE__ROOT_GROUP:
				return basicSetRootGroup(null, msgs);
			case PalettePackage.PALETTE__AUTOMATION_SYSTEM:
				return basicSetAutomationSystem(null, msgs);
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
			case PalettePackage.PALETTE__ROOT_GROUP:
				if (resolve) return getRootGroup();
				return basicGetRootGroup();
			case PalettePackage.PALETTE__PROJECT:
				return getProject();
			case PalettePackage.PALETTE__AUTOMATION_SYSTEM:
				if (resolve) return getAutomationSystem();
				return basicGetAutomationSystem();
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
			case PalettePackage.PALETTE__ROOT_GROUP:
				setRootGroup((PaletteGroup)newValue);
				return;
			case PalettePackage.PALETTE__PROJECT:
				setProject((IProject)newValue);
				return;
			case PalettePackage.PALETTE__AUTOMATION_SYSTEM:
				setAutomationSystem((AutomationSystem)newValue);
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
			case PalettePackage.PALETTE__ROOT_GROUP:
				setRootGroup((PaletteGroup)null);
				return;
			case PalettePackage.PALETTE__PROJECT:
				setProject(PROJECT_EDEFAULT);
				return;
			case PalettePackage.PALETTE__AUTOMATION_SYSTEM:
				setAutomationSystem((AutomationSystem)null);
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
			case PalettePackage.PALETTE__ROOT_GROUP:
				return rootGroup != null;
			case PalettePackage.PALETTE__PROJECT:
				return PROJECT_EDEFAULT == null ? project != null : !PROJECT_EDEFAULT.equals(project);
			case PalettePackage.PALETTE__AUTOMATION_SYSTEM:
				return automationSystem != null;
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
		result.append(" (project: "); //$NON-NLS-1$
		result.append(project);
		result.append(')');
		return result.toString();
	}

	/**
	 * This method creates a new PaletteEntry.
	 * 
	 * @param file
	 *          - the eclipse resource file representation
	 * @return the newly created PaletteEntry
	 */
	@Override
	public PaletteEntry createFBTypeEntry(final IFile file, PaletteGroup parent) {
		FBTypePaletteEntry entry = PaletteFactory.eINSTANCE
				.createFBTypePaletteEntry();
		configurePaletteEntry(entry, file, parent);
		return entry;
	}	
	

	@Override
	public PaletteEntry createDeviceEntry(IFile file, PaletteGroup parent){
		DeviceTypePaletteEntry entry = PaletteFactory.eINSTANCE.createDeviceTypePaletteEntry();
		configurePaletteEntry(entry, file, parent);
		return entry;
	}
	
	@Override
	public PaletteEntry createResourceTypeEntry(final IFile file, PaletteGroup parent) {
		ResourceTypeEntry entry = PaletteFactory.eINSTANCE
				.createResourceTypeEntry();
		configurePaletteEntry(entry, file, parent);
		return entry;
	}
	
	@Override
	public PaletteEntry createSegmentTypeEntry(final IFile file, PaletteGroup parent) {
		SegmentTypePaletteEntry entry = PaletteFactory.eINSTANCE
				.createSegmentTypePaletteEntry();
		configurePaletteEntry(entry, file, parent);
		return entry;
	}
	
	@Override
	public PaletteEntry createAdapterEntry(IFile file, PaletteGroup parent){
		AdapterTypePaletteEntry entry = PaletteFactory.eINSTANCE.createAdapterTypePaletteEntry();
		configurePaletteEntry(entry, file, parent);
		return entry;
	}
	
	@Override
	public PaletteEntry createSubApplicationEntry(IFile file, PaletteGroup parent){
		SubApplicationTypePaletteEntry entry = PaletteFactory.eINSTANCE.createSubApplicationTypePaletteEntry();
		configurePaletteEntry(entry, file, parent);
		return entry;
	}


	private void configurePaletteEntry(PaletteEntry entry, IFile file,
			PaletteGroup parent) {
		entry.setType(null);
		entry.setLabel(TypeLibrary.getTypeNameFromFile(file));
		entry.setFile(file);
		parent.addEntry(entry);
	}

	/**
	 * This method creates a new PaletteGroup
	 * 
	 * @param parent
	 *          - the parent paletteGroup of the newly to be created paletteGroup
	 * @param groupName
	 *          - the name of the new paletteGroup
	 * @return pGroup - the new created group, or null if no name has been given
	 */
	@Override
	public PaletteGroup createGroup(final PaletteGroup parent,
			final String groupName) {
		if (groupName != null) {
			PaletteGroup pGroup = PaletteFactory.eINSTANCE.createPaletteGroup();
			pGroup.setLabel(groupName);
			parent.getSubGroups().add(pGroup);
			return pGroup;
		}
		return null;
	}
	
	
	@Override
	public PaletteGroup createGroupWithFolder(final PaletteGroup parent, final String groupName){
		PaletteGroup pGroup = createGroup(parent, groupName);
		
		String path = groupName;
		PaletteGroup runner = parent; 
		while(runner.eContainer() instanceof PaletteGroup){
			path = runner.getLabel() + "/" + path;
			runner = (PaletteGroup)runner.eContainer();
		}
		
		IContainer cont = TypeLibrary.getLibPath(this);
		IFolder groupFolder = cont.getFolder(new Path(path));
		if(!groupFolder.exists()){
			IProgressMonitor monitor = new NullProgressMonitor();
			try {
				groupFolder.create(true, true, monitor);
				groupFolder.refreshLocal(IResource.DEPTH_INFINITE, monitor);
			} catch (CoreException e) {
				Activator.getDefault().logError(e.getMessage(), e);
			}
		}
		
		return pGroup;
	}

	/**
	 * This method returns the group that is indicated in the path name. If the
	 * group does not exist, it is created.
	 * 
	 * @param path
	 *          - a list of strings, containing the entire path to the group
	 * @param createDir - create the directory if the group has to be created 
	 * @return group - the group that is indicated in the path name
	 */
	@Override
	public PaletteGroup getGroup(final List<String> path, boolean createDir) {
		String dirName = null;
		PaletteGroup group = getRootGroup();
		EList<PaletteGroup> subGroups = group.getSubGroups();
		PaletteGroup parent = null;
		for (Iterator<String> iterator = path.iterator(); iterator.hasNext();) {
			dirName = iterator.next();
			parent = group;
			group = checkSubGroups(subGroups, dirName);
			if (group == null) {
				break;
			} else {
				subGroups = group.getSubGroups();
			}
		}
		if (group == null && parent != null && dirName != null) {
			// create new path
			for (int i = path.indexOf(dirName); i < path.size(); i++) {
				if(createDir){
					group = createGroupWithFolder(parent, path.get(i));					
				}
				else{
					group = createGroup(parent, path.get(i));					
				}
				parent = group;
			}
		}
		return group;
	}

	/**
	 * This method searches for a specific subgroup in a list of groups.
	 * 
	 * @param group
	 *          - a List of PaletteGroups
	 * @param name
	 *          - the name of the group being searched for
	 * @return pGroup if the group is found, otherwise null
	 */
	private PaletteGroup checkSubGroups(final List<PaletteGroup> group,
			final String name) {
		for (Iterator<PaletteGroup> iterator = group.iterator(); iterator.hasNext();) {
			PaletteGroup pGroup = iterator.next();
			if (pGroup.getLabel().equalsIgnoreCase(name)) {
				return pGroup;
			}
		}
		return null;
	}

	private boolean removeSubGroups(final PaletteGroup pGroup,
			final List<PaletteGroup> deleteAbleGroups) {
		for (Iterator<PaletteGroup> iterator = pGroup.getSubGroups().iterator(); iterator
				.hasNext();) {
			PaletteGroup group = iterator.next();
			removeSubGroups(group, deleteAbleGroups);
		}
		return pGroup.getSubGroups().removeAll(deleteAbleGroups);

	}

	@Override
	public boolean removeGroups(final List<PaletteGroup> groups) {
		for (Iterator<PaletteGroup> iterator = groups.iterator(); iterator
				.hasNext();) {
			PaletteGroup group = iterator.next();
			if (group.getParentGroup() != null) {
				removeSubGroups(group.getParentGroup(), groups);
			}
		}

		return false;
	}

	@Override
	public List<PaletteGroup> getAllGroups(final PaletteGroup group) {
		List<PaletteGroup> temp = new ArrayList<PaletteGroup>();
		temp.addAll(group.getSubGroups());
		for (Iterator<PaletteGroup> iterator = group.getSubGroups().iterator(); iterator
				.hasNext();) {
			temp.addAll(getAllGroups(iterator.next()));
		}
		return temp;
	}

	@Override
	public List<PaletteGroup> getAllParentGroups(final PaletteGroup group) {
		List<PaletteGroup> temp = new ArrayList<PaletteGroup>();
		PaletteGroup parent = group.getParentGroup();
		while (parent != null) {
			temp.add(parent);
			parent = parent.getParentGroup();
		}
		return temp;
	}

	/**
	 * This method searches for all instances of a FBType in the Palette
	 * 
	 * @param typeName
	 *          - the name of the FBType searched for
	 * @return types - a list containing all occurences of FBType in the Palette
	 */
	@Override
	public List<FBType> getFBTypes(final String typeName) {
		return checkGroupsForFBType(getRootGroup(), typeName);
	}

	/**
	 * This method searches for all entries of a typeName in the Palette
	 * 
	 * @param typeName
	 *          - the name of the FBType searched for
	 * @return types - a list containing all occurrences of PaletteEntries in the
	 *         Palette
	 */
	@Override
	public List<PaletteEntry> getTypeEntries(final String typeName) {
		return checkGroupsForFBTypeEntries(getRootGroup(), typeName);
	}

	@Override
	public PaletteEntry getTypeEntryForPath(String typePath, String typeEnding){
		
		//TODO similar code is in TypeLibrary.getTargetGroup(...);
		String[] path = typePath.split(ImportUtils.getSeperatorRegex());
		if(path.length <= 1){
			path = typePath.split("/");
			//quick fix for wrong path
			if(path.length <= 1){
				path = typePath.split("\\\\");
			}
		}
		
		List<String> groupPath = new ArrayList<String>();
		int i = 0;
		if(path[0].length() == 0){
			i =1;
		}
		
		for (; i < path.length; i++) {
			String string = path[i];
			if (!string.toUpperCase().endsWith(typeEnding.toUpperCase())) {
				groupPath.add(string);
			}
		}
		PaletteGroup group = findGroup(groupPath);
		if (group == null) {
			// TODO error handling
			return null;
		}
		return group.getEntry(TypeLibrary.getTypeNameFromFileName(path[path.length - 1]));
	}

	@Override
	public PaletteGroup findGroup(final List<String> path) {
		String dirName = null;
		PaletteGroup group = getRootGroup();
		EList<PaletteGroup> subGroups = group.getSubGroups();
		for (Iterator<String> iterator = path.iterator(); iterator.hasNext();) {
			dirName = iterator.next();
			group = checkSubGroups(subGroups, dirName);
			if (group == null) {
				break;
			} else {
				subGroups = group.getSubGroups();
			}
		}
		return group;
	}

	/**
	 * This method searches for all instances of a FBType in a specific
	 * PaletteGroup
	 * 
	 * @param group
	 *          - the PaletteGroup being searched for the FBType
	 * @param name
	 *          - the name of the FBType being searched for
	 * @return types - a list containing all occurrences of the FBType in the
	 *         PaletteGroup
	 */
	private List<FBType> checkGroupsForFBType(final PaletteGroup group,
			final String name) {
		List<FBType> types = new ArrayList<FBType>();
		
		for (PaletteEntry entry : group.getEntries()) {
			if((entry instanceof FBTypePaletteEntry) && (name.equals(entry.getLabel()))){
				types.add(((FBTypePaletteEntry)entry).getFBType());				
			}
		}
		for (PaletteGroup pGroup  : group.getSubGroups()) {
			types.addAll(checkGroupsForFBType(pGroup, name));
		}
		return types;
	}

	/**
	 * This method searches for all instances of a FBType/PaletteEntry in a
	 * specific PaletteGroup
	 * 
	 * @param group
	 *          - the PaletteGroup being searched for the FBType/PaletteEntry
	 * @param name
	 *          - the name of the FBType being searched for
	 * @return types - a list containing all occurences of the FBType/PaletteEntry
	 *         in the PaletteGroup
	 */
	private List<PaletteEntry> checkGroupsForFBTypeEntries(
			final PaletteGroup group, final String name) {
		List<PaletteEntry> types = new ArrayList<PaletteEntry>();
		for (PaletteEntry entry : group.getEntries()) {
			if (entry.getLabel().equals(name)) {
				types.add(entry);
			}
		}
		for (PaletteGroup pGroup : group.getSubGroups()) {
			types.addAll(checkGroupsForFBTypeEntries(pGroup, name));
		}
		return types;
	}

} // PaletteImpl
