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
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.ui.IWorkbenchPart;

public class CreateOutputVariableAction extends CreateInterfaceElementAction {
	private static final String ID_PREFIX = "OUTPUT_"; //$NON-NLS-1$
	private final DataType dataType;

	public CreateOutputVariableAction(final IWorkbenchPart part, final FBType fbType, final DataType dataType) {
		super(part, fbType);
		setId(getID(dataType.getName()));
		setText(dataType.getName());
		this.dataType = dataType;
	}

	@Override
	public void run() {
		final CreateInterfaceElementCommand cmd = new CreateInterfaceElementCommand(dataType,
				getFbType().getInterfaceList(), false, -1);
		execute(cmd);
	}

	public static String getID(final String name) {
		return ID_PREFIX + name;
	}
}
