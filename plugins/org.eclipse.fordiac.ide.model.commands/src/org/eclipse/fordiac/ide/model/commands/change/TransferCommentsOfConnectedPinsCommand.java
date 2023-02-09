/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import java.util.List;
import java.util.Map;

import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.gef.commands.Command;

public class TransferCommentsOfConnectedPinsCommand extends Command {
	private final Map<IInterfaceElement, List<IInterfaceElement>> commentsToCopy;

	public TransferCommentsOfConnectedPinsCommand(
			final Map<IInterfaceElement, List<IInterfaceElement>> commentsToCopy) {
		this.commentsToCopy = commentsToCopy;
	}

	@Override
	public void execute() {
		commentsToCopy.forEach((key, elements) -> elements.forEach(e -> e.setComment(key.getComment())));
	}
}
