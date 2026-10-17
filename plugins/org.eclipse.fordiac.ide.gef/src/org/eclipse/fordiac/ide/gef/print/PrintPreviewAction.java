/*******************************************************************************
 * Copyright (c) 2019 Profactor GbmH, Johannes Kepler University, HR Agrartechnik
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
 *   Moritz Ortmeier - print the currently visible page of multi-page and
 *                     breadcrumb editors (e.g. the FB network of a Subapp)
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.print;

import org.eclipse.fordiac.ide.gef.Messages;
import org.eclipse.fordiac.ide.gef.utilities.GraphicalViewerResolver;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

public class PrintPreviewAction extends Action {

	/** The viewer of the editor this action belongs to, used if the active editor has none. */
	private final GraphicalViewer viewer;

	/** Instantiates a new prints the preview action.
	 *
	 * @param viewer the viewer */
	public PrintPreviewAction(final GraphicalViewer viewer) {
		super();
		this.viewer = viewer;
		setId(ActionFactory.PRINT.getId());
		setText(Messages.PrintPreviewAction_LABEL_Print);
		final ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
		setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_ETOOL_PRINT_EDIT));
		setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_ETOOL_PRINT_EDIT_DISABLED));
	}

	/** returns true if there is a viewer that can be printed.
	 *
	 * @return true, if checks if is enabled */
	@Override
	public boolean isEnabled() {
		return getViewerToPrint() != null;
	}

	/** The viewer of the visible page of the active editor, e.g. the FB Network of a Subapp, or the viewer this
	 * action was created for if the active editor has none. */
	private GraphicalViewer getViewerToPrint() {
		final GraphicalViewer activeViewer = getActiveViewer();
		return activeViewer != null ? activeViewer : usableViewer();
	}

	/** The viewer of this action, or null if its editor was closed meanwhile. */
	private GraphicalViewer usableViewer() {
		return (viewer != null && viewer.getControl() != null && !viewer.getControl().isDisposed()) ? viewer : null;
	}

	private static GraphicalViewer getActiveViewer() {
		final IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window == null || window.getActivePage() == null) {
			return null;
		}
		final IEditorPart editor = window.getActivePage().getActiveEditor();
		return GraphicalViewerResolver.resolveActiveViewer(editor);
	}

	/** opens the IEC61499PrintDialog. */
	@Override
	public void run() {
		// Always resolve from the currently active editor so we print whichever
		// tab (FB Interface / FB Network) is visible.
		final GraphicalViewer viewerToPrint = getViewerToPrint();
		if (null != viewerToPrint) {
			final Shell shell = viewerToPrint.getControl().getShell();
			final PrintPreview preview = new PrintPreview(shell, viewerToPrint,
					Messages.PrintPreviewAction_LABEL_PrintPreview);
			preview.setBlockOnOpen(true);
			preview.open();
		}
	}
}
