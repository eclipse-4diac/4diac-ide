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
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;

public class ChangeTypeNameMarkerResolution extends ChangeNameMarkerResolution {

	public ChangeTypeNameMarkerResolution(final IMarker marker) {
		super(marker);
	}

	@Override
	public String getDescription() {
		return Messages.ChangeTypeNameToFile_QuickfixDesc;
	}

	@Override
	public String getLabel() {
		return Messages.ChangeTypeNameToFile_QuickfixLabel;
	}

	@Override
	protected void runInWorkspace(final IMarker marker) throws CoreException {
		if (!(marker.getResource() instanceof final IFile file)) {
			throw createExceptionForMarker(Messages.ChangeName_NoFileError, marker);
		}
		final TypeEntry typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(file);
		if (typeEntry == null) {
			throw createExceptionForMarker(Messages.ChangeName_NoTypeEntryError, marker);
		}
		final LibraryElement type = typeEntry.getType();
		if (type == null) {
			throw createExceptionForMarker(Messages.ChangeName_NoTypeError, marker);
		}
		type.setName(TypeEntry.getTypeNameFromFile(file));
		typeEntry.save(type);
	}
}
