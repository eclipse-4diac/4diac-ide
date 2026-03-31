/*******************************************************************************
 * Copyright (c) 2026 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.model;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;

public final class TypePinProperty extends PinProperty implements IAdaptable {

	TypePinProperty(final IInterfaceElement pin) {
		super(pin);
	}

	@Override
	public <T> T getAdapter(final Class<T> adapter) {
		if (adapter == ConfigurableObject.class) {
			return adapter.cast(getPin());
		}
		return null;
	}

}
