/*******************************************************************************
 * Copyright (c) 2023 Johannes Kepler University
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
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;

public class ChangeAttributeDeclarationCommand extends AbstractChangeAttributeCommand {
	private final AttributeDeclaration attributeDeclaration;
	private final DataType dataType;

	private AttributeDeclaration oldAttributeDeclaration;
	private DataType oldDataType;

	private ChangeAttributeDeclarationCommand(final Attribute attribute,
			final AttributeDeclaration attributeDeclaration, final DataType dataType) {
		super(attribute);
		this.attributeDeclaration = attributeDeclaration;
		this.dataType = dataType;
	}

	public static boolean attributeDeclarationChanged(final Attribute attribute, final String newName) {
		final TypeLibrary typeLibrary = TypeLibraryManager.INSTANCE.getTypeLibraryFromContext(attribute);

		AttributeDeclaration newDecl = null;
		if (typeLibrary != null && typeLibrary.getAttributeTypeEntry(newName) != null) {
			newDecl = typeLibrary.getAttributeTypeEntry(newName).getType();
		}
		final AttributeDeclaration oldDecl = attribute.getAttributeDeclaration();
		return oldDecl != newDecl;
	}

	public static ChangeAttributeDeclarationCommand forName(final Attribute attribute, final String newName) {
		final TypeLibrary typeLibrary = TypeLibraryManager.INSTANCE.getTypeLibraryFromContext(attribute);

		if (typeLibrary != null && typeLibrary.getAttributeTypeEntry(newName) != null) {
			final AttributeDeclaration newDecl = typeLibrary.getAttributeTypeEntry(newName).getType();
			return new ChangeAttributeDeclarationCommand(attribute, newDecl, newDecl.getType());
		}
		if (attribute.getAttributeDeclaration() != null) {
			return new ChangeAttributeDeclarationCommand(attribute, null, ElementaryTypes.STRING);
		}
		return new ChangeAttributeDeclarationCommand(attribute, null, attribute.getType());
	}

	@Override
	public void doExecute() {
		final Attribute attribute = getAttribute();

		oldDataType = attribute.getType();
		oldAttributeDeclaration = attribute.getAttributeDeclaration();

		attribute.setAttributeDeclaration(attributeDeclaration);
		attribute.setType(dataType);
	}

	@Override
	public void doUndo() {
		getAttribute().setAttributeDeclaration(oldAttributeDeclaration);
		getAttribute().setType(oldDataType);
	}

	@Override
	public void doRedo() {
		getAttribute().setAttributeDeclaration(attributeDeclaration);
		getAttribute().setType(dataType);
	}
}
