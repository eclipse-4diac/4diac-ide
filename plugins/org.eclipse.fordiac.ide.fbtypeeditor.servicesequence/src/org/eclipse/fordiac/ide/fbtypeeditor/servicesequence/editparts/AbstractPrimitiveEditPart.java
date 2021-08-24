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
 *   Bianca Wiesmayr, Melanie Winter - implemented anchor refresh
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.ChangePrimitiveEventCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.figures.AdvancedFixedAnchor;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.figures.PrimitiveFigure;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.policies.DeletePrimitiveEditPolicy;
import org.eclipse.fordiac.ide.gef.FixedAnchor;
import org.eclipse.fordiac.ide.gef.editparts.AbstractDirectEditableEditPart;
import org.eclipse.fordiac.ide.gef.policies.ChangeStringEditPolicy;
import org.eclipse.fordiac.ide.gef.policies.EmptyXYLayoutEditPolicy;
import org.eclipse.fordiac.ide.gef.policies.IChangeStringEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.Primitive;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.DirectEditRequest;

public abstract class AbstractPrimitiveEditPart extends AbstractDirectEditableEditPart
implements NodeEditPart, IChangeStringEditPart {

	private final PrimitiveConnection connection;
	protected FixedAnchor srcAnchor;
	protected FixedAnchor dstAnchor;
	protected AdvancedFixedAnchor srcNeighbourAnchor;
	protected AdvancedFixedAnchor dstNeighbourAnchor;

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
	public PrimitiveFigure getFigure() {
		return (PrimitiveFigure) super.getFigure();
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		final PrimitiveFigure figure = getFigure();
		if (null != getModel()) {
			figure.setInterfaceDirection(isLeftInterface());
			figure.setNameLabelText(getModel().getEvent());
			figure.setParameterLabelText(getModel().getParameters());
			connection.setInputDirection(isLeftInterface());
		}
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(final ConnectionEditPart connection) {
		if (connection instanceof PrimitiveConnectionEditPart) {
			srcAnchor = getPrimitiveConnSourceAnchor();
			return srcAnchor;
		}
		if (connection instanceof ConnectingConnectionEditPart) {
			srcNeighbourAnchor = getConnectingConnSourceAnchor();
			return srcNeighbourAnchor;
		}
		return null;
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(final ConnectionEditPart connection) {
		if (connection instanceof PrimitiveConnectionEditPart) {
			dstAnchor = getPrimitiveConnTargetAnchor();
			return dstAnchor;
		}
		if (connection instanceof ConnectingConnectionEditPart) {
			dstNeighbourAnchor = getConnectingConnTargetAnchor();
			return dstNeighbourAnchor;
		}
		return null;
	}

	protected abstract FixedAnchor getPrimitiveConnSourceAnchor();

	protected abstract AdvancedFixedAnchor getConnectingConnSourceAnchor();

	protected abstract FixedAnchor getPrimitiveConnTargetAnchor();

	protected abstract AdvancedFixedAnchor getConnectingConnTargetAnchor();

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
		return new PrimitiveFigure(isLeftInterface(), getModel().getEvent(), getModel().getParameters());
	}

	@Override
	public Label getNameLabel() {
		return getFigure().getNameLabel();
	}

	public Figure getCenterFigure() {
		return getFigure().getCenterFigure();
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(final Request request) {
		return null;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new DeletePrimitiveEditPolicy());
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new ChangeStringEditPolicy() {
			@Override
			protected Command getDirectEditCommand(final DirectEditRequest request) {
				if (getHost() instanceof AbstractPrimitiveEditPart) {
					final AbstractPrimitiveEditPart viewEditPart = (AbstractPrimitiveEditPart) getHost();
					return new ChangePrimitiveEventCommand((Primitive) viewEditPart.getElement(),
							(String) request.getCellEditor().getValue());
				}
				return null;
			}
		});
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new EmptyXYLayoutEditPolicy());
	}
}
