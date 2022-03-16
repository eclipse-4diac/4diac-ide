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
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IFilter;

public class CreateConnectionFilter implements IFilter {

	@Override
	public boolean select(Object toTest) {
		if (toTest instanceof EditPart) {
			toTest = ((EditPart) toTest).getModel();
		}
		if (toTest instanceof IInterfaceElement) {
			final IInterfaceElement ie = (IInterfaceElement) toTest;
			if (ie.getFBNetworkElement() != null) {
				// if the interface element is part of typed instance we can not create a connection to it.
				return !ie.getFBNetworkElement().isContainedInTypedInstance();
			}
			return true;
		}

		return false;
	}

}
