/*******************************************************************************
 * Copyright (c) 2013, 2014, 2017 fortiss GmbH
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
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.ui.IWorkbenchPart;

public class CreateOutputVariableAction extends WorkbenchPartAction {
	private static final String ID_PREFIX = "OUTPUT_"; //$NON-NLS-1$
	private FBType fbType;
	private DataType dataType;

	public CreateOutputVariableAction(IWorkbenchPart part, FBType fbType,
			DataType dataType) {
		super(part);
		setId(getID(dataType.getName()));
		setText(dataType.getName());
		this.fbType = fbType;
		this.dataType = dataType;
	}

	@Override
	protected boolean calculateEnabled() {
		return (null != fbType);
	}

	@Override
	public void run() {
		CreateInterfaceElementCommand cmd = new CreateInterfaceElementCommand(dataType, fbType.getInterfaceList(), false, -1);
		execute(cmd);
	}

	public static String getID(String name) {
		return ID_PREFIX + name;
	}
}
