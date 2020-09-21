/*******************************************************************************
 * Copyright (c) 2008 -2016 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Martin Melik Merkumians, Monika Wenger 
 *       - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.create;

import org.eclipse.fordiac.ide.model.ErrorMessenger;
import org.eclipse.fordiac.ide.model.commands.Messages;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.eclipse.fordiac.ide.ui.Abstract4DIACUIPlugin;
import org.eclipse.gef.commands.Command;

public class WithCreateCommand extends Command {
	private Event event;
	private VarDeclaration varDeclaration;
	private boolean forwardCreation;
	private With with;

	public WithCreateCommand() {
	}

	public WithCreateCommand(Event event, VarDeclaration varDeclaration) {
		this.event = event;
		this.varDeclaration = varDeclaration;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(final Event event) {
		this.event = event;
	}

	public VarDeclaration getVarDeclaration() {
		return varDeclaration;
	}

	public void setVarDeclaration(final VarDeclaration varDeclaration) {
		this.varDeclaration = varDeclaration;
	}

	@Override
	public boolean canExecute() {
		if (event == null || varDeclaration == null) {
			return false;
		}
		if (varDeclaration.getType() instanceof AdapterType) {
			ErrorMessenger.popUpErrorMessage(Messages.AdapterConnectionNotAllowed);
			return false;
		}
		for (With w : varDeclaration.getWiths()) {
			if (w.eContainer().equals(event)) {
				ErrorMessenger.popUpErrorMessage(Messages.WithExists);
				return false;
			}
		}
		if ((varDeclaration.isIsInput() && event.isIsInput()) || (!varDeclaration.isIsInput() && !event.isIsInput())) {
			return true;
		}
		return false;

	}

	@Override
	public void execute() {
		with = LibraryElementFactory.eINSTANCE.createWith();
		event.getWith().add(with);
		with.setVariables(varDeclaration);
	}

	@Override
	public void undo() {
		with.setVariables(null);
		event.getWith().remove(with);
	}

	@Override
	public void redo() {
		event.getWith().add(with);
		with.setVariables(varDeclaration);
	}

	public boolean isForwardCreation() {
		return forwardCreation;
	}

	public void setForwardCreation(final boolean forwardCreation) {
		this.forwardCreation = forwardCreation;
	}
}
