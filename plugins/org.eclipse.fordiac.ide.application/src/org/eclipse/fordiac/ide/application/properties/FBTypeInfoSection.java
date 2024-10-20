/*******************************************************************************
 * Copyright (c) 2014, 2016 fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.fordiac.ide.gef.properties.TypeInfoSection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.IWorkbenchPart;

public class FBTypeInfoSection extends TypeInfoSection {

	@Override
	protected CommandStack getCommandStack(final IWorkbenchPart part, final Object input) {
		return null;
	}

	@Override
	protected LibraryElement getInputType(final Object input) {
		Object elementToCheck = input;

		if (input instanceof final EditPart ep) {
			elementToCheck = ep.getModel();
		}
		if (elementToCheck instanceof final FBNetworkElement fbnEl) {
			return fbnEl.getType();
		}
		return null;
	}

}
