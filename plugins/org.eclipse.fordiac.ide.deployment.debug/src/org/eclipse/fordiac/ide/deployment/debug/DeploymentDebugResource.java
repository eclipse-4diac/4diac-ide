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

import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDisconnect;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.fordiac.ide.deployment.devResponse.FBStatus;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementExecutorService;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;

public class DeploymentDebugResource extends DeploymentDebugElement implements IThread, IDisconnect {

	private final Resource resource;

	private FBStatus status = FBStatus.UNKNOWN;

	public DeploymentDebugResource(final Resource resoure, final DeploymentDebugDevice target) {
		super(target);
		this.resource = resoure;
		fireCreationEvent();
	}

	public FBStatus getStatus() {
		return status;
	}

	public void setStatus(final FBStatus status) {
		if (status == this.status) {
			return;
		}
		this.status = status;
		switch (status) {
		case IDLE, STOPPED -> fireSuspendEvent(DebugEvent.UNSPECIFIED);
		case RUNNING -> fireResumeEvent(DebugEvent.UNSPECIFIED);
		case KILLED -> fireTerminateEvent();
		default -> {
			// do nothing
		}
		}
	}

	@Override
	public boolean canResume() {
		return getDebugTarget().isAlive() && switch (status) {
		case UNKNOWN, IDLE, STOPPED -> true;
		default -> false;
		};
	}

	@Override
	public boolean canSuspend() {
		return getDebugTarget().isAlive() && getDebugTarget().isAllowTerminate() && switch (status) {
		case UNKNOWN, RUNNING -> true;
		default -> false;
		};
	}

	@Override
	public boolean isSuspended() {
		return status == FBStatus.IDLE || status == FBStatus.STOPPED;
	}

	@Override
	public void resume() throws DebugException {
		try {
			getDeviceManagementExecutorService().startResource(resource);
		} catch (final DeploymentException e) {
			throw new DebugException(Status.error(
					MessageFormat.format(Messages.DeploymentDebugResource_ResumeError, resource.getQualifiedName()),
					e));
		}
	}

	@Override
	public void suspend() throws DebugException {
		try {
			getDeviceManagementExecutorService().stopResource(resource);
		} catch (final DeploymentException e) {
			throw new DebugException(Status.error(
					MessageFormat.format(Messages.DeploymentDebugResource_SuspendError, resource.getQualifiedName()),
					e));
		}
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
		return getDebugTarget().isAlive() && getDebugTarget().isAllowTerminate();
	}

	@Override
	public boolean isTerminated() {
		return status == FBStatus.KILLED;
	}

	@Override
	public void terminate() throws DebugException {
		try {
			getDeviceManagementExecutorService().killResource(resource.getName());
			getDeviceManagementExecutorService().deleteResource(resource.getName());
		} catch (final DeploymentException e) {
			throw new DebugException(Status.error(
					MessageFormat.format(Messages.DeploymentDebugResource_TerminateError, resource.getQualifiedName()),
					e));
		}
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
		return new IStackFrame[0];
	}

	@Override
	public boolean hasStackFrames() {
		return false;
	}

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public DeploymentDebugStackFrame getTopStackFrame() {
		return null;
	}

	@Override
	public String getName() {
		return resource.getName();
	}

	@Override
	public IBreakpoint[] getBreakpoints() {
		return new IBreakpoint[0];
	}

	@Override
	public DeploymentDebugDevice getDebugTarget() {
		return (DeploymentDebugDevice) super.getDebugTarget();
	}

	public IDeviceManagementExecutorService getDeviceManagementExecutorService() {
		return getDebugTarget().getDeviceManagementExecutorService();
	}
}
