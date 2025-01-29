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
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.fordiac.ide.application.handlers.FordiacQuickFixHandler;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.EditPart;

public class RepairElementPropertyTester extends PropertyTester {

	@Override
	public boolean test(final Object receiver, final String property, final Object[] args, final Object expectedValue) {
		return isSupported(receiver);
	}

	protected static boolean isSupported(final Object element) {
		if (element instanceof final EditPart editPart) {
			return FordiacQuickFixHandler.hasMarker(editPart);
		}
		if (element instanceof VarDeclaration || element instanceof FB) {
			return FordiacQuickFixHandler.retrieveMarkerFromObject(element).length > 0;
		}
		return false;
	}

}