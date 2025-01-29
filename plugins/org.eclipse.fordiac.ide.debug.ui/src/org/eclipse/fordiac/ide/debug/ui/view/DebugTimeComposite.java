/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Lukas Leimeister - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug.ui.view;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import org.eclipse.fordiac.ide.debug.EvaluatorProcess;
import org.eclipse.fordiac.ide.debug.fb.FBDebugClockMode;
import org.eclipse.fordiac.ide.debug.fb.FBLaunchEventQueue;
import org.eclipse.fordiac.ide.model.eval.AbstractEvaluator;
import org.eclipse.fordiac.ide.model.eval.fb.FBEvaluator;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class DebugTimeComposite {

	private static final int NUM_COLUMNS = 1;
	private static final int NUM_BUTTONS = 3;
	private EvaluatorProcess evaluator;
	private FBLaunchEventQueue eventQueue;
	private final Group debugTimeGroup;
	private Button systemTimeRadio;
	private Button incrementTimeRadio;
	private Button manualTimeRadio;
	private Text debugTimeText;

	/**
	 * Create an new edit part to control the debug time settings. This interface
	 * contains three radio buttons for mode selection and one text/ lable to adjust
	 * the time value. It is part of the debug view and only visible, when context
	 * is activated.
	 *
	 * @param parent The parent Composite of the debug view.
	 */
	public DebugTimeComposite(final Composite parent) {
		debugTimeGroup = createDebugTimeGroup(parent);
		GridLayoutFactory.fillDefaults().numColumns(NUM_COLUMNS).margins(0, 0).generateLayout(debugTimeGroup);
		GridDataFactory.fillDefaults().align(SWT.CENTER, SWT.CENTER).grab(true, false).applyTo(debugTimeGroup);
		setEditPartVisible(false);
	}

	protected Group createDebugTimeGroup(final Composite parent) {
		// this method creates the Test Recorder Interface Group
		final Group group = new Group(parent, SWT.NONE);
		group.setText("Set Debug Time"); //$NON-NLS-1$

		// create sleeptime selection
		final Composite debugTimeSelectionComposite = createDebugTimeModeSelectionComposite(group);
		GridLayoutFactory.fillDefaults().numColumns(NUM_BUTTONS).margins(0, 0)
				.generateLayout(debugTimeSelectionComposite);
		GridDataFactory.fillDefaults().align(SWT.CENTER, SWT.CENTER).grab(true, false)
				.applyTo(debugTimeSelectionComposite);

		// create sleeptime settings
		final Composite debugTimeValueComposite = createDebugTimeValueComposite(group);
		GridLayoutFactory.fillDefaults().numColumns(NUM_BUTTONS).margins(0, 0).generateLayout(debugTimeValueComposite);
		GridDataFactory.fillDefaults().align(SWT.CENTER, SWT.CENTER).grab(true, false).applyTo(debugTimeValueComposite);

		// set visability of the group
		setEditPartVisible(false);
		return group;
	}

	protected Composite createDebugTimeValueComposite(final Group group) {
		// create composite for sleeptime section
		final Composite composite = new Composite(group, SWT.NONE);

		// create elements fo composite
		final Button setButton = new Button(composite, SWT.PUSH);
		setButton.setText("Set"); //$NON-NLS-1$
		setButton.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, true, 1, 1));
		debugTimeText = new Text(composite, SWT.BORDER);
		final Label debugTimeLable = new Label(composite, SWT.NONE);

		// customize elements of composite
		debugTimeText.setText(""); //$NON-NLS-1$
		debugTimeText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		debugTimeLable.setText("ms"); //$NON-NLS-1$
		debugTimeLable.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));

		// customize elements of composite
		setButton.addSelectionListener(
				SelectionListener.widgetSelectedAdapter(e -> setDebugTime(debugTimeText.getText())));
		return composite;
	}

	protected Composite createDebugTimeModeSelectionComposite(final Group group) {
		// create composite for sleeptime section
		final Composite composite = new Composite(group, SWT.NONE);

		systemTimeRadio = new Button(composite, SWT.RADIO);
		systemTimeRadio.setText("System"); //$NON-NLS-1$
		incrementTimeRadio = new Button(composite, SWT.RADIO);
		incrementTimeRadio.setText("Increment"); //$NON-NLS-1$
		manualTimeRadio = new Button(composite, SWT.RADIO);
		manualTimeRadio.setText("Manual"); //$NON-NLS-1$
		return composite;
	}

	protected void setCheckbox(final String text) {
		// only the most recent selected box is checked
		switch (text) {
		case "System": //$NON-NLS-1$
			systemTimeRadio.setSelection(true);
			incrementTimeRadio.setSelection(false);
			manualTimeRadio.setSelection(false);
			break;
		case "Increment": //$NON-NLS-1$
			systemTimeRadio.setSelection(false);
			incrementTimeRadio.setSelection(true);
			manualTimeRadio.setSelection(false);
			break;
		case "Manual": //$NON-NLS-1$
			systemTimeRadio.setSelection(false);
			incrementTimeRadio.setSelection(false);
			manualTimeRadio.setSelection(true);
			break;
		default:
			break;
		}
	}

	private void setDebugTime(final String text) {
		// set button is pushed combined with one of the three selections
		if (manualTimeRadio.getSelection() && (text != null)) {
			manualSelection(text);
		}
		if (incrementTimeRadio.getSelection() && (text != null)) {
			incrementSelection(text);
		}
		if (systemTimeRadio.getSelection()) {
			systemSelection();
		}
	}

	private void systemSelection() {
		// time is set to default system time
		evaluator.getExecutor().setMonotonicClock(AbstractEvaluator.MonotonicClock.UTC);
		if (eventQueue != null) {
			eventQueue.setEvaluatorProcess(evaluator);
			eventQueue.setDebugTimeValue(FBDebugClockMode.SYSTEM, Duration.ZERO);
		}
	}

	private void incrementSelection(final String text) {
		// time is incrementally increased every time an event is triggered
		try {
			final long value = Long.parseLong(text);
			final Duration sleepTime = Duration.of(value, ChronoUnit.MILLIS);
			if (eventQueue != null) {
				eventQueue.setEvaluatorProcess(evaluator);
				eventQueue.setDebugTimeValue(FBDebugClockMode.INCREMENT, sleepTime);
			}
		} catch (final NumberFormatException | java.lang.ArithmeticException e) {
			throw new IllegalStateException("Debug Time Value is not accepted!"); //$NON-NLS-1$
		}
	}

	private void manualSelection(final String text) {
		// time is fixed and set to user input
		try {
			final long value = Long.parseLong(text);
			final Duration manualTime = Duration.of(value, ChronoUnit.MILLIS);
			final Instant instant = Instant.ofEpochSecond(manualTime.getSeconds(), manualTime.getNano());
			if (eventQueue != null) {
				eventQueue.setEvaluatorProcess(evaluator);
				eventQueue.setDebugTimeValue(FBDebugClockMode.MANUAL, manualTime);
			}
			evaluator.getExecutor().setMonotonicClock(Clock.fixed(instant, ZoneId.systemDefault()));
		} catch (final NumberFormatException | java.lang.ArithmeticException e) {
			throw new IllegalStateException("Debug Time Value is not accepted!"); //$NON-NLS-1$
		}
	}

	/**
	 * Set the Edit Part visible or invisible.
	 *
	 * @param visiblestate The boolean state of the visibility
	 */
	public void setEditPartVisible(final boolean visiblestate) {
		// set the visibility status of the group
		if (debugTimeGroup != null) {
			debugTimeGroup.setVisible(visiblestate);
		}
	}

	/**
	 * Update the contents of the Edit Part.
	 *
	 * @param visiblestate The boolean state of the visibility
	 */
	public void updateEditPartVisible() {
		// update elements of edit part
		final FBLaunchEventQueue newEventQueue = getEventQueue(evaluator);
		if (eventQueue != newEventQueue) {
			eventQueue = newEventQueue;
		}
		if (eventQueue != null) {
			systemTimeRadio.setSelection(eventQueue.isDebugTimeSystem());
			incrementTimeRadio.setSelection(eventQueue.isDebugTimeIncremental());
			manualTimeRadio.setSelection(eventQueue.isDebugTimeManual());
			debugTimeText.setText(String.valueOf(eventQueue.getDebugTimeValue().toMillis()));
			eventQueue.setEvaluatorProcess(evaluator);
		}
	}

	/**
	 * Set the content of the DebugTimeEditPart.
	 *
	 * @param evaluatorprocess Evaluator Process which is currently active in View
	 */
	public void setContent(final EvaluatorProcess evaluatorprocess) {
		evaluator = evaluatorprocess;
		final FBLaunchEventQueue newEventQueue = getEventQueue(evaluator);
		if (eventQueue != newEventQueue) {
			eventQueue = newEventQueue;
		}
	}

	private static FBLaunchEventQueue getEventQueue(final EvaluatorProcess evaluator) {
		if (evaluator != null) {
			final var queue = ((FBEvaluator<?>) evaluator.getEvaluator()).getEventQueue();
			if (queue instanceof final FBLaunchEventQueue fBLaunchEventQueue) {
				return fBLaunchEventQueue;
			}
		}
		return null;
	}
}