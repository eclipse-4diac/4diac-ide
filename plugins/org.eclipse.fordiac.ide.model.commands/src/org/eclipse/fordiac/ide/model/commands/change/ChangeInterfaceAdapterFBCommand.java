/*******************************************************************************
 * Copyright (c) 2025 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import java.util.Objects;

import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.gef.commands.Command;

/**
 * This class is only used by the {@link ChangeDataTypeCommand}
 */
class ChangeInterfaceAdapterFBCommand extends Command {

	private final AdapterDeclaration adpDecl;
	private final AdapterFB oldAdapterFB;
	protected AdapterFB newAdapterFB;

	public ChangeInterfaceAdapterFBCommand(final AdapterDeclaration adpDecl) {
		this.adpDecl = Objects.requireNonNull(adpDecl);
		oldAdapterFB = adpDecl.getInterfaceOnlyAdapterFB();
	}

	@Override
	public void execute() {
		newAdapterFB = createAdapterFB(adpDecl);
		setAdapterFB(newAdapterFB);
	}

	@Override
	public void undo() {
		setAdapterFB(oldAdapterFB);
	}

	@Override
	public void redo() {
		setAdapterFB(newAdapterFB);
	}

	private void setAdapterFB(final AdapterFB fb) {
		adpDecl.setAdapterFB(fb);
		adpDecl.setInterfaceOnlyAdapterFB(fb);
	}

	private static AdapterFB createAdapterFB(final AdapterDeclaration adpDecl) {
		final AdapterType adpType = adpDecl.getType();

		final AdapterFB adapterFB = LibraryElementFactory.eINSTANCE.createAdapterFB();
		adapterFB.setAdapterDecl(adpDecl);
		adapterFB.setTypeEntry(adpType.getTypeEntry());
		final InterfaceList typeInterface = (adapterFB.isPlug() ? adpType.getPlugType().getInterfaceList()
				: adpType.getInterfaceList());
		adapterFB.setInterface(typeInterface.instanceCopy());
		adapterFB.setName(adpDecl.getName());
		return adapterFB;
	}

}
