/*******************************************************************************
 * Copyright (c) 2011, 2024 TU Wien ACIN, fortiss GmbH
 * 							Primetals Technologies Austria GmbH
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
import org.eclipse.emf.common.notify.impl.NotificationImpl;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.ui.editors.LibraryElementProvider;
import org.eclipse.fordiac.ide.model.ui.editors.LibraryElementStateListener;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.part.FileEditorInput;

public class LibraryElementContentProvider extends AdapterFactoryContentProvider {

	private final List<Notifier> targets = new ArrayList<>();
	private final Adapter typeEntryAdapter = new AdapterImpl() {
		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			if (notification.getNotifier() instanceof final TypeEntry entry
					&& TypeEntry.TYPE_ENTRY_TYPE_FEATURE.equals(notification.getFeature())) {
				LibraryElementContentProvider.super.notifyChanged(
						new ViewerNotification(notification, entry.getFile()));
			}
		}
	};
	private final LibraryElementStateListener elementStateListener = new LibraryElementStateListener() {

		@Override
		public void elementConnected(final IEditorInput input) {
			fireElementChanged(input);
		}

		@Override
		public void elementDisconnected(final IEditorInput input) {
			fireElementChanged(input);
		}

		@Override
		public void elementContentReplaced(final IEditorInput input) {
			fireElementChanged(input);
		}

		private void fireElementChanged(final IEditorInput input) {
			if (input instanceof final IFileEditorInput fileEditorInput) {
				LibraryElementContentProvider.super.notifyChanged(new ViewerNotification(
						new NotificationImpl(Notification.SET, fileEditorInput.getFile(), fileEditorInput.getFile()),
						fileEditorInput.getFile()));
			}
		}
	};

	public LibraryElementContentProvider(final AdapterFactory adapterFactory) {
		super(adapterFactory);
		LibraryElementProvider.INSTANCE.addLibraryElementStateListener(elementStateListener);
	}

	@Override
	public Object[] getElements(final Object inputElement) {
		return getChildren(inputElement);
	}

	@Override
	public Object[] getChildren(final Object parentElement) {
		if (parentElement instanceof final IFile file) {
			final TypeEntry entry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(file);
			if (entry == null) {
				return new Object[0];
			}

			hookToTypeEntry(entry);

			final LibraryElement libraryElement = getLibraryElement(file, entry);
			if (libraryElement == null) {
				return new Object[0];
			}
			return super.getChildren(libraryElement);
		}

		return super.getChildren(parentElement);
	}

	protected static LibraryElement getLibraryElement(final IFile file, final TypeEntry entry) {
		final LibraryElement libraryElement = LibraryElementProvider.INSTANCE
				.getLibraryElement(new FileEditorInput(file));
		if (libraryElement != null) {
			return libraryElement;
		}
		return entry.getType();
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
		LibraryElementProvider.INSTANCE.addLibraryElementStateListener(elementStateListener);
		targets.forEach(entry -> entry.eAdapters().remove(typeEntryAdapter));
		targets.clear();
		super.dispose();
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
