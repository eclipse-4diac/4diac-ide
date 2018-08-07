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

import org.eclipse.fordiac.ide.model.libraryElement.Service;
import org.eclipse.gef.commands.Command;

public class ChangeServiceInterfaceCommentCommand extends Command {
	private Service service;
	private boolean isLeftInterface;
	private String comment;
	private String oldComment;
	
	public ChangeServiceInterfaceCommentCommand(String comment, Service service, boolean isLeftInterface){
		this.service = service;
		this.isLeftInterface = isLeftInterface;
		this.comment = comment;
	}
	
	@Override
	public void execute(){
		if(isLeftInterface){
			oldComment = service.getLeftInterface().getComment();
		}else{
			oldComment = service.getRightInterface().getComment();
		}
		redo();
	}
	
	@Override
	public void undo() {
		if(isLeftInterface){
			service.getLeftInterface().setComment(oldComment);
		}else{
			service.getRightInterface().setComment(oldComment);
		}
	}

	@Override
	public void redo() {
		if(isLeftInterface){
			service.getLeftInterface().setComment(comment);
		}else{
			service.getRightInterface().setComment(comment);
		}
	}
}
