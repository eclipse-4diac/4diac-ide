/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, AIT, fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
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
 *   Bianca Wiesmayr - fixed untyped subapp interface reorder/delete
 *   Alois Zoitl - separated FBNetworkElement from instance name for better
 *                 direct editing of instance names
 *               - added update support for removing or readding subapp type
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.application.actions.OpenSubApplicationEditorAction;
import org.eclipse.fordiac.ide.application.figures.FBNetworkElementFigure;
import org.eclipse.fordiac.ide.application.figures.SubAppForFbNetworkFigure;
import org.eclipse.fordiac.ide.application.policies.FBAddToSubAppLayoutEditPolicy;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;

public class SubAppForFBNetworkEditPart extends AbstractFBNElementEditPart {

	private Adapter subAppInterfaceAdapter = new EContentAdapter() {
		@Override
		public void notifyChanged(final Notification notification) {
			switch (notification.getEventType()) {
			case Notification.ADD:
			case Notification.ADD_MANY:
			case Notification.MOVE:
			case Notification.REMOVE:
			case Notification.REMOVE_MANY:
				refreshChildren();
				break;
			default:
				break;
			}
		}
	};

	public SubAppForFBNetworkEditPart() {
		super();
	}

	@Override
	public void activate() {
		super.activate();
		if ((null != getModel()) && !getModel().getInterface().eAdapters().contains(subAppInterfaceAdapter)) {
			getModel().getInterface().eAdapters().add(subAppInterfaceAdapter);
		}
	}

	@Override
	public void deactivate() {
		super.deactivate();
		if (null != getModel()) {
			getModel().getInterface().eAdapters().remove(subAppInterfaceAdapter);
		}
	}

	@Override
	protected IFigure createFigureForModel() {
		return new SubAppForFbNetworkFigure(getModel(), this);
	}

	@Override
	public SubAppForFbNetworkFigure getFigure() {
		return (SubAppForFbNetworkFigure) super.getFigure();
	}

	@Override
	public SubApp getModel() {
		return (SubApp) super.getModel();
	}

	@Override
	public Adapter createContentAdapter() {
		return new AdapterImpl() {
			@Override
			public void notifyChanged(final Notification notification) {
				super.notifyChanged(notification);
				switch (notification.getEventType()) {
				case Notification.ADD:
				case Notification.ADD_MANY:
				case Notification.MOVE:
					if (notification.getNewValue() instanceof IInterfaceElement) {
						refreshChildren();
					}
					break;
				case Notification.REMOVE:
				case Notification.REMOVE_MANY:
					if (notification.getOldValue() instanceof IInterfaceElement) {
						refreshChildren();
					}
					break;
				case Notification.SET:
					refreshVisuals();
					break;
				default:
					break;
				}
				refreshToolTip();
				backgroundColorChanged(getFigure());
			}
		};
	}

	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		// Add policy to handle drag&drop of fbs
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new FBAddToSubAppLayoutEditPolicy());
	}

	@Override
	public void performRequest(final Request request) {
		if (request.getType().equals(RequestConstants.REQ_OPEN)) {
			if (null != getModel().getPaletteEntry()) {
				// we have a type open the sub-app type editor
				FBNetworkElementFigure.openTypeInEditor(getModel());
			} else {
				SubApp subApp = getModel();
				if ((null == subApp.getSubAppNetwork()) && subApp.isMapped()) {
					// we are mapped and the mirrored subapp located in the resource, get the one
					// from the application
					subApp = (SubApp) subApp.getOpposite();
				}
				new OpenSubApplicationEditorAction(subApp).run();
			}
		} else {
			super.performRequest(request);
		}
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		getFigure().updateTypeLabel(getModel());
	}
}
