/*******************************************************************************
 * Copyright (c) 2022, 2024 Martin Erich Jobst
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

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

import org.eclipse.debug.core.IStreamListener;
import org.eclipse.debug.core.model.IFlushableStreamMonitor;

public class DeploymentStreamMonitor implements IFlushableStreamMonitor {

	private final Set<IStreamListener> listeners = ConcurrentHashMap.newKeySet();
	private final Queue<String> messageBuffer = new ConcurrentLinkedQueue<>();
	private boolean buffered = true;

	@Override
	public String getContents() {
		return messageBuffer.stream().collect(Collectors.joining("\n")); //$NON-NLS-1$
	}

	@Override
	public void flushContents() {
		messageBuffer.clear();
	}

	@Override
	public void setBuffered(final boolean buffer) {
		buffered = buffer;
	}

	@Override
	public boolean isBuffered() {
		return buffered;
	}

	@Override
	public void addListener(final IStreamListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeListener(final IStreamListener listener) {
		listeners.remove(listener);
	}

	protected void message(final String message) {
		if (buffered) {
			messageBuffer.add(message);
		}
		listeners.forEach(l -> l.streamAppended(message, this));
	}
}
