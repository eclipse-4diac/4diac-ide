/*******************************************************************************
 * Copyright (c) 2008 - 2016 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.resourceediting.editparts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.application.editparts.FBNetworkEditPart;
import org.eclipse.fordiac.ide.application.policies.FBNetworkXYLayoutEditPolicy;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.eclipse.swt.graphics.Point;

/**
 * The Class FBNetworkContainerEditPart.
 *
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */
public class FBNetworkContainerEditPart extends FBNetworkEditPart {

	/** The content adapter. */
	private Adapter contentAdapter;

	private final Map<IInterfaceElement, VirtualIO> virutalIOMapping = new HashMap<>();

	@Override
	protected Adapter getContentAdapter() {
		if (null == contentAdapter) {
			contentAdapter = new AdapterImpl() {
				@Override
				public void notifyChanged(Notification notification) {
					super.notifyChanged(notification);
					Object feature = notification.getFeature();
					if (LibraryElementPackage.eINSTANCE.getFBNetwork_NetworkElements().equals(feature)
							|| LibraryElementPackage.eINSTANCE.getIInterfaceElement_InputConnections().equals(feature)
							|| LibraryElementPackage.eINSTANCE.getIInterfaceElement_OutputConnections()
									.equals(feature)) {
						refreshChildren();
						refreshVisuals();
					}
				}
			};
		}
		return contentAdapter;
	}

	public VirtualIO getVirtualIOElement(IInterfaceElement element) {
		return virutalIOMapping.get(element);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected List getModelChildren() {
		virutalIOMapping.clear();
		ArrayList<Object> children = new ArrayList<>(super.getModelChildren());
		ArrayList<Object> interfaceElements = new ArrayList<>();

		for (Object object : children) {
			if (object instanceof FBNetworkElement) {
				FBNetworkElement fbNetworkelement = (FBNetworkElement) object;
				if (fbNetworkelement.isMapped()) {
					FBNetworkElement opposite = fbNetworkelement.getOpposite();
					for (IInterfaceElement ie : opposite.getInterface().getAllInterfaceElements()) {
						EList<Connection> connections = (ie.isIsInput()) ? ie.getInputConnections()
								: ie.getOutputConnections();
						for (Connection connection : connections) {
							if (connection.isBrokenConnection()) {
								VirtualIO vIO = createVirtualIOElement(fbNetworkelement, ie.getName());
								if (null != vIO) {
									interfaceElements.add(vIO);
								}
							}
						}
					}
				}
			}
		}

		children.addAll(interfaceElements);
		return children;
	}

	private VirtualIO createVirtualIOElement(FBNetworkElement fbNetworkelement, String name) {
		IInterfaceElement ie = fbNetworkelement.getInterfaceElement(name);
		if (null != ie) {
			VirtualIO vIO = new VirtualIO(ie);
			virutalIOMapping.put(ie, vIO);
			return vIO;
		}
		return null;
	}

	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();

		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new FBNetworkXYLayoutEditPolicy());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.gef.editparts.AbstractEditPart#performRequest(org.eclipse.gef
	 * .Request)
	 */
	@Override
	public void performRequest(final Request req) {
		Command cmd = getCommand(req);
		if (cmd != null && cmd.canExecute()) {
			getViewer().getEditDomain().getCommandStack().execute(cmd);
		}
		super.performRequest(req);
	}

	@Override
	protected void refreshVisuals() {
		Point p = getParent().getViewer().getControl().getSize();
		Rectangle rect = new Rectangle(0, 0, p.x, p.y);
		((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), rect);
		super.refreshVisuals();
	}

}
