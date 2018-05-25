/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH,  fortiss GmbH, AIT
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Filip Pr�stl Andr�n, Monika Wenger
 *       - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.commands.delete.DeleteFBNetworkElementCommand;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Mapping;
import org.eclipse.fordiac.ide.ui.controls.Abstract4DIACUIPlugin;
import org.eclipse.gef.commands.Command;
import org.eclipse.ui.IEditorPart;

public class UnmapCommand extends Command {
	private final IEditorPart editor;	
	private final Mapping mapping;
	private final AutomationSystem system;
	
	private DeleteFBNetworkElementCommand deleteMappedFBCmd;
	
	public UnmapCommand(final FBNetworkElement element) {
		super("Unmap");
		mapping = element.getMapping();
		system = mapping.getAutomationSystem();
		editor = Abstract4DIACUIPlugin.getCurrentActiveEditor();
	}

	@Override
	public boolean canExecute() {		
		return mapping != null && null != system;
	}

	@Override
	public boolean canUndo() {
		return editor != null && editor.equals(Abstract4DIACUIPlugin.getCurrentActiveEditor());
	}

	@Override
	public void execute() {
		deleteMappedFBCmd = new DeleteFBNetworkElementCommand(mapping.getTo());
		mapping.getFrom().setMapping(null);
		mapping.getTo().setMapping(null);
		system.getMapping().remove(mapping);
		deleteMappedFBCmd.execute();
	}

	@Override
	public void undo() {
		deleteMappedFBCmd.undo();
		mapping.getFrom().setMapping(mapping);
		mapping.getTo().setMapping(mapping);
		system.getMapping().add(mapping);
	}

	@Override
	public void redo() {
		mapping.getFrom().setMapping(null);
		mapping.getTo().setMapping(null);
		system.getMapping().remove(mapping);
		deleteMappedFBCmd.redo();		
	}
	
	public FBNetworkElement getMappedFBNetworkElement() {
		return deleteMappedFBCmd.getFBNetworkElement();
	}
}
