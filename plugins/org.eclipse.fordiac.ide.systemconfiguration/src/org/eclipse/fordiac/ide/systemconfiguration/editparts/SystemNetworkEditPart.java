/*******************************************************************************
 * Copyright (c) 2008, 2012, 2016, 2017 Profactor GbmH, TU Wien ACIN, fortiss GmbH,  
 * 				 2018 Johannes Kepler University
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
package org.eclipse.fordiac.ide.systemconfiguration.editparts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.gef.editparts.AbstractDiagramEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.systemconfiguration.policies.SystemConfXYLayoutEditPolicy;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;

public class SystemNetworkEditPart extends AbstractDiagramEditPart {
	private EContentAdapter adapter;

	@Override
	protected IFigure createFigure() {
		Figure f = new FreeformLayer();
		f.setBorder(new MarginBorder(10));
		f.setLayoutManager(new FreeformLayout());
		f.setOpaque(false);
		// Create the static router for the connection layer
		return f;
	}

	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			getModel().eAdapters().add(getContentAdapter());
		}
	}

	@Override
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			getModel().eAdapters().remove(getContentAdapter());
		}
	}

	public EContentAdapter getContentAdapter() {
		if (adapter == null) {
			adapter = new EContentAdapter() {
				@Override
				public void notifyChanged(final Notification notification) {
					int type = notification.getEventType();
					switch (type) {
					case Notification.ADD:
					case Notification.ADD_MANY:
					case Notification.REMOVE:
					case Notification.REMOVE_MANY:
						refreshChildren();
						break;
					case Notification.SET:
						break;
					}
				}
			};
		}
		return adapter;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
		// handles constraint changes (e.g. moving and/or resizing) of model
		// elements and creation of new model elements
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new SystemConfXYLayoutEditPolicy());

	}

	@Override
	public SystemConfiguration getModel() {
		return (SystemConfiguration) super.getModel();
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	protected List getModelChildren() {
		ArrayList<EObject> children = new ArrayList<>();
		children.addAll(getModel().getDevices());
		children.addAll(getModel().getSegments());
		children.addAll(getDeviceInputValues(getModel().getDevices()));
		return children;
	}
	
	
	 private static List<? extends EObject> getDeviceInputValues(EList<Device> devices) {
		 List<Value> children = new ArrayList<>();
		 for (Device dev : devices) {
			 for (VarDeclaration varDecl : dev.getVarDeclarations()) {
				 if (varDecl.getValue() != null) {
					 children.add(varDecl.getValue());
				 }
			 }
		 }
		 return children;		 
	 }
			


	

}
