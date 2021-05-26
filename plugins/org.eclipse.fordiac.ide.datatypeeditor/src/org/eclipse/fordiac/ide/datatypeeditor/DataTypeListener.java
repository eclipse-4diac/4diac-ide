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
 *   Bianca Wiesmayr - initial implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.datatypeeditor;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.IPath;
import org.eclipse.fordiac.ide.datatypeeditor.editors.DataTypeEditor;
import org.eclipse.swt.widgets.Display;

public class DataTypeListener implements IResourceChangeListener {
	IPath file;
	DataTypeEditor editor;

	public DataTypeListener(final IFile file, final DataTypeEditor editor) {
		this.file = file.getFullPath();
		this.editor = editor;
	}

	@Override
	public void resourceChanged(final IResourceChangeEvent event) {
		IResourceDelta delta = event.getDelta();

		// Look for change to our file

		delta = delta.findMember(file);
		// renaming: kind REMOVED, flag MOVED_TO
		if ((delta != null) && (delta.getKind() == IResourceDelta.REMOVED)
				&& ((delta.getFlags() & IResourceDelta.MOVED_TO) != 0)) {
			file = delta.getMovedToPath();
			Display.getDefault().asyncExec(() -> editor.updateDataType(file));
		}
	}

}
