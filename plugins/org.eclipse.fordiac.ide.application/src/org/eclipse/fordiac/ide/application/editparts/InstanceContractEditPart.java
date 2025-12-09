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
import org.eclipse.fordiac.ide.gef.editparts.FigureCellEditorLocator;
import org.eclipse.fordiac.ide.gef.editparts.TextDirectEditManager;
import org.eclipse.fordiac.ide.model.commands.change.ChangeContractCommand;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;

public class InstanceContractEditPart extends AbstractGraphicalEditPart {

	private class DeleteContractEditPolicy extends org.eclipse.gef.editpolicies.ComponentEditPolicy {
		@Override
		protected Command createDeleteCommand(final GroupRequest request) {
			return new ChangeContractCommand(getModel().getSubApp(), null);
		}
	}

	private class ContractRenameEditPolicy extends DirectEditPolicy {
		@Override
		protected Command getDirectEditCommand(final DirectEditRequest request) {
			if (request.getCellEditor().getValue() instanceof final String s) {
				return new ChangeContractCommand(getModel().getSubApp(), s);
			}
			return null;
		}

		@Override
		protected void showCurrentEditValue(final DirectEditRequest request) {
			final String value = (String) request.getCellEditor().getValue();
			getFigure().setText(value);
		}

		@Override
		protected void revertOldEditValue(final DirectEditRequest request) {
			refreshValue();
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
		// remove standard direct edit policy and add custom one
		removeEditPolicy(EditPolicy.DIRECT_EDIT_ROLE);
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new ContractRenameEditPolicy());
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

		if (request.getType() == RequestConstants.REQ_DIRECT_EDIT || request.getType() == RequestConstants.REQ_OPEN) {
			performDirectEdit();
		} else {
			super.performRequest(request);
		}
	}

	private void performDirectEdit() {
		new TextDirectEditManager(this, new FigureCellEditorLocator(getFigure())) {
			private boolean bringDownCalled = false;

			@Override
			protected CellEditor createCellEditorOn(final Composite composite) {
				return new ContractCellEditor(composite, getModel());
			}

			@Override
			protected void initCellEditor() {
				super.initCellEditor();
				getCellEditor().setValue(getModel().getContract());
			}

			@Override
			protected void bringDown() {
				// closing the editor with changes would call bringDown twice, throwing an
				// exception - the 2nd call happens because changing a contract first deletes
				// the old one, which in turn removes the edit part and its cell editor
				if (!bringDownCalled) {
					bringDownCalled = true;
					super.bringDown();
				}
			}
		}.show();
	}
}
