/*******************************************************************************
 * Copyright (c) 2014, 2017 fortiss GmbH
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.actions;

import org.eclipse.fordiac.ide.model.commands.create.CreateInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.typelibrary.AdapterTypeEntry;
import org.eclipse.gef.commands.Command;
import org.eclipse.ui.IWorkbenchPart;

public class CreateNewPlugAction extends CreateFromNewAdapterAction {
	public static final String ID = "CreateNewPlugAction"; //$NON-NLS-1$

	public CreateNewPlugAction(final IWorkbenchPart part, final FBType fbType) {
		super(part, fbType);
	}

	@Override
	public String getId() {
		return ID;
	}

	@Override
	protected Command getCreationCommand(final AdapterTypeEntry adpEntry) {
		return new CreateInterfaceElementCommand(adpEntry.getType(), getFbType().getInterfaceList(), false, -1);
	}

}
