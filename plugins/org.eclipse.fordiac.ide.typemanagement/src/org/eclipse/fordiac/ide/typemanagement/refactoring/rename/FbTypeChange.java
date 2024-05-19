/*******************************************************************************
 * Copyright (c) 2024 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Schwarz - initial implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring.rename;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.search.types.InstanceSearch;
import org.eclipse.fordiac.ide.model.search.types.StructDataTypeSearch;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.refactoring.InterfaceDataTypeChange;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;

public class FbTypeChange extends CompositeChange {

	private final IFile file;
	private final TypeEntry oldTypeEntry;

	public FbTypeChange(final IFile targetFile) {
		super(Messages.Refactoring_AffectedFuctionBlock);
		this.file = targetFile;
		this.oldTypeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(this.file);
		buildChanges();
	}

	private void buildChanges() {
		buildSubChanges().forEach(this::add);
	}

	private List<CompositeChange> buildSubChanges() {
		final List<FBType> affectedFBs = searchAffectedFBs();

		final Map<String, Set<Change>> functionBlockChangeMap = new HashMap<>();

		affectedFBs.forEach(functionBlockType -> {
			final String label = buildLabel(functionBlockType.getTypeEntry().getFile().getName(),
					functionBlockType.getTypeEntry().getFile().getProject().getName());

			if (!functionBlockChangeMap.containsKey(label)) {
				functionBlockChangeMap.put(label, new HashSet<>());
			}

			functionBlockChangeMap.get(label).add(new InterfaceDataTypeChange(functionBlockType, oldTypeEntry));
		});

		return functionBlockChangeMap.entrySet().stream().map(entry -> {
			final CompositeChange fbChange = new CompositeChange(entry.getKey());
			entry.getValue().stream().forEach(fbChange::add);
			return fbChange;
		}).toList();
	}

	private List<FBType> searchAffectedFBs() {

		// TODO refactor to new search

		final InstanceSearch search = StructDataTypeSearch
				.createStructInterfaceSearch((StructuredType) oldTypeEntry.getTypeEditable());

		return search.performTypeLibBlockSearch(oldTypeEntry.getTypeLibrary()).stream().filter(FBType.class::isInstance)
				.map(FBType.class::cast).toList();
	}

	private String buildLabel(final String fbFileName, final String projectName) {
		return fbFileName + " [" + projectName + "]"; //$NON-NLS-1$
	}

}
