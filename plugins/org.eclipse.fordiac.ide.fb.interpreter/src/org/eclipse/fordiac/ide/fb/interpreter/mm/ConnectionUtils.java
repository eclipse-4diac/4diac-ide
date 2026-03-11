/*******************************************************************************
 * Copyright (c) 2025 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr - initial implementation and/or documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.fb.interpreter.mm;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;

public class ConnectionUtils {

	public static EList<Connection> getOutputConnections(final IInterfaceElement iel) {
		IInterfaceElement pin = iel;
		if (InterfacePinUtils.isContainedInAdapter(iel)) {
			pin = InterfacePinUtils.getContainingAdapterDecl(iel);
		}
		return pin.getOutputConnections();
	}

	private ConnectionUtils() {
		throw new UnsupportedOperationException("this class should not be instantiated"); //$NON-NLS-1$
	}
}
