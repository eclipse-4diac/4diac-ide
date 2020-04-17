/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
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
package org.eclipse.fordiac.ide.systemmanagement.ui.systemexplorer;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.systemmanagement.ui.Activator;
import org.eclipse.jface.viewers.ITreeContentProvider;

public class SystemExplorerRootContentProvider implements ITreeContentProvider {

	@Override
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof IWorkspaceRoot) {
			// show only 4diac or closed projects
			IWorkspaceRoot root = (IWorkspaceRoot) parentElement;
			return Arrays.stream(root.getProjects()).filter(SystemExplorerRootContentProvider::projectToShow)
					.collect(Collectors.toList()).toArray(new IProject[0]);
		}
		if (parentElement instanceof IProject) {
			try {
				return ((IProject) parentElement).members();
			} catch (CoreException e) {
				Activator.getDefault().logError("Could not read project children", e); //$NON-NLS-1$
			}
		}
		return new Object[0];
	}

	private static boolean projectToShow(IProject proj) {
		// if the project is closed or a 4diac project return true
		try {
			return !proj.isOpen() || proj.hasNature(SystemManager.FORDIAC_PROJECT_NATURE_ID);
		} catch (CoreException e) {
			Activator.getDefault().logError("Could not read project nature", e); //$NON-NLS-1$
		}
		return false;
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof IResource) {
			return ((IResource) element).getParent();
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof IProject) {
			return ((IProject) element).isAccessible();
		}
		return false;
	}

}
