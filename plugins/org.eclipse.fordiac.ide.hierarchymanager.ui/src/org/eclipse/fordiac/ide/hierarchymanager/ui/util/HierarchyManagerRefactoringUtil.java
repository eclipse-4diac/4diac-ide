/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Qemal Alliu - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.hierarchymanager.ui.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLMapImpl;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.HierarchyPackage;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.RootLevel;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.util.HierarchyResourceFactoryImpl;
import org.eclipse.fordiac.ide.hierarchymanager.ui.view.PlantHierarchyView;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

public class HierarchyManagerRefactoringUtil {

	private static List<String> ALLOWED_FILE_EXTENSIONS = List.of("sys", "sub"); //$NON-NLS-1$ //$NON-NLS-2$

	public static List<IFile> getFilesFromResource(final IResource resource) throws CoreException {
		final List<IFile> files = new ArrayList<>();

		if (resource instanceof final IFile file && resource.getFileExtension() != null
				&& ALLOWED_FILE_EXTENSIONS.contains(resource.getFileExtension().toLowerCase())) {
			files.add(file);
		} else if (resource instanceof final IFolder folder) {
			for (final IResource member : folder.members()) {
				files.addAll(getFilesFromResource(member));
			}
		}

		return files;
	}

	public static RootLevel getPlantHierarchy(final IProject project) {
		final AtomicReference<RootLevel> plantHierarchyRef = new AtomicReference<>();

		Display.getDefault().syncExec(() -> {
			final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			PlantHierarchyView view = null;

			if (page != null) {
				view = (PlantHierarchyView) page.findView("org.eclipse.fordiac.ide.hierarchymanager.view"); //$NON-NLS-1$
			}

			if (view != null) {
				final Object input = view.getCommonViewer().getInput();
				if (input instanceof final RootLevel rootLevel) {
					plantHierarchyRef.set(rootLevel);
				}
			} else {
				plantHierarchyRef.set(loadPlantHierarchy(project));
			}
		});

		return plantHierarchyRef.get();
	}

	public static String getOldPath(final IResource element) {
		return element.getProjectRelativePath().toPortableString();
	}

	public static String getNewPath(final IResource element, final String newName) {
		return element.getProjectRelativePath().removeLastSegments(1).append(newName).toPortableString();
	}

	public static String getDestinationPath(final IResource element, final IFolder destination) {

		return destination.getProjectRelativePath().append(element.getName()).toPortableString();
	}

	public static RootLevel loadPlantHierarchy(final IProject project) {

		final Map<String, Object> loadOptions = new HashMap<>();
		final ResourceSet hierarchyResouceSet = new ResourceSetImpl();

		hierarchyResouceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put( //
				PlantHierarchyView.PLANT_HIERARCHY_FILE_NAME_EXTENSION, //
				new HierarchyResourceFactoryImpl());

		hierarchyResouceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put( //
				PlantHierarchyView.PLANT_HIERARCHY_FILE_NAME_EXTENSION.toLowerCase(), //
				new HierarchyResourceFactoryImpl());
		loadOptions.put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);

		final XMLMapImpl map = new XMLMapImpl();

		map.setNoNamespacePackage(HierarchyPackage.eINSTANCE);
		loadOptions.put(XMLResource.OPTION_XML_MAP, map);
		hierarchyResouceSet.getLoadOptions().put(XMLResource.OPTION_XML_MAP, map);

		return (RootLevel) PlantHierarchyView.loadHierachyForProject(project, hierarchyResouceSet, loadOptions);
	}
}
