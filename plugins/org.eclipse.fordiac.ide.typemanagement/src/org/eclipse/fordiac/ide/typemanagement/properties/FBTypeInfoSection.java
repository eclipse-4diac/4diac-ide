/*******************************************************************************
 * Copyright (c) 2014 - 2017 fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.properties;

import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.gef.properties.CompilableTypeInfoSection;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.IWorkbenchPart;

public class FBTypeInfoSection extends CompilableTypeInfoSection {

	@Override
	protected CommandStack getCommandStack(IWorkbenchPart part, Object input) {
		return null;
	}

	@Override
	protected LibraryElement getInputType(Object input) {
		if (input instanceof IFile) {
			PaletteEntry entry = TypeLibrary.getPaletteEntryForFile((IFile) input);
			if (null != entry) {
				return entry.getType();
			}
		}
		return null;
	}
}
