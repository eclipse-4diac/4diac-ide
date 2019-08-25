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
package org.eclipse.fordiac.ide.model.commands.delete;

import org.eclipse.fordiac.ide.model.libraryElement.Compiler;
import org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo;
import org.eclipse.gef.commands.Command;

/**
 * The Class DeleteCompilerCommand.
 */
public class DeleteCompilerCommand extends Command {
	
	/** The identification of the type. */
	private CompilerInfo compilerInfo;
	
	/** The new Compiler value. */
	private Compiler compiler;
	
	/** The old index. */
	private int oldIndex;


	public DeleteCompilerCommand(final CompilerInfo compilerInfo, Compiler compiler) {
		super();
		this.compilerInfo = compilerInfo;
		this.compiler = compiler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		oldIndex = compilerInfo.getCompiler().indexOf(compiler);
		redo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		compilerInfo.getCompiler().add(oldIndex, compiler);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		compilerInfo.getCompiler().remove(compiler);
	}

}
