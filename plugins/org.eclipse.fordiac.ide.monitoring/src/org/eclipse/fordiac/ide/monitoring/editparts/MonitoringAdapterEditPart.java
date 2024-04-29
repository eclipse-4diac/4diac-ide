/*******************************************************************************
 * Copyright (c) 2015 - 2017 fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerd Kainz, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.editparts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.application.figures.FBNetworkElementFigure;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringAdapterElement;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.swt.widgets.Display;

public class MonitoringAdapterEditPart extends AbstractMonitoringBaseEditPart {

	@Override
	protected IFigure createFigureForModel() {
		return new FBNetworkElementFigure(getFB());
	}

	private FB getFB() {
		return getModel().getMonitoredAdapterFB();
	}

	@Override
	protected Adapter createContentAdapter() {
		return new AdapterImpl() {
			@Override
			public void notifyChanged(final Notification notification) {
				super.notifyChanged(notification);
				Display.getDefault().asyncExec(MonitoringAdapterEditPart.this::refreshVisuals);
			}
		};
	}

	@Override
	public FBNetworkElementFigure getFigure() {
		if (super.getFigure() instanceof final FBNetworkElementFigure fbnElFigure) {
			return fbnElFigure;
		}
		return null;
	}

	@Override
	protected void createEditPolicies() {
		// currently for adapters we don't need any edit policies
	}

	@Override
	public boolean understandsRequest(final Request request) {
		if (request.getType() == RequestConstants.REQ_DIRECT_EDIT || request.getType() == RequestConstants.REQ_OPEN) {
			// no direct edit for the monitored adapter fb
			return false;
		}
		return super.understandsRequest(request);
	}

	@Override
	public void performRequest(final Request request) {
		if (request.getType() == RequestConstants.REQ_DIRECT_EDIT || request.getType() == RequestConstants.REQ_OPEN) {
			// no direct edit for the monitored adapter fb
		} else {
			super.performRequest(request);
		}
	}

	@Override
	protected void addChildVisual(final EditPart childEditPart, final int index) {
		final IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
		if (childEditPart instanceof final InterfaceEditPart interfaceEditPart) {
			final IFigure ieContainer = (interfaceEditPart.isInput()) ? getInputIFigure(interfaceEditPart)
					: getOutPutFigure(interfaceEditPart);
			ieContainer.add(child);
		} else {
			super.addChildVisual(childEditPart, index);
		}
	}

	private IFigure getInputIFigure(final InterfaceEditPart interfaceEditPart) {
		if (interfaceEditPart.isEvent()) {
			return getFigure().getEventInputs();
		}
		if (interfaceEditPart.isAdapter()) {
			return getFigure().getSockets();
		}
		if (interfaceEditPart.isVariable()) {
			return getFigure().getDataInputs();
		}
		return getFigure();
	}

	private IFigure getOutPutFigure(final InterfaceEditPart interfaceEditPart) {
		if (interfaceEditPart.isEvent()) {
			return getFigure().getEventOutputs();
		}
		if (interfaceEditPart.isAdapter()) {
			return getFigure().getPlugs();
		}
		if (interfaceEditPart.isVariable()) {
			return getFigure().getDataOutputs();
		}
		return getFigure();
	}

	@Override
	protected void removeChildVisual(final EditPart childEditPart) {
		final IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
		if (childEditPart instanceof final InterfaceEditPart interfaceEditPart) {
			final IFigure ieContainer = (interfaceEditPart.isInput()) ? getInputIFigure(interfaceEditPart)
					: getOutPutFigure(interfaceEditPart);
			ieContainer.remove(child);
		} else {
			super.removeChildVisual(childEditPart);
		}
	}

	@Override
	protected List<Object> getModelChildren() {
		final ArrayList<Object> elements = new ArrayList<>();
		elements.addAll(getFB().getInterface().getAllInterfaceElements());
		return elements;
	}

	@Override
	public MonitoringAdapterElement getModel() {
		return (MonitoringAdapterElement) super.getModel();
	}

	@Override
	public INamedElement getINamedElement() {
		return getInterfaceElement();
	}

	@Override
	public Label getNameLabel() {
		return null;
	}
}
