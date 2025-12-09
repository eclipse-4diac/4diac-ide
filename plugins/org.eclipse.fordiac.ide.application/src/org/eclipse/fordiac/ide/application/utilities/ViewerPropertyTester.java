/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Lukas Wais, Bianca Wiesmayr,
 *   Daniel Lindhuber, Michael Oberlehner - initial implementation
 *   										and/or initial documentation
 *   Lukas Wais, Michael Oberlehner		  - refactored to work correctly with
 *   										receiver object
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.utilities;

import java.util.List;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.fordiac.ide.gef.editparts.AbstractFBNetworkEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class ViewerPropertyTester extends PropertyTester {

	@Override
	public boolean test(final Object receiver, final String property, final Object[] args, final Object expectedValue) {
		if (receiver instanceof List) {
			final List<?> selectedElements = (List<?>) receiver;
			if (selectedElements.size() > 1) {
				return false;
			}

			if (selectedElements.get(0) instanceof AbstractFBNetworkEditPart) {
				return false;
			}
		}

		final IWorkbenchPart part = getCurrentActiveView();
		return (part != null) && (part.getAdapter(FBNetwork.class) != null);
	}

	public static IWorkbenchPart getCurrentActiveView() {
		final IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window != null && window.getActivePage() != null) {
			return window.getActivePage().getActivePart();
		}
		return null;
	}
}
