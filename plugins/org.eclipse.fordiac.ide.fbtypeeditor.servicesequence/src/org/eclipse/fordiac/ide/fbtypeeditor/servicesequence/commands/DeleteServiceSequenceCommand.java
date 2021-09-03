/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011  Profactor GmbH, TU Wien ACIN
 *               2021 Johannes Kepler University Linz
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
 *   Melanie Winter - restore original position during undo
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands;

import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.gef.commands.Command;

public class DeleteServiceSequenceCommand extends Command {

	private final FBType fbType;
	private final ServiceSequence sequence;
	private final int oldIndex;
	
	/** Instantiates a new delete service squence command.
	 *
	 * @param fbType   the fb type
	 * @param sequence the sequence to delete */
	public DeleteServiceSequenceCommand(final FBType fbType, final ServiceSequence sequence) {
		this.fbType = fbType;
		this.sequence = sequence;
		this.oldIndex = fbType.getService().getServiceSequence().indexOf(sequence);
	}

	@Override
	public boolean canExecute() {
		return (fbType != null) && (sequence != null) && sequenceExistsInFBType();
	}

	private boolean sequenceExistsInFBType() {
		return oldIndex >= 0;
	}

	@Override
	public void execute() {
		fbType.getService().getServiceSequence().remove(sequence);
	}

	@Override
	public void undo() {
		fbType.getService().getServiceSequence().add(oldIndex, sequence);
	}

	@Override
	public void redo() {
		fbType.getService().getServiceSequence().remove(sequence);
	}
}
