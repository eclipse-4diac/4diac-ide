/*******************************************************************************
 * Copyright (c) 2026 Johannes Kepler Universiy Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Schmid
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.properties;

import org.eclipse.fordiac.ide.fbtypeeditor.properties.FBTypePropertiesFilter;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;

public class SimpleFBTypeFilter extends FBTypePropertiesFilter {

	@Override
	public boolean select(final Object toTest) {
		return getFBTypeFromSelected(toTest) != null;
	}

	public static SimpleFBType getFBTypeFromSelected(final Object selected) {
		if (getFBTypeFromSelectedElement(selected) instanceof final SimpleFBType simple) {
			return simple;
		}
		return null;
	}
}
