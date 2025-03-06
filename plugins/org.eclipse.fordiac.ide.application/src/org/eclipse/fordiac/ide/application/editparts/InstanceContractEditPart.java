/*******************************************************************************
 * Copyright (c) 2025 Felix Schmid
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Schmid
 *     - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.fordiac.ide.application.figures.InstanceContractFigure;
import org.eclipse.fordiac.ide.application.widgets.ContractEditorDialog;
import org.eclipse.fordiac.ide.model.commands.change.ChangeContractCommand;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.swt.widgets.Display;

public class InstanceContractEditPart extends AbstractGraphicalEditPart {

	private static final int CANCEL = -1;

	private class DeleteContractEditPolicy extends org.eclipse.gef.editpolicies.ComponentEditPolicy {
		@Override
		protected Command createDeleteCommand(final GroupRequest request) {
			return new ChangeContractCommand(getModel().getSubApp(), null);
		}
	}

	void refreshValue() {
		getFigure().setText(getModel().getContract());
	}

	@Override
	public void refresh() {
		super.refresh();
		refreshValue();
	}

	@Override
	public InstanceContractFigure getFigure() {
		return (InstanceContractFigure) super.getFigure();
	}

	@Override
	protected void createEditPolicies() {
		// for deleting a contract
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new DeleteContractEditPolicy());
	}

	@Override
	public InstanceContract getModel() {
		return (InstanceContract) super.getModel();
	}

	@Override
	protected IFigure createFigure() {
		return new InstanceContractFigure(getModel());
	}

	@Override
	public void performRequest(final Request request) {
		// REQ_DIRECT_EDIT -> first select 0.4 sec pause -> click -> edit
		// REQ_OPEN -> doubleclick

		final CommandStack cmdStack = EditorUtils.getCurrentActiveEditor().getAdapter(CommandStack.class);

		if (((request.getType() == RequestConstants.REQ_DIRECT_EDIT)
				|| (request.getType() == RequestConstants.REQ_OPEN)) && cmdStack != null) {
			performDirectEdit(cmdStack);
		} else {
			super.performRequest(request);
		}
	}

	private void performDirectEdit(final CommandStack cmdStack) {
		final var shell = Display.getCurrent().getActiveShell();
		final var editor = new ContractEditorDialog(shell, getModel().getSubApp(), getModel().getContract());

		if (editor.open() != CANCEL) {
			cmdStack.execute(new ChangeContractCommand(getModel().getSubApp(), editor.getContractRule()));
		}
	}
}
