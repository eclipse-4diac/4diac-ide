/*******************************************************************************
 * Copyright (c) 2014 fortiss GmbH
 *               2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr, Melanie Winter - clean up
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands;

import org.eclipse.fordiac.ide.model.libraryElement.Primitive;
import org.eclipse.gef.commands.Command;

public class ChangePrimitiveEventCommand extends Command {
	private final Primitive primitive;
	private final String eventName;
	private String oldEventName;

	public ChangePrimitiveEventCommand(final Primitive primitive, final String eventName) {
		this.primitive = primitive;
		this.eventName = eventName;
	}

	@Override
	public void execute() {
		oldEventName = primitive.getEvent();
		primitive.setEvent(eventName);
	}

	@Override
	public void undo() {
		primitive.setEvent(oldEventName);
	}

	@Override
	public void redo() {
		primitive.setEvent(eventName);
	}
}
