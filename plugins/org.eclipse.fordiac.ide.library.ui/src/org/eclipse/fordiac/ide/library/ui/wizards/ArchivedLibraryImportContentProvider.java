/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.ui.wizards;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.fordiac.ide.library.LibraryManager;
import org.eclipse.jface.viewers.ITreeContentProvider;

public class ArchivedLibraryImportContentProvider implements ITreeContentProvider {
	private static final Object[] EMPTY_ARR = new Object[0];

	@Override
	public Object[] getElements(final Object inputElement) {
		return ((Path[]) inputElement);
	}

	@Override
	public Object[] getChildren(final Object parentElement) {
		if (parentElement instanceof final Path path && Files.isDirectory(path)) {
			return LibraryManager.INSTANCE.listArchiveFolders(path);
		}
		return EMPTY_ARR;
	}

	@Override
	public Object getParent(final Object element) {
		if (element instanceof final File file) {
			return file.getParentFile();
		}
		return null;
	}

	@Override
	public boolean hasChildren(final Object element) {
		return element instanceof final Path path && Files.isDirectory(path);
	}
}
