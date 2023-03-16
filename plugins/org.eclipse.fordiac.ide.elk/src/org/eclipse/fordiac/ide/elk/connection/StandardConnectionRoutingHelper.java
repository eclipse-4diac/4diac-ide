/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.elk.connection;

import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.application.editparts.UnfoldedSubappContentEditPart;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.Group;

public class StandardConnectionRoutingHelper extends AbstractConnectionRoutingHelper {

	public static final StandardConnectionRoutingHelper INSTANCE = new StandardConnectionRoutingHelper();

	@Override
	protected void saveConnections(final ConnectionLayoutMapping mapping, final InterfaceEditPart ie) {
		if (mapping.getParentElement() instanceof UnfoldedSubappContentEditPart
				&& ((UnfoldedSubappContentEditPart) mapping.getParentElement()).getParent() == ie.getParent()
				&& ie.getModel().isIsInput()) {
			// we have an unfolded subapp input pin. for this we don't want the connection targets as these are outside
			return;
		}
		super.saveConnections(mapping, ie);
	}

	@Override
	protected void saveConnection(final ConnectionLayoutMapping mapping, final ConnectionEditPart con) {
		final Group sourceGroup = getGroupFromModel(con.getSource().getParent().getModel());
		final Group targetGroup = getGroupFromModel(con.getTarget().getParent().getModel());

		if (sourceGroup == null && targetGroup == null) {
			mapping.getConnections().add(con);
		}
	}

}
