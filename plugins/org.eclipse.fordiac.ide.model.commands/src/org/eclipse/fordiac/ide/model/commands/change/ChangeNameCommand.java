/*******************************************************************************
 * Copyright (c) 2008, 2023 Profactor GmbH, fortiss GmbH,
 *                          Johannes Kepler Unviersity Linz
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.gef.commands.Command;

public class ChangeNameCommand extends Command {
	private final INamedElement element;
	private final String name;
	private String oldName;
	private INamedElement adapterElement;

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
		setName(name);
	}

	@Override
	public void undo() {
		setName(oldName);
	}

	@Override
	public void redo() {
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

}