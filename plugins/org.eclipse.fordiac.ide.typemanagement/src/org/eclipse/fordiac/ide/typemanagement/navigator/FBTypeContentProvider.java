/*******************************************************************************
 * Copyright (c) 2011 - 2024 TU Wien ACIN, fortiss GmbH
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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;

public class FBTypeContentProvider extends AdapterFactoryContentProvider {

	private List<Notifier> targets = new ArrayList<>();

	private final Adapter typeEntryAdapter = new AdapterImpl() {
		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			final Object feature = notification.getFeature();
			if (TypeEntry.TYPE_ENTRY_TYPE_EDITABLE_FEATURE.equals(feature)) {
				final TypeEntry entry = (TypeEntry) notification.getNotifier();
				FBTypeContentProvider.super.notifyChanged(new ViewerNotification(notification, entry.getFile()));
			}
		}
	};

	public FBTypeContentProvider() {
		super(FBTypeComposedAdapterFactory.getAdapterFactory());
	}

	@Override
	public Object[] getElements(final Object inputElement) {
		return getChildren(inputElement);

	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof final IFile element) {
			final TypeEntry entry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(element);
			if (null != entry) {
				hookToTypeEntry(entry);
				parentElement = entry.getTypeEditable();
			}
		}
		if ((parentElement instanceof AutomationSystem) || (parentElement instanceof Application)
				|| (parentElement instanceof SystemConfiguration) || (parentElement instanceof FB)
				|| (parentElement instanceof Device) || (parentElement instanceof Resource)
				|| (parentElement instanceof SubApp)) {
			return new Object[0];
		}
		return super.getChildren(parentElement);
	}

	private void hookToTypeEntry(final TypeEntry entry) {
		if (!entry.eAdapters().contains(typeEntryAdapter)) {
			entry.eAdapters().add(typeEntryAdapter);
			targets.add(entry);
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		targets.forEach(entry -> entry.eAdapters().remove(typeEntryAdapter));
		targets = null;
	}

	@Override
	public Object getParent(final Object element) {
		if (element instanceof IFile) {
			return ((IResource) element).getParent();
		}
		if (element instanceof final FBType fbtype) {
			return fbtype.getTypeEntry().getFile();
		}
		final Object retval = super.getParent(element);
		if (retval instanceof final FBType fbtype) {
			return fbtype.getTypeEntry().getFile();
		}
		return retval;
	}

	@Override
	public boolean hasChildren(final Object element) {
		if ((element instanceof AutomationSystem) || (element instanceof Application)
				|| (element instanceof SystemConfiguration) || (element instanceof FB) || (element instanceof Device)
				|| (element instanceof Resource) || (element instanceof SubApp)) {
			return false;
		}
		if (element instanceof IFile) {
			return true;
		}
		return super.hasChildren(element);
	}

	@Override
	public void notifyChanged(final Notification notification) {
		if (notification.getNotifier() instanceof FBType) {
			// as the automation system is changed we need to perform a special refresh here
			final LibraryElement type = (LibraryElement) notification.getNotifier();
			super.notifyChanged(new ViewerNotification(notification, type.getTypeEntry().getFile()));
		} else {
			super.notifyChanged(notification);
		}
	}

}
