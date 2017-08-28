/********************************************************************************
 * Copyright (c) 2014 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Waldemar Eisenmenger
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.typelibrary;

import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.model.Palette.PaletteFactory;
import org.eclipse.fordiac.ide.model.Palette.ResourceTypeEntry;
import org.eclipse.fordiac.ide.model.Palette.impl.PaletteEntryImpl;

public class CreateResourceTypePaletteEntry implements IPaletteEntryCreator, TypeLibraryTags {

	@Override
	public boolean canHandle(IFile file) {
		 if (RESOURCE_TYPE_FILE_ENDING.equalsIgnoreCase(file.getFileExtension())){
			 return true;
		 } 
		 return false;
	}

	@Override
	public PaletteEntryImpl createPaletteEntry() {
		
		ResourceTypeEntry entry = PaletteFactory.eINSTANCE.createResourceTypeEntry();
		
		return (PaletteEntryImpl) entry;
	}

}
