/*******************************************************************************
 * Copyright (c) 2012 - 2017 Profactor GmbH, fortiss GmbH
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *    - initial implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.fbtester.editparts;

import org.eclipse.fordiac.ide.fbtypeeditor.editparts.WithEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.model.TestElement;
import org.eclipse.fordiac.ide.gef.editparts.ValueEditPart;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.eclipse.fordiac.ide.typemanagement.FBTypeEditorInput;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.parts.GraphicalEditor;

public class FBInterfaceEditPartFactory extends org.eclipse.fordiac.ide.fbtypeeditor.editparts.FBInterfaceEditPartFactory {

	public FBInterfaceEditPartFactory(GraphicalEditor editor, Palette systemPalette,
			ZoomManager zoomManager) {
		super(editor, systemPalette, zoomManager);
	}

	@Override
	protected EditPart getPartForElement(final EditPart context,
			final Object modelElement) {
		if (modelElement instanceof FBTypeEditorInput && context == null) {
			return new FBTypeRootEditPart();
		}
		if (modelElement instanceof FB) {
			return new org.eclipse.fordiac.ide.fbtypeeditor.fbtester.editparts.TesterFBEditPart(zoomManager);
		}
		if (modelElement instanceof IInterfaceElement) {
			return new org.eclipse.fordiac.ide.fbtypeeditor.fbtester.editparts.InterfaceEditPart();
		}

		if (modelElement instanceof Value) {
			ValueEditPart part = new ValueEditPart();
			part.setContext(context);
			return part;
		}
		if (modelElement instanceof TestElement) {
			return ((TestElement) modelElement).createEditPart();
		}
		if (modelElement instanceof With) {
			return new WithEditPart();
		}
		return super.getPartForElement(context, modelElement);
	}
}
