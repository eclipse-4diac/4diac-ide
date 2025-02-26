/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Qemal Alliu - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.hierarchymanager.ui.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

public class HierarchyManagerRefactoringUtil {

	private static List<String> ALLOWED_FILE_EXTENSIONS = List.of("sys", "sub"); //$NON-NLS-1$ //$NON-NLS-2$

	public static List<IFile> getFilesFromResource(final IResource resource) throws CoreException {
		final List<IFile> files = new ArrayList<>();

		if (resource instanceof final IFile file && ALLOWED_FILE_EXTENSIONS.contains(resource.getFileExtension())) {
			files.add(file);
		} else if (resource instanceof final IFolder folder) {
			for (final IResource member : folder.members()) {
				files.addAll(getFilesFromResource(member));
			}
		}

		return files;
	}
}
