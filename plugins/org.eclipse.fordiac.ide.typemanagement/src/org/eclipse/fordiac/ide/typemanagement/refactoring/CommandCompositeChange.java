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
 *   Mathias Garstenauer - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

/**
 * A CompositeChange which can handle multiple
 * {@link org.eclipse.fordiac.ide.typemanagement.refactoring.AbstractCommandChange
 * AbstractCommandChanges}. It remembers which commands were executed last on
 * the CommandStack of each LibraryElement and considers this in the
 * {@link org.eclipse.fordiac.ide.typemanagement.refactoring.CommandCompositeChange#isValid
 * isValid} method (if the CompositeCommandChange is created as a Undo-Change.
 */
public class CommandCompositeChange extends CompositeChange {
	Collection<Change> validList;

	public CommandCompositeChange(final String name) {
		super(name);
	}

	public CommandCompositeChange(final String name, final Change[] children) {
		super(name, children);
	}

	private CommandCompositeChange(final String name, final Change[] childUndos, final Collection<Change> validList) {
		super(name, childUndos);
		this.validList = validList;
	}

	@Override
	public RefactoringStatus isValid(final IProgressMonitor pm) throws CoreException {
		final RefactoringStatus result = new RefactoringStatus();
		final SubMonitor subMonitor = SubMonitor.convert(pm, getChildren().length);
		final Change[] changeArray = getChildren();

		for (final Change change : changeArray) {
			if (change.isEnabled()
					&& (validList == null || change instanceof CommandUndoChange && validList.contains(change))) {
				result.merge(change.isValid(subMonitor.split(1)));
			} else {
				pm.worked(1);
			}
		}
		return result;

	}

	@Override
	protected Change createUndoChange(final Change[] childUndos) {
		final Map<URI, Change> map = new HashMap<>();
		for (int i = childUndos.length - 1; i >= 0; i--) {
			if (childUndos[i] instanceof final AbstractCommandChange cmdChange) {
				map.put(cmdChange.getElementURI().trimFragment(), cmdChange);
			}
		}
		return new CommandCompositeChange(getName(), childUndos, map.values());
	}
}
