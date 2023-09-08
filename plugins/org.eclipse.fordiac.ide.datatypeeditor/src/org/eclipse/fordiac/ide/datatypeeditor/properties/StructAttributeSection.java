/*******************************************************************************
 * Copyright (c) 2023 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.datatypeeditor.properties;

import org.eclipse.fordiac.ide.datatypeeditor.widgets.StructViewingComposite;
import org.eclipse.fordiac.ide.gef.properties.AttributeSection;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;

public class StructAttributeSection extends AttributeSection {
	@Override
	protected ConfigurableObject getInputType(final Object input) {
		if ((input instanceof final StructViewingComposite structViewingComposite)
				&& (structViewingComposite.getStruct() instanceof final ConfigurableObject configObj)) {
			return configObj;
		}
		return null;
	}
}
