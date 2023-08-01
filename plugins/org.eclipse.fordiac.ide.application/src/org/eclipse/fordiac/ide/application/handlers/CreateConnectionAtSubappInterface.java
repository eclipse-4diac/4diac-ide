/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.application.editparts.InterfaceEditPartForFBNetwork;
import org.eclipse.fordiac.ide.model.commands.create.CreateConnectionAtSubappInterfaceCommand;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkElementHelper;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class CreateConnectionAtSubappInterface extends AbstractHandler {

	// use FBInterfaceElementIsFreeAndPartOfSubapp property tester before to ensure right types
	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		final CommandStack commandStack = editor.getAdapter(CommandStack.class);
		final StructuredSelection selection = (StructuredSelection) HandlerUtil.getCurrentSelection(event);
		if (selection.getFirstElement() instanceof final InterfaceEditPartForFBNetwork ieEP) {
			final IInterfaceElement ie = ieEP.getModel();
			SubApp subApp = null;
			if (ie.eContainer().eContainer() instanceof final FB fb) {
				subApp = FBNetworkElementHelper.getContainerSubappOfFB(fb);
			}
			if (ie.getFBNetworkElement() != null && ie.getFBNetworkElement().isNestedInSubApp()
					&& ie.eContainer().eContainer() instanceof final SubApp typedSubapp) {
				subApp = FBNetworkElementHelper.getUntypedContainerSubappOfTypedSubapp(typedSubapp);
			}

			final CreateConnectionAtSubappInterfaceCommand cmd = new CreateConnectionAtSubappInterfaceCommand(ie,
					subApp);
			commandStack.execute(cmd);

			return Status.OK_STATUS;
		}
		return Status.CANCEL_STATUS;
	}
}
