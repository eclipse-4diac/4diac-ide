/*******************************************************************************
 * Copyright (c) 2019 Profactor GbmH, Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl - initial API and implementation and/or
 *   								  initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.print;

import org.eclipse.fordiac.ide.gef.Messages;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

public class PrintPreviewAction extends Action {

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
		if (null == viewer) {
			viewer = getViewer();
		}
		return (null != viewer);
	}

	private static GraphicalViewer getViewer() {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IEditorPart editor = window.getActivePage().getActiveEditor();
		if (null != editor) {
			return editor.getAdapter(GraphicalViewer.class);
		}
		return null;
	}

	/**
	 * opens the IEC61499PrintDialog.
	 */
	@Override
	public void run() {
		if (null != viewer) {
			Shell shell = viewer.getControl().getShell();
			PrintPreview preview = new PrintPreview(shell, viewer, Messages.PrintPreviewAction_LABEL_PrintPreview);
			preview.setBlockOnOpen(true);
			preview.open();
		}
	}
}
