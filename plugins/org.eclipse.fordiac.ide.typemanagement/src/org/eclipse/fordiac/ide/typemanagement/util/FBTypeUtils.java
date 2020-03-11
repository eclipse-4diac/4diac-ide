/*******************************************************************************
 * Copyright (c) 2011 - 2017 TU Wien ACIN, fortiss GmbH, Profactor GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.util;

import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;

public final class FBTypeUtils {

	public static Palette getPalletteForFBTypeFile(IFile element) {
		Palette palette = null;
		if (null != element) {
			if (element.getProject().getName().equals(TypeLibraryTags.TOOL_LIBRARY_PROJECT_NAME)) {
				palette = TypeLibrary.getInstance().getPalette();
			} else {
				AutomationSystem system = SystemManager.INSTANCE.getSystemForName(element.getProject().getName());
				if (null != system) {
					palette = system.getPalette();
				}
			}
		}
		return palette;
	}

	public static PaletteEntry getPaletteEntryForFile(IFile typeFile) {
		PaletteEntry entry = null;
		Palette palette = getPalletteForFBTypeFile(typeFile);
		if (null != palette) {
			return TypeLibrary.getPaletteEntryForFile(typeFile, palette);
		}
		return entry;
	}

	private FBTypeUtils() {
		throw new UnsupportedOperationException("FBTypeUtils utility class should not be instantiated!"); //$NON-NLS-1$
	}
}
