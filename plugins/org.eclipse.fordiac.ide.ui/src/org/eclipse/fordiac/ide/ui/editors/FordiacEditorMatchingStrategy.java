/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.ui.editors;

import org.eclipse.fordiac.ide.ui.UIPlugin;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorMatchingStrategy;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.PartInitException;

/** The default behavior for file based editors compares the file name (input.getName) with the part name this is not
 * working for 4diac editors where the type/system name is shown. Therefore we need this own strategy for matching
 * editors. */
public class FordiacEditorMatchingStrategy implements IEditorMatchingStrategy {

	@Override
	public boolean matches(final IEditorReference editorRef, final IEditorInput input) {
		try {
			return (editorRef.getName().equals(stripExtension(input.getName()))
					&& input.equals(editorRef.getEditorInput()));
		} catch (final PartInitException e) {
			UIPlugin.getDefault().logError(e.getMessage(), e);
		}
		return false;
	}

	private static String stripExtension(final String fileName) {
		String name = fileName;
		final int index = fileName.lastIndexOf('.');
		if (-1 != index) {
			name = fileName.substring(0, index);
		}
		return name;
	}

}
