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
package org.eclipse.fordiac.ide.debug.ui.view;

import java.time.Clock;
import java.time.Duration;
import java.util.Collection;
import java.util.Optional;

import org.eclipse.fordiac.ide.debug.EvaluatorProcess;
import org.eclipse.fordiac.ide.debug.fb.FBDebugClockMode;
import org.eclipse.fordiac.ide.debug.fb.FBLaunchConfigurationDelegate;
import org.eclipse.fordiac.ide.debug.ui.Messages;
import org.eclipse.fordiac.ide.debug.ui.widgets.DebugClockWidget;
import org.eclipse.fordiac.ide.model.eval.AbstractEvaluator;
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.EvaluatorMonitor;
import org.eclipse.fordiac.ide.model.eval.EvaluatorMonitor.NullEvaluatorMonitor;
import org.eclipse.fordiac.ide.model.eval.EvaluatorThreadPoolExecutor;
import org.eclipse.fordiac.ide.model.eval.fb.FBEvaluator;
import org.eclipse.fordiac.ide.model.eval.fb.FBEvaluatorCountingEventQueue;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class FBDebugViewClockWidget extends DebugClockWidget {

	private EvaluatorProcess process;
	private boolean dirty;
	private boolean refreshing;

	private Button applyButton;

	private final EvaluatorMonitor evaluatorMonitor = new NullEvaluatorMonitor() {

		@Override
		public void update(final Collection<? extends Variable<?>> variables, final Evaluator evaluator) {
			if (process != null && evaluator == process.getEvaluator() && !refreshing) {
				refreshing = true;
				Display.getDefault().asyncExec(FBDebugViewClockWidget.this::refresh);
			}
		}

		@Override
		public void terminated(final EvaluatorThreadPoolExecutor executor) {
			Display.getDefault().asyncExec(FBDebugViewClockWidget.this::refresh);
		}
	};

	@Override
	public Composite createControl(final Composite parent) {
		final Composite composite = super.createControl(parent);
		applyButton = new Button(composite, SWT.PUSH);
		applyButton.setText(Messages.FBDebugViewClockWidget_Apply);
		applyButton.setEnabled(false);
		applyButton.addSelectionListener(SelectionListener.widgetSelectedAdapter(e -> performApply()));
		GridDataFactory.swtDefaults().applyTo(applyButton);
		return composite;
	}

	private void performApply() {
		if (process == null || process.isTerminated()) {
			return;
		}
		final Optional<String> error = validate();
		if (error.isPresent()) {
			MessageDialog.openError(getControl().getShell(), Messages.FBDebugViewClockWidget_InvalidValues,
					error.get());
			return;
		}
		final FBDebugClockMode clockMode = getSelectedClockMode();
		final Duration clockInterval = getClockInterval();
		final FBEvaluatorCountingEventQueue eventQueue = getEventQueue();

		process.getExecutor().setRealtimeClock(FBLaunchConfigurationDelegate.createClock(clockMode,
				getRealtimeClockValue(), clockInterval, eventQueue));
		process.getExecutor().setMonotonicClock(FBLaunchConfigurationDelegate.createClock(clockMode,
				getMonotonicClockValue(), clockInterval, eventQueue));

		setDirty(false);
	}

	public void refresh() {
		refresh(false);
	}

	public void refresh(final boolean force) {
		if (process != null && (force || !isDirty())) {
			final Clock realtimeClock = process.getExecutor().getRealtimeClock();
			final Clock monotonicClock = process.getExecutor().getMonotonicClock();
			setRealtimeClockValue(realtimeClock.instant());
			setMonotonicClockValue(monotonicClock.instant());
			switch (monotonicClock) {
			case final AbstractEvaluator.MonotonicClock unused -> setSelectedClockMode(FBDebugClockMode.SYSTEM);
			case final AbstractEvaluator.IntervalClock intervalClock -> {
				setClockInterval(intervalClock.getInterval());
				setSelectedClockMode(FBDebugClockMode.INTERVAL);
			}
			default -> setSelectedClockMode(FBDebugClockMode.FIXED);
			}
			setDirty(false); // ensure not dirty
		}
		refreshing = false;
	}

	protected FBEvaluatorCountingEventQueue getEventQueue() {
		return process != null && process.getEvaluator() instanceof final FBEvaluator<?> evaluator
				&& evaluator.getEventQueue() instanceof final FBEvaluatorCountingEventQueue queue ? queue : null;
	}

	@Override
	protected void handleClockUpdated() {
		setDirty(true);
		super.handleClockUpdated();
	}

	public EvaluatorProcess getProcess() {
		return process;
	}

	public void setProcess(final EvaluatorProcess process) {
		if (this.process != null) {
			this.process.getExecutor().removeMonitor(evaluatorMonitor);
		}
		this.process = process;
		if (this.process != null) {
			this.process.getExecutor().addMonitor(evaluatorMonitor);
		}
	}

	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(final boolean dirty) {
		this.dirty = dirty;
		applyButton.setEnabled(dirty);
	}
}
