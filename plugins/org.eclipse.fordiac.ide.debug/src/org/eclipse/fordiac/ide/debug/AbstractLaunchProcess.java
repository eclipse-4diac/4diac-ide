/*******************************************************************************
 * Copyright (c) 2022, 2024 Martin Erich Jobst,
 *                          Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *   Alois Zoitl  - extracted AbstractLaunchProcess from EvaluatorProcess
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IProcess;

public abstract class AbstractLaunchProcess extends PlatformObject implements IProcess {

	protected final String name;
	protected final ILaunch launch;
	private final Map<String, String> attributes = new HashMap<>();

	protected AbstractLaunchProcess(final String name, final ILaunch launch) {
		this.name = name;
		this.launch = launch;
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

	@Override
	public ILaunch getLaunch() {
		return launch;
	}

	@SuppressWarnings("static-method")
	protected void fireEvent(final DebugEvent event) {
		final DebugPlugin manager = DebugPlugin.getDefault();
		if (manager != null) {
			manager.fireDebugEventSet(new DebugEvent[] { event });
		}
	}

	@Override
	public <T> T getAdapter(final Class<T> adapter) {
		if (adapter.equals(IProcess.class)) {
			return adapter.cast(this);
		}
		if (adapter.equals(IDebugTarget.class)) {
			for (final IDebugTarget target : getLaunch().getDebugTargets()) {
				if (equals(target.getProcess())) {
					return adapter.cast(target);
				}
			}
			return null;
		}
		if (adapter.equals(ILaunch.class)) {
			return adapter.cast(getLaunch());
		}
		if (adapter.equals(ILaunchConfiguration.class)) {
			return adapter.cast(getLaunch().getLaunchConfiguration());
		}
		return super.getAdapter(adapter);
	}

	@Override
	public String getLabel() {
		return name;
	}

	@Override
	public void setAttribute(final String key, final String value) {
		attributes.put(key, value);
	}

	@Override
	public String getAttribute(final String key) {
		return attributes.get(key);
	}

}