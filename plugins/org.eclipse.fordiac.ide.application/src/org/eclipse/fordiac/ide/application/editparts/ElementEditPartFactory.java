/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Gerd Kainz, Monika Wenger 
 *   - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import org.eclipse.fordiac.ide.gef.editparts.Abstract4diacEditPartFactory;
import org.eclipse.fordiac.ide.gef.editparts.ValueEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.parts.GraphicalEditor;

/**
 * A factory for creating new EditParts.
 */
public class ElementEditPartFactory extends Abstract4diacEditPartFactory {

	protected final ZoomManager zoomManager;

	public ElementEditPartFactory(GraphicalEditor editor, ZoomManager zoomManager) {
		super(editor);
		this.zoomManager = zoomManager;
	}
	
	/**
	 * Maps an object to an EditPart.
	 * 
	 * @throws RuntimeException
	 *           if no match was found (programming error)
	 */
	@Override
	protected EditPart getPartForElement(final EditPart context,
			final Object modelElement) {
		EditPart part = null;

		if (modelElement instanceof FBNetwork) {
			if(((FBNetwork) modelElement).eContainer() instanceof SubApp){
				part = new UISubAppNetworkEditPart();
			}else {
				part = new FBNetworkEditPart();				
			}
		}
		else if (modelElement instanceof FB) {
			part = new FBEditPart(zoomManager);
		}
		else if (modelElement instanceof Connection) {
			part = new ConnectionEditPart();
		}
		else if (modelElement instanceof SubApp) {
			part = new SubAppForFBNetworkEditPart(zoomManager);
		}
		else  if (modelElement instanceof IInterfaceElement) {   
			IInterfaceElement element = (IInterfaceElement)modelElement;
			if(element.getFBNetworkElement() instanceof SubApp && null == element.getFBNetworkElement().getType()){
				part = new UntypedSubAppInterfaceElementEditPart();
			}else{
				part = new InterfaceEditPartForFBNetwork();
			}
		} else if (modelElement instanceof Value){
			part = new ValueEditPart();
		}
		else {
			throw createEditpartCreationException(modelElement);
		}
		return part;
	}

	
}
