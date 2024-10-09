/*******************************************************************************
 * Copyright (c) 2008, 2012, 2016, 2017 Profactor GbmH, TU Wien ACIN, fortiss GmbH,
 * 				 2018 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.editparts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.gef.editparts.AbstractDiagramEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.systemconfiguration.policies.SystemConfXYLayoutEditPolicy;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;

public class SystemNetworkEditPart extends AbstractDiagramEditPart {
	private Adapter adapter;

	@Override
	protected ConnectionRouter createConnectionRouter(final IFigure figure) {
		// we have a connection specific router so return null here
		return null;
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

	public Adapter getContentAdapter() {
		if (null == adapter) {
			adapter = new AdapterImpl() {
				@Override
				public void notifyChanged(final Notification notification) {
					final int type = notification.getEventType();
					switch (type) {
					case Notification.ADD:
					case Notification.ADD_MANY:
					case Notification.REMOVE:
					case Notification.REMOVE_MANY:
						refreshChildren();
						break;
					case Notification.SET:
						break;
					default:
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

	@Override
	protected List<EObject> getModelChildren() {
		final ArrayList<EObject> children = new ArrayList<>();
		children.addAll(getModel().getDevices());
		children.addAll(getModel().getSegments());
		children.addAll(getDeviceInputValues(getModel().getDevices()));
		return children;
	}

	private static List<? extends EObject> getDeviceInputValues(final EList<Device> devices) {
		final List<Value> children = new ArrayList<>();
		for (final Device dev : devices) {
			for (final VarDeclaration varDecl : dev.getVarDeclarations()) {
				if (varDecl.getValue() != null) {
					children.add(varDecl.getValue());
				}
			}
		}
		return children;
	}

}
