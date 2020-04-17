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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;

public class FordiacProjectSorter extends ViewerComparator {

	private static final String SYSTEM_FILE_EXTENSION = "sys"; //$NON-NLS-1$
	private static final String TYPE_LIB_FOLDER_NAME = "Type Library"; //$NON-NLS-1$

	public FordiacProjectSorter() {
		// nothing special to do here
	}

	public FordiacProjectSorter(Comparator<? super String> comparator) {
		super(comparator);
	}

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		if (isSystemFile(e1)) {
			if (isSystemFile(e2)) {
				// both are system files: sort alphabetically
				return super.compare(viewer, e1, e2);
			}
			return -1; // system file content before everything else
		}
		if (isSystemFile(e2)) {
			return 1; // the second is a system file order it before the first
		}

		if (isTypeLibFolder(e1)) {
			return -1;
		}

		if (isTypeLibFolder(e2)) {
			return 1;
		}
		// sort the rest alphabetically
		return super.compare(viewer, e1, e2);
	}

	public static boolean isSystemFile(Object entry) {
		return ((entry instanceof IFile) && SYSTEM_FILE_EXTENSION.equalsIgnoreCase(((IFile) entry).getFileExtension()));
	}

	public static boolean isTypeLibFolder(Object entry) {
		return ((entry instanceof IFolder) && TYPE_LIB_FOLDER_NAME.equals(((IFolder) entry).getName()));
	}

}
