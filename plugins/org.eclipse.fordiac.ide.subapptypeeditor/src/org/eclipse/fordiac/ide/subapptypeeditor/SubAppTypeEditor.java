/*******************************************************************************
 * Copyright (c) 2014 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.subapptypeeditor;

import org.eclipse.fordiac.ide.fbtypeeditor.editors.FBTypeEditor;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.SubApplicationTypePaletteEntry;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;

public class SubAppTypeEditor extends FBTypeEditor {

	@Override
	protected FBType getFBType(PaletteEntry paletteEntry) {
		if(paletteEntry instanceof SubApplicationTypePaletteEntry){
			return ((SubApplicationTypePaletteEntry)paletteEntry).getSubApplicationType();
		}
		return null;
	}

	@Override
	protected boolean checkTypeEditorType(FBType fbType, String editorType) {
		return ((editorType.equals("ForAllTypes")) ||  //$NON-NLS-1$
				(editorType.equals("subapp"))); //$NON-NLS-1$
	}
}
