/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Hesam Rezaee - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.commands.Command;

public class VarConfigurationCommand extends Command {

	private final VarDeclaration varDeclaration;
	private final boolean config;

	public VarConfigurationCommand(final VarDeclaration varDeclaration, final boolean config) {
		this.varDeclaration = varDeclaration;
		this.config = config;
	}

	@Override
	public void execute() {
		varDeclaration.setVarConfig(config);
	}

	@Override
	public void redo() {
		execute();
	}

	@Override
	public void undo() {
		varDeclaration.setVarConfig(!config);
	}

}
