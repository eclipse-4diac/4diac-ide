/*******************************************************************************
 * Copyright (c) 2008, 2014 Profactor GmbH, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts;

import org.eclipse.gef.editparts.AbstractConnectionEditPart;

public class ConnectingConnectionEditPart extends AbstractConnectionEditPart {

	@Override
	public ConnectingConnection getModel() {
		return (ConnectingConnection) super.getModel();
	}

	@Override
	protected void createEditPolicies() {
		// currently no policies for this edit part
	}

}
