/*******************************************************************************
 * Copyright (c) 2008 - 2010 Profactor GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.util;

/**
 * The Interface IDeploymentListener.
 * 
 * @author Gerhard Ebenhofer, gerhard.ebenhofer@profactor.at
 */
public interface IDeploymentListener {

	
	void connectionOpened();
	/**
	 * 
	 * @param info a string containing "ip:port : resource"
	 * @param destination the resource
	 * @param command the command
	 */
	void postCommandSent(String info, String destination, String command);
	
	/**
	 * Response received.
	 * 
	 * @param response the response
	 * @param source the source
	 */
	void postResponseReceived(String response, String source);

	/**
	 * Finished.
	 */
	void connectionClosed();

}
