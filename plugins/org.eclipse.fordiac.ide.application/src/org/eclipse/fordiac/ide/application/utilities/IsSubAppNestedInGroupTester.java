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
 *   Fabio Gandolfi
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.utilities;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.gef.EditPart;

public class IsSubAppNestedInGroupTester extends PropertyTester {

	@Override
	public boolean test(final Object receiver, final String property, final Object[] args, final Object expectedValue) {

		if (receiver instanceof EditPart) {
			final Object model = ((EditPart) receiver).getModel();
			if (model instanceof FBNetworkElement) {
				return !((FBNetworkElement) model).isInGroup();
			}
		}
		return false;
	}
}

