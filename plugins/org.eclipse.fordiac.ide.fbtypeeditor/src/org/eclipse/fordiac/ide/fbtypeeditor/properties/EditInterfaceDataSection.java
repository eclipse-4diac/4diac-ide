/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.properties;

import org.eclipse.fordiac.ide.fbtypeeditor.editors.FBTypeContentOutline;
import org.eclipse.fordiac.ide.fbtypeeditor.editors.FBTypeEditor;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.FBTypeEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.FBTypeRootEditPart;
import org.eclipse.fordiac.ide.gef.properties.AbstractEditInterfaceDataSection;
import org.eclipse.fordiac.ide.model.commands.change.ChangeInterfaceOrderCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInterfaceCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.contentoutline.ContentOutline;

public class EditInterfaceDataSection extends AbstractEditInterfaceDataSection {
	@Override
	protected CreateInterfaceElementCommand newCreateCommand(boolean isInput) {
		return new CreateInterfaceElementCommand(DataTypeLibrary.getInstance().getType(fillTypeCombo()[2]), getType().getInterfaceList(), isInput, -1);
	}

	@Override
	protected INamedElement getInputType(Object input) {
		if(input instanceof FBTypeEditPart){
			return ((FBTypeEditPart) input).getModel();	
		}
		if(input instanceof FBTypeRootEditPart){
				return ((FBTypeRootEditPart) input).getCastedFBTypeModel();
		}
		return null;
	}

	@Override
	protected DeleteInterfaceCommand newDeleteCommand(IInterfaceElement selection) {
		return new DeleteInterfaceCommand(selection);
	}
	
	@Override
	protected ChangeInterfaceOrderCommand newOrderCommand(IInterfaceElement selection, boolean isInput,
			boolean moveUp) {
		return new ChangeInterfaceOrderCommand(selection, isInput, moveUp);
	}
	
	@Override
	protected FBType getType() {
		return (FBType)type;
	}
	
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
}
