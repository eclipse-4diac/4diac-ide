/*******************************************************************************
 * Copyright (c) 2023 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dario Romano - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.gef.commands.Command;

/** UpdateFBTypeCommand triggers an update of the type for an FB instance */
public class UpdateUntypedSubAppInterfaceCommand extends Command {
	private final DataTypeEntry dataTypeEntry;
	private final SubApp subApp;

	public UpdateUntypedSubAppInterfaceCommand(final FBNetworkElement fbnElement, final DataTypeEntry type) {
		this.subApp = (SubApp) fbnElement;
		this.dataTypeEntry = type;
	}

	@Override
	public boolean canExecute() {
		return true;
	}

	@Override
	public void execute() {
		subApp.getInterface().getAllInterfaceElements().stream()
				.filter(i -> i.getTypeName().equals(dataTypeEntry.getTypeName()))
				.filter(VarDeclaration.class::isInstance).map(VarDeclaration.class::cast).forEach(el -> {
					el.setType(dataTypeEntry.getType());
					if (el.getValue() != null && !el.getValue().getValue().isBlank()) { // as a fallback we just reset
						// the current value
						el.getValue().setValue("");
					}
				});
	}
}