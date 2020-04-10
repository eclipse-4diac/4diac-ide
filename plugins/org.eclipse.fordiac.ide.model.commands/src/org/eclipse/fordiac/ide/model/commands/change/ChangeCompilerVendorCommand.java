/*******************************************************************************
 * Copyright (c) 2012, 2014 fortiss GmbH
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
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.libraryElement.Compiler;
import org.eclipse.gef.commands.Command;

/**
 * The Class ChangeCompilerVendorCommand.
 */
public class ChangeCompilerVendorCommand extends Command {

	/** The new Compiler value. */
	private Compiler compiler;

	private String newVendor;
	private String oldVendor;

	public ChangeCompilerVendorCommand(final Compiler compiler, final String newVendor) {
		super();
		this.compiler = compiler;
		this.newVendor = newVendor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		oldVendor = compiler.getVendor();
		redo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		compiler.setVendor(oldVendor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		compiler.setVendor(newVendor);
	}

}
