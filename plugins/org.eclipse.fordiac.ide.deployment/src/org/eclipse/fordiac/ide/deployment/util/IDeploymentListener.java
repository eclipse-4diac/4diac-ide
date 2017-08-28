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

	/**
	 * Post command sent.
	 * 
	 * @param command the command - formatted as XML
	 * @param destination the destination - formatted as XML comment
	 */
	void postCommandSent(String command, String destination);
	
	/**
	 * Post command sent.
	 * 
	 * @param message - written unformatted to output window
	 */
	void postCommandSent(final String message);

	/**
	 * Response received.
	 * 
	 * @param response the response
	 * @param source the source
	 */
	void responseReceived(String response, String source);

	/**
	 * Finished.
	 */
	void finished();

	
	/**
	 * 
	 * @param info a string containing "ip:port : resource"
	 * @param destination the resource
	 * @param command the command
	 */
	void postCommandSent(String info, String destination, String command);

}
