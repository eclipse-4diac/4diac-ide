/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH, Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Monika Wenger - initial API and implementation and/or initial documentation
 *    Bianca Wiesmayr - merge double ResourceInterfaceSection to one class
 *    Alois Zoitl   - fixed layout, reduced code duplication
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.properties;

import org.eclipse.fordiac.ide.gef.properties.AbstractInterfaceSection;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.gef.EditPart;

public class ResourceSection extends AbstractInterfaceSection {

	@Override
	protected Resource getInputType(final Object input) {
		final Object inputHelper = (input instanceof final EditPart e) ? e.getModel() : input;
		if (inputHelper instanceof final Resource res) {
			return res;
		}
		return null;
	}
}
