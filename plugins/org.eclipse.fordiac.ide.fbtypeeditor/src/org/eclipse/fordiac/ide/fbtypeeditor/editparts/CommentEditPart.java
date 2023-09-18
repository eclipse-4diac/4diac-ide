/*******************************************************************************
 * Copyright (c) 2011 - 2017 Profactor GmbH, fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
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
 *   Alois Zoitl - added selection feedback
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.editparts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.fordiac.ide.gef.editparts.AbstractDirectEditableEditPart;
import org.eclipse.fordiac.ide.gef.policies.INamedElementRenameEditPolicy;
import org.eclipse.fordiac.ide.gef.policies.ModifiedNonResizeableEditPolicy;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.DirectEditRequest;

public class CommentEditPart extends AbstractInterfaceElementEditPart {

	@Override
	public IInterfaceElement getCastedModel() {
		return getModel().getReferencedElement();
	}

	@Override
	public CommentField getModel() {
		return (CommentField) super.getModel();
	}

	@Override
	protected IFigure createFigure() {
		return new Label(getModel().getLabel());
	}

	@Override
	public Label getFigure() {
		return (Label) super.getFigure();
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		getFigure().setText(getModel().getLabel());
	}

	@Override
	protected void createEditPolicies() {
		final ModifiedNonResizeableEditPolicy handle = new ModifiedNonResizeableEditPolicy();
		handle.setDragAllowed(false);
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, handle);

		if (isDirectEditable()) {
			installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new INamedElementRenameEditPolicy() {
				@Override
				protected Command getDirectEditCommand(final DirectEditRequest request) {
					return new ChangeCommentCommand(getTargetInterfaceElement(),
							(String) request.getCellEditor().getValue());
				}

				@Override
				protected void revertOldEditValue(final DirectEditRequest request) {
					if (getHost() instanceof final AbstractDirectEditableEditPart viewEditPart) {
						viewEditPart.getNameLabel().setText(viewEditPart.getINamedElement().getComment());
					}
				}
			});
		}
	}

	@Override
	public void performRequest(final Request request) {
		// REQ_DIRECT_EDIT -> first select 0.4 sec pause -> click -> edit
		// REQ_OPEN -> doubleclick

		if (request.getType() == RequestConstants.REQ_OPEN) {
			// Perform double click as direct edit
			request.setType(RequestConstants.REQ_DIRECT_EDIT);
		}
		super.performRequest(request);
	}

	@Override
	public Label getNameLabel() {
		return getFigure();
	}

	@Override
	public INamedElement getINamedElement() {
		return getCastedModel();
	}

	@Override
	public void refreshName() {
		getNameLabel().setText(getCastedModel().getComment());
	}

	@Override
	public boolean isConnectable() {
		return false;
	}

	protected IInterfaceElement getTargetInterfaceElement() {
		if (getCastedModel() instanceof final VarDeclaration varDecl && varDecl.isInOutVar() && !varDecl.isIsInput()) {
			return varDecl.getInOutVarOpposite();
		}
		return getCastedModel();
	}
}
