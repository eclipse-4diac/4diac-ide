/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.parts.GraphicalEditor;

public class UntypedSubAppEditPartFactory extends ElementEditPartFactory {

	public UntypedSubAppEditPartFactory(GraphicalEditor editor, ZoomManager zoomManager) {
		super(editor, zoomManager);
	}

	@Override
	protected EditPart getPartForElement(final EditPart context, final Object modelElement) {
		if (modelElement instanceof IInterfaceElement && context instanceof UISubAppNetworkEditPart) {
			//this is an interface element of the containing subapp
			return new SubAppInternalInterfaceEditPart();
		}
		return super.getPartForElement(context, modelElement);
	}
}
