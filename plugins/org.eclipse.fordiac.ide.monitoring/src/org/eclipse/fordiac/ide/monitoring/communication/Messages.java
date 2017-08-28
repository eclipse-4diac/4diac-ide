/*******************************************************************************
 * Copyright (c) 2012 Profactor GmbH
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
package org.eclipse.fordiac.ide.monitoring.communication;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.monitoring.communication.messages"; //$NON-NLS-1$
	public static String TCPCommunicationObject_Monitoring_ADD_Watch;
	public static String TCPCommunicationObject_Monitoring_Delete_Watch;
	public static String TCPCommunicationObject_Monitoring_Read_Watches;
	public static String TCPCommunicationObject_WriteParameter;
	public static String TCPCommunicationObject_Monitoring_Force_Value;
	
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
