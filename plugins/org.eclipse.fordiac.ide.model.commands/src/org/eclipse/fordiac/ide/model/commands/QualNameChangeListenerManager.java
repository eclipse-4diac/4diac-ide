/*******************************************************************************
 * Copyright (c) 2024 Primetals Technology Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CommandStackEvent;
import org.eclipse.gef.commands.CommandStackEventListener;
import org.eclipse.ui.IEditorPart;

public enum QualNameChangeListenerManager implements CommandStackEventListener {

	INSTANCE;

	QualNameChangeListenerManager() {
		initialize();
	}

	private static final String EXTENSION_POINT_ID = "org.eclipse.fordiac.ide.model.commands.QualNameChangeListener";
	private final List<QualNameChangeListener> listeners = new ArrayList<>();

	public void initialize() {
		final IConfigurationElement[] config = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(EXTENSION_POINT_ID);
		for (final IConfigurationElement element : config) {
			try {
				final Object obj = element.createExecutableExtension("class");
				if (obj instanceof QualNameChangeListener) {
					listeners.add((QualNameChangeListener) obj);
				}
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void addCommandStackEventListener(final CommandStack commandStack) {
		commandStack.addCommandStackEventListener(INSTANCE);
	}

	public void removeCommandStackEventListener(final CommandStack commandStack, final Object notfier) {
		listeners.forEach(l -> l.flush(notfier));
		commandStack.removeCommandStackEventListener(INSTANCE);
	}

	@Override
	public void stackChanged(final CommandStackEvent event) {

		if (event.getCommand() instanceof final QualNameAffectedCommand cmd) {
			final QualNameChange qualNameChange = cmd.getQualNameChange();
			switch (event.getDetail()) {
			case CommandStack.POST_EXECUTE:
				notifyListenersExecute(qualNameChange);
				break;
			case CommandStack.POST_UNDO:
				notifyListenersUndo(qualNameChange);
				break;
			case CommandStack.POST_REDO:
				notifyListenersRedo(qualNameChange);
				break;

			default:
				break;
			}

		}

		if (event.getDetail() == CommandStack.POST_MARK_SAVE && event.getSource() instanceof final CommandStack stack) {

			final TypeEntry typeEntry = getTypeEntryKeyFromCommandStack(stack);
			if (typeEntry != null) {
				notifiyCommit(typeEntry);
			}
		}

	}

	private static TypeEntry getTypeEntryKeyFromCommandStack(final CommandStack stack) {
		final IEditorPart currentActiveEditor = EditorUtils.getCurrentActiveEditor();

		if (currentActiveEditor.getAdapter(CommandStack.class) == stack) {
			final LibraryElement libraryElement = currentActiveEditor.getAdapter(LibraryElement.class);
			return libraryElement.getTypeEntry();

		}

		return null;

	}

	public void notifyListenersExecute(final QualNameChange qualNameChange) {
		for (final QualNameChangeListener listener : listeners) {
			listener.onCommandExecuted(qualNameChange);
		}
	}

	public void notifyListenersUndo(final QualNameChange qualNameChange) {
		for (final QualNameChangeListener listener : listeners) {
			listener.onCommandUndoExecuted(qualNameChange);
		}
	}

	public void notifyListenersRedo(final QualNameChange qualNameChange) {
		for (final QualNameChangeListener listener : listeners) {
			listener.onCommandRedoExecuted(qualNameChange);
		}
	}

	void notifiyCommit(final Object notfier) {
		for (final QualNameChangeListener listener : listeners) {
			listener.commitOperations(notfier);
		}

	}

}