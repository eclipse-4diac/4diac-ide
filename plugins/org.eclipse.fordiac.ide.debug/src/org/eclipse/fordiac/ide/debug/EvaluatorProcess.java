/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.debug;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IStreamsProxy;
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.EvaluatorExitException;
import org.eclipse.fordiac.ide.model.eval.EvaluatorThreadGroup;

public class EvaluatorProcess extends PlatformObject implements IProcess, Callable<IStatus> {

	private final String name;
	private final Evaluator evaluator;
	private final ILaunch launch;
	private FutureTask<IStatus> task;
	private final Thread thread;
	private final EvaluatorThreadGroup threadGroup;
	private final EvaluatorStreamsProxy streamsProxy;
	private Map<String, String> attributes = new HashMap<>();

	public EvaluatorProcess(String name, Evaluator evaluator, ILaunch launch) {
		this.name = evaluator.getClass().getSimpleName();
		this.evaluator = evaluator;
		this.launch = launch;
		this.task = new FutureTask<>(this);
		this.threadGroup = new EvaluatorThreadGroup(name);
		this.thread = new Thread(threadGroup, this.task, name);
		this.streamsProxy = new EvaluatorStreamsProxy(this.threadGroup);
		launch.addProcess(this);
		this.fireCreationEvent();
	}

	@Override
	public IStatus call() throws Exception {
		try {
			long start = System.nanoTime();
			try {
				this.evaluator.evaluate();
			} catch (EvaluatorExitException e) {
				// exit
			}
			long finish = System.nanoTime();
			Duration elapsed = Duration.ofNanos(finish - start);
			this.streamsProxy.getOutputStreamMonitor().info(String.format("Elapsed: %d:%02d:%02d:%03d",
					elapsed.toHours(), elapsed.toMinutesPart(), elapsed.toSecondsPart(), elapsed.toMillisPart()));
			return Status.OK_STATUS;
		} catch (InterruptedException e) {
			this.streamsProxy.getErrorStreamMonitor().error("Terminated");
			return Status.error("Terminated");
		} catch (Throwable t) {
			this.streamsProxy.getErrorStreamMonitor().error("Exception occurred", t);
			return Status.error("Exception occurred", t);
		} finally {
			fireTerminateEvent();
		}
	}

	public void start() {
		this.thread.start();
	}

	public Evaluator getEvaluator() {
		return this.evaluator;
	}

	public EvaluatorThreadGroup getThreadGroup() {
		return this.threadGroup;
	}

	@Override
	public boolean canTerminate() {
		return !this.isTerminated();
	}

	@Override
	public boolean isTerminated() {
		return this.threadGroup.isDestroyed();
	}

	@Override
	public void terminate() throws DebugException {
		this.threadGroup.interrupt();
	}

	@Override
	public int getExitValue() throws DebugException {
		try {
			return this.task.get(-1, TimeUnit.NANOSECONDS).getCode();
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			throw new DebugException(Status.error("Couldn't get exit code", e));
		} catch (CancellationException e) {
			return -1;
		}
	}

	@Override
	public String getLabel() {
		return this.name;
	}

	@Override
	public ILaunch getLaunch() {
		return this.launch;
	}

	@Override
	public IStreamsProxy getStreamsProxy() {
		return this.streamsProxy;
	}

	protected void fireCreationEvent() {
		fireEvent(new DebugEvent(this, DebugEvent.CREATE));
	}

	protected void fireTerminateEvent() {
		fireEvent(new DebugEvent(this, DebugEvent.TERMINATE));
	}

	protected void fireChangeEvent() {
		fireEvent(new DebugEvent(this, DebugEvent.CHANGE));
	}

	protected void fireEvent(DebugEvent event) {
		DebugPlugin manager = DebugPlugin.getDefault();
		if (manager != null) {
			manager.fireDebugEventSet(new DebugEvent[] { event });
		}
	}

	@Override
	public void setAttribute(String key, String value) {
		this.attributes.put(key, value);
	}

	@Override
	public String getAttribute(String key) {
		return this.attributes.get(key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(Class<T> adapter) {
		if (adapter.equals(IProcess.class)) {
			return (T) this;
		} else if (adapter.equals(IDebugTarget.class)) {
			ILaunch launch = getLaunch();
			IDebugTarget[] targets = launch.getDebugTargets();
			for (IDebugTarget target : targets) {
				if (this.equals(target.getProcess())) {
					return (T) target;
				}
			}
			return null;
		} else if (adapter.equals(ILaunch.class)) {
			return (T) getLaunch();
		} else if (adapter.equals(ILaunchConfiguration.class)) {
			return (T) getLaunch().getLaunchConfiguration();
		}
		return super.getAdapter(adapter);
	}
}
