package org.eclipse.fordiac.ide.typemanagement.refactoring.rename;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.search.types.InstanceSearch;
import org.eclipse.fordiac.ide.model.search.types.StructDataTypeSearch;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.refactoring.StructuredTypeMemberChange;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;

public class StructTypeChange extends CompositeChange {

	private final IFile file;
	private final String newName;
	private final TypeEntry oldTypeEntry;

	public StructTypeChange(final IFile file, final String newName) {
		super(Messages.Refactoring_AffectedStruct);
		this.file = file;
		this.oldTypeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(file);
		this.newName = newName;
		buildChanges();
	}

	private void buildChanges() {
		buildSubChanges().forEach(this::add);
	}

	private List<CompositeChange> buildSubChanges() {
		final Map<String, Set<Change>> affectedStructChanges = new HashMap<>();

		searchAffectedStructuredType().forEach(impactedStructuredType -> {
			final String label = buildLabel(impactedStructuredType.getTypeEntry().getFile().getName(),
					impactedStructuredType.getTypeEntry().getFile().getProject().getName());

			if (!affectedStructChanges.containsKey(label)) {
				affectedStructChanges.put(label, new HashSet<>());
			}

			affectedStructChanges.get(label)
					.add(new StructuredTypeMemberChange(impactedStructuredType, oldTypeEntry, newName));
		});

		return affectedStructChanges.entrySet().stream().map(entry -> {
			final CompositeChange fbChange = new CompositeChange(entry.getKey());

			entry.getValue().stream().forEach(fbChange::add);

			return fbChange;
		}).toList();
	}

	private List<StructuredType> searchAffectedStructuredType() {
		final InstanceSearch structMemberSearch = StructDataTypeSearch
				.createStructMemberSearch((StructuredType) oldTypeEntry.getTypeEditable());

		final Set<INamedElement> search = InstanceSearch.performProjectSearch(this.file.getProject(),
				StructDataTypeSearch.createStructMemberSearch((StructuredType) oldTypeEntry.getTypeEditable()),
				StructDataTypeSearch.createStructInterfaceSearch((StructuredType) oldTypeEntry.getTypeEditable()));

		search.addAll(StructDataTypeSearch.createStructMemberSearch((StructuredType) oldTypeEntry.getTypeEditable())
				.searchStructuredTypes(oldTypeEntry.getTypeLibrary()));
		return search.stream().filter(StructuredType.class::isInstance).map(StructuredType.class::cast).toList();
	}

	private String buildLabel(final String fbFileName, final String projectName) {
		return fbFileName + " [" + projectName + "]"; //$NON-NLS-1$
	}
}
