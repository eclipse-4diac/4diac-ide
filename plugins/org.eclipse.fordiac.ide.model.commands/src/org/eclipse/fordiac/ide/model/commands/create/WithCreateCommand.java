/*******************************************************************************
 * Copyright (c) 2008 -2016 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Martin Melik Merkumians, Monika Wenger 
 *       - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.create;

import java.util.Iterator;

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
	
	public WithCreateCommand(Event event, VarDeclaration varDeclaration, boolean forwardCreation) {
		this.event = event;
		this.varDeclaration = varDeclaration;
		this.forwardCreation = forwardCreation;
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
			Abstract4DIACUIPlugin.statusLineErrorMessage(Messages.AdapterConnectionNotAllowed);
			return false;
		}
		for (Iterator<With> iterator = varDeclaration.getWiths().iterator(); iterator
				.hasNext();) {
			With with = iterator.next();
			if (with.eContainer().equals(event)) {
				Abstract4DIACUIPlugin.statusLineErrorMessage(Messages.WithExists);
				return false;
			}
		}
		if ((varDeclaration.isIsInput() && event.isIsInput())
				|| (!varDeclaration.isIsInput() && !event.isIsInput())) {
			Abstract4DIACUIPlugin.statusLineErrorMessage(null);
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
