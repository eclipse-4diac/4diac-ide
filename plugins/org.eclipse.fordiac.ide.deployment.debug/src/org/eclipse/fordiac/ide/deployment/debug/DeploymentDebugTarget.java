/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.deployment.debug;

import java.text.MessageFormat;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDisconnect;
import org.eclipse.debug.core.model.IMemoryBlock;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.fordiac.ide.debug.EvaluatorDebugVariable;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;

public class DeploymentDebugTarget extends DeploymentDebugElement implements IDeploymentDebugTarget {

	private final ILaunch launch;
	private final AutomationSystem system;
	private final boolean allowTerminate;

	private final DeploymentProcess process;
	private final DeploymentDebugThread thread;
	private final AtomicLong variableUpdateCount = new AtomicLong();

	private boolean terminated;
	private boolean disconnected;

	public DeploymentDebugTarget(final AutomationSystem system, final Set<INamedElement> selection,
			final ILaunch launch, final boolean allowTerminate) throws DeploymentException {
		super(null);
		this.launch = launch;
		this.system = system;
		this.allowTerminate = allowTerminate;
		process = new DeploymentProcess(system, selection, launch);
		process.getJob().addJobChangeListener(IJobChangeListener.onDone(this::deploymentDone));
		thread = new DeploymentDebugThread(this);
		launch.addDebugTarget(this);
		fireCreationEvent();
	}

	private void deploymentDone(final IJobChangeEvent event) {
		if (event.getResult().isOK()) {
			Job.create(MessageFormat.format(Messages.DeploymentDebugTarget_ConnectJobName, getName()), this::doConnect)
					.schedule();
		} else {
			terminated();
		}
	}

	protected void doConnect(final IProgressMonitor monitor) {
		// TODO connect
		thread.fireSuspendEvent(DebugEvent.CLIENT_REQUEST);
	}

	public void start() {
		process.start();
	}

	@Override
	public long getVariableUpdateCount() {
		return variableUpdateCount.get();
	}

	public long incrementVariableUpdateCount() {
		return variableUpdateCount.incrementAndGet();
	}

	@Override
	public EvaluatorDebugVariable createVariable(final Variable<?> variable, final String expression) {
		return new DeploymentDebugVariable(variable, expression, this);
	}

	@Override
	public boolean canResume() {
		return false;
	}

	@Override
	public boolean canSuspend() {
		return false;
	}

	@Override
	public boolean isSuspended() {
		return false;
	}

	@Override
	public void resume() throws DebugException {
		throw createUnsupportedOperationException();
	}

	@Override
	public void suspend() throws DebugException {
		throw createUnsupportedOperationException();
	}

	@Override
	public boolean supportsBreakpoint(final IBreakpoint breakpoint) {
		return false; // none yet
	}

	@Override
	public void breakpointAdded(final IBreakpoint breakpoint) {
		// do nothing
	}

	@Override
	public void breakpointRemoved(final IBreakpoint breakpoint, final IMarkerDelta delta) {
		// do nothing
	}

	@Override
	public void breakpointChanged(final IBreakpoint breakpoint, final IMarkerDelta delta) {
		// do nothing
	}

	@Override
	public String getName() {
		return system.getName();
	}

	@Override
	public IDeploymentDebugTarget getDebugTarget() {
		return this;
	}

	@Override
	public ILaunch getLaunch() {
		return launch;
	}

	@Override
	public AutomationSystem getSystem() {
		return system;
	}

	public boolean isAllowTerminate() {
		return allowTerminate;
	}

	@Override
	public boolean canTerminate() {
		return !isTerminated() && !isDisconnected() && allowTerminate;
	}

	@Override
	public boolean isTerminated() {
		return terminated;
	}

	@Override
	public void terminate() throws DebugException {
		terminated();
		launch.terminate();
	}

	protected void terminated() {
		terminated = true;
		fireTerminateEvent();
	}

	@Override
	public boolean canDisconnect() {
		return !isTerminated() && !isDisconnected();
	}

	@Override
	public void disconnect() throws DebugException {
		disconnected();
		if (launch instanceof final IDisconnect disconnect) {
			disconnect.disconnect();
		}
	}

	protected void disconnected() {
		disconnected = true;
		fireTerminateEvent();
	}

	@Override
	public boolean isDisconnected() {
		return disconnected;
	}

	@Override
	public boolean supportsStorageRetrieval() {
		return false;
	}

	@Override
	public IMemoryBlock getMemoryBlock(final long startAddress, final long length) throws DebugException {
		throw createUnsupportedOperationException();
	}

	@Override
	public DeploymentProcess getProcess() {
		return process;
	}

	@Override
	public IThread[] getThreads() {
		return hasThreads() ? new IThread[] { thread } : new IThread[0];
	}

	@Override
	public boolean hasThreads() {
		return process.isTerminated() && !isTerminated() && !isDisconnected();
	}
}
