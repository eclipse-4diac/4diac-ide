/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mario Kastner - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.listeners;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.fordiac.ide.application.handlers.MarkPredecessorHandler;
import org.eclipse.fordiac.ide.model.commands.QualNameChange;
import org.eclipse.fordiac.ide.model.commands.QualNameChangeListener;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.util.marker.MarkerStore;

public class PredecessorQualifiedNameListener extends QualNameChangeListener {

	@Override
	public void onCommandExecuted(final List<QualNameChange> qualNameChange) {
		removeMarkerEntries(qualNameChange);
		super.onCommandExecuted(qualNameChange);
	}

	@Override
	public void onCommandUndoExecuted(final List<QualNameChange> qualNameChange) {
		removeMarkerEntries(qualNameChange);
		super.onCommandUndoExecuted(qualNameChange);
	}

	@Override
	public void onCommandRedoExecuted(final List<QualNameChange> qualNameChange) {
		removeMarkerEntries(qualNameChange);
		super.onCommandRedoExecuted(qualNameChange);
	}

	@Override
	protected List<AbstractOperation> constructExecutableOperations(final QualNameChange change,
			final Object receiver) {
		return Collections.emptyList();
	}

	@Override
	protected List<AbstractOperation> constructExecutableUndoOperations(final QualNameChange change,
			final Object receiver) {
		return Collections.emptyList();
	}

	@Override
	protected Object getReceiver(final TypeEntry key) {
		return null;
	}

	@Override
	protected void executeOperation(final AbstractOperation op) {
		// do nothing
	}

	private static void removeMarkerEntries(final List<QualNameChange> qualNameChange) {
		// here the element already has a new URI
		final MarkerStore store = MarkerStore.getStoreFromEditor().orElse(null);
		if (store != null) {
			qualNameChange.stream().map(QualNameChange::notifier)
					.filter(s -> store.isMarkedElement(s.getQualifiedName()))
					.forEach(s -> MarkPredecessorHandler.removePredecessor());
		}
	}
}
