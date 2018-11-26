/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
		if(((FBNetworkElement)element).isMapped()){
			return ((FBNetworkElement)element).getOpposite();
		}
		return null;
	}
}
