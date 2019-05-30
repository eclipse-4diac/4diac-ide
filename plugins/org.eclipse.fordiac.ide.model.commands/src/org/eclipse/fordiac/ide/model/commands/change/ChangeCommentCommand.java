/*******************************************************************************
 * Copyright (c) 2008, 2009, 2012, 2015 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.gef.commands.Command;

/**
 * The Class ChangeCommentCommand.
 */
public class ChangeCommentCommand extends Command {

	/** The interface element. */
	private INamedElement interfaceElement;

	/** The comment. */
	private String comment;

	/** The old comment. */
	private String oldComment;

	/**
	 * Instantiates a new change comment command.
	 * 
	 * @param interfaceElement the interface element
	 * @param comment          the comment
	 */
	public ChangeCommentCommand(final INamedElement interfaceElement, final String comment) {
		this.interfaceElement = interfaceElement;
		this.comment = comment;
	}

	/**
	 * Sets the interface element.
	 * 
	 * @param interfaceElement the new interface element
	 */
	public void setInterfaceElement(final IInterfaceElement interfaceElement) {
		this.interfaceElement = interfaceElement;
	}

	/**
	 * Gets the comment.
	 * 
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Sets the comment.
	 * 
	 * @param comment the new comment
	 */
	public void setComment(final String comment) {
		this.comment = comment;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		oldComment = interfaceElement.getComment();
		interfaceElement.setComment(comment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		interfaceElement.setComment(oldComment);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		interfaceElement.setComment(comment);
	}

}
