/*******************************************************************************
 * Copyright (c) 2011 - 2017 TU Wien ACIN, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.navigator;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class ToolLibraryContentProvider implements ITreeContentProvider {

	@Override
	public void dispose() {
		// currently nothing to do here
	}

	@Override
	public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
		// currently nothing to do here
	}

	@Override
	public Object[] getElements(final Object inputElement) {

		if ((null == inputElement) || (inputElement instanceof IWorkspaceRoot)) {
			// this content provider is only required on the lowest level of the tree
			final IWorkspaceRoot myWorkspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
			return myWorkspaceRoot.getProjects();
		}
		if (inputElement instanceof final IContainer container) {
			try {
				return container.members();
			} catch (final CoreException e) {
				FordiacLogHelper.logError(e.getMessage(), e);
			}
		}

		return new Object[0];
	}

	@Override
	public Object[] getChildren(final Object parentElement) {
		return new Object[0];
	}

	@Override
	public Object getParent(final Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(final Object element) {
		return false;
	}

}
