/*******************************************************************************
 * Copyright (c) 2014 fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Waldemar Eisenmenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.typelibrary;

import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;

/**
 * Objects implementing this element can create a palette entry if the file type
 * can be handled.
 * 
 * @author eisenmenger
 *
 */
public interface IPaletteEntryCreator {

	/**
	 * Tests whether the file type can be handled.
	 * 
	 * @param file file type
	 * @return true if the file can handled and false if not.
	 */
	boolean canHandle(IFile file);

	/**
	 * Creates the palette entry
	 * 
	 * @return the created palette entry
	 */
	PaletteEntry createPaletteEntry();

}
