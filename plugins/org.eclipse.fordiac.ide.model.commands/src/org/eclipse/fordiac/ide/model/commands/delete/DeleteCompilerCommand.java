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

import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Compiler;
import org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo;
import org.eclipse.gef.commands.Command;

/**
 * The Class DeleteCompilerCommand.
 */
public class DeleteCompilerCommand extends Command implements ScopedCommand {

	/** The identification of the type. */
	private final CompilerInfo compilerInfo;

	/** The new Compiler value. */
	private final Compiler compiler;

	/** The old index. */
	private int oldIndex;

	public DeleteCompilerCommand(final CompilerInfo compilerInfo, final Compiler compiler) {
		this.compilerInfo = Objects.requireNonNull(compilerInfo);
		this.compiler = Objects.requireNonNull(compiler);
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

	@Override
	public Set<EObject> getAffectedObjects() {
		return Set.of(compilerInfo);
	}
}
