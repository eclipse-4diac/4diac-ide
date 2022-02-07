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

/** See PinEventInfoFilter */

public class PinDataInfoFilter implements IFilter {

	@Override
	public boolean select(final Object toTest) {
		if (toTest instanceof UntypedSubAppInterfaceElementEditPart) {
			final IInterfaceElement type = ((UntypedSubAppInterfaceElementEditPart) toTest).getModel();
			if (type instanceof VarDeclaration) {
				return true; // True --> render the PinDataInfoSection
			} else if (type instanceof Event) {
				return false;
			}
		}
		return false;
	}

}
