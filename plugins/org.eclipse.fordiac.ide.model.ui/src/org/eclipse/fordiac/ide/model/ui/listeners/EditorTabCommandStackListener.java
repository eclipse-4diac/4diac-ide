/*******************************************************************************
 * Copyright (c) 2020, 2021 Primetals Technologies Germany GmbH, Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial implementation and/or documentation
 *               - implemented first version of gotoMarker for FB markers
 *               - extracted breadcrumb based editor to model.ui
 *   Alois Zoitl, Bianca Wiesmayr, Michael Oberlehner, Lukas Wais, Daniel Lindhuber
 *     - initial implementation of breadcrumb navigation location
 *   Michael Oberlehner, Alois Zoitl
 *               - implemented save and restore state
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.ui.listeners;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CommandStackEvent;
import org.eclipse.gef.commands.CommandStackEventListener;
import org.eclipse.ui.INavigationLocation;
import org.eclipse.ui.INavigationLocationProvider;

/** A command stack listener for editors with many subeditors (e.g., breadcrumb, or tabs)
 *
 * It will track the open editor tabs and ensure that on undo and redo the right editor tab is shown.
 *
 * @author az */
public class EditorTabCommandStackListener implements CommandStackEventListener {

	private static class EditorLocations {
		INavigationLocation undoLocation;
		INavigationLocation redoLocation;
	}

	private final INavigationLocationProvider locationProvider;

	private final Map<Command, EditorLocations> undoHistory = new HashMap<>();
	private final Map<Command, EditorLocations> redoHistory = new HashMap<>();

	public EditorTabCommandStackListener(final INavigationLocationProvider locationProvider) {
		this.locationProvider = locationProvider;
	}

	@Override
	public void stackChanged(final CommandStackEvent event) {
		switch (event.getDetail()) {
		case CommandStack.PRE_EXECUTE:
			recordUndoLocation(event.getCommand());
			break;
		case CommandStack.POST_EXECUTE:
			recordRedoLocation(event.getCommand());
			// when a command is executed the redo history needs to be purged
			clearRedoHistory();
			break;
		case CommandStack.PRE_REDO:
			restoreRedoLocation(event.getCommand());
			break;
		case CommandStack.PRE_UNDO:
			restoreUndoLocation(event.getCommand());
			break;
		case CommandStack.PRE_FLUSH:
			clearLocationStacks();
			break;
		default:
			break;
		}

	}

	private void recordUndoLocation(final Command command) {
		final EditorLocations newLocation = new EditorLocations();
		newLocation.undoLocation = locationProvider.createNavigationLocation();
		undoHistory.put(command, newLocation);
	}

	private void recordRedoLocation(final Command command) {
		// to be on the save side create one
		final EditorLocations location = undoHistory.computeIfAbsent(command, cmd -> new EditorLocations());
		location.redoLocation = locationProvider.createNavigationLocation();
	}

	private void restoreRedoLocation(final Command command) {
		final EditorLocations location = redoHistory.get(command);
		if (location != null) {
			redoHistory.remove(command);
			if (location.redoLocation != null) {
				location.redoLocation.restoreLocation();
			}
			undoHistory.put(command, location);
		}
	}

	private void restoreUndoLocation(final Command command) {
		final EditorLocations location = undoHistory.get(command);
		if (location != null) {
			undoHistory.remove(command);
			if (location.undoLocation != null) {
				location.undoLocation.restoreLocation();
			}
			redoHistory.put(command, location);
		}
	}

	private void clearLocationStacks() {
		clearUndoHistory();
		clearRedoHistory();
	}

	private void clearUndoHistory() {
		undoHistory.clear();
	}

	private void clearRedoHistory() {
		redoHistory.clear();
	}

}
