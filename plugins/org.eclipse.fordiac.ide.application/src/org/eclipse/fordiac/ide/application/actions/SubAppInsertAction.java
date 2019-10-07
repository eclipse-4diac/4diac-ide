/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
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
