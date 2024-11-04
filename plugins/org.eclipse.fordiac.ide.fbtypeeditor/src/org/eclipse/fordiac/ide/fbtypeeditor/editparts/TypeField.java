/*******************************************************************************
 * Copyright (c) 2011, 2024 Profactor GmbH, fortiss GmbH,
 *                          Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.editparts;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;

public class TypeField implements IAdaptable {
	private final IInterfaceElement referencedElement;

	public IInterfaceElement getReferencedElement() {
		return referencedElement;
	}

	public TypeField(final IInterfaceElement referencedElement) {
		this.referencedElement = referencedElement;
	}

	public String getLabel() {
		return getReferencedElement().getFullTypeName();
	}

	@Override
	public <T> T getAdapter(final Class<T> adapter) {
		if (adapter == ConfigurableObject.class) {
			return adapter.cast(referencedElement);
		}
		return null;
	}
}
