/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.hierarchymanager.ui.operations;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Level;

public class ChangeLevelCommentOperation extends AbstractChangeHierarchyOperation {

	private final Level level;
	private final String newComment;
	private final String oldComment;

	public ChangeLevelCommentOperation(final Level level, final String comment) {
		super("change level comment"); //$NON-NLS-1$
		this.level = level;
		this.newComment = comment;
		this.oldComment = level.getComment();
	}

	@Override
	public IStatus execute(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
		level.setComment(newComment);
		saveHierarchy(level, monitor);
		return Status.OK_STATUS;
	}

	@Override
	public IStatus redo(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
		level.setComment(newComment);
		saveHierarchy(level, monitor);
		return Status.OK_STATUS;
	}

	@Override
	public IStatus undo(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
		level.setComment(oldComment);
		saveHierarchy(level, monitor);
		return Status.OK_STATUS;
	}

}
