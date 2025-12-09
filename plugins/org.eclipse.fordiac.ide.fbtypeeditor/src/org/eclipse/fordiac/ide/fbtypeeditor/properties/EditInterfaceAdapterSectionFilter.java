/*******************************************************************************
 * Copyright (c) 2024 Johannes Kepler University Linz
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
package org.eclipse.fordiac.ide.fbtypeeditor.properties;

import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;

public class EditInterfaceAdapterSectionFilter extends FBTypePropertiesFilter {

	@Override
	public boolean select(final Object toTest) {
		final var retval = getFBTypeFromSelectedElement(toTest);
		// the adapter interface section should not be shown for adapters as there
		// cannot be adapters in adapters.
		return (retval != null && !(retval instanceof AdapterType));
	}

}
