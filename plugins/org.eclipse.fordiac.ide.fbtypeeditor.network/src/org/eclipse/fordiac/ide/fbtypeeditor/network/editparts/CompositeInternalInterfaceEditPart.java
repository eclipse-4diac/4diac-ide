/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, fortiss GmbH
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
package org.eclipse.fordiac.ide.fbtypeeditor.network.editparts;

import org.eclipse.draw2d.Label;
import org.eclipse.fordiac.ide.application.policies.AdapterNodeEditPolicy;
import org.eclipse.fordiac.ide.application.policies.EventNodeEditPolicy;
import org.eclipse.fordiac.ide.application.policies.VariableNodeEditPolicy;
import org.eclipse.fordiac.ide.fbtypeeditor.network.viewer.CompositeInternalInterfaceEditPartRO;
import org.eclipse.fordiac.ide.gef.editparts.ConnCreateDirectEditDragTrackerProxy;
import org.eclipse.fordiac.ide.gef.editparts.LabelDirectEditManager;
import org.eclipse.fordiac.ide.gef.editparts.NameCellEditorLocator;
import org.eclipse.fordiac.ide.gef.policies.INamedElementRenameEditPolicy;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.viewers.TextCellEditor;

public class CompositeInternalInterfaceEditPart extends CompositeInternalInterfaceEditPartRO {
	
	public CompositeInternalInterfaceEditPart() {
		super();
		setConnectable(true);
	}

	protected DirectEditManager manager;

	@Override
	protected GraphicalNodeEditPolicy getNodeEditPolicy() {
		if (isEvent()) {
			return new EventNodeEditPolicy();
		}
		if (isAdapter()) {
			return new AdapterNodeEditPolicy();
		}
		if (isVariable()) {
			return new VariableNodeEditPolicy();
		}
		return null;
	}

	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new INamedElementRenameEditPolicy() {
			@Override
			protected Command getDirectEditCommand(final DirectEditRequest request) {
				if (getHost() instanceof CompositeInternalInterfaceEditPart) {
					ChangeNameCommand cmd = new ChangeNameCommand(getModel(),
							(String) request.getCellEditor().getValue());
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
		if ((request.getType() == RequestConstants.REQ_OPEN)
				|| (request.getType() == RequestConstants.REQ_DIRECT_EDIT)) {
			performDirectEdit();
		} else {
			super.performRequest(request);
		}
	}

	@Override
	public DragTracker getDragTracker(Request request) {
		return new ConnCreateDirectEditDragTrackerProxy(this);
	}

	@Override
	protected void refreshVisuals() {
		getNameLabel().setText(getModel().getName());
		super.refreshVisuals();
	}

	public void performDirectEdit() {
		getManager().show();
	}

	public DirectEditManager getManager() {
		if (manager == null) {
			Label l = getNameLabel();
			manager = new LabelDirectEditManager(this, TextCellEditor.class, new NameCellEditorLocator(l), l);
		}
		return manager;
	}

	private Label getNameLabel() {
		return (Label) getFigure();
	}
}