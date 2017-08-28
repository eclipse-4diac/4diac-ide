/*******************************************************************************
 * Copyright (c) 2015 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl 
 *   - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editors;

import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.ui.model.WorkbenchLabelProvider;


/** Label provider for the FB type tree palette used in function block network editors.
 * 
 * For the images we'll use the default images for folders and FB type files. For type file names we
 * hide the extension to make the palette look clearer.  
 */
public class FBPaletteLabelProvider extends WorkbenchLabelProvider implements
		ILabelProvider {

	@Override
	public StyledString getStyledText(Object element) {
		if(element instanceof IFile){
			//we want to hide the extension of the fb type
			return new StyledString(TypeLibrary.getTypeNameFromFile((IFile) element));
		}			
		return super.getStyledText(element);
	}

	
	
}
