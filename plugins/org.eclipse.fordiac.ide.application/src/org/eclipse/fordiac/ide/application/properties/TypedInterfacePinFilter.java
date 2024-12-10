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

import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
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
		if (retval instanceof final EditPart ep) {
			retval = ep.getModel();
		}
		if (retval instanceof final Value value) {
			retval = value.getParentIE();
		}
		return isPinOfTypedElement(retval) ? (IInterfaceElement) retval : null;
	}

	private static boolean isPinOfTypedElement(final Object element) {
		if (element instanceof final IInterfaceElement ie) {
			final FBNetworkElement fbEl = ie.getFBNetworkElement();
			return ((fbEl != null) && ((fbEl.getType() != null) || isIndirectlyTyped(fbEl)));
		}
		return false;
	}

	private static boolean isIndirectlyTyped(final FBNetworkElement subapp) {
		if (subapp instanceof SubApp) {
			return subapp.isContainedInTypedInstance();
		}
		return false;
	}
}
