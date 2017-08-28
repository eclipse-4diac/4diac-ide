/*******************************************************************************
 * Copyright (c) 2012, 2014 fortiss GmbH
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
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.libraryElement.FBType;

/**
 * The Class ChangeCompilerInfoClassdef.
 */
public class ChangeCompilerInfoClassdefCommand extends ChangeComplierInfoCommand {
	
	/** The new ApplicationDomain value. */
	private String newClassdef;

	/** The old ApplicationDomain value. */
	private String oldClassdef;

	public ChangeCompilerInfoClassdefCommand(final FBType type, final String newClassdef) {
		super(type);
		this.newClassdef = newClassdef;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		oldClassdef = compilerInfo.getClassdef();
		redo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		compilerInfo.setClassdef(oldClassdef);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		compilerInfo.setClassdef(newClassdef);
	}

}
