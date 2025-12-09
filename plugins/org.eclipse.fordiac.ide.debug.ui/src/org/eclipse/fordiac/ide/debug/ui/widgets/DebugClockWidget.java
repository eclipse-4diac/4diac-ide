/*******************************************************************************
 * Copyright (c) 2025 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug.ui.widgets;

import static org.eclipse.swt.events.SelectionListener.widgetSelectedAdapter;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.fordiac.ide.debug.fb.FBDebugClockMode;
import org.eclipse.fordiac.ide.debug.ui.Messages;
import org.eclipse.fordiac.ide.model.value.DateAndTimeValueConverter;
import org.eclipse.fordiac.ide.model.value.TimeValueConverter;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class DebugClockWidget {

	private Group group;
	private Button systemClockRadio;
	private Button intervalClockRadio;
	private Button fixedClockRadio;
	private Text clockIntervalText;
	private Text realtimeClockText;
	private Text monotonicClockText;

	private Runnable updateRunnable;

	public DebugClockWidget() {
	}

	public DebugClockWidget(final Runnable updateRunnable) {
		this.updateRunnable = updateRunnable;
	}

	public Composite createControl(final Composite parent) {
		group = new Group(parent, SWT.BORDER);
		GridLayoutFactory.swtDefaults().numColumns(2).applyTo(group);
		group.setText(Messages.DebugClockWidget_Title);

		final Composite clockModes = new Composite(group, SWT.NONE);
		GridLayoutFactory.swtDefaults().numColumns(2).applyTo(clockModes);
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1).applyTo(clockModes);

		systemClockRadio = new Button(clockModes, SWT.RADIO);
		systemClockRadio.setText(Messages.DebugClockWidget_SystemClock);
		systemClockRadio.addSelectionListener(widgetSelectedAdapter(e -> handleClockModeUpdated()));
		GridDataFactory.fillDefaults().span(2, 1).applyTo(systemClockRadio);

		intervalClockRadio = new Button(clockModes, SWT.RADIO);
		intervalClockRadio.setText(Messages.DebugClockWidget_IntervalClock);
		intervalClockRadio.addSelectionListener(widgetSelectedAdapter(e -> handleClockModeUpdated()));
		GridDataFactory.swtDefaults().applyTo(intervalClockRadio);

		final Composite clockIntervalComposite = createClockIntervalComposite(clockModes);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(clockIntervalComposite);

		fixedClockRadio = new Button(clockModes, SWT.RADIO);
		fixedClockRadio.setText(Messages.DebugClockWidget_FixedClock);
		fixedClockRadio.addSelectionListener(widgetSelectedAdapter(e -> handleClockModeUpdated()));
		GridDataFactory.fillDefaults().span(2, 1).applyTo(fixedClockRadio);

		final Label realtimeClockLabel = new Label(group, SWT.NONE);
		realtimeClockLabel.setText(Messages.DebugClockWidget_RealtimeClock);
		GridDataFactory.swtDefaults().applyTo(realtimeClockLabel);

		final Composite realtimeClockTextComposite = createRealtimeClockTextComposite(group);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(realtimeClockTextComposite);

		final Label monotonicClockLabel = new Label(group, SWT.NONE);
		monotonicClockLabel.setText(Messages.DebugClockWidget_MonotonicClock);
		GridDataFactory.swtDefaults().applyTo(monotonicClockLabel);

		final Composite monotonicClockTextComposite = createMonotonicClockTextComposite(group);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(monotonicClockTextComposite);

		return group;
	}

	public Composite createClockIntervalComposite(final Composite parent) {
		final Composite clockIntervalComposite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.swtDefaults().numColumns(2).applyTo(clockIntervalComposite);

		final Label clockIntervalTextLabel = new Label(clockIntervalComposite, SWT.NONE);
		clockIntervalTextLabel.setText(Messages.DebugClockWidget_ClockIntervalTextLabel);
		GridDataFactory.swtDefaults().applyTo(clockIntervalTextLabel);

		clockIntervalText = new Text(clockIntervalComposite, SWT.BORDER);
		clockIntervalText.setEditable(false);
		clockIntervalText.addModifyListener(e -> handleClockUpdated());
		GridDataFactory.fillDefaults().grab(true, false).applyTo(clockIntervalText);
		return clockIntervalComposite;
	}

	public Composite createRealtimeClockTextComposite(final Composite parent) {
		final Composite realtimeClockComposite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.swtDefaults().numColumns(2).applyTo(realtimeClockComposite);

		final Label realtimeClockTextLabel = new Label(realtimeClockComposite, SWT.NONE);
		realtimeClockTextLabel.setText(Messages.DebugClockWidget_RealtimeClockTextLabel);
		GridDataFactory.swtDefaults().applyTo(realtimeClockTextLabel);

		realtimeClockText = new Text(realtimeClockComposite, SWT.BORDER);
		realtimeClockText.setEditable(false);
		realtimeClockText.addModifyListener(e -> handleClockUpdated());
		GridDataFactory.fillDefaults().grab(true, false).applyTo(realtimeClockText);
		return realtimeClockComposite;
	}

	public Composite createMonotonicClockTextComposite(final Composite parent) {
		final Composite monotonicClockComposite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.swtDefaults().numColumns(2).applyTo(monotonicClockComposite);

		final Label monotonicClockTextLabel = new Label(monotonicClockComposite, SWT.NONE);
		monotonicClockTextLabel.setText(Messages.DebugClockWidget_MonotonicClockTextLabel);
		GridDataFactory.swtDefaults().applyTo(monotonicClockTextLabel);

		monotonicClockText = new Text(monotonicClockComposite, SWT.BORDER);
		monotonicClockText.setEditable(false);
		monotonicClockText.addModifyListener(e -> handleClockUpdated());
		GridDataFactory.fillDefaults().grab(true, false).applyTo(monotonicClockText);
		return monotonicClockComposite;
	}

	public FBDebugClockMode getSelectedClockMode() {
		if (systemClockRadio.getSelection()) {
			return FBDebugClockMode.SYSTEM;
		}
		if (fixedClockRadio.getSelection()) {
			return FBDebugClockMode.FIXED;
		}
		if (intervalClockRadio.getSelection()) {
			return FBDebugClockMode.INTERVAL;
		}
		return FBDebugClockMode.SYSTEM;
	}

	public void setSelectedClockMode(final FBDebugClockMode clockMode) {
		final Button selected = switch (clockMode) {
		case SYSTEM -> systemClockRadio;
		case INTERVAL -> intervalClockRadio;
		case FIXED -> fixedClockRadio;
		case null, default -> null;
		};
		Stream.of(systemClockRadio, intervalClockRadio, fixedClockRadio)
				.forEach(radio -> radio.setSelection(radio == selected));
		clockIntervalText.setEditable(selected == intervalClockRadio);
		realtimeClockText.setEditable(selected == intervalClockRadio || selected == fixedClockRadio);
		monotonicClockText.setEditable(selected == intervalClockRadio || selected == fixedClockRadio);
	}

	protected void handleClockModeUpdated() {
		clockIntervalText.setEditable(intervalClockRadio.getSelection());
		realtimeClockText.setEditable(intervalClockRadio.getSelection() || fixedClockRadio.getSelection());
		monotonicClockText.setEditable(intervalClockRadio.getSelection() || fixedClockRadio.getSelection());
		handleClockUpdated();
	}

	protected void handleClockUpdated() {
		if (updateRunnable != null) {
			updateRunnable.run();
		}
	}

	public Optional<String> validate() {
		final FBDebugClockMode clockMode = getSelectedClockMode();
		if (clockMode == FBDebugClockMode.INTERVAL) {
			try {
				TimeValueConverter.INSTANCE.toValue(getClockIntervalText());
			} catch (final IllegalArgumentException e) {
				return Optional
						.of(MessageFormat.format(Messages.DebugClockWidget_InvalidInterval, e.getLocalizedMessage()));
			}
		}
		if (clockMode == FBDebugClockMode.INTERVAL || clockMode == FBDebugClockMode.FIXED) {
			try {
				DateAndTimeValueConverter.INSTANCE.toValue(getRealtimeClockText());
			} catch (final IllegalArgumentException e) {
				return Optional.of(MessageFormat.format(Messages.DebugClockWidget_InvalidRealtimeClockValue,
						e.getLocalizedMessage()));
			}
			try {
				TimeValueConverter.INSTANCE.toValue(getMonotonicClockText());
			} catch (final IllegalArgumentException e) {
				return Optional.of(MessageFormat.format(Messages.DebugClockWidget_InvalidMonotonicClockValue,
						e.getLocalizedMessage()));
			}
		}
		return Optional.empty();
	}

	public Group getControl() {
		return group;
	}

	public void setControl(final Group control) {
		this.group = control;
	}

	public Duration getClockInterval() {
		try {
			return TimeValueConverter.INSTANCE.toValue(getClockIntervalText());
		} catch (final IllegalArgumentException e) {
			return Duration.ZERO;
		}
	}

	public void setClockInterval(final Duration value) {
		setClockIntervalText(TimeValueConverter.INSTANCE.toString(value));
	}

	public String getClockIntervalText() {
		return clockIntervalText.getText();
	}

	public void setClockIntervalText(final String value) {
		clockIntervalText.setText(value);
	}

	public Instant getRealtimeClockValue() {
		try {
			return DateAndTimeValueConverter.INSTANCE.toValue(getRealtimeClockText()).toInstant(ZoneOffset.UTC);
		} catch (final Exception e) {
			return Instant.EPOCH;
		}
	}

	public void setRealtimeClockValue(final Instant value) {
		setRealtimeClockText(
				DateAndTimeValueConverter.INSTANCE.toString(LocalDateTime.ofInstant(value, ZoneOffset.UTC)));
	}

	public String getRealtimeClockText() {
		return realtimeClockText.getText();
	}

	public void setRealtimeClockText(final String value) {
		realtimeClockText.setText(value);
	}

	public Instant getMonotonicClockValue() {
		try {
			final Duration value = TimeValueConverter.INSTANCE.toValue(getMonotonicClockText());
			return Instant.ofEpochSecond(value.getSeconds(), value.getNano());
		} catch (final Exception e) {
			return Instant.EPOCH;
		}
	}

	public void setMonotonicClockValue(final Instant value) {
		setMonotonicClockText(
				TimeValueConverter.INSTANCE.toString(Duration.ofSeconds(value.getEpochSecond(), value.getNano())));
	}

	public String getMonotonicClockText() {
		return monotonicClockText.getText();
	}

	public void setMonotonicClockText(final String value) {
		monotonicClockText.setText(value);
	}

	public Runnable getUpdateRunnable() {
		return updateRunnable;
	}

	public void setUpdateRunnable(final Runnable updateRunnable) {
		this.updateRunnable = updateRunnable;
	}
}
