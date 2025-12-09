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
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Node;
import org.eclipse.fordiac.ide.hierarchymanager.ui.operations.DeleteNodeOperation;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class DeleteLevel extends AbstractHierarchyHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final ISelection selection = HandlerUtil.getCurrentSelection(event);
		if ((selection instanceof final StructuredSelection sel)
				&& (sel.getFirstElement() instanceof final Node node)) {
			executeOperation(new DeleteNodeOperation(node));
		}
		return Status.OK_STATUS;
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final ISelection selection = (ISelection) HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_CURRENT_SELECTION_NAME);
		setBaseEnabled((selection instanceof final StructuredSelection sel) && (sel.getFirstElement() instanceof Node));
	}


}
