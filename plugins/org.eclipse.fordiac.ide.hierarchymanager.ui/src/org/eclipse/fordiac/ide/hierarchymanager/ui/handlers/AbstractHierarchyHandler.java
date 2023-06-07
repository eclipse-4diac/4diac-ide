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

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.fordiac.ide.hierarchymanager.ui.operations.AbstractChangeHierarchyOperation;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.undo.WorkspaceUndoUtil;

public abstract class AbstractHierarchyHandler extends AbstractHandler {

	protected static void executeOperation(final AbstractChangeHierarchyOperation cmd) {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		final IOperationHistory operationHistory = workbench.getOperationSupport().getOperationHistory();
		final IUndoContext undoContext = workbench.getOperationSupport().getUndoContext();
		cmd.addContext(undoContext);

		try {
			operationHistory.execute(cmd, null,
					WorkspaceUndoUtil.getUIInfoAdapter(workbench.getActiveWorkbenchWindow().getShell()));
		} catch (final ExecutionException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}
	}

}