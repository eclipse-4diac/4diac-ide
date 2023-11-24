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
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.commands.internal.ChangeCompilerInfoCommand;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;

/** The Class ChangeCompilerInfoHeader. */
public class ChangePackageNameCommand extends ChangeCompilerInfoCommand {
	/** The new ApplicationDomain value. */
	private final String newName;
	/** The old ApplicationDomain value. */
	private String oldName;

	public ChangePackageNameCommand(final LibraryElement type, final String newName) {
		super(type);
		this.newName = newName;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		oldName = getCompilerInfo().getPackageName();
		getCompilerInfo().setPackageName(newName);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		getCompilerInfo().setPackageName(oldName);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		getCompilerInfo().setPackageName(newName);
	}
}