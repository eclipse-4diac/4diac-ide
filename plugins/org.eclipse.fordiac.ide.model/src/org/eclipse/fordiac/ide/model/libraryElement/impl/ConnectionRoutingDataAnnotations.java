/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.libraryElement.impl;

final class ConnectionRoutingDataAnnotations {

	static boolean connectionRoutingDataValueIsNull(final double value) {
		// for us only two digits are significant therefore everything smaller then 0.01
		// is equal to 0
		return Math.abs(value) < 0.01;
	}

	private ConnectionRoutingDataAnnotations() {
		throw new UnsupportedOperationException("Helper class must not be instantiated"); //$NON-NLS-1$
	}
}
