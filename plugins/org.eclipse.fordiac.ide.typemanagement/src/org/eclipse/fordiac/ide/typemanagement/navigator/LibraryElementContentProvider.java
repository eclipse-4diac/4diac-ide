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
 *   Patrick Aigner
 *     - moved methods from FBTypeContentProvider into this shared base class
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.navigator;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;

public class LibraryElementContentProvider extends AdapterFactoryContentProvider {

	protected List<Notifier> targets = new ArrayList<>();

	protected final Adapter typeEntryAdapter = new AdapterImpl() {
		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			final Object feature = notification.getFeature();
			if (TypeEntry.TYPE_ENTRY_TYPE_FEATURE.equals(feature)
					|| TypeEntry.TYPE_ENTRY_TYPE_EDITABLE_FEATURE.equals(feature)) {
				final TypeEntry entry = (TypeEntry) notification.getNotifier();
				LibraryElementContentProvider.super.notifyChanged(
						new ViewerNotification(notification, entry.getFile()));
			}
		}
	};

	public LibraryElementContentProvider(final AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	@Override
	public Object[] getElements(final Object inputElement) {
		return getChildren(inputElement);
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		parentElement = getTypeParent(parentElement);
		return super.getChildren(parentElement);
	}

	@Override
	public Object getParent(final Object object) {
		final Object parent = super.getParent(object);
		if (parent instanceof final LibraryElement libElement) {
			return libElement.getTypeEntry().getFile();
		}
		return parent;
	}

	/**
	 * Get the correct type if it exists
	 *
	 * Note: use in {@link #getChildren(Object)} to obtain the correct object
	 *
	 * @param parentElement
	 * @return the parentElement or its respective type
	 */
	protected Object getTypeParent(final Object parentElement) {
		TypeEntry entry = null;
		if (parentElement instanceof final IFile element) {
			entry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(element);
		} else if (parentElement instanceof final LibraryElement element) {
			entry = element.getTypeEntry();
		}
		if (null != entry) {
			hookToTypeEntry(entry);
			return entry.getTypeEditable();
		}
		return parentElement;
	}

	/**
	 * attaches adapter for notifications
	 *
	 * @param entry the {@link TypeEntry} to attach to
	 */
	protected void hookToTypeEntry(final TypeEntry entry) {
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
	public void notifyChanged(final Notification notification) {
		if (notification.getNotifier() instanceof final LibraryElement libElement) {
			// as the automation system is changed we need to perform a special refresh here
			super.notifyChanged(new ViewerNotification(notification, libElement.getTypeEntry().getFile()));
		} else {
			super.notifyChanged(notification);
		}
	}
}
