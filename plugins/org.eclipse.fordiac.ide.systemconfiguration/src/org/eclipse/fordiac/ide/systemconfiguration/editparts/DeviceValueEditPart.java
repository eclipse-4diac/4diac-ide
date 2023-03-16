/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies GmbH
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
package org.eclipse.fordiac.ide.systemconfiguration.editparts;

import org.eclipse.fordiac.ide.gef.editparts.ValueEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class DeviceValueEditPart extends ValueEditPart {

	@Override
	protected String getDefaultValue(final IInterfaceElement ie) {
		final VarDeclaration typeInput = getDeviceTypeInput(ie);
		if (typeInput != null) {
			return super.getDefaultValue(typeInput);
		}
		// we should never be here as all device interface need a type entry, but as backup
		return super.getDefaultValue(ie);
	}

	private static VarDeclaration getDeviceTypeInput(final IInterfaceElement ie) {
		final Device dev = (Device) ie.eContainer();
		return dev.getType().getVarDeclaration().stream().filter(input -> input.getName().equals(ie.getName()))
				.findAny().orElse(null);
	}

}
