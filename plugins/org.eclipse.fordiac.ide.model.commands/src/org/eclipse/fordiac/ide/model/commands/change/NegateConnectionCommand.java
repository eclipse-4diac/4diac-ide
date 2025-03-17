/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Paul Stemmer - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkElementHelper;
import org.eclipse.fordiac.ide.model.libraryElement.CFBInstance;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.commands.Command;

public class NegateConnectionCommand extends Command implements ScopedCommand {

	private final Connection connection;
	private final boolean isNegated;

	public NegateConnectionCommand(final Connection connection, final boolean isNegated) {
		this.connection = Objects.requireNonNull(connection);
		this.isNegated = isNegated;
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		return Set.of(connection.getSource(), connection.getDestination());
	}

	@Override
	public boolean canExecute() {
		if (connection.getFBNetwork().eContainer() instanceof final FBNetworkElement fbnEl) {
			if (((fbnEl instanceof final SubApp subApp) && (subApp.isTyped())) || (fbnEl instanceof CFBInstance)) {
				return false;
			}
			return !FBNetworkElementHelper.isContainedInTypedInstance(fbnEl);
		}
		return true;
	}

	@Override
	public void execute() {
		setNegated(isNegated);
	}

	@Override
	public void undo() {
		setNegated(!isNegated);
	}

	@Override
	public void redo() {
		setNegated(isNegated);
	}

	public void setNegated(final boolean negated) {
		connection.setNegated(negated);
	}

}