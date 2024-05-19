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
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.search.types.FBInstanceSearch;
import org.eclipse.fordiac.ide.model.search.types.InstanceSearch;
import org.eclipse.fordiac.ide.model.search.types.StructDataTypeSearch;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.refactoring.UpdateInstancesChange;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;

public class FbInstanceChange extends CompositeChange {

	private final IFile file;
	private final TypeEntry oldTypeEntry;

	public FbInstanceChange(final IFile file) {
		super(Messages.Refactoring_AffectedFbInstances);
		this.file = file;
		this.oldTypeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(file);
		buildChanges();
	}

	private void buildChanges() {
		buildSubChanges().forEach(this::add);
	}

	private List<CompositeChange> buildSubChanges() {
		final Map<String, Set<Change>> affectedStructChanges = new HashMap<>();

		searchAffectedStructuredType().forEach(impactedStructuredType -> {
			final String label = impactedStructuredType.getTypeEntry().getFile().getProject().getName();

			if (!affectedStructChanges.containsKey(label)) {
				affectedStructChanges.put(label, new HashSet<>());
			}

			affectedStructChanges.get(label).add(new UpdateInstancesChange(impactedStructuredType, oldTypeEntry));
		});

		return affectedStructChanges.entrySet().stream().map(entry -> {
			final CompositeChange fbChange = new CompositeChange(entry.getKey());

			entry.getValue().stream().forEach(fbChange::add);

			return fbChange;
		}).toList();
	}

	private List<FBNetworkElement> searchAffectedStructuredType() {
		// TODO use new search
		final InstanceSearch structMemberSearch = StructDataTypeSearch
				.createStructMemberSearch((StructuredType) oldTypeEntry.getTypeEditable());

		final Set<INamedElement> search = InstanceSearch.performProjectSearch(this.file.getProject(),
				StructDataTypeSearch.createStructMemberSearch((StructuredType) oldTypeEntry.getTypeEditable()),
				StructDataTypeSearch.createStructInterfaceSearch((StructuredType) oldTypeEntry.getTypeEditable()),
				new FBInstanceSearch((DataTypeEntry) oldTypeEntry));

		search.addAll(StructDataTypeSearch.createStructMemberSearch((StructuredType) oldTypeEntry.getTypeEditable())
				.searchStructuredTypes(oldTypeEntry.getTypeLibrary()));
		return search.stream().filter(FBNetworkElement.class::isInstance).map(FBNetworkElement.class::cast).toList();
	}

}
