/*******************************************************************************
 * Copyright (c) 2017, 2018 fortiss GmbH
 * 				 2018 - 2020 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *               - fixed untyped subapp interface updates and according code
 *                 cleanup
 *               - allow navigation to parent by double-clicking on subapp
 *                 interface element
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import org.eclipse.draw2d.Border;
import org.eclipse.draw2d.Label;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.application.policies.DeleteSubAppInterfaceElementPolicy;
import org.eclipse.fordiac.ide.gef.draw2d.ConnectorBorder;
import org.eclipse.fordiac.ide.gef.editparts.LabelDirectEditManager;
import org.eclipse.fordiac.ide.gef.figures.ToolTipFigure;
import org.eclipse.fordiac.ide.gef.policies.INamedElementRenameEditPolicy;
import org.eclipse.fordiac.ide.model.commands.change.ChangeSubAppIENameCommand;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.util.IdentifierVerifyListener;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.tools.DirectEditManager;

public class UntypedSubAppInterfaceElementEditPart extends InterfaceEditPartForFBNetwork {
	protected class UntypedSubappIEAdapter extends EContentAdapter {
		@Override
		public void notifyChanged(final Notification notification) {
			final Object feature = notification.getFeature();
			if (LibraryElementPackage.eINSTANCE.getIInterfaceElement_InputConnections().equals(feature)
					|| LibraryElementPackage.eINSTANCE.getIInterfaceElement_OutputConnections().equals(feature)
					|| LibraryElementPackage.eINSTANCE.getINamedElement_Name().equals(feature)
					|| LibraryElementPackage.eINSTANCE.getINamedElement_Comment().equals(feature)) {
				refresh();
			} else if (LibraryElementPackage.eINSTANCE.getIInterfaceElement_Type().equals(feature)) {
				updateConnectorBorderColor();
				refreshToolTip();
			}
			super.notifyChanged(notification);
		}

		private void updateConnectorBorderColor() {
			final Border border = getFigure().getBorder();
			if (border instanceof ConnectorBorder) {
				((ConnectorBorder) border).updateColor();
				getFigure().repaint();
			}

		}
	}

	private DirectEditManager manager;

	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new INamedElementRenameEditPolicy() {
			@Override
			protected Command getDirectEditCommand(final DirectEditRequest request) {
				if (getHost() instanceof UntypedSubAppInterfaceElementEditPart) {
					return new ChangeSubAppIENameCommand(getModel(), (String) request.getCellEditor().getValue());
				}
				return null;
			}

			@Override
			protected void revertOldEditValue(final DirectEditRequest request) {
				getNameLabel().setText(getModel().getName());
				super.revertOldEditValue(request);
			}
		});
		// allow delete of a subapp's interface element
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new DeleteSubAppInterfaceElementPolicy());
	}

	@Override
	public void performRequest(final Request request) {
		if (request.getType() == RequestConstants.REQ_DIRECT_EDIT) {
			// REQ_DIRECT_EDIT -> first select 0.4 sec pause -> click -> edit
			getManager().show();
		} else {
			super.performRequest(request);
		}
	}

	private DirectEditManager getManager() {
		if (manager == null) {
			manager = new LabelDirectEditManager(this, getNameLabel(), new IdentifierVerifyListener());
		}
		return manager;
	}

	public Label getNameLabel() {
		return (Label) getFigure();
	}

	@Override
	protected Adapter createContentAdapter() {
		return new UntypedSubappIEAdapter();
	}

	@Override
	public void refresh() {
		super.refresh();
		getNameLabel().setText(getModel().getName());
		refreshToolTip();
	}

	private void refreshToolTip() {
		getFigure().setToolTip(new ToolTipFigure(getModel()));
	}
}