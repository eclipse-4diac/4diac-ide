/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, fortiss GmbH
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.network.editparts;

import org.eclipse.draw2d.Label;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.application.policies.AdapterNodeEditPolicy;
import org.eclipse.fordiac.ide.application.policies.EventNodeEditPolicy;
import org.eclipse.fordiac.ide.application.policies.VariableNodeEditPolicy;
import org.eclipse.fordiac.ide.fbtypeeditor.network.viewer.CompositeInternalInterfaceEditPartRO;
import org.eclipse.fordiac.ide.gef.annotation.FordiacAnnotationUtil;
import org.eclipse.fordiac.ide.gef.editparts.ConnCreateDirectEditDragTrackerProxy;
import org.eclipse.fordiac.ide.gef.editparts.LabelDirectEditManager;
import org.eclipse.fordiac.ide.gef.figures.ToolTipFigure;
import org.eclipse.fordiac.ide.gef.policies.INamedElementRenameEditPolicy;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.tools.DirectEditManager;

public class CompositeInternalInterfaceEditPart extends CompositeInternalInterfaceEditPartRO {

	private DirectEditManager manager;

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
					return ChangeNameCommand.forName(getModel(), (String) request.getCellEditor().getValue());
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
	public DragTracker getDragTracker(final Request request) {
		return new ConnCreateDirectEditDragTrackerProxy(this);
	}

	@Override
	protected void refreshVisuals() {
		getNameLabel().setText(getModel().getName());
		getFigure().setToolTip(new ToolTipFigure(getModel(), FordiacAnnotationUtil.getAnnotationModel(this)));
		super.refreshVisuals();
	}

	public void performDirectEdit() {
		getManager().show();
	}

	public DirectEditManager getManager() {
		if (manager == null) {
			manager = new LabelDirectEditManager(this, getNameLabel());
		}
		return manager;
	}

	private Label getNameLabel() {
		return (Label) getFigure();
	}

	@Override
	protected Adapter createContentAdapter() {
		return new AdapterImpl() {
			@Override
			public void notifyChanged(final Notification notification) {
				final Object feature = notification.getFeature();
				if (LibraryElementPackage.eINSTANCE.getIInterfaceElement_InputConnections().equals(feature)
						|| LibraryElementPackage.eINSTANCE.getIInterfaceElement_OutputConnections().equals(feature)
						|| LibraryElementPackage.eINSTANCE.getINamedElement_Name().equals(feature)
						|| LibraryElementPackage.eINSTANCE.getIInterfaceElement_Type().equals(feature)
						|| LibraryElementPackage.eINSTANCE.getINamedElement_Comment().equals(feature)) {
					refresh();
				}
				if (LibraryElementPackage.eINSTANCE.getConfigurableObject_Attributes().equals(feature)
						|| LibraryElementPackage.eINSTANCE.getAttribute_Value().equals(feature)) {
					updatePadding(getYPositionFromAttribute(getModel()));
				}
				super.notifyChanged(notification);
			}
		};
	}

	@Override
	public boolean isConnectable() {
		return true;
	}

}