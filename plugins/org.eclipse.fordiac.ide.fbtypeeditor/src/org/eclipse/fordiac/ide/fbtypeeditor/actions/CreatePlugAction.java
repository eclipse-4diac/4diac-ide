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

import org.eclipse.fordiac.ide.model.commands.create.CreateInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.typelibrary.AdapterTypeEntry;
import org.eclipse.ui.IWorkbenchPart;

public class CreatePlugAction extends CreateInterfaceElementAction {
	private static final String ID_PREFIX = "PLUG_"; //$NON-NLS-1$
	private final AdapterTypeEntry entry;

	public CreatePlugAction(final IWorkbenchPart part, final FBType fbType, final AdapterTypeEntry entry) {
		super(part, fbType);
		setId(getID(entry));
		setText(entry.getTypeName());
		this.entry = entry;
	}

	@Override
	public void run() {
		final CreateInterfaceElementCommand cmd = new CreateInterfaceElementCommand(entry.getType(),
				getFbType().getInterfaceList(), false, -1);
		execute(cmd);
	}

	public static String getID(final AdapterTypeEntry entry) {
		return ID_PREFIX + entry.getFile().getFullPath().toString();
	}

}
