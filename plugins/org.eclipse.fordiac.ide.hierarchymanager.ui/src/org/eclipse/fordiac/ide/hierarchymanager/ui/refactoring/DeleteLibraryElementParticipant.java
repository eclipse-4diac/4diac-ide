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

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Leaf;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.RootLevel;
import org.eclipse.fordiac.ide.hierarchymanager.ui.listeners.HierachyManagerUpdateListener;
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

	private IFile file;

	@Override
	protected boolean initialize(final Object element) {

		if (element instanceof final IFile file) {
			this.file = file;
		}

		Display.getDefault().syncExec(() -> {
			final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			PlantHierarchyView view = null;

			if (page != null) {
				view = (PlantHierarchyView) page.findView("org.eclipse.fordiac.ide.hierarchymanager.view"); //$NON-NLS-1$

			}

			if (view != null) {
				plantHierarchy = (RootLevel) view.getCommonViewer().getInput();
			} else if (element instanceof final IFile file) {
				plantHierarchy = (RootLevel) HierachyManagerUpdateListener.loadPlantHierachy(file.getProject());
			}
		});

		return true;
	}

	@Override
	public String getName() {
		return "Delete element of plant hierarchy";
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

			final List<Leaf> leaves = HierarchyManagerUtil.searchLeaf(plantHierarchy,
					leaf -> leaf.getContainerFileName().contains(file.getName()));

			if (!leaves.isEmpty()) {
				return new SafePlantElementDeletionChange(plantHierarchy, leaves);
			}

			return null;
		} finally {
			pm.done();
		}
	}

}
