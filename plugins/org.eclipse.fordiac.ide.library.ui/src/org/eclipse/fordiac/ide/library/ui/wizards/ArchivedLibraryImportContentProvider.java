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

import org.eclipse.jface.viewers.ITreeContentProvider;

public class ArchivedLibraryImportContentProvider implements ITreeContentProvider {
	
	private static final Object[] EMPTY_ARR = new Object[0];

	@Override
	public Object[] getElements(Object inputElement) {
		return ((File[])inputElement);
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof File file && file.isDirectory()) {
			return file.listFiles();
		}
		return EMPTY_ARR;
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof File file) {
			return file.getParentFile();
		}
		return new Object();
	}

	@Override
	public boolean hasChildren(Object element) {
		return element instanceof File file && file.isDirectory();
	}

}
