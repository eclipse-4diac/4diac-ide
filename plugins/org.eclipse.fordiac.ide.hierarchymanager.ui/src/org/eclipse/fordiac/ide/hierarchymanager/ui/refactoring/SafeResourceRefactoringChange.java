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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Leaf;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.RootLevel;
import org.eclipse.fordiac.ide.hierarchymanager.ui.handlers.AbstractHierarchyHandler;
import org.eclipse.fordiac.ide.hierarchymanager.ui.operations.UpdateLeafContainerFileNameOperation;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.swt.widgets.Display;

public class SafeResourceRefactoringChange extends Change {

	private final RootLevel plantHierarchy;
	private final List<Leaf> leaves;
	private final String oldPath;
	private final String newPath;

	public SafeResourceRefactoringChange(final RootLevel plantHierarchy, final List<Leaf> leaves, final String oldPath,
			final String newPath) {

		this.plantHierarchy = plantHierarchy;
		this.leaves = leaves;
		this.oldPath = oldPath;
		this.newPath = newPath;
	}

	@Override
	public String getName() {
		return "Update references on Plant Hierarchy"; //$NON-NLS-1$
	}

	@Override
	public void initializeValidationData(final IProgressMonitor pm) {
		// nothing to validate
	}

	@Override
	public RefactoringStatus isValid(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		return new RefactoringStatus();
	}

	@Override
	public Change perform(final IProgressMonitor pm) throws CoreException {
		for (final Leaf leaf : leaves) {
			Display.getDefault().asyncExec(() -> {
				AbstractHierarchyHandler
						.executeOperation((new UpdateLeafContainerFileNameOperation(leaf, oldPath, newPath)));
			});
		}

		return null;
	}

	@Override
	public Object getModifiedElement() {
		return plantHierarchy;
	}

}
