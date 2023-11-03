/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, AIT, fortiss GmbH
 * 				 2019 - 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Filip Andren, Alois Zoitl, Monika Wenger
 *   - initial API and implementation and/or initial documentation
 *   Alois Zoitl - fixed untyped subapp interface updates and according code cleanup
 *   Daniel Lindhuber - altered methods to work with the elk layouter
 *   Alois Zoitl - Moved code to common base class
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.application.policies.FBNetworkXYLayoutEditPolicy;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;

public class UISubAppNetworkEditPart extends EditorWithInterfaceEditPart {
	private final Adapter contentAdapter = new AdapterImpl() {
		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			switch (notification.getEventType()) {
			case Notification.ADD, Notification.ADD_MANY, Notification.MOVE, Notification.REMOVE,
					Notification.REMOVE_MANY:
				refreshChildren();
				break;
			case Notification.SET:
				refreshVisuals();
				break;
			default:
				break;
			}
		}
	};

	private final Adapter subAppInterfaceAdapter = new EContentAdapter() {
		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			switch (notification.getEventType()) {
			case Notification.ADD:
				if (LibraryElementPackage.eINSTANCE.getConfigurableObject_Attributes()
						.equals(notification.getFeature())) {
					refreshVisuals();
					break;
				}
				//$FALL-THROUGH$
			case Notification.ADD_MANY, Notification.MOVE, Notification.REMOVE, Notification.REMOVE_MANY:
				refreshChildren();
				break;
			default:
				break;
			}
		}
	};

	@Override
	public void activate() {
		super.activate();
		if ((null != getModel()) && !getModel().eAdapters().contains(contentAdapter)) {
			getModel().eAdapters().add(contentAdapter);
			if ((null != getSubApp()) && !getSubApp().getInterface().eAdapters().contains(subAppInterfaceAdapter)) {
				getSubApp().getInterface().eAdapters().add(subAppInterfaceAdapter);
			}
		}
	}

	@Override
	public void deactivate() {
		super.deactivate();
		if (null != getModel()) {
			getModel().eAdapters().remove(contentAdapter);
			if (null != getSubApp()) {
				getSubApp().getInterface().eAdapters().remove(subAppInterfaceAdapter);
			}
		}
	}

	public SubApp getSubApp() {
		return (SubApp) getModel().eContainer();
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
		// handles constraint changes (e.g. moving and/or resizing) of model
		// elements and creation of new model elements
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new FBNetworkXYLayoutEditPolicy());
	}

	@Override
	protected InterfaceList getInterfaceList() {
		return getSubApp().getInterface();
	}

}