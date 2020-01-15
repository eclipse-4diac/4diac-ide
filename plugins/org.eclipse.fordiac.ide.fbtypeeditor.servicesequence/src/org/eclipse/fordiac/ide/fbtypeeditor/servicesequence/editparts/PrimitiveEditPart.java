/*******************************************************************************
 * Copyright (c) 2014 - 2015 fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.figures.PrimitiveFigure;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.policies.DeletePrimitiveEditPolicy;
import org.eclipse.fordiac.ide.gef.editparts.AbstractDirectEditableEditPart;
import org.eclipse.fordiac.ide.gef.policies.ChangeStringEditPolicy;
import org.eclipse.fordiac.ide.gef.policies.EmptyXYLayoutEditPolicy;
import org.eclipse.fordiac.ide.gef.policies.IChangeStringEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.Primitive;
import org.eclipse.fordiac.ide.model.libraryElement.Service;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;

public abstract class PrimitiveEditPart extends AbstractDirectEditableEditPart
		implements NodeEditPart, IChangeStringEditPart {

	private final PrimitiveConnection connection;

	private Adapter adapter = new AdapterImpl() {
		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			if (getCastedModel().eAdapters().contains(adapter)) {
				refresh();
			}
		}
	};

	public PrimitiveEditPart(PrimitiveConnection connection) {
		super();
		this.connection = connection;
	}

	@Override
	public void activate() {
		if (!isActive()) {
			getCastedModel().eAdapters().add(adapter);
		}
		super.activate();
	}

	@Override
	public void deactivate() {
		if (isActive()) {
			getCastedModel().eAdapters().remove(adapter);
		}
		super.deactivate();
	}

	public PrimitiveConnection getPrimitiveConnection() {
		return connection;
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		PrimitiveFigure figure = (PrimitiveFigure) getFigure();
		if (null != getCastedModel()) {
			figure.setLabelText(getCastedModel().getEvent());
			figure.setInterfaceDirection(isLeftInterface());
		}
	}

	public Primitive getCastedModel() {
		return (Primitive) getModel();
	}

	protected TransactionEditPart getCastedParent() {
		return (TransactionEditPart) getParent();
	}

	protected boolean isLeftInterface() {
		return getCastedModel().getInterface().getName().equals(
				((Service) getCastedModel().eContainer().eContainer().eContainer()).getLeftInterface().getName());
	}

	@Override
	protected IFigure createFigure() {
		return new PrimitiveFigure(isLeftInterface(), getCastedModel().getEvent());
	}

	@Override
	public Label getNameLabel() {
		return ((PrimitiveFigure) getFigure()).getLabel();
	}

	public Figure getCenterFigure() {
		return ((PrimitiveFigure) getFigure()).getCenterFigure();
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(final Request request) {
		return null;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new DeletePrimitiveEditPolicy());
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new ChangeStringEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new EmptyXYLayoutEditPolicy());
	}
}
