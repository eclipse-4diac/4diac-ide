/*******************************************************************************
 * Copyright (c) 2023 Primetals Technology Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.hierarchymanager.ui.view;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLMapImpl;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.HierarchyPackage;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.util.HierarchyResourceFactoryImpl;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.util.HierarchyResourceImpl;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.part.ShowInContext;

public class PlantHierarchyView extends CommonNavigator {

	private static final String PLANT_HIERARCHY_PROJECT = "PlantHierarchy.Project"; //$NON-NLS-1$
	private static final String PLANT_HIERARCHY_FILE_NAME = ".plant.hier"; //$NON-NLS-1$
	private static final String PLANT_HIERARCHY_FILE_NAME_EXTENSION = "hier"; //$NON-NLS-1$

	final Map<String, Object> loadOptions = new HashMap<>();
	private final ResourceSet hierarchyResouceSet = new ResourceSetImpl();

	private IProject currentProject;

	public PlantHierarchyView() {
		setupEMFInfra();
	}

	@Override
	public boolean show(final ShowInContext context) {
		if (context == null) {
			return false;
		}
		final ISelection selection = context.getSelection();
		if (selection instanceof final IStructuredSelection structSel && !selection.isEmpty()) {
			final IProject selProject = getSelectedProject(structSel.getFirstElement());
			if (selProject != null) {
				setInput(selProject);
				return true;
			}
		}
		return super.show(context);
	}

	@Override
	public void saveState(final IMemento aMemento) {
		super.saveState(aMemento);
		if (currentProject != null) {
			aMemento.putString(PLANT_HIERARCHY_PROJECT, currentProject.getName());
		}
	}

	@Override
	protected Object getInitialInput() {
		if (memento != null) {
			final String projectName = memento.getString(PLANT_HIERARCHY_PROJECT);
			if (projectName != null) {
				final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
				if (project != null && project.exists() && project.isOpen()) {
					// we can not use setInput here as getInitialInput is interacting with the viewer in the base class
					currentProject = project;
					return loadHierachyForProject(currentProject);
				}
			}
		}
		return super.getInitialInput();
	}

	@Override
	public void createPartControl(final Composite aParent) {
		super.createPartControl(aParent);
		final Tree treeWidget = getCommonViewer().getTree();
		treeWidget.addListener(SWT.MouseDown, event -> {
			final TreeItem item = treeWidget.getItem(new Point(event.x, event.y));
			if (item == null) {
				// No tree item at the click location deselect everything
				getCommonViewer().setSelection(TreeSelection.EMPTY);
			}
		});
	}

	public IProject getCurrentProject() {
		return currentProject;
	}

	private void setInput(final IProject proj) {
		if (currentProject != proj) {
			// the new project is different set
			currentProject = proj;
			getCommonViewer().setInput(loadHierachyForProject(proj));
		}
	}

	private static IProject getSelectedProject(final Object firstElement) {
		if (firstElement instanceof final EObject eObj) {
			return getProjectFromEObject(eObj);
		}

		if (firstElement instanceof final IResource res) {
			return res.getProject();
		}

		return firstElement instanceof final IProject proj ? proj : null;
	}

	private static IProject getProjectFromEObject(final EObject eObj) {
		final EObject rootContainer = EcoreUtil.getRootContainer(eObj);

		if (rootContainer instanceof final LibraryElement le) {
			return le.getTypeEntry().getFile().getProject();
		}
		return null;
	}

	private EObject loadHierachyForProject(final IProject proj) {
		final IFile file = proj.getFile(PLANT_HIERARCHY_FILE_NAME);
		if (file.exists()) {
			final URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
			// we don't want to load the resource content as we can not give the mapping options
			Resource resource = hierarchyResouceSet.getResource(uri, true);
			try {
				if (resource == null) {
					resource = new HierarchyResourceImpl(uri);
					hierarchyResouceSet.getResources().add(resource);
					resource.load(loadOptions);
				}
				return resource.getContents().get(0);
			} catch (final IOException e) {
				FordiacLogHelper.logWarning("Could not load plant hierarchy", e); //$NON-NLS-1$
			}
		}
		return null;
	}

	private void setupEMFInfra() {
		// add file extension to registry
		hierarchyResouceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
		.put(PLANT_HIERARCHY_FILE_NAME_EXTENSION, new HierarchyResourceFactoryImpl());
		setupLoadOptions();
	}

	private void setupLoadOptions() {
		loadOptions.put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);
		final XMLMapImpl map = new XMLMapImpl();
		map.setNoNamespacePackage(HierarchyPackage.eINSTANCE);
		loadOptions.put(XMLResource.OPTION_XML_MAP, map);
		hierarchyResouceSet.getLoadOptions().put(XMLResource.OPTION_XML_MAP, map);
	}
}
