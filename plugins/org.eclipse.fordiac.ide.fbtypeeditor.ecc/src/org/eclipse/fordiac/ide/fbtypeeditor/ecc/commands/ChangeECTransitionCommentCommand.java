/*******************************************************************************
 * Copyright (c) 2011, 2015 TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands;

import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.gef.commands.Command;

/**
 * A command to rename an EC Transition comment.
 * 
 */
public class ChangeECTransitionCommentCommand extends Command {
	private ECTransition ecTransition;
	private String comment;
	private String oldComment;

	public ChangeECTransitionCommentCommand(final ECTransition ecTransition, final String comment) {
		this.ecTransition = ecTransition;
		this.comment = comment;
	}

	@Override
	public void execute() {
		oldComment = ecTransition.getComment();
		ecTransition.setComment(comment);
	}

	@Override
	public void undo() {
		ecTransition.setComment(oldComment);
	}

	@Override
	public void redo() {
		ecTransition.setComment(comment);
	}
}
