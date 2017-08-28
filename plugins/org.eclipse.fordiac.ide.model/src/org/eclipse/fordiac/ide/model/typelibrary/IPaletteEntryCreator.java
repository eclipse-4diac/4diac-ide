/*******************************************************************************
 * Copyright (c) 2014 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Waldemar Eisenmenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.typelibrary;

import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.model.Palette.impl.PaletteEntryImpl;


/**
 *  Objects implementing this element can create a palette entry if the file type can be handled. 
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
	PaletteEntryImpl createPaletteEntry();

}
