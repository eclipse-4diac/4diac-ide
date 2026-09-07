/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands;

import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;

public class DependentCommand extends Command implements ScopedCommand {

	private final Command first;
	private final Supplier<Command> secondSupplier;
	private Command second;

	public DependentCommand(final Command first, final Supplier<Command> secondSupplier) {
		this.first = first;
		this.secondSupplier = secondSupplier;
	}

	@Override
	public void execute() {
		first.execute();
		second = secondSupplier.get();
		second.execute();
	}

	@Override
	public void undo() {
		second.undo();
		first.undo();
	}

	@Override
	public void redo() {
		first.redo();
		second.redo();
	}

	@Override
	public boolean canExecute() {
		return first.canExecute();
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		return Stream.of(first, second).filter(ScopedCommand.class::isInstance).map(ScopedCommand.class::cast)
				.map(ScopedCommand::getAffectedObjects).flatMap(Set::stream).collect(Collectors.toUnmodifiableSet());
	}
}