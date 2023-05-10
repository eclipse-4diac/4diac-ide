/********************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Alois Zoitl - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.libraryElement.provider;

import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;

public final class ConnectProviderAnnotation {

	/** Create a string representation for the connection following IEC 61499-1 textual syntax.
	 *
	 * @param con connection
	 * @return string representation of the connection, i.e., "con.source TO con.destination" */
	public static String getConnectionName(final Connection con) {
		String result = genConnEndPointString(con.getSource());
		result += " TO "; //$NON-NLS-1$
		result += genConnEndPointString(con.getDestination());
		return result;
	}

	private static String genConnEndPointString(final IInterfaceElement conEndpoint) {
		String result = ""; //$NON-NLS-1$

		if (conEndpoint != null) {
			if (conEndpoint.getFBNetworkElement() != null) {
				// we are not an interface connection
				result += conEndpoint.getFBNetworkElement().getName();
				result += "."; //$NON-NLS-1$
			}
			result += conEndpoint.getName();
		}

		return result;
	}

	private ConnectProviderAnnotation() {
		throw new UnsupportedOperationException("Helper class should not be instantiated!"); //$NON-NLS-1$
	}
}
