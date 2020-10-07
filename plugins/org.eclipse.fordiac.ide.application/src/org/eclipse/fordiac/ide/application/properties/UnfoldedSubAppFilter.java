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
package org.eclipse.fordiac.ide.application.properties;

import java.util.List;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.application.editparts.FBEditPart;
import org.eclipse.fordiac.ide.application.editparts.UISubAppNetworkEditPart;
import org.eclipse.fordiac.ide.application.editparts.UnfoldedSubappContentEditPart;

public class UnfoldedSubAppFilter extends PropertyTester {

	/*
	 * This function is called inside the eclipse manifest file (application.xml) if
	 * - selected objects are outside of SubAppNetworkEditor 
	 * - selection includes a connection (even in the SubAppNetworkEditor)
	 */
	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (receiver instanceof List) {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			List<Object> list = (List) receiver;
			// this flag is used to indicate if the selection consists of connections only
			boolean hasFBs = false;
			for (Object v : list) {
				if (v instanceof ConnectionEditPart) {
					// no need to do anything
				} else if ((v instanceof FBEditPart)
						&& ((((FBEditPart) v).getParent() instanceof UnfoldedSubappContentEditPart)
								|| (((FBEditPart) v).getParent() instanceof UISubAppNetworkEditPart))) {
					hasFBs = true;
				} else {
					// if it is not a connection or a fb inside a subapp
					return false;
				}
			}
			return hasFBs;
		}
		return false;
	}

}
