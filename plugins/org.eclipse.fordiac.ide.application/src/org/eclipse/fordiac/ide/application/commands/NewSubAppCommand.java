/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 				 2018 Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger 
 *   - initial API and implementation and/or initial documentation
 *   Alois Zoitl  - reworked to make it usable for AddElementsToSubApp
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.commands;

import java.util.List;

import org.eclipse.fordiac.ide.model.commands.change.MapToCommand;
import org.eclipse.fordiac.ide.model.commands.create.AbstractCreateFBNetworkElementCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.EditPart;
import org.eclipse.ui.IEditorInput;

public class NewSubAppCommand extends AbstractCreateFBNetworkElementCommand {
	/** The input for reopening subApp. */
	private IEditorInput input = null;
	private final AddElementsToSubAppCommand addElements;
	private MapToCommand mappSubappCmd = null;  //can not be in the compound command as it needs to be performed when subapp interface is finished

	public NewSubAppCommand(FBNetwork fbNetwork, List<?> selection, int x, int y) {
		super(fbNetwork, LibraryElementFactory.eINSTANCE.createSubApp(), x, y);
		getSubApp().setSubAppNetwork(LibraryElementFactory.eINSTANCE.createFBNetwork());
		addElements = new AddElementsToSubAppCommand(getSubApp(), selection); 
		checkMapping(selection);
	}
	
	@Override
	public void execute() {
		super.execute();
		//We can not call redo here as the unmap and map commands would not be handled correctly
		addElements.execute();
		if(null != mappSubappCmd) {
			mappSubappCmd.execute();
		}
		openClosedEditor();
	}

	@Override
	public void redo() {
		super.redo();
		addElements.redo();
		if(null != mappSubappCmd) {
			mappSubappCmd.redo();
		}
		openClosedEditor();
	}

	@Override
	public void undo() {
		if(null != mappSubappCmd) {
			mappSubappCmd.undo();
		}
		addElements.undo();   //this has to be done bevor super.undo() as otherwise addElements does not have the correct networks.
		super.undo();
		closeOpenedSubApp();
	}
	
	private void checkMapping(List<?> selection) {
		Resource res = null;
		for(Object ne : selection){
			if(ne instanceof EditPart && ((EditPart)ne).getModel() instanceof FBNetworkElement){
				FBNetworkElement element = (FBNetworkElement)((EditPart) ne).getModel();
				if(element.isMapped()) {
					if(null == res) {
						//this is the first element
						res = element.getResource();
					} else if(res != element.getResource()) {
							return; //we have elements mapped to different entities
					}
				} else {
					return; //we have at least one unmapped element so we will not mapp the whole subapp
				}
			}
		}
		if(null != res) {
			mappSubappCmd = new MapToCommand(getSubApp(), res);
		}
	}
	
	@Override
	protected final InterfaceList getTypeInterfaceList() {
		return LibraryElementFactory.eINSTANCE.createInterfaceList();
	}
	
	@Override
	protected String getInitalInstanceName() {
		return "SubApp"; //$NON-NLS-1$
	}
	
	private void closeOpenedSubApp() {
		input = CommandUtil.closeOpenedSubApp(getSubApp().getSubAppNetwork());
	}

	private SubApp getSubApp() {
		return (SubApp)getElement();
	}

	private void openClosedEditor() {
		if (input != null) {
			CommandUtil.openSubAppEditor(input);
		}
	}
}
