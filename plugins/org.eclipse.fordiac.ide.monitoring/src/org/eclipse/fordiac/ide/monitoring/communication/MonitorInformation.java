/*******************************************************************************
 * Copyright (c) 2012, 2014 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.communication;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.eclipse.fordiac.ide.monitoring.Activator;


/**
 * The Class MgrInformation.
 */
public class MonitorInformation {

	/** The original MgrID. */
	public String origMgrID;

	/** The IP. */
	public String iP;

	/** The port. */
	public Integer port = 0;

	public MonitorInformation(final String mgrID) {
		if (mgrID != null) {
			String id = mgrID;
			if (id.startsWith("\"") && id.endsWith("\"")) { //$NON-NLS-1$ //$NON-NLS-2$
				id = id.substring(1, id.length() - 1);
			}
			String[] splitID = id.split(":"); //$NON-NLS-1$
				origMgrID = mgrID;
			if (splitID.length == 2) {
				try {
					InetAddress adress = InetAddress.getByName(splitID[0]);
					iP = adress.getHostAddress();
				} catch (UnknownHostException e) {
				}
				try {
					port = Integer.parseInt(splitID[1]);
				} catch (NumberFormatException e) {
					port = 0;
				}
				if (1023 > port && port > 65536) {
					port = 0;
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return iP + ":" + port; //$NON-NLS-1$
	}

	public InetAddress getInetAdress() {
		try {
			return InetAddress.getByName(iP);
		} catch (UnknownHostException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
		return null;
	}
}