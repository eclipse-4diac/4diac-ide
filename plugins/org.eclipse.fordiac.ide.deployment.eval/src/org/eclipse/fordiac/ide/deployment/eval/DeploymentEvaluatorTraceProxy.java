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
package org.eclipse.fordiac.ide.deployment.eval;

import java.text.MessageFormat;

import org.eclipse.fordiac.ide.deployment.util.IDeploymentListener;
import org.eclipse.fordiac.ide.model.eval.AbstractEvaluator;
import org.eclipse.fordiac.ide.model.libraryElement.Device;

public class DeploymentEvaluatorTraceProxy implements IDeploymentListener {

	@Override
	public void connectionOpened(final Device dev) {
		trace(MessageFormat.format(Messages.DeploymentEvaluatorTraceProxy_ConnectionOpened, dev.getName()));
	}

	@Override
	public void connectionClosed(final Device dev) {
		trace(MessageFormat.format(Messages.DeploymentEvaluatorTraceProxy_ConnectionClosed, dev.getName()));
	}

	@Override
	public void postCommandSent(final String info, final String destination, final String command) {
		trace(command.replaceAll("\\R", "")); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	public void postResponseReceived(final String response, final String source) {
		trace(response.replaceAll("\\R", "")); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private static void trace(final String message) {
		AbstractEvaluator.currentMonitors().forEach(monitor -> monitor.info(message));
	}
}
