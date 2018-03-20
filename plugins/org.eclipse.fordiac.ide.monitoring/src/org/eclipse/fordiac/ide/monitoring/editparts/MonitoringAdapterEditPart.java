/*******************************************************************************
 * Copyright (c) 2015 - 2017 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.application.figures.FBFigure;
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
		return new FBFigure(getFB(), null);
	}

	private FB getFB(){
		return getModel().getMonitoredAdapterFB();
	}
	@Override
	protected EContentAdapter createContentAdapter() {
		return new EContentAdapter() {
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

	private FBFigure getCastedFigure() {
		if (getFigure() instanceof FBFigure) {
			return (FBFigure) getFigure();
		}
		return null;
	}
	
	@Override
	protected void createEditPolicies() {
		//currently for adapters we don't need any edit policies
	}
	
	@Override
	public boolean understandsRequest(Request request) {
		if (request.getType() == RequestConstants.REQ_DIRECT_EDIT
				|| request.getType() == RequestConstants.REQ_OPEN) {
			//no direct edit for the monitored adapter fb
			return false;
		} 
		return super.understandsRequest(request);
	}

	@Override
	public void performRequest(Request request) {
		if (request.getType() == RequestConstants.REQ_DIRECT_EDIT
				|| request.getType() == RequestConstants.REQ_OPEN) {
			//no direct edit for the monitored adapter fb
		}else {
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
					getCastedFigure().getEventInputs().add(child);
				} else{
					if(interfaceEditPart.isAdapter()){
						getCastedFigure().getSockets().add(child);
					}else if (interfaceEditPart.isVariable()) {
						getCastedFigure().getDataInputs().add(child);
					}
				}
			} else {
				if (interfaceEditPart.isEvent()) {
					getCastedFigure().getEventOutputs().add(child);
				} else { 
					if(interfaceEditPart.isAdapter()){
						getCastedFigure().getPlugs().add(child);
					}else if (interfaceEditPart.isVariable()) {
						getCastedFigure().getDataOutputs().add(child);
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
					getCastedFigure().getEventInputs().remove(child);
				} else { 
					if(interfaceEditPart.isAdapter()){
						getCastedFigure().getSockets().remove(child);
					}else if (interfaceEditPart.isVariable()) {
						getCastedFigure().getDataInputs().remove(child);
					}	
				}
			} else {
				if (interfaceEditPart.isEvent()) {
					getCastedFigure().getEventOutputs().remove(child);
				} else { 
					if(interfaceEditPart.isAdapter()){
						getCastedFigure().getPlugs().remove(child);
					}else if (interfaceEditPart.isVariable()) {
						getCastedFigure().getDataOutputs().remove(child);
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
		return (Label)getFigure();
	}
}
