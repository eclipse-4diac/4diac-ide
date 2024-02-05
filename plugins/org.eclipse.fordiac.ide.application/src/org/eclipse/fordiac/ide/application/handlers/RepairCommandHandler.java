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
 *   Dario Romano
 *    - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.fordiac.ide.application.commands.CreateNewTypeLibraryEntryCommand;
import org.eclipse.fordiac.ide.gef.editparts.AbstractConnectableEditPart;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class RepairCommandHandler extends AbstractHandler {
	private TypeLibrary typeLib;

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection sel = HandlerUtil.getCurrentStructuredSelection(event);
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		final CommandStack commandStack = editor.getAdapter(CommandStack.class);
		final List<AbstractConnectableEditPart> editParts = getEditPartsFromSelection(sel);
		final List<CreateNewTypeLibraryEntryCommand> commandList = new ArrayList<>();
 // TODO use adapt stuff here. implement in corresponding classes
//		if (!editParts.isEmpty()) {
//			final EObject o = EcoreUtil.getRootContainer(editParts.get(0).getModel());
//			if (o instanceof final LibraryElement libel) {
//				typeLib = libel.getTypeLibrary();
//			}
//		}

//		for (final AbstractConnectableEditPart editPart : editParts) {
//			commandList.add(createNewLibraryEntryCommand(editPart));
//		}
		for (final CreateNewTypeLibraryEntryCommand command : commandList) {
			commandStack.execute(command);
		}
		typeLib.reload();
		return null;
	}

	private static List<AbstractConnectableEditPart> getEditPartsFromSelection(final IStructuredSelection sel) {
		return sel.toList().stream().filter(AbstractConnectableEditPart.class::isInstance)
				.map(AbstractConnectableEditPart.class::cast).toList();
	}

//	private CreateNewTypeLibraryEntryCommand createNewLibraryEntryCommand(final AbstractConnectableEditPart part) {
//		return new CreateNewTypeLibraryEntryCommand(typeLib, part.getModel().getTypeEntry(),
//				part.getModel().getInterface().getErrorMarker());
//	}
}
