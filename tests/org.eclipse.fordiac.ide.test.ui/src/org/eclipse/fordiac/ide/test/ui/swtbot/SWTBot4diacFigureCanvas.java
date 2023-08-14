/*******************************************************************************
 * Copyright (c) 2023 Andrea Zoitl
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Andrea Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.ui.swtbot;

import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefFigureCanvas;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;

public class SWTBot4diacFigureCanvas extends SWTBotGefFigureCanvas {

	public SWTBot4diacFigureCanvas(final Canvas canvas, final LightweightSystem lightweightSystem)
			throws WidgetNotFoundException {
		super(canvas, lightweightSystem);
	}

	public SWTBot4diacFigureCanvas(final FigureCanvas figureCanvas) {
		super(figureCanvas);
	}

	/**
	 * this method moves the mouse a bit between drag and drop and emits mouse
	 * events that handle drags within the canvas
	 *
	 * 4diac requires a mouse movement to pull the connection. The method of the
	 * parent class jumps from one pin to another and thus no connection can be
	 * drawn. For this reason a small mouse movement was inserted.
	 *
	 * @param fromXPosition the relative x position within the canvas to drag from
	 * @param fromYPosition the relative y position within the canvas to drag from
	 * @param toXPosition   the relative x position within the canvas to drag to
	 * @param toYPosition   the relative y position within the canvas to drag to
	 */
	@Override
	public void mouseDrag(final int fromXPosition, final int fromYPosition, final int toXPosition,
			final int toYPosition) {
		UIThreadRunnable.asyncExec(() -> {
			final org.eclipse.swt.events.MouseEvent meMove = wrapMouseEvent(fromXPosition, fromYPosition, 0, 0, 0);
			eventDispatcher.dispatchMouseMoved(meMove);
			final org.eclipse.swt.events.MouseEvent meDown = wrapMouseEvent(fromXPosition, fromYPosition, 1,
					SWT.BUTTON1, 1);
			eventDispatcher.dispatchMousePressed(meDown);

			// addition mouse move to be able to create a connection
			final org.eclipse.swt.events.MouseEvent littleMove = wrapMouseEvent(fromXPosition + 10, fromYPosition + 10,
					1, SWT.BUTTON1, 0);
			eventDispatcher.dispatchMouseMoved(littleMove);

			final org.eclipse.swt.events.MouseEvent meMoveTarget = wrapMouseEvent(toXPosition, toYPosition, 1,
					SWT.BUTTON1, 0);
			eventDispatcher.dispatchMouseMoved(meMoveTarget);
			final org.eclipse.swt.events.MouseEvent meUp = wrapMouseEvent(toXPosition, toYPosition, 1, SWT.BUTTON1, 1);
			eventDispatcher.dispatchMouseReleased(meUp);
		});
	}

	private org.eclipse.swt.events.MouseEvent wrapMouseEvent(final int x, final int y, final int button,
			final int stateMask, final int count) {
		return new org.eclipse.swt.events.MouseEvent(createMouseEvent(x, y, button, stateMask, count));
	}

}
