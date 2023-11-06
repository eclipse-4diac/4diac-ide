/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Melik Merkumians
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.model.commands.change;

import java.text.MessageFormat;

import org.eclipse.fordiac.ide.model.commands.Messages;
import org.eclipse.fordiac.ide.model.commands.util.FordiacMarkerCommandHelper;
import org.eclipse.fordiac.ide.model.errormarker.ErrorMarkerBuilder;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.typelibrary.ErrorTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public class ChangeFbTypeCommand extends Command {

	private final FB fb;
	private FBTypeEntry oldEntry;
	private final FBTypeEntry newType;
	private final CompoundCommand additionalCommands = new CompoundCommand();

	protected ChangeFbTypeCommand(final FB fb, final FBTypeEntry newType) {
		this.fb = fb;
		this.newType = newType;
	}

	public static ChangeFbTypeCommand forTypeName(final FB fb, final String typeName) {
		final TypeLibrary typeLibrary = TypeLibraryManager.INSTANCE.getTypeLibraryFromContext(fb);
		FBTypeEntry typeEntry = typeLibrary.getFBTypeEntry(typeName);
		if (typeEntry == null) {
			typeEntry = (FBTypeEntry) typeLibrary.createErrorTypeEntry(typeName,
					LibraryElementPackage.eINSTANCE.getFBType());
		}
		return ChangeFbTypeCommand.forDataType(fb, typeEntry);
	}

	public static ChangeFbTypeCommand forDataType(final FB fb, final FBTypeEntry typeEntry) {
		final ChangeFbTypeCommand result = new ChangeFbTypeCommand(fb, typeEntry);
		if (fb.getTypeEntry() instanceof ErrorTypeEntry) {
			result.getAdditionalCommands()
					.add(FordiacMarkerCommandHelper.newDeleteMarkersCommand(FordiacMarkerHelper.findMarkers(fb)));
		}
		if (typeEntry instanceof ErrorTypeEntry) {
			result.getAdditionalCommands()
					.add(FordiacMarkerCommandHelper.newCreateMarkersCommand(ErrorMarkerBuilder.createErrorMarkerBuilder(
							MessageFormat.format(Messages.ChangeFbTypeCommand_TypeNotFound, typeEntry.getTypeName(),
									fb.getName()))
							.setTarget(fb)));
		}
		return result;
	}

	@Override
	public boolean canExecute() {
		return FBNetworkHelper.isTypeInsertionSave(newType.getType(), fb);
	}

	@Override
	public void execute() {
		oldEntry = (FBTypeEntry) fb.getType().getTypeEntry();
		setFBType(newType);
		additionalCommands.execute();
	}

	private void setFBType(final FBTypeEntry entry) {
		fb.setTypeEntry(entry);
		fb.setInterface(entry.getType().getInterfaceList().copy());
	}

	@Override
	public void redo() {
		setFBType(newType);
		additionalCommands.redo();
	}

	@Override
	public void undo() {
		additionalCommands.undo();
		setFBType(oldEntry);
	}

	public CompoundCommand getAdditionalCommands() {
		return additionalCommands;
	}
}
