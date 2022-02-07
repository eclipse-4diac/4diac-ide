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
 *   Dunja Životin - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.fordiac.ide.application.editparts.UntypedSubAppInterfaceElementEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.jface.viewers.IFilter;

/** Filter that checks whether the toTest object is data or event - for the pin info sections. */

public class PinEventInfoFilter implements IFilter {

	@Override
	public boolean select(final Object toTest) {
		if (toTest instanceof UntypedSubAppInterfaceElementEditPart) {
			final IInterfaceElement type = ((UntypedSubAppInterfaceElementEditPart) toTest).getModel();
			if (type instanceof VarDeclaration) {
				return false;
			} else if (type instanceof Event) {
				return true; // True --> render the PinEventInfoSection
			}
		}
		return false;
	}

}
