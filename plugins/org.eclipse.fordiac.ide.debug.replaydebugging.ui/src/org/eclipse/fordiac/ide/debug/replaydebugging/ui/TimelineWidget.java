/*******************************************************************************
 * Copyright (c) 2025 Jose Cabral
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug.replaydebugging.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.debug.replaydebugging.core.ReplayNavigator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Slider;

/**
 * A widget for displaying and interacting with a timeline for replay
 * navigation.
 *
 * This widget provides the following controls: - Slider: Allows the user to
 * navigate through timeline events - Previous ("<"): Moves one event backward
 * in the timeline - Jump Backward ("|<"): Jumps to the closest previous
 * highlighted event or the beginning if no highlighted event is present</li> -
 * Jump Forward (">|"): Jumps to the closest next highlighted event or to the
 * end if no highlighted event is present</li> - Next (">"): Moves one event
 * forward in the timeline
 *
 */
public class TimelineWidget extends Composite {

	// slider and control
	private Canvas lineCanvas;
	private Slider timelineSlider;

	private Button previousButton;
	private Button jumpBackButton;
	private Button jumpForwardButton;
	private Button nextButton;

	private static final String PREVIOUS_BUTTON_TEXT = "<"; //$NON-NLS-1$
	private static final String JUMP_BACK_BUTTON_TEXT = "|<"; //$NON-NLS-1$
	private static final String JUMP_FORWARD_BUTTON_TEXT = ">|"; //$NON-NLS-1$
	private static final String NEXT_BUTTON_TEXT = ">"; //$NON-NLS-1$

	private Label positionLabel;

	private static final int SLIDER_THUMB_SIZE = 1; // Size of the slider thumb

	private boolean mouseDownOnCanvas = false;

	private List<Integer> highlightPositions = new ArrayList<>();

	private final ReplayNavigator replayNavigator;

	/**
	 * Constructs a new TimelineWidget.
	 *
	 * @param parent the parent composite
	 * @param style  the SWT style flags
	 */
	public TimelineWidget(final String name, final ReplayNavigator replayNavigator, final Composite parent,
			final int style, final CollapsableComposite.CollapseListener collapsableListener) {
		super(parent, style);
		this.replayNavigator = replayNavigator;
		setLayout(new GridLayout(1, false));
		setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		createControlGroup(name, collapsableListener);
		updateCurrentPositionInfo();
	}

	@Override
	public boolean setFocus() {
		return lineCanvas.setFocus();
	}

	/**
	 * Sets the positions to be highlighted on the slider.
	 *
	 * @param datapointOfInterest the datapoint which is used to highlight the
	 *                            positions
	 */
	public void setHighlightPositions(final String datapointOfInterest) {
		this.highlightPositions = replayNavigator.getEventsThatTouch(datapointOfInterest);
		lineCanvas.redraw();
	}

	/**
	 * @brief Set the event in replay navigator from an X position in the canvas.
	 *
	 * @param x the X position in the canvas
	 */
	private void setEventToCanvasX(final int x) {
		replayNavigator.moveToEvent(getEventFromCanvasX(x));
		timelineSlider.setSelection(replayNavigator.getCurrentEventNumber());
		updateCurrentPositionInfo();
	}

	/**
	 * @brief Get the event number in the replay navigator from an X position in the
	 *        canvas.
	 *
	 * @param x the X position in the canvas
	 *
	 * @return the event number corresponding to the X position
	 */
	private int getEventFromCanvasX(final int x) {
		final int maxEvent = timelineSlider.getMaximum() - timelineSlider.getThumb();
		if (maxEvent <= 0) {
			return 0;
		}
		final int width = lineCanvas.getSize().x;
		final double singleEventStepInPixels = (double) width / maxEvent;
		return (int) Math.round(x / singleEventStepInPixels); // round to the nearest event
	}

