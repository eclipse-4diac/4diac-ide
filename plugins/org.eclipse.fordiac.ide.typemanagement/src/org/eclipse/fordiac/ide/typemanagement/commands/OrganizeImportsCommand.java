/*******************************************************************************
 * Copyright (c) 2023, 2025 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.typemanagement.commands;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.helpers.ImportHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Import;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.search.ISearchFactory;
import org.eclipse.fordiac.ide.model.search.ISearchSupport;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.gef.commands.Command;

public class OrganizeImportsCommand extends Command implements ScopedCommand {

	private final LibraryElement libraryElement;

	private EList<Import> oldImports;
	private EList<Import> newImports;
	private boolean incompleteResult;

	public OrganizeImportsCommand(final LibraryElement libraryElement) {
		this.libraryElement = Objects.requireNonNull(libraryElement);
	}

	@Override
	public void execute() {
		oldImports = ECollections.newBasicEList(ImportHelper.getImports(libraryElement));
		try {
			final Set<String> importedNamespaces = getAllImportedNamespaces(libraryElement);
			ImportHelper.organizeImports(libraryElement, importedNamespaces, !incompleteResult);
		} catch (final Exception e) {
			FordiacLogHelper.logError("Error organizing imports", e); //$NON-NLS-1$
		} finally {
			newImports = ECollections.newBasicEList(ImportHelper.getImports(libraryElement));
		}
	}

	private Set<String> getAllImportedNamespaces(final EObject object) {
		return StreamSupport.stream(Spliterators.spliteratorUnknownSize(object.eAllContents(), 0), false)
				.map(this::getImportedNamespaces).flatMap(Collection::stream).collect(Collectors.toSet());
	}

	private Set<String> getImportedNamespaces(final EObject object) {
		final ISearchSupport support = ISearchFactory.createSearchSupport(object, object.eClass().getInstanceClass());
		if (support != null) {
			final Set<String> dependencies = support.getImportedNamespaces();
			incompleteResult |= support.isIncompleteResult();
			return dependencies;
		}
		return Set.of();
	}

	@Override
	public void redo() {
		ECollections.setEList(ImportHelper.getMutableImports(libraryElement), newImports);
	}

	@Override
	public void undo() {
		ECollections.setEList(ImportHelper.getMutableImports(libraryElement), oldImports);
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		return Set.of(libraryElement);
	}
}
