/*******************************************************************************
 * Copyright (c) 2013, 2014, 2017 fortiss GmbH
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

import org.eclipse.fordiac.ide.model.Palette.AdapterTypePaletteEntry;
import org.eclipse.fordiac.ide.model.commands.create.CreateInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.ui.IWorkbenchPart;

public class CreatePlugAction extends WorkbenchPartAction {
	private static final String ID_PREFIX = "PLUG_"; //$NON-NLS-1$
	private FBType fbType;
	private AdapterTypePaletteEntry entry;

	public CreatePlugAction(IWorkbenchPart part, FBType fbType,
			AdapterTypePaletteEntry entry) {
		super(part);
		setId(getID(entry));
		setText(entry.getLabel());
		this.fbType = fbType;
		this.entry = entry;
	}

	@Override
	protected boolean calculateEnabled() {
		return (null != fbType);
	}

	@Override
	public void run() {
		CreateInterfaceElementCommand cmd = new CreateInterfaceElementCommand(entry.getType(), fbType.getInterfaceList(), false, -1);
		execute(cmd);
	}

	public static String getID(AdapterTypePaletteEntry entry ) {
		return ID_PREFIX + entry.getFile().getFullPath().toString();
	}

}
