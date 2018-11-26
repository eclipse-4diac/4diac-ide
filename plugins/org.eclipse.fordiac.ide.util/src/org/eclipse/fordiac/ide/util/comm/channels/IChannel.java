/*******************************************************************************
 * Copyright (c) 2011 TU Wien ACIN
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Ingo Hegny, Oliver Hummer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.util.comm.channels;

/**
 * interface provides channel types for low level communication
 */
public interface IChannel {
	
	/**
	 * UDP channel<br>
	 * decision if it is a unicast, multicast or broadcast channel is made on the inetAddress
	 */
	int UDP = 0;
	
	/**
	 * TCP channel<br>
     * TODO: Client or Server Role has to be defined! */
	int TCP = 1;
	
}
