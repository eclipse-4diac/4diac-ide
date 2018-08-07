/*******************************************************************************
 * Copyright (c) 2013 - 2017 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.actions;

import org.eclipse.fordiac.ide.model.commands.create.CreateInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.typelibrary.EventTypeLibrary;
import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.ui.IWorkbenchPart;

public class CreateInputEventAction extends WorkbenchPartAction {
	public static final String ID = "InsertInputEventAction"; //$NON-NLS-1$
	private FBType fbType;

	public CreateInputEventAction(IWorkbenchPart part, FBType fbType) {
		super(part);
		setId(ID);
		setText("Create Input Event");
		this.fbType = fbType;
	}

	public FBType getFbType() {
		return fbType;
	}

	@Override
	public IWorkbenchPart getWorkbenchPart() {
		return super.getWorkbenchPart();
	}

	@Override
	protected boolean calculateEnabled() {
		return (null != fbType);
	}

	@Override
	public void run() {
		CreateInterfaceElementCommand cmd = new CreateInterfaceElementCommand(EventTypeLibrary.getInstance().getType(null), fbType.getInterfaceList(), true, -1);
		execute(cmd);
	}
}
