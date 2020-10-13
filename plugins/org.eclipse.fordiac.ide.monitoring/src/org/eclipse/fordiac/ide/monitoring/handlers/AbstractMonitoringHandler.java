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
import org.eclipse.fordiac.ide.fbtypeeditor.network.viewer.CompositeInstanceViewer;
import org.eclipse.fordiac.ide.gef.DiagramEditor;
import org.eclipse.fordiac.ide.gef.DiagramEditorWithFlyoutPalette;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.RootEditPart;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

public abstract class AbstractMonitoringHandler extends AbstractHandler {

	private RootEditPart rootEditPart = null;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		setEditor(HandlerUtil.getActiveEditor(event));
		return null;
	}

	protected void setEditor(IEditorPart activeEditor) {
		if (activeEditor instanceof DiagramEditorWithFlyoutPalette) {
			DiagramEditorWithFlyoutPalette editorW = (DiagramEditorWithFlyoutPalette) activeEditor;
			rootEditPart = editorW.getViewer().getRootEditPart();
		} else if (activeEditor instanceof CompositeInstanceViewer) {
			DiagramEditor editor = (DiagramEditor) activeEditor;
			rootEditPart = editor.getViewer().getRootEditPart();
		} else {
			rootEditPart = null;
		}
	}

	protected void refreshEditor() {
		if (null != rootEditPart) {
			refresh(rootEditPart);
		}
	}

	private static void refresh(RootEditPart rootEditPart) {
		rootEditPart.refresh();

		List<?> children = rootEditPart.getChildren();
		children.forEach(child -> ((EditPart) child).refresh());
	}
}
