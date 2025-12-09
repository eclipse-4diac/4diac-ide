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
package org.eclipse.fordiac.ide.hierarchymanager.ui.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Level;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.RootLevel;
import org.eclipse.fordiac.ide.hierarchymanager.ui.operations.CreateLevelOperation;
import org.eclipse.fordiac.ide.hierarchymanager.ui.view.PlantHierarchyView;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class CreateLevel extends AbstractHierarchyHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final ISelection selection = HandlerUtil.getCurrentSelection(event);

		final EObject parent = getParent(selection, event);
		if (parent != null) {

			final String newName = showCreateDialog();
			if (null != newName) {
				executeOperation(new CreateLevelOperation(parent, newName));
			}
		}
		return Status.OK_STATUS;
	}

	private static EObject getParent(final ISelection selection, final ExecutionEvent event) {
		if (selection instanceof final StructuredSelection sel) {
			final Object firstElement = sel.getFirstElement();
			if (firstElement instanceof final Level level) {
				return level;
			}
			if (firstElement == null) {
				return getParentFromPart(HandlerUtil.getActivePart(event));
			}
		}
		return null;
	}

	private static EObject getParentFromPart(final IWorkbenchPart activePart) {
		if ((activePart instanceof final PlantHierarchyView phView)
				&& (phView.getCommonViewer().getInput() instanceof final RootLevel root)) {
			return root;
		}
		return null;
	}

	private static String showCreateDialog() {
		final InputDialog dialog = new InputDialog(Display.getDefault().getActiveShell(), "Create Level",
				"Enter new level name", "", null);
		final int ret = dialog.open();
		if (ret == Window.OK) {
			return dialog.getValue();
		}
		return null;
	}
}
