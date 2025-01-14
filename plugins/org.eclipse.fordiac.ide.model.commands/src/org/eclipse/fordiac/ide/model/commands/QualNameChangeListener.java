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
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;

public abstract class QualNameChangeListener {

	public enum QualNameChangeState {
		RENAME, RENAME_UNDO, RENAME_REDO, DELETE, DELETE_UNDO, DELETE_REDO;
	}

	/**
	 * A list of changes which are chached by the receiving object. This changes
	 * need to be applied to the receiver after an editor is saved. The editor is
	 * identified by its TypeEntry
	 */
	protected final HashMap<TypeEntry, List<QualNameChange>> pendingChanges = new HashMap<>();

	protected abstract List<AbstractOperation> constructExecutableOperations(final QualNameChange change,
			Object receiver);

	protected abstract List<AbstractOperation> constructExecutableUndoOperations(final QualNameChange change,
			Object receiver);

	protected abstract void executeOperation(AbstractOperation op);

	protected abstract Object getReceiver(TypeEntry key);

	protected void onCommandExecuted(final QualNameChange qualNameChange) {
		if (isEnabled(qualNameChange.notifier())) {
			addToPendingList(qualNameChange);
		}
	}

	protected void onCommandUndoExecuted(final QualNameChange qualNameChange) {
		if (isEnabled(qualNameChange.notifier())) {
			addChangeToPendingList(qualNameChange);
		}
	}

	private void addToPendingList(final QualNameChange qualNameChange) {
		addChangeToPendingList(qualNameChange);
	}

	void onCommandRedoExecuted(final QualNameChange qualNameChange) {
		addChangeToPendingList(qualNameChange);
	}

	public void flush(final Object notifier) {
		pendingChanges.remove(notifier);
	}

	public void commitOperations(final Object notifier) {

		if (!(notifier instanceof final TypeEntry key)) {
			return;
		}

		final List<QualNameChange> list = pendingChanges.get(key);

		if (list == null || list.isEmpty()) {
			// we only want to reload if changes have been performed
			return;
		}

		for (final QualNameChange change : list) {
			List<AbstractOperation> operation = null;

			if (change.state() == QualNameChangeState.RENAME_UNDO) {
				operation = constructExecutableUndoOperations(change, getReceiver(key));
			} else {
				operation = constructExecutableOperations(change, getReceiver(key));
			}

			if (operation == null || operation.isEmpty()) {
				continue;
				// the receiving object might have change in the meantime
				// TODO add to Plant hier a warning that we have umcomited change -> save all
				// editors before editing plant hierachy
			}

			operation.stream().forEach(this::executeOperation);
		}

		pendingChanges.remove(key);
	}

	protected void addChangeToPendingList(final QualNameChange qualNameChange) {
		final List<QualNameChange> changeList = pendingChanges.computeIfAbsent(qualNameChange.key(),
				key -> new ArrayList<>());
		changeList.add(qualNameChange);
	}

	@SuppressWarnings("static-method")
	protected boolean isEnabled(final INamedElement element) {
		return true;
	}

}
