/*******************************************************************************
 * Copyright (c) 2020 Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.elk.properties;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;

public class UnfoldedSubAppPropertyTester extends PropertyTester {

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (receiver instanceof SubAppForFBNetworkEditPart) {
			return ((SubAppForFBNetworkEditPart) receiver).getModel().isUnfolded();
		}
		return false;
	}

}
