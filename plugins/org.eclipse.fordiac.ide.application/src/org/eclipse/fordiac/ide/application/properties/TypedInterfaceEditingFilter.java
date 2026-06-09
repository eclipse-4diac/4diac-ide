/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Aigner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IFilter;

public class TypedInterfaceEditingFilter implements IFilter {

	@Override
	public boolean select(final Object toTest) {
		return getTypedElementFromSelectedElement(toTest) != null;
	}

	static BlockFBNetworkElement getTypedElementFromSelectedElement(final Object element) {
		if (element instanceof final EditPart ep && ep.getModel() instanceof final BlockFBNetworkElement bfbne) {
			if (bfbne.isContainedInTypedInstance()) {
				return null;
			}

			if (bfbne instanceof final SubApp subapp && !subapp.isTyped()) {
				return null;
			}

			return bfbne;
		}
		return null;
	}

}
