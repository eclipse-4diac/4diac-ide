/*******************************************************************************
 * Copyright (c) 2014 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands;

import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.gef.commands.Command;

public class ChangeSequenceCommentCommand extends Command {
	private ServiceSequence sequence;
	private String comment;
	private String oldComment;
	
	public ChangeSequenceCommentCommand(String comment, ServiceSequence sequence){
		this.sequence = sequence;
		this.comment = comment;
	}
	
	public void execute(){
		oldComment = sequence.getComment();
		redo();
	}
	
	public void undo() {
		sequence.setComment(oldComment);
	}

	public void redo() {
		sequence.setComment(comment);
	}
}
