/*******************************************************************************
 * Copyright (c) 2018 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
