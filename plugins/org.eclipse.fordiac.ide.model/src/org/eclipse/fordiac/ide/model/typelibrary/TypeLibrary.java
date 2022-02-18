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

import java.util.Collection;
import java.util.HashMap;
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
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.fordiac.ide.model.Palette.DataTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PaletteFactory;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public final class TypeLibrary implements TypeLibraryTags {

	private static final String PLUGIN_ID = "org.eclipse.fordiac.ide.model"; //$NON-NLS-1$

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

	private final Palette blockTypeLib = PaletteFactory.eINSTANCE.createPalette();
	private final Palette errorTypeLib = PaletteFactory.eINSTANCE.createPalette();
	private final DataTypeLibrary dataTypeLib = new DataTypeLibrary();
	private IProject project;

	/** An array of palette entry creators */
	private static IPaletteEntryCreator[] paletteCreators = null;

	public static String getTypeNameFromFile(final IFile element) {
		return getTypeNameFromFileName(element.getName());
	}

	public static String getTypeNameFromFileName(final String fileName) {
		String name = fileName;
		final int index = fileName.lastIndexOf('.');
		if (-1 != index) {
			name = fileName.substring(0, index);
		}
		return name;
	}

	public static PaletteEntry getPaletteEntryForFile(final IFile typeFile) {
		final TypeLibrary typeLib = TypeLibrary.getTypeLibrary(typeFile.getProject());
		return typeLib.getPaletteEntry(typeFile);
	}

	public PaletteEntry getPaletteEntry(final IFile typeFile) {
		if (isDataTypeFile(typeFile)) {
			return dataTypeLib.getDerivedDataTypes().get(TypeLibrary.getTypeNameFromFile(typeFile));
		}
		final EMap<String, ? extends PaletteEntry> typeEntryList = getTypeList(typeFile);
		if (null != typeEntryList) {
			return typeEntryList.get(TypeLibrary.getTypeNameFromFile(typeFile));
		}
		return null;
	}

	private static boolean isDataTypeFile(final IFile typeFile) {
		return TypeLibraryTags.DATA_TYPE_FILE_ENDING.equalsIgnoreCase(typeFile.getFileExtension());
	}

	public Palette getBlockTypeLib() {
		return blockTypeLib;
	}

	public DataTypeLibrary getDataTypeLibrary() {
		return dataTypeLib;
	}

	public IProject getProject() {
		return project;
	}

	private EMap<String, ? extends PaletteEntry> getTypeList(final IFile typeFile) {
		final String extension = typeFile.getFileExtension();
		if (null != extension) {
			switch (extension.toUpperCase()) {
			case TypeLibraryTags.ADAPTER_TYPE_FILE_ENDING:
				return blockTypeLib.getAdapterTypes();
			case TypeLibraryTags.DEVICE_TYPE_FILE_ENDING:
				return blockTypeLib.getDeviceTypes();
			case TypeLibraryTags.FB_TYPE_FILE_ENDING:
				return blockTypeLib.getFbTypes();
			case TypeLibraryTags.RESOURCE_TYPE_FILE_ENDING:
				return blockTypeLib.getResourceTypes();
			case TypeLibraryTags.SEGMENT_TYPE_FILE_ENDING:
				return blockTypeLib.getSegmentTypes();
			case TypeLibraryTags.SUBAPP_TYPE_FILE_ENDING:
				return blockTypeLib.getSubAppTypes();
			default:
				break;
			}
		}
		return null;
	}

	/**
	 * Instantiates a new fB type library.
	 */
	private TypeLibrary(final IProject project) {
		this.project = project;
		blockTypeLib.setTypeLibrary(this);
		errorTypeLib.setTypeLibrary(this);
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
					createPaletteEntry((IFile) iResource);
				}
			}
		} catch (final CoreException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}
	}

	/**
	 *
	 * @param palette
	 * @param file
	 * @return
	 */
	public PaletteEntry createPaletteEntry(final IFile file) {
		PaletteEntry entry = null;
		for (final IPaletteEntryCreator in : getPaletteCreators()) {
			if (in.canHandle(file)) {
				entry = in.createPaletteEntry();
				configurePaletteEntry(entry, file);
				addPaletteEntry(entry);
			}
		}
		return entry;
	}

	public void addPaletteEntry(final PaletteEntry entry) {

		final FBTypePaletteEntry errorEntry = errorTypeLib.getFBTypeEntry(entry.getLabel());
		if (errorEntry != null) {
			errorTypeLib.removePaletteEntry(errorEntry);
		}

		if (entry instanceof DataTypePaletteEntry) {
			entry.setPalette(blockTypeLib); // for data type entries the palette will not be automatically set
			dataTypeLib.addPaletteEntry((DataTypePaletteEntry) entry);
		} else {
			blockTypeLib.addPaletteEntry(entry);
		}
	}

	public void removePaletteEntry(final PaletteEntry entry) {
		if (entry instanceof DataTypePaletteEntry) {
			dataTypeLib.removePaletteEntry((DataTypePaletteEntry) entry);
		} else {
			blockTypeLib.removePaletteEntry(entry);
		}
	}

	/**
	 *
	 */
	private static void setPaletteCreators() {
		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		final IConfigurationElement[] elems = registry.getConfigurationElementsFor(PLUGIN_ID, "PaletteEntryCreator"); //$NON-NLS-1$
		int countPaletteCreater = 0;
		paletteCreators = new IPaletteEntryCreator[elems.length];

		for (final IConfigurationElement elem : elems) {
			try {
				final Object object = elem.createExecutableExtension("class"); //$NON-NLS-1$
				if (object instanceof IPaletteEntryCreator) {
					paletteCreators[countPaletteCreater] = (IPaletteEntryCreator) object;
					countPaletteCreater++;
				}
			} catch (final CoreException e) {
				FordiacLogHelper.logError(e.getMessage(), e);
			}
		}
	}

	private static IPaletteEntryCreator[] getPaletteCreators() {
		if (null == paletteCreators) {
			setPaletteCreators();
		}
		return paletteCreators;
	}

	private static void configurePaletteEntry(final PaletteEntry entry, final IFile file) {
		entry.setType(null);
		entry.setLabel(TypeLibrary.getTypeNameFromFile(file));
		entry.setFile(file);
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
		checkDeletionsForTypeGroup(blockTypeLib.getAdapterTypes().values());
		checkDeletionsForTypeGroup(blockTypeLib.getDeviceTypes().values());
		checkDeletionsForTypeGroup(blockTypeLib.getFbTypes().values());
		checkDeletionsForTypeGroup(blockTypeLib.getResourceTypes().values());
		checkDeletionsForTypeGroup(blockTypeLib.getSegmentTypes().values());
		checkDeletionsForTypeGroup(blockTypeLib.getSubAppTypes().values());
		checkDeletionsForTypeGroup(dataTypeLib.getDerivedDataTypes().values());
	}

	private static void checkDeletionsForTypeGroup(final Collection<? extends PaletteEntry> typeEntries) {
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
					createPaletteEntry((IFile) resource);
				}
			}
		} catch (final CoreException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}

	}

	public boolean containsType(final IFile file) {
		return (null != getPaletteEntry(file));
	}

	/**
	 * Returns the tool library project.
	 *
	 * @return the tool library project of the 4diac-ide instance
	 */
	private static IProject getToolLibProject() {
		final IWorkspaceRoot myWorkspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		return myWorkspaceRoot.getProject(TOOL_LIBRARY_PROJECT_NAME);
	}

	public static IFolder getToolLibFolder() {

		final IProject toolLibProject = getToolLibProject();

		if (!toolLibProject.exists()) {
			createToolLibProject(toolLibProject);
		}

		IFolder toolLibFolder = toolLibProject.getFolder(TOOL_LIBRARY_PROJECT_NAME);
		if (!toolLibFolder.exists()) {
			createToolLibLink(toolLibProject);
			toolLibFolder = toolLibProject.getFolder(TOOL_LIBRARY_PROJECT_NAME);
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

		final IFolder link = toolLibProject.getFolder(TOOL_LIBRARY_PROJECT_NAME);

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

	public Palette getErrorTypeLib() {
		return errorTypeLib;
	}

	public PaletteEntry find(final String name) {

		PaletteEntry entry = blockTypeLib.getSubAppTypeEntry(name);

		if (entry != null) {
			return entry;
		}

		entry = blockTypeLib.getFBTypeEntry(name);

		if (entry != null) {
			return entry;
		}

		entry = dataTypeLib.getDerivedDataTypes().get(name);

		if (entry != null) {
			return entry;
		}

		return blockTypeLib.getAdapterTypeEntry(name);
	}

}
