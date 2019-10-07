/*******************************************************************************
 * Copyright (c) 2018 fortiss GmbH
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
package org.eclipse.fordiac.ide.fbtypeeditor.network.viewer;

import org.eclipse.fordiac.ide.application.editparts.InterfaceEditPartForFBNetwork;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;

public class InterfaceEditPartForFBNetworkRO extends InterfaceEditPartForFBNetwork {

	public InterfaceEditPartForFBNetworkRO() {
		super();
		setConnectable(false);
	}
	
	@Override
	protected GraphicalNodeEditPolicy getNodeEditPolicy() {
		return null;
	}

}
