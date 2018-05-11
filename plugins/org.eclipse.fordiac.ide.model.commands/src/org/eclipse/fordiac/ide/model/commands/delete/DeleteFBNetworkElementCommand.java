/*******************************************************************************
 * Copyright (c) 2016 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *       - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.delete;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.commands.Messages;
import org.eclipse.fordiac.ide.model.commands.change.UnmapCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.ui.controls.Abstract4DIACUIPlugin;
import org.eclipse.fordiac.ide.ui.controls.editors.EditorUtils;
import org.eclipse.fordiac.ide.ui.controls.editors.I4diacModelEditor;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ui.IEditorPart;

public class DeleteFBNetworkElementCommand extends Command {
	private IEditorPart editor;
	private FBNetwork fbParent;
	private final FBNetworkElement element;
	private CompoundCommand cmds = new CompoundCommand();
	
	public DeleteFBNetworkElementCommand(final FBNetworkElement element) {
		super("Delete FB or Subapplication");
		this.element = element;
	}
	
	public FBNetworkElement getFBNetworkElement() {
		return element;
	}

	@Override
	public boolean canUndo() {
		return editor != null && editor.equals(Abstract4DIACUIPlugin.getCurrentActiveEditor());
	}

	@Override
	public boolean canExecute() {
		if((element instanceof FB) && ((FB)element).isResourceTypeFB()){  //TODO model refactoring - consider if there should be resource type subapps
			Abstract4DIACUIPlugin.statusLineErrorMessage(Messages.DeleteFBNetworkElement);
			return false;
		}
		return null != element && null != element.getFbNetwork();
	}

	@Override
	public void execute() {
		editor = Abstract4DIACUIPlugin.getCurrentActiveEditor();
		fbParent = element.getFbNetwork();
		if(element.isMapped()){
			cmds.add(new UnmapCommand(element));
		}
		getDeleteConnections(element);
		//Before removing the fbnetwork element the connections and mapping should be removed
		if(cmds.canExecute()){
			cmds.execute();
		}
		fbParent.getNetworkElements().remove(element);
		if(element instanceof SubApp) {
			closeSubApplicationEditor((SubApp)element);
		}
	}
	
	@Override
	public void undo() {
		fbParent.getNetworkElements().add(element);		
		if(cmds.canUndo()) {
			cmds.undo();
		}
	}

	@Override
	public void redo() {
		if(cmds.canRedo()){
			cmds.redo();
		}
		fbParent.getNetworkElements().remove(element);
	}
	
	private void getDeleteConnections(final FBNetworkElement element) {
		for (IInterfaceElement intElement: element.getInterface().getAllInterfaceElements()){
			EList<Connection> connList = null;
			if(intElement.isIsInput()){
				connList = intElement.getInputConnections();
			} else { 
				connList = intElement.getOutputConnections();
			}			
			if(null != connList){
				connList.forEach((Connection con) -> 
					cmds.add(new DeleteConnectionCommand(con)));
			}
		}
	}
	
	private void closeSubApplicationEditor(SubApp subapp) {
		EditorUtils.closeEditorsFiltered((IEditorPart editor) -> {
			return ((editor instanceof I4diacModelEditor) && 
					(subapp.getSubAppNetwork() == ((I4diacModelEditor)editor).getModel()) );
		});
	}

}
