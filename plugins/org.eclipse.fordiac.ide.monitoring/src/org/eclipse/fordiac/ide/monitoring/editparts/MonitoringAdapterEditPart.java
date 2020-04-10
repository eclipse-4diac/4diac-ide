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
		return new FBNetworkElementFigure(getFB(), null);
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
				Display.getDefault().asyncExec(new Runnable() {

					@Override
					public void run() {
						refreshVisuals();

					}
				});
			}

		};
	}

	@Override
	public FBNetworkElementFigure getFigure() {
		if (super.getFigure() instanceof FBNetworkElementFigure) {
			return (FBNetworkElementFigure) super.getFigure();
		}
		return null;
	}

	@Override
	protected void createEditPolicies() {
		// currently for adapters we don't need any edit policies
	}

	@Override
	public boolean understandsRequest(Request request) {
		if (request.getType() == RequestConstants.REQ_DIRECT_EDIT || request.getType() == RequestConstants.REQ_OPEN) {
			// no direct edit for the monitored adapter fb
			return false;
		}
		return super.understandsRequest(request);
	}

	@Override
	public void performRequest(Request request) {
		if (request.getType() == RequestConstants.REQ_DIRECT_EDIT || request.getType() == RequestConstants.REQ_OPEN) {
			// no direct edit for the monitored adapter fb
		} else {
			super.performRequest(request);
		}
	}

	@Override
	protected void addChildVisual(final EditPart childEditPart, final int index) {
		IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
		if (childEditPart instanceof InterfaceEditPart) {
			InterfaceEditPart interfaceEditPart = (InterfaceEditPart) childEditPart;
			if (interfaceEditPart.isInput()) {
				if (interfaceEditPart.isEvent()) {
					getFigure().getEventInputs().add(child);
				} else {
					if (interfaceEditPart.isAdapter()) {
						getFigure().getSockets().add(child);
					} else if (interfaceEditPart.isVariable()) {
						getFigure().getDataInputs().add(child);
					}
				}
			} else {
				if (interfaceEditPart.isEvent()) {
					getFigure().getEventOutputs().add(child);
				} else {
					if (interfaceEditPart.isAdapter()) {
						getFigure().getPlugs().add(child);
					} else if (interfaceEditPart.isVariable()) {
						getFigure().getDataOutputs().add(child);
					}
				}

			}
		} else {
			super.addChildVisual(childEditPart, index);
		}
	}

	@Override
	protected void removeChildVisual(final EditPart childEditPart) {
		IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
		if (childEditPart instanceof InterfaceEditPart) {
			InterfaceEditPart interfaceEditPart = (InterfaceEditPart) childEditPart;
			if (interfaceEditPart.isInput()) {
				if (interfaceEditPart.isEvent()) {
					getFigure().getEventInputs().remove(child);
				} else {
					if (interfaceEditPart.isAdapter()) {
						getFigure().getSockets().remove(child);
					} else if (interfaceEditPart.isVariable()) {
						getFigure().getDataInputs().remove(child);
					}
				}
			} else {
				if (interfaceEditPart.isEvent()) {
					getFigure().getEventOutputs().remove(child);
				} else {
					if (interfaceEditPart.isAdapter()) {
						getFigure().getPlugs().remove(child);
					} else if (interfaceEditPart.isVariable()) {
						getFigure().getDataOutputs().remove(child);
					}
				}

			}
		} else {
			super.removeChildVisual(childEditPart);
		}
	}

	@Override
	protected List<Object> getModelChildren() {
		ArrayList<Object> elements = new ArrayList<>();
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
