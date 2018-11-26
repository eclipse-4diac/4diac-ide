/*******************************************************************************
 * Copyright (c) 2008 -2017 Profactor GmbH, fortiss GmbH
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
package org.eclipse.fordiac.ide.fbtypeeditor.network.editparts;

import org.eclipse.fordiac.ide.application.editparts.ElementEditPartFactory;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.parts.GraphicalEditor;

/**
 * A factory for creating ECCEditPart objects.
 */
public class CompositeNetworkEditPartFactory extends ElementEditPartFactory {

	public CompositeNetworkEditPartFactory(GraphicalEditor editor, ZoomManager zoomManager) {
		super(editor, zoomManager);
	}

	@Override
	protected EditPart getPartForElement(final EditPart context,
			final Object modelElement) {
		if (modelElement instanceof FBNetwork) {
			return new CompositeNetworkEditPart();
		}
		if (modelElement instanceof IInterfaceElement) {
			IInterfaceElement iElement = (IInterfaceElement)modelElement;
			if(iElement.eContainer().eContainer() instanceof CompositeFBType){
				return new CompositeInternalInterfaceEditPart();
			}
		}
		if (modelElement instanceof AdapterFB) {
			return new AdapterFBEditPart(zoomManager);
		}
		return super.getPartForElement(context, modelElement);
	}

}
