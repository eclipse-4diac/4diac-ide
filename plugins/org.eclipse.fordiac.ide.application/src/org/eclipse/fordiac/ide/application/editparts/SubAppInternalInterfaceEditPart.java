/*******************************************************************************
 * Copyright (c) 2017 - 2018 fortiss GmbH
 *               2018 - 2020 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *               - allow navigation to parent by double-clicking on subapp 
 *                 interface element
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.application.editors.ApplicationEditorInput;
import org.eclipse.fordiac.ide.application.editors.FBNetworkEditor;
import org.eclipse.fordiac.ide.application.editors.SubAppNetworkEditor;
import org.eclipse.fordiac.ide.application.editors.SubApplicationEditorInput;
import org.eclipse.fordiac.ide.application.policies.DeleteSubAppInterfaceElementPolicy;
import org.eclipse.fordiac.ide.gef.draw2d.ConnectorBorder;
import org.eclipse.fordiac.ide.gef.figures.ToolTipFigure;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.ui.IEditorInput;

public class SubAppInternalInterfaceEditPart extends UntypedSubAppInterfaceElementEditPart {

	@Override
	protected IFigure createFigure() {
		InterfaceFigure figure = new InterfaceFigure();
		figure.setBorder(new ConnectorBorder(getModel()) {
			@Override
			public boolean isInput() {
				return !super.isInput();
			}
		});
		return figure;
	}

	@Override
	public boolean isInput() {
		return !super.isInput();
	}

	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		// allow delete of a subapp's interface element
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new DeleteSubAppInterfaceElementPolicy());
	}

	@Override
	protected void refreshVisuals() {
		getFigure().setToolTip(new ToolTipFigure(getModel()));
		super.refreshVisuals();
	}

	@Override
	public void performRequest(final Request request) {
		if (request.getType() == RequestConstants.REQ_OPEN) {
			// REQ_OPEN -> doubleclick
			goToParent();
		} else {
			super.performRequest(request);
		}
	}

	private void goToParent() {
		EObject perentModel = getModel().getFBNetworkElement().eContainer().eContainer();
		FBNetworkEditor newEditor = (FBNetworkEditor) EditorUtils.openEditor(getEditorInput(perentModel),
				getEditorId(perentModel));
		newEditor.selectElement(getModel());
	}

	private static IEditorInput getEditorInput(EObject model) {
		if (model instanceof SubApp) {
			return new SubApplicationEditorInput((SubApp) model);
		}
		if (model instanceof Application) {
			return new ApplicationEditorInput((Application) model);
		}
		return null;
	}

	private static String getEditorId(EObject model) {
		if (model instanceof SubApp) {
			return SubAppNetworkEditor.class.getName();
		}
		if (model instanceof Application) {
			return FBNetworkEditor.class.getName();
		}
		return null;
	}

}
