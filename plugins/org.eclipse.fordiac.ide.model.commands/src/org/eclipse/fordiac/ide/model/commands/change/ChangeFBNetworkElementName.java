/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;

public class ChangeFBNetworkElementName extends AbstractChangeElementNameWithOppositeCommand {

	public ChangeFBNetworkElementName(FBNetworkElement element, String name) {
		super(element, name);
	}

	@Override
	protected INamedElement getOppositeElement(INamedElement element) {
		if (((FBNetworkElement) element).isMapped()) {
			return ((FBNetworkElement) element).getOpposite();
		}
		return null;
	}
}
