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

import org.eclipse.fordiac.ide.model.libraryElement.Service;
import org.eclipse.gef.commands.Command;

public class ChangeServiceInterfaceCommentCommand extends Command {
	private final Service service;
	private final boolean isLeftInterface;
	private final String comment;
	private String oldComment;

	public ChangeServiceInterfaceCommentCommand(final String comment, final Service service, final boolean isLeftInterface) {
		this.service = service;
		this.isLeftInterface = isLeftInterface;
		this.comment = comment;
	}

	@Override
	public void execute() {
		if (isLeftInterface) {
			oldComment = service.getLeftInterface().getComment();
		} else {
			oldComment = service.getRightInterface().getComment();
		}
		setComment(comment);
	}

	@Override
	public void undo() {
		setComment(oldComment);
	}

	@Override
	public void redo() {
		setComment(comment);
	}

	private void setComment(final String comment) {
		if (isLeftInterface) {
			service.getLeftInterface().setComment(comment);
		} else {
			service.getRightInterface().setComment(comment);
		}
	}
}
