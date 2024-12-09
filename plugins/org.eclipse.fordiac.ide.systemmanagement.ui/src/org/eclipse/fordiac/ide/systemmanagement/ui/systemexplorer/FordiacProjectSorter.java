/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.systemexplorer;

import java.util.Comparator;

import org.eclipse.core.resources.IFolder;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;

public class FordiacProjectSorter extends ViewerComparator {

	public FordiacProjectSorter() {
		// nothing special to do here
	}

	public FordiacProjectSorter(final Comparator<? super String> comparator) {
		super(comparator);
	}

	@Override
	public int compare(final Viewer viewer, final Object e1, final Object e2) {
		if (SystemManager.isSystemFile(e1)) {
			if (SystemManager.isSystemFile(e2)) {
				// both are system files: sort alphabetically
				return super.compare(viewer, e1, e2);
			}
			return -1; // system file content before everything else
		}
		if (SystemManager.isSystemFile(e2)) {
			return 1; // the second is a system file order it before the first
		}

		if (isTypeLibFolder(e1)) {
			return -1;
		}

		if (isTypeLibFolder(e2)) {
			return 1;
		}

		if (isLibFolder(e1)) {
			if (isLibFolder(e2)) {
				return super.compare(viewer, e1, e2);
			}
			return -1;
		}

		if (isLibFolder(e2)) {
			return 1;
		}

		// sort the rest alphabetically
		return super.compare(viewer, e1, e2);
	}

	public static boolean isTypeLibFolder(final Object entry) {
		return ((entry instanceof final IFolder folder)
				&& TypeLibraryTags.TYPE_LIB_FOLDER_NAME.equals(folder.getName()));
	}

	public static boolean isLibFolder(final Object entry) {
		return ((entry instanceof final IFolder folder)
				&& (TypeLibraryTags.STANDARD_LIB_FOLDER_NAME.equals(folder.getName())
						|| TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME.equals(folder.getName())));
	}
}
