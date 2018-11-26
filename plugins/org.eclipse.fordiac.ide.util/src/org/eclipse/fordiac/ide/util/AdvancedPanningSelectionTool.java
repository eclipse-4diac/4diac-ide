/*******************************************************************************
 * Copyright (c) 2008, 2009, 2012 Profactor GbmH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.util;

import org.eclipse.draw2d.Cursors;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.requests.SelectionRequest;
import org.eclipse.gef.tools.SelectionTool;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Cursor;

/**
 * The Class AdvancedPanningSelectionTool.
 */
public class AdvancedPanningSelectionTool extends SelectionTool {

	private boolean isSpaceBarDown = false;
	private Point viewLocation;
	private boolean moved = false;

	/**
	 * The state to indicate that the space bar has been pressed but no drag has
	 * been initiated.
	 */
	protected static final int PAN = SelectionTool.MAX_STATE << 1;

	/**
	 * Checks if is moved.
	 * 
	 * @return true, if is moved
	 */
	public boolean isMoved() {
		return moved;
	}

	/**
	 * The state to indicate that a pan is in progress.
	 */
	protected static final int PAN_IN_PROGRESS = PAN << 1;

	/** Max state */
	protected static final int MAX_STATE = PAN_IN_PROGRESS;

	/**
	 * Returns <code>true</code> if spacebar condition was accepted.
	 * 
	 * @param e
	 *          the key event
	 * @return true if the space bar was the key event.
	 */

	protected boolean acceptSpaceBar(KeyEvent e) {
		return (e.character == ' ' && (e.stateMask & SWT.MODIFIER_MASK) == 0);
	}

	/**
	 * @see org.eclipse.gef.tools.AbstractTool#getDebugName()
	 */
	@Override
	protected String getDebugName() {
		return "Panning Tool";
	}

	/**
	 * @see org.eclipse.gef.tools.AbstractTool#getDebugNameForState(int)
	 */
	@Override
	protected String getDebugNameForState(int state) {
		if (state == PAN) {
			return "Pan Initial";
		} else if (state == PAN_IN_PROGRESS) {
			return "Pan In Progress";
		}
		return super.getDebugNameForState(state);
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
	protected boolean handleButtonDown(int which) {
		if ((which == 3 && getCurrentViewer().getControl() instanceof FigureCanvas) &&
			 (stateTransition(STATE_INITIAL, PAN)) &&
				 (stateTransition(PAN, PAN_IN_PROGRESS))) {
			refreshCursor();
			viewLocation = ((FigureCanvas) getCurrentViewer().getControl()).getViewport().getViewLocation();
			updateTargetRequest();
			((SelectionRequest) getTargetRequest()).setLastButtonPressed(which);
			updateTargetUnderMouse();
			EditPart editpart = getTargetEditPart();
			if (editpart != null) {
				setDragTracker(editpart.getDragTracker(getTargetRequest()));
				lockTargetEditPart(editpart);
			}
			return true;
		}
		if (which == 1 && getCurrentViewer().getControl() instanceof FigureCanvas
				&& stateTransition(PAN, PAN_IN_PROGRESS)) {
			viewLocation = ((FigureCanvas) getCurrentViewer().getControl())
					.getViewport().getViewLocation();
			return true;
		}
		
		if((2 == which) && (getCurrentViewer().getControl() instanceof FigureCanvas)){
			isSpaceBarDown = true;
			if (stateTransition(STATE_INITIAL, PAN)) {
				stateTransition(PAN, PAN_IN_PROGRESS);
				refreshCursor();
				viewLocation = ((FigureCanvas) getCurrentViewer().getControl())
						.getViewport().getViewLocation();
			}
			return true;
		}
		return super.handleButtonDown(which);
	}

	/**
	 * @see org.eclipse.gef.tools.SelectionTool#handleButtonUp(int)
	 */
	@Override
	protected boolean handleButtonUp(int which) {
		if (which == 3 && stateTransition(PAN_IN_PROGRESS, STATE_INITIAL)) {
			refreshCursor();
			Point currentLocation = ((FigureCanvas) getCurrentViewer().getControl())
					.getViewport().getViewLocation();

			if (currentLocation.equals(viewLocation)) {
				moved = false;
			} else {
				moved = true;
			}
			((SelectionRequest) getTargetRequest()).setLastButtonPressed(0);
			setDragTracker(null);
			setState(STATE_INITIAL);
			unlockTargetEditPart();
			return true;
		}
		if (which == 1 && isSpaceBarDown && stateTransition(PAN_IN_PROGRESS, PAN)) {
			return true;
		} else if (which == 1 && stateTransition(PAN_IN_PROGRESS, STATE_INITIAL)) {
			refreshCursor();
			return true;
		}
		
		if (2 == which){
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
		if (isInState(PAN_IN_PROGRESS)
				&& getCurrentViewer().getControl() instanceof FigureCanvas) {
			FigureCanvas canvas = (FigureCanvas) getCurrentViewer().getControl();
			canvas.scrollTo(viewLocation.x - getDragMoveDelta().width, viewLocation.y
					- getDragMoveDelta().height);
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
	protected boolean handleKeyDown(KeyEvent e) {
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
		} else if (isInState(PAN_IN_PROGRESS)) {
			isSpaceBarDown = false;
		}
		return super.handleKeyDown(e);
	}

	/**
	 * @see org.eclipse.gef.tools.SelectionTool#handleKeyUp(org.eclipse.swt.events.KeyEvent)
	 */
	@Override
	protected boolean handleKeyUp(KeyEvent e) {
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

	/* (non-Javadoc)
	 * @see org.eclipse.gef.tools.TargetingTool#getTargetEditPart()
	 */
	@Override
	public EditPart getTargetEditPart() {
		return super.getTargetEditPart();
	}

}
