/*******************************************************************************
 * Copyright (c) 2025 Felix Schmid
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Schmid - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.marker.resolution;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public class ChangeFileNameMarkerResolution extends ChangeNameMarkerResolution {

	public ChangeFileNameMarkerResolution(final IMarker marker) {
		super(marker);
	}

	@Override
	public String getDescription() {
		return Messages.ChangeFileNameToType_QuickfixDesc;
	}

	@Override
	public String getLabel() {
		return Messages.ChangeFileNameToType_QuickfixLabel;
	}

	@Override
	public void run(final IMarker marker) {
		if (!(marker.getResource() instanceof final IFile file)) {
			return;
		}
		final TypeEntry te = TypeLibraryManager.INSTANCE.getTypeEntryForFile(file);
		final String newName = te.getTypeName() + "." + marker.getResource().getFileExtension(); //$NON-NLS-1$
		// remove type entry, new one will be created on resource move
		te.getTypeLibrary().removeTypeEntry(te);

		final IPath path = marker.getResource().getFullPath();
		final IPath newPath = path.removeLastSegments(1).append(newName);

		try {
			marker.getResource().move(newPath, false, new NullProgressMonitor());
		} catch (final CoreException e) {
			FordiacLogHelper.logError("Could not perform quickfix file rename", e); //$NON-NLS-1$
		}
	}
}
