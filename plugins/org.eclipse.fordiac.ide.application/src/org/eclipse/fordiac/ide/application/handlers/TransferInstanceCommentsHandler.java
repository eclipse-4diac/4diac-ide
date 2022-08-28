/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.editparts.StructManipulatorEditPart;
import org.eclipse.fordiac.ide.application.wizards.StructSearch;
import org.eclipse.fordiac.ide.application.wizards.StructUpdateDialog;
import org.eclipse.fordiac.ide.model.commands.change.TransferInstanceCommentsCommand;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class TransferInstanceCommentsHandler extends AbstractHandler {

	private static final int DEFAULT_BUTTON_INDEX = 0; // Save

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection sel = HandlerUtil.getCurrentStructuredSelection(event);
		final StructManipulatorEditPart struct = (StructManipulatorEditPart) sel.getFirstElement();
		final String[] labels = { Messages.TransferInstanceComments_TransferLabel, Messages.Cancel };

		final StructUpdateDialog structUpdateDialog = new StructUpdateDialog(null,
				Messages.TransferInstanceComments_WizardTitle, null, "",
				MessageDialog.NONE, labels, DEFAULT_BUTTON_INDEX,
				(DataTypeEntry) struct.getModel().getStructType().getTypeEntry()) {
			@Override
			protected final List<INamedElement> performStructSearch() {
				final StructSearch structSearch = new StructSearch(dataTypeEntry);
				final List<INamedElement> result = structSearch.getAllTypesWithStruct();
				result.removeIf(el -> el.equals(struct.getModel()));
				return result;
			}
		};

		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		final CommandStack commandStack = editor.getAdapter(CommandStack.class);
		if (structUpdateDialog.open() == DEFAULT_BUTTON_INDEX) {
			final TransferInstanceCommentsCommand cmd = new TransferInstanceCommentsCommand(
					struct.getModel(), structUpdateDialog.getUpdatedTypes());
			commandStack.execute(cmd);
		}
		return null;
	}

}
