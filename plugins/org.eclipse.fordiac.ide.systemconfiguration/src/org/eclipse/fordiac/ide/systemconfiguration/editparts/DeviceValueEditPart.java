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
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public class DeviceValueEditPart extends ValueEditPart {

	@Override
	protected String getDefaultValue(final IInterfaceElement ie) {
		if (!IecTypes.GenericTypes.isAnyType(ie.getType())) {
			final VarDeclaration typeInput = getDeviceTypeInput(ie);

			try {
				if (typeInput != null ) {
					return VariableOperations.newVariable(typeInput).getValue().toString();
				}
				// we should never be here as all device interface need a type entry, but as backup
				return VariableOperations.newVariable((VarDeclaration) ie).getValue().toString();
		} catch (final IllegalArgumentException ex) {
			// we are only logging it and jump to default value below
			FordiacLogHelper.logWarning("could not aquire type default value", ex); //$NON-NLS-1$
		}
		}
		return ""; //$NON-NLS-1$
	}

	private static VarDeclaration getDeviceTypeInput(final IInterfaceElement ie) {
		final Device dev = (Device) ie.eContainer();
		return dev.getType().getVarDeclaration().stream().filter(input -> input.getName().equals(ie.getName()))
				.findAny().orElse(null);
	}

}
