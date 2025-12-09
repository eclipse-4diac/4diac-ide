/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *                    Martin Erich Jobst
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
 *   Martin Erich Jobst
 *     - add initial imported namespace
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.create;

import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.helpers.ImportHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Import;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.providers.CreationCommand;

/** The Class AddNewCompilerCommand. */
public class AddNewImportCommand extends CreationCommand implements ScopedCommand {

	/** The new Compiler value. */
	private Import importer;

	private final LibraryElement type;
	private final String importedNamespace;

	public AddNewImportCommand(final LibraryElement type) {
		this(type, FordiacMessages.Unknown);
	}

	public AddNewImportCommand(final LibraryElement type, final String importedNamespace) {
		this.type = Objects.requireNonNull(type);
		this.importedNamespace = importedNamespace;
	}

	@Override
	public void execute() {
		importer = LibraryElementFactory.eINSTANCE.createImport();
		importer.setImportedNamespace(importedNamespace);
		redo();
	}

	@Override
	public void undo() {
		ImportHelper.getMutableImports(type).remove(importer);
	}

	@Override
	public void redo() {
		ImportHelper.getMutableImports(type).add(importer);
	}

	@Override
	public Object getCreatedElement() {
		return importer;
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		// imports affect entire type
		return Set.of(type);
	}
}
