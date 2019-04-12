/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.actions;

import org.eclipse.fordiac.ide.model.commands.create.CreateSubAppInstanceCommand;
import org.eclipse.ui.IWorkbenchPart;

public class SubAppInsertAction extends FBNetworkElementInsertAction {
	
	public SubAppInsertAction(IWorkbenchPart part, CreateSubAppInstanceCommand createCmd) {
		super(part, createCmd, createCmd.getPaletteEntry());
	}
}
