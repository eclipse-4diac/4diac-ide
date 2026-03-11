/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Mario Kastner - initial API and implementation
 *******************************************************************************/

package org.eclipse.fordiac.ide.systemmanagement.ui.systemexplorer;

import java.text.MessageFormat;

import org.eclipse.core.resources.IFolder;
import org.eclipse.fordiac.ide.library.model.library.Manifest;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;

public class LibraryFolderDecorator implements ILightweightLabelDecorator {

	private static final String VERSION_FORMAT = " [{0}]"; //$NON-NLS-1$

	@Override
	public void addListener(final ILabelProviderListener listener) {
		// do nothing
	}

	@Override
	public void dispose() {
		// do nothing
	}

	@Override
	public boolean isLabelProperty(final Object element, final String property) {
		return false;
	}

	@Override
	public void removeListener(final ILabelProviderListener listener) {
		// do nothing
	}

	@Override
	public void decorate(final Object element, final IDecoration decoration) {
		if ((element instanceof final IFolder folder) && isLinkedLibraryFolder(folder)) {
			final Manifest manifest = ManifestHelper.getContainerManifest(folder);
			if (manifest == null) {
				return;
			}
			final String version = manifest.getProduct().getVersionInfo().getVersion();
			decoration.addSuffix(MessageFormat.format(VERSION_FORMAT, version));
		}
	}

	private static boolean isLinkedLibraryFolder(final IFolder folder) {
		if (folder.getParent() != null) {
			final String parentName = folder.getParent().getName();
			return parentName.equals(TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME)
					|| parentName.equals(TypeLibraryTags.STANDARD_LIB_FOLDER_NAME);
		}
		return false;
	}

}
