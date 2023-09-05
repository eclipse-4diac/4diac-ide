/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0 3
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.fordiac.ide.gef.properties.AbstractAttributeSection;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.gef.EditPart;

public class ApplicationAttributeSection extends AbstractAttributeSection {

	@Override
	protected ConfigurableObject getInputType(final Object input) {
		Object inputHelper = input instanceof final EditPart editpart ? editpart.getModel() : input;
		inputHelper = inputHelper instanceof final FBNetwork fbNetwork ? fbNetwork.eContainer() : inputHelper;
		return inputHelper instanceof final ConfigurableObject configurableObject ? configurableObject : null;
	}

	@Override
	protected ConfigurableObject getType() {
		return type instanceof final ConfigurableObject configurableObject ? configurableObject : null;
	}
}
