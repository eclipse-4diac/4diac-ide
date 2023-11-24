/*******************************************************************************
 * Copyright (c) 2022,2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Hesam Rezaee - initial API and implementation and/or initial documentation
 *   Martin Melik Merkumians - renamed to match naming scheme
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.commands.Command;

public class ChangeVarConfigurationCommand extends Command implements ScopedCommand {

	private final VarDeclaration varDeclaration;
	private final boolean config;

	public ChangeVarConfigurationCommand(final VarDeclaration varDeclaration, final boolean config) {
		this.varDeclaration = Objects.requireNonNull(varDeclaration);
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

	@Override
	public Set<EObject> getAffectedObjects() {
		return Set.of(varDeclaration);
	}
}
