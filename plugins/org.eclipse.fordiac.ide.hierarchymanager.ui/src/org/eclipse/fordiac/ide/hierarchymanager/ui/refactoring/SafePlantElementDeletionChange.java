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
import org.eclipse.fordiac.ide.hierarchymanager.ui.operations.DeleteNodeOperation;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.swt.widgets.Display;

public class SafePlantElementDeletionChange extends Change {

	private final RootLevel rootLevel;

	private final List<Leaf> leaves;

	public SafePlantElementDeletionChange(final RootLevel rootLevel, final List<Leaf> leaves) {
		this.rootLevel = rootLevel;
		this.leaves = leaves;
	}

	@Override
	public String getName() {
		return "Delete from Plant hierarchy";
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

		for (final Leaf l : leaves) {
			Display.getDefault().asyncExec(() -> {
				AbstractHierarchyHandler.executeOperation(new DeleteNodeOperation(l));
			});
		}

		return null;
	}

	@Override
	public Object getModifiedElement() {
		return rootLevel;
	}

}
