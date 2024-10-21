/*******************************************************************************
 * Copyright (c) 2008, 2023 Profactor GmbH, fortiss GmbH,
 *                          Johannes Kepler University Linz
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Gerhard Ebenhofer
 *       - initial API and implementation and/or initial documentation
 *   Alois Zoitl - fixed issues in adapter update
 *               - moved adapter type handling to own adapter command
 *   Martin Jobst - add value validation
 *                - resolve imports
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.helpers.ImportHelper;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.EventTypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.gef.commands.CompoundCommand;

public final class ChangeDataTypeCommand extends AbstractChangeInterfaceElementCommand {
	private static final Pattern ARRAY_TYPE_DECLARATION_PATTERN = Pattern.compile("ARRAY\\s*\\[(.*)\\]\\s*OF\\s+(.+)", //$NON-NLS-1$
			Pattern.CASE_INSENSITIVE);

	private final DataType dataType;
	private DataType oldDataType;
	private final CompoundCommand additionalCommands = new CompoundCommand();

	private ChangeDataTypeCommand(final IInterfaceElement interfaceElement, final DataType dataType) {
		super(interfaceElement);
		this.dataType = dataType;
	}

	public static ChangeDataTypeCommand forTypeName(final IInterfaceElement interfaceElement, final String typeName) {
		final TypeLibrary typeLibrary = TypeLibraryManager.INSTANCE.getTypeLibraryFromContext(interfaceElement);
		final DataType dataType;
		if (interfaceElement instanceof Event) {
			dataType = ImportHelper.resolveImport(typeName, interfaceElement, EventTypeLibrary.getInstance()::getType,
					name -> {
						throw new NoSuchElementException(name);
					});
		} else if (interfaceElement instanceof AdapterDeclaration) {
			dataType = ImportHelper
					.resolveImport(typeName, interfaceElement, typeLibrary::getAdapterTypeEntry, name -> {
						throw new NoSuchElementException(name);
					}).getType();
		} else {
			dataType = ImportHelper.resolveImport(typeName, interfaceElement,
					typeLibrary.getDataTypeLibrary()::getTypeIfExists, typeLibrary.getDataTypeLibrary()::getType);
		}
		return ChangeDataTypeCommand.forDataType(interfaceElement, dataType);
	}

	public static ChangeDataTypeCommand forDataType(final IInterfaceElement interfaceElement, final DataType dataType) {
		final ChangeDataTypeCommand result = new ChangeDataTypeCommand(interfaceElement, dataType);
		if (interfaceElement != null && interfaceElement.getFBNetworkElement() instanceof final SubApp subApp
				&& subApp.isMapped()) {
			result.getAdditionalCommands().add(new ChangeDataTypeCommand(
					subApp.getOpposite().getInterfaceElement(interfaceElement.getName()), dataType));
		}
		if (interfaceElement instanceof final AdapterDeclaration adapterDeclaration
				&& interfaceElement.eContainer() instanceof final InterfaceList interfaceList
				&& interfaceList.eContainer() instanceof final CompositeFBType compositeFBType
				&& !(compositeFBType instanceof SubAppType)) {
			result.getAdditionalCommands().add(new ChangeAdapterFBCommand(adapterDeclaration));
		}
		return result;
	}

	public static ChangeDataTypeCommand forTypeDeclaration(final IInterfaceElement interfaceElement,
			final String typeDeclaration) {
		if (interfaceElement instanceof final VarDeclaration varDeclaration) {
			final Matcher matcher = ARRAY_TYPE_DECLARATION_PATTERN.matcher(typeDeclaration.trim());
			final String arraySize;
			final String dataTypeName;
			if (matcher.matches()) {
				arraySize = matcher.group(1);
				dataTypeName = matcher.group(2);
			} else {
				arraySize = null;
				dataTypeName = typeDeclaration;
			}
			final ChangeDataTypeCommand result = ChangeDataTypeCommand.forTypeName(varDeclaration, dataTypeName);
			result.getAdditionalCommands().add(ChangeArraySizeCommand.forArraySize(varDeclaration, arraySize));
			return result;
		}
		return forTypeName(interfaceElement, typeDeclaration);
	}

	@Override
	protected void doExecute() {
		oldDataType = getInterfaceElement().getType();
		setNewType();
		additionalCommands.execute();
	}

	@Override
	protected void doUndo() {
		additionalCommands.undo();
		getInterfaceElement().setType(oldDataType);
	}

	@Override
	protected void doRedo() {
		setNewType();
		additionalCommands.redo();
	}

	private void setNewType() {
		getInterfaceElement().setType(dataType);
	}

	public CompoundCommand getAdditionalCommands() {
		return additionalCommands;
	}
}
