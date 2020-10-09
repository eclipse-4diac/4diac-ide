/*******************************************************************************
 * Copyright (c) 2008, 2012 - 2016 Profactor GbmH, TU Wien ACIN, fortiss GmbH
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

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.gef.policies.FeedbackConnectionEndpointEditPolicy;
import org.eclipse.fordiac.ide.gef.router.BendpointPolicyRouter;
import org.eclipse.fordiac.ide.gef.router.RouterUtil;
import org.eclipse.fordiac.ide.model.libraryElement.Color;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Link;
import org.eclipse.fordiac.ide.systemconfiguration.policies.DeleteLinkEditPolicy;
import org.eclipse.fordiac.ide.systemconfiguration.routers.LinkConnectionRouter;
import org.eclipse.fordiac.ide.util.ColorManager;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;

public class LinkEditPart extends AbstractConnectionEditPart {
	private final Adapter adapter = new AdapterImpl() {
		@Override
		public void notifyChanged(Notification notification) {
			super.notifyChanged(notification);
			Object feature = notification.getFeature();
			if (LibraryElementPackage.eINSTANCE.getColorizableElement_Color().equals(feature)) {
				updateLinkColor(getFigure());
			}
			refreshVisuals();
		}
	};

	@Override
	public Link getModel() {
		return (Link) super.getModel();
	}

	protected void updateLinkColor(IFigure connection) {
		if (null != getModel().getSegment()) {
			Color segmentColor = getModel().getSegment().getColor();
			if (null != segmentColor) {
				connection.setForegroundColor(ColorManager.getColor(segmentColor));
			}
		}
	}

	@Override
	protected void createEditPolicies() {
		// Selection handle edit policy.
		// Makes the connection show a feedback, when selected by the user.
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, new FeedbackConnectionEndpointEditPolicy());
		installEditPolicy(EditPolicy.CONNECTION_ROLE, new DeleteLinkEditPolicy());
		if (getConnectionFigure().getConnectionRouter() instanceof BendpointPolicyRouter) {
			installEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE,
					((BendpointPolicyRouter) getConnectionFigure().getConnectionRouter())
							.getBendpointPolicy(getModel()));
		}
	}

	@Override
	protected IFigure createFigure() {
		PolylineConnection connection;
		connection = RouterUtil.getConnectionRouterFactory(null).createConnectionFigure();
		connection.setConnectionRouter(new LinkConnectionRouter(this));
		connection.setLineWidth(3);
		updateLinkColor(connection);
		return connection;
	}

	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			getModel().eAdapters().add(adapter);
			getModel().getSegment().eAdapters().add(adapter);
		}
	}

	@Override
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			getModel().eAdapters().remove(adapter);
			if (null != getModel().getSegment()) {
				getModel().getSegment().eAdapters().remove(adapter);
			}
		}
	}
}