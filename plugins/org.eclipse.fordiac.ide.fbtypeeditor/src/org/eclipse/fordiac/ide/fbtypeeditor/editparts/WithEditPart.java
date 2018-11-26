/*******************************************************************************
 * Copyright (c) 2011 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.editparts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteWithCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.eclipse.fordiac.ide.util.Activator;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gef.editpolicies.ConnectionEditPolicy;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

public class WithEditPart extends AbstractConnectionEditPart {

	public With getCastedModel() {
		return (With) getModel();
	}

	private boolean isInput() {
		With with = getCastedModel();
		if(null != with){
			Event event = (Event) with.eContainer();
			if( null != event){
				return event.isIsInput();
			}else{
				return false;
			}		
		}
		return false;
	}

	private int calculateInputWithPos() {
		int pos;
		With with = getCastedModel();
		Event event = (Event) with.eContainer();
		InterfaceList interfaceList = (InterfaceList) event.eContainer();
		pos = interfaceList.getEventInputs().indexOf(event) + 1;
		return pos;
	}

	private int calculateOutputWithPos() {
		int pos;
		With with = getCastedModel();
		Event event = (Event) with.eContainer();
		InterfaceList interfaceList = (InterfaceList) event.eContainer();
		pos = interfaceList.getEventOutputs().indexOf(event) + 1;
		return pos;
	}

	@Override
	protected void createEditPolicies() {
		// // Selection handle edit policy.
		// // Makes the connection show a feedback, when selected by the user.
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE,
				new ConnectionEndpointEditPolicy());
		// // Allows the removal of the connection model element
		installEditPolicy(EditPolicy.CONNECTION_ROLE, new ConnectionEditPolicy() {
			@Override
			protected Command getDeleteCommand(final GroupRequest request) {
				return new DeleteWithCommand(getCastedModel());
			}
		});
	}


	@Override
	protected IFigure createFigure() {
		PolylineConnection connection = (PolylineConnection) super.createFigure();		
		updateConnection(connection);
		return connection;
	}

	private void updateConnection(PolylineConnection connection) {
		int h = 15;
		float scale = 0.2f;
		PointList rect = new PointList();
		rect.addPoint(-h, -h);
		rect.addPoint(-h, h);
		rect.addPoint(h, h);
		rect.addPoint(h, -h);
		rect.addPoint(-h, -h);
		rect.addPoint(0, -h);
		if (isInput()) {
			rect.addPoint(0, -h - 45);
			rect.addPoint(0, +h + 45 * calculateInputWithPos());
		} else {
			rect.addPoint(0, -h - 45 * calculateOutputWithPos());
			rect.addPoint(0, +h + 45);
		}
		rect.addPoint(0, -h);
		PointList targetRect = new PointList();
		targetRect.addPoint(-h, -h);
		targetRect.addPoint(-h, h);
		targetRect.addPoint(h, h);
		targetRect.addPoint(h, -h);
		targetRect.addPoint(-h, -h);
		targetRect.addPoint(0, -h);
		if (isInput()) {
			targetRect.addPoint(0, -h - 45 * calculateInputWithPos());
			targetRect.addPoint(0, +h + 45);
		} else {
			targetRect.addPoint(0, -h - 45);
			targetRect.addPoint(0, +h + 45 * calculateOutputWithPos());
		}
		targetRect.addPoint(0, -h);
		PolygonDecoration rectDec = new PolygonDecoration();
		rectDec.setTemplate(targetRect.getCopy());
		rectDec.setScale(scale, scale);
		rectDec.setFill(false);
		connection.setTargetDecoration(rectDec);
		PolygonDecoration rectDec2 = new PolygonDecoration();
		rectDec2.setTemplate(rect.getCopy());
		rectDec2.setScale(scale, scale);
		rectDec2.setFill(false);
		connection.setSourceDecoration(rectDec2);
	}

	private final IPropertyChangeListener propertyChangeListener = new IPropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent event) {
		}
	};

	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			Activator.getDefault().getPreferenceStore().addPropertyChangeListener(
					propertyChangeListener);
		}
	}

	@Override
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			Activator.getDefault().getPreferenceStore()
					.removePropertyChangeListener(propertyChangeListener);
		}
	}
	
	public void updateWithPos() {
		if(null != getCastedModel().eContainer()){
			//if the container is null our model got already removed from it. We don't need to perform updates.
			updateConnection((PolylineConnection)getFigure());	
			refreshSourceAnchor();
			refreshTargetAnchor();
		}
	}

	public WithEditPart() {
		super();
	}
}
