/*******************************************************************************
 * Copyright (c) 2015, 2016 fortiss GmbH
 * 				 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - New Project Explorer layout
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.providers;

import java.util.Arrays;
import java.util.Collections;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.systemmanagement.changelistener.DistributedSystemListener;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public class SystemContentProvider extends AdapterFactoryContentProvider implements DistributedSystemListener {

	private static ComposedAdapterFactory systemAdapterFactory = new ComposedAdapterFactory(Collections.emptyList());

	public SystemContentProvider() {
		super(systemAdapterFactory);
		SystemManager.INSTANCE.addWorkspaceListener(this);
	}

	@Override
	public Object[] getElements(final Object inputElement) {
		return getChildren(inputElement);
	}

	@Override
	public Object[] getChildren(final Object parentElement) {
		if (parentElement instanceof final IWorkspaceRoot root) {
			return Arrays.stream(root.getProjects()).filter(SystemContentProvider::projectToShow).toArray();
		}
		return new Object[0];
	}

	@Override
	public Object getParent(final Object object) {
		if (object instanceof final IResource ires) {
			return ires.getParent();
		}
		return null;
	}

	@Override
	public boolean hasChildren(final Object element) {
		if (element instanceof IResource) {
			if (element instanceof final IProject project) {
				return project.isAccessible();
			}
			if (SystemManager.isSystemFile(element)) {
				return true;
			}
		}
		return super.hasChildren(element);
	}

	@Override
	public void distributedSystemWorkspaceChanged() {
		if (null != viewer && null != viewer.getControl() && null != viewer.getControl().getDisplay()) {
			viewer.getControl().getDisplay().asyncExec(() -> {
				if (null != viewer && null != viewer.getControl() && !viewer.getControl().isDisposed()) {
					viewer.refresh();
				}
			});
		}
	}

	private static boolean projectToShow(final IProject proj) {
		// if the project is closed or a 4diac project return true
		try {
			return !proj.isOpen() || proj.hasNature(SystemManager.FORDIAC_PROJECT_NATURE_ID)
					|| proj.hasNature(SystemManager.ROBOT_PROJECT_NATURE_ID);
		} catch (final CoreException e) {
			FordiacLogHelper.logError("Could not read project nature", e); //$NON-NLS-1$
		}
		return false;
	}

}
