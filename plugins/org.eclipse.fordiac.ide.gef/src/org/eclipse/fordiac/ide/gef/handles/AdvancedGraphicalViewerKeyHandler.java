/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr - initial implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.handles;

import org.eclipse.fordiac.ide.gef.AdvancedScrollingGraphicalViewer;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;

public class AdvancedGraphicalViewerKeyHandler extends GraphicalViewerKeyHandler {
	private static final int SCROLL_SPEED = 400;

	public AdvancedGraphicalViewerKeyHandler(AdvancedScrollingGraphicalViewer viewer) {
		super(viewer);
	}

	@Override
	public boolean keyPressed(KeyEvent event) {
		switch (event.keyCode) {
		case SWT.ARROW_DOWN:
			getViewer().scrollByOffset(0, SCROLL_SPEED);
			return true;
		case SWT.ARROW_UP:
			getViewer().scrollByOffset(0, -SCROLL_SPEED);
			return true;
		case SWT.ARROW_RIGHT:
			getViewer().scrollByOffset(SCROLL_SPEED, 0);
			return true;
		case SWT.ARROW_LEFT:
			getViewer().scrollByOffset(-SCROLL_SPEED, 0);
			return true;
		case SWT.PAGE_DOWN:
			if ((event.stateMask & SWT.MODIFIER_MASK) == 0) {
				getViewer().scrollByOffset(0, getViewer().getFigureCanvasSize().y);
			} else {
				getViewer().scrollByOffset(getViewer().getFigureCanvasSize().x, 0);
			}
			return true;
		case SWT.PAGE_UP:
			if ((event.stateMask & SWT.MODIFIER_MASK) == 0) {
				getViewer().scrollByOffset(0, -getViewer().getFigureCanvasSize().y);
			} else {
				getViewer().scrollByOffset(-getViewer().getFigureCanvasSize().x, 0);
			}
			return true;
		default:
			return super.keyPressed(event);
		}
	}

	@Override
	public AdvancedScrollingGraphicalViewer getViewer() {
		return (AdvancedScrollingGraphicalViewer) super.getViewer();
	}
}
