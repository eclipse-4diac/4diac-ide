/*******************************************************************************
 * Copyright (c) 2015, 2024 fortiss GmbH, Martin Erich Jobst
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
 *   Martin Erich Jobst - rewrite based on MonitoringAdapterEditPart
 *                        for new deployment monitoring framework
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.debug.ui.editparts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.fordiac.ide.application.figures.FBNetworkElementFigure;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;

public class AdapterWatchValueEditPart extends AbstractWatchValueEditPart {

	@Override
	protected IFigure createFigure() {
		return new FBNetworkElementFigure(getFB());
	}

	@Override
	public FBNetworkElementFigure getFigure() {
		return (FBNetworkElementFigure) super.getFigure();
	}

	@Override
	protected List<Object> getModelChildren() {
		final List<Object> elements = new ArrayList<>();
		elements.addAll(getFB().getInterface().getAllInterfaceElements());
		return elements;
	}

	@Override
	protected EditPart createChild(final Object model) {
		if (model instanceof final IInterfaceElement interfaceElement) {
			return new AdapterWatchValueInterfaceEditPart(interfaceElement);
		}
		return super.createChild(model);
	}

	private FBNetworkElement getFB() {
		return getInterfaceElement().getAdapterFB();
	}

	@Override
	public AdapterDeclaration getInterfaceElement() {
		return (AdapterDeclaration) getModel().getElement();
	}

	@Override
	protected Dimension calculateSize() {
		return getFigure().getPreferredSize();
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
			getInterfaceFigure(interfaceEditPart).add(child);
		} else {
			super.addChildVisual(childEditPart, index);
		}
	}

	@Override
	protected void removeChildVisual(final EditPart childEditPart) {
		final IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
		if (childEditPart instanceof final InterfaceEditPart interfaceEditPart) {
			getInterfaceFigure(interfaceEditPart).remove(child);
		} else {
			super.removeChildVisual(childEditPart);
		}
	}

	private IFigure getInterfaceFigure(final InterfaceEditPart interfaceEditPart) {
		return interfaceEditPart.isInput() ? getInputFigure(interfaceEditPart) : getOutputFigure(interfaceEditPart);
	}

	private IFigure getInputFigure(final InterfaceEditPart interfaceEditPart) {
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

	private IFigure getOutputFigure(final InterfaceEditPart interfaceEditPart) {
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
}
