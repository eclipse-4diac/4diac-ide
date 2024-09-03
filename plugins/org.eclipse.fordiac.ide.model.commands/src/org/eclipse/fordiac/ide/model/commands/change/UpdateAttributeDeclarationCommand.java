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
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.commands.create.CreateAttributeCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteAttributeCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.typelibrary.AttributeTypeEntry;
import org.eclipse.gef.commands.Command;

public class UpdateAttributeDeclarationCommand extends Command {

	private final ConfigurableObject co;
	private final AttributeTypeEntry entry;

	private DeleteAttributeCommand deleteAttributeCommand = null;
	private CreateAttributeCommand createAttributeCommand = null;

	public UpdateAttributeDeclarationCommand(final ConfigurableObject co, final AttributeTypeEntry entry) {
		this.co = co;
		this.entry = entry;
	}

	@Override
	public void execute() {
		final Attribute attribute = co.getAttribute(entry.getTypeName());
		final int oldIndex = co.getAttributes().indexOf(attribute);
		deleteAttributeCommand = new DeleteAttributeCommand(co, attribute);
		deleteAttributeCommand.execute();
		final AttributeDeclaration attributeDeclaration = entry.getType();
		final Attribute attr = LibraryElementFactory.eINSTANCE.createAttribute();
		attr.setName(attributeDeclaration.getName());
		attr.setType(attributeDeclaration.getType());
		attr.setAttributeDeclaration(attributeDeclaration);

		createAttributeCommand = CreateAttributeCommand.forTemplate(co, attr, oldIndex);
		createAttributeCommand.execute();
	}

	@Override
	public boolean canUndo() {
		return deleteAttributeCommand != null && createAttributeCommand != null;
	}

	@Override
	public boolean canRedo() {
		return deleteAttributeCommand != null && createAttributeCommand != null;
	}

	@Override
	public void undo() {
		createAttributeCommand.undo();
		deleteAttributeCommand.redo();
	}

	@Override
	public void redo() {
		deleteAttributeCommand.redo();
		createAttributeCommand.redo();
	}
}
