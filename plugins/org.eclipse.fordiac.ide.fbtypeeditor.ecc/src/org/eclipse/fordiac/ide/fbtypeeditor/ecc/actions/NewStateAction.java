/*******************************************************************************
 * Copyright (c) 2012 - 2016 fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.actions;

import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.Messages;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.CreateECStateCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editors.ECCEditor;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editors.StateCreationFactory;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.ui.IWorkbenchPart;

public class NewStateAction extends WorkbenchPartAction {

	/**
	 * Create State action id. Value:
	 * <code>"org.eclipse.fordiac.ide.fbtypeeditor.ecc.actions.CreateStateAction"</code>
	 */
	public static final String CREATE_STATE = "org.eclipse.fordiac.ide.fbtypeeditor.ecc.actions.CreateStateAction";//$NON-NLS-1$

	private static StateCreationFactory stateFactory = new StateCreationFactory();
	private FigureCanvas viewerControl;
	private org.eclipse.swt.graphics.Point pos = new org.eclipse.swt.graphics.Point(0, 0);
	private ZoomManager zoomManager;

	public NewStateAction(final IWorkbenchPart part) {
		super(part);
		setId(CREATE_STATE);
		setText(Messages.ECCActions_AddState);
		setImageDescriptor(FordiacImage.ICON_ADD_STATE.getImageDescriptor());
	}

	public void setViewerControl(final FigureCanvas control) {
		viewerControl = control;
		if (null != viewerControl) {
			viewerControl.addMenuDetectListener(e -> pos = viewerControl.toControl(e.x, e.y));
		}
	}

	public void setZoomManager(final ZoomManager zoomManager) {
		this.zoomManager = zoomManager;
	}

	@Override
	protected boolean calculateEnabled() {
		return true; // we can always be enabled
	}

	@Override
	public void run() {
		final ECCEditor editor = (ECCEditor) getWorkbenchPart();

		final Point location = viewerControl.getViewport().getViewLocation();
		final Point realPos = new Point(pos.x + location.x, pos.y + location.y);
		realPos.scale(1.0 / zoomManager.getZoom());

		final ECState model = (ECState) stateFactory.getNewObject();
		execute(new CreateECStateCommand(model, realPos, editor.getModel()));

		editor.outlineSelectionChanged(model);
	}
}
