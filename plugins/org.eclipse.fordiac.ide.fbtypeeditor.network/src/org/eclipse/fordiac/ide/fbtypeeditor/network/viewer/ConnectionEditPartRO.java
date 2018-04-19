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

import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;

public class ConnectionEditPartRO extends ConnectionEditPart {
	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		//we don't want to allow for RO connections that they can be deleted
		removeEditPolicy(EditPolicy.CONNECTION_ROLE);
	}
}