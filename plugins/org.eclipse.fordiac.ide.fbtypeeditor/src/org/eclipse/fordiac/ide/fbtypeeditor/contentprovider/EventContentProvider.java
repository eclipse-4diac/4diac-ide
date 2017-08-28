/*******************************************************************************
 * Copyright (c) 2011, 2013, 2016, 2017 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.contentprovider;

import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.jface.viewers.IStructuredContentProvider;

public class EventContentProvider implements IStructuredContentProvider {
	@Override
	public Object[] getElements(final Object inputElement) {
		if (inputElement instanceof VarDeclaration) {
			VarDeclaration var = (VarDeclaration) inputElement;
			FBType fbtype = (FBType) var.eContainer().eContainer();
			if (var.isIsInput()) {
				return fbtype.getInterfaceList().getEventInputs().toArray();
			} else {
				return fbtype.getInterfaceList().getEventOutputs().toArray();
			}
		}
		return null;
	}
}
