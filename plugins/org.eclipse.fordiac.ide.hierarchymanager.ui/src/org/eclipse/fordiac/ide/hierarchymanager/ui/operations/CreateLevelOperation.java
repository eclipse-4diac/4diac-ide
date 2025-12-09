/*******************************************************************************
 * Copyright (c) 2023 Primetals Technology Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.hierarchymanager.ui.operations;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.HierarchyFactory;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Level;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.RootLevel;

public class CreateLevelOperation extends AbstractChangeHierarchyOperation {

	private final EObject parent;
	private final String name;
	private Level newLevel;

	public CreateLevelOperation(final EObject parent, final String name) {
		super("Create level");
		this.parent = parent;
		this.name = name;
	}

	@Override
	public IStatus execute(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
		newLevel = HierarchyFactory.eINSTANCE.createLevel();
		newLevel.setName(name);
		insertNewLevel();
		saveHierarchy(parent, monitor);
		return Status.OK_STATUS;
	}

	@Override
	public IStatus redo(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
		insertNewLevel();
		saveHierarchy(parent, monitor);
		return Status.OK_STATUS;
	}

	@Override
	public IStatus undo(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
		if (parent instanceof final RootLevel root) {
			root.getLevels().remove(newLevel);
		}
		if (parent instanceof final Level level) {
			level.getChildren().remove(newLevel);
		}
		saveHierarchy(parent, monitor);
		return Status.OK_STATUS;
	}

	private void insertNewLevel() {
		if (parent instanceof final RootLevel root) {
			root.getLevels().add(newLevel);
		}
		if (parent instanceof final Level level) {
			level.getChildren().add(newLevel);
		}
	}

}
