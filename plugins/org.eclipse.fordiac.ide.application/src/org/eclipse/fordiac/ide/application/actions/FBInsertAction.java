/*******************************************************************************
 * Copyright (c) 2013, 2017 fortiss GmbH
 * 				 2019 Johannes Kepler University
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
 *   Alois Zoitl - Extracted this class from the FBInsertAction  
 *******************************************************************************/
 package org.eclipse.fordiac.ide.application.actions;

import org.eclipse.fordiac.ide.model.commands.create.FBCreateCommand;
import org.eclipse.ui.IWorkbenchPart;

public class FBInsertAction extends FBNetworkElementInsertAction {
	
	public FBInsertAction(IWorkbenchPart part, FBCreateCommand createCmd) {
		super(part, createCmd, createCmd.getPaletteEntry());
	}
}
