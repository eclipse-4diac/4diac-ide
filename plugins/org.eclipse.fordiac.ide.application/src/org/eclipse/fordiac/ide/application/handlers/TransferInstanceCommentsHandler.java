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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.editparts.StructManipulatorEditPart;
import org.eclipse.fordiac.ide.application.wizards.StructSearch;
import org.eclipse.fordiac.ide.application.wizards.StructUpdateDialog;
import org.eclipse.fordiac.ide.model.commands.change.TransferInstanceCommentsCommand;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class TransferInstanceCommentsHandler extends AbstractHandler {
	private StructUpdateDialog structUpdateDialog;
	private StructManipulatorEditPart struct;

	private static final int DEFAULT_BUTTON_INDEX = 0; // Save

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection sel = HandlerUtil.getCurrentStructuredSelection(event);
		struct = (StructManipulatorEditPart) sel.getFirstElement();
		final String[] labels = { Messages.TransferInstanceComments_TransferLabel, Messages.Cancel };

		structUpdateDialog = new StructUpdateDialog(null, Messages.TransferInstanceComments_WizardTitle, null, "",
				MessageDialog.NONE, labels, DEFAULT_BUTTON_INDEX,
				(DataTypeEntry) struct.getModel().getStructType().getTypeEntry()) {
			@Override
			protected final List<INamedElement> performStructSearch() {
				final StructSearch structSearch = new StructSearch(dataTypeEntry);

				List<INamedElement> result = new ArrayList<>();

				final EObject root = EcoreUtil.getRootContainer(struct.getModel());
				if (root instanceof AutomationSystem) {
					result = structSearch.getAllTypesWithStructFromSystem((AutomationSystem) root);
				} else if (root instanceof SubAppType) {
					result = structSearch.getAllTypesWithStructFromNetworkElements(
							((SubAppType) root).getFBNetwork().getNetworkElements());
				}
				result.removeIf(el -> el.equals(struct.getModel()) || isTypedOrContainedInTypedInstance(el));
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

	private static boolean isTypedOrContainedInTypedInstance(final INamedElement element) {
		return element.eContainer() != null && element.eContainer().eContainer() instanceof SubApp
				&& (((SubApp) element.eContainer().eContainer()).isContainedInTypedInstance()
						|| ((SubApp) element.eContainer().eContainer()).isTyped());
	}

}
