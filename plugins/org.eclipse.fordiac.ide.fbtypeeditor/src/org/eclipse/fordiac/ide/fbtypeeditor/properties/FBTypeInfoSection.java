/*******************************************************************************
 * Copyright (c) 2014 - 2017 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.properties;

import org.eclipse.fordiac.ide.fbtypeeditor.editors.FBTypeContentOutline;
import org.eclipse.fordiac.ide.fbtypeeditor.editors.FBTypeEditor;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.FBTypeEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.FBTypeRootEditPart;
import org.eclipse.fordiac.ide.gef.properties.CompilableTypeInfoSection;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.provider.PropertiesItemProvider;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.contentoutline.ContentOutline;

public class FBTypeInfoSection extends CompilableTypeInfoSection {

	@Override
	protected CommandStack getCommandStack(IWorkbenchPart part, Object input) {
		if(part instanceof FBTypeEditor){
			return ((FBTypeEditor)part).getCommandStack();
		}
		if(part instanceof ContentOutline){
			return ((FBTypeContentOutline) ((ContentOutline)part).getCurrentPage()).getCommandStack();
		}
		return null;
	}

	@Override
	protected LibraryElement getInputType(Object input) {
		if(input instanceof FBTypeEditPart){
			return ((FBTypeEditPart) input).getModel();	
		}
		if(input instanceof FBTypeRootEditPart){
				return ((FBTypeRootEditPart) input).getCastedFBTypeModel();
		}
		if(input instanceof PropertiesItemProvider){
			return (LibraryElement) ((PropertiesItemProvider) input).getTarget();
		}
		return null;
	}
}
