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
package org.eclipse.fordiac.ide.systemmanagement.ui.systemexplorer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.fordiac.ide.model.data.provider.DataItemProviderAdapterFactory;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.systemmanagement.DistributedSystemListener;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;

public class SystemContentProvider extends AdapterFactoryContentProvider implements DistributedSystemListener {

	private static ComposedAdapterFactory systemAdapterFactory = new ComposedAdapterFactory(createFactoryList());

	private BaseWorkbenchContentProvider workbenchContentProvider = new BaseWorkbenchContentProvider();

	public SystemContentProvider() {
		super(systemAdapterFactory);
		SystemManager.INSTANCE.addWorkspaceListener(this);
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof IResource) {
			Object[] children = workbenchContentProvider.getChildren(parentElement);
			List<Object> newChildren = new ArrayList<>(children.length);
			for (Object child : children) {
				if ((child instanceof IFile) && ("sys".equalsIgnoreCase(((IFile) child).getFileExtension()))) {
					child = SystemManager.INSTANCE.getSystem((IFile) child);
				}
				newChildren.add(child);
			}
			return newChildren.toArray();
		}
		return super.getChildren(parentElement);
	}

	@Override
	public Object getParent(Object object) {
		if (object instanceof AutomationSystem) {
			return ((AutomationSystem) object).getSystemFile().getParent();
		}
		return super.getParent(object);
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof IProject) {
			return (null != SystemManager.INSTANCE.getSystemForName(((IProject) element).getName()));
		}
		return super.hasChildren(element);
	}

	private static List<AdapterFactory> createFactoryList() {
		ArrayList<AdapterFactory> factories = new ArrayList<>();
		factories.add(new SystemElementItemProviderAdapterFactory());
		factories.add(new DataItemProviderAdapterFactory());
		return factories;
	}

	@Override
	public void distributedSystemWorkspaceChanged() {
		if (null != viewer && null != viewer.getControl() && null != viewer.getControl().getDisplay()) {
			viewer.getControl().getDisplay().asyncExec(() -> viewer.refresh());
		}
	}

}
