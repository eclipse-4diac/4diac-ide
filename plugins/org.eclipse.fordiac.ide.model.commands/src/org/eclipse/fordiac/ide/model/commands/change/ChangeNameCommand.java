/*******************************************************************************
 * Copyright (c) 2008, 2023 Profactor GmbH, fortiss GmbH,
 *                          Johannes Kepler Unviersity Linz
 *               2023 Primetals Technologies Austria GmbHy
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - updated for new adapter FB handling
 *   Fabio Gandolfi - added FBNewtork error marker handling
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.ConnectionLayoutTagger;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteFBNetworkElementErrorMarkerCommand;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public final class ChangeNameCommand extends Command implements ConnectionLayoutTagger {
	private final INamedElement element;
	private final String name;
	private String oldName;
	private final CompoundCommand additionalCommands = new CompoundCommand();

	private ChangeNameCommand(final INamedElement element, final String name) {
		this.element = element;
		this.name = name;
	}

	public static ChangeNameCommand forName(final INamedElement element, final String name) {
		final ChangeNameCommand result = new ChangeNameCommand(element, name);
		if (element instanceof final FBNetworkElement fbne) {
			if (fbne.isMapped()) {
				result.getAdditionalCommands().add(new ChangeNameCommand(fbne.getOpposite(), name));
			}
			if (fbne.hasError()) {
				result.getAdditionalCommands().add(new DeleteFBNetworkElementErrorMarkerCommand(fbne));
			}
		}
		if (element instanceof final IInterfaceElement interfaceElement
				&& interfaceElement.getFBNetworkElement() instanceof final SubApp subApp && subApp.isMapped()) {
			result.getAdditionalCommands().add(
					new ChangeNameCommand(subApp.getOpposite().getInterfaceElement(interfaceElement.getName()), name));
		}
		if (element instanceof final AdapterDeclaration adapterDeclaration) {
			if (adapterDeclaration.getAdapterFB() != null) {
				result.getAdditionalCommands().add(new ChangeNameCommand(adapterDeclaration.getAdapterFB(), name));
			}
			if (adapterDeclaration.getAdapterNetworkFB() != null) {
				result.getAdditionalCommands()
						.add(new ChangeNameCommand(adapterDeclaration.getAdapterNetworkFB(), name));
			}
		}
		if (element instanceof final AdapterFB adapterFB) {
			result.getAdditionalCommands().add(new ChangeNameCommand(adapterFB.getAdapterDecl(), name));
		}
		return result;
	}

	@Override
	public boolean canExecute() {
		return NameRepository.isValidName(element, name)
				&& (additionalCommands.isEmpty() || additionalCommands.canExecute())
				&& !(element instanceof final FBNetworkElement fbne && fbne.isContainedInTypedInstance());
	}

	@Override
	public boolean canRedo() {
		return super.canRedo() && (additionalCommands.isEmpty() || additionalCommands.canRedo());
	}

	@Override
	public boolean canUndo() {
		return super.canUndo() && (additionalCommands.isEmpty() || additionalCommands.canUndo());
	}

	@Override
	public void execute() {
		oldName = element.getName();
		setName(name);
		additionalCommands.execute();
	}

	@Override
	public void undo() {
		additionalCommands.undo();
		setName(oldName);
	}

	@Override
	public void redo() {
		setName(name);
		additionalCommands.redo();
	}

	private void setName(final String name) {
		element.setName(name);
	}

	public INamedElement getElement() {
		return element;
	}

	public String getOldName() {
		return oldName;
	}

	public CompoundCommand getAdditionalCommands() {
		return additionalCommands;
	}
}
