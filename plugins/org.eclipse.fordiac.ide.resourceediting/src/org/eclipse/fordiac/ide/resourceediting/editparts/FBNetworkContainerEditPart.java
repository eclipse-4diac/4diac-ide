/*******************************************************************************
 * Copyright (c) 2008, 2025 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 *                          Primetals Technologies Germany GmbH
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
import java.util.Objects;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.application.editparts.FBNetworkEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.swt.graphics.Point;

/**
 * The Class FBNetworkContainerEditPart.
 *
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */
public class FBNetworkContainerEditPart extends FBNetworkEditPart {

	/** The content adapter. */
	private Adapter contentAdapter;

	private final Map<IInterfaceElement, VirtualIO> virtualIOMapping = new HashMap<>();

	@Override
	protected Adapter getContentAdapter() {
		if (null == contentAdapter) {
			contentAdapter = new AdapterImpl() {
				@Override
				public void notifyChanged(final Notification notification) {
					super.notifyChanged(notification);
					final Object feature = notification.getFeature();
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

	public VirtualIO getVirtualIOElement(final IInterfaceElement element) {
		return virtualIOMapping.get(element);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected List getModelChildren() {
		virtualIOMapping.clear();
		final ArrayList<Object> children = new ArrayList<>(super.getModelChildren());
		final ArrayList<Object> interfaceElements = new ArrayList<>();

		for (final Object object : children) {
			if (object instanceof final BlockFBNetworkElement fbNetworkelement && fbNetworkelement.isMapped()) {
				for (final IInterfaceElement ie : fbNetworkelement.getOpposite().getInterface()
						.getAllInterfaceElements()) {
					final EList<Connection> connections = (ie.isIsInput()) ? ie.getInputConnections()
							: ie.getOutputConnections();

					connections.stream().filter(Connection::isBrokenConnection)
							.map(con -> createVirtualIOElement(fbNetworkelement, ie.getName())).filter(Objects::nonNull)
							.forEach(interfaceElements::add);
				}
			}
		}

		children.addAll(interfaceElements);
		return children;
	}

	private VirtualIO createVirtualIOElement(final BlockFBNetworkElement fbNetworkelement, final String name) {
		final IInterfaceElement ie = fbNetworkelement.getInterfaceElement(name);
		if ((null != ie) && (virtualIOMapping.get(ie) == null)) {
			final VirtualIO vIO = new VirtualIO(ie);
			virtualIOMapping.put(ie, vIO);
			return vIO;
		}
		return null;
	}

	@Override
	protected void refreshVisuals() {
		final Point p = getParent().getViewer().getControl().getSize();
		final Rectangle rect = new Rectangle(0, 0, p.x, p.y);
		((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), rect);
		super.refreshVisuals();
	}

}
