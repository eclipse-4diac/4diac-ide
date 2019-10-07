/*******************************************************************************
 * Copyright (c) 2012 TU Wien ACIN
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor;

import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.IEditorPart;

public class FBTypeEditDomain extends DefaultEditDomain {
	private CommandStack commandStack;

	public FBTypeEditDomain(IEditorPart editorPart, CommandStack commandStack) {
		super(editorPart);
		this.commandStack = commandStack;
		loadDefaultTool(); //redo this here as the first invocation in the super constructor will not work as the command stack is not correctly set the first time
	}

	@Override
	public org.eclipse.gef.commands.CommandStack getCommandStack() {
		return commandStack;
	}

	@Override
	public void loadDefaultTool() {
		if(null != commandStack){
			super.loadDefaultTool();
		}
	}
}
