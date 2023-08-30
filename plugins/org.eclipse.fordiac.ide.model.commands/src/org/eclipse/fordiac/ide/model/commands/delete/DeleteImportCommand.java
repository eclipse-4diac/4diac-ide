/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.delete;

import org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo;
import org.eclipse.fordiac.ide.model.libraryElement.Import;
import org.eclipse.gef.commands.Command;

/** The Class DeleteCompilerCommand. */
public class DeleteImportCommand extends Command {

	/** The identification of the type. */
	private final CompilerInfo compilerInfo;

	/** The new Compiler value. */
	private final Import importer;

	/** The old index. */
	private int oldIndex;

	public DeleteImportCommand(final CompilerInfo compilerInfo, final Import importer) {
		this.compilerInfo = compilerInfo;
		this.importer = importer;
	}

	/* (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#execute() */
	@Override
	public void execute() {
		oldIndex = compilerInfo.getImports().indexOf(importer);
		redo();
	}

	/* (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#undo() */
	@Override
	public void undo() {
		compilerInfo.getImports().add(oldIndex, importer);
	}

	/* (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#redo() */
	@Override
	public void redo() {
		compilerInfo.getImports().remove(importer);
	}

}
