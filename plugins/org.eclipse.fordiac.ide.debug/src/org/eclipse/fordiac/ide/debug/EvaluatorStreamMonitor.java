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

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

import org.eclipse.debug.core.IStreamListener;
import org.eclipse.debug.core.model.IFlushableStreamMonitor;
import org.eclipse.fordiac.ide.model.eval.EvaluatorMonitor;

public class EvaluatorStreamMonitor implements IFlushableStreamMonitor, EvaluatorMonitor {

	private Set<IStreamListener> listeners = ConcurrentHashMap.newKeySet();
	private Queue<String> messageBuffer = new ConcurrentLinkedQueue<>();
	private boolean buffered = true;
	private boolean error;

	public EvaluatorStreamMonitor(boolean error) {
		this.error = error;
	}

	@Override
	public String getContents() {
		return this.messageBuffer.stream().collect(Collectors.joining("\n"));
	}

	@Override
	public void flushContents() {
		this.messageBuffer.clear();
	}

	@Override
	public void setBuffered(boolean buffer) {
		this.buffered = buffer;
	}

	@Override
	public boolean isBuffered() {
		return this.buffered;
	}

	@Override
	public void addListener(IStreamListener listener) {
		this.listeners.add(listener);
	}

	@Override
	public void removeListener(IStreamListener listener) {
		this.listeners.remove(listener);
	}

	protected void message(String message) {
		if (this.buffered) {
			this.messageBuffer.add(message);
		}
		this.listeners.forEach(l -> l.streamAppended(message, this));
	}

	@Override
	public void info(String message) {
		if (!this.error) {
			message(message + "\n");
		}
	}

	@Override
	public void warn(String message) {
		if (this.error) {
			message(message + "\n");
		}
	}

	@Override
	public void error(String message) {
		if (this.error) {
			message(message + "\n");
		}
	}

	@Override
	public void error(String message, Throwable t) {
		if (this.error) {
			message(message + ": " + t + "\n");
		}
	}
}