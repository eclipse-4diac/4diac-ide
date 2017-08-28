/*******************************************************************************
 * Copyright (c) 2013, 2017 fortiss GmbH
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
package org.eclipse.fordiac.ide.application.actions;

import org.eclipse.fordiac.ide.model.commands.create.FBCreateCommand;
import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.IWorkbenchPart;

public class FBInsertAction extends WorkbenchPartAction {
	
	FBCreateCommand createCmd;

	public FBInsertAction(IWorkbenchPart part, FBCreateCommand createCmd) {
		super(part);
		this.createCmd = createCmd;
		
		setId(createCmd.getPaletteEntry().getFile().getFullPath().toString());
		setText(createCmd.getPaletteEntry().getLabel());

	}

	@Override
	protected boolean calculateEnabled() {
		return createCmd.canExecute();
	}

	@Override
	public void run() {
		execute(createCmd);
	}

	public void updateCreatePosition(Point pt) {
		createCmd.updateCreatePosition(pt.x, pt.y);		
	}
}
