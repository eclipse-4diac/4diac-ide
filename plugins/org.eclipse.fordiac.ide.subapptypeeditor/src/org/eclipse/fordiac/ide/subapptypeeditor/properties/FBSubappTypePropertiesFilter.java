/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Mario Kastner - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.subapptypeeditor.properties;

import org.eclipse.fordiac.ide.fbtypeeditor.properties.FBTypePropertiesFilter;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;

public class FBSubappTypePropertiesFilter extends FBTypePropertiesFilter {

	@Override
	public boolean select(final Object toTest) {
		return (getFBTypeFromSelectedElement(toTest) instanceof SubAppType);
	}
}
