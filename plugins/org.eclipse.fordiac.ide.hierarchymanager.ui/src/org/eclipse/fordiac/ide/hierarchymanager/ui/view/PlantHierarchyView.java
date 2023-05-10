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
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.part.ShowInContext;

public class PlantHierarchyView extends CommonNavigator {

	private static final String PLANT_HIERARCHY_PROJECT = "PlantHierarchy.Project"; //$NON-NLS-1$

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
		final Object input = getCommonViewer().getInput();
		if (input instanceof final IProject project) {
			aMemento.putString(PLANT_HIERARCHY_PROJECT, project.getName());
		}
	}

	@Override
	protected Object getInitialInput() {
		if (memento != null) {
			final String projectName = memento.getString(PLANT_HIERARCHY_PROJECT);
			if (projectName != null) {
				final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
				if (project != null && project.exists() && project.isOpen()) {
					return project;
				}
			}
		}
		return super.getInitialInput();
	}

	private void setInput(final IProject proj) {
		if (getCommonViewer().getInput() != proj) {
			// the new project is different set
			getCommonViewer().setInput(proj);
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
}
