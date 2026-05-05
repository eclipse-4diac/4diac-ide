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
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.bulkeditor.commands;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.bulkeditor.nattable.BulkEditorNatTable;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteAttributeCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.gef.commands.Command;

public class DeleteAttributeBulkEditorCommand extends Command implements ScopedCommand {

	private final BulkEditorNatTable table;
	private final Attribute attribute;
	private final DeleteAttributeCommand deletCommand;

	public DeleteAttributeBulkEditorCommand(final BulkEditorNatTable table, final ConfigurableObject configurableObject,
			final Attribute attribute) {
		this.table = table;
		this.attribute = attribute;
		this.deletCommand = new DeleteAttributeCommand(configurableObject, attribute);
	}

	@Override
	public boolean canExecute() {
		return deletCommand.canExecute() && table.getCurrentList() != null;
	}

	@Override
	public void execute() {
		table.getCurrentList().remove(attribute);
		deletCommand.execute();
	}

	@Override
	public void redo() {
		table.getCurrentList().remove(attribute);
		deletCommand.redo();
	}

	@Override
	public void undo() {
		deletCommand.undo();
		table.getCurrentList().add(attribute);
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		return deletCommand.getAffectedObjects();
	}
}
