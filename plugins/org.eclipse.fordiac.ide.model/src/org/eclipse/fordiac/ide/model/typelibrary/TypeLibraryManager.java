/********************************************************************************
 * Copyright (c) 2008, 2023 Profactor GmbH, TU Wien ACIN, fortiss GmbH, IBH Systems,
 * 		            		Johannes Kepler University,
 *                          Primetals Technologies Austria GmbH
 *                          Martin Erich Jobst
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
 *              - Extracted the typelibrary managing functions from the
 *                TypeLibrary
 *  Martin Jobst - add helper to get type library from context object
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.typelibrary;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.ModelPlugin;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public enum TypeLibraryManager {

	INSTANCE;

	// !> Holds type libraries of all open 4diac IDE projects
	private final Map<IProject, TypeLibrary> typeLibraryList = new HashMap<>();

	private final IEventBroker eventBroker = initEventBroker();

	public TypeLibrary getTypeLibrary(final IProject proj) {
		synchronized (typeLibraryList) {
			return typeLibraryList.computeIfAbsent(proj, this::createTypeLibrary);
		}
	}

	public TypeLibrary getTypeLibraryFromContext(final EObject context) {
		// attempt to get from root library element
		if (EcoreUtil.getRootContainer(context) instanceof final LibraryElement libraryElement) {
			return libraryElement.getTypeLibrary();
		}
		// attempt to get from EMF resource through URI
		final Resource resource = context.eResource();
		if (resource != null) {
			final URI uri = resource.getURI();
			if (uri != null) {
				final IFile file;
				if (uri.isPlatformResource()) {
					file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(uri.toPlatformString(true)));
				} else if (uri.isFile()) {
					file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(uri.toFileString()));
				} else {
					return null;
				}
				if (file.getProject() != null) {
					return getTypeLibrary(file.getProject());
				}
			}
		}
		return null;
	}

	public boolean hasTypeLibrary(final IProject proj) {
		synchronized (typeLibraryList) {
			return typeLibraryList.get(proj) != null;
		}
	}

	public void removeProject(final IProject project) {
		synchronized (typeLibraryList) {
			typeLibraryList.remove(project);
		}
	}

	public void renameProject(final IProject oldProject, final IProject newProject) {
		synchronized (typeLibraryList) {
			final TypeLibrary typelib = typeLibraryList.remove(oldProject);
			if (typelib != null) {
				typelib.setProject(newProject);
				typeLibraryList.put(newProject, typelib);
			}
		}
	}

	public TypeEntry getTypeEntryForFile(final IFile typeFile) {
		final TypeLibrary typeLib = getTypeLibrary(typeFile.getProject());
		return typeLib.getTypeEntry(typeFile);
	}

	public TypeEntry getTypeEntryForURI(final URI uri) {
		if (uri.isPlatformResource()) {
			return getTypeEntryForFile(
					ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(uri.toPlatformString(true))));
		}
		return null;
	}

	private TypeLibrary createTypeLibrary(final IProject project) {
		final TypeLibrary library = new TypeLibrary(project);
		eventBroker.send("testevent", library); //$NON-NLS-1$
		return library;
	}

	public void loadToolLibrary() {
		synchronized (typeLibraryList) {
			final IProject toolLibProject = getToolLibProject();
			typeLibraryList.computeIfAbsent(toolLibProject, this::createToolLibrary);
		}
	}

	private TypeLibrary createToolLibrary(final IProject toolLibProject) {
		if (toolLibProject.exists()) {
			// clean-up old links
			try {
				toolLibProject.delete(true, new NullProgressMonitor());
			} catch (final CoreException e) {
				FordiacLogHelper.logError(e.getMessage(), e);
			}
		}

		createToolLibProject(toolLibProject);

		return createTypeLibrary(toolLibProject);
	}

	public void refreshTypeLib(final IResource res) {
		final TypeLibrary typeLib = getTypeLibrary(res.getProject());
		typeLib.refresh();
	}

	/**
	 * Returns the tool library project.
	 *
	 * @return the tool library project of the 4diac-ide instance
	 */
	public static IProject getToolLibProject() {
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
		if (workspace.validateLinkLocation(link, location).isOK() && location.toFile().isDirectory()) {
			try {
				link.createLink(location, IResource.NONE, null);
			} catch (final Exception e) {
				FordiacLogHelper.logError(e.getMessage(), e);
			}
		} else {
			// invalid location, throw an exception or warn user
		}
	}

	private static IEventBroker initEventBroker() {
		loadExtension("org.eclipse.fordiac.ide.model.libraryLinkerExtension", ILibraryLinker.class); //$NON-NLS-1$
		return EclipseContextFactory.getServiceContext(ModelPlugin.getDefault().getBundle().getBundleContext())
				.get(IEventBroker.class);
	}

	// TODO: move to more appropriate utility class
	public static <T> T loadExtension(final String extensionPoint, final Class<T> type) {
		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		final IExtensionPoint point = registry.getExtensionPoint(extensionPoint);
		final IExtension[] extensions = point.getExtensions();
		for (final IExtension extension : extensions) {
			final IConfigurationElement[] elements = extension.getConfigurationElements();
			for (final IConfigurationElement element : elements) {
				try {
					final Object obj = element.createExecutableExtension("class"); //$NON-NLS-1$
					if (type.isInstance(obj)) {
						return type.cast(obj);
					}
				} catch (final Exception e) {
					FordiacLogHelper.logError(e.getMessage(), e);
				}
			}
		}
		return null;
	}
}
