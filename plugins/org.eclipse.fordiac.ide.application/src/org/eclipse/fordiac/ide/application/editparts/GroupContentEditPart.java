/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import java.util.List;

import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;

public class GroupContentEditPart extends AbstractContainerContentEditPart {

	@Override
	protected List<FBNetworkElement> getNetworkElements() {
		// our model is the group and the getNetworkElements all elements in the group we want to show as children
		return getModel().getNetworkElements();
	}

}
