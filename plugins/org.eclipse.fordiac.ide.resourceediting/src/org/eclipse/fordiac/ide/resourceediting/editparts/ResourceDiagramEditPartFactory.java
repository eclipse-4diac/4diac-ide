/*******************************************************************************
 * Copyright (c) 2008, 2009, 2012 - 2017 Profactor GmbH, fortiss GmbH
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
package org.eclipse.fordiac.ide.resourceediting.editparts;

import org.eclipse.fordiac.ide.application.editparts.ElementEditPartFactory;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.resourceediting.editors.ResourceDiagramEditor;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.editparts.ZoomManager;

/**
 * A factory for creating new EditParts.
 * 
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */
public class ResourceDiagramEditPartFactory extends ElementEditPartFactory {
	
	public ResourceDiagramEditPartFactory(final ResourceDiagramEditor editor, ZoomManager zoomManager) {
		super(editor, zoomManager);
	}

	@Override
	protected EditPart getPartForElement(final EditPart context,
			final Object modelElement) {
		
		if (modelElement instanceof FBNetwork) {
			return new FBNetworkContainerEditPart();
		}
		if (modelElement instanceof FB) {
			return new ResFBEditPart(zoomManager);
		}
		if (modelElement instanceof IInterfaceElement) {
			IInterfaceElement element = (IInterfaceElement)modelElement;
			if(element.getFBNetworkElement() instanceof SubApp && null == element.getFBNetworkElement().getType()){
				return  new UntypedSubAppInterfaceElementEditPartForResource();
			}
			return new InterfaceEditPartForResourceFBs();
		}
		if (modelElement instanceof VirtualIO) {	
			return new VirtualInOutputEditPart();
		}
		return super.getPartForElement(context, modelElement);
	}

}
