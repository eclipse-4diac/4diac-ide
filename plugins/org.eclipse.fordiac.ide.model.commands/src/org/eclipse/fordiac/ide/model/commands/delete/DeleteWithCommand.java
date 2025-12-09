/*******************************************************************************
 * Copyright (c) 2008, 2009 Profactor GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.delete;

import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.eclipse.gef.commands.Command;

public class DeleteWithCommand extends Command implements ScopedCommand {
	private final With with;
	private final VarDeclaration oldVarDecl;
	private final Event event;

	public DeleteWithCommand(final With with) {
		this.with = Objects.requireNonNull(with);
		event = (Event) with.eContainer();
		oldVarDecl = with.getVariables();
	}

	@Override
	public void execute() {
		redo();
	}

	@Override
	public void undo() {
		event.getWith().add(with);
		with.setVariables(oldVarDecl);
	}

	@Override
	public void redo() {
		with.setVariables(null);
		event.getWith().remove(with);
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		if (event != null && oldVarDecl != null) {
			return Set.of(event, oldVarDecl);
		}
		return Set.of();
	}
}
