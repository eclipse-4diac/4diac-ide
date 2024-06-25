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
package org.eclipse.fordiac.ide.library.ui.editors;

import java.io.File;
import java.util.stream.Stream;

import org.eclipse.jface.viewers.ITreeContentProvider;

public class ExtractedLibraryImportContentProvider implements ITreeContentProvider {

	private static final Object[] EMPTY_ARR = new Object[0];
	
	@Override
	public Object[] getElements(Object inputElement) {
		return Stream.of((File[])inputElement).filter(File::isDirectory).toArray();
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		return EMPTY_ARR;
	}

	@Override
	public Object getParent(Object element) {
		return EMPTY_ARR;
	}

	@Override
	public boolean hasChildren(Object element) {
		return false;
	}

}
