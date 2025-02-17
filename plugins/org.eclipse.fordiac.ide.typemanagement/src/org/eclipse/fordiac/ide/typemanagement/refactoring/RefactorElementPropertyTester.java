/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.gef.EditPart;

public class RefactorElementPropertyTester extends PropertyTester {

	@Override
	public boolean test(final Object receiver, final String property, final Object[] args, final Object expectedValue) {
		return isSupported(receiver);
	}

	protected boolean isSupported(final Object element) {
		if (element instanceof final EditPart editPart) {
			return isSupported(editPart.getModel());
		}

		return ((element instanceof final IInterfaceElement ie && !(element instanceof ErrorMarkerInterface))
				&& (ie.getFBNetworkElement() instanceof final FB fb
						&& !fb.getType().getTypeEntry().getFile().isReadOnly()))
				|| (element instanceof final FB fbb && fbb.eContainer() instanceof BaseFBType);
	}
}
