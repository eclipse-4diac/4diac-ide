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

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDisconnect;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;

public class DeploymentDebugThread extends DeploymentDebugElement implements IThread, IDisconnect {

	private final DeploymentDebugStackFrame stackFrame;

	public DeploymentDebugThread(final DeploymentDebugTarget target) {
		super(target);
		stackFrame = new DeploymentDebugStackFrame(this);
		fireCreationEvent();
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
		return getDebugTarget().getProcess().isTerminated();
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
	public boolean canStepInto() {
		return false;
	}

	@Override
	public boolean canStepOver() {
		return false;
	}

	@Override
	public boolean canStepReturn() {
		return false;
	}

	@Override
	public boolean isStepping() {
		return false;
	}

	@Override
	public void stepInto() throws DebugException {
		throw createUnsupportedOperationException();
	}

	@Override
	public void stepOver() throws DebugException {
		throw createUnsupportedOperationException();
	}

	@Override
	public void stepReturn() throws DebugException {
		throw createUnsupportedOperationException();
	}

	@Override
	public boolean canTerminate() {
		return getDebugTarget().canTerminate();
	}

	@Override
	public boolean isTerminated() {
		return getDebugTarget().isTerminated();
	}

	@Override
	public void terminate() throws DebugException {
		getDebugTarget().terminate();
	}

	@Override
	public boolean canDisconnect() {
		return getDebugTarget().canDisconnect();
	}

	@Override
	public void disconnect() throws DebugException {
		getDebugTarget().disconnect();
	}

	@Override
	public boolean isDisconnected() {
		return getDebugTarget().isDisconnected();
	}

	@Override
	public IStackFrame[] getStackFrames() {
		return new IStackFrame[] { stackFrame };
	}

	@Override
	public boolean hasStackFrames() {
		return true;
	}

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public DeploymentDebugStackFrame getTopStackFrame() {
		return stackFrame;
	}

	@Override
	public String getName() {
		return Messages.DeploymentDebugThread_Name;
	}

	@Override
	public IBreakpoint[] getBreakpoints() {
		return new IBreakpoint[0];
	}

	@Override
	public DeploymentDebugTarget getDebugTarget() {
		return (DeploymentDebugTarget) super.getDebugTarget();
	}
}
