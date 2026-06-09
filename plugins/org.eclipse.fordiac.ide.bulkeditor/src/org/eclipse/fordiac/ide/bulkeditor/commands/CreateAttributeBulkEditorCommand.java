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
import org.eclipse.fordiac.ide.model.commands.create.CreateAttributeCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.ui.providers.CreationCommand;

public class CreateAttributeBulkEditorCommand extends CreationCommand implements ScopedCommand {

	private final BulkEditorNatTable table;
	private Attribute attribute;
	private final CreateAttributeCommand createCommand;

	public CreateAttributeBulkEditorCommand(final BulkEditorNatTable table, final ConfigurableObject configurableObject,
			final String name, final String comment, final DataType dataType, final AttributeDeclaration attributeDecl,
			final String value) {
		this.table = table;
		if (attributeDecl == null) {
			this.createCommand = CreateAttributeCommand.forValues(configurableObject, name, comment, dataType, null,
					value, -1);
		} else {
			this.createCommand = CreateAttributeCommand.forValues(configurableObject, attributeDecl.getFullTypeName(),
					comment, attributeDecl.getType(), attributeDecl, value, -1);
		}
	}

	@Override
	public boolean canExecute() {
		return createCommand.canExecute() && table.getCurrentList() != null;
	}

	@Override
	public void execute() {
		createCommand.execute();
		attribute = (Attribute) createCommand.getCreatedElement();
		table.getCurrentList().add(attribute);
	}

	@Override
	public void redo() {
		createCommand.redo();
		table.getCurrentList().add(attribute);
	}

	@Override
	public void undo() {
		table.getCurrentList().remove(attribute);
		createCommand.undo();
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		return createCommand.getAffectedObjects();
	}

	@Override
	public Object getCreatedElement() {
		return attribute;
	}
}
