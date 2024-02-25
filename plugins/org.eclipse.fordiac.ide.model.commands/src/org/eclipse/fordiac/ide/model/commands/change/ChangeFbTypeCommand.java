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

import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public class ChangeFbTypeCommand extends Command implements ScopedCommand {

	private final FB fb;
	private FBTypeEntry oldEntry;
	private final FBTypeEntry newType;
	private final CompoundCommand additionalCommands = new CompoundCommand();

	protected ChangeFbTypeCommand(final FB fb, final FBTypeEntry newType) {
		this.fb = Objects.requireNonNull(fb);
		this.newType = Objects.requireNonNull(newType);
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
		return new ChangeFbTypeCommand(fb, typeEntry);
	}

	@Override
	public boolean canExecute() {
		return FBNetworkHelper.isTypeInsertionSafe(newType.getType(), fb);
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

	@Override
	public Set<EObject> getAffectedObjects() {
		return Set.of(fb);
	}
}
