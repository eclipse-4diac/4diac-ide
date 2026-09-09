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
package org.eclipse.fordiac.ide.model.commands.delete;

import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.libraryElement.OverrideAttribute;
import org.eclipse.fordiac.ide.model.libraryElement.TypedSubApp;
import org.eclipse.gef.commands.Command;

public class DeleteOverrideAttributeCommand extends Command implements ScopedCommand {
	private final TypedSubApp typedSubApp;
	private final OverrideAttribute attribute;
	private int index = -1;

	public DeleteOverrideAttributeCommand(final TypedSubApp typedSubApp, final OverrideAttribute attribute) {
		this.typedSubApp = Objects.requireNonNull(typedSubApp);
		this.attribute = attribute;
	}

	@Override
	public boolean canExecute() {
		return attribute != null && typedSubApp.getOverrideAttributes().contains(attribute);
	}

	@Override
	public void execute() {
		final var overrideAttributes = typedSubApp.getOverrideAttributes();
		index = overrideAttributes.indexOf(attribute);
		if (index >= 0) {
			overrideAttributes.remove(index);
		}
	}

	@Override
	public void undo() {
		if (index >= 0) {
			typedSubApp.getOverrideAttributes().add(index, attribute);
		}
	}

	@Override
	public void redo() {
		execute();
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		return Set.of(typedSubApp);
	}
}
