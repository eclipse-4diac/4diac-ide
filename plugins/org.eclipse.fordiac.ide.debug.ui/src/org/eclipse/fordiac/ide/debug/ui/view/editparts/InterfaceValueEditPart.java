/*******************************************************************************
 * Copyright (c)  2012, 2024 Profactor GmbH, fortiss GmbH,
 * 							 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger - initial implementation
 *   Alois Zoitl - extracted createFigure and position calculation from
 *                 TestEditPart from the FBTester to be used here
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug.ui.view.editparts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.fordiac.ide.debug.ui.view.policies.InterfaceValueDirectEditPolicy;
import org.eclipse.fordiac.ide.gef.editparts.FigureCellEditorLocator;
import org.eclipse.fordiac.ide.gef.editparts.InitialValueDirectEditManager;
import org.eclipse.fordiac.ide.gef.editparts.LabelDirectEditManager;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;

public class InterfaceValueEditPart extends AbstractDebugInterfaceValueEditPart {

	@Override
	public InterfaceValueEntity getModel() {
		return (InterfaceValueEntity) super.getModel();
	}

	@Override
	protected IFigure createFigure() {
		final Label l = (Label) super.createFigure();
		l.setText(getModel().getVariable().getValue().toString());
		return l;
	}

	@Override
	protected void createEditPolicies() {
		if (getModel().getDebugTarget() != null) {
			// only in debug mode we should be editable
			installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new InterfaceValueDirectEditPolicy());
		}
	}

	@Override
	protected IInterfaceElement getInterfaceElement() {
		return getModel().getInterfaceElement();
	}

	@Override
	public void performRequest(final Request request) {
		if ((request.getType() == RequestConstants.REQ_DIRECT_EDIT || request.getType() == RequestConstants.REQ_OPEN)
				&& (getModel().getDebugTarget() != null)) {
			// only allow direct edit when we are in debug mode
			performDirectEdit();
		} else {
			super.performRequest(request);
		}
	}

	private void performDirectEdit() {
		if (getModel().getInterfaceElement() instanceof final VarDeclaration varDecl) {
			new InitialValueDirectEditManager(this, new FigureCellEditorLocator(getLabelFigure()), varDecl,
					getLabelFigure().getText()).show();
		} else {
			new LabelDirectEditManager(this, getLabelFigure()).show();
		}
	}

	public void updateValue() {
		if (isActive() && getFigure() != null) {
			getLabelFigure().setText(getModel().getVariable().toString());
			refreshVisuals();
		}
	}

}
