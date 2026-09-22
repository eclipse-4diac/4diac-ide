/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mario Kastner
 *   	- initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.ui.editors;

import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.operations.RedoActionHandler;
import org.eclipse.ui.operations.UndoActionHandler;
import org.eclipse.ui.part.EditorActionBarContributor;

public class ManifestEditorActionBarContributor extends EditorActionBarContributor {

	private UndoActionHandler undoActionHandler;
	private RedoActionHandler redoActionHandler;

	@Override
	public void setActiveEditor(final IEditorPart targetEditor) {
		super.setActiveEditor(targetEditor);

		disposeHandlers();

		if (!(targetEditor instanceof final ManifestEditor editor)) {
			return;
		}

		undoActionHandler = new UndoActionHandler(targetEditor.getSite(), editor.getUndoContext());
		redoActionHandler = new RedoActionHandler(targetEditor.getSite(), editor.getUndoContext());

		final IActionBars actionBars = getActionBars();

		actionBars.setGlobalActionHandler(ActionFactory.UNDO.getId(), undoActionHandler);
		actionBars.setGlobalActionHandler(ActionFactory.REDO.getId(), redoActionHandler);
	}

	private void disposeHandlers() {
		if (undoActionHandler != null) {
			undoActionHandler.dispose();
			undoActionHandler = null;
		}

		if (redoActionHandler != null) {
			redoActionHandler.dispose();
			redoActionHandler = null;
		}
	}

	@Override
	public void dispose() {
		disposeHandlers();
		super.dispose();
	}
}