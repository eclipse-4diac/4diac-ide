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
package org.eclipse.fordiac.ide.gef.filters;

import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.RootEditPart;
import org.eclipse.jface.viewers.IFilter;

/** Filter that checks whether the toTest object is data or event - for the pin info sections. */

public class PinEventInfoFilter implements IFilter {

	@Override
	public boolean select(final Object toTest) {
		if (toTest instanceof EditPart) {
			final RootEditPart rootEP = ((EditPart) toTest).getRoot();
			if ((rootEP != null) && (rootEP.getAdapter(FBNetwork.class) != null)) {
				final Object type = ((EditPart) toTest).getModel();
				return FilterProperties.isEventPin(type) && FilterProperties.isUntypedSubappPin(type);
			}
		}
		return false;
	}

}
