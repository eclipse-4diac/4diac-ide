/*******************************************************************************
 * Copyright (c) 2025 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.typemanagement.refactoring;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public final class ModelEditChange extends AbstractCommandChange<LibraryElement> {

	private final List<ModelEdit<?>> edits;

	private ModelEditChange(final List<ModelEdit<?>> edits) {
		super(edits.getFirst().getLibraryElementURI(), LibraryElement.class);
		this.edits = edits;
	}

	@Override
	public void initializeValidationData(final LibraryElement element, final IProgressMonitor pm) {
		final SubMonitor subMonitor = SubMonitor.convert(pm, edits.size());
		for (final ModelEdit<?> edit : edits) {
			if (edit.isEnabled()) {
				edit.initializeValidationData(element, subMonitor.newChild(1));
			}
		}
	}

	@Override
	public RefactoringStatus isValid(final LibraryElement element, final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		final RefactoringStatus result = new RefactoringStatus();
		final SubMonitor subMonitor = SubMonitor.convert(pm, edits.size());
		for (final ModelEdit<?> edit : edits) {
			if (edit.isEnabled()) {
				result.merge(edit.isValid(element, subMonitor.split(1)));
			}
		}
		return result;
	}

	@Override
	protected Command createCommand(final LibraryElement element) throws CoreException {
		final CompoundCommand result = new CompoundCommand(getName());
		for (final ModelEdit<?> edit : edits) {
			if (edit.isEnabled()) {
				result.add(edit.createCommand(element));
			}
		}
		return result;
	}

	/**
	 * Get the model edits
	 *
	 * @return The model edits
	 */
	public List<ModelEdit<?>> getModelEdits() {
		return Collections.unmodifiableList(edits);
	}

	@Override
	public void setEnabled(final boolean enabled) {
		super.setEnabled(enabled);
		for (final ModelEdit<?> edit : edits) {
			edit.setEnabled(enabled);
		}
	}

	@Override
	public Change[] getChildren() {
		return edits.toArray(Change[]::new);
	}

	public static CompositeChange fromModelEdits(final String name, final List<? extends ModelEdit<?>> edits) {
		if (edits.isEmpty()) {
			return null;
		}
		final Map<URI, List<ModelEdit<?>>> groupedEdits = edits.stream()
				.collect(Collectors.groupingBy(ModelEdit::getLibraryElementURI));
		final Change[] changes = groupedEdits.values().stream().map(ModelEditChange::new).toArray(Change[]::new);
		return new CompositeChange(name, changes);
	}
}
