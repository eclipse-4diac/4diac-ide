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

import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.gef.commands.Command;

/** UpdateFBTypeCommand triggers an update of the type for an FB instance */
public class UpdatePinInTypeDeclarationCommand extends Command {
	private final DataTypeEntry dataTypeEntry;
	private final FBType fbType;

	public UpdatePinInTypeDeclarationCommand(final FBType fbnElement, final DataTypeEntry type) {
		this.fbType = fbnElement;
		this.dataTypeEntry = type;
	}

	@Override
	public boolean canExecute() {
		return true;
	}

	@Override
	public void execute() {
		fbType.getInterfaceList().getAllInterfaceElements().stream().filter(VarDeclaration.class::isInstance)
				.map(VarDeclaration.class::cast).filter(i -> i.getType().getTypeEntry() == dataTypeEntry)
				.forEach(el -> {
					el.setType(dataTypeEntry.getTypeEditable());
				});
	}
}