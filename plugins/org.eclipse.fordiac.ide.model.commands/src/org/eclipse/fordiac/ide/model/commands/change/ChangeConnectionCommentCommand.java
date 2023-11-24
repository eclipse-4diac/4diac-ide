/*******************************************************************************
 * Copyright (c) 2008, 2023 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 *                          Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Copy/Modified from ChangeCommentCommand
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.gef.commands.Command;

/**
 * The Class ChangeCommentCommand.
 */
public class ChangeConnectionCommentCommand extends Command implements ScopedCommand {

	/** The interface element. */
	private final Connection connection;

	private final String newComment;

	/** The old comment. */
	private String oldComment;

	/**
	 * Instantiates a new change connection comment command.
	 *
	 * @param connection the connection element for which the comment should be
	 *                   changed
	 * @param comment    the comment
	 */
	public ChangeConnectionCommentCommand(final Connection connection, final String comment) {
		this.connection = Objects.requireNonNull(connection);
		// comment must not be null, ensure a correct value in all cases
		this.newComment = (comment != null) ? comment : ""; //$NON-NLS-1$
	}

	/**
	 * Gets the comment.
	 *
	 * @return the comment
	 */
	public String getComment() {
		return newComment;
	}

	@Override
	public void execute() {
		oldComment = connection.getComment();
		connection.setComment(newComment);
	}

	@Override
	public void undo() {
		connection.setComment(oldComment);

	}

	@Override
	public void redo() {
		connection.setComment(newComment);
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		return Set.of(connection);
	}
}
