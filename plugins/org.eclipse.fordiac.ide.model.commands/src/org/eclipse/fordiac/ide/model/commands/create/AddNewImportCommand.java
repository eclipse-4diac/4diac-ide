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
package org.eclipse.fordiac.ide.model.commands.create;

import org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo;
import org.eclipse.fordiac.ide.model.libraryElement.Import;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.providers.CreationCommand;

/** The Class AddNewCompilerCommand. */
public class AddNewImportCommand extends CreationCommand {

	/** The new Compiler value. */
	private Import importer;

	private final LibraryElement type;

	public AddNewImportCommand(final LibraryElement type) {
		this.type = type;
		if (type.getCompilerInfo() == null) {
			type.setCompilerInfo(LibraryElementFactory.eINSTANCE.createCompilerInfo());
		}
	}

	/* (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#execute() */
	@Override
	public void execute() {
		importer = LibraryElementFactory.eINSTANCE.createImport();
		importer.setImportedNamespace(FordiacMessages.Unknown);

		redo();
	}

	/* (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#undo() */
	@Override
	public void undo() {
		getCompilerInfo().getImports().remove(importer);
	}

	/* (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#redo() */
	@Override
	public void redo() {
		getCompilerInfo().getImports().add(importer);
	}

	private CompilerInfo getCompilerInfo() {
		return type.getCompilerInfo();
	}

	@Override
	public Object getCreatedElement() {
		return importer;
	}

}
