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

package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IFilter;

public class AttributeFilter implements IFilter {

	@Override
	public boolean select(final Object toTest) {
		Object inputHelper = (toTest instanceof final EditPart editpart) ? editpart.getModel() : toTest;
		inputHelper = (inputHelper instanceof final FBNetwork fbNetwork) ? fbNetwork.eContainer() : inputHelper;

		if (inputHelper instanceof ConfigurableObject) {
			return true;
		}
		return false;
	}

}
