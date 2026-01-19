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
 *   Alois Zoitl, Gerd Kainz
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.navigator;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

public class ToolLibraryLabelProvider implements ILabelProvider {

	@Override
	public void addListener(final ILabelProviderListener listener) {
		// currently nothing to be done here
	}

	@Override
	public void dispose() {
		// currently nothing to be done here
	}

	@Override
	public boolean isLabelProperty(final Object element, final String property) {
		return false;
	}

	@Override
	public void removeListener(final ILabelProviderListener listener) {
		// currently nothing to be done here
	}

	@Override
	public Image getImage(final Object element) {
		if (getText(element) != null) {
			return FordiacImage.ICON_TYPE_NAVIGATOR.getImage();
		}
		return null;
	}

	@Override
	public String getText(final Object element) {
		if (element instanceof final IFolder folder && folder.getName().equals(TypeLibraryTags.TYPE_LIB_FOLDER_NAME)) {
			final IContainer parent = folder.getParent();
			// only for folders that sit at root of project
			if (parent != null && parent.getFullPath().segmentCount() == 1) {
				return parent.getName(); // use name of project for its Type Library
			}
		}
		return null;
	}
}
