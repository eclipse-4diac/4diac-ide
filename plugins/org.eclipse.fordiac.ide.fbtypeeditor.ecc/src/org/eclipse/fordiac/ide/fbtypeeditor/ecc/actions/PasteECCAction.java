/*******************************************************************************
 * Copyright (c) 2026 Vikash Kumar Sinha
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vikash Kumar Sinha
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.actions;

import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.Messages;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.PasteECCCommand;
import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.ui.FordiacClipboard;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

public class PasteECCAction extends SelectionAction {

	// control coordinates of the last right click in the editor
	private Point menuPos;

	public PasteECCAction(final IWorkbenchPart part) {
		super(part);
		setId(ActionFactory.PASTE.getId());
		setText(Messages.ECCActions_Paste);
		final ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
		setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE));
		setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE_DISABLED));
	}

	public void setViewerControl(final FigureCanvas canvas) {
		if (canvas != null) {
			canvas.addMenuDetectListener(e -> menuPos = canvas.toControl(e.x, e.y));
		}
	}

	@Override
	protected boolean calculateEnabled() {
		final ECC ecc = getECC();
		final ECCCopyPasteData data = getClipboardData();
		return (ecc != null) && !data.isEmpty() && (data.sourceType() == ecc.getBasicFBType());
	}

	@Override
	public void runWithEvent(final Event event) {
		if (event.widget instanceof FigureCanvas) {
			// Ctrl+V: use the cursor, not an old right click position
			menuPos = null;
		}
		super.runWithEvent(event);
	}

	@Override
	public void run() {
		execute(new PasteECCCommand(getClipboardData(), getECC(), calculatePastePos()));
		menuPos = null;
	}

	private static ECCCopyPasteData getClipboardData() {
		final Object content = FordiacClipboard.INSTANCE.getGraphicalContents();
		return (content instanceof final ECCCopyPasteData data) ? data : ECCCopyPasteData.EMPTY;
	}

	private ECC getECC() {
		return getWorkbenchPart().getAdapter(ECC.class);
	}

	private Position calculatePastePos() {
		final GraphicalViewer viewer = getWorkbenchPart().getAdapter(GraphicalViewer.class);
		final FigureCanvas canvas = (FigureCanvas) viewer.getControl();
		final ZoomManager zoom = (ZoomManager) viewer.getProperty(ZoomManager.class.toString());
		final org.eclipse.draw2d.geometry.Point viewLocation = canvas.getViewport().getViewLocation();
		final Rectangle area = canvas.getClientArea();

		Point mouse = (menuPos != null) ? menuPos : canvas.toControl(Display.getCurrent().getCursorLocation());
		if (!area.contains(mouse.x, mouse.y)) {
			mouse = new Point(area.width / 2, area.height / 2);
		}

		final double scale = (zoom != null) ? zoom.getZoom() : 1.0;
		return CoordinateConverter.INSTANCE.createPosFromScreenCoordinates((int) ((mouse.x + viewLocation.x) / scale),
				(int) ((mouse.y + viewLocation.y) / scale));
	}
}
