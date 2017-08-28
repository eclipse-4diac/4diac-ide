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

import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.application.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.gef.editparts.Abstract4diacEditPartFactory;
import org.eclipse.fordiac.ide.gef.editparts.ValueEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.resourceediting.editors.ResourceDiagramEditor;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.editparts.ZoomManager;

/**
 * A factory for creating new EditParts.
 * 
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */
public class ResourceDiagramEditPartFactory extends Abstract4diacEditPartFactory {

	protected ZoomManager zoomManager;	
	
	/**
	 * Instantiates a new resource diagram edit part factory.
	 * 
	 * @param viewer
	 *          the viewer
	 */
	public ResourceDiagramEditPartFactory(final ResourceDiagramEditor editor, final GraphicalViewer viewer, ZoomManager zoomManager) {
		super(editor);
		this.zoomManager = zoomManager;
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
			EditPart parent = context.getParent();
			IInterfaceElement iElement = (IInterfaceElement)modelElement;
			if (iElement.eContainer().eContainer() instanceof Resource){
				return new InterfaceEditPart();				
			}
			if (parent instanceof FBNetworkContainerEditPart) {
				return new InterfaceEditPartForResourceFBs();
			}
			if (context instanceof FBNetworkContainerEditPart) {
				return new VirtualInOutputEditPart();
			}
		}
		if (modelElement instanceof Connection) {
			// return default connection edit part with the wrong connection style
			return new ConnectionEditPart(); 
		}
		if (modelElement instanceof SubApp) {
			return new SubAppForFBNetworkEditPart(zoomManager);
		}
		if (modelElement instanceof Value){
			return new ValueEditPart();
		}

		throw createEditpartCreationException(modelElement);
	}

}
