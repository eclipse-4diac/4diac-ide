/*******************************************************************************
 * Copyright (c) 2014 - 2015 fortiss GmbH
 *               2021 Johannes Kepler University Linz
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
 *   Bianca Wiesmayr, Melanie Winter - cleanup, addChildVisual
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
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.swt.layout.GridData;

public abstract class AbstractPrimitiveEditPart extends AbstractDirectEditableEditPart
implements NodeEditPart, IChangeStringEditPart {

	private final PrimitiveConnection connection;

	private final Adapter adapter = new AdapterImpl() {
		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			refresh();

		}
	};

	protected AbstractPrimitiveEditPart(final PrimitiveConnection connection) {
		this.connection = connection;
	}

	@Override
	public void activate() {
		if (!isActive()) {
			getModel().eAdapters().add(adapter);
		}
		super.activate();
	}

	@Override
	public void deactivate() {
		if (isActive()) {
			getModel().eAdapters().remove(adapter);
		}
		super.deactivate();
	}

	public PrimitiveConnection getPrimitiveConnection() {
		return connection;
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		final PrimitiveFigure figure = (PrimitiveFigure) getFigure();
		if (null != getModel()) {
			figure.setLabelText(getModel().getEvent());
			figure.setInterfaceDirection(isLeftInterface());
		}
	}

	@Override
	public Primitive getModel() {
		return (Primitive) super.getModel();
	}

	protected TransactionEditPart getCastedParent() {
		return (TransactionEditPart) getParent();
	}

	protected boolean isLeftInterface() {
		return getModel().getInterface().isLeftInterface();
	}

	@Override
	protected IFigure createFigure() {
		return new PrimitiveFigure(isLeftInterface(), getModel().getEvent());
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

	@Override
	protected void addChildVisual(final EditPart childEditPart, final int index) {
		if (childEditPart instanceof ParameterEditPart) {
			final PrimitiveFigure thisFigure = (PrimitiveFigure) getFigure();
			final IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
			final GridData childData = new GridData();
			childData.grabExcessHorizontalSpace = true;
			childData.horizontalAlignment = GridData.FILL;
			thisFigure.getParameterFigure().getLayoutManager().setConstraint(child, childData);
			thisFigure.getParameterFigure().add(child, index);
		}
	}
}