	// construct the widget with all internal things
	private CollapsableComposite createControlGroup(final String name,
			final CollapsableComposite.CollapseListener collapsableListener) {
		final CollapsableComposite controlGroup = new CollapsableComposite(this, name, collapsed -> {
			layout(true, true);
			collapsableListener.onCollapse(collapsed);
		});
		final Composite contentsParent = controlGroup.getContentsParent();

		lineCanvas = new Canvas(contentsParent, SWT.NONE);
		lineCanvas.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		lineCanvas.addPaintListener(new TimesliderPainter());

		// listen to the user clicking on the canvas directly
		lineCanvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(final MouseEvent e) {
				mouseDownOnCanvas = true;
				setEventToCanvasX(e.x);
			}

			@Override
			public void mouseUp(final MouseEvent e) {
				mouseDownOnCanvas = false;
				setEventToCanvasX(e.x);
			}
		});

		// listen to the user dragging on the canvas directly
		lineCanvas.addMouseMoveListener(e -> {
			if (mouseDownOnCanvas) {
				setEventToCanvasX(e.x);
			}
		});

		// listen to the user pressing keys while the canvas has focus
		lineCanvas.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(final KeyEvent e) {
				if ((e.stateMask & SWT.CTRL) != 0) {
					if (e.keyCode == SWT.ARROW_RIGHT && jumpForwardButton.isEnabled()) {
						jumpForwardButton.notifyListeners(SWT.Selection, new Event());
					} else if (e.keyCode == SWT.ARROW_LEFT && jumpBackButton.isEnabled()) {
						jumpBackButton.notifyListeners(SWT.Selection, new Event());
					}
				} else if (e.keyCode == SWT.ARROW_RIGHT && nextButton.isEnabled()) {
					nextButton.notifyListeners(SWT.Selection, new Event());
				} else if (e.keyCode == SWT.ARROW_LEFT && previousButton.isEnabled()) {
					previousButton.notifyListeners(SWT.Selection, new Event());
				}
			}
		});

		timelineSlider = new Slider(contentsParent, SWT.HORIZONTAL);
		timelineSlider.setThumb(SLIDER_THUMB_SIZE); // make the bar of size 1
		timelineSlider.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		timelineSlider.setMinimum(0);
		timelineSlider.setMaximum(replayNavigator.getAmountOfEvents() + timelineSlider.getThumb());
		timelineSlider.setEnabled(true);
		timelineSlider.setSelection(replayNavigator.getCurrentEventNumber());
		timelineSlider.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				setFocus();
				replayNavigator.moveToEvent(timelineSlider.getSelection());
				updateCurrentPositionInfo();
			}
		});

		positionLabel = new Label(contentsParent, SWT.NONE);
		final GridData positionLayoutData = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		// twice the maximum, plus the / and the two blank space after and before it
		final int maxHintLength = String.valueOf(timelineSlider.getMaximum() - timelineSlider.getThumb()).length() * 2
				+ 3;
		positionLayoutData.widthHint = maxHintLength * 12; // Approximate pixel width per character
		positionLabel.setLayoutData(positionLayoutData);
		positionLabel.setAlignment(SWT.CENTER);

		final Composite buttonComposite = new Composite(contentsParent, SWT.NONE);
		buttonComposite.setLayout(new GridLayout(4, false));
		buttonComposite.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));

		jumpBackButton = createNavButton(buttonComposite, JUMP_BACK_BUTTON_TEXT,
				() -> replayNavigator.moveToEvent(getBackwardsJump()));
		previousButton = createNavButton(buttonComposite, PREVIOUS_BUTTON_TEXT, replayNavigator::moveOneEventBackwards);
		nextButton = createNavButton(buttonComposite, NEXT_BUTTON_TEXT, replayNavigator::moveOneEventForward);
		jumpForwardButton = createNavButton(buttonComposite, JUMP_FORWARD_BUTTON_TEXT,
				() -> replayNavigator.moveToEvent(getForwardJump()));

		final Composite highlightComposite = new Composite(contentsParent, SWT.NONE);
		highlightComposite.setLayout(new GridLayout(2, false));
		highlightComposite.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));

		return controlGroup;
	}

	private class TimesliderPainter implements PaintListener {
		private static final int NUMBER_OF_SECTIONS_IN_CANVAS = 10;
		private static final int TEXT_Y_OFFSET_IN_CANVAS = 15; // Y position of the text next to the lines
		private static final int TEXT_X_OFFSET_IN_CANVAS = 2; // X position of the text next to the lines
		private static final int TEXT_X_OFFSET_IN_CANVAS_LAST = -10; // X position of the text next to the lines for the
																		// last one
		private static final int DIVITIONS_COLOR = SWT.COLOR_BLACK;
		private static final int HIGHTLIGHT_COLOR = SWT.COLOR_RED;
		private static final int CURRENT_POSITION_COLOR = SWT.COLOR_BLUE;

		@Override
		public void paintControl(final PaintEvent paintEvent) {
			final int maxEvent = timelineSlider.getMaximum() - timelineSlider.getThumb();
			if (maxEvent <= 0) {
				return;
			}

			final int width = lineCanvas.getSize().x;
			final int height = lineCanvas.getSize().y;
			final int singleEventStepInPixels = width / maxEvent;

			int divisions = Math.min(maxEvent, NUMBER_OF_SECTIONS_IN_CANVAS);
			// handle edge cases for low amount of events that the divisions are not at
			// integer positions
			if ((maxEvent / divisions) * divisions != maxEvent) {
				divisions = maxEvent / (int) Math.ceil((double) maxEvent / divisions);
			}
			final int eventDivisionStep = maxEvent / divisions;
			divisions = maxEvent / eventDivisionStep; // compensate for rounding errors

			paintEvent.gc.setForeground(getDisplay().getSystemColor(DIVITIONS_COLOR));
			for (int i = 0; i < divisions; i++) {
				final int eventPosition = i * eventDivisionStep;
				final int x = eventPosition * singleEventStepInPixels;
				paintEvent.gc.drawLine(x, 0, x, height);
				final String label = String.valueOf(eventPosition);
				paintEvent.gc.drawString(label, x + TEXT_X_OFFSET_IN_CANVAS, height - TEXT_Y_OFFSET_IN_CANVAS, true);
			}
			// handle last division differently by having the number on the left side of the
			// division
			paintEvent.gc.drawLine(width - 1, 0, width - 1, height);
			paintEvent.gc.drawString(String.valueOf(divisions * eventDivisionStep),
					width - 1 + TEXT_X_OFFSET_IN_CANVAS_LAST, height - TEXT_Y_OFFSET_IN_CANVAS, true);

			paintEvent.gc.setForeground(getDisplay().getSystemColor(HIGHTLIGHT_COLOR));
			for (final Integer pos : highlightPositions) {
				if (pos.intValue() >= 0 && pos.intValue() <= maxEvent) {
					final int x = pos.intValue() * singleEventStepInPixels;
					paintEvent.gc.drawLine(x, 0, x, height);
					final String label = String.valueOf(pos);
					paintEvent.gc.drawString(label, x + TEXT_X_OFFSET_IN_CANVAS, TEXT_Y_OFFSET_IN_CANVAS, true);
				}
			}

			paintEvent.gc.setForeground(getDisplay().getSystemColor(CURRENT_POSITION_COLOR));
			final int current = timelineSlider.getSelection();
			final int x = current * singleEventStepInPixels;
			final String label = String.valueOf(current);
			if (current == timelineSlider.getMaximum() - timelineSlider.getThumb()) { // draw number with another offset
																						// for the last one
				paintEvent.gc.drawLine(width - 1, 0, width - 1, height);
				paintEvent.gc.drawString(label, x + TEXT_X_OFFSET_IN_CANVAS_LAST, height / 2, true); // place the text
																										// in the middle
																										// of the line
			} else {
				paintEvent.gc.drawLine(x, 0, x, height);
				paintEvent.gc.drawString(label, x + TEXT_X_OFFSET_IN_CANVAS, height / 2, true); // place the text in the
																								// middle of the line
			}
		}
	}

	/**
	 * Interface for moving the slider in the timeline. This is used to abstract the
	 * movement logic for different buttons.
	 */
	private interface SliderMover {
		void moveSlider();
	}

	private Button createNavButton(final Composite parent, final String text, final SliderMover action) {
		final Button button = new Button(parent, SWT.PUSH);
		button.setText(text);
		button.setEnabled(true);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				setFocus();
				action.moveSlider();
				timelineSlider.setSelection(replayNavigator.getCurrentEventNumber());
				updateCurrentPositionInfo();
			}
		});
		return button;
	}

	// Calculate the jump distance for the next highlighted position or the end of
	// the timeline.
	private int getForwardJump() {
		if (highlightPositions.isEmpty()) {
			return timelineSlider.getMaximum() - timelineSlider.getThumb();
		}
		final int current = timelineSlider.getSelection();
		int left = 0;
		int right = highlightPositions.size() - 1;
		int result = -1;
		while (left <= right) {
			final int mid = left + (right - left) / 2;
			final int value = highlightPositions.get(mid).intValue();
			if (value > current) {
				result = value;
				right = mid - 1;
			} else {
				left = mid + 1;
			}
		}
		if (result != -1) {
			return result;
		}
		return timelineSlider.getMaximum() - timelineSlider.getThumb();
	}

	// Calculate the jump distance for the previous highlighted position or the
	// beginning of the timeline.
	private int getBackwardsJump() {
		if (highlightPositions.isEmpty()) {
			return 0;
		}
		final int current = timelineSlider.getSelection();
		int left = 0;
		int right = highlightPositions.size() - 1;
		int result = -1;
		while (left <= right) {
			final int mid = left + (right - left) / 2;
			final int value = highlightPositions.get(mid).intValue();
			if (value < current) {
				result = value;
				left = mid + 1;
			} else {
				right = mid - 1;
			}
		}
		if (result != -1) {
			return result;
		}
		return 0;
	}

	// Update the current position information and the button state accordingly
	private void updateCurrentPositionInfo() {
		final int current = timelineSlider.getSelection();
		final int total = timelineSlider.getMaximum() - timelineSlider.getThumb();
		positionLabel.setText(current + " / " + total); //$NON-NLS-1$
		lineCanvas.redraw();
		if (current == total) {
			nextButton.setEnabled(false);
			jumpForwardButton.setEnabled(false);
			jumpBackButton.setEnabled(true);
			previousButton.setEnabled(true);
		} else if (current == 0) {
			nextButton.setEnabled(true);
			jumpForwardButton.setEnabled(true);
			jumpBackButton.setEnabled(false);
			previousButton.setEnabled(false);
		} else {
			jumpBackButton.setEnabled(true);
			previousButton.setEnabled(true);
			nextButton.setEnabled(true);
			jumpForwardButton.setEnabled(true);
		}
	}
}
