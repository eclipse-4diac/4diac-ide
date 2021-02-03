/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, fortiss GmbH
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.gef.commands.Command;

public class ChangeNameCommand extends Command {
	private final INamedElement element;
	private final String name;
	private String oldName;
	private FBNetworkElement fbNetworkElement;
	private AdapterDeclaration adapterDeclaration;

	public ChangeNameCommand(final INamedElement element, final String name) {
		super();
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
		if ((element instanceof AdapterDeclaration) && (element.eContainer().eContainer() instanceof CompositeFBType)) {
			fbNetworkElement = ((AdapterDeclaration) element).getAdapterFB();
		}
		if (element instanceof AdapterFB) {
			adapterDeclaration = ((AdapterFB) element).getAdapterDecl();
		}
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

	private void setName(String name) {
		element.setName(name);
		if (null != fbNetworkElement) {
			fbNetworkElement.setName(name);
		}
		if (null != adapterDeclaration) {
			adapterDeclaration.setName(name);
		}
	}

}