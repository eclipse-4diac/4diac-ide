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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.fordiac.ide.model.data.provider.DataItemProviderAdapterFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.systemmanagement.changelistener.DistributedSystemListener;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public class SystemContentProvider extends AdapterFactoryContentProvider implements DistributedSystemListener {

	private static ComposedAdapterFactory systemAdapterFactory = new ComposedAdapterFactory(createFactoryList());

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
		if (parentElement instanceof IResource) {
			return getResourceChildren((IResource) parentElement);
		}
		return super.getChildren(parentElement);
	}

	@Override
	public Object getParent(final Object object) {
		if (object instanceof IResource) {
			return ((IResource) object).getParent();
		}
		if (object instanceof Application) {
			final Application app = (Application) object;
			// the automation system can be null if the the app was just deleted
			return (null != app.getAutomationSystem()) ? app.getAutomationSystem().getSystemFile() : null;
		}
		if (object instanceof SystemConfiguration) {
			return ((SystemConfiguration) object).getAutomationSystem().getSystemFile();
		}
		if (object instanceof AutomationSystem) {
			return ((AutomationSystem) object).getSystemFile();
		}
		return super.getParent(object);
	}

	@Override
	public boolean hasChildren(final Object element) {
		if (element instanceof IResource) {
			if (element instanceof IProject) {
				return ((IProject) element).isAccessible();
			}
			if (SystemManager.isSystemFile(element)) {
				return true;
			}
		}
		return super.hasChildren(element);
	}

	private static List<AdapterFactory> createFactoryList() {
		final ArrayList<AdapterFactory> factories = new ArrayList<>();
		factories.add(new SystemElementItemProviderAdapterFactory());
		factories.add(new DataItemProviderAdapterFactory());
		return factories;
	}

	@Override
	public void notifyChanged(final Notification notification) {
		if (notification.getNotifier() instanceof AutomationSystem) {
			// as the automation system is changed we need to perform a special refresh here
			final AutomationSystem system = (AutomationSystem) notification.getNotifier();
			super.notifyChanged(new ViewerNotification(notification, system.getSystemFile()));
		} else {
			super.notifyChanged(notification);
			final int featureID = notification.getFeatureID(ConfigurableObject.class);
			if (LibraryElementPackage.INAMED_ELEMENT == featureID
					|| (LibraryElementPackage.INAMED_ELEMENT__COMMENT == featureID
					&& notification.getNotifier() instanceof Group)) {
				// trigger a resorting
				distributedSystemWorkspaceChanged();
			}
		}
	}

	@Override
	public void distributedSystemWorkspaceChanged() {
		if (null != viewer && null != viewer.getControl() && null != viewer.getControl().getDisplay()) {
			viewer.getControl().getDisplay().asyncExec(() -> viewer.refresh());
		}
	}

	private Object[] getResourceChildren(final IResource resource) {
		if (resource instanceof IWorkspaceRoot) {
			// show only 4diac or closed projects
			final IWorkspaceRoot root = (IWorkspaceRoot) resource;
			return Arrays.stream(root.getProjects()).filter(SystemContentProvider::projectToShow)
					.collect(Collectors.toList())
					.toArray(new IProject[0]);
		}
		if (((resource instanceof IProject) && ((IProject) resource).isOpen()) || (resource instanceof IFolder)) {
			try {
				return ((IContainer) resource).members();
			} catch (final CoreException e) {
				FordiacLogHelper.logError("Could not read project children", e); //$NON-NLS-1$
			}
		}

		if (SystemManager.isSystemFile(resource)) {
			// retrieve the children for the Automation system
			return super.getChildren(SystemManager.INSTANCE.getSystem((IFile) resource));
		}

		return new Object[0];
	}

	private static boolean projectToShow(final IProject proj) {
		// if the project is closed or a 4diac project return true
		try {
			return !proj.isOpen() || proj.hasNature(SystemManager.FORDIAC_PROJECT_NATURE_ID) || proj.hasNature(SystemManager.ROBOT_PROJECT_NATURE_ID);
		} catch (final CoreException e) {
			FordiacLogHelper.logError("Could not read project nature", e); //$NON-NLS-1$
		}
		return false;
	}

}
