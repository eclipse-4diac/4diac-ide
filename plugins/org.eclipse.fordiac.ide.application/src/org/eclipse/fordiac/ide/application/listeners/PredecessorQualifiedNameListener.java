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
import org.eclipse.fordiac.ide.application.editparts.AbstractBlockFBNElementEditPart;
import org.eclipse.fordiac.ide.application.editparts.FBNetworkRootEditPart;
import org.eclipse.fordiac.ide.application.handlers.MarkPredecessorHandler;
import org.eclipse.fordiac.ide.model.commands.QualNameChange;
import org.eclipse.fordiac.ide.model.commands.QualNameChangeListener;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.ui.IEditorPart;

public class PredecessorQualifiedNameListener extends QualNameChangeListener {

	@Override
	public void onCommandExecuted(final List<QualNameChange> qualNameChange) {
		updateMarkerEntries(qualNameChange);
		super.onCommandExecuted(qualNameChange);
	}

	@Override
	public void onCommandUndoExecuted(final List<QualNameChange> qualNameChange) {
		updateMarkerEntries(qualNameChange);
		super.onCommandUndoExecuted(qualNameChange);
	}

	@Override
	public void onCommandRedoExecuted(final List<QualNameChange> qualNameChange) {
		updateMarkerEntries(qualNameChange);
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

	private static void updateMarkerEntries(final List<QualNameChange> qualNameChange) {
		// handle deleted elements
		qualNameChange.stream().filter(change -> change.newQualName() == null).map(QualNameChange::notifier)
				.filter(MarkPredecessorHandler::hasPredecessorMarker).forEach(t -> {
					if (t instanceof final FBNetworkElement fbe) {
						final AbstractBlockFBNElementEditPart ep = getEP(fbe);
						if (ep.getRoot() instanceof final FBNetworkRootEditPart root) {
							MarkPredecessorHandler.removePredecessor(root);
						}
					}
				});

		// handle moved elements
		qualNameChange.stream().map(QualNameChange::notifier).filter(MarkPredecessorHandler::hasPredecessorMarker)
				.forEach(t -> {
					if (t instanceof final FBNetworkElement fbe) {
						final AbstractBlockFBNElementEditPart ep = getEP(fbe);
						if (ep.getRoot() instanceof final FBNetworkRootEditPart root) {
							MarkPredecessorHandler.setPredecessor(root, ep);
						}
					}
				});
	}

	private static AbstractBlockFBNElementEditPart getEP(final FBNetworkElement elem) {
		final IEditorPart currentActiveEditor = EditorUtils.getCurrentActiveEditor();
		if (currentActiveEditor != null) {
			final GraphicalViewer viewer = currentActiveEditor.getAdapter(GraphicalViewer.class);
			if (viewer.getEditPartRegistry().get(elem) instanceof final AbstractBlockFBNElementEditPart ep) {
				return ep;
			}
		}
		return null;
	}
}
