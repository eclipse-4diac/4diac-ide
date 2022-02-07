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

import org.eclipse.fordiac.ide.application.editparts.InterfaceEditPartForFBNetwork;
import org.eclipse.fordiac.ide.application.editparts.UntypedSubAppInterfaceElementEditPart;
import org.eclipse.jface.viewers.IFilter;

// Filtering the additional pin tab out

public class TypedInterfacePinFilter implements IFilter {

	@Override
	public boolean select(final Object toTest) {
		return (toTest instanceof InterfaceEditPartForFBNetwork
				&& !(toTest instanceof UntypedSubAppInterfaceElementEditPart));

	}

}
