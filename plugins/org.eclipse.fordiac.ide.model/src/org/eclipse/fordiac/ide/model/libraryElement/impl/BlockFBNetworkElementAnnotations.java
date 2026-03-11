/*******************************************************************************
 * Copyright (c) 2008, 2025 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 *                          Johannes Kepler University,
 *                          Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger, Martin Jobst
 *   			   - initial API and implementation and/or initial documentation
 *   Monika Wenger - extracted the model helper methods into this annotations
 *                   class
 *                 - introduced IEC 61499 attribute support into the model
 *   Alois Zoitl   - reworked model helper functions for better mapping and
 *                   sub-app support
 *   			   - extracted from annotations class and extended with group
 *   			     functions
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.libraryElement.impl;

import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;

public final class BlockFBNetworkElementAnnotations {

	public static void checkConnections(final BlockFBNetworkElement fbne) {
		fbne.getInterface().getAllInterfaceElements().forEach(element -> {
			element.getInputConnections().forEach(Connection::checkIfConnectionBroken);
			element.getOutputConnections().forEach(Connection::checkIfConnectionBroken);
		});
	}

	private BlockFBNetworkElementAnnotations() {
		throw new UnsupportedOperationException();
	}

}
