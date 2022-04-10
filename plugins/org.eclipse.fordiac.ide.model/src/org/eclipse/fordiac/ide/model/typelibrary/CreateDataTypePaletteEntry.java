/********************************************************************************
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
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.typelibrary;

import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.model.Palette.DataTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PaletteFactory;

public class CreateDataTypePaletteEntry implements IPaletteEntryCreator {

	@Override
	public boolean canHandle(final IFile file) {
		return (TypeLibraryTags.DATA_TYPE_FILE_ENDING.equalsIgnoreCase(file.getFileExtension()));
	}

	@Override
	public DataTypePaletteEntry createPaletteEntry() {
		return PaletteFactory.eINSTANCE.createDataTypePaletteEntry();
	}
}
