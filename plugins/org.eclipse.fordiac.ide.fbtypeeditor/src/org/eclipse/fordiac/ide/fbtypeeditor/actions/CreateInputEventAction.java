/*******************************************************************************
 * Copyright (c) 2013, 2023 fortiss GmbH
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Martin Jobst
 *     - introduce common superclass
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.actions;

import org.eclipse.fordiac.ide.fbtypeeditor.Messages;
import org.eclipse.fordiac.ide.model.commands.create.CreateInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.typelibrary.EventTypeLibrary;
import org.eclipse.ui.IWorkbenchPart;

public class CreateInputEventAction extends CreateInterfaceElementAction {
	public static final String ID = "InsertInputEventAction"; //$NON-NLS-1$

	public CreateInputEventAction(final IWorkbenchPart part, final FBType fbType) {
		super(part, fbType);
		setId(ID);
		setText(Messages.CreateInputEventAction_CreateInputEvent);
	}

	@Override
	public IWorkbenchPart getWorkbenchPart() {
		return super.getWorkbenchPart();
	}

	@Override
	public void run() {
		final CreateInterfaceElementCommand cmd = new CreateInterfaceElementCommand(
				EventTypeLibrary.getInstance().getType(null), getFbType().getInterfaceList(), true, -1);
		execute(cmd);
	}
}
