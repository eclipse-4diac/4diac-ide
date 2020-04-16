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
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.fordiac.ide.model.data.provider.DataItemProviderAdapterFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.systemmanagement.DistributedSystemListener;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;

public class SystemContentProvider extends AdapterFactoryContentProvider implements DistributedSystemListener {

	private static ComposedAdapterFactory systemAdapterFactory = new ComposedAdapterFactory(createFactoryList());

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
		if (parentElement instanceof IFile) {
			// retrieve the children for the Automation system
			return super.getChildren(SystemManager.INSTANCE.getSystem((IFile) parentElement));
		}
		return super.getChildren(parentElement);
	}

	@Override
	public Object getParent(Object object) {
		if (object instanceof Application) {
			return ((Application) object).getAutomationSystem().getSystemFile();
		}
		if (object instanceof SystemConfiguration) {
			return ((SystemConfiguration) object).getAutomationSystem().getSystemFile();
		}
		return super.getParent(object);
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof IFile) {
			// if we are here the file is a system configuration file. For this we always
			// have children
			return true;
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
