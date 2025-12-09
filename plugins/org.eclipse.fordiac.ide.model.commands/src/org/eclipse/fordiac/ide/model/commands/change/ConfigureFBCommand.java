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
 *   Bianca Wiesmayr - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableFB;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableMoveFB;

/**
 * Command for updating the data type of the F_MOVE
 */
public class ConfigureFBCommand extends UpdateFBTypeCommand {
	DataType configuration;

	public ConfigureFBCommand(final ConfigurableFB fbnElement, final DataType config) {
		super(fbnElement);
		this.configuration = config;
	}

	@Override
	public boolean canExecute() {
		return super.canExecute() && configuration != null;
	}

	@Override
	protected void handleConfigurableFB() {
		// for the configurable move fb we have to modify the data type
		if (newElement instanceof final ConfigurableMoveFB fMove) {
			fMove.setDataType(configuration);
			fMove.updateConfiguration();
		}
	}

	@Override
	public ConfigurableFB getNewElement() {
		return (ConfigurableFB) super.getNewElement();
	}

	@Override
	public ConfigurableFB getOldElement() {
		return (ConfigurableFB) super.getOldElement();
	}
}
