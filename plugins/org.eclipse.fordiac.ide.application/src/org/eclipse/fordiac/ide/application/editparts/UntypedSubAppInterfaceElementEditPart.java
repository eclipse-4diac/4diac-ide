/*******************************************************************************
 * Copyright (c) 2017, 2018 fortiss GmbH, Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import org.eclipse.draw2d.Label;
import org.eclipse.fordiac.ide.application.policies.DeleteSubAppInterfaceElementPolicy;
import org.eclipse.fordiac.ide.gef.editparts.LabelDirectEditManager;
import org.eclipse.fordiac.ide.gef.editparts.NameCellEditorLocator;
import org.eclipse.fordiac.ide.gef.policies.INamedElementRenameEditPolicy;
import org.eclipse.fordiac.ide.model.commands.change.ChangeSubAppIENameCommand;
import org.eclipse.fordiac.ide.util.IdentifierVerifyListener;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.viewers.TextCellEditor;

public class UntypedSubAppInterfaceElementEditPart extends InterfaceEditPartForFBNetwork {
	protected DirectEditManager manager;
	
	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE,
			new INamedElementRenameEditPolicy() {
				@Override
				protected Command getDirectEditCommand(
						final DirectEditRequest request) {
					if (getHost() instanceof UntypedSubAppInterfaceElementEditPart) {
						return new ChangeSubAppIENameCommand(getModel(), (String)request.getCellEditor().getValue());
					}
					return null;
				}
			});
		// allow delete of a subapp's interface element
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new DeleteSubAppInterfaceElementPolicy());
	}
	
	@Override
	public void performRequest(final Request request) {
		// REQ_DIRECT_EDIT -> first select 0.4 sec pause -> click -> edit
		// REQ_OPEN -> doubleclick
		if ((request.getType() == RequestConstants.REQ_OPEN) ||
				(request.getType() == RequestConstants.REQ_DIRECT_EDIT)){
			getManager().show();
		}
		super.performRequest(request);
	}
	
	public DirectEditManager getManager() {
		if (manager == null) {
			Label l = getNameLabel();
			manager = new LabelDirectEditManager(this, TextCellEditor.class,
					new NameCellEditorLocator(l), l,
					new IdentifierVerifyListener()) {
				@Override
				protected void bringDown() {
					if (getEditPart() instanceof UntypedSubAppInterfaceElementEditPart) {
						((UntypedSubAppInterfaceElementEditPart) getEditPart()).refreshName();
					}
					super.bringDown();
				}
			}; // ensures that interface elements are only valid identifiers
		}
		return manager;
	}
	
	public Label getNameLabel() {
		return (Label) getFigure();
	}
	
	public void refreshName() {
		getNameLabel().setText(getModel().getName());
	}
}