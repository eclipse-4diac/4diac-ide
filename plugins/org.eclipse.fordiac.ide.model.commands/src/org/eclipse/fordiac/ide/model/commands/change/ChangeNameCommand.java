/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.gef.commands.Command;

public class ChangeNameCommand extends Command {
	protected INamedElement element;
	protected String name;
	private String oldName;

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
			redo();
		}

	@Override
	public void undo() {
		element.setName(oldName);
	}

	@Override
	public void redo() {
		element.setName(name);
	}

}