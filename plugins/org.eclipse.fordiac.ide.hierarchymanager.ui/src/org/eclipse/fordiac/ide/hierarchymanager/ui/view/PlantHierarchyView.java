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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.hierarchymanager.model.HierarchyManagerPersistenceHelper;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
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
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class PlantHierarchyView extends CommonNavigator implements ITabbedPropertySheetPageContributor {

	private static final String PLANT_HIERARCHY_PROJECT = "PlantHierarchy.Project"; //$NON-NLS-1$

	/** The PROPERTY_CONTRIBUTOR_ID. */
	public static final String PROPERTY_CONTRIBUTOR_ID = "org.eclipse.fordiac.ide.hierarchymanager.ui.view"; //$NON-NLS-1$

	private IProject currentProject;

	@Override
	protected void setPartName(final String partName) {
		if (currentProject != null) {
			super.setPartName(partName + ": " + currentProject.getName()); //$NON-NLS-1$
		} else {
			super.setPartName(partName);
		}
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
					// we can not use setInput here as getInitialInput is interacting with the
					// viewer in the base class
					currentProject = project;
					return HierarchyManagerPersistenceHelper.loadPlantHierarchy(currentProject);
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

	public void setInput(final IProject proj) {
		if (currentProject != proj) {
			// the new project is different set
			currentProject = proj;
			getCommonViewer().setInput(HierarchyManagerPersistenceHelper.loadPlantHierarchy(proj));
			setPartName(getConfigurationElement().getAttribute("name")); //$NON-NLS-1$
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

	@Override
	public String getContributorId() {
		return PROPERTY_CONTRIBUTOR_ID;
	}

	@Override
	public <T> T getAdapter(final Class<T> adapter) {
		if (adapter == IPropertySheetPage.class) {
			return adapter.cast(new TabbedPropertySheetPage(this));
		}
		return super.getAdapter(adapter);
	}
}
