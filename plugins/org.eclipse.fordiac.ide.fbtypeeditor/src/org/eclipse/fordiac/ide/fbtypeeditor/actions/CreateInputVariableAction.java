/*******************************************************************************
 * Copyright (c) 2013 - 2017 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.actions;

import org.eclipse.fordiac.ide.model.commands.create.CreateInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.ui.IWorkbenchPart;

public class CreateInputVariableAction extends WorkbenchPartAction {
	private static final String ID_PREFIX = "INPUT_"; //$NON-NLS-1$
	private FBType fbType;
	private DataType dataType;

	public CreateInputVariableAction(IWorkbenchPart part, FBType fbType, DataType dataType) {
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
		CreateInterfaceElementCommand cmd = new CreateInterfaceElementCommand(dataType, fbType.getInterfaceList(), true, -1);
		execute(cmd);
	}

	public static String getID(String name) {
		return ID_PREFIX + name;
	}

}
