/*******************************************************************************
 * Copyright (c) 2011 - 2017 Profactor GmbH, fortiss GmbH
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
package org.eclipse.fordiac.ide.fbtypeeditor.editparts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.fordiac.ide.gef.editparts.AbstractDirectEditableEditPart;
import org.eclipse.fordiac.ide.gef.policies.INamedElementRenameEditPolicy;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.DirectEditRequest;

public class CommentEditPart extends AbstractInterfaceElementEditPart implements
		EditPart {

	private Label comment;

	@Override
	public IInterfaceElement getCastedModel() {
		return ((CommentField)getModel()).getReferencedElement();
	}

	@Override
	protected IFigure createFigure() {
		comment = new Label();
		update();
		return comment;
	}

	@Override
	protected void update() {
		comment.setText(((CommentField)getModel()).getLabel());
	}
	
	@Override
	protected void createEditPolicies() {
		// super.createEditPolicies();
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE,
				new INamedElementRenameEditPolicy() {
					@Override
					protected Command getDirectEditCommand(
							final DirectEditRequest request) {
						if (getHost() instanceof AbstractDirectEditableEditPart) {
							ChangeCommentCommand cmd = new ChangeCommentCommand(
									getCastedModel(), (String) request
											.getCellEditor().getValue());
							return cmd;
						}
						return null;
					}

				});
		
	}
	
	@Override
	public void performRequest(final Request request) {
		// REQ_DIRECT_EDIT -> first select 0.4 sec pause -> click -> edit
		// REQ_OPEN -> doubleclick

		if (request.getType() == RequestConstants.REQ_OPEN) {
			//Perform double click as direct edit
			request.setType(RequestConstants.REQ_DIRECT_EDIT); 
		}
		super.performRequest(request);
	}

	@Override
	public Label getNameLabel() {
		return (Label) getFigure();
	}

	@Override
	public INamedElement getINamedElement() {
		return getCastedModel();
	}
	
	@Override
	public void refreshName() {
		getNameLabel().setText(getCastedModel().getComment());
	}
}
