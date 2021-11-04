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
 *   Melanie Winter - use ServicePrimitiveCellEditor
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.ChangePrimitiveEventCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.ChangePrimitiveParameterCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.figures.AdvancedFixedAnchor;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.figures.PrimitiveFigure;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.policies.DeletePrimitiveEditPolicy;
import org.eclipse.fordiac.ide.gef.FixedAnchor;
import org.eclipse.fordiac.ide.gef.editparts.AbstractDirectEditableEditPart;
import org.eclipse.fordiac.ide.gef.editparts.ZoomScalableFreeformRootEditPart;
import org.eclipse.fordiac.ide.gef.policies.EmptyXYLayoutEditPolicy;
import org.eclipse.fordiac.ide.gef.policies.IChangeStringEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Primitive;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.tools.DirectEditManager;

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
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new DirectEditPolicy() {
			@Override
			protected Command getDirectEditCommand(final DirectEditRequest request) {
				if (getHost() instanceof AbstractPrimitiveEditPart) {
					final String[] values = (String[]) request.getCellEditor().getValue();
					if (null != values) {
						final int selected = Integer.parseInt(values[0]);
						final List<String> events = getRelevantEvents(getHost(), (Primitive) getHost().getModel());
						String ev = null;

						final CompoundCommand commands = new CompoundCommand();
						if ((0 <= selected) && (selected < events.size())) {
							ev = events.get(selected);
							commands.add(new ChangePrimitiveEventCommand(getModel(), ev));
						}

						if (values[1] != null) {
							commands.add(new ChangePrimitiveParameterCommand(getModel(), values[1]));
						}
						return commands;
					}
				}
				return null;
			}

			@Override
			protected void showCurrentEditValue(final DirectEditRequest request) {
				// handled by the direct edit manager
			}
		});
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new EmptyXYLayoutEditPolicy());
	}


	@Override
	protected DirectEditManager createDirectEditManager() {
		return new ServicePrimitiveDirectEditManager(this, getModel(), getFigure().getNameLabel(), getZoomManager(),
				(FigureCanvas) getViewer().getControl());
	}

	private ZoomManager getZoomManager() {
		return ((ZoomScalableFreeformRootEditPart) getRoot()).getZoomManager();
	}

	private static List<String> getRelevantEvents(final EditPart editPart, final Primitive primitive) {
		final List<String> events = new ArrayList<>();
		if (editPart instanceof InputPrimitiveEditPart) {
			for (final Event event : primitive.getService().getFBType().getInterfaceList()
					.getEventInputs()) {
				events.add(event.getName());
			}
		} else if (editPart instanceof OutputPrimitiveEditPart) {
			for (final Event event : primitive.getService().getFBType().getInterfaceList()
					.getEventOutputs()) {
				events.add(event.getName());
			}
		}
		return events;
	}

	@Override
	public int getFeatureID() {
		return 0;
	}

	@Override
	public EObject getElement() {
		return getModel();
	}

	@Override
	public Label getLabel() {
		return getNameLabel();
	}

	@Override
	public INamedElement getINamedElement() {
		return null;
	}
}
