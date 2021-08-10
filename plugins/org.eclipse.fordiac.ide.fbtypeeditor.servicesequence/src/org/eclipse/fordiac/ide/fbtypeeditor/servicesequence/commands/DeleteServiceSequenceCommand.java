/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011  Profactor GmbH, TU Wien ACIN
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands;

import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.gef.commands.Command;

public class DeleteServiceSequenceCommand extends Command {

	private final FBType fbType;
	private final ServiceSequence sequence;

	/**
	 * Instantiates a new delete service squence command.
	 * 
	 * @param fbType   the fb type
	 * @param sequence the sequence
	 */
	public DeleteServiceSequenceCommand(FBType fbType, ServiceSequence sequence) {
		this.fbType = fbType;
		this.sequence = sequence;
	}

	@Override
	public boolean canExecute() {
		return (fbType != null) && (sequence != null);
	}

	@Override
	public void execute() {
		fbType.getService().getServiceSequence().remove(sequence);
	}

	@Override
	public void undo() {
		fbType.getService().getServiceSequence().add(sequence);
	}

	@Override
	public void redo() {
		fbType.getService().getServiceSequence().remove(sequence);
	}
}
