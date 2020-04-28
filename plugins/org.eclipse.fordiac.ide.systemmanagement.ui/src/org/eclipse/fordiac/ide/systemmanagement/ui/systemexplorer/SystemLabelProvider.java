/*******************************************************************************
 * Copyright (c) 2015 fortiss GmbH
 * 				 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Gerd Kainz
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Reworked system explorer layout
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.systemexplorer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.fordiac.ide.model.data.provider.DataItemProviderAdapterFactory;
import org.eclipse.fordiac.ide.model.libraryElement.provider.LibraryElementItemProviderAdapterFactory;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.navigator.IDescriptionProvider;

public class SystemLabelProvider extends AdapterFactoryLabelProvider implements IDescriptionProvider {

	private static ComposedAdapterFactory systemAdapterFactory = new ComposedAdapterFactory(createFactoryList());

	public SystemLabelProvider() {
		super(systemAdapterFactory);
	}

	@Override
	public String getText(Object object) {
		if (object instanceof IResource) {
			return null;
		}
		return super.getText(object);
	}

	@Override
	public Image getImage(Object object) {
		if (object instanceof IResource) {
			return getImageForResource((IResource) object);
		}
		return super.getImage(object);
	}

	private static Image getImageForResource(IResource resource) {
		if (FordiacProjectSorter.isSystemFile(resource)) {
			// provide the icon for the system configuration file,
			// TODO this should in the future provided by a dedicated editor
			return FordiacImage.ICON_SYSTEM_PERSPECTIVE.getImage();
		}

		if (FordiacProjectSorter.isTypeLibFolder(resource)) {
			return FordiacImage.ICON_TYPE_NAVIGATOR.getImage();
		}

		return null;
	}

	@Override
	public String getDescription(Object anElement) {
		// TODO provide descriptive tooltip text here
		return super.getText(anElement);
	}

	private static List<AdapterFactory> createFactoryList() {
		ArrayList<AdapterFactory> factories = new ArrayList<>();
		factories.add(new LibraryElementItemProviderAdapterFactory());
		factories.add(new DataItemProviderAdapterFactory());
		return factories;
	}
}
