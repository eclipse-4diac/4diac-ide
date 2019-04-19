/*******************************************************************************
 * Copyright (c) 2019 Profactor GbmH, Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl - initial API and implementation and/or 
 *   								  initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.print;

import org.eclipse.fordiac.ide.gef.Messages;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.ui.parts.GraphicalEditor;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

public class PrintPreviewAction extends Action {

	private GraphicalEditor editor;

	private GraphicalViewer viewer;

	/**
	 * Instantiates a new prints the preview action.
	 * 
	 * @param viewer the viewer
	 */
	public PrintPreviewAction(GraphicalViewer viewer) {
		super();
		this.viewer = viewer;
		setId(ActionFactory.PRINT.getId());
		setText(Messages.PrintPreviewAction_LABEL_Print);
		ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages(); 
		setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_ETOOL_PRINT_EDIT));
	    setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_ETOOL_PRINT_EDIT_DISABLED));
	}

	/**
	 * returns true if editor is either an applicationEditor or a
	 * PhysicalViewEditor.
	 * 
	 * @return true, if checks if is enabled
	 */
	@Override
	public boolean isEnabled() {		
		if (viewer != null)  {
			return true;
		}
		
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		return (editor != null || window.getActivePage().getActiveEditor() instanceof GraphicalEditor);
	}

	/**
	 * opens the IEC61499PrintDialog.
	 */
	@Override
	public void run() {
		if (viewer == null) {
			IWorkbenchWindow window = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow();
			if (window.getActivePage().getActiveEditor() instanceof GraphicalEditor) {
				editor = (GraphicalEditor) window.getActivePage().getActiveEditor();
			}
			// open a Print Preview
			if (editor != null) {
				viewer = (GraphicalViewer) editor.getAdapter(GraphicalViewer.class);
			}
		}

		if (null != viewer) {
			Shell shell = viewer.getControl().getShell();
			PrintPreview preview = new PrintPreview(shell, viewer,
					Messages.PrintPreviewAction_LABEL_PrintPreview);
			preview.setBlockOnOpen(true);
			preview.open();
		}
	}
}
