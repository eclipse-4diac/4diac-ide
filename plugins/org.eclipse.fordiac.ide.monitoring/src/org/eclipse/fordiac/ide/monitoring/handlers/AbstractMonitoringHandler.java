/*******************************************************************************
 * Copyright (c) 2016 - 2017 fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.handlers;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.model.ui.editors.HandlerHelper;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.RootEditPart;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

public abstract class AbstractMonitoringHandler extends AbstractHandler {

	private RootEditPart rootEditPart = null;

	@Override
	public final Object execute(final ExecutionEvent event) throws ExecutionException {
		setEditor(HandlerUtil.getActiveEditor(event));
		final ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof final StructuredSelection structSel) {
			doExecute(event, structSel);
		}
		return Status.OK_STATUS;
	}

	protected abstract void doExecute(ExecutionEvent event, StructuredSelection structSel) throws ExecutionException;

	protected void setEditor(final IEditorPart activeEditor) {

		final GraphicalViewer viewer = HandlerHelper.getViewer(activeEditor);
		if (null != viewer) {
			rootEditPart = viewer.getRootEditPart();
		} else {
			rootEditPart = null;
		}
	}

	protected void refreshEditor() {
		if (null != rootEditPart) {
			refresh(rootEditPart);
		}
	}

	public static void refresh(final RootEditPart rootEditPart) {
		rootEditPart.refresh();

		final List<?> children = rootEditPart.getChildren();
		children.forEach(child -> ((EditPart) child).refresh());
	}
}
