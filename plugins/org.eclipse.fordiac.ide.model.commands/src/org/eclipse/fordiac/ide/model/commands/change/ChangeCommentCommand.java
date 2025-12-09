/*******************************************************************************
 * Copyright (c) 2008, 2009, 2012, 2015 Profactor GmbH, TU Wien ACIN, fortiss GmbH
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.gef.commands.Command;

/**
 * The Class ChangeCommentCommand.
 */
public class ChangeCommentCommand extends Command implements ScopedCommand {

	/** The interface element. */
	private final INamedElement namedElement;

	/** The comment. */
	private final String comment;

	/** The old comment. */
	private String oldComment;

	/**
	 * Instantiates a new change comment command.
	 *
	 * @param namedElement the named element
	 * @param comment      the comment
	 */
	public ChangeCommentCommand(final INamedElement namedElement, final String comment) {
		this.namedElement = Objects.requireNonNull(namedElement);
		// comment must not be null, ensure a correct value in all cases
		this.comment = (comment != null) ? comment : ""; //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		oldComment = namedElement.getComment();
		namedElement.setComment(comment);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		namedElement.setComment(oldComment);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		namedElement.setComment(comment);
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		return Set.of(namedElement);
	}
}
