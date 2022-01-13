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

import java.io.IOException;

import org.eclipse.debug.core.model.IStreamsProxy;
import org.eclipse.fordiac.ide.model.eval.EvaluatorThreadGroup;

public class EvaluatorStreamsProxy implements IStreamsProxy {

	private EvaluatorStreamMonitor outputStreamMonitor = new EvaluatorStreamMonitor(false);
	private EvaluatorStreamMonitor errorStreamMonitor = new EvaluatorStreamMonitor(true);

	public EvaluatorStreamsProxy(EvaluatorThreadGroup threadGroup) {
		threadGroup.addMonitor(errorStreamMonitor);
		threadGroup.addMonitor(outputStreamMonitor);
	}

	@Override
	public EvaluatorStreamMonitor getErrorStreamMonitor() {
		return errorStreamMonitor;
	}

	@Override
	public EvaluatorStreamMonitor getOutputStreamMonitor() {
		return outputStreamMonitor;
	}

	@Override
	public void write(String input) throws IOException {
	}
}