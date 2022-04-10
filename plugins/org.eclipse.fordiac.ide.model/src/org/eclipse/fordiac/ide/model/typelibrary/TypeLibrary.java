/********************************************************************************
 * Copyright (c) 2008, 2021 Profactor GmbH, TU Wien ACIN, fortiss GmbH, IBH Systems
 * 		            Johannes Kepler University,
 *                          Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Gerhard Ebenhofer, Martijn Rooker, Alois Zoitl, Monika Wenger, Jens Reimann,
 *  Waldemar Eisenmenger, Gerd Kainz
 *    - initial API and implementation and/or initial documentation
 *  Martin Melik-Merkumians - adds convenience methods
 *  Alois Zoitl - Changed to a per project Type and Data TypeLibrary
 *              - Added support for project renameing
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.typelibrary;

import java.text.Collator;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.model.Palette.DataTypePaletteEntry;
import org.eclipse.fordiac.ide.model.typelibrary.impl.BlockTypeLibraryImpl;
import org.eclipse.fordiac.ide.model.typelibrary.impl.TypeEntryFactory;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public final class TypeLibrary {

	// !> Holds type libraries of all open 4diac IDE projects
	private static Map<IProject, TypeLibrary> typeLibraryList = new HashMap<>();

	public static TypeLibrary getTypeLibrary(final IProject proj) {
		synchronized (typeLibraryList) {
			return typeLibraryList.computeIfAbsent(proj, TypeLibrary::new);
		}

	}

	public static void removeProject(final IProject project) {
		synchronized (typeLibraryList) {
			typeLibraryList.remove(project);
		}
	}

	public static void renameProject(final IProject oldProject, final IProject newProject) {
		synchronized (typeLibraryList) {
			final TypeLibrary typelib = typeLibraryList.remove(oldProject);
			if (typelib != null) {
				typelib.project = newProject;
				typeLibraryList.put(newProject, typelib);
			}
		}
	}

	private final DataTypeLibrary dataTypeLib = new DataTypeLibrary();
	private IProject project;

	private final BlockTypeLibrary newBlockTypeLib = new BlockTypeLibraryImpl();
	private final BlockTypeLibrary newErrorTypeLib = new BlockTypeLibraryImpl();

	public Map<String, AdapterTypeEntry> getAdapterTypes() {
		return newBlockTypeLib.getAdapterTypes();
	}

	public List<AdapterTypeEntry> getAdapterTypesSorted() {
		return getAdapterTypes().values().stream()
				.sorted((o1, o2) -> Collator.getInstance().compare(o1.getTypeName(), o2.getTypeName())).toList();
	}

	public Map<String, DeviceTypeEntry> getDeviceTypes() {
		return newBlockTypeLib.getDeviceTypes();
	}

	public Map<String, FBTypeEntry> getFbTypes() {
		return newBlockTypeLib.getFbTypes();
	}

	public Map<String, ResourceTypeEntry> getResourceTypes() {
		return newBlockTypeLib.getResourceTypes();
	}

	public Map<String, SegmentTypeEntry> getSegmentTypes() {
		return newBlockTypeLib.getSegmentTypes();
	}

	public Map<String, SubAppTypeEntry> getSubAppTypes() {
		return newBlockTypeLib.getSubAppTypes();
	}

	public AdapterTypeEntry getAdapterTypeEntry(final String typeName) {
		return getAdapterTypes().get(typeName);
	}

	public DeviceTypeEntry getDeviceTypeEntry(final String typeName) {
		return getDeviceTypes().get(typeName);
	}

	public FBTypeEntry getFBTypeEntry(final String typeName) {
		return getFbTypes().get(typeName);
	}

	public ResourceTypeEntry getResourceTypeEntry(final String typeName) {
		return getResourceTypes().get(typeName);
	}

	public SegmentTypeEntry getSegmentTypeEntry(final String typeName) {
		return getSegmentTypes().get(typeName);
	}

	public SubAppTypeEntry getSubAppTypeEntry(final String typeName) {
		return getSubAppTypes().get(typeName);
	}

	public static TypeEntry getTypeEntryForFile(final IFile typeFile) {
		final TypeLibrary typeLib = TypeLibrary.getTypeLibrary(typeFile.getProject());
		return typeLib.getTypeEntry(typeFile);
	}

	public TypeEntry getTypeEntry(final IFile typeFile) {
		if (isDataTypeFile(typeFile)) {
			return dataTypeLib.getDerivedDataTypes().get(TypeEntry.getTypeNameFromFile(typeFile));
		}
		final Map<String, ? extends TypeEntry> typeEntryList = getTypeList(typeFile);
		if (null != typeEntryList) {
			return typeEntryList.get(TypeEntry.getTypeNameFromFile(typeFile));
		}
		return null;
	}

	private static boolean isDataTypeFile(final IFile typeFile) {
		return TypeLibraryTags.DATA_TYPE_FILE_ENDING.equalsIgnoreCase(typeFile.getFileExtension());
	}

	public DataTypeLibrary getDataTypeLibrary() {
		return dataTypeLib;
	}

	public IProject getProject() {
		return project;
	}

	private Map<String, ? extends TypeEntry> getTypeList(final IFile typeFile) {
		final String extension = typeFile.getFileExtension();
		if (null != extension) {
			switch (extension.toUpperCase()) {
			case TypeLibraryTags.ADAPTER_TYPE_FILE_ENDING:
				return getAdapterTypes();
			case TypeLibraryTags.DEVICE_TYPE_FILE_ENDING:
				return getDeviceTypes();
			case TypeLibraryTags.FB_TYPE_FILE_ENDING:
				return getFbTypes();
			case TypeLibraryTags.RESOURCE_TYPE_FILE_ENDING:
				return getResourceTypes();
			case TypeLibraryTags.SEGMENT_TYPE_FILE_ENDING:
				return getSegmentTypes();
			case TypeLibraryTags.SUBAPP_TYPE_FILE_ENDING:
				return getSubAppTypes();
			default:
				break;
			}
		}
		return Collections.emptyMap();
	}

	/**
	 * Instantiates a new fB type library.
	 */
	private TypeLibrary(final IProject project) {
		this.project = project;
		if (project != null && project.exists()) {
			loadPaletteFolderMembers(project);
		}
	}

	public static void loadToolLibrary() {
		synchronized (typeLibraryList) {
			final IProject toolLibProject = getToolLibProject();
			typeLibraryList.computeIfAbsent(toolLibProject, TypeLibrary::createToolLibrary);
		}
	}

	private static TypeLibrary createToolLibrary(final IProject toolLibProject) {
		if (toolLibProject.exists()) {
			// clean-up old links
			try {
				toolLibProject.delete(true, new NullProgressMonitor());
			} catch (final CoreException e) {
				FordiacLogHelper.logError(e.getMessage(), e);
			}
		}

		createToolLibProject(toolLibProject);

		return new TypeLibrary(toolLibProject);
	}

	private void loadPaletteFolderMembers(final IContainer container) {
		IResource[] members;
		try {
			members = container.members();

			for (final IResource iResource : members) {
				if (iResource instanceof IFolder) {
					loadPaletteFolderMembers((IFolder) iResource);
				}
				if (iResource instanceof IFile) {
					createTypeEntry((IFile) iResource);
				}
			}
		} catch (final CoreException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}
	}

	public TypeEntry createTypeEntry(final IFile file) {
		final TypeEntry entry = TypeEntryFactory.INSTANCE.createTypeEntry(file);
		if (null != entry) {
			addTypeEntry(entry);
		}
		return entry;
	}

	public void addTypeEntry(final TypeEntry entry) {
		final FBTypeEntry errorEntry = newErrorTypeLib.getFbTypes().get(entry.getTypeName());
		if (errorEntry != null) {
			newErrorTypeLib.removeTypeEntry(errorEntry);
		}
		entry.setTypeLibrary(this);
		if (entry instanceof DataTypePaletteEntry) {
			dataTypeLib.addTypeEntry((DataTypeEntry) entry);
		} else {
			newBlockTypeLib.addTypeEntry(entry);
		}
	}

	public void removeTypeEntry(final TypeEntry entry) {
		if (entry instanceof DataTypePaletteEntry) {
			dataTypeLib.removeTypeEntry((DataTypeEntry) entry);
		} else {
			newBlockTypeLib.removeTypeEntry(entry);
		}
	}

	public static void refreshTypeLib(final IFile file) {
		final TypeLibrary typeLib = TypeLibrary.getTypeLibrary(file.getProject());
		typeLib.refresh();
	}

	private void refresh() {
		try {
			project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		} catch (final CoreException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}

		checkDeletions();
		checkAdditions(project);
	}

	private void checkDeletions() {
		checkDeletionsForTypeGroup(getAdapterTypes().values());
		checkDeletionsForTypeGroup(getDeviceTypes().values());
		checkDeletionsForTypeGroup(getFbTypes().values());
		checkDeletionsForTypeGroup(getResourceTypes().values());
		checkDeletionsForTypeGroup(getSegmentTypes().values());
		checkDeletionsForTypeGroup(getSubAppTypes().values());
		checkDeletionsForTypeGroup(dataTypeLib.getDerivedDataTypes().values());
	}

	private static void checkDeletionsForTypeGroup(final Collection<? extends TypeEntry> typeEntries) {
		typeEntries.removeIf(e -> (!e.getFile().exists()));
	}

	private void checkAdditions(final IContainer container) {
		try {
			final IResource[] members = container.members();

			for (final IResource resource : members) {
				if (resource instanceof IFolder) {
					checkAdditions((IFolder) resource);
				}
				if ((resource instanceof IFile) && (!containsType((IFile) resource))) {
					// only add new entry if it does not exist
					createTypeEntry((IFile) resource);
				}
			}
		} catch (final CoreException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}

	}

	public boolean containsType(final IFile file) {
		return (null != getTypeEntry(file));
	}

	/**
	 * Returns the tool library project.
	 *
	 * @return the tool library project of the 4diac-ide instance
	 */
	private static IProject getToolLibProject() {
		final IWorkspaceRoot myWorkspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		return myWorkspaceRoot.getProject(TypeLibraryTags.TOOL_LIBRARY_PROJECT_NAME);
	}

	public static IFolder getToolLibFolder() {

		final IProject toolLibProject = getToolLibProject();

		if (!toolLibProject.exists()) {
			createToolLibProject(toolLibProject);
		}

		IFolder toolLibFolder = toolLibProject.getFolder(TypeLibraryTags.TOOL_LIBRARY_PROJECT_NAME);
		if (!toolLibFolder.exists()) {
			createToolLibLink(toolLibProject);
			toolLibFolder = toolLibProject.getFolder(TypeLibraryTags.TOOL_LIBRARY_PROJECT_NAME);
		}

		return toolLibFolder;
	}

	private static void createToolLibProject(final IProject toolLibProject) {
		final IProgressMonitor progressMonitor = new NullProgressMonitor();

		try {
			toolLibProject.create(progressMonitor);
			toolLibProject.open(progressMonitor);
		} catch (final Exception e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}

		createToolLibLink(toolLibProject);
	}

	private static void createToolLibLink(final IProject toolLibProject) {
		final IWorkspace workspace = ResourcesPlugin.getWorkspace();

		final IFolder link = toolLibProject.getFolder(TypeLibraryTags.TOOL_LIBRARY_PROJECT_NAME);

		final String typeLibPath = System.getProperty("4diac.typelib.path"); //$NON-NLS-1$

		final IPath location;

		if ((null != typeLibPath) && !typeLibPath.isEmpty()) {
			location = new Path(typeLibPath);
		} else {
			location = new Path(Platform.getInstallLocation().getURL().getFile() + TypeLibraryTags.TYPE_LIBRARY);
		}
		if (workspace.validateLinkLocation(link, location).isOK()
				&& location.toFile().isDirectory()) {
			try {
				link.createLink(location, IResource.NONE, null);
			} catch (final Exception e) {
				FordiacLogHelper.logError(e.getMessage(), e);
			}
		} else {
			// invalid location, throw an exception or warn user
		}
	}

	public BlockTypeLibrary getErrorTypeLib() {
		return newErrorTypeLib;
	}

	public TypeEntry find(final String name) {

		TypeEntry entry = getSubAppTypeEntry(name);

		if (entry != null) {
			return entry;
		}

		entry = getFBTypeEntry(name);

		if (entry != null) {
			return entry;
		}

		entry = dataTypeLib.getDerivedDataTypes().get(name);

		if (entry != null) {
			return entry;
		}

		return getAdapterTypeEntry(name);
	}

}
