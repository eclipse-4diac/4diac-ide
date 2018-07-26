/*******************************************************************************
 * Copyright (c) 2013, 2015, 2017 fortiss GmbH, Profactor GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl, Gerhard Ebenhofer, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.actions;

import java.util.Iterator;

import org.eclipse.fordiac.ide.fbtypeeditor.network.viewer.CompositeInstanceViewer;
import org.eclipse.fordiac.ide.gef.DiagramEditor;
import org.eclipse.fordiac.ide.gef.DiagramEditorWithFlyoutPalette;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.RootEditPart;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public abstract class AbstractMonitoringAction implements IObjectActionDelegate {

	//todo find better solution for this
	private DiagramEditorWithFlyoutPalette editorWithPalette = null;
	private DiagramEditor editor = null;

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		if (targetPart instanceof DiagramEditorWithFlyoutPalette){
			DiagramEditorWithFlyoutPalette editorW = (DiagramEditorWithFlyoutPalette)targetPart;
			editorWithPalette = editorW;
		}else if(targetPart instanceof CompositeInstanceViewer) {
			editor = (DiagramEditor) targetPart;		
		}

	}

	protected void refreshEditor() {
		RootEditPart rootEditPart = null;
		if (null != editor) {
			rootEditPart = editor.getViewer().getRootEditPart(); 
		} else if(null != editorWithPalette){
			rootEditPart = editorWithPalette.getViewer().getRootEditPart();
		}
		if(null != rootEditPart){ 
			refresh(rootEditPart);
		}
	}

	@SuppressWarnings("rawtypes")
	private static void refresh(RootEditPart rootEditPart) {
		rootEditPart.refresh();
		for (Iterator iterator = rootEditPart.getChildren().iterator(); iterator.hasNext();) {
			EditPart part = (EditPart) iterator.next();
			part.refresh();
		}
	}
}
