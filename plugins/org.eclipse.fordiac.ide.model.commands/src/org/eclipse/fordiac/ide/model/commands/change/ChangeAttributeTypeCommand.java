/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import java.util.Objects;

import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.helpers.ImportHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;

public class ChangeAttributeTypeCommand extends AbstractChangeAttributeCommand {
	private final DataType dataType;
	private DataType oldDataType;

	private ChangeAttributeTypeCommand(final Attribute attribute, final DataType dataType) {
		super(attribute);
		this.dataType = dataType;
	}

	public static ChangeAttributeTypeCommand forTypeName(final Attribute attribute, final String typeName) {
		final TypeLibrary typeLibrary = TypeLibraryManager.INSTANCE.getTypeLibraryFromContext(attribute);
		final DataType dataType = ImportHelper.resolveImport(typeName, attribute,
				typeLibrary.getDataTypeLibrary()::getTypeIfExists, typeLibrary.getDataTypeLibrary()::getType);
		return ChangeAttributeTypeCommand.forDataType(attribute, dataType);
	}

	public static ChangeAttributeTypeCommand forDataType(final Attribute attribute, final DataType dataType) {
		return new ChangeAttributeTypeCommand(attribute, Objects.requireNonNullElse(dataType, ElementaryTypes.STRING));
	}

	@Override
	public boolean canExecute() {
		return super.canExecute() && getAttribute().getAttributeDeclaration() == null;
	}

	@Override
	public void doExecute() {
		oldDataType = getAttribute().getType();
		getAttribute().setType(dataType);
	}

	@Override
	public void doUndo() {
		getAttribute().setType(oldDataType);
	}

	@Override
	public void doRedo() {
		getAttribute().setType(dataType);
	}
}
