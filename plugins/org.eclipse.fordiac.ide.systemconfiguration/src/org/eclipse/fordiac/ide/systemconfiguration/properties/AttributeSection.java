/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0 3
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.properties;

import org.eclipse.fordiac.ide.gef.properties.AbstractAttributeSection;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.gef.EditPart;

public class AttributeSection extends AbstractAttributeSection {
	@Override
	protected ConfigurableObject getInputType(Object input) {
		Object inputHelper = (input instanceof EditPart) ? ((EditPart) input).getModel() : input;
		if (inputHelper instanceof ConfigurableObject) {
			return (ConfigurableObject) inputHelper;
		}
		return null;
	}

	@Override
	protected ConfigurableObject getType() {
		if (type instanceof ConfigurableObject) {
			return (ConfigurableObject) type;
		}
		return null;
	}
}
