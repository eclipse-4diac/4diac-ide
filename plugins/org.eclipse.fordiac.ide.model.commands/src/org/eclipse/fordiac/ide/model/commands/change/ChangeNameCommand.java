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

import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteFBNetworkElementErrorMarkerCommand;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public class ChangeNameCommand extends Command {
	private final INamedElement element;
	private final String name;
	private String oldName;
	private INamedElement adapterElement;
	private final CompoundCommand deleteErrorMarkersCommand = new CompoundCommand();

	public ChangeNameCommand(final INamedElement element, final String name) {
		this.element = element;
		this.name = name;
	}

	@Override
	public boolean canExecute() {
		return NameRepository.isValidName(element, name);
	}

	@Override
	public void execute() {
		oldName = element.getName();
		checkForAdapter();
		checkForErrorMarkers();

		if (deleteErrorMarkersCommand.canExecute()) {
			deleteErrorMarkersCommand.execute();
		}
		setName(name);
	}

	@Override
	public void undo() {
		if (deleteErrorMarkersCommand.canUndo()) {
			deleteErrorMarkersCommand.undo();
		}
		setName(oldName);
	}

	@Override
	public void redo() {
		if (deleteErrorMarkersCommand.canRedo()) {
			deleteErrorMarkersCommand.redo();
		}
		setName(name);
	}

	private void setName(final String name) {
		element.setName(name);
		if (null != adapterElement) {
			adapterElement.setName(name);
		}
	}

	public INamedElement getElement() {
		return element;
	}

	public String getOldName() {
		return oldName;
	}

	private void checkForAdapter() {
		if (element instanceof final AdapterDeclaration adpDecl) {
			if (adpDecl.getAdapterFB() != null) {
				adapterElement = adpDecl.getAdapterFB();
			} else if (adpDecl.getAdapterNetworkFB() != null) {
				adapterElement = adpDecl.getAdapterNetworkFB();
			}
		}
		if (element instanceof final AdapterFB adapterFB) {
			adapterElement = adapterFB.getAdapterDecl();
		}
	}

	private void checkForErrorMarkers() {
		if (element instanceof final FBNetworkElement fbne && fbne.hasError()) {
			deleteErrorMarkersCommand.add(new DeleteFBNetworkElementErrorMarkerCommand(fbne));
		}
	}

}