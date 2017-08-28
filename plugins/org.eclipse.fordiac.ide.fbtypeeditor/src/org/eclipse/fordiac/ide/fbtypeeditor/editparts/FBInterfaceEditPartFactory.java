/*******************************************************************************
 * Copyright (c) 2011 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.editparts;

import org.eclipse.fordiac.ide.gef.editparts.Abstract4diacEditPartFactory;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.parts.GraphicalEditor;

public class FBInterfaceEditPartFactory extends Abstract4diacEditPartFactory {

    private Palette systemPalette;
    protected ZoomManager zoomManager;
    
	public FBInterfaceEditPartFactory(GraphicalEditor editor, Palette systemPalette, ZoomManager zoomManager) {
		super(editor);
		this.systemPalette = systemPalette;
		this.zoomManager = zoomManager;
	}
	
	@Override
	protected EditPart getPartForElement(final EditPart context,
			final Object modelElement) {
		if (modelElement instanceof FBType && context == null) {
			return new FBTypeRootEditPart();
		}
		if (modelElement instanceof FBType
				&& context instanceof FBTypeRootEditPart) {
			return new FBTypeEditPart(zoomManager);
		}
		if (modelElement instanceof EventInputContainer
				|| modelElement instanceof EventOutputContainer
				|| modelElement instanceof VariableInputContainer
				|| modelElement instanceof VariableOutputContainer
				|| modelElement instanceof SocketContainer
			    || modelElement instanceof PlugContainer) {
			return new InterfaceContainerEditPart();
		}

		if (modelElement instanceof Event) {
			return new InterfaceEditPart();
		}
		if (modelElement instanceof VarDeclaration) {
			if (modelElement instanceof AdapterDeclaration){
				return new AdapterInterfaceEditPart(systemPalette);
			}
			else{
				return createInterfaceEditPart();
			}
		}
		if (modelElement instanceof With) {
			return new WithEditPart();
		}
		if (modelElement instanceof CommentTypeField) {
			return new CommentTypeEditPart();
		}
		if (modelElement instanceof CommentTypeField.CommentTypeSeparator) {
			return new CommentTypeSeparatorEditPart();
		}
		if (modelElement instanceof CommentField){
			return new CommentEditPart();
		}
		if (modelElement instanceof TypeField){
			return new TypeEditPart(systemPalette);
		}
		throw createEditpartCreationException(modelElement);
	}
	
	protected EditPart createInterfaceEditPart() {
		return new InterfaceEditPart();
	}
}
