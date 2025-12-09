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
package org.eclipse.fordiac.ide.deployment.util;

public interface IDeploymentListener2 extends IDeploymentListener {

	/**
	 * Response received.
	 *
	 * @param info     a string containing "ip:port : resource"
	 * @param request  the request
	 * @param response the response
	 * @param peer     the peer
	 */
	void postResponseReceived(final String info, final String request, final String response, final String peer);
}
