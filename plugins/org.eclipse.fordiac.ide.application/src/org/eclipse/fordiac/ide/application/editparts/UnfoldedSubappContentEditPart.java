/*******************************************************************************
 * Copyright (c) 2020, 2021 Primetals Technologies Germany GmbH,
 * 							Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr, Alois Zoitl - initial implementation and/or documentation
 *   Alexander Lumplecker, Bianca Wiesmayr, Alois Zoitl - Bug: 568569
 *   Alois Zoitl - extracted most code into common base class for group
 *                 infrastructure
 *******************************************************************************/

package org.eclipse.fordiac.ide.application.editparts;

import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;

public class UnfoldedSubappContentEditPart extends AbstractContainerContentEditPart {

	@Override
	public void setModel(final Object model) {
		super.setModel(model);
		p = FBNetworkHelper.getTopLeftCornerOfFBNetwork(getModel().getNetworkElements());
		p.x -= 40;
	}

}
