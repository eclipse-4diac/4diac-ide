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
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Leaf;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.RootLevel;
import org.eclipse.fordiac.ide.hierarchymanager.ui.listeners.HierachyManagerUpdateListener;
import org.eclipse.fordiac.ide.hierarchymanager.ui.util.HierarchyManagerRefactoringUtil;
import org.eclipse.fordiac.ide.hierarchymanager.ui.util.HierarchyManagerUtil;
import org.eclipse.fordiac.ide.hierarchymanager.ui.view.PlantHierarchyView;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.DeleteParticipant;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

public class DeleteLibraryElementParticipant extends DeleteParticipant {

	private RootLevel plantHierarchy;

	private List<IFile> files;

	@Override
	protected boolean initialize(final Object element) {

		if (element instanceof final IResource resource) {
			try {
				this.files = HierarchyManagerRefactoringUtil.getFilesFromResource(resource);
			} catch (final CoreException e) {
				return false;
			}
		}

		Display.getDefault().syncExec(() -> {
			final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			PlantHierarchyView view = null;

			if (page != null) {
				view = (PlantHierarchyView) page.findView("org.eclipse.fordiac.ide.hierarchymanager.view"); //$NON-NLS-1$
			}

			if (view != null) {
				final Object input = view.getCommonViewer().getInput();
				if (input instanceof final RootLevel rootLevel) {
					plantHierarchy = rootLevel;
				}
			} else if (element instanceof final IResource resource) {
				plantHierarchy = (RootLevel) HierachyManagerUpdateListener.loadPlantHierachy(resource.getProject());
			}
		});

		return plantHierarchy != null;
	}

	@Override
	public String getName() {
		return "Delete element of plant hierarchy"; //$NON-NLS-1$
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
				return new SafePlantElementDeletionChange(plantHierarchy, leaves);
			}

			return null;
		} finally {
			pm.done();
		}
	}

}
