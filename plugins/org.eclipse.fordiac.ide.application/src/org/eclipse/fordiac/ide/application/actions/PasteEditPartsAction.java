/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, AIT, fortiss GmbH
 * 				 2018 - 2020 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Filip Andren
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - fixed copy/paste handling
 *   Bianca Wiesmayr - fixed copy/paste position
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.actions;

import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.commands.PasteCommand;
import org.eclipse.fordiac.ide.application.editors.FBNetworkEditor;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.actions.Clipboard;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

/**
 * The Class PasteEditPartsAction.
 */
public class PasteEditPartsAction extends SelectionAction {

	private Point pasteRefPosition;

	/**
	 * Instantiates a new paste edit parts action.
	 *
	 * @param editor the editor
	 */
	public PasteEditPartsAction(IWorkbenchPart editor) {
		super(editor);
	}

	@Override
	protected boolean calculateEnabled() {
		FBNetwork fbNetwork = getFBNetwork();
		return (null != fbNetwork) && !getClipboardContents().isEmpty();
	}

	protected Command createPasteCommand() {
		FBNetwork fbNetwork = getFBNetwork();
		if (null != fbNetwork) {
			return new PasteCommand(getClipboardContents(), fbNetwork, pasteRefPosition);
		}
		return new CompoundCommand();
	}

	private List<? extends Object> getClipboardContents() {
		Object obj = Clipboard.getDefault().getContents();
		if (obj instanceof List) {
			return (List<?>)obj;
		}
		return Collections.emptyList();
	}

	@Override
	protected void init() {
		setId(ActionFactory.PASTE.getId());
		setText(Messages.PasteEditPartsAction_Text);
		ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
		setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE));
		setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE_DISABLED));
	}

	@Override
	public void runWithEvent(Event event) {
		if (event.widget instanceof FigureCanvas) {
			// handles insertion via copy&paste
			setMouseLocationAsPastePos(event);
		}
		super.runWithEvent(event);
	}

	public void setMouseLocationAsPastePos(Event event) {
		FigureCanvas figureCanvas = (FigureCanvas) event.widget;
		org.eclipse.draw2d.geometry.Point viewLocation = figureCanvas.getViewport().getViewLocation();
		Point mouseLocation = Display.getCurrent().getCursorLocation();
		mouseLocation = figureCanvas.toControl(mouseLocation.x, mouseLocation.y);

		if (figureCanvas.getBounds().contains(mouseLocation.x, mouseLocation.y)) {
			ZoomManager zoomManager = ((FBNetworkEditor) getWorkbenchPart()).getZoomManger();
			mouseLocation.x += viewLocation.x;
			mouseLocation.y += viewLocation.y;
			setPastRefPosition(new org.eclipse.draw2d.geometry.Point(mouseLocation.x, mouseLocation.y)
					.scale(1.0 / zoomManager.getZoom()));
		} else {
			Dimension visibleArea = figureCanvas.getViewport().getSize();
			setPastRefPosition(
					new Point(viewLocation.x + (visibleArea.width / 2), viewLocation.y + (visibleArea.height / 2)));
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		execute(createPasteCommand());
		pasteRefPosition = null;
	}

	protected FBNetwork getFBNetwork() {
		if (getWorkbenchPart() instanceof FBNetworkEditor) {
			return ((FBNetworkEditor) getWorkbenchPart()).getModel();
		}
		return null;
	}

	public void setPastRefPosition(Point pt) {
		pasteRefPosition = pt;
	}

	public void setPastRefPosition(org.eclipse.draw2d.geometry.Point point) {
		setPastRefPosition(new Point(point.x, point.y));
	}

}
