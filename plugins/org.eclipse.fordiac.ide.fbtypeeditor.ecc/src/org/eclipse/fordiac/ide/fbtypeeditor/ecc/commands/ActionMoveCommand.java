/*******************************************************************************
 * Copyright (c) 2018 fortiss GmbH
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/

package org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands;

import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.ui.controls.Abstract4DIACUIPlugin;
import org.eclipse.gef.commands.Command;
import org.eclipse.ui.IEditorPart;

public class ActionMoveCommand extends Command {
	private ECAction action;
	private IEditorPart editor;
	private ECState state;
	private ECState targetState;
	private int indexNew;
	private int indexOld;

	public ActionMoveCommand(ECAction action, ECState targetState, int indexNew) {
		this.action = action;
		this.indexNew = indexNew;
		this.targetState = targetState;
	}
	@Override
	public boolean canExecute() {
		return action != null && targetState != null;
	}
	
	@Override
	public boolean canUndo() {
		return editor.equals(Abstract4DIACUIPlugin.getCurrentActiveEditor());
	}

	@Override
	public void execute() {
		editor = Abstract4DIACUIPlugin.getCurrentActiveEditor();
		state = (ECState) action.eContainer();
		indexOld = state.getECAction().indexOf(action);
		redo();
	}

	@Override
	public void undo() {
		if(state == targetState) {
			state.getECAction().move(indexOld, action);
		}else {
			targetState.getECAction().remove(action);
			state.getECAction().add(indexOld, action);
		}
	}

	@Override
	public void redo() {
		if(state == targetState) {
			if(indexNew >= state.getECAction().size()) {
				state.getECAction().move(state.getECAction().size() - 1, action);
			}else {
				state.getECAction().move(indexNew, action);
			}
		}else {
			state.getECAction().remove(action);
			targetState.getECAction().add(indexNew, action);
		}
	}
}