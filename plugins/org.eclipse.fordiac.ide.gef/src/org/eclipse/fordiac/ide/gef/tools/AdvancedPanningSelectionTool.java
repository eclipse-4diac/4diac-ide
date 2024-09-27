/*******************************************************************************
 * Copyright (c) 2008, 2009, 2012 Profactor GbmH, fortiss GmbH
 *                    2019 - 2020 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 * 	 Bianca Wiesmayr, Alois Zoitl
 *     - improve scrolling
 * *******************************************************************************/
package org.eclipse.fordiac.ide.gef.tools;

import org.eclipse.draw2d.Cursors;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.tools.SelectionTool;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Cursor;

/**
 * The Class AdvancedPanningSelectionTool.
 */
public class AdvancedPanningSelectionTool extends SelectionTool {

	private static final int MOUSE_LEFT = 1;
	private static final int MOUSE_MIDDLE = 2;

	private boolean isSpaceBarDown;
	private Point viewLocation;

	/**
	 * The state to indicate that the space bar has been pressed but no drag has
	 * been initiated.
	 */
	protected static final int PAN = SelectionTool.MAX_STATE << 1;

	/**
	 * The state to indicate that a pan is in progress.
	 */
	protected static final int PAN_IN_PROGRESS = PAN << 1;

	/** Max state */
	protected static final int MAX_STATE = PAN_IN_PROGRESS;

	/**
	 * Returns <code>true</code> if spacebar condition was accepted.
	 *
	 * @param e the key event
	 * @return true if the space bar was the key event.
	 */

	@SuppressWarnings("static-method")
	protected boolean acceptSpaceBar(final KeyEvent e) {
		return ((e.character == ' ') && ((e.stateMask & SWT.MODIFIER_MASK) == 0));
	}

	/**
	 * Returns the cursor used under normal conditions.
	 *
	 * @see #setDefaultCursor(Cursor)
	 * @return the default cursor
	 */
	@Override
	protected Cursor getDefaultCursor() {
		if (isInState(PAN | PAN_IN_PROGRESS)) {
			return Cursors.HAND;
		}
		return super.getDefaultCursor();
	}

	/**
	 * @see org.eclipse.gef.tools.SelectionTool#handleButtonDown(int)
	 */
	@Override
	protected boolean handleButtonDown(final int which) {
		if (getCurrentViewer().getControl() instanceof FigureCanvas) {
			viewLocation = ((FigureCanvas) getCurrentViewer().getControl()).getViewport().getViewLocation();
		}
		switch (which) {
		case MOUSE_LEFT:
			if (stateTransition(PAN, PAN_IN_PROGRESS)) {
				return true;
			}
			break;
		case MOUSE_MIDDLE:
			isSpaceBarDown = true;
			if (stateTransition(STATE_INITIAL, PAN)) {
				stateTransition(PAN, PAN_IN_PROGRESS);
				refreshCursor();
			}
			return true;
		default:
			break;
		}
		return super.handleButtonDown(which);
	}

	/**
	 * @see org.eclipse.gef.tools.SelectionTool#handleButtonUp(int)
	 */
	@Override
	protected boolean handleButtonUp(final int which) {
		if ((MOUSE_LEFT == which) && isSpaceBarDown && stateTransition(PAN_IN_PROGRESS, PAN)) {
			return true;
		}

		if ((MOUSE_LEFT == which) && stateTransition(PAN_IN_PROGRESS, STATE_INITIAL)) {
			refreshCursor();
			return true;
		}

		if (MOUSE_MIDDLE == which) {
			if (stateTransition(PAN_IN_PROGRESS, STATE_INITIAL)) {
				isSpaceBarDown = false;
				refreshCursor();
			}
			return true;
		}

		return super.handleButtonUp(which);
	}

	/**
	 * @see org.eclipse.gef.tools.AbstractTool#handleDrag()
	 */
	@Override
	protected boolean handleDrag() {
		if (isInState(PAN_IN_PROGRESS) && (getCurrentViewer().getControl() instanceof FigureCanvas)) {
			final FigureCanvas canvas = (FigureCanvas) getCurrentViewer().getControl();
			canvas.scrollTo(viewLocation.x - getDragMoveDelta().width, viewLocation.y - getDragMoveDelta().height);
			return true;
		}
		return super.handleDrag();
	}

	/**
	 * @see org.eclipse.gef.tools.SelectionTool#handleFocusLost()
	 */
	@Override
	protected boolean handleFocusLost() {
		if (isInState(PAN | PAN_IN_PROGRESS)) {
			setState(STATE_INITIAL);
			refreshCursor();
			return true;
		}
		return super.handleFocusLost();
	}

	/**
	 * @see org.eclipse.gef.tools.SelectionTool#handleKeyDown(org.eclipse.swt.events.KeyEvent)
	 */
	@Override
	protected boolean handleKeyDown(final KeyEvent e) {
		if (acceptSpaceBar(e)) {
			isSpaceBarDown = true;
			if (stateTransition(STATE_INITIAL, PAN)) {
				refreshCursor();
			}
			return true;
		}
		if (stateTransition(PAN, STATE_INITIAL)) {
			refreshCursor();
			isSpaceBarDown = false;
			return true;
		}
		if (isInState(PAN_IN_PROGRESS)) {
			isSpaceBarDown = false;
		}
		return super.handleKeyDown(e);
	}

	/**
	 * @see org.eclipse.gef.tools.SelectionTool#handleKeyUp(org.eclipse.swt.events.KeyEvent)
	 */
	@Override
	protected boolean handleKeyUp(final KeyEvent e) {
		if (acceptSpaceBar(e)) {
			isSpaceBarDown = false;
			if (stateTransition(PAN, STATE_INITIAL)) {
				refreshCursor();
			}
			return true;
		}

		return super.handleKeyUp(e);
	}

	/**
	 * Returns the current x, y position of the mouse cursor.
	 *
	 * @return the mouse location
	 */
	@Override
	public Point getLocation() {
		return new Point(getCurrentInput().getMouseLocation());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.tools.TargetingTool#getTargetEditPart()
	 */
	@Override
	public EditPart getTargetEditPart() {
		return super.getTargetEditPart();
	}

}
