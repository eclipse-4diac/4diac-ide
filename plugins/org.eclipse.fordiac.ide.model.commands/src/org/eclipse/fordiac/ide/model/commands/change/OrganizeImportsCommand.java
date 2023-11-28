/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;
import org.eclipse.fordiac.ide.model.helpers.ImportHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Import;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.ui.errormessages.ErrorMessenger;
import org.eclipse.gef.commands.Command;

public class OrganizeImportsCommand extends Command {

	private final LibraryElement libraryElement;

	private EList<Import> oldImports;
	private EList<Import> newImports;

	public OrganizeImportsCommand(final LibraryElement libraryElement) {
		this.libraryElement = libraryElement;
	}

	@Override
	public void execute() {
		oldImports = ECollections.newBasicEList(ImportHelper.getImports(libraryElement));
		try {
			ImportHelper.organizeImports(libraryElement, VariableOperations.getAllDependencies(libraryElement));
		} catch (final Exception e) {
			ErrorMessenger.popUpErrorMessage(e.getLocalizedMessage());
		} finally {
			newImports = ECollections.newBasicEList(ImportHelper.getImports(libraryElement));
		}
	}

	@Override
	public void redo() {
		ECollections.setEList(ImportHelper.getMutableImports(libraryElement), newImports);
	}

	@Override
	public void undo() {
		ECollections.setEList(ImportHelper.getMutableImports(libraryElement), oldImports);
	}
}
