/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Aigner
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.ui.refactoring;

import java.util.EnumSet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.library.model.library.Manifest;
import org.eclipse.fordiac.ide.library.model.library.Required;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.refactoring.IFordiacPreviewChange;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;

public class DeleteLibraryDependencyChange extends CompositeChange implements IFordiacPreviewChange {
	private final IProject project;
	private final String symbolicName;
	private final EnumSet<ChangeState> state = EnumSet.noneOf(ChangeState.class);

	public DeleteLibraryDependencyChange(final IProject project, final String symbolicName) {
		super(Messages.DeleteLibraryParticipant_Change_Title);
		this.project = project;
		this.symbolicName = symbolicName;
		state.addAll(this.getDefaultSelection());
	}

	@Override
	public Change perform(final IProgressMonitor pm) throws CoreException {
		if (state.contains(ChangeState.DELETE)) {
			final Manifest projectManifest = ManifestHelper.getContainerManifest(project);
			if (projectManifest == null || projectManifest.getDependencies() == null) {
				return null;
			}
			final Required dependency = projectManifest.getDependencies().getRequired().stream()
					.filter(r -> symbolicName.equals(r.getSymbolicName())).findAny().orElse(null);

			if (dependency != null && ManifestHelper.removeDependency(projectManifest, dependency)
					&& ManifestHelper.saveManifest(projectManifest)) {
				return new AddLibraryDependencyChange(project, dependency);
			}
		}
		return null;
	}

	@Override
	public EnumSet<ChangeState> getState() {
		return state;
	}

	@Override
	public EnumSet<ChangeState> getAllowedChoices() {
		return EnumSet.of(ChangeState.DELETE, ChangeState.NO_CHANGE);
	}

	@Override
	public void addState(final ChangeState newState) {
		state.add(newState);
	}

	@Override
	public EnumSet<ChangeState> getDefaultSelection() {
		return EnumSet.of(ChangeState.DELETE);
	}

}
