/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring.rename;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.ContainerVarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.search.types.BlockTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.search.types.DataTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.refactoring.ModelEdit;
import org.eclipse.fordiac.ide.typemanagement.refactoring.ModelEditChange;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;

public final class RenameElementRefactoringHelper {

	public static Change createRenameChange(final String name, final URI elementURI, final String newName) {
		final List<ModelEdit<?>> instanceModelEdits = new ArrayList<>();
		createChildChanges(instanceModelEdits, elementURI, newName);

		final RenameElementModelEdit typeModelEdit = new RenameElementModelEdit(MessageFormat
				.format(Messages.RenameElementRefactoringProcessor_RenamePinInType, elementURI.lastSegment()),
				elementURI, newName);
		if (instanceModelEdits.isEmpty()) {
			return ModelEditChange.fromModelEdits(name, List.of(typeModelEdit));
		}

		final CompositeChange result = new CompositeChange(name);
		result.add(ModelEditChange.fromModelEdits(name, instanceModelEdits));
		result.add(ModelEditChange.fromModelEdits(name, List.of(typeModelEdit)));
		return result;
	}

	private static void createChildChanges(final List<ModelEdit<?>> modelEdits, final URI elementURI,
			final String newName) {
		final TypeEntry typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForURI(elementURI);
		if (typeEntry == null) {
			return;
		}

		final List<? extends EObject> result = (typeEntry instanceof final DataTypeEntry dtEntry)
				? DataTypeInstanceSearch.createSearchIncludingDerivedDataTypes(dtEntry).performSearch()
				: new BlockTypeInstanceSearch(typeEntry).performSearch();
		final var eChild = getChildByURI(typeEntry.getType(), elementURI);
		if (eChild instanceof final IInterfaceElement renamedElement) {
			createRenameChanges(modelEdits, newName, result, renamedElement);
		}
	}

	protected static void createRenameChanges(final List<ModelEdit<?>> modelEdits, final String newName,
			final List<? extends EObject> result, final IInterfaceElement renamedElement) {
		for (final EObject element : result) {
			final IInterfaceElement instancePin = getInstancePin(element, renamedElement);
			if (instancePin != null) {
				modelEdits.add(new RenameElementModelEdit(
						MessageFormat.format(Messages.RenameElementRefactoringProcessor_RenamePinInInstance,
								renamedElement.getName()),
						EcoreUtil.getURI(instancePin), newName));
			}
		}
	}

	private static IInterfaceElement getInstancePin(final EObject element, final IInterfaceElement renamedElement) {
		if (element instanceof final ContainerVarDeclaration container
				&& container.getBlockFBNetworkElement() != null) {
			return container.getCachedMember(renamedElement.getBlockRelativePath(), false);
		}
		if (element instanceof final BlockFBNetworkElement block) {
			return block.getInterface().getInterfaceElement(renamedElement);
		}
		return null;
	}

	private static EObject getChildByURI(final EObject parent, final URI uri) {
		final EObject[] found = { null };
		parent.eAllContents().forEachRemaining(child -> {
			final String uriFragment = child.eResource().getURIFragment(child);
			if (uriFragment.equals(uri.fragment())) {
				found[0] = child;
			}
		});
		return found[0];
	}

	private RenameElementRefactoringHelper() {
		throw new UnsupportedOperationException();
	}
}
