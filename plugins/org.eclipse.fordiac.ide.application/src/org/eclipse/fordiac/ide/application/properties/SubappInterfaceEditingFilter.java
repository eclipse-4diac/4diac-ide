/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.jface.viewers.IFilter;

public class SubappInterfaceEditingFilter implements IFilter {

	@Override
	public boolean select(final Object toTest) {
		if (toTest instanceof final SubAppForFBNetworkEditPart subAppEP) {
			final SubApp subapp = subAppEP.getModel();
			return (!subapp.isTyped()) && !subapp.isContainedInTypedInstance(); // only for untyped
		}
		return SubappPropertySectionFilter.getFBNetworkElementFromSelectedElement(toTest) != null;
	}
}
