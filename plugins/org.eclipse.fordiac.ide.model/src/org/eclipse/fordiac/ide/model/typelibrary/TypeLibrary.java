/********************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH, IBH Systems,
 * 				 2018 Johannes Kepler University
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
 *  Martin Melik-Merkumians
 *    - adds convenience methods
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.typelibrary;

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
import org.eclipse.fordiac.ide.model.Activator;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PaletteFactory;
import org.eclipse.fordiac.ide.model.Palette.PalettePackage;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;

public final class TypeLibrary implements TypeLibraryTags {

	/** The palette. */
	private Palette palette = PaletteFactory.eINSTANCE.createPalette();

	/** An array of palette entry creators */
	private static IPaletteEntryCreator[] paletteCreators = null;

	/** The instance. */
	private static TypeLibrary instance;

	public static String getTypeNameFromFile(IFile element) {
		return getTypeNameFromFileName(element.getName());
	}

	public static String getTypeNameFromFileName(final String fileName) {
		String name = fileName;
		int index = fileName.lastIndexOf('.');
		if (-1 != index) {
			name = fileName.substring(0, index);
		}
		return name;
	}

	public static boolean isIEC61499TypeFile(String filename) {
		return ((filename.toUpperCase().endsWith(FB_TYPE_FILE_ENDING_WITH_DOT))
				|| (filename.toUpperCase().endsWith(TypeLibraryTags.ADAPTER_TYPE_FILE_ENDING_WITH_DOT))
				|| (filename.toUpperCase().endsWith(TypeLibraryTags.DEVICE_TYPE_FILE_ENDING_WITH_DOT))
				|| (filename.toUpperCase().endsWith(TypeLibraryTags.RESOURCE_TYPE_FILE_ENDING_WITH_DOT))
				|| (filename.toUpperCase().endsWith(TypeLibraryTags.SEGMENT_TYPE_FILE_ENDING_WITH_DOT)));
	}

	public static PaletteEntry getPaletteEntryForFile(IFile typeFile, Palette palette) {
		EMap<String, ? extends PaletteEntry> typeEntryList = getTypeList(palette, typeFile);
		if (null != typeEntryList) {
			return typeEntryList.get(TypeLibrary.getTypeNameFromFile(typeFile));
		}
		return null;
	}

	private static EMap<String, ? extends PaletteEntry> getTypeList(Palette palette, IFile typeFile) {
		String extension = typeFile.getFileExtension();
		if (null != extension) {
			switch (extension.toUpperCase()) {
			case TypeLibraryTags.ADAPTER_TYPE_FILE_ENDING:
				return palette.getAdapterTypes();
			case TypeLibraryTags.DEVICE_TYPE_FILE_ENDING:
				return palette.getDeviceTypes();
			case TypeLibraryTags.FB_TYPE_FILE_ENDING:
				return palette.getFbTypes();
			case TypeLibraryTags.RESOURCE_TYPE_FILE_ENDING:
				return palette.getResourceTypes();
			case TypeLibraryTags.SEGMENT_TYPE_FILE_ENDING:
				return palette.getSegmentTypes();
			case TypeLibraryTags.SUBAPP_TYPE_FILE_ENDING:
				return palette.getSubAppTypes();
			default:
				break;
			}
		}
		return null;
	}

	/**
	 * Instantiates a new fB type library.
	 */
	private TypeLibrary() {
		// private constructor for singleton

	}

	/**
	 * Gets the single instance of FBTypeLibrary.
	 *
	 * @return single instance of FBTypeLibrary
	 */
	public static TypeLibrary getInstance() {
		if (instance == null) {
			TypeLibrary newTypeLib = new TypeLibrary();

			IWorkspaceRoot myWorkspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
			IProject toolLibProject = myWorkspaceRoot.getProject(TOOL_LIBRARY_PROJECT_NAME);

			if (toolLibProject.exists()) {
				try {
					toolLibProject.delete(true, new NullProgressMonitor());
				} catch (CoreException e) {
					Activator.getDefault().logError(e.getMessage(), e);
				}
			}

			newTypeLib.loadPalette();

			instance = newTypeLib;
		}
		return instance;
	}

	/**
	 * Gets the palette.
	 *
	 * @return the palette
	 */
	public Palette getPalette() {
		return palette;
	}

	/** The pack. */
	@SuppressWarnings("unused")
	private static PalettePackage pack = PalettePackage.eINSTANCE;

	/**
	 * Load palette.
	 *
	 * @return the palette
	 */
	private Palette loadPalette() {

		IFolder toolLibFolder = getToolLibFolder();

		palette = loadPalette(toolLibFolder);
		return palette;
	}

	public static Palette loadPalette(IContainer container) {
		Palette newPalette = PaletteFactory.eINSTANCE.createPalette();
		loadPaletteFolderMembers(newPalette, container);

		return newPalette;
	}

	private static void loadPaletteFolderMembers(Palette palette, IContainer container) {
		IResource[] members;
		try {
			if (!ResourcesPlugin.getWorkspace().isTreeLocked()) {
				container.refreshLocal(IResource.DEPTH_ONE, null);
			}
			members = container.members();

			for (IResource iResource : members) {
				if (iResource instanceof IFolder) {
					loadPaletteFolderMembers(palette, (IFolder) iResource);
				}
				if (iResource instanceof IFile) {
					createPaleteEntry(palette, (IFile) iResource);
				}
			}
		} catch (CoreException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
	}

	/**
	 *
	 * @param palette
	 * @param file
	 * @return
	 */
	public static PaletteEntry createPaleteEntry(Palette palette, IFile file) {
		PaletteEntry entry = null;
		for (IPaletteEntryCreator in : getPaletteCreators()) {
			if (in.canHandle(file)) {
				entry = in.createPaletteEntry();
				configurePaletteEntry(entry, file);
				palette.addPaletteEntry(entry);
			}
		}
		return entry;
	}

	/**
	 *
	 */
	private static void setPaletteCreators() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elems = registry
				.getConfigurationElementsFor(org.eclipse.fordiac.ide.model.Activator.PLUGIN_ID, "PaletteEntryCreator"); //$NON-NLS-1$
		int countPaletteCreater = 0;
		paletteCreators = new IPaletteEntryCreator[elems.length];

		for (int i = 0; i < elems.length; i++) {
			IConfigurationElement elem = elems[i];
			try {
				Object object = elem.createExecutableExtension("class"); //$NON-NLS-1$
				if (object instanceof IPaletteEntryCreator) {
					paletteCreators[countPaletteCreater] = (IPaletteEntryCreator) object;
					countPaletteCreater++;
				}
			} catch (CoreException e) {
				Activator.getDefault().logError(e.getMessage(), e);
			}
		}
	}

	/**
	 *
	 * @return
	 */
	private static IPaletteEntryCreator[] getPaletteCreators() {
		if (null == paletteCreators) {
			setPaletteCreators();
		}
		return paletteCreators;
	}

	/**
	 *
	 * @param entry
	 * @param file
	 * @param parent
	 */
	private static void configurePaletteEntry(PaletteEntry entry, IFile file) {
		entry.setType(null);
		entry.setLabel(TypeLibrary.getTypeNameFromFile(file));
		entry.setFile(file);
	}

	public static void refreshPalette(Palette palette) {
		IContainer container = TypeLibrary.getLibPath(palette);

		try {
			container.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		} catch (CoreException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}

		checkDeletions(palette);
		checkAdditions(palette, container);
	}

	private static void checkDeletions(Palette palette) {
		checkDeletionsForTypeGroup(palette.getAdapterTypes());
		checkDeletionsForTypeGroup(palette.getDeviceTypes());
		checkDeletionsForTypeGroup(palette.getFbTypes());
		checkDeletionsForTypeGroup(palette.getResourceTypes());
		checkDeletionsForTypeGroup(palette.getSegmentTypes());
		checkDeletionsForTypeGroup(palette.getSubAppTypes());
	}

	private static void checkDeletionsForTypeGroup(EMap<String, ? extends PaletteEntry> types) {
		types.entrySet().removeIf(e -> (!e.getValue().getFile().exists()));
	}

	private static void checkAdditions(Palette palette, IContainer container) {
		try {
			IResource[] members = container.members();

			for (IResource iResource : members) {
				if (iResource instanceof IFolder) {
					checkAdditions(palette, (IFolder) iResource);
				}
				if ((iResource instanceof IFile) && (!paletteContainsType(palette, (IFile) iResource))) {
					// only add new entry if it does not exist
					createPaleteEntry(palette, (IFile) iResource);
				}
			}
		} catch (CoreException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}

	}

	public static boolean paletteContainsType(Palette palette, IFile file) {
		String typeName = getTypeNameFromFile(file);
		return ((null != palette.getAdapterTypeEntry(typeName)) || (null != palette.getDeviceTypeEntry(typeName))
				|| (null != palette.getFBTypeEntry(typeName)) || (null != palette.getResourceTypeEntry(typeName))
				|| (null != palette.getSegmentTypeEntry(typeName)) || (null != palette.getSubAppTypeEntry(typeName)));
	}

	public static IContainer getLibPath(Palette palette) {
		IContainer libPath = null;

		AutomationSystem system = palette.getAutomationSystem();

		if (system == null) {
			libPath = TypeLibrary.getToolLibFolder();
		} else {
			libPath = system.getProject();
		}
		return libPath;
	}

	/**
	 * Returns the tool library project.
	 *
	 * @return the tool library project of the 4diac-ide instance
	 */
	public static IProject getToolLibProject() {
		IWorkspaceRoot myWorkspaceRoot = ResourcesPlugin.getWorkspace().getRoot();

		return myWorkspaceRoot.getProject(TOOL_LIBRARY_PROJECT_NAME);
	}

	public static IFolder getToolLibFolder() {

		IProject toolLibProject = getToolLibProject();

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

	private static void createToolLibProject(IProject toolLibProject) {
		IProgressMonitor progressMonitor = new NullProgressMonitor();

		try {
			toolLibProject.create(progressMonitor);
			toolLibProject.open(progressMonitor);
		} catch (Exception e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}

		createToolLibLink(toolLibProject);
	}

	private static void createToolLibLink(IProject toolLibProject) {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();

		IFolder link = toolLibProject.getFolder(TOOL_LIBRARY_PROJECT_NAME);

		final String typeLibPath = System.getProperty("4diac.typelib.path"); //$NON-NLS-1$

		final IPath location;

		if (typeLibPath != null && !typeLibPath.isEmpty()) {
			location = new Path(typeLibPath);
		} else {
			location = new Path(Platform.getInstallLocation().getURL().getFile() + TypeLibraryTags.TYPE_LIBRARY);
		}
		if (workspace.validateLinkLocation(link, location).isOK()) {
			try {
				link.createLink(location, IResource.NONE, null);
			} catch (Exception e) {
				Activator.getDefault().logError(e.getMessage(), e);
			}
		} else {
			// invalid location, throw an exception or warn user
		}
	}

}
