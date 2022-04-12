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
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PalettePackage;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFBType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
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
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

public class FBTypeContentProvider extends AdapterFactoryContentProvider {

	private List<Notifier> targets = new ArrayList<>();

	private final Adapter palletteEntryAdapter = new AdapterImpl() {
		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			final Object feature = notification.getFeature();
			if (PalettePackage.eINSTANCE.getPaletteEntry_TypeEditable().equals(feature)) {
				final PaletteEntry entry = (PaletteEntry) notification.getNotifier();
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
		if (parentElement instanceof IFile) {
			final IFile element = (IFile) parentElement;
			final TypeEntry entry = TypeLibrary.getTypeEntryForFile(element);
			if (null != entry) {
				hookToPaletteEntry(entry);
				parentElement = entry.getTypeEditable();
				if (parentElement instanceof AdapterType) {
					parentElement = ((AdapterType) parentElement).getAdapterFBType();
				}
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

	private void hookToPaletteEntry(final TypeEntry entry) {
		// FIXME Bug 579648 readd that when TypeEntry implements a notifier again
		// if (!entry.eAdapters().contains(palletteEntryAdapter)) {
		// entry.eAdapters().add(palletteEntryAdapter);
		// targets.add(entry);
		// }
	}

	@Override
	public void dispose() {
		super.dispose();
		targets.forEach(entry -> entry.eAdapters().remove(palletteEntryAdapter));
		targets = null;
	}

	@Override
	public Object getParent(final Object element) {
		if (element instanceof IFile) {
			return ((IResource) element).getParent();
		}
		final Object retval = super.getParent(element);
		if (retval instanceof FBType) {
			return ((FBType) retval).getTypeEntry().getFile();
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
			LibraryElement type = (LibraryElement) notification.getNotifier();
			if (type instanceof AdapterFBType) {
				type = ((AdapterFBType) type).getAdapterType();
			}
			super.notifyChanged(new ViewerNotification(notification, type.getTypeEntry().getFile()));
		} else {
			super.notifyChanged(notification);
		}
	}

}
