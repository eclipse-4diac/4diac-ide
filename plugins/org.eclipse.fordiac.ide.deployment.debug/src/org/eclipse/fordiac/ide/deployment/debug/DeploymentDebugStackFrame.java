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
import org.eclipse.debug.core.model.IDisconnect;
import org.eclipse.debug.core.model.IRegisterGroup;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IVariable;

public class DeploymentDebugStackFrame extends DeploymentDebugElement implements IStackFrame, IDisconnect {

	private final DeploymentDebugThread thread;

	public DeploymentDebugStackFrame(final DeploymentDebugThread thread) {
		super(thread.getDebugTarget());
		this.thread = thread;
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
	public boolean canResume() {
		return false;
	}

	@Override
	public boolean canSuspend() {
		return false;
	}

	@Override
	public boolean isSuspended() {
		return thread.isSuspended();
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
	public boolean canTerminate() {
		return thread.canTerminate();
	}

	@Override
	public boolean isTerminated() {
		return thread.isTerminated();
	}

	@Override
	public void terminate() throws DebugException {
		thread.terminate();
	}

	@Override
	public boolean canDisconnect() {
		return thread.canDisconnect();
	}

	@Override
	public void disconnect() throws DebugException {
		thread.disconnect();
	}

	@Override
	public boolean isDisconnected() {
		return thread.isDisconnected();
	}

	@Override
	public DeploymentDebugThread getThread() {
		return thread;
	}

	@Override
	public IVariable[] getVariables() {
		return getDebugTarget().getWatches().values().toArray(IVariable[]::new);
	}

	@Override
	public boolean hasVariables() {
		return !getDebugTarget().getWatches().isEmpty();
	}

	@Override
	public int getLineNumber() {
		return -1;
	}

	@Override
	public int getCharStart() {
		return -1;
	}

	@Override
	public int getCharEnd() {
		return -1;
	}

	@Override
	public String getName() {
		return Messages.DeploymentDebugStackFrame_Name;
	}

	@Override
	public IRegisterGroup[] getRegisterGroups() {
		return new IRegisterGroup[0];
	}

	@Override
	public boolean hasRegisterGroups() {
		return false;
	}

	@Override
	public DeploymentDebugTarget getDebugTarget() {
		return (DeploymentDebugTarget) super.getDebugTarget();
	}
}
