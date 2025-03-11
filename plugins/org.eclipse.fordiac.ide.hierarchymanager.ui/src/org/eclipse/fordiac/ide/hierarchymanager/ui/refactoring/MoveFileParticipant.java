/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Qemal Alliu - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.hierarchymanager.ui.refactoring;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Leaf;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.RootLevel;
import org.eclipse.fordiac.ide.hierarchymanager.ui.util.HierarchyManagerRefactoringUtil;
import org.eclipse.fordiac.ide.hierarchymanager.ui.util.HierarchyManagerUtil;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.MoveParticipant;

public class MoveFileParticipant extends MoveParticipant {

	private List<IFile> files = new ArrayList<>();

	private IResource element;

	private RootLevel plantHierarchy;

	@Override
	protected boolean initialize(final Object element) {
		if (element instanceof final IResource resource) {
			this.element = resource;

			if (!HierarchyManagerRefactoringUtil.plantHierachyExists(resource.getProject())) {
				plantHierarchy = null;
			} else {
				plantHierarchy = HierarchyManagerRefactoringUtil.getPlantHierarchy(resource.getProject());

				try {
					this.files = HierarchyManagerRefactoringUtil.getFilesFromResource(resource);
				} catch (final CoreException e) {
					return false;
				}
			}
		}
		return !(plantHierarchy == null || files.isEmpty());
	}

	@Override
	public String getName() {
		return "Fix references on plant hierarchy"; //$NON-NLS-1$
	}

	@Override
	public RefactoringStatus checkConditions(final IProgressMonitor pm, final CheckConditionsContext context)
			throws OperationCanceledException {
		return new RefactoringStatus();
	}

	@Override
	public Change createChange(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		try {
			pm.beginTask("Creating change...", 1); //$NON-NLS-1$

			final List<Leaf> leaves = new ArrayList<>();

			for (final IFile file : files) {
				final List<Leaf> matches = HierarchyManagerUtil.searchLeaf(plantHierarchy,
						leaf -> leaf.getContainerFileName().contains(file.getName()));
				leaves.addAll(matches);
			}

			if (!leaves.isEmpty()) {
				final IFolder destination = (IFolder) this.getArguments().getDestination();

				return new SafeResourceRefactoringChange(plantHierarchy, leaves,
						HierarchyManagerRefactoringUtil.getOldPath(element),
						HierarchyManagerRefactoringUtil.getDestinationPath(element, destination));
			}

		} finally {
			pm.done();
		}

		return null;
	}

}
