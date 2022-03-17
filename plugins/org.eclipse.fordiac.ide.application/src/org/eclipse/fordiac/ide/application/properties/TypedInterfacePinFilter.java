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
 *   Bianca Wiesmayr, Dunja Životin
 *   - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IFilter;

// Filtering the additional pin tab out

public class TypedInterfacePinFilter implements IFilter {

	@Override
	public boolean select(final Object toTest) {
		return getInterfaceElementFromSelectedElement(toTest) != null;

	}

	static IInterfaceElement getInterfaceElementFromSelectedElement(final Object element) {
		Object retval = element;
		if (retval instanceof EditPart) {
			retval = ((EditPart) retval).getModel();
		}
		if (retval instanceof Value) {
			retval = ((Value) retval).getVarDeclaration();
		}
		return isPinOfTypedElement(retval) ? (IInterfaceElement) retval : null;
	}

	private static boolean isPinOfTypedElement(final Object element) {
		if (element instanceof IInterfaceElement) {
			final IInterfaceElement ie = (IInterfaceElement) element;
			return (ie.getFBNetworkElement() != null && ie.getFBNetworkElement().getType() != null);
		}
		return false;
	}

}
